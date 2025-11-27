package FileManager;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RenameFolderServlet")
public class RenameFolderServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String oldName = request.getParameter("oldName");
        String newName = request.getParameter("newName");

        String appPath = request.getServletContext().getRealPath("");
        File oldFolder = new File(appPath + File.separator + UPLOAD_DIR + File.separator + oldName);
        File newFolder = new File(appPath + File.separator + UPLOAD_DIR + File.separator + newName);

        String msg = "error_rename";

        if (oldFolder.exists() && !newFolder.exists()) {
            boolean success = oldFolder.renameTo(newFolder);
            if (success) {
                msg = "rename_success";
            }
        } else if (newFolder.exists()) {
            msg = "rename_exists";
        }

        response.sendRedirect("FileManager.jsp?msg=" + msg);
    }
}
