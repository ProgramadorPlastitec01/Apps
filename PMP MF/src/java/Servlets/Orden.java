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
                    if (id_orden > 0) {
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
                    if (proceso) {
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
                        arg_grupos = request.getParameter("Arg_grupos");
                        arg_actividades_id = request.getParameter("Arg_id_actividades");
                        arg_actividades_id_pendientes = request.getParameter("Arg_id_actividades_pendientes");
                        arg_actividades = request.getParameter("Arg_actividades");
                        cont_actividades_programadas = Integer.parseInt(request.getParameter("Cont_actividades_programadas"));
                        arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                        jpacotd.Registrar_detalle_orden_trabajo(id_orden, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, 0, "", "", "", "N/A", "", arg_actividades_id_pendientes, arg_id_lineas_zonas);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        mtdccr.Correo_ot(id_orden, "PROGRAMACIÓN OT", rol_usuario);
                        request.getRequestDispatcher("Orden?opc=1&Id_equipo=" + id_equipo + "&Id_orden=0").forward(request, response);
                    } else if (estado_ot == 2) {
                        jpacotb.Ejecutar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        mtdccr.Correo_ot(id_orden, "EJECUCIÓN OT", rol_usuario);
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot == 3) {
                        jpacotb.Verificar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot == 5) {
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        mtdccr.Correo_ot(id_orden, "CERRAR OT", rol_usuario);
                        request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    } else {
                        if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                            jpacotb.Aprobar_ot(id_orden);
                        }
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
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
                    cont_actividades_ejecutadas = Integer.parseInt(request.getParameter("Cont_actividades_ejecutadas"));
                    pendientes = request.getParameter("Txt_datos_pendientes");
                    descripcion = request.getParameter("Txt_datos_descripcion");
                    repuestos = request.getParameter("Txt_datos_repuestos");
                    no_pmp = request.getParameter("No_pmp");
                    arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                    tiempo_trabajo = Integer.parseInt(request.getParameter("Tiempo_trabajo"));
                    jpacotb.Tiempo_estimado_ot(id_orden, tiempo_trabajo);
                    proceso = jpacotd.Actualizar_detalle_orden_trabajo(id_orden_detalle, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, cont_actividades_ejecutadas, pendientes, descripcion, repuestos, no_pmp, ((no_pmp.equals("N/A") ? "" : rol_usuario)), arg_actividades_id_pendientes, arg_id_lineas_zonas);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_orden_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_orden_detalle");
                    }
                    request.getRequestDispatcher("Orden?opc=5&Id_orden=" + id_orden).forward(request, response);
                    break;
                //</editor-fold>
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="FIRMAS PRF Y GC">
                    id_orden = Integer.parseInt(request.getParameter("Id_orden"));
                    tipo_firma = Integer.parseInt(request.getParameter("tfm"));
                    documento = Integer.parseInt(request.getParameter("Txt_documento"));
                    if (documento > 0) {
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
                        id_origen = Integer.parseInt(request.getParameter("Id_origen"));
                    } catch (Exception e) {
                        id_origen = 0;
                    }
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.setAttribute("Id_origen", id_origen);
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
                    if (id_orden > 0) {
                        proceso = jpacotb.Modificar_orden_trabajo_zona(id_orden, usuario_programador, usuario_ejecutor, usuario_revisor, usuario_aprobador, rol_usuario, id_tipo_mtto, fecha_prog);
                    } else {
                        proceso = jpacotb.Registrar_orden_trabajo(numero_ot, id_tipo_mtto, id_origen, usuario_programador, usuario_ejecutor, usuario_revisor, usuario_aprobador, 1, rol_usuario);
                    }
                    if (proceso) {
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
                    request.setAttribute("Id_tipo_mtto", id_tipo_mtto);
                    request.setAttribute("Id_origen", id_origen);
                    request.setAttribute("Id_orden", id_orden);
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
                        jpacotd.Registrar_detalle_orden_trabajo(id_orden, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, 0, "", "", "", "N/A", "", arg_actividades_id_pendientes, arg_id_lineas_zonas);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
//                        mtdccr.Correo_ot(id_orden, "PROGRAMACIÓN OT " + ((id_tipo_mtto == 3) ? "ZONA" : "GENERAL"), rol_usuario);
                        request.getRequestDispatcher("Orden?opc=10&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=" + id_origen + "&Id_orden=0").forward(request, response);
                    } else if (estado_ot == 2) {
                        jpacotb.Ejecutar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
//                        mtdccr.Correo_ot(id_orden, "EJECUCIÓN OT " + ((id_tipo_mtto == 3) ? "ZONA" : "GENERAL"), rol_usuario);
                        request.getRequestDispatcher("Orden?opc=13&Id_tipo_mtto=" + id_tipo_mtto + "&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot == 3) {
                        jpacotb.Verificar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, (estado_ot + 1));
                        request.getRequestDispatcher("Orden?opc=13&Id_tipo_mtto=" + id_tipo_mtto + "&Id_orden=" + id_orden).forward(request, response);
                    } else if (estado_ot >= 4) {
                        jpacotb.Aprobar_ot(id_orden);
                        jpacotb.Cambiar_estado(id_orden, 6);
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
                    cont_actividades_ejecutadas = Integer.parseInt(request.getParameter("Cont_actividades_ejecutadas"));
                    pendientes = request.getParameter("Txt_datos_pendientes");
                    descripcion = request.getParameter("Txt_datos_descripcion");
                    repuestos = request.getParameter("Txt_datos_repuestos");
                    no_pmp = request.getParameter("No_pmp");
                    arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                    tiempo_trabajo = Integer.parseInt(request.getParameter("Tiempo_trabajo"));
                    jpacotb.Tiempo_estimado_ot(id_orden, tiempo_trabajo);
                    proceso = jpacotd.Actualizar_detalle_orden_trabajo(id_orden_detalle, arg_grupos, arg_actividades_id, arg_actividades, cont_actividades_programadas, cont_actividades_ejecutadas, pendientes, descripcion, repuestos, no_pmp, ((no_pmp.equals("N/A") ? "" : rol_usuario)), arg_actividades_id_pendientes, arg_id_lineas_zonas);
                    if (proceso) {
                        request.setAttribute("Alerta", "Actualizar_orden_detalle");
                    } else {
                        request.setAttribute("Alerta", "Error_actualizar_orden_detalle");
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
                    cont_actividades_ejecutadas = Integer.parseInt(request.getParameter("Cont_actividades_ejecutadas"));
                    pendientes = request.getParameter("Txt_datos_pendientes");
                    descripcion = request.getParameter("Txt_datos_descripcion");
                    repuestos = request.getParameter("Txt_datos_repuestos");
                    no_pmp = request.getParameter("No_pmp");
                    arg_id_lineas_zonas = request.getParameter("Arg_id_lineas_zona");
                    tiempo_trabajo = Integer.parseInt(request.getParameter("Tiempo_trabajo"));
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
                    proceso = jpacotb.Autorizacion(id_orden, firma, justificacion);
                    request.setAttribute("Alerta", "Firma_autorizacion");
                    request.getRequestDispatcher("Orden?opc=1&Id_equipo=" + id_equipo + "&Id_orden=0").forward(request, response);
                //</editor-fold>
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
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
