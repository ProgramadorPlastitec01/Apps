package Servlet;

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
import Controller.UserControllerJpa;

@WebServlet("/UpdateSignatureUser")
@MultipartConfig
public class UpdateSignatureUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener archivo subido
        UserControllerJpa UserJpa = new UserControllerJpa();
        Part filePart = request.getPart("File");

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String nuevaFirma = null;

        if (fileName != null && !fileName.isEmpty()) {

            // Ruta dentro del proyecto
            String uploadPath = getServletContext().getRealPath("") + "Interface/Uploads/Signature/";

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Guardar el archivo en la carpeta
            filePart.write(uploadPath + fileName);

            // Guardar el nombre de archivo en la BD
            nuevaFirma = fileName;
        }

        // Ejemplo: actualizar firma en BD
         int idUser = Integer.parseInt(request.getParameter("idUser"));
         UserJpa.UpdateSignature(idUser, nuevaFirma);

        response.sendRedirect("Profile?opt=1&msg=FirmaActualizada");
    }
}
