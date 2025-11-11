package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.RoleControllerJpa;

public class Role extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        request.setCharacterEncoding("UTF-8");
        RoleControllerJpa RolJpa = new RoleControllerJpa();
        int opt = 0, IdRole = 0, IdPermissionRol = 0, State = 0;
        String Name = "", NameRegister = "Administrador", Permission = "";
        boolean Result = false;
        try {
            opt = Integer.parseInt(request.getParameter("opt"));
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE ROLE">
                    try {
                        IdRole = Integer.parseInt(request.getParameter("IdRole"));
                    } catch (Exception e) {
                        IdRole = 0;
                    }
                    try {
                        IdPermissionRol = Integer.parseInt(request.getParameter("IdPermissionRol"));
                    } catch (Exception e) {
                        IdPermissionRol = 0;
                    }
                    request.setAttribute("IdRole", IdRole);
                    request.setAttribute("IdPermissionRol", IdPermissionRol);
                    request.getRequestDispatcher("Role.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER AND UPDATE ROLE">
                    try {
                        IdRole = Integer.parseInt(request.getParameter("IdRole"));
                    } catch (Exception e) {
                        IdRole = 0;
                    }
                    Name = request.getParameter("Txt_Name");
                    if (IdRole > 0) {
                        Result = RolJpa.UpdateRole(IdRole, Name);
                        request.setAttribute("RoleUpdate", Result);
                    } else {
                        Result = RolJpa.RegisterRole(Name, NameRegister);
                        request.setAttribute("RoleRegister", Result);
                    }
                    request.getRequestDispatcher("Role?opt=1&IdRole=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE STATE">
                    try {
                        IdRole = Integer.parseInt(request.getParameter("IdRole"));
                    } catch (Exception e) {
                        IdRole = 0;
                    }
                    State = Integer.parseInt(request.getParameter("State"));
                    if (State == 1) {
                        State = 0;
                    } else {
                        State = 1;
                    }
                    Result = RolJpa.UpdateRoleState(IdRole, State);
                    request.setAttribute("RoleUpdateState", Result);
                    request.getRequestDispatcher("Role?opt=1&IdRole=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="ASSIGN PERMISSION">
                    try {
                        IdRole = Integer.parseInt(request.getParameter("IdRole"));
                    } catch (Exception e) {
                        IdRole = 0;
                    }
                    Permission = request.getParameter("Cbx_permission");
                    Result = RolJpa.UpdateRolePermission(IdRole, Permission);
                    request.setAttribute("RoleUpdatePermission", Result);
                    request.getRequestDispatcher("Role?opt=1&IdRole=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Role.jsp").forward(request, response);
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
