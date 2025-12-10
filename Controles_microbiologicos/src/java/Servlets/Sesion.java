package Servlets;

import Metodos.controlEncriptacion;
import controladoras.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Sesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            controlEncriptacion md5 = new controlEncriptacion();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            //String user, password;
            int idUsuario;
            List lst_usa = null;
            List lst_usa1 = null;
            String user, password, passwordEncrypt = "";
            boolean accion = true;
            List lst_usuario = null;

            switch (opc) {
                case 1:
                    user = request.getParameter("Txt_user").toString();
                    password = request.getParameter("Txt_password").toString();
                    if (user.isEmpty() && password.isEmpty()) {
                        request.setAttribute("Alerta", "usuario_sin_digitar");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else if (password.length() >= 8) {
                        passwordEncrypt = md5.md5(password);
                        lst_usuario = jpacusa.Usuario_sesión(user, passwordEncrypt);
                        if (lst_usuario != null) {
                            Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                            sesion.setAttribute("Id_usuario", obj_sesion[0]);
                            sesion.setAttribute("Nombre", obj_sesion[2]);
                            sesion.setAttribute("Rol", obj_sesion[4]);
                            request.getRequestDispatcher("Menu.jsp").forward(request, response);
                        } else {
                            lst_usuario = jpacusa.Usuario_sesión(user, password);
                            request.setAttribute("Alerta", "Usuario_no_existe");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    try {
                        lst_usa = jpacusa.Usuario_sesión(user, password);
                        Object[] obj_usuario = (Object[]) lst_usa.get(0);
                        if (Integer.parseInt(obj_usuario[6].toString()) == 1) {
                            lst_usa = jpacusa.Usuario_sesión(user, password);
                            request.setAttribute("idUsuario", obj_usuario[0]);
                            request.setAttribute("Alerta", "Cambio_contraseña");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    } catch (Exception e) {
                        request.setAttribute("Alerta", "password_incorrecta");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    break;
                case 2:
                    idUsuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password");
                    passwordEncrypt = md5.md5(password);
                    accion = jpacusa.cambiarPass(idUsuario, passwordEncrypt);
                    request.setAttribute("Alerta", "password_actualizada");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("Salir.jsp").forward(request, response);
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
