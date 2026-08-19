package Servlets;

import Controladoras.UsuarioJpaController;
import Email.Control_encriptacion;
import java.io.IOException;
import java.io.PrintWriter;
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
            HttpSession sesion = request.getSession();
            String rol = sesion.getAttribute("Rol").toString();
            int opc = Integer.parseInt(request.getParameter("op"));
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            int idCargo = 0;
            int idCargoC = 0;
            int idUsuario = 0;
            int codigo = 0;
            int documento = 0;
            int estado = 0;
            String nombre = "";
            String apellido = "";
            String usuario = "";
            String correo = "";
            String usuarioR = "";
            String contrasena = "";
            int validacion = 0;
            String filtro = "";
            boolean resultado = false;
            if (opc <= 6) {
                switch (opc) {
                    case 1:
                        filtro = request.getParameter("txt_bus");
                        idUsuario = Integer.parseInt(request.getParameter("idU").toString());
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        if (idCargo != 0) {
                            if (idUsuario == 0) {
                                request.setAttribute("consultaUsuario", jpa_usuario.ConsultaUsuarioPorIdCargo(idCargo));
                                request.setAttribute("idCargo", idCargo);
                                request.setAttribute("filtro", filtro);
                            } else {
                                request.setAttribute("consultaUsuario", jpa_usuario.ConsultaUsuarioPorIdCargo(idCargo));
                                request.setAttribute("consultaUsuarioM", jpa_usuario.ConsultaUsuarioPorId(idUsuario));
                                request.setAttribute("idCargo", idCargo);
                                request.setAttribute("filtro", filtro);
                            }
                        } else if (filtro == null || filtro.isEmpty()) {
                            if (idUsuario == 0) {
                                request.setAttribute("consultaUsuario", jpa_usuario.ConsultaUsuarios());
                                request.setAttribute("filtro", filtro);
                                request.setAttribute("idCargo", idCargo);
                            } else {
                                request.setAttribute("consultaUsuarioM", jpa_usuario.ConsultaUsuarioPorId(idUsuario));
                                request.setAttribute("consultaUsuario", jpa_usuario.ConsultaUsuarios());
                                request.setAttribute("filtro", filtro);
                                request.setAttribute("idCargo", idCargo);
                            }
                        } else if (idUsuario == 0) {
                            request.setAttribute("consultaUsuario", jpa_usuario.ConsultaUsuarioPorFiltro(filtro));
                            request.setAttribute("filtro", filtro);
                            request.setAttribute("idCargo", idCargo);
                        } else {
                            request.setAttribute("consultaUsuarioM", jpa_usuario.ConsultaUsuarioPorId(idUsuario));
                            request.setAttribute("consultaUsuario", jpa_usuario.ConsultaUsuarioPorFiltro(filtro));
                            request.setAttribute("filtro", filtro);
                            request.setAttribute("idCargo", idCargo);
                        }
                        request.getRequestDispatcher("usuario.jsp").forward(request, response);
                        break;
                    case 2:
                        idCargo = Integer.parseInt(request.getParameter("slc_cargo").toString());
                        usuarioR = request.getParameter("txt_registro");
                        nombre = request.getParameter("txt_nombre");
                        apellido = request.getParameter("txt_apellido");
                        documento = Integer.parseInt(request.getParameter("txt_documento"));
                        codigo = Integer.parseInt(request.getParameter("txt_codigo").toString());
                        usuario = request.getParameter("txt_usuario");
                        contrasena = request.getParameter("txt_pass");
                        correo = request.getParameter("txt_mail");
                        resultado = jpa_usuario.RegistroUsuario(idCargo, usuarioR, nombre, apellido, documento, codigo, usuario, contrasena, correo);
                        if (resultado) {
                            request.setAttribute("Resultado_Usuario", resultado);
                        } else {
                            request.setAttribute("Resultado_Usuario", resultado);
                        }
                        request.getRequestDispatcher("Usuario?op=1&idU=" + 0 + "&txt_bus=&idC=" + 0 + "").forward(request, response);
                        break;
                    case 3:
                        idCargo = Integer.parseInt(request.getParameter("idC").toString());
                        filtro = request.getParameter("txt_bus");
                        idUsuario = Integer.parseInt(request.getParameter("idU").toString());
                        estado = Integer.parseInt(request.getParameter("est").toString());
                        resultado = jpa_usuario.CambiarEstadoUsuario(idUsuario, estado);
                        if (resultado) {
                            request.setAttribute("Resultado_UsuarioE", resultado);
                            request.setAttribute("estado", estado);
                        } else {
                            request.setAttribute("Resultado_UsuarioE", resultado);
                        }
                        request.getRequestDispatcher("Usuario?op=1&idU=" + 0 + "&txt_bus=" + filtro + "&idC=" + idCargo + "").forward(request, response);
                        break;
                    case 4:
                        idUsuario = Integer.parseInt(request.getParameter("idU").toString());
                        idCargo = Integer.parseInt(request.getParameter("slc_cargoM").toString());
                        idCargoC = Integer.parseInt(request.getParameter("idC").toString());
                        usuarioR = request.getParameter("txt_registroM");
                        nombre = request.getParameter("txt_nombreM");
                        apellido = request.getParameter("txt_apellidoM");
                        documento = Integer.parseInt(request.getParameter("txt_documentoM").toString());
                        codigo = Integer.parseInt(request.getParameter("txt_codigoM").toString());
                        usuario = request.getParameter("txt_usuarioM");
                        filtro = request.getParameter("txt_bus");
                        correo = request.getParameter("txt_mailM");
                        resultado = jpa_usuario.ModificarUsuario(idUsuario, idCargo, usuarioR, nombre, apellido, documento, codigo, usuario, correo);
                        request.setAttribute("Resultado_UsuarioM", resultado);
                        request.getRequestDispatcher("Usuario?op=1&idU=" + 0 + "&txt_bus=" + filtro + "&idC=" + idCargoC + "").forward(request, response);
                        break;
                    case 5:
                        idUsuario = Integer.parseInt(request.getParameter("idU").toString());
                        validacion = Integer.parseInt(request.getParameter("validacion").toString());
                        resultado = jpa_usuario.RestablecerPassUsuario(idUsuario);
                        if (resultado) {
                            request.setAttribute("resultado_contraseñaC", resultado);
                        } else {
                            request.setAttribute("resultado_contraseñaC", resultado);
                        }
                        if (validacion == 1) {
                             request.getRequestDispatcher("Usuario?op=1&idU=" + 0 + "&txt_bus=&idC=" + 0 + "").forward(request, response);
                        } else {
                            request.getRequestDispatcher("salir.jsp").forward(request, response);
                        }
                        break;
                }
            } else {
                request.setAttribute("res", "Se a producido un error. \\rPor favor intente de nuevo.");
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            }
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
