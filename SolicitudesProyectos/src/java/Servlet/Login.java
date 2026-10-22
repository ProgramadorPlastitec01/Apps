package Servlet;

import Controladores.UsuarioJpaController;
import Metodos.Control_encriptacion;
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
            HttpSession sesion = request.getSession();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            int opc = Integer.parseInt(request.getParameter("opc"));
            Control_encriptacion md5 = new Control_encriptacion();
            String contrasenaE = "";
            int id_usuario = 0;
            boolean resultado = false;
            String contrasena = "";
            String usuario = "";
            switch (opc) {
                case 1:
                    usuario = request.getParameter("Txt_user");
                    contrasena = request.getParameter("Txt_password");
                    if (usuario.isEmpty() || contrasena.isEmpty()) {
                        request.setAttribute("CamposVacios", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        contrasenaE = md5.md5(contrasena);
                        List lst_usuario = jpa_usuario.login(usuario, contrasena);
                        List lst_usuarioE = jpa_usuario.login(usuario, contrasenaE);
                        if (lst_usuario != null || lst_usuarioE != null) {
                            Object[] obj_usuarios = (Object[]) ((lst_usuario != null) ? lst_usuario.get(0) : lst_usuarioE.get(0));
                            if (obj_usuarios[5].toString().length() == contrasenaE.length()) {
                                // Se reutiliza lst_usuarioE: la consulta que
                                // habia aqui era identica a la de arriba, con
                                // los mismos parametros. Era un tercer viaje a
                                // la base por cada intento de ingreso.
                                List resultadoLogin = lst_usuarioE;
                                if (resultadoLogin != null) {
                                    Object[] obj_usa = (Object[]) resultadoLogin.get(0);
                                    // estado es int(11) NULLABLE: el cast directo
                                    // a Integer lanzaba NullPointerException con estado
                                    // NULL, y el unboxing fallaba si el driver devolvia
                                    // otro tipo numerico. Se lee via Number y se guarda
                                    // el NULL como inactivo, no como caida.
                                    if (obj_usa[7] != null && ((Number) obj_usa[7]).intValue() == 1) {
                                        sesion.setAttribute("id", obj_usa[0]);
                                        sesion.setAttribute("Nombre", obj_usa[1] + " " + obj_usa[2]);
                                        sesion.setAttribute("Rol", obj_usa[3]);
                                        sesion.setAttribute("Area", obj_usa[4]);
                                        sesion.setAttribute("Documento", obj_usa[8]);
                                        request.setAttribute("Ingreso_sistema", true);
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
                                sesion.setAttribute("id_pendiente_cambio", obj_usuarios[0]);
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
                    Object idPendiente = sesion.getAttribute("id_pendiente_cambio");
                    Object idSesion = sesion.getAttribute("id");
                    boolean autorizadoCambio = (idPendiente != null && Integer.parseInt(idPendiente.toString()) == id_usuario)
                            || (idSesion != null && Integer.parseInt(idSesion.toString()) == id_usuario);
                    if (!autorizadoCambio) {
                        response.sendRedirect("index.jsp");
                        return;
                    }
                    sesion.removeAttribute("id_pendiente_cambio");
                    try {
                        contrasena = request.getParameter("txt_passw");
                    } catch (Exception e) {
                        contrasena = "";
                    }
                    if (contrasena == null || contrasena.trim().isEmpty()) {
                        request.setAttribute("CamposVacios", true);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        return;
                    }
                    contrasenaE = md5.md5(contrasena);
                    resultado = jpa_usuario.modificarPasswordUsuario(id_usuario, contrasenaE);
                    request.setAttribute("resultado_contraseña", resultado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;

                case 3:
                    // Restablecer contraseña: solo usuario autenticado para si mismo, o ADMIN
                    Object idLogueado = sesion.getAttribute("id");
                    Object rolLogueado = sesion.getAttribute("Rol");
                    if (idLogueado == null) {
                        response.sendRedirect("index.jsp");
                        return;
                    }
                    id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
                    int idSes = Integer.parseInt(idLogueado.toString());
                    String rSes = (rolLogueado != null) ? rolLogueado.toString() : "";
                    if (id_usuario != idSes && !rSes.equals("ADMIN")) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "No autorizado");
                        return;
                    }
                    resultado = jpa_usuario.RestablecerContrasena(id_usuario);
                    request.setAttribute("resultado_contraseñaR", resultado);
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
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
