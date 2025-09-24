package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.OrdenProduccionJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Cleanup extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        String UserName = sesion.getAttribute("Nombres").toString();
        String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
        String UserRol = sesion.getAttribute("idRol").toString();
        OrdenProduccionJpaController OrderProdJpa = new OrdenProduccionJpaController();

        int opc = Integer.parseInt(request.getParameter("opc"));
        int idOrder = 0, est = 0, dataSheet = 0, temp = 0, temps = 0, action = 0;
        String txtLote = "", txtLoteP = "", txtLoteC = "", txtFecha = "", consc = "";
        List lst_order = null;

        boolean result = false;
        try {
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN CLEANUP">
                    try {
                        action = Integer.parseInt(request.getParameter("act"));
                    } catch (Exception e) {
                        action = 0;
                    }
                    try {
                        temp = Integer.parseInt(request.getParameter("temp1"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        idOrder = Integer.parseInt(request.getParameter("idOrder"));
                    } catch (Exception e) {
                        idOrder = 0;
                    }
                    if (temp == 2) {
                        try {
                            txtFecha = request.getParameter("txtFecha");
                        } catch (Exception e) {
                            txtFecha = "";
                        }
                        try {
                            txtLote = request.getParameter("txtLote");
//                            request.setAttribute("registerGeneration", true);
                        } catch (Exception e) {
                            txtLote = "";
                        }
                        request.setAttribute("txtLote", txtLote);
                    }
                    request.setAttribute("idOrder", idOrder);
                    request.setAttribute("action", action);
                    request.getRequestDispatcher("Cleanup.jsp").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Cleanup.jsp").forward(request, response);
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
