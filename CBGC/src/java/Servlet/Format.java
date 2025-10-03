package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.FormatJpaController;

public class Format extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            int opt = Integer.parseInt(request.getParameter("opt"));
            FormatJpaController FormatJpa = new FormatJpaController();
            int IdFormat = 0, Version = 0, State = 0, Temp = 0;
            String Application = "", Record = "", dataFormat = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    try {
                        IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                    } catch (Exception e) {
                        IdFormat = 0;
                    }
                    try {
                        Temp = Integer.parseInt(request.getParameter("Temp"));
                    } catch (Exception e) {
                        Temp = 0;
                    }
                    request.setAttribute("IdFormat", IdFormat);
                    request.setAttribute("Temp", Temp);
                    request.getRequestDispatcher("Format.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                    } catch (Exception e) {
                        IdFormat = 0;
                    }
                    Application = request.getParameter("Application");
                    Record = request.getParameter("Record");
                    Version = Integer.parseInt(request.getParameter("Version"));
                    if (IdFormat > 0) {
                        result = FormatJpa.FormatRegister(Application, Record, Version, "Administrador");
                    } else {
                        result = FormatJpa.FormatUpdate(IdFormat, Application, Record, Version, "Administrador");
                    }
                    request.getRequestDispatcher("Format?opt=1&IdFormat=0").forward(request, response);
                    break;
                case 3:
                    try {
                        IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                    } catch (Exception e) {
                        IdFormat = 0;
                    }
                    State = Integer.parseInt(request.getParameter("State"));
                    result = FormatJpa.FormatUpdateState(IdFormat, State);
                    request.getRequestDispatcher("Format?opt=1&IdFormat=0").forward(request, response);
                    break;
                case 4:
                    try {
                        IdFormat = Integer.parseInt(request.getParameter("IdFormat"));
                    } catch (Exception e) {
                        IdFormat = 0;
                    }
                    dataFormat = request.getParameter("dataFormat");
                    result = FormatJpa.UpdateFormatData(IdFormat, dataFormat);
                    request.getRequestDispatcher("Format?opt=1&IdFormat=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("400.jsp").forward(request, response);
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
