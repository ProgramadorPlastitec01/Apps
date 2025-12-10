package Tags;

import Controladores_BD.AreaJpaController;
import Controladores_BD.CategoriaJpaController;
import Controladores_BD.DotacionJpaController;
import Controladores_BD.EppJpaController;
import Controladores_BD.MenuJpaController;
import Controladores_BD.PersonalJpaController;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_reportes extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            MenuJpaController jpacmnu = new MenuJpaController();
            AreaJpaController jpacara = new AreaJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            PersonalJpaController jpacpsn = new PersonalJpaController();
            DotacionJpaController jpacdtc = new DotacionJpaController();
            EppJpaController jpacepp = new EppJpaController();
            //FECHA
            String fechaps_incio = pageContext.getSession().getAttribute("FechaPS_inicio") + "";
            String fechaps_fin = pageContext.getSession().getAttribute("FechaPS_fin") + "";
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            String mes_mas = ((cal.get(Calendar.MONTH) == 11) ? "01" : (cal.get(Calendar.MONTH) + 2)) + "";
            String dia = (cal.get(Calendar.DAY_OF_MONTH)) + "";
            List lst_personal = null;
            List lst_areas = null;
            int anio_report = 0;
            int mes_report = 0;
            String arg_meses[] = {"1 ENERO", "2 FEBRERO", "3 MARZO", "4 ABRIL", "5 MAYO", "6 JUNIO", "7 JULIO", "8 AGOSTO", "9 SEPTIEMBRE", "10 OCTUBRE", "11 NOVIEMBRE", "12 DICIEMBRE"};
            String arg_progress_bar[] = {"purple", "green", "yellow", "blue", "pink", "teal"};
            List lst_opciones_permisos = null;
            List lst_persona = null;
            List lst_vistas = null;
            List lst_rotacion = null;
            List lst_dotacion_anio_mes = null;
            List lst_rotacion_detallado = null;
            List lst_informe_agrupado = null;
            List lst_informe_detallado = null;
            List lst_empleados_anio_mes = null;
            List lst_empleados_ingreso_anio_mes = null;
            List lst_empleados_inicio_anio = null;
            List lst_verificacion_registros = null;
            List lst_calificacion_competencias = null;
            List lst_numero_trabajadores = null;
            List lst_trabajadores_anio = null;
            String permisos = "";
            String archivo_plano = "";
            String new_salarios = "";
            String old_salarios = "";
            String actualizacion_empleados = "";
            String[] arg_contenedor = null;
            String[] arg_datos = null;
            int validacion = 0;
            int tipo_reporte = 0;
            List lst_actualizacion_salarios = null;
            String consulta = "";
            long mult = (long) Math.pow(10, 2);
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int id_opcion_menu = 0;
            if (pageContext.getRequest().getAttribute("Reportes") != null) {
                //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                id_opcion_menu = Integer.parseInt(pageContext.getRequest().getAttribute("Permisos").toString());
                lst_opciones_permisos = jpacmnu.Opciones_usuario_id(id_opcion_menu, menu);
                if (lst_opciones_permisos != null) {
                    Object[] obj_permisos = (Object[]) lst_opciones_permisos.get(0);
                    permisos = obj_permisos[3].toString();
                } else {
                    permisos = "";
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REPORTE DE CUMPLEAÑOS">
                if (pageContext.getRequest().getAttribute("Reportes").equals("Cumpleanos")) {
                    mes = pageContext.getRequest().getAttribute("Mes").toString();
                    lst_personal = jpacpsn.Reporte_cumpleanios(Integer.parseInt(mes));
                    out.print("<div id='content_sin'>");
                    out.print("<h3 >Reporte de cumpleaños " + ((mes.equals("0")) ? "" : "" + arg_meses[Integer.parseInt(mes) - 1].split(" ")[1]) + "");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("</h3>");
                    out.print("<form id='FormMeses' name='FormMeses' action='Reportes?opc=1&mnu=11' method='post' >");
                    out.print("Mes ");
                    out.print("<select name='Cbx_mes' id='Cbx_mes' onchange='this.form.submit()'>");
                    out.print("<option value='0' selected>Click para seleccionar</option>");
                    for (int i = 0; i < 12; i++) {
                        out.print("<option value='" + arg_meses[i].split(" ")[0] + "' " + ((mes.equals(arg_meses[i].split(" ")[0])) ? "selected" : "") + ">" + arg_meses[i].split(" ")[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("" + ((lst_personal != null) ? "" : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b class='rojo'>No se genero consulta para el mes</b>") + "");
                    out.print("</form>");
                    //<editor-fold defaultstate="collapsed" desc="VISUAL">
                    if (!mes.equals("0") && lst_personal != null) {
                        out.print("<div id='" + arg_meses[Integer.parseInt(mes) - 1].split(" ")[1].substring(0, 3) + "'>"
                                + "<h1 style='color: #fff;font-weight: 300;font-size: 50px;margin-bottom: 0px;margin-top: 0px;text-shadow: 0 0 3px #ccc, 0 0 5px #ccc;'>Reporte mes " + arg_meses[Integer.parseInt(mes) - 1].split(" ")[1] + "</h1>"
                                + "</div>");
                        out.print("<div style='display: inline-block;width:100%'>");
                        out.print("<div style='display: inline-block;width:50%;vertical-align: top;'>");
                        out.print("<table class='table' style='width:95%;float:left'>");
                        out.print("<tr>");
                        out.print("<td><b>Documento</b></td>");
                        out.print("<td><b>Nombre</b></td>");
                        out.print("<td><b>Dia</b></td>");
                        out.print("<td><b>Area</b></td>");
                        out.print("</tr>");
                        for (int i = 0; i < (lst_personal.size() / 2); i++) {
                            out.print("<tr>");
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<td>" + obj_personal[3] + "</td>");
                            out.print("<td>" + obj_personal[0] + "</td>");
                            out.print("<td align='center'>" + obj_personal[1] + "</td>");
                            out.print("<td>" + obj_personal[2] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div style='display: inline-block;width:50%;vertical-align: top;'>");
                        out.print("<table class='table' style='width:95%;float:left;'>");
                        out.print("<tr>");
                        out.print("<td><b>Documento</b></td>");
                        out.print("<td><b>Nombre</b></td>");
                        out.print("<td><b>Dia</b></td>");
                        out.print("<td><b>Area</b></td>");
                        out.print("</tr>");
                        for (int i = (lst_personal.size() / 2); i < lst_personal.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<td>" + obj_personal[3] + "</td>");
                            out.print("<td>" + obj_personal[0] + "</td>");
                            out.print("<td align='center'>" + obj_personal[1] + "</td>");
                            out.print("<td>" + obj_personal[2] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado un mes</center>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="IMPRIMIR">
                    out.print("<div id='Imprimir' style='display:none'>");
                    if (!mes.equals("0") && lst_personal != null) {
//                        out.print("<div class='CONT" + arg_meses[Integer.parseInt(mes) - 1].split(" ")[0] + "' style='background-color:#fff'>");
                        out.print("<table class='CONT" + arg_meses[Integer.parseInt(mes) - 1].split(" ")[0] + "'><tr><td>");
                        out.print("<h1 style='color: #fff;font-weight: 300;font-size: 50px;margin-bottom: 0px;margin-top: 0px;text-shadow: 0 0 3px #ccc, 0 0 5px #ccc;'>Feliz Cumpleaños " + arg_meses[Integer.parseInt(mes) - 1].split(" ")[1] + "</h1>");
                        out.print("<div style='display: inline-block;width:100%'>");
                        out.print("<div style='display: inline-block;width:50%;vertical-align: top;'>");
                        out.print("<center><table class='tabla_reporte'>");
                        out.print("<tr>");
                        out.print("<td><b>Dia</b></td>");
                        out.print("<td><b>Nombre</b></td>");
                        out.print("<td><b>Area</b></td>");
                        out.print("</tr>");
                        for (int i = 0; i < (lst_personal.size() / 2); i++) {
                            out.print("<tr>");
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<td align='center'>" + obj_personal[1] + "</td>");
                            out.print("<td>" + obj_personal[0] + "</td>");
                            out.print("<td>" + obj_personal[2] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table></center>");
                        out.print("</div>");
                        out.print("<div style='display: inline-block;width:50%;vertical-align: top;'>");
                        out.print("<center><table class='tabla_reporte'>");
                        out.print("<tr>");
                        out.print("<td><b>Dia</b></td>");
                        out.print("<td><b>Nombre</b></td>");
                        out.print("<td><b>Area</b></td>");
                        out.print("</tr>");
                        for (int i = (lst_personal.size() / 2); i < lst_personal.size(); i++) {
                            out.print("<tr>");
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<td align='center'>" + obj_personal[1] + "</td>");
                            out.print("<td>" + obj_personal[0] + "</td>");
                            out.print("<td>" + obj_personal[2] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table></center>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</td></tr></table>");
                    }
                    out.print("</div>");
//</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PLASTITEC-DATA">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Export-data")) {
                    out.print("<div id='content_sin'>");
                    out.print("<h3>Plastitec-Data</h3>");
                    lst_vistas = jpacmnu.Vistas_sirh();
//                    out.print("<b>Fecha inicio : </b><input type='text' id='start' name='Txt_fecha_inicio' required /></br><b>Fecha final : </b><input type='text' id='end' name='Txt_fecha_fin' required />");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th style='width:30%'>Origen de datos</th>");
                    out.print("<th style='width:10%'>Exportar</th>");
                    out.print("<th style='width:30%'>Origen de datos</th>");
                    out.print("<th style='width:10%'>Exportar</th>");
                    out.print("<td rowspan='" + ((lst_vistas.size() / 2) + 1) + "' style='padding: 15px;border: 3px dashed orange;' valign='top'>"
                            + "<br /><center><span class='fa fa-database fa-size_medio'></span></center><br />"
                            + "<div id='ExportDetail'><i style='text-align: left;'>Este modulo permite descargar archivo excel con diferente tipo de información de los origenes de datos de la aplicación SIRH dar click en <span class='far fa-arrow-alt-circle-down fa-size_small'></span> del reporte de interes.</i></div>"
                            //+ "<div id='ExportDetail2' style='display:none'><i>El origen de datos seleccionado <b id='TituloExport' class='negro'></b></i><br /><b>Nombre de archivo : </b><input type='text' id='fnm' name='fnm' required value='' /></br><input type='hidden' id='path' value='' /><input type='hidden' id='fpt' value='' /><b>Fecha inicio : </b><input type='text' id='start' name='Txt_fecha_inicio' onchange='Ajustar_fechas_export();' value='" + fechaps_incio + "' required /></br><b>Fecha final : </b><input type='text' id='end' name='Txt_fecha_fin' onchange='Ajustar_fechas_export();' value='" + fechaps_fin + "' required /><input onclick='DownloadData();' type='submit' value='Descargar' /></div>"
                            + "<div id='ExportDetail2' style='display:none'>"
                            + "<form action='Reportes?opc=2&mnu=12&ept=1' method='POST'>"
                            + "<i>El origen de datos seleccionado "
                            + "<b id='TituloExport' class='negro'></b></i><br />"
                            + "<b>Nombre de archivo : </b>"
                            + "<input type='text' id='fnm' name='fnm' required value='' /></br>"
                            + "<input type='hidden' id='path' name='fpt' value='TituloExport' />"
                            + "<input type='hidden' id='fpt' value='' />"
                            + "<b>Fecha inicio : </b>"
                            + "<input type='text' id='start' name='Txt_fecha_inicio' onchange='Ajustar_fechas_export();' value='" + fechaps_incio + "' required />"
                            + "</br>"
                            + "<b>Fecha final : </b>"
                            + "<input type='text' id='end' name='Txt_fecha_fin' onchange='Ajustar_fechas_export();' value='" + fechaps_fin + "' required />"
                            + "<input type='submit' value='Descargar' />"
                            + "</form>"
                            + "</div>"
                            + "</td>");
                    out.print("</tr>");
                    int cont_ajuste_tabla = 0;
                    for (int i = 0; i < lst_vistas.size(); i++) {
                        Object[] obj_vistas = (Object[]) lst_vistas.get(i);
                        if (!obj_vistas[0].toString().equals("vw_salario_personal")) {
                            cont_ajuste_tabla++;
                            if (cont_ajuste_tabla == 1) {
                                out.print("<tr>");
                            }
                            out.print("<td>" + obj_vistas[0] + "</td>");
                            out.print("<td align='center'>");
                            if (obj_vistas[0].toString().contains("vwf")) {
                                out.print("<span onclick=\"Ajustar_exportar('" + obj_vistas[0].toString().replace("vw_", "").replace("vwf_", "") + "','" + obj_vistas[0] + "');\" class='far fa-arrow-alt-circle-down fa-size_small'></span></td>");
                            } else {
                                out.print("<a href='Reportes?opc=2&mnu=12&ept=1&fnm=" + obj_vistas[0].toString().replace("vw_", "") + "&fpt=" + obj_vistas[0] + "'><span class='fa fa-file-excel fa-size_small'></span></a>");
                            }
                            out.print("</td>");
                            //out.print("<td align='center'><a href='Reportes?opc=2&mnu=12&ept=1&fnm=" + obj_vistas[0].toString().replace("vw_", "") + "&fpt=" + obj_vistas[0] + "'><span class='fa fa-file-excel fa-size_small'></span></a></td>");
                            if (cont_ajuste_tabla == 2) {
                                out.print("</tr>");
                                cont_ajuste_tabla = 0;
                            }
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DOTACION X MES">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Dotacion_x_mes")) {
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    mes_report = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                    tipo_reporte = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_reporte").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3><a style='text-decoration:none' href='Reportes?opc=10&mnu=37'><span class='fa fa-tshirt fa-size_super_small'></span></a>");
                    out.print("Generar informe de " + ((tipo_reporte == 0) ? "dotación" : "epp") + " por el mes de " + ((mes_report > 0) ? arg_meses[mes_report - 1].split(" ")[1] : ""));
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div style='float:right'><a onclick=\"tableToExcel('Excel', 'Reporte')\"><span class='fa fa-file-excel fa-size_super_small'></span></a>");
                        out.print("&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_informe()'><span class='fa fa-print fa-size_super_small'></span></a></div>");
                    }
                    out.print("</h3>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    if (anio_report == 0) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Popup_informe_dotaciones' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        out.print("<form action='Reportes?opc=10&mnu=37' method='post'>");
                        out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Popup_informe_dotaciones').style.display='none';\" class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de informe</h3>");
                        //out.print("Ingresar rango de fechas para limitar muestra de datos para el informe de Ausentismo.<br /><br />");
                        out.print("Seleccionar año y mes para limitar muestra de datos para el informe de Dotaciones o EPP.<br />");
                        out.print("<b>Año :</b><br /><select name='Cbx_anio' id='Cbx_anio'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i <= 5; i++) {
                            out.print("<option value='" + (anio - i) + "' >" + (anio - i) + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Mes :</b><br /><select name='Cbx_mes' id='Cbx_mes'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i < 12; i++) {
                            out.print("<option value='" + arg_meses[i].split(" ")[0] + "' >" + arg_meses[i].split(" ")[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mes');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Origen :</b><br /><select name='Cbx_origen' id='Cbx_origen'>");
                        out.print("<option value='0' " + ((tipo_reporte == 0) ? "selected" : "") + ">Dotaciones</option>");
                        out.print("<option value='1' " + ((tipo_reporte == 1) ? "selected" : "") + ">Epp</option>");
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_origen');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['2'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Generar'>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    if (anio_report == 0) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado Año y mes</center>");
                    } else {
                        if (tipo_reporte == 0) {
                            lst_dotacion_anio_mes = jpacdtc.Dotaciones_anio_mes(anio_report, mes_report);
                        } else {
                            lst_dotacion_anio_mes = jpacepp.Epp_anio_mes(anio_report, mes_report);
                        }
                        if (lst_dotacion_anio_mes == null) {
                            out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se han encontrado resultados con el filtro Año y mes</center>");
                        } else {
                            out.print("<table class='table' id='Excel'>");
                            out.print("<tr>");
                            out.print("<th>Fecha/CC</th>");
                            out.print("<th style='width:30%'>Personal</th>");
                            out.print("<th style='width:40%'>" + ((tipo_reporte == 1) ? "EPP" : "Dotación") + " asignada</th>");
                            out.print("<th style='width:20%'>Observación</th>");
                            out.print("</tr>");
                            int cant_total = 0;
                            for (int i = 0; i < lst_dotacion_anio_mes.size(); i++) {
                                Object[] obj_dotaciones = (Object[]) lst_dotacion_anio_mes.get(i);
                                String dotacion_personal = obj_dotaciones[6].toString().replace("][", "___").replace("[", "").replace("]", "");
                                String[] arg_dotacion_personal = dotacion_personal.split("___");
                                out.print("<tr>");
                                out.print("<td align='center'><b class='negro'>" + obj_dotaciones[5] + "</b><br /><b class='tooltip'>" + obj_dotaciones[0] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_dotaciones[0] + ".jpg' style='width:200px;heigth:200px' /></span></b></a></td>");
                                out.print("<td valign='top'><b>Nombre : </b>" + obj_dotaciones[1] + " " + obj_dotaciones[2] + "<br />"
                                        + "<b>Área : </b>" + obj_dotaciones[3] + "<br /><b>Cargo : </b>" + obj_dotaciones[4] + "</td>");
                                out.print("<td valign='top'><table style='width:100%'>");
                                for (int j = 0; j < arg_dotacion_personal.length; j++) {
                                    out.print("<tr>");
                                    out.print("<td style='width:80%'><b>Ref : </b>" + arg_dotacion_personal[j].split(" / ")[0] + "</td>");
                                    out.print("<td><b>Cant : </b>" + arg_dotacion_personal[j].split(" / ")[1] + "</td>");
                                    cant_total = cant_total + Integer.parseInt(arg_dotacion_personal[j].split(" / ")[1]);
                                    out.print("</tr>");
                                }
                                out.print("</table></td>");
                                out.print("<td valign='top'>" + obj_dotaciones[7] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td colspan='4'><b class='negro'>Cantidad total de elementos de dotación asignadas para el mes de <b>" + arg_meses[mes_report - 1].split(" ")[1] + "</b> del año <b>" + anio_report + "</b> es : <b>" + cant_total + "</b></b></td>");
                            out.print("</tr>");
                            out.print("</table>");
                        }
                    }
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="AUSENTISMO">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Ausencias")) {
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    mes_report = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3><a style='text-decoration:none' href='Reportes?opc=4&mnu=13'><span class='fa fa-chart-bar fa-size_super_small'></span></a>");
                    out.print("Generar informe de ausentismo");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_informe()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("</h3>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    if (anio_report == 0) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Popup_informe_ausentismo' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        //out.print("<div style='float:right;'><a href='Reportes?opc=4&mnu=13&Cbx_anio=" + anio + "&Cbx_mes=" + mes + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Popup_informe_ausentismo').style.display='none';\" class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de informe</h3>");
                        //out.print("Ingresar rango de fechas para limitar muestra de datos para el informe de Ausentismo.<br /><br />");
                        out.print("Seleccionar año y mes para limitar muestra de datos para el informe de Ausentismo.<br />");
                        out.print("<form action='Reportes?opc=4&mnu=13' method='post'>");
//                        out.print("<b>Fecha Inicio :</b><br /><input type='text' name='Txt_fecha_inicio' id='start' autocomplete='off' placeholder='Fecha_inicio' />"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
//                        out.print("<br /><b>Fecha Fin :</b><br /><input type='text' name='Txt_fecha_fin' id='end' autocomplete='off' placeholder='Fecha_fin' />"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                        out.print("<b>Año :</b><br /><select name='Cbx_anio' id='Cbx_anio'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i <= 5; i++) {
                            out.print("<option value='" + (anio - i) + "' >" + (anio - i) + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Mes :</b><br /><select name='Cbx_mes' id='Cbx_mes'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i < 12; i++) {
                            out.print("<option value='" + arg_meses[i].split(" ")[0] + "' >" + arg_meses[i].split(" ")[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mes');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Generar'>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    if (anio_report == 0) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado Año y mes</center>");
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="VARIABLES Y PARAMETRIZACIÓN">
                        lst_informe_agrupado = jpacmnu.Informe_ausencias_agrupado(anio_report, mes_report);
                        lst_informe_detallado = jpacmnu.Informe_ausencias_detallado(anio_report, mes_report);
                        lst_empleados_anio_mes = jpacmnu.Empleados_anio_mes(anio_report, mes_report);
                        if (lst_empleados_anio_mes == null) {
                            out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha encontrado información del Año y mes filtrado</center>");
                        } else {
                            Object[] obj_empleados_anio_mes = (Object[]) lst_empleados_anio_mes.get(0);
                            int total_casos = 0;
                            double total_costos = 0;
                            double total_horas = 0;
                            int porcentaje_casos = 0;
                            double porcentaje_costos = 0;
                            double porcentaje_horas = 0;
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                total_casos = total_casos + Integer.parseInt(obj_informe_agrupado[1].toString());
                                total_costos = total_costos + Double.parseDouble(obj_informe_agrupado[3].toString());
                                total_horas = total_horas + Double.parseDouble(obj_informe_agrupado[2].toString());
                            }
                            BigDecimal big_horas = new BigDecimal(total_horas);
                            BigDecimal big_costos = new BigDecimal(total_costos);
                            big_costos = big_costos.setScale(2, BigDecimal.ROUND_HALF_UP);
                            big_horas = big_horas.setScale(2, BigDecimal.ROUND_HALF_UP);
                            BigDecimal big_porcentaje_ausencias = new BigDecimal(((total_horas * 100) / Double.parseDouble(obj_empleados_anio_mes[1].toString())));
                            big_porcentaje_ausencias = big_porcentaje_ausencias.setScale(2, BigDecimal.ROUND_HALF_UP);
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REPORTE">
                            out.print("<div id='Imprimir_informe'>");
                            out.print("<table class='table' >");
                            out.print("<tr>");
                            out.print("<th colspan='2' style='width:30%'>Resumen</th>");
                            out.print("<td rowspan='9' style='padding:25px' valign='top'>");
                            out.print("<center><h3>Reporte de Ausentismo <b>" + arg_meses[(mes_report - 1)].split(" ")[1] + "</b> <b>" + anio_report + "</b></h3></center>");
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                out.print("<div class='pb_informe'>");
                                out.print("<div class='pb_informe_det " + arg_progress_bar[i] + "' style='width:" + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "%;text-align:center;'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "%</div>");
                                out.print("</div>");
                                out.print(" " + obj_informe_agrupado[0] + "<br />");
                            }
                            out.print("<center><h3>Porcentaje de ausentimo : <b>" + big_porcentaje_ausencias + "%</b></h3></center>");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Mes</b></td>");
                            out.print("<td>" + arg_meses[(mes_report - 1)].split(" ")[1] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Año</b></td>");
                            out.print("<td>" + anio_report + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Cantidad de trabajadores</b></td>");
                            out.print("<td>" + obj_empleados_anio_mes[0] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Horas totales</b></td>");
                            out.print("<td>" + obj_empleados_anio_mes[1] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Horas ausencias</b></td>");
                            out.print("<td>" + big_horas + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Porcentaje ausencias</b></td>");
                            out.print("<td>" + big_porcentaje_ausencias + "%</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Total costo</b></td>");
                            out.print("<td>" + big_costos + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Total casos</b></td>");
                            out.print("<td>" + total_casos + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DISTRIBUCIÓN GENERAL">
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<td colspan='7' align='center'><b>DISTRIBUCIÓN GENERAL</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>Concepto</th>");
                            out.print("<th>Casos</th>");
                            out.print("<th>% Casos</th>");
                            out.print("<th>Costo</th>");
                            out.print("<th>% Costo</th>");
                            out.print("<th>Horas</th>");
                            out.print("<th>% Horas</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_informe_agrupado[0] + "</td>");
                                out.print("<td align='right'>" + obj_informe_agrupado[1] + "</td>");
                                out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "%</td>");
                                BigDecimal big_costo = new BigDecimal(Double.parseDouble(obj_informe_agrupado[3].toString()));
                                big_costo = big_costo.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='right'>" + big_costo + "</td>");
                                out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[3].toString()) * 100) / total_costos) * mult)) / (double) mult + "%</td>");
                                out.print("<td align='right'>" + obj_informe_agrupado[2] + "</td>");
                                out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[2].toString()) * 100) / total_horas) * mult)) / (double) mult + "%</td>");
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td><b>Total</b></td>");
                            out.print("<td align='right'><b>" + total_casos + "</b></td>");
                            out.print("<td align='right'><b>100%</b></td>");
                            out.print("<td align='right'><b>" + big_costos + "</b></td>");
                            out.print("<td align='right'><b>100%</b></td>");
                            out.print("<td align='right'><b>" + big_horas + "</b></td>");
                            out.print("<td align='right'><b>100%</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //                        // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
//                        out.print("<script type=\"text/javascript\" src=\"Interfaz/Graficas/js/highcharts_principal.js\"></script>");
//                        out.print("<script src=\"Interfaz/Graficas/js/highcharts.js\"></script>");
//                        out.print("<script src=\"Interfaz/Graficas/js/modules/exporting.js\"></script>");
//                        out.print("<script type=\"text/javascript\">");
//                        out.print("$(function () {");
//                        out.print("$('#Grafica_informe_ausencias').highcharts({");
//                        out.print("chart: {");
//                        out.print("type: 'pie',");
//                        out.print("options3d: {");
//                        out.print("enabled: true,");
//                        out.print("alpha: 45");
//                        out.print("}");
//                        out.print("},");
//                        out.print("title: {");
//                        out.print("text: '...'");
//                        out.print("},");
//                        out.print("subtitle: {");
//                        out.print("text: 'Grafica Informe de ausencias por mes'");
//                        out.print("},");
//                        out.print("plotOptions: {");
//                        out.print("pie: {");
//                        out.print("innerSize: 100,");
//                        out.print("depth: 45");
//                        out.print("}");
//                        out.print("},");
//                        out.print("series: [{");
//                        out.print("name: 'Porcentaje',");
//                        out.print("data: [");
//                        for (int i = 0; i < lst_informe_agrupado.size(); i++) {
//                            Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
//                            if (i == 0) {
//                                out.print("['" + obj_informe_agrupado[0] + " # " + obj_informe_agrupado[1] + "', " + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "]");
//                            } else {
//                                out.print(",['" + obj_informe_agrupado[0] + " # " + obj_informe_agrupado[1] + "', " + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "]");
//                            }
//                        }
//                        out.print("]");
//                        out.print("}]");
//                        out.print("});");
//                        out.print("});");
//                        out.print("</script>");
//                        out.print("<div id='Grafica_informe_ausencias' style='min-width: 310px; margin: 0 auto;'></div>");
//                        // </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DETALLE MODULOS">
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                out.print("<button class='accordion'>" + obj_informe_agrupado[0] + "</button>");
                                out.print("<div class='panel'>");
                                if (Integer.parseInt(obj_informe_agrupado[1].toString()) > 0) {
                                    out.print("<table class='table'>");
                                    out.print("<tr>");
                                    out.print("<td colspan='7' align='center'><b>DETALLE " + obj_informe_agrupado[0].toString().toUpperCase() + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th>Tipo</th>");
                                    out.print("<th>Casos</th>");
                                    out.print("<th>% Casos</th>");
                                    out.print("<th>Costo</th>");
                                    out.print("<th>% Costo</th>");
                                    out.print("<th>Horas</th>");
                                    out.print("<th>% Horas</th>");
                                    out.print("</tr>");
                                    for (int j = 0; j < lst_informe_detallado.size(); j++) {
                                        Object[] obj_informe_detallado = (Object[]) lst_informe_detallado.get(j);
                                        if (obj_informe_detallado[0].toString().equals(obj_informe_agrupado[0].toString())) {
                                            out.print("<tr>");
                                            out.print("<td>" + obj_informe_detallado[1] + "</td>");
                                            out.print("<td align='right'>" + obj_informe_detallado[2] + "</td>");
                                            out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_detallado[2].toString()) * 100) / Double.parseDouble(obj_informe_agrupado[1].toString())) * mult)) / (double) mult + "%</td>");
                                            BigDecimal big_costo = new BigDecimal(Double.parseDouble(obj_informe_detallado[4].toString()));
                                            big_costo = big_costo.setScale(2, BigDecimal.ROUND_HALF_UP);
                                            out.print("<td align='right'>" + big_costo + "</td>");
                                            out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_detallado[4].toString()) * 100) / Double.parseDouble(obj_informe_agrupado[3].toString())) * mult)) / (double) mult + "%</td>");
                                            out.print("<td align='right'>" + obj_informe_detallado[3] + "</td>");
                                            out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_detallado[3].toString()) * 100) / Double.parseDouble(obj_informe_agrupado[2].toString())) * mult)) / (double) mult + "%</td>");
                                            out.print("</tr>");
                                        }
                                    }
                                    out.print("<tr>");
                                    out.print("<td><b>Total</b></td>");
                                    out.print("<td align='right'><b>" + obj_informe_agrupado[1] + "</b></td>");
                                    out.print("<td align='right'><b>100%</b></td>");
                                    BigDecimal big_costo = new BigDecimal(Double.parseDouble(obj_informe_agrupado[3].toString()));
                                    big_costo = big_costo.setScale(2, BigDecimal.ROUND_HALF_UP);
                                    out.print("<td align='right'><b>" + big_costo + "</b></td>");
                                    out.print("<td align='right'><b>100%</b></td>");
                                    out.print("<td align='right'><b>" + obj_informe_agrupado[2] + "</b></td>");
                                    out.print("<td align='right'><b>100%</b></td>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                } else {
                                    out.print("<b>No se tienen seguimientos del personal.</b>");
                                }
                                out.print("</div>");
                            }
//</editor-fold>
                        }
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ROTACIÓN">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Rotacion")) {
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    lst_trabajadores_anio = jpacmnu.Numero_trabajadores_anio(anio_report);
                    out.print("<div id='content_sin'>");
                    out.print("<h3><a style='text-decoration:none' href='Reportes?opc=5&mnu=24'><span class='fa fa-sync-alt fa-size_super_small'></span></a>");
                    out.print(" Generar informe de rotación");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick=\"Imprimir_informe()\" style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("</h3>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    if (anio_report == 0) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Popup_informe_ausentismo' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        //out.print("<div style='float:right;'><a href='Reportes?opc=4&mnu=13&Cbx_anio=" + anio + "&Cbx_mes=" + mes + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Popup_informe_ausentismo').style.display='none';\" class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de informe</h3>");
                        //out.print("Ingresar rango de fechas para limitar muestra de datos para el informe de Ausentismo.<br /><br />");
                        out.print("Seleccionar año para limitar el reporte de rotación.<br />");
                        out.print("<form action='Reportes?opc=5&mnu=24' method='post'>");
                        out.print("<b>Año :</b><br /><select name='Cbx_anio' id='Cbx_anio' >");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i <= 8; i++) {
                            out.print("<option value='" + (anio - i) + "' >" + (anio - i) + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Generar'>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    if (anio_report == 0) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado Año</center>");
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="INFORME">
                        out.print("<div id='Imprimir_informe' style='background-color:#fff'>");
                        out.print("<table class='table'>");
                        out.print("<tr>");
                        out.print("<td colspan='20' style='background-color:#ccc;border-radius:20px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='5' style='width:20%' >"
                                + "<img src='Interfaz/MasterPage/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='10' style='width:60%' align='center'><b class='negro'>INDICADORES DE GESTIÓN<hr />ROTACIÓN</b></td>");
                        //out.print("<td colspan='3'>CODIGO R-RH-009<hr />VERSION 4</td>");
                        out.print("<td colspan='5' style='width:20%' align='center'>AÑO " + anio_report + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("<center><div style='width:90%'>");
                        //int empleados_mes_anterior = 0;
                        int cant_empleados = 0;
                        int cant_empleados_old = 0;
                        for (int i = 0; i < lst_trabajadores_anio.size(); i++) {
                            lst_numero_trabajadores = jpacmnu.Numero_trabajadores_anio_mes(anio_report, Integer.parseInt(arg_meses[i].split(" ")[0]));
                            if (lst_numero_trabajadores != null || lst_numero_trabajadores.size() == 0 || lst_numero_trabajadores.isEmpty()) {
                                Object[] obj_numero_trabajadores = (Object[]) lst_numero_trabajadores.get(0);
                                cant_empleados = (Integer) obj_numero_trabajadores[3];
                                cant_empleados_old = (Integer) obj_numero_trabajadores[6];
                                lst_rotacion = jpacmnu.Informe_rotacion(anio_report, Integer.parseInt(arg_meses[i].split(" ")[0]));
                                lst_empleados_ingreso_anio_mes = jpacmnu.Ingresos_anio_mes(anio_report, Integer.parseInt(arg_meses[i].split(" ")[0]));
                                lst_empleados_inicio_anio = jpacmnu.Empleados_inicio_anio(anio_report);
                                Object[] obj_empleados_ingreso_anio_mes = (Object[]) lst_empleados_ingreso_anio_mes.get(0);
                                Object[] obj_empleados_inicio_anio = (Object[]) lst_empleados_inicio_anio.get(0);
                                if (lst_rotacion != null) {
                                    out.print("<h3 style='text-align:left'><b>" + arg_meses[i].split(" ")[1] + "</b></h3>");
                                    out.print("<table class='table'>");
                                    out.print("<tr>");
//                                out.print("<th style='width:15%'>Mes</th>");
                                    out.print("<th style='width:8%'># Retiros</th>");
                                    out.print("<th style='width:8%'># Casos</th>");
                                    out.print("<th style='width:35%'>Motivo</th>");
                                    out.print("<th style='width:8%'>% Casos</th>");
                                    out.print("<th style='width:10%'># Empleados</th>");
                                    out.print("<th style='width:8%'># Ingresos</th>");
                                    out.print("<th style='width:10%'>% Retiros</th>");
                                    out.print("</tr>");
                                    double valor_retiros = 0;
                                    int retiro_old = 0;
                                    for (int j = 0; j < lst_rotacion.size(); j++) {
                                        out.print("<tr>");
                                        Object[] obj_rotacion = (Object[]) lst_rotacion.get(j);
                                        if (j == 0) {
                                            out.print("<td rowspan='" + lst_rotacion.size() + "' align='center'><b class='rojo'>" + obj_rotacion[2] + "</b></td>");
                                            retiro_old = Integer.parseInt(obj_rotacion[4].toString());
                                        }
                                        out.print("<td align='center'>" + obj_rotacion[0] + "</td>");
                                        out.print("<td>" + obj_rotacion[1] + "</td>");
                                        out.print("<td align='right'>" + obj_rotacion[3] + "%</td>");
                                        if (j == 0) {
                                            BigDecimal big_valor_retiros = null;
//                                        if (arg_meses[i].equals("1 ENERO")) {
////                                            cant_empleados = (Integer.parseInt(obj_empleados_inicio_anio[0].toString()) + Integer.parseInt(obj_empleados_ingreso_anio_mes[0].toString()));
////                                            empleados_mes_anterior = cant_empleados - Integer.parseInt(obj_rotacion[2].toString());
//                                            valor_retiros = ((Double.parseDouble(obj_rotacion[2].toString()) * 100) / cant_empleados);
//                                            big_valor_retiros = new BigDecimal(valor_retiros);
//                                            big_valor_retiros = big_valor_retiros.setScale(2, BigDecimal.ROUND_HALF_UP);
//                                            out.print("<td rowspan='" + lst_rotacion.size() + "' align='center'>" + cant_empleados + "</td>");
//                                        } else {
////                                            cant_empleados = (empleados_mes_anterior + Integer.parseInt(obj_empleados_ingreso_anio_mes[0].toString()));
////                                            empleados_mes_anterior = (empleados_mes_anterior + Integer.parseInt(obj_empleados_ingreso_anio_mes[0].toString())) - Integer.parseInt(obj_rotacion[2].toString());
//                                            
//                                            valor_retiros = ((Double.parseDouble(obj_rotacion[2].toString()) * 100) / cant_empleados);
//                                            big_valor_retiros = new BigDecimal(valor_retiros);
//                                            big_valor_retiros = big_valor_retiros.setScale(2, BigDecimal.ROUND_HALF_UP);
//                                            out.print("<td rowspan='" + lst_rotacion.size() + "' align='center'>" + cant_empleados + "</td>");
//                                        }
                                            valor_retiros = ((Double.parseDouble(obj_rotacion[2].toString()) * 100) / (cant_empleados_old - retiro_old));
                                            big_valor_retiros = new BigDecimal(valor_retiros);
                                            big_valor_retiros = big_valor_retiros.setScale(2, BigDecimal.ROUND_HALF_UP);
                                            out.print("<td rowspan='" + lst_rotacion.size() + "' align='center'>" + cant_empleados + "</td>");
                                            out.print("<td rowspan='" + lst_rotacion.size() + "' align='center'><b class='verde'>" + obj_empleados_ingreso_anio_mes[0] + "</b></td>");
                                            out.print("<td rowspan='" + lst_rotacion.size() + "' align='center'><b style='font-size:18px;'>" + big_valor_retiros + "%</b></td>");
                                        }
                                        out.print("</tr>");
                                    }
                                    out.print("</table>");
                                    out.print("<button class='accordion'>Detalle retiros mes de " + arg_meses[i].split(" ")[1] + "</button>");
                                    out.print("<div class='panel' style='height:auto'>");
                                    lst_rotacion_detallado = jpacmnu.Informe_rotacion_detallado(anio_report, Integer.parseInt(arg_meses[i].split(" ")[0]));
                                    out.print("<table class='table'>");
                                    out.print("<tr>");
                                    out.print("<th>#</th>");
                                    out.print("<th>Documento</th>");
                                    out.print("<th>Personal</th>");
                                    out.print("<th>Área / Cargo</th>");
                                    out.print("<th>Fecha</th>");
                                    out.print("<th>Motivo</th>");
                                    out.print("<th>Detalle</th>");
                                    out.print("</tr>");
                                    for (int j = 0; j < lst_rotacion_detallado.size(); j++) {
                                        Object[] obj_rotacion_detallado = (Object[]) lst_rotacion_detallado.get(j);
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>" + (j + 1) + "</b></td>");
                                        out.print("<td align='center'><b class='tooltip'>" + obj_rotacion_detallado[1] + "<span id='Ocultar_foto_informe' class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_rotacion_detallado[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                                        out.print("<td>" + ((obj_rotacion_detallado[2] == null) ? "NN" : "" + obj_rotacion_detallado[2]) + "<br />" + ((obj_rotacion_detallado[3] == null) ? "" : "" + obj_rotacion_detallado[3]) + "</td>");
                                        out.print("<td>" + obj_rotacion_detallado[7] + "<br />" + obj_rotacion_detallado[6] + "</td>");
                                        out.print("<td>" + obj_rotacion_detallado[4] + "</td>");
                                        out.print("<td>" + obj_rotacion_detallado[8] + "</td>");
                                        out.print("<td>" + obj_rotacion_detallado[9] + "</td>");
                                        out.print("</tr>");
                                    }
                                    out.print("</table>");
                                    out.print("</div>");
                                    out.print("<br /><br /><br />");
                                } else {
                                    out.print("<b><No se reportar retiros de personal</b>");
                                }
                            }
                        }
                        out.print("</div></center>");
                        out.print("</div>");
//</editor-fold>
                    }
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="GENERACIÓN CARNE">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Carnets")) {
                    out.print("<div id='content_sin'>");
                    try {
                        out.print("<h3>");
                        if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                            out.print("<a onclick='Imprimir()'><span class='fa fa-print fa-size_super_small'></span></a>");
                            out.print("Generación de carné provicionales</h3>");
                        } else {
                            out.print("Generación de carné provicionales</h3>");
                        }
                        out.print("<div style='float:right'><b>Fecha Vigencia : </b><input type='hidden' value='" + anio + "-" + ((mes.length() == 1) ? "0" + mes : mes) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "' id='start' autocomplete='off'/><input type='text' id='end' onchange='VigenciaCarne()' value='" + anio + "-" + ((mes_mas.length() == 1) ? "0" + mes_mas : mes_mas) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "' autocomplete='off'/></div>");
                        out.print("</h3>");
                        out.print("<div id='Imprimir'>");
                        out.print("<table class='table' style='width:100%;font-size:12px'>");
                        consulta = pageContext.getSession().getAttribute("Consulta").toString();
                        if (!"".equals(consulta)) {
                            String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int i = 0; i < arg_consulta.length; i++) {
                                lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                Object[] obj_persona = (Object[]) lst_persona.get(0);
                                if (i == 0) {
                                    out.print("<input type='hidden' id='CantCarne' value='" + arg_consulta.length + "' />");
                                }
                                //<editor-fold defaultstate="collapsed" desc="CARNE OLD">
//                                out.print("<tr>");
//                                out.print("<td colspan='2'>");
//                                out.print("</td>");
//                                out.print("</tr>");
//                                out.print("<tr >");
//                                //out.print("<td style='border:2px solid #0984e3;border-radius:15px;padding:10px;width:50%;height:200px;background-image: linear-gradient(to bottom left, #74b9ff 50%, white 50%);'>");
//                                out.print("<td style='border:2px solid #0984A2;border-radius:15px;padding:20px;width:50%;height:200px;background-image:url(\"Interfaz/MasterPage/images/BG9.png\");background-size: auto;background-color:#fff'>");
//                                out.print("<div style='float:right;font-size:18px;width:40%' align='center'>");
//                                out.print("<img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style=\"width:70%;border-radius:15%;border: 2px solid #fff;\" /><br /><i><b style='color:#0984A2'>RH :</b><i contenteditable='true'>" + obj_persona[22] + "</i></i>");
//                                out.print("</div>");
//                                out.print("<div style='float:left;width:60%;font-size:11px;'>");
//                                out.print("<img src='Interfaz/MasterPage/images/Logo_carne2.png' style='width:50%;heigth:50%'/><br />");
//                                out.print("<b style='color:#EA4335;font-size:14px;'>CARNÉ PROVISIONAL</b><br />");
////                                out.print("<b style='color:ORANGE'>FECHA GENERACIÓN : </b><i>" + anio + "-" + ((mes.length() == 1) ? "0" + mes : mes) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "</i><br /><b style='color:ORANGE'>FECHA VIGENCIA : </b><i id='FechaVigencia" + i + "'>" + anio + "-" + ((mes_mas.length() == 1) ? "0" + mes_mas : mes_mas) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "</i><br />");
//                                out.print("<b style='color:#0984A2'>" + obj_persona[2] + " " + obj_persona[1] + "</b><br /><b style='color:#0984A2'>CC : </b>" + obj_persona[0] + "<br /><b style='color:#0984A2'>AREA : </b>" + obj_persona[9] + "<br /><b style='color:#0984A2'>CARGO : </b>" + obj_persona[7]);
//                                out.print("</div>");
//                                out.print("</td>");
//                                out.print("<td align='center' style='border:2px solid #0984A2;border-radius:15px;background-image:url(\"Interfaz/MasterPage/images/BG9.png\");background-size: auto;background-color:#fff'>");
//                                out.print("<table align='center'><tr><td style='background:none;border:none'><img src='Interfaz/MasterPage/images/Logo_carne2.png' style='width:80%;heigth:80%;' /></td>");
//                                //<editor-fold defaultstate="collapsed" desc="CODIGO DE BARRAS">
//                                out.print("<td style='background:none;border:none'><script type='text/javascript'>$(document).ready(function () {$('#bcTarget" + i + "').barcode('" + ((obj_persona[5] == null) ? "0" : (Integer.parseInt(obj_persona[5].toString()) + 10000)) + "', 'code128', {barWidth: 2, barHeight: 50});});</script>");
//                                out.print("<div style='margin-top:10px;float:left;color:#596275' id='bcTarget" + i + "'></div></td>");
////</editor-fold>
//                                out.print("</tr></table>");
//                                out.print("<br /><b style='color:ORANGE'>FECHA GENERACIÓN : </b><i>" + anio + "-" + ((mes.length() == 1) ? "0" + mes : mes) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "</i><br /><b style='color:ORANGE'>FECHA VIGENCIA : </b><i id='FechaVigencia" + i + "'>" + anio + "-" + ((mes_mas.length() == 1) ? "0" + mes_mas : mes_mas) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "</i><br />");
//                                out.print("<b style='color:0984A2'>CARRERA 56 No. 5C-72 TEL: 2614706 </b>");
//                                out.print("</td>");
//                                out.print("</tr>");
//</editor-fold>
                                out.print("<tr>");
                                out.print("<td colspan='2'></td>");
                                out.print("</tr>");
                                out.print("<tr >");
                                out.print("<td style='border:2px solid #0984A2;border-radius:15px;padding:12px;width:500px;height:280px;background-image:url(\"Interfaz/MasterPage/images/BG9.png\");background-size: auto;background-color:#fff'>");
                                out.print("<table style='width:100%'>");
                                out.print("<tr>");
                                out.print("<td style='width:30%;background:none;border:none' rowspan='3'>");
                                out.print("<img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style=\"width:70%;border-radius:15%;border: 2px solid #fff;\" /><br /><i><b style='color:#0984A2'>RH :</b><i contenteditable='true'>" + obj_persona[22] + "</i></i>");
                                //out.print("<img id='Img_foto' src='Interfaz/MasterPage/images/No_encontrado.jpg' src='Fotos/" + obj_persona[0] + ".jpg' style=\"width:70%;border-radius:15%;border: 2px solid #fff;\" /><br /><i><b style='color:#0984A2'>RH :</b><i contenteditable='true'>" + obj_persona[22] + "</i></i>");
                                out.print("</td>");
                                out.print("<td style='width:60%;background-color:#0984A2;border:none'>");
                                out.print("<img src='Interfaz/MasterPage/images/Logo_Plastitec_Ph.png' style='width:100%;heigth:100%'/><br />");
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:60%;background:none;border:none'>");
                                out.print("<b style='color:#EA4335;font-size:14px;'>CARNÉ PROVISIONAL</b><br />");
                                out.print("<b style='color:#0984A2'>" + obj_persona[2] + " " + obj_persona[1] + "</b><br /><b style='color:#0984A2'>CC : </b>" + obj_persona[0] + "<br /><b style='color:#0984A2'>AREA : </b>" + obj_persona[9] + "<br /><b style='color:#0984A2'>CARGO : </b>" + obj_persona[7]);
                                out.print("</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='background:none;border:none'><script type='text/javascript'>$(document).ready(function () {$('#bcTarget" + i + "').barcode('" + ((obj_persona[5] == null) ? "0" : (Integer.parseInt(obj_persona[5].toString())) + 10000) + "', 'code128', {barWidth: 3, barHeight: 30});});</script>");
                                out.print("<div style='margin-top:10px;float:left;color:#596275' id='bcTarget" + i + "'></div></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</td>");
                                out.print("<td align='center' style='font-size:18px;border:2px solid #0984A2;border-radius:15px;padding:12px;width:500px;height:280px;background-image:url(\"Interfaz/MasterPage/images/BG9.png\");background-size: auto;background-color:#fff'>");
                                out.print("<b style='color:0984A2;font-size:30px'>PLASTITEC SA</b>");
                                out.print("<br /><b style='color:ORANGE'>FECHA GENERACIÓN : </b><i>" + anio + "-" + ((mes.length() == 1) ? "0" + mes : mes) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "</i><br /><b style='color:ORANGE'>FECHA VIGENCIA : </b><i id='FechaVigencia" + i + "'>" + anio + "-" + ((mes_mas.length() == 1) ? "0" + mes_mas : mes_mas) + "-" + ((dia.length() == 1) ? "0" + dia : dia) + "</i><br />");
                                out.print("<b style='color:0984A2'>CARRERA 56 No. 5C-72 TEL: 2614706 </b>");
                                out.print("</td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("</div>");
                    } catch (Exception e) {
                        out.print("Se debe seleccionar personal del modulo de Consulta de personal.");
                    }
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="VERIFICACION REGISTROS">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Verificacion_registros")) {
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    mes_report = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3><a style='text-decoration:none' href='Reportes?opc=6&mnu=31&Cbx_anio=0'><span class='fa fa-search fa-size_super_small'></span></a>");
                    out.print("Verificación de registros");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_informe()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("</h3>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    if (anio_report == 0) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Popup_informe_ausentismo' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        //out.print("<div style='float:right;'><a href='Reportes?opc=4&mnu=13&Cbx_anio=" + anio + "&Cbx_mes=" + mes + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Popup_informe_ausentismo').style.display='none';\" class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de informe</h3>");
                        out.print("Seleccionar año y mes para verificar los movimientos de los modulos de seguimientos al personal.<br />");
                        out.print("<form action='Reportes?opc=6&mnu=31' method='post'>");
                        out.print("<b>Año :</b><br /><select name='Cbx_anio' id='Cbx_anio'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i <= 5; i++) {
                            out.print("<option value='" + (anio - i) + "' >" + (anio - i) + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Mes :</b><br /><select name='Cbx_mes' id='Cbx_mes'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i < 12; i++) {
                            out.print("<option value='" + arg_meses[i].split(" ")[0] + "' >" + arg_meses[i].split(" ")[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mes');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Generar'>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    if (anio_report == 0) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado Año y mes</center>");
                    } else {
                        out.print("<center>");
                        for (int i = 0; i < arg_meses.length; i++) {
                            if (arg_meses[i].split(" ")[0].equals(mes_report + "")) {
                                out.print("<a href='Reportes?opc=6&mnu=31&Cbx_anio=" + anio_report + "&Cbx_mes=" + arg_meses[i].split(" ")[0] + "'><i> <b>" + arg_meses[i].split(" ")[1] + "</b></i></a> -");
                            } else {
                                out.print("<a href='Reportes?opc=6&mnu=31&Cbx_anio=" + anio_report + "&Cbx_mes=" + arg_meses[i].split(" ")[0] + "'><i> " + arg_meses[i].split(" ")[1] + "</i></a> -");
                            }
                        }
                        out.print("<br /><br />");
                        out.print("<div id='Imprimir_informe'>");
                        out.print("<h3><b>" + arg_meses[(mes_report - 1)].split(" ")[1] + "</b> del <b>" + anio_report + "</b></h3>");
                        out.print("<table class='table' style='width:70%'>");
                        out.print("<tr>");
                        out.print("<th>Modulo</th>");
                        out.print("<th>Estado</th>");
                        out.print("<th>Contadores</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        lst_verificacion_registros = jpacmnu.Verificacion_registros_anio_mes(anio_report, mes_report);
                        for (int j = 0; j < lst_verificacion_registros.size(); j++) {
                            Object[] obj_verificacion_registros = (Object[]) lst_verificacion_registros.get(j);
                            out.print("<tr>");
                            if (j % 2 == 0) {
                                out.print("<td rowspan='2'>" + obj_verificacion_registros[0] + "</td>");
                                if (Integer.parseInt(obj_verificacion_registros[1].toString()) > 0) {
                                    out.print("<td align='center' style='background-color:#EAFAF1'><b class='verde'>Cerrados</b></td>");
                                    out.print("<td align='center' style='background-color:#EAFAF1'><b class='verde'>" + obj_verificacion_registros[1] + "</b></td>");
                                } else {
                                    out.print("<td align='center' style='background-color:#EAFAF1'>Cerrados</td>");
                                    out.print("<td align='center' style='background-color:#EAFAF1'><b class='naranja'>---</b></td>");
                                }
                            } else if (Integer.parseInt(obj_verificacion_registros[1].toString()) > 0) {
                                out.print("<td align='center' style='background-color:#FDEDEC'><b class='rojo'>Abiertos</b></td>");
                                out.print("<td align='center' style='background-color:#FDEDEC'><b class='rojo'>" + obj_verificacion_registros[1] + "</b></td>");
                            } else {
                                out.print("<td align='center' style='background-color:#FDEDEC'>Abiertos</td>");
                                out.print("<td align='center' style='background-color:#FDEDEC'><b class='naranja'>---</b></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</center>");
                    }
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CALIFICACIÓN DE COMPETENCIAS">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Calificacion_competencias")) {
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    mes_report = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3><a style='text-decoration:none' href='Reportes?opc=7&mnu=32&Cbx_anio=0'><span class='fa fa-tasks fa-size_super_small'></span></a>");
                    out.print("Calificación de Competencias");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_informe()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("</h3>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    if (anio_report == 0) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Popup_informe_ausentismo' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        //out.print("<div style='float:right;'><a href='Reportes?opc=4&mnu=13&Cbx_anio=" + anio + "&Cbx_mes=" + mes + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Popup_informe_ausentismo').style.display='none';\" class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de informe</h3>");
                        out.print("Seleccionar año y mes para verificar los movimientos de los odulos de seguimientos al personal.<br />");
                        out.print("<form action='Reportes?opc=7&mnu=32' method='post'>");
                        out.print("<b>Año :</b><br /><select name='Cbx_anio' id='Cbx_anio'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i <= 5; i++) {
                            out.print("<option value='" + (anio - i) + "' >" + (anio - i) + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Generar'>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    if (anio_report == 0) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado Año y mes</center>");
                    } else {
                        out.print("<center>");
                        for (int i = 0; i < arg_meses.length; i++) {
                            if (arg_meses[i].split(" ")[0].equals(mes_report + "")) {
                                out.print("<a href='Reportes?opc=7&mnu=32&Cbx_anio=" + anio_report + "&Cbx_mes=" + arg_meses[i].split(" ")[0] + "'><i> <b>" + arg_meses[i].split(" ")[1] + "</b></i></a> -");
                            } else {
                                out.print("<a href='Reportes?opc=7&mnu=32&Cbx_anio=" + anio_report + "&Cbx_mes=" + arg_meses[i].split(" ")[0] + "'><i> " + arg_meses[i].split(" ")[1] + "</i></a> -");
                            }
                        }
                        out.print("<br /><br />");
                        out.print("<div id='Imprimir_informe'>");
                        out.print("<h3><b>" + arg_meses[(mes_report - 1)].split(" ")[1] + "</b> del <b>" + anio_report + "</b></h3>");
                        out.print("<table class='table' style='width:70%'>");
                        out.print("<tr>");
                        out.print("<th rowspan='2'>Área</th>");
                        out.print("<th colspan='4'>Calificación de competencias</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' style='text-transform: capitalize;'><b>Total Programadas</b></td>");
                        out.print("<td align='center' style='text-transform: capitalize;'><b>Total Realizadas</b></td>");
                        out.print("<td align='center' style='text-transform: capitalize;'><b>Con Recomendaciones</b></td>");
                        out.print("<td align='center' style='text-transform: capitalize;'><b>Satisfactorias</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        String arg_areas_content = "";
                        lst_calificacion_competencias = jpacmnu.Informe_calificacion_competencias(anio_report, mes_report);
                        if (lst_calificacion_competencias != null) {
                            for (int j = 0; j < lst_calificacion_competencias.size(); j++) {
                                Object[] obj_calificacion_competencias = (Object[]) lst_calificacion_competencias.get(j);
                                arg_areas_content += obj_calificacion_competencias[1];
                            }
                        }
                        lst_areas = jpacara.Consultar_areas();
                        int total_p = 0;
                        int total_r = 0;
                        int total_rec = 0;
                        int total_sat = 0;
                        for (int i = 0; i < lst_areas.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_areas.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_areas[1] + "</td>");
                            if (lst_calificacion_competencias == null) {
                                out.print("<td align='center' style='background-color:#F5EEF8'><b class='naranja'>---</b></td>");
                                out.print("<td align='center' style='background-color:#D4FDFF'><b class='naranja'>---</b></td>");
                                out.print("<td align='center' style='background-color:#FDEDEC'><b class='naranja'>---</b></td>");
                                out.print("<td align='center' style='background-color:#EAFAF1'><b class='naranja'>---</b></td>");
                            } else if (!arg_areas_content.contains("" + obj_areas[1])) {
                                out.print("<td align='center' style='background-color:#F5EEF8'><b class='naranja'>---</b></td>");
                                out.print("<td align='center' style='background-color:#D4FDFF'><b class='naranja'>---</b></td>");
                                out.print("<td align='center' style='background-color:#FDEDEC'><b class='naranja'>---</b></td>");
                                out.print("<td align='center' style='background-color:#EAFAF1'><b class='naranja'>---</b></td>");
                            } else {
                                String dato_totalp = "";
                                String dato_totalr = "";
                                String dato_bajo = "";
                                String dato_alto = "";
                                for (int j = 0; j < lst_calificacion_competencias.size(); j++) {
                                    Object[] obj_calificacion_competencias = (Object[]) lst_calificacion_competencias.get(j);
                                    if (obj_calificacion_competencias[0].equals(obj_areas[0]) && obj_calificacion_competencias[2].toString().equals("Total_p")) {
                                        dato_totalp = obj_calificacion_competencias[3].toString();
                                    }
                                    if (obj_calificacion_competencias[0].equals(obj_areas[0]) && obj_calificacion_competencias[2].toString().equals("Total_r")) {
                                        dato_totalr = obj_calificacion_competencias[3].toString();
                                    }
                                    if (obj_calificacion_competencias[0].equals(obj_areas[0]) && obj_calificacion_competencias[2].toString().equals("No")) {
                                        dato_bajo = obj_calificacion_competencias[3].toString();
                                    }
                                    if (obj_calificacion_competencias[0].equals(obj_areas[0]) && obj_calificacion_competencias[2].toString().equals("Si")) {
                                        dato_alto = obj_calificacion_competencias[3].toString();
                                    }
                                }
                                if (dato_totalp.length() > 0) {
                                    out.print("<td align='center' style='background-color:#F5EEF8'><b class='negro'>" + dato_totalp + "</b></td>");
                                }
                                if (dato_totalr.length() > 0) {
                                    out.print("<td align='center' style='background-color:#D4FDFF'><b class='negro'>" + dato_totalr + "</b></td>");
                                } else {
                                    out.print("<td align='center' style='background-color:#D4FDFF'><b class='rojo'>0</b></td>");
                                    dato_totalr = "0";
                                }
                                if (dato_bajo.length() > 0) {
                                    out.print("<td align='center' style='background-color:#FDEDEC'><b class='rojo'>" + dato_bajo + "</b></td>");
                                } else {
                                    out.print("<td align='center' style='background-color:#FDEDEC'><b class='rojo'>0</b></td>");
                                    dato_bajo = "0";
                                }
                                if (dato_alto.length() > 0) {
                                    out.print("<td align='center' style='background-color:#EAFAF1'><b class='verde'>" + dato_alto + "</b></td>");
                                } else {
                                    out.print("<td align='center' style='background-color:#EAFAF1'><b class='verde'>0</b></td>");
                                    dato_alto = "0";
                                }
                                total_p += Integer.parseInt(dato_totalp);
                                total_r += Integer.parseInt(dato_totalr);
                                total_rec += Integer.parseInt(dato_bajo);
                                total_sat += Integer.parseInt(dato_alto);
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td><b>Cantidad Total</b></td>");
                        out.print("<td align='center'><b>" + total_p + "</b></td>");
                        out.print("<td align='center'><b>" + total_r + "</b></td>");
                        out.print("<td align='center'><b>" + total_rec + "</b></td>");
                        out.print("<td align='center'><b>" + total_sat + "</b></td>");
                        out.print("</tr>");
                        if (total_p > 0) {
                            out.print("<tr>");
                            out.print("<td><b>Alcance Total</b></td>");
                            out.print("<td align='center'><b>100%</b></td>");
                            out.print("<td align='center'><b>" + ((total_r * 100) / total_p) + "%</b></td>");
                            out.print("<td align='center'><b>" + ((total_rec * 100) / total_r) + "%</b></td>");
                            out.print("<td align='center'><b>" + ((total_sat * 100) / total_r) + "%</b></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</center>");
                    }
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTUALIZAR SALARIOS">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Actualizar_salarios")) {
                    validacion = Integer.parseInt(pageContext.getRequest().getAttribute("Validacion").toString());
                    archivo_plano = pageContext.getRequest().getAttribute("Salarios").toString();
                    out.print("<div id='content_sin'>");
                    if (validacion == 1) {
                        out.print("<br /><a href='Reportes?opc=8&mnu=35&vsl=0'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                        out.print("<h3>Actualización de Salarios<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else if (validacion == 2) {
                        out.print("<br /><a href='Reportes?opc=8&mnu=35&vsl=0'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                        out.print("<h3>Historial Actualización de Salarios<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else {
                        out.print("<h3>Actualización de Salarios</h3>");
                    }
                    if (validacion == 1) {
                        //<editor-fold defaultstate="collapsed" desc="VALIDACION SALARIOS">
//                        out.print(""+salarios);
                        archivo_plano = archivo_plano.replace(" | ", "--__--");
                        arg_contenedor = archivo_plano.split("--__--");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Estado</th>");
                        out.print("<th># Documento</th>");
                        out.print("<th>Nombre</th>");
                        out.print("<th>Contrato</th>");
                        out.print("<th>Salario Actual</th>");
                        out.print("<th>Salario Nuevo</th>");
                        out.print("<th>%</th>");
                        out.print("</tr>");
                        int cont_sube = 0;
                        int cont_mantiene = 0;
                        int cont_baja = 0;
                        int cont_no_archivo = 0;
                        int cont_no_bd = 0;
                        for (int i = 0; i < arg_contenedor.length; i++) {
                            if (i != 0) {
                                arg_datos = arg_contenedor[i].split(";");
                                lst_personal = jpacpsn.Consultar_empleado_documento("" + arg_datos[0].trim());
                                double porcentaje = 0;
                                if (lst_personal == null) {
                                    out.print("<tr class='rojo'>");
                                    out.print("<td align='center'><span class='fa fa-user-times fa-size_small'></span></td>");
                                    out.print("<td>" + arg_datos[0] + "</td>");
                                    out.print("<td>" + arg_datos[3] + " " + arg_datos[2] + "</td>");
                                    out.print("<td>SIN DEFINIR</td>");
                                    out.print("<td>" + arg_datos[1] + "</td>");
                                    cont_no_archivo++;
                                } else {
                                    Object[] obj_personal = (Object[]) lst_personal.get(0);
                                    int nuevo_salario = Integer.parseInt(arg_datos[1].trim());
                                    int salario_actual = Integer.parseInt(obj_personal[12].toString());
                                    if (nuevo_salario > salario_actual) {
                                        porcentaje = 100 - ((salario_actual * 100) / nuevo_salario);
                                        cont_sube++;
                                        new_salarios += nuevo_salario + ",";
                                        old_salarios += salario_actual + ",";
                                        actualizacion_empleados += obj_personal[0] + ",";
                                    } else if (nuevo_salario < salario_actual) {
                                        porcentaje = (100 - ((nuevo_salario * 100) / salario_actual)) * -1;
                                        cont_baja++;
                                        new_salarios += nuevo_salario + ",";
                                        old_salarios += salario_actual + ",";
                                        actualizacion_empleados += obj_personal[0] + ",";
                                    } else {
                                        porcentaje = 0;
                                        cont_mantiene++;
                                    }
                                    out.print("<tr>");
                                    out.print("<td align='center'><b class='" + ((nuevo_salario == salario_actual) ? "naranja" : ((nuevo_salario > salario_actual) ? "verde" : "rojo")) + "'><span class='fa fa-donate fa-size_small'></span></b></td>");
                                    out.print("<td>" + obj_personal[0] + "</td>");
                                    out.print("<td>" + obj_personal[2] + " " + obj_personal[1] + "</td>");
                                    out.print("<td>" + ((obj_personal[13].toString().equals("1")) ? "Directo" : "Temporal") + "</td>");
                                    out.print("<td>" + obj_personal[12] + "</td>");
                                }
                                out.print("<td>" + arg_datos[1] + "</td>");
                                out.print("<td align='center'>" + porcentaje + "%</td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        //<editor-fold defaultstate="collapsed" desc="TABAL RESULTADOS">
                        out.print("<h3>Resultados</h3>");
                        out.print("<table class='table' >");
                        out.print("<tr>");
                        out.print("<th>Estado</th>");
                        out.print("<th>Descripción</th>");
                        out.print("<th>Contador</th>");
                        out.print("<td rowspan='5' style='width:50%;padding:15px;border:3px dashed orange;' valign='top'><h3>Confirmar Actualización de salarios</h3>");
                        out.print("<form action='Reportes?opc=9' method='post'>");
                        out.print("<div style='float:left;width:45%'>");
                        out.print("<b>Fecha :</b><br /><input type='text' style='width:95%'name='Txt_fecha' id='datepicker' autocomplete='off' placeholder='Fecha de actualización' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script><br />");
                        out.print("<b>Concepto :</b><br /><textarea style='width:95%' name='Txt_concepto' id='Txt_concepto' placeholder='Concepto de actualización'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_concepto');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div style='width:45%;float:right'>"
                                + "Al confirmar esta actualización de salario se procedera con el cambio masivo para el personal que presenta estas condiciones, incremento o depreciación de salario."
                                + "<input type='hidden' name='Txt_archivo_plano' value='" + archivo_plano + "'>"
                                + "<input type='hidden' name='Txt_new_salarios' value='" + new_salarios + "'>"
                                + "<input type='hidden' name='Txt_old_salarios' value='" + old_salarios + "'>"
                                + "<input type='hidden' name='Txt_act_empleados' value='" + actualizacion_empleados + "'>"
                                + "<input type='submit' value='Actualizar Salarios'>"
                                + "</div>");
                        out.print("</form>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b class='rojo'><span class='fa fa-user-times fa-size_small'></span></b></td>");
                        out.print("<td>Personal en archivo plano pero no se encuentra en la base de datos</td>");
                        out.print("<td align='center'>" + cont_no_archivo + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b class='verde'><span class='fa fa-donate fa-size_small'></span></b></td>");
                        out.print("<td>Personal que presenta incremento en el salario.</td>");
                        out.print("<td align='center'>" + cont_sube + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b class='naranja'><span class='fa fa-donate fa-size_small'></span></b></td>");
                        out.print("<td>Personal sin cambios en el salario.</td>");
                        out.print("<td align='center'>" + cont_mantiene + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'><b class='rojo'><span class='fa fa-donate fa-size_small'></span></b></td>");
                        out.print("<td>Personal que se deprecia su salario actual.</td>");
                        out.print("<td align='center'>" + cont_baja + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
//</editor-fold>
                    } else if (validacion == 2) {
                        //<editor-fold defaultstate="collapsed" desc="HISTORICO">
                        lst_actualizacion_salarios = jpacmnu.Consulta_actualizacion_salarios();
                        if (lst_actualizacion_salarios != null) {
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th>Fecha</th>");
                            out.print("<th>Concepto</th>");
                            out.print("<th>Responsable</th>");
//                        out.print("<th>Opc.</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_actualizacion_salarios.size(); i++) {
                                Object[] obj_act_salarios = (Object[]) lst_actualizacion_salarios.get(i);
                                out.print("<tr>");
                                out.print("<td align='center'><b>" + obj_act_salarios[1] + "</b></td>");
                                out.print("<td>" + obj_act_salarios[3] + "</td>");
                                out.print("<td>" + obj_act_salarios[9] + "</td>");
//                            out.print("<td align='center' style='width:15%'>");
//                            if (Integer.parseInt(obj_act_salarios[7].toString()) == 1) {
//                                out.print("<a onclick=\"\"><span class='fa fa-eye fa-size_small'></span></a>");
//                                out.print("&nbsp;&nbsp;&nbsp;<a onclick=\"\"><span class='fa fa-eye fa-size_small'></span></a>");
//                            } else {
//                                out.print("<a onclick=\"\"><span class='fa fa-eye fa-size_small'></span></a>");
//                                out.print("&nbsp;&nbsp;&nbsp;<a onclick=\"\"><span class='fa fa-eye fa-size_small'></span></a>");
//                            }
//                            out.print("</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados', 10);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                        } else {
                            out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se encuentran resultados</center>");
                        }
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="ARCHIVO PLANO">
                        out.print("<label for=\"file-input\" style='padding: 5px 10px;background: #fff;color:#596275;border-right:1px solid #596275;cursor:pointer;'>\n"
                                + "<i class=\"fa fa-cloud-upload-alt fa-size_super_small\"></i> Subir archivo\n"
                                + "</label>\n"
                                + "<input id=\"file-input\" onchange='cambiar()' type=\"file\" style='display: none;'/>");
                        lst_vistas = jpacmnu.Vistas_sirh();
                        for (int i = 0; i < lst_vistas.size(); i++) {
                            Object[] obj_vistas = (Object[]) lst_vistas.get(i);
                            if (obj_vistas[0].toString().equals("vw_salario_personal")) {
                                out.print("<a href='Reportes?opc=2&mnu=12&ept=1&fnm=" + obj_vistas[0].toString().replace("vw_", "") + "&fpt=" + obj_vistas[0] + "&trp=1'><label style='padding: 5px 10px;background: #fff;color:#596275;border-right:1px solid #596275;cursor:pointer;'>\n"
                                        + "<i class=\"fa fa-file-excel fa-size_super_small\"></i> Salarios del personal activo\n"
                                        + "</label></a>\n");
                            }
                        }
                        out.print("<a href='Reportes?opc=8&mnu=35'><label style='padding: 5px 10px;background: #fff;color:#596275;border-right:1px solid #596275;cursor:pointer;'>\n"
                                + "<i class=\"fa fa-eraser fa-size_super_small\"></i> Limpiar Archivo\n"
                                + "</label></a>\n");
                        out.print("<a href='Reportes?opc=8&mnu=35&vsl=2'><label style='padding: 5px 10px;background: #fff;color:#596275;border-right:1px solid #596275;cursor:pointer;'>\n"
                                + "<i class=\"fa fa-money-check-alt fa-size_super_small\"></i> Historico\n"
                                + "</label></a>\n");
                        out.print("<h3>Contenido del archivo: <b id='File_name' style='font-size:16px'></b></h3>");
                        out.print("<pre id='contenido-archivo' style='height:50%;width:35%;overflow-y: auto;padding:10px;background-color:#ddd;border-radius:25px;margin:0px;float:left'></pre>");
                        //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                        out.print("<div style='padding:10px;float:left;width:20%'>");
                        out.print("<b class='negro'>Opciones</b>");
                        out.print("<br /><br />");
                        out.print("<label id='Id_separar' onclick='SepararData()' style='pointer-events:none;background: #fff;color:#aaa;cursor:pointer;padding: 5px 10px'>\n"
                                + "<i class=\"fa fa-funnel-dollar fa-size_super_small\"></i> Separar contenido"
                                + "</label><br /><hr />");
                        out.print("<label id='Id_integridad' onclick='IntegridadData()' style='pointer-events:none;background: #fff;color:#aaa;cursor:pointer;padding: 5px 10px'>\n"
                                + "<i class=\"fa fa-search-dollar fa-size_super_small\"></i> Integridad de los datos"
                                + "</label><br /><hr />");
                        out.print("<label id='Cont_mal' style='background: #fff;color:#EA4335;padding: 5px 10px'>\n"
                                + "<i class=\"fa fa-times fa-size_super_small\"></i> ---"
                                + "</label><br /><hr />");
                        out.print("<label id='Cont_bien' style='background: #fff;color:#3AA757;padding: 5px 10px'>\n"
                                + "<i class=\"fa fa-check fa-size_super_small\"></i> ---"
                                + "</label><br /><hr />");
                        out.print("<label id='Id_verificar' onclick=\"javascript:document.getElementById('Control_carga').style.display='block';VerificarData()\" style='pointer-events:none;background: #fff;color:#aaa;cursor:pointer;padding: 5px 10px'>\n"
                                + "<i class=\"fa fa-comments-dollar fa-size_super_small\"></i> Verificación de datos"
                                + "</label>");
                        out.print("</div>");
//</editor-fold>
                        out.print("<pre id='contenido-proceso' style='height:50%;width:35%;overflow-y: auto;padding:10px;border-radius:25px;margin:0px;float:left'></pre>");
                        out.print("<form action='Reportes?opc=8&mnu=35&vsl=1' method='post' id='FormVerificarData'><input type='hidden' id='Txt_salarios' name='Txt_salarios' />"
                                + "<input type='hidden' id='Txt_salarios_error' name='Txt_salarios_error' /></form>");
//</editor-fold>
                    }
                    out.print("</div>");
                    out.print("<div class='clear'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="AUSENTISMO SST">
                else if (pageContext.getRequest().getAttribute("Reportes").equals("Ausencias_sst")) {
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    mes_report = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3><a style='text-decoration:none' href='Reportes?opc=11&mnu=39'><span class='fas fa-chart-area fa-size_super_small'></span></a>");
                    out.print("Generar informe de ausentismo SGSST");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_informe()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("</h3>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO FECHAS">
                    if (anio_report == 0) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Popup_informe_ausentismo' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        //out.print("<div style='float:right;'><a href='Reportes?opc=4&mnu=13&Cbx_anio=" + anio + "&Cbx_mes=" + mes + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Popup_informe_ausentismo').style.display='none';\" class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de informe</h3>");
                        //out.print("Ingresar rango de fechas para limitar muestra de datos para el informe de Ausentismo.<br /><br />");
                        out.print("Seleccionar año y mes para limitar muestra de datos para el informe de Ausentismo.<br />");
                        out.print("<form action='Reportes?opc=11&mnu=39' method='post'>");
//                        out.print("<b>Fecha Inicio :</b><br /><input type='text' name='Txt_fecha_inicio' id='start' autocomplete='off' placeholder='Fecha_inicio' />"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
//                        out.print("<br /><b>Fecha Fin :</b><br /><input type='text' name='Txt_fecha_fin' id='end' autocomplete='off' placeholder='Fecha_fin' />"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                        out.print("<b>Año :</b><br /><select name='Cbx_anio' id='Cbx_anio'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i <= 5; i++) {
                            out.print("<option value='" + (anio - i) + "' >" + (anio - i) + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Mes :</b><br /><select name='Cbx_mes' id='Cbx_mes'>");
                        out.print("<option value='0' selected>Click para seleccionar</option>");
                        for (int i = 0; i < 12; i++) {
                            out.print("<option value='" + arg_meses[i].split(" ")[0] + "' >" + arg_meses[i].split(" ")[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mes');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Generar'>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    if (anio_report == 0) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha filtrado Año y mes</center>");
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="VARIABLES Y PARAMETRIZACIÓN">
                        lst_informe_agrupado = jpacmnu.Informe_ausencias_agrupado_sst(anio_report, mes_report);
                        lst_informe_detallado = jpacmnu.Informe_ausencias_detallado_sst(anio_report, mes_report);
                        lst_empleados_anio_mes = jpacmnu.Empleados_anio_mes_sst(anio_report, mes_report);
                        if (lst_empleados_anio_mes == null) {
                            out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se ha encontrado información del Año y mes filtrado</center>");
                        } else {
                            Object[] obj_empleados_anio_mes = (Object[]) lst_empleados_anio_mes.get(0);
                            int total_casos = 0;
                            double total_costos = 0;
                            double total_horas = 0;
                            int porcentaje_casos = 0;
                            double dias_totales = 0;
                            double porcentaje_costos = 0;
                            double porcentaje_horas = 0;
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                total_casos = total_casos + Integer.parseInt(obj_informe_agrupado[1].toString());
                                total_costos = total_costos + Double.parseDouble(obj_informe_agrupado[3].toString());
                                total_horas = total_horas + Double.parseDouble(obj_informe_agrupado[2].toString());
                            }
                            BigDecimal big_horas = new BigDecimal(total_horas);
                            BigDecimal big_costos = new BigDecimal(total_costos);
                            big_costos = big_costos.setScale(2, BigDecimal.ROUND_HALF_UP);
                            big_horas = big_horas.setScale(2, BigDecimal.ROUND_HALF_UP);
                            BigDecimal big_porcentaje_ausencias = new BigDecimal(((total_horas / 24 * 100) / Double.parseDouble(obj_empleados_anio_mes[1].toString())));
                            big_porcentaje_ausencias = big_porcentaje_ausencias.setScale(2, BigDecimal.ROUND_HALF_UP);
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REPORTE">
                            out.print("<div id='Imprimir_informe'>");
                            out.print("<table class='table' >");
                            out.print("<tr>");
                            out.print("<th colspan='2' style='width:30%'>Resumen</th>");
                            out.print("<td rowspan='9' style='padding:25px' valign='top'>");
                            out.print("<center><h3>Reporte de Ausentismo SST <b>" + arg_meses[(mes_report - 1)].split(" ")[1] + "</b> <b>" + anio_report + "</b></h3></center>");
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                out.print("<div class='pb_informe'>");
                                out.print("<div class='pb_informe_det " + arg_progress_bar[i] + "' style='width:" + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "%;text-align:center;'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "%</div>");
                                out.print("</div>");
                                out.print(" " + obj_informe_agrupado[0] + "<br />");
                            }
                            out.print("<center><h3>Porcentaje de ausentimo : <b>" + big_porcentaje_ausencias + "%</b></h3></center>");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Mes</b></td>");
                            out.print("<td>" + arg_meses[(mes_report - 1)].split(" ")[1] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Año</b></td>");
                            out.print("<td>" + anio_report + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Cantidad de trabajadores</b></td>");
                            out.print("<td>" + obj_empleados_anio_mes[0] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Días totales</b></td>");
                            out.print("<td>" + obj_empleados_anio_mes[1] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            dias_totales = Integer.valueOf(big_horas.intValue());
                            dias_totales = Math.round((dias_totales / 24));
                            out.print("<td><b>Días ausencias</b></td>");
                            out.print("<td>" + dias_totales + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Porcentaje ausencias</b></td>");
                            out.print("<td>" + big_porcentaje_ausencias + "%</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Total costo</b></td>");
                            out.print("<td>" + big_costos + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Total casos</b></td>");
                            out.print("<td>" + total_casos + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DISTRIBUCIÓN GENERAL">
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<td colspan='7' align='center'><b>DISTRIBUCIÓN GENERAL</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>Concepto</th>");
                            out.print("<th>Casos</th>");
                            out.print("<th>% Casos</th>");
                            out.print("<th>Costo</th>");
                            out.print("<th>% Costo</th>");
                            out.print("<th>Horas</th>");
                            out.print("<th>% Horas</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_informe_agrupado[0] + "</td>");
                                out.print("<td align='right'>" + obj_informe_agrupado[1] + "</td>");
                                out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "%</td>");
                                BigDecimal big_costo = new BigDecimal(Double.parseDouble(obj_informe_agrupado[3].toString()));
                                big_costo = big_costo.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='right'>" + big_costo + "</td>");
                                out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[3].toString()) * 100) / total_costos) * mult)) / (double) mult + "%</td>");
                                out.print("<td align='right'>" + obj_informe_agrupado[2] + "</td>");
                                out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_agrupado[2].toString()) * 100) / total_horas) * mult)) / (double) mult + "%</td>");
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td><b>Total</b></td>");
                            out.print("<td align='right'><b>" + total_casos + "</b></td>");
                            out.print("<td align='right'><b>100%</b></td>");
                            out.print("<td align='right'><b>" + big_costos + "</b></td>");
                            out.print("<td align='right'><b>100%</b></td>");
                            out.print("<td align='right'><b>" + big_horas + "</b></td>");
                            out.print("<td align='right'><b>100%</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //                        // <editor-fold defaultstate="collapsed" desc="Javascript graficas">
//                        out.print("<script type=\"text/javascript\" src=\"Interfaz/Graficas/js/highcharts_principal.js\"></script>");
//                        out.print("<script src=\"Interfaz/Graficas/js/highcharts.js\"></script>");
//                        out.print("<script src=\"Interfaz/Graficas/js/modules/exporting.js\"></script>");
//                        out.print("<script type=\"text/javascript\">");
//                        out.print("$(function () {");
//                        out.print("$('#Grafica_informe_ausencias').highcharts({");
//                        out.print("chart: {");
//                        out.print("type: 'pie',");
//                        out.print("options3d: {");
//                        out.print("enabled: true,");
//                        out.print("alpha: 45");
//                        out.print("}");
//                        out.print("},");
//                        out.print("title: {");
//                        out.print("text: '...'");
//                        out.print("},");
//                        out.print("subtitle: {");
//                        out.print("text: 'Grafica Informe de ausencias por mes'");
//                        out.print("},");
//                        out.print("plotOptions: {");
//                        out.print("pie: {");
//                        out.print("innerSize: 100,");
//                        out.print("depth: 45");
//                        out.print("}");
//                        out.print("},");
//                        out.print("series: [{");
//                        out.print("name: 'Porcentaje',");
//                        out.print("data: [");
//                        for (int i = 0; i < lst_informe_agrupado.size(); i++) {
//                            Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
//                            if (i == 0) {
//                                out.print("['" + obj_informe_agrupado[0] + " # " + obj_informe_agrupado[1] + "', " + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "]");
//                            } else {
//                                out.print(",['" + obj_informe_agrupado[0] + " # " + obj_informe_agrupado[1] + "', " + (Math.round(((Double.parseDouble(obj_informe_agrupado[1].toString()) * 100) / total_casos) * mult)) / (double) mult + "]");
//                            }
//                        }
//                        out.print("]");
//                        out.print("}]");
//                        out.print("});");
//                        out.print("});");
//                        out.print("</script>");
//                        out.print("<div id='Grafica_informe_ausencias' style='min-width: 310px; margin: 0 auto;'></div>");
//                        // </editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DETALLE MODULOS">
                            for (int i = 0; i < lst_informe_agrupado.size(); i++) {
                                Object[] obj_informe_agrupado = (Object[]) lst_informe_agrupado.get(i);
                                out.print("<button class='accordion'>" + obj_informe_agrupado[0] + "</button>");
                                out.print("<div class='panel'>");
                                if (Integer.parseInt(obj_informe_agrupado[1].toString()) > 0) {
                                    out.print("<table class='table'>");
                                    out.print("<tr>");
                                    out.print("<td colspan='7' align='center'><b>DETALLE " + obj_informe_agrupado[0].toString().toUpperCase() + "</b></td>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<th>Tipo</th>");
                                    out.print("<th>Casos</th>");
                                    out.print("<th>% Casos</th>");
                                    out.print("<th>Costo</th>");
                                    out.print("<th>% Costo</th>");
                                    out.print("<th>Horas</th>");
                                    out.print("<th>% Horas</th>");
                                    out.print("</tr>");
                                    for (int j = 0; j < lst_informe_detallado.size(); j++) {
                                        Object[] obj_informe_detallado = (Object[]) lst_informe_detallado.get(j);
                                        if (obj_informe_detallado[0].toString().equals(obj_informe_agrupado[0].toString())) {
                                            out.print("<tr>");
                                            out.print("<td>" + obj_informe_detallado[1] + "</td>");
                                            out.print("<td align='right'>" + obj_informe_detallado[2] + "</td>");
                                            out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_detallado[2].toString()) * 100) / Double.parseDouble(obj_informe_agrupado[1].toString())) * mult)) / (double) mult + "%</td>");
                                            BigDecimal big_costo = new BigDecimal(Double.parseDouble(obj_informe_detallado[4].toString()));
                                            big_costo = big_costo.setScale(2, BigDecimal.ROUND_HALF_UP);
                                            out.print("<td align='right'>" + big_costo + "</td>");
                                            out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_detallado[4].toString()) * 100) / Double.parseDouble(obj_informe_agrupado[3].toString())) * mult)) / (double) mult + "%</td>");
                                            out.print("<td align='right'>" + obj_informe_detallado[3] + "</td>");
                                            out.print("<td align='right'>" + (Math.round(((Double.parseDouble(obj_informe_detallado[3].toString()) * 100) / Double.parseDouble(obj_informe_agrupado[2].toString())) * mult)) / (double) mult + "%</td>");
                                            out.print("</tr>");
                                        }
                                    }
                                    out.print("<tr>");
                                    out.print("<td><b>Total</b></td>");
                                    out.print("<td align='right'><b>" + obj_informe_agrupado[1] + "</b></td>");
                                    out.print("<td align='right'><b>100%</b></td>");
                                    BigDecimal big_costo = new BigDecimal(Double.parseDouble(obj_informe_agrupado[3].toString()));
                                    big_costo = big_costo.setScale(2, BigDecimal.ROUND_HALF_UP);
                                    out.print("<td align='right'><b>" + big_costo + "</b></td>");
                                    out.print("<td align='right'><b>100%</b></td>");
                                    out.print("<td align='right'><b>" + obj_informe_agrupado[2] + "</b></td>");
                                    out.print("<td align='right'><b>100%</b></td>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                } else {
                                    out.print("<b>No se tienen seguimientos del personal.</b>");
                                }
                                out.print("</div>");
                            }
//</editor-fold>
                        }
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
