package Servlet;

import Controller.CertificateFileJpaController;
import Method.OfficePlatformService;
import Method.PdfImageUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Uploads a "documento de soporte" (client letter, etc.) for a lote, to be
 * signed later from SupportDocumentSignServlet. Sube el binario a Office
 * Platform (carpeta "SupportDocs" dentro del lote) y registra el archivo en
 * la tabla certificate_files, para que FileManager.jsp lo muestre en la
 * pestaña "Soporte" e incluirlo en el Batch Record unificado.
 *
 * Gated by permission code [39] ("Adjuntar y firmar documento de soporte"),
 * created via the Role/Permission admin screen (Role.java) — update this
 * literal if that permission is registered with a different id.
 */
@WebServlet("/SupportDocumentUploadServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50,     // 50MB
        maxRequestSize = 1024 * 1024 * 100  // 100MB
)
public class SupportDocumentUploadServlet extends HttpServlet {

    private static final String PERMISSION_CODE = "[39]";

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            ".pdf", ".png", ".jpg", ".jpeg", ".gif"
    );

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

        String cliente = request.getParameter("cliente");
        String anio = request.getParameter("anio");
        String orden = request.getParameter("orden");
        String lote = request.getParameter("lote");

        if (!permission.contains(PERMISSION_CODE)) {
            response.sendRedirect("FileManager.jsp"
                    + "?cliente=" + cliente + "&anio=" + anio + "&orden=" + orden + "&lote=" + lote
                    + "&msg=error_permission");
            return;
        }

        String uploadedById = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;
        String uploadedByName = session.getAttribute("Usuario") != null ? session.getAttribute("Usuario").toString() : null;

        boolean uploaded = false;
        boolean rejected = false;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Long folderId = null;

        CertificateFileJpaController certificateFiles = new CertificateFileJpaController();

        for (Part part : request.getParts()) {

            if (!"file".equals(part.getName()) || part.getSize() <= 0) {
                continue;
            }

            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String nameLower = fileName.toLowerCase();
            int dot = nameLower.lastIndexOf('.');
            String ext = dot >= 0 ? nameLower.substring(dot) : "";

            if (!ALLOWED_EXTENSIONS.contains(ext)) {
                rejected = true;
                continue;
            }

            String baseName = dot >= 0 ? fileName.substring(0, dot) : fileName;
            String safeBaseName = baseName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String finalName = safeBaseName + "_" + timestamp + ".pdf";

            // El PDF final (directo o convertido de imagen) se sube a Office
            // Platform por streaming; solo la conversión imagen->PDF necesita
            // un par de archivos temporales efímeros en java.io.tmpdir (la
            // librería de conversión trabaja con File, no con Stream) que se
            // borran en el mismo finally, nunca queda nada persistente en disco.
            File pdfParaSubir = null;
            File tempImage = null;

            try {
                if (ext.equals(".pdf")) {
                    pdfParaSubir = File.createTempFile("supportdoc_", ".pdf");
                    part.write(pdfParaSubir.getAbsolutePath());
                } else {
                    tempImage = File.createTempFile("supportdoc_src_", ext);
                    part.write(tempImage.getAbsolutePath());
                    pdfParaSubir = File.createTempFile("supportdoc_", ".pdf");
                    PdfImageUtil.imageToPdf(tempImage, pdfParaSubir);
                }

                if (folderId == null) {
                    folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", cliente, anio, orden, lote, "SupportDocs");
                }

                try (InputStream contenido = new FileInputStream(pdfParaSubir)) {
                    OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                            folderId, finalName, null, contenido, "application/pdf",
                            uploadedById, uploadedByName);

                    certificateFiles.registerFile(cliente, anio, orden, lote, "SupportDocs", finalName,
                            resultado.fileId, resultado.uuid, resultado.mimeType, resultado.size,
                            uploadedById, uploadedByName);
                }
                uploaded = true;
            } catch (IOException ex) {
                System.err.println("[SupportDocumentUploadServlet] Error subiendo '" + finalName + "' a Office Platform: " + ex.getMessage());
                rejected = true;
            } finally {
                if (tempImage != null) {
                    tempImage.delete();
                }
                if (pdfParaSubir != null) {
                    pdfParaSubir.delete();
                }
            }
        }

        String msg = uploaded ? (rejected ? "support_upload_partial" : "support_upload_success")
                : (rejected ? "error_extension" : "error_upload");

        response.sendRedirect("FileManager.jsp"
                + "?cliente=" + cliente + "&anio=" + anio + "&orden=" + orden + "&lote=" + lote
                + "&msg=" + msg);
    }
}
