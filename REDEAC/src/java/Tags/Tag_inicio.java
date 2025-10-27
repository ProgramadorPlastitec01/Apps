package Tags;

import Controladoras.BitacoraJpaController;
import Controladoras.CronogramaJpaController;
import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        BitacoraJpaController jpa_bitacora = new BitacoraJpaController();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        CronogramaJpaController jpa_cronograma = new CronogramaJpaController();
        String firma = "";
        try {
            firma = pageContext.getRequest().getAttribute("Tecnico_turno").toString();
        } catch (Exception e) {
            firma = "";
        }
        try {

            if (id_rol == 8) {
                //<editor-fold defaultstate="collapsed" desc="INICIO DE CONSULTA">
                out.print("<h3>¡Bienvenido a REDEAC!</h3>");
                out.print("<br><b class='verde'>Podra consultar los casos reportandos y los casos ya solucionados</b>");
                //</editor-fold>
            } else if (!firma.equals("firma")) {
                //<editor-fold defaultstate="collapsed" desc="INICIO DE CONSULTA">
                List lst_bitacoras = jpa_bitacora.consultaultimasBitacoras();
                List lst_cronograma = null;
                if (id_rol == 2 || id_rol == 4 || id_rol == 3) {
                    lst_cronograma = jpa_cronograma.consultaCronogramaAnio("0000");
                } else {
                    lst_cronograma = jpa_cronograma.consultaCronograma(((id_rol == 3) ? 1 : 2), "0000");
                }
                out.print("<ul class='nav nav-tabs'>");
                out.print("<li class='active'><a data-toggle='tab' href='#calendario'>Calendario</a></li>");
                out.print("<li><a data-toggle='tab' href='#bitacora'>Bitacoras</a></li>");
                out.print("</ul>");
                out.print("<div class='tab-content'>");
                out.print("<div id='calendario' class='tab-pane fade in active' style='overflow:scroll;height: 92.8%;'>");
                //<editor-fold defaultstate="collapsed" desc="Calendario">
                out.print("<div id='calendar'></div>");
                out.print("<script>");
                out.print("$(function() {\n");
                out.print("var currentYear = new Date().getFullYear();\n");
                out.print("$('#calendar').calendar({\n");
                out.print("displayWeekNumber: true,\n");
                out.print("language:'es',\n");
                out.print("style:'border',\n");
                out.print("mouseOnDay: function(e) {\n");
                out.print("if(e.events.length > 0) {\n");
                out.print("var content = '';\n");
                out.print("for(var i in e.events) {\n");
                out.print("content += '<div class=\"event-tooltip-content\">'\n");
                out.print("+ '<div class=\"event-name\" style=\"color:' + e.events[i].color + '\">' + e.events[i].name + '</div>'\n");
                out.print("+ '<div class=\"event-location\">' + e.events[i].location + '</div>'\n");
                out.print("+ '</div>';\n");
                out.print("}\n");
                out.print("\n");
                out.print("$(e.element).popover({\n");
                out.print("trigger: 'manual',\n");
                out.print("container: 'body',\n");
                out.print("html:true,\n");
                out.print("content: content\n");
                out.print("});\n");
                out.print("$(e.element).popover('show');\n");
                out.print("}\n");
                out.print("},\n");
                out.print("mouseOutDay: function(e) {\n");
                out.print("if(e.events.length > 0) {\n");
                out.print("$(e.element).popover('hide');\n");
                out.print("}\n");
                if (lst_cronograma != null) {
                    out.print("},");
                    out.print("dataSource: [\n");
                    for (int i = 0; i < lst_cronograma.size(); i++) {
                        Object[] obj_cronograma = (Object[]) lst_cronograma.get(i);
                        if ((Integer) obj_cronograma[6] != 0) {
                            out.print("{\n");
                            out.print("id: " + obj_cronograma[0] + ",\n");
                            out.print("name: '" + ((obj_cronograma[3] == null) ? "Actividad" : obj_cronograma[3]) + "',\n");
                            out.print("location: '" + obj_cronograma[4] + "',\n");
                            out.print("startDate: new Date(currentYear, " + (Integer.parseInt(obj_cronograma[5].toString().split("-")[1]) - 1) + ", 1),\n");
                            out.print("endDate: new Date(currentYear, " + (Integer.parseInt(obj_cronograma[5].toString().split("-")[1]) - 1) + ", 28)\n");
                            out.print("},\n");
                        }
                    }
                    out.print("]\n");
                } else {
                    out.print("}");
                }
                out.print("});\n");
                out.print("});");
                out.print("</script>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div id='bitacora' class='tab-pane fade' style='height: 92.8%;'>");
                //<editor-fold defaultstate="collapsed" desc="Bitacoras">
                out.print("<div class='slidePrn'>");
                if (lst_bitacoras != null) {
                    for (int i = 0; i < lst_bitacoras.size(); i++) {
                        Object[] obj_bitacora = (Object[]) lst_bitacoras.get(i);
                        out.print("<div class='slidesBtc'>");
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th style='width:20%'>Usuario</th>");
                        out.print("<th style='width:80%'>Actividades</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'  valign='top'><img src='Interfaz/Fotos/" + obj_bitacora[4] + ".jpg' width='140px' height='144px'>");
                        out.print("<br><br><b>" + obj_bitacora[2] + "</b><br>" + obj_bitacora[0] + "<br>" + obj_bitacora[1] + "</b>");
                        out.print("</td>");
                        out.print("<td valign='top' >" + obj_bitacora[3] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                    }
                } else {
                    out.print("NO EXISTE BITACORAS");
                }
                out.print("</div>");
                out.print("<div onclick='plusDivs(-1)' style='float: left;'><span style='font-size: 3em;color: graytext'><i class='fas fa-angle-left'></i></span></div>");
                out.print("<div onclick='plusDivs(1)' style='float: right;'><span style='font-size: 3em;color: graytext'><i class='fas fa-angle-right'></i></span></div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="firma usuario">
                List lst_turno = jpa_usuario.traerUsuarioTurno();
                out.print("<div class='modal fade' id='Firma' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog'>");
                out.print("<div class='modal-content'>");
                out.print("<input type='hidden' name='idU' value='" + id_usuario + "' id='idA'>");
                out.print("<div class='modal-header'>");
                out.print("<a href='Salir.jsp' class='close'>&times;</a>");
                out.print("<h4 class='modal-title'>Control Turno Tecnico</h4>");
                out.print("</div>");
                out.print("<div class='modal-body'>");
                out.print("<table class='table'>");
                out.print("<tr>");
                out.print("<td align='center'><b class='title'>Tecnico(s) en turno: </b></td>");
                out.print("<td valign='top'>");
                if (lst_turno == null) {
                    out.print("<b>No hay tecnicos en turno</b>");
                } else {
                    for (int i = 0; i < lst_turno.size(); i++) {
                        Object[] obj_turno = (Object[]) lst_turno.get(i);
                        out.print("<b>" + obj_turno[1] + " " + obj_turno[2] + "</b><br>");
                    }
                }
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("<div class='modal-footer' align='center'>");
                out.print("<form action='Login?opc=2' name='formA' method='post'>");
                out.print("<div style='display:flex; margin-left: 8%; align-items:center' >");
                out.print("<div><b>Ingresar al turno?</b></div>&nbsp;&nbsp;"
                        + "<div><input type='text' class='form-control' name='txt_firma' id='firma-id' value='' autocomplete='off' placeholder='Firma' required></div>");
                out.print("</div>");
                out.print("<div style='float:right;'><input type='submit' value='Firmar'></div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>");
                out.print("$(\"#Firma\").modal(\"show\");");
                out.print("</script>");
//</editor-fold>
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
