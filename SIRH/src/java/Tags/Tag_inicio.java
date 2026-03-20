package Tags;

import Controladores_BD.CalendarioJpaController;
import Controladores_BD.MenuJpaController;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores_BD.ParametrosJpa;
import GLPI.GLPISession;

import Metodos.Control_correo;

public class Tag_inicio extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        Control_correo EnviarCorreo = new Control_correo();

        try {
            //JPAS
            MenuJpaController jpacmnu = new MenuJpaController();
            CalendarioJpaController jpaccld = new CalendarioJpaController();
            ParametrosJpa ParamJpa = new ParametrosJpa();
            //VARIABLES GLOBALES
            //FECHA
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                mes = "0" + (cal.get(Calendar.MONTH) + 1);
            } else {
                mes = (cal.get(Calendar.MONTH) + 1) + "";
            }
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            int formulario = 0;
            List lst_calendario = null;
            List lst_opciones_permisos = null;
            String permisos = "";
            List lst_parametros = null;
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int id_opcion_menu = 0;
            if (pageContext.getRequest().getAttribute("Inicio") != null) {
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
                //<editor-fold defaultstate="collapsed" desc="SIRH">
                if (pageContext.getRequest().getAttribute("Inicio").equals("SIRH")) {
                    out.print("<div id='content_sin'>");
                    out.print("<center><div style='margin-top:30px'>");
                    out.print("<span class='fab fa-hornbill fa-size_big'></span><br />");
                    out.print("<b style='font-size:50px'>SIRH</b><br />");
                    out.print("<p style='font-size:20px;margin-top:-15px'>Sistema de Información Recursos Humanos</p><br />");
                    out.print("<i style='font-size:16px;font-color:#596275;text-align:justify;'>");
                    out.print("<b>Este es un proyecto de reingenieria de software en colaboración de Automatización, Recursos Humanos y Sistemas, donde se logra conservar la funcionalidad e información de AIRH.</b><br /><br />"
                            + "La aplicación SIRH (Sistema de información recursos humanos) centraliza toda la información relacionada con el personal de la empresa Directo o Temporal en cuanto a "
                            + "Datos personales, Firma electronica, Accidentes de trabajo, Enfermedades profesionales, Incapacidades, Ausencias, Eventos disciplinación, Retiros, "
                            + "Asignación de Dotación, Capacitaciones, Examenes medicos, EPP, Calificación de competencias. "
                            + "Con reportes que permiten ver datos historicos y estado actual de la información del personal, apoyando de la mejor manera con seguridad, integridad, disponibilidad de la información "
                            + "del personal al equipo de recursos humanos de Plastitec.");
                    out.print("</i><br /><br />");
                    out.print("<span class='fa fa-user-tie' style='font-size:4em;color:#ddd;'></span>&nbsp;&nbsp;&nbsp;"
                            + "<span class='fa fa-hands-helping' style='font-size:3em;color:#ddd;'></span>&nbsp;&nbsp;&nbsp;"
                            + "<span class='fa fa-user-friends' style='font-size:4em;color:#ddd;'></span>");
                    out.print("</div></center>");
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CALENDARIO">
                else if (pageContext.getRequest().getAttribute("Inicio").equals("Calendario")) {
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Inicio?opc=2&mnu=0&fml=1'><span class='fa fa-calendar fa-size_super_small'></span></a>");
                    }
                    out.print(" Calendario</h3>");
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                    if (formulario == 1) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position:absolute;top:10%;left:30%;'>");
                        out.print("<div style='float:right;'><a href='Inicio?opc=2&mnu=5&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Programar Actividad</h3>");
                        out.print("<form action='Inicio?opc=3' method='post'>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td><b>Fecha inicio :</b><br /><input type='text' name='Txt_fecha_inicio' id='start' placeholder='Fecha inicio' autocomplete='off' required /></td>");
                        out.print("<td><b>Hora inicio :</b><br /><input type='time' name='Txt_hora_inicio' id='Txt_hora_inicio' style='height:28px' /></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>Fecha fin :</b><br /><input type='text' name='Txt_fecha_fin' id='end' placeholder='Fecha fin' autocomplete='off' required /></td>");
                        out.print("<td><b>Hora fin :</b><br /><input type='time' name='Txt_hora_fin' id='Txt_hora_fin' style='height:28px' /></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2'><b>Actividad :</b><br /><input type='text' name='Txt_actividad' id='Txt_actividad' style='width:100%' placeholder='Actividad' required /></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2'><b>Descripción :</b><textarea name='Txt_descripcion' id='Txt_descripcion' style='width:100%;height:100px' required placeholder='Descripción'></textarea></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b>Color :</b><input type='text' id='Txt_color' name='Txt_color' style='color:#fff;border:none;' readonly required  />"
                                + "<div style=\"display: block;height: 90px;width:170px\">\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#607D8B';document.getElementById('Txt_color').style.backgroundColor='#607D8B';\" style=\"border:1px solid #fff;float:left;background-color: #607D8B;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#05c46b';document.getElementById('Txt_color').style.backgroundColor='#05c46b';\" style=\"border:1px solid #fff;float:left;background-color: #05c46b;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#99b433';document.getElementById('Txt_color').style.backgroundColor='#99b433';\" style=\"border:1px solid #fff;float:left;background-color: #99b433;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#00a300';document.getElementById('Txt_color').style.backgroundColor='#00a300';\" style=\"border:1px solid #fff;float:left;background-color: #00a300;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#1e7145';document.getElementById('Txt_color').style.backgroundColor='#1e7145';\" style=\"border:1px solid #fff;float:left;background-color: #1e7145;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#E91E63';document.getElementById('Txt_color').style.backgroundColor='#E91E63';\" style=\"border:1px solid #fff;float:left;background-color: #E91E63;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#B53471';document.getElementById('Txt_color').style.backgroundColor='#B53471';\" style=\"border:1px solid #fff;float:left;background-color: #B53471;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#553787';document.getElementById('Txt_color').style.backgroundColor='#553787';\" style=\"border:1px solid #fff;float:left;background-color: #553787;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#00aba9';document.getElementById('Txt_color').style.backgroundColor='#00aba9';\" style=\"border:1px solid #fff;float:left;background-color: #00aba9;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#17a2b8';document.getElementById('Txt_color').style.backgroundColor='#17a2b8';\" style=\"border:1px solid #fff;float:left;background-color: #17a2b8;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#2b5797';document.getElementById('Txt_color').style.backgroundColor='#2b5797';\" style=\"border:1px solid #fff;float:left;background-color: #2b5797;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#ffc40d';document.getElementById('Txt_color').style.backgroundColor='#ffc40d';\" style=\"border:1px solid #fff;float:left;background-color: #ffc40d;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#e3a21a';document.getElementById('Txt_color').style.backgroundColor='#e3a21a';\" style=\"border:1px solid #fff;float:left;background-color: #e3a21a;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#da532c';document.getElementById('Txt_color').style.backgroundColor='#da532c';\" style=\"border:1px solid #fff;float:left;background-color: #da532c;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#ef5350';document.getElementById('Txt_color').style.backgroundColor='#ef5350';\" style=\"border:1px solid #fff;float:left;background-color: #ef5350;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#e52d27';document.getElementById('Txt_color').style.backgroundColor='#e52d27';\" style=\"border:1px solid #fff;float:left;background-color: #e52d27;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#b91d47';document.getElementById('Txt_color').style.backgroundColor='#b91d47';\" style=\"border:1px solid #fff;float:left;background-color: #b91d47;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "<div onclick=\"javascript:document.getElementById('Txt_color').value='#795548';document.getElementById('Txt_color').style.backgroundColor='#795548';\" style=\"border:1px solid #fff;float:left;background-color: #795548;border-radius:50px;height:25px;width:25px;\"></div>\n"
                                + "</div>"
                                + "</td>");
                        out.print("<td><input type='submit' value='Programar' /></td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CALENDARIO">
                    out.print("<link href='Interfaz/Full_calendar/packages/core/main.css' rel='stylesheet' />");
                    out.print("<link href='Interfaz/Full_calendar/packages/daygrid/main.css' rel='stylesheet' />");
                    out.print("<link href='Interfaz/Full_calendar/packages/timegrid/main.css' rel='stylesheet' />");
                    out.print("<link href='Interfaz/Full_calendar/packages/list/main.css' rel='stylesheet' />");
                    out.print("<script src='Interfaz/Full_calendar/packages/core/main.js'></script>");
                    out.print("<script src='Interfaz/Full_calendar/packages/interaction/main.js'></script>");
                    out.print("<script src='Interfaz/Full_calendar/packages/daygrid/main.js'></script>");
                    out.print("<script src='Interfaz/Full_calendar/packages/timegrid/main.js'></script>");
                    out.print("<script src='Interfaz/Full_calendar/packages/list/main.js'></script>");
                    out.print("<script>");
                    out.print("document.addEventListener('DOMContentLoaded', function () {");
                    out.print("var initialLocaleCode = 'es';");
                    out.print("var calendarEl = document.getElementById('calendar');");
                    out.print("var calendar = new FullCalendar.Calendar(calendarEl, {");
                    out.print("plugins: ['interaction', 'dayGrid', 'timeGrid', 'list'],");
                    out.print("header: {");
                    out.print("left: 'prev,next today',");
                    out.print("center: 'title',");
                    // out.print("right: 'dayGridMonth,listMonth'");
                    out.print("right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'");
                    out.print("},");
                    out.print("locale: initialLocaleCode,");
                    out.print("defaultDate: '" + anio + "-" + mes + "-" + dia + "',");
                    out.print("navLinks: true,");
                    out.print("businessHours: true,");
                    out.print("weekNumbers: true,");
                    out.print("editable: false,");
                    out.print("events: [");
                    lst_calendario = jpaccld.Consultar_calendario();
                    //<editor-fold defaultstate="collapsed" desc="DATOS">
                    if (lst_calendario != null) {
                        for (int i = 0; i < lst_calendario.size(); i++) {
                            Object[] obj_calendario = (Object[]) lst_calendario.get(i);
                            if (i == 0) {
                                out.print("{");
                                out.print("title: '" + obj_calendario[1] + "',");
                                out.print("start: '" + obj_calendario[2].toString().replace(" ", "T") + "',");
                                out.print("end: '" + obj_calendario[3].toString().replace(" ", "T") + "',");
                                out.print("color: '" + obj_calendario[4] + "',");
                                out.print("url: \"javascript:swal('" + obj_calendario[1] + "','" + obj_calendario[5] + ".','info');\"");
                                out.print("}");
                            } else {
                                out.print(",{");
                                out.print("title: '" + obj_calendario[1] + "',");
                                out.print("start: '" + obj_calendario[2].toString().replace(" ", "T") + "',");
                                out.print("end: '" + obj_calendario[3].toString().replace(" ", "T") + "',");
                                out.print("color: '" + obj_calendario[4] + "',");
                                out.print("url: \"javascript:swal('" + obj_calendario[1] + "','" + obj_calendario[5] + ".','info');\"");
                                out.print("}");
                            }
                        }
                    }
//</editor-fold>
                    out.print("]");
                    out.print("});");
                    out.print("calendar.render();");
                    out.print("});");
                    out.print("</script>");
                    out.print("<center><div id='calendar' style='text-transform: capitalize;max-width: 900px;z-index:0;position: relative;'></div></center>");
                    //</editor-fold>
                    out.print("</div>");
                }//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ENVIO DE CORREOS JEFES">
                String validFecha = anio + "-" + mes + "-" + dia;
                lst_parametros = ParamJpa.ConsultarParametrosxCategoria("EnvioCorreoJefes");
                String correoval = "";
                if (lst_parametros != null) {
                    Object[] obj_correo = (Object[]) lst_parametros.get(0);
                    correoval = obj_correo[2].toString();
                }
                validFecha = anio + "-" + mes + "-01";
                if (!correoval.equals(validFecha)) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:'Atencion!',"
                            + "text:'Recuerde que debe calificar al personal a cargo.<br> <b>Competencias</b> -> <b>Calificar competencias.</b>',"
                            + "type:'warning',"
                            + "html: true"
                            + "});");
                    out.print("</script>");
                    try {
                        EnviarCorreo.RecordatorioCalificaciones();
                        ParamJpa.CambiarFechaCorreo(validFecha);
                    } catch (Exception e) {
                        out.print("error");
                    }
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="TEST CONECCTION GLPI">
//                GLPISession session = new GLPISession();
//                String token = session.initSession();
//                System.out.println("Session token: " + token);
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
