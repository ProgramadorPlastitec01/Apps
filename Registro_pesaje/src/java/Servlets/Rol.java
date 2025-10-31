package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.RolJpaController;
import javax.servlet.http.HttpSession;

public class Rol extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            PrintWriter out = response.getWriter();
            RolJpaController jparol = new RolJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_rol = 0, estado = 0;
            String nombre = "", usuario_registro = "";
            boolean result = false;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO USUARIO">
                    try {
                        id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    } catch (Exception e) {
                        id_rol = 0;
                    }
                    request.setAttribute("id_rol", id_rol);
                    request.getRequestDispatcher("Rol.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR USUARIO">
                    try {
                        id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    } catch (Exception e) {
                        id_rol = 0;
                    }
                    nombre = request.getParameter("Txt_nombre");
//                    try {
//                        usuario_registro = request.getParameter("nombre");
//                    } catch (Exception e) {
//                        usuario_registro = "ADMINISTRADOR";
//                    }
                    usuario_registro = "ADMINISTRADOR";
                    if (id_rol > 0) {
                        result = jparol.ModificarRol(id_rol,nombre);
                        id_rol = 0;
                        request.setAttribute("Modificar_Rol", result);
                    } else {
                        result = jparol.RegistrarRol(nombre, usuario_registro);
                        request.setAttribute("Registro_Rol", result);
                    }
                    request.getRequestDispatcher("Rol?opc=1&id_rol=" + id_rol + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO USUARIO">
                    id_rol = Integer.parseInt(request.getParameter("id_rol"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    if (estado == 1) {
                        estado = 0;
                    } else {
                        estado = 1;
                    }
                    result = jparol.CambiarEstadoRol(id_rol, estado);
                    request.setAttribute("Cambiar_estado_Rol", result);
                    request.getRequestDispatcher("Rol?opc=1&id_rol=" + 0 + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
