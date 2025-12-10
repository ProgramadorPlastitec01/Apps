package Tags;

import Controladores.AreaJpaController;
import Controladores.CalificacionJpaController;
import Controladores.GrupoJpaController;
import Controladores.InformeJpaController;
import Controladores.TipoCalificacionJpaController;
import Controladores.TipoInformeJpaController;
import Controladores.ValidacionJpaController;
import Metodos.Connection_mysql_daruma;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_reporte extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
//PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String[] cod_area = pageContext.getSession().getAttribute("Id/Area").toString().split("/");
            int cod = Integer.parseInt(cod_area[0].toString());
            String area = cod_area[1];
//FIN PERMISOS
            AreaJpaController jpacara = new AreaJpaController();
            TipoCalificacionJpaController jpactcl = new TipoCalificacionJpaController();
            TipoInformeJpaController jpactif = new TipoInformeJpaController();
            GrupoJpaController jpacgpo = new GrupoJpaController();
            InformeJpaController jpacifm = new InformeJpaController();
            CalificacionJpaController jpacclf = new CalificacionJpaController();
            ValidacionJpaController jpacvld = new ValidacionJpaController();
            Connection_mysql_daruma mtdcmd = new Connection_mysql_daruma();
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
//VARIABLE GLOBALES
            int id_calificacion = 0;
            String calificacion_actual = "";
            String calificacion_anterior = "";
            int count_calificacion = 0;
            int dependencias = 0;
            String dependencias_informe = "";
            int id_dependencia = 0;
            int id_informe = 0;
            int id_validacion = 0;
            List lst_area = null;
            List lst_informes = null;
            List lst_validaciones = null;
            List lst_validacion = null;
            List lst_informe = null;
            List lst_grupos = null;
            List lst_subgrupos = null;
            List lst_protocolos = null;
            List lst_tipo_calificacion = null;
            List lst_tipo_informe = null;
            List lst_calificaciones = null;
            List lst_cronograma = null;
            List lst_calificacion = null;
            List lst_anios = null;
            String filtro = "";
            if (pageContext.getRequest().getAttribute("Reporte") != null) {
                // <editor-fold defaultstate="collapsed" desc="CRONOGRAMA">
                if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Modulo_cronograma")) {
                    id_informe = Integer.parseInt(pageContext.getRequest().getAttribute("Id_informe").toString());
                    int anio_send = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    lst_area = jpacara.Areas();
                    out.print("<div id='content_sin'>");
                    if (anio_send == 0) {
                        anio_send = Integer.parseInt(anio);
                    }
                    //<editor-fold defaultstate="collapsed" desc="INFORME">
                    if (id_informe > 0) {
                        lst_informe = jpacifm.Informes_id_informe(id_informe);
                        Object[] obj_informe = (Object[]) lst_informe.get(0);
                        lst_calificacion = jpacclf.Traer_calificacion_id(Integer.parseInt(obj_informe[1].toString()));
                        Object[] obj_calificacion = (Object[]) lst_calificacion.get(0);
                        dependencias_informe = obj_informe[26].toString();
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;height:600px;overflow:scroll;position: absolute;top: 2px;left:2%;'>");
                        out.print("<div style='float:right;'><a href='Reporte?opc=1&iif=0&Cbx_anio=" + anio_send + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                        out.print("<div style='float:left;'><a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF </div>");
                        out.print("<div id='Imprimir'>");
//                        if (obj_informe[18].toString().equals("VALIDACION")) {
                        //<editor-fold defaultstate="collapsed" desc="INFORME DE VALIDACION">
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td colspan='9' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='5' align='center'><b class='negro'>REGISTRO</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>NO CODIFICADO</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='center'><b>Calificacion</b></td>");
                        out.print("<td align='center'><b>Tipo</b></td>");
                        out.print("<td colspan='2' align='center'><b>Frecuencia</b></td>");
                        out.print("<td align='center'><b>Documento</b></td>");
                        out.print("<td align='center'><b>Grupo</b></td>");
                        out.print("<td colspan='3' align='center'><b>Flujo de trabajo</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='left'>" + obj_calificacion[1] + "</td>");
                        out.print("<td align='center' align='left'>" + obj_calificacion[4] + "</td>");
                        out.print("<td colspan='2' align='left'><b>ULT.</b>" + obj_calificacion[21] + "<br /><b>PROX.</b>" + obj_calificacion[22] + "</td>");
                        out.print("<td align='left'>" + obj_calificacion[13] + "</td>");
                        out.print("<td align='left'>" + obj_calificacion[11] + "</td>");
                        out.print("<td colspan='3' align='left'><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
                                + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
                                + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th COLSPAN='9'>INFORMES DE VALIDACION</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='3' style='width:33%'><h2>PQ</h2><b>Validación</b></td>");
                        out.print("<td COLSPAN='3' style='width:33%'><h2>OQ</h2><b>Calificacion de operación</b></td>");
                        out.print("<td COLSPAN='3' style='width:34%'><h2>IQ</h2><b>Calificacion de instalación</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        if (Integer.parseInt(obj_informe[9].toString()) == 6 || Integer.parseInt(obj_informe[9].toString()) == 5 || Integer.parseInt(obj_informe[9].toString()) == 4 || Integer.parseInt(obj_informe[9].toString()) == 2) {
                            //<editor-fold defaultstate="collapsed" desc="PQ">
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                            out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                            out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                            out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                            out.print(obj_informe[3].toString() + "");
                            out.print("</div>");
                            out.print("</td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
                                    if (obj_informes[20].toString().contains("/OQ") || obj_informes[20].toString().equals("OQ")) {
                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                        out.print(obj_informes[3].toString() + "");
                                        out.print("</div>");
                                    }
                                }
                            }
                            out.print("</td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
                                    if (obj_informes[20].toString().equals("IQ")) {
                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                        out.print(obj_informes[3].toString() + "");
                                        out.print("</div>");
                                    }
                                }
                            }
                            out.print("</td>");
                            //</editor-fold>
                        } else if (Integer.parseInt(obj_informe[9].toString()) == 1) {
                            //<editor-fold defaultstate="collapsed" desc="IQ">
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
                                    if (obj_informes[20].toString().equals("IQ")) {
                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                        out.print(obj_informes[3].toString() + "");
                                        out.print("</div>");
                                    }
                                }
                            } else {
                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                                out.print(obj_informe[3].toString() + "");
                                out.print("</div>");
                            }
                            out.print("</td>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="OQ">
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                            out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                            out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                            out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                            out.print(obj_informe[3].toString() + "");
                            out.print("</div>");
                            out.print("</td>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
//                                    if (obj_informes[20].toString().contains("/OQ") || obj_informes[20].toString().equals("OQ") || obj_informes[20].toString().equals("IQ")) {
                                    out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                                    out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                    out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                    out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                    out.print(obj_informes[3].toString() + "");
                                    out.print("</div>");
                                    out.print("</td>");
//                                    }
                                }
                            } else {
//                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
//                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
//                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
//                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
//                                out.print(obj_informe[3].toString() + "");
//                                out.print("</div>");
                            }
//                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            //</editor-fold>
                        }
                        
                        
//                        if (dependencias_informe.equals("N/A")) {
//                            if (obj_informe[20].toString().equals("PQ")) {
//                                out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
//                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
//                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
//                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
//                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
//                                out.print(obj_informe[3].toString() + "");
//                                out.print("</div>");
//                                out.print("</td>");
//                            } else if (obj_informe[20].toString().equals("OQ")) {
//                                out.print("<td colspan='3'></td>");
//                                out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
//                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
//                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
//                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
//                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
//                                out.print(obj_informe[3].toString() + "");
//                                out.print("</div>");
//                                out.print("</td>");
//                            } else {
//                                out.print("<td rowspan='2' colspan='6'></td>");
//                                out.print("<td colspan='3' valign='top' style='text-align:left'>");
//                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
//                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
//                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
//                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
//                                out.print(obj_informe[3].toString() + "");
//                                out.print("</div>");
//                                out.print("</td>");
//                            }
//                        } else {
//                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
//                            if (!dependencias_informe.equals("N/A")) {
//                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
//                                out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
//                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
//                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
//                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
//                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
//                                out.print(obj_informe[3].toString() + "");
//                                out.print("</div>");
//                                out.print("</td>");
//                                for (int i = 0; i < arg_dependencia.length; i++) {
//                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
//                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
//                                    if (obj_informes[20].toString().contains("/OQ") || obj_informes[20].toString().equals("OQ")) {
//                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
//                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
//                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
//                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
//                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
//                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
//                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
//                                        out.print(obj_informes[3].toString() + "");
//                                        out.print("</div>");
//                                    }
//                                }
//                            }
//                            out.print("</td>");
//                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
//                            if (!dependencias_informe.equals("N/A")) {
//                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
//                                for (int i = 0; i < arg_dependencia.length; i++) {
//                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
//                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
//                                    if (obj_informes[20].toString().equals("IQ")) {
//                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
//                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
//                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
//                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
//                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
//                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
//                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
//                                        out.print(obj_informes[3].toString() + "");
//                                        out.print("</div>");
//                                    }
//                                }
//                            }
//                            out.print("</td>");
//                        }
                        out.print("</tr>");
                        out.print("</table>");
                        //</editor-fold>
//                        } else {
////                            //<editor-fold defaultstate="collapsed" desc="INFORME DE CALIFICACION">
////                            out.print("<h3>Detalle Informe de calificación</h3>");
////                            out.print("<table class='table2' style='width:100%' >");
////                            //<editor-fold defaultstate="collapsed" desc="CABECERA">
////                            out.print("<tr>");
////                            out.print("<td colspan='10' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
////                            out.print("</tr>");
////                            out.print("<tr>");
////                            out.print("<td align='center'>"
////                                    + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
////                                    + "</td>");
////                            out.print("<td colspan='6' align='center'><b class='negro'>REGISTRO</b></td>");
////                            out.print("<td colspan='3' align='center'><b class='negro'>NO CODIFICADO</b></td>");
////                            out.print("</tr>");
//////</editor-fold>
////                            //<editor-fold defaultstate="collapsed" desc="CALIFICACCION">
////                            out.print("<tr>");
////                            out.print("<th colspan='10'>Calificación</th>");
////                            out.print("</tr>");
////                            out.print("<tr>");
////                            out.print("<td colspan='2' align='center'><b>Calificacion</b></td>");
////                            out.print("<td align='center'><b>Tipo</b></td>");
////                            out.print("<td colspan='2' align='center'><b>Frecuencia</b></td>");
////                            out.print("<td align='center'><b>Documento</b></td>");
////                            out.print("<td align='center'><b>Grupo</b></td>");
////                            out.print("<td colspan='3' align='center'><b>Flujo de trabajo</b></td>");
////                            out.print("</tr>");
////                            out.print("<tr>");
////                            out.print("<td colspan='2' align='left'>" + obj_calificacion[1] + "</td>");
////                            out.print("<td align='center' align='left'>" + obj_calificacion[4] + "</td>");
////                            out.print("<td colspan='2' align='left'><b>ULT.</b>" + obj_calificacion[21] + "<br /><b>PROX.</b>" + obj_calificacion[22] + "</td>");
////                            out.print("<td align='left'>" + obj_calificacion[13] + "</td>");
////                            out.print("<td align='left'>" + obj_calificacion[11] + "</td>");
////                            out.print("<td colspan='3' align='left'><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
////                                    + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
////                                    + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
////                            out.print("</tr>");
//////</editor-fold>
////                            //<editor-fold defaultstate="collapsed" desc="INFORME PRINCIPAL">
////                            out.print("<tr>");
////                            out.print("<th colspan='10'>Informe Principal</th>");
////                            out.print("</tr>");
////                            out.print("<tr>");
////                            out.print("<th style='background-color:" + obj_informe[27] + ";width:5%'>" + obj_informe[18] + "<br />" + obj_informe[6] + "</th>");
////                            out.print("<td valign='top' colspan='4' style='width:25%' align='left'>"
////                                    //  + "<b>" + obj_informe[15] + " </b>" + ((obj_informe[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informe[6].toString()) + "<br />"
////                                    //  + "<b>" + obj_informe[16] + " </b>" + ((obj_informe[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informe[7].toString()) + "<br />"
////                                    //  + "<b>" + obj_informe[17] + " </b>" + ((obj_informe[8] == null && obj_informe[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informe[7] != null) ? obj_informe[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
////                                    + "<b>Califiacción : </b><br />" + obj_informe[7] + "<br />"
////                                    + "<b>Documento : </b><br />" + obj_informe[12] + "<br />"
////                                    + "<b>Tipo calificación : </b><br />" + obj_informe[20] + "<br />"
////                                    + "<b>Grupo : </b><br />" + obj_informe[22] + " / " + obj_informe[23] + "<br />"
////                                    //+ "<b>Dependencia(s) : </b>" + obj_informe[22] + "</td>");
////                                    + "</td>");
////                            out.print("<td valign='top' colspan='6' align='left'>");
////                            out.print("<b>Contenido : </b>" + obj_informe[28]);
////                            out.print("<button class='accordion'>Informe</button>");
////                            out.print("<div class='panel'>");
////                            out.print(obj_informe[3].toString().split("<hr />")[0] + "");
////                            out.print("</div>");
////                            out.print("<button class='accordion'>Conclusión</button>");
////                            out.print("<div class='panel'>");
////                            out.print(obj_informe[3].toString().split("<hr />")[1] + "");
////                            out.print("</div>");
////                            out.print("<button class='accordion'>Desviaciones</button>");
////                            out.print("<div class='panel'>");
////                            out.print(obj_informe[3].toString().split("<hr />")[2] + "");
////                            out.print("</div>");
////                            out.print("<button class='accordion'>Responsables</button>");
////                            out.print("<div class='panel'>"
////                                    + "<b>" + obj_informe[13] + " </b><br />"
////                                    + "<b>" + obj_informe[14] + " </b><br />"
////                                    + "<b>" + obj_informe[15] + " </b><hr />"
////                                    //  + "<b>" + obj_informe[15] + " </b>" + ((obj_informe[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informe[6].toString()) + "<br />"
////                                    //  + "<b>" + obj_informe[16] + " </b>" + ((obj_informe[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informe[7].toString()) + "<br />"
////                                    //  + "<b>" + obj_informe[17] + " </b>" + ((obj_informe[8] == null && obj_informe[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informe[7] != null) ? obj_informe[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
////                                    + "");
////                            out.print("</div>");
////                            out.print("</td>");
////                            out.print("</tr>");
//////</editor-fold>
////                            //<editor-fold defaultstate="collapsed" desc="INFORME DEPENDENCIA">
////                            if (!dependencias_informe.equals("N/A")) {
////                                out.print("<tr>");
////                                out.print("<th colspan='10'>Informe(s) de dependencia</th>");
////                                out.print("</tr>");
////                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
////                                for (int i = 0; i < arg_dependencia.length; i++) {
////                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
////                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
////                                    out.print("<tr>");
////                                    out.print("<th style='background-color:" + obj_informes[27] + ";width:5%'>" + obj_informes[18] + "<br />" + obj_informes[6] + "</th>");
////                                    out.print("<td valign='top' colspan='4' style='width:25%'align='left'>"
////                                            // + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
////                                            // + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
////                                            // + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
////                                            + "<b>Calificación : </b><br />" + obj_informes[7] + "<br />"
////                                            + "<b>Documento : </b><br />" + obj_informes[12] + "<br />"
////                                            + "<b>Tipo calificación : </b><br />" + obj_informes[20] + "<br />"
////                                            + "<b>Grupo : </b><br />" + obj_informes[22] + " / " + obj_informes[23] + "<br />"
////                                            //+ "<b>Dependencia(s) : </b>" + obj_informes[22] + "</td>");
////                                            + "</td>");
////                                    out.print("<td valign='top' colspan='6' align='left'>");
////                                    out.print("<b>Contenido : </b>" + obj_informes[28]);
////                                    out.print("<button class='accordion'>Informe</button>");
////                                    out.print("<div class='panel'>");
////                                    out.print(obj_informes[3].toString().split("<hr />")[0] + "");
////                                    out.print("</div>");
////                                    out.print("<button class='accordion'>Conclusión</button>");
////                                    out.print("<div class='panel'>");
////                                    out.print(obj_informes[3].toString().split("<hr />")[1] + "");
////                                    out.print("</div>");
////                                    out.print("<button class='accordion'>Desviaciones</button>");
////                                    out.print("<div class='panel'>");
////                                    out.print(obj_informes[3].toString().split("<hr />")[2] + "");
////                                    out.print("</div>");
////                                    out.print("<button class='accordion'>Responsables</button>");
////                                    out.print("<div class='panel'>"
////                                            + "<b>" + obj_informes[13] + " </b><br />"
////                                            + "<b>" + obj_informes[14] + " </b><br />"
////                                            + "<b>" + obj_informes[15] + " </b><hr />"
////                                            // + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
////                                            // + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
////                                            // + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
////                                            + "");
////                                    out.print("</div>");
////                                    out.print("</td>");
////                                    out.print("</tr>");
////                                }
////                            }
//////</editor-fold>
////                            out.print("</table>");
////                            //</editor-fold>
//                        }
                        out.print("</div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    out.print("<h3>Cronograma " + anio_send);
                    lst_anios = jpacifm.Traer_anios_historial();
                    out.print("<div style='float:right'>");
                    out.print("<form action='Reporte?opc=1&iif=0' method='post' id='FormAnio' name='FormAnio'>");
                    out.print("<select name='Cbx_anio' id='Cbx_anio' onchange='PostBackAnio()'>");
                    for (int i = 0; i < lst_anios.size(); i++) {
                        Object[] obj_anios = (Object[]) lst_anios.get(i);
                        if (anio_send == 0 && i == 0) {
                            anio_send = Integer.parseInt(anio);
                        }
                        if (anio_send == Integer.parseInt(obj_anios[0].toString())) {
                            out.print("<option value='" + obj_anios[0] + "' selected> Año " + obj_anios[0] + " #INF. " + obj_anios[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_anios[0] + "'> Año " + obj_anios[0] + " #INF. " + obj_anios[1] + "</option>");
                        }
                    }
                    out.print("</select></form></div></h3>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_areas = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                            out.print("<button class='accordion'>" + obj_areas[1] + "</button>");
                            out.print("<div class='panel'>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:20%'><b>Calificación</b></td>");
                            out.print("<td align='center' style='width:5%'><b>Informe</b></td>");
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
//out.print("<tr>");
//for (int j = 1; j <= 12; j++) {
// out.print("<td><b>Cant.</b></td>");
// out.print("<td><b>min</b></td>");
//}
//out.print("</tr>");
                            lst_cronograma = jpacifm.Cronograma(Integer.parseInt(obj_areas[0].toString()), anio_send);
                            if (lst_cronograma != null) {
                                for (int j = 0; j < lst_cronograma.size(); j++) {
                                    Object[] obj_cronograma = (Object[]) lst_cronograma.get(j);
                                    calificacion_actual = obj_cronograma[0].toString();
                                    out.print("<tr>");
                                    for (int k = 0; k < lst_cronograma.size(); k++) {
                                        Object[] obj_cronograma_count = (Object[]) lst_cronograma.get(k);
                                        if (obj_cronograma_count[0].toString().equals(calificacion_actual)) {
                                            count_calificacion++;
                                        }
                                    }
                                    if (!calificacion_actual.equals(calificacion_anterior)) {
                                        out.print("<td rowspan='" + count_calificacion + "'><b style='font-size:11px;color:" + obj_cronograma[11] + "'>" + obj_cronograma[5] + " / " + obj_cronograma[9] + "</b></td>");
                                    } else {
                                        count_calificacion = 0;
                                    }
                                    if (count_calificacion == 1) {
                                        count_calificacion = 0;
                                    }
                                    calificacion_anterior = obj_cronograma[0].toString();
                                    out.print("<th style='background-color:" + obj_cronograma[11] + "' ><a style='color:white' href='Reporte?opc=1&iif=" + obj_cronograma[1] + "&Cbx_anio=" + anio_send + "'>" + obj_cronograma[10] + "</a></th>");
//out.print("<td>" + obj_informe_actividades[2] + "</td>");
                                    for (int k = 14; k <= 25; k++) {
                                        if (obj_cronograma[k] == null) {
                                            out.print("<td style='background-color:#eee' colspan='2'></td>");
                                        } else {
                                            out.print("<td colspan='2' align='center' style='color:" + obj_cronograma[11] + "'>" + obj_cronograma[k].toString() + "</td>");
                                        }
                                    }
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                            out.print("</div>");
                        }
                    }
                    out.print("<script src='Interfaz/Acordeon/Js_accordeon.js'></script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>  
                //<editor-fold defaultstate="collapsed" desc="PLAN MAESTRO DE VALIDACIONES">
                else if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Plan_maestro")) {
                    id_calificacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_calificacion").toString());
                    id_informe = Integer.parseInt(pageContext.getRequest().getAttribute("Id_informe").toString());
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="CALIFICACION">
                    if (id_calificacion > 0) {
                        lst_calificacion = jpacclf.Traer_calificacion_id(id_calificacion);
                        Object[] obj_calificacion = (Object[]) lst_calificacion.get(0);
                        lst_informes = jpacifm.Informes_id_calificacion(id_calificacion);
                        if (lst_informes != null) {
                            out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:300px%;height:450px;overflow:scroll;position: absolute;top: 15px;left:25%;'>");
                            out.print("<div style='float:right;'><a href='Reporte?opc=4&icl=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                            out.print("<h3>Detalles</h3>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>Datos</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='left'><b>Calificacion :</b>" + obj_calificacion[1] + "");
                            out.print("<br /><b>Tipo</b>" + obj_calificacion[4] + "");
                            out.print("<br /><b>Frecuencia : </b><b>ULT.</b>" + obj_calificacion[21] + " <b>PROX.</b>" + obj_calificacion[22] + "");
                            out.print("<br /><b>Documento : </b>" + obj_calificacion[13] + "");
                            out.print("<br /><b>Grupo : </b>" + obj_calificacion[11] + "");
                            out.print("<br /><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
                                    + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
                                    + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>Listado de informes realizados</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_informes.size(); i++) {
                                Object[] obj_informes = (Object[]) lst_informes.get(i);
                                out.print("<tr><td align='left'>");
                                out.print("<a style='text-decoration:none' href='Reporte?opc=4&icl=" + id_calificacion + "&iif=" + obj_informes[0] + "'><b style='color:" + obj_informes[27] + "'>" + obj_informes[18] + " " + obj_informes[6] + " " + obj_informes[20] + "</b></a><br />");
                                out.print("</td></tr>");
                            }
                            out.print("</table>");
                            out.print("</fieldset>");
                            out.print("</div>");
                        }
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="INFORME">
                    if (id_informe > 0) {
                        lst_informe = jpacifm.Informes_id_informe(id_informe);
                        Object[] obj_informe = (Object[]) lst_informe.get(0);
                        lst_calificacion = jpacclf.Traer_calificacion_id(id_calificacion);
                        Object[] obj_calificacion = (Object[]) lst_calificacion.get(0);
                        dependencias_informe = obj_informe[26].toString();
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;height:600px;overflow:scroll;position: absolute;top: 2px;left:2%;'>");
                        out.print("<div style='float:right;'><a href='Reporte?opc=4&icl=" + id_calificacion + "&iif=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                        out.print("<div style='float:left;'><a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF </div>");
                        out.print("<div id='Imprimir'>");
//                        if (obj_informe[18].toString().equals("VALIDACION")) {
                        //<editor-fold defaultstate="collapsed" desc="INFORME DE VALIDACION">
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td colspan='9' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='5' align='center'><b class='negro'>REGISTRO</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>NO CODIFICADO</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='center'><b>Calificacion</b></td>");
                        out.print("<td align='center'><b>Tipo</b></td>");
                        out.print("<td colspan='2' align='center'><b>Frecuencia</b></td>");
                        out.print("<td align='center'><b>Documento</b></td>");
                        out.print("<td align='center'><b>Grupo</b></td>");
                        out.print("<td colspan='3' align='center'><b>Flujo de trabajo</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='left'>" + obj_calificacion[1] + "</td>");
                        out.print("<td align='center' align='left'>" + obj_calificacion[4] + "</td>");
                        out.print("<td colspan='2' align='left'><b>ULT.</b>" + obj_calificacion[21] + "<br /><b>PROX.</b>" + obj_calificacion[22] + "</td>");
                        out.print("<td align='left'>" + obj_calificacion[13] + "</td>");
                        out.print("<td align='left'>" + obj_calificacion[11] + "</td>");
                        out.print("<td colspan='3' align='left'><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
                                + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
                                + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th COLSPAN='9'>INFORMES DE VALIDACION</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='3' style='width:33%'><h2>PQ</h2><b>Validación</b></td>");
                        out.print("<td COLSPAN='3' style='width:33%'><h2>OQ</h2><b>Calificacion de operación</b></td>");
                        out.print("<td COLSPAN='3' style='width:34%'><h2>IQ</h2><b>Calificacion de instalación</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                        out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                        out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                        out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                        out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                        out.print(obj_informe[3].toString() + "");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                        if (!dependencias_informe.equals("N/A")) {
                            String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int i = 0; i < arg_dependencia.length; i++) {
                                lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                Object[] obj_informes = (Object[]) lst_informes.get(0);
                                if (obj_informes[20].toString().contains("/OQ") || obj_informes[20].toString().equals("OQ")) {
                                    out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                    out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                    out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                    out.print(obj_informes[3].toString() + "");
                                    out.print("</div>");
                                }
                            }
                        }
                        out.print("</td>");
                        out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                        if (!dependencias_informe.equals("N/A")) {
                            String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                            for (int i = 0; i < arg_dependencia.length; i++) {
                                lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                Object[] obj_informes = (Object[]) lst_informes.get(0);
                                if (obj_informes[20].toString().equals("IQ")) {
                                    out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                    out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                    out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                    out.print(obj_informes[3].toString() + "");
                                    out.print("</div>");
                                }
                            }
                        }
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        //  </editor-fold>
//                        } else {
//                            //<editor-fold defaultstate="collapsed" desc="INFORME DE CALIFICACION">
//                            out.print("<h3>Detalle Informe de calificación</h3>");
//                            out.print("<table class='table2' style='width:100%' >");
//                            //<editor-fold defaultstate="collapsed" desc="CABECERA">
//                            out.print("<tr>");
//                            out.print("<td colspan='10' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td align='center'>"
//                                    + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
//                                    + "</td>");
//                            out.print("<td colspan='6' align='center'><b class='negro'>REGISTRO</b></td>");
//                            out.print("<td colspan='3' align='center'><b class='negro'>NO CODIFICADO</b></td>");
//                            out.print("</tr>");
////</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="CALIFICACCION">
//                            out.print("<tr>");
//                            out.print("<th colspan='10'>Calificación</th>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td colspan='2' align='center'><b>Calificacion</b></td>");
//                            out.print("<td align='center'><b>Tipo</b></td>");
//                            out.print("<td colspan='2' align='center'><b>Frecuencia</b></td>");
//                            out.print("<td align='center'><b>Documento</b></td>");
//                            out.print("<td align='center'><b>Grupo</b></td>");
//                            out.print("<td colspan='3' align='center'><b>Flujo de trabajo</b></td>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td colspan='2' align='left'>" + obj_calificacion[1] + "</td>");
//                            out.print("<td align='center' align='left'>" + obj_calificacion[4] + "</td>");
//                            out.print("<td colspan='2' align='left'><b>ULT.</b>" + obj_calificacion[21] + "<br /><b>PROX.</b>" + obj_calificacion[22] + "</td>");
//                            out.print("<td align='left'>" + obj_calificacion[13] + "</td>");
//                            out.print("<td align='left'>" + obj_calificacion[11] + "</td>");
//                            out.print("<td colspan='3' align='left'><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
//                                    + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
//                                    + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
//                            out.print("</tr>");
////</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="INFORME PRINCIPAL">
//                            out.print("<tr>");
//                            out.print("<th colspan='10'>Informe Principal</th>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<th style='background-color:" + obj_informe[27] + ";width:5%'>" + obj_informe[18] + "<br />" + obj_informe[6] + "</th>");
//                            out.print("<td valign='top' colspan='4' style='width:25%' align='left'>"
//                                    //  + "<b>" + obj_informe[15] + " </b>" + ((obj_informe[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informe[6].toString()) + "<br />"
//                                    //  + "<b>" + obj_informe[16] + " </b>" + ((obj_informe[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informe[7].toString()) + "<br />"
//                                    //  + "<b>" + obj_informe[17] + " </b>" + ((obj_informe[8] == null && obj_informe[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informe[7] != null) ? obj_informe[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                    + "<b>Califiacción : </b><br />" + obj_informe[7] + "<br />"
//                                    + "<b>Documento : </b><br />" + obj_informe[12] + "<br />"
//                                    + "<b>Tipo calificación : </b><br />" + obj_informe[20] + "<br />"
//                                    + "<b>Grupo : </b><br />" + obj_informe[22] + " / " + obj_informe[23] + "<br />"
//                                    //+ "<b>Dependencia(s) : </b>" + obj_informe[22] + "</td>");
//                                    + "</td>");
//                            out.print("<td valign='top' colspan='6' align='left'>");
//                            out.print("<b>Contenido : </b>" + obj_informe[28]);
//                            out.print("<button class='accordion'>Informe</button>");
//                            out.print("<div class='panel'>");
//                            out.print(obj_informe[3].toString().split("<hr />")[0] + "");
//                            out.print("</div>");
//                            out.print("<button class='accordion'>Conclusión</button>");
//                            out.print("<div class='panel'>");
//                            out.print(obj_informe[3].toString().split("<hr />")[1] + "");
//                            out.print("</div>");
//                            out.print("<button class='accordion'>Desviaciones</button>");
//                            out.print("<div class='panel'>");
//                            out.print(obj_informe[3].toString().split("<hr />")[2] + "");
//                            out.print("</div>");
//                            out.print("<button class='accordion'>Responsables</button>");
//                            out.print("<div class='panel'>"
//                                    + "<b>" + obj_informe[13] + " </b><br />"
//                                    + "<b>" + obj_informe[14] + " </b><br />"
//                                    + "<b>" + obj_informe[15] + " </b><hr />"
//                                    //  + "<b>" + obj_informe[15] + " </b>" + ((obj_informe[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informe[6].toString()) + "<br />"
//                                    //  + "<b>" + obj_informe[16] + " </b>" + ((obj_informe[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informe[7].toString()) + "<br />"
//                                    //  + "<b>" + obj_informe[17] + " </b>" + ((obj_informe[8] == null && obj_informe[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informe[7] != null) ? obj_informe[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                    + "");
//                            out.print("</div>");
//                            out.print("</td>");
//                            out.print("</tr>");
////</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="INFORME DEPENDENCIA">
//                            if (!dependencias_informe.equals("N/A")) {
//                                out.print("<tr>");
//                                out.print("<th colspan='10'>Informe(s) de dependencia</th>");
//                                out.print("</tr>");
//                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
//                                for (int i = 0; i < arg_dependencia.length; i++) {
//                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
//                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
//                                    out.print("<tr>");
//                                    out.print("<th style='background-color:" + obj_informes[27] + ";width:5%'>" + obj_informes[18] + "<br />" + obj_informes[6] + "</th>");
//                                    out.print("<td valign='top' colspan='4' style='width:25%'align='left'>"
//                                            // + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
//                                            // + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
//                                            // + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                            + "<b>Calificación : </b><br />" + obj_informes[7] + "<br />"
//                                            + "<b>Documento : </b><br />" + obj_informes[12] + "<br />"
//                                            + "<b>Tipo calificación : </b><br />" + obj_informes[20] + "<br />"
//                                            + "<b>Grupo : </b><br />" + obj_informes[22] + " / " + obj_informes[23] + "<br />"
//                                            //+ "<b>Dependencia(s) : </b>" + obj_informes[22] + "</td>");
//                                            + "</td>");
//                                    out.print("<td valign='top' colspan='6' align='left'>");
//                                    out.print("<b>Contenido : </b>" + obj_informes[28]);
//                                    out.print("<button class='accordion'>Informe</button>");
//                                    out.print("<div class='panel'>");
//                                    out.print(obj_informes[3].toString().split("<hr />")[0] + "");
//                                    out.print("</div>");
//                                    out.print("<button class='accordion'>Conclusión</button>");
//                                    out.print("<div class='panel'>");
//                                    out.print(obj_informes[3].toString().split("<hr />")[1] + "");
//                                    out.print("</div>");
//                                    out.print("<button class='accordion'>Desviaciones</button>");
//                                    out.print("<div class='panel'>");
//                                    out.print(obj_informes[3].toString().split("<hr />")[2] + "");
//                                    out.print("</div>");
//                                    out.print("<button class='accordion'>Responsables</button>");
//                                    out.print("<div class='panel'>"
//                                            + "<b>" + obj_informes[13] + " </b><br />"
//                                            + "<b>" + obj_informes[14] + " </b><br />"
//                                            + "<b>" + obj_informes[15] + " </b><hr />"
//                                            // + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
//                                            // + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
//                                            // + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                            + "");
//                                    out.print("</div>");
//                                    out.print("</td>");
//                                    out.print("</tr>");
//                                }
//                            }
////</editor-fold>
//                            out.print("</table>");
//                            //</editor-fold>
//                        }
                        out.print("</div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="ENLACES">
                    out.print("<script src='Interfaz/Acordeon/Js_accordeon.js'></script>");
                    out.print("<script src=\"Interfaz/Mapa_conceptual/jOrgChart/api_google_1.js\"></script>\n"
                            + "<script src=\"Interfaz/Mapa_conceptual/jOrgChart/api_google_2.js\"></script>\n"
                            + "<link rel=\"stylesheet\" href=\"Interfaz/Mapa_conceptual/jOrgChart/jquery.jOrgChart.css\"/>\n"
                            + "<script src=\"Interfaz/Mapa_conceptual/jOrgChart/jquery.jOrgChart.js\"></script>\n"
                            + "<script src=\"Interfaz/Mapa_conceptual/jOrgChart/Js_mapa.js\"></script>\n"
                            + "<script src=\"Interfaz/Mapa_conceptual/jOrgChart/Jq_mapa.js\"></script>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS">
                    out.print("<ul id=\"org\" style=\"display:none\">");
                    out.print("<li><div style=\"width:300px;font-weight:bold;font-size:14px;border: 2px #292929 solid\">PLAN MAESTRO DE VALIDACIONES PLASTITEC S.A</div>");
                    out.print("<ul>");
                    lst_grupos = jpacgpo.Traer_subgrupos("N/A");
                    for (int i = 0; i < lst_grupos.size(); i++) {
                        Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                        out.print("<li><div style=\"font-weight:bold;color:#fff;background-color:" + obj_grupos[6] + ";border: 2px " + obj_grupos[6] + " solid\">" + obj_grupos[1].toString().toUpperCase() + "</div>");
                        lst_subgrupos = jpacgpo.Traer_subgrupos(obj_grupos[1].toString());
                        if (lst_subgrupos != null) {
                            out.print("<ul>");
                            for (int j = 0; j < lst_subgrupos.size(); j++) {
                                Object[] obj_sub_grupos = (Object[]) lst_subgrupos.get(j);
                                out.print("<li><div style=\"font-weight:bold;color:" + obj_sub_grupos[6] + ";border: 2px " + obj_sub_grupos[6] + " solid\">" + obj_sub_grupos[1].toString().toUpperCase() + "</div>");
                                lst_calificaciones = jpacclf.Calificaciones_grupo(Integer.parseInt(obj_sub_grupos[0].toString()));
                                if (lst_calificaciones != null) {
                                    out.print("<ul>");
                                    out.print("<li>");
                                    out.print("<div align='left' style=\"font-size:10px;border: 2px " + obj_sub_grupos[6] + " dashed;\">");
                                    for (int k = 0; k < lst_calificaciones.size(); k++) {
                                        Object[] obj_calificaciones = (Object[]) lst_calificaciones.get(k);
                                        if (obj_calificaciones[4].toString().contains("PQ") || obj_calificaciones[4].toString().contains("RETROSPECTIVA") || obj_calificaciones[8].toString().contains("SI")) {
                                            out.print("<a style='text-decoration: none' href='Reporte?opc=4&icl=" + obj_calificaciones[0] + "'><b style='font-size:12px;color:" + obj_grupos[6] + "'>( " + obj_calificaciones[0] + " )</b></a>" + obj_calificaciones[1].toString().toUpperCase() + "<hr />");
                                        }
                                    }
                                    out.print("</div>");
                                    out.print("</li>");
                                    out.print("</ul>");
                                }
                                out.print("</li>");
                            }
                            out.print("</ul>");
                        }
                        out.print("</li>");
                    }
                    out.print("</ul></li></ul>");
                    out.print("");
                    out.print("<div style='float:left;'><a onclick='Imprimir_chart();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF </div>");
                    out.print("<div style='width:100%;height:550px;overflow:scroll;background-color:#fff' id=\"chart\" class=\"orgChart\"></div>");
                    //</editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
//</editor-fold>
// // <editor-fold defaultstate="collapsed" desc="VALIDACIÓN RESTROSPECTIVA">
// if (pageContext.getRequest().getAttribute("Reporte").toString().equals("Modulo_validacion")) {
//  id_validacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_validacion").toString());
//  out.print("<div id='content_sin'>");
//  out.print("");
//  if (!rol.equals("Consulta")) {
//out.print("<h3><a href='Reporte?opc=2&ivl=-1'><img src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Desplegar Menu' /></a>"
//  + "Validación Retrospectiva<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
//  } else {
//out.print("<h3>Validación Retrospectiva<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
//  }
//  //<editor-fold defaultstate="collapsed" desc="REGISTRAR INFORME">
//  lst_tipo_informe = jpactif.Tipos_informe();
//  if (id_validacion == -1) {
//out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
//out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 50px;left:25%;'>");
//out.print("<div style='float:right;'><a href='Reporte?opc=2&ivl=0'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
//out.print("<h3>Nuevo Informe</h3>");
//out.print("<form action='Reporte?opc=3&ivl=0' onsubmit='Informe();' method='post' id='Form_informe'>");
//out.print("<table style='text-align: left;'><tr>");
//out.print("<td><b>Fecha:</b></td>");
//out.print("<td><input type='text' name='Txt_fecha' id=\"datepicker\"  placeholder='Fecha'>");
//out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script></td></tr>");
//out.print("<tr><td><b>Tipo informe:</b></td>");
//out.print("<td><select name='Cbx_tipo_informe' id='Cbx_tipo_informe' title='Tipo de informe' >");
//out.print("<option value='0' >Tipo de informe</option>");
//for (int i = 0; i < lst_tipo_informe.size(); i++) {
// Object[] obj_tipo_informe = (Object[]) lst_tipo_informe.get(i);
// if (Integer.parseInt(obj_tipo_informe[4].toString()) > 0) {
//  out.print("<option value='" + obj_tipo_informe[0] + "' style='color:" + obj_tipo_informe[3] + "'>" + obj_tipo_informe[1] + "</option>");
// }
//}
//out.print("</select>"
//  + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_informe');"
//  + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
//out.print("<tr><td colspan='2'><b>Contenido:</b></td></tr>");
//out.print("<tr><td colspan='2'><textarea name='Txt_contenido' id='Txt_contenido' style='width: 500px;' placeholder='Contenido'></textarea>");
//out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_contenido');validation.add( Validate.Presence );</script></td></tr>");
//out.print("</table>");
//out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 500px; height: 250px' placeholder='descripcion'>");
//out.print("<b>Informe de validación retrospectiva : </b><br/>");
//out.print("<div contenteditable='true'><p>*</p><p></p></div>");
//out.print("</textarea>");
//out.print("<input type='submit' id='Btn_guardar_informe' value='Guardar' />");
//out.print("</form>");
//out.print("</fieldset>");
//out.print("</div>");
//  } //</editor-fold>
//  //<editor-fold defaultstate="collapsed" desc="MODIFICAR INFORME">
//  else if (id_validacion > 0) {
//lst_validacion = jpacvld.Traer_validacion_id(id_validacion);
//Object[] obj_validacion = (Object[]) lst_validacion.get(0);
//out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
//out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:500px;position: absolute;top: 50px;left:25%;'>");
//out.print("<div style='float:right;'><a href='Reporte?opc=2&ivl=0'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Cancelar'></a></div>");
//out.print("<h3>Modificar Informe</h3>");
//out.print("<form action='Reporte?opc=3&ivl=" + id_validacion + "' onsubmit='Informe();' method='post' id='Form_informe'>");
//out.print("<table align='left'><tr>");
//out.print("<td><b>Fecha:</b></td>");
//out.print("<td><input type='text' name='Txt_fecha' id=\"datepicker\" placeholder='Fecha' value='" + obj_validacion[6] + "' />");
//out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script></td></tr>");
//out.print("<tr><td><b>Tipo informe:</b></td>");
//out.print("<td><select name='Cbx_tipo_informe' id='Cbx_tipo_informe' title='Tipo de informe' >");
//out.print("<option value='0' >Tipo de informe</option>");
//for (int i = 0; i < lst_tipo_informe.size(); i++) {
// Object[] obj_tipo_informe = (Object[]) lst_tipo_informe.get(i);
// if (Integer.parseInt(obj_tipo_informe[4].toString()) > 0) {
//  if (Integer.parseInt(obj_tipo_informe[0].toString()) == Integer.parseInt(obj_validacion[3].toString())) {
//out.print("<option value='" + obj_tipo_informe[0] + "' style='color:" + obj_tipo_informe[3] + "' selected>" + obj_tipo_informe[1] + "</option>");
//  } else {
//out.print("<option value='" + obj_tipo_informe[0] + "' style='color:" + obj_tipo_informe[3] + "'>" + obj_tipo_informe[1] + "</option>");
//  }
// }
//}
//out.print("</select>"
//  + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_informe');"
//  + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
//out.print("<tr><td colspan='2'><b>Contenido:</b></td></tr>");
//out.print("<tr><td colspan='2'><textarea name='Txt_contenido' id='Txt_contenido' style='width: 500px;' placeholder='Contenido'>" + obj_validacion[1] + "</textarea>");
//out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_contenido');validation.add( Validate.Presence );</script></td></tr>");
//out.print("</table>");
//out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 500px; height: 400' placeholder='descripcion'>");
//out.print("" + obj_validacion[2].toString().replace("<div>", "<div contenteditable='true'>") + "");
//out.print("</textarea>");
//out.print("<input type='submit' id='Btn_guardar_informe' value='Modificar '>");
//out.print("</form>");
//out.print("</fieldset>");
//out.print("</div>");
//  }
//  //</editor-fold>
//  //<editor-fold defaultstate="collapsed" desc="CONSULTA">
//  lst_validaciones = jpacvld.Consultar_validaciones();
//  if (lst_validaciones == null) {
//out.print("<center>");
//out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
//out.print("<b>No hay datos de informes registrados</b>");
//out.print("</center>");
//  } else {
//out.print("<div id='NavPosicion'></div>");
//out.print("<table class='table' id='resultados' style='width:100%'>");
//out.print("<tr><td colspan='5'></td></tr>");
//for (int i = 0; i < lst_validaciones.size(); i++) {
// Object[] obj_validaciones = (Object[]) lst_validaciones.get(i);
// out.print("<tr>");
// out.print("<th style='width:10%;background-color:" + obj_validaciones[5] + "'>" + obj_validaciones[6] + "<br />" + obj_validaciones[4] + "</th>");
// out.print("<td style='width:40%;' valign='top'><b>Contenido</b><br />" + obj_validaciones[1] + "</td>");
// out.print("<td style='width:50%;' valign='top'>");
// if (!rol.equals("Consulta")) {
//  if (i == 0) {
//out.print("<a href='Reporte?opc=2&ivl=" + obj_validaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' title='Modificar informe'></a> ");
//  }
// }
// out.print("<b>Informe</b><br />" + obj_validaciones[2] + "</td>");
// out.print("</tr>");
//}
//out.print("</table>");
//out.print("<script type='text/javascript'>");
//out.print("var pager = new Pager('resultados', 10);");
//out.print("pager.init();");
//out.print("pager.showPageNav('pager','NavPosicion');");
//out.print("pager.showPage(1);");
//out.print("</script>");
//  }
//  //</editor-fold>
//  out.print("</div> <!-- END of content -->");
//  out.print("<div class='cleaner'></div>");
// } // </editor-fold>  
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
