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
import Email.Control_encriptacion;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            String contrasenaE = "";
            int id_usuario = 0;
            boolean resultado = false;
            switch (opc) {
                case 1:
                    // <editor-fold defaultstate="collapsed"  desc="Inicio session">
                    String usuario = request.getParameter("Txt_user");
                    String contrasena = request.getParameter("Txt_password");
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5.md5(contrasena);
                        List lst_usuario = jpa_usuario.Login(usuario, contrasena);
                        List lst_usuarioE = jpa_usuario.Login(usuario, contrasenaE);
                        if (lst_usuario != null || lst_usuarioE != null) {
                            Object[] obj_usuarios = (Object[]) ((lst_usuario != null) ? lst_usuario.get(0) : lst_usuarioE.get(0));
                            if (obj_usuarios[4].toString().length() == contrasenaE.length()) {
                                List resultadoLogin = jpa_usuario.Login(usuario, contrasenaE);
                                if (resultadoLogin != null) {
                                    Object[] obj_usa = (Object[]) resultadoLogin.get(0);
                                    if ((Integer) obj_usa[7] == 1) {
                                        sesion.setAttribute("id", obj_usuarios[0]);
                                        sesion.setAttribute("id_rol", obj_usuarios[1]);
                                        sesion.setAttribute("Rol", obj_usuarios[6]);
                                        sesion.setAttribute("Nombre", obj_usuarios[2]);
                                        sesion.setAttribute("Mail", obj_usuarios[5]);
                                        sesion.setAttribute("documento", obj_usuarios[8]);
                                        sesion.setAttribute("welcome", true);
                                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                                    } else {
                                        request.setAttribute("estadoInactivo", true);
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("ingreso_sistema", true);
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("id_usa", obj_usuarios[0]);
                                request.setAttribute("cambio_contraseña", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("ingreso_sistema", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    // </editor-fold>
                    break;
                case 2:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    contrasena = request.getParameter("txt_passw");
                    contrasenaE = md5.md5(contrasena);
                    resultado = jpa_usuario.modificarPasswordUsuario(id_usuario, contrasenaE);
                    request.setAttribute("resultado_contraseña", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
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
