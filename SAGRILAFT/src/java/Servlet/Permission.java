package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.PermissionControllerJpa;

public class Permission extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        PermissionControllerJpa PermissionJpa = new PermissionControllerJpa();
        int opt = 0, IdPermission = 0, State = 0;
        String Module, Option = "", Description = "", User = "";
        boolean Result = false;
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE PERMISSION">
                    try {
                        IdPermission = Integer.parseInt(request.getParameter("IdPermission"));
                    } catch (Exception e) {
                        IdPermission = 0;
                    }
                    request.setAttribute("IdPermission", IdPermission);
                    request.getRequestDispatcher("Permission.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRER AND UDPATE PERMISSION">
                    try {
                        IdPermission = Integer.parseInt(request.getParameter("IdPermission"));
                    } catch (Exception e) {
                        IdPermission = 0;
                    }
                    Module = request.getParameter("Txt_module");
                    Option = request.getParameter("Txt_option");
                    Description = request.getParameter("Txt_description");
                    if (IdPermission > 0) {
                        Result = PermissionJpa.PermissionUpadte(IdPermission, Module, Option, Description);
                        request.setAttribute("PermissionUpdate", Result);
                    } else {
                        State = Integer.parseInt(request.getParameter("State"));
                        Result = PermissionJpa.PermissionRegister(Module, Option, Description, State, User);
                        request.setAttribute("PermissionRegister", Result);
                    }
                    request.getRequestDispatcher("Permission?opt=1&IdPermission=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE STATE">
                    try {
                        IdPermission = Integer.parseInt(request.getParameter("IdPermission"));
                    } catch (Exception e) {
                        IdPermission = 0;
                    }
                    State = Integer.parseInt(request.getParameter("State"));
                    Result = PermissionJpa.PermissionUpadteState(IdPermission, State);
                    request.setAttribute("PermissionUpdateState", Result);
                    request.getRequestDispatcher("Permission?opt=1&IdPermission=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Permission.jsp").forward(request, response);
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
