package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Controladores.RegistroJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controladores.UsuarioJpaController;
import Entidad.Usuario;
import Mail.Control_encriptacion;

public class Registro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String nombre_responsable = sesion.getAttribute("Nombre").toString() + " " + sesion.getAttribute("Apellido").toString();
            response.setContentType("text/html;charset=UTF-8");
            int opc = Integer.parseInt(request.getParameter("opc"));
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            RegistroJpaController jpargt = new RegistroJpaController();
            String usuario_session = "ADMINISTRADOR";
            List lst_registro = null;
            List lst_usuario = null;
            boolean result = false;
            int id_registro = 0, id_orden = 0, id_maquina = 0, id_recipiente = 0, peso = 0, estiba = 0, estado = 0, id_despeje = 0, rol_signature = 0, Obs = 0, IdRolS = 0;
            String fecha = "", lote = "", observacion = "", observacionEncargada = "", lote_completo = "", codigo = "", format = "", Usuario = "", password = "", passwordEncrypt = "", signature = "";
            String ObsFinal = "";
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO REGISTRO">
                    try {
                        id_despeje = Integer.parseInt(request.getParameter("id_despeje"));
                    } catch (Exception e) {
                        id_despeje = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        Obs = Integer.parseInt(request.getParameter("Obs"));
                    } catch (Exception e) {
                        Obs = 0;
                    }
                    try {
                        IdRolS = Integer.parseInt(request.getParameter("IdRolS"));
                    } catch (Exception e) {
                        IdRolS = 0;
                    }
                    request.setAttribute("id_registro", id_registro);
                    request.setAttribute("id_orden", id_orden);
                    request.setAttribute("id_despeje", id_despeje);
                    request.setAttribute("Obs", Obs);
                    request.setAttribute("IdRolS", IdRolS);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR - MODIFICAR REGISTRO">
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_recipiente = Integer.parseInt(request.getParameter("Cbx_recipiente"));
                    fecha = request.getParameter("Txt_fecha");
                    codigo = request.getParameter("Txt_codigo");
                    lote = request.getParameter("Txt_lote");
                    lote_completo = codigo + "-" + lote;
                    estiba = Integer.parseInt(request.getParameter("Txt_estiba"));
                    observacion = request.getParameter("Txt_observacion");
                    if (id_registro > 0) {
                        result = jpargt.ModificarRegistro(id_registro, id_orden, id_recipiente, fecha, lote_completo, estiba, observacion, nombre_responsable);
                        request.setAttribute("Modificar_registro", result);
                    } else {
                        id_maquina = Integer.parseInt(request.getParameter("Cbx_maquina"));
                        result = jpargt.RegistarRegistro(id_orden, id_recipiente, fecha, lote_completo, estiba, id_maquina, observacion, nombre_responsable);
                        request.setAttribute("Registar_registro", result);
                    }
                    request.getRequestDispatcher("Registro?opc=1&id_registro=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIO DE ESTADO REGISTRO">
                    id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    if (estado == 3) {
                        estado = 0;
                    }
                    lst_registro = jpargt.Consultar_estadosxDetalle(id_registro);
                    if (lst_registro != null) {
                        Object[] obj_reg = (Object[]) lst_registro.get(0);
                        if (obj_reg[1] == null || obj_reg[1].toString().equals("CERRADO")) {
                            if (estado == 1) {
                                estado = 0;
                                result = jpargt.CambiarEstado(id_registro, estado);
                                request.setAttribute("Cambiar_estado_registro", result);
                            } else {
                                estado = 1;
                                if (obj_reg[5].toString().equals("ABIERTO")) {
                                    result = jpargt.CambiarEstado(id_registro, estado);
                                    request.setAttribute("Cambiar_estado_registro", result);
                                } else {
                                    request.setAttribute("ValidacionCambiarEstadoOrdenAbierta", true);
                                }
                            }
                        } else {
                            request.setAttribute("registros_abiertos_Detalle", true);
                        }
                    } else {
                        result = false;
                        request.setAttribute("ErrorCambiarEstado_r", true);
                    }
                    request.getRequestDispatcher("Registro?opc=1&id_registro=0&id_orden=" + id_orden + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DESPEJE">
                    id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));

                    lst_registro = jpargt.ConsultarPlantillaDespeje();
                    if (lst_registro != null) {
                        Object[] ObjPlantilla = (Object[]) lst_registro.get(0);
                        format = ObjPlantilla[3].toString();
                    } else {
                        format = "Sin formato";
                    }
                    result = jpargt.RegistroDespeje(id_registro, format, nombre_responsable);
                    if (result) {
                        jpargt.ActaulizarRegistro(id_registro);
                    }
                    request.setAttribute("RegistroDespeje", result);
                    request.getRequestDispatcher("Registro?opc=1&id_registro=0&id_orden=" + id_orden + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="USUARIO DESPEJE">
                    try {
                        id_despeje = Integer.parseInt(request.getParameter("id_despeje"));
                    } catch (Exception e) {
                        id_despeje = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }

                    Usuario = request.getParameter("Txt_user");
                    password = request.getParameter("Txt_password");
                    if (password.length() >= 8) {
                        passwordEncrypt = md5.md5(password);
                        lst_usuario = jpa_usuario.UsuarioSesion(Usuario, passwordEncrypt);
                        if (lst_usuario == null) {
                            lst_usuario = jpa_usuario.UsuarioSesion(Usuario, password);
                        }
                    } else {
                        lst_usuario = jpa_usuario.UsuarioSesion(Usuario, password);
                    }
                    if (lst_usuario != null) {
                        Object[] obj_user = (Object[]) lst_usuario.get(0);
                        request.setAttribute("idUsuario", obj_user[0]);
                        request.setAttribute("Nombres", obj_user[1]);
                        request.setAttribute("NombreRol", obj_user[8]);
                        request.setAttribute("idRol", obj_user[6]);
                    }
                    request.getRequestDispatcher("Registro?opc=1&id_registro=" + id_registro + "&id_orden=" + id_orden + "&id_despeje=" + id_despeje + "").forward(request, response);
//</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="FIRMAS DESPEJE">
                    try {
                        id_despeje = Integer.parseInt(request.getParameter("id_despeje"));
                    } catch (Exception e) {
                        id_despeje = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        signature = request.getParameter("signature");
                    } catch (Exception e) {
                        signature = "";
                    }
                    try {
                        rol_signature = Integer.parseInt(request.getParameter("rol_signature"));
                    } catch (Exception e) {
                        rol_signature = 0;
                    }
                    format = request.getParameter("Txt_template");
                    if (!signature.equals("")) {
                        if (rol_signature == 3) {
                            format = format.replace("XXXCOORDINADORAXXX", signature);
                            result = true;
                            request.setAttribute("Signature_responsible", result);
                        } else if (rol_signature == 4) {
                            format = format.replace("XXXENCARGADAXXX", signature);
                            request.setAttribute("Signature_responsible", result);
                        } else if (rol_signature == 5) {
                            format = format.replace("XXXINSPECTORA_CALIDADXXX", signature);
                            request.setAttribute("Signature_responsible", result);
                        } else if (rol_signature == 6) {
                            format = format.replace("XXXMANTENIMIENTOXXX", signature);
                            request.setAttribute("Signature_responsible", result);
                        } else if (rol_signature == 7) {
                            format = format.replace("XXXCOORDINADORA_CALIDADXXX", signature);
                            request.setAttribute("Signature_responsible", result);
                        } else {
                            result = true;
                            request.setAttribute("Signature_not_permissions", result);
                        }
                    }
                    format = format.replace("'", "\"");
                    result = jpargt.ActualizarDespeje(id_despeje, format);
                    request.setAttribute("idUsuario", request.getParameter("idUsuario"));
                    request.setAttribute("Nombres", request.getParameter("Nombres"));
                    request.setAttribute("NombreRol", request.getParameter("NombreRol"));
                    request.setAttribute("idRol", request.getParameter("idRol"));
                    request.setAttribute("id_registro", id_registro);
                    request.setAttribute("id_orden", id_orden);
                    request.setAttribute("id_despeje", id_despeje);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="LIBERAR DESPEJE">
                    try {
                        id_despeje = Integer.parseInt(request.getParameter("id_despeje"));
                    } catch (Exception e) {
                        id_despeje = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    estado = Integer.parseInt(request.getParameter("est"));
                    if (estado == 0) {
                        estado = 1;
                    } else {
                        estado = 0;
                    }
                    result = jpargt.ActualizarEstadoDespeje(id_despeje, estado);
                    request.getRequestDispatcher("Registro?opc=1&id_registro=" + id_registro + "&id_orden=" + id_orden + "&id_despeje=" + id_despeje + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="GUARDAR DESPEJE">
                    try {
                        id_despeje = Integer.parseInt(request.getParameter("id_despeje"));
                    } catch (Exception e) {
                        id_despeje = 0;
                    }
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_registro = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        IdRolS = Integer.parseInt(request.getParameter("IdRolS"));
                    } catch (Exception e) {
                        IdRolS = 0;
                    }
                    format = request.getParameter("Txt_template");
                    format = format.replace("style=\"background: #f9a6a6bf;\"", "");
                    format = format.replace("'", "\"");
                    result = jpargt.ActualizarDespeje(id_despeje, format);
                    request.setAttribute("TemplateSave", result);
                    request.setAttribute("idUsuario", request.getParameter("idUsuario"));
                    request.setAttribute("Nombres", request.getParameter("Nombres"));
                    request.setAttribute("NombreRol", request.getParameter("NombreRol"));
                    request.setAttribute("idRol", request.getParameter("idRol"));
                    request.setAttribute("id_registro", id_registro);
                    request.setAttribute("id_orden", id_orden);
                    request.setAttribute("id_despeje", id_despeje);
                    request.getRequestDispatcher("Registro.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="GUARDAR OBSERVACION ENCARGADA">
                    try {
                        id_registro = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (NumberFormatException e) {
                        id_registro = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (NumberFormatException e) {
                        id_orden = 0;
                    }
                    observacion = request.getParameter("Txt_observacion");
                    observacionEncargada = request.getParameter("observacionEncargada");
                    ObsFinal = observacion + "- Encargada: " + observacionEncargada;
                    result = jpargt.ActualizarObservacionEncargada(id_registro, ObsFinal);
                    request.setAttribute("RegistroDespeje", result);
                    request.getRequestDispatcher("Registro?opc=1&id_registro=0").forward(request, response);
                    //</editor-fold>
                    break;

            }
        } catch (Exception ex) {
            request.getRequestDispatcher("Registro.jsp").forward(request, response);
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
