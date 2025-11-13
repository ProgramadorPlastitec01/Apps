package FileManager;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/FileManagerServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50,     // 50MB
        maxRequestSize = 1024 * 1024 * 100  // 100MB
)
public class FileManagerServlet extends HttpServlet {
    
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String lote = request.getParameter("lote");
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + UPLOAD_DIR + File.separator + lote;

        // Verificar y crear carpeta si no existe
        File fileSaveDir = new File(savePath);
        boolean isNewFolder = false;
        if (!fileSaveDir.exists()) {
            isNewFolder = fileSaveDir.mkdirs(); // crea carpeta del lote
        }

        try {
            boolean hasFiles = false;

            for (Part part : request.getParts()) {
                String fileName = extractFileName(part);
                if (!fileName.isEmpty()) {
                    hasFiles = true;
                    part.write(savePath + File.separator + fileName);
                }
            }

            // Construir mensaje de éxito o advertencia según el caso
            String msg;
            if (isNewFolder && hasFiles) {
                msg = "folder_created";
            } else if (!isNewFolder && hasFiles) {
                msg = "upload_success";
            } else {
                msg = "error_upload";
            }

            // Redirigir al JSP con parámetro msg
            response.sendRedirect("FileManager.jsp?msg=" + msg);

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("FileManager.jsp?msg=error_upload");
        }
    }

    // Método auxiliar para obtener el nombre real del archivo
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
