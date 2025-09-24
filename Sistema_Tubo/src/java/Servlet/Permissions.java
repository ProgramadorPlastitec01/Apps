package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import Controladores.PermisosJpaController;
import java.util.List;

public class Permissions extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String UserName = sesion.getAttribute("Nombres").toString();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            PrintWriter out = response.getWriter();
            PermisosJpaController PermissionJpa = new PermisosJpaController();
            String UserRol = sesion.getAttribute("idRol").toString();
            List lst_Permission = null;
            int opc = Integer.parseInt(request.getParameter("opc"));
            boolean result = false;

            int id_permiss = 0, est = 0;
            String module = "", option = "", description = "", UserReg = "";

            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="CONSULT PERMISSIONS">
                    try {
                        id_permiss = Integer.parseInt(request.getParameter("id_permiss"));
                    } catch (Exception e) {
                        id_permiss = 0;
                    }
                    request.setAttribute("id_permiss", id_permiss);
                    request.setAttribute("id_rol", UserRol);
                    request.getRequestDispatcher("Permission.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTER && EDIT PERMISSIONS">
                    try {
                        id_permiss = Integer.parseInt(request.getParameter("id_permiss"));
                    } catch (Exception e) {
                        id_permiss = 0;
                    }

                    module = request.getParameter("Txt_module");
                    option = request.getParameter("Txt_option");
                    description = request.getParameter("Txt_description");

                    if (id_permiss <= 0) {
                        result = PermissionJpa.PermissionRegister(module, option, description, rol_usuario);
                        request.setAttribute("PermissionRegister", result);
                    }else{
                        est = Integer.parseInt(request.getParameter("Nmb_est"));
                        result = PermissionJpa.PermissionUpdate(id_permiss, module, option, description, est);
                        request.setAttribute("PermissionUpdate", result);
                    }
                    request.getRequestDispatcher("Permissions?opc=1&id_permiss=0").forward(request, response);
//</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="ESTATE CHANGE">
                    try {
                        id_permiss = Integer.parseInt(request.getParameter("id_permiss"));
                    } catch (Exception e) {
                        id_permiss = 0;
                    }
                    est = Integer.parseInt(request.getParameter("est"));
                    if (est == 1) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                    result = PermissionJpa.PermissionEstate(id_permiss, est);
                    request.setAttribute("PermissionChangeEstate", result);
                    request.getRequestDispatcher("Permissions?opc=1&id_permiss=0").forward(request, response);
//</editor-fold>
                    break;
            }
        } catch (Exception ex) {
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
