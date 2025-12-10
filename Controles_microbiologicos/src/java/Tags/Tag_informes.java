package Tags;

import controladoras.AnalisisPorAreaJpaController;
import controladoras.AreaMuestradaJpaController;
import java.io.IOException;
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
            AnalisisPorAreaJpaController jpacapa = new AnalisisPorAreaJpaController();
            AreaMuestradaJpaController jpacame = new AreaMuestradaJpaController();
            List lst_analisis = null;
            List lst_analisis_superficies = null;
            List lst_analisis_personal = null;
            List lst_analisis_linea_produccion = null;
            List lst_areas = null;
            String fecha_inicio = "";
            String fecha_fin = "";
            String linea = "";
            int id_area = 0;
            if (pageContext.getRequest().getAttribute("Informes") != null) {
                // <editor-fold defaultstate="collapsed" desc="INFORME DE ACTIVIDADES">
                if (pageContext.getRequest().getAttribute("Informes").toString().equals("Informe_areas")) {
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    id_area = Integer.parseInt(pageContext.getRequest().getAttribute("Id_area").toString());
                    lst_areas = jpacame.Consultar_areas_muestradas();
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Informe</h3>");
                    out.print("<form action='Informes?opc=1' method='post'>");
                    out.print("<b>Fecha inicio</b>");
                    out.print("<input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' " + ((fecha_inicio == null ? "" == null : fecha_inicio.equals("")) ? "" : "value='" + fecha_inicio + "'") + " required/>");
                    out.print("<b>Fecha fin</b>");
                    out.print("<input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin' " + ((fecha_fin == null ? "" == null : fecha_fin.equals("")) ? "" : "value='" + fecha_fin + "'") + " required/>");
                    out.print("<b>Area</b>");
                    out.print("<select name='Cbx_area' id='Cbx_area'>");
                    out.print("<option value='-1'>Todas</option>");
                    for (int i = 0; i < lst_areas.size(); i++) {
                        Object[] obj_areas = (Object[]) lst_areas.get(i);
                        if ((Integer) obj_areas[0] == id_area) {
                            out.print("<option value='" + obj_areas[0] + "' selected>" + obj_areas[1] + "</option>");
                            linea = obj_areas[1].toString();
                        } else {
                            out.print("<option value='" + obj_areas[0] + "'>" + obj_areas[1] + "</option>");
                        }
                    }
                    out.print("</select><br /><br />");
                    out.print("<input type='submit' value='Generar' />");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    out.print("<script src=\"Interfaz/Graficas/js/JS_1GRAFICS.js\"></script>");
                    out.print("<script src=\"Interfaz/Graficas/js/JS_2GRAFICS.js\"></script>");
                    out.print("<script src=\"Interfaz/Graficas/js/JS_3GRAFICS.js\"></script>");
                    if (id_area != -1) {
                        lst_analisis_superficies = jpacapa.Informe_area_superficies(fecha_inicio, fecha_fin, id_area);
                        lst_analisis_personal = jpacapa.Informe_area_personal(fecha_inicio, fecha_fin, id_area);
                        lst_analisis_linea_produccion = jpacapa.Informe_area_linea_produccion(fecha_inicio, fecha_fin, id_area);
                        // <editor-fold defaultstate="collapsed" desc="INFORME MESA DE INSPECCIÓN">
                        if (lst_analisis_superficies == null) {
                        } else {
                            // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
                            out.print("<script type=\"text/javascript\">");
                            out.print("$(function () {");
                            out.print("$('#container').highcharts({");
                            out.print("data: {");
                            out.print("table: 'datatable'");
                            out.print("},");
                            out.print("chart: {");
                            out.print("type: 'column'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'GRAFICA " + linea + " SUPERFICIES'");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("allowDecimals: false,");
                            out.print("title: {");
                            out.print("text: ''");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("formatter: function () {");
                            out.print("return '<b>' + this.series.name + '</b><br/>' +");
                            out.print("this.point.y + ' / ' + this.point.name.toUpperCase();");
                            out.print("}");
                            out.print("}");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                            // </editor-fold>
                            out.print("<div id=\"container\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div>");
                            out.print("<table id=\"datatable\" class='table' style='display:none'>");
                            out.print("<tbody>");
                            out.print("<tr>");
                            out.print("<th></th>");
                            out.print("<th>AM</th>");
                            out.print("<th>HONGOS</th>");
                            out.print("<th>LEVADURAS</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_analisis_superficies.size(); i++) {
                                out.print("<tr>");
                                Object[] obj_analisis = (Object[]) lst_analisis_superficies.get(i);
                                out.print("<th>" + obj_analisis[1] + "</th>");
                                out.print("<td>" + obj_analisis[8] + "</td>");
                                out.print("<td>" + obj_analisis[9] + "</td>");
                                out.print("<td>" + obj_analisis[10] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="INFORME PERSONAL">
                        if (lst_analisis_personal == null) {
                        } else {
                            // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
                            out.print("<script type=\"text/javascript\">");
                            out.print("$(function () {");
                            out.print("$('#container2').highcharts({");
                            out.print("data: {");
                            out.print("table: 'datatable2'");
                            out.print("},");
                            out.print("chart: {");
                            out.print("type: 'column'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'GRAFICA " + linea + " PERSONAL'");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("allowDecimals: false,");
                            out.print("title: {");
                            out.print("text: ''");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("formatter: function () {");
                            out.print("return '<b>' + this.series.name + '</b><br/>' +");
                            out.print("this.point.y + ' / ' + this.point.name.toUpperCase();");
                            out.print("}");
                            out.print("}");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                            // </editor-fold>
                            out.print("<div id=\"container2\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div>");
                            out.print("<table id=\"datatable2\" class='table' style='display:none'>");
                            out.print("<tbody>");
                            out.print("<tr>");
                            out.print("<th></th>");
                            out.print("<th>AM</th>");
                            out.print("<th>HONGOS</th>");
                            out.print("<th>LEVADURAS</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_analisis_personal.size(); i++) {
                                out.print("<tr>");
                                Object[] obj_analisis = (Object[]) lst_analisis_personal.get(i);
                                out.print("<th>" + obj_analisis[1] + "</th>");
                                out.print("<td>" + obj_analisis[8] + "</td>");
                                out.print("<td>" + obj_analisis[9] + "</td>");
                                out.print("<td>" + obj_analisis[10] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="INFORME OTROS">
                        if (lst_analisis_linea_produccion == null) {
                        } else {
                            // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
                            out.print("<script type=\"text/javascript\">");
                            out.print("$(function () {");
                            out.print("$('#container3').highcharts({");
                            out.print("data: {");
                            out.print("table: 'datatable3'");
                            out.print("},");
                            out.print("chart: {");
                            out.print("type: 'column'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'GRAFICAS " + linea + " AMBIENTE'");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("allowDecimals: false,");
                            out.print("title: {");
                            out.print("text: ''");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("formatter: function () {");
                            out.print("return '<b>' + this.series.name + '</b><br/>' +");
                            out.print("this.point.y + ' / ' + this.point.name.toUpperCase();");
                            out.print("}");
                            out.print("}");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                            // </editor-fold>
                            out.print("<div id=\"container3\" style=\"min-width: 310px; height: 400px; margin: 0 auto\"></div>");
                            out.print("<table id=\"datatable3\" class='table' style='display:none'>");
                            out.print("<tbody>");
                            out.print("<tr>");
                            out.print("<th></th>");
                            out.print("<th>AM</th>");
                            out.print("<th>HONGOS</th>");
                            out.print("<th>LEVADURAS</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_analisis_linea_produccion.size(); i++) {
                                out.print("<tr>");
                                Object[] obj_analisis = (Object[]) lst_analisis_linea_produccion.get(i);
                                out.print("<th>" + obj_analisis[1] + "</th>");
                                out.print("<td>" + obj_analisis[8] + "</td>");
                                out.print("<td>" + obj_analisis[9] + "</td>");
                                out.print("<td>" + obj_analisis[10] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                        }
                        // </editor-fold>
                    } else {
                        // <editor-fold defaultstate="collapsed" desc="GRAFICA AREAS SUPERFICIES">
                        lst_analisis_superficies = jpacapa.Informe_areas_superficies(fecha_inicio, fecha_fin);
                        if (lst_analisis_superficies == null) {
                        } else {
                            out.print("<script type='text/javascript'>");
                            out.print("$(function () {");
                            out.print("$('#contGraficas').highcharts({");
                            out.print("chart: {");
                            out.print("type: 'bar'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'ESTADISTICA POR AREA SUPERFICIES<br /> DE " + fecha_inicio + " A " + fecha_fin + "'");
                            out.print("},");
                            out.print("subtitle: {");
                            out.print("text: ''");
                            out.print("},");
                            out.print(" xAxis: {");
                            out.print("categories: [");
                            for (int i = 0; i < lst_analisis_superficies.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_superficies.get(i);
                                if (i == 0) {
                                    out.print("'" + obj_analisis[2] + "'");
                                } else {
                                    out.print(",'" + obj_analisis[2] + "'");
                                }
                            }
                            out.print("],");
                            out.print("title: {");
                            out.print("text: null");
                            out.print("}");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("min: 0,");
                            out.print("title: {");
                            out.print("text: '',");
                            out.print("align: 'high'");
                            out.print("},");
                            out.print("labels: {");
                            out.print("overflow: 'justify'");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("valueSuffix: ' '");
                            out.print("},");
                            out.print("plotOptions: {");
                            out.print("bar: {");
                            out.print("dataLabels: {");
                            out.print("enabled: true");
                            out.print("}}},");
                            out.print("legend: {");
                            out.print("layout: 'vertical',");
                            out.print("align: 'right',");
                            out.print("verticalAlign: 'top',");
                            out.print("x: -40,");
                            out.print("y: 80,");
                            out.print("floating: true,");
                            out.print("borderWidth: 1,");
                            out.print("backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),");
                            out.print("shadow: true},");
                            out.print("credits: {");
                            out.print("enabled: false},");
                            out.print("series: [{");
                            out.print("name: 'AM',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_superficies.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_superficies.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[3] + "");
                                } else {
                                    out.print("," + obj_analisis[3] + "");
                                }
                            }
                            out.print("]");
                            out.print("}, {");
                            out.print("name: 'HONGOS',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_superficies.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_superficies.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[4] + "");
                                } else {
                                    out.print("," + obj_analisis[4] + "");
                                }
                            }
                            out.print("]");
                            out.print("}, {");
                            out.print("name: 'LEVADURAS',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_superficies.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_superficies.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[5] + "");
                                } else {
                                    out.print("," + obj_analisis[5] + "");
                                }
                            }
                            out.print("]");
                            out.print("}]");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="GRAFICA AREAS PERSONAL">
                        lst_analisis_personal = jpacapa.Informe_areas_personal(fecha_inicio, fecha_fin);
                        if (lst_analisis_personal == null) {
                        } else {
                            out.print("<script type='text/javascript'>");
                            out.print("$(function () {");
                            out.print("$('#contGraficas2').highcharts({");
                            out.print("chart: {");
                            out.print("type: 'bar'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'ESTADISTICA POR AREA PERSONAL<br /> DE " + fecha_inicio + " A " + fecha_fin + "'");
                            out.print("},");
                            out.print("subtitle: {");
                            out.print("text: ''");
                            out.print("},");
                            out.print(" xAxis: {");
                            out.print("categories: [");
                            for (int i = 0; i < lst_analisis_personal.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_personal.get(i);
                                if (i == 0) {
                                    out.print("'" + obj_analisis[2] + "'");
                                } else {
                                    out.print(",'" + obj_analisis[2] + "'");
                                }
                            }
                            out.print("],");
                            out.print("title: {");
                            out.print("text: null");
                            out.print("}");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("min: 0,");
                            out.print("title: {");
                            out.print("text: '',");
                            out.print("align: 'high'");
                            out.print("},");
                            out.print("labels: {");
                            out.print("overflow: 'justify'");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("valueSuffix: ' '");
                            out.print("},");
                            out.print("plotOptions: {");
                            out.print("bar: {");
                            out.print("dataLabels: {");
                            out.print("enabled: true");
                            out.print("}}},");
                            out.print("legend: {");
                            out.print("layout: 'vertical',");
                            out.print("align: 'right',");
                            out.print("verticalAlign: 'top',");
                            out.print("x: -40,");
                            out.print("y: 80,");
                            out.print("floating: true,");
                            out.print("borderWidth: 1,");
                            out.print("backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),");
                            out.print("shadow: true},");
                            out.print("credits: {");
                            out.print("enabled: false},");
                            out.print("series: [{");
                            out.print("name: 'AM',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_personal.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_personal.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[3] + "");
                                } else {
                                    out.print("," + obj_analisis[3] + "");
                                }
                            }
                            out.print("]");
                            out.print("}, {");
                            out.print("name: 'HONGOS',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_personal.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_personal.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[4] + "");
                                } else {
                                    out.print("," + obj_analisis[4] + "");
                                }
                            }
                            out.print("]");
                            out.print("}, {");
                            out.print("name: 'LEVADURAS',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_personal.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_personal.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[5] + "");
                                } else {
                                    out.print("," + obj_analisis[5] + "");
                                }
                            }
                            out.print("]");
                            out.print("}]");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                        }
                        // </editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="GRAFICA AREAS AMBIENTE">
                        lst_analisis_linea_produccion = jpacapa.Informe_areas_lineas_produccion(fecha_inicio, fecha_fin);
                        if (lst_analisis_linea_produccion == null) {
                        } else {
                            out.print("<script type='text/javascript'>");
                            out.print("$(function () {");
                            out.print("$('#contGraficas3').highcharts({");
                            out.print("chart: {");
                            out.print("type: 'bar'");
                            out.print("},");
                            out.print("title: {");
                            out.print("text: 'ESTADISTICA POR AREA AMBIENTE <br /> DE " + fecha_inicio + " A " + fecha_fin + "'");
                            out.print("},");
                            out.print("subtitle: {");
                            out.print("text: ''");
                            out.print("},");
                            out.print(" xAxis: {");
                            out.print("categories: [");
                            for (int i = 0; i < lst_analisis_linea_produccion.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_linea_produccion.get(i);
                                if (i == 0) {
                                    out.print("'" + obj_analisis[2] + "'");
                                } else {
                                    out.print(",'" + obj_analisis[2] + "'");
                                }
                            }
                            out.print("],");
                            out.print("title: {");
                            out.print("text: null");
                            out.print("}");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("min: 0,");
                            out.print("title: {");
                            out.print("text: 'test',");
                            out.print("align: 'high'");
                            out.print("},");
                            out.print("labels: {");
                            out.print("overflow: 'justify'");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("valueSuffix: ' '");
                            out.print("},");
                            out.print("plotOptions: {");
                            out.print("bar: {");
                            out.print("dataLabels: {");
                            out.print("enabled: true");
                            out.print("}}},");
                            out.print("legend: {");
                            out.print("layout: 'vertical',");
                            out.print("align: 'right',");
                            out.print("verticalAlign: 'top',");
                            out.print("x: -40,");
                            out.print("y: 80,");
                            out.print("floating: true,");
                            out.print("borderWidth: 1,");
                            out.print("backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),");
                            out.print("shadow: true},");
                            out.print("credits: {");
                            out.print("enabled: false},");
                            out.print("series: [{");
                            out.print("name: 'AM',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_linea_produccion.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_linea_produccion.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[3] + "");
                                } else {
                                    out.print("," + obj_analisis[3] + "");
                                }
                            }
                            out.print("]");
                            out.print("}, {");
                            out.print("name: 'HONGOS',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_linea_produccion.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_linea_produccion.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[4] + "");
                                } else {
                                    out.print("," + obj_analisis[4] + "");
                                }
                            }
                            out.print("]");
                            out.print("}, {");
                            out.print("name: 'LEVADURAS',");
                            out.print("data: [");
                            for (int i = 0; i < lst_analisis_linea_produccion.size(); i++) {
                                Object[] obj_analisis = (Object[]) lst_analisis_linea_produccion.get(i);
                                if (i == 0) {
                                    out.print("" + obj_analisis[5] + "");
                                } else {
                                    out.print("," + obj_analisis[5] + "");
                                }
                            }
                            out.print("]");
                            out.print("}]");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                        }
                        // </editor-fold>
                        out.print("<br />");
                        out.print("<article class='accordion'>");
                        out.print("<section id='Superficies'>");
                        out.print("<h3><a href='#Superficies'>SUPERFICIES</a></h3>");
                        out.print("<div id='contGraficas' style='min-width: 800px; max-width: 950px; height: 1500px; margin: 0 auto'></div>");
                        out.print("</section>");
                        out.print("<section id='Personal'>");
                        out.print("<h3><a href='#Personal'>PERSONAL</a></h3>");
                        out.print("<div id='contGraficas2' style='min-width: 800px; max-width: 950px; height: 1500px; margin: 0 auto'></div>");
                        out.print("</section>");
                        out.print("<section id='Produccion'>");
                        out.print("<h3><a href='#Produccion'>AMBIENTE</a></h3>");
                        out.print("<div id='contGraficas3' style='min-width: 800px; max-width: 950px; height: 1500px; margin: 0 auto'></div>");
                        out.print("</section>");
                        out.print("</article>");
                    }
                    out.print("</div><!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_informes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}