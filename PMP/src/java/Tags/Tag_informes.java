package Tags;

import Controladores.EquipoJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.TipoEquipoJpaController;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_informes extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //JPAS
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            EquipoJpaController jpaceqp = new EquipoJpaController();
            //FECHA
            Calendar cal = Calendar.getInstance();
            String anio = cal.get(Calendar.YEAR) + "";
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            //VARIABLES
            String fecha_inicio = "";
            String fecha_fin = "";
            String filtro = "";
            int sum_act_programadas = 0;
            int sum_act_ejecutadas = 0;
            double sum_tiempo_ejecutado = 0;
            List lst_anios = null;
            List lst_equipos = null;
            List lst_devoluciones = null;
            List lst_eliminaciones = null;
            List lst_semanas = null;
            List lst_informe_historial = null;
            List lst_tipo_equipos = null;
            List lst_informe_actividades = null;
            List lst_informe_actividades_estadisticos = null;
            if (pageContext.getRequest().getAttribute("Informes") != null) {
                // <editor-fold defaultstate="collapsed" desc="INFORME DE ACTIVIDADES">
                if (pageContext.getRequest().getAttribute("Informes").toString().equals("Actividades")) {
                    int anio_send = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    out.print("<div id='content_sin'>");
                    if (anio_send == 0) {
                        anio_send = Integer.parseInt(anio);
                    }
                    out.print("<h3>Informe de actividades " + anio_send);
                    lst_anios = jpacotb.Traer_anios_ot();
                    lst_tipo_equipos = jpacteq.Tipos_equipo();
                    out.print("<div style='float:right'>");
                    out.print("<form action='Informe?opc=1' method='post' id='FormAnio' name='FormAnio'>");
                    out.print("<select name='Cbx_anio' id='Cbx_anio' onchange='PostBackAnio()'>");
                    for (int i = 0; i < lst_anios.size(); i++) {
                        Object[] obj_anios = (Object[]) lst_anios.get(i);
                        if (anio_send == 0 && i == 0) {
                            anio_send = Integer.parseInt(anio);
                        }
                        if (anio_send == Integer.parseInt(obj_anios[0].toString())) {
                            out.print("<option value='" + obj_anios[0] + "' selected> Año " + obj_anios[0] + " #OT " + obj_anios[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_anios[0] + "'> Año " + obj_anios[0] + " #OT " + obj_anios[1] + "</option>");
                        }
                    }
                    out.print("</select></form></div></h3>");
                    out.print("<div style='float:left;width:300px'>"
                            + "<a onclick=\"tableToExcel('Excel', 'Informe de actividades " + anio_send + "')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF <br />"
                            //                            + "<input name='key' type='text' id='key' placeholder='Buscar' onkeyup='buscar(this.value)' />"
                            + "</div>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table3' id='Excel' style='width:100%'><tr><td>");
                    for (int i = 0; i < lst_tipo_equipos.size(); i++) {
                        Object[] obj_tipos_equipos = (Object[]) lst_tipo_equipos.get(i);
//                        out.print("<tr>");
//                        out.print("<th colspan='26'>" + obj_tipos_equipos[1] + "</th>");
//                        out.print("</tr>");
                        int count_equipo = 0;
                        String equipo_actual = "";
                        String equipo_anterior = "";
                        lst_informe_actividades = jpacotb.Informe_actividades(obj_tipos_equipos[1].toString(), anio_send);
                        if (lst_informe_actividades == null || lst_informe_actividades.isEmpty()) {
                        } else {
                            out.print("<button class='accordion'>" + obj_tipos_equipos[1] + "</button>");
                            out.print("<div class='panel'>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td rowspan='2' align='center'><b>Equipo</b></td>");
                            out.print("<td rowspan='2' align='center'><b>OT</b></td>");
                            out.print("<td colspan='2' align='center'><b>ENE</b></td>");
                            out.print("<td colspan='2' align='center'><b>FEB</b></td>");
                            out.print("<td colspan='2' align='center'><b>MAR</b></td>");
                            out.print("<td colspan='2' align='center'><b>ABR</b></td>");
                            out.print("<td colspan='2' align='center'><b>MAY</b></td>");
                            out.print("<td colspan='2' align='center'><b>JUN</b></td>");
                            out.print("<td colspan='2' align='center'><b>JUL</b></td>");
                            out.print("<td colspan='2' align='center'><b>AGO</b></td>");
                            out.print("<td colspan='2' align='center'><b>SEP</b></td>");
                            out.print("<td colspan='2' align='center'><b>OCT</b></td>");
                            out.print("<td colspan='2' align='center'><b>NOV</b></td>");
                            out.print("<td colspan='2' align='center'><b>DIC</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            for (int j = 1; j <= 12; j++) {
                                out.print("<td><b>Cant.</b></td>");
                                out.print("<td><b>min</b></td>");
                            }
                            out.print("</tr>");
                            for (int j = 0; j < lst_informe_actividades.size(); j++) {
                                Object[] obj_informe_actividades = (Object[]) lst_informe_actividades.get(j);
                                out.print("<tr>");
                                for (int k = 0; k < lst_informe_actividades.size(); k++) {
                                    Object[] obj_informe_actividades_count = (Object[]) lst_informe_actividades.get(k);
                                    equipo_actual = obj_informe_actividades[1].toString();
                                    if (obj_informe_actividades_count[1].equals(equipo_actual)) {
                                        count_equipo++;
                                    }
                                }
                                if (!equipo_actual.equals(equipo_anterior)) {
                                    out.print("<td rowspan='" + count_equipo + "'><b style='font-size:11px'>" + obj_informe_actividades[1] + "</b></td>");
                                } else {
                                    count_equipo = 0;
                                }
                                if (count_equipo == 1) {
                                    count_equipo = 0;
                                }
                                equipo_anterior = obj_informe_actividades[1].toString();
                                out.print("<th><a style='color:white' href='Orden_trabajo?opc=3&iot=" + obj_informe_actividades[15] + "' target='_blank'>" + obj_informe_actividades[0] + "</a></th>");
//                              out.print("<td>" + obj_informe_actividades[2] + "</td>");
                                for (int k = 3; k <= 14; k++) {
                                    if (Integer.parseInt(obj_informe_actividades[k].toString().split("/")[1]) == 0) {
                                        out.print("<td style='background-color:#eee' colspan='2'></td>");
                                    } else {
                                        out.print("<td>" + (Integer.parseInt(obj_informe_actividades[k].toString().split("/")[0]) + Integer.parseInt(obj_informe_actividades[k].toString().split("/")[3])) + "<b> / </b>"
                                                + "" + (Integer.parseInt(obj_informe_actividades[k].toString().split("/")[1]) + Integer.parseInt(obj_informe_actividades[k].toString().split("/")[3])) + "</td>");
                                        out.print("<td>" + obj_informe_actividades[k].toString().split("/")[2] + "</td>");
                                    }
                                }
//                                
                                out.print("</tr>");
                            }
                            lst_informe_actividades_estadisticos = jpacotb.Informe_actividades_estadisticos(obj_tipos_equipos[1].toString(), anio_send);
                            for (int j = 0; j < lst_informe_actividades_estadisticos.size(); j++) {
                                Object[] obj_informe_actividades_estadistico = (Object[]) lst_informe_actividades_estadisticos.get(j);
                                out.print("<tr>");
                                out.print("<th colspan='2'>Tiempo Total</th>");
                                for (int k = 1; k <= 12; k++) {
                                    //out.print("<td colspan='2' align='center'><b style='font-size:12px'>" + ((obj_informe_actividades_estadistico[k] == null) ? "" : obj_informe_actividades_estadistico[k]) + "</b></td>");
                                    if (Integer.parseInt(obj_informe_actividades_estadistico[k].toString().split("/")[1]) == 0) {
                                        out.print("<td style='background-color:#eee' colspan='2'></td>");
                                    } else {
                                        out.print("<td><b>" + (Integer.parseInt(obj_informe_actividades_estadistico[k].toString().split("/")[0]) + Integer.parseInt(obj_informe_actividades_estadistico[k].toString().split("/")[3])) + " / "
                                                + "" + (Integer.parseInt(obj_informe_actividades_estadistico[k].toString().split("/")[1]) + Integer.parseInt(obj_informe_actividades_estadistico[k].toString().split("/")[3])) + "</b></td>");
                                        out.print("<td><b>" + obj_informe_actividades_estadistico[k].toString().split("/")[2] + "</b></td>");
                                    }
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("</div>");
                        }
                    }
                    out.print("</td></tr></table>");
                    out.print("</div>");
                    out.print("<script src='Interfaz/Acordeon/Js_accordeon.js'></script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="INFORME HISTORIAL DE HOROMETROS">
                else if (pageContext.getRequest().getAttribute("Informes").toString().equals("Historial_horometros")) {
                    int anio_send = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    int mes_send = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                    if (anio_send == 0) {
                        anio_send = Integer.parseInt(anio);
                    }
                    if (mes_send == 0) {
                        mes_send = Integer.parseInt(mes);
                    }
                    lst_anios = jpacotb.Traer_anios_historial();
                    lst_tipo_equipos = jpacteq.Tipos_equipo();
                    out.print("<div id='content_sin'>");
                    out.print("<h3>Historial actualización horometros");
                    out.print("<div style='float:right;width:300px'>");
                    out.print("<form action='Informe?opc=2' method='post' id='FormAnio' name='FormAnio'>");
                    out.print("<b>AÑO</b> <select name='Cbx_anio' style='width:100px' id='Cbx_anio'>");
                    for (int i = 0; i < lst_anios.size(); i++) {
                        Object[] obj_anios = (Object[]) lst_anios.get(i);
                        if (anio_send == Integer.parseInt(obj_anios[0].toString())) {
                            out.print("<option value='" + obj_anios[0] + "' selected>" + obj_anios[0] + "</option>");
                        } else {
                            out.print("<option value='" + obj_anios[0] + "'>" + obj_anios[0] + "</option>");
                        }
                    }
                    out.print("</select>");
                    String arg_meses[] = {"1) ENERO", "2) FEBRERO", "3) MARZO", "4) ABRIL", "5) MAYO", "6) JUNIO", "7) JULIO", "8) AGOSTO", "9) SEPTIEMBRE", "10) OCTUBRE", "11) NOVIEMBRE", "12) DICIEMBRE"};
                    out.print("<b> MES</b> <select name='Rdb_mes' style='width:100px'>");
                    for (int i = 0; i < 12; i++) {
//                        out.print("<input type='radio' name='Rdb_mes' id='Rdb_mes' value='" + (i + 1) + "' " + (((i + 1) == mes_send) ? "checked" : "") + "> " + arg_meses[i] + " / ");
                        out.print("<option value='" + (i + 1) + "' " + (((i + 1) == mes_send) ? "selected" : "") + "> " + arg_meses[i] + "</option>");
                    }
                    out.print("</select>");
                    out.print("<hr /><input type='submit' value='Generar' style='width:300px' />");
                    out.print("</div></h3>");
                    out.print("</form>");
                    out.print("<div style='float:left;width:300px'>"
                            + "<a onclick=\"tableToExcel('Excel', 'Informe de actividades " + anio_send + "')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF <br />"
                            //                            + "<input name='key' type='text' id='key' placeholder='Buscar' onkeyup='buscar(this.value)' />"
                            + "</div>");
                    //<editor-fold defaultstate="collapsed" desc="tabla_acoordion">
//                    out.print("<div id='Tabla_resalta'>");
                    out.print("<div id='Imprimir'>");
                    out.print("<table class='table3' id='Excel' style='width:100%'><tr><td>");
                    for (int i = 0; i < lst_tipo_equipos.size(); i++) {
                        Object[] obj_tipos_equipos = (Object[]) lst_tipo_equipos.get(i);
                        out.print("<button class='accordion'>" + obj_tipos_equipos[1] + "</button>");
                        lst_semanas = jpacotb.Traer_meses_historial(anio_send, mes_send);
                        if (lst_semanas != null) {
                            out.print("<div class='panel'>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th style='width:15%'>Equipo</th>");
                            for (int j = 0; j < lst_semanas.size(); j++) {
                                Object[] obj_semanas = (Object[]) lst_semanas.get(j);
                                out.print("<th>" + obj_semanas[0] + "</th>");
                            }
                            out.print("<th>Resumen mes</th>");
                            out.print("</tr>");
                            lst_informe_historial = jpacotb.Informe_historial_horometros(obj_tipos_equipos[1].toString(), lst_semanas);
                            if (lst_informe_historial != null) {
                                for (int j = 0; j < lst_informe_historial.size(); j++) {
                                    Object[] obj_informe_historial = (Object[]) lst_informe_historial.get(j);
                                    out.print("<tr>");
                                    out.print("<td><b>" + obj_informe_historial[1] + "</b></td>");
                                    int horometro_inicial = 0;
                                    int horometro_final = 0;
                                    for (int k = 0; k < lst_semanas.size(); k++) {
                                        if (k == 0) {
                                            horometro_inicial = Integer.parseInt(obj_informe_historial[(k + 2)].toString().split(" / ")[1]);
                                        }
                                        if (k == lst_semanas.size() - 1) {
                                            horometro_final = Integer.parseInt(obj_informe_historial[(k + 2)].toString().split(" / ")[1]);
                                        }
                                        out.print("<td>" + obj_informe_historial[(k + 2)].toString().split(" / ")[1] + "</td>");
                                    }
                                    out.print("<td><b>" + (horometro_final - horometro_inicial) + "</b></td>");
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                            out.print("</div>");
                        } else {
                            out.print("<div class='panel'>");
                            out.print("<b class='naranja'>No se encontraro ordenes de trabajo programadas</b>");
                            out.print("</div>");
                        }
                    }
                    out.print("</td></tr></table>");
                    out.print("</div>");
//                    out.print("</div>");
                    out.print("<script src='Interfaz/Acordeon/Js_accordeon.js'></script>");
                    //</editor-fold>
//                    //<editor-fold defaultstate="collapsed" desc="tabla_exportar">
//                    out.print("<div id='Tabla_resalta'>");
//                    out.print("<div id='Imprimir'>");
//                    out.print("<table class='table3' id='Excel' style='width:100%'>");
//                    for (int i = 0; i < lst_tipo_equipos.size(); i++) {
//                        Object[] obj_tipos_equipos = (Object[]) lst_tipo_equipos.get(i);
//                        out.print("<tr>");
//                        out.print("<th>" + obj_tipos_equipos[1] + "</th>");
//                        out.print("</tr>");
//                        lst_semanas = jpacotb.Traer_meses_historial(anio_send, mes_send);
//                        if (lst_semanas != null) {
//                            out.print("<tr>");
//                            out.print("<td>");
//                            out.print("<table class='table1' style='width:100%'>");
//                            out.print("<tr>");
//                            out.print("<td style='width:15%'><b>Equipo</b></td>");
//                            for (int j = 0; j < lst_semanas.size(); j++) {
//                                Object[] obj_semanas = (Object[]) lst_semanas.get(j);
//                                out.print("<td align='center'><b>" + obj_semanas[0] + "</b></td>");
//                            }
//                            out.print("<td style='width:10%'><b>Resumen mes</b></td>");
//                            out.print("</tr>");
//                            lst_informe_historial = jpacotb.Informe_historial_horometros(obj_tipos_equipos[1].toString(), lst_semanas);
//                            for (int j = 0; j < lst_informe_historial.size(); j++) {
//                                Object[] obj_informe_historial = (Object[]) lst_informe_historial.get(j);
//                                out.print("<tr>");
//                                out.print("<td><b>" + obj_informe_historial[1] + "</b></td>");
//                                int horometro_inicial = 0;
//                                int horometro_final = 0;
//                                for (int k = 0; k < lst_semanas.size(); k++) {
//                                    if (k == 0) {
//                                        horometro_inicial = Integer.parseInt(obj_informe_historial[(k + 2)].toString().split(" / ")[1]);
//                                    }
//                                    if (k == lst_semanas.size() - 1) {
//                                        horometro_final = Integer.parseInt(obj_informe_historial[(k + 2)].toString().split(" / ")[1]);
//                                    }
//                                    out.print("<td>" + obj_informe_historial[(k + 2)].toString().split(" / ")[1] + "</td>");
//                                }
//                                out.print("<td><b>" + (horometro_final - horometro_inicial) + "</b></td>");
//                                out.print("</tr>");
//                            }
//                            out.print("</table>");
//                            out.print("</td>");
//                            out.print("</tr>");
//                        } else {
//                            out.print("<tr>");
//                            out.print("<td><b class='naranja'>No se encontraro ordenes de trabajo programadas</b></td>");
//                            out.print("</tr>");
//                        }
//                    }
//                    out.print("</table>");
//                    out.print("</div>");
//                    out.print("</div>");
//                    //</editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="INFORME DE ACTIVIDADES MES">
                else if (pageContext.getRequest().getAttribute("Informes").toString().equals("Actividades_mes")) {
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE INFORME">
                    out.print("<br /><div style='float:right'>");
                    out.print("<form action='Informe?opc=3' method='post'>");
                    out.print("<b>Equipo(s): </b><input type='text' name='Txt_filtro' List='Equipos' value='" + filtro + "' placeholder='Nombre de equipo' style='width:300px' /> ");
                    out.print("<datalist id='Equipos'><label><select name='Equipos'>");
                    lst_equipos = jpaceqp.Equipos();
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipo = (Object[]) lst_equipos.get(i);
                        out.print("<option value='" + obj_equipo[1] + "'>");
                    }
                    out.print("</select></label></datalist></label>");
                    out.print("<b>Fecha inicio : </b><input type='text' id='start' name='Txt_fecha_inicio' value='" + fecha_inicio + "' placeholder='Fecha Fin' style='width:100px' />");
                    out.print("<b>Fecha fin : </b><input type='text' id='end' name='Txt_fecha_fin' value='" + fecha_fin + "' placeholder='Fecha Final' style='width:100px' /> ");
                    out.print(" <input type='submit' value='Generar'>");
                    out.print("</form>");
                    out.print("</div>");
//</editor-fold>
                    if (filtro.toUpperCase().equals("TODAS") || filtro.equals("")) {
                        lst_informe_actividades = jpacotb.Informe_actividades_mes(fecha_inicio, fecha_fin);
                    } else {
                        lst_informe_actividades = jpacotb.Informe_actividades_mes_filtro(fecha_inicio, fecha_fin, filtro);
                    }
                    if (lst_informe_actividades != null) {
                        out.print("<div style='float:left;width:300px'>"
                                + "<a onclick=\"tableToExcel('Excel', 'Informe de actividades')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                                + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF <br />"
                                //                            + "<input name='key' type='text' id='key' placeholder='Buscar' onkeyup='buscar(this.value)' />"
                                + "</div>");
                        out.print("<div id='Imprimir'>");
                        out.print("<table class='table3' id='Excel' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Fecha</th>");
                        out.print("<th>OT</th>");
                        out.print("<th>Equipo</th>");
                        out.print("<th>Actividades Programadas</th>");
                        out.print("<th>Actividades Ejecutadas</th>");
                        out.print("<th>Tiempo de ejecución (min)</th>");
                        out.print("<th>Responsable</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_informe_actividades.size(); i++) {
                            Object[] obj_informe_actividades = (Object[]) lst_informe_actividades.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                            out.print("<td align='center'>" + obj_informe_actividades[2] + "</td>");
                            out.print("<td align='center'><a href='Orden_trabajo?opc=3&iot=" + obj_informe_actividades[0] + "' target='_blank'><b>" + obj_informe_actividades[1] + "</b></a></td>");
                            out.print("<td>" + obj_informe_actividades[4] + "</td>");
                            out.print("<td align='center'>" + obj_informe_actividades[5] + "</td>");
                            sum_act_programadas = sum_act_programadas + Integer.parseInt(obj_informe_actividades[5].toString());
                            if (Integer.parseInt(obj_informe_actividades[6].toString()) == Integer.parseInt(obj_informe_actividades[5].toString())) {
                                out.print("<td align='center'>" + obj_informe_actividades[6] + "</td>");
                            } else {
                                out.print("<td align='center'><b class='naranja'>" + obj_informe_actividades[6] + "</b></td>");
                            }
                            sum_act_ejecutadas = sum_act_ejecutadas + Integer.parseInt(obj_informe_actividades[6].toString());
                            out.print("<td align='center'>" + obj_informe_actividades[7] + "</td>");
                            sum_tiempo_ejecutado = sum_tiempo_ejecutado + Double.parseDouble(obj_informe_actividades[7].toString());
                            out.print("<td style='width:30%'>" + obj_informe_actividades[8] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<th colspan='2'>TOTAL</th>");
                        out.print("<td align='center' colspan='2'><b>" + lst_informe_actividades.size() + " O.T</b></td>");
                        out.print("<td align='center'><b>" + sum_act_programadas + "</b></td>");
                        out.print("<td align='center'><b>" + sum_act_ejecutadas + "</b></td>");
                        out.print("<td align='center'><b>" + sum_tiempo_ejecutado + " min </b></td>");
                        List lst_result = jpacotb.Calculo_indicador(sum_act_ejecutadas + "", sum_act_programadas + "");
                        Object[] obj_result = (Object[]) lst_result.get(0);
                        out.print("<td><b class='negro'>Porcentaje alcanzado " + obj_result[0] + " % </b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                    } else {
                        out.print("<br /><br /><br /><center><img src=\"Interfaz/Contenido/Iconos/Alert.png\" style=\"width:126.5px;height:112.75px\" alt=\"edit\" title=\"Sin permisos\"><br />"
                                + "<b class='rojo'>El valor filtrado (" + filtro + ") no encuentra información en el rango de fechas dado.</b></center>");
                    }
                    out.print("</div>");
                } // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="OTS DEVUELTAS">
                else if (pageContext.getRequest().getAttribute("Informes").toString().equals("Historial_devoluciones")) {
                    out.print("<div id='content_sin'>");
                    out.print("<h3> Historial de OT's devueltas a proceso<div style='float:right'><input type='text' onkeyup='Filtrar()' id='Txt_filtro' placeholder='Buscar'  /></div></h3>");
                    lst_devoluciones = jpacotb.Consultar_historial_devoluciones();
                    if (lst_devoluciones != null) {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Equipo</th>");
                        out.print("<th>#OT/Horometro/Fecha</th>");
                        out.print("<th>Responsables OT</th>");
                        out.print("<th style='width:40%'>Justificación</th>");
                        out.print("<th>Fecha / Responsable</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_devoluciones.size(); i++) {
                            Object[] obj_devoluciones = (Object[]) lst_devoluciones.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_devoluciones[8] + "<br />" + obj_devoluciones[9] + "</b></td>");
                            out.print("<td><b>" + obj_devoluciones[2] + "</b></br><b class='negro'>" + obj_devoluciones[3] + "</b><br />" + obj_devoluciones[7] + "</td>");
                            out.print("<td><b>Programador: </b>" + obj_devoluciones[4] + "<br />"
                                    + "<b>Ejecutor: </b>" + obj_devoluciones[5] + "</br>"
                                    + "<b>Revisor: </b>" + obj_devoluciones[6] + "</td>");
                            out.print("<td >" + obj_devoluciones[10] + "</td>");
                            out.print("<td >" + obj_devoluciones[11] + " / </br><b class='negro'>" + obj_devoluciones[12] + "</b></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="OTS ELIMINADAS">
                else if (pageContext.getRequest().getAttribute("Informes").toString().equals("Historial_eliminaciones")) {
                    out.print("<div id='content_sin'>");
                    out.print("<h3> Historial de OT's eliminadas<div style='float:right'><input type='text' onkeyup='Filtrar()' id='Txt_filtro' placeholder='Buscar'  /></div></h3>");
                    lst_eliminaciones = jpacotb.Consultar_historial_eliminacion();
                    if (lst_eliminaciones != null) {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Equipo</th>");
                        out.print("<th>#OT/Horometro</th>");
                        out.print("<th style='width:40%'>Justificación</th>");
                        out.print("<th>Fecha / Responsable</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_eliminaciones.size(); i++) {
                            Object[] obj_eliminaciones = (Object[]) lst_eliminaciones.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_eliminaciones[2] + "<br />" + obj_eliminaciones[3] + "</b></td>");
                            out.print("<td><b>" + obj_eliminaciones[4] + "</b></br><b class='negro'>" + obj_eliminaciones[5] + "</b></td>");
                            out.print("<td >" + obj_eliminaciones[6] + "</td>");
                            out.print("<td >" + obj_eliminaciones[7] + " / </br><b class='negro'>" + obj_eliminaciones[8] + "</b></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div>");
                }
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_informes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
