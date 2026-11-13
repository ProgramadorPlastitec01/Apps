package Servlets;

import Controladores.OrdenTrabajoDetalleJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.UsuarioJpaController;
import Metodos.Connection_mysql_sirh;
import Metodos.Control_correos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Orden", urlPatterns = {"/Orden"})
public class Orden extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        try (PrintWriter out = response.getWriter()) {
            //Sesion
            HttpSession sesion = request.getSession();
            String rol_usuario = sesion.getAttribute("Rol/Nombres").toString();
            String nombre_rol = sesion.getAttribute("Nombre_rol").toString();
            String nombre_usuario = sesion.getAttribute("Nombres").toString();
            //JPAS
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            OrdenTrabajoDetalleJpaController jpacotd = new OrdenTrabajoDetalleJpaController();
            UsuarioJpaController jpausu = new UsuarioJpaController();
            Control_correos mtdccr = new Control_correos();
            Connection_mysql_sirh mtdcms = new Connection_mysql_sirh();
            //Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            boolean proceso = true;
            String tipo = "";
            String arg_actividades = "";
            String arg_actividades_id = "";
            String arg_actividades_id_pendientes = "";
            String arg_grupos = "";
            String justificacion = "";
            String arg_id_lineas_zonas = "";
            String fecha_prog = "";
            int firma_mtf = 0;
            int firma_prf = 0;
            int cont_actividades_programadas = 0;
            int cont_actividades_ejecutadas = 0;
            int id_orden = 0;
            int id_origen = 0;
            int id_orden_detalle = 0;
            int id_equipo = 0;
            int estado_ot = 0;
            int id_tipo_mtto = 0;
            int tiempo_trabajo = 0;
            int id_zona = 0;
            int documento = 0;
            int tipo_firma = 0;
            int id_general = 0;
            int estado = 0;
            int tipo_mtto = 0;
            int verificacion = 0;
            List lst_empleado = null;
            List lst_usuario = null;
            String usuario_programador = "", usuario_ejecutor = "", usuario_revisor = "", usuario_recibe_pf = "", usuario_recibe_gc = "", usuario_aprobador = "";
            String pendientes = "", descripcion = "", repuestos = "";
            String no_pmp = "", fecha_realizado = "";
            int numero_ot = 0;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDER TABAJO">
                    tipo = "Orden_equipo";
                    request.setAttribute("Orden", tipo);
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    try {
                        id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    request.setAttribute("Id_equipo", id_equipo);
                    request.setAttribute("Id_orden", id_orden);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR Y REGISTRAR ORDEN TRABAJO">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    numero_ot = Integer.parseInt(request.getParameter("Txt_numero_ot"));
                    usuario_programador = request.getParameter("Txt_programador");
                    usuario_ejecutor = request.getParameter("Cbx_ejecutor");
                    usuario_revisor = request.getParameter("Cbx_revisor");
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Cbx_tipo_mtto"));
                    try {
                        usuario_aprobador = request.getParameter("Cbx_aprobador");
                    } catch (Exception e) {
                    }
                    if (!Es_rol_programador(nombre_rol)) {
                        request.setAttribute("Alerta", "Sin_permiso_rol");
                    } else if (id_orden > 0) {
                        if (usuario_aprobador != null) {
                            proceso = jpacotb.Modificar_orden_trabajo(id_orden, usuario_programador, usuario_ejecutor, "N/A", usuario_aprobador, rol_usuario, id_tipo_mtto);
                        } else {
                            proceso = jpacotb.Modificar_orden_trabajo(id_orden, usuario_programador, usuario_ejecutor, usuario_revisor, "N/A", rol_usuario, id_tipo_mtto);
                        }
                    } else if (usuario_aprobador != null) {
                        proceso = jpacotb.Registrar_orden_trabajo(numero_ot, id_tipo_mtto, id_equipo, usuario_programador, usuario_ejecutor, usuario_revisor, usuario_aprobador, 1, rol_usuario);
                    } else {
                        proceso = jpacotb.Registrar_orden_trabajo(numero_ot, id_tipo_mtto, id_equipo, usuario_programador, usuario_ejecutor, usuario_revisor, "N/A", 1, rol_usuario);
                    }
                    if (!Es_rol_programador(nombre_rol)) {
                        // Alerta Sin_permiso_rol ya asignada
                    } else if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_orden == 0) ? "Registro" : "Modificar") + "_orden");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_orden == 0) ? "registro" : "modificar") + "_orden");
                    }
                    request.setAttribute("var1", numero_ot);
                    request.getRequestDispatcher("Orden?opc=1&Id_equipo=" + id_equipo + "&Id_orden=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDEN EQUIPO TRABAJO EMISIÓN">
                    tipo = "Orden_equipo_trabajo_emision";
                    request.setAttribute("Orden", tipo);
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    request.setAttribute("Id_orden", id_orden);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA DETALLE Y CAMBIAR ESTADO">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    estado_ot = Integer.parseInt(request.getParameter("Estado_ot"));
                    if (estado_ot == 1) {
                        if (Es_rol_programador(nombre_rol)) {
                            arg_grupos = request.getParameter("Arg_grupos");
                            arg_actividades_id = request.getParameter("Arg_id_actividades");
                            arg_actividades_id_pendientes = request.getParameter("Arg_id_actividades_pendientes");
                            arg_actividades = request.getParameter("Arg_actividades");
                            cont_actividades_programadas = Integer.parseInt(request.getParameter("Cont_actividades_programadas"));
                            arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                            jpacotd.Registrar_detalle_orden_trabajo(id_orden, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, 0, "", "", "", "N/A", "", arg_actividades_id_pendientes, arg_id_lineas_zonas);
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                            mtdccr.Correo_ot(id_orden, "PROGRAMACIÓN OT", rol_usuario);
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_rol");
                        }
                        request.getRequestDispatcher("Orden?opc=1&Id_equipo=" + id_equipo + "&Id_orden=0").forward(request, response);
                    } else if (estado_ot == 2) {
                        Object[] obj_asig2 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        if (obj_asig2 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig2[6] == null ? null : obj_asig2[6].toString())) {
                            jpacotb.Ejecutar_ot(id_orden);
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                            mtdccr.Correo_ot(id_orden, "EJECUCIÓN OT", rol_usuario);
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot == 3) {
                        Object[] obj_asig3 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        if (obj_asig3 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig3[8] == null ? null : obj_asig3[8].toString())) {
                            jpacotb.Verificar_ot(id_orden);
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot == 5) {
                        boolean autoclave_equipo5 = (id_equipo == 47 || id_equipo == 37 || id_equipo == 48);
                        Object[] obj_asig5 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        boolean autorizado_cierre = obj_asig5 != null
                                && (Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_asig5, 37))
                                || (autoclave_equipo5 && Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_asig5, 6))));
                        if (autorizado_cierre) {
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                            mtdccr.Correo_ot(id_orden, "CERRAR OT", rol_usuario);
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    } else {
                        boolean autoclave_equipo = (id_equipo == 47 || id_equipo == 37 || id_equipo == 48);
                        Object[] obj_asig4 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        boolean autorizado_aprobador = obj_asig4 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig4[37] == null ? null : obj_asig4[37].toString());
                        boolean autorizado_tecnico_excepcion = autoclave_equipo && obj_asig4 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig4[6] == null ? null : obj_asig4[6].toString());
                        if (autorizado_aprobador || autorizado_tecnico_excepcion) {
                            if (autoclave_equipo) {
                                jpacotb.Aprobar_ot(id_orden);
                            }
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="MODULO EQUIPO TRABAJO GESTION">
                    tipo = "Orden_equipo_trabajo_gestion";
                    request.setAttribute("Orden", tipo);
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    request.setAttribute("Id_orden", id_orden);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR DETALLE ORDEN DE TRABAJO">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_orden_detalle = Integer.parseInt(request.getParameter("Id_orden_detalle"));
                    arg_grupos = request.getParameter("Arg_grupos");
                    arg_actividades_id = request.getParameter("Arg_id_actividades");
                    arg_actividades_id_pendientes = request.getParameter("Arg_id_actividades_pendientes");
                    arg_actividades = request.getParameter("Arg_actividades");
                    cont_actividades_programadas = Integer.parseInt(request.getParameter("Cont_actividades_programadas"));
                    cont_actividades_ejecutadas = Contar_actividades_ejecutadas(arg_actividades, request.getParameter("Cont_actividades_ejecutadas"));
                    pendientes = request.getParameter("Txt_datos_pendientes");
                    descripcion = request.getParameter("Txt_datos_descripcion");
                    repuestos = request.getParameter("Txt_datos_repuestos");
                    no_pmp = request.getParameter("No_pmp");
                    arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                    tiempo_trabajo = Integer.parseInt(request.getParameter("Tiempo_trabajo"));
                    if (Validar_usuario_asignado_ot(nombre_rol, nombre_usuario, Obtener_orden_asignacion(jpacotb, id_orden, 0), 0)) {
                        jpacotb.Tiempo_estimado_ot(id_orden, tiempo_trabajo);
                        proceso = jpacotd.Actualizar_detalle_orden_trabajo(id_orden_detalle, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, cont_actividades_ejecutadas, pendientes, descripcion, repuestos, no_pmp, ((no_pmp.equals("N/A") ? "" : rol_usuario)), arg_actividades_id_pendientes, arg_id_lineas_zonas);
                        if (proceso) {
                            request.setAttribute("Alerta", "Actualizar_orden_detalle");
                        } else {
                            request.setAttribute("Alerta", "Error_actualizar_orden_detalle");
                        }
                    } else {
                        request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                    }
                    request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="FIRMAS PRF Y GC">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    tipo_firma = Integer.parseInt(request.getParameter("tfm"));
                    documento = Integer.parseInt(request.getParameter("Txt_documento"));
                    if (!Validar_usuario_asignado_ot(nombre_rol, nombre_usuario, Obtener_orden_asignacion(jpacotb, id_orden, 0), 0)) {
                        request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                    } else if (documento > 0) {
                        lst_empleado = mtdcms.Empleado_sirh(documento + "");
                        String empleado = lst_empleado.get(0).toString();
                        if (tipo_firma == 0) {
                            proceso = jpacotb.Recibir_prf_ot(id_orden, empleado);
                        } else {
                            proceso = jpacotb.Recibir_gc_ot(id_orden, empleado);
                        }
                        if (proceso) {
                            request.setAttribute("Alerta", "Firma_OT");
                            request.setAttribute("var1", tipo_firma);
                            request.setAttribute("var2", empleado);
                        } else {
                            request.setAttribute("Alerta", "Error_firma_OT");
                        }
                    } else {
                        request.setAttribute("Alerta", "Empleado_inexistente");
                        request.setAttribute("var1", documento);
                    }
                    request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden + "").forward(request, response);
                    break;
                //</editor-fold>
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDEN ZONA GENERAL">
                    tipo = "Orden_zona_general";
                    request.setAttribute("Orden", tipo);
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 1;
                    }
                    try {
                        id_origen = Integer.parseInt(request.getParameter("Id_origen"));
                    } catch (Exception e) {
                        id_origen = 0;
                    }
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.setAttribute("Id_origen", id_origen);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR OT">
                    id_origen = Integer.parseInt(request.getParameter("Id_origen"));
                    numero_ot = Integer.parseInt(request.getParameter("Txt_numero_ot"));
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    usuario_programador = request.getParameter("Txt_programador");
                    usuario_ejecutor = request.getParameter("Cbx_ejecutor");
                    usuario_revisor = request.getParameter("Cbx_revisor");
                    usuario_aprobador = request.getParameter("Cbx_aprobador");
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Cbx_tipo_mtto"));
                    fecha_prog = request.getParameter("Txt_fecha_prog");
                    if (!Es_rol_programador(nombre_rol)) {
                        proceso = false;
                    } else if (id_orden > 0) {
                        proceso = jpacotb.Modificar_orden_trabajo_zona(id_orden, usuario_programador, usuario_ejecutor, usuario_revisor, usuario_aprobador, rol_usuario, id_tipo_mtto, fecha_prog);
                    } else {
                        proceso = jpacotb.Registrar_orden_trabajo(numero_ot, id_tipo_mtto, id_origen, usuario_programador, usuario_ejecutor, usuario_revisor, usuario_aprobador, 1, rol_usuario);
                    }
                    if (!Es_rol_programador(nombre_rol)) {
                        request.setAttribute("Alerta", "Sin_permiso_rol");
                    } else if (proceso) {
                        request.setAttribute("Alerta", "" + ((id_orden == 0) ? "Registro" : "Modificar") + "_orden");
                    } else {
                        request.setAttribute("Alerta", "Error_" + ((id_orden == 0) ? "registro" : "modificar") + "_orden");
                    }
                    request.setAttribute("var1", numero_ot);
                    if (id_orden > 0) {
                        request.getRequestDispatcher("Orden?opc=10&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=" + id_origen + "&Id_orden=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Orden?opc=8&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=0").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDEN ZONA GENERAL HISTORIAL">                    
                    tipo = "Orden_zona_general_historial";
                    request.setAttribute("Orden", tipo);
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    id_origen = Integer.parseInt(request.getParameter("Id_origen"));
                    try {
                        id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    } catch (Exception e) {
                        id_orden = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 1;
                    }
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.setAttribute("Id_origen", id_origen);
                    request.setAttribute("Id_orden", id_orden);
                    request.setAttribute("estado", estado);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDEN ORIGEN TRABAJO EMISIÓN">
                    tipo = "Orden_origen_trabajo_emision";
                    request.setAttribute("Orden", tipo);
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    request.setAttribute("Id_orden", id_orden);
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="GESTION Y ESTADO OT">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_origen = Integer.parseInt(request.getParameter("Id_origen"));
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    estado_ot = Integer.parseInt(request.getParameter("Estado_ot"));
                    if (estado_ot == 1) {
                        arg_grupos = request.getParameter("Arg_grupos");
                        arg_actividades_id = request.getParameter("Arg_id_actividades");
                        arg_actividades_id_pendientes = request.getParameter("Arg_id_actividades_pendientes");
                        arg_actividades = request.getParameter("Arg_actividades");
                        cont_actividades_programadas = Integer.parseInt(request.getParameter("Cont_actividades_programadas"));
                        arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                        if (Es_rol_programador(nombre_rol)) {
                            jpacotd.Registrar_detalle_orden_trabajo(id_orden, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, 0, "", "", "", "N/A", "", arg_actividades_id_pendientes, arg_id_lineas_zonas);
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_rol");
                        }
//                        mtdccr.Correo_ot(id_orden, "PROGRAMACIÓN OT " + ((id_tipo_mtto == 3) ? "ZONA" : "GENERAL"), rol_usuario);
                        request.getRequestDispatcher("Orden?opc=10&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=" + id_origen + "&Id_orden=0").forward(request, response);
                    } else if (estado_ot == 2) {
                        Object[] obj_asig_zg2 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        if (obj_asig_zg2 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig_zg2[6] == null ? null : obj_asig_zg2[6].toString())) {
                            jpacotb.Ejecutar_ot(id_orden);
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
//                        mtdccr.Correo_ot(id_orden, "EJECUCIÓN OT " + ((id_tipo_mtto == 3) ? "ZONA" : "GENERAL"), rol_usuario);
                        request.getRequestDispatcher("Orden?opc=13&Id_tipo_mtto=" + id_tipo_mtto + "&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot == 3) {
                        Object[] obj_asig_zg3 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        if (obj_asig_zg3 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig_zg3[8] == null ? null : obj_asig_zg3[8].toString())) {
                            jpacotb.Verificar_ot(id_orden);
                            jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
                        request.getRequestDispatcher("Orden?opc=13&Id_tipo_mtto=" + id_tipo_mtto + "&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot >= 4) {
                        Object[] obj_asig_zg4 = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto);
                        if (obj_asig_zg4 != null && Validar_usuario_accion(nombre_rol, nombre_usuario, obj_asig_zg4[20] == null ? null : obj_asig_zg4[20].toString())) {
                            jpacotb.Aprobar_ot(id_orden);
                            jpacotb.Cambiar_estado(id_orden, 6);
                        } else {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        }
//                        mtdccr.Correo_ot(id_orden, "CIERRE OT " + ((id_tipo_mtto == 3) ? "ZONA" : "GENERAL"), rol_usuario);
                        request.getRequestDispatcher("Orden?opc=13&Id_tipo_mtto=" + id_tipo_mtto + "&Id_orden=" + id_orden).forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ORDE ORIGEN TRABAJO GESTION">
                    tipo = "Orden_origen_trabajo_gestion";
                    request.setAttribute("Orden", tipo);
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    request.setAttribute("Id_orden", id_orden);
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.getRequestDispatcher("Orden_trabajo.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR DETALLE">
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_orden_detalle = Integer.parseInt(request.getParameter("Id_orden_detalle"));
                    arg_grupos = request.getParameter("Arg_grupos");
                    arg_actividades_id = request.getParameter("Arg_id_actividades");
                    arg_actividades_id_pendientes = request.getParameter("Arg_id_actividades_pendientes");
                    arg_actividades = request.getParameter("Arg_actividades");
                    cont_actividades_programadas = Integer.parseInt(request.getParameter("Cont_actividades_programadas"));
                    cont_actividades_ejecutadas = Contar_actividades_ejecutadas(arg_actividades, request.getParameter("Cont_actividades_ejecutadas"));
                    pendientes = request.getParameter("Txt_datos_pendientes");
                    descripcion = request.getParameter("Txt_datos_descripcion");
                    repuestos = request.getParameter("Txt_datos_repuestos");
                    no_pmp = request.getParameter("No_pmp");
                    arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                    tiempo_trabajo = Integer.parseInt(request.getParameter("Tiempo_trabajo"));
                    if (Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_mtto), 6))) {
                        jpacotb.Tiempo_estimado_ot(id_orden, tiempo_trabajo);
                        proceso = jpacotd.Actualizar_detalle_orden_trabajo(id_orden_detalle, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, cont_actividades_ejecutadas, pendientes, descripcion, repuestos, no_pmp, ((no_pmp.equals("N/A") ? "" : rol_usuario)), arg_actividades_id_pendientes, arg_id_lineas_zonas);
                        if (proceso) {
                            request.setAttribute("Alerta", "Actualizar_orden_detalle");
                        } else {
                            request.setAttribute("Alerta", "Error_actualizar_orden_detalle");
                        }
                    } else {
                        request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                    }
                    request.getRequestDispatcher("Orden?opc=13&Id_tipo_mtto=" + id_tipo_mtto + "&Id_orden=" + id_orden).forward(request, response);
                    break;
                //</editor-fold>
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="DEVOLUCIÓN">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_equipo = Integer.parseInt(request.getParameter("Id_equipo"));
                    estado_ot = Integer.parseInt(request.getParameter("Estado_ot"));
                    justificacion = request.getParameter("Txt_justificacion");
                    tipo_mtto = Integer.parseInt(request.getParameter("Tipo_mt"));
                    {
                        // Devuelve solo el verificador (estado 3) o el aprobador (estados 4 y 5) asignado
                        int id_tipo_dev = (tipo_mtto == 1) ? 0 : Integer.parseInt(request.getParameter("idTP"));
                        Object[] obj_asig_dev = Obtener_orden_asignacion(jpacotb, id_orden, id_tipo_dev);
                        boolean autorizado_dev;
                        if (nombre_rol.equals("Tecnico") || nombre_rol.equals("Consulta")) {
                            autorizado_dev = false;
                        } else if (estado_ot == 3) {
                            autorizado_dev = Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_asig_dev, 8));
                        } else if (estado_ot == 4 || estado_ot == 5) {
                            autorizado_dev = Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_asig_dev, Indice_aprobador(id_tipo_dev)));
                        } else {
                            autorizado_dev = nombre_rol.equals("Administrador");
                        }
                        if (!autorizado_dev) {
                            request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                            if (tipo_mtto == 1) {
                                request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                            } else {
                                request.getRequestDispatcher("Orden?opc=13&Id_orden=" + id_orden + "&Id_tipo_mtto=" + id_tipo_dev + "").forward(request, response);
                            }
                            break;
                        }
                    }
                    if (estado_ot == 2) {
                        jpacotb.Ejecutar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot - 1));
                        jpacotd.RegistrarDevolucion(id_orden, justificacion, estado_ot, rol_usuario);
//                        mtdccr.Correo_ot(id_orden, "EJECUCIÓN OT", rol_usuario);
                        if (tipo_mtto == 1) {
                            request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                        } else {
                            id_tipo_mtto = Integer.parseInt(request.getParameter("idTP"));
                            request.getRequestDispatcher("Orden?opc=13&Id_orden=" + id_orden + "&Id_tipo_mtto=" + id_tipo_mtto + "").forward(request, response);
                        }
                    } else if (estado_ot == 3) {
                        jpacotb.Verificar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot - 1));
                        jpacotd.RegistrarDevolucion(id_orden, justificacion, estado_ot, rol_usuario);
                        if (tipo_mtto == 1) {
                            request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                        } else {
                            id_tipo_mtto = Integer.parseInt(request.getParameter("idTP"));
                            request.getRequestDispatcher("Orden?opc=13&Id_orden=" + id_orden + "&Id_tipo_mtto=" + id_tipo_mtto + "").forward(request, response);
                        }
                    } else if (estado_ot == 4) {
                        jpacotb.Cambiar_estado(id_orden, (estado_ot - 1));
//                        mtdccr.Correo_ot(id_orden, "CERRAR OT", rol_usuario);
                        jpacotd.RegistrarDevolucion(id_orden, justificacion, estado_ot, rol_usuario);
                        if (tipo_mtto == 1) {
                            request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                        } else {
                            id_tipo_mtto = Integer.parseInt(request.getParameter("idTP"));
                            request.getRequestDispatcher("Orden?opc=13&Id_orden=" + id_orden + "&Id_tipo_mtto=" + id_tipo_mtto + "").forward(request, response);
                        }
                    } else if (estado_ot == 5) {
                        jpacotb.Cambiar_estado(id_orden, (estado_ot - 1));
//                        mtdccr.Correo_ot(id_orden, "CERRAR OT", rol_usuario);
                        jpacotd.RegistrarDevolucion(id_orden, justificacion, estado_ot, rol_usuario);
                        if (tipo_mtto == 1) {
                            request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                        } else {
                            id_tipo_mtto = Integer.parseInt(request.getParameter("idTP"));
                            request.getRequestDispatcher("Orden?opc=13&Id_orden=" + id_orden + "&Id_tipo_mtto=" + id_tipo_mtto + "").forward(request, response);
                        }
                    }
                    break;
                //</editor-fold>
                case 16:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZA REGISTROS AUTOCLAVE">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    id_orden_detalle = Integer.parseInt(request.getParameter("Id_orden_detalle"));
                    arg_grupos = request.getParameter("Arg_grupos");
                    arg_actividades_id = request.getParameter("Arg_id_actividades");
                    arg_actividades_id_pendientes = request.getParameter("Arg_id_actividades_pendientes");
                    arg_actividades = request.getParameter("Arg_actividades");
                    cont_actividades_programadas = Integer.parseInt(request.getParameter("Cont_actividades_programadas"));
                    cont_actividades_ejecutadas = Contar_actividades_ejecutadas(arg_actividades, request.getParameter("Cont_actividades_ejecutadas"));
                    pendientes = request.getParameter("Txt_datos_pendientes");
                    descripcion = request.getParameter("Txt_datos_descripcion");
                    repuestos = request.getParameter("Txt_datos_repuestos");
                    no_pmp = request.getParameter("No_pmp");
                    arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                    tiempo_trabajo = Integer.parseInt(request.getParameter("Tiempo_trabajo"));
                    if (!Validar_usuario_asignado_ot(nombre_rol, nombre_usuario, Obtener_orden_asignacion(jpacotb, id_orden, 0), 0)) {
                        request.setAttribute("Alerta", "Sin_permiso_usuario_asignado");
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                        break;
                    }
                    jpacotb.Tiempo_estimado_ot(id_orden, tiempo_trabajo);
                    try {
                        verificacion = Integer.parseInt(request.getParameter("Txt_ver_equipo"));
                    } catch (Exception e) {
                    }
                    if (verificacion > 0) {
                        proceso = jpacotd.Actualizar_detalle_orden_trabajo_Autoclave(id_orden_detalle, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, cont_actividades_ejecutadas, pendientes, descripcion, repuestos, no_pmp, ((no_pmp.equals("N/A") ? "" : rol_usuario)), arg_actividades_id_pendientes, arg_id_lineas_zonas, verificacion);
                    } else {
                        proceso = jpacotd.Actualizar_detalle_orden_trabajo(id_orden_detalle, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, cont_actividades_ejecutadas, pendientes, descripcion, repuestos, no_pmp, ((no_pmp.equals("N/A") ? "" : rol_usuario)), arg_actividades_id_pendientes, arg_id_lineas_zonas);
                    }
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_orden_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_orden_detalle");
                    }
                    request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    break;
                //</editor-fold>
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="ACTUALIZACION FECHA PROGRAMACIÓN">
                    id_orden = Integer.parseInt(request.getParameter("Txt_id"));
                    id_equipo = Integer.parseInt(request.getParameter("Equipo"));
                    firma_mtf = Integer.parseInt(request.getParameter("Txt_documento_mtf"));
                    firma_prf = Integer.parseInt(request.getParameter("Txt_documento_prf"));
                    String firma = "[" + firma_mtf + "]" + "[" + firma_prf + "]";
                    justificacion = request.getParameter("Txt_justificacion");
                    if (Es_rol_programador(nombre_rol)) {
                        proceso = jpacotb.Autorizacion(id_orden, firma, justificacion);
                        request.setAttribute("Alerta", "Firma_autorizacion");
                    } else {
                        request.setAttribute("Alerta", "Sin_permiso_rol");
                    }
                    request.getRequestDispatcher("Orden?opc=1&Id_equipo=" + id_equipo + "&Id_orden=0").forward(request, response);
                    break;
                //</editor-fold>
                case 18:
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO ZONAS Y ORDENES DE TRABAJO">
                    int id = Integer.parseInt(request.getParameter("id"));
                    estado = Integer.parseInt(request.getParameter("Estado"));
                    id_tipo_mtto = Integer.parseInt(request.getParameter("Id_tipo_mtto"));
                    if (!Es_rol_programador(nombre_rol)) {
                        request.setAttribute("Alerta", "Sin_permiso_rol");
                    } else if (id_tipo_mtto == 3) {
                        jpacotb.CambiarEstadoZona(id, estado);
                    } else {
                        jpacotb.CambiarEstadoGeneral(id, estado);
                    }
                    request.getRequestDispatcher("Orden?opc=8&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=0").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Verifica que el usuario en sesión sea el usuario asignado para la
     * acción, además de tener el rol requerido. El Administrador siempre
     * está autorizado.
     */
    private boolean Validar_usuario_accion(String nombre_rol, String nombre_usuario, String usuario_asignado) {
        if (nombre_rol.equals("Administrador")) {
            return true;
        }
        return usuario_asignado != null && usuario_asignado.trim().equalsIgnoreCase(nombre_usuario.trim());
    }

    /**
     * Roles que programan, emiten y modifican la asignación de las OT.
     */
    private boolean Es_rol_programador(String nombre_rol) {
        return nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente");
    }

    /**
     * Posición del aprobador en la fila de la OT: equipo (37) o zona/general
     * (20).
     */
    private int Indice_aprobador(int id_tipo_mtto) {
        return (id_tipo_mtto == 3 || id_tipo_mtto == 4) ? 20 : 37;
    }

    private String Valor(Object[] obj, int indice) {
        return (obj == null || obj[indice] == null) ? null : obj[indice].toString();
    }

    /**
     * Cuenta las actividades ejecutadas (SI o N/A) a partir del texto
     * guardado [id_/_EJECUTO_/_OBSERVACION]. El contador que envía el
     * navegador sumaba en cada clic y podía superar las programadas.
     */
    private int Contar_actividades_ejecutadas(String arg_actividades, String cont_navegador) {
        if (arg_actividades == null || !arg_actividades.contains("_/_")) {
            try {
                return Integer.parseInt(cont_navegador);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        int contador = 0;
        String[] actividades = arg_actividades.split("\\]\\[");
        for (String actividad : actividades) {
            String[] partes = actividad.replace("[", "").replace("]", "").split("_/_", -1);
            if (partes.length > 1) {
                String ejecuto = partes[1].trim();
                if (ejecuto.equals("SI") || ejecuto.equals("N/A")) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Verifica que el usuario en sesión esté asignado a la OT como ejecutor,
     * verificador o aprobador. El Administrador siempre está autorizado.
     */
    private boolean Validar_usuario_asignado_ot(String nombre_rol, String nombre_usuario, Object[] obj_orden, int id_tipo_mtto) {
        if (nombre_rol.equals("Administrador")) {
            return true;
        }
        if (obj_orden == null) {
            return false;
        }
        return Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_orden, 6))
                || Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_orden, 8))
                || Validar_usuario_accion(nombre_rol, nombre_usuario, Valor(obj_orden, Indice_aprobador(id_tipo_mtto)));
    }

    /**
     * Trae la fila de la orden de trabajo (equipo, zona o general) con los
     * usuarios asignados por estado, para validar la propiedad de la acción.
     */
    private Object[] Obtener_orden_asignacion(OrdenTrabajoJpaController jpacotb, int id_orden, int id_tipo_mtto) {
        List lst;
        if (id_tipo_mtto == 3) {
            lst = jpacotb.Orden_trabajo_zona_id(id_orden);
        } else if (id_tipo_mtto == 4) {
            lst = jpacotb.Orden_trabajo_general_id(id_orden);
        } else {
            lst = jpacotb.Orden_trabajo_equipo_id(id_orden);
        }
        return (lst != null && !lst.isEmpty()) ? (Object[]) lst.get(0) : null;
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
