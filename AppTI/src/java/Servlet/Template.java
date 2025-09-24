package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Controller.TemplateControllerJpa;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpSession;

public class Template extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();
        
        try {
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idTempl = 0;
            String template = "", titleTem = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        idTempl = Integer.parseInt(request.getParameter("idTempl"));
                    } catch (Exception e) {
                        idTempl = 0;
                    }
                    request.setAttribute("IdUser", idUser);
                    request.setAttribute("idTempl", idTempl);
                    request.getRequestDispatcher("Template.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="EDIT TEMPLATE">
                    try {
                        idTempl = Integer.parseInt(request.getParameter("idTempl"));
                    } catch (Exception e) {
                        idTempl = 0;
                    }
                    titleTem = request.getParameter("txtTitle");
                    template = request.getParameter("txtTemplate").toString();
                    
                    result = TemplateJpa.UpdateTemplateUser(idTempl, titleTem, template);
                    
                    request.setAttribute("UpdateTemplate", result);
                    request.getRequestDispatcher("Template?opt=1&idTempl=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER TEMPLATE">
                    titleTem = request.getParameter("txtTitle");
                    result = TemplateJpa.RegisterTemplateUser(idUser, titleTem);
                    request.setAttribute("RegisterTemplate", result);
                    request.getRequestDispatcher("Template?opt=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="CHANGE STATE">
                    try {
                        idTempl = Integer.parseInt(request.getParameter("idTempl"));
                    } catch (Exception e) {
                        idTempl = 0;
                    }                    
                    result = TemplateJpa.UpdateTemplateState(idTempl);
                    
                    request.setAttribute("UpdateTemplateState", result);
                    request.getRequestDispatcher("Template?opt=1&idTempl=0").forward(request, response);
//</editor-fold>
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
