/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import Controladores.EtapaJpaController;
import Controladores.ProyectoJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String var = sesion.getAttribute("Usuario_cargo").toString();
        String user = sesion.getAttribute("Usuario").toString();
        int position_user = Integer.parseInt(sesion.getAttribute("id_position").toString());
        int id_usuario = Integer.parseInt(sesion.getAttribute("Id_usuario").toString());
        LocalDate hoy = LocalDate.now();
        String fecha = hoy.toString();
        ProyectoJpaController jpa_proyecto = new ProyectoJpaController();
        List lst_ult_proyectos = null, lst_norma = null, lst_usuarios = null, lst_calendar_pyt = null;
        boolean isFirstItem = false, ListFirstItem = false;
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");

            out.print("<div class='row'>");
            out.print("<div class='col'>");
            out.print("<div class='text-center'>");
            out.print("<h2 class='font-italic'>");
            out.print("<span class='font-weight-normal' style='color: #666666;'>Bienvenido(a)</span>");
            out.print("</h2>");
            out.print("<h2 class='font-italic'>");
            out.print("<span class='text-uppercase' style='color: #0066a6;'> " + var + "</span>");
            out.print("</h2>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<br>");

            out.print("<div class='row'>");

            //<editor-fold defaultstate="collapsed" desc="CALENDARIO">
            out.print("<div class='col'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header'>");
            out.print("<h4>Calendario</h4>");
            out.print("</div>");
            out.print("<div class='card-body'>");

            out.print("<div id='calendar'></div>");

            out.print("<script>\n"
                    + "document.addEventListener('DOMContentLoaded', function() {\n"
                    + "var calendarEl = document.getElementById('calendar');\n"
                    + "var calendar = new FullCalendar.Calendar(calendarEl, {\n"
                    + "headerToolbar: {\n"
                    + "left: 'prev,next,today',\n"
                    + "center: 'title',\n"
                    + "right: 'multiMonthYear,dayGridMonth,timeGridWeek,timeGridDay'\n"
                    + "},\n"
                    + "locale: 'es',\n"
                    + "initialView: 'dayGridMonth',\n"
                    + "initialDate: '" + fecha + "',\n"
                    + "editable: true,\n"
                    + "selectable: false,\n"
                    + "dayMaxEvents: true,\n"
                    + "events: [\n");
            lst_calendar_pyt = jpa_proyecto.Traer_info_calendario();
            for (int c = 0; c < lst_calendar_pyt.size(); c++) {
                Object[] obj_calendario = (Object[]) lst_calendar_pyt.get(c);
                out.print("{\n");
                out.print("title: '" + obj_calendario[3] + " - "+obj_calendario[4]+"',\n"); // Usando <br> para HTML
                out.print("start: '" + obj_calendario[1] + "',\n");
                out.print("url: 'Proyecto?opc=7&ipy=" + obj_calendario[0] + "&estadoM=" + obj_calendario[5] + "'\n");
                if (c == lst_calendar_pyt.size() - 1) { // Cambiado para que la condición sea correcta
                    out.print("}");
                } else {
                    out.print("},");
                }
            }

            out.print("]\n"
                    + "});\n"
                    + "calendar.render();\n"
                    + "});\n"
                    + "</script>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="ULTIMOS CUATRO PROYECTOS">
            out.print("<div class='col'>");
            out.print("<div class='card' style='height: 96% !important;'>");
            out.print("<div class='card-header'>");
            out.print("<h4>Ultimos 4 proyectos</h4>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div id='carouselExampleIndicators2' class='carousel slide' data-ride='carousel'>");
            out.print("<ol class='carousel-indicators'>");
            ListFirstItem = true;
            lst_ult_proyectos = jpa_proyecto.Traer_ultimos_cuatro_proyectos();
            if (lst_ult_proyectos != null) {
                for (int i = 0; i < lst_ult_proyectos.size(); i++) {
                    out.print("<li data-target='#carouselExampleIndicators2' data-slide-to='" + i + "' " + (ListFirstItem ? "class='active'" : "") + "></li>");
                    ListFirstItem = false;
                }
            }
            out.print("</ol>");
            out.print("<div class='carousel-inner'>");
            isFirstItem = true;
            if (lst_ult_proyectos != null) {
                for (int inp = 0; inp < lst_ult_proyectos.size(); inp++) {
                    Object[] obj_lst_ults_proyectos = (Object[]) lst_ult_proyectos.get(inp);
                    out.print("<div class='carousel-item " + (isFirstItem ? "active" : "") + "'>");
                    out.print("<div class='carousel-caption d-none d-md-block text-dark'>");
                    out.print("<table class='table-bordered table-striped ' id='commentTable' style='width: 100%;'>");
                    out.print("<tbody>");
                    out.print("<tr class='thead' " + ((Integer.parseInt(obj_lst_ults_proyectos[10].toString()) == 0) ? "" : "style='background-color:#bbecc3c9 !important;'") + ">");
                    out.print("<td class='text-center'>");
                    lst_norma = jpa_proyecto.Traer_etapa(Integer.parseInt(obj_lst_ults_proyectos[0].toString()));
                    if (lst_norma.size() > 0) {
                        Object[] obj_lst_etapa = (Object[]) lst_norma.get(0);
                        out.print("<b>" + obj_lst_etapa[5] + "</b>");
                    }
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>FECHA: </b> " + obj_lst_ults_proyectos[3] + "");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>ESTADO: </b>");
                    if (obj_lst_ults_proyectos[4].equals("PROCESO")) {
                        out.print("<span class='badge badge-warning'>" + obj_lst_ults_proyectos[4] + "</span>");
                    } else if (obj_lst_ults_proyectos[4].equals("TERMINADO")) {
                        out.print("<span class='badge badge-success'>" + obj_lst_ults_proyectos[4] + "</span>");
                    } else if (obj_lst_ults_proyectos[4].equals("REVISION")) {
                        out.print("<span class='badge badge-primary'>" + obj_lst_ults_proyectos[4] + "</span>");
                    } else if (obj_lst_ults_proyectos[4].equals("FINALIZADO")) {
                        out.print("<span class='badge badge-dark'>" + obj_lst_ults_proyectos[4] + "</span>");
                    }
                    out.print("</td>");
                    if (obj_lst_ults_proyectos[8].toString().contains("[" + id_usuario + "]") || obj_lst_ults_proyectos[2].toString().contains(user) || position_user == 6) {
                        out.print("<td class='text-center'>");
                        out.print("<a href='Proyecto?opc=7&ipy=" + obj_lst_ults_proyectos[0] + "&estadoM=" + obj_lst_ults_proyectos[7] + "' class='btn btn-secondary' style='color:black;' data-toggle='tooltip' data-placement='top' title='Ver detalle'>Ver detalle &gt;&gt;</a>");
                        out.print("</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td " + ((obj_lst_ults_proyectos[13] != null) ? "rowspan='5'" : "rowspan='4'") + " class='text-center bg-white'>");
                    out.print(obj_lst_ults_proyectos[5]);
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='text-uppercase'>");
                    out.print("<b>PROYECTO: </b> " + obj_lst_ults_proyectos[6] + "");
                    out.print("</td>");
                    if (obj_lst_ults_proyectos[8].toString().contains("[" + id_usuario + "]") || obj_lst_ults_proyectos[2].toString().contains(user) || position_user == 6) {
                        out.print("<td colspan='2'>");
                    } else {
                        out.print("<td>");
                    }
                    out.print("<b>RESPONSABLE: </b> " + obj_lst_ults_proyectos[2] + "");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>ELEMENTOS DE ENTRADA: </b> " + obj_lst_ults_proyectos[11].toString().replace(";", "-") + "");
                    out.print("</td>");
                    if (obj_lst_ults_proyectos[8].toString().contains("[" + id_usuario + "]") || obj_lst_ults_proyectos[2].toString().contains(user) || position_user == 6) {
                        out.print("<td colspan='2' class='text-uppercase'>");
                    } else {
                        out.print("<td class='text-uppercase'>");
                    }
                    out.print("<b>USO PREVISTO: </b> " + obj_lst_ults_proyectos[9] + "");
                    out.print("</td>");
                    out.print("</tr>");
                    if (obj_lst_ults_proyectos[13] != null) {
                        out.print("<tr>");
                        if (obj_lst_ults_proyectos[8].toString().contains("[" + id_usuario + "]") || obj_lst_ults_proyectos[2].toString().contains(user) || position_user == 6) {
                            out.print("<td colspan='3'>");
                        } else {
                            out.print("<td colspan='2'>");
                        }
                        out.print("<b>RESPONSABLE REVISION</b>: " + obj_lst_ults_proyectos[13] + "");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    if (obj_lst_ults_proyectos[8].toString().contains("[" + id_usuario + "]") || obj_lst_ults_proyectos[2].toString().contains(user) || position_user == 6) {
                        out.print("<td colspan='3'>");
                    } else {
                        out.print("<td colspan='2'>");
                    }
                    out.print("<div class='text-center' data-toggle='tooltip' data-html='true' title='");

                    try {
                        if (obj_lst_ults_proyectos[8] != null) {
                            String arr[] = obj_lst_ults_proyectos[8].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            StringBuilder tooltipContent = new StringBuilder(); // Usamos StringBuilder para una concatenación eficiente
                            for (int q = 0; q < arr.length; q++) {
                                String cadena = arr[q];
                                lst_usuarios = jpa_proyecto.Traer_usuario(Integer.parseInt(cadena));
                                if (!lst_usuarios.isEmpty()) {
                                    Object[] obj_l_u = (Object[]) lst_usuarios.get(0);
                                    tooltipContent.append(obj_l_u[3]).append(" ").append(obj_l_u[4])
                                            .append("<b> / ").append(obj_l_u[12]).append("</b><br />");
                                }
                            }
                            if (tooltipContent.length() == 0) {
                                tooltipContent.append("<b>No se han asignado responsables en el proyecto.</b>");
                            }
                            out.print(tooltipContent.toString());
                        } else {
                            out.print("<b>No se han asignado responsables en el proyecto.</b>");
                        }
                    } catch (Exception e) {
                        out.print("<b>No se han asignado responsables en el proyecto.</b>");
                    }

                    out.print("'>");
                    out.print("LISTADO DISTRIBUCI&Oacute;N");
                    out.print("</div>");
                    out.print("</td>");
                    ;
                    out.print("</tr>");
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<img class='d-block' style='width: 74%;height: 555px;' alt='unicolor' src='Interfaz/Contenido/Img/Unicolor.png'>");
                    out.print("</div>");

                    isFirstItem = false;
                }
            }
            out.print("</div>");
            out.print("<a class='carousel-control-prev' href='#carouselExampleIndicators2' role = 'button' data-slide='prev'>");
            out.print("<i class='fas fa-angle-left fa-lg' style='color: #000000;font-size: 30px;'></i>");
            out.print("<a>");
            out.print("<a class='carousel-control-next' href='#carouselExampleIndicators2' role = 'button' data-slide='next'>");
            out.print("<i class='fas fa-angle-right fa-lg' style='color: #000000;font-size: 30px;'></i>");
            out.print("<a>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            out.print("</div>");

            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(TagSupport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
