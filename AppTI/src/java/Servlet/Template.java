package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.TemplateControllerJpa;

public class Template extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        
        try {

            int opt = Integer.parseInt(request.getParameter("opt"));
            int idUser = 0;
            String template = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUSer"));
                    } catch (Exception e) {
                        idUser = 1;
                    }
                    request.setAttribute("IdUser", idUser);
                    request.getRequestDispatcher("Template.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUser"));
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    template = request.getParameter("txtTemplate").toString();
                    result = TemplateJpa.UpdateTemplateUser(idUser, template);
                    request.setAttribute("UpdateTemplate", result);
                    request.getRequestDispatcher("Template?opt=1").forward(request, response);
                    break;
                case 3:
                    try {
                        idUser = Integer.parseInt(request.getParameter("idUser"));
                    } catch (Exception e) {
                        idUser = 0;
                    }
                    template = request.getParameter("txtTemplate");
                    result = TemplateJpa.RegisterTemplateUser(idUser, template);
                    request.setAttribute("UpdateTemplate", result);
                    request.getRequestDispatcher("Template?opt=1").forward(request, response);
                    
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Template.jsp").forward(request, response);
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
