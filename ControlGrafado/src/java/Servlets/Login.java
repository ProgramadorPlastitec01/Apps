package Servlets;

import Controladores.ClienteJpaController;
import Email.Control_encriptacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            ClienteJpaController jpa_usuario = new ClienteJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            int opc = Integer.parseInt(request.getParameter("opc"));
            String contrasenaE = "";
            int id_usuario = 0;
            boolean resultado = false;
            switch (opc) {
                case 1:
                    String usuario = request.getParameter("Txt_user").toUpperCase();
                    String contrasena = request.getParameter("Txt_password");
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        request.setAttribute("CamposVacios", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5.md5(contrasena);
                        List lst_usuario = jpa_usuario.login(usuario, contrasena);
                        List lst_usuarioE = jpa_usuario.login(usuario, contrasenaE);
                        if (lst_usuario != null || lst_usuarioE != null) {
                            Object[] obj_usuarios = (Object[]) ((lst_usuario != null) ? lst_usuario.get(0) : lst_usuarioE.get(0));
                            if (obj_usuarios[6].toString().length() == contrasenaE.length()) {
                                List resultadoLogin = jpa_usuario.login(usuario, contrasenaE);
                                if (resultadoLogin != null) {
                                    Object[] obj_usa = (Object[]) resultadoLogin.get(0);
                                    if ((Integer) obj_usa[7] == 1) {
                                        session.setAttribute("id_usuario", obj_usa[0]);
                                        session.setAttribute("Nombre", obj_usa[2].toString() + " " + obj_usa[3].toString());
                                        session.setAttribute("Documento", obj_usa[4].toString());
                                        session.setAttribute("Usuario", obj_usa[5].toString());
                                        session.setAttribute("id_rol", obj_usa[8].toString());
                                        session.setAttribute("Rol", obj_usa[9].toString());
                                        request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                                    } else {
                                        request.setAttribute("UsuarioInactivo", true);
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("DatosIncorrectos", true);
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("id_usa", obj_usuarios[0]);
                                request.setAttribute("Cambio_contraseña", true);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("DatosIncorrectos", true);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    contrasena = request.getParameter("txt_passw");
                    contrasenaE = md5.md5(contrasena);
                    resultado = jpa_usuario.modificarContraseña(id_usuario, contrasenaE);
                    request.setAttribute("resultado_contraseña", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    resultado = jpa_usuario.RestablecerPassword(id_usuario);
                    request.setAttribute("resultado_contraseña", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
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
