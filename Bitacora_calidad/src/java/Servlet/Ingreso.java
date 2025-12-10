/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.UsuariosJpaController;
import static Metodo.Control_encriptacion.md5;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Ingreso extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            HttpSession sesion = request.getSession();
            UsuariosJpaController ObjetoUsuarios = new UsuariosJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = 0;
            boolean resultado = false;
            String usuario = "";
            String contrasena = "";
            String contrasenaE = "";
            List lst_usa = null;
            List lst_usaE = null;
//</editor-fold>

            switch (opc) {
                case 1:
                    usuario = request.getParameter("user");
                    contrasena = request.getParameter("password");
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        request.setAttribute("ingreso_sistema", "true");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5(contrasena);
                        lst_usa = ObjetoUsuarios.logeoUsuario(usuario, contrasena);
                        lst_usaE = ObjetoUsuarios.logeoUsuario(usuario, contrasenaE);
                        if (lst_usa != null || lst_usaE != null) {
                            Object[] obj_usa = (Object[]) ((lst_usa != null) ? lst_usa.get(0) : lst_usaE.get(0));
                            if (obj_usa[6].toString().length() == contrasenaE.length()) {
                                List resultadoLogin = ObjetoUsuarios.logeoUsuario(usuario, contrasenaE);
                                if (resultadoLogin != null) {
                                    Object[] obj_sesion = (Object[]) resultadoLogin.get(0);
                                    if (obj_sesion[4].equals(1)) {
                                        sesion.setAttribute("identificacion", obj_sesion[0]);
                                        sesion.setAttribute("nombre", obj_sesion[1].toString() + " " + obj_sesion[2].toString());
                                        sesion.setAttribute("rol", obj_sesion[3]);
                                        request.getRequestDispatcher("menu.jsp").forward(request, response);
                                    } else {
                                        request.setAttribute("estadoInactivo", "false"); // alerta indica el usuario inactivo
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("ingreso_sistema", "false");
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("id_usa", obj_usa[0]); // id del usuario
                                request.setAttribute("cambio_contraseña", "true"); // ventana emergente para cambiar la contraseña y encriptarla
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("ingreso_sistema", "false"); // datos incorrectos del usuario con contraseña normal
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    contrasena = request.getParameter("txt_passw");
                    contrasenaE = md5(contrasena);
                    resultado = ObjetoUsuarios.cambiarPassUsuario(id_usuario, contrasenaE);
                    request.setAttribute("resultado_contraseña", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ex) {
            request.getRequestDispatcher("salir.jsp").forward(request, response);
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
