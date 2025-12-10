package Servlets;

import Controladores.PendienteJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Solucionp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            int id_pendiente = 0;
            int est = 0;
            boolean proceso = true;
            PendienteJpaController jpacpde = new PendienteJpaController();
            switch (opc) {
                case 1:
                    id_pendiente = Integer.parseInt(request.getParameter("idpnd").toString());
                    request.setAttribute("Id_pendiente", id_pendiente);
                    request.getRequestDispatcher("SolucionP.jsp").forward(request, response);
                    break;
                case 2:
                    String txt_solucion = request.getParameter("Txt_descripcion").toString();
                    id_pendiente = Integer.parseInt(request.getParameter("idpnd").toString());
                    request.setAttribute("Id_pendiente", id_pendiente);
                    proceso = jpacpde.Registrar_solucionp(id_pendiente, txt_solucion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registrar_solucion");
                    } else {
                        request.setAttribute("Alerta", "Error_Registrar_solucion");
                    }
                    request.getRequestDispatcher("SolucionP.jsp").forward(request, response);
                    break;

                case 3:
                    est = Integer.parseInt(request.getParameter("est").toString());
                    id_pendiente = Integer.parseInt(request.getParameter("idpnd").toString());
                    request.setAttribute("Id_pendiente", id_pendiente);
                    proceso = jpacpde.Estado_delpendiente(id_pendiente, est);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registrar_solucion");
                    } else {
                        request.setAttribute("Alerta", "Error_Registrar_solucion");
                    }
                    request.getRequestDispatcher("SolucionP.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
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
