package Servlet;

import Controller.CertificatesJpaController;
import Method.SignatureStorage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Sirve la imagen de firma desde el Gestor de Archivos (reemplaza el acceso
 * estático a Interface/Uploads/Signature/&lt;archivo&gt;).
 *
 * - ?cert=ID  → la firma con la que se firmó el certificado ID (solo estado
 *   3). No exige sesión porque CertifiedView.jsp es la vista pública del
 *   certificado.
 * - ?name=X   → la firma X (ej. la del perfil). Exige sesión.
 */
@WebServlet("/SignatureImage")
public class SignatureImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = null;
        String certParam = request.getParameter("cert");
        if (certParam != null) {
            try {
                List lst = new CertificatesJpaController().ConsultCertificatesIdHtml(Integer.parseInt(certParam));
                if (lst != null) {
                    Object[] fila = (Object[]) lst.get(0);
                    if (fila[4] != null && Integer.parseInt(fila[4].toString()) == 3 && fila[5] != null) {
                        fileName = fila[5].toString();
                    }
                }
            } catch (Exception e) {
                fileName = null;
            }
        } else {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("Usuario") == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            fileName = request.getParameter("name");
        }

        SignatureStorage.SignatureContent contenido;
        try {
            contenido = SignatureStorage.open(getServletContext(), fileName);
        } catch (IOException ex) {
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "No se pudo obtener la firma del gestor de archivos.");
            return;
        }
        if (contenido == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(contenido.contentType != null ? contenido.contentType : "image/png");
        if (contenido.contentLength >= 0) {
            response.setContentLengthLong(contenido.contentLength);
        }
        // Los nombres de firma son únicos e inmutables: se puede cachear.
        response.setHeader("Cache-Control", "private, max-age=86400");
        try (InputStream in = contenido.inputStream; OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int leidos;
            while ((leidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
            }
        }
    }
}
