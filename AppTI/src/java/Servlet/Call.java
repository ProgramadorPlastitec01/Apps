package Servlet;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Call extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {

            HttpSession sesion = request.getSession();
            Calendar cal = Calendar.getInstance();
            int CurrYear = cal.get(Calendar.YEAR);
            String IdUser = sesion.getAttribute("idUsuario").toString();
            String Nombres = sesion.getAttribute("Nombres").toString();
            int opt = Integer.parseInt(request.getParameter("opt"));
            String Module= "";
            int Yar = 0, Mth = 0;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="R-TI-001">
                    try {
                        Yar = Integer.parseInt(request.getParameter("Yar"));
                    } catch (Exception e) {
                        Yar = CurrYear;
                    }
                    try {
                        Mth = Integer.parseInt(request.getParameter("Mth"));
                    } catch (Exception e) {
                        Mth = 0;
                    }
                    try {
                        Module = request.getParameter("Module");
                    } catch (Exception e) {
                        Module = "General";
                        
                    }
                    request.setAttribute("Yar", Yar);
                    request.setAttribute("Module", Module);
                    request.setAttribute("Mth", Mth);
                    request.getRequestDispatcher("Call.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException | ServletException ex) {
            request.getRequestDispatcher("Call.jsp").forward(request, response);
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
