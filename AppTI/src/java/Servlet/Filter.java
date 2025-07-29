package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Filter extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        int opt = Integer.parseInt(request.getParameter("opt"));
        String Date = "", Module = "";
        String[] Data = {}, PersonActive = {};
        String[] PersonInactive = {};
        try {
            switch (opt) {
                case 1:
                    try {
                        Date = request.getParameter("DateRange");
                    } catch (Exception e) {
                        Date = "";
                    }
                    try {
                        Data = request.getParameterValues("Data");
                    } catch (Exception e) {
                    }
                    try {
                        PersonActive = request.getParameterValues("PersonActive");
                    } catch (Exception e) {
                    }
                    try {
                        PersonInactive = request.getParameterValues("PersonInactive");
                    } catch (Exception e) {
                    }
                    try {
                        Module = request.getParameter("Module");
                    } catch (Exception e) {
                        Module = "";
                    }
                    request.setAttribute("Date", Date);
                    request.setAttribute("Data", Data);
                    request.setAttribute("PersonActive", PersonActive);
                    request.setAttribute("PersonInactive", PersonInactive);
                    request.setAttribute("Module", Module);
                    request.getRequestDispatcher("Filter.jsp").forward(request, response);
                    break;
            }
        } catch (IOException | ServletException ex) {
            request.getRequestDispatcher("Filter.jsp").forward(request, response);
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
