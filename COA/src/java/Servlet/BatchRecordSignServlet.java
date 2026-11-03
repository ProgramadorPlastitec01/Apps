package Servlet;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Method.OfficePlatformService;
import Method.SignatureHistory;
import Method.SignatureStorage;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 * Firma / aprobación documental del Batch Record unificado (requerimiento
 * 1.1.U01, rol "Coordinadora Documental"): la coordinadora da su aval de que
 * el expediente del lote quedó correctamente parametrizado.
 *
 * La firma se estampa automáticamente en la portada ("Inventario de
 * registros compilados", la lista de verificación del lote), dentro del
 * bloque "REVISIÓN Y APROBACIÓN DOCUMENTAL" que deja reservado
 * BatchRecordPdfGenerateServlet: imagen de firma del usuario (session
 * "Firma", ver Method.SignatureStorage), nombre, cargo (rol) y fecha. El
 * estado "PENDIENTE DE APROBACIÓN DOCUMENTAL" de la portada se cubre con
 * "APROBADO". Las posiciones se ubican leyendo el texto del PDF (Chrome
 * headless embebe el texto), no con coordenadas fijas, así funciona aunque
 * el inventario ocupe varias páginas.
 *
 * Igual que SupportDocumentSignServlet: sube el resultado como archivo nuevo
 * ("_FIRMADO_{usuario}_{timestamp}") a la misma carpeta "BatchRecord" del
 * lote y purga el pendiente original, así un Batch Record es siempre
 * exactamente uno de "pendiente de aprobación" o "aprobado". Nada queda en
 * disco local.
 *
 * Gated by permission code [41] ("Firmar / aprobar Batch Record"), ver
 * sql/2026-11-03_coordinadora_documental_firma_batch_record.sql.
 */
@WebServlet("/BatchRecordSignServlet")
public class BatchRecordSignServlet extends HttpServlet {

    private static final String PERMISSION_CODE = "[41]";

    /** Textos que BatchRecordPdfGenerateServlet escribe en la portada; deben coincidir. */
    public static final String MARCA_BLOQUE = "REVISIÓN Y APROBACIÓN DOCUMENTAL";
    public static final String ESTADO_PENDIENTE = "PENDIENTE DE APROBACIÓN DOCUMENTAL";
    public static final String LABEL_FIRMA = "Firma:";
    public static final String LABEL_APROBADO_POR = "Aprobado por:";
    public static final String LABEL_CARGO = "Cargo:";
    public static final String LABEL_FECHA = "Fecha de aprobación:";

    /** Páginas iniciales donde se busca el bloque (la portada nunca debería pasar de aquí). */
    private static final int MAX_PAGINAS_PORTADA = 15;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String permission = attr(session, "Permisos", "");
        String firma = attr(session, "Firma", "");
        String userName = attr(session, "Nombres", "Usuario");
        String cargo = attr(session, "NombreRol", "");
        String userId = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;

        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        String idParam = request.getParameter("id");

        String redirectBase = "FileManager.jsp?cliente=" + enc(cliente) + "&anio=" + enc(anio)
                + "&orden=" + enc(orden) + "&lote=" + enc(lote);

        if (!permission.contains(PERMISSION_CODE)) {
            response.sendRedirect(redirectBase + "&msg=br_sign_permission");
            return;
        }
        if (firma.trim().isEmpty()) {
            response.sendRedirect(redirectBase + "&msg=br_sign_no_signature");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (Exception ex) {
            response.sendRedirect(redirectBase + "&msg=br_sign_error");
            return;
        }

        // Solo se firma un Batch Record de ESTE lote (carpeta "BatchRecord"),
        // no cualquier archivo cuyo id llegue en el POST.
        CertificateFileJpaController certificateFiles = new CertificateFileJpaController();
        CertificateFileRow fila = null;
        for (CertificateFileRow f : certificateFiles.consultFilesByLote(cliente, anio, orden, lote, "BatchRecord")) {
            if (f.getId() == id) {
                fila = f;
                break;
            }
        }
        if (fila == null) {
            response.sendRedirect(redirectBase + "&msg=file_not_found");
            return;
        }
        if (fila.getName().contains("_FIRMADO_")) {
            response.sendRedirect(redirectBase + "&msg=br_sign_already");
            return;
        }

        byte[] signatureBytes;
        try {
            signatureBytes = SignatureStorage.readBytes(getServletContext(), firma);
        } catch (IOException ex) {
            getServletContext().log("No se pudo obtener la firma del gestor de archivos: " + firma, ex);
            signatureBytes = null;
        }
        if (signatureBytes == null) {
            response.sendRedirect(redirectBase + "&msg=br_sign_no_signature");
            return;
        }

        String archivo = fila.getName();
        int dot = archivo.toLowerCase().lastIndexOf(".pdf");
        String baseName = dot > 0 ? archivo.substring(0, dot) : archivo;
        Date ahora = new Date();
        String safeUserName = userName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
        String nombreFirmado = baseName + "_FIRMADO_" + safeUserName + "_"
                + new SimpleDateFormat("yyyyMMdd_HHmmss").format(ahora) + ".pdf";

        File origen = File.createTempFile("batchrecord_sign_src_", ".pdf");
        File destino = File.createTempFile("batchrecord_sign_", ".pdf");

        try {
            try {
                OfficePlatformService.OfficeDownload descarga = OfficePlatformService.descargarArchivo(fila.getOfficeFileId());
                try (InputStream in = descarga.inputStream) {
                    Files.copy(in, origen.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                getServletContext().log("No se pudo descargar el Batch Record pendiente de Office Platform: " + archivo, ex);
                response.sendRedirect(redirectBase + "&msg=file_not_found");
                return;
            }

            String resultadoFirma = estamparAprobacion(origen, destino, signatureBytes, firma, userName, cargo,
                    new SimpleDateFormat("dd/MM/yyyy HH:mm").format(ahora));
            if (resultadoFirma != null) {
                response.sendRedirect(redirectBase + "&msg=" + resultadoFirma);
                return;
            }

            try {
                long folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", cliente, anio, orden, lote, "BatchRecord");
                try (InputStream contenido = new FileInputStream(destino)) {
                    OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                            folderId, nombreFirmado, null, contenido, "application/pdf", userId, userName);
                    boolean registrado = certificateFiles.registerFile(cliente, anio, orden, lote, "BatchRecord", nombreFirmado,
                            resultado.fileId, resultado.uuid, resultado.mimeType, resultado.size, userId, userName);
                    if (!registrado) {
                        throw new IOException("El Batch Record firmado se subió (fileId " + resultado.fileId
                                + ") pero no se pudo registrar en certificate_files.");
                    }
                }
            } catch (IOException ex) {
                getServletContext().log("No se pudo subir el Batch Record firmado a Office Platform: " + nombreFirmado, ex);
                response.sendRedirect(redirectBase + "&msg=br_sign_error");
                return;
            }

            // El pendiente queda reemplazado por el aprobado (mismo criterio que
            // los documentos de soporte). userId/userName del uploader ORIGINAL:
            // sin ellos la API responde "éxito" pero no borra nada.
            try {
                OfficePlatformService.eliminarArchivo(fila.getOfficeFileId(), fila.getUploadedById(), fila.getUploadedByName());
                OfficePlatformService.purgarArchivo(fila.getOfficeFileId(), fila.getUploadedById(), fila.getUploadedByName());
            } catch (IOException ex) {
                getServletContext().log("No se pudo purgar el Batch Record pendiente original: " + archivo, ex);
            }
            certificateFiles.deleteFile(id);

            SignatureHistory.registrar(request, SignatureHistory.TIPO_BATCH_RECORD,
                    SignatureHistory.batchRecordKey(archivo), null, nombreFirmado, cliente, anio, orden, lote,
                    SignatureHistory.EVENTO_FIRMADO, "Batch Record firmado: revisión y aprobación documental.");

            response.sendRedirect(redirectBase + "&msg=br_sign_success");
        } finally {
            origen.delete();
            destino.delete();
        }
    }

    /**
     * Estampa la aprobación en la portada. Devuelve null si quedó firmado, o
     * el código de mensaje (msg) a mostrar si no se pudo.
     */
    private String estamparAprobacion(File origen, File destino, byte[] signatureBytes, String signatureName,
            String nombre, String cargo, String fecha) {
        try (PDDocument doc = PDDocument.load(origen)) {
            int paginas = Math.min(doc.getNumberOfPages(), MAX_PAGINAS_PORTADA);

            // Bloque de aprobación (puede caer en la 2ª+ página si el inventario es largo)
            int paginaBloque = -1;
            TextoUbicado bloque = null;
            for (int p = 1; p <= paginas && bloque == null; p++) {
                bloque = buscar(doc, p, MARCA_BLOQUE, 0f);
                if (bloque != null) {
                    paginaBloque = p;
                }
            }
            if (bloque == null) {
                // Batch Record generado antes de esta funcionalidad: su portada no
                // tiene el bloque reservado; hay que volver a generarlo.
                return "br_sign_legacy";
            }

            TextoUbicado lblFirma = buscar(doc, paginaBloque, LABEL_FIRMA, bloque.yTop);
            TextoUbicado lblAprobado = buscar(doc, paginaBloque, LABEL_APROBADO_POR, bloque.yTop);
            TextoUbicado lblCargo = buscar(doc, paginaBloque, LABEL_CARGO, bloque.yTop);
            TextoUbicado lblFecha = buscar(doc, paginaBloque, LABEL_FECHA, bloque.yTop);
            if (lblFirma == null || lblAprobado == null || lblCargo == null || lblFecha == null) {
                return "br_sign_legacy";
            }

            PDPage page = doc.getPage(paginaBloque - 1);
            float pageHeight = page.getMediaBox().getHeight();
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;
            PDFont font = PDType1Font.HELVETICA;

            PDImageXObject signatureImage = PDImageXObject.createFromByteArray(doc, signatureBytes, signatureName);
            float maxW = 210f, maxH = 52f;
            float escala = Math.min(maxW / signatureImage.getWidth(), maxH / signatureImage.getHeight());
            float imgW = signatureImage.getWidth() * escala;
            float imgH = signatureImage.getHeight() * escala;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                // Imagen debajo de la etiqueta "Firma:", dentro del área reservada
                float imgTopDown = lblFirma.yBaseline + 6f;
                cs.drawImage(signatureImage, lblFirma.xStart, pageHeight - imgTopDown - imgH, imgW, imgH);

                escribir(cs, fontBold, 10f, lblAprobado, pageHeight, nombre);
                escribir(cs, font, 10f, lblCargo, pageHeight, cargo);
                escribir(cs, font, 10f, lblFecha, pageHeight, fecha);
            }

            // Estado de la portada: "PENDIENTE DE APROBACIÓN DOCUMENTAL" -> "APROBADO"
            for (int p = 1; p <= paginas; p++) {
                TextoUbicado estado = buscar(doc, p, ESTADO_PENDIENTE, 0f);
                if (estado != null) {
                    PDPage pagEstado = doc.getPage(p - 1);
                    float h = pagEstado.getMediaBox().getHeight();
                    try (PDPageContentStream cs = new PDPageContentStream(doc, pagEstado, PDPageContentStream.AppendMode.APPEND, true, true)) {
                        cs.setNonStrokingColor(Color.WHITE);
                        // Margen generoso: el ancho de glifo que reporta PDFBox puede
                        // quedarse corto en el último carácter y dejar residuos visibles.
                        cs.addRect(estado.xStart - 2f, h - estado.yBaseline - estado.descent - 2f,
                                estado.xEnd - estado.xStart + 8f, estado.height + estado.descent + 4f);
                        cs.fill();
                        cs.setNonStrokingColor(new Color(0x1e, 0x7e, 0x34));
                        cs.beginText();
                        cs.setFont(fontBold, 10f);
                        cs.newLineAtOffset(estado.xStart, h - estado.yBaseline);
                        cs.showText("APROBADO");
                        cs.endText();
                    }
                    break;
                }
            }

            doc.save(destino);
            return null;
        } catch (Exception ex) {
            getServletContext().log("Error firmando el Batch Record", ex);
            return "br_sign_error";
        }
    }

    /** Escribe {@code valor} a la derecha de la etiqueta, sobre su misma línea base. */
    private void escribir(PDPageContentStream cs, PDFont font, float size, TextoUbicado etiqueta,
            float pageHeight, String valor) throws IOException {
        cs.setNonStrokingColor(new Color(0x21, 0x25, 0x29));
        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(etiqueta.xEnd + 5f, pageHeight - etiqueta.yBaseline);
        cs.showText(textoSeguro(font, valor));
        cs.endText();
    }

    /** Helvetica (WinAnsi) no cubre todo Unicode: lo que no se pueda codificar se reemplaza por "?". */
    private String textoSeguro(PDFont font, String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            String c = String.valueOf(texto.charAt(i));
            try {
                font.encode(c);
                sb.append(c);
            } catch (Exception ex) {
                sb.append('?');
            }
        }
        return sb.toString();
    }

    /** Posición (coordenadas top-down de PDFBox) de un texto encontrado en una página. */
    private static class TextoUbicado {
        float xStart, xEnd, yBaseline, yTop, height, descent;
    }

    /**
     * Busca {@code frase} en la página {@code pagina} (1-based), ignorando
     * espacios, y solo por debajo de {@code minYTop} (top-down). Devuelve la
     * primera coincidencia o null.
     */
    private TextoUbicado buscar(PDDocument doc, int pagina, String frase, float minYTop) throws IOException {
        final List<TextPosition> posiciones = new ArrayList<TextPosition>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                posiciones.addAll(textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(pagina);
        stripper.setEndPage(pagina);
        stripper.getText(doc);

        // Texto plano sin espacios + mapa índice-de-carácter -> TextPosition
        StringBuilder plano = new StringBuilder();
        List<TextPosition> mapa = new ArrayList<TextPosition>();
        for (TextPosition tp : posiciones) {
            String u = tp.getUnicode();
            if (u == null) {
                continue;
            }
            for (int i = 0; i < u.length(); i++) {
                char c = u.charAt(i);
                if (!Character.isWhitespace(c)) {
                    plano.append(c);
                    mapa.add(tp);
                }
            }
        }
        String objetivo = frase.replaceAll("\\s+", "");
        String texto = plano.toString();

        int desde = 0;
        while (true) {
            int idx = texto.indexOf(objetivo, desde);
            if (idx < 0) {
                return null;
            }
            TextPosition primero = mapa.get(idx);
            TextPosition ultimo = mapa.get(idx + objetivo.length() - 1);
            float yTop = primero.getYDirAdj() - primero.getHeightDir();
            if (yTop >= minYTop) {
                TextoUbicado t = new TextoUbicado();
                t.xStart = primero.getXDirAdj();
                t.xEnd = ultimo.getXDirAdj() + ultimo.getWidthDirAdj();
                t.yBaseline = primero.getYDirAdj();
                t.height = primero.getHeightDir();
                t.yTop = yTop;
                t.descent = t.height * 0.3f;
                return t;
            }
            desde = idx + 1;
        }
    }

    private static String attr(HttpSession session, String name, String def) {
        Object v = session.getAttribute(name);
        return v != null ? v.toString() : def;
    }

    private static String enc(String v) {
        try {
            return URLEncoder.encode(v == null ? "" : v, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
}
