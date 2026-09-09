package Servlet;

import Method.BatchRecordManifest;
import Method.ChromeHeadlessPdf;
import Method.RemoteHtmlFetcher;
import java.io.File;
import java.io.IOException;
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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Generates the unified Batch Record PDF entirely on the server: renders a
 * cover page plus one PDF per source document with Chrome headless
 * (Method.ChromeHeadlessPdf), merges them with PDFBox, stores a copy under
 * the same Certificates/{cliente}/{anio}/{orden}/{lote} folder convention
 * already used for manually uploaded files, and streams the result back.
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

        if (orden == null || lote == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros orden o lote.");
            return;
        }

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
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontraron documentos vinculados a este lote.");
                return;
            }

            // 1. Portada
            individualPdfs.add(ChromeHeadlessPdf.renderHtmlToPdf(buildCoverHtml(manifest, documentos), workDir));

            // 2. Cada documento, renderizado con su propio <base href> intacto
            for (Map<String, Object> item : documentos) {
                try {
                    if ("fisico".equals(item.get("categoria"))) {
                        File physicalPdf = renderPhysicalFile(item, workDir);
                        if (physicalPdf != null) {
                            individualPdfs.add(physicalPdf);
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
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
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

            // 4. Guardar una copia en el almacenamiento de documentos del lote
            //    (misma convención de carpetas que FileManagerServlet usa para uploads manuales)
            File storageDir = new File(getServletContext().getRealPath(
                    "/Certificates/" + safe(cliente) + "/" + safe(anio) + "/" + safe(orden) + "/" + safe(lote) + "/BatchRecord"));
            storageDir.mkdirs();
            String fileName = "BatchRecord_" + safe(orden) + "_" + safe(lote) + "_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
            Files.copy(mergedPdf.toPath(), new File(storageDir, fileName).toPath());

            // 5. Responder con el PDF recién generado
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            response.setContentLengthLong(mergedPdf.length());
            try (OutputStream out = response.getOutputStream()) {
                Files.copy(mergedPdf.toPath(), out);
            }

        } catch (Exception ex) {
            getServletContext().log("Error generando el Batch Record PDF unificado", ex);
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocurrió un error al generar el Batch Record PDF.");
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
        String relPath = (String) item.get("url");
        String realPath = getServletContext().getRealPath("/" + relPath);
        File source = realPath != null ? new File(realPath) : null;
        if (source == null || !source.isFile()) {
            getServletContext().log("Archivo físico no encontrado para el Batch Record: " + relPath);
            return null;
        }

        String nameLower = source.getName().toLowerCase();
        if (!workDir.exists()) {
            workDir.mkdirs();
        }

        if (nameLower.endsWith(".pdf")) {
            File copy = new File(workDir, UUID.randomUUID().toString() + ".pdf");
            Files.copy(source.toPath(), copy.toPath());
            return copy;
        }

        if (nameLower.endsWith(".png") || nameLower.endsWith(".jpg") || nameLower.endsWith(".jpeg") || nameLower.endsWith(".gif")) {
            File imagePdf = new File(workDir, UUID.randomUUID().toString() + ".pdf");
            try (PDDocument doc = new PDDocument()) {
                PDPage page = new PDPage(PDRectangle.A4);
                doc.addPage(page);
                PDImageXObject image = PDImageXObject.createFromFile(source.getAbsolutePath(), doc);

                float margin = 20f;
                float maxWidth = page.getMediaBox().getWidth() - margin * 2;
                float maxHeight = page.getMediaBox().getHeight() - margin * 2;
                float scale = Math.min(maxWidth / image.getWidth(), maxHeight / image.getHeight());
                float drawWidth = image.getWidth() * scale;
                float drawHeight = image.getHeight() * scale;
                float x = (page.getMediaBox().getWidth() - drawWidth) / 2;
                float y = (page.getMediaBox().getHeight() - drawHeight) / 2;

                try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                    content.drawImage(image, x, y, drawWidth, drawHeight);
                }
                doc.save(imagePdf);
            }
            return imagePdf;
        }

        getServletContext().log("Archivo físico con formato no soportado para el Batch Record (se omite): " + source.getName());
        return null;
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

    private String buildCoverHtml(Map<String, Object> manifest, List<Map<String, Object>> documentos) {
        StringBuilder rows = new StringBuilder();
        int i = 1;
        for (Map<String, Object> doc : documentos) {
            rows.append("<tr>")
                    .append("<td align='center'>").append(i++).append("</td>")
                    .append("<td>").append(esc(doc.get("origen"))).append("</td>")
                    .append("<td>").append(esc(doc.get("tipo"))).append("</td>")
                    .append("<td>").append(esc(doc.get("nombre"))).append("</td>")
                    .append("</tr>");
        }

        return "<html><head><meta charset='UTF-8'>"
                + "<style>"
                + "@page { size: A4 landscape; margin: 10mm; }"
                + "body { font-family: Arial, sans-serif; margin:0; padding:0; color:#212529; }"
                + "table { width:100%; border-collapse: collapse; font-size:12px; }"
                + "th, td { border:1px solid #ccc; padding:6px 8px; text-align:left; }"
                + "th { background:#e9ecef; }"
                + "</style></head><body>"
                + "<div style='background:#0b0025;color:#fff;padding:20px;border-radius:4px;'>"
                + "<h2 style='margin:0 0 5px 0;'>PLASTITEC S.A.S</h2>"
                + "<h4 style='margin:0;font-weight:normal;opacity:0.9;'>EXPEDIENTE BATCH RECORD COMPLETO DE AUDITORÍA</h4>"
                + "</div>"
                + "<h3 style='margin-top:25px;color:#333;border-bottom:2px solid #0b0025;padding-bottom:8px;'>HOJA DE RUTA Y REGISTROS DEL LOTE</h3>"
                + "<table style='margin-top:15px;font-size:13px;'>"
                + "<tr><td><strong>Cliente / Destino:</strong> " + esc(manifest.get("cliente")) + "</td><td><strong>Orden de Producción:</strong> " + esc(manifest.get("orden")) + "</td></tr>"
                + "<tr><td><strong>Año de Gestión:</strong> " + esc(manifest.get("anio")) + "</td><td><strong>Lote de Producto:</strong> " + esc(manifest.get("lote")) + "</td></tr>"
                + "<tr><td><strong>Fecha Expedición:</strong> " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "</td><td><strong>Estado:</strong> VERIFICADO / AUDITORÍA</td></tr>"
                + "</table>"
                + "<h4 style='margin-top:30px;background:#f0f4f8;padding:10px;color:#0b0025;border-radius:4px;'>INVENTARIO DE REGISTROS COMPILADOS</h4>"
                + "<table style='margin-top:10px;'>"
                + "<thead><tr><th>#</th><th>Origen del Sistema</th><th>Tipo de Registro</th><th>Nombre / Descripción</th></tr></thead>"
                + "<tbody>" + rows + "</tbody></table>"
                + "</body></html>";
    }

    private String esc(Object value) {
        return value == null ? "" : String.valueOf(value)
                .replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
