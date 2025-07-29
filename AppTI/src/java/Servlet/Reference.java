package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Controller.ReferenceControllerJpa;

public class Reference extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession sesion = request.getSession();
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        
        ReferenceControllerJpa ReferenceJpa = new ReferenceControllerJpa();
        
        int opt = Integer.parseInt(request.getParameter("opt"));
        boolean result = false;
        String ref = "", refName = "", Supplier = "", Brand = "", Location = "";
        try {
            switch (opt) {
                case 1:
                    try {
                        ref = request.getParameter("txt_Ref");
                    } catch (Exception e) {
                        ref = "";
                    }
                    request.setAttribute("ref", ref);
                    request.getRequestDispatcher("Reference.jsp").forward(request, response);
                    break;
                case 2:
                    try {
                        ref = request.getParameter("txt_Ref");
                    } catch (Exception e) {
                        ref = "";
                    }
                    refName = request.getParameter("txt_RefName");
                    Supplier = request.getParameter("txt_supplier");
                    Brand = request.getParameter("txt_brand");
                    Location = request.getParameter("txt_location");
                    
                    result = ReferenceJpa.RegisterReference(ref, refName, Supplier, Brand, Location, idUser);
                    request.setAttribute("RegisterReference", result);
                    request.getRequestDispatcher("Reference?opt=1&txt_Ref=").forward(request, response);
                    break;
            }
        } catch (Exception e) {
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
