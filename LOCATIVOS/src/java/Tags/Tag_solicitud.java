package Tags;

import Controladores.AreaJpaController;
import Controladores.ClasificacionJpaController;
import Controladores.EvidenciaJpaController;
import Controladores.ProgramacionDetalleJpaController;
import Controladores.ProgramacionJpaController;
import Controladores.SolicitudJpaController;
import Controladores.UbicacionJpaController;
import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_solicitud
        extends TagSupport {

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            Date fecha = new Date();
            UsuarioJpaController jpacusu = new UsuarioJpaController();
            SolicitudJpaController jpacsol = new SolicitudJpaController();
            EvidenciaJpaController jpacevd = new EvidenciaJpaController();
            ProgramacionJpaController jpacpro = new ProgramacionJpaController();
            ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
            ClasificacionJpaController jpaccl = new ClasificacionJpaController();
            UbicacionJpaController jpacubi = new UbicacionJpaController();
            ProgramacionJpaController jpapro = new ProgramacionJpaController();
            AreaJpaController jpacare = new AreaJpaController();
            List lst_historial_actividades = null;
            List lst_adjuntos = null;
            List lst_ubiccacion = jpacubi.Ubicaciones();
            String id_usuario = this.pageContext.getSession().getAttribute("Id_usuario").toString();
            String nombre_usuario = this.pageContext.getSession().getAttribute("Nombres").toString();
            String nombre_rol = this.pageContext.getSession().getAttribute("Nombre_rol").toString();
            int iare = Integer.parseInt(this.pageContext.getSession().getAttribute("Id_area").toString());
            int id_solicitud = 0;
            List lst_historial = null;
            List lst_programaciones = null;
            List lst_solicitud = null;
            List lst_cont_clasificacion = null;
            int id_programacion = 0;
            List lst_areas = null;
            List lst_programacion = null;
            String Id_Solicitudes_Programar = "";
            List lst_clasificacion = null;
            List lst_seguimiento = null;
            List lst_solicitudes_filtro = null;
            List lst_ejecutor = null;
            List lst_siglatura_area = null;
            List lst_solicitud_detalle = null;
            List lst_solicitudes = null;
            List lst_rango_de_solicitudes = null;
            if (this.pageContext.getRequest().getAttribute("Solicitud") != null) {
                out.print("<div id='content_sin'>");
                if (!nombre_rol.equals("Consulta")) {
                    out.print("<h3><img id=\"Menu_registro\" src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Desplegar Registro' />Solicitudes | <a href='#' onclick=\"mostrar('3')\"><i >Convenciones</i></a><div style='float:right;width:240px'><input type='text' onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar'/> ");
                    out.print("<a href='#' onclick='EnviarCorreo()' ><img src='Interfaz/Contenido/Iconos/Mail_send.png' title='Enviar solicitudes confirmadas' style='width:height:17px;width:30px' /></a></div>");
                    out.print("</h3>");
                } else {
                    out.print("<h3>Programaciones</h3>");
                }
                //<editor-fold defaultstate="collapsed" desc="CONVENCIONES ESTADOS">
                out.print("<div id='emergente3' style='width: 600px; display:none; padding-left: 15px; padding-right: 20px; margin-left: 10%; margin-top: 0%; border:solid 1px #b33939;background-color:#fff; position: absolute;'>");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr><th>Estado</th>");
                out.print("<th>Descripción</th></tr>");
                out.print("<tr>");
                out.print("<td align='center'><div class='circulo_editar'><img src='Interfaz/Contenido/Iconos/Edit.png' style='height:20px; width:20px;margin-top:13px;' title='Editar' /></div>Editar</td>");
                out.print("<td>La solicitud que se encuentra en edición para cambios previos a la programación</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' ><div class='circulo_enviar'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:20px;margin-top:17px;' title='Enviado' /></div>Enviado</td>");
                out.print("<td>La solicitud se envio a Programación y esta a espera para ser programada. </td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><div class='circulo_programado'><img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top:13px;' title='Programado' /></div>Programado</td>");
                out.print("<td>La solicitud que se encuentra en Programación</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><div class='circulo_pendiente'><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Pendiente ó Seguimiento' /></div>Pendiente ó Seguimiento</td>");
                out.print("<td><b>Pendiente : </b>Cuando la solicitud se encuentra en estado pediente, indica que se prolongo la fecha de la realización del locativo<hr /><b>Seguimiento: </b>Cuando la solicitud esta en seguimiento, indica que va pasar por el mismo proceso.</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><div class='circulo_ejecutado'><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Ejecutado ó Terminada' /></div>Ejecutado ó Terminada</td>");
                out.print("<td><b>Ejecutado : </b>Indica que el programador ya realizo la confirmación de la solicitud, por consiguiente el usuario tendra la opción de dar por terminada o en seguimiento la solicitud. <hr /><b>Terminado: </b>Solicitud recibida a conformidad por el usuario</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><div class='circulo_agrupada_sin'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div><div class='circulo_agrupada'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div>Agrupada</td>");
                out.print("<td>Cuando la solicitud esta  agrupada, indica que esta relacionado con una solicitud a la cual se realiza gestión locativa y replicara la solución a las agrupadas</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'><div class='circulo_declinada'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación sin confirmar' /></div><div class='circulo_declinada_fin'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación confirmada' /></div>Declinación</td>");
                out.print("<td><b>Sin confirmar : </b>Indica que la solicitud ha sido declinada ya sea por el ejecutor o el programador, esperando una confirmación <hr /><b>Confirmado : </b>Indicar que la solicitud ha sido efectuada y ya no se toma en cuenta para las programaciones de actividades locativas</td>");
                out.print("</tr>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR SOLICITUD">
                if (this.pageContext.getRequest().getAttribute("Solicitud").toString().equals("Registro_solicitud")) {
                    out.print("<script>");
                    out.print("$(Menu_registro).click(function() {");
                    out.print("$(\"#toggle\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");

                    out.print("<div style='display:none;border: 1px solid #b33939;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Solicitud</h3>");
                    out.print("<form action='Solicitud?opc=2'  method='post' onsubmit='registroS();'>");
                    out.print("<b>Fecha :</b>");
                    out.print("<input type='text' name='Txt_fecha' readonly id='Txt_fecha' value='" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() < 10 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() < 10 ? "-0" : "-") + "" + fecha.getDate() + "' title='fecha'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");

                    out.print("<input type='hidden' name='Id_usuario' id='Id_usuario' value='" + id_usuario + "'/>");
                    out.print("<b>Solicitante :</b>");
                    out.print("<input type='text' name='Txt_solicitante' value='" + nombre_usuario.toString().toUpperCase() + "' id='Txt_solicitante' readonly  title='Solicitante' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_solicitante');val1.add(Validate.Presence);</script>");

                    out.print("<b>Seleccione Planta :</b>");
                    out.print("<select name='Cbx_planta' id='Cbx_planta' title='planta' style='width: 188;'>");
                    out.print("<option style='display:none;' value=''>Seleccionar </option>");
                    out.print("<option value='PLANTA1'>Planta 1</option>");
                    out.print("<option value='PLANTA2'>Planta 2</option>");
                    out.print("<option value='BODEGA5'>Bodega 5</option>");
                    if (iare == 11) {
                        out.print("<option value='GENERAL'>General</option>");
                    }
                    out.print("</select></br></br><script type='text/javascript'>var val1 = new LiveValidation('Cbx_planta');val1.add(Validate.Presence);</script>");

                    out.print("<b>Ubicación :</b>");

                    out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                    out.print("<option value=''>Seleccione Ubicacion</option>");
                    if (lst_ubiccacion == null) {
                        out.print("<option value=''>No hay Ubicaciones</option>");
                    } else {
                        int Daggo = 0;
                        String Typet = "";
                        String Color = "";
                        for (int t = 0; t < 3; t++) {
                            if (t == 0) {
                                Typet = "Insumos";
                                Color = "#f9f5b6";
                            } else if (t == 1) {
                                Typet = "Farmacéuticos";
                                Color = "#ffd3d3";
                            } else {
                                Typet = "General";
                                Color = "#e0dfcd";
                            }
                            out.print("<optgroup label='" + Typet + "' style='background-color: " + Color + "; color:#000000;'>");
                            for (int i = 0; i < lst_ubiccacion.size(); i++) {
                                Object[] objet_ubi = (Object[]) lst_ubiccacion.get(i);
                                String areas = objet_ubi[4].toString().replace("][", "-").replace("]", "").replace("[", "");
                                if (areas != "") {
                                    String[] vector_area = areas.split("-");
                                    for (int j = 0; j < vector_area.length; j++) {
                                        if (Integer.parseInt(vector_area[j]) == iare) {
                                            if ((objet_ubi[3].equals(Integer.valueOf(1))) && (objet_ubi[2].equals(Typet))) {
                                                out.print("<option style='background-color:" + Color + "; color:#000000;'>" + objet_ubi[1] + "</option>");
                                            }
                                            j = vector_area.length;
                                        }
                                    }
                                }
                            }
                            out.print("</optgroup>");
                            Daggo = 1;
                        }
                    }
                    out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);val1.add(Validate.Txt_ubicacion);</script>");

                    out.print("<b>Descripción :</b>");
                    out.print("<textarea type='text' placeholder='Describa su locativo' name='Txt_descripcion' style='height:150px;' id='Txt_descripcion' title='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");

                    out.print("<b>Seleccione Clasificación :</b>");
                    out.print("<select name='Cbx_clasificacion' id='Cbx_clasificacion' title='Clasificación' style='width: 188;'>");
                    out.print("<option style='display:none;' value=''>Seleccionar </option>");
                    out.print("<option value='ALTA'>Alta</option>");
                    out.print("<option value='MEDIA'>Media</option>");
                    out.print("<option value='BAJA'>Baja</option>");
                    out.print("</select></br></br><script type='text/javascript'>var val1 = new LiveValidation('Cbx_clasificacion');val1.add(Validate.Presence);</script>");

                    out.print("<input type='submit' id='btsubmit' value='Registrar solicitud' style='width: 188;'/> </br></br>");
                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n          <div></div>\n          <div></div>\n          <div></div>\n        </div>");

                    out.print("</form>");
                    out.print("</div><div class='cleaner'> </div>");
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR SOLICITUD">
                else if (this.pageContext.getRequest().getAttribute("Solicitud").toString().equals("Modificar_Solicitud")) {
                    lst_solicitud = (List) this.pageContext.getRequest().getAttribute("Datos_solicitud");
                    Object[] obj_solicitud = (Object[]) lst_solicitud.get(0);
                    out.print("<script>");
                    out.print("$(Menu_registro).click(function() {");
                    out.print("$(\"#toggle\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='display:block;border: 1px solid #b33939;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                    out.print("<div id='sidebar'>");
                    out.print("<div align='right'><a href='Solicitud?opc=1&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Cancelar Modificación' /></a></div>");
                    out.print("<h3>Modificar Solicitud</h3>");
                    out.print("<form action='Solicitud?opc=4' method='post'>");
                    out.print("<input type='hidden' name='Id_solicitud' id='Id_solicitud' value='" + obj_solicitud[0] + "'/>");
                    out.print("<b>Fecha :</b>");
                    out.print("<input type='text' name='Txt_fecha' readonly id='Txt_fecha' value='" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() < 10 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() < 10 ? "-0" : "-") + "" + fecha.getDate() + "' title='fecha'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");

                    out.print("<b>Solicitante :</b>");
                    out.print("<input type='text' name='Txt_solicitante' value='" + nombre_usuario.toString().toUpperCase() + "' id='Txt_solicitante' readonly  title='Solicitante' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_solicitante');val1.add(Validate.Presence);</script>");

                    out.print("<b>Seleccione Planta :</b>");
                    out.print("<select name='Cbx_planta' id='Cbx_planta' title='planta' style='width: 188;'>");
                    out.print("<option value='" + obj_solicitud[8] + "' style='display:none;'>" + obj_solicitud[8] + "</option>");
                    out.print("<option value='PLANTA1'>Planta 1</option>");
                    out.print("<option value='PLANTA2'>Planta 2</option>");
                    out.print("<option value='BODEGA5'>Bodega 5</option>");
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_planta');mySelect.add(Validate.Persisten, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<b>Ubicación :</b>");
                    if (lst_ubiccacion == null) {
                        out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                        out.print("<option value=''>No hay Ubicaciones</option>");
                        out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);val1.add(Validate.Txt_ubicacion);</script>");
                    } else {
                        out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                        int Daggo = 0;
                        String Typet = "";
                        String Color = "";
                        for (int t = 0; t < 3; t++) {
                            if (t == 0) {
                                Typet = "Insumos";
                                Color = "#f9f5b6";
                            } else if (t == 1) {
                                Typet = "Farmacéuticos";
                                Color = "#ffd3d3";
                            } else {
                                Typet = "General";
                                Color = "#e0dfcd";
                            }
                            out.print("<optgroup label='" + Typet + "' style='background-color: " + Color + "; color:#000000;'>");
                            for (int i = 0; i < lst_ubiccacion.size(); i++) {
                                Object[] objet_ubi = (Object[]) lst_ubiccacion.get(i);
                                String areas = objet_ubi[4].toString().replace("][", "-").replace("]", "").replace("[", "");
                                if (areas != "") {
                                    String[] vector_area = areas.split("-");
                                    if (objet_ubi[1].toString().equals(obj_solicitud[1].toString())) {
                                        if (objet_ubi[2].equals(Typet)) {
                                            out.print("<option style='background-color:" + Color + "; color:#000000;' selected>" + objet_ubi[1] + "</option>");
                                        }
                                    } else {
                                        for (int j = 0; j < vector_area.length; j++) {
                                            if (Integer.parseInt(vector_area[j]) == iare) {
                                                if ((objet_ubi[3].equals(Integer.valueOf(1))) && (objet_ubi[2].equals(Typet))) {
                                                    out.print("<option style='background-color:" + Color + "; color:#000000;'>" + objet_ubi[1] + "</option>");
                                                }
                                                j = vector_area.length;
                                            }
                                        }
                                    }
                                }
                            }
                            out.print("</optgroup>");
                            Daggo = 1;
                        }
                        out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);val1.add(Validate.Txt_ubicacion);</script>");
                    }
                    out.print("<b>Descripción :</b>");
                    out.print("<textarea type='text' name='Txt_descripcion' id='Txt_descripcion' style='height:150px;'  title='Descripcion' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_solicitud[2] + "</textarea>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");

                    out.print("<b>Clasificación :</b>");
                    out.print("<select name='Cbx_clasificacion' id='Cbx_clasificacion' title='Clasificacion' style='width: 188;'>");
                    out.print("<option value='" + obj_solicitud[3] + "' style='display:none;'>" + obj_solicitud[3] + "</option>");
                    out.print("<option value='alta'>Alta</option>");
                    out.print("<option value='media'>Media</option>");
                    out.print("<option value='baja'>Baja</option>");
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_clasificacion');mySelect.add(Validate.Persisten, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<input type='submit' value='Actualizar' style='width: 188;'/></br></br>");
                    out.print("</form>");
                    out.print("</div><div class='cleaner'> </div>");
                    out.print("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR">
                lst_solicitudes = jpacsol.Solicitudes(iare, nombre_rol);
                if (lst_solicitudes == null) {
                    out.print("<center>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                    out.print("<b>No existe ningun locativo solicitado</b>");
                    out.print("</center>");
                } else {
                    out.print("<form action='Solicitud?opc=5' name='vector_solicitudes_correo' id='vector_solicitudes_correo' method='post'>");
                    out.println("<input type='hidden' name='Id_solicitudes_correo' id='Id_solicitudes_correo' onclick=\"this.focus();this.select();\" />");
                    out.print("</form>");

                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<td coslpan='6'></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_solicitudes.size(); i++) {
                        Object[] obj_solicitud = (Object[]) lst_solicitudes.get(i);
                        out.print("<tr>");
//                        out.print("<td style='font-size:18px'><b>#" + obj_solicitud[0] + "</b></td>");
                        out.print("<td align='center' style='font-size:16px;border:1px solid #b33939;'><b>#" + obj_solicitud[0] + "</b></td>");
                        out.print("<td style='width:70%;' valign='top'><div style='float:right'><b class='negro' title='Fecha de solicitud'>" + obj_solicitud[1] + "</b> | " + "<b title='Prioridad' class='" + (obj_solicitud[5].toString().equals("ALTA") ? "rojo" : obj_solicitud[5].toString().equals("MEDIA") ? "naranja" : "verde") + "'> " + obj_solicitud[5] + "</b></div>" + "<b>Solicitante: </b>" + obj_solicitud[2] + "<br />" + "<b>Ubicación: </b>" + obj_solicitud[7] + "<b>-</b>" + obj_solicitud[3] + "</br>");
                        out.print("<b>Descripción:</b>" + obj_solicitud[4] + "</td>");
                        if (Integer.parseInt(obj_solicitud[6].toString()) == 1) {
                            // out.print("<td align='center' style='background-color:rgba(45, 137, 239, 0.56)'><a href='Solicitud?opc=3&Id_solicitud=" + obj_solicitud[0] + "' ><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' style='margin-top:5%;'alt='edit' title='Modificar Registro' /></a><br/><b style='font-weight:bold;color:#000;'>edicion</b></td>");
                            out.print("<td align='center'><div class='circulo_editar'><a href='Solicitud?opc=3&Id_solicitud=" + obj_solicitud[0] + "' ><img src='Interfaz/Contenido/Iconos/Edit.png' style='height:20px; width:20px;margin-top:13px;' title='Editar' /></div></a>Editar</td>");
                            out.print("<td  align='center'><input type='checkbox' name='box" + obj_solicitud[0] + "' id='box" + obj_solicitud[0] + "' value='[" + obj_solicitud[0] + "]' onclick=\"add_sub(this);\" /></td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 2) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgba(246, 146, 30, 0.50);'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:25px;' style='margin-top:5%;' title='Correo enviado' /><br/><b style='font-weight:bold;color:#000;'>enviado</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_enviar'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:20px;margin-top:17px;' title='Enviado' /></div>Enviado</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 3) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgba(247, 224, 55, 0.57);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top:5%;' title='Programado' />" + "</a><br/><b style='font-weight:bold;color:#000;'>programado</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_programado'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top:13px;' title='Programado' /></div></a>Programado</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 4) {
                            //out.print("<td align='center' colspan='2' style='background-color: rgba(239, 20, 0, 0.72);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:5%;' title='pendiente' />" + "</a><br/><b style='font-weight:bold;color:#000;'>pendiente</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_pendiente'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Pendiente' /></div></a>Pendiente</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 5) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgba(172, 244, 25, 0.57);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:5%;' title='ejecutado' />" + "</a><br/><b style='font-weight:bold;color:#000;'>ejecutado</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_ejecutado'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Ejecutado' /></div></a>Ejecutado</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 6) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgba(204,0,0,0.78);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:5%;' title='seguimiento' />" + "</a><br/><b style='font-weight:bold;color:#000;'>seguimiento</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_pendiente'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Seguimiento' /></div></a>Seguimiento</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 7) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgba(172, 244, 25, 0.57);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:5%;' title='terminada' />" + "</a><br/><b style='font-weight:bold;color:#000;'>solicitud terminada</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_ejecutado'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Terminado' /></div></a>Terminada</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 8) {
                            lst_historial = jpacsol.Traer_solicitudes_con_programacion_detalle(Integer.parseInt(obj_solicitud[8].toString()));
                            if (lst_historial != null) {
                                //out.print("<td align='center' colspan='2' style='background-color:rgba(177, 25, 244, 0.57);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;' title='Seguimiento' />" + "<br/><b style='font-weight:bold;color:#000;'>Agrupada</b></a></td>");
                                out.print("<td align='center' colspan='2'><div class='circulo_agrupada'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div></a>Agrupada</td>");
                            } else {
                                //out.print("<td align='center' colspan='2' style='background-color:rgba(177, 25, 244, 0.57);'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;' title='Seguimiento' /><br/><b style='font-weight:bold;color:#000;'>Agrupada</b></a></td>");
                                out.print("<td align='center' colspan='2'><div class='circulo_agrupada_sin'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div>Agrupada en #" + obj_solicitud[8] + "</td>");
                            }
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 9) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgb(255, 51, 184);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:5%;' title='terminada' />" + "</a><br/><b style='font-weight:bold;color:#000;'>Declinada sin confirmar</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_declinada'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación sin confirmar' /></div></a>Declinación sin confirmar</td>");
                        } else if (Integer.parseInt(obj_solicitud[6].toString()) == 10) {
                            //out.print("<td align='center' colspan='2' style='background-color:rgb(63, 127, 191);'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\">" + "<img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:5%;' title='terminada' />" + "</a><br/><b style='font-weight:bold;color:#000;'>Declinación confirmada</b></td>");
                            out.print("<td align='center' colspan='2'><div class='circulo_declinada_fin'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=1000,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación confirmada' /></div></a>Declinación confirmada</td>");
                        }
                        out.print("<td align=center>");

                        lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitud[0].toString()), "S");
                        if (!nombre_rol.equals("Consulta")) {
                            if ((lst_adjuntos == null) || (lst_adjuntos.isEmpty())) {
                                out.print("<a onclick='mostrar_" + obj_solicitud[0] + "()'><img src='Interfaz/Contenido/Iconos/Adjunto.png' title='Adjuntar archivo' width='15' height='15'></a><div align='center' style='color:#fff;border-radius: 10px;background-color:grey'>0</div>");
                            } else {
                                out.print("<a onclick='mostrar_" + obj_solicitud[0] + "()'><img src='Interfaz/Contenido/Iconos/Adjunto.png' title='Adjuntar archivo' width='15' height='15'></a><div align='center' style='color:#fff;border-radius: 10px;background-color:#b33939'>" + lst_adjuntos.size() + "</div>");
                            }
                        }
                        lst_siglatura_area = jpacare.Traer_area_id(iare);
                        Object[] obj_siglatura = (Object[]) lst_siglatura_area.get(0);
                        out.print("<script type='text/javascript'>function mostrar_" + obj_solicitud[0] + "(){" + "document.getElementById('oculto_" + obj_solicitud[0] + "').style.display = 'block';}" + "</script>");

                        out.print("<script type='text/javascript'>function ocultar_" + obj_solicitud[0] + "(){" + "document.getElementById('oculto_" + obj_solicitud[0] + "').style.display = 'none';}" + "</script>");

                        out.print("<div id='oculto_" + obj_solicitud[0] + "' style='display:none;'>" + "<div style='float:left;'>" + "<fieldset class='resalta_field' style='width:450px;position:absolute;left:60%;'>");

                        out.print("<div style='float: right'><a onclick='ocultar_" + obj_solicitud[0] + "()'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Cancelar' width='20' height='20'></a></div>");
                        if (Integer.parseInt(obj_solicitud[6].toString()) == 1) {
                            out.print("<b>ADJUNTAR</b><br /><form action='Adjunto.jsp' method='post' enctype='multipart/form-data'><input type='hidden' name='Id_origen' value='" + obj_solicitud[0] + "'/>" + "<input type='hidden' name='Siglatura' value='" + obj_siglatura[1] + "'/>" + "<input type='hidden' name='Tipo_origen' value='S'/>" + "<input type='file' name='Txt_adjunto' required/></br>" + "<br /><b>OBSERVACIÓN</b><br />" + "<textarea name='Txt_observacion' id='Txt_observacion' style='width:450px' value='N/A' placeholder='Observación del archivo adjunto' onchange='javascript:this.value=this.value.toUpperCase();'>N/A</textarea>" + "<script type='text/javascript'>var validation = new LiveValidation('Txt_observacion');validation.add( Validate.Presence );</script>" + "<br /><input type='submit' value='Adjuntar' /></form>");
                        }
                        if ((lst_adjuntos == null) || (lst_adjuntos.isEmpty())) {
                            out.print("<b class='naranja'>No hay archivos adjuntos.</b>");
                        } else {
                            out.print("<br />");
                            out.print("<h3>HISTORIAL DE ADJUNTOS</h3>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<th>Adjunto</th>");
                            out.print("<th>Fecha</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("</tr>");
                            for (int m = 0; m < lst_adjuntos.size(); m++) {
                                Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                out.print("<tr>");
                                out.print("<td>");
                                out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a>" + "</td>");

                                out.print("<td>" + obj_adjuntos[6] + "</td>");
                                out.print("<td>" + obj_adjuntos[4] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        }
                        out.print("</fieldset></div></div>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='5' style='background-color:#ddd;'></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 20);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of content -->");
                //</editor-fold>
            } else if ((this.pageContext.getRequest().getAttribute("Historial_solicitud") != null) && (this.pageContext.getRequest().getAttribute("Historial_solicitud").toString().equals("Historial_solicitud"))) {
                //<editor-fold defaultstate="collapsed" desc="DETALLE SOLICITUD HISTORICO">
                id_solicitud = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_solicitud").toString());
                int id_solicitud_principal = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_solicitud_principal").toString());
                lst_historial = (List) this.pageContext.getRequest().getAttribute("lst_historial");
                lst_seguimiento = (List) this.pageContext.getRequest().getAttribute("lst_seguimiento");
                Object[] Obj_seguimiento = (Object[]) lst_historial.get(0);

                out.print("<table class='table2' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td align=\"center\" colspan=\"4\" style=\"width:30%;\"><img src=\"Interfaz/Contenido/images/Logo.png\" alt=\"logo\" style=\"width:170.5px; height:69.5px\"></td>");
                out.print("<td align=\"center\" colspan=\"4\" style=\"width:40%;\"><b>" + Obj_seguimiento[5] + "</b></td>");
                out.print("<td align=\"center\" colspan=\"4\" style=\"width:30%;\">");
                out.print("<b>Fecha Registro:</b>  " + Obj_seguimiento[2] + "<br>");
                if (Integer.parseInt(Obj_seguimiento[8].toString()) == 3) {
                    out.print("<b>Estado:</b> Programado <br>");
                } else if (Integer.parseInt(Obj_seguimiento[8].toString()) == 4) {
                    out.print("<b>Estado:</b> Pendiente <br>");
                } else if (Integer.parseInt(Obj_seguimiento[8].toString()) == 5) {
                    out.print("<b>Estado:</b> Ejecutada <br>");
                } else if (Integer.parseInt(Obj_seguimiento[8].toString()) == 6) {
                    out.print("<b>Estado:</b> En Seguimiento <br>");
                } else if (Integer.parseInt(Obj_seguimiento[8].toString()) == 7) {
                    out.print("<b>Estado:</b> Terminada <br>");
                } else if (Integer.parseInt(Obj_seguimiento[8].toString()) == 8) {
                    out.print("<b>Estado:</b> Agrupada <br>");
                }
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan=\"8\"><b>Solicitante:</b>" + Obj_seguimiento[3] + "<br>");
                out.print("<b>Ubicación:</b>" + Obj_seguimiento[4] + "</br>");
                out.print("<b>#:</b> " + Obj_seguimiento[0] + "</br></td>");
                out.print("<td>");
                List Couter = jpacsol.Contador_act(id_solicitud);
                int S = 0;
                int N = 0;
                for (int i = 0; i < Couter.size(); i++) {
                    Object[] Contador = (Object[]) Couter.get(i);
                    if ("NO".equals(Contador[2].toString())) {
                        N++;
                    } else {
                        S++;
                    }
                }
                out.print("<b>Ejecutado: </b>" + S + "<br />");
                out.print("<b>No Ejecutado: </b>" + N + "");
                out.print("</td>");
                out.print("<td valing=\"top\">");

                out.print("<b>Anexo: </b><br />");
                lst_adjuntos = jpacevd.Adjuntos_origen(id_solicitud, "S");
                if (lst_adjuntos != null) {
                    for (int m = 0; m < lst_adjuntos.size(); m++) {
                        Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                        out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                    }
                } else {
                    out.print("No hay adjuntos");
                }
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");

                out.print("<table class='table2' style='width:100%;'>");
                if (id_solicitud_principal == 0) {
                    if ((Integer.parseInt(Obj_seguimiento[8].toString()) == 5) && (!nombre_rol.equals("Consulta"))) {
                        out.print("<tr>");
                        out.print("<td align='right' colspan='10'><b>terminado </b><a href='Solicitud?opc=9&Id_solicitud=" + id_solicitud + "'><img src='Interfaz/Contenido/Iconos/Check.png' title='Dar solicitud como terminada' width='20' height='20'></a>");
                        out.print("<b> seguimiento</b><a id='mostrar' onclick='mostrar_" + id_solicitud + "();'><img src='Interfaz/Contenido/Iconos/Plus.png' id='cambiar' title='Enviar solicitud a seguimiento' width='20' height='20'></a></td>");
                        out.print("</tr>");
                    }
                } else if ((Integer.parseInt(Obj_seguimiento[8].toString()) == 5) && (id_solicitud_principal == id_solicitud) && (!nombre_rol.equals("Consulta"))) {
                    out.print("<tr>");
                    out.print("<td align='right' colspan='10'><b>terminado </b><a href='Solicitud?opc=9&Id_solicitud=" + id_solicitud + "'><img src='Interfaz/Contenido/Iconos/Check.png' title='Dar solicitud como terminada' width='20' height='20'></a>");
                    out.print("<b> seguimiento</b>");
                    out.print("<a  id='mostrar' onclick='mostrar_" + id_solicitud + "();'><img src='Interfaz/Contenido/Iconos/Plus.png' style='width:26px;height:26px' alt='edit' title='seguimiento'/></a></td>");
                    out.print("</tr>");
                }
                out.print("<tr>");
                List lst_historial_seguimiento = jpacsol.traer_solicitudes_con_seguimiento(id_solicitud);
                out.print("<th colspan='5'>Programacion</th>");
                out.print("<th colspan='5'>Actividades</th>");
                if (lst_historial_seguimiento != null) {
                    out.print("<th>seguimiento</th>");
                }
                out.print("</tr>");
                for (int i = 0; i < lst_historial.size(); i++) {
                    Object[] obj_historial = (Object[]) lst_historial.get(i);
                    out.print("<tr>");
                    out.print("<td colspan=\"5\" style=\"width:20%;\">");
                    List list_progrmacion_solicitud = jpapro.Traer_progrmacion_p_solictud(Integer.parseInt(obj_historial[1].toString()));
                    Object[] rograma_soli = (Object[]) list_progrmacion_solicitud.get(0);
                    out.print("<b>Nombre: </b>" + rograma_soli[1] + "</br>");
                    out.print("<b>Fecha Inicio: </b>" + rograma_soli[2] + "</br>");
                    out.print("<b>Fecha Fin: </b>" + rograma_soli[3] + "</br>");
                    if (Integer.parseInt(rograma_soli[5].toString()) == 1) {
                        out.print("<b>Estado: </b>Programada</br>");
                    } else if (Integer.parseInt(rograma_soli[5].toString()) == 2) {
                        out.print("<b>Estado: </b>Ejecucion</br>");
                    } else if (Integer.parseInt(rograma_soli[5].toString()) == 3) {
                        out.print("<b>Estado: </b>Revision</br>");
                    } else if (Integer.parseInt(rograma_soli[5].toString()) == 4) {
                        out.print("<b>Estado: </b>Cerrada</br>");
                    }
                    out.print("</td>");
                    lst_historial_actividades = jpacsol.Traer_actividades_solicitudes(Integer.parseInt(obj_historial[1].toString()));
                    out.print("<td colspan=\"5\" style=\"width:60%;\">");

                    out.print("<table class='table'>");
                    if (lst_historial_actividades != null) {
                        out.print("<tr>");
                        out.print("<td align='center' colspan=\"5\" style=\"width:55%;\"><b>Actividades</b></td>");
                        out.print("<td align='center' colspan=\"5\" style=\"width:15%;\"><b>Area lista</b></td>");
                        out.print("<td align='center' colspan=\"5\" style=\"width:15%;\"><b>Ejecutado</b></td>");
                        out.print("<td align='center' colspan=\"5\" style=\"width:15%;\"><b>Observacion</b></td>");
                        out.print("</tr>");
                        for (int j = 0; j < lst_historial_actividades.size(); j++) {
                            Object[] obj_historial_actividades = (Object[]) lst_historial_actividades.get(j);
                            out.print("<tr>");
                            out.print("<td colspan=\"5\" style=\"width:55%;\">" + obj_historial_actividades[2] + "</td>");
                            out.print("<td align='center' colspan=\"5\" style=\"width:15%;\">" + obj_historial_actividades[3] + "</td>");
                            out.print("<td align='center' colspan=\"5\" style=\"width:15%;\">" + (obj_historial_actividades[5] == null ? "<b class='naranja'>sin ejecución</b>" : obj_historial_actividades[5]) + "</td>");
                            if ((obj_historial_actividades[4] == null) || (obj_historial_actividades[4] == "")) {
                                out.print("<td align='center' colspan=\"5\" style=\"width:15%;\">Ninguna</td>");
                            } else {
                                out.print("<td align='center' colspan=\"5\" style=\"width:15%;\">" + obj_historial_actividades[4] + "</td>");
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    if (lst_historial_seguimiento != null) {
                        for (int j = 0; j < lst_historial_seguimiento.size(); j++) {
                            Object[] obj_historial_seguimiento = (Object[]) lst_historial_seguimiento.get(j);
                            out.print("<td colspan=\"5\" style=\"width:20%;\">");
                            out.print("<b>Causa del seguimiento:</b><hr>" + obj_historial_seguimiento[3] + "");
                            List lst_descargar_adjuntos = jpacevd.Traer_evidencia_seguimiento(id_solicitud);
                            if (lst_descargar_adjuntos != null) {
                                out.print("<dir/>");
                                for (int k = 0; k < lst_descargar_adjuntos.size(); k++) {
                                    Object[] obj_descargar_adjuntos = (Object[]) lst_descargar_adjuntos.get(k);
                                    out.print("<a href='Descargar?file_name=" + obj_descargar_adjuntos[0] + "&ruta_proyecto=" + obj_descargar_adjuntos[2] + "\\R\\'>" + obj_descargar_adjuntos[0] + "</a></br>");
                                }
                            }
                            out.print("</td>");
                        }
                    }
                }
                out.print("</table>");
                if (Integer.parseInt(Obj_seguimiento[8].toString()) == 5) {
                    out.println("<dir />");
                    if (id_solicitud_principal == 0) {
                        lst_solicitud_detalle = jpacsol.Traer_solicitudes_con_programacion_detalle(id_solicitud);
                    } else {
                        lst_solicitud_detalle = jpacsol.Traer_solicitudes_con_programacion_detalle(id_solicitud_principal);
                    }
                    Object[] obj_solicitud_detalle = (Object[]) lst_solicitud_detalle.get(0);
                    lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitud_detalle[0].toString()), "R");
                    out.print("<script type='text/javascript'>");
                    out.print("function mostrar_" + id_solicitud + "(){");
                    out.print("document.getElementById('oculto_" + id_solicitud + "').style.display = 'block';}");
                    out.print("</script>");
                    out.print("<script type='text/javascript'>");
                    out.print("function ocultar_" + id_solicitud + "(){");
                    out.print("document.getElementById('oculto_" + id_solicitud + "').style.display = 'none';}");
                    out.print("</script>");

                    out.print("<div id='oculto_" + id_solicitud + "' style='display:none;'>");
                    out.print("<fieldset class='resalta_field' style='width:400px;position:absolute;top:268px;left:68%;'>");
                    out.print("<div style='float:right'><a onclick='ocultar_" + id_solicitud + "()'><img src='Interfaz/Contenido/Iconos/Min.png' title='Minimizar' width='20' height='20'></a></div>");
                    out.print("<h3>Enviar solicitud a seguimiento</h3>");
                    out.print("<table class='table' style='width:30%'>");
                    out.print("<form action ='Solicitud?opc=8' id='form_seguimiento' method='post' >");
                    out.print("<input type='hidden' name='Id_solicitud' value='" + id_solicitud + "' id='Id_solicitud'/>");
                    out.print("<tr>");
                    out.print("<td><b>Fecha :</b></br>");
                    out.print("<input type='text' name='Txt_fecha_seguimiento' readonly id='Txt_fecha_seguimiento' value='" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() < 10 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() < 10 ? "-0" : "-") + "" + fecha.getDate() + "' title='fecha'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script>");

                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Solicitante :</b></br>");
                    out.print("<input type='text' name='Txt_solicitante' value='" + nombre_usuario.toString().toUpperCase() + "' id='Txt_solicitante' readonly  title='Solicitante' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_solicitante');val1.add(Validate.Presence);</script>");

                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>ubicación :</b></br>");
                    out.print("<input type='text' name='Txt_ubicacion' value='" + Obj_seguimiento[4] + "' id='Txt_ubicacion' readonly  title='ubicación' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);</script>");

                    out.print("</tr>");
                    out.print("<input type='hidden' name='Txt_clasificacion' value='SEGUIMIENTO' id='Txt_clasificacion' readonly onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<input type='hidden' name='Id_solicitud' value='" + Obj_seguimiento[0] + "' id='Id_solicitud' readonly onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<tr>");
                    out.print("<td><b>Por que desea enviar la solicitud a seguimiento?</b></br>");
                    out.print("<textarea id='Txt_observacion' name='Txt_observacion' style='width:300px;text-transform:uppercase;'></textarea></td>");
                    out.print("</form>");
                    out.print("<td align=center>");
                    if ((lst_adjuntos == null) || (lst_adjuntos.isEmpty())) {
                        out.print("<a onclick='abrir_" + obj_solicitud_detalle[0] + "()'><img src='Interfaz/Contenido/Iconos/Adjunto.png' title='Adjuntar archivo' width='15' height='15'></a><div align='center' style='color:#fff;border-radius: 10px;background-color:grey'>0</div>");
                    } else {
                        out.print("<a onclick='abrir_" + obj_solicitud_detalle[0] + "()'><img src='Interfaz/Contenido/Iconos/Adjunto.png' title='Adjuntar archivo' width='15' height='15'></a><div align='center' style='color:#fff;border-radius: 10px;background-color:#b33939'>" + lst_adjuntos.size() + "</div>");
                    }
                    lst_siglatura_area = jpacare.Traer_area_id(iare);
                    Object[] obj_siglatura = (Object[]) lst_siglatura_area.get(0);
                    out.print("<script type='text/javascript'>function abrir_" + obj_solicitud_detalle[0] + "(){" + "document.getElementById('esconder_" + obj_solicitud_detalle[0] + "').style.display = 'block';}" + "</script>");

                    out.print("<script type='text/javascript'>function cerrar_" + obj_solicitud_detalle[0] + "(){" + "document.getElementById('esconder_" + obj_solicitud_detalle[0] + "').style.display = 'none';}" + "</script>");

                    out.print("<div id='esconder_" + obj_solicitud_detalle[0] + "' style='display:none;'>");
                    out.print("<fieldset class='resalta_field' style='width:450px;position:absolute;left:-39%;'>");
                    out.print("<div style='float:right'><a onclick='cerrar_" + obj_solicitud_detalle[0] + "()'><img src='Interfaz/Contenido/Iconos/Min.png' title='Cancelar' width='20' height='20'></a></div>");
                    if ((Integer.parseInt(obj_solicitud_detalle[8].toString()) == 5) || (Integer.parseInt(obj_solicitud_detalle[8].toString()) == 6)) {
                        out.print("<b>ADJUNTAR</b><br /><form action='Adjunto.jsp' method='post' enctype='multipart/form-data'><input type='hidden' name='Id_origen' value='" + obj_solicitud_detalle[0] + "'/>" + "<input type='hidden' name='Siglatura' value='" + obj_siglatura[1] + "'/>" + "<input type='hidden' name='Tipo_origen' value='R'/>" + "<input type='file' name='Txt_adjunto' required/></br>" + "<br /><b>OBSERVACIÓN</b><br />" + "<textarea name='Txt_observacion' id='Txt_observacion' style='width:450px' value='N/A' placeholder='Observación del archivo adjunto' onchange='javascript:this.value=this.value.toUpperCase();'>N/A</textarea>" + "<script type='text/javascript'>var validation = new LiveValidation('Txt_observacion');validation.add( Validate.Presence );</script>" + "<br /><input type='submit' value='Adjuntar' /></form>");
                    }
                    if ((lst_adjuntos == null) || (lst_adjuntos.isEmpty())) {
                        out.print("<b class='naranja'>No hay archivos adjuntos.</b>");
                    } else {
                        out.print("<br />");
                        out.print("<h3>HISTORIAL DE ADJUNTOS</h3>");
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>Adjunto</th>");
                        out.print("<th>Fecha</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("</tr>");
                        for (int m = 0; m < lst_adjuntos.size(); m++) {
                            Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                            out.print("<tr>");
                            out.print("<td>");
                            out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_siglatura[1] + "\\R\\'>" + obj_adjuntos[3] + "</a>" + "</td>");

                            out.print("<td>" + obj_adjuntos[6] + "</td>");
                            out.print("<td>" + obj_adjuntos[4] + "</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    }
                    out.print("</fieldset></div></div>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><input type='submit' onclick='javascript:form_seguimiento.submit();' value='Enviar a segimiento'/></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</fieldset></div></div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="CONSULTAR POR ESTADOS SOLICITUDES">
            if (this.pageContext.getRequest().getAttribute("Consultar") != null) {
                if (this.pageContext.getRequest().getAttribute("Consultar").toString().equals("Consultar_solicitudes")) {
                    out.print("<div id='content_sin'>");
                    int estado = Integer.parseInt(this.pageContext.getRequest().getAttribute("Estado").toString());
                    String fecha_inicio = this.pageContext.getRequest().getAttribute("Fecha_inicio").toString();
                    String fecha_fin = this.pageContext.getRequest().getAttribute("Fecha_fin").toString();
                    out.print("<h3>Consultar solicitudes | <a href='#' onclick=\"mostrar('3')\"><i >Convenciones</i></a></h3>");
                    //<editor-fold defaultstate="collapsed" desc="CONVENCIONES ESTADOS">
                    out.print("<div id='emergente3' style='width: 600px; display:none; padding-left: 15px; padding-right: 20px; margin-left: 10%; margin-top: 0%; border:solid 1px #b33939;background-color:#fff; position: absolute;'>");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr><th>Estado</th>");
                    out.print("<th>Descripción</th></tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_editar'><img src='Interfaz/Contenido/Iconos/Edit.png' style='height:20px; width:20px;margin-top:13px;' title='Editar' /></div>Editar</td>");
                    out.print("<td>La solicitud que se encuentra en edición para cambios previos a la programación</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' ><div class='circulo_enviar'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:20px;margin-top:17px;' title='Enviado' /></div>Enviado</td>");
                    out.print("<td>La solicitud se envio a Programación y esta a espera para ser programada. </td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_programado'><img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top:13px;' title='Programado' /></div>Programado</td>");
                    out.print("<td>La solicitud que se encuentra en Programación</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_pendiente'><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Pendiente ó Seguimiento' /></div>Pendiente ó Seguimiento</td>");
                    out.print("<td><b>Pendiente : </b>Cuando la solicitud se encuentra en estado pediente, indica que se prolongo la fecha de la realización del locativo<hr /><b>Seguimiento: </b>Cuando la solicitud esta en seguimiento, indica que va pasar por el mismo proceso.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_ejecutado'><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Ejecutado ó Terminada' /></div>Ejecutado ó Terminada</td>");
                    out.print("<td><b>Ejecutado : </b>Indica que el programador ya realizo la confirmación de la solicitud, por consiguiente el usuario tendra la opción de dar por terminada o en seguimiento la solicitud. <hr /><b>Terminado: </b>Solicitud recibida a conformidad por el usuario</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_agrupada_sin'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div><div class='circulo_agrupada'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div>Agrupada</td>");
                    out.print("<td>Cuando la solicitud esta  agrupada, indica que esta relacionado con una solicitud a la cual se realiza gestión locativa y replicara la solución a las agrupadas</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='circulo_declinada'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación sin confirmar' /></div><div class='circulo_declinada_fin'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación confirmada' /></div>Declinación</td>");
                    out.print("<td><b>Sin confirmar : </b>Indica que la solicitud ha sido declinada ya sea por el ejecutor o el programador, esperando una confirmación <hr /><b>Confirmado : </b>Indicar que la solicitud ha sido efectuada y ya no se toma en cuenta para las programaciones de actividades locativas</td>");
                    out.print("</tr>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("<form method='Post' action='Solicitud?opc=10' id='form_consulta'>");
                    if (estado > 0) {
                        lst_solicitudes = jpacsol.Solicitudes_estado(estado, fecha_inicio, fecha_fin);
                    } else {
                        lst_solicitudes = jpacsol.Traer_todas_las_solicitudes();
                    }
                    out.print("<div style='display:block'>");
                    //                    //<editor-fold defaultstate="collapsed" desc="OLD">
//                    out.print("<div style='float:left;width:150px;'>");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='1' " + (estado == 1 ? "checked" : "") + " />" + (estado == 1 ? "<b>" : "") + "1) EN EDICION" + (estado == 1 ? "</b>" : "") + "<br />");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='2' " + (estado == 2 ? "checked" : "") + " />" + (estado == 2 ? "<b>" : "") + "2) ENVIADAS" + (estado == 2 ? "</b>" : "") + "<br />");
//                    out.print("</div>");
//                    out.print("<div style='float:left;width:150px;'>");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='3' " + (estado == 3 ? "checked" : "") + "/>" + (estado == 3 ? "<b>" : "") + "3) PROGRAMADAS" + (estado == 3 ? "</b>" : "") + "<br />");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='5' " + (estado == 5 ? "checked" : "") + "/>" + (estado == 5 ? "<b>" : "") + "4) EJECUTADAS" + (estado == 5 ? "</b>" : "") + "<br />");
//                    out.print("</div>");
//                    out.print("<div style='float:left;width:150px;'>");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='4' " + (estado == 4 ? "checked" : "") + "/>" + (estado == 4 ? "<b>" : "") + "5) PENDIENTES" + (estado == 4 ? "</b>" : "") + "<br />");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='6' " + (estado == 6 ? "checked" : "") + "/>" + (estado == 6 ? "<b>" : "") + "6) EN SEGUIMIENTO" + (estado == 6 ? "</b>" : "") + "<br />");
//                    out.print("</div>");
//                    out.print("<div style='float:left;width:150px;'>");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='7' " + (estado == 7 ? "checked" : "") + "/>" + (estado == 7 ? "<b>" : "") + "7) TERMINADAS" + (estado == 7 ? "</b>" : "") + "<br />");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='8' " + (estado == 8 ? "checked" : "") + "/>" + (estado == 8 ? "<b>" : "") + "8) AGRUPADAS" + (estado == 8 ? "</b>" : "") + "<br />");
//                    out.print("</div>");
//                    out.print("<div style='float:left;width:150px;'>");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='0' " + (estado == 0 ? "checked" : "") + "/>" + (estado == 0 ? "<b>" : "") + "9) TODAS" + (estado == 0 ? "</b>" : "") + "<br />");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='9' " + (estado >= 9 ? "checked" : "") + "/>" + (estado >= 9 ? "<b>" : "") + "10) DECLINADAS" + (estado >= 0 ? "</b>" : "") + "<br />");
//                    out.print("</div>");
////</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="OPCIONES">
                    out.print("<div style='float:left;width:150px;'>");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='1' " + (estado == 1 ? "checked" : "") + " />" + (estado == 1 ? "<b>" : "") + "1) EN EDICION" + (estado == 1 ? "</b>" : "") + "<br />");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='2' " + (estado == 2 ? "checked" : "") + " />" + (estado == 2 ? "<b>" : "") + "2) ENVIADAS" + (estado == 2 ? "</b>" : "") + "<br />");
                    out.print("</div>");
                    out.print("<div style='float:left;width:150px;'>");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='3' " + (estado == 3 ? "checked" : "") + "/>" + (estado == 3 ? "<b>" : "") + "3) PROGRAMADAS" + (estado == 3 ? "</b>" : "") + "<br />");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='5' " + (estado == 5 ? "checked" : "") + "/>" + (estado == 5 ? "<b>" : "") + "4) EJECUTADAS" + (estado == 5 ? "</b>" : "") + "<br />");
                    out.print("</div>");
                    out.print("<div style='float:left;width:150px;'>");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='4' " + (estado == 4 ? "checked" : "") + "/>" + (estado == 4 ? "<b>" : "") + "5) PENDIENTES" + (estado == 4 ? "</b>" : "") + "<br />");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='8' " + (estado == 8 ? "checked" : "") + "/>" + (estado == 8 ? "<b>" : "") + "6) AGRUPADAS" + (estado == 8 ? "</b>" : "") + "<br />");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='6' " + (estado == 6 ? "checked" : "") + "/>" + (estado == 6 ? "<b>" : "") + "6) EN SEGUIMIENTO" + (estado == 6 ? "</b>" : "") + "<br />");
                    out.print("</div>");
//                    out.print("<div style='float:left;width:150px;'>");
//                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='7' " + (estado == 7 ? "checked" : "") + "/>" + (estado == 7 ? "<b>" : "") + "7) TERMINADAS" + (estado == 7 ? "</b>" : "") + "<br />");
//                    out.print("</div>");
                    out.print("<div style='float:left;width:150px;'>");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='0' " + (estado == 0 ? "checked" : "") + "/>" + (estado == 0 ? "<b>" : "") + "7) TODAS" + (estado == 0 ? "</b>" : "") + "<br />");
                    out.print("<input type='radio' name='rdo_estado' onclick='javascript:form_consulta.submit();' value='9' " + (estado >= 9 ? "checked" : "") + "/>" + (estado >= 9 ? "<b>" : "") + "8) DECLINADAS" + (estado >= 0 ? "</b>" : "") + "<br />");
                    out.print("</div>");
////</editor-fold>
                    out.print("<div style='float:right;width:35%'><b>Fecha Inicio : </b><input style='width:80px' type='text' name='Txt_fecha_inicio' id='start' value='" + fecha_inicio + "'/>");
                    out.print("&nbsp&nbsp&nbsp&nbsp<b>Fecha Fin : </b><input style='width:80px' type='text' align='right' name='Txt_fecha_fin' id='end' value='" + fecha_fin + "'/>" + "&nbsp&nbsp&nbsp&nbsp<a onclick='javascript:form_consulta.submit();'><img src=\"Interfaz/Contenido/Iconos/Update.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Filtrar' /></a>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form><br /><br /><br /><hr />");
                    if (lst_solicitudes == null) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>No existe ningun locativos en este estado</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div style='float:right'><input type='text' align='left' onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' style='margin-bottom: 0' placeholder='Buscar'/>");
                        out.print("&ensp;&ensp;&ensp;<a onclick='Imprimir();'><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a>");
                        out.print("</div>");
                        out.print("<br /><br /><br /><br /><div id='NavPosicion'></div>");
                        out.print("<table id='resultados' class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td colspan='6'>");
                        if ((estado == 2) || (estado == 0)) {
                            out.print("<form action='Solicitud?opc=11' method='post'>");
                            out.print("<input type='submit' value='Agrupar Solicitudes'/>");
                            out.print("<input type='hidden' name='Id_solicitudes_agrupar' id='Id_solicitudes_agrupar' onclick=\"this.focus();this.select();\" />");
                            out.print("<input type='hidden' name='Fecha_inicio' id='Fecha_inicio' value='" + fecha_inicio + "' />");
                            out.print("<input type='hidden' name='Fecha_fin' id='Fecha_fin' value='" + fecha_fin + "'/></div>");
                            out.print("</form>");
                        }
                        out.print("</td>");
                        out.print("<td colspan='2'>");
                        if ((estado == 2) || (estado == 0)) {
                            out.print("<form action='Solicitud?opc=13' method='post'>");
                            out.print("<input type='hidden' name='Id_Solicitudes_Programar' id='Id_Solicitudes_Programar' onclick=\"this.focus();this.select();\" />");
                            out.print("<input type='submit' value='Programar'/>");
                            out.print("</form>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_solicitudes.size(); i++) {
                            Object[] obj_solicitud = (Object[]) lst_solicitudes.get(i);
                            out.print("<tr>");
                            if (Integer.parseInt(obj_solicitud[6].toString()) == 2) {
                                out.print("<td align='center'><input type='checkbox' name='box" + obj_solicitud[0] + "' id='box" + obj_solicitud[0] + "' value='[" + obj_solicitud[0] + "]' onclick=\"agrupar_solicitudes(this);\" /><b>#" + obj_solicitud[0] + "</b></td>");
                            } else {
                                out.print("<td align='center'><b> #" + obj_solicitud[0] + "</b></td>");
                            }
                            out.print("<th style='width:100px'>" + obj_solicitud[1] + "</th>");
                            out.print("<td style='width:150px'><b>Solicitante:</b> </br> " + obj_solicitud[2] + "</td>");
                            out.print("<td style='width:300px;' valign='top'><b> Descripción:</b></br>  " + obj_solicitud[4] + "</td>");
                            out.print("<td style='width:250px;' valign='top'><b> Ubicación :</b></br>  " + obj_solicitud[3] + "<b>-</b>" + obj_solicitud[7] + "</td>");
                            out.print("<td  valign='top'><b>Clasificación:</b></br> " + obj_solicitud[5] + "</br></td>");
                            if (Integer.parseInt(obj_solicitud[6].toString()) == 1) {
                                out.print("<td align='center' colspan='2'><div class='circulo_editar'><img src='Interfaz/Contenido/Iconos/Edit.png' style='height:20px; width:20px;margin-top:13px;' title='Editar' /></div>Editar</td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 2) {
                                out.print("<td align='center' colspan='2' ><div class='circulo_enviar'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:20px;margin-top:17px;' title='Enviado' /></div>Enviado</td>");
                                out.print("</td>");
                                out.print("<td><input type='checkbox' name='box" + obj_solicitud[0] + "' id='box" + obj_solicitud[0] + "' value='[" + obj_solicitud[0] + "]' onclick=\"Programar_Solicitudes(this);\" /></b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 3) {
                                out.print("<td align='center' colspan='2'><div class='circulo_programado'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top:13px;' title='Programado' /></div></a>Programado</td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 4) {
                                out.print("<td align='center' colspan='2'><div class='circulo_pendiente'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Pendiente' /></div></a>Pendiente</td>");
                                out.print("</td>");
                                out.print("<td><input type='checkbox' name='box" + obj_solicitud[0] + "' id='box" + obj_solicitud[0] + "' value='[" + obj_solicitud[0] + "]' onclick=\"Programar_Solicitudes(this);\" /></b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 5) {
                                out.print("<td align='center' colspan='2'><div class='circulo_ejecutado'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Ejecutado' /></div></a>Ejecutado</td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 6) {
                                out.print("<td align='center' colspan='2'><div class='circulo_pendiente'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Seguimiento' /></div></a>Seguimiento</td>");
                                out.print("</td>");
                                out.print("<td><input type='checkbox' name='box" + obj_solicitud[0] + "' id='box" + obj_solicitud[0] + "' value='[" + obj_solicitud[0] + "]' onclick=\"Programar_Solicitudes(this);\" /></b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 7) {
                                out.print("<td align='center' colspan='2'><div class='circulo_ejecutado'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Terminado' /></div></a>Terminada</td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 8) {
                                lst_historial = jpacsol.Traer_solicitudes_con_programacion_detalle(Integer.parseInt(obj_solicitud[8].toString()));
                                if (lst_historial != null) {
                                    out.print("<td align='center' colspan='2'><div class='circulo_agrupada'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div></a>Agrupada</td>");
                                } else {
                                    out.print("<td align='center' colspan='2'><div class='circulo_agrupada_sin'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div>Agrupada en #" + obj_solicitud[8] + "</td>");
                                }
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 9) {
                                out.print("<td align='center' colspan='2'><div class='circulo_declinada'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación sin confirmar' /></div></a>Declinación sin confirmar</td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 10) {
                                out.print("<td align='center' colspan='2'><div class='circulo_declinada_fin'><a href=\"javascript:window.open('Solicitud?opc=6&Id_solicitud_principal=" + obj_solicitud[8] + "&Id_solicitud=" + obj_solicitud[0] + "','','width=2724,height=300,left=50,top=50,toolbar=yes');void 0\"><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación confirmada' /></div></a>Declinación confirmada</td>");
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            }
                            if (Integer.parseInt(obj_solicitud[6].toString()) < 9 && Integer.parseInt(obj_solicitud[6].toString()) != 5) {
                                out.print("<td align='center'><img onclick='DeclinarSolicitud(" + obj_solicitud[0] + ")' src='Interfaz/Contenido/Iconos/Delete.png' style='height:20px; width:20px;' title='Declinar Solicitud' /></td>");
                            } else {
                                out.print("<td align='center'><b class='naranja'>---</b></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");

                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 8);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        out.print("<div id='Imprimir' style='display:none'>");
                        out.print("<table class='table' style='width:100%'>");
                        for (int i = 0; i < lst_solicitudes.size(); i++) {
                            Object[] obj_solicitud = (Object[]) lst_solicitudes.get(i);
                            out.print("<tr>");
                            out.print("<th style='width:100px'> <b style='color:#fff'> " + obj_solicitud[1] + " </b></br>#" + obj_solicitud[0] + "</th>");
                            out.print("<td style='width:150px'><b>Solicitante:</b> </br> " + obj_solicitud[2] + "</td>");
                            out.print("<td style='width:300px;' valign='top'><b> Descripción:</b></br>  " + obj_solicitud[4] + "</td>");
                            out.print("<td style='width:300px;' valign='top'><b> Ubicación :</b></br>  " + obj_solicitud[3] + "<b>-</b>" + obj_solicitud[7] + "</td>");
                            out.print("<td  valign='top'><b>Clasificación:</b></br> " + obj_solicitud[5] + "</br></td>");
                            if (Integer.parseInt(obj_solicitud[6].toString()) == 1) {
                                out.print("<td align='center'><div class='circulo_editar'><img src='Interfaz/Contenido/Iconos/Edit.png' style='height:20px; width:20px;margin-top:13px;' title='Editar' /></div>Editar</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 2) {
                                out.print("<td align='center' ><div class='circulo_enviar'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:20px;margin-top:17px;' title='Enviado' /></div>Enviado</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 3) {
                                out.print("<td align='center'><div class='circulo_programado'><img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top:13px;' title='Programado' /></div>Programado</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 4) {
                                out.print("<td align='center'><div class='circulo_pendiente'><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Pendiente' /></div>Pendiente</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 5) {
                                out.print("<td align='center'><div class='circulo_ejecutado'><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Ejecutado' /></div>Ejecutado</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 6) {
                                out.print("<td align='center'><div class='circulo_pendiente'><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top:13px;' title='Seguimiento' /></div>Seguimiento</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 7) {
                                out.print("<td align='center'><div class='circulo_ejecutado'><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top:13px;' title='Terminado' /></div>Terminada</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 8) {
                                out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;margin-top:13px;' title='Agrupada' /></div>Agrupada</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 9) {
                                out.print("<td align='center'><div class='circulo_declinada'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación sin confirmar' /></div>Declinación sin confirmar</td>");
                            } else if (Integer.parseInt(obj_solicitud[6].toString()) == 10) {
                                out.print("<td align='center'><div class='circulo_declinada_fin'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;margin-top:13px;' title='Declinación confirmada' /></div>Declinación confirmada</td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR PROGRAMACIÓN">
                if (this.pageContext.getRequest().getAttribute("Programacion").toString().equals("Registro_programacion")) {
                    Id_Solicitudes_Programar = this.pageContext.getRequest().getAttribute("Id_Solicitudes_Programar").toString();
                    if (Id_Solicitudes_Programar != "") {
                        out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                        out.print("<center><fieldset align='left' style='overflow:scroll;width:70%;height:600px;margin-top:50px' >");
                        out.print("<form action='Solicitud?opc=14' method='post' name='Form_asignar' id='Form_asignar' onsubmit='registroS();'>");
                        out.println("<input type='hidden' name='Id_solicitudes' id='Id_solicitudes' value='" + Id_Solicitudes_Programar + "' />");
                        out.print("<div style='float:right'><a href='Solicitud?opc=10&rdo_estado=0'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Cancelar'/></a></div>");
                        out.print("<input type='submit' id='btsubmit' value='Asignar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n          <div></div>\n          <div></div>\n          <div></div>\n        </div>");

                        out.print("<h3>Asignación de locativos</h3>");
                        lst_programaciones = jpacpro.Consultar_Programacion();
                        if (lst_programaciones == null) {
                            out.print("<center>");
                            out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                            out.print("<b>No existe ninguna programacion</b>");
                            out.print("</center>");
                        } else {
                            out.print("<table class='table'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%'></td>");
                            out.print("<th style='width:10%'>#</td>");
                            out.print("<th style='width:30%'>Nombre</td>");
                            out.print("<th style='width:50%'>Observacion</td>");
                            out.print("</tr>");
                            int coun_chk = 0;
                            for (int i = 0; i < lst_programaciones.size(); i++) {
                                Object[] Programaciones = (Object[]) lst_programaciones.get(i);
                                if (Programaciones[5].equals(Integer.valueOf(1))) {
                                    out.print("<tr>");
                                    if (coun_chk == 0) {
                                        out.print("<td><input type=\"radio\" name=\"Id_programacion\" value=\"" + Programaciones[0] + "\" checked></td>");
                                    } else {
                                        out.print("<td><input type=\"radio\" name=\"Id_programacion\" value=\"" + Programaciones[0] + "\"></td>");
                                    }
                                    out.print("<td>" + Programaciones[0] + "</td>");
                                    out.print("<td>" + Programaciones[1] + "</td>");
                                    out.print("<td>" + Programaciones[4] + "</td>");
                                    out.print("</tr>");
                                    coun_chk++;
                                }
                            }
                            out.print("</table>");
                        }
                        out.print("</form>");
                        out.print("</fieldset></center>");
                        out.print("</div>");
                    }
                }
                //</editor-fold>
            }
            //</editor-fold> 
            //<editor-fold defaultstate="collapsed" desc="AGRUPAR SOLICITUDES">
            if ((this.pageContext.getRequest().getAttribute("Agrupar") != null) && (this.pageContext.getRequest().getAttribute("Agrupar").toString().equals("Agrupar_solicitudes"))) {
                int solicitudes_principal = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_solicitud_principal").toString());
                String solicitudes_agrupadas = this.pageContext.getRequest().getAttribute("Id_solicitudes_agrupar").toString();
                String id_solicitudes_ag = solicitudes_agrupadas.toString().replace("][", "-").replace("[", "").replace("]", "");
                String[] vector_Solicitud = id_solicitudes_ag.split("-");
                out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                out.print("<center><fieldset align='left' style='overflow:scroll;width:70%;height:600px;margin-top:50px' >");
                out.print("<h3><a href='Solicitud?opc=10&rdo_estado=0&Txt_fecha_inicio=" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() <= 9 ? "-" : "-") + "" + (fecha.getMonth() + 1) + "-01&Txt_fecha_fin=" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() <= 9 ? "-" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() <= 9 ? "-" : "-") + "" + fecha.getDate() + "'><img src='Interfaz/Contenido/Iconos/Volver.png' style='height:20px; width:20px;' title='salir' /></a>Solicitudes Agrupadas</h3>");
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PRINCIPAL">
                if (solicitudes_principal != 0) {
                    out.print("<h3>Solicitud principal</h3>");
                    lst_solicitudes = jpacsol.Traer_Solicitud(solicitudes_principal);
                    Object[] obj_solicitud = (Object[]) lst_solicitudes.get(0);
                    out.print("<form action='Solicitud?opc=4&opc2=1&Id_solicitud=" + solicitudes_principal + "&Solicitudes_agrupadas=" + solicitudes_agrupadas + "' method='post'>");
//                    out.print("<input type='hidden' name='Id_solicitud' id='Id_solicitud' value='" + solicitudes_principal + "'/>");
//                    out.print("<input type='hidden' name='Id_solicitudes_agrupar' id='Id_solicitudes_agrupar' value='" + solicitudes_agrupadas + "'/>");
                    out.print("<table>");
                    out.print("<tr>");
                    out.print("<td><b>Fecha :</b></td>");
                    out.print("<td><b>Solicitante :</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><input type='text' name='Txt_fecha' readonly id='Txt_fecha' value='" + (fecha.getYear() + 1900) + "" + (fecha.getMonth() < 10 ? "-0" : "-") + "" + (fecha.getMonth() + 1) + "" + (fecha.getDate() < 10 ? "-0" : "-") + "" + fecha.getDate() + "' title='fecha'/>&ensp;&ensp;" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fecha');val1.add(Validate.Presence);</script></td>");
                    out.print("<td><input type='text' name='Txt_solicitante' value='" + nombre_usuario.toString().toUpperCase() + "' id='Txt_solicitante' readonly  title='Solicitante' onchange='javascript:this.value=this.value.toUpperCase();'/></td>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_solicitante');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Seleccione Planta :</b></td>");
                    out.print("<td><b>Clasificación :</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><select name='Cbx_planta' id='Cbx_planta' title='planta' style='width: 188;'></br>");
                    out.print("<option value='" + obj_solicitud[8] + "' style='display:none;'>" + obj_solicitud[8] + "</option>");
                    out.print("<option value='PLANTA1'>Planta 1</option>");
                    out.print("<option value='PLANTA2'>Planta 2</option>");
                    out.print("<option value='BODEGA5'>Bodega 5</option>");
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_planta');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td>");
                    out.print("<td><select name='Cbx_clasificacion' id='Cbx_clasificacion' title='Clasificacion' style='width: 188;'>");
                    out.print("<option value='" + obj_solicitud[3] + "' style='display:none;'>" + obj_solicitud[3] + "</option>");
                    out.print("<option value='alta'>Alta</option>");
                    out.print("<option value='media'>Media</option>");
                    out.print("<option value='baja'>Baja</option>");
                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_clasificacion');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><b>Ubicación :</b><br/>");
                    out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                    for (int i = 0; i < lst_ubiccacion.size(); i++) {
                        Object[] objet_ubi = (Object[]) lst_ubiccacion.get(i);
                        if (objet_ubi[1].toString() == obj_solicitud[1].toString()) {
                            out.print("<option selected=\"true\">" + objet_ubi[1] + "</option>");
                        } else {
                            out.print("<option>" + objet_ubi[1] + "</option>");
                        }
                    }
                    out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);val1.add(Validate.Txt_ubicacion);</script>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><b>Descripción :</b><br />");
                    out.print("<textarea type='text' name='Txt_descripcion' id='Txt_descripcion' style='height:50px;width:400px;'  title='Descripcion' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_solicitud[2] + "</textarea></td>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr><td colspan='2'><input type='submit' value='Actualizar' style='width: 188;'/><td></tr>");
                    out.print("</table>");
                    out.print("</form>");
                }
                //</editor-fold>
                out.print("<form method='post' action='Solicitud?opc=11&Id_solicitudes_agrupar=" + solicitudes_agrupadas + "' name='form_agrupacion'>");
                out.print("<input type='hidden' name='Id_solicitud_principal' id='Id_solicitud_principal' />");
                //<editor-fold defaultstate="collapsed" desc="TABLA DE AGRUPACIÓN">
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<td></td>");
                out.print("</tr>");
                for (int i = 0; i < vector_Solicitud.length; i++) {
                    int id_de_solicitud = Integer.parseInt(vector_Solicitud[i].toString());
                    lst_solicitudes = jpacsol.Traer_Solicitud(id_de_solicitud);
                    Object[] obj_solicitud = (Object[]) lst_solicitudes.get(0);
                    out.print("<tr>");
                    if (solicitudes_principal > 0) {
                        if (Integer.parseInt(obj_solicitud[0].toString()) == solicitudes_principal) {
                            out.print("<td align='center'><b class='verde'>Principal</b></td>");
                        } else {
                            out.print("<td align='center'><b class='naranja'>Agrupada</b></td>");
                        }
                    } else {
                        out.print("<td align='center'><input type='radio' name='box' id='box" + obj_solicitud[0] + "' onchange=\"habilitaDeshabilita('" + id_solicitudes_ag + "','" + obj_solicitud[0] + "')\" /></br><b id='estado" + obj_solicitud[0] + "' >Agrupada</b></td>");
                    }
                    out.print("<th style='width:100px'> <b style='color:#fff'> " + obj_solicitud[4] + " </b></th>");
                    out.print("<td style='width:150px'><b>Solicitante:</b> </br> " + obj_solicitud[6] + "</td>");
                    out.print("<td style='width:300px;' valign='top'><b> Descripción:</b>" + obj_solicitud[2] + "</br>");
                    out.print("<b> Ubicación :</b>" + obj_solicitud[1] + "<b>-</b>" + obj_solicitud[7] + "</td>");
                    out.print("<td  valign='top'><b> Clasificación :</b></br> " + obj_solicitud[3] + "</br></td>");
                    if (Integer.parseInt(obj_solicitud[9].toString()) == 1) {
                        out.print("<td align='center' style='background-color:rgba(45, 137, 239, 0.56)'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' style='margin-top: 10px;' alt='edit' title='Modificar Registro' /><br/><b style='font-weight:bold;color:#000;'>edicion</b></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 2) {
                        out.print("<td align='center' colspan='2' style='background-color:rgba(246, 146, 30, 0.50);'><img src='Interfaz/Contenido/Iconos/Mail.png' style='height:15px; width:25px;margin-top: 10px;' title='Correo enviado' /><br/><b style='font-weight:bold;color:#000;'>enviado</b></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 3) {
                        out.print("<td align='center' colspan='2' style='background-color:rgba(247, 224, 55, 0.57);'><img src='Interfaz/Contenido/Iconos/Calendario.png' style='height:20px; width:20px;margin-top: 10px;' title='Programado' /><br/><b style='font-weight:bold;color:#000;'>programado</b></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 4) {
                        out.print("<td align='center' colspan='2' style='background-color: rgba(239, 20, 0, 0.72);'><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;margin-top: 10px;' title='Pendiente' /><br/><b style='font-weight:bold;color:#000;'>pendiente</b></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 5) {
                        out.print("<td align='center' colspan='2' style='background-color:rgba(172, 244, 25, 0.57);'><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;margin-top: 10px;' title='Ejecutada' /><br/><b style='font-weight:bold;color:#000;'>ejecutado</b></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 6) {
                        out.print("<td align='center' colspan='2' style='background-color:rgba(204,0,0,0.78);'><img src='Interfaz/Contenido/Iconos/Min.png' style='height:20px; width:20px;' title='Seguimiento' /><br/><b style='font-weight:bold;color:#000;'>seguimiento</b></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 7) {
                        out.print("<td align='center' colspan='2' style='background-color:rgba(172, 244, 25, 0.57);'><a href='Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "' style='text-decoration:none;'><img src='Interfaz/Contenido/Iconos/Check.png' style='height:20px; width:20px;' title='Seguimiento' /><br/><b style='font-weight:bold;color:#000;'>Solicitud </br> Terminada</b></a></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 8) {
                        out.print("<td align='center' colspan='2' style='background-color:rgba(177, 25, 244, 0.57);'><a href='Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "' style='text-decoration:none;'><img src='Interfaz/Contenido/Iconos/agrupacion.png' style='height:20px; width:20px;' title='Seguimiento' /><br/><b style='font-weight:bold;color:#000;'>Agrupada</b></a></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 9) {
                        out.print("<td align='center' colspan='2' style='background-color:rgb(255, 51, 184);'><a href='Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "' style='text-decoration:none;'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;' title='Seguimiento' /><br/><b style='font-weight:bold;color:#000;'>Declinación sin confirmar</b></a></td>");
                    } else if (Integer.parseInt(obj_solicitud[9].toString()) == 10) {
                        out.print("<td align='center' colspan='2' style='background-color:rgb(63, 127, 191);'><a href='Solicitud?opc=6&Id_solicitud=" + obj_solicitud[0] + "' style='text-decoration:none;'><img src='Interfaz/Contenido/Iconos/Trash.png' style='height:20px; width:20px;' title='Seguimiento' /><br/><b style='font-weight:bold;color:#000;'>Declinación confirmada</b></a></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
//</editor-fold>
                if (solicitudes_principal == 0) {
                    out.print("<br /><br /><input type='submit' id='confirmar' style='display:none;' value='Confirmar' />");
                }
                out.print("</form>");
                out.print("</fieldset></center>");
                out.print("</div>");
            }
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_solicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

    private void ProgramacionJpaController() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
