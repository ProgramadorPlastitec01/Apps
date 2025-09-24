package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.IdeaJpaController;

public class Idea extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession sesion = request.getSession();
        int opt = Integer.parseInt(request.getParameter("opt"));
        String UserRol = sesion.getAttribute("idRol").toString();
        IdeaJpaController IdeaJpa = new IdeaJpaController();
        int idIdea = 0, State = 0;
        String Description = "";
        boolean Result = false;
        try {
            switch (opt) {
                case 1:
                    try {
                        idIdea = Integer.parseInt(request.getParameter("idIdea"));
                    } catch (Exception e) {
                        idIdea = 0;
                    }
                    request.setAttribute("idIdea", idIdea);
                    request.setAttribute("idRol", UserRol);
                    request.getRequestDispatcher("Idea.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        idIdea = Integer.parseInt(request.getParameter("idIdea"));
                    } catch (Exception e) {
                        idIdea = 0;
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("state"));
                    } catch (Exception e) {
                        State = 0;
                    }
                    Description = request.getParameter("Txt_description");
                    Result = IdeaJpa.CloseIdea(idIdea, State, Description);
                    if (Result) {
                        request.setAttribute("CloseTempIdea", Result);
                    }
                    request.getRequestDispatcher("Idea?opt=1&idIdea=0").forward(request, response);
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
