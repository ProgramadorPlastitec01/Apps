package Vista;

import Controlador.ActividadJpaController;
import Controlador.UbicacionJpaController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Actividades_R_GC_079 extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="0. VARIABLES">
            HttpSession sesion = pageContext.getSession();
            UbicacionJpaController jpa_ubicacion = new UbicacionJpaController();
            ActividadJpaController jpa_actividades = new ActividadJpaController();
            String rol = sesion.getAttribute("rol").toString();
            int Id_Usuario = Integer.parseInt(sesion.getAttribute("identificacion").toString());
            int Id_Actividad = Integer.parseInt(pageContext.getRequest().getAttribute("Id_Actividad").toString());
            int consecutivo = Integer.parseInt(pageContext.getRequest().getAttribute("Consecutivo").toString());
            List list_Ubicaciones = jpa_ubicacion.consultaUbicacion();;
            List list_actividades = null;
            List list_actividad = null;
            String filtro = pageContext.getRequest().getAttribute("txt_bus").toString();
            String fecha_i = pageContext.getRequest().getAttribute("fch_inicio").toString();
            String fecha_f = pageContext.getRequest().getAttribute("fch_fin").toString();
//</editor-fold>
            out.print("<div id='content_sin'>");
            out.print("<a href='#'><img src='Interfaz/Contenido/Iconos/Plus.png' width='25' height='25'  onclick='javascript:document.getElementById(\"Form_registro\").style.display=\"block\"'></a>");

            if (Id_Actividad == 0) {
                //<editor-fold defaultstate="collapsed" desc="1. REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Form_registro' style='opacity: 1.03; display: none;'>");
                out.print("<fieldset class='popup_local' id='Fiel_registrar' style='width:750px; height:550px; position: absolute;top: 2%;left:25%; overflow:scroll;'>");
                out.print("<div style='float:right;'><a href='#'><img src='Interfaz/Contenido/Iconos/Delete.png' onclick='javascript:document.getElementById(\"Form_registro\").style.display=\"none\"' width='22' height='22' title='Cancelar'></a></div>");
                out.print("<h3>Registar R-GC-079</h3>");
                out.print("<form method='post' onsubmit='registroR()' action='R_GC_079?opc=2' name='form1'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' />");
                out.print("<div style='width:200px; float:left;'>");
                out.print("<input type='text' name='consecutiv' value='" + consecutivo + "' class='input_full' readonly='true'/>");
                out.print("<b>Fecha:</b>");
                out.print("<input type='text' name='txtfecha' class='input_full' readonly='true'/>");
                out.print("<b>Hora:</b>");
                out.print("<input type='text' name='txthora' class='input_full' readonly='true'/>");
                out.print("<b>Turno:</b>");
                out.print("<select name='txtturno' id='txtturno' class='input_full' >");
                out.print("<option style='display;none:' value=''>Seleccione turno</option>");
                out.print("<option value='1'>TURNO 1</option>");
                out.print("<option value='2'>TURNO 2</option>");
                out.print("<option value='3'>TURNO 3</option>");
                out.print("<option value='1/12'>TURNO 1/12</option>");
                out.print("<option value='2/12'>TURNO 2/12</option>");
                out.print("<option value='OFICINA'>TURNO OFICINA</option>");
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('txtturno');");
                out.print(" validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("<b>Ubicacion:</b>");
                out.print("<select name='Ubicacion' id='Ubicacion'>");
                out.print("<option value=''>Seleccione Ubicacion</option>");
                for (int i = 0; i < list_Ubicaciones.size(); i++) {
                    Object[] obj_Ubicacion = (Object[]) list_Ubicaciones.get(i);
                    if (obj_Ubicacion[2].equals(1)) {
                        out.print("<option value='" + obj_Ubicacion[0] + "'>" + obj_Ubicacion[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Ubicacion');");
                out.print(" validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit'  value='Registrar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px; left:72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</div>");
                out.print("<div style='width:500px; float:left;'>");
                out.print("<textarea  id='descripcion-id' name='txt_descripcion-id' style='width:500px; height:400px;'><b style='color:#880e4f;'>Actividades Urgentes:</b><div contenteditable='true'><p></p></div><hr/>"
                        + "<b style='color:#880e4f;'>Personal:</b><div contenteditable='true'><p></p></div><hr/>"
                        + "<b style='color:#880e4f;'>Notas:</b><div contenteditable='true'><p></p></div>");
                out.print("</textarea>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
            } else if (Id_Actividad > 0) {
                list_actividad = jpa_actividades.actividadId(Id_Actividad);
                //<editor-fold defaultstate="collapsed" desc="2. MODIFICAR">
                Object[] Obj_acti = (Object[]) list_actividad.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Form_modificar' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:750px; height:550px; position: absolute;top: 2%;left:25%; overflow:scroll;'>");
                out.print("<div style='float:right;'><a href='#' onclick='javascript:document.getElementById(\"Form_modificar\").style.display=\"none\"'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                out.print("<h3>Modificar R-GC-079</h3>");
                out.print("<form method='post' onsubmit='registroR()' action='R_GC_079?opc=3' name='form1'>");
                out.print("<div style='width:200px; float:left;'>");
                out.print("<b>Consecutivo:</b>");
                out.print("<input type='text' name='consecutiv' value='" + Obj_acti[8] + "' class='input_full' readonly='true'/>");
                out.print("<input type='hidden' name='Accion' value='Modificar' class='input_full' readonly='true'/>");
                out.print("<input type='hidden' name='Id_Actividad' value='" + Obj_acti[0] + "' class='input_full' readonly='true'/>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' />");
                out.print("<b>Fecha:</b>");
                out.print("<input type='text' name='txtfecha' class='input_full' readonly='true'/>");
                out.print("<b>Hora:</b>");
                out.print("<input type='text' name='txthora' class='input_full' readonly='true'/>");
                out.print("<b>Turno:</b>");
                out.print("<select name='txtturno' id='txtturno' class='input_full' >");
                out.print("<option style='display;none:' value='" + Obj_acti[4] + "'>TURNO " + Obj_acti[4] + "</option>");
                out.print("<option value='1'>TURNO 1</option>");
                out.print("<option value='2'>TURNO 2</option>");
                out.print("<option value='3'>TURNO 3</option>");
                out.print("<option value='1/12'>TURNO 1/12</option>");
                out.print("<option value='2/12'>TURNO 2/12</option>");
                out.print("<option value='OFICINA'>TURNO OFICINA</option>");
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('txtturno');");
                out.print(" validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("<b>Ubicacion:</b>");
                out.print("<select name='Ubicacion' id='Ubicacion'>");
                out.print("<option value=''>Seleccione Ubicacion</option>");
                for (int i = 0; i < list_Ubicaciones.size(); i++) {
                    Object[] obj_Ubicacion = (Object[]) list_Ubicaciones.get(i);
                    if (obj_Ubicacion[2].equals(1)) {
                        if (obj_Ubicacion[0].equals(Obj_acti[13])) {
                            out.print("<option value='" + obj_Ubicacion[0] + "' selected>" + obj_Ubicacion[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_Ubicacion[0] + "'>" + obj_Ubicacion[1] + "</option>");
                        }
                    }
                }
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('Ubicacion');");
                out.print(" validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit'  value='Modificar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</div>");
                out.print("<div style='width:500px; float:left;'>");
                out.print("<textarea  id='descripcion-id' name='txt_descripcion-id' style='width:500px; height:400px;'>" + Obj_acti[5].toString().replace("<div>", "<div contenteditable='true'>") + "<hr/>"
                        + "" + Obj_acti[6].toString().replace("<div>", "<div contenteditable='true'>") + "<hr/>"
                        + "" + Obj_acti[7].toString().replace("<div>", "<div contenteditable='true'>") + "");
                out.print("</textarea>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset></div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="3. CONSULTAR">
            if (rol.equals("Inspector_calidad")) {
                if (!fecha_i.equals("") && !fecha_f.equals("")) {
                    list_actividades = jpa_actividades.filtroRangoPorRol(fecha_i, fecha_f, filtro, rol);
                } else if (!filtro.equals("")) {
                    list_actividades = jpa_actividades.filtroActividadesRol(filtro, rol);
                } else {
                    list_actividades = jpa_actividades.consultarActividadesRol(rol);
                }
            }
            if (!rol.equals("Inspector_calidad")) {
                if (!fecha_i.equals("") && !fecha_f.equals("")) {
                    list_actividades = jpa_actividades.filtroRango(fecha_i, fecha_f, filtro);
                } else if (!filtro.equals("")) {
                    list_actividades = jpa_actividades.filtroActividades(filtro);
                } else {
                    list_actividades = jpa_actividades.consultarActividades();
                }
            }
            out.print("<div style='float:right;'>");
            out.print("<form action='R_GC_079?opc=1&Id_Actividad=0' method='post' >");
            out.print("<a id='mostrarr' href='javascript:mostrar1();'><img id='cambiar' src='Interfaz/Contenido/Iconos/Search.png' width='25px' height='25px' alt='edit' alt='edit' title='Consulta actividades'></a>&nbsp&nbsp");
            out.print("<input type='text' name='txt_bus'  placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();' style='margin-left: initial;'>");
            out.print("</form>");
            out.print("</div>");
            out.print("<script language='Javascript'>function mostrar1() {var panel, mostrarr ;var pagina =''; panel = document.getElementById('RangoFecha');if(panel.style.visibility == 'hidden') {panel.style.visibility = 'visible';mostrarr = document.getElementById('mostrar');document.getElementById('cambiar').src='Interfaz/Contenido/Iconos/Min.png';document.getElementById('cambiar').title = 'Cancelar';}else {panel.style.visibility = 'hidden';mostrarr = document.getElementById('mostrar');document.getElementById('cambiar').src = 'Interfaz/Contenido/Iconos/Search.png';document.getElementById('cambiar').title = 'Consulta actividades';}}</script>");
            out.print("<fieldset class='resalta_field' id='RangoFecha' style='width: 200px;visibility: hidden;position: absolute;top: 300px;left: 45%;  '>");
            out.print("<legend>Consulta Actividades</legend>");
            out.print("<form action='R_GC_079?opc=1&Id_Actividad=0' method='post' >");
            out.print("<input type='hidden' name='horaI'  value=''>");
            out.print("<input type='hidden' name='horaF' value=''>");
            out.print("<b>Fecha inicio</b><input id='start' type='text' name='fch_inicio' class='required input_field'  placeholder='Seleccionar fecha' >");
            out.print("<span class=' LV_validation_message LV_valid'></span>");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
            out.print("<b>Fecha fin</b><input id='end' type='text' name='fch_fin' class='required input_field' placeholder='Seleccionar fecha' >");
            out.print("<span class=' LV_validation_message LV_valid'></span>");
            out.print("<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
            out.print("<b>Busqueda</b><input type='text' name='txt_bus' id='txtsolicitudbus' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
            out.print("<input type='submit' value='Consultar' name='Consultar'><br />");
            out.print("</form>");
            out.print("</fieldset>");
            if (list_actividades == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                out.print("<b>No se encontro ninguna Actividad</b>");
                out.print("</center>");
            } else {
                out.print("<h3>Actividades</h3>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                for (int i = 0; i < list_actividades.size(); i++) {
                    Object[] obj_actividad = (Object[]) list_actividades.get(i);
                    out.print("<tr>");
                    out.print("<td colspan='5'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th style='width:10%;'><div class='girar'>" + obj_actividad[2] + "</br> TURNO: " + obj_actividad[4] + "</div></th>");
                    out.print("<td style='width:25%;' valign='top'>");
                    out.print("<b>Usuario: </b>" + obj_actividad[11] + " " + obj_actividad[12] + "</br></br>");
                    out.print("<b>Consecutivo: </b>" + obj_actividad[8] + "</br></br>");
                    if ((Integer) obj_actividad[0] < 9185) {
                        out.print("<b>Ubicacion: </b>N/A </br>");
                    } else {
                        out.print("<b>Ubicacion: </b>" + obj_actividad[15] + "</br></br>");
                    }
                    ////<editor-fold defaultstate="collapsed" desc="REVISIÓN">
                    out.print("<hr />");
                    String reviso = obj_actividad[10].toString();
                    if (reviso.equals("N/A")) {
                        out.print("<b class='naranja'>Sin Revisar</b>");
                    } else {
                        out.print("<b>Revisado por:</b><br />" + reviso.split(" ")[1] + " " + reviso.split(" ")[2] + " - " + reviso.split(" ")[8] + "/" + reviso.split(" ")[4] + "/" + reviso.split(" ")[5] + "");
                    }
                    ////</editor-fold>
                    out.print("</td>");
                    out.print("<td colspan='2' valign='top' style='width:67%; height:10%;'>");
                    out.print("<button class='accordion'>ACTIVIDADES URGENTES</button>");
                    out.print("<div class='panel'>");
                    out.print(" " + obj_actividad[5] + "");
                    out.print("</div>");
                    out.print("<button class='accordion'> NOVEDADES PERSONAL</button>");
                    out.print("<div class='panel'>");
                    out.print(" " + obj_actividad[6] + "");
                    out.print("</div>");
                    out.print("<button class='accordion'> NOTAS / OBSERVACIONES</button>");
                    out.print("<div class='panel'>");
                    out.print(" " + obj_actividad[7] + "");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("<td style='width:3%;' align='center'>");
                    if (!rol.equals("Consulta")) {
                        if (obj_actividad[9].equals("a")) {
                            if (Integer.parseInt(obj_actividad[13].toString()) == Id_Usuario || rol.equals("Administrador")) {
                                out.print("<a href='R_GC_079?opc=1&Id_Actividad=" + obj_actividad[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' title='Modificar' </a><hr>");
                            }
                            out.print("<a href='Novedades?opc=1&Id_Actividad=" + obj_actividad[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='Logo' width='25' height='25.5' title='Novedades de maquina' /></a>");
                            if (Integer.parseInt(obj_actividad[13].toString()) == Id_Usuario || rol.equals("Administrador")) {
                                out.print("<hr>");
                                out.print("<a href='#' " + ((Integer.parseInt(obj_actividad[13].toString()) == Id_Usuario || rol.equals("Administrador")) ? " onclick=\"FinalizarA(\'" + obj_actividad[0] + "\',\'c\')\"" : "") + "><img src='Interfaz/Contenido/Iconos/Open.png' alt='Logo' width='25' height='25.5' title='Finalizar' /></a>");
                            } else {
//                                out.print("<a href='#'  onclick=\"FinalizarC(\'" + obj_actividad[0] + "\',\'c\')\"><img src='Interfaz/Contenido/Iconos/Open.png' alt='Logo' width='25' height='25.5' title='Finalizar' /></a>");
                                out.print("<hr>");
                                out.print("<img src='Interfaz/Contenido/Iconos/Warning.png' alt='Logo' width='23' height='23' title='Sin permiso' /></a>");
                            }
                        } else if (obj_actividad[9].equals("c")) {
                            out.print("<a href='Novedades?opc=5&Id_Actividad=" + obj_actividad[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='Logo' width='25' height='25.5' title='Novedades de maquina' /></a><hr>");
                            if (rol.equals("Administrador") || rol.equals("Coordinador_calidad")) {
                                out.print("<a href='#' onclick=\"AbrirA(\'" + obj_actividad[0] + "\',\'a\')\"><img src='Interfaz/Contenido/Iconos/Close.png' alt='Logo' width='25' height='25.5' title='Finalizado'/></a><hr>");
                            } else if (rol.equals("Administrador") || rol.equals("Coordinador_calidad")) {
                                out.print("<a href='#'" + ((Integer.parseInt(obj_actividad[13].toString()) == Id_Usuario) ? "onclick=\"AbrirA(\'" + obj_actividad[0] + "\',\'a\')\"" : "") + "><img src='Interfaz/Contenido/Iconos/Close.png' alt='Logo' width='25' height='25.5' title='Finalizado'/></a><hr>");
                            } else {
                                out.print("<img src='Interfaz/Contenido/Iconos/Close.png' alt='Logo' width='25' height='25.5' title='Finalizado'/></a>");
                            }
                            if (rol.equals("Administrador") || rol.equals("Coordinador_calidad")) {
                                out.print("<a href='#'  " + ((rol.equals("Coordinador_calidad") || rol.equals("Administrador")) ? " onclick=\"RevisarA(\'" + obj_actividad[0] + "\')\"" : "") + " ><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='25' height='25.5' title='Revisar'/></a></a>");
                            }
                        } else {
                            out.print("<a href='Novedades?opc=5&Id_Actividad=" + obj_actividad[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='Logo' width='25' height='25.5' title='Novedades de maquina' /></a>");
                        }
                    } else {
                        out.print("<a href='Novedades?opc=5&Id_Actividad=" + obj_actividad[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='Logo' width='25' height='25.5' title='Novedades de maquina'/></a>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
            }
            //</editor-fold>
            out.print("</div>");
        } catch (IOException ex) {
            Logger.getLogger(Actividades_R_GC_079.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
