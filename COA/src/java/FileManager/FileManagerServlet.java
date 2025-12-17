package FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cliente = request.getParameter("cliente");
        String anio    = request.getParameter("anio");
        String orden   = request.getParameter("orden");
        String lote    = request.getParameter("lote");

        String basePath = getServletContext().getRealPath("/Certificates");

        String uploadPath = basePath
                + File.separator + cliente
                + File.separator + anio
                + File.separator + orden
                + File.separator + lote;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        boolean uploaded = false;

        for (Part part : request.getParts()) {

            if ("files".equals(part.getName()) && part.getSize() > 0) {

                String fileName = Paths.get(part.getSubmittedFileName())
                                       .getFileName().toString();

                part.write(uploadPath + File.separator + fileName);
                uploaded = true;
            }
        }

        String redirect = "FileManager.jsp"
                + "?cliente=" + cliente
                + "&anio=" + anio
                + "&orden=" + orden
                + "&lote=" + lote
                + "&msg=" + (uploaded ? "upload_success" : "error_upload");

        response.sendRedirect(redirect);
    }
}
