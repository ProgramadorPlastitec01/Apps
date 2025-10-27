package Tags;

import Controladoras.BitacoraJpaController;
import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_bitacoras extends TagSupport {

    @Override
    @SuppressWarnings("null")
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        BitacoraJpaController jpa_bitacora = new BitacoraJpaController();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        String nombreUsa = pageContext.getSession().getAttribute("Nombre_apellido").toString();
        String modulo = pageContext.getRequest().getAttribute("modulo").toString();
        Date fecha = new Date();
        try {
            if (modulo.equals("B")) {
                //<editor-fold defaultstate="collapsed" desc="generar bitacora">
                out.print("<div class='modal fade' id='Generar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog modal-sm' style='width:35%;'>");
                out.print("<div class='modal-content'>");
                out.print("<form action='Bitacora?opc=2' name='formA' method='post'>");
                out.print("<div class='modal-header'>");
                out.print("<a href='Bitacora?opc=1&mod=BC' class='close'>&times;</a>");
                out.print("<h4 class='modal-title'>Generar Bitacora</h4>");
                out.print("</div>");
                out.print("<div class='modal-body' align='center'>");
                out.print("<table style='width:90%;font-size:12px'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Fecha Inicio: </b><br>");
                out.print("<input type='text' class='form-control' name='txt_fechaI' id='datepicker' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha inicio' required>");
                out.print("</td>");
                out.print("<td>");
                out.print("<b>Hora Inicio: </b><br>");
                out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' placeholder='Hora Inicio' required>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Fecha Fin: </b><br>");
                out.print("<input type='text' class='form-control' name='txt_fechaF' id='datepicker2' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha fin' required>");
                out.print("</td>");
                out.print("<td>");
                out.print("<b>Hora Fin: </b><br>");
                out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' placeholder='Hora Inicio' required>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("<div class='modal-footer'>");
                out.print("<input type='submit' value='Generar'>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>");
                out.print("$(\"#Generar\").modal(\"show\");");
                out.print("</script>");
//</editor-fold>
            }
            if (modulo.equals("BG")) {
                //<editor-fold defaultstate="collapsed" desc="enviar bitacora">
                String fechaI = pageContext.getRequest().getAttribute("fecha_inicial").toString();
                String fechaF = pageContext.getRequest().getAttribute("fecha_final").toString();
                String asunto = "R-TI-" + fechaI.split(" ")[0].replace("-", "") + " BITACORA DE COMUNICACION INTERNA " + nombreUsa.toUpperCase() + "";
                out.print("<b>R-TI-" + fechaI.split(" ")[0].replace("-", "") + " BITACORA DE COMUNICACION INTERNA " + nombreUsa + "</b><br>");
                out.print("<b class='title'>Plastitec</b><br>");
                out.print("<form action='Bitacora?opc=3' method='post' name='FormB'>");
                out.print("<input type='hidden' name='txt_asunto' id='txt_asunto' value='" + asunto + "' />");
                out.print("<input type='hidden' name='txt_fechaI' id='txt_asunto' value='" + fechaI + "' />");
                out.print("<input type='hidden' name='txt_fechaF' id='txt_asunto' value='" + fechaF + "' />");
                out.print("<table class='table2'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b class='title'>Turno: </b>");
                out.print("<select name='slc_turno' id='turno-id' required>");
                out.print("<option value='' style='display:none'>Seleccione Turno</option>");
                out.print("<option value='TURNO 1'>TURNO 1</option>");
                out.print("<option value='TURNO 2'>TURNO 2</option>");
                out.print("<option value='TURNO 3'>TURNO 3</option>");
                out.print("<option value='TURNO 1 12 horas'>TURNO 1 12 HORAS</option>");
                out.print("<option value='TURNO 2 12 horas'>TURNO 2 12 HORAS</option>");
                out.print("<option value='TURNO OFICINA'>TURNO OFICINA</option>");
                out.print("</select>");
                out.print("</td>");
                out.print("<td><b class='title'>Responsable: </b>" + nombreUsa + "</td>");
                out.print("<td><b class='title'>Area: </b>Tecnología de Información</td>");
                out.print("<td><b class='title'>Llegada: </b>" + fechaI + "<hr><b class='title'>Salida: </b>" + fechaF + "</td>");
                out.print("<td align='center'><a href='#' style='color:#292929' title='Actualizar' data-toggle='modal' data-target='#ActualizarF'><i class='fas fa-retweet fa-2x'></i></a></td>");
                out.print("<td align='center'><a href='#' onclick='Javascript:document.FormB.submit();' style='color:#292929' title='Guardar'><i class='fas fa-save fa-2x'></i></a></td>");
                out.print("</tr>");
                out.print("</table><br>");
                out.print("<div class='panel-group' id='accordion'>");
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#actividades'>ACTIVIDADES</a></h4>");
                out.print("</div>");
                out.print("<div id='actividades' class='panel-collapse collapse in'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="actividades">
                List lst_actividades = jpa_bitacora.consultaActividadesBitacora(id_usuario, fechaI, fechaF, 0);
                if (lst_actividades != null) {
                    out.print("<input type='hidden' name='txt_actividades' id='txt_actividades' value='" + lst_actividades.size() + "' />");
                    out.println("<table class='table'>");
                    out.println("<tr>");
                    out.println("<th align='center' style='width:15%'>Fecha Registro</th>");
                    out.println("<th align='center' style='width:15%'>Asunto</th>");
                    out.println("<th align='center' style='width:70%'>Actividades</th>");
                    out.println("</tr>");
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                        out.println("<tr>");
                        out.println("<td align='center'>" + obj_actividades[4] + "</td>");
                        out.println("<td>" + obj_actividades[1] + "</td>");
                        out.println("<td valign='top'>" + obj_actividades[2] + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                } else {
                    out.print("<input type='hidden' name='txt_actividades' id='txt_actividades' value='0' />");
                    out.print("<b>No se encontraron resultados</b>");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="actividades reportadas">
                if (id_rol == 3 || id_rol == 5) {
                    List lst_actividadesR = jpa_bitacora.consultaActividadesReportadasBitacora(id_usuario, fechaI, fechaF, 0);
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#actividadesR'>ACTIVIDADES REPORTADAS</a></h4>");
                    out.print("<span class='label pull-right label-info'>" + ((lst_actividadesR != null) ? lst_actividadesR.size() : 0) + "</span>");
                    out.print("</div>");
                    out.print("<div id='actividadesR' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_actividadesR != null) {
                        out.print("<input type='hidden' name='txt_actividadesR' id='txt_actividadesR' value='" + lst_actividadesR.size() + "' />");
                        out.print("<table class='table' id='resultados'>");
                        for (int i = 0; i < lst_actividadesR.size(); i++) {
                            Object[] obj_actividadesR = (Object[]) lst_actividadesR.get(i);
                            out.print("<tr>");
                            out.print("<td colspan='5' style='background-color: #ddd;'></d>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:20%'><b class='title'>Fecha: </b>" + obj_actividadesR[15] + "</td>");
                            out.print("<td colspan='2' style='width:25%'><b class='title'>Reportante: </b>" + obj_actividadesR[1] + "</td>");
                            out.print("<td style='width:25%'><b class='title'>Area: </b>" + ((obj_actividadesR[20] == null) ? "SIN ASIGNAR" : obj_actividadesR[20]) + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:23%'><b class='title'>Tipo Soporte: </b>" + obj_actividadesR[6] + "</td>");
                            if (id_rol == 5) {
                                out.print("<td colspan='2' style='width:18%'><b class='title'>Aplicativo: </b>" + obj_actividadesR[8] + "</td>");
                            } else {
                                out.print("<td style='width:18%'><b class='title'>PC: </b>" + obj_actividadesR[3] + "</td>");
                                out.print("<td style='width:18%'><b class='title'>Equipo: </b>" + ((obj_actividadesR[18] == null) ? "N/A" : obj_actividadesR[18]) + "</td>");
                            }
                            out.print("<td align='top'><b>Parada Equipo: " + obj_actividadesR[16] + "<br>Produccion: " + obj_actividadesR[17] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' valign='top'>" + obj_actividadesR[12] + "</td>");
                            out.print("<td colspan='2' valign='top'>" + obj_actividadesR[13] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b class='title'>Fecha Reportante: </b>" + obj_actividadesR[9] + "</td>");
                            out.print("<td colspan='2'><b class='title'>Fecha Ejecucion: </b>" + obj_actividadesR[10] + "</td>");
                            out.print("<td><b class='title'>Fecha Fin: </b>" + obj_actividadesR[11] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<input type='hidden' name='txt_actividadesR' id='txt_actividadesR' value='0' />");
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<input type='hidden' name='txt_actividadesR' id='txt_actividadesR' value='0' />");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Casos">
                if (id_rol == 3) {
                    List lst_casos = jpa_bitacora.consultaCasosBitacora(id_usuario, fechaI, fechaF, 0);
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#casos'>CASOS</a></h4>");
                    out.print("<span class='label pull-right label-info'>" + ((lst_casos != null) ? lst_casos.size() : 0) + "</span>");
                    out.print("</div>");
                    out.print("<div id='casos' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_casos != null) {
                        out.print("<input type='hidden' name='txt_casos' id='txt_casos' value='" + lst_casos.size() + "' />");
                        out.print("<table class='table' id='resultados'>");
                        for (int i = 0; i < lst_casos.size(); i++) {
                            Object[] obj_casos = (Object[]) lst_casos.get(i);
                            out.print("<tr>");
                            out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                            out.print("<td style='width:70%' valign='top'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                            out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_casos[4] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_casos[9] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<input type='hidden' name='txt_casos' id='txt_casos' value='0' />");
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<input type='hidden' name='txt_casos' id='txt_casos' value='0' />");
                }
                //</editor-fold>
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                List lst_pendientesS = jpa_bitacora.consultaPendientesSolucionadosBitacora(id_usuario, fechaI, fechaF, 0);
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#pendientesS'>PENDIENTES SOLUCIONADOS</a></h4>");
                out.print("<span class='label pull-right label-info'>" + ((lst_pendientesS != null) ? lst_pendientesS.size() : 0) + "</span>");
                out.print("</div>");
                out.print("<div id='pendientesS' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="Pendientes Solucionados">
                if (lst_pendientesS != null) {
                    out.println("<input type='hidden' name='txt_pendientesS' id='txt_pendientesS' value='" + lst_pendientesS.size() + "' />");
                    out.print("<table class='table'>");
                    for (int i = 0; i < lst_pendientesS.size(); i++) {
                        Object[] obj_pendiente = (Object[]) lst_pendientesS.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_pendiente[4] + "<hr/><b class='title'>Asunto: </b>" + obj_pendiente[13] + "</td>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Pendiente: </b>" + obj_pendiente[1] + "</td>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_pendiente[9] + "<hr /><b class='title'>Para: </b>" + obj_pendiente[10] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_pendiente[2] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.println("<input type='hidden' name='txt_pendientesS' id='txt_pendientesS' value='0' />");
                    out.print("<b>No se encontraron resultados</b>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="Movimientos Equipos">
                if (id_rol == 3 || id_rol == 4) {
                    List lst_movimientos = jpa_bitacora.consultaMovimientosEquiposBitacora(nombreUsa, fechaI.split(" ")[0], fechaF.split(" ")[0]);
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#movimientos'>MOVIMIENTOS EQUIPOS</a></h4>");
                    out.print("<span class='label pull-right label-info'>" + ((lst_movimientos != null) ? lst_movimientos.size() : 0) + "</span>");
                    out.print("</div>");
                    out.print("<div id='movimientos' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_movimientos != null) {
                        out.print("<input type='hidden' name='txt_movimientos' id='txt_movimientos' value='" + lst_movimientos.size() + "' />");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th style='width:10%'>Equipo</th>");
                        out.print("<th style='width:10%'>Estado</th>");
                        out.print("<th style='width:20%'>Responsable</th>");
                        out.print("<th style='width:10%'>Tipo</th>");
                        out.print("<th style='width:40%'>Observaciones/Area</th>");
                        out.print("<th style='width:10%'>Fecha</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_movimientos.size(); i++) {
                            Object[] obj_movimientos = (Object[]) lst_movimientos.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_movimientos[1] + "</td>");
                            out.print("<td align='center'>");
                            if (obj_movimientos[7].equals("B")) {
                                out.print("<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #51cf66;'></i>");
                            } else if (obj_movimientos[7].equals("R")) {
                                out.print("<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #ff922b;'></i>&nbsp;<i class='far fa-circle'></i>");
                            } else {
                                out.print("<i class='fa fa-circle' style='color: #ff6b6b;'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>");
                            }
                            out.print("</td>");
                            out.print("<td>" + obj_movimientos[2] + "</td>");
                            out.print("<td>" + obj_movimientos[3] + "</td>");
                            out.print("<td>" + obj_movimientos[8] + "<hr>" + obj_movimientos[5] + "&nbsp;|&nbsp;<b class='title'>Cargo: </b>" + obj_movimientos[6] + "</td>");
                            out.print("<td align='center'>" + obj_movimientos[11] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<input type='hidden' name='txt_movimientos' id='txt_movimientos' value='0' />");
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<input type='hidden' name='txt_movimientos' id='txt_movimientos' value='0' />");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="casos pendientes">
                if (id_rol == 3) {
                    List lst_casosP = jpa_bitacora.consultaCasosPendientesBitacora();
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#casosP'>CASOS PENDIENTES</a></h4>");
                    out.print("<span class='label pull-right label-warning'>" + ((lst_casosP != null) ? lst_casosP.size() : 0) + "</span>");
                    out.print("</div>");
                    out.print("<div id='casosP' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_casosP != null) {
                        out.println("<input type='hidden' name='txt_casosP' id='txt_casosP' value='" + lst_casosP.size() + "' />");
                        out.print("<table class='table' id='resultados'>");
                        out.println("<tr>");
                        out.println("<th>Fecha</th>");
                        out.println("<th>Área</th>");
                        out.println("<th>Reportante</th>");
                        out.println("<th>Prioridad</th>");
                        out.println("<th>Caso</th>");
                        out.println("</tr>");
                        for (int i = 0; i < lst_casosP.size(); i++) {
                            Object[] obj_casosP = (Object[]) lst_casosP.get(i);
                            out.println("<tr>");
                            out.println("<td>" + obj_casosP[1] + "</td>");
                            out.println("<td>" + obj_casosP[3] + "</td>");
                            out.println("<td>" + obj_casosP[5] + "</td>");
                            out.println("<td>" + obj_casosP[7] + "</td>");
                            out.println("<td>" + obj_casosP[6] + "</td>");
                            out.println("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.println("<input type='hidden' name='txt_casosP' id='txt_casosP' value='0' />");
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.println("<input type='hidden' name='txt_casosP' id='txt_casosP' value='0' />");
                }
//</editor-fold>

                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                List lst_pendientes = jpa_bitacora.consultaPendientesBitacora(id_rol);
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#pendientes'>PENDIENTES</a></h4>");
                out.print("<span class='label pull-right label-warning'>" + ((lst_pendientes != null) ? lst_pendientes.size() : 0) + "</span>");
                out.print("</div>");
                out.print("<div id='pendientes' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="pendientes">
                if (lst_pendientes != null) {
                    out.println("<input type='hidden' name='txt_pendientes' id='txt_pendientes' value='" + lst_pendientes.size() + "' />");
                    out.print("<table class='table' id='resultados'>");
                    for (int i = 0; i < lst_pendientes.size(); i++) {
                        Object[] obj_pendiente = (Object[]) lst_pendientes.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='title'>Fecha: </b>" + obj_pendiente[4] + "&nbsp;|&nbsp;<b class='title'>Asunto: </b>" + obj_pendiente[12] + "</td>");
                        out.print("<td style='width:15%'><b class='title'>De: </b>" + obj_pendiente[9] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:80%' valign='top'><b class='title'>Pendiente: </b>" + obj_pendiente[1] + "</td>");
                        out.print("<td><b class='title'>Para: </b>" + obj_pendiente[10] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.println("<input type='hidden' name='txt_pendientes' id='txt_pendientes' value='0' />");
                    out.print("<b>No se encontraron resultados</b>");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES DIARIAS">
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                List lst_acDiarias = jpa_bitacora.consultaActividadesdiariasBitacora(id_usuario, fechaI, fechaF);
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#acDairias'>ACTIVIDADES DIARIAS (R-TI-001)</a></h4>");
                out.print("<span class='label pull-right label-warning'>" + ((lst_acDiarias != null) ? lst_acDiarias.size() : 0) + "</span>");
                out.print("</div>");
                out.print("<div id='acDairias' class='panel-collapse collapse'>");
                out.print("<div class='panel-body' style='overflow-x: auto;'>");
                out.print("<input type='hidden' name='txt_AcDiarias' id='txt_AcDiarias' value='" + ((lst_acDiarias == null) ? 0 : lst_acDiarias.size()) + "'>");
                if (lst_acDiarias != null) {
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Fecha Solicitud</th>");
                    out.print("<th>Usuario</th>");
                    out.print("<th>Fecha Ejecucion</th>");
                    out.print("<th>Nro equipo</th>");
                    out.print("<th>Asunto</th>");
                    out.print("<th>Solucion</th>");
                    out.print("<th>Ejecuto</th>");
                    out.print("<th>Fecha Ejecucion</th>");
                    out.print("<th>Parada?</th>");
                    out.print("<th>Tipo Soporte</th>");
                    out.print("<th>Calificacion</th>");
                    out.print("<th>Opinion</th>");
                    out.print("<th>Firma</th>");
                    out.print("</tr>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_acDiarias.size(); i++) {
                        Object[] obj_diaria = (Object[]) lst_acDiarias.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_diaria[1] + "</td>");
                        out.print("<td>" + obj_diaria[2] + "</td>");
                        out.print("<td align='center'>" + obj_diaria[3] + "</td>");
                        if (obj_diaria[4].toString().equals("N/A")) {
                            out.print("<td align='center'>" + obj_diaria[5] + "</td>");
                        } else if (obj_diaria[5].toString().equals("N/A")) {
                            out.print("<td align='center'>" + obj_diaria[4] + "</td>");
                        }
                        out.print("<td>" + obj_diaria[6] + "</td>");
                        out.print("<td>" + obj_diaria[7] + "</td>");
                        out.print("<td>" + obj_diaria[8] + "</td>");
                        out.print("<td>" + obj_diaria[9] + "</td>");
                        out.print("<td align='center'>");
                        int paradaPc = Integer.parseInt(obj_diaria[10].toString());
                        int paradaPr = Integer.parseInt(obj_diaria[11].toString());

                        if (paradaPc != 0 && paradaPr != 0) {
                            out.print("Equipo: <br>" + paradaPc + " Min <br>");
                            out.print("Produccion: <br>" + paradaPc + " Min");
                        } else if (paradaPc != 0) {
                            out.print("Equipo: <br>" + paradaPc + " Min");
                        } else if (paradaPr != 0) {
                            out.print("Produccion: <br>" + paradaPc + " Min");
                        } else {
                            out.print("0");
                        }
                        out.print("</td>");
                        out.print("<td>" + obj_diaria[14] + "</td>");
                        int stars = 0;
                        try {
                            stars = Integer.parseInt(obj_diaria[15].toString());
                        } catch (Exception e) {
                            stars = 0;
                        }
                        String star = "";
                        for (int j = 1; j <= stars; j++) {
                            star += "<i class='fas fa-star' style='color: orange;'></i>";
                        }
                        out.print("<td align='center'>" + ((obj_diaria[15] == null) ? "Sin <br> Calificacion" : star) + "</td>");
                        out.print("<td align='center'>" + ((obj_diaria[16] == null) ? "Sin <br> Calificacion" : obj_diaria[16]) + "</td>");
                        out.print("<td align='center'>");
                        String firma = "";
                        try {
                            firma = obj_diaria[12].toString();
                        } catch (Exception e) {
                            firma = "";
                        }
                        if (firma.equals("")) {
                            out.print("Sin firmar");
                        } else {
                            out.print("<b style='color: orange;'>FIRMADO</b>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                } else {
                    out.print("<b style='text-align: center;'>Este usuario no ha registrado actividades en registro 001</b>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                out.print("</div>");
                out.print("</form>");
                //<editor-fold defaultstate="collapsed" desc="actualizar fechas">
                out.print("<div class='modal fade' id='ActualizarF' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog modal-sm' style='width:35%;'>");
                out.print("<div class='modal-content'>");
                out.print("<form action='Bitacora?opc=2' name='formA' method='post'>");
                out.print("<div class='modal-header'>");
                out.print("<a href='#' class='close' data-dismiss='modal'>&times;</a>");
                out.print("<h4 class='modal-title'>Actualizar Bitacora</h4>");
                out.print("</div>");
                out.print("<div class='modal-body' align='center'>");
                out.print("<table style='width:90%;font-size:12px'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Fecha Inicio: </b><br>");
                out.print("<input type='text' class='form-control' name='txt_fechaI' id='start' value='" + fechaI.split(" ")[0] + "' autocomplete='off' placeholder='Fecha inicio' required>");
                out.print("</td>");
                out.print("<td>");
                out.print("<b>Hora Inicio: </b><br>");
                out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' value='" + fechaI.split(" ")[1] + "' placeholder='Hora Inicio' required>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Fecha Fin: </b><br>");
                out.print("<input type='text' class='form-control' name='txt_fechaF' id='end' value='" + fechaF.split(" ")[0] + "' autocomplete='off' placeholder='Fecha fin' required>");
                out.print("</td>");
                out.print("<td>");
                out.print("<b>Hora Fin: </b><br>");
                out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' value='" + fechaF.split(" ")[1] + "' placeholder='Hora Inicio' required>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                out.print("<div class='modal-footer'>");
                out.print("<input type='submit' value='Generar'>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
//</editor-fold>
            }
            if (modulo.equals("BC")) {
                //<editor-fold defaultstate="collapsed" desc="BITACORAS">
                List lst_usuarios = jpa_usuario.consultarUsuarios();
                out.print("<h3>Consultar bitacoras</h3>");
                out.print("<div style='height:93%; max-height:94%; overflow-y: auto;'>");
                out.print("<table class='table'>");
                out.print("<tr>");
                out.print("<th>Apellidos</th>");
                out.print("<th>Nombres</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Ver</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                    if (Integer.parseInt(obj_usuarios[9].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[7] + "</td>");
                        out.print("<td align='center'><a href='Bitacora?opc=1&mod=BCU&idU=" + obj_usuarios[0] + "' style='color:#292929'><i class='far fa-eye fa-2x'></i></a></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
            }
            if (modulo.equals("BCU")) {
                //<editor-fold defaultstate="collapsed" desc="bitacoras usuario">
                int id_usuarioB = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuarioB").toString());
                List lst_usuario = jpa_usuario.consultaUsuarioId(id_usuarioB);
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                out.print("<a href='Bitacora?opc=1&mod=BC'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Bitacoras " + obj_usuario[1] + " " + obj_usuario[2] + "</h3>");
                List lst_bitacoras = jpa_bitacora.consultaBitacorasUsuario(id_usuarioB, "2019-04-01 00:00:00", fecha_final);
                out.print("<div style='float: right;width:20%'><input type='text' class='form-control' class='form-control' onkeyup='Filtrar()' name='txt_bus' id='Txt_filtro' onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Buscar'></div>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<div style='height:90%; max-height:90%; overflow-y: auto;'>");
                if (lst_bitacoras != null) {
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4' >Bitácora</th>");
                    out.print("<th class='sticky4'>Turno</th>");
                    out.print("<th class='sticky4'>Solucionados</th>");
                    out.print("<th class='sticky4'>Pendientes</th>");
                    out.print("<th class='sticky4'>Ver</th>");
                    out.print("<th class='sticky4'>Revisar</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_bitacoras.size(); i++) {
                        Object[] obj_bitacoras = (Object[]) lst_bitacoras.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_bitacoras[1] + "</td>");
                        out.print("<td>" + obj_bitacoras[2] + "</td>");
                        if (obj_bitacoras[15].equals("Tecnico T.I")) {
                            out.print("<td><b class='title'>Actividades: </b>" + obj_bitacoras[5] + "<br />"
                                    + "<b class='title'>Actividades Reportadas: </b>" + obj_bitacoras[6] + "<br />"
                                    + "<b class='title'>Casos: </b>" + obj_bitacoras[7] + "<br />"
                                    + "<b class='title'>Pendientes: </b>" + obj_bitacoras[9] + "<br />"
                                    + "<b class='title'>Movimientos equipos : </b>" + obj_bitacoras[17] + "<br /></td>");
                            out.print("<td><b class='title'>Casos: </b>" + obj_bitacoras[8] + "<br />"
                                    + "<b class='title'>Pendientes: </b>" + obj_bitacoras[10] + "<br /></td>");
                        } else if (obj_bitacoras[15].equals("Programador")) {
                            out.print("<td><b class='title'>Actividades: </b>" + obj_bitacoras[5] + "<br />"
                                    + "<b class='title'>Actividades Reportadas: </b>" + obj_bitacoras[6] + "<br />"
                                    + "<b class='title'>Pendientes: </b>" + obj_bitacoras[9] + "<br /></td>");
                            out.print("<td><b class='title'>Pendientes: </b>" + obj_bitacoras[10] + "<br /></td>");
                        } else if (obj_bitacoras[15].equals("Asistente") || obj_bitacoras[15].equals("Aprendiz Sena")) {
                            out.print("<td><b class='title'>Actividades: </b>" + obj_bitacoras[5] + "<br />"
                                    + "<b class='title'>Pendientes: </b>" + obj_bitacoras[9] + "<br />"
                                    + "<b class='title'>Movimientos equipos: </b>" + obj_bitacoras[17] + "<br /></td>");
                            out.print("<td><b class='title'>Pendientes: </b>" + obj_bitacoras[10] + "<br /></td>");
                        }
                        out.print("<td align='center'><a href='Bitacora?opc=1&mod=BU&idB=" + obj_bitacoras[0] + "' style='color:#292929'><i class='far fa-eye fa-2x'></i></a></td>"
                                + "</form>");
                        if (id_rol == 2 || id_rol == 4) {
                            if (Integer.parseInt(obj_bitacoras[11].toString()) == 0) {
                                out.print("<td align='center'><a href='Bitacora?opc=4&idB=" + obj_bitacoras[0] + "&idU=" + id_usuarioB + "' ><i class='fa fa-check fa-lg' style='color: #ea7200;'></i></a></td>");
                            } else {
                                out.print("<td align='center'><i class='fa fa-check-double fa-lg'></i></td>");
                            }
                        } else if (Integer.parseInt(obj_bitacoras[11].toString()) == 0) {
                            out.print("<td align='center'><i class='fa fa-check fa-lg' style='color: #ea7200;'></i></td>");
                        } else {
                            out.print("<td align='center'><i class='fa fa-check-double fa-lg'></i></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',20);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                } else {
                    out.print("<br/><b>No se han encontrado resultados</b>");
                }
//</editor-fold>
            }
            if (modulo.equals("BU")) {
                //<editor-fold defaultstate="collapsed" desc="bitacoras generadas">
                int id_bitacora = Integer.parseInt(pageContext.getRequest().getAttribute("id_bitacora").toString());
                List lst_bitacora = jpa_bitacora.consultaBitacoraId(id_bitacora);
                Object[] obj_bitacora = (Object[]) lst_bitacora.get(0);
                List lst_usuario = jpa_usuario.consultaUsuarioId((Integer) obj_bitacora[13]);
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                out.print("<a href='Bitacora?opc=1&mod=BCU&idU=" + obj_bitacora[13] + "'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a><br><br>");
                out.print("<b class='title'>PLASTITEC </b><br>");
                if (Integer.parseInt(obj_bitacora[0].toString()) <= 16883) {
                    out.print("<b>R-TI-" + obj_bitacora[3].toString().split(" ")[0].replace("-", "") + " Bitacora de comunicacion interna " + obj_usuario[1] + " " + obj_usuario[2] + "</b><br>");
                } else {
                    out.print("<b>R-TI-" + obj_bitacora[3].toString().split(" ")[0].replace("-", "") + " Bitacora de comunicacion interna " + obj_usuario[1] + " " + obj_usuario[2] + "</b><br>");
                }
                out.print("<div style='height:90%; max-height:90%; overflow-y: auto;'>");
                out.print("<table class='table'>");
                out.print("<tr>");
                out.print("<td><b class='title'>Turno: </b>" + obj_bitacora[2] + "</td>");
                out.print("<td><b class='title'>Responsable: </b>" + obj_usuario[1] + " " + obj_usuario[2] + "</td>");
                out.print("<td><b class='title'>Area: </b>Tecnología de Información</td>");
                out.print("<td><b class='title'>Llegada: </b>" + obj_bitacora[3] + "<hr><b class='title'>Salida: </b>" + obj_bitacora[4] + "</td>");
                out.print("</tr>");
                out.print("</table><br>");
                out.print("<div class='panel-group' id='accordion'>");
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#actividades'>ACTIVIDADES</a></h4>");
                out.print("</div>");
                out.print("<div id='actividades' class='panel-collapse collapse in'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="actividades">
                List lst_actividades = jpa_bitacora.consultaActividadesBitacora((Integer) obj_bitacora[13], obj_bitacora[3].toString(), obj_bitacora[4].toString(), 1);
                if (lst_actividades != null) {
                    out.print("<input type='hidden' name='txt_actividades' id='txt_actividades' value='" + lst_actividades.size() + "' />");
                    out.println("<table class='table'>");
                    out.println("<tr>");
                    out.println("<th align='center' style='width:15%'>Fecha Registro</th>");
                    out.println("<th align='center' style='width:15%'>Asunto</th>");
                    out.println("<th align='center' style='width:70%'>Actividades</th>");
                    out.println("</tr>");
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                        out.println("<tr>");
                        out.println("<td align='center'>" + obj_actividades[4] + "</td>");
                        out.println("<td>" + obj_actividades[1] + "</td>");
                        out.println("<td valign='top'>" + obj_actividades[2] + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                } else {
                    out.print("<input type='hidden' name='txt_actividades' id='txt_actividades' value='0' />");
                    out.print("<b>No se encontraron resultados</b>");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="actividades reportadas">
                List lst_actividadesR = jpa_bitacora.consultaActividadesReportadasBitacora((Integer) obj_bitacora[13], obj_bitacora[3].toString(), obj_bitacora[4].toString(), 1);
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#actividadesR'>ACTIVIDADES REPORTADAS</a></h4>");
                out.print("<span class='label pull-right label-info'>" + ((lst_actividadesR != null) ? lst_actividadesR.size() : 0) + "</span>");
                out.print("</div>");
                out.print("<div id='actividadesR' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                if (lst_actividadesR != null) {
                    out.print("<input type='hidden' name='txt_actividadesR' id='txt_actividadesR' value='" + lst_actividadesR.size() + "' />");
                    out.print("<table class='table' id='resultados'>");
                    for (int i = 0; i < lst_actividadesR.size(); i++) {
                        Object[] obj_actividadesR = (Object[]) lst_actividadesR.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='5' style='background-color: #ddd;'></d>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:20%'><b class='title'>Fecha: </b>" + obj_actividadesR[15] + "</td>");
                        out.print("<td colspan='2' style='width:25%'><b class='title'>Reportante: </b>" + obj_actividadesR[1] + "</td>");
                        out.print("<td style='width:25%'><b class='title'>Area: </b>" + ((obj_actividadesR[20] == null) ? "SIN ASIGNAR" : obj_actividadesR[20]) + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:23%'><b class='title'>Tipo Soporte: </b>" + obj_actividadesR[6] + "</td>");
                        if (id_rol == 5) {
                            out.print("<td style='width:18%'><b class='title'>Aplicativo: </b>" + obj_actividadesR[8] + "</td>");
                        } else {
                            out.print("<td style='width:18%'><b class='title'>PC: </b>" + obj_actividadesR[3] + "</td>");
                            out.print("<td style='width:18%'><b class='title'>Equipo: </b>" + ((obj_actividadesR[18] == null) ? "N/A" : obj_actividadesR[18]) + "</td>");
                        }
                        out.print("<td align='top'><b>Parada Equipo: " + obj_actividadesR[16] + "<br>Produccion: " + obj_actividadesR[17] + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' valign='top'>" + obj_actividadesR[12] + "</td>");
                        out.print("<td colspan='2' valign='top'>" + obj_actividadesR[13] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='title'>Fecha Reportante: </b>" + obj_actividadesR[9] + "</td>");
                        out.print("<td colspan='2'><b class='title'>Fecha Ejecucion: </b>" + obj_actividadesR[10] + "</td>");
                        out.print("<td><b class='title'>Fecha Fin: </b>" + obj_actividadesR[11] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.print("<input type='hidden' name='txt_actividadesR' id='txt_actividadesR' value='0' />");
                    out.print("<b>No se encontraron resultados</b>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Casos">
                List lst_casos = jpa_bitacora.consultaCasosBitacora((Integer) obj_bitacora[13], obj_bitacora[3].toString(), obj_bitacora[4].toString(), 1);
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#casos'>CASOS</a></h4>");
                out.print("<span class='label pull-right label-info'>" + ((lst_casos != null) ? lst_casos.size() : 0) + "</span>");
                out.print("</div>");
                out.print("<div id='casos' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                if (lst_casos != null) {
                    out.print("<input type='hidden' name='txt_casos' id='txt_casos' value='" + lst_casos.size() + "' />");
                    out.print("<table class='table' id='resultados'>");
                    for (int i = 0; i < lst_casos.size(); i++) {
                        Object[] obj_casos = (Object[]) lst_casos.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_casos[10] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_casos[9] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.print("<b>No se encontraron resultados</b>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                List lst_pendientesS = jpa_bitacora.consultaPendientesSolucionadosBitacora((Integer) obj_bitacora[13], obj_bitacora[3].toString(), obj_bitacora[4].toString(), 1);
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#pendientesS'>PENDIENTES SOLUCIONADOS</a></h4>");
                out.print("<span class='label pull-right label-info'>" + ((lst_pendientesS != null) ? lst_pendientesS.size() : 0) + "</span>");
                out.print("</div>");
                out.print("<div id='pendientesS' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="Pendientes Solucionados">
                if (lst_pendientesS != null) {
                    out.println("<input type='hidden' name='txt_pendientesS' id='txt_pendientesS' value='" + lst_pendientesS.size() + "' />");
                    out.print("<table class='table'>");
                    for (int i = 0; i < lst_pendientesS.size(); i++) {
                        Object[] obj_pendiente = (Object[]) lst_pendientesS.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_pendiente[4] + "<hr/><b class='title'>Asunto: </b>" + obj_pendiente[13] + "</td>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Pendiente: </b>" + obj_pendiente[1] + "</td>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_pendiente[9] + "<hr /><b class='title'>Para: </b>" + obj_pendiente[10] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_pendiente[2] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.println("<input type='hidden' name='txt_pendientesS' id='txt_pendientesS' value='0' />");
                    out.print("<b>No se encontraron resultados</b>");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //<editor-fold defaultstate="collapsed" desc="Movimientos Equipos">
                if (id_rol == 3 || id_rol == 4) {
                    List lst_movimientos = jpa_bitacora.consultaMovimientosEquiposBitacora(obj_usuario[1] + " " + obj_usuario[2], obj_bitacora[3].toString(), obj_bitacora[4].toString());
                    out.print("<div class='panel panel-default'>");
                    out.print("<div class='panel-heading'>");
                    out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#movimientos'>MOVIMIENTOS EQUIPOS</a></h4>");
                    out.print("<span class='label pull-right label-info'>" + ((lst_movimientos != null) ? lst_movimientos.size() : 0) + "</span>");
                    out.print("</div>");
                    out.print("<div id='movimientos' class='panel-collapse collapse'>");
                    out.print("<div class='panel-body'>");
                    if (lst_movimientos != null) {
                        out.print("<input type='hidden' name='txt_movimientos' id='txt_movimientos' value='" + lst_movimientos.size() + "' />");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th style='width:10%'>Equipo</th>");
                        out.print("<th style='width:10%'>Estado</th>");
                        out.print("<th style='width:20%'>Responsable</th>");
                        out.print("<th style='width:10%'>Tipo</th>");
                        out.print("<th style='width:40%'>Observaciones/Area</th>");
                        out.print("<th style='width:10%'>Fecha</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_movimientos.size(); i++) {
                            Object[] obj_movimientos = (Object[]) lst_movimientos.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_movimientos[1] + "</td>");
                            out.print("<td align='center'>");
                            if (obj_movimientos[7].equals("B")) {
                                out.print("<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #51cf66;'></i>");
                            } else if (obj_movimientos[7].equals("R")) {
                                out.print("<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #ff922b;'></i>&nbsp;<i class='far fa-circle'></i>");
                            } else {
                                out.print("<i class='fa fa-circle' style='color: #ff6b6b;'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>");
                            }
                            out.print("</td>");
                            out.print("<td>" + obj_movimientos[2] + "</td>");
                            out.print("<td>" + obj_movimientos[3] + "</td>");
                            out.print("<td>" + obj_movimientos[8] + "<hr>" + obj_movimientos[5] + "&nbsp;|&nbsp;<b class='title'>Cargo: </b>" + obj_movimientos[6] + "</td>");
                            out.print("<td align='center'>" + obj_movimientos[11] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    } else {
                        out.print("<input type='hidden' name='txt_movimientos' id='txt_movimientos' value='0' />");
                        out.print("<b>No se encontraron resultados</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES DIARIAS">
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#acDairias'>ACTIVIDADES DIARIAS (R-TI-001)</a></h4>");
                List lst_acDiarias = jpa_bitacora.consultaActividadesdiariasBitacora((Integer) obj_bitacora[13], obj_bitacora[3].toString(), obj_bitacora[4].toString());
                out.print("<span class='label pull-right label-info'>" + ((lst_acDiarias == null) ? 0 : lst_acDiarias.size()) + "</span>");
                out.print("</div>");
                out.print("<div id='acDairias' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                out.print("<input type='hidden' name='txt_AcDiarias' id='txt_AcDiarias' value='" + ((lst_acDiarias == null) ? 0 : lst_acDiarias.size()) + "'>");
                if (lst_acDiarias != null) {
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Fecha Solicitud</th>");
                    out.print("<th>Usuario</th>");
                    out.print("<th>Fecha Ejecucion</th>");
                    out.print("<th>Nro equipo</th>");
                    out.print("<th>Asunto</th>");
                    out.print("<th>Solucion</th>");
                    out.print("<th>Ejecuto</th>");
                    out.print("<th>Fecha Ejecucion</th>");
                    out.print("<th>Parada?</th>");
                    out.print("<th>Tipo Soporte</th>");
                    out.print("<th>Calificacion</th>");
                    out.print("<th>Opinion</th>");
                    out.print("<th>Firma</th>");
                    out.print("</tr>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_acDiarias.size(); i++) {
                        Object[] obj_diaria = (Object[]) lst_acDiarias.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_diaria[1] + "</td>");
                        out.print("<td>" + obj_diaria[2] + "</td>");
                        out.print("<td align='center'>" + obj_diaria[3] + "</td>");
                        if (obj_diaria[4].toString().equals("N/A")) {
                            out.print("<td align='center'>" + obj_diaria[5] + "</td>");
                        } else if (obj_diaria[5].toString().equals("N/A")) {
                            out.print("<td align='center'>" + obj_diaria[4] + "</td>");
                        }
                        out.print("<td>" + obj_diaria[6] + "</td>");
                        out.print("<td>" + obj_diaria[7] + "</td>");
                        out.print("<td>" + obj_diaria[8] + "</td>");
                        out.print("<td>" + obj_diaria[9] + "</td>");
                        out.print("<td align='center'>");
                        int paradaPc = Integer.parseInt(obj_diaria[10].toString());
                        int paradaPr = Integer.parseInt(obj_diaria[11].toString());

                        if (paradaPc != 0 && paradaPr != 0) {
                            out.print("Equipo: <br>" + paradaPc + " Min <br>");
                            out.print("Produccion: <br>" + paradaPc + " Min");
                        } else if (paradaPc != 0) {
                            out.print("Equipo: <br>" + paradaPc + " Min");
                        } else if (paradaPr != 0) {
                            out.print("Produccion: <br>" + paradaPc + " Min");
                        } else {
                            out.print("0");
                        }
                        out.print("</td>");
                        out.print("<td>" + obj_diaria[14] + "</td>");
                        int stars = 0;
                        try {
                            stars = Integer.parseInt(obj_diaria[15].toString());
                        } catch (Exception e) {
                            stars = 0;
                        }
                        String star = "";
                        for (int j = 1; j <= stars; j++) {
                            star += "<i class='fas fa-star' style='color: orange;'></i>";
                        }
                        out.print("<td align='center'>" + ((obj_diaria[15] == null) ? "Sin <br> Calificacion" : star) + "</td>");
                        out.print("<td align='center'>" + ((obj_diaria[16] == null) ? "Sin <br> Calificacion" : obj_diaria[16]) + "</td>");
                        out.print("<td align='center'>");
                        String firma = "";
                        try {
                            firma = obj_diaria[12].toString();
                        } catch (Exception e) {
                            firma = "";
                        }
                        if (firma.equals("")) {
                            out.print("Sin firmar");
                        } else {
                            out.print("<b style='color: orange;'>FIRMADO</b>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                } else {
                    out.print("<b style='text-align: center;'>Este usuario no ha registrado actividades en registro 001</b>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_bitacoras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
