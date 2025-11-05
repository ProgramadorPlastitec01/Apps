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

        if (lote != null && fileName != null) {
            String appPath = request.getServletContext().getRealPath("");
            String filePath = appPath + File.separator + "uploads" + File.separator + lote + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }

        response.sendRedirect("FileManager.jsp?lote=" + lote);
    }
}