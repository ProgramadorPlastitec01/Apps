package Servlet;

import Controladores.OrdenProduccionJpaController;
import Controladores.RegistroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controladores.RolloPPJpaController;
import java.util.List;

public class RollPP extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        
        String UserName = sesion.getAttribute("Nombres").toString();
        String UserRol = sesion.getAttribute("idRol").toString();
        String txPermisos = sesion.getAttribute("Permisos").toString();
        
        RolloPPJpaController RolloPPJpa = new RolloPPJpaController();
        RegistroJpaController RegisterJpa = new RegistroJpaController();
        OrdenProduccionJpaController OrderJpa = new OrdenProduccionJpaController();
        
        List lst_roll = null;
        int opc = Integer.parseInt(request.getParameter("opc"));
        boolean result = false;
        
        try {
            
            switch (opc){
                case 1: 
                    request.getRequestDispatcher("RollPP.jsp").forward(request, response);
                    break;
            }
            
        } catch (Exception ex) {
            request.getRequestDispatcher("RollPP.jsp").forward(request, response);
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
