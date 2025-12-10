package Servlets;

import Metodos.Control_encriptacion;
import Controladores_BD.MenuJpaController;
import Controladores_BD.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Usuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesion = request.getSession();
            String usuario_registro = sesion.getAttribute("Nombre_apellido").toString();
            int id_usuarios_sesion = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
            int opc = Integer.parseInt(request.getParameter("opc"));
            int mnu = 0;
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            MenuJpaController jpacmnu = new MenuJpaController();
            Calendar cal = Calendar.getInstance();
            //Variables globales
            int year = cal.get(Calendar.YEAR);
            boolean proceso = true;
            int cont_proceso = 0;
            int id_rol = 0;
            int id_area = 0;
            int consulta = 0;
            int id_usuario = 0;
            // variables de registro
            String nombres = "";
            String apellidos = "";
            int documento = 0;
            String nombre_usuario = "";
            String password = "";
            String password_encript = "";
            String firma = "";
            String correo = "";
            List lst_sesion = null;
            String color = "default.css";
            String permisos = "";
            String permisos_detallados = "";
            int tipo_estado = 0;
            switch (opc) {
                //Registrar y modificar usuarios
                case 1:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Usuario", "Registrar_usuario");
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    request.setAttribute("Id_usuario", id_usuario);
                    request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    break;
                //Cuenta
                case 2:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Usuario", "Cuenta");
                    request.setAttribute("Id_usuario", id_usuarios_sesion);
                    request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    break;
                //Consultar usuarios
                case 3:
                    mnu = Integer.parseInt(request.getParameter("mnu"));
                    request.setAttribute("Permisos", mnu);
                    request.setAttribute("Usuario", "Consultar_usuarios");
                    request.getRequestDispatcher("Usuarios.jsp").forward(request, response);
                    break;
                //Registro de usuarios con privilegios
                case 4:
                    try {
                        id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    } catch (Exception e) {
                        id_usuario = 0;
                    }
                    nombres = request.getParameter("Txt_nombres");
                    apellidos = request.getParameter("Txt_apellidos");
                    documento = Integer.parseInt(request.getParameter("Txt_documento"));
                    nombre_usuario = request.getParameter("Txt_usuario");
                    firma = request.getParameter("Txt_firma");
                    correo = request.getParameter("Txt_correo");
                    id_area = Integer.parseInt(request.getParameter("Cbx_area"));
                    consulta = Integer.parseInt(request.getParameter("Rdb_personal"));
                    permisos = request.getParameter("Txt_seleccion_permisos");
                    permisos_detallados = request.getParameter("Txt_seleccion_permisos_detallados");
                    id_rol = Integer.parseInt(request.getParameter("Cbx_rol"));
                    if (id_usuario > 0) {
                        proceso = jpacusa.Modificar_usuario(id_usuario, nombres, apellidos, documento, nombre_usuario, firma, correo, id_rol, usuario_registro, id_area, consulta);
                        cont_proceso = 2;
                    } else {
                        proceso = jpacusa.Registrar_usuario(nombres, apellidos, documento, nombre_usuario, firma, correo, id_rol, color, usuario_registro, id_area, consulta);
                        cont_proceso = 1;
                    }
                    if (!proceso) {
                        request.setAttribute("Alerta", ((cont_proceso == 1) ? "Error_usuario" : "Error_usuario_modificar"));
                    } else {
                        if (id_usuario == 0) {
                            lst_sesion = jpacusa.Iniciar_sesion(nombre_usuario, year + "");
                            Object[] obj_sesion = (Object[]) lst_sesion.get(0);
                            id_usuario = Integer.parseInt(obj_sesion[0].toString());
                        }
                        jpacmnu.Eliminar_privilegios(id_usuario);
                        //Se obtienen los datos de la sesion
                        if (id_usuario > 1 && permisos.length() > 0) {
                            String[] arg_permisos = permisos.replace("][", "-").replace("[", "").replace("]", "").split("-");
//                            String[] arg_permisos = permisos.replace("]", "-").split("-");
                            //Se asigna el usuario
                            for (int i = 0; i < arg_permisos.length; i++) {
                                jpacmnu.Registrar_privilegios(Integer.parseInt(arg_permisos[i]), id_usuario);
                                if (permisos_detallados.contains("[" + Integer.parseInt(arg_permisos[i]) + "/")) {
                                    String[] arg_permisos_detallados = permisos_detallados.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                    String permisos_opcion = "";
                                    for (int j = 0; j < arg_permisos_detallados.length; j++) {
                                        if (arg_permisos_detallados[j].contains(arg_permisos[i] + "/")) {
                                            if ("".equals(permisos_opcion)) {
                                                permisos_opcion = arg_permisos_detallados[j].split("/")[1];
                                            } else {
                                                permisos_opcion = permisos_opcion + "-" + arg_permisos_detallados[j].split("/")[1];
                                            }
                                        }
                                    }
                                    jpacmnu.Registrar_privilegios_detallados(Integer.parseInt(arg_permisos[i]), id_usuario, permisos_opcion);
                                }
                            }
                            request.setAttribute("Alerta", ((cont_proceso == 1) ? "Registro_usuario" : "Modificar_usuario"));
                            request.setAttribute("var1", nombres + " " + apellidos);
                        } else {
                            request.setAttribute("Alerta", ((cont_proceso == 1) ? "Registro_usuario_sin" : "Modificar_usuario_sin"));
                            request.setAttribute("var1", nombres + " " + apellidos);
                        }
                    }
                    if (id_usuarios_sesion == id_usuario) {
                        sesion.removeAttribute("Id_areaS");
                        sesion.setAttribute("Id_areaS", id_area);
                    }
                    if (cont_proceso == 1) {
                        request.getRequestDispatcher("Usuario?opc=1&mnu=6").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Usuario?opc=3&mnu=7").forward(request, response);
                    }
                    break;
                case 5:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    tipo_estado = Integer.parseInt(request.getParameter("Estado"));
                    if (tipo_estado == 1) {
                        jpacusa.Activar_usuario(id_usuario);
                    } else {
                        jpacusa.Desactivar_usuario(id_usuario);
                    }
                    request.getRequestDispatcher("Usuario?opc=3&mnu=7").forward(request, response);
                    break;
                case 6:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    nombre_usuario = request.getParameter("Txt_usuario");
                    password = request.getParameter("Txt_password");
                    password_encript = md5.md5(password);
                    jpacusa.Cambiar_user_password_id(id_usuario, nombre_usuario, password_encript);
                    request.setAttribute("Alerta", "Password_restablecido");  // alerta de exito
                    request.getRequestDispatcher("Inicio.jsp").forward(request, response);
                    break;
                case 7:
                    id_usuario = Integer.parseInt(request.getParameter("Id_usuario"));
                    jpacusa.Restablecer_password(id_usuario);
                    request.setAttribute("Alerta", "Password_actualizado_year");  // alerta de exito
                    request.getRequestDispatcher("Usuario?opc=3&mnu=7").forward(request, response);
                    break;
            }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
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
