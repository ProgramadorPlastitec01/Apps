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

        String lote = request.getParameter("lote");
        String fileName = request.getParameter("file");
        String msg = "error_delete"; // valor por defecto

        if (lote != null && fileName != null) {
            String appPath = request.getServletContext().getRealPath("");
            String filePath = appPath + File.separator + "uploads" + File.separator + lote + File.separator + fileName;
            File file = new File(filePath);

            if (file.exists()) {
                if (file.delete()) {
                    msg = "delete_success"; // borrado correcto
                } else {
                    msg = "error_delete"; // no se pudo eliminar
                }
            } else {
                msg = "file_not_found"; // archivo no existe
            }
        } else {
            msg = "invalid_params"; // parámetros nulos
        }

        // Redirigir de nuevo al lote con el mensaje para toastr
        response.sendRedirect("FileManager.jsp?lote=" + lote + "&msg=" + msg);
    }
}
