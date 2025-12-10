package Servlets;

import Controladores.SolicitudJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Libre extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            SolicitudJpaController jpacsol = new SolicitudJpaController();
            int id_solicitud = 0;
            List lst_solicitud = null;
            int tipo = 0;
            switch (opc) {
                case 1:
                    id_solicitud = Integer.parseInt(request.getParameter("Id_solicitud").toString());
                    lst_solicitud = jpacsol.Traer_Solicitud(id_solicitud);
                    Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                    if (Integer.parseInt(obj_solicitud[14].toString()) == 1) {
                        request.setAttribute("Alerta", "Solicitud_liberada");
                    } else {
                        tipo = Integer.parseInt(request.getParameter("Tipo").toString());
                        jpacsol.Confirmar_declinar_solicitud(id_solicitud, tipo);
                        if (tipo == 0) {
                            request.setAttribute("Alerta", "Declinacion_rechazada");
                        } else {
                            request.setAttribute("Alerta", "Declinacion_confirmada");
                        }
                    }
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
