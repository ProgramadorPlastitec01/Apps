package FileManager;

import java.io.File;
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
        String archivo = request.getParameter("archivo");

        String msg = "error_delete";

        if (cliente != null && anio != null && orden != null && lote != null && archivo != null) {

            String basePath = request.getServletContext().getRealPath("/Certificates");

            File file = new File(
                basePath + File.separator
                + cliente + File.separator
                + anio + File.separator
                + orden + File.separator
                + lote + File.separator
                + archivo
            );

            if (file.exists()) {
                if (file.delete()) {
                    msg = "delete_success";
                } else {
                    msg = "error_delete";
                }
            } else {
                msg = "file_not_found";
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
