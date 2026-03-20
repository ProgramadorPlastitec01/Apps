package Vista;

import Controlador.CargoJpaController;
import Controlador.PruebaJpaController;
import Controlador.TipoProcesoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Reporte extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        int id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
        TipoProcesoJpaController jpa_proceso = new TipoProcesoJpaController();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        List lst_proceso = jpa_proceso.ConsultaTiposProcesoIdArea(id_area);
        List lst_cargo = jpa_cargo.car_c_cargo_area(id_area);
        List lst_reporte = null;
        String Query_reporte = (String) pageContext.getRequest().getAttribute("Query_reporte");
        if (!Query_reporte.equals("")) {
            lst_reporte = jpa_prueba.ConsultaReporte(Query_reporte);
        }
        String cargos = "";
        String procesosGrafica = "";
        String puntajesGrafica = "";
        String puntaje = "";
        try {
            out.print("<div id='sidebar'>");
            //<editor-fold defaultstate="collapsed" desc="generar reporte">
            out.print("<h3>Reporte pruebas</h3>");
            out.print("<form method='post' action='Reporte?opc=2'>");
            out.print("<b>Cargo: </b>");
            out.print("<select id='cargo-id' name='cbx_cargo'>");
            out.print("<option style='display:none;' value=''>Seleccione un cargo</option>");
            out.print("<option value='0'>Todos</option>");
            for (int i = 0; i < lst_cargo.size(); i++) {
                Object[] obj_cargo = (Object[]) lst_cargo.get(i);
                out.print("<option value='" + obj_cargo[0] + "'>" + obj_cargo[1] + "</option>");

            }
            out.print("</select><br /><br />");
            out.print("<script type='text/javascript'>");
            out.print("var validation = new LiveValidation('cargo-id');");
            out.print("validation.add( Validate.Presence );");
            out.print("</script>");
            out.print("<b>Proceso: </b>");
            out.print("<select id='proceso-id' name='cbx_proceso'>");
            out.print("<option style='display:none;' value=''>Seleccione un proceso</option>");
            out.print("<option value='0'>Todos</option>");
            for (int i = 0; i < lst_proceso.size(); i++) {
                Object[] obj_proceso = (Object[]) lst_proceso.get(i);
                out.print("<option value='" + obj_proceso[0] + "'>" + obj_proceso[1] + "</option>");
            }
            out.print("</select><br /><br />");
            out.print("<script type='text/javascript'>");
            out.print("var validation = new LiveValidation('proceso-id');");
            out.print("validation.add( Validate.Presence );");
            out.print("</script>");
            out.print("<b>Fecha Inicial:</b>");
            out.print("<input type='text' id='start' name='txt_fchI'  placeholder='Seleccionar fecha' autocomplete='off'><br/>");
            out.print("<b>Fecha Final:</b><br/>");
            out.print("<input type='text' id='end' name='txt_fchF' placeholder='Seleccionar fecha' autocomplete='off'>");
            out.print("<input type='submit' value='Generar'>");
            out.print("</form>");
            //</editor-fold>
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            if (lst_reporte != null) {
                out.print("<a onclick='Imprimir();' ><img src=\"Interfaz/Template/images/Printer.png\" style=\"width: 30px;height: 30px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF");
                out.print("<div id='Imprimir'>");
                for (int i = 0; i < lst_reporte.size(); i++) {
                    String procesosS = "";
                    String notas = "";
                    if (lst_reporte.size() != 0) {
                        Object[] obj_reporte = (Object[]) lst_reporte.get(i);
                        if (i == 0) {
                            procesosGrafica = "{name: '" + obj_reporte[2] + "',data: [" + obj_reporte[2] + "]}";
                            cargos = "'" + obj_reporte[1] + " <br /> TOTAL " + obj_reporte[3] + " %'";
                            puntaje = "" + obj_reporte[3] + "";
                            //<editor-fold defaultstate="collapsed" desc="acordeon">
                            out.print("<button class='accordion'><b style='color:black;float:left'>" + obj_reporte[1] + "</b><b style='color:black;float:right'>TOTAL " + obj_reporte[3] + " %</b></button>");
                            out.print("<div class='panel' style='overflow:scroll;'>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th align='center'>Procesos</th>");
                            out.print("<th align='center'>Porcentaje total</th>");
                            out.print("<td align='center' rowspan='" + (lst_reporte.size() + 1) + "'>");
                            out.print("<div id='container" + i + "' style='min-width: 310px; height: 350px; margin: 0 auto'></div>");
                            out.print("</td>");
                            out.print("</tr>");
                            for (int j = 0; j < lst_reporte.size(); j++) {
                                Object[] obj_reporte2 = (Object[]) lst_reporte.get(j);
                                if (obj_reporte2[1].toString().equals(obj_reporte[1].toString())) {
                                    out.print("<tr>");
                                    out.print("<td align='center'>" + obj_reporte2[2] + "</td>");
                                    out.print("<td align='center'>" + obj_reporte2[4] + " %</td>");
                                    out.print("</tr>");
                                    if (procesosS.equals("")) {
                                        procesosS = "'" + obj_reporte2[2] + "'";
                                        notas = "" + obj_reporte2[4] + "";
                                    } else {
                                        procesosS = procesosS + ",'" + obj_reporte2[2] + "'";
                                        notas = notas + "," + obj_reporte2[4] + "";
                                    }
                                }

                            }
                            out.print("</table>");
                            //<editor-fold defaultstate="collapsed" desc="script grafica">
                            out.print("<script type='text/javascript'>");
                            out.print(" $(function () {");
                            out.print("$('#container" + i + "').highcharts({");
                            out.print("title: {");
                            out.print("text: '" + obj_reporte[1] + "',");
                            out.print("x: -20");
                            out.print("},");
                            out.print("xAxis: {");
                            out.print("categories: [");
                            out.print("" + procesosS + "");
                            out.print("]");
                            out.print("},");
                            out.print("yAxis: {");
                            out.print("title: {");
                            out.print("text: 'Porcentaje'");
                            out.print("}");
                            out.print("},");
                            out.print("tooltip: {");
                            out.print("valueSuffix: '%'");// value del punto a mostrar
                            out.print("},");
                            out.print("legend: {");
                            out.print("layout: 'vertical',");
                            out.print("align: 'right',");
                            out.print("verticalAlign: 'middle',");
                            out.print("borderWidth: 0");
                            out.print("},");
                            out.print("series: [{");
                            out.print("name: 'nota',");
                            out.print("data: [" + notas + "]");
                            out.print("}]");
                            out.print("});");
                            out.print("});");
                            out.print("</script>");
                            //</editor-fold>
                            out.print("</div>");
                            //</editor-fold>
                        } else {
                            if (!procesosGrafica.contains("{name: '" + obj_reporte[2] + "',data: [" + obj_reporte[2] + "]}")) {
                                procesosGrafica = procesosGrafica + ",{name: '" + obj_reporte[2] + "',data: [" + obj_reporte[2] + "]}";
                            }
                            Object[] obj_reporteAnt = (Object[]) lst_reporte.get(i - 1);
                            if (!obj_reporte[1].toString().equals(obj_reporteAnt[1])) {
                                cargos = cargos + ",'" + obj_reporte[1] + " <br /> TOTAL " + obj_reporte[3] + " %'";
                                puntaje = puntaje + "," + obj_reporte[3] + "";
                                //<editor-fold defaultstate="collapsed" desc="acordeon">
                                out.print("<button class='accordion'><b style='color:black;float:left'>" + obj_reporte[1] + "</b><b style='color:black;float:right'>TOTAL " + obj_reporte[3] + " %</b></button>");
                                out.print("<div class='panel' style='overflow:scroll;'>");
                                out.print("<table class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<th align='center'>Procesos</th>");
                                out.print("<th align='center'>Porcentaje total</th>");
                                out.print("<td align='center' rowspan='5'>");
                                out.print("<div id='container" + i + "' style='min-width: 310px; height: 350px; margin: 0 auto'></div>");
                                out.print("</td>");
                                out.print("</tr>");
                                for (int j = 0; j < lst_reporte.size(); j++) {
                                    Object[] obj_reporte2 = (Object[]) lst_reporte.get(j);
                                    if (obj_reporte2[1].toString().equals(obj_reporte[1].toString())) {
                                        out.print("<tr>");
                                        out.print("<td align='center'>" + obj_reporte2[2] + "</td>");
                                        out.print("<td align='center'>" + obj_reporte2[4] + " %</td>");
                                        out.print("</tr>");
                                        if (procesosS.equals("")) {
                                            procesosS = "'" + obj_reporte2[2] + "'";
                                            notas = "" + obj_reporte2[4] + "";
                                        } else {
                                            procesosS = procesosS + ",'" + obj_reporte2[2] + "'";
                                            notas = notas + "," + obj_reporte2[4] + "";
                                        }
                                    }
                                }
                                out.print("</table>");
                                //<editor-fold defaultstate="collapsed" desc="script grafico">
                                out.print("<script type='text/javascript'>");
                                out.print(" $(function () {");
                                out.print("$('#container" + i + "').highcharts({");
                                out.print("title: {");
                                out.print("text: '" + obj_reporte[1] + "',");
                                out.print("x: -20");
                                out.print("},");
                                out.print("xAxis: {");
                                out.print("categories: [");
                                out.print("" + procesosS + "");
                                out.print("]");
                                out.print("},");
                                out.print("yAxis: {");
                                out.print("title: {");
                                out.print("text: 'Porcentaje'");
                                out.print("}");
                                out.print("},");
                                out.print("tooltip: {");
                                out.print("valueSuffix: '%'");// value del punto a mostrar
                                out.print("},");
                                out.print("legend: {");
                                out.print("layout: 'vertical',");
                                out.print("align: 'right',");
                                out.print("verticalAlign: 'middle',");
                                out.print("borderWidth: 0");
                                out.print("},");
                                out.print("series: [{");
                                out.print("name: 'nota',");
                                out.print("data: [" + notas + "]");
                                out.print("}]");
                                out.print("});");
                                out.print("});");
                                out.print("</script>");
                                //</editor-fold>
                                out.print("</div>");
                                //</editor-fold>
                            }
                        }
                    } else {
                        out.print("<h3>No se han encontrado resultados</h3>");
                    }
                }
                if (lst_reporte.size() != 0) {
                    Object[] obj_reporteA = (Object[]) lst_reporte.get(0);
                    for (int i = 0; i < lst_reporte.size(); i++) {
                        puntajesGrafica = "";
                        Object[] obj_reporte = (Object[]) lst_reporte.get(i);
                        for (int j = 0; j < lst_reporte.size(); j++) {
                            Object[] obj_reporte2 = (Object[]) lst_reporte.get(j);
                            if (obj_reporte2[2].toString().equals(obj_reporte[2].toString())) {
                                if (puntajesGrafica.equals("")) {
                                    puntajesGrafica = obj_reporte2[4].toString();
                                } else {
                                    puntajesGrafica = puntajesGrafica + "," + obj_reporte2[4].toString();
                                }
                            }
                        }
                        procesosGrafica = procesosGrafica.replace("{name: '" + obj_reporte[2] + "',data: [" + obj_reporte[2] + "]}", "{name: '" + obj_reporte[2] + "',data: [" + puntajesGrafica + "]}");
                    }
                    out.print("<script type='text/javascript'>");
                    out.print(" $(function () {");
                    out.print("$('#containerCarPro').highcharts({");
                    out.print("chart: {");
                    out.print("type: 'bar'");
                    out.print("},");
                    out.print("title: {");
                    out.print("text: 'Total area " + obj_reporteA[5] + " %',");
                    out.print("x: -20");
                    out.print("},");
                    out.print("xAxis: {");
                    out.print("categories: [");
                    out.print("" + cargos + "");
                    out.print("]");
                    out.print("},");
                    out.print("yAxis: {");
                    out.print("title: {");
                    out.print("text: 'Porcentaje'");
                    out.print("}");
                    out.print("},");
                    out.print("tooltip: {");
                    out.print("valueSuffix: '%'");// value del punto a mostrar
                    out.print("},");
                    out.print("plotOptions: {");
                    out.print("bar: {");
                    out.print("dataLabels: {");
                    out.print("enabled: true");
                    out.print("}");
                    out.print("}");
                    out.print("},");
                    out.print("legend: {");
                    out.print("layout: 'vertical',");
                    out.print("align: 'right',");
                    out.print("verticalAlign: 'middle',");
                    out.print("borderWidth: 0");
                    out.print("},");
                    out.print("series: [");
                    out.print("" + procesosGrafica + "");
                    out.print("]");
                    out.print("});");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div id='containerCarPro' style='min-width: 310px; height: 350px; margin: 0 auto'></div>");
                    out.print("</div>");
                } else {
                    out.print("<h3>No se han encontrado resultados</h3>");
                }
            } else {
                out.print("<h3>No se han encontrado resultados</h3>");
            }
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
