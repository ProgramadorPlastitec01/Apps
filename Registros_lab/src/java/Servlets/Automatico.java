package Servlets;

import Controladores.RegistroJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Automatico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            RegistroJpaController jpacrgt = new RegistroJpaController();
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int turno = 0;
            switch (opc) {
                case 1:
                    turno = Integer.parseInt(request.getParameter("tno"));
                    if (turno == 1) {
                        jpacrgt.Cerrar_turnos_automatico("Turno 1");
                    } else if (turno == 2) {
                        jpacrgt.Cerrar_turnos_automatico("Turno 2");
                    } else if (turno == 3) {
                        jpacrgt.Cerrar_turnos_automatico("Turno 3");
                    }
                    response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
            }
        } catch (Exception ex) {
            response.sendRedirect("http://172.16.2.117:8080/Aplicativos_Plastitec/Automatic_servlets.jsp");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
