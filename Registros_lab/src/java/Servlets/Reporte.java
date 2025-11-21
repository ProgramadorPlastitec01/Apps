package Servlets;

import Controladores.RegistroFrecuenciaHoraJpaController;
import Controladores.RegistroJpaController;
import Controladores.ResumenJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Controladores.ParamJpaController;

public class Reporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        try {
            //Sesion
            HttpSession sesion = request.getSession();
            //JPAS
            RegistroFrecuenciaHoraJpaController jpacrfh = new RegistroFrecuenciaHoraJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            ResumenJpaController jpacrsm = new ResumenJpaController();
            ParamJpaController jpapam = new ParamJpaController();
//JAVA CALENDAR
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
//Variables Globales
            int opc = Integer.parseInt(request.getParameter("opc").toString());
            String tipo = "";
            int tipo_report = 0;
            String tipo_oee = "";
            String filtro_primario = "";
            String agrupacion_oee = "";
            int orden = 0;
            int id_producto = 0;
            int id_resumen = 0;
            String lote = "";
            int id_linea = 0;
            int id_ficha_tecnica = 0;
            int val = 0;
            String ciclo = "";
            String datos_totales = "";
            String codigo_producto = "";
            String producto = "0";
            String lote_report = "0";
            String volumen = "";
            String filtro = "";
            String fecha_inicio = "";
            String observacion = "";
            String fecha_fin = "";
            String hora_inicio = "";
            String hora_fin = "";
            String numero_certificado = "";
            String fecha_despacho = "";
            String usuario_responsable = "";
            String turno = "";
            String loteCola = "";
            String FchI = "";
            String FchF = "";
            List lst_registros = null;
            List lst_pram = null;
            boolean proceso = true;
            String[] lote_arg = null;
            switch (opc) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="REGISTROS RESUMIDOS">
                    tipo = "Registros_resumidos";
                    id_resumen = Integer.parseInt(request.getParameter("irs").toString());
                    try {
                        val = Integer.parseInt(request.getParameter("Val").toString());
                    } catch (Exception e) {
                        val = 2;
                    }
                    try {
                        year = Integer.parseInt(request.getParameter("Cbx_anio"));
                    } catch (Exception e) {
                        year = cal.get(Calendar.YEAR);
                    }
                    try {
                        FchI = request.getParameter("FchI").toString();
                    } catch (Exception e) {
                        lst_pram = jpapam.ConsultarParametrosxCategoria("RangoResumen");
                        if (lst_pram != null) {
                            Object[] ObjPram = (Object[]) lst_pram.get(0);
                            int Ms = Integer.parseInt(ObjPram[2].toString());
                            cal.add(Calendar.MONTH, -Ms);
                            FchI = String.format("%tF", cal); // Formato yyyy-MM-dd
                        } else {
                            cal.add(Calendar.MONTH, -3);
                            FchI = String.format("%tF", cal); // Formato yyyy-MM-dd
                        }
                    }
                    try {
                        FchF = request.getParameter("FchF").toString();
                    } catch (Exception e) {
                        FchF = String.format("%tF", Calendar.getInstance()); // Formato yyyy-MM-dd
                    }
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Anio", year);
                    request.setAttribute("Val", val);
                    request.setAttribute("FchI", FchI);
                    request.setAttribute("FchF", FchF);
                    if (id_resumen > 0) {
                        request.setAttribute("Id_resumen", id_resumen);
                    } else {
                        request.setAttribute("Id_resumen", 0);
                    }
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 2:
                    //<editor-fold defaultstate="collapsed" desc="DATOS ESTADISTICOS">
                    tipo = "Datos_estadisticos";
                    request.setAttribute("Reporte", tipo);
                    codigo_producto = request.getParameter("cpd");
                    if (!codigo_producto.equals("0")) {
                        producto = request.getParameter("Cbx_producto");
                        if (!producto.equals("0")) {
                            id_ficha_tecnica = Integer.parseInt(request.getParameter("Cbx_ficha_tecnica"));
                            tipo_report = Integer.parseInt(request.getParameter("Rdb_tipo"));
                            if (id_ficha_tecnica > 0 && tipo_report == 0) {
                                lote_report = request.getParameter("Cbx_lote");
                            }
                        }
                    }
                    request.setAttribute("Codigo_producto", codigo_producto);
                    request.setAttribute("Producto", producto);
                    request.setAttribute("Id_ficha_tecnica", id_ficha_tecnica);
                    request.setAttribute("Tipo", tipo_report);
                    request.setAttribute("Lote", lote_report);
                    request.setAttribute("loteCola", loteCola);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR RESUMEN">
                    try {
                        loteCola = request.getParameter("loteCola").toString();
                    } catch (Exception e) {
                        loteCola = "1463-33F13";
                    }
                    tipo = "Reporte_R-GC-017_guardado";
                    orden = Integer.parseInt(request.getParameter("Txt_orden").toString());
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    lote_arg = request.getParameter("Cbx_lote").toString().split(" / ");
                    lote = lote_arg[0];
                    id_linea = Integer.parseInt(lote_arg[1].toString());
                    ciclo = lote_arg[2].toString();
                    fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                    fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    hora_inicio = request.getParameter("Txt_hora_inicio").toString();
                    hora_fin = request.getParameter("Txt_hora_fin").toString();
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    datos_totales = request.getParameter("Txt_datos_totales").toString();
                    if (hora_inicio.equals("00:00")) {
                        hora_inicio = "00:08";
                    }
                    if (hora_fin.equals("00:00")) {
                        hora_fin = "00:08";
                    }
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Ciclo", ciclo);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.setAttribute("Datos_totales", datos_totales);
                    request.setAttribute("loteCola", loteCola);
                    request.setAttribute("Usuario_responsable", sesion.getAttribute("Rol/Nombres").toString());
                    lst_registros = jpacrfh.Registros_lote(lote, id_producto, orden, id_linea, ciclo, fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin);
                    jpacrsm.Registrar_resumen(numero_certificado, orden + "", id_producto + "", lote, id_linea, lst_registros.size() + "", fecha_inicio + " " + hora_inicio, fecha_fin + " " + hora_fin, fecha_despacho, sesion.getAttribute("Rol/Nombres").toString(), ciclo);
                    for (int i = 0; i < lst_registros.size(); i++) {
                        Object[] obj_registros = (Object[]) lst_registros.get(i);
                        jpacrgt.Resumir_registro((Integer) obj_registros[0]);
                    }
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 4:
                    //<editor-fold defaultstate="collapsed" desc="REPORTE R-GC-017">
                    try {
                        loteCola = request.getParameter("loteCola").toString();
                    } catch (Exception e) {
                        loteCola = "";
                    }
                    tipo = "Reporte_R-GC-017_guardado";
                    orden = Integer.parseInt(request.getParameter("Txt_orden").toString());
                    id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    try {
                        lote_arg = request.getParameter("Cbx_lote").toString().split(" / ");
                        lote = lote_arg[0];
                        id_linea = Integer.parseInt(lote_arg[1].toString());
                        ciclo = lote_arg[2].toString();
                    } catch (Exception e) {
                        lote = request.getParameter("Cbx_lote").toString();
                    }
                    fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                    fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    hora_inicio = request.getParameter("Txt_hora_inicio").toString();
                    hora_fin = request.getParameter("Txt_hora_fin").toString();
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    try {
                        datos_totales = request.getParameter("Txt_datos_totales").toString();
                        usuario_responsable = request.getParameter("Txt_usuario_responsable").toString();
                    } catch (Exception e) {
                        datos_totales = "";
                        usuario_responsable = "";
                    }

                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Ciclo", ciclo);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.setAttribute("Datos_totales", datos_totales);
                    request.setAttribute("Usuario_responsable", usuario_responsable);
                    request.setAttribute("loteCola", loteCola);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 5:
                    //<editor-fold defaultstate="collapsed" desc="COMPLETAR RESUMEN">
                    id_resumen = Integer.parseInt(request.getParameter("Id_resumen"));
                    numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                    fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    observacion = request.getParameter("Txt_descripcion");
                    proceso = jpacrsm.Completar_resumen(id_resumen, numero_certificado, fecha_despacho, observacion);
                    if (proceso) {
                        request.setAttribute("Alerta", "Completar_resumen");
                    } else {
                        request.setAttribute("Alerta", "Error_completar_resumen");
                    }
                    request.getRequestDispatcher("Reporte?opc=1&irs=0&fto=").forward(request, response);
                    //</editor-fold>
                    break;
                case 6:
                    //<editor-fold defaultstate="collapsed" desc="REPORTE OEE">
                    tipo = "Reporte_OEE";
                    filtro_primario = request.getParameter("Rdb_filtro_primario");
                    codigo_producto = request.getParameter("Txt_cod_producto").toString();
                    id_linea = Integer.parseInt(request.getParameter("Cbx_linea").toString());
                    volumen = request.getParameter("Cbx_volumen").toString();
                    fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                    fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                    turno = request.getParameter("Cbx_turno").toString();
                    tipo_oee = request.getParameter("Rdb_tipo_oee").toString();
                    agrupacion_oee = request.getParameter("Rdb_agrupacion_oee").toString();
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Filtro_primario", filtro_primario);
                    request.setAttribute("Codigo_producto", codigo_producto);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Volumen", volumen);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Turno", turno);
                    request.setAttribute("Tipo_oee", tipo_oee);
                    request.setAttribute("Agrupacion_oee", agrupacion_oee);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 7:
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DEL DÍA">
                    tipo = "Registros_dia";
                    filtro = request.getParameter("fto").toString();
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Filtro", filtro);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 8:
                    //<editor-fold defaultstate="collapsed" desc="REPORTE R-GC-017">
                    tipo = "Reporte_R-GC-017";
                    try {
                        orden = Integer.parseInt(request.getParameter("Txt_orden").toString());
                    } catch (Exception e) {
                        orden = 0;
                    }
                    try {
                        id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    } catch (Exception e) {
                        id_producto = 0;
                    }
                    try {
                        lote_arg = request.getParameter("Cbx_lote").toString().split(" / ");
                        lote = lote_arg[0];
                        id_linea = Integer.parseInt(lote_arg[1].toString());
                        ciclo = lote_arg[2].toString();
                    } catch (Exception e) {
                        lote = "0";
                        id_linea = 0;
                        ciclo = "0";
                    }
                    try {
                        fecha_inicio = request.getParameter("Txt_fecha_inicio").toString();
                        fecha_fin = request.getParameter("Txt_fecha_fin").toString();
                        hora_inicio = request.getParameter("Txt_hora_inicio").toString();
                        hora_fin = request.getParameter("Txt_hora_fin").toString();
                    } catch (Exception e) {
                        fecha_inicio = "0";
                        fecha_fin = "0";
                        hora_inicio = "0";
                        hora_fin = "0";
                    }
                    try {
                        numero_certificado = request.getParameter("Txt_numero_certificado").toString();
                        fecha_despacho = request.getParameter("Txt_fecha_despacho").toString();
                    } catch (Exception e) {
                        numero_certificado = "0";
                        fecha_despacho = "0";
                    }
                    request.setAttribute("Reporte", tipo);
                    request.setAttribute("Orden", orden);
                    request.setAttribute("Producto", id_producto);
                    request.setAttribute("Lote", lote);
                    request.setAttribute("Linea", id_linea);
                    request.setAttribute("Ciclo", ciclo);
                    request.setAttribute("Fecha_inicio", fecha_inicio);
                    request.setAttribute("Fecha_fin", fecha_fin);
                    request.setAttribute("Hora_inicio", hora_inicio);
                    request.setAttribute("Hora_fin", hora_fin);
                    request.setAttribute("Numero_certificado", numero_certificado);
                    request.setAttribute("Fecha_despacho", fecha_despacho);
                    request.getRequestDispatcher("Reportes.jsp").forward(request, response);
                    //</editor-fold>
                    break;
                case 9:
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DATOS CORRECION ERRORES">
                    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
                    String IdsRegistro = "",
                     lote_cola = "",
                     ensamble = "",
                     ensamble_2 = "",
                     lote_ensamble = "",
                     lote_ensamble_2 = "",
                     ensamble_3 = "",
                     ensamble_4 = "",
                     lote_ensamble_3 = "",
                     lote_ensamble_4 = "",
                     ciclo_esterilizacion = "",
                     lote_tubo_refuerzo = "",
                     lote_manga_c = "",
                     lote_manga_p = "",
                     lote_dto_drc_c = "",
                     lote_dto_drc_p = "",
                     lote_dto_ctl_c = "",
                     lote_dto_ctl_p = "",
                     lote_dto_izq_c = "",
                     lote_dto_izq_p = "",
                     color_tinta = "",
                     lote_tinta = "";
                    //</editor-fold>
                    try {
                        IdsRegistro = request.getParameter("IdsRegistro");
                    } catch (Exception e) {
                        IdsRegistro = "";
                    }
                    try {
                        orden = Integer.parseInt(request.getParameter("Txt_orden").toString());
                    } catch (Exception e) {
                        orden = 0;
                    }
                    try {
                        id_producto = Integer.parseInt(request.getParameter("Cbx_producto").toString());
                    } catch (Exception e) {
                        id_producto = 0;
                    }
                    if (!IdsRegistro.equals("")) {
                        //<editor-fold defaultstate="collapsed" desc="RECIBIR VARIABLES">
                        try {
                            lote_cola = request.getParameter("lote_cola");
                        } catch (Exception e) {
                            lote_cola = "";
                        }
                        try {
                            ensamble = request.getParameter("ensamble");
                        } catch (Exception e) {
                            ensamble = "";
                        }
                        try {
                            ensamble_2 = request.getParameter("ensamble_2");
                        } catch (Exception e) {
                            ensamble_2 = "";
                        }
                        try {
                            lote_ensamble = request.getParameter("lote_ensamble");
                        } catch (Exception e) {
                            lote_ensamble = "";
                        }
                        try {
                            lote_ensamble_2 = request.getParameter("lote_ensamble_2");
                        } catch (Exception e) {
                            lote_ensamble_2 = "";
                        }
                        try {
                            ensamble_3 = request.getParameter("ensamble_3");
                        } catch (Exception e) {
                            ensamble_3 = "";
                        }
                        try {
                            ensamble_4 = request.getParameter("ensamble_4");
                        } catch (Exception e) {
                            ensamble_4 = "";
                        }
                        try {
                            lote_ensamble_3 = request.getParameter("lote_ensamble_3");
                        } catch (Exception e) {
                            lote_ensamble_3 = "";
                        }
                        try {
                            lote_ensamble_4 = request.getParameter("lote_ensamble_4");
                        } catch (Exception e) {
                            lote_ensamble_4 = "";
                        }
                        try {
                            ciclo_esterilizacion = request.getParameter("ciclo_esterilizacion");
                        } catch (Exception e) {
                            ciclo_esterilizacion = "";
                        }
                        try {
                            lote_tubo_refuerzo = request.getParameter("lote_tubo_refuerzo");
                        } catch (Exception e) {
                            lote_tubo_refuerzo = "";
                        }
                        try {
                            lote_manga_c = request.getParameter("lote_manga_c");
                        } catch (Exception e) {
                            lote_manga_c = "";
                        }
                        try {
                            lote_manga_p = request.getParameter("lote_manga_p");
                        } catch (Exception e) {
                            lote_manga_p = "";
                        }
                        try {
                            lote_dto_drc_c = request.getParameter("lote_dto_drc_c");
                        } catch (Exception e) {
                            lote_dto_drc_c = "";
                        }
                        try {
                            lote_dto_drc_p = request.getParameter("lote_dto_drc_p");
                        } catch (Exception e) {
                            lote_dto_drc_p = "";
                        }
                        try {
                            lote_dto_ctl_c = request.getParameter("lote_dto_ctl_c");
                        } catch (Exception e) {
                            lote_dto_ctl_c = "";
                        }
                        try {
                            lote_dto_ctl_p = request.getParameter("lote_dto_ctl_p");
                        } catch (Exception e) {
                            lote_dto_ctl_p = "";
                        }
                        try {
                            lote_dto_izq_c = request.getParameter("lote_dto_izq_c");
                        } catch (Exception e) {
                            lote_dto_izq_c = "";
                        }
                        try {
                            lote_dto_izq_p = request.getParameter("lote_dto_izq_p");
                        } catch (Exception e) {
                            lote_dto_izq_p = "";
                        }
                        try {
                            color_tinta = request.getParameter("color_tinta");
                        } catch (Exception e) {
                            color_tinta = "";
                        }
                        try {
                            lote_tinta = request.getParameter("lote_tinta");
                        } catch (Exception e) {
                            lote_tinta = "";
                        }
                        //</editor-fold>
                        proceso = jpacrgt.UpdateMasivoResumen(IdsRegistro, lote_cola, ensamble, ensamble_2, lote_ensamble, lote_ensamble_2, ensamble_3, ensamble_4, lote_ensamble_3, lote_ensamble_4, ciclo_esterilizacion, lote_tubo_refuerzo, lote_manga_c, lote_manga_p, lote_dto_drc_c, lote_dto_drc_p, lote_dto_ctl_c, lote_dto_ctl_p, lote_dto_izq_c, lote_dto_izq_p, color_tinta, lote_tinta);
                        if (proceso) {
                            request.setAttribute("Alerta", "Datos_cambiados");
                        } else {
                            request.setAttribute("Alerta", "Error_comprobador");
                        }
                    } else {
                        request.setAttribute("Alerta", "Error_comprobador");
                    }
                    request.getRequestDispatcher("Reporte?opc=8&Txt_orden=" + orden + "&Cbx_producto=" + id_producto + "").forward(request, response);
                    //</editor-fold>
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("Alerta", "Error_sesion");
            request.getRequestDispatcher("Reportes.jsp").forward(request, response);
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
