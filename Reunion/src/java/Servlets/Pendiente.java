package Servlets;

import Controladores.PendienteJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pendiente extends HttpServlet {

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
                    request.setAttribute("Solucionar_pendiente", 1);
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 2:
                    String txt_solucion = request.getParameter("Txt_descripcion").toString();
                    id_pendiente = Integer.parseInt(request.getParameter("idpnd").toString());
                    request.setAttribute("Modulo_pendiente", "Inicio");
                    request.setAttribute("Id_pendiente", id_pendiente);
                    request.setAttribute("Solucionar_pendiente", 1);
                    proceso = jpacpde.Registrar_solucionp(id_pendiente, txt_solucion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registrar_solucion");
                    } else {
                        request.setAttribute("Alerta", "Error_Registrar_solucion");
                    }
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 3:
                    est = Integer.parseInt(request.getParameter("est").toString());
                    id_pendiente = Integer.parseInt(request.getParameter("idpnd").toString());
                    request.setAttribute("Modulo_pendiente", "Inicio");
                    request.setAttribute("Id_pendiente", id_pendiente);
                    request.setAttribute("Solucionar_pendiente", 1);
                    proceso = jpacpde.Estado_delpendiente(id_pendiente, est);
                    if (proceso) {
                        request.setAttribute("Alerta", "Registrar_solucion");
                    } else {
                        request.setAttribute("Alerta", "Error_Registrar_solucion");
                    }
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 4:
                    request.setAttribute("Modulo_pendiente", "Solucionados");
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
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
