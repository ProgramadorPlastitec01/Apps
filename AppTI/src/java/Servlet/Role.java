package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controller.RoleControllerJpa;
import javax.servlet.http.HttpSession;

public class Role extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        try {
            RoleControllerJpa RoleJpa = new RoleControllerJpa();
            HttpSession sesion = request.getSession();
            String UserRol = sesion.getAttribute("idRol").toString();
            int opt = Integer.parseInt(request.getParameter("opt"));
            int idRole = 0, state = 0, IdRolePermission = 0, IdPermission = 0;
            String name = "", permissions = "", Access = "", module = "", option = "", description = "";
            boolean result = false;
            switch (opt) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="ROLE MÓDULE">
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (NumberFormatException e) {
                        idRole = 0;
                    }
                    try {
                        IdPermission = Integer.parseInt(request.getParameter("IdPermission"));
                    } catch (NumberFormatException e) {
                        IdPermission = 0;
                    }
                    try {
                        IdRolePermission = Integer.parseInt(request.getParameter("IdRolePermission"));
                    } catch (NumberFormatException e) {
                        IdRolePermission = 0;
                    }
                    try {
                        Access = request.getParameter("Access");
                    } catch (Exception e) {
                        Access = "Role";
                    }
                    request.setAttribute("idRole", idRole);
                    request.setAttribute("idRol", UserRol);
                    request.setAttribute("IdRolePermission", IdRolePermission);
                    request.setAttribute("IdPermission", IdPermission);
                    request.setAttribute("Access", Access);
                    request.getRequestDispatcher("Role.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="ROLE REGISTRER AND UPDATE">
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (NumberFormatException e) {
                        idRole = 0;
                    }
                    name = request.getParameter("Txt_name");
                    if (idRole > 0) {
                        try {
                            state = Integer.parseInt(request.getParameter("state"));
                        } catch (NumberFormatException e) {
                            state = 0;
                        }
                        result = RoleJpa.RoleUpdate(idRole, name, state);
                        request.setAttribute("RoleUpdate", result);
                    } else {
                        result = RoleJpa.RoleRegister(name, "ADMINISTRADOR");
                        request.setAttribute("RoleRegister", result);
                    }
                    request.getRequestDispatcher("Role?opt=1&idRole=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="ROLE CHANGE STATE">
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (NumberFormatException e) {
                        idRole = 0;
                    }
                    try {
                        state = Integer.parseInt(request.getParameter("state"));
                    } catch (NumberFormatException e) {
                        state = 0;
                    }
                    if (state == 1) {
                        state = 0;
                    } else {
                        state = 1;
                    }
                    result = RoleJpa.RoleUpdateStateChange(idRole, state);
                    request.setAttribute("RoleChangeState", result);
                    request.getRequestDispatcher("Role?opt=1&idRole=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE PERMISSION ROLE">
                    try {
                        idRole = Integer.parseInt(request.getParameter("idRole"));
                    } catch (NumberFormatException e) {
                        idRole = 0;
                    }
                    permissions = request.getParameter("Cbx_permission");
                    result = RoleJpa.RoleUpdatePermission(idRole, permissions);
                    request.setAttribute("RoleUpdatePermission", result);
                    request.getRequestDispatcher("Role?opt=1&idRole=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="PERMISSION REGISTER AND UPDATE">
                    try {
                        IdPermission = Integer.parseInt(request.getParameter("IdPermission"));
                    } catch (NumberFormatException e) {
                        IdPermission = 0;
                    }
                    module = request.getParameter("Txt_module");
                    option = request.getParameter("Txt_option");
                    description = request.getParameter("Txt_description");
                    if (IdPermission > 0) {
                        result = RoleJpa.PermissionUpdate(IdPermission, module, option, description);
                        request.setAttribute("RoleUpdate", result);
                    } else {
                        result = RoleJpa.PermissionRegister(module, option, description, "ADMINISTRADOR");
                        request.setAttribute("RegisterPermission", result);
                    }
                    request.getRequestDispatcher("Role?opt=1&Access=Permission&IdPermission=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="UPDATE PERMISSION STATE">
                    try {
                        IdPermission = Integer.parseInt(request.getParameter("IdPermission"));
                    } catch (NumberFormatException e) {
                        IdPermission = 0;
                    }
                    state = Integer.parseInt(request.getParameter("state"));
                    if (state == 1) {
                        state = 0;
                    } else {
                        state = 1;
                    }
                    result = RoleJpa.PermissionUpdateState(IdPermission, state);
                    request.setAttribute("UpdatePermissionState", result);
                    request.getRequestDispatcher("Role?opt=1&Access=Permission&IdPermission=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (IOException | ServletException ex) {
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
