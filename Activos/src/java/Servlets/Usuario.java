package Servlets;

import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            HttpSession sesion = request.getSession();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            List lst_usuario = null;
            int opc = Integer.parseInt(request.getParameter("opc"));
            int idUsuario = 0;
            String nombre, apellido, usuario;
            int codigo, rol, area = 0;
            int documento = 0;
            boolean accion = true;
//</editor-fold> 
            switch (opc) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRA/CONSULTA">
                case 1:
                    try {
                        idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    } catch (Exception e) {
                        idUsuario = 0;
                    }
                    request.setAttribute("idUsuario", idUsuario);
                    request.getRequestDispatcher("Usuario.jsp").forward(request, response);
                    break;
//</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    nombre = request.getParameter("Txt_nombre");
                    apellido = request.getParameter("Txt_apellido");
                    documento = Integer.parseInt(request.getParameter("Txt_documento"));
                    codigo = Integer.parseInt(request.getParameter("Txt_codigo"));
                    usuario = request.getParameter("Txt_usuario");
                    rol = Integer.parseInt(request.getParameter("Cbx_rol"));
                    area = Integer.parseInt(request.getParameter("Cbx_area"));
                    accion = jpa_usuario.registarUsuario(nombre, apellido, documento, codigo, usuario, rol, area, sesion.getAttribute("Nombres").toString());
                    if (accion == true) {
                        request.setAttribute("Alerta", "Registro_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    request.getRequestDispatcher("Usuario?opc=1").forward(request, response);
                    break;
//</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICA">
                    idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    nombre = request.getParameter("Txt_nombreM");
                    apellido = request.getParameter("Txt_apellidoM");
                    documento = Integer.parseInt(request.getParameter("Txt_documentoM"));
                    codigo = Integer.parseInt(request.getParameter("Txt_codigoM"));
                    usuario = request.getParameter("Txt_usuarioM");
                    rol = Integer.parseInt(request.getParameter("Cbx_rolM"));
                    area = Integer.parseInt(request.getParameter("Cbx_areaM"));
                    accion = jpa_usuario.modificarUsuario(idUsuario, nombre, apellido, documento, codigo, usuario, rol, area);
                    if (accion) {
                        request.setAttribute("Alerta", "Modificar_usuario");
                        request.setAttribute("var1", nombre + " " + apellido);
                    } else {
                        request.setAttribute("Alerta", "Error_modificar");
                    }
                    request.getRequestDispatcher("Usuario?opc=1&idUsuario=0").forward(request, response);
                    break;
//</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="DESACTIVA">
                    idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    accion = jpa_usuario.desactivarUsuario(idUsuario);
                    request.getRequestDispatcher("Usuario?opc=1&idUsuario=0").forward(request, response);
                    break;
//</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="ACTIVA">
                    idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    accion = jpa_usuario.activarUsuario(idUsuario);
                    request.getRequestDispatcher("Usuario?opc=1&idUsuario=0").forward(request, response);
                    break;
//</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REEESTABLECE PASS">
                    idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    accion = jpa_usuario.reestablecePass(idUsuario);
                    request.setAttribute("Alerta", "password_reestablecida");
                    request.getRequestDispatcher("Usuario?opc=1&idUsuario=0").forward(request, response);
//</editor-fold>
                    break;
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
