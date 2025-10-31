package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.RegistroDetalleJpaController;
import Controladores.RegistroJpaController;
import java.util.List;
import java.util.Calendar;
import Mail.Control_encriptacion;
import Controladores.UsuarioJpaController;

public class Registro_detalle extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        try {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String nombre_responsable = sesion.getAttribute("Nombre").toString() + " " + sesion.getAttribute("Apellido").toString();
            String NombreRol = sesion.getAttribute("NombreRol").toString();
            int opc = Integer.parseInt(request.getParameter("opc"));
            Calendar cal = Calendar.getInstance();
            int id_usuario = (Integer) sesion.getAttribute("idRol");
            boolean result = false;
            RegistroDetalleJpaController RegistroDetalleJpa = new RegistroDetalleJpaController();
            RegistroJpaController RegistrosJpa = new RegistroJpaController();
            Control_encriptacion md5 = new Control_encriptacion();
            UsuarioJpaController jpa_usuario = new UsuarioJpaController();
            List lst_regDetalle = null;
            List lst_usuario = null;
            String tiempo = "";
            int fecha = cal.get(Calendar.DATE), valid = 0;
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            int id_reg = 0, id_dell = 0, rdll = 0, id_orden = 0, id_hora = 0, edit = 0, time = 0, obs = 0, est = 0, temp = 0, TotalAprob = 0, tipoLim = 0, control = 0;
            String txt_turno = "", txt_grupo = "", defecto = "", defecto_t = "", tiempo_t = "", observacion = "0", Usuario = "", password = "", passwordEncrypt = "";
            String txt_motivo = "", txt_justificacion = "", txt_observacion = "", txt_observ = "", Turno = "", BaseC = "", BaseP = "", PistonC = "", PistonP = "",
                    DefecCuare = "", NumCuarent = "";
            int bascula = 0, id_cuarentena = 0, id_control = 0, UndCuarent = 0, id_cuarentenaID = 0, event = 0, idUser = 0, tempH = 0, limpieza = 0, validacion = 0;
            double tara = 0, peso = 0, peso_meta = 0, peso_acumulado = 0, peso_t = 0, peso_r = 0;
            String[] txt_personal = {};
            //</editor-fold>
            List lst_registro = null;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE REGISTRO DETALLE">
                    //<editor-fold defaultstate="collapsed" desc="VARIABLES">

                    try {
                        id_reg = Integer.parseInt(request.getParameter("id_registro"));
                    } catch (Exception e) {
                        id_reg = 0;
                    }
                    try {
                        id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    } catch (Exception e) {
                        id_dell = 0;
                    }
                    try {
                        rdll = Integer.parseInt(request.getParameter("rdll"));
                    } catch (Exception e) {
                        rdll = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        id_hora = Integer.parseInt(request.getParameter("id_hora"));
                    } catch (Exception e) {
                        id_hora = 0;
                    }
                    try {
                        tiempo = request.getParameter("tiempo");
                    } catch (Exception e) {
                        tiempo = "0";
                    }
                    try {
                        defecto = request.getParameter("defecto");
                    } catch (Exception e) {
                        defecto = "0";
                    }
                    try {
                        observacion = request.getParameter("observacion");
                    } catch (Exception e) {
                        observacion = "0";
                    }

                    try {
                        est = Integer.parseInt(request.getParameter("est"));
                    } catch (Exception e) {
                        est = 0;
                    }
                    try {
                        id_cuarentenaID = Integer.parseInt(request.getParameter("id_cuarentena"));
                    } catch (Exception e) {
                        id_cuarentenaID = 0;
                    }
                    try {
                        id_control = Integer.parseInt(request.getParameter("id_contrl"));
                    } catch (Exception e) {
                        id_control = 0;
                    }

                    try {
                        temp = Integer.parseInt(request.getParameter("temp"));
                    } catch (Exception e) {
                        temp = 0;
                    }
                    try {
                        valid = Integer.parseInt(request.getParameter("validhor"));
                    } catch (Exception e) {
                        valid = 0;
                    }
                    try {
                        tempH = Integer.parseInt(request.getParameter("tempH"));
                    } catch (Exception e) {
                        tempH = 0;
                    }
                    try {
                        limpieza = Integer.parseInt(request.getParameter("limpieza"));
                    } catch (Exception e) {
                        limpieza = 0;
                    }
//</editor-fold>

                    if (est == 0 && temp == 1) {
                        result = RegistroDetalleJpa.Cambiar_estado_detalle(id_dell, est);
                        RegistroDetalleJpa.Liberar_basculas(id_dell);
                        id_dell = 0;
                        request.setAttribute("Cerrar_registroDetalle", result);
                    } else if (est == 1 && temp == 1) {
                        if (NombreRol.equals("Administrador") || NombreRol.equals("Coordinadora") || NombreRol.equals("Encargada")) {
                            lst_registro = RegistrosJpa.Consultar_estadosxDetalle(id_reg);
                            if (lst_registro != null) {
                                Object[] obj_registro = (Object[]) lst_registro.get(0);
                                if (obj_registro[3].toString().equals("ABIERTO")) {
                                    result = RegistroDetalleJpa.Cambiar_estado_detalle(id_dell, est);
                                    int id_bas = Integer.parseInt(request.getParameter("Cbx_bascula"));
                                    RegistroDetalleJpa.Seleccion_Basculas(id_dell, id_bas);
                                    id_dell = 0;
                                    request.setAttribute("Abir_registroDetalle", result);
                                } else {
                                    id_dell = 0;
                                    request.setAttribute("ValidacionRegistroAbiertoDellCerrado", true);
                                }
                            }
                        } else {
                            request.setAttribute("NoHayPermiso_estado", true);
                        }
                    } else if (valid > 0) {
                        RegistroDetalleJpa.Registrar_Horas_Iniciales(id_dell, id_hora);
                        request.setAttribute("InicioDespeje", true);
                        id_dell = 0;
                        id_hora = 0;
                    }

                    request.setAttribute("id_orden", id_orden);
                    request.setAttribute("id_regDetalle", id_dell);
                    request.setAttribute("id_regDetalle", id_dell);
                    request.setAttribute("id_registro", id_reg);
                    request.setAttribute("id_temp", rdll);
                    request.setAttribute("tiempo", tiempo);
                    request.setAttribute("defecto", defecto);
                    request.setAttribute("tempH", tempH);
                    request.setAttribute("observacion", observacion);
                    request.setAttribute("id_cuarentena", id_cuarentenaID);
                    request.setAttribute("id_control", id_control);
                    request.setAttribute("limpieza", limpieza);
//                    request.setAttribute("Conf_regHora", id_hora);
                    request.setAttribute("id_hora", id_hora);

                    request.getRequestDispatcher("RegistroDetalle.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR DETALLE">
                    try {
                        id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    } catch (Exception e) {
                        id_dell = 0;
                    }
                    try {
                        id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    } catch (Exception e) {
                        id_reg = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        txt_turno = request.getParameter("Cbx_turno");
                    } catch (Exception e) {
                        txt_turno = "";
                    }
                    try {
                        txt_personal = request.getParameterValues("Txt_filtro_avanzado");
                    } catch (Exception e) {
//                        txt_personal = {};
                    }
                    try {
                        bascula = Integer.parseInt(request.getParameter("Cbx_bascula"));
                    } catch (Exception e) {
                        bascula = 0;
                    }
                    if (!txt_personal.equals("")) {
                        if (id_dell == 0) {
                            if (txt_personal.toString().contains("[")) {
                                for (int i = 0; i < txt_personal.length; i++) {
                                    String Arg_personal = txt_personal[i].toString().replace("[", "").replace("]", "");
                                    result = RegistroDetalleJpa.registar_regDetalle(id_reg, txt_turno, Arg_personal, nombre_responsable, bascula);
                                }
                            }
                            request.setAttribute("RegistrarDetalle", result);
                        } else {
                            String Arg_personal = txt_personal[0].toString().replace("[", "").replace("]", "");
                            result = RegistroDetalleJpa.ModificarRegistroDetalle(id_dell, txt_turno, Arg_personal, bascula);
                            request.setAttribute("ActualizarDetalle", result);
                        }
                    } else {
                        request.setAttribute("SinUsuarios", true);
                    }
                    request.setAttribute("id_regDetalle", id_dell);
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR PESO">
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    id_hora = Integer.parseInt(request.getParameter("id_hora"));
                    try {
                        peso = Double.parseDouble(request.getParameter("peso"));
                    } catch (Exception e) {
                        peso = 0;
                    }
                    try {
                        tara = Double.parseDouble(request.getParameter("tara"));
                    } catch (Exception e) {
                        tara = 0;
                    }
                    if (peso > tara && tara > 0) {
                        result = RegistroDetalleJpa.ActualizarPeso(id_dell, id_hora, (double) tara);

                    } else {
                        result = RegistroDetalleJpa.ActualizarPeso(id_dell, id_hora, (double) peso);
                    }
                    RegistroDetalleJpa.Registrar_Horas_Finales(id_dell, id_hora);
                    if (result = true) {
                        if (id_hora == 8) {
                            RegistroDetalleJpa.ActualizarEstado(id_dell);
                        }
                    }
                    if (peso == 0) {
                        request.setAttribute("NoHayPeso", true);
                    } else {
                        request.setAttribute("ActualizarHora", result);
                    }
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR TIEMPO">
                    id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    tiempo = request.getParameter("txt_tiempo");
                    try {
                        time = Integer.parseInt(request.getParameter("time"));
                    } catch (Exception e) {
                        time = 0;
                    }
                    if (!tiempo.equals("")) {
                        tiempo_t = request.getParameter("txt_tiempot");
                        String[] arr = tiempo.replace("][", "]-[").split("-");
                        String[] arr2 = tiempo_t.replace("][", "]-[").split("-");
                        for (int i = 0; i < arr.length; i++) {
                            String[] arr3 = arr[i].replace("/", "-/").split("-");
                            for (int j = 0; j < arr2.length; j++) {
                                if (arr2[j].contains(arr3[1])) {
                                    tiempo_t = tiempo_t.replace(arr2[j], arr[i]);
                                    j = arr2.length;
                                }
                            }
                        }
                        result = RegistroDetalleJpa.Registrar_tiempo(id_dell, tiempo_t);
                        if (time == 1) {
                            request.setAttribute("EditarTiempo", result);
                        } else {
                            request.setAttribute("ActualizarTiempo", result);
                        }
                    } else if (time == 1) {
                        request.setAttribute("EditarTiempo", false);
                    } else {
                        request.setAttribute("ActualizarTiempo", false);
                    }

                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR DEFECTOS">
                    id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_cuarentena = Integer.parseInt(request.getParameter("id_cuarent"));
                    defecto = request.getParameter("txt_defecto");
                    try {
                        edit = Integer.parseInt(request.getParameter("edit"));
                    } catch (Exception e) {
                        edit = 0;
                    }
                    if (!defecto.equals("")) {
                        defecto_t = request.getParameter("txt_defectot");
                        String[] arr = defecto.replace("][", "]-[").split("-");
                        String[] arr2 = defecto_t.replace("][", "]-[").split("-");
                        for (int i = 0; i < arr.length; i++) {
                            String[] arr3 = arr[i].replace("/", "-/").split("-");
                            for (int j = 0; j < arr2.length; j++) {
                                if (arr2[j].contains(arr3[1])) {
                                    defecto_t = defecto_t.replace(arr2[j], arr[i]);
                                    j = arr2.length;
                                }
                            }
                        }
                        result = RegistroDetalleJpa.Registrar_defectoDetalle(id_cuarentena, defecto_t);
                        if (edit == 1) {
                            request.setAttribute("Reg_EditarDefecto", result);
                        } else {
                            request.setAttribute("RegistroDefecto", result);
                        }
                    } else if (edit == 1) {
                        request.setAttribute("EditarDefecto", false);
                    } else {
                        request.setAttribute("RegistroDefecto", false);
                    }
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&defecto=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR Y MODIFICAR OBSERVACIONES">
                    id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_hora = Integer.parseInt(request.getParameter("cbx_hora"));
                    txt_motivo = request.getParameter("txt_motivo");
                    txt_justificacion = request.getParameter("txt_justifi");
                    txt_observ = request.getParameter("txt_comple");
                    if (!txt_observ.equals("null")) {
                        txt_observ = txt_observ.replace("]-[", "]--[").replace("]----", "]--");
                    } else {
                        txt_observ = "";
                    }
                    try {
                        obs = Integer.parseInt(request.getParameter("obs"));
                    } catch (Exception e) {
                        obs = 0;
                    }
                    txt_observacion = "[" + id_hora + "][" + txt_motivo + "][" + txt_justificacion + "]--";
                    txt_observacion = txt_observacion + txt_observ;
                    txt_observacion = txt_observacion.replace("]-[", "]--[").replace("----", "--");
                    txt_observacion = txt_observacion.replace("Ã", "ó")
                            .replace("Ã\u009A", "ú").replace("Ã\u0093", "ó")
                            .replace("Ã\u008D", "í").replace("Ã\u0089", "é")
                            .replace("Ã\u0081", "á").replace("Ã³", "ó");

                    result = RegistroDetalleJpa.Registrar_obsrvaciones(id_dell, txt_observacion);
                    if (obs == 1) {
                        request.setAttribute("EditarObservacion", result);
                    } else {
                        request.setAttribute("RegistrarObservacion", result);
                    }
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE PESO CUANDO ES IGUAL A PESO TOTAL">
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    peso_meta = Double.parseDouble(request.getParameter("peso_meta"));
                    peso_acumulado = Double.parseDouble(request.getParameter("peso_a"));
                    id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    id_hora = Integer.parseInt(request.getParameter("id_hora"));
                    peso = Double.parseDouble(request.getParameter("peso"));
                    tara = Double.parseDouble(request.getParameter("tara"));

                    peso_t = peso_meta - peso_acumulado;
                    peso_r = peso - peso_t;

                    result = RegistroDetalleJpa.ActualizarPeso(id_dell, id_hora, (int) peso_t);
                    RegistroDetalleJpa.Registrar_Horas_Finales(id_dell, id_hora);
                    boolean result_2 = RegistroDetalleJpa.Cambiar_Estados_Masivo(id_orden);

                    request.setAttribute("peso_r", peso_r);
                    request.setAttribute("peso_t", peso_t);
                    request.setAttribute("validacionTaraPeso", result);
                    request.setAttribute("CambioMasivoEstados", result_2);
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0").forward(request, response);

                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO CONTROL CUARENTENA">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    Turno = request.getParameter("txtTurno");

                    BaseC = request.getParameter("txtBaseC");
                    BaseP = request.getParameter("txtBaseP");
                    PistonC = request.getParameter("txtPistonC");
                    PistonP = request.getParameter("txtPistonP");

                    String LteBase = "[" + BaseC + "/" + BaseP + "]";
                    String LtePiston = "[" + PistonC + "/" + PistonP + "]";

                    result = RegistroDetalleJpa.RegistrarControlesCuarentenas(id_reg, Turno, LteBase, LtePiston);
                    request.setAttribute("RegistroControl", result);
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0&defecto=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DE CUARENTENAS">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    id_control = Integer.parseInt(request.getParameter("id_contrl"));
                    NumCuarent = request.getParameter("NumCuarent");
                    UndCuarent = Integer.parseInt(request.getParameter("UndCuarent"));
                    defecto = request.getParameter("txtDefectos");
                    result = RegistroDetalleJpa.Registrar_CabeceraCuarentenas(id_control, NumCuarent, UndCuarent, defecto, nombre_responsable);
                    request.setAttribute("RegistrarCuarentena", result);
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0&defecto=1").forward(request, response);
//</editor-fold>
                    break;
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIO USUARIO">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_reg = Integer.parseInt(request.getParameter("id_registro"));
                    id_control = Integer.parseInt(request.getParameter("id_contrl"));
                    id_cuarentena = Integer.parseInt(request.getParameter("id_cuarent"));

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
                        request.setAttribute("idUsuarioChng", obj_user[0]);
                        request.setAttribute("NombresChng", obj_user[1]);
                        request.setAttribute("NombreRolChng", obj_user[8]);
                        request.setAttribute("idRolChng", obj_user[6]);
                    }
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0&defecto=0&id_cuarentena=" + id_cuarentena + "&id_contrl=" + id_control + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="FIRMAR CUARENTENA">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_reg = Integer.parseInt(request.getParameter("id_registro"));
                    id_control = Integer.parseInt(request.getParameter("id_contrl"));
                    id_cuarentena = Integer.parseInt(request.getParameter("id_cuarent"));
                    event = Integer.parseInt(request.getParameter("event"));
                    idUser = Integer.parseInt(request.getParameter("idUser"));

                    result = RegistroDetalleJpa.FirmarCuarentena(id_control, id_cuarentena, event, idUser);
                    request.setAttribute("FirmarCuarentena", result);
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "&id_hora=0&defecto=0&id_cuarentena=" + id_cuarentena + "&id_contrl=" + id_control + "").forward(request, response);
                    //</editor-fold>
                    break;
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="CIERRE DE CUARENTENA">
                    id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    id_reg = Integer.parseInt(request.getParameter("id_registro"));
                    id_control = Integer.parseInt(request.getParameter("id_contrl"));
                    id_cuarentena = Integer.parseInt(request.getParameter("id_cuarent"));
                    TotalAprob = Integer.parseInt(request.getParameter("totalAprob"));
                    DefecCuare = request.getParameter("defectCuaren");

                    result = RegistroDetalleJpa.CerrarCuarentenas(id_cuarentena, DefecCuare, TotalAprob);
                    request.setAttribute("CierreCuarentena", result);
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_registro=" + id_reg + "&id_orden=" + id_orden + "&defecto=1").forward(request, response);
                    //</editor-fold>
                    break;
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="LIMPIEZA PESO">
                    try {
                        id_dell = Integer.parseInt(request.getParameter("id_dell"));
                    } catch (Exception e) {
                        id_dell = 0;
                    }
                    try {
                        id_reg = Integer.parseInt(request.getParameter("id_reg"));
                    } catch (Exception e) {
                        id_reg = 0;
                    }
                    try {
                        id_orden = Integer.parseInt(request.getParameter("id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    tipoLim = Integer.parseInt(request.getParameter("tipoLim"));
                    control = Integer.parseInt(request.getParameter("Cbx_limpieza"));
                    if (tipoLim == 1) {
                        result = RegistroDetalleJpa.LimpiarPesoHora(id_dell, control);
                        request.setAttribute("ActualizarDetalle", result);
                    } else {
                        result = RegistroDetalleJpa.LimpiarPesoHoraGeneral(id_dell, control);
                        request.setAttribute("ActualizarDetalle", result);
                    }
                    request.getRequestDispatcher("Registro_detalle?opc=1&id_dell=0&id_registro=" + id_reg + "&id_orden=" + id_orden + "").forward(request, response);
                    //</editor-fold>
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
