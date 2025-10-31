package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.UsuarioJpaController;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

public class Usuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            PrintWriter out = response.getWriter();
            UsuarioJpaController jpausr = new UsuarioJpaController();
            Calendar cal = Calendar.getInstance();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = 0, estado = 0, documento = 0, codigo = 0, id_rol = 0;
            String nombre = "", apellido = "", user = "", password = "";
            boolean result = false;
            int anio = cal.get(Calendar.YEAR);
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO USUARIO">
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    request.setAttribute("id_usuario", id_usuario);
                    request.getRequestDispatcher("Usuario.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR USUARIO">
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    documento = Integer.parseInt(request.getParameter("documento"));
                    codigo = Integer.parseInt(request.getParameter("codigo"));
                    user = request.getParameter("Txt_usuario");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol"));
                    password = request.getParameter("Txt_pass");
                    if (password == null) {
                        password = String.valueOf(anio);
                    }
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    if (id_usuario > 0) {
                        result = jpausr.ModificarUsuario(nombre, apellido, documento, codigo, user, id_rol, id_usuario);
                        id_usuario = 0;
                        request.setAttribute("Modificar_Usuario", result);
                    } else {
                        result = jpausr.RegistrarUsuario(nombre, apellido, documento, codigo, user, password, id_rol, rol_usuario);
                        request.setAttribute("Registro_Usuario", result);
                    }
                    request.getRequestDispatcher("Usuario?opc=1&id_usuario=" + id_usuario + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO USUARIO">
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    if (estado == 1) {
                        estado = 0;
                    } else {
                        estado = 1;
                    }
                    result = jpausr.CambiarEstadoUsuario(estado, id_usuario);
                    request.setAttribute("Cambiar_estado_Usuario", result);
                    request.getRequestDispatcher("Usuario?opc=1&id_usuario=" + 0 + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="RESTABLECER CONTRASEÑA">
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    result = jpausr.RestablecerPassword(id_usuario);
                    request.setAttribute("Restablecer_Password", result);
                    request.getRequestDispatcher("Usuario?opc=1&id_usuario=" + 0 + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Error_app", true);
            request.getRequestDispatcher("Usuario.jsp").forward(request, response);
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
