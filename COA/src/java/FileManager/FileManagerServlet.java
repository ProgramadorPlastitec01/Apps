package FileManager;

import Controller.CertificateFileJpaController;
import Method.OfficePlatformService;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/FileManagerServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50,     // 50MB
        maxRequestSize = 1024 * 1024 * 100  // 100MB
)
public class FileManagerServlet extends HttpServlet {

    // Únicamente los tipos que el generador del Batch Record unificado sabe
    // fusionar de forma nativa (PDF directo, imagen dibujada en una página).
    // Los formatos de Office (doc/docx/xls/xlsx/ppt/pptx) requerían convertir
    // a PDF con LibreOffice headless, pero soffice.bin crashea de forma
    // consistente al ser lanzado como hijo de Tomcat en este servidor
    // (interferencia del EDR corporativo con procesos hijos de un servicio
    // de red) y no se puede tocar esa política, así que se excluyen del todo
    // en vez de dejar al usuario subir algo que el unificado nunca va a
    // poder incluir. Debe reflejar el "accept" del input de FileManager.jsp
    // y lo que soporta renderPhysicalFile/renderRemoteBinaryFile en
    // BatchRecordPdfGenerateServlet.
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            ".pdf", ".png", ".jpg", ".jpeg", ".gif"
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cliente = request.getParameter("cliente");
        String anio    = request.getParameter("anio");
        String orden   = request.getParameter("orden");
        String lote    = request.getParameter("lote");

        HttpSession session = request.getSession();
        String uploadedById = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;
        String uploadedByName = session.getAttribute("Usuario") != null ? session.getAttribute("Usuario").toString() : null;

        boolean uploaded = false;
        boolean rejected = false;
        Long folderId = null;

        CertificateFileJpaController certificateFiles = new CertificateFileJpaController();

        for (Part part : request.getParts()) {

            if ("files".equals(part.getName()) && part.getSize() > 0) {

                String fileName = Paths.get(part.getSubmittedFileName())
                                       .getFileName().toString();

                String nameLower = fileName.toLowerCase();
                int dot = nameLower.lastIndexOf('.');
                String ext = dot >= 0 ? nameLower.substring(dot) : "";
                if (!ALLOWED_EXTENSIONS.contains(ext)) {
                    rejected = true;
                    continue;
                }

                try {
                    if (folderId == null) {
                        folderId = OfficePlatformService.resolveOrCreateFolderPath("COA", cliente, anio, orden, lote);
                    }

                    OfficePlatformService.OfficeUploadResult resultado = OfficePlatformService.subirArchivo(
                            folderId, fileName, null, part.getInputStream(), part.getContentType(),
                            uploadedById, uploadedByName);

                    certificateFiles.registerFile(cliente, anio, orden, lote, "", fileName,
                            resultado.fileId, resultado.uuid, resultado.mimeType,
                            resultado.size >= 0 ? resultado.size : part.getSize(),
                            uploadedById, uploadedByName);

                    uploaded = true;
                } catch (IOException ex) {
                    System.err.println("[FileManagerServlet] Error subiendo '" + fileName + "' a Office Platform: " + ex.getMessage());
                    rejected = true;
                }
            }
        }

        String msg = uploaded ? (rejected ? "upload_partial" : "upload_success")
                : (rejected ? "error_extension" : "error_upload");

        String redirect = "FileManager.jsp"
                + "?cliente=" + cliente
                + "&anio=" + anio
                + "&orden=" + orden
                + "&lote=" + lote
                + "&msg=" + msg;

        response.sendRedirect(redirect);
    }
}
