package FileManager;

import Controller.CertificateFileJpaController;
import Controller.CertificateFileRow;
import Method.OfficePlatformService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteFileServlet")
public class DeleteFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cliente = request.getParameter("cliente");
        String anio    = request.getParameter("anio");
        String orden   = request.getParameter("orden");
        String lote    = request.getParameter("lote");
        String idParam = request.getParameter("id");

        String msg = "error_delete";

        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                CertificateFileJpaController certificateFiles = new CertificateFileJpaController();
                CertificateFileRow fila = certificateFiles.consultFileById(id);

                if (fila != null) {
                    // Se envía a la papelera del gestor (recuperable) en vez de un
                    // borrado físico permanente, que era el comportamiento anterior.
                    // userId/userName deben coincidir con los usados al subir el
                    // archivo: sin ellos la API responde "éxito" pero no borra nada.
                    OfficePlatformService.eliminarArchivo(fila.getOfficeFileId(), fila.getUploadedById(), fila.getUploadedByName());
                    msg = certificateFiles.deleteFile(id) ? "delete_success" : "error_delete";
                } else {
                    msg = "file_not_found";
                }
            } catch (NumberFormatException | IOException ex) {
                msg = "error_delete";
            }
        } else {
            msg = "invalid_params";
        }

        response.sendRedirect(
            "FileManager.jsp"
            + "?cliente=" + cliente
            + "&anio=" + anio
            + "&orden=" + orden
            + "&lote=" + lote
            + "&msg=" + msg
        );
    }
}
