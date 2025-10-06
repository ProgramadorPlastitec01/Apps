package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Generate extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int Order = 0, IdFormat = 0;
            String Type = "", Product = "", Batch = "";
            switch (opt) {
                case 1:
                    try {
                        Type = request.getParameter("");
                    } catch (Exception e) {
                        Type = "";
                    }

                    request.setAttribute("Type", Type);
                    request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        Order = Integer.parseInt(request.getParameter("order"));
                    } catch (Exception e) {
                        Order = 0;
                    }
                    try {
                        Product = request.getParameter("product");
                    } catch (Exception e) {
                        Product = "";
                    }
                    try {
                        Batch = request.getParameter("batch");
                    } catch (Exception e) {
                        Batch = "";
                    }
                    try {
                        IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                    } catch (Exception e) {
                        IdFormat = 0;
                    }

                    request.setAttribute("order", Order);
                    request.setAttribute("product", Product);
                    request.setAttribute("batch", Batch);
                    request.setAttribute("IdFormat", IdFormat);
                    request.getRequestDispatcher("Visual.jsp").forward(request, response);
                    break;
            }

        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("GenerateReport.jsp").forward(request, response);
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
