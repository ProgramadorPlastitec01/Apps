package Servlets;

import Controladoras.UsuarioJpaController;
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
            // <editor-fold defaultstate="collapsed"  desc="Inicio session">
            HttpSession sesion = request.getSession();
            UsuarioJpaController jpa_usuarios = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            String usuario = "";
            String contrasena = "";
            String contrasenaE = "";
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_usuario = 0;
            boolean resultado = false;
            switch (opc) {
                case 1:
                    usuario = request.getParameter("Txt_user");
                    contrasena = request.getParameter("Txt_password");
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        request.setAttribute("ingreso_sistema", "true");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5.md5(contrasena);
                        List resultadoLoginU = jpa_usuarios.Login(usuario, contrasena);
                        List resultadoLoginUE = jpa_usuarios.Login(usuario, contrasenaE);
                        if (resultadoLoginU != null || resultadoLoginUE != null) {
                            Object[] obj_usa = (Object[]) ((resultadoLoginU != null) ? resultadoLoginU.get(0) : resultadoLoginUE.get(0));
                            if (obj_usa[9].toString().length() == contrasenaE.length()) {
                                List resultadoLogin = jpa_usuarios.Login(usuario, contrasenaE);
                                if (resultadoLogin != null) {
                                    Object[] objetos = (Object[]) resultadoLogin.get(0);
                                    if (objetos[10].equals(1)) {
                                        sesion.setAttribute("Identificacion", objetos[0]);
                                        sesion.setAttribute("idRol", objetos[1]);
                                        sesion.setAttribute("Nombre", objetos[4].toString() + " " + objetos[5].toString());
                                        sesion.setAttribute("documento", objetos[6]);
                                        sesion.setAttribute("codigo", objetos[7]);
                                        sesion.setAttribute("Rol", objetos[11]);
                                        sesion.setAttribute("Area", objetos[13]);
                                        sesion.setAttribute("Cargo", objetos[12]);
                                        request.getRequestDispatcher("menu.jsp").forward(request, response);
                                    } else {
                                        request.setAttribute("estadoInactivo", "false");
                                        request.getRequestDispatcher("index.jsp").forward(request, response);
                                    }
                                } else {
                                    request.setAttribute("ingreso_sistema", "false");
                                    request.getRequestDispatcher("index.jsp").forward(request, response);
                                }
                            } else {
                                request.setAttribute("id_usa", obj_usa[0]);
                                request.setAttribute("cambio_contraseña", "true");
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                        } else {
                            request.setAttribute("ingreso_sistema", "false");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    contrasena = request.getParameter("txt_passw");
                    contrasenaE = md5.md5(contrasena);
                    resultado = jpa_usuarios.CambiarPassUsuario(id_usuario, contrasenaE);
                    if (resultado) {
                        request.setAttribute("resultado_contraseña", resultado);
                    } else {
                        request.setAttribute("resultado_contraseña", resultado);
                    }
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
            // </editor-fold>
        } catch (Exception ex) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            out.close();
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
