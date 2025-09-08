package Servlet;

import Controller.UserControllerJpa;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import Controller.DeviceJpaController;

public class Device extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        
        DeviceJpaController DeviceJpa = new DeviceJpaController();
        
        HttpSession sesion = request.getSession();
        int opt = Integer.parseInt(request.getParameter("opt"));
        int idTypeDv = 0, steDv = 0, act = 0, itm = 0, consect = 0, idArea = 0;
        String chargue = "", respon = "", nameDv = "", serial = "", location = "";
        boolean result = false;
        try {
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                    try {
                        act = Integer.parseInt(request.getParameter("act"));
                    } catch (Exception e) {
                        act = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        steDv = Integer.parseInt(request.getParameter("steDv"));
                    } catch (Exception e) {
                        steDv = 0;
                    }
                    request.setAttribute("act", act);
                    request.setAttribute("idTypeDv", idTypeDv);
                    request.setAttribute("steDv", steDv);
                    request.getRequestDispatcher("Device.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER">
                    try {
                        act = Integer.parseInt(request.getParameter("act"));
                    } catch (Exception e) {
                        act = 0;
                    }
                    try {
                        idTypeDv = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idTypeDv = 0;
                    }
                    try {
                        consect = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        consect = 0;
                    }
                    
                    try {
                        chargue = request.getParameter("idTypeDv");
                    } catch (Exception e) {
                        chargue = "";
                    }
                    try {
                        respon = request.getParameter("idTypeDv");
                    } catch (Exception e) {
                        respon = "";
                    }
                    try {
                        nameDv = request.getParameter("idTypeDv");
                    } catch (Exception e) {
                        nameDv = "";
                    }
                    try {
                        idArea = Integer.parseInt(request.getParameter("idTypeDv"));
                    } catch (Exception e) {
                        idArea = 0;
                    }
                    try {
                        serial = request.getParameter("idTypeDv");
                    } catch (Exception e) {
                        serial = "";
                    }
                    try {
                        location = request.getParameter("idTypeDv");
                    } catch (Exception e) {
                        location = "";
                    }
                    
                    
                    

                    request.getRequestDispatcher("Device?opt=1&act=" + act + "&idTypeDv=" + idTypeDv + "").forward(request, response);
//</editor-fold>
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
