package Servlets;

import Controladores.PendienteJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pendientes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String[] usuario_rol = request.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = usuario_rol[0];
            String usuario = usuario_rol[1];
            int id_usuario = Integer.parseInt(request.getSession().getAttribute("Id_usuario").toString());
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int id_pendiente = 0;
            int est = 0;
            boolean proceso = true;
            PendienteJpaController jpacpde = new PendienteJpaController();
            switch (opc) {
                case 1:
                    id_pendiente = Integer.parseInt(request.getParameter("idpnd").toString());
                    request.setAttribute("Modulo_pendiente", "Inicio");
                    request.setAttribute("Id_pendiente", id_pendiente);
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
