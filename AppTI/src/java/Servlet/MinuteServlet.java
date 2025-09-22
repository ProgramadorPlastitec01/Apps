package Servlet;

import Mail.Mail_Minute;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class MinuteServlet extends HttpServlet {

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // ✅ Capturar asunto y destinatarios
    String asunto = request.getParameter("asunto");
    String destinatarios = request.getParameter("destinatario"); 
    // Puede venir como "correo1@dom.com;correo2@dom.com"

    Part filePart = request.getPart("pdf");
    if (filePart == null) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("No se recibió PDF");
        return;
    }

    // Guardar PDF temporalmente
    ServletContext context = getServletContext();
    String uploadPath = context.getRealPath("/Uploads/");
    new File(uploadPath).mkdirs();
    String pdfPath = uploadPath + File.separator + "Acta.pdf";

    try (InputStream input = filePart.getInputStream();
         FileOutputStream output = new FileOutputStream(pdfPath)) {
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    try {
        // ✅ Pasar asunto y destinatarios al envío
        Mail_Minute mail = new Mail_Minute();
        mail.SendMinute(pdfPath, asunto, destinatarios, context);

        response.getWriter().write("Acta enviada con exito a: " + destinatarios);
    } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("❌ Error enviando acta: " + e.getMessage());
    }
}
}
