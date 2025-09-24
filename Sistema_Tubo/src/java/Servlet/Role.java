package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.RolJpaController;
import javax.servlet.http.HttpSession;

public class Role extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String UserRol = sesion.getAttribute("idRol").toString();
            RolJpaController RolJpa = new RolJpaController();
            PrintWriter out = response.getWriter();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_rol = 0, state = 0,id_rol_permission = 0;
            String name = "", permissions = "", user_record = "ADMINISTRADOR";
            boolean result = false;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULE ROLE">
                    try {
                        id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    } catch (Exception e) {
                        id_rol = 0;
                    }
                    try {
                        id_rol_permission = Integer.parseInt(request.getParameter("id_rol_permission"));
                    } catch (Exception e) {
                        id_rol_permission = 0;
                    }
                    request.setAttribute("id_rol", id_rol);
                    request.setAttribute("idRol", UserRol);
                    request.setAttribute("id_rol_permission", id_rol_permission);
                    request.getRequestDispatcher("Role.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="ROLE REGISTRER AND UPDATE">
                    try {
                        id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    } catch (Exception e) {
                        id_rol = 0;
                    }
                    name = request.getParameter("Txt_name");
                    if (id_rol > 0) {
                        try {
                            state = Integer.parseInt(request.getParameter("state"));
                        } catch (Exception e) {
                            state = 0;
                        }
                        result = RolJpa.RolUpdate(id_rol, name, state);
                        request.setAttribute("Role_update", result);
                    } else {
                        result = RolJpa.RolRegister(name, rol_usuario);
                        request.setAttribute("Role_register", result);

                    }
                    request.getRequestDispatcher("Role?opc=1&id_rol=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="ROL CHANGE STATUS">
                    id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    state = Integer.parseInt(request.getParameter("state"));
                    if (state == 1) {
                        state = 0;
                    } else {
                        state= 1;
                    }
                    result = RolJpa.StateUpdate(id_rol, state);
                    request.setAttribute("Role_ChangeStatus", result);
                    request.getRequestDispatcher("Role?opc=1&id_rol=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="ASSIGN PERMISSION">
                    try {
                        id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    } catch (Exception e) {
                        id_rol = 0;
                    }
                    permissions = request.getParameter("Cbx_permission");
                    result = RolJpa.UpdatePermission(id_rol, permissions);
                    request.setAttribute("Role_UpdatePermission", result);
                    request.getRequestDispatcher("Role?opc=1&id_rol=0").forward(request, response);
                    
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
