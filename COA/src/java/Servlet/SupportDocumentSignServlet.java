package Servlet;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Method.OfficePlatformService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Stamps the signed-in user's registered signature (session "Firma", an
 * image under /Interface/Uploads/Signature/) onto a pending "documento de
 * soporte", at every page/position the user picked interactively in
 * SupportDocumentSign.jsp (a PDF.js viewer that shows every page at once and
 * lets the user drop as many signature copies as needed, anywhere). Sube el
 * resultado como un archivo nuevo (sufijo "_FIRMADO_{usuario}_{timestamp}") a
 * la misma carpeta "SupportDocs" del lote en Office Platform, y envía el
 * pendiente original a la papelera del gestor + borra su fila local, así un
 * documento es siempre exactamente uno de "pendiente de firma" o "firmado",
 * nunca ambos. El PDF de origen y el firmado son archivos temporales
 * efímeros (java.io.tmpdir, borrados en el finally); nada queda en disco.
 *
 * "firmas" is a single request parameter packing every placement as
 * "pagina:xFrac:yFrac:widthFrac" entries separated by ";" (built client-side
 * in SupportDocumentSign.jsp). xFrac/yFrac/widthFrac are fractions (0..1) of
 * that page's rendered width/height, so they don't depend on the viewer's
 * zoom level; xFrac/yFrac describe the top-left corner in image (top-down)
 * coordinates, and PDF page coordinates start at the bottom-left, so the Y
 * axis is flipped when drawing.
 *
 * Gated by permission code [39] ("Adjuntar y firmar documento de soporte"),
 * created via the Role/Permission admin screen (Role.java) — update this
 * literal if that permission is registered with a different id.
 */
@WebServlet("/SupportDocumentSignServlet")
public class SupportDocumentSignServlet extends HttpServlet {

    private static final String PERMISSION_CODE = "[39]";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String permission;
        try {
            permission = session.getAttribute("Permisos").toString();
        } catch (Exception e) {
            permission = "";
        }
        String firma;
        try {
            firma = session.getAttribute("Firma").toString();
        } catch (Exception e) {
            firma = "";
        }
        String userName;
        try {
            userName = session.getAttribute("Nombres").toString();
        } catch (Exception e) {
            userName = "Usuario";
        }
        String userId = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;

        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");
        String idParam = request.getParameter("id");

        String redirectBase = "FileManager.jsp"
                + "?cliente=" + cliente + "&anio=" + anio + "&orden=" + orden + "&lote=" + lote;

        if (!permission.contains(PERMISSION_CODE) || firma == null || firma.trim().isEmpty()) {
            response.sendRedirect(redirectBase + "&msg=error_permission");
            return;
        }

        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (Exception ex) {
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        CertificateFileJpaController certificateFiles = new CertificateFileJpaController();
        CertificateFileRow fila = certificateFiles.consultFileById(id);

        if (fila == null || fila.getName().contains("_FIRMADO_")) {
            // El documento ya está firmado (o no se encontró): nada que firmar de nuevo.
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        String firmasParam = request.getParameter("firmas");
        if (firmasParam == null || firmasParam.trim().isEmpty()) {
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        String signaturePath = getServletContext().getRealPath("/Interface/Uploads/Signature/" + firma);
        File signatureFile = new File(signaturePath);
        if (!signatureFile.isFile()) {
            response.sendRedirect(redirectBase + "&msg=error_sign");
            return;
        }

        String archivo = fila.getName();
        int dot = archivo.toLowerCase().lastIndexOf(".pdf");
        String baseName = dot > 0 ? archivo.substring(0, dot) : archivo;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String safeUserName = userName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
        String nombreFirmado = baseName + "_FIRMADO_" + safeUserName + "_" + timestamp + ".pdf";

        // El PDF pendiente y el firmado son archivos temporales efímeros: se
        // descarga el pendiente de Office Platform, se estampa la firma encima,
        // y el resultado se sube de vuelta sin pasar por almacenamiento local.
        File origen = File.createTempFile("supportdoc_sign_src_", ".pdf");
        File destino = File.createTempFile("supportdoc_sign_", ".pdf");

        try {
            try {
                OfficePlatformService.OfficeDownload descarga = OfficePlatformService.descargarArchivo(fila.getOfficeFileId());
                try (InputStream in = descarga.inputStream) {
                    java.nio.file.Files.copy(in, origen.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException ex) {
                getServletContext().log("No se pudo descargar el documento de soporte pendiente de Office Platform: " + archivo, ex);
                response.sendRedirect(redirectBase + "&msg=file_not_found");
                return;
            }

            boolean firmadoOk = estamparFirma(origen, destino, signatureFile, firmasParam);
            if (!firmadoOk) {
                response.sendRedirect(redirectBase + "&msg=error_sign");
                return;
            }

            try {
                long folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", cliente, anio, orden, lote, "SupportDocs");
                try (InputStream contenido = new FileInputStream(destino)) {
                    OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                            folderId, nombreFirmado, null, contenido, "application/pdf", userId, userName);
                    certificateFiles.registerFile(cliente, anio, orden, lote, "SupportDocs", nombreFirmado,
                            resultado.fileId, resultado.uuid, resultado.mimeType, resultado.size, userId, userName);
                }
            } catch (IOException ex) {
                getServletContext().log("No se pudo subir el documento de soporte firmado a Office Platform: " + nombreFirmado, ex);
                response.sendRedirect(redirectBase + "&msg=error_sign");
                return;
            }

            // El documento pendiente queda reemplazado por el firmado: nunca deben
            // coexistir ambos estados para el mismo documento de soporte, así que el
            // pendiente se borra definitivamente (no basta con enviarlo a la papelera:
            // ahí seguía apareciendo como archivo activo en el gestor).
            try {
                // userId/userName deben ser los del uploader ORIGINAL del pendiente
                // (no los de quien firma): sin ellos la API responde "éxito" pero no
                // borra nada, comprobado en vivo.
                OfficePlatformService.eliminarArchivo(fila.getOfficeFileId(), fila.getUploadedById(), fila.getUploadedByName());
                OfficePlatformService.purgarArchivo(fila.getOfficeFileId(), fila.getUploadedById(), fila.getUploadedByName());
            } catch (IOException ex) {
                getServletContext().log("No se pudo purgar el documento de soporte pendiente original: " + archivo, ex);
            }
            certificateFiles.deleteFile(id);

            response.sendRedirect(redirectBase + "&msg=support_sign_success");
        } finally {
            origen.delete();
            destino.delete();
        }
    }

    /** Estampa la firma en todas las posiciones indicadas por {@code firmasParam} y guarda en {@code destino}. Devuelve false si no se pudo aplicar ninguna firma. */
    private boolean estamparFirma(File origen, File destino, File signatureFile, String firmasParam) {
        try (PDDocument doc = PDDocument.load(origen)) {
            PDImageXObject signatureImage = PDImageXObject.createFromFile(signatureFile.getAbsolutePath(), doc);
            float aspect = (float) signatureImage.getHeight() / (float) signatureImage.getWidth();

            String[] firmas = firmasParam.split(";");
            int aplicadas = 0;
            for (String firmaEntry : firmas) {
                if (firmaEntry.trim().isEmpty()) {
                    continue;
                }
                String[] partes = firmaEntry.split(":");
                if (partes.length != 4) {
                    continue;
                }

                int pagina = Integer.parseInt(partes[0]);
                double xFrac = Double.parseDouble(partes[1]);
                double yFrac = Double.parseDouble(partes[2]);
                double widthFrac = Double.parseDouble(partes[3]);

                if (pagina < 1 || pagina > doc.getNumberOfPages()) {
                    continue;
                }

                PDPage page = doc.getPage(pagina - 1);
                PDRectangle box = page.getMediaBox();

                float drawWidth = (float) (widthFrac * box.getWidth());
                float drawHeight = drawWidth * aspect;

                float x = box.getLowerLeftX() + (float) (xFrac * box.getWidth());
                float yTop = box.getLowerLeftY() + (float) (yFrac * box.getHeight());
                float y = box.getHeight() - yTop - drawHeight;

                try (PDPageContentStream content = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    content.drawImage(signatureImage, x, y, drawWidth, drawHeight);
                }
                aplicadas++;
            }

            if (aplicadas == 0) {
                return false;
            }

            doc.save(destino);
            return true;
        } catch (Exception ex) {
            getServletContext().log("Error firmando documento de soporte", ex);
            return false;
        }
    }
}
