package FileManager;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Method.OfficePlatformService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Sirve, por streaming, un archivo de Batch Record que ya no vive en
 * web/Certificates/ sino en Office Platform. Reemplaza el acceso estático
 * directo que Tomcat hacía antes a "Certificates/&lt;cliente&gt;/.../&lt;archivo&gt;".
 * No se escribe ningún archivo intermedio en disco.
 */
@WebServlet("/FileDownloadProxyServlet")
public class FileDownloadProxyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("Usuario") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String idParam = request.getParameter("id");
        String modo = request.getParameter("modo");

        long id;
        try {
            id = Long.parseLong(idParam);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CertificateFileJpaController certificateFiles = new CertificateFileJpaController();
        CertificateFileRow fila = certificateFiles.consultFileById(id);
        if (fila == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            OfficePlatformService.OfficeDownload descarga = OfficePlatformService.descargarArchivo(fila.getOfficeFileId());

            String contentType = descarga.contentType != null ? descarga.contentType
                    : (fila.getMimeType() != null ? fila.getMimeType() : "application/octet-stream");
            response.setContentType(contentType);
            if (descarga.contentLength >= 0) {
                response.setContentLengthLong(descarga.contentLength);
            }

            String disposition = "adjunto".equals(modo) ? "attachment" : "inline";
            response.setHeader("Content-Disposition", disposition + "; filename=\"" + fila.getName() + "\"");

            try (InputStream in = descarga.inputStream; OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int leidos;
                while ((leidos = in.read(buffer)) != -1) {
                    out.write(buffer, 0, leidos);
                }
            }
        } catch (IOException ex) {
            response.sendError(HttpServletResponse.SC_BAD_GATEWAY, "No se pudo obtener el archivo del gestor de archivos.");
        }
    }
}
