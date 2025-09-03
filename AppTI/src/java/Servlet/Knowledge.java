package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controller.KnowledgeJpaController;

public class Knowledge extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        int opt = Integer.parseInt(request.getParameter("opt"));
        String UserRol = sesion.getAttribute("idRol").toString();
        KnowledgeJpaController KnowlegdeJpa = new KnowledgeJpaController();
        String NameUser = sesion.getAttribute("Nombres").toString();
        String Category = "", Title = "", Attach = "", Description = "";
        int IdKnowledge = 0, State = 0;
        boolean Result = false;
        try {
            switch (opt) {
                case 1:
                    try {
                        IdKnowledge = Integer.parseInt(request.getParameter("IdKnowledge"));
                    } catch (Exception e) {
                        IdKnowledge = 0;
                    }
                    request.setAttribute("idRol", UserRol);
                    request.setAttribute("IdKnowledge", IdKnowledge);
                    request.getRequestDispatcher("Knowledge_table.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        IdKnowledge = Integer.parseInt(request.getParameter("IdKnowledge"));
                    } catch (Exception e) {
                        IdKnowledge = 0;
                    }
                    Category = request.getParameter("Category");
                    Title = request.getParameter("Title");
                    Attach = request.getParameter("Attach");
                    Description = request.getParameter("Description");
                    if (IdKnowledge > 0) {
                        Result = KnowlegdeJpa.UpdateKnowlegde(IdKnowledge, Category, Title, Attach, Description);
                        if (Result) {
                            request.setAttribute("UpdateKnowledge", Result);
                        }
                    } else {
                        Result = KnowlegdeJpa.RegisterKnowlegde(Category, Title, Attach, Description, NameUser);
                        if (Result) {
                            request.setAttribute("RegisterKnowledge", Result);
                        }
                    }
                    request.getRequestDispatcher("Knowledge?opt=1&IdKnowledge=0").forward(request, response);
                    break;
                case 3:
                    try {
                        IdKnowledge = Integer.parseInt(request.getParameter("IdKnowledge"));
                    } catch (Exception e) {
                        IdKnowledge = 0;
                    }
                    try {
                        State = Integer.parseInt(request.getParameter("State"));
                    } catch (Exception e) {
                        State = 0;
                    }
                    if (State == 1) {
                        State = 0;
                    } else {
                        State = 1;
                    }
                    Result = KnowlegdeJpa.ChangeStateKnowlegde(IdKnowledge, State);
                    if (Result) {
                        request.setAttribute("ChangeStateKnowlegde", Result);
                    }
                    request.getRequestDispatcher("Knowledge?opt=1&IdKnowledge=0").forward(request, response);
                    break;
                case 4:
                    try {
                        IdKnowledge = Integer.parseInt(request.getParameter("IdKnowledge"));
                    } catch (Exception e) {
                        IdKnowledge = 0;
                    }
                    Result = KnowlegdeJpa.UpdateView(IdKnowledge);
                    request.getRequestDispatcher("Knowledge?opt=1&IdKnowledge=0").forward(request, response);
                    break;
            }
        } catch (IOException | ServletException ex) {
            request.getRequestDispatcher("Knowledge_table.jsp").forward(request, response);
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
