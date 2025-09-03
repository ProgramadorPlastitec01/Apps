package Servlet;

import Controller.UserControllerJpa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Device extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion = request.getSession();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int idTypeDv = 0, steDv = 0, act = 0;

        try {
            switch (opt) {
                case 1:
                    try {
                        act = Integer.parseInt(request.getParameter("act"));
                    } catch (Exception e) {
                        act = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        steDv = Integer.parseInt(request.getParameter("steDv"));
                    } catch (Exception e) {
                        steDv = 0;
                    }
                    request.setAttribute("act", act);
                    request.setAttribute("idTypeDv", idTypeDv);
                    request.setAttribute("steDv", steDv);
                    request.getRequestDispatcher("Device.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
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
