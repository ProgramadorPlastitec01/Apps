package Tag;

import Controladores.LineaJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.TipoMttoJpaController;
import Controladores.GeneralJpaController;
import Controladores.EquipoJpaController;
import java.io.IOException;
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
            ///JPAS
            LineaJpaController jpaclna = new LineaJpaController();
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            TipoMttoJpaController jpactmt = new TipoMttoJpaController();
            GeneralJpaController jpacgjc = new GeneralJpaController();
             EquipoJpaController jpaceqp = new EquipoJpaController();
            ///VARIABLES
            Calendar cal = Calendar.getInstance();
            List lst_lineas = jpaclna.Lineas();
            List lst_actividad = jpacgjc.Actividades_generales();
            List lst_cronograma = null;
            List lst_tipo_mtto = jpactmt.Tipos_mtto();
            List lst_ot = null;
            List lst_actividades = null;
            List lst_prox_ot = null;
            List lst_total_actividades = null;
            List lst_cols_week = null;
            List lst_rango_week = null;
            List lst_filtro_anio = null;
            List lst_contador = null;
            List lst_equipo = null;
            List lst_notas = null;
            String valor = "";
            String filtro = "";
            String fecha_inicio = "";
            String fecha_fin = "";
            String fecha = "";
            int id_tipo_mtto = 0;
            int anio = cal.get(Calendar.YEAR);
            int id_linea = 0;
            int anio_report = 0;
            int semana = 0;
            try {
                anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("anio").toString());
            } catch (Exception e) {
                anio = cal.get(Calendar.YEAR);
            }
            String titulo = "PROGRAMA DE MANTENIMIENTO PREVENTIVO";
            String arg_meses[] = {"1 ENERO", "2 FEBRERO", "3 MARZO", "4 ABRIL", "5 MAYO", "6 JUNIO", "7 JULIO", "8 AGOSTO", "9 SEPTIEMBRE", "10 OCTUBRE", "11 NOVIEMBRE", "12 DICIEMBRE"};
            if (pageContext.getRequest().getAttribute("Reportes") != null) {
                if (pageContext.getRequest().getAttribute("Reportes").toString().equals("Cronograma")) {
                    //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA">
                    //<editor-fold defaultstate="collapsed" desc="FILTRO CRONOGRAMA">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Filtro Cronograma</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                    out.print("<div class=\"card-body\" >");
                    out.print("<form action='Reportes?opc=5' method='post'  id='FormReporte' >");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                    out.print("<tr><td><b>Año :</b>");
                    lst_filtro_anio = jpaclna.filtro_anio();
                    out.print("<select class='form-control' name='Cbx_anio' id='Cbx_anio'>");
                    out.print("<option value='0' >Seleccionar año</option>");
                    if (lst_filtro_anio != null) {
                        for (int i = 0; i < lst_filtro_anio.size(); i++) {
                            Object[] obj_filtro_anio = (Object[]) lst_filtro_anio.get(i);
                            out.print("<option value='" + obj_filtro_anio[1] + "'>" + obj_filtro_anio[1] + "</option>");
                        }
                    }
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<b>Valor a Buscar :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_filtro' id='Txt_filtro' placeholder='Ingresar numero de OT' title='Valor a buscar'/>");
                    out.print("</div>");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                    out.print("<b>Lineas :</b>");
                    out.print("<select class='form-control' name='Cbx_linea' id='Cbx_linea' title='Lineas'>");
                    out.print("<option value='0' >Seleccionar linea</option>");
                    for (int i = 0; i < lst_lineas.size(); i++) {
                        Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                        out.print("<option value='" + obj_lineas[0] + "'>" + obj_lineas[1] + "</option>");
                    }
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("<br><br><div style='width:50%;margin-left:41%;margin-top:3%'>");
                    out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click()\" class='fas fa-filter fa-size_small' title='Registrar'></span>&nbsp;&nbsp;");
                    out.print("<span onclick=\"javascript:location.href='Reportes?opc=1'\" class='far fa-times-circle fa-size_small' title='Cancelar'></span>");
                    out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    try {
                        id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("id_linea").toString());
                    } catch (Exception e) {
                        id_linea = 0;
                    }
                    try {
                        filtro = pageContext.getRequest().getAttribute("filtro").toString();
                    } catch (Exception e) {
                        filtro = "";
                    }
                    out.print("<table id='resultados' class='table3'>");
                    out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA PROTOTIPO</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                    out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                    out.print("<td align='center' colspan='2'>CODIGO R-MTF-004</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                    out.print("<td align='center' colspan='2'>VERSIÓN: 32</td>");
                    out.print("</tr>");
                    out.print("<th class='sticky'>CLASIFICACIÓN : </th>");
                    out.print("<td align='center'><div class='crono_1' style='width:50px;'>MM</div> MTTO MAYOR</td>"
                            + "<td align='center'><div class='crono_2' style='width:50px;'>INSP</div> INSPECCIÓN</td>"
                            + "<td align='center' colspan='3'><div class='crono_3' style='width:70px;'>LIMP_AI</div> LIMPIEZA y AJUSTE DE INICIO</td>"
                            + "<td align='center' colspan='2'><div class='crono_4' style='width:50px;'>GE</div> ACTIVIDAD GENERAL</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<div style=\"overflow-x:auto;\">");
                    out.print("<table class='table2'>");
                    out.print("<tr>");
                    out.print("<th rowspan='2' class='sticky'>LINEA</th>");
                    out.print("<th rowspan='2' colspan='2'>ORIGEN</th>");
                    lst_cols_week = jpaclna.Cronograma_cols_week_anio(anio);
                    Object[] obj_cols_week = (Object[]) lst_cols_week.get(0);
                    for (int i = 0; i < 24; i += 2) {
                        out.print("<th colspan='" + obj_cols_week[i + 1] + "'>" + obj_cols_week[i] + "</th>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    lst_rango_week = jpaclna.Cronograma_rango_week_anio(anio);
                    Object[] obj_rango_week = (Object[]) lst_rango_week.get(0);
                    for (int i = Integer.parseInt(obj_rango_week[0].toString()); i <= Integer.parseInt(obj_rango_week[23].toString()); i++) {
                        out.print("<td align='center'><b class='negro'>" + (i + 1) + "</b></td>");
                    }
                    out.print("</tr>");
                    if (anio_report != 0) {
                        if (id_linea != 0) {
                            lst_cronograma = jpaclna.Cronograma_linea_anio_filtro(anio_report, id_linea, filtro);
                        }
                        if (id_linea != 0) {
                            //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA FILTRO LINEA">
                            List lst_id_linea = jpaclna.Linea_id(id_linea);
                            Object[] obj_lineas = (Object[]) lst_id_linea.get(0);
                            if (lst_cronograma != null) {
                                for (int j = 0; j < lst_cronograma.size(); j++) {
                                    out.print("<tr>");
                                    if (j == 0) {
                                        out.print("<td rowspan='" + lst_cronograma.size() + "'>" + obj_lineas[1] + "</td>");
                                    }
                                    Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                    if (obj_cronograma[10].toString().equals("N/A")) {
                                        out.print("<td><a style='text-decoration:none; color:#2c3e50;'  href='Equipo?opc=10&Id_equipo=" + obj_cronograma[2] + "&id_nota=0&temp1=1'><div class='custom-cursor'>" + obj_cronograma[3] + "</div></a></td><td><b class='negro'>O.T</b></td>");
                                    } else {
                                        out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                    }
                                    try {
                                        String[] arg_id_ot = obj_cronograma[4].toString().split(",");
                                        String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                        String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                        String week_ot = obj_cronograma[7].toString();
                                        String week_ot_proxM = obj_cronograma[8].toString();
                                        String week_ot_proxI = obj_cronograma[9].toString();
                                        String week_ot_proxZ = obj_cronograma[10].toString();
                                        String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                        for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                            if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                out.print("<td>");
                                                for (int l = 0; l < arg_week_ot.length; l++) {
                                                    if (arg_week_ot[l].equals(k + "")) {
                                                        if (Integer.parseInt(arg_tipo_mtto_ot[l].toString()) == 3) {
                                                            out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=3'\">" + arg_num_ot[l] + "</div>");
                                                        } else {
                                                            out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=5&Id_orden=" + arg_id_ot[l] + "'\">" + arg_num_ot[l] + "</div>");
                                                        }
                                                    }
                                                }
                                                if (week_ot_proxM.equals(k + "")) {
                                                    out.print("<div class='crono_empty_1'>?</div>");
                                                }
                                                if (week_ot_proxI.equals(k + "")) {
                                                    out.print("<div class='crono_empty_2'>?</div>");
                                                }
                                                if (week_ot_proxZ.equals(k + "")) {
                                                    out.print("<div class='crono_empty_3'>?</div>");
                                                }
                                                out.print("</td>");
                                            } else {
                                                out.print("<td></td>");
                                            }
                                        }
                                    } catch (Exception e) {
                                        for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<tr><td colspan='55' align='center'><b>NO EXISTEN OT</b></td></tr>");
                            }
                            out.print("</table>");
                            out.print("<div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA GENERAL">
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                lst_cronograma = jpaclna.Cronograma_linea_anio(anio_report, Integer.parseInt(obj_lineas[0].toString()));
                                if (lst_cronograma != null) {
                                    for (int j = 0; j < lst_cronograma.size(); j++) {
                                        out.print("<tr>");
                                        if (j == 0) {
                                            out.print("<td rowspan='" + lst_cronograma.size() + "'>" + obj_lineas[1] + "</td>");
                                        }
                                        Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                        if (obj_cronograma[10].toString().equals("N/A")) {
                                            out.print("<td><a style='text-decoration:none; color:#2c3e50;'  href='Equipo?opc=10&Id_equipo=" + obj_cronograma[2] + "&id_nota=0&temp1=1'><div class='custom-cursor'>" + obj_cronograma[3] + "</div></a></td><td><b class='negro'>O.T</b></td>");
                                        } else {
                                            out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                        }
                                        try {
                                            String[] arg_id_ot = obj_cronograma[4].toString().split(",");
                                            String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                            String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                            String week_ot = obj_cronograma[7].toString();
                                            String week_ot_proxM = obj_cronograma[8].toString();
                                            String week_ot_proxI = obj_cronograma[9].toString();
                                            String week_ot_proxZ = obj_cronograma[10].toString();
                                            String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                            for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                                if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                    out.print("<td>");
                                                    for (int l = 0; l < arg_week_ot.length; l++) {
                                                        if (arg_week_ot[l].equals(k + "")) {
                                                            if (arg_tipo_mtto_ot[l].toString().equals("1")) {
                                                                out.print("<div class='crono_1verde' onclick=\"location.href='Orden?opc=5&Id_orden=" + arg_id_ot[l] + "'\">" + arg_num_ot[l] + "</div>");
                                                            } else if (arg_tipo_mtto_ot[l].toString().equals("3")) {
                                                                out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=" + arg_tipo_mtto_ot[l].toString() + "'\">" + arg_num_ot[l] + "</div>");
                                                            } else {
                                                                out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=5&Id_orden=" + arg_id_ot[l] + "'\">" + arg_num_ot[l] + "</div>");
                                                            }
                                                        }
                                                    }
                                                    if (week_ot_proxM.equals(k + "")) {
                                                        out.print("<div class='crono_empty_1'>?</div>");
                                                    }
                                                    if (week_ot_proxI.equals(k + "")) {
                                                        out.print("<div class='crono_empty_2'>?</div>");
                                                    }
                                                    if (week_ot_proxZ.equals(k + "")) {
                                                        out.print("<div class='crono_empty_3'>?</div>");
                                                    }
                                                    out.print("</td>");
                                                } else {
                                                    out.print("<td></td>");
                                                }
                                            }
                                        } catch (Exception e) {
                                            for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                                out.print("<td></td>");
                                            }
                                        }
                                        out.print("</tr>");
                                    }
                                }
                            }
                            out.print("</table>");
                            out.print("<div>");
                            //</editor-fold>
                        }
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA GENERAL">
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            lst_cronograma = jpaclna.Cronograma_linea_anio(anio, Integer.parseInt(obj_lineas[0].toString()));
                            if (lst_cronograma != null) {
                                for (int j = 0; j < lst_cronograma.size(); j++) {
                                    out.print("<tr>");
                                    if (j == 0) {
                                        out.print("<td rowspan='" + lst_cronograma.size() + "'>" + obj_lineas[1] + "</td>");
                                    }
                                    Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                    if (obj_cronograma[10].toString().equals("N/A")) {
                                        out.print("<td><a style='text-decoration:none; color:#2c3e50;'  href='Equipo?opc=10&Id_equipo=" + obj_cronograma[2] + "&id_nota=0&temp1=1'><div class='custom-cursor'>" + obj_cronograma[3] + "</div></a></td><td><b class='negro'>O.T</b></td>");
                                    } else {
                                        out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                    }
                                    try {
                                        String[] arg_id_ot = obj_cronograma[4].toString().split(",");
                                        String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                        String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                        String week_ot = obj_cronograma[7].toString();
                                        String week_ot_proxM = obj_cronograma[8].toString();
                                        String week_ot_proxI = obj_cronograma[9].toString();
                                        String week_ot_proxZ = obj_cronograma[10].toString();
                                        String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                        for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                            if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                out.print("<td>");
                                                for (int l = 0; l < arg_week_ot.length; l++) {
                                                    if (arg_week_ot[l].equals(k + "")) {
                                                        if (arg_tipo_mtto_ot[l].toString().equals("1")) {
                                                            out.print("<div class='crono_1verde' onclick=\"location.href='Orden?opc=5&Id_orden=" + arg_id_ot[l] + "'\">" + arg_num_ot[l] + "</div>");
                                                        } else if (arg_tipo_mtto_ot[l].toString().equals("3")) {
                                                            out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=" + arg_tipo_mtto_ot[l].toString() + "'\">" + arg_num_ot[l] + "</div>");
                                                        } else {
                                                            out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=5&Id_orden=" + arg_id_ot[l] + "'\">" + arg_num_ot[l] + "</div>");
                                                        }
                                                    }
                                                }
                                                if (week_ot_proxM.equals(k + "")) {
                                                    out.print("<div class='crono_empty_1'>?</div>");
                                                }
                                                if (week_ot_proxI.equals(k + "")) {
                                                    out.print("<div class='crono_empty_2'>?</div>");
                                                }
                                                if (week_ot_proxZ.equals(k + "")) {
                                                    out.print("<div class='crono_empty_3'>?</div>");
                                                }
                                                out.print("</td>");
                                            } else {
                                                out.print("<td></td>");
                                            }
                                        }
                                    } catch (Exception e) {
                                        for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            }
                        }
                        out.print("</table>");
                        out.print("<div>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Reportes").toString().equals("Cronograma_actividad")) {
                    //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA ACTIVIDAD">
                    //<editor-fold defaultstate="collapsed" desc="FILTRO CRONOGRAMA">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Filtro Cronograma</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                    out.print("<div class=\"card-body\" >");
                    out.print("<form action='Reportes?opc=8' method='post'  id='FormReporte' >");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                    out.print("<tr><td><b>Año :</b>");
                    lst_filtro_anio = jpaclna.filtro_anio_ag();
                    out.print("<select class='form-control' name='Cbx_anio' id='Cbx_anio'>");
                    out.print("<option value='0' >Seleccionar año</option>");
                    if (lst_filtro_anio != null) {
                        for (int i = 0; i < lst_filtro_anio.size(); i++) {
                            Object[] obj_filtro_anio = (Object[]) lst_filtro_anio.get(i);
                            out.print("<option value='" + obj_filtro_anio[1] + "'>" + obj_filtro_anio[1] + "</option>");
                        }
                    }
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<b>Valor a Buscar :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_filtro' id='Txt_filtro' placeholder='Ingresar numero de OT' title='Valor a buscar'/>");
                    out.print("</div>");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                    out.print("<b>Actividad General :</b>");
                    out.print("<select class='form-control' name='Cbx_linea' id='Cbx_linea' title='Actividad'>");
                    out.print("<option value='0' >Seleccionar Actividad</option>");
                    for (int i = 0; i < lst_actividad.size(); i++) {
                        Object[] obj_actividad = (Object[]) lst_actividad.get(i);
                        out.print("<option value='" + obj_actividad[0] + "'>" + obj_actividad[1] + "</option>");
                    }
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("<br><br><div style='width:50%;margin-left:41%;margin-top:3%'>");
                    out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click()\" class='fas fa-filter fa-size_small' title='Registrar'></span>&nbsp;&nbsp;");
                    out.print("<span onclick=\"javascript:location.href='Reportes?opc=7'\" class='far fa-times-circle fa-size_small' title='Cancelar'></span>");
                    out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    try {
                        filtro = pageContext.getRequest().getAttribute("filtro").toString();
                    } catch (Exception e) {
                        filtro = "";
                    }
                    try {
                        id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("id_linea").toString());
                    } catch (Exception e) {
                        id_linea = 0;
                    }
                    out.print("<table id='resultados' class='table2'>");
                    out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA PROTOTIPO</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                    out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                    out.print("<td align='center' colspan='2'>CODIGO R-MTF-004</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                    out.print("<td align='center' colspan='2'>VERSIÓN: 32</td>");
                    out.print("</tr>");
                    out.print("<th>CLASIFICACIÓN : </th>");
                    out.print("<td align='center'><div class='crono_1' style='width:50px;'>MM</div> MTTO MAYOR</td>"
                            + "<td align='center'><div class='crono_2' style='width:50px;'>INSP</div> INSPECCIÓN</td>"
                            + "<td align='center' colspan='3'><div class='crono_3' style='width:70px;'>LIMP_AI</div> LIMPIEZA y AJUSTE DE INICIO</td>"
                            + "<td align='center' colspan='2'><div class='crono_4' style='width:50px;'>GE</div> ACTIVIDAD GENERAL</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<div style=\"overflow-x:auto;\">");
                    out.print("<table class='table2'>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>LINEA</th>");
                    out.print("<th rowspan='2' colspan='2'>ORIGEN</th>");
                    lst_cols_week = jpaclna.Cronograma_cols_week_anio(anio);
                    Object[] obj_cols_week = (Object[]) lst_cols_week.get(0);
                    for (int i = 0; i < 24; i += 2) {
                        out.print("<th colspan='" + obj_cols_week[i + 1] + "'>" + obj_cols_week[i] + "</th>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    lst_rango_week = jpaclna.Cronograma_rango_week_anio(anio);
                    Object[] obj_rango_week = (Object[]) lst_rango_week.get(0);
                    for (int i = Integer.parseInt(obj_rango_week[0].toString()); i <= Integer.parseInt(obj_rango_week[23].toString()); i++) {
                        out.print("<td align='center'><b class='negro'>" + (i + 1) + "</b></td>");
                    }
                    out.print("</tr>");
                    if (anio_report != 0) {
                        if (id_linea != 0) {
                            lst_cronograma = jpaclna.Cronograma_linea_anio_filtro_ag(anio_report, id_linea, filtro);
                        } else if (!filtro.equals("")) {
                            lst_cronograma = jpaclna.Cronograma_anio_filtro_ag(anio_report, filtro);
                        }
                        if (!filtro.equals("")) {
                            //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA FILTRO">
                            if (lst_cronograma != null) {
                                for (int j = 0; j < lst_cronograma.size(); j++) {
                                    Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                    out.print("<tr>");
                                    if (j == 0) {
                                        out.print("<td rowspan='" + lst_cronograma.size() + "'>" + obj_cronograma[1] + "</td>");
                                    }
                                    out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                    try {
                                        String[] arg_id_ot = obj_cronograma[2].toString().split(",");
                                        String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                        String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                        String week_ot = obj_cronograma[7].toString();
                                        String week_ot_proxM = obj_cronograma[8].toString();
                                        String week_ot_proxI = obj_cronograma[9].toString();
                                        String week_ot_proxZ = obj_cronograma[10].toString();
                                        String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                        for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                            if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                out.print("<td>");
                                                for (int l = 0; l < arg_week_ot.length; l++) {
                                                    if (arg_week_ot[l].equals(k + "")) {
                                                        out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=4'\">" + arg_num_ot[l] + "</div>");
                                                    }
                                                }
                                                if (week_ot_proxM.equals(k + "")) {
                                                    out.print("<div class='crono_empty_4'>?</div>");
                                                }
                                                out.print("</td>");
                                            } else {
                                                out.print("<td></td>");
                                            }
                                        }
                                    } catch (Exception e) {
                                        for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<tr><td colspan='55' align='center'><b>NO EXISTEN OT</b></td></tr>");
                            }
                            out.print("</table>");
                            out.print("<div>");
                            //</editor-fold>
                        } else if (id_linea != 0) {
                            //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA FILTRO LINEA">
                            List lst_id_linea = jpacgjc.Actividad_general_id(id_linea);
                            Object[] obj_lineas = (Object[]) lst_id_linea.get(0);
                            if (lst_cronograma != null) {
                                for (int j = 0; j < lst_cronograma.size(); j++) {
                                    out.print("<tr>");
                                    if (j == 0) {
                                        out.print("<td rowspan='" + lst_cronograma.size() + "'>" + obj_lineas[1] + "</td>");
                                    }
                                    Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                    out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                    try {
                                        String[] arg_id_ot = obj_cronograma[2].toString().split(",");
                                        String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                        String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                        String week_ot = obj_cronograma[7].toString();
                                        String week_ot_proxM = obj_cronograma[8].toString();
                                        String week_ot_proxI = obj_cronograma[9].toString();
                                        String week_ot_proxZ = obj_cronograma[10].toString();
                                        String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                        for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                            if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                out.print("<td>");
                                                for (int l = 0; l < arg_week_ot.length; l++) {
                                                    if (arg_week_ot[l].equals(k + "")) {
                                                        out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=4'\">" + arg_num_ot[l] + "</div>");
                                                    }
                                                }
                                                if (week_ot_proxM.equals(k + "")) {
                                                    out.print("<div class='crono_empty_4'>?</div>");
                                                }
                                                out.print("</td>");
                                            } else {
                                                out.print("<td></td>");
                                            }
                                        }
                                    } catch (Exception e) {
                                        for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            } else {
                                out.print("<tr><td colspan='55' align='center'><b>NO EXISTEN OT</b></td></tr>");
                            }
                            out.print("</table>");
                            out.print("<div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA GENERAL">
                            for (int i = 0; i < lst_actividad.size(); i++) {
                                Object[] obj_actividad = (Object[]) lst_actividad.get(i);
                                lst_cronograma = jpaclna.Cronograma_linea_anio_actividad(anio, Integer.parseInt(obj_actividad[0].toString()));
                                if (lst_cronograma != null) {
                                    for (int j = 0; j < lst_cronograma.size(); j++) {
                                        out.print("<tr>");
                                        if (j == 0) {
                                            out.print("<td rowspan='" + lst_cronograma.size() + "'>" + obj_actividad[1] + "</td>");
                                        }
                                        Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                        out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                        try {
                                            String[] arg_id_ot = obj_cronograma[2].toString().split(",");
                                            String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                            String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                            String week_ot = obj_cronograma[7].toString();
                                            String week_ot_proxM = obj_cronograma[8].toString();
                                            String week_ot_proxI = obj_cronograma[9].toString();
                                            String week_ot_proxZ = obj_cronograma[10].toString();
                                            String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                            for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                                if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                    out.print("<td>");
                                                    for (int l = 0; l < arg_week_ot.length; l++) {
                                                        if (arg_week_ot[l].equals(k + "")) {
                                                            out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=4'\">" + arg_num_ot[l] + "</div>");
                                                        }
                                                    }
                                                    if (week_ot_proxM.equals(k + "")) {
                                                        out.print("<div class='crono_empty_4'>?</div>");
                                                    }
                                                    out.print("</td>");
                                                } else {
                                                    out.print("<td></td>");
                                                }
                                            }
                                        } catch (Exception e) {
                                            for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                                out.print("<td></td>");
                                            }
                                        }
                                        out.print("</tr>");
                                    }
                                }
                            }
                            out.print("</table>");
                            out.print("<div>");
                            //</editor-fold>
                        }
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="CRONOGRAMA GENERAL">
                        for (int i = 0; i < lst_actividad.size(); i++) {
                            Object[] obj_actividad = (Object[]) lst_actividad.get(i);
                            lst_cronograma = jpaclna.Cronograma_linea_anio_actividad(anio, Integer.parseInt(obj_actividad[0].toString()));
                            if (lst_cronograma != null) {
                                for (int j = 0; j < lst_cronograma.size(); j++) {
                                    out.print("<tr>");
                                    if (j == 0) {
                                        out.print("<td  rowspan='" + lst_cronograma.size() + "'>" + obj_actividad[1] + "</td>");
                                    }
                                    Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                    out.print("<td>" + obj_cronograma[3] + "</td><td><b class='negro'>O.T</b></td>");
                                    try {
                                        String[] arg_id_ot = obj_cronograma[2].toString().split(",");
                                        String[] arg_num_ot = obj_cronograma[5].toString().split(",");
                                        String[] arg_tipo_mtto_ot = obj_cronograma[6].toString().split(",");
                                        String week_ot = obj_cronograma[7].toString();
                                        String week_ot_proxM = obj_cronograma[8].toString();
                                        String week_ot_proxI = obj_cronograma[9].toString();
                                        String week_ot_proxZ = obj_cronograma[10].toString();
                                        String[] arg_week_ot = obj_cronograma[7].toString().replace("[", "").replace("]", "").split(",");
                                        for (int k = Integer.parseInt(obj_rango_week[0].toString()); k <= Integer.parseInt(obj_rango_week[23].toString()); k++) {
                                            if (week_ot.contains("[" + k + "]") || week_ot_proxM.equals(k + "") || week_ot_proxI.equals(k + "") || week_ot_proxZ.equals(k + "")) {
                                                out.print("<td>");
                                                for (int l = 0; l < arg_week_ot.length; l++) {
                                                    if (arg_week_ot[l].equals(k + "")) {
                                                        out.print("<div class='crono_" + arg_tipo_mtto_ot[l] + "' onclick=\"location.href='Orden?opc=13&Id_orden=" + arg_id_ot[l] + "&Id_tipo_mtto=4'\">" + arg_num_ot[l] + "</div>");
                                                    }
                                                }
                                                if (week_ot_proxM.equals(k + "")) {
                                                    out.print("<div class='crono_empty_4'>?</div>");
                                                }
                                                out.print("</td>");
                                            } else {
                                                out.print("<td></td>");
                                            }
                                        }
                                    } catch (Exception e) {
                                        for (int z = Integer.parseInt(obj_rango_week[0].toString()); z <= Integer.parseInt(obj_rango_week[23].toString()); z++) {
                                            out.print("<td></td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            }
                        }
                        out.print("</table>");
                        out.print("<div>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Reportes").toString().equals("Indicador_actividades")) {
                    //<editor-fold defaultstate="collapsed" desc="INDICADOR">
                    valor = pageContext.getRequest().getAttribute("Valor").toString();
                    fecha_inicio = pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    fecha_fin = pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    id_tipo_mtto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_mtto").toString());
                    out.print("<div>");
                    //<editor-fold defaultstate="collapsed" desc="CONTADOR">
                    out.print("<div class=\"modal fade\" id=\"AjusteFechaProg\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                    out.print("<div class=\"modal-dialog\" role=\"document\">");
                    out.print("<div class=\"modal-content\">");
                    out.print("<div class=\"modal-header\">");
                    out.print("<h5 class=\"modal-title\" id=\"AjusteFechaProg\">Contador Indicador</h5>");
                    out.print("</div>");
                    out.print("<div class=\"modal-body\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<th rowspan='10' style='width:12%'><div style='-webkit-transform: rotate(-90deg);' >ORDEN TRABAJO</div></th>");
                    out.print("<tr>");
                    out.print("<th>MM</th>");
                    out.print("<th>INSP</th>");
                    out.print("<th>Zonas</th>");
                    out.print("<th>General</th>");
                    out.print("<th>Justificadas</th>");
                    out.print("</tr>");
                    lst_contador = jpacotb.Contador_actividad(fecha_inicio, fecha_fin);
                    if (lst_contador != null) {
                        Object[] obj_contador = (Object[]) lst_contador.get(0);
                        out.print("<tr>");
                        out.print("<td align='center'><b>" + obj_contador[0] + "</b></td>");
                        out.print("<td align='center'><b>" + obj_contador[1] + "</b></td>");
                        out.print("<td align='center'><b>" + obj_contador[2] + "</b></td>");
                        out.print("<td align='center'><b>" + obj_contador[3] + "</b></td>");
                        out.print("<td align='center'><b>" + obj_contador[4] + "</b></td>");
                        out.print("</tr>");
                        out.print("<th colspan='6'>Gestion</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='6' align='center'><b>" + obj_contador[5] + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<tr>");
                        out.print("<th colspan='6'>Cerradas</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='6' align='center'><b>" + obj_contador[6] + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th colspan='6'>Total</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='6' align='center'><b>" + obj_contador[7] + "</b></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("<form action='Reportes?opc=2' method='post'>");
                    out.print("<table>");
                    out.print("<tr>");
                    out.print("<td><b>Valor : </b><input type='text' style='width:300px' class='form-control' name='vlr' id='vlr' value='" + valor + "' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('vlr');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><b>Fecha inicio : </b><input type='text' style='width:150px' class='form-control' name='fin' id='start' value='" + fecha_inicio + "' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><b>Fecha fin : </b><input type='text' style='width:150px' class='form-control' name='ffn' id='end' value='" + fecha_fin + "' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><b>Tipo de Mtto : </b><select class='form-control' name='itm' id='itm' >");
                    out.print("<option value='-1'>Seleccionar tipo</option>");
                    out.print("<option " + ((id_tipo_mtto == 0) ? "selected" : "") + " value='0'>Todos</option>");
                    for (int i = 0; i < lst_tipo_mtto.size(); i++) {
                        Object[] obj_tipo_mtto = (Object[]) lst_tipo_mtto.get(i);
                        out.print("<option " + ((id_tipo_mtto == Integer.parseInt(obj_tipo_mtto[0].toString())) ? "selected" : "") + " value='" + obj_tipo_mtto[0] + "' >" + obj_tipo_mtto[1] + " /" + obj_tipo_mtto[2] + "</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('itm');"
                            + "mySelect.add(Validate.Exclusion, { within: ['-1'], failureMessage: \"\"});</script></td>");
                    out.print("<td style='display:none'><input type='submit' id='Btn_accionC' value='Enviar' /></td>");
                    out.print("<td><br /><span onclick=\"javascript:document.getElementById('Btn_accionC').click();\" class='fas fa-search fa-size_small' title='Consultar'></span>&nbsp;&nbsp;");
                    out.print("<span class='fas fa-print fa-size_small' onclick=\"Imprimir();\" title='Imprimir'></span>&nbsp;&nbsp;");
                    out.print("<span class='far fa-file-excel fa-size_small' onclick=\"exportTableToExcel('dataTable','Indicador');\" title='Excel'></span>"
                            + "&nbsp;&nbsp;<span class='fas fa-calculator fa-size_small' title='Contador OT' data-toggle=\"modal\" data-target=\"#AjusteFechaProg\" onclick='ActualizarFechasProg();'></span>&nbsp;&nbsp;");
                    out.print("<span class='fas fa-eraser fa-size_small' onclick='VolverReporteIndicador()' title='Limpiar filtro'></span></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("<div>");
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Reporte de actividades Programadas vs Ejecutadas</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<div id='Div_export'>");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>No OT.</th>");
                    out.print("<th rowspan='2'>Fecha</th>");
                    out.print("<th rowspan='2'>Tipo</th>");
                    out.print("<th rowspan='2'>Origen</th>");
                    out.print("<th colspan='3'>Actividades</th>");
                    out.print("<th colspan='4'>Responsables</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Programadas</th>");
                    out.print("<th>Ejecutadas</th>");
                    out.print("<th>%</th>");
                    out.print("<th>Programador</th>");
                    out.print("<th>Ejecutor</th>");
                    out.print("<th>Revisor</th>");
                    out.print("<th>Aprobador</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_actividades = jpacotb.Reporte_actividades(fecha_inicio, fecha_fin, id_tipo_mtto, valor);
                    if (lst_actividades != null) {
                        for (int i = 0; i < lst_actividades.size(); i++) {
                            Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='negro' style='cursor:pointer;' onclick=\"location.href='Orden?opc=5&Id_orden=" + obj_actividades[0] + "'\">" + obj_actividades[1] + "</b></td>");
                            out.print("<td align='center'>" + obj_actividades[2] + "</td>");
                            out.print("<td align='center'>" + obj_actividades[4] + "</td>");
                            out.print("<td>" + obj_actividades[5] + "</td>");
                            out.print("<td align='center'>" + obj_actividades[6] + "</td>");
                            out.print("<td align='center'>" + obj_actividades[7] + "</td>");
                            out.print("<td align='center'>" + obj_actividades[8] + "</td>");
                            out.print("<td>" + obj_actividades[9] + "</td>");
                            out.print("<td>" + obj_actividades[10] + "</td>");
                            out.print("<td>" + obj_actividades[11] + "</td>");
                            out.print("<td>" + obj_actividades[12] + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</tbody>");
                    lst_total_actividades = jpacotb.Reporte_total_actividades(fecha_inicio, fecha_fin, id_tipo_mtto, valor);
                    if (lst_total_actividades != null) {
                        Object[] obj_total_actividades = (Object[]) lst_total_actividades.get(0);
                        out.print("<tfoot>");
                        out.print("<tr>");
                        out.print("<th colspan='2'>Total No. de OT</th>");
                        out.print("<th colspan='2'>" + obj_total_actividades[0] + "</th>");
                        out.print("<th>" + obj_total_actividades[1] + "</th>");
                        out.print("<th>" + obj_total_actividades[2] + "</th>");
                        out.print("<th>" + obj_total_actividades[3] + "</th>");
                        out.print("<th colspan='4'> </th>");
                        out.print("</tr>");
                        out.print("</tfoot>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Reportes").toString().equals("Indicador_proximas_OT")) {
                    //<editor-fold defaultstate="collapsed" desc="INDICADOR PROXIMAS OT">
                    anio_report = Integer.parseInt(pageContext.getRequest().getAttribute("anio").toString());
                    fecha = pageContext.getRequest().getAttribute("fecha").toString();
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("id_linea").toString());
                    //<editor-fold defaultstate="collapsed" desc="FILTRO">
                    out.print("<div>");
                    out.print("<form action='Reportes?opc=6' method='post'>");
                    out.print("<table>");
                    out.print("<tr>");
                    out.print("<td><b>Año :</b>");
                    lst_filtro_anio = jpaclna.filtro_anio();
                    out.print("<select class='form-control' style='width:300px' name='Cbx_anio' id='Cbx_anio'>");
                    out.print("<option value='-1' >Seleccionar año</option>");
                    if (lst_filtro_anio != null) {
                        for (int i = 0; i < lst_filtro_anio.size(); i++) {
                            Object[] obj_filtro_anio = (Object[]) lst_filtro_anio.get(i);
                            out.print("<option value='" + obj_filtro_anio[1] + "'>" + obj_filtro_anio[1] + "</option>");
                        }
                    }
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_anio');"
                            + "mySelect.add(Validate.Exclusion, { within: ['-1'], failureMessage: \"\"});</script>");
                    out.print("<td><b>Fecha : </b><input type='text' style='width:300px' class='form-control' name='fcha' id='datepicker' value='" + fecha_inicio + "' placeholder='Seleccione fecha' autocomplete='off' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><b>Linea : </b>");
                    out.print("<select class='form-control' style='width:300px' name='Cbx_linea' id='Cbx_linea'>");
                    out.print("<option value='-1' >Seleccionar linea</option>");
                    out.print("<option value='0' >TODAS</option>");
                    for (int i = 0; i < lst_lineas.size(); i++) {
                        Object[] obj_linea = (Object[]) lst_lineas.get(i);
                        out.print("<option value='" + obj_linea[0] + "'>" + obj_linea[1] + "</option>");
                    }
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                            + "mySelect.add(Validate.Exclusion, { within: ['-1'], failureMessage: \"\"});</script></td>");
                    out.print("<td style='display:none'><input type='submit' id='Btn_accionC' value='Enviar' /></td>");
                    out.print("<td><br /><span onclick=\"javascript:document.getElementById('Btn_accionC').click();\" class='fas fa-search fa-size_small' title='Consultar'></span>&nbsp;&nbsp;");
                    out.print("<span class='fas fa-print fa-size_small' onclick=\"Imprimir();\" title='Imprimir'></span>&nbsp;&nbsp;");
                    out.print("<span class='far fa-file-excel fa-size_small' onclick=\"exportTableToExcel('dataTable','Indicador');\" title='Excel'></span>&nbsp;&nbsp;");
                    out.print("<span class='fas fa-eraser fa-size_small' onclick='VolverReporteProximaOT()' title='Limpiar filtro'></span></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("<div>");
                    //</editor-fold>
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Reporte proxima OT</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<div id='Div_export'>");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Linea</th>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Proxima OT MM</th>");
                    out.print("<th>Proxima OT INSP</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    if (anio_report != 0) {
                        lst_prox_ot = jpacotb.Reporte_OT_Filtro(anio_report, fecha, id_linea);
                    } else {
                        lst_prox_ot = jpacotb.Reporte_OT(anio);
                    }
                    if (lst_prox_ot != null) {
                        for (int i = 0; i < lst_prox_ot.size(); i++) {
                            Object[] obj_ot = (Object[]) lst_prox_ot.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_ot[1] + "</td>");
                            out.print("<td align='center'>" + obj_ot[3] + "</td>");
                            out.print("<td align='center'>Semana: <b>" + obj_ot[7] + "</b></td>");
                            out.print("<td align='center'>Semana: <b>" + obj_ot[8] + "</b></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Reportes").toString().equals("Notas")) {
                    //<editor-fold defaultstate="collapsed" desc="NOTAS">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Reporte Notas</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Id</th>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Equipo</th>");
                    out.print("<th style='width:20%;'>Asunto</th>");
                    out.print("<th style='width:30%;'>Descripción</th>");
                    out.print("<th>Responsable</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_notas = jpaceqp.Notas_General();
                    if (lst_notas != null) {
                        for (int i = 0; i < lst_notas.size(); i++) {
                            Object[] obj_notas = (Object[]) lst_notas.get(i);
                            out.print("<tr>");
                            out.print("<td style='text-align:center;'>" + obj_notas[0] + "</td>");
                            out.print("<td>" + obj_notas[3] + "</td>");
                            out.print("<td>" + obj_notas[2] + "</td>");
                            out.print("<td>" + obj_notas[4] + "</td>");
                            out.print("<td>" + obj_notas[5] + "</td>");
                            out.print("<td>" + obj_notas[8] + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
