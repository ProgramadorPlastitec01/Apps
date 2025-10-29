package Servlets;

import Controladores.RequisicionJpaController;
import Metodos.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Requisicion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            HttpSession sesion = request.getSession();
            int opc = Integer.parseInt(request.getParameter("opc"));
            int id_area = (Integer) sesion.getAttribute("idArea");
            String nombre = (String) sesion.getAttribute("Nombres");
            Calendar cal = Calendar.getInstance();
            RequisicionJpaController jpa_requisicion = new RequisicionJpaController();
            int idRequisicion, estado2 = 0, idSolicitud, estado = 0, estado_fl = 0, modulo = 0;
            int prioridad = 0;
            int clasificacion = 0;
            int var_filtro = 0;
            int history = 0;
            int importarcion = 0, dias_vencidos = 0, area = 0, limit = 0, campo = 0;
            int anio = 0;
            String referenciap, referenciag, centro_costo, proyecto, proveedor, cotizacion7, elemento, marca, destino, fechaE, cotizacion, cotizacion2,
                    idsCantidades, cotizacionCor, cotizacion3, descripcion, observaciones, fecha_i, fecha_f, fecha_prov, unidad, SelCantidad, SelDisp, Ocompra, buscar = "";
            Email correo = new Email();
            String FechaC = "", FechaO = "", DDisp = "", rentrega = "";
            String query = "", fto_esp = "", consulta = "", condicion = "", arg_requisicion = "", Ids_Cantidades = "";
            double cantidad;
            boolean accion = true;
            List lst_requisicion = null;
            List lst_estado4 = null;
            List lst_fechas = null;
            List lst_const_req = null;
            lst_fechas = jpa_requisicion.TraerFechas();
            Object[] obj_fec = (Object[]) lst_fechas.get(0);
            //</editor-fold>
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="FUNCION DE SERVLET ">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    cotizacionCor = request.getParameter("Txt_ids");
                    if (cotizacionCor == null) {
                        cotizacionCor = "";
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("modulo"));
                    } catch (Exception e) {
                        modulo = 0;
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 0;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        cotizacion2 = request.getParameter("Txt_ids2");
                    } catch (Exception e) {
                        cotizacion2 = "";
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[4].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 1;
                    }
                    try {
                        id_area = Integer.parseInt(request.getParameter("id_Area"));
                        request.setAttribute("id_Area", id_area);
                    } catch (Exception e) {
                        id_area = id_area = (Integer) sesion.getAttribute("idArea");;
                        request.setAttribute("idArea", id_area);
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("id_var", var_filtro);
                    request.setAttribute("Txt_ids2", cotizacion2);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("Txt_ids", cotizacionCor);
                    request.setAttribute("estado", estado);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloSolicitud");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRA">
                    referenciap = request.getParameter("Txt_referencias");
                    referenciag = request.getParameter("Txt_gasto");
                    elemento = request.getParameter("Txt_elemento");
                    cantidad = Double.parseDouble(request.getParameter("Txt_cantidad"));
                    marca = request.getParameter("Txt_marca");
                    destino = request.getParameter("Txt_destino");
                    fechaE = request.getParameter("Txt_fechaE");
                    prioridad = Integer.parseInt(request.getParameter("Rbo_prioridad"));
                    clasificacion = Integer.parseInt(request.getParameter("Cbx_clasificacion"));
                    try {
                        area = Integer.parseInt(request.getParameter("Cbx_area"));
                    } catch (Exception e) {
                        id_area = (Integer) sesion.getAttribute("idArea");
                    }
                    unidad = request.getParameter("Txt_unidad");
                    descripcion = request.getParameter("Txt_descripcion");
                    centro_costo = request.getParameter("Cbx_tipo_activo");
                    cotizacion7 = request.getParameter("Txt_cotizacion");
                    proyecto = request.getParameter("Cbx_proyecto");
                    if (centro_costo.equals("GASTO")) {
                        proyecto = "N/A";
                    } else {
                        referenciag = "N/A";
                    }
                    if (area != 0) {
                        accion = jpa_requisicion.registrarRequisicion(referenciap, elemento, cantidad, marca, destino, fechaE, clasificacion, unidad, prioridad, nombre, area, descripcion, cotizacion7, centro_costo, referenciag, proyecto);
                    } else {
                        accion = jpa_requisicion.registrarRequisicion(referenciap, elemento, cantidad, marca, destino, fechaE, clasificacion, unidad, prioridad, nombre, id_area, descripcion, cotizacion7, centro_costo, referenciag, proyecto);
                    }
                    if (accion) {
                        request.setAttribute("Alerta", "Registro_Requisicion");
                        request.setAttribute("var1", elemento);
                    } else {
                        request.setAttribute("Alerta", "ErrorDescripcion");
                    }
                    request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0").forward(request, response);
                    break;
                //</editor-fold>
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                    idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    fechaE = request.getParameter("Txt_fechaE");
                    elemento = request.getParameter("Txt_elemento");
                    cantidad = Double.parseDouble(request.getParameter("Txt_cantidad"));
                    marca = request.getParameter("Txt_marca");
                    destino = request.getParameter("Txt_destino");
                    clasificacion = Integer.parseInt(request.getParameter("Cbx_clasificacion"));
                    unidad = request.getParameter("Txt_unidad");
                    prioridad = Integer.parseInt(request.getParameter("Rbo_prioridad"));
                    descripcion = request.getParameter("Txt_descripcion");
                    referenciap = request.getParameter("Txt_referencias");
                    referenciag = request.getParameter("Txt_gasto");
                    centro_costo = request.getParameter("Cbx_tipo_activo");
                    cotizacion7 = request.getParameter("Txt_cotizacion");
                    proyecto = request.getParameter("Cbx_proyecto");
                    if (centro_costo.equals("GASTO")) {
                        proyecto = "N/A";
                    } else {
                        referenciag = "N/A";
                    }
                    jpa_requisicion.registrarLogRequisicion(idRequisicion, nombre);
                    accion = jpa_requisicion.modificarRequisicion(idRequisicion, fechaE, elemento, cantidad, marca,
                            destino, clasificacion, unidad, prioridad, descripcion, referenciap, referenciag, centro_costo, cotizacion7, proyecto);
                    if (accion == true) {
                        request.setAttribute("Alerta", "Modificar_Requisicion");
                        request.setAttribute("var1", elemento);
                    } else {
                        request.setAttribute("Alerta", "Error_registro");
                    }
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    if (modulo == 2) {
                        request.getRequestDispatcher("Requisicion?opc=36&estado=2&Txt_ids=&idRequisicion=0").forward(request, response);
                    } else {
                        request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="COTIZACION">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    cotizacionCor = request.getParameter("Txt_ids");
                    if (cotizacionCor == null) {
                        cotizacionCor = "";
                    } else {
                        cotizacionCor = "provedor";
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 0;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 2;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[4].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("id_var", var_filtro);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("estado", estado);
                    request.setAttribute("Txt_ids", cotizacionCor);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloRequisicion");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="CHECKBOX SELECCIONAR">
                    cotizacion = request.getParameter("Txt_ids");
                    estado = Integer.parseInt(request.getParameter("estado"));
                    try {
                        estado2 = Integer.parseInt(request.getParameter("estado2"));
                    } catch (Exception e) {
                        estado2 = 0;
                    }
                    String[] idC = cotizacion.split("-");
                    if (cotizacion.equals("")) {
                        request.setAttribute("Alerta", "ErrorReq");
                        if (estado2 == 1) {
                            request.getRequestDispatcher("Requisicion?opc=1").forward(request, response);
                            break;
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + estado2 + "&Txt_ids=").forward(request, response);
                            break;
                        }
                    } else {
                        for (int i = 0; i < idC.length; i++) {
                            lst_requisicion = jpa_requisicion.ConsultaRequsicionId(Integer.parseInt(idC[i]));
                            if (estado2 == 5) {
                                accion = jpa_requisicion.liberarRequisicion((Integer.parseInt(idC[i])), estado, nombre);
                            } else {
                                accion = jpa_requisicion.AprobarCotizacion(Integer.parseInt(idC[i]), estado);
                            }
                        }
                        if (accion == true) {
                            if (estado == 6) {
                                correo.RequisicionEntregada(cotizacion, nombre);
                            } else if (estado == 1 || estado == 2 || estado == 5) {
                                correo.NotificacionRequisiciones(cotizacion, nombre, estado);
                            }
                        }
                    }
                    if (accion) {
                        request.setAttribute("Alerta", "EnvioRequisiciones");
                        if (estado2 == 1) {
                            request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0&estado=" + estado2 + "").forward(request, response);
                            break;
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + estado2 + "&Txt_ids=").forward(request, response);
                            break;
                        }
                    }
                //</editor-fold>
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="NOTIFICAR DECLINACION, DEVOLUCION Y DUPLICAR REQ">
                    idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    estado = Integer.parseInt(request.getParameter("estado"));
                    String justificacion = request.getParameter("Txt_justificacion");
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    if (!justificacion.equals("")) {
                        jpa_requisicion.registrarLogRequisicion(idRequisicion, nombre);
                        accion = jpa_requisicion.estadoRequisicion(idRequisicion, estado, justificacion);
                        if (estado == 7) {
                            if (accion) {
                                request.setAttribute("Alerta", "DevolucionSolicitud");
                                correo.RequisicionDeclinadaYDevuelta(idRequisicion, nombre);
                            } else {
                                request.setAttribute("Alerta", "ErrorNotificar");
                            }
                        } else if (estado == 0) {
                            if (accion) {
                                request.setAttribute("Alerta", "Declinado");
                                correo.RequisicionDeclinadaYDevuelta(idRequisicion, nombre);
                            } else {
                                request.setAttribute("Alerta", "ErrorNotificar");
                            }
                        } else {
                            request.setAttribute("Alerta", "RetornarRequisicion");
                        }
                    } else {
                        request.setAttribute("Alerta", "ErrorJustDeclinado");
                    }
                    if (modulo == 1) {
                        request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0").forward(request, response);
                        break;
                    } else if (modulo == 7) {
                        request.getRequestDispatcher("Requisicion?opc=17&idRequisicion=0").forward(request, response);
                        break;
                    } else if (modulo == 8) {
                        request.getRequestDispatcher("Requisicion?opc=21&idRequisicion=0").forward(request, response);
                        break;
                    } else {
                        request.getRequestDispatcher("Requisicion?opc=36&idRequisicion=0&estado=" + modulo + "").forward(request, response);
                        break;
                    }
                //</editor-fold>
                case 10:
                    //<editor-fold defaultstate="collapsed" desc="CONTENIDO GENERAL">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    cotizacionCor = request.getParameter("Txt_ids3");
                    Ids_Cantidades = request.getParameter("Txt_ids4");
                    if (cotizacionCor == null) {
                        cotizacionCor = "";
                    }
                    if (Ids_Cantidades == null) {
                        Ids_Cantidades = "";
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 0;
                    }
                    //idarea = Integer.parseInt(request.getParameter("id_area"));
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        limit = Integer.parseInt(request.getParameter("limit"));
                    } catch (Exception e) {
                        limit = 500;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[0].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("Txt_ids3", cotizacionCor);
                    request.setAttribute("Txt_ids4", Ids_Cantidades);
                    request.setAttribute("id_var", var_filtro);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("estado", estado);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloGeneral");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 11:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE COTIZACIÓN">
                    idSolicitud = Integer.parseInt(request.getParameter("idCotizacion"));
                    FechaC = request.getParameter("Txt_fechaDetalle");
                    descripcion = request.getParameter("Txt_descripcion");
                    cotizacion = request.getParameter("Txt_cotizacion");
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    if (descripcion.equals("")) {
                        request.setAttribute("Alerta", "ErrorDetalle");
                        request.getRequestDispatcher("Requisicion?opc=" + modulo).forward(request, response);
                    } else {
                        jpa_requisicion.registrarLogRequisicion(idSolicitud, nombre);
                        accion = jpa_requisicion.modificarCotizacion(idSolicitud, cotizacion, descripcion, FechaC, nombre);
                        if (accion) {
                            request.setAttribute("Alerta", "ActualizarDetalle");
                        } else {
                            request.setAttribute("Alerta", "ErrorDescripcion");
                        }
                        if (modulo == 6) {
                            request.getRequestDispatcher("Requisicion?opc=39").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + modulo + "").forward(request, response);
                        }
                    }
                    break;
                //</editor-fold>
                case 12:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE ORDEN DE COMPRA">
                    idSolicitud = Integer.parseInt(request.getParameter("idCotizacion"));
                    descripcion = request.getParameter("Txt_descripcion");
                    FechaO = request.getParameter("Txt_fechaProv");
                    fecha_prov = request.getParameter("Txt_fechaProv");
                    proveedor = request.getParameter("Txt_proveedor");
                    Ocompra = request.getParameter("Txt_Ocompra");
                    importarcion = Integer.parseInt(request.getParameter("Txt_importancion"));
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    if (descripcion.equals("")) {
                        request.setAttribute("Alerta", "ErrorDetalle");
                        request.getRequestDispatcher("Requisicion?opc=" + modulo).forward(request, response);
                    } else {
                        jpa_requisicion.registrarLogRequisicion(idSolicitud, nombre);
                        accion = jpa_requisicion.DetalleOrdenCompra(idSolicitud, descripcion, nombre, FechaO, fecha_prov, proveedor, Ocompra, importarcion);
                        if (accion) {
                            request.setAttribute("Alerta", "ActualizarDetalle");
                        } else {
                            request.setAttribute("Alerta", "ErrorDescripcion");
                        }
                        if (modulo == 6) {
                            request.getRequestDispatcher("Requisicion?opc=39").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + modulo + "").forward(request, response);
                        }
                    }
                    break;
                //</editor-fold>
                case 13:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE OC/C GENERADOS">
                    idSolicitud = Integer.parseInt(request.getParameter("idCotizacion"));
                    descripcion = request.getParameter("Txt_descripcion");
                    cantidad = Double.parseDouble(request.getParameter("Txt_cantidad"));
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    fecha_f = request.getParameter("Txt_fechall");
                    if (descripcion.equals("")) {
                        request.setAttribute("Alerta", "ErrorDetalle");
                        request.getRequestDispatcher("Requisicion?opc=" + modulo).forward(request, response);
                    } else {
                        jpa_requisicion.registrarLogRequisicion(idSolicitud, nombre);
                        accion = jpa_requisicion.DetalleGenerado(idSolicitud, fecha_f, descripcion, cantidad, nombre);
                        if (accion) {
                            request.setAttribute("Alerta", "ActualizarDetalle");
                        } else {
                            request.setAttribute("Alerta", "ErrorDescripcion");
                        }
                        if (modulo == 6) {
                            request.getRequestDispatcher("Requisicion?opc=39").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + modulo + "").forward(request, response);
                        }
                    }
                    break;
                //</editor-fold>
                case 14:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE DISPONIBILIDAD">
                    idSolicitud = Integer.parseInt(request.getParameter("idCotizacion"));
                    descripcion = request.getParameter("Txt_descripcion");
                    rentrega = request.getParameter("txt_entrega");
                    estado = Integer.parseInt(request.getParameter("estado"));
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    if (descripcion.equals("")) {
                        request.setAttribute("Alerta", "ErrorDetalle");
                        request.getRequestDispatcher("Requisicion?opc=" + modulo).forward(request, response);
                    } else {
                        accion = jpa_requisicion.DetalleDE(idSolicitud, descripcion, rentrega, nombre);
                        if (accion) {
                            jpa_requisicion.registrarLogRequisicion(idSolicitud, nombre);
                            request.setAttribute("Alerta", "ActualizarDetalle");
                        } else {
                            request.setAttribute("Alerta", "ErrorDescripcion");
                        }
                        if (modulo == 6) {
                            request.getRequestDispatcher("Requisicion?opc=39").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + modulo + "").forward(request, response);
                        }
                    }
                    break;
                //</editor-fold>
                case 15:
                    //<editor-fold defaultstate="collapsed" desc="HISTORIAL ORDEN DE COMPRA">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 100;
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("modulo"));
                    } catch (Exception e) {
                        modulo = 0;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[0].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    try {
                        history = Integer.parseInt(request.getParameter("history"));
                    } catch (Exception e) {
                        history = 1;
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("id_var", var_filtro);
                    request.setAttribute("history", history);
                    idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("estado", estado);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "HistorialRequisicion");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 17:
                    //<editor-fold defaultstate="collapsed" desc="SOLICITUD DECLINADA">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[0].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("estado", estado);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloSolicitudDeclinada");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 19:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO MASIVO">
                    String Matriz = request.getParameter("Txt_Matriz");
                    String[] filas = Matriz.split("\n");
                    if (Matriz.equals("")) {
                        request.setAttribute("Alerta", "ErrorReg_Mas");
                        request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0").forward(request, response);
                    } else {
                        for (int i = 0; i < filas.length; i++) {
                            String[] columnas = filas[i].replace("\r", "").split(",");
                            if (columnas.length >= 8 || columnas[0].equals(Types.DATE)) {
                                try {
                                    referenciap = columnas[0];
                                    if (referenciap == null || referenciap == "") {
                                        referenciap = "N/A";
                                    }
                                    fechaE = columnas[1];
                                    elemento = columnas[2];
                                    marca = columnas[3];
                                    destino = columnas[4];
                                    if (columnas[5].equals("SERVICIO")) {
                                        clasificacion = 2;
                                    } else if (columnas[5].equals("PROCESO")) {
                                        clasificacion = 1;
                                    }
                                    cantidad = Double.parseDouble(columnas[6]);
                                    unidad = columnas[7];
                                    if (columnas[8].equals("ALTA")) {
                                        prioridad = 1;
                                    } else if (columnas[8].equals("NORMAL")) {
                                        prioridad = 0;
                                    }
                                    cotizacion = columnas[12];
                                    if (cotizacion == null || cotizacion == "") {
                                        cotizacion = "N/A";
                                    }
                                    centro_costo = columnas[9];
                                    referenciag = columnas[11];
                                    if (referenciag == null || referenciag == "") {
                                        referenciag = "N/A";
                                    }
                                    proyecto = columnas[10];
                                    if (proyecto == null || proyecto == "") {
                                        proyecto = "N/A";
                                    }
                                    accion = jpa_requisicion.registrarMasivo(referenciap, elemento, cantidad, marca, destino, fechaE, clasificacion, unidad, prioridad, nombre, id_area, "N/A", cotizacion, centro_costo, referenciag, proyecto);
                                } catch (Exception e) {
                                    accion = false;
                                }
                            } else {
                                accion = false;
                            }
                        }
                        if (accion) {
                            request.setAttribute("Alerta", "Registro_Masivo");
                            request.setAttribute("var1", filas.length);
                        } else {
                            request.setAttribute("Alerta", "ErrorReg_Masivo");
                        }
                        request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 21:
                    //<editor-fold defaultstate="collapsed" desc="DEVOLUCIÓN">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }

                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 7;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[0].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("estado", estado);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloDevolucion");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 22:
                    //<editor-fold defaultstate="collapsed" desc="FECHA ESTIMADA SOBREPASADA - REPORTE DE REQUISICION">
                    accion = correo.ReporteRequisiciones();
                    response.sendRedirect("http://172.16.2.117:8084/Aplicativos_Plastitec/Automatic_servlets.jsp");
                    break;
                //</editor-fold>
                case 24:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE DE COTIZACION MASIVO">
                    int cont_reg = 0;
                    cotizacion2 = request.getParameter("Txt_ids");
                    cotizacion = request.getParameter("Txt_cotizacion");
                    descripcion = request.getParameter("Txt_descripcion2");
                    FechaC = request.getParameter("Txt_fechaDetalle");
                    cotizacion2 = cotizacion2.replace("][", "-").replace("[", "").replace("]", "");
                    String[] idC2 = cotizacion2.split("-");
                    if (descripcion == "") {
                        request.setAttribute("Alerta", "ErrorDetalle");
                    } else {
                        for (int j = 0; j < idC2.length; j++) {
                            accion = jpa_requisicion.DetalleCotizacion(Integer.parseInt(idC2[j]), cotizacion, descripcion, nombre, FechaC, 2);
                            if (accion) {
                                jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idC2[j].toString()), nombre);
                            }
                        }
                    }
                    request.getRequestDispatcher("Requisicion?opc=26&Txt_ids3=" + cotizacion2 + "").forward(request, response);
                    break;
                //</editor-fold>
                case 25:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE OC MASIVO">
                    int cont_re = 0;
                    cotizacion3 = request.getParameter("Txt_ids");
                    descripcion = request.getParameter("Txt_descripcion3");
                    FechaO = request.getParameter("Txt_fechaDetalle");
                    fecha_prov = request.getParameter("Txt_fechaProv");
                    Ocompra = request.getParameter("Txt_Ocompra");
                    proveedor = request.getParameter("Txt_proveedor");
                    importarcion = Integer.parseInt(request.getParameter("Txt_importancion"));
                    cotizacion3 = cotizacion3.replace("][", "-").replace("[", "").replace("]", "");
                    String[] idC3 = cotizacion3.split("-");
                    int idsokc = 0;
                    String ids_okc = "";
                    String ids_failc = "";

                    if (cotizacion3 != "") {
                        for (int j = 0; j < idC3.length; j++) {
                            accion = jpa_requisicion.DetalleOrdenCompra(Integer.parseInt(idC3[j]), descripcion, nombre, FechaO, fecha_prov, proveedor, Ocompra, importarcion);
                            if (accion) {
                                if (ids_okc.length() > 0) {
                                    ids_okc += "-" + Integer.parseInt(idC3[j].toString()) + "";
                                    jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idC3[j].toString()), nombre);
                                } else {
                                    ids_okc = Integer.parseInt(idC3[j].toString()) + "";
                                    jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idC3[j].toString()), nombre);
                                }
                                idsokc++;
                            } else {
                                ids_failc += "[" + Integer.parseInt(idC3[j].toString()) + "]";
                            }
                        }
                    }
                    request.getRequestDispatcher("Requisicion?opc=5&Txt_ids=" + cotizacion3 + "&estado=4&estado2=3").forward(request, response);
                    break;
                //</editor-fold>
                case 26:
                    //<editor-fold defaultstate="collapsed" desc="CONSTRUIR COTIZACION">
                    int cont_r = 0;
                    cotizacionCor = request.getParameter("Txt_ids3");
                    cotizacionCor = cotizacionCor.replace("][", "-").replace("[", "").replace("]", "");
                    String[] idCCor = cotizacionCor.split("-");
                    if (cotizacionCor == "") {
                        request.setAttribute("Alerta", "ErrorDetalle");
                    } else {
                        cont_r++;
                        if (cont_r > 0) {
                            request.setAttribute("Alerta", "ActualizarDetalle");
                        } else {
                            request.setAttribute("Alerta", "ErrorDescripcion");
                        }
                    }
                    request.getRequestDispatcher("Requisicion?opc=36&estado=2&Txt_ids=" + cotizacionCor + "").forward(request, response);

                    break;
                //</editor-fold>
                case 28:
                    //<editor-fold defaultstate="collapsed" desc="CORREO AREA PARA PROOVEDOR POR SELECCION Y GEN">
                    cotizacion3 = request.getParameter("Txt_ids4");
                    try {
                        cotizacion3 = cotizacion3.trim().replace("][", "-").replace("[", "").replace("]", "");
                        String[] idC4 = cotizacion3.split("-");
                        for (int i = 0; i < idC4.length; i++) {
                            accion = jpa_requisicion.ElementoCotizado(idC4[i]);
                        }
                        accion = correo.CorreoMttoGeneralSeleccion(id_area, idC4);
                        if (accion) {
                            request.setAttribute("Alerta", "EnviarOrden");
                        } else {
                            request.setAttribute("Alerta", "Email_Error");

                        }
                        int Vaco = Integer.parseInt(request.getParameter("validarCheck"));
                        if (Vaco == 1) {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=2").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=5&Txt_ids=" + cotizacion3 + "&estado=8&estado2=2").forward(request, response);
                        }
                    } catch (Exception e) {
                        lst_const_req = jpa_requisicion.consultarRequisicionA(2);
                        if (lst_const_req != null) {
                            for (int i = 0; i < lst_const_req.size(); i++) {
                                Object[] obj_req = (Object[]) lst_const_req.get(i);
                                accion = jpa_requisicion.ElementoCotizado(obj_req[0].toString());
                            }
                            accion = correo.CorreoMttoGeneralGeneral(3);
                        }
                        if (accion) {
                            request.setAttribute("Alerta", "EnviarOrden");
                        } else {
                            request.setAttribute("Alerta", "Email_Error");
                        }
                        request.getRequestDispatcher("Requisicion?opc=36&estado=2").forward(request, response);
                    }
                    break;
                //</editor-fold>
                case 29:
                    //<editor-fold defaultstate="collapsed" desc="HISTORIAL DECLINADAS Y DEVUELTAS">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("modulo"));
                    } catch (Exception e) {
                        modulo = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[0].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("estado", estado);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "Historial_Declinadas_Devueltas");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
//</editor-fold>
                case 30:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE OC/C GENERADOS MASIVO">
                    cotizacionCor = request.getParameter("Txt_ids");
                    String[] idCan = cotizacionCor.split("-");
                    fecha_f = request.getParameter("Txt_fechall");
                    if (cotizacionCor != "") {
                        for (int i = 0; i < idCan.length; i++) {
                            cantidad = Double.parseDouble(request.getParameter("Txt_cantidad" + i + ""));
                            observaciones = request.getParameter("Txt_observacion" + i + "");
                            jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idCan[i].toString()), nombre);
                            jpa_requisicion.DetalleGenerado(Integer.parseInt(idCan[i].toString()), fecha_f, observaciones, cantidad, nombre);
                        }
                    }
                    request.getRequestDispatcher("Requisicion?opc=5&Txt_ids=" + cotizacionCor + "&estado=5&estado2=4").forward(request, response);
                    break;
                //</editor-fold>
                case 31:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE DISPONILIBIDAD MASIVO">
                    cotizacionCor = request.getParameter("Txt_ids");
                    cotizacionCor = cotizacionCor.replace("][", "-").replace("[", "").replace("]", "");
                    String[] idCanDe = cotizacionCor.split("-");
                    DDisp = request.getParameter("Txt_descripcion");
                    rentrega = request.getParameter("txt_entrega");
                    if (cotizacionCor != "") {
                        for (int j = 0; j < idCanDe.length; j++) {
//                            jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idCanDe[j]), nombre);
                            accion = jpa_requisicion.DetalleDisponibilidad(Integer.parseInt(idCanDe[j].toString()), DDisp, rentrega, nombre);
                        }
                    }
                    request.getRequestDispatcher("Requisicion?opc=5&Txt_ids=" + cotizacionCor + "&estado=6&estado2=5").forward(request, response);
                    break;
                //</editor-fold>
                case 33:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE REPORTE">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        dias_vencidos = Integer.parseInt(request.getParameter("dias_vencidos"));
                    } catch (Exception e) {
                        dias_vencidos = 0;
                    }
                    try {
                        query = request.getParameter("query");
                    } catch (Exception e) {
                        query = "";
                    }
                    try {
                        arg_requisicion = request.getParameter("txt_arg_requisicion");
                    } catch (Exception e) {
                        arg_requisicion = "[0]";
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[0].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("estado", estado);
                    request.setAttribute("dias_vencidos", dias_vencidos);
                    request.setAttribute("query", query);
                    request.setAttribute("arg_observacion", arg_requisicion);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloReporte");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 34:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE BUSQUEDA REPORTE">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    estado = Integer.parseInt(request.getParameter("slc_estado"));
                    consulta = request.getParameter("slc_filtro");
                    fto_esp = request.getParameter("fto");
                    String[] filtro = fto_esp.replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < filtro.length; i++) {
                        if (i != (filtro.length - 1)) {
                            condicion = condicion + "r.elemento LIKE '%" + filtro[i] + "%' OR ";
                        } else {
                            condicion = condicion + "r.elemento LIKE '%" + filtro[i] + "%')";
                        }
                    }
                    query = "SELECT a.nombre AS 'Área',r.id_requisicion AS '#Req',r.elemento AS 'Elemento', "
                            + "	CASE "
                            + "		WHEN r.estado = 0 THEN 'Declinado' "
                            + "		WHEN r.estado = 1 THEN 'Solicitado' "
                            + "		WHEN r.estado = 2 THEN 'Por cotizar' "
                            + "		WHEN r.estado = 3 THEN 'Pendiente O.C' "
                            + "		WHEN r.estado = 4 THEN 'Pendiente entrega' "
                            + "		WHEN r.estado = 5 THEN 'Recibido' "
                            + "		WHEN r.estado = 6 THEN 'Entregado' "
                            + "		ELSE 'Devuelto' "
                            + "	END AS 'Estado', "
                            + " IF(r.prioridad=0,'Normal','Alta') AS 'Prioridad',r.cantidad as 'Cant. Solicitada', DATE_FORMAT(r.fecha_solictud,'%Y-%m-%d')  as 'Fecha_solicitud',"
                            + " r.cantidad1 as 'Cant. recibida', DATE_FORMAT(r.fecha_estimada,'%Y-%m-%d') as 'Fecha_estimada', DATE_FORMAT(r.fecha_etg_prov,'%Y-%m-%d') as 'Fecha_proveedor',r.orden_compra as 'OC',"
                            + "	CASE "
                            + "	WHEN (CASE WHEN NOW() > r.fecha_etg_prov THEN DATEDIFF(now(),r.fecha_etg_prov) ELSE DATEDIFF(now(),r.fecha_estimada)end) " + (consulta.equals("CVD") ? ">= 0" : "<= 0") + "  "
                            + "		THEN (CASE WHEN NOW() > r.fecha_etg_prov then DATEDIFF(now(),r.fecha_etg_prov) ELSE DATEDIFF(now(),r.fecha_estimada)end) else 'N/A' "
                            + " END as 'Dias vencidos',r.obs_reporte,r.importacion "
                            + " FROM requisicion_material r INNER JOIN area a ON r.id_area = a.id_area "
                            + " WHERE (r.fecha_solictud >= '" + fecha_i + "' AND (r.fecha_etg_prov <= '" + fecha_f + "' OR r.fecha_estimada <= '" + fecha_f + "' )) AND r.estado = " + estado + " " + (fto_esp == null ? "" : "AND (" + condicion + "") + ""
                            + " ORDER BY r.prioridad = 0,"
                            + " CASE "
                            + "     WHEN NOW() > r.fecha_etg_prov THEN DATEDIFF(NOW(),r.fecha_etg_prov) "
                            + "     ELSE DATEDIFF(NOW(),r.fecha_estimada) "
                            + "     END DESC";
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        dias_vencidos = Integer.parseInt(request.getParameter("dias_vencidos"));
                    } catch (Exception e) {
                        dias_vencidos = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    arg_requisicion = "[0]";
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("dias_vencidos", dias_vencidos);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("estado", estado);
                    request.setAttribute("query", query);
                    request.setAttribute("arg_observacion", arg_requisicion);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloReporte");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 35:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR OBSERVACION REPORTE">
                    arg_requisicion = request.getParameter("txt_arg_requisicion");
                    observaciones = request.getParameter("txt_observacion");
                    idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    if (arg_requisicion != null) {
                        if (arg_requisicion.contains("[") || arg_requisicion.contains("][")) {
                            String[] arr_requisicion = arg_requisicion.replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < arr_requisicion.length; i++) {
                                int idR = Integer.parseInt(arr_requisicion[i]);
                                accion = jpa_requisicion.RegistrarObservacion(idR, observaciones);
                            }
                        }
                    } else {
                        accion = jpa_requisicion.RegistrarObservacion(idRequisicion, observaciones);
                    }
                    request.getRequestDispatcher("Requisicion?opc=33&query=").forward(request, response);
                    break;
                //</editor-fold>
                case 36:
                    //<editor-fold defaultstate="collapsed" desc="MODULO DE REQUISICION">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    cotizacionCor = request.getParameter("Txt_ids");
                    if (cotizacionCor == null) {
                        cotizacionCor = "";
                    }
                    try {
                        limit = Integer.parseInt(request.getParameter("limit"));
                    } catch (Exception e) {
                        limit = 250;
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 100;
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 0;
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("modulo"));
                    } catch (Exception e) {
                        modulo = 0;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        estado_fl = Integer.parseInt(request.getParameter("txt_estado"));
                    } catch (Exception e) {
                        estado_fl = 0;
                    }
                    try {
                        area = Integer.parseInt(request.getParameter("slt_area"));
                    } catch (Exception e) {
                        area = 0;
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[4].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    try {
                        history = Integer.parseInt(request.getParameter("history"));
                    } catch (Exception e) {
                        history = 0;
                    }
                    try {
                        campo = Integer.parseInt(request.getParameter("Cmp_filter"));
                    } catch (Exception e) {
                        campo = 0;
                    }
                    try {
                        buscar = request.getParameter("Txt_buscar");
                    } catch (Exception e) {
                        buscar = "";
                    }
                    request.setAttribute("limit", limit);
                    request.setAttribute("Txt_ids", cotizacionCor);
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("id_var", var_filtro);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("estado", estado);
                    request.setAttribute("estado_flt", estado_fl);
                    request.setAttribute("sel_area", area);
                    request.setAttribute("history", history);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("campo", campo);
                    request.setAttribute("buscar", buscar);
                    request.setAttribute("Requisicion", "ModuloRequisicion");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 37:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE PROCESO DE COMPRA">
                    idSolicitud = Integer.parseInt(request.getParameter("idCotizacion"));
                    FechaC = request.getParameter("Txt_fechaDetalle");
                    descripcion = request.getParameter("Txt_descripcion");
                    modulo = Integer.parseInt(request.getParameter("modulo"));
                    if (descripcion.equals("")) {
                        request.setAttribute("Alerta", "ErrorDetalle");
                        request.getRequestDispatcher("Requisicion?opc=" + modulo).forward(request, response);
                    } else {
//                        jpa_requisicion.registrarLogRequisicion(idSolicitud);
                        accion = jpa_requisicion.actualizarProcesoCompra(idSolicitud, descripcion, FechaC, nombre);
                        if (accion) {
                            request.setAttribute("Alerta", "ActualizarDetalle");
                            jpa_requisicion.registrarLogRequisicion(idSolicitud, nombre);
                        } else {
                            request.setAttribute("Alerta", "ErrorDescripcion");
                        }
                        if (modulo == 6) {
                            request.getRequestDispatcher("Requisicion?opc=39").forward(request, response);
                        } else {
                            request.getRequestDispatcher("Requisicion?opc=36&estado=" + modulo + "").forward(request, response);
                        }
                    }
                    break;
                //</editor-fold>
                case 38:
                    //<editor-fold defaultstate="collapsed" desc="DETALLE PROCESO DE COMPRA MASIVO">
                    int cont_pc = 0;
                    cotizacion2 = request.getParameter("Txt_ids");
                    descripcion = request.getParameter("Txt_descripcion");
                    FechaC = request.getParameter("Txt_fechaDetalle");
                    cotizacion2 = cotizacion2.replace("][", "-").replace("[", "").replace("]", "");
                    String[] idC8 = cotizacion2.split("-");
                    int idsopc = 0;
                    String ids_okpc = "";
                    String ids_failpc = "";
                    if (descripcion == "") {
                        request.setAttribute("Alerta", "ErrorDetalle");
                    } else {
                        for (int j = 0; j < idC8.length; j++) {
                            accion = jpa_requisicion.DetalleProcesoCompra(Integer.parseInt(idC8[j]), descripcion, nombre, FechaC);
                            if (accion) {
                                if (ids_okpc.length() > 0) {
                                    ids_okpc += "-" + Integer.parseInt(idC8[j].toString()) + "";
                                    jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idC8[j].toString()), nombre);
                                } else {
                                    ids_okpc = Integer.parseInt(idC8[j].toString()) + "";
                                    jpa_requisicion.registrarLogRequisicion(Integer.parseInt(idC8[j].toString()), nombre);
                                }
                                idsopc++;
                            } else {
                                ids_failpc += "[" + Integer.parseInt(idC8[j].toString()) + "]";
                            }
                        }
                        accion = correo.CorreoMttoGeneralSeleccionPC(id_area, idC8);
                    }
                    request.getRequestDispatcher("Requisicion?opc=5&Txt_ids=" + cotizacion2 + "&estado=3&estado2=8").forward(request, response);
                    break;
                //</editor-fold>
                case 39:
                    //<editor-fold defaultstate="collapsed" desc="MODULO ENTREGADO">
                    fecha_i = request.getParameter("fch_inicio");
                    fecha_f = request.getParameter("fch_fin");
                    try {
                        limit = Integer.parseInt(request.getParameter("limit"));
                    } catch (Exception e) {
                        limit = 500;
                    }
                    try {
                        var_filtro = Integer.parseInt(request.getParameter("id_var"));
                    } catch (Exception e) {
                        var_filtro = 0;
                    }
                    try {
                        modulo = Integer.parseInt(request.getParameter("modulo"));
                    } catch (Exception e) {
                        modulo = 0;
                    }
                    try {
                        prioridad = Integer.parseInt(request.getParameter("prioridad"));
                    } catch (Exception e) {
                        prioridad = 2;
                    }
                    try {
                        idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    } catch (Exception e) {
                        idRequisicion = 0;
                    }
                    try {
                        estado = Integer.parseInt(request.getParameter("estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        anio = Integer.parseInt(request.getParameter("anio"));
                    } catch (Exception e) {
                        anio = cal.get(Calendar.YEAR);
                    }
                    if (fecha_f == null || fecha_f == "") {
                        try {
                            fecha_f = obj_fec[4].toString();
                        } catch (Exception e) {
                            fecha_f = obj_fec[4].toString();
                        }
                    }
                    if (fecha_i == null || fecha_i == "") {
                        try {
                            fecha_i = obj_fec[1].toString();
                        } catch (Exception e) {
                            fecha_i = "2019-10-01 00:00:01";
                        }
                    }
                    try {
                        area = Integer.parseInt(request.getParameter("slt_area"));
                    } catch (Exception e) {
                        area = 0;
                    }
                    request.setAttribute("sel_area", area);
                    request.setAttribute("limit", limit);
                    request.setAttribute("anio", anio);
                    request.setAttribute("fch_inicio", fecha_i);
                    request.setAttribute("fch_fin", fecha_f);
                    request.setAttribute("id_var", var_filtro);
                    request.setAttribute("prioridad", prioridad);
                    request.setAttribute("idRequisicion", idRequisicion);
                    request.setAttribute("estado", 6);
                    request.setAttribute("modulo", modulo);
                    request.setAttribute("Requisicion", "ModuloEntregado");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
                case 40:
                    //<editor-fold defaultstate="collapsed" desc="DUPLICAR REQUISICIÓN">
                    idRequisicion = Integer.parseInt(request.getParameter("idRequisicion"));
                    observaciones = request.getParameter("Txt_observaciones");
                    accion = jpa_requisicion.registrarRequisicionDuplicada(idRequisicion, nombre, observaciones);
                    if (accion) {
                        request.setAttribute("Alerta", "Duplicar_Requisicion");
                    } else {
                        request.setAttribute("Alerta", "ErrorDescripcion");
                    }
                    request.getRequestDispatcher("Requisicion?opc=1&idRequisicion=0").forward(request, response);
                //</editor-fold>
                case 41:
                    //<editor-fold defaultstate="collapsed" desc="FILTRO AREA Y ESTADO">
                    try {
                        estado = Integer.parseInt(request.getParameter("txt_estado"));
                    } catch (Exception e) {
                        estado = 0;
                    }
                    try {
                        area = Integer.parseInt(request.getParameter("slt_area"));
                    } catch (Exception e) {
                        area = 0;
                    }
                    request.setAttribute("estado_flt", estado);
                    request.setAttribute("slt_area", area);
                    request.setAttribute("Requisicion", "ModuloRequisicion");
                    request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
                    break;
                //</editor-fold>
            }
        } catch (Exception e) {
            request.getRequestDispatcher("Requisicion.jsp").forward(request, response);
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
