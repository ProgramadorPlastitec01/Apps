package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SystemStatus extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession(false);
            String nombreRol = "";
            try {
                nombreRol = session.getAttribute("NombreRol").toString();
            } catch (Exception e) {
                nombreRol = "";
            }
            // Esta sección queda condicionada exclusivamente al rol Administrador
            // (no al sistema de permisos granulares por opción), por lo que se
            // revalida en el servidor y no solo ocultando el enlace en el menú.
            if (!"Administrador".equalsIgnoreCase(nombreRol.trim())) {
                request.setAttribute("errorMessage", "No tienes permisos para acceder a esta sección.");
                request.getRequestDispatcher("404.jsp").forward(request, response);
                return;
            }

            int opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    request.getRequestDispatcher("SystemStatus.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("404.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
