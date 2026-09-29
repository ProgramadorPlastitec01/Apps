package Servlet;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import Controller.UserControllerJpa;
import Method.SignatureStorage;

@WebServlet("/UpdateSignatureUser")
@MultipartConfig
public class UpdateSignatureUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserControllerJpa UserJpa = new UserControllerJpa();
        HttpSession session = request.getSession();

        try {
            // El usuario siempre es el de la sesión: el idUser del formulario
            // permitía cambiar la firma de otro usuario.
            int idUser = Integer.parseInt(session.getAttribute("idUsuario").toString());

            Part filePart = request.getPart("File");
            String fileName = getFileName(filePart);
            String mimeType = filePart != null ? filePart.getContentType() : null;

            if (fileName == null || fileName.isEmpty() || mimeType == null || !mimeType.startsWith("image/")) {
                response.sendRedirect("Profile?opt=1&msg=ErrorFirma");
                return;
            }

            String userId = session.getAttribute("Documento") != null ? session.getAttribute("Documento").toString() : null;
            String userName = session.getAttribute("Nombres") != null ? session.getAttribute("Nombres").toString() : null;

            // La imagen se guarda en el Gestor de Archivos (Office Platform),
            // ya no en Interface/Uploads/Signature/.
            String nuevaFirma;
            try (InputStream contenido = filePart.getInputStream()) {
                nuevaFirma = SignatureStorage.upload(idUser, fileName, contenido, mimeType, userId, userName);
            }

            if (UserJpa.UpdateSignature(idUser, nuevaFirma)) {
                // Generate / SupportDocumentSign leen la firma de la sesión:
                // sin esto seguían usando la de antes (o ninguna) hasta volver
                // a iniciar sesión.
                session.setAttribute("Firma", nuevaFirma);
                response.sendRedirect("Profile?opt=1&msg=FirmaActualizada");
            } else {
                response.sendRedirect("Profile?opt=1&msg=ErrorFirma");
            }

        } catch (Exception e) {

            e.printStackTrace();
            response.sendRedirect("Profile?opt=1&msg=ErrorFirma");

        }
    }

    // Método compatible con Tomcat 7
    private String getFileName(Part part) {

        if (part == null) {
            return null;
        }

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
