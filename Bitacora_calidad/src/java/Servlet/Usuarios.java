/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.UsuariosJpaController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Usuarios extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            UsuariosJpaController UsuJpa = new UsuariosJpaController();
            boolean resultado = true;
            int idusuario = 0;
            int documento = 0;
            String responsable = "";
            String nombre = "";
            String apellido = "";
            String user = "";
            String password = "";
            String rol = "";
            int estado = 0;
//</editor-fold>
            int opcion = Integer.parseInt(request.getParameter("l"));
            switch (opcion) {
                //<editor-fold defaultstate="collapsed" desc="1. CONSULTA">
                case 1:
                    request.setAttribute("usuarios", UsuJpa.consultaUsuarios());
                    request.getRequestDispatcher("usuarios.jsp").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. REGISTRAR">
                case 2:
                    responsable = request.getParameter("responsable");
                    documento = Integer.parseInt(request.getParameter("txtdocumento"));
                    nombre = request.getParameter("txtnombreus");
                    apellido = request.getParameter("txtapel");
                    user = request.getParameter("txtuser");
                    rol = request.getParameter("lstrol");
                    estado = Integer.parseInt(request.getParameter("estado"));
                    resultado = UsuJpa.nuevoUsuario(responsable, documento, nombre, apellido, user, rol, estado);
                    if (resultado) {
                        request.setAttribute("alerta_usuario", "true");
                        request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    } else {
                        request.setAttribute("alerta_usuario", "false");
                        request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    }
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="3. CONSULTAR_ID">
                case 3:
                    idusuario = Integer.parseInt(request.getParameter("d"));
                    request.setAttribute("unusuario", UsuJpa.consultaUnUsuarioPorId(idusuario));
                    request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="4. MODIFICAR">
                case 4:
                    idusuario = Integer.parseInt(request.getParameter("id"));
                    responsable = request.getParameter("Mresponsable");
                    documento = Integer.parseInt(request.getParameter("Mdocumento"));
                    nombre = request.getParameter("Mnombre");
                    apellido = request.getParameter("Mapellido");
                    rol = request.getParameter("Mrol");
                    user = request.getParameter("Muser");
                    password = request.getParameter("passM");
                    estado = Integer.parseInt(request.getParameter("Mestado"));
                    if (password.equals("")) {
                        resultado = UsuJpa.modificacionUsuario(idusuario, responsable, documento, nombre, apellido, rol, user, estado);
                    } else {
                        resultado = UsuJpa.cambiarPassUsuario(idusuario, password);
                    }
                    if (resultado) {
                        request.setAttribute("alerta_modusuario", "true");
                        request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    } else {
                        request.setAttribute("alerta_modusuario", "false");
                        request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    }
                    break;
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="5. MODIFICAR ESTADO">
                case 5:
                    idusuario = Integer.parseInt(request.getParameter("id"));
                    estado = Integer.parseInt(request.getParameter("Mestado"));
                    resultado = UsuJpa.modificarEstado(idusuario, estado);
                    if (resultado) {
                        request.setAttribute("alerta_modusuario", "true");
                        request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    } else {
                        request.setAttribute("alerta_modusuario", "false");
                        request.getRequestDispatcher("Usuarios?l=1").forward(request, response);
                    }
                    break;
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="6. CONTRASEÑA ">
                case 6:
                    idusuario = Integer.parseInt(request.getParameter("idUsuario"));
                    password = request.getParameter("txt_passM");
                    resultado = UsuJpa.cambiarPassUsuario(idusuario, password);
                    request.setAttribute("resultado_contraseñaR", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                    //</editor-fold>
            }

        } catch (RuntimeException e) {
            throw e;
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
