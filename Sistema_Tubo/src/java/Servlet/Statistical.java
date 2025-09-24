package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Statistical extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int opc = Integer.parseInt(request.getParameter("opc"));
        int id_order = 0, temp = 0, start_roll = 0, end_roll = 0;
        String batch = "";
        try {
            switch (opc) {
                case 1:
                    try {
                        id_order = Integer.parseInt(request.getParameter("id_order"));
                    } catch (NumberFormatException e) {
                        id_order = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (NumberFormatException e) {
                        temp = 0;
                    }
                    if (temp > 1) {
                        try {
                            batch = request.getParameter("batch");
                        } catch (Exception e) {
                            batch = "";
                        }
                        try {
                            start_roll = Integer.parseInt(request.getParameter("start_roll"));
                        } catch (NumberFormatException e) {
                            start_roll = 0;
                        }
                        try {
                            end_roll = Integer.parseInt(request.getParameter("end_roll"));
                        } catch (NumberFormatException e) {
                            end_roll = 0;
                        }
                    }
                    request.setAttribute("id_order", id_order);
                    request.setAttribute("batch", batch);
                    request.setAttribute("start_roll", start_roll);
                    request.setAttribute("end_roll", end_roll);
                    request.getRequestDispatcher("Statistical.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Statistical.jsp").forward(request, response);
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
