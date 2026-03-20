package Method;

import Method.Mail;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/error-404")
public class Error404Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession(false);

            String usuario = "Anonimo";
            if (session != null && session.getAttribute("user") != null) {
                usuario = session.getAttribute("user").toString();
            }

            String url = request.getRequestURL().toString();
            String ip = request.getRemoteAddr();

            Mail mail = new Mail();
//            mail.SendError404Mail(usuario, url, ip, getServletContext());

        } catch (Exception e) {
            // ⚠ Nunca rompas el flujo por el correo
            e.printStackTrace();
        }

        request.getRequestDispatcher("/404.jsp").forward(request, response);
    }
}
