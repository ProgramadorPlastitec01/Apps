package Servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import Controller.UserControllerJpa;

@WebServlet("/UpdateSignatureUser")
@MultipartConfig
public class UpdateSignatureUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserControllerJpa UserJpa = new UserControllerJpa();
        String nuevaFirma = null;

        try {

            Part filePart = request.getPart("File");
            String fileName = getFileName(filePart);

            if (fileName != null && !fileName.isEmpty()) {

                // Ruta real dentro del proyecto
                String uploadPath = getServletContext().getRealPath("/Interface/Uploads/Signature/");

                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Guardar archivo
                filePart.write(uploadPath + File.separator + fileName);

                nuevaFirma = fileName;
            }

            int idUser = Integer.parseInt(request.getParameter("idUser"));

            if (nuevaFirma != null) {
                UserJpa.UpdateSignature(idUser, nuevaFirma);
            }

            response.sendRedirect("Profile?opt=1&msg=FirmaActualizada");

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("Profile?opt=1&msg=ErrorFirma");

        }
    }

    // Método compatible con Tomcat 7
    private String getFileName(Part part) {

        String contentDisp = part.getHeader("content-disposition");

        if (contentDisp == null) {
            return null;
        }

        String[] tokens = contentDisp.split(";");

        for (String token : tokens) {

            if (token.trim().startsWith("filename")) {

                String fileName = token.substring(token.indexOf("=") + 2, token.length() - 1);

                // Elimina ruta completa de Windows
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);

                return fileName;
            }
        }

        return null;
    }
}