package Servlets;

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

public class Sesion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            Control_encriptacion md5 = new Metodos.Control_encriptacion();
            //JPAS
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String user, password;
            List lst_usuario = null;
            String password_encript = "";
            int id_usuario = 0;
            switch (opc) {
                case 1:
                    user = request.getParameter("Txt_user").toString();
                    password = request.getParameter("Txt_password").toString();
                    if (password.length() >= 8) {
                        password_encript = md5.md5(password);
                        lst_usuario = jpacusa.Usuario_sesión(user, password_encript);
                        if (lst_usuario == null) {
                            lst_usuario = jpacusa.Usuario_sesión(user, password);
                        }
                    } else {
                        lst_usuario = jpacusa.Usuario_sesión(user, password);
                    }
                    if (lst_usuario == null) {
                        request.setAttribute("Alerta", "Usuario_no_existe");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        Object[] obj_sesion = (Object[]) lst_usuario.get(0);
                        if ((Integer) obj_sesion[5] == 0) {
                            request.setAttribute("Alerta", "Usuario_desactivado");
                            request.setAttribute("var1", obj_sesion[1]);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else if (obj_sesion[10].toString().equals("SI")) {
                            request.setAttribute("Alerta", "Cambio_contraseña");
                            request.setAttribute("var1", obj_sesion[0]);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        } else {
                            sesion.setAttribute("Id_usuario", obj_sesion[0]);
                            sesion.setAttribute("Nombres", obj_sesion[1]);
                            sesion.setAttribute("Rol/Nombres", obj_sesion[7] + "/" + obj_sesion[1]);
                            sesion.setAttribute("Codigo", obj_sesion[2]);
                            sesion.setAttribute("Usuario", obj_sesion[3]);
                            sesion.setAttribute("Password", obj_sesion[4]);
                            sesion.setAttribute("Estado", obj_sesion[5]);
                            sesion.setAttribute("Id_rol", obj_sesion[6]);
                            sesion.setAttribute("Nombre_rol", obj_sesion[7]);
                            sesion.setAttribute("Fecha_registro", obj_sesion[8]);
                            sesion.setAttribute("Menu", obj_sesion[0]);
                            request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                        }
                    }
                    break;
                case 2:
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 3:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    password = request.getParameter("Txt_password");
                    password_encript = md5.md5(password); 		// se encripta contraseña nueva
                    jpacusa.Cambiar_password(id_usuario, password_encript); // metodo para modificar la contraseña
                    request.setAttribute("Alerta", "Password_actualizado");  // alerta de exito
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                //<editor-fold defaultstate="collapsed" desc="SERVLET ANTERIOR">
//                    user = request.getParameter("Txt_user");
//                    password = request.getParameter("Txt_password");
//                    contrasena_incriptada = md5.md5(password);
//                    lst_usa = jpacusa.Usuarios_user(user);
//                    Object[] obj_usuario_user = (Object[]) lst_usa.get(0);
//                    if (obj_usuario_user[6].toString().length() == contrasena_incriptada.length()) {
//                        lst_usa = jpacusa.Usuario_sesion(user, contrasena_incriptada);
//                        if (lst_usa == null) {
//                            request.setAttribute("Alerta", "no_user");
//                            request.getRequestDispatcher("index.jsp").forward(request, response);
//                        } else {
//                            Object[] obj_sesion = (Object[]) lst_usa.get(0);
//                            if ((Integer) obj_sesion[5] == 0) {
//                                request.setAttribute("Alerta", "Usuario_desactivado");
//                                request.setAttribute("var1", obj_sesion[1]);
//                                request.getRequestDispatcher("index.jsp").forward(request, response);
//                            } else {
//                                sesion.setAttribute("Id_usuario", obj_sesion[0]);
//                                sesion.setAttribute("Nombres", obj_sesion[1]);
//                                sesion.setAttribute("Rol/Nombres", obj_sesion[7] + "/" + obj_sesion[1]);
//                                sesion.setAttribute("Codigo", obj_sesion[2]);
//                                sesion.setAttribute("Usuario", obj_sesion[3]);
//                                sesion.setAttribute("Password", obj_sesion[4]);
//                                sesion.setAttribute("Estado", obj_sesion[5]);
//                                sesion.setAttribute("Id_rol", obj_sesion[6]);
//                                sesion.setAttribute("Nombre_rol", obj_sesion[7]);
//                                sesion.setAttribute("Fecha_registro", obj_sesion[8]);
//                                sesion.setAttribute("Menu", obj_sesion[0]);
//                                request.getRequestDispatcher("Inicio.jsp").forward(request, response);
//                            }
//                        }
//                    } else {
//                        lst_usa = jpacusa.Usuario_sesion(user, password);
//                        if (lst_usa == null) {
//                            request.setAttribute("Alerta", "no_user");
//                            request.getRequestDispatcher("index.jsp").forward(request, response);
//                        } else {
//                            Object[] obj_sesion = (Object[]) lst_usa.get(0);
//                            if ((Integer) obj_sesion[5] == 0) {
//                                request.setAttribute("Alerta", "Usuario_desactivado");
//                                request.setAttribute("var1", obj_sesion[1]);
//                                request.getRequestDispatcher("index.jsp").forward(request, response);
//                            } else {
//                                sesion.setAttribute("Id_usuario", obj_sesion[0]);
//                                sesion.setAttribute("Nombres", obj_sesion[1]);
//                                sesion.setAttribute("Rol/Nombres", obj_sesion[7] + "/" + obj_sesion[1]);
//                                sesion.setAttribute("Codigo", obj_sesion[2]);
//                                sesion.setAttribute("Usuario", obj_sesion[3]);
//                                sesion.setAttribute("Password", obj_sesion[4]);
//                                sesion.setAttribute("Estado", obj_sesion[5]);
//                                sesion.setAttribute("Id_rol", obj_sesion[6]);
//                                sesion.setAttribute("Nombre_rol", obj_sesion[7]);
//                                sesion.setAttribute("Fecha_registro", obj_sesion[8]);
//                                sesion.setAttribute("Menu", obj_sesion[0]);
//                                cambio_contrasena = 5;
//                                request.setAttribute("cambio_contrasena", cambio_contrasena);
//                                request.getRequestDispatcher("Inicio.jsp").forward(request, response);
//                            }
//                        }
//                    }
//                case 2:
//                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
//                    break;
//                case 3:
//                    m = request.getParameter("m");
//                    request.setAttribute("m", m);
//                    request.getRequestDispatcher("index.jsp").forward(request, response);
//                    break;
//                case 4:
//                    documento = Integer.parseInt(request.getParameter("cdocumento").toString());
//
//                    List lst_datos_user = jpacusa.Recuperar_contrasena(documento);
//                    if (lst_datos_user != null) {
//                        request.setAttribute("documento", documento);
//                        request.getRequestDispatcher("index.jsp").forward(request, response);
//                    } else {
//                        request.setAttribute("Alerta", "usuer_not_exis");
//                        request.getRequestDispatcher("index.jsp").forward(request, response);
//                    }
//                    break;
//                case 5:
//                    r_documento = Integer.parseInt(request.getParameter("r_cedula"));
//                    List lst_datos_usuario = jpacusa.Recuperar_contrasena(r_documento);
//                    Object[] obj_d_usuario = (Object[]) lst_datos_usuario.get(0);
//                    if (obj_d_usuario[5].equals(obj_d_usuario[6]) && obj_d_usuario[7].equals("")) {
//                        documento = Integer.parseInt(request.getParameter("cr_documento").toString());
//
//                        primer_apellido = request.getParameter("cr_apellido");
//                        r_primer_apellido = request.getParameter("r_primer_apellido");
//
//                        tiempo_aplicativo = request.getParameter("cr_tiempo_aplicativo");
//                        r_tiempo_aplicativo = request.getParameter("r_tiempo_aplicativo");
//
//                        rol = request.getParameter("cr_rol");
//                        r_rol = request.getParameter("r_rol_cumple");
//                        if (documento == r_documento && primer_apellido.equals(r_primer_apellido) && tiempo_aplicativo.equals(r_tiempo_aplicativo) && rol.equals(r_rol)) {
//                            request.setAttribute("documento_correcto", r_documento);
//                            request.getRequestDispatcher("index.jsp").forward(request, response);
//                        } else {
//                            request.setAttribute("Alerta", "Error_test");
//                            request.getRequestDispatcher("index.jsp").forward(request, response);
//                        }
//                    } else {
//                        documento = Integer.parseInt(request.getParameter("cr_documento").toString());
//
//                        fecha_nacimiento = request.getParameter("cr_fecha_nacimiento");
//                        r_fecha_nacimiento = request.getParameter("r_fecha_nacimiento");
//
//                        numero_hijos = request.getParameter("cr_hijos");
//                        r_numero_hijos = request.getParameter("r_numero_hijos");
//
//                        rol = request.getParameter("cr_rol");
//                        r_rol = request.getParameter("r_rol_cumple");
//                        if (documento == r_documento && fecha_nacimiento.equals(r_fecha_nacimiento) && numero_hijos.equals(r_numero_hijos) && rol.equals(r_rol)) {
//                            request.setAttribute("documento_correcto", r_documento);
//                            request.getRequestDispatcher("index.jsp").forward(request, response);
//                        } else {
//                            request.setAttribute("Alerta", "Error_test");
//                            request.getRequestDispatcher("index.jsp").forward(request, response);
//                        }
//                    }
//                    break;
//                case 6:
//                    contrasena_incriptada = request.getParameter("ccontrasena");
//                    documento = Integer.parseInt(request.getParameter("d_correcto").toString());
//                    contrasena_incriptada = md5.md5(contrasena_incriptada);
//
//                    actualizar_contrasena = jpacusa.actualizar_contrasena(contrasena_incriptada, documento);
////                    actualizar_contrasena = jpacusa.actualizar_contrasena(contrasena, documento);
//                    request.setAttribute("Alerta", "users_desactivado");
//                    request.getRequestDispatcher("index.jsp").forward(request, response);
//                    break;
//                case 7:
//                    contrasena_incriptada = request.getParameter("ccontrasena");
//                    id_usu = Integer.parseInt(request.getParameter("id_usuario").toString());
//                    contrasena_incriptada = md5.md5(contrasena_incriptada);
//                    actualizar_contrasena = jpacusa.actualizar_contrasena_primera_vez(contrasena_incriptada, id_usu);
//                    request.setAttribute("Alerta", "contrasena_exitosa");
//                    request.getRequestDispatcher("index.jsp").forward(request, response);
//                    break;
//</editor-fold>
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
