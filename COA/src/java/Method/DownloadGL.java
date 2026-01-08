package Method;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DownloadGL extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String global_rute = "\\\\172.16.5.99\\c$\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Generacion_Lotes\\UserFiles\\File\\";

        String fileName = request.getParameter("File_name");

        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo inválido");
            return;
        }

        fileName = fileName.trim(); // 🔥 MUY IMPORTANTE

        File file = new File(global_rute, fileName);

        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado");
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.reset();
        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + file.getName() + "\"");

        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}