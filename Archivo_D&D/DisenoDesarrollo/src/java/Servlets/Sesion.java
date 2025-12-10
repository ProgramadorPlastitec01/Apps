/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Controladores.UsuarioJpaController;
import Methods.controlEncriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Sesion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //Sesion
        int idUsuario;
        String user, password, passMd5;
        Object[] obj_sesion;
        boolean accion;
        HttpSession sesion = request.getSession();
        //JPAS
        UsuarioJpaController jpacusa = new UsuarioJpaController();
        controlEncriptacion md5 = new controlEncriptacion();
        //Variables Globales
        int opc = Integer.parseInt(request.getParameter("opc").toString());
        List<Object[]> lst_usuario = null;
        PrintWriter out = response.getWriter();
        try {
            switch (opc) {
                case 1:
                    user = request.getParameter("Txt_user").toString();
                    password = request.getParameter("Txt_password").toString();
                    if (password.length() >= 8) {
                        String str = controlEncriptacion.md5(password);
                        lst_usuario = jpacusa.Login(user, str);
                        if (lst_usuario == null) {
                            lst_usuario = jpacusa.Login(user, password);
                        }
                    } else {
                        lst_usuario = jpacusa.Login(user, password);
                    }
                    if (lst_usuario == null) {
                        request.setAttribute("Alerta", "Usuario_no_existe");
                        request.getRequestDispatcher("index.jsp").forward((ServletRequest) request, (ServletResponse) response);
                        break;
                    }
                    obj_sesion = lst_usuario.get(0);
                    if (((Integer) obj_sesion[9]).intValue() == 0) {
                        request.setAttribute("Alerta", "Usuario_desactivado");
                        request.setAttribute("var1", obj_sesion[6]);
                        request.getRequestDispatcher("index.jsp").forward((ServletRequest) request, (ServletResponse) response);
                        break;
                    }
                    if (obj_sesion[13].equals("Si")) {
                        request.setAttribute("idUsuario", obj_sesion[0]);
                        request.setAttribute("Alerta", "Cambio_contrasena");
                        request.getRequestDispatcher("index.jsp").forward((ServletRequest) request, (ServletResponse) response);
                        break;
                    }
                    sesion.setAttribute("Id_usuario", obj_sesion[0]);
                    sesion.setAttribute("Usuario", obj_sesion[3].toString() + " " + obj_sesion[4].toString());
                    sesion.setAttribute("id_position", obj_sesion[10]);
                    sesion.setAttribute("Cargo", obj_sesion[11].toString());
                    sesion.setAttribute("Rol/Nombres", obj_sesion[11].toString() + " / " + obj_sesion[3].toString() + " " + obj_sesion[4].toString());
                    sesion.setAttribute("Mail", obj_sesion[8].toString());
                    sesion.setAttribute("Pass_mail", obj_sesion[12].toString());
                    sesion.setAttribute("Usuario_cargo", obj_sesion[3].toString() + " " + obj_sesion[4].toString() + " / " + obj_sesion[11].toString());
                    sesion.setAttribute("Menu", obj_sesion[0]);
                    sesion.setAttribute("Documento", obj_sesion[5]);
                    request.setAttribute("Alerta", "Bienvenido");
                    request.getRequestDispatcher("Inicio.jsp").forward((ServletRequest) request, (ServletResponse) response);
                    break;
                case 2:
                    idUsuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password").toString();
                    passMd5 = controlEncriptacion.md5(password);
                    accion = jpacusa.cambiarPass(idUsuario, passMd5);
                    if (accion == true) {
                        request.setAttribute("Alerta", "password_actualizada");
                    }
                    request.getRequestDispatcher("index.jsp").forward((ServletRequest) request, (ServletResponse) response);
                    break;
                case 3:
                    idUsuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
                    accion = jpacusa.reestablecePass(idUsuario);
                    if (accion == true) {
                        sesion.invalidate();
                        request.setAttribute("Alerta", "password_reestablecida");
                        request.getRequestDispatcher("index.jsp").forward((ServletRequest) request, (ServletResponse) response);
                        break;
                    }
                    request.setAttribute("Alerta", "error_restablecimiento");
                    request.getRequestDispatcher("Inicio.jsp").forward((ServletRequest) request, (ServletResponse) response);
                    break;
                case 4:
                    request.setAttribute("Alerta", "Salida");
                    request.getRequestDispatcher("Salir.jsp").forward(request, response);
                    break;
            }
        } catch (Exception ex) {
            // Logger.getLogger(Orden.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("Alerta", "Error_sesion");
//            
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
