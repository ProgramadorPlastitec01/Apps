package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Indicator extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        int opt = Integer.parseInt(request.getParameter("opt"));
        int YearInt = 0, MonthInt = 0, Type = 0;
        String MonthName = "";
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE INDICATOR MOUNTH">
                    try {
                        YearInt = Integer.parseInt(request.getParameter("YearInt"));
                    } catch (NumberFormatException e) {
                        YearInt = LocalDate.now().getYear();
                    }
                    try {
                        MonthInt = Integer.parseInt(request.getParameter("MonthInt"));
                        MonthName = request.getParameter("MonthName");
                    } catch (NumberFormatException e) {
                        MonthInt = LocalDate.now().getMonthValue();
                        MonthName = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                    }
                    try {
                        Type = Integer.parseInt(request.getParameter("Type"));
                    } catch (NumberFormatException e) {
                        Type = 0;
                    }
                    request.setAttribute("YearInt", YearInt);
                    request.setAttribute("MonthInt", MonthInt);
                    request.setAttribute("MonthName", MonthName);
                    request.setAttribute("Type", Type);
                    request.getRequestDispatcher("Indicator.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Indicator.jsp").forward(request, response);
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
