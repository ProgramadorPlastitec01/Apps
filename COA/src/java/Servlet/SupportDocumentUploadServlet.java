package Servlet;

import Method.PdfImageUtil;
import java.io.File;
import java.io.IOException;
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
 * signed later from SupportDocumentSignServlet. Stored under the same
 * Certificates/{cliente}/{anio}/{orden}/{lote}/ folder FileManagerServlet
 * uses, in a SupportDocs/ subfolder, so it's picked up by
 * Method.BatchRecordManifest and included in the unified Batch Record PDF.
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

        String basePath = getServletContext().getRealPath("/Certificates");
        String supportDocsPath = basePath
                + File.separator + cliente
                + File.separator + anio
                + File.separator + orden
                + File.separator + lote
                + File.separator + "SupportDocs";

        File uploadDir = new File(supportDocsPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        boolean uploaded = false;
        boolean rejected = false;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

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

            if (ext.equals(".pdf")) {
                File destino = new File(uploadDir, safeBaseName + "_" + timestamp + ".pdf");
                part.write(destino.getAbsolutePath());
                uploaded = true;
            } else {
                File tempImage = new File(uploadDir, "tmp_" + timestamp + ext);
                part.write(tempImage.getAbsolutePath());
                File destino = new File(uploadDir, safeBaseName + "_" + timestamp + ".pdf");
                try {
                    PdfImageUtil.imageToPdf(tempImage, destino);
                    uploaded = true;
                } finally {
                    tempImage.delete();
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
