package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.CodeJpaController;

public class Code extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            HttpSession sesion = request.getSession();
            CodeJpaController CodeJpa = new CodeJpaController();
            String IdRol = sesion.getAttribute("idRol").toString();
            String name_user = sesion.getAttribute("Nombres").toString();
            int opt = Integer.parseInt(request.getParameter("opt"));
            boolean result = false;
            int IdCode = 0, state = 0;
            String CustomerName = "", Code = "";
            switch (opt) {
                case 1:
                    try {
                        IdCode = Integer.parseInt(request.getParameter("IdCode"));
                    } catch (Exception e) {
                        IdCode = 0;
                    }
                    request.setAttribute("IdCode", IdCode);
                    request.setAttribute("idRol", IdRol);
                    request.getRequestDispatcher("Code.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        IdCode = Integer.parseInt(request.getParameter("IdCode"));
                    } catch (Exception e) {
                        IdCode = 0;
                    }
                    CustomerName = request.getParameter("CustomerName");
                    Code = request.getParameter("Code");
                    if (IdCode > 0) {
                        result = CodeJpa.UpdateCode(IdCode, CustomerName, Code, name_user);
                        request.setAttribute("UpdateCode", result);
                    } else {
                        result = CodeJpa.RegisterCode(CustomerName, Code, name_user);
                        request.setAttribute("RegisterCode", result);
                    }
                    request.getRequestDispatcher("Code?opt=1&IdCode=0").forward(request, response);
                    break;
                case 3:
                    try {
                        IdCode = Integer.parseInt(request.getParameter("IdCode"));
                    } catch (Exception e) {
                        IdCode = 0;
                    }
                    state = Integer.parseInt(request.getParameter("state"));
                    result = CodeJpa.UpdateCodeState(IdCode, state);
                    request.setAttribute("UpdateCodeState", result);
                    request.getRequestDispatcher("Code?opt=1&IdCode=0").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMessage", "Ha ocurrido un error procesando tu solicitud: " + ex.getMessage());
            request.getRequestDispatcher("404.jsp").forward(request, response);

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
