package Tags;

import Controladores.EquipoJpaController;
import Controladores.HistorialHorometroJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.UsuarioJpaController;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_horometro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            EquipoJpaController jpaceqp = new EquipoJpaController();
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            HistorialHorometroJpaController jpachhr = new HistorialHorometroJpaController();
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
            List lst_equipos = null;
            String fecha = "";
            String posicion = "";
            List lst_actualizacion_horometros = null;
            List lst_horometros = null;
            List lst_anios = null;
            if (pageContext.getRequest().getAttribute("Horometros") != null) {
                //<editor-fold defaultstate="collapsed" desc="PROGRAMAR HORAMETROS">
                if (pageContext.getRequest().getAttribute("Horometros").toString().equals("Programar_horometros")) {
                    out.print("<div id='content_sin'>");
                    if (rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                            out.print("<form action='Horometro?opc=2' method='post' name='f1' id='f1'>");
                            out.print("<h3>Programar Equipos Actualización Horometros <br /><input type='text' id='datepicker' name='Txt_fecha' placeholder='Fecha de actividad' required/><input type='submit' value='Programar' /></h3>");
                            out.print("<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");
                        } else {
                            out.print("<h3>Programar Equipos Actualización Horometros</h3>");
                        }
                        lst_equipos = jpaceqp.Equipos_historial();
                        out.print("<input type='hidden' name='Cantidad_equipos' value='" + lst_equipos.size() + "' />");
                        out.print("<div align='right' style='margin:0px;width:200px;float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar Equipos' onchange='javascript:this.value=this.value.toUpperCase();'/></div>");
                        out.print("<a href='javascript:seleccionar_todo()'>Marcar todos</a> | <a href='javascript:deseleccionar_todo()'>ninguno</a>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table align='center' class='table' id='resultados' style='width:100%;'>");
                        out.print("<tr>");
                        if (!rol.equals("Consulta")) {
                            out.print("<th>#</th>");
                        }
                        out.print("<th>Equipo</th>");
                        out.print("<th>Ubicación</th>");
                        out.print("<th>Tipo de equipo</th>");
                        out.print("<th>Horometro<br />Actual</th>");
                        out.print("<th>Horometro<br />Nuevo</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_equipos.size(); i++) {
                            Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                            if (Integer.parseInt(obj_equipos[14].toString()) == 1) {
                                out.print("<tr>");
                                if (!rol.equals("Consulta")) {
                                    out.print("<td align='center'><input type='checkbox' name='Ckb_equipo[" + i + "]' value='" + obj_equipos[0] + "-" + obj_equipos[13] + "' /></td>");
                                }
                                out.print("<td><b class='negro'>" + obj_equipos[1] + "</b></td>");
                                out.print("<td>" + obj_equipos[9] + "</td>");
                                out.print("<td>" + obj_equipos[7] + "</td>");
                                //out.print("<td align='center'>" + obj_equipos[12] + "<br />" + obj_equipos[23] + "</td>");
                                out.print("<td align='center'>" + obj_equipos[13] + "<br /><b>" + obj_equipos[18].toString().split(" ")[0] + "</b></td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
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
                        if (!rol.equals("Consulta")) {
                            out.print("</form>");
                        }
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="HOROMETROS PROGRAMADOS">
                else if (pageContext.getRequest().getAttribute("Horometros").toString().equals("Horometros_programados")) {
                    int anio_send = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                    int periodo = Integer.parseInt(pageContext.getRequest().getAttribute("Periodo").toString());
                    if (periodo > 1) {
                        lst_actualizacion_horometros = jpachhr.Actualizacion_horometros_programados(anio_send, 7, 12);
                    } else {
                        lst_actualizacion_horometros = jpachhr.Actualizacion_horometros_programados(anio_send, 1, 6);
                    }
                    out.print("<div id='content_sin'>");
                    lst_anios = jpachhr.Traer_anio_historial_horometros();
                    out.print("<h3>Horometros Programados");
                    out.print("<div style='float:right'>");
                    out.print("<form action='Horometro?opc=3' method='post' id='FormAnio' name='FormAnio'>");
                    out.print("<select name='Cbx_anio' id='Cbx_anio' onchange='PostBackAnio()'>");
                    for (int i = 0; i < lst_anios.size(); i++) {
                        Object[] obj_anios = (Object[]) lst_anios.get(i);
                        if (anio_send == Integer.parseInt(obj_anios[0].toString())) {
                            out.print("<option value='" + obj_anios[0] + "' selected> Año " + obj_anios[0] + " #Semanas " + obj_anios[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_anios[0] + "'> Año " + obj_anios[0] + " #Semanas " + obj_anios[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<select name='Cbx_periodo' id='Cbx_periodo' onchange='PostBackAnio()'>");
                    out.print("<option value='1' " + ((periodo > 1) ? "" : "selected") + ">Ene - Jun</option>");
                    out.print("<option value='2' " + ((periodo > 1) ? "selected" : "") + ">Jul - Dic</option>");
                    out.print("</select></form></div></h3>");
                    if (lst_actualizacion_horometros == null) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Horometro?opc=2' method='post' name='f1' id='f1'>");
                        out.print("<div style='margin:0px;width:200px;float:right' align='right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'/></div>");
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table align='center' class='table' id='resultados' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Fecha progragada</th>");
                        out.print("<th>Equipos</th>");
                        out.print("<th>Pendientes</th>");
                        out.print("<th>Programador</th>");
                        out.print("<th>Ver</th>");
                        out.print("<th>E-Mail</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_actualizacion_horometros.size(); i++) {
                            Object[] obj_act_horometros = (Object[]) lst_actualizacion_horometros.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + (i + 1) + "</b></th>");
                            out.print("<td align='center'><b class='negro'>" + obj_act_horometros[0] + "</b></td>");
                            out.print("<td align='center'>" + obj_act_horometros[1] + "</td>");
                            if (obj_act_horometros[2] == null) {
                                out.print("<td align='center'>Completado</td>");
                            } else {
                                out.print("<td align='center'>" + obj_act_horometros[2] + "</td>");
                            }
                            out.print("<td align='center'>" + obj_act_horometros[4] + "</td>");
                            out.print("<td align='center'><a href='Horometro?opc=4&fat=" + obj_act_horometros[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='edit' title='Detalle R-MTO-151' /></a></td>");
                            if (obj_act_horometros[2] == null) {
                                if (obj_act_horometros[3] == null) {
                                    out.print("<td align='center'><a href='Horometro?opc=6&fat=" + obj_act_horometros[0] + "'><img src='Interfaz/Contenido/Iconos/Mail.png' style='width:22px;height:15px;' alt='edit' title='Enviar Informe' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Informe enviado' /></a></td>");
                                }
                            } else {
                                out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' alt='edit' title='Sin permisos para enviar informe' /></a></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        out.print("</form>");
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="R-MTI-151">
                else if (pageContext.getRequest().getAttribute("Horometros").toString().equals("R-MTI-151")) {
                    fecha = pageContext.getRequest().getAttribute("Fecha").toString();
                    posicion = pageContext.getRequest().getAttribute("Posicion").toString();
                    lst_horometros = jpachhr.Traer_horometros_programados(fecha);
                    Object[] obj_horometro = (Object[]) lst_horometros.get(0);
                    double fecha_decimal = Double.parseDouble(fecha.split("-")[0] + "." + fecha.split("-")[1] + fecha.split("-")[2]);
                    out.print("<div id='content_sin'>");
                    out.println("<input type='hidden' id='Txt_pos' value='" + posicion + "' />");
                    out.print("<h3><form action='Horometro?opc=3' method='post' name='FormVolver' id='FormVolver'>"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' width='30px' height='30px' alt='edit' title='Volver a horometros programados' /></a> Volver | <b id='Convenciones'>Convenciones</b></h3>"
                            + "</form>");
                    //<editor-fold defaultstate="collapsed" desc="TABLA DE CONVENCIONES">
                    out.print("<script>");
                    out.print("$(Convenciones).click(function() {");
                    out.print("$(\"#toggleC\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='width:400px;padding-left:20px;padding-right:20px;margin-left:15%;margin-top:-1%;display:none;border: 1px solid #016279;background-color:#fff;position:absolute;' id=\"toggleC\">");
                    out.print("<h3>Tabla de convenciones</h3>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Descripción</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_rojo'></div></td>");
                    out.print("<td>Valor nuevo inferior al anterior.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_naranja'></div></td>");
                    out.print("<td>Valor nuevo supera las horas de la semana.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_verde'></div></div></td>");
                    out.print("<td>Dentro del incremento normal de horometros.</td>");
                    out.print("</tr>");
                    out.print("</table><br /><br />");
                    out.print("</div>");
//</editor-fold>
                    out.print("<div align='right'><input id='Txt_filtro_3' type='text' onkeyup='Filtrar_3()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'/></div>");
                    out.print("<table class='table' id='resultados_3' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<td colspan='8' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='2'>"
                            + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:202.5px;height:67.5px' /></td>");
                    out.print("<td align='center' colspan='3'><h3><b class='negro'>CONTROL SEMANAL HORAS <br />DE SERVICIO EQUIPOS</b><span id='Modulo_historial'></span></h3></td>");
                    if (fecha_decimal > 2016.0513) {
                        out.print("<td align='center' ><h3><b class='negro'>CODIGO : R-MTI-151<br />VERSION: </b><b> 2 </b></h3></td>");
                    } else {
                        out.print("<td align='center' ><h3><b class='negro'>CODIGO : R-MTI-151<br />VERSION: </b><b> 1 </b></h3></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>Fecha</b></td>");
                    out.print("<td align='center'>" + fecha + "</td>");
                    out.print("<td align='center'><b>Responsable</b></td>");
                    out.print("<td align='center' colspan='3'>" + obj_horometro[8] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>i</th>");
                    out.print("<th>Horometro nuevo</th>");
                    out.print("<th>Horometro actual</th>");
                    out.print("<th>Equipo</th>");
                    out.print("<th>Ubicación</th>");
                    out.print("<th colspan='2'>Tipo</th>");
                    out.print("</tr>");
                    if (lst_horometros != null) {
                        for (int i = 0; i < lst_horometros.size(); i++) {
                            Object[] obj_horometros = (Object[]) lst_horometros.get(i);
                            out.print("<tr>");
                            if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                if (Integer.parseInt(obj_horometros[10].toString()) == 1) {
                                    out.print("<td align='center'>");
                                    if ((Integer.parseInt(obj_horometros[7].toString()) < Integer.parseInt(obj_horometros[6].toString()))) {
                                        out.print("<div class='circulo_rojo' title='Valor nuevo inferior al anterior'></div>");
                                    } else if (Integer.parseInt(obj_horometros[7].toString()) > (Integer.parseInt(obj_horometros[6].toString()) + 168)) {
                                        out.print("<div class='circulo_naranja' title='Valor nuevo supera las horas de la semana'></div>");
                                    } else {
                                        out.print("<div class='circulo_verde' title='Dentro del incremento normal de horometros'></div>");
                                    }
                                    out.print("</td>");
                                    out.print("<td align='center'>" + (((Integer) obj_horometros[7] != 0) ? obj_horometros[7] : "") + "</td>");
                                } else {
                                    out.print("<td align='center'>");
                                    if ((Integer.parseInt(obj_horometros[7].toString()) < Integer.parseInt(obj_horometros[6].toString()))) {
                                        out.print("<div class='circulo_rojo' title='Valor nuevo inferior al anterior'></div>");
                                    } else if (Integer.parseInt(obj_horometros[7].toString()) > (Integer.parseInt(obj_horometros[6].toString()) + 168)) {
                                        out.print("<div class='circulo_naranja' title='Valor nuevo supera las horas de la semana'></div>");
                                    } else {
                                        out.print("<div class='circulo_verde' title='Dentro del incremento normal de horometros'></div>");
                                    }
                                    out.print("</td>");
                                    out.print("<td align='center'><form action='Horometro?opc=5' method='post' id='Form_horometro_" + i + "'>");
                                    out.print("<input type='hidden' name='fat' value='" + fecha + "' />"
                                            + "<input type='hidden' name='Id_horometro' value='" + obj_horometros[0] + "' />"
                                            + "<input type='hidden' name='psc' value='Psc_" + obj_horometros[2] + "' />"
                                            + "<input type='number' name='Txt_act_horometro' id='Txt_act_horometro" + i + "' onchange=\"validHor(" + i + ")\" onkeydown=\"if(event.key === 'Enter'){ event.preventDefault(); validHor(" + i + "); return false; }\" style='text-align:center;border-width:0;width:100px;font-size: 11px;color:#292929;margin:0;' value='" + (((Integer) obj_horometros[7] != 0) ? obj_horometros[7] : "") + "' autocomplete='off'/></td>");
                                    out.print("</form></td>");
                                    try {
                                        out.print("<input type='hidden' name='' id='Txt_actual_horometro" + i + "' value='" + obj_horometros[6] + "'>");
                                    } catch (Exception e) {
                                        out.print("<input type='hidden' name='' id='Txt_actual_horometro" + i + "' value='0'>");
                                    }
                                }
                            } else {
                                out.print("<td align='center'>");
                                if ((Integer.parseInt(obj_horometros[7].toString()) < Integer.parseInt(obj_horometros[6].toString()))) {
                                    out.print("<div class='circulo_rojo' title='Valor nuevo inferior al anterior'></div>");
                                } else if (Integer.parseInt(obj_horometros[7].toString()) > (Integer.parseInt(obj_horometros[6].toString()) + 168)) {
                                    out.print("<div class='circulo_naranja' title='Valor nuevo supera las horas de la semana'></div>");
                                } else {
                                    out.print("<div class='circulo_verde' title='Dentro del incremento normal de horometros'></div>");
                                }
                                out.print("</td>");
                                out.print("<td align='center'>" + (((Integer) obj_horometros[7] != 0) ? obj_horometros[7] : "") + "</td>");
                            }
                            out.print("<td align='center'>" + obj_horometros[6] + " </td>");
                            out.print("<td><b>" + obj_horometros[2] + "<span id='Psc_" + obj_horometros[2] + "'></span></b></td>");
                            out.print("<td>" + obj_horometros[4] + "</td>");
                            out.print("<td colspan='2'>" + obj_horometros[3] + "</td>");
                            out.print("<tr>");
                        }
                    }
                    out.print("</table>");
                }
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_horometro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
