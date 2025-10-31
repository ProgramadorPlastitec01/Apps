package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Reporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_orden = 0, id_registro = 0,variable = 0, temp = 0, turno = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REPORTE">
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        variable = Integer.parseInt(request.getParameter("var"));
                    } catch (Exception e) {
                        variable = 0;
                    }
                    try {
                        turno = Integer.parseInt(request.getParameter("turno"));
                    } catch (Exception e) {
                        turno = 0;
                    }
                    request.setAttribute("id_orden", id_orden);
                    request.setAttribute("id_registro", id_registro);
                    request.setAttribute("variable", variable);
                    request.setAttribute("turno", turno);
                    request.getRequestDispatcher("Reporte.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Error_app", true);
            request.getRequestDispatcher("Reporte.jsp").forward(request, response);
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
