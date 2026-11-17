package Servlet;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Method.BatchRecordChecklist;
import Method.BatchRecordManifest;
import Method.ChromeHeadlessPdf;
import Method.OfficePlatformService;
import Method.PdfImageUtil;
import Method.RemoteHtmlFetcher;
import Method.SignatureHistory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 * Generates the unified Batch Record PDF entirely on the server: renders a
 * cover page plus one PDF per source document with Chrome headless
 * (Method.ChromeHeadlessPdf), merges them with PDFBox, uploads a copy to
 * Office Platform (folder "COA/{cliente}/{anio}/{orden}/{lote}/BatchRecord",
 * registered in certificate_files) and streams the result back. No local
 * copy is kept — workDir is a per-request temp directory, deleted at the end.
 *
 * This replaces the previous client-side html2canvas + jsPDF pipeline,
 * which could not reliably reproduce the source layout (canvas slicing cuts
 * table rows across pages) and never persisted anything on the server.
 */
@WebServlet("/BatchRecordPdfGenerateServlet")
public class BatchRecordPdfGenerateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        // formato=json (FileManager.jsp): en vez de devolver el PDF, responde con el
        // id del archivo archivado, para abrirlo en otra pestaña y recargar la página
        // del lote (estado, aprobación y versiones actualizados).
        boolean json = "json".equals(request.getParameter("formato"));

        if (orden == null || lote == null) {
            fallar(response, json, HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros orden o lote.");
            return;
        }

        javax.servlet.http.HttpSession session = request.getSession(false);
        String userId = session != null && session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;
        String userName = session != null && session.getAttribute("Usuario") != null ? session.getAttribute("Usuario").toString() : null;

        String appBaseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/";

        File workDir = new File(System.getProperty("java.io.tmpdir"), "coa-batchrecord-" + System.nanoTime());
        List<File> individualPdfs = new ArrayList<File>();

        try {
            Map<String, Object> manifest = BatchRecordManifest.build(orden, lote, cliente, anio,
                    getServletContext().getRealPath("/Certificates"));
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> documentos = (List<Map<String, Object>>) manifest.get("documentos");

            if (documentos == null || documentos.isEmpty()) {
                fallar(response, json, HttpServletResponse.SC_NOT_FOUND, "No se encontraron documentos vinculados a este lote.");
                return;
            }

            // Lista de verificación R-GC-094 (requerimiento 1.1.U04) sobre este mismo
            // manifiesto: va en la portada y define el orden de los documentos.
            BatchRecordChecklist.Resultado checklist = null;
            try {
                checklist = BatchRecordChecklist.evaluar(cliente, anio, orden, lote, documentos);
                documentos = BatchRecordChecklist.ordenarDocumentos(checklist, documentos);
            } catch (Exception exChecklist) {
                getServletContext().log("No se pudo evaluar la lista de verificación R-GC-094 del lote " + lote, exChecklist);
                checklist = null;
            }

            // 1. Portada
            individualPdfs.add(ChromeHeadlessPdf.renderHtmlToPdf(buildCoverHtml(manifest, documentos, checklist), workDir));

            // 2. Cada documento, renderizado con su propio <base href> intacto
            for (Map<String, Object> item : documentos) {
                try {
                    if ("fisico".equals(item.get("categoria")) || "soporte".equals(item.get("categoria"))) {
                        File physicalPdf = renderPhysicalFile(item, workDir);
                        if (physicalPdf != null) {
                            individualPdfs.add(physicalPdf);
                        }
                        continue;
                    }

                    if ("anexo".equals(item.get("categoria"))) {
                        // DownloadGL sirve el archivo (PDF/imagen) tal cual, en binario:
                        // no es una página HTML que se pueda pasar por RemoteHtmlFetcher +
                        // Chrome (eso decodifica los bytes del archivo como si fueran texto
                        // y produce páginas de basura, ver historial de este archivo).
                        File anexoPdf = renderRemoteBinaryFile(item, workDir, appBaseUrl, request.getHeader("Cookie"));
                        if (anexoPdf != null) {
                            individualPdfs.add(anexoPdf);
                        }
                        continue;
                    }

                    if ("manga".equals(item.get("categoria")) || "manga_despeje".equals(item.get("categoria"))) {
                        // Resumen Estadístico / Registros de Despeje de Inspección Manga:
                        // BatchRecordManifest ya trajo o armó el HTML directo de su base de
                        // datos (su servlet "Reporte"/"Orden" exige sesión de usuario logeado,
                        // que COA no tiene), así que aquí solo se imprime, sin fetch remoto.
                        String htmlManga = (String) item.get("html");
                        if (htmlManga != null) {
                            individualPdfs.add(ChromeHeadlessPdf.renderHtmlToPdf(htmlManga, workDir));
                        }
                        continue;
                    }

                    String targetUrl = (String) item.get("url");
                    @SuppressWarnings("unchecked")
                    Map<String, String> postParams = (Map<String, String>) item.get("postParams");
                    String postUrl = (String) item.get("postUrl");

                    String fetchUrl = (postParams != null && postUrl != null) ? postUrl : targetUrl;
                    if (fetchUrl == null) {
                        continue;
                    }
                    fetchUrl = fetchUrl.trim();

                    getServletContext().log("DEBUG doc [" + item.get("nombre") + "]: fetchUrl=" + fetchUrl + " tienePostParams=" + (postParams != null));

                    String html = RemoteHtmlFetcher.fetch(fetchUrl, postParams, appBaseUrl, false, request.getHeader("Cookie"));
                    getServletContext().log("DEBUG doc [" + item.get("nombre") + "]: HTML obtenido, longitud=" + html.length() + " caracteres");

                    html = RemoteHtmlFetcher.injectPrintStyle(html);

                    File pdfDoc = ChromeHeadlessPdf.renderHtmlToPdf(html, workDir);
                    getServletContext().log("DEBUG doc [" + item.get("nombre") + "]: PDF generado " + pdfDoc.getAbsolutePath() + " (" + pdfDoc.length() + " bytes)");
                    individualPdfs.add(pdfDoc);
                } catch (Exception errDoc) {
                    getServletContext().log("Error generando PDF de un documento del Batch Record (" + item.get("nombre") + ")", errDoc);
                }
            }

            if (individualPdfs.size() <= 1) {
                fallar(response, json, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "No se pudo renderizar ningún documento del lote (solo se generó la portada).");
                return;
            }

            // 3. Fusionar todo en un único PDF
            File mergedPdf = new File(workDir, "unificado.pdf");
            PDFMergerUtility merger = new PDFMergerUtility();
            merger.setDestinationFileName(mergedPdf.getAbsolutePath());
            for (File pdf : individualPdfs) {
                merger.addSource(pdf);
            }
            merger.mergeDocuments(null);

            // 4. Subir una copia a Office Platform (cliente/anio/orden/lote SIN
            //    sanitizar, tal cual vienen — misma convención que FileManagerServlet)
            //    y registrarla en certificate_files, carpeta "BatchRecord". Nada queda
            //    en disco local: workDir se borra en el finally de este método.
            // Con la lista R-GC-094 incompleta (o sin poder evaluarla) el PDF es un
            // borrador: queda marcado en el nombre para que la pantalla no ofrezca
            // firmarlo y BatchRecordSignServlet lo rechace sin tener que abrirlo.
            boolean borrador = checklist == null || (checklist.aplica && !checklist.isCompleta());
            String fileName = "BatchRecord_" + safe(orden) + "_" + safe(lote) + "_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
                    + (borrador ? BatchRecordChecklist.SUFIJO_BORRADOR : "") + ".pdf";
            Long idArchivado = null;
            try {
                long folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", cliente, anio, orden, lote, "BatchRecord");
                try (InputStream contenido = new FileInputStream(mergedPdf)) {
                    OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                            folderId, fileName, null, contenido, "application/pdf", userId, userName);
                    CertificateFileJpaController certFiles = new CertificateFileJpaController();
                    if (certFiles.registerFile(cliente, anio, orden, lote, "BatchRecord", fileName,
                            resultado.fileId, resultado.uuid, resultado.mimeType, resultado.size, userId, userName)) {
                        for (CertificateFileRow fila : certFiles.consultFilesByLote(cliente, anio, orden, lote, "BatchRecord")) {
                            if (fila.getOfficeFileId() == resultado.fileId) {
                                idArchivado = fila.getId();
                            }
                        }
                    }
                }
                SignatureHistory.registrar(request, SignatureHistory.TIPO_BATCH_RECORD,
                        SignatureHistory.batchRecordKey(fileName), null, fileName, cliente, anio, orden, lote,
                        SignatureHistory.EVENTO_REGISTRADO,
                        "Batch Record generado (" + documentos.size() + " documentos), "
                        + (borrador ? "BORRADOR: no se puede aprobar hasta completar la lista y generarlo de nuevo."
                                : "pendiente de aprobación documental.")
                        + (checklist != null && checklist.aplica
                                ? " Lista de verificación R-GC-094: " + checklist.verificados + "/" + checklist.total
                                + (checklist.pendientes > 0 ? " (" + checklist.pendientes + " pendientes)." : " (completa).")
                                : ""));
            } catch (IOException exSubida) {
                // No bloquea la respuesta al usuario: el PDF ya se generó y se le va a
                // entregar igual, solo no quedó archivada la copia en el gestor.
                getServletContext().log("No se pudo archivar la copia del Batch Record PDF unificado en Office Platform", exSubida);
            }

            // 5. Responder: el id del archivo archivado (formato=json) o el PDF.
            if (json) {
                if (idArchivado == null) {
                    fallar(response, true, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "El Batch Record se generó, pero no se pudo archivar en el gestor documental. Inténtelo de nuevo.");
                    return;
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"ok\":true,\"id\":" + idArchivado + ",\"borrador\":" + borrador
                        + ",\"nombre\":\"" + jsonEsc(fileName) + "\"}");
                return;
            }
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            response.setContentLengthLong(mergedPdf.length());
            try (OutputStream out = response.getOutputStream()) {
                Files.copy(mergedPdf.toPath(), out);
            }

        } catch (Exception ex) {
            getServletContext().log("Error generando el Batch Record PDF unificado", ex);
            if (!response.isCommitted()) {
                fallar(response, json, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al generar el Batch Record PDF.");
            }
        } finally {
            deleteRecursively(workDir);
        }
    }

    /**
     * Manually uploaded physical files (PDFs, images) don't need the
     * HTML-fetch + Chrome pipeline — they're not web pages. A PDF gets merged
     * as-is; an image is dropped onto a single A4 page via PDFBox. Anything
     * else (Word/Excel, etc.) isn't previewable as a PDF today — same
     * limitation FileManager.jsp's own "PDF" button has for those types — so
     * it's skipped with a log entry rather than silently producing garbage.
     */
    private File renderPhysicalFile(Map<String, Object> item, File workDir) throws IOException {
        Object officeFileIdObj = item.get("officeFileId");
        String nombre = String.valueOf(item.get("nombre"));
        if (officeFileIdObj == null) {
            getServletContext().log("Archivo físico sin officeFileId para el Batch Record: " + nombre);
            return null;
        }
        long officeFileId = ((Number) officeFileIdObj).longValue();

        if (!workDir.exists()) {
            workDir.mkdirs();
        }

        String nameLower = nombre.toLowerCase();

        if (nameLower.endsWith(".pdf")) {
            File copy = new File(workDir, UUID.randomUUID().toString() + ".pdf");
            descargarAArchivo(officeFileId, copy);
            return copy;
        }

        if (nameLower.endsWith(".png") || nameLower.endsWith(".jpg") || nameLower.endsWith(".jpeg") || nameLower.endsWith(".gif")) {
            File tempImage = new File(workDir, UUID.randomUUID().toString() + "-" + nombre);
            descargarAArchivo(officeFileId, tempImage);
            File imagePdf = new File(workDir, UUID.randomUUID().toString() + ".pdf");
            PdfImageUtil.imageToPdf(tempImage, imagePdf);
            return imagePdf;
        }

        getServletContext().log("Archivo físico con formato no soportado para el Batch Record (se omite): " + nombre);
        return null;
    }

    /** Descarga el archivo {@code officeFileId} desde Office Platform a un archivo temporal en workDir. */
    private void descargarAArchivo(long officeFileId, File destino) throws IOException {
        OfficePlatformService.OfficeDownload descarga = OfficePlatformService.descargarArchivo(officeFileId);
        try (InputStream in = descarga.inputStream) {
            Files.copy(in, destino.toPath());
        }
    }

    /**
     * "Anexos" de Generación de Lotes se sirven vía DownloadGL?File_name=...,
     * que devuelve el archivo (típicamente un PDF, a veces una imagen) en
     * binario con Content-Disposition: attachment — no una página HTML. Se
     * descarga como bytes crudos y se trata igual que un archivo físico
     * (renderPhysicalFile), en vez de pasarlo por RemoteHtmlFetcher (que
     * decodifica la respuesta como texto) y Chrome headless.
     */
    private File renderRemoteBinaryFile(Map<String, Object> item, File workDir, String appBaseUrl, String cookieHeader) throws IOException {
        String relUrl = (String) item.get("url");
        if (relUrl == null || relUrl.trim().isEmpty()) {
            return null;
        }
        String resolvedUrl = relUrl.trim().matches("(?i)^https?://.*")
                ? relUrl.trim()
                : (appBaseUrl.endsWith("/") ? appBaseUrl : appBaseUrl + "/") + (relUrl.startsWith("/") ? relUrl.substring(1) : relUrl);

        if (!workDir.exists()) {
            workDir.mkdirs();
        }

        java.net.HttpURLConnection conn = null;
        try {
            java.net.URL url = new java.net.URL(resolvedUrl);
            conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(20000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) COA-BatchRecord/1.0");
            if (cookieHeader != null && !cookieHeader.trim().isEmpty()) {
                conn.setRequestProperty("Cookie", cookieHeader);
            }

            int status = conn.getResponseCode();
            if (status >= 400) {
                getServletContext().log("Anexo no encontrado en Generación de Lotes (HTTP " + status + "): " + resolvedUrl);
                return null;
            }

            // "nombre" es solo la etiqueta descriptiva (sin extensión); "archivo" es
            // el nombre real del archivo en disco, que es el que trae la extensión.
            Object archivoObj = item.get("archivo");
            String nameLower = (archivoObj != null ? String.valueOf(archivoObj) : String.valueOf(item.get("nombre"))).toLowerCase();

            if (nameLower.endsWith(".pdf")) {
                File copy = new File(workDir, UUID.randomUUID().toString() + ".pdf");
                try (InputStream in = conn.getInputStream()) {
                    Files.copy(in, copy.toPath());
                }
                return copy;
            }

            if (nameLower.endsWith(".png") || nameLower.endsWith(".jpg") || nameLower.endsWith(".jpeg") || nameLower.endsWith(".gif")) {
                File tempImage = new File(workDir, UUID.randomUUID().toString() + "-" + new File(nameLower).getName());
                try (InputStream in = conn.getInputStream()) {
                    Files.copy(in, tempImage.toPath());
                }
                File imagePdf = new File(workDir, UUID.randomUUID().toString() + ".pdf");
                PdfImageUtil.imageToPdf(tempImage, imagePdf);
                return imagePdf;
            }

            getServletContext().log("Anexo con formato no soportado para el Batch Record (se omite): " + item.get("nombre"));
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /** Error en el formato pedido: JSON {ok:false, error} o la página de error de siempre. */
    private static void fallar(HttpServletResponse response, boolean json, int status, String mensaje) throws IOException {
        if (!json) {
            response.sendError(status, mensaje);
            return;
        }
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"ok\":false,\"error\":\"" + jsonEsc(mensaje) + "\"}");
    }

    private static String jsonEsc(String valor) {
        return valor == null ? "" : valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "SIN_DATO" : value.trim().replaceAll("[^a-zA-Z0-9_\\-]", "_");
    }

    private void deleteRecursively(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteRecursively(f);
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
    }

    private String buildCoverHtml(Map<String, Object> manifest, List<Map<String, Object>> documentos,
            BatchRecordChecklist.Resultado checklist) {
        return buildCoverHtml(manifest, documentos, checklist, null);
    }

    /**
     * @param estadoExportacion null = portada del Batch Record (con bloque de
     *      aprobación e inventario, el que firma BatchRecordSignServlet). No
     *      null = exportación solo de la lista de verificación
     *      (BatchRecordChecklistPdfServlet): se muestra ese texto como estado y
     *      se omiten el bloque de firma y el inventario.
     */
    static String buildCoverHtml(Map<String, Object> manifest, List<Map<String, Object>> documentos,
            BatchRecordChecklist.Resultado checklist, String estadoExportacion) {
        boolean exportacion = estadoExportacion != null;
        boolean conLista = checklist != null && checklist.aplica;
        StringBuilder rows = new StringBuilder();
        int i = 1;
        for (Map<String, Object> doc : documentos) {
            Object item = doc.get("itemChecklist");
            rows.append("<tr>")
                    .append("<td align='center'>").append(i++).append("</td>");
            if (conLista) {
                rows.append("<td align='center'>").append(item != null ? esc(item) : "—").append("</td>");
            }
            rows.append("<td>").append(esc(doc.get("origen"))).append("</td>")
                    .append("<td>").append(esc(doc.get("tipo"))).append("</td>")
                    .append("<td>").append(esc(doc.get("nombre"))).append("</td>")
                    .append("</tr>");
        }

        String logo = BatchRecordManifest.logoDataUri();
        String codigoRegistro = conLista ? checklist.codigoRegistro : "R-GC-094";
        String version = conLista ? String.valueOf(checklist.version) : "11";

        return "<html><head><meta charset='UTF-8'>"
                + "<style>"
                + "@page { size: A4 landscape; margin: 10mm; }"
                + "body { font-family: Arial, sans-serif; margin:0; padding:0; color:#212529; }"
                + "table { width:100%; border-collapse: collapse; font-size:12px; }"
                + "th, td { border:1px solid #ccc; padding:6px 8px; text-align:left; }"
                + "th { background:#e9ecef; }"
                + "table.rgc094 td, table.rgc094 th { border:1px solid #444; padding:2px 6px; font-size:11px; line-height:1.25; }"
                + "table.rgc094 th { background:#fff; text-align:center; }"
                + "td.ok { color:#1a7a4c; font-weight:bold; } td.na { color:#6c757d; font-weight:bold; } td.pend { color:#c0392b; font-weight:bold; }"
                + "small.det { color:#555; font-weight:normal; font-size:9.5px; }"
                + "</style></head><body>"
                // Encabezado del registro R-GC-094 (mismo formato del registro físico).
                + "<table class='rgc094'><tr>"
                + "<td rowspan='2' style='width:28%;text-align:center;'>"
                + (logo != null ? "<img src='" + logo + "' alt='Logo' style='width:150px;height:50px'/>" : "<b>PLASTITEC S.A.S</b>")
                + "</td>"
                + "<td style='text-align:center;width:44%;'>REGISTRO</td>"
                + "<td style='text-align:center;'>CODIGO<br><b>" + esc(codigoRegistro) + "</b></td>"
                + "</tr><tr>"
                + "<td style='text-align:center;'><b>LISTA DE VERIFICACION DOCUMENTOS DEL LOTE</b></td>"
                + "<td style='text-align:center;'>VERSIÓN:<br><b>" + esc(version) + "</b></td>"
                + "</tr></table>"
                + "<table style='margin-top:8px;font-size:12px;'>"
                + "<tr><td><strong>FECHA:</strong> " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "</td>"
                + "<td><strong>ORDEN PRODUCCION:</strong> " + esc(manifest.get("orden")) + "</td>"
                + "<td><strong>CLIENTE:</strong> " + esc(manifest.get("cliente")) + "</td></tr>"
                // El estado y el bloque de aprobación los completa BatchRecordSignServlet
                // cuando la Coordinadora Documental firma: ubica estos textos en el PDF
                // (no coordenadas fijas), así que deben coincidir con sus constantes.
                + "<tr><td><strong>LOTE:</strong> " + esc(manifest.get("lote")) + "</td>"
                + "<td><strong>Año de Gestión:</strong> " + esc(manifest.get("anio")) + "</td>"
                + "<td><strong>Estado:</strong> " + (exportacion ? esc(estadoExportacion) : BatchRecordSignServlet.ESTADO_PENDIENTE) + "</td></tr>"
                + "</table>"
                + buildChecklistHtml(checklist)
                + (exportacion ? "</body></html>" : buildAprobacionEInventarioHtml(conLista, rows));
    }

    private static String buildAprobacionEInventarioHtml(boolean conLista, StringBuilder rows) {
        return ""
                // APROBADO de la lista de verificación (R-GC-094): justo debajo de la
                // tabla, como en el registro físico. Lo firma la Coordinadora Documental.
                + "<div style='margin-top:8px;border:1px solid #0b0025;border-radius:4px;page-break-inside:avoid;break-inside:avoid;'>"
                + "<div style='background:#0b0025;color:#fff;padding:4px 12px;font-weight:bold;font-size:12px;'>APROBADO &nbsp;·&nbsp; " + BatchRecordSignServlet.MARCA_BLOQUE + "</div>"
                + "<table style='border:none;font-size:12px;'><tr>"
                + "<td style='border:none;width:45%;height:80px;vertical-align:top;'><strong>" + BatchRecordSignServlet.LABEL_FIRMA + "</strong></td>"
                + "<td style='border:none;vertical-align:top;line-height:22px;'>"
                + "<strong>" + BatchRecordSignServlet.LABEL_APROBADO_POR + "</strong><br>"
                + "<strong>" + BatchRecordSignServlet.LABEL_CARGO + "</strong><br>"
                + "<strong>" + BatchRecordSignServlet.LABEL_FECHA + "</strong>"
                + "</td></tr></table>"
                + "</div>"
                + "<h4 style='margin-top:24px;background:#f0f4f8;padding:10px;color:#0b0025;border-radius:4px;page-break-before:always;'>INVENTARIO DE REGISTROS COMPILADOS</h4>"
                + "<table style='margin-top:10px;'>"
                + "<thead><tr><th>#</th>" + (conLista ? "<th>Ítem R-GC-094</th>" : "")
                + "<th>Origen del Sistema</th><th>Tipo de Registro</th><th>Nombre / Descripción</th></tr></thead>"
                + "<tbody>" + rows + "</tbody></table>"
                + "</body></html>";
    }

    /**
     * Tabla de la lista de verificación R-GC-094 (consecutivo, nombre, código,
     * verificación) y la línea de estado COMPLETA / INCOMPLETA que
     * BatchRecordSignServlet lee para no aprobar un PDF generado con
     * pendientes.
     */
    static String buildChecklistHtml(BatchRecordChecklist.Resultado checklist) {
        if (checklist == null) {
            return "<p style='margin-top:18px;color:#c0392b;'><b>No se pudo evaluar la lista de verificación R-GC-094 de este lote.</b></p>";
        }
        if (!checklist.aplica) {
            return "<p style='margin-top:18px;color:#555;'><b>Lista de verificación R-GC-094:</b> "
                    + esc(checklist.diagnostico) + "</p>";
        }
        StringBuilder filas = new StringBuilder();
        for (BatchRecordChecklist.Item item : checklist.items) {
            String clase;
            String verificacion;
            String detalle;
            if (BatchRecordChecklist.ESTADO_SISTEMA.equals(item.estado)) {
                clase = "ok";
                verificacion = "&#10004; Verificado por el sistema";
                detalle = item.detalle;
            } else if (BatchRecordChecklist.ESTADO_VERIFICADO.equals(item.estado)) {
                clase = "ok";
                verificacion = "&#10004; Verificado";
                detalle = item.usuario + " " + item.fecha
                        + (item.nombreArchivo != null ? " · " + item.nombreArchivo : "")
                        + (item.observacion.isEmpty() ? "" : " · " + item.observacion);
            } else if (BatchRecordChecklist.ESTADO_NA.equals(item.estado)) {
                clase = "na";
                verificacion = "N/A";
                detalle = item.usuario + " " + item.fecha + (item.observacion.isEmpty() ? "" : " · " + item.observacion);
            } else {
                clase = "pend";
                verificacion = "&#10008; Pendiente";
                detalle = item.detalle;
            }
            filas.append("<tr>")
                    .append("<td style='text-align:center;'>").append(item.numero).append("</td>")
                    .append("<td>").append(esc(item.nombre)).append("</td>")
                    .append("<td>").append(esc(item.codigoRegistro)).append("</td>")
                    .append("<td class='").append(clase).append("'>").append(verificacion)
                    .append(detalle == null || detalle.trim().isEmpty() ? "" : "<br><small class='det'>" + esc(detalle.trim()) + "</small>")
                    .append("</td></tr>");
        }
        String estado = checklist.isCompleta()
                ? "<span style='color:#1a7a4c;'>" + BatchRecordChecklist.PORTADA_COMPLETA + "</span>"
                : "<span style='color:#c0392b;'>" + BatchRecordChecklist.PORTADA_INCOMPLETA + "</span>";
        return "<table class='rgc094' style='margin-top:10px;'>"
                + "<tr><th colspan='4' style='color:#d00;font-size:13px;'>" + esc(checklist.titulo) + "</th></tr>"
                + "<tr><th style='width:9%;'>Consecutivo</th><th>Nombre del Registro</th><th style='width:15%;'>Código registro</th><th style='width:34%;'>VERIFICACION</th></tr>"
                + filas
                + "</table>"
                + "<p style='margin:6px 0 0 0;font-size:12px;'><b>" + estado + "</b> — "
                + checklist.verificados + " de " + checklist.total + " ítems verificados"
                + (checklist.pendientes > 0 ? ", " + checklist.pendientes + " pendiente" + (checklist.pendientes == 1 ? "" : "s") : "")
                + ".</p>";
    }

    private static String esc(Object value) {
        return value == null ? "" : String.valueOf(value)
                .replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
