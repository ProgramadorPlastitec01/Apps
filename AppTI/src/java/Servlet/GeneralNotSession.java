package Servlet;

import Controller.KnowledgeJpaController;
import Controller.IdeaJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneralNotSession extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        KnowledgeJpaController KnowlegdeJpa = new KnowledgeJpaController();
        IdeaJpaController IdeaJpa = new IdeaJpaController();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int IdKnowledge = 0;
        String Name = "", Type = "", Detail = "";
        boolean Result = false;
        try {
            switch (opt) {
                case 1:
                    try {
                        IdKnowledge = Integer.parseInt(request.getParameter("IdKnowledge"));
                    } catch (Exception e) {
                        IdKnowledge = 0;
                    }
                    KnowlegdeJpa.UpdateView(IdKnowledge);
                    request.getRequestDispatcher("Knowledge.jsp").forward(request, response);
                    break;
                case 2:
                    Name = request.getParameter("Name");
                    Type = request.getParameter("Type");
                    Detail = request.getParameter("Detail");
                    Result = IdeaJpa.RegisterIdea(Name, Type, Detail);
                    request.setAttribute("RegisterIdea", Result);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } catch (IOException ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
