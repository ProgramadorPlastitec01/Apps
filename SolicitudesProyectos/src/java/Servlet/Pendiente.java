package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pendiente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean resultado = false;
            int id_solicitud = 0;
            int id_ficha = 0;
            int var = 0;
            switch (opc) {
                case 1:
                    try {
                        id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    } catch (NumberFormatException e) {
                        id_solicitud = 0;
                    }
                    request.setAttribute("id_solicitud", id_solicitud);
                    request.setAttribute("id_ficha", id_ficha);
                    request.setAttribute("Pendiente", "Herramental");
                    request.getRequestDispatcher("Pendiente.jsp").forward(request, response);
                    break;

                case 2:
                    try {
                        id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    } catch (NumberFormatException e) {
                        id_solicitud = 0;
                    }
                    request.setAttribute("id_solicitud", id_solicitud);
                    request.setAttribute("id_ficha", id_ficha);
                    request.setAttribute("Pendiente", "Ficha_tecnica");
                    request.getRequestDispatcher("Pendiente.jsp").forward(request, response);
                    break;
                case 3:
                    try {
                        var = Integer.parseInt(request.getParameter("var"));
                    } catch (NumberFormatException e) {
                        var = 0;
                    }
                    try {
                        id_solicitud = Integer.parseInt(request.getParameter("idS"));
                    } catch (NumberFormatException e) {
                        id_solicitud = 0;
                    }
                    request.setAttribute("id_solicitud", id_solicitud);
                    request.setAttribute("var", var);
                    request.setAttribute("Pendiente", "Visual_solicitud");
                    request.getRequestDispatcher("Visual_Solicitud.jsp").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("Pendiente.jsp").forward(request, response);
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
