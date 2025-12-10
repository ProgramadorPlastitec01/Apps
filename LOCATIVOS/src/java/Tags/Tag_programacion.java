package Tags;

import Controladores.ActividadesAdicionalesJpaController;
import Controladores.ActividadesJpaController;
import Controladores.AreaJpaController;
import Controladores.ClasificacionJpaController;
import Controladores.EvidenciaJpaController;
import Controladores.ProgramacionDetalleJpaController;
import Controladores.ProgramacionJpaController;
import Controladores.ProveedorDetalleJpaController;
import Controladores.ProveedorJpaController;
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

public class Tag_programacion
        extends TagSupport {

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            ProgramacionJpaController jpacpro = new ProgramacionJpaController();
            ProgramacionDetalleJpaController jpacpdt = new ProgramacionDetalleJpaController();
            ClasificacionJpaController jpaccl = new ClasificacionJpaController();
            AreaJpaController jpacare = new AreaJpaController();
            UsuarioJpaController jpacusu = new UsuarioJpaController();
            ProveedorJpaController jpacprov = new ProveedorJpaController();
            ActividadesJpaController jpacact = new ActividadesJpaController();
            UbicacionJpaController jpacubi = new UbicacionJpaController();
            EvidenciaJpaController jpacevd = new EvidenciaJpaController();
            SolicitudJpaController jpacsol = new SolicitudJpaController();
            ProveedorDetalleJpaController jpacprovd = new ProveedorDetalleJpaController();
            ActividadesAdicionalesJpaController jpacacta = new ActividadesAdicionalesJpaController();
            Date fecha = new Date();
            int id_programacion = 0;
            int id_programacion_detalle = 0;
            int iare = Integer.parseInt(this.pageContext.getSession().getAttribute("Id_area").toString());
            int id_proveedor = 0;
            int Cont_i = 0;
            int Cont_f = 0;
            int Cont_far = 0;
            int Cont_fa = 0;
            int Cont_in = 0;
            int Cont_ins = 0;
            int row = 0;
            int Cont_actividades_a_modificar = 0;
            List lst_siglatura_area = jpacare.Traer_area_id(iare);
            Object[] obj_siglatura = (Object[]) lst_siglatura_area.get(0);
            List lst_programacion = null;
            List lst_ubiccacion = jpacubi.Ubicaciones();
            List lst_actividades_adicionales = null;
            List lst_programaciones = null;
            List lst_solicitud = null;
            List lst_solicitudes_programadas = null;
            List Cont_de_actividades = null;
            List lst_cont_clasificacion = null;
            List faster2 = null;
            List lst_clasificacion = null;
            List lst_servicio_general = null;
            List list_ActA = null;
            List lst_historial_actividades = null;
            List lst_actividades = null;
            List Cont_actividades = null;
            List lst_adjunto_correo = null;
            List lst_personal_externo = null;
            List lst_actividades_solicitud = null;
            List lst_areas = null;
            List lst_ubicacion = null;
            List lst_proveedores = null;
            List lst_proveedor_detalle = null;
            List lst_actividades_filtro = null;
            List lst_ejecutor = null;
            List lst_actividad_programadas = null;
            List lst_programacion_estado = null;
            List lst_programacion_validar = null;
            List lst_solicitudes_pendientes = null;
            List lst_adjuntos = null;
            List lst_empresas_externas = null;
            List lst_personal = null;
            List lst_actividades_de_empresa = null;
            List informacion_solicitud = null;
            String tabla_actividades = "";
            String lectura_I = "";
            String Imprimir = "";
            String dotacion = "";
            if (this.pageContext.getRequest().getAttribute("Programacion") != null) {
                out.print("<div id='content_sin'>");
                String nombre_rol = this.pageContext.getSession().getAttribute("Nombre_rol").toString();
                if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                    out.print("<h3><img id=\"Menu_registro\" src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Desplegar Registro' />Programaciones<div style='float:right;width:200px'><input type='text' onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar'/></div></h3>");
                } else {
                    out.print("<h3>Programaciones</h3>");
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR PROGRAMACION">
                if (this.pageContext.getRequest().getAttribute("Programacion").toString().equals("Registro_programacion")) {
                    id_programacion = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_programacion").toString());
                    if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                        lst_programaciones = jpacpro.EstadoPrograma();
                        if (lst_programaciones != null) {
                            out.print("<script>");
                            out.print("$(Menu_registro).click(function() {");
                            out.print("$(\"#toggle\").toggle(\"slide\");");
                            out.print("});");
                            out.print("</script>");
                            out.print("<div style='display:none;border: 1px solid #dc143c;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                            out.print("<div id='sidebar'>");
                            out.print("<h3>Hay Programaciones Abiertas</h3>");
                            out.print("</div><div class='cleaner'> </div>");
                            out.print("</div>");
                        } else {
                            out.print("<script>");
                            out.print("$(Menu_registro).click(function() {");
                            out.print("$(\"#toggle\").toggle(\"slide\");");
                            out.print("});");
                            out.print("</script>");
                            out.print("<div style='display:none;border: 1px solid #dc143c;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                            out.print("<div id='sidebar'>");
                            out.print("<h3>Registrar Programación</h3>");
                            out.print("<form  action='Programacion?opc=3' name='f1' id='f1' method='post' onsubmit='registroP();'>");
                            out.print("<b>Nombre programación:</b>");
                            out.println("<input type='text'  name='Txt_nombre_programacion' id='Txt_nombre_programacion' placeholder='Nombre programacion' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_programacion');val1.add(Validate.Presence);</script>");

                            out.print("<b>Fecha inicio :</b>");
                            out.println("<input type='text' name='Txt_fecha_inicio' autocomplete='off' id='start' value='" + new StringBuilder().append(fecha.getYear() + 1900).append("").append(fecha.getMonth() <= 9 ? "-0" : "-").append("").append(fecha.getMonth() + 1).append("").append(fecha.getDate() <= 9 ? "-0" : "-").append("").append(fecha.getDate()).toString().toString().replace("-", "/") + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");

                            out.print("<b>Fecha fin :</b>");
                            out.println("<input type='text' name='Txt_fecha_fin' autocomplete='off' id='end' value='" + new StringBuilder().append(fecha.getYear() + 1900).append("").append(fecha.getMonth() <= 9 ? "-0" : "-").append("").append(fecha.getMonth() + 1).append("").append(fecha.getDate() <= 9 ? "-0" : "-").append("").append(fecha.getDate()).toString().toString().replace("-", "/") + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");

                            out.print("<input type='hidden' name='Id_usuario' id='Id_usuario' value=''/>");
                            out.print("<b>Responsable Interno :</b>");
                            out.print("<select name='Cbx_ejecutor' id='Cbx_ejecutor' title='Ejecutor' style ='width: 188;'>");
                            out.print("<option style='display:none;' value=''>Seleccionar</option>");
                            out.print("<option>Ejecutor</option>");
                            lst_ejecutor = jpacusu.Usuarios();
                            for (int j = 0; j < lst_ejecutor.size(); j++) {
                                Object[] obj_ejecutor = (Object[]) lst_ejecutor.get(j);
                                if (obj_ejecutor[8].equals(Integer.valueOf(4))) {
                                    out.print("<option value='" + obj_ejecutor[1] + "'>" + obj_ejecutor[1] + "</option>");
                                }
                            }
                            out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Cbx_ejecutor');val1.add(Validate.Presence);</script>");

                            out.print("<b>Observación :</b>");
                            out.print("<textarea type='text' placeholder='...' style=' height:100px' id='Txt_nota' name='Txt_nota'  onchange='javascript:this.value=this.value.toUpperCase();'></textarea><script type='text/javascript'>var val1 = new LiveValidation('Txt_nota');val1.add(Validate.Presence);</script>");

                            out.print("<input type='submit' id='btsubmit'  value='Generar Programación' /><br /><br />");
                            out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n          <div></div>\n          <div></div>\n          <div></div>\n        </div>");

                            out.print("</form>");
                            out.print("</div><div class='cleaner'> </div>");
                            out.print("</div>");
                        }
                    }
                    if (id_programacion > 0) {
                        out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                        out.print("<center><fieldset align='left' style='overflow:scroll;width:70%;height:600px;margin-top:50px' >");
                        out.print("<form action='Programacion?opc=4' method='post' name='Form_asignar' id='Form_asignar' onsubmit='registroPS();'>");
                        out.println("<input type='hidden' name='Id_solicitudes' id='Id_solicitudes' onclick=\"this.focus(); this.select();\" />");
                        out.print("<div style='float:right'><a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Cancelar'/></a></div>");
                        lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                        if (lst_programacion != null) {
                            Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                            out.print("<h3>Informacion Programación</h3>");
                            out.print("<input type='hidden' name='Id_programacion' id='Id_programacion' value='" + obj_programacion[0] + "' />");
                            out.print("<table>");
                            out.print("<tr>");
                            out.print("<td><i class='color'>Nombre : </i>" + obj_programacion[1] + "</br>");
                            out.print("<i class='color'>Fecha inicio : </i>" + obj_programacion[2] + " ");
                            out.print("<i class='color'>Fecha fin : </i>" + obj_programacion[3] + "</br>");
                            out.print("<i class='color'>Observación : </i>" + obj_programacion[4] + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
                        }
                        out.print("<div style='float: right;'><a href='#' onclick='Seleccionar_todo();' style='text-decoration:none;'><b class='negro'>Marcar todos</b></a> | <a href='#' onclick='Deseleccionar_todo();' style='text-decoration:none;'><b class='negro'>Ninguno</b></a></div>");

                        out.print("<input type='submit' id='btsubmit1' value='Asignar' />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos1'>\n          <div></div>\n          <div></div>\n          <div></div>\n        </div>");

                        out.print("<h3>Asignación de locativos</h3>");
                        out.print("<ul id='browser' class='treeview-famfamfam treeview'>");
                        lst_areas = jpacpdt.Consultar_solicitudes_por_area();
                        try {
                            for (int i = 0; i < lst_areas.size(); i++) {
                                int valida_contador = 0;
                                Object[] obj_areas = (Object[]) lst_areas.get(i);
                                if (!obj_areas[3].toString().equals("0")) {
                                    valida_contador = 1;
                                    out.print("<li class='closed'><span class='folder' style='font-weight: bold;color: #000;'> <span style='background: none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px; text-align: center;padding: 2px 6px;color:#fff'>" + obj_areas[3] + "</span> " + obj_areas[1] + "</span>" + "<hr/>" + "<ul>");
                                }
                                if (valida_contador > 0) {
                                    lst_clasificacion = jpaccl.Clasificacion();
                                    for (int j = 0; j < lst_clasificacion.size(); j++) {
                                        Object[] obj_clasificacion = (Object[]) lst_clasificacion.get(j);
                                        if (obj_clasificacion[2].toString().equals("Solicitud")) {
                                            lst_cont_clasificacion = jpacpdt.Consultar_areas_solicitud(Integer.parseInt(obj_areas[0].toString()), obj_clasificacion[1].toString());
                                            lst_solicitud = jpacpdt.Consultar_programaciones_de_solicitud(Integer.parseInt(obj_areas[0].toString()), obj_clasificacion[1].toString());
                                            Object[] obj_cont_clasificacion = (Object[]) lst_cont_clasificacion.get(0);
                                            if (obj_cont_clasificacion[2].toString().equals("0")) {
                                                out.print("<li class='closed'><span class='folder'><span></span>" + obj_clasificacion[1] + "</span></br></br><ul>");
                                            } else {
                                                out.print("<li class='closed'><span class='file' ><span style='background: none repeat scroll 0 0 #fff;border-radius: 3px 3px 3px 3px; text-align: center;padding: 2px 3px;color:#dc143c;border:1px solid #dc143c;'>" + obj_cont_clasificacion[2] + "</span>" + obj_clasificacion[1] + "</span></br></br><ul style='background-color:#fff;border:1px solid #dc143c;width:95%;'>");
                                            }
                                            if (lst_solicitud == null) {
                                                out.print("<li><b style='color:#000'>No hay solicitudes reportadas de esta area</b></li>");
                                            } else {
                                                out.print("<h3>Solicitudes prioridad " + obj_clasificacion[1] + "</h3>");
                                                out.print("<table class='table' aling='right'>");
                                                for (int k = 0; k < lst_solicitud.size(); k++) {
                                                    Object[] obj_solicitud = (Object[]) lst_solicitud.get(k);
                                                    out.print("<tr>");
                                                    out.print("<td  style='width:10px' align='center'><b " + (Integer.parseInt(obj_solicitud[7].toString()) == 4 ? " class='naranja' title='SOLICITUD PENDIENTE'" : Integer.parseInt(obj_solicitud[7].toString()) == 6 ? " class='rojo' title='SOLICITUD EN SEGUIMIENTO'" : " class='negro'") + "># " + obj_solicitud[0] + "<hr /><input type='checkbox' name='box" + i + j + k + "' id='box" + i + j + k + "' value='[" + obj_solicitud[0] + "]' onclick=\"add_sub(this);\" /></b></td>");

                                                    out.print("<td ><b>Fecha : </b>" + obj_solicitud[2] + "&ensp;&ensp;&ensp;");

                                                    out.print("<b>Solicitante :</b> " + obj_solicitud[1] + "</br>");
                                                    out.print("<b> Ubicación : </b>" + obj_solicitud[5] + "<b>/</b>" + obj_solicitud[10] + "</br>");
                                                    out.print("<b> Descripción :</b>" + obj_solicitud[3] + "</br>");
                                                    if (obj_solicitud[11] == null) {
                                                        lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitud[0].toString()), "S");
                                                        if (lst_adjuntos != null) {
                                                            out.print("<dir /><b> Anexos :</b></br>");
                                                            for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                                Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                                out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                            }
                                                        }
                                                    } else {
                                                        lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitud[0].toString()), "R");
                                                        out.print("</br><b> seguimiento :</br></b>" + obj_solicitud[11] + "</br>");
                                                        if (lst_adjuntos != null) {
                                                            out.print("<dir /><b> Anexos seguimiento:</b></br>");
                                                            for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                                Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                                out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                            }
                                                        }
                                                    }
                                                    if (obj_solicitud[7].toString().equals("6")) {
                                                        out.print("<b>descripción de seguimiento:</b></br>" + obj_solicitud[11] + "</br>");
                                                        out.print("<input type='hidden' name='Txt_recibe" + obj_solicitud[0] + "' id='Txt_recibe" + obj_solicitud[0] + "' value='" + obj_solicitud[11] + "'/>");
                                                    }
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                }
                                                out.print("</table>");
                                            }
                                            out.print("</ul></li>");
                                        }
                                    }
                                }
                                if (!obj_areas[3].toString().equals("0")) {
                                    out.print("</ul></li>");
                                }
                            }
                        } catch (Exception localException1) {
                        }
                        out.print("</ul>");
                        out.print("</form>");
                        out.print("</fieldset></center>");
                        out.print("</div>");
                    }
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PROGRAMACION">
                else if ((this.pageContext.getRequest().getAttribute("Programacion").toString().equals("Modificar_programacion")) && (!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                    lst_programacion = (List) this.pageContext.getRequest().getAttribute("Datos_programacion");
                    Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                    out.print("<script>");
                    out.print("$(Menu_registro).click(function() {");
                    out.print("$(\"#toggle\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='display:block;border: 1px solid #dc143c;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                    out.print("<div id='sidebar'>");
                    out.print("<div style='float:right'><a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Cancelar'/></a></div>");
                    out.print("<h3>Modificar Programación</h3>");
                    out.print("<form  action='Programacion?opc=6&Id_Programacion=" + obj_programacion[0] + "' name='f1' id='f1' method='post'>");
                    out.print("<b>Nombre programación :</b>");
                    out.println("<td><input type='text' value='" + obj_programacion[1] + "' name='Txt_nombre_programacion' id='Txt_nombre_programacion' placeholder='Nombre programacion' onchange='javascript:this.value=this.value.toUpperCase();'/></td>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_programacion');val1.add(Validate.Presence);</script>");

                    out.print("<b>Fecha inicio :</b>");
                    out.println("<td><input type='text' name='Txt_fecha_inicio' autocomplete='off' id='start' value='" + obj_programacion[2] + "'/></td>" + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");

                    out.print("<b>Fecha fin :</b>");
                    out.println("<td><input type='text' name='Txt_fecha_fin' autocomplete='off' id='end' value='" + obj_programacion[3] + "' /></td>" + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");

                    out.print("<b>Responsable interno :</b>");
                    out.print("<td><select name='Cbx_ejecutor' id='Cbx_ejecutor' title='Ejecutor' style ='width: 188;'>");
                    out.print("<option value='" + obj_programacion[6] + "' style='display:none;'>" + obj_programacion[6] + "</option>");
                    lst_ejecutor = jpacusu.Usuarios();
                    for (int j = 0; j < lst_ejecutor.size(); j++) {
                        Object[] obj_ejecutor = (Object[]) lst_ejecutor.get(j);
                        if (obj_ejecutor[8].equals(Integer.valueOf(4))) {
                            out.print("<option value='" + obj_ejecutor[1] + "'>" + obj_ejecutor[1] + "</option>");
                        }
                    }
                    out.print("</select></td><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ejecutor');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<b>Observación :</b>");
                    out.print("<td colspan='4'><textarea  type='text' style=' height:100px' id='Txt_nota' name='Txt_nota' onchange='javascript:this.value=this.value.toUpperCase();' >" + obj_programacion[4] + "</textarea></td>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nota');val1.add(Validate.Presence);</script>");

                    out.print("<input type='submit' value='Modificar Programacion' /> </br></br>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR PROGRAMACION">
                lst_programaciones = jpacpro.Consultar_Programacion();
                if (lst_programaciones != null) {
                    out.print("<form action='#' method='post'>");
                    out.print("<table style='width:100%' class='table' id='resultados'>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<tr>");
                    out.print("<th style='width:10%;'>Nombre</th>");
                    out.print("<th style='width:10%;'>Fecha inicio</th>");
                    out.print("<th style='width:10%;'>Fecha fin </th>");
                    out.print("<th style='width:50%;'>Observación</th>");
                    if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                        out.print("<th>Asignar</th>");
                    }
                    if (!nombre_rol.equals("Ejecutor")) {
                        out.print("<th>Actividades</th>");
                    } else {
                        out.print("<th>Proceso</th>");
                    }
                    if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                        out.print("<th>Modificar</th>");
                    }
                    if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                        out.print("<th>Estado</th>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_programaciones.size(); i++) {
                        Object[] obj_programaciones = (Object[]) lst_programaciones.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_programaciones[1] + "</td>");
                        out.print("<td>" + obj_programaciones[2] + "</td>");
                        out.print("<td>" + obj_programaciones[3] + "</td>");
                        out.print("<td>" + obj_programaciones[4] + "</td>");
                        if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                            if ((obj_programaciones[5].equals(Integer.valueOf(2))) || (obj_programaciones[5].equals(Integer.valueOf(3))) || (obj_programaciones[5].equals(Integer.valueOf(4)))) {
                                out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Check.png' width='22px' height='22px'   title='Solicitud cerrada' /></td>");
                            } else {
                                out.print("<td align='center'><a href='Programacion?opc=1&Id_programacion=" + obj_programaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px'   title='Asignar solicitudes' /></a></td>");
                            }
                        }
                        if (!nombre_rol.equals("Ejecutor")) {
                            out.print("<td align='center'><a href='Programacion?opc=7&Id_programacion=" + obj_programaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' width='26px' height='26px'   title='Ver actividades' /></a></td>");
                        } else if (!obj_programaciones[5].equals(Integer.valueOf(1))) {
                            out.print("<td align='center'><a href='Programacion?opc=7&Id_programacion=" + obj_programaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Detalle.png' width='26px' height='26px'   title='ejecutar actividades' /></a></td>");
                        } else if (obj_programaciones[5].equals(Integer.valueOf(1))) {
                            out.print("<td align='center'><b class='rojo'>EN PROGRAMACIÓN</b></td>");
                        }
                        if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                            out.print("<td align='center'><a href='Programacion?opc=5&Id_Programacion=" + obj_programaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='26px' height='26px' title='Modificar' /></a></td>");
                        }
                        if ((!nombre_rol.equals("Ejecutor")) && (!nombre_rol.equals("Consulta"))) {
                            if ((obj_programaciones[5].equals(Integer.valueOf(1))) || (obj_programaciones[5].equals(Integer.valueOf(3)))) {
                                out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Open_Block.png' width='26px' height='26px' title='Estado' /></td>");
                            } else {
                                out.print("<td align='center'><img src='Interfaz/Contenido/Iconos/Check.png' width='26px' height='26px' title='Estado' /></td>");
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<center>");
                    out.print("<h3>Programaciones</h3>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                    out.print("<b>No hay ninguna programacion</b>");
                    out.print("</center>");
                }
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            }
            if (this.pageContext.getRequest().getAttribute("Actividades") != null) {
                out.print("<div id='content_sin'>");
                String nombre_rol = this.pageContext.getSession().getAttribute("Nombre_rol").toString();
                if (this.pageContext.getRequest().getAttribute("Actividades").toString().equals("Actividades")) {
                    id_programacion = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_programacion").toString());
                    id_programacion_detalle = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_programacion_detalle").toString());
                    id_proveedor = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_proveedor").toString());
                    lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                    lst_solicitudes_programadas = jpacpro.Traer_solicitudes_programadas(id_programacion);
                    out.print("<div class='content_sin'>");
                    if (lst_solicitudes_programadas == null) {
                        out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");
                        out.print("<center>");
                        out.print("<h3>Programación</h3>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>No existe ninguna solicitud programada</b>");
                        out.print("</center>");
                    } else if (!nombre_rol.equals("Ejecutor")) {
                        lst_programacion_estado = jpacpro.Traer_estado_de_programacion_programador(id_programacion);
                        Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                        if (obj_programacion[5].equals(Integer.valueOf(1))) {
                            out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");
                            out.print("<div style='float:right;'>| <a href='Programacion?opc=7&Id_programacion=" + id_programacion + "&Cbx_proveedor=" + (id_proveedor > 0 ? Integer.valueOf(id_proveedor) : "2") + "' >" + "<img src='Interfaz/Contenido/Iconos/Detalle.png' style=\"padding-right: 10px;\" alt='edit' title='Personal externo' /></a>Personal externo | ");

                            lst_programacion_validar = jpacpro.Validar_solicitudes_programacion(id_programacion);
                            String solicitudes_duplicadas = "";
                            for (int i = 0; i < lst_programacion_validar.size(); i++) {
                                Object[] obj_val_programacion = (Object[]) lst_programacion_validar.get(i);
                                if (Integer.parseInt(obj_val_programacion[1].toString()) > 1) {
                                    solicitudes_duplicadas = solicitudes_duplicadas + "[" + obj_val_programacion[0] + "]";
                                }
                            }
                            if (!solicitudes_duplicadas.isEmpty()) {
                                out.print("<a href='#' onclick=\"SolicitudesDuplicadas('" + solicitudes_duplicadas + "');\"><img src='Interfaz/Contenido/Iconos/Open.png' alt='Enviar correo' style='padding-right:10px;height:18px'/></a>Cerrar Programación</div>");
                            } else {
                                lst_programacion_validar = jpacpro.Validar_actividades_programacion(id_programacion);
                                if ((lst_programacion_validar == null) || (lst_programacion_validar.isEmpty())) {
                                    out.print("<a href='#' onclick='CierreProgramacion(" + id_programacion + ")'><img src='Interfaz/Contenido/Iconos/Open.png' alt='Enviar correo' style='padding-right:10px;height:18px'/></a>Cerrar Programación</div>");
                                } else {
                                    out.print("<a href='#' onclick='SinActividades()'><img src='Interfaz/Contenido/Iconos/Open.png' alt='Enviar correo' style='padding-right:10px;height:18px'/></a>Cerrar Programación</div>");
                                }
                            }
                            out.print("<hr />");
                            if (id_proveedor > 0) {
                                lst_proveedor_detalle = jpacprovd.Traer_proveedor_detalle(id_proveedor, id_programacion);
                                if (lst_proveedor_detalle == null) {
                                    out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                    out.print("<fieldset class='popup_local' style='width:auto;visibility:visible;position:fixed;top: 50px;left: 15%;width:800px;overflow:scroll;height:600px;'>");
                                    out.print("<div align='left'>");
                                    out.print("<a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img align='right' src='Interfaz/Contenido/Iconos/Delete.png' /></a></br>");
                                    lst_empresas_externas = jpacprov.proveedores();
                                    out.print("<b>Lista de externos:</b></br>");
                                    out.print("<form action='Programacion?opc=7&Id_programacion=" + id_programacion + "' method='post' name='Form_id_proveedor' id='Form_id_proveedor'>");
                                    out.print("<select  name='Cbx_proveedor' id='Cbx_proveedor' onchange='javascript:Form_id_proveedor.submit()' onclick='ver_lista_ejecutores()' title='Proveedores' style ='width: 188;'>");
                                    out.print("<option value='' style='display:none'>Seleccionar</option>");
                                    for (int i = 0; i < lst_empresas_externas.size(); i++) {
                                        Object[] obj_empresas_externas = (Object[]) lst_empresas_externas.get(i);
                                        if (!obj_empresas_externas[2].equals("N/A") && Integer.parseInt(obj_empresas_externas[5].toString()) > 0) {
                                            if (((Integer) obj_empresas_externas[0]).intValue() == id_proveedor) {
                                                out.print("<option value='" + obj_empresas_externas[0] + "' selected>" + obj_empresas_externas[2] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_empresas_externas[0] + "'>" + obj_empresas_externas[2] + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_proveedor');mySelect.add(Validate.Persisten, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("</form>");
                                    out.print("<form action='Programacion?opc=20&Id_programacion=" + id_programacion + "' method='post' name='Form_externos' id='Form_externos'>");
                                    out.print("<input type='hidden' name='Txt_trabajadores_externos' id='Txt_trabajadores_externos_registro' />");
                                    out.print("<input type='hidden' name='Id_solicitud_externos' id='Id_solicitud_externos' onclick=\"this.focus(); this.select();\" />");
                                    out.print("<input type='hidden' name='Id_proveedor' id='Id_proveedor' value='" + id_proveedor + "' />");
                                    if (id_proveedor != 0) {
                                        out.print("</br><b>Trabajadores Externos:</b></br>");
                                        out.print("<textarea style='width:300px;height:50px;text-transform: uppercase;' placeholder='TRABAJADORES EXTERNOS' name='Txt_trabajadores_externos_temp' id='Txt_trabajadores_externos_temp' onKeyDown='Salto_linea()' onchange='Salto_linea()' onKeyUp='Salto_linea()'></textarea>");
                                        out.print("<input type='submit' style='margin-left:70%;margin-top:-5%;' value='Generar Cartas'/>");
                                        out.print("</br><table class='table' width='90%'>");
                                        out.print("<tr>");
                                        out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>Item<hr />ID</b></td>");
                                        out.print("<td style=\"background-color: #ddf5f9;\" align='center'><b>Solicitudes</b></td>");
                                        out.print("</tr>");
                                        int Daggo = 0;
                                        for (int z = 0; z < 2; z++) {
                                            String tipado = "F";
                                            String Tipa2 = "Farmaceutico";
                                            if (z == 1) {
                                                Daggo = 0;
                                                tipado = "I";
                                                Tipa2 = "Insumos";
                                            }
                                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                                if (obj_solicitudes_programadas[15].equals(tipado)) {
                                                    if (Daggo == 0) {
                                                        out.print("<tr>");
                                                        out.print("<th colspan='9'>" + Tipa2 + "</th>");
                                                        out.print("</tr>");
                                                    }
                                                    Daggo++;
                                                    out.print("<tr>");
                                                    out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                                    out.print("<td style=\"background-color: #ddf5f9;\" colspan='8' align='center'><b>ubicacion:</b>" + obj_solicitudes_programadas[7] + "</br>" + "<b>Descripcion de solicitud:</b>" + obj_solicitudes_programadas[8] + "</td>");

                                                    out.print("</tr>");

                                                    out.print("<tr>");
                                                    out.print("<th style='background-color: #a29e9f;'></td>");
                                                    out.print("<th colspan='8' style='background-color: #a29e9f;'>Actividades</td>");
                                                    out.print("</tr>");
                                                    lst_historial_actividades = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                                    if (lst_historial_actividades != null) {
                                                        for (int j = 0; j < lst_historial_actividades.size(); j++) {
                                                            out.print("<tr>");
                                                            Object[] obj_historial_actividades = (Object[]) lst_historial_actividades.get(j);
                                                            try {
                                                                Object[] obj_proveedor_detalle = (Object[]) lst_proveedor_detalle.get(0);
                                                                String provedor_detalle = obj_proveedor_detalle[4].toString().replace("][", "]-[");
                                                                String[] vector_proveedor = provedor_detalle.split("-");
                                                                int c = 0;
                                                                for (int k = 0; k < vector_proveedor.length; k++) {
                                                                    if (vector_proveedor[k].contains("[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]")) {
                                                                        c = 1;
                                                                    }
                                                                }
                                                                if (c == 1) {
                                                                    out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" checked /></td>");
                                                                } else {
                                                                    out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                                }
                                                            } catch (Exception e) {
                                                                out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                            }
                                                            out.print("<td colspan=\"8\">" + obj_historial_actividades[1] + "</td>");
                                                            out.print("</tr>");
                                                        }
                                                    } else {
                                                        out.print("<tr>");
                                                        out.print("<td></td>");
                                                        out.print("<td colspan=\"8\">");
                                                        out.print("<b class='naranja'>No hay actividades</b>");
                                                        out.print("</td>");
                                                        out.print("</tr>");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    out.print("</table>");
                                    out.print("</form>");
                                    out.print("</div>");
                                    out.print("</fieldset>");
                                } else {
                                    Object[] obj_proveedor_detalle = (Object[]) lst_proveedor_detalle.get(0);
                                    out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                    out.print("<fieldset class='popup_local' style='width:auto;visibility:visible;position:fixed;top: 50px;left: 15%;width:800px;overflow:scroll;height:600px;'>");
                                    out.print("<div align='left'>");
                                    out.print("<a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img align='right' src='Interfaz/Contenido/Iconos/Delete.png' witdth='26px' height='26px' ></a></br>");
                                    lst_empresas_externas = jpacprov.proveedores();
                                    out.print("<b>Lista de externos:</b></br>");
                                    out.print("<form action='Programacion?opc=7&Id_programacion=" + id_programacion + "' method='post' name='Form_id_proveedor' id='Form_id_proveedor'>");
                                    out.print("<select  name='Cbx_proveedor' id='Cbx_proveedor' onchange='javascript:Form_id_proveedor.submit()' title='Proveedores' style ='width: 188;'>");
                                    out.print("<option value='' style='display:none'>Seleccionar</option>");
                                    for (int i = 0; i < lst_empresas_externas.size(); i++) {
                                        Object[] obj_empresas_externas = (Object[]) lst_empresas_externas.get(i);
                                        if (!obj_empresas_externas[2].equals("N/A") && Integer.parseInt(obj_empresas_externas[5].toString()) == 1) {
                                            if (((Integer) obj_empresas_externas[0]).intValue() == id_proveedor) {
                                                out.print("<option value='" + obj_empresas_externas[0] + "' selected>" + obj_empresas_externas[2] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_empresas_externas[0] + "'>" + obj_empresas_externas[2] + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_proveedor');mySelect.add(Validate.Persisten, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("</form>");
                                    out.print("<form action='Programacion?opc=20&Id_programacion=" + id_programacion + "' method='post' name='Form_externos' id='Form_externos'>");
                                    out.print("<input type='hidden' name='Txt_trabajadores_externos' id='Txt_trabajadores_externos_registro' value='" + obj_proveedor_detalle[3] + "'/>");
                                    out.print("<input type='hidden' name='Id_solicitud_externos' id='Id_solicitud_externos' onclick=\"this.focus(); this.select();\"  value='" + obj_proveedor_detalle[4] + "'/>");
                                    out.print("<input type='hidden' name='Id_proveedor' id='Id_proveedor' value='" + id_proveedor + "' />");
                                    out.print("</br><b>Trabajadores Externos:</b></br>");
                                    out.print("<textarea style='width:300px;height:50px;text-transform: uppercase;' placeholder='TRABAJADORES EXTERNOS' name='Txt_trabajadores_externos_temp' id='Txt_trabajadores_externos_temp' onKeyDown='Salto_linea()' onchange='Salto_linea()' onKeyUp='Salto_linea()'>" + obj_proveedor_detalle[3].toString().replace("---", "\n") + "</textarea>");
                                    out.print("<input type='submit' style='margin-left:70%;margin-top:-5%;' value='Generar Cartas'/>");
                                    out.print("</br><table class='table' width='90%'>");
                                    out.print("<tr>");
                                    out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>Item<hr />ID</b></td>");
                                    out.print("<td style=\"background-color: #ddf5f9;\" align='center'><b>Solicitudes</b></td>");
                                    out.print("</tr>");
                                    int Daggo = 0;
                                    for (int z = 0; z < 2; z++) {
                                        String tipado = "F";
                                        String Tipa2 = "Farmaceutico";
                                        if (z == 1) {
                                            Daggo = 0;
                                            tipado = "I";
                                            Tipa2 = "Insumos";
                                        }
                                        for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                            Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                            if (obj_solicitudes_programadas[15].equals(tipado)) {
                                                if (Daggo == 0) {
                                                    out.print("<tr>");
                                                    out.print("<th colspan='9'>" + Tipa2 + "</th>");
                                                    out.print("</tr>");
                                                }
                                                Daggo++;
                                                out.print("<tr>");
                                                out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                                out.print("<td style=\"background-color: #ddf5f9;\" align='center'><b>ubicación:</b>" + obj_solicitudes_programadas[7] + "</br>" + "<b>Descripcion de solicitud:</b>" + obj_solicitudes_programadas[8] + "</td>");

                                                out.print("</tr>");

                                                out.print("<tr>");
                                                out.print("<th style='background-color: #a29e9f;' ></th>");
                                                out.print("<th colspan='8' style='background-color: #a29e9f;'>Actividades</td>");
                                                out.print("</tr>");
                                                lst_historial_actividades = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                                if (lst_historial_actividades != null) {
                                                    for (int j = 0; j < lst_historial_actividades.size(); j++) {
                                                        out.print("<tr>");
                                                        Object[] obj_historial_actividades = (Object[]) lst_historial_actividades.get(j);
                                                        try {
                                                            String provedor_detalle = obj_proveedor_detalle[4].toString().replace("][", "]-[");
                                                            String[] vector_proveedor = provedor_detalle.split("-");
                                                            int c = 0;
                                                            for (int k = 0; k < vector_proveedor.length; k++) {
                                                                if (vector_proveedor[k].contains("[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]")) {
                                                                    c = 1;
                                                                }
                                                            }
                                                            if (c == 1) {
                                                                out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" checked /></td>");
                                                            } else {
                                                                out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                            }
                                                        } catch (Exception e) {
                                                            out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                        }
                                                        out.print("<td colspan=\"8\">" + obj_historial_actividades[1] + "</td>");
                                                        out.print("</tr>");
                                                    }
                                                } else {
                                                    out.print("<tr>");
                                                    out.print("<td></td>");
                                                    out.print("<td colspan=\"8\">");
                                                    out.print("<b class='naranja'>No hay actividades</b>");
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                }
                                            }
                                        }
                                    }
                                    out.print("</table>");
                                    out.print("</form>");
                                    out.print("</div>");
                                    out.print("</fieldset>");
                                }
                                out.print("</div>");
                            }
                            out.print("<table class='table' style='width:100%' >");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4' style='width:25%;'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                            out.print("<td align='center' colspan='4' style='width:30%;'>Locativos Programados para </br><b>" + obj_programacion[2] + "</b> Hasta <b>" + obj_programacion[3] + "</b></td>");
                            out.print("<td align='center' style='width:35%;'><b>" + obj_programacion[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='8'><b>Observaciones :</b>" + obj_programacion[4] + "</br>");
                            out.print("<b>Responsable interno :</b>" + obj_programacion[6] + "</td>");
                            out.print("<td valing='top' >");
                            out.print("<form action='Adjunto_correo.jsp' method='post' enctype='multipart/form-data'><input type='hidden' name='Id_origen' value='" + id_programacion + "'/>");

                            out.print("<input type='file' name='Txt_adjunto' required />&nbsp;&nbsp;&nbsp;&nbsp;");
                            out.print("<input type='submit' value='Adjuntar' style='width:100px' />");
                            out.print("</form><hr />");
                            try {
                                lst_adjunto_correo = jpacevd.Adjuntos_correo(id_programacion);
                                if (lst_adjunto_correo != null) {
                                    Object[] obj_adjunto_plano = (Object[]) lst_adjunto_correo.get(0);
                                    out.print("<a href='Descargar_plano?file_name=" + obj_adjunto_plano[2] + "'>" + obj_adjunto_plano[2] + "</a></br>");
                                }
                            } catch (Exception localException2) {
                            }
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table' style='width:100%' >");
                            out.print("<tr>");
                            out.print("<td style='width:5%' align='center'><b>Item<hr />ID</b></td>");
                            out.print("<td colspan='5' style='width:50%;' align='center'><b>Solicitud</b></td>");
                            out.print("<td colspan='5' align='center'><b>Actividades</b></td>");
                            out.print("</tr>");

                            int Daggo = 0;
                            for (int z = 0; z < 2; z++) {
                                String tipado = "F";
                                String Tipa2 = "Farmaceutico";
                                if (z == 1) {
                                    Daggo = 0;
                                    tipado = "I";
                                    Tipa2 = "Insumos";
                                }
                                for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                    Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                    lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    if (obj_solicitudes_programadas[15].equals(tipado)) {
                                        if (Daggo == 0) {
                                            out.print("<tr>");
                                            out.print("<th colspan='10'>" + Tipa2 + "</th>");
                                            out.print("</tr>");
                                        }
                                        Daggo++;
                                        if (lst_actividades == null) {
                                            out.print("<tr>");
                                            if (nombre_rol.equals("Consulta")) {
                                                out.print("<td align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                            } else if (solicitudes_duplicadas.contains("[" + obj_solicitudes_programadas[11] + "]")) {
                                                out.print("<td style='background-color:#FFE4E4;' align='center'><b>" + (i + 1) + " | # " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b><hr/><b class='rojo'>Duplicada</b><a onclick='Quitar_solicitud_de_programacion(" + obj_solicitudes_programadas[10] + "," + obj_solicitudes_programadas[9] + "," + id_programacion + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' id='cambiar' height='20px' title='Quitar solicitud de la programacion' style='width:15px;height:15px;'/></a></td>");
                                            } else {
                                                out.print("<td align='center'><a style='text-decoration:none' href='Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_programacion_detalle=" + obj_solicitudes_programadas[9] + "&Ubicacion=" + obj_solicitudes_programadas[7] + "&Id_solicitud=" + obj_solicitudes_programadas[11] + "'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></a><hr/><a onclick='Quitar_solicitud_de_programacion(" + obj_solicitudes_programadas[10] + "," + obj_solicitudes_programadas[9] + "," + id_programacion + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' id='cambiar' height='20px' title='Quitar solicitud de la programacion' style='width:15px;height:15px;'/></a></td>");
                                            }
                                            out.print("<input type='hidden' name='Ubicacion' id='Ubicacion' value='" + obj_solicitudes_programadas[7] + "'/>");
                                            out.print("<td colspan='5' ><b> Solicitante :</b>" + obj_solicitudes_programadas[6] + "<b>/</b>" + "<b>fecha :</b>" + obj_solicitudes_programadas[5] + "</br>");

                                            out.print("<b> Ubicado en </b>" + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br>");
                                            out.print("<b> Descripción :</b>" + obj_solicitudes_programadas[8] + "");
                                            if (obj_solicitudes_programadas[17] == null) {
                                                lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "S");
                                                if (lst_adjuntos != null) {
                                                    out.print("<dir /><b> Anexos :</b></br>");
                                                    for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                        Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                        out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                    }
                                                }
                                            } else {
                                                lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "R");
                                                out.print("</br><b> seguimiento :</br></b>" + obj_solicitudes_programadas[17] + "</br>");
                                                if (lst_adjuntos != null) {
                                                    out.print("<dir /><b> Anexos seguimiento:</b></br>");
                                                    for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                        Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                        out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                    }
                                                }
                                            }
                                            out.print("</td>");
                                            out.print("<td colspan='5' valign='top'>");
                                            out.print("<table width='100%'>");
                                            out.print("<tr>");
                                            out.print("<td align='center' style='width:250px' ><b>Trabajos a ejecutar</b></td>");
                                            out.print("<td align='center' style='width:240px'><b>Area lista para trabajar</b></td>");
                                            out.print("<td align='center' style='width:100px'><b>Eliminar</b></td>");
                                            out.print("</tr>");
                                            out.print("<tr>");
                                            lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                            if (lst_actividad_programadas == null) {
                                                out.print("<td align='center' colspan='3'><b class='naranja'>Sin actividades</b></td>");
                                            } else {
                                                for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                                    Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                                    out.print("<tr>");
                                                    out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                                    out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                                    if (!nombre_rol.equals("Consulta")) {
                                                        out.print("<td align='center'><a href='#' onclick='Confirmar_eliminacion(" + obj_actividades_programadas[0] + "," + id_programacion + "," + id_programacion_detalle + ")' style='text-decoration:none;'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px'title='Eliminar actividad'/></a></td>");
                                                    }
                                                    out.print("</tr>");
                                                }
                                            }
                                            out.print("</table>");
                                            out.print("</td>");
                                        } else {
                                            out.print("<tr>");
                                            for (int j = 0; j < lst_actividades.size(); j++) {
                                                if (nombre_rol.equals("Consulta")) {
                                                    out.print("<td align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                                } else if (solicitudes_duplicadas.contains("[" + obj_solicitudes_programadas[11] + "]")) {
                                                    out.print("<td style='background-color:#FFE4E4;' align='center'><b>" + (i + 1) + " | # " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b><hr/><b class='rojo'>Duplicada</b><a onclick='Quitar_solicitud_de_programacion(" + obj_solicitudes_programadas[10] + "," + obj_solicitudes_programadas[9] + "," + id_programacion + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' id='cambiar' height='20px' title='Quitar solicitud de la programacion' style='width:15px;height:15px;'/></a></td>");
                                                } else {
                                                    out.print("<td align='center'><a style='text-decoration:none' href='Programacion?opc=7&Id_programacion=" + id_programacion + "&Id_programacion_detalle=" + obj_solicitudes_programadas[9] + "&Ubicacion=" + obj_solicitudes_programadas[7] + "'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></a><hr/><a onclick='Quitar_solicitud_de_programacion(" + obj_solicitudes_programadas[10] + "," + obj_solicitudes_programadas[9] + "," + id_programacion + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' id='cambiar' height='20px' title='Quitar solicitud de la programacion' style='width:15px;height:15px;'/></a></td>");
                                                }
                                                out.print("<td colspan='5' ><b> Solicitante :</b>" + obj_solicitudes_programadas[6] + "<b>/</b>" + "<b>fecha :</b>" + obj_solicitudes_programadas[5] + "</br>");

                                                out.print("<b> Ubicado en </b>" + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br>");
                                                out.print("<b> Descripción :</b>" + obj_solicitudes_programadas[8] + "");
                                                if (obj_solicitudes_programadas[17] == null) {
                                                    lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "S");
                                                    if (lst_adjuntos != null) {
                                                        out.print("<dir /><b> Anexos :</b></br>");
                                                        for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                            Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                            out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                        }
                                                    }
                                                } else {
                                                    lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "R");
                                                    out.print("</br><b> seguimiento :</br></b>" + obj_solicitudes_programadas[17] + "</br>");
                                                    if (lst_adjuntos != null) {
                                                        out.print("<dir /><b> Anexos seguimiento :</b></br>");
                                                        for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                            Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                            out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                        }
                                                    }
                                                }
                                                out.print("</td>");
                                                out.print("<td colspan='5' valign='top'>");
                                                out.print("<table width='100%'>");
                                                out.print("<tr>");
                                                out.print("<td align='center' style='width:250px'><b>Trabajos a ejecutar</b></td>");
                                                out.print("<td align='center' style='width:240px'><b>Se requiere area para trabajar</b></td>");
                                                if (!nombre_rol.equals("Consulta")) {
                                                    out.print("<td align='center' style='width:100px'><b>Eliminar</b></td>");
                                                }
                                                out.print("</tr>");
                                                out.print("<tr>");
                                                lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                                if (lst_actividad_programadas == null) {
                                                    out.print("<td align='center' colspan='3'><b class='naranja'>Sin actividades</b></td>");
                                                } else {
                                                    for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                                        Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                                        out.print("<tr>");
                                                        out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                                        out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                                        if (!nombre_rol.equals("Consulta")) {
                                                            out.print("<td align='center'><a href='#' onclick='Confirmar_eliminacion(" + obj_actividades_programadas[0] + "," + id_programacion + "," + id_programacion_detalle + "," + obj_solicitudes_programadas[10] + ")' style='text-decoration:none;'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px'title='Eliminar actividad'/></a></td>");
                                                        }
                                                        out.print("</tr>");
                                                    }
                                                }
                                                out.print("</table>");
                                                out.print("</td>");
                                                out.print("</tr>");
                                            }
                                        }
                                    }
                                }
                            }
                            out.print("</table>");
                            if (id_programacion_detalle > 0) {
                                lst_proveedores = jpacprov.proveedores();
                                lst_ejecutor = jpacusu.Usuarios();
                                lst_actividades = jpacpdt.Consultar_programacion_detalle(id_programacion_detalle);
                                informacion_solicitud = jpacpdt.Consultar_informacion_de_solicitud(id_programacion_detalle);
                                if ((lst_actividades != null) && (informacion_solicitud != null)) {
                                    Object[] obj_solicitudes_programadas = (Object[]) informacion_solicitud.get(0);
                                    String Ubicacion = this.pageContext.getRequest().getAttribute("Ubicacion").toString();
                                    out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                    lst_actividades_solicitud = jpacpdt.traer_actividades_por_solicitud(id_programacion_detalle);
                                    Object[] obj_Programacion_detalle = (Object[]) lst_actividades.get(0);
                                    out.print("<fieldset class='popup_local' style='width:auto; visibility:" + (id_programacion_detalle > 0 ? "visible" : "hidden") + "; position:absolute; top:100px; left:15%;width:900px;height:550px;'>");
                                    out.print("<h3>Registrar actividades<div style='float:right'><a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px'height='26px'title='Cancelar'/></a></div></h3><hr />");
                                    out.print("<form action='Programacion?opc=9' method='post'>");
                                    out.print("<div style='display:block'>");
                                    out.print("<div align='left' style='float:left;width:420px;overflow:scroll;height:450px;'>");
                                    out.print("<input type='hidden' name='Id_solicitud' id='Id_solicitud' value='" + obj_solicitudes_programadas[2] + "'/>");
                                    out.print("<input type='hidden' name='Id_programacion' id='Id_programacion' value='" + id_programacion + "'/>");
                                    out.print("<input type='hidden' name='Id_programacion_detalle' id='Id_programacion_detalle' value='" + id_programacion_detalle + "'/>");
                                    out.print("<i style='color: #dc143c'>Informacion de solicitud</i></br>");
                                    out.print("<i style='color: #dc143c'>Solicitante:</i>" + obj_solicitudes_programadas[1] + "</br>");
                                    out.print("<i style='color: #dc143c'> Descripción :</i>" + obj_solicitudes_programadas[0] + "</br></br>");

                                    out.print("<b>Ubicación</b></br>");
                                    out.print("<select style='width:350px;' name='Txt_ubicacion_final' id='Txt_ubicacion_final' onchange='javascript:this.value=this.value.toUpperCase();'>");
                                    out.print("<option value=\"\">Seleccione Ubicacion</option>");
                                    if (lst_ubiccacion == null) {
                                        out.print("<option value=''>No hay Ubicaciones</option>");
                                    } else {
                                        int Daggov = 0;
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
                                                if (objet_ubi[1].toString().equals(Ubicacion)) {
                                                    if (objet_ubi[2].equals(Typet)) {
                                                        out.print("<option style='background-color:" + Color + "; color:#000000;' selected>" + objet_ubi[1] + "</option>");
                                                    }
                                                } else if ((objet_ubi[3].equals(Integer.valueOf(1))) && (objet_ubi[2].equals(Typet))) {
                                                    out.print("<option style='background-color:" + Color + "; color:#000000;'>" + objet_ubi[1] + "</option>");
                                                }
                                            }
                                            out.print("</optgroup>");
                                            Daggov = 1;
                                        }
                                    }
                                    out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion_final');val1.add(Validate.Presence);</script></td>");
                                    if (lst_actividades_solicitud != null) {
                                        out.print("</br><b>Actividades:</b></br>");
                                        for (int i = 0; i < lst_actividades_solicitud.size(); i++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividades_solicitud.get(i);
                                            out.print("<input type='hidden' name='Id_actividad" + i + "' id='Id_actividad" + i + "' value='" + obj_actividades_programadas[4] + "'/>");
                                            out.print("<textarea style='width:350px;text-transform:uppercase;' name='Txt_actividad_m" + i + "' id='Txt_actividad_m" + i + "' >" + obj_actividades_programadas[0] + "</textarea>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad_m" + i + "');val1.add(Validate.Presence);</script></br>");

                                            out.print("Se requiere del area lista para el trabajo? ");
                                            if (obj_actividades_programadas[3].equals("SI REQUERIDA")) {
                                                out.print("<b>SI</b><input type='radio' name='Rdb_area_lista_m" + i + "' id='Rdb_area_lista_m" + i + "' value='SI REQUERIDA' checked='checked'/><b>" + "NO</b><input type='radio' name='Rdb_area_lista_m" + i + "' id='Rdb_area_lista_m" + i + "' value='NO REQUERIDA'/></br>");
                                            } else {
                                                out.print("<b>SI</b><input type='radio' name='Rdb_area_lista_m" + i + "' id='Rdb_area_lista_m" + i + "' value='SI REQUERIDA'/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_m" + i + "' id='Rdb_area_lista_m" + i + "' value='NO REQUERIDA' checked='checked'/></br>");
                                            }
                                            Cont_actividades_a_modificar++;
                                        }
                                    }
                                    out.print("</div>");
                                    out.print("<div align='left' style='float:left;width:450px;overflow:scroll;height:450px;'>");
                                    out.print("<input type='hidden' name='Cont_actividades_modificar' id='Cont_actividades_modificar' value='" + Cont_actividades_a_modificar + "'/></br>");
                                    out.print("<div id='fiel'><a href='#' onclick='crear(this)'><img src='Interfaz/Contenido/Iconos/Plus.png' width='26px' height='26px'/></a><b>Nueva Actividad<b></div><input type='hidden' name='cantidad_actividades' value='0' id='cantidad_actividades'/></div>");
                                    out.print("</div>");
                                    out.print("<input type='submit' value='Guardar'/><br />");
                                    out.print("</div>");
                                    out.print("</form>");
                                    out.print("</fieldset>");
                                    out.print("</div>");
                                }
                            }
                        } else if (obj_programacion[5].equals(Integer.valueOf(2))) {
                            out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");
                            out.print("<div style='float:right;'>| <a href='Programacion?opc=7&Id_programacion=" + id_programacion + "&Cbx_proveedor=" + (id_proveedor > 0 ? Integer.valueOf(id_proveedor) : "2") + "' >" + "<img src='Interfaz/Contenido/Iconos/Detalle.png' style=\"padding-right: 10px;\" alt='edit' title='Personal externo' /></a>Personal externo | ");

                            out.print("<a href='Programacion?opc=17&Id_programacion=" + id_programacion + "' ><img src='Interfaz/Contenido/Iconos/Personal_externo.png' style=\"padding-right: 10px;\" alt='edit' title='Personal externo' /></a>Cartas de ingreso | ");
                            out.print("<a href='Programacion?opc=23&Id_programacion=" + id_programacion + "'><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"padding-right: 10px;\" alt=\"\" title='Imprimir' /></a>Imprimir lista | ");
                            if (obj_programacion[7].equals(Integer.valueOf(0))) {
                                out.print("<a href='Programacion?opc=27&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Open.png' style='padding-right:10px;'/></a>Abrir Programacion | ");
                                out.print("<a href='#' onclick=\"EnviarCorreoProgramacion('" + id_programacion + "');\"><img src='Interfaz/Contenido/Iconos/Mail_Danger.png' alt='Enviar correo' style='padding-right:10px;height:18px'/></a>Enviar Programación");
                            } else {
                                out.print("<img src='Interfaz/Contenido/Iconos/Check.png' alt='Enviar correo' style='padding-right:10px;height:18px'/>Programación Enviada");
                            }
                            out.print("</div>");
                            if (id_proveedor > 0) {
                                lst_proveedor_detalle = jpacprovd.Traer_proveedor_detalle(id_proveedor, id_programacion);
                                if (lst_proveedor_detalle == null) {
                                    out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                    out.print("<fieldset class='popup_local' style='width:auto;visibility:visible;position:fixed;top: 50px;left: 15%;width:800px;overflow:scroll;height:600px;'>");
                                    out.print("<div align='left'>");
                                    out.print("<a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img align='right' src='Interfaz/Contenido/Iconos/Delete.png' /></a></br>");
                                    lst_empresas_externas = jpacprov.proveedores();
                                    out.print("<b>Lista de externos:</b></br>");
                                    out.print("<form action='Programacion?opc=7&Id_programacion=" + id_programacion + "' method='post' name='Form_id_proveedor' id='Form_id_proveedor'>");
                                    out.print("<select  name='Cbx_proveedor' id='Cbx_proveedor' onchange='javascript:Form_id_proveedor.submit()' onclick='ver_lista_ejecutores()' title='Proveedores' style ='width: 188;'>");
                                    out.print("<option value='' style='display:none'>Seleccionar</option>");
                                    for (int i = 0; i < lst_empresas_externas.size(); i++) {
                                        Object[] obj_empresas_externas = (Object[]) lst_empresas_externas.get(i);
                                        if (!obj_empresas_externas[2].equals("N/A") && Integer.parseInt(obj_empresas_externas[5].toString()) > 0) {
                                            if (((Integer) obj_empresas_externas[0]).intValue() == id_proveedor) {
                                                out.print("<option value='" + obj_empresas_externas[0] + "' selected>" + obj_empresas_externas[2] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_empresas_externas[0] + "'>" + obj_empresas_externas[2] + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_proveedor');mySelect.add(Validate.Persisten, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("</form>");
                                    out.print("<form action='Programacion?opc=20&Id_programacion=" + id_programacion + "' method='post' name='Form_externos' id='Form_externos'>");
                                    out.print("<input type='hidden' name='Txt_trabajadores_externos' id='Txt_trabajadores_externos_registro' />");
                                    out.print("<input type='hidden' name='Id_solicitud_externos' id='Id_solicitud_externos' onclick=\"this.focus(); this.select();\" />");
                                    out.print("<input type='hidden' name='Id_proveedor' id='Id_proveedor' value='" + id_proveedor + "' />");
                                    if (id_proveedor != 0) {
                                        out.print("</br><b>Trabajadores Externos:</b></br>");
                                        out.print("<textarea style='width:300px;height:50px;text-transform: uppercase;' placeholder='TRABAJADORES EXTERNOS' name='Txt_trabajadores_externos_temp' id='Txt_trabajadores_externos_temp' onKeyDown='Salto_linea()' onchange='Salto_linea()' onKeyUp='Salto_linea()'></textarea>");
                                        out.print("<input type='submit' style='margin-left:70%;margin-top:-5%;' value='Generar Cartas'/>");
                                        out.print("</br><table class='table' width='90%'>");
                                        out.print("<tr>");
                                        out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>Item<hr />ID</b></td>");
                                        out.print("<td style=\"background-color: #ddf5f9;\" align='center'><b>Solicitudes</b></td>");
                                        out.print("</tr>");
                                        int Daggo = 0;
                                        for (int z = 0; z < 2; z++) {
                                            String tipado = "F";
                                            String Tipa2 = "Farmaceutico";
                                            if (z == 1) {
                                                Daggo = 0;
                                                tipado = "I";
                                                Tipa2 = "Insumos";
                                            }
                                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                                if (obj_solicitudes_programadas[15].equals(tipado)) {
                                                    if (Daggo == 0) {
                                                        out.print("<tr>");
                                                        out.print("<th colspan='9'>" + Tipa2 + "</th>");
                                                        out.print("</tr>");
                                                    }
                                                    Daggo++;
                                                    out.print("<tr>");
                                                    out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                                    out.print("<td style=\"background-color: #ddf5f9;\" colspan='8' align='center'><b>ubicacion:</b>" + obj_solicitudes_programadas[7] + "</br>" + "<b>Descripcion de solicitud:</b>" + obj_solicitudes_programadas[8] + "</td>");

                                                    out.print("</tr>");

                                                    out.print("<tr>");
                                                    out.print("<th style='background-color: #a29e9f;'></td>");
                                                    out.print("<th colspan='8' style='background-color: #a29e9f;'>Actividades</td>");
                                                    out.print("</tr>");
                                                    lst_historial_actividades = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                                    if (lst_historial_actividades != null) {
                                                        for (int j = 0; j < lst_historial_actividades.size(); j++) {
                                                            out.print("<tr>");
                                                            Object[] obj_historial_actividades = (Object[]) lst_historial_actividades.get(j);
                                                            try {
                                                                Object[] obj_proveedor_detalle = (Object[]) lst_proveedor_detalle.get(0);
                                                                String provedor_detalle = obj_proveedor_detalle[4].toString().replace("][", "]-[");
                                                                String[] vector_proveedor = provedor_detalle.split("-");
                                                                int c = 0;
                                                                for (int k = 0; k < vector_proveedor.length; k++) {
                                                                    if (vector_proveedor[k].contains("[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]")) {
                                                                        c = 1;
                                                                    }
                                                                }
                                                                if (c == 1) {
                                                                    out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" checked /></td>");
                                                                } else {
                                                                    out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                                }
                                                            } catch (Exception e) {
                                                                out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                            }
                                                            out.print("<td colspan=\"8\">" + obj_historial_actividades[1] + "</td>");
                                                            out.print("</tr>");
                                                        }
                                                    } else {
                                                        out.print("<tr>");
                                                        out.print("<td></td>");
                                                        out.print("<td colspan=\"8\">");
                                                        out.print("<b class='naranja'>No hay actividades</b>");
                                                        out.print("</td>");
                                                        out.print("</tr>");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    out.print("</table>");
                                    out.print("</form>");
                                    out.print("</div>");
                                    out.print("</fieldset>");
                                } else {
                                    Object[] obj_proveedor_detalle = (Object[]) lst_proveedor_detalle.get(0);
                                    out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                    out.print("<fieldset class='popup_local' style='width:auto;visibility:visible;position:fixed;top: 50px;left: 15%;width:800px;overflow:scroll;height:600px;'>");
                                    out.print("<div align='left'>");
                                    out.print("<a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img align='right' src='Interfaz/Contenido/Iconos/Delete.png' witdth='26px' height='26px' ></a></br>");
                                    lst_empresas_externas = jpacprov.proveedores();
                                    out.print("<b>Lista de externos:</b></br>");
                                    out.print("<form action='Programacion?opc=7&Id_programacion=" + id_programacion + "' method='post' name='Form_id_proveedor' id='Form_id_proveedor'>");
                                    out.print("<select  name='Cbx_proveedor' id='Cbx_proveedor' onchange='javascript:Form_id_proveedor.submit()' title='Proveedores' style ='width: 188;'>");
                                    out.print("<option value='' style='display:none'>Seleccionar</option>");
                                    for (int i = 0; i < lst_empresas_externas.size(); i++) {
                                        Object[] obj_empresas_externas = (Object[]) lst_empresas_externas.get(i);
                                        if (!obj_empresas_externas[2].equals("N/A")) {
                                            if (((Integer) obj_empresas_externas[0]).intValue() == id_proveedor) {
                                                out.print("<option value='" + obj_empresas_externas[0] + "' selected>" + obj_empresas_externas[2] + "</option>");
                                            } else {
                                                out.print("<option value='" + obj_empresas_externas[0] + "'>" + obj_empresas_externas[2] + "</option>");
                                            }
                                        }
                                    }
                                    out.print("</select><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_proveedor');mySelect.add(Validate.Persisten, { within: ['0'], failureMessage: \"\"});</script>");

                                    out.print("</form>");
                                    out.print("<form action='Programacion?opc=20&Id_programacion=" + id_programacion + "' method='post' name='Form_externos' id='Form_externos'>");
                                    out.print("<input type='hidden' name='Txt_trabajadores_externos' id='Txt_trabajadores_externos_registro' value='" + obj_proveedor_detalle[3] + "'/>");
                                    out.print("<input type='hidden' name='Id_solicitud_externos' id='Id_solicitud_externos' onclick=\"this.focus(); this.select();\"  value='" + obj_proveedor_detalle[4] + "'/>");
                                    out.print("<input type='hidden' name='Id_proveedor' id='Id_proveedor' value='" + id_proveedor + "' />");
                                    out.print("</br><b>Trabajadores Externos:</b></br>");
                                    out.print("<textarea style='width:300px;height:50px;text-transform: uppercase;' placeholder='TRABAJADORES EXTERNOS' name='Txt_trabajadores_externos_temp' id='Txt_trabajadores_externos_temp' onKeyDown='Salto_linea()' onchange='Salto_linea()' onKeyUp='Salto_linea()'>" + obj_proveedor_detalle[3].toString().replace("---", "\n") + "</textarea>");
                                    out.print("<input type='submit' style='margin-left:70%;margin-top:-5%;' value='Generar Cartas'/>");
                                    out.print("</br><table class='table' width='90%'>");
                                    out.print("<tr>");
                                    out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>Item<hr />ID</b></td>");
                                    out.print("<td style=\"background-color: #ddf5f9;\" align='center'><b>Solicitudes</b></td>");
                                    out.print("</tr>");
                                    int Daggo = 0;
                                    for (int z = 0; z < 2; z++) {
                                        String tipado = "F";
                                        String Tipa2 = "Farmaceutico";
                                        if (z == 1) {
                                            Daggo = 0;
                                            tipado = "I";
                                            Tipa2 = "Insumos";
                                        }
                                        for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                            Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                            if (obj_solicitudes_programadas[15].equals(tipado)) {
                                                if (Daggo == 0) {
                                                    out.print("<tr>");
                                                    out.print("<th colspan='9'>" + Tipa2 + "</th>");
                                                    out.print("</tr>");
                                                }
                                                Daggo++;
                                                out.print("<tr>");
                                                out.print("<td style=\"background-color: #ddf5f9; width:5%;\" align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                                out.print("<td style=\"background-color: #ddf5f9;\" align='center'><b>ubicacion:</b>" + obj_solicitudes_programadas[7] + "</br>" + "<b>Descripcion de solicitud:</b>" + obj_solicitudes_programadas[8] + "</td>");

                                                out.print("</tr>");

                                                out.print("<tr>");
                                                out.print("<th style='background-color: #a29e9f;' ></th>");
                                                out.print("<th colspan='8' style='background-color: #a29e9f;'>Actividades</td>");
                                                out.print("</tr>");
                                                lst_historial_actividades = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                                if (lst_historial_actividades != null) {
                                                    for (int j = 0; j < lst_historial_actividades.size(); j++) {
                                                        out.print("<tr>");
                                                        Object[] obj_historial_actividades = (Object[]) lst_historial_actividades.get(j);
                                                        try {
                                                            String provedor_detalle = obj_proveedor_detalle[4].toString().replace("][", "]-[");
                                                            String[] vector_proveedor = provedor_detalle.split("-");
                                                            int c = 0;
                                                            for (int k = 0; k < vector_proveedor.length; k++) {
                                                                if (vector_proveedor[k].contains("[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]")) {
                                                                    c = 1;
                                                                }
                                                            }
                                                            if (c == 1) {
                                                                out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" checked /></td>");
                                                            } else {
                                                                out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                            }
                                                        } catch (Exception e) {
                                                            out.print("<td><input type='checkbox' name='box" + obj_historial_actividades[0] + "' " + "id='box" + obj_historial_actividades[0] + "' " + "value='[" + obj_solicitudes_programadas[10] + "/" + obj_historial_actividades[0] + "]' onclick=\"externos_sub(this);\" /></td>");
                                                        }
                                                        out.print("<td colspan=\"8\">" + obj_historial_actividades[1] + "</td>");
                                                        out.print("</tr>");
                                                    }
                                                } else {
                                                    out.print("<tr>");
                                                    out.print("<td></td>");
                                                    out.print("<td colspan=\"8\">");
                                                    out.print("<b class='naranja'>No hay actividades</b>");
                                                    out.print("</td>");
                                                    out.print("</tr>");
                                                }
                                            }
                                        }
                                    }
                                    out.print("</table>");
                                    out.print("</form>");
                                    out.print("</div>");
                                    out.print("</fieldset>");
                                }
                                out.print("</div>");
                            }
                            out.print("<table class='table' style='width:100%' >");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4' style='width:25%;'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                            out.print("<td align='center' colspan='4' style='width:30%;'>Locativos Programados para </br><b>" + obj_programacion[2] + "</b> Hasta <b>" + obj_programacion[3] + "</b></td>");
                            out.print("<td align='center' style='width:35%;'><b>" + obj_programacion[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='5'><b>Observaciones :</b>" + obj_programacion[4] + "</br>");
                            out.print("<b>Responsable interno :</b>" + obj_programacion[6] + "");
                            out.print("</td>");
                            try {
                                lst_adjunto_correo = jpacevd.Adjuntos_correo(id_programacion);
                                out.print("<td colspan='4'><b>Anexos :</b>");
                                if (lst_adjunto_correo != null) {
                                    Object[] obj_adjunto_plano = (Object[]) lst_adjunto_correo.get(0);
                                    out.print("<a href='Descargar_plano?file_name=" + obj_adjunto_plano[2] + "'>" + obj_adjunto_plano[2] + "</a></br>");
                                }
                                out.print("</td>");
                            } catch (Exception localException3) {
                            }
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table' style='width:100%' >");
                            out.print("<tr>");
                            out.print("<td style='width:5%' align='center'><b>Item<hr />ID</b></td>");
                            out.print("<td colspan='5' style='width:50%;' align='center'><b>Solicitud</b></td>");
                            out.print("<td colspan='5' align='center'><b>Actividades</b></td>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                if (obj_solicitudes_programadas[15].equals("F")) {
                                    if (Cont_f == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Farmaceutico</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_f++;
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        out.print("<tr>");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        out.print("<td align='center'><b title='EMPRESAS EXTERNAS\n\n");
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                        String[] arg_responsables = obj_personal_externo[1].toString().split("---");
                                                        for (int q = 0; q < arg_responsables.length; q++) {
                                                            out.print("*" + arg_responsables[q] + "\n");
                                                        }
                                                        out.print("\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("'>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5'><b>Solicitante: </b>" + obj_solicitudes_programadas[6] + "<b>/fecha :</b>" + obj_solicitudes_programadas[5] + "</br>");
                                        out.print("<b>ubicado en </b> " + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br><b>Descripción:</b>" + obj_solicitudes_programadas[8] + "");
                                        if (obj_solicitudes_programadas[17] == null) {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "S");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        } else {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "R");
                                            out.print("</br><b> seguimiento :</br></b>" + obj_solicitudes_programadas[17] + "</br>");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos seguimiento :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td colspan='3' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:250px'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:240px'><b>Se requiere área lista?</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            out.print("</tr>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                    }
                                } else {
                                    if (Cont_i == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Insumo</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_i++;
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        Object[] obj_actividades = (Object[]) lst_actividades.get(j);
                                        out.print("<tr>");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        out.print("<td align='center'><b title='EMPRESAS EXTERNAS\n\n");
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                        String[] arg_responsables = obj_personal_externo[1].toString().split("---");
                                                        for (int q = 0; q < arg_responsables.length; q++) {
                                                            out.print("*" + arg_responsables[q] + "\n");
                                                        }
                                                        out.print("\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("'>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5'><b>Solicitante: </b>" + obj_solicitudes_programadas[6] + "<b>/fecha :</b>" + obj_solicitudes_programadas[5] + "</br>");
                                        out.print("<b>ubicado en </b> " + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br><b>Descripción:</b>" + obj_solicitudes_programadas[8] + "");
                                        if (obj_solicitudes_programadas[17] == null) {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "S");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        } else {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "R");
                                            out.print("</br><b> seguimiento :</br></b>" + obj_solicitudes_programadas[17] + "</br>");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos seguimiento :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td colspan='3' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:250px'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:240px'><b>Se requiere área lista?</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            out.print("</tr>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table>");
                                        out.print("</tr>");
                                    }
                                }
                            }
                            out.print("</table>");
                        } else if (obj_programacion[5].equals(Integer.valueOf(3))) {
                            out.print("<form action='Programacion?opc=12' id='form_actualizar_solicitud' name='form_actualizar_solicitud' method='post'>");
                            out.print("<input type='hidden' id='Cont_sol' name='Cont_sol' value='" + lst_solicitudes_programadas.size() + "'/>");
                            out.print("<input type='hidden' id='Id_programacion' name='Id_programacion' value='" + id_programacion + "'/>");
                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                Object[] obj_solicitud_cierre_programacion = (Object[]) lst_solicitudes_programadas.get(i);
                                out.print("<input type='hidden' id='Id_solicitud" + i + "' name='Id_solicitud" + i + "' value='" + obj_solicitud_cierre_programacion[10] + "'/>");
                            }
                            out.print("</form>");
                            lst_actividad_programadas = jpacact.Consultar_actividades_programacion(id_programacion);
                            Cont_de_actividades = jpacacta.Contador_de_actividades(id_programacion);
                            Object[] cont_acti = (Object[]) Cont_de_actividades.get(0);
                            out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");
                            out.print("<div style='float:right;'>");
                            if (Integer.parseInt(cont_acti[0].toString()) > 0) {
                                out.print("| <a href='#' onclick='mostrar_" + id_programacion + "();' ><img src='Interfaz/Contenido/Iconos/Plus.png' style=\"padding-right: 10px;\" alt='edit' title='Actividades adicionales' /></a>Actividades adicionales " + "<span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>" + cont_acti[0] + "</span>");
                            }
                            if (!nombre_rol.equals("Consulta")) {
                                out.print("| <a href='#' onclick='VolverEjecucion(" + id_programacion + ");'><img src=\"Interfaz/Contenido/Iconos/Volver.png\" style=\"padding-right: 10px;\" alt=\"\" title='Volver a ejecución' /></a>Volver a ejecución | ");
                                out.print("<a href='#' onclick='Confirmar_cierre_de_programacion();'><img src='Interfaz/Contenido/Iconos/Open.png' alt='Cerrar programación' style='padding-right:10px;'/></a>Cerrar Programación");
                            }
                            out.print("</div>");
                            out.print("<script type='text/javascript'>");
                            out.print("function mostrar_" + id_programacion + "(){");
                            out.print("document.getElementById('oculto_" + id_programacion + "').style.display = 'block';}");
                            out.print("</script>");
                            out.print("<div id='oculto_" + id_programacion + "' style='display:none;float:left'>");
                            out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:600px;visibility:visible;position:fixed;top: 100px;left: 27%;height:500px;overflow:scroll;'>");
                            out.print("<div align='right' style='float:rigth'><a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Minimizar'></a></div>");
                            out.print("<h3 align='left'>Actividades adicionales</h3>");
                            lst_actividades_adicionales = jpacacta.Consultar_actividades_adicionales(id_programacion);
                            if (lst_actividades_adicionales != null) {
                                for (int i = 0; i < lst_actividades_adicionales.size(); i++) {
                                    Object[] obj_actividades = (Object[]) lst_actividades_adicionales.get(i);
                                    out.print("<b>Ubicación:</b></br>");
                                    out.print("" + obj_actividades[1] + "</br>");
                                    out.print("<b>Actividad:</b></br>");
                                    out.print("" + obj_actividades[2] + "");
                                    out.print("<hr/>");
                                }
                            }
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<form action='Programacion?opc=11&Id_programacion=" + id_programacion + "' id='form_ejecucion' method='post'>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4' style='width:25%;'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                            out.print("<td align='center' colspan='4' style='width:30%;'>Locativos Programados para </br><b>" + obj_programacion[2] + "</b> Hasta <b>" + obj_programacion[3] + "</b></td>");
                            out.print("<td align='center' style='width:35%;'><b>" + obj_programacion[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='9'><b>Observaciones :</b>" + obj_programacion[4] + "</br>");
                            out.print("<b>Responsable interno :</b>" + obj_programacion[6] + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<td style='width:5%' align='center'><b>Item<hr />ID</b></td>");
                            out.print("<td colspan='5' style='width:50%;' align='center'><b>Solicitud</b></td>");
                            out.print("<td colspan='5' align='center'><b>Actividades</b></td>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                if (obj_solicitudes_programadas[15].equals("F")) {
                                    if (Cont_f == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Farmaceutico</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_f++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        Object[] obj_actividades = (Object[]) lst_actividades.get(j);
                                        out.print("<tr>");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        out.print("<td align='center'><b title='EMPRESAS EXTERNAS\n\n");
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                        String[] arg_responsables = obj_personal_externo[1].toString().split("---");
                                                        for (int q = 0; q < arg_responsables.length; q++) {
                                                            out.print("*" + arg_responsables[q] + "\n");
                                                        }
                                                        out.print("\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("'>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5'><b>Solicitante: </b>" + obj_solicitudes_programadas[6] + "<b>/fecha: </b>" + obj_solicitudes_programadas[5] + "</br>");
                                        out.print("<b>ubicado en </b> " + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br><b>Descripción:</b>" + obj_solicitudes_programadas[8] + "");
                                        if (obj_solicitudes_programadas[17] == null) {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "S");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        } else {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "R");
                                            out.print("</br><b> seguimiento :</br></b>" + obj_solicitudes_programadas[17] + "</br>");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos seguimiento :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:60%'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:30%'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:10%'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'>No requerida</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            }
                                            if (!obj_actividades_programadas[9].equals("N/A")) {
                                                out.print("<td align='center' style='background:rgba(205, 162, 10, 0.41);' title='Observacion:\n" + obj_actividades_programadas[9] + "'>" + obj_actividades_programadas[8] + "</br>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[8] + "</br>");
                                            }
                                            out.print("</td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    }
                                } else if (obj_solicitudes_programadas[15].equals("I")) {
                                    if (Cont_i == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Insumos</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_i++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        Object[] obj_actividades = (Object[]) lst_actividades.get(j);
                                        out.print("<tr>");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        out.print("<td align='center'><b title='EMPRESAS EXTERNAS\n\n");
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                        String[] arg_responsables = obj_personal_externo[1].toString().split("---");
                                                        for (int q = 0; q < arg_responsables.length; q++) {
                                                            out.print("*" + arg_responsables[q] + "\n");
                                                        }
                                                        out.print("\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("'>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5'><b>Solicitante: </b>" + obj_solicitudes_programadas[6] + "<b>/fecha: </b>" + obj_solicitudes_programadas[5] + "</br>");

                                        out.print("<b>ubicado en </b> " + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br><b>Descripción:</b>" + obj_solicitudes_programadas[8] + "");
                                        if (obj_solicitudes_programadas[17] == null) {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "S");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\S\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        } else {
                                            lst_adjuntos = jpacevd.Adjuntos_origen(Integer.parseInt(obj_solicitudes_programadas[10].toString()), "R");
                                            out.print("</br><b> seguimiento :</br></b>" + obj_solicitudes_programadas[17] + "</br>");
                                            if (lst_adjuntos != null) {
                                                out.print("<dir /><b> Anexos seguimiento :</b></br>");
                                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                                    out.print("<a href='Descargar?file_name=" + obj_adjuntos[3] + "&ruta_proyecto=" + obj_adjuntos[7] + "\\R\\'>" + obj_adjuntos[3] + "</a></br>");
                                                }
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:60%'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:30%'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:10%'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'>No requerida</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            }
                                            if (!obj_actividades_programadas[9].equals("N/A")) {
                                                out.print("<td align='center' style='background:rgba(205, 162, 10, 0.41);' title='Observacion:\n" + obj_actividades_programadas[9] + "'>" + obj_actividades_programadas[8] + "</br>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[8] + "</br>");
                                            }
                                            out.print("</td>");
                                        }
                                        out.print("</tr>");
                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    }
                                }
                            }
                            out.print("</table>");
                            out.print("</form>");
                        } else if (obj_programacion[5].equals(Integer.valueOf(4))) {
                            lst_actividad_programadas = jpacact.Consultar_actividades_programacion(id_programacion);
                            Cont_de_actividades = jpacacta.Contador_de_actividades(id_programacion);
                            Object[] cont_acti = (Object[]) Cont_de_actividades.get(0);
                            out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");
                            out.print("<div style='float:right;'>");
                            if (Integer.parseInt(cont_acti[0].toString()) > 0) {
                                out.print("| <a href='#' onclick='mostrar_" + id_programacion + "();' ><img src='Interfaz/Contenido/Iconos/Plus.png' style=\"padding-right: 10px;\" alt='edit' title='Actividades adicionales' /></a>Actividades adicionales " + "<span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>" + cont_acti[0] + "</span>");
                            }
                            if (!nombre_rol.equals("Consulta")) {
                                lst_adjuntos = jpacevd.Adjuntos_origen(id_programacion, "Pr");
                                if ((lst_adjuntos == null) || (lst_adjuntos.isEmpty())) {
                                    out.print("| <a href='#' onclick='Anexos_" + id_programacion + "();'><img src=\"Interfaz/Contenido/Iconos/Adjunto.png\" style=\"padding-right: 10px;\" alt=\"\" title='Anexos' /></a>Anexos <span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>0</span> | ");
                                } else {
                                    out.print("| <a href='#' onclick='Anexos_" + id_programacion + "();'><img src=\"Interfaz/Contenido/Iconos/Adjunto.png\" style=\"padding-right: 10px;\" alt=\"\" title='Anexos' /></a>Anexos <span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>" + lst_adjuntos.size() + "</span> | ");
                                }
                            }
                            out.print("<img src='Interfaz/Contenido/Iconos/Close.png' alt='Programación Cerrada' style='padding-right:10px;'/>Programación Cerrada");
                            out.print("</div>");
                            out.print("<script type='text/javascript'>");
                            out.print("function mostrar_" + id_programacion + "(){");
                            out.print("document.getElementById('oculto_" + id_programacion + "').style.display = 'block';}");
                            out.print("</script>");
                            out.print("<div id='oculto_" + id_programacion + "' style='display:none;float:left'>");
                            out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:600px;visibility:visible;position:fixed;top: 100px;left: 27%;height:500px;overflow:scroll;'>");
                            out.print("<div align='right' style='float:rigth'><a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Minimizar'></a></div>");
                            out.print("<h3 align='left'>Actividades adicionales</h3>");
                            lst_actividades_adicionales = jpacacta.Consultar_actividades_adicionales(id_programacion);
                            if (lst_actividades_adicionales != null) {
                                for (int i = 0; i < lst_actividades_adicionales.size(); i++) {
                                    Object[] obj_actividades = (Object[]) lst_actividades_adicionales.get(i);
                                    out.print("<b>Ubicación:</b></br>");
                                    out.print("" + obj_actividades[1] + "</br>");
                                    out.print("<b>Actividad:</b></br>");
                                    out.print("" + obj_actividades[2] + "");
                                    out.print("<hr/>");
                                }
                            }
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<script type='text/javascript'>function Anexos_" + id_programacion + "(){" + "document.getElementById('Div_anexos" + id_programacion + "').style.display='block';}" + "</script>");

                            out.print("<div id='Div_anexos" + id_programacion + "' style='display:none;float:left'>");
                            out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:600px;visibility:visible;position:fixed;top: 100px;left: 27%;height:500px;overflow:scroll;'>");
                            out.print("<div align='right' style='float:rigth'><a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Minimizar'></a></div>");
                            out.print("<h3 align='left'>Anexos de la programación</h3><form action='Adjunto_anexos.jsp' method='post' enctype='multipart/form-data'><input type='hidden' name='Id_origen' value='" + id_programacion + "'/>" + "<input type='hidden' name='Tipo_origen' value='Pr'/>" + "<input type='file' name='Txt_adjunto' required /></br>" + "<br /><b>Observación</b><br/>" + "<textarea name='Txt_observacion' id='Txt_observacion' style='width:450px' value='N/A' placeholder='Observación del archivo adjunto' onchange='javascript:this.value=this.value.toUpperCase();'>N/A</textarea>" + "<script type='text/javascript'>var validation = new LiveValidation('Txt_observacion');validation.add( Validate.Presence );</script>" + "<br/><input type='submit' value='Adjuntar' /></form>");
                            if ((lst_adjuntos == null) || (lst_adjuntos.isEmpty())) {
                                out.print("<b class='naranja'>No hay archivos adjuntos.</b>");
                            } else {
                                out.print("<br/>");
                                out.print("<h3>Historual de anexos</h3>");
                                out.print("<table class='table' style='width:100%;'>");
                                out.print("<tr>");
                                out.print("<th>Adjunto</th>");
                                out.print("<th>Fecha</th>");
                                out.print("<th>Observaciones</th>");
                                out.print("</tr>");
                                for (int m = 0; m < lst_adjuntos.size(); m++) {
                                    Object[] obj_adjuntos = (Object[]) lst_adjuntos.get(m);
                                    out.print("<tr>");
                                    out.print("<td>");
                                    out.print("<a href='Descargar_anexos?file_name=" + obj_adjuntos[3] + "'>" + obj_adjuntos[3] + "</a>" + "</td>");

                                    out.print("<td>" + obj_adjuntos[6] + "</td>");
                                    out.print("<td>" + obj_adjuntos[4] + "</td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                            }
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4' style='width:25%;'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                            out.print("<td align='center' colspan='4' style='width:30%;'>Locativos Programados para </br><b>" + obj_programacion[2] + "</b> Hasta <b>" + obj_programacion[3] + "</b></td>");
                            out.print("<td align='center' style='width:35%;'><b>" + obj_programacion[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='9'><b>Observaciones :</b>" + obj_programacion[4] + "</br>");
                            out.print("<b>Responsable interno :</b>" + obj_programacion[6] + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td style='width:5%' align='center'><b>Item<hr />ID</b></td>");
                            out.print("<td colspan='5' style='width:50%;' align='center'><b>Solicitud</b></td>");
                            out.print("<td colspan='5' align='center'><b>Actividades</b></td>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                if (obj_solicitudes_programadas[15].equals("F")) {
                                    if (Cont_f == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Farmaceutico</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_f++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        out.print("<tr>");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        out.print("<td align='center'><b title='EMPRESAS EXTERNAS\n\n");
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                        String[] arg_responsables = obj_personal_externo[1].toString().split("---");
                                                        for (int q = 0; q < arg_responsables.length; q++) {
                                                            out.print("*" + arg_responsables[q] + "\n");
                                                        }
                                                        out.print("\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("'>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5'><b>Solicitante:</b>" + obj_solicitudes_programadas[6] + " <b>/fecha: </b>" + obj_solicitudes_programadas[5] + "</br>");
                                        out.print("<b>ubicado en </b> " + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br><b>Descripción:</b>" + obj_solicitudes_programadas[8] + "");
                                        out.print("</td>");
                                        out.print("<td colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:250px'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:100px'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:100px'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'>No requerida</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            }
                                            if (!obj_actividades_programadas[9].equals("N/A")) {
                                                out.print("<td align='center' style='background:rgba(205, 162, 10, 0.41);' title='Observacion:\n" + obj_actividades_programadas[9] + "'>" + obj_actividades_programadas[8] + "</br>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[8] + "</br>");
                                            }
                                            out.print("</td>");
                                            out.print("</tr>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    }
                                } else if (obj_solicitudes_programadas[15].equals("I")) {
                                    if (Cont_i == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Insumos</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_i++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        out.print("<tr>");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        out.print("<td align='center'><b title='EMPRESAS EXTERNAS\n\n");
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                        String[] arg_responsables = obj_personal_externo[1].toString().split("---");
                                                        for (int q = 0; q < arg_responsables.length; q++) {
                                                            out.print("*" + arg_responsables[q] + "\n");
                                                        }
                                                        out.print("\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("'>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5'><b>Solicitante:</b>" + obj_solicitudes_programadas[6] + " <b>/fecha: </b>" + obj_solicitudes_programadas[5] + "</br>");
                                        out.print("<b>ubicado en </b> " + obj_solicitudes_programadas[7] + "<b>-</b>" + obj_solicitudes_programadas[14] + "</br><b>Descripción:</b>" + obj_solicitudes_programadas[8] + "");
                                        out.print("</td>");
                                        out.print("<td colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:250px'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:100px'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:100px'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'>No requerida</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            }
                                            if (!obj_actividades_programadas[9].equals("N/A")) {
                                                out.print("<td align='center' style='background:rgba(205, 162, 10, 0.41);' title='Observacion:\n" + obj_actividades_programadas[9] + "'>" + obj_actividades_programadas[8] + "</br>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[8] + "</br>");
                                            }
                                            out.print("</td>");
                                            out.print("</tr>");
                                        }
                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    }
                                }
                            }
                            out.print("</tr>");
                            out.print("</table>");
                        }
                    } else {
                        lst_programacion_estado = jpacpro.Traer_estado_de_programacion_ejecutor(id_programacion);
                        Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                        if (obj_programacion[5].equals(Integer.valueOf(2))) {
                            Cont_de_actividades = jpacacta.Contador_de_actividades(id_programacion);
                            Object[] cont_acti = (Object[]) Cont_de_actividades.get(0);
                            out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver'/></a>");

                            String Guardado = this.pageContext.getRequest().getParameter("Guardado");
                            if (Guardado == null) {
                                Guardado = "";
                            }
                            if (Guardado.equals("Guardado")) {
                                out.print("<div style='float:right;'>| <a href='#' onclick='mostrar_" + id_programacion + "();' ><img src='Interfaz/Contenido/Iconos/Plus.png' style=\"padding-right: 10px;\" alt='edit' title='Actividades Adicionales' /></a>Actividades adicionales " + "<span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>" + cont_acti[0] + "</span> | ");
                                out.print("<a onclick='Terminar_ejecucion(" + id_programacion + ");'><img src=\"Interfaz/Contenido/Iconos/Open.png\" style=\"padding-right: 10px;\" alt=\"\" title='Terminar ejecución' /></a>Terminar ejecución | ");
                            } else {
                                out.print("<div style='float:right;'>| <a href='#'><img src='Interfaz/Contenido/Iconos/Plus_Block.png' style=\"padding-right: 10px;\" alt='edit' title='No se ha Guardado' /></a>Actividades adicionales <span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>" + cont_acti[0] + "</span> | ");
                                out.print("<img src=\"Interfaz/Contenido/Iconos/Open_Block.png\" style=\"padding-right: 10px;\" alt=\"\" title='No se ha Guardado' />Terminar ejecución | ");
                            }
                            out.print("<a href='#' onclick='Guardar_ejecucion_prog();' ><img src='Interfaz/Contenido/Iconos/Save.png' alt='Guardar' style='padding-right:10px;'/></a>Guardar</div>");
                            out.print("<script type='text/javascript'>");
                            out.print("function mostrar_" + id_programacion + "(){");
                            out.print("document.getElementById('oculto_" + id_programacion + "').style.display = 'block';}");
                            out.print("</script>");
                            if (this.pageContext.getRequest().getAttribute("Act_Add").toString().equals("Act_Add")) {
                                String Raf = this.pageContext.getRequest().getAttribute("Raf").toString();
                                if (Raf == "") {
                                    out.print("<div id='oculto_" + id_programacion + "' style='display:none;float:left'>");
                                } else {
                                    out.print("<div id='oculto_" + id_programacion + "' style='display:block;float:left'>");
                                }
                            } else {
                                out.print("<div id='oculto_" + id_programacion + "' style='display:block;float:left'>");
                            }
                            out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:600px;visibility:visible;position:fixed;top: 100px;left: 27%;height:500px;overflow:scroll;'>");
                            out.print("<div align='right' style='float:rigth'><a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Minimizar'></a></div>");
                            out.print("<h3 align='left'><img id=\"Menu_registro\" src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Desplegar Registro' /></a>Actividades Adicional </h3>");

                            lst_actividades_adicionales = jpacacta.Consultar_actividades_adicionales(id_programacion);
                            if (this.pageContext.getRequest().getAttribute("Act_Add").toString().equals("Modoficar_Actividad_Add")) {
                                String Ref = this.pageContext.getRequest().getAttribute("Ref").toString();
                                list_ActA = (List) this.pageContext.getRequest().getAttribute("list_ActA");
                                Object[] Activadad = (Object[]) list_ActA.get(0);
                                int Id_actividad = Integer.parseInt(this.pageContext.getRequest().getParameter("Id_actividad"));
                                out.print("<script>");
                                out.print("$(Menu_registro).click(function() {");
                                out.print("$(\"#toggle\").toggle(\"slide\");");
                                out.print("});");
                                out.print("</script>");
                                if (Ref == "") {
                                    out.print("<div style='display:block;border: 1px solid #dc143c;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                                } else {
                                    out.print("<div style='display:none;border: 1px solid #dc143c;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                                }
                                out.print("<div id='sidebar'>");
                                out.print("<h3>Modificar Actividad Adicional</h3>");
                                out.print("<form action='Programacion?opc=25'  method='post' onsubmit='registroS();'>");
                                out.print("<input type='hidden' name='Id_actividad' id='Id_actividad' value='" + Id_actividad + "'/>");
                                out.print("<input type='hidden' name='Id_programacion' id='Id_actividad' value='" + id_programacion + "'/>");
                                out.print("<b>Ubicación :</b>");

                                out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                                if (lst_ubiccacion == null) {
                                    out.print("<option value=''>No hay Ubicaciones</option>");
                                } else {
                                    for (int j = 0; j < lst_ubiccacion.size(); j++) {
                                        Object[] objet_ubi = (Object[]) lst_ubiccacion.get(j);
                                        if (objet_ubi[1].toString().equals(Activadad[2].toString())) {
                                            out.print("<option selected=\"true\">" + objet_ubi[1] + "</option>");
                                        } else {
                                            out.print("<option>" + objet_ubi[1] + "</option>");
                                        }
                                    }
                                }
                                out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);val1.add(Validate.Txt_ubicacion);</script>");

                                out.print("<b>Actividad Adicional :</b>");
                                out.print("<textarea type='text' name='Txt_actividad' style='height:150px;' id='Txt_actividad' title='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'>" + Activadad[3] + "</textarea>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad');val1.add(Validate.Presence);</script>");

                                out.print("<input type='submit' id='btsubmit' value='Modificar Actividad Adicional' style='width: 188;'/> </br></br>");
                                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n          <div></div>\n          <div></div>\n          <div></div>\n        </div>");

                                out.print("</form>");
                                out.print("</div><div class='cleaner'> </div>");
                                out.print("</div>");
                            } else {
                                out.print("<script>");
                                out.print("$(Menu_registro).click(function() {");
                                out.print("$(\"#toggle\").toggle(\"slide\");");
                                out.print("});");
                                out.print("</script>");
                                out.print("<div style='display:none;border: 1px solid #dc143c;border-right:none;backgroung-color:#fff;position:absolute' id=\"toggle\">");
                                out.print("<div id='sidebar'>");
                                out.print("<h3>Registrar Actividad Adicional</h3>");
                                out.print("<form action='Programacion?opc=21'  method='post' onsubmit='registroS();'>");
                                out.print("<input type='hidden' name='cantidad_actividades' id='cantidad_actividades' value='-0'/>");
                                out.print("<input type='hidden' name='Cont_actividades_modificar' id='Cont_actividades_modificar' value='0'/>");
                                out.print("<input type='hidden' name='id_programacion' id='id_programacion' value='" + id_programacion + "'/>");
                                out.print("<b>Ubicación :</b>");

                                out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                                out.print("<option value=''>Seleccione Ubicacion</option>");
                                if (lst_ubiccacion == null) {
                                    out.print("<option value=''>No hay Ubicaciones</option>");
                                } else {
                                    for (int i = 0; i < lst_ubiccacion.size(); i++) {
                                        Object[] objet_ubi = (Object[]) lst_ubiccacion.get(i);
                                        out.print("<option>" + objet_ubi[1] + "</option>");
                                    }
                                }
                                out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);val1.add(Validate.Txt_ubicacion);</script>");

                                out.print("<b>Actividad Adicional :</b>");
                                out.print("<textarea type='text' placeholder='...' name='Txt_actividad' style='height:150px;' id='Txt_actividad' title='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad');val1.add(Validate.Presence);</script>");

                                out.print("<input type='submit' id='btsubmit' value='Registrar Actividad Adicional' style='width: 188;'/> </br></br>");
                                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n          <div></div>\n          <div></div>\n          <div></div>\n        </div>");

                                out.print("</form>");
                                out.print("</div><div class='cleaner'> </div>");
                                out.print("</div>");
                            }
                            if (lst_actividades_adicionales != null) {
                                for (int i = 0; i < lst_actividades_adicionales.size(); i++) {
                                    Object[] obj_actividades_adicionales = (Object[]) lst_actividades_adicionales.get(i);
                                    if (i == 0) {
                                        out.print("<hr/>");
                                        out.print("<table class='table' style='width:100%;'>");
                                        out.print("<tr>");
                                        out.print("<th>Ubicacion</th>");
                                        out.print("<th>Actividad Adicional</th>");
                                        out.print("<th>Eliminar</th>");
                                        out.print("<th>Editar</th>");
                                        out.print("</tr>");
                                    }
                                    out.print("<tr>");
                                    out.print("<td>" + obj_actividades_adicionales[1] + "</td>");
                                    out.print("<td>" + obj_actividades_adicionales[2] + "</td>");
                                    out.print("<td><a onclick='Confirmar_eliminacion_de_actividad(" + obj_actividades_adicionales[0] + "," + id_programacion + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' style='float:left;margin-left: 2%;' title='Eliminar actividad' width='20' height='20'></a></td>");
                                    out.print("<td><a href='Programacion?opc=24&Id_programacion=" + id_programacion + "&Id_actividad=" + obj_actividades_adicionales[0] + "'><img src=\"Interfaz/Contenido/Iconos/Edit.png\" width=\"20px\" height=\"20px\" style=\"margin-top:5%;\" alt=\"edit\" title=\"Modificar Registro\"></a></td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                            } else {
                                out.print("<center>");
                                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                                out.print("<b>No existe ningun actividad adicional</b>");
                                out.print("</center>");
                            }
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4' style='width:25%;'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                            out.print("<td align='center' colspan='4' style='width:30%;'>Locativos Programados para </br><b>" + obj_programacion[2] + "</b> Hasta <b>" + obj_programacion[3] + "</b></td>");
                            out.print("<td align='center' style='width:35%;'><b>" + obj_programacion[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='9'><b>Observaciones :</b>" + obj_programacion[4] + "</br>");
                            out.print("<b>Responsable interno :</b>" + obj_programacion[6] + "</td>");
                            out.print("</tr>");
                            out.print("</table'>");
                            out.print("<form action='Programacion?opc=11&Id_programacion=" + id_programacion + "' id='form_ejecucion' name='form_ejecucion' method='post'>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td style='width:3%' align='center'><b>Item<hr />ID</b></td>");
                            out.print("<td colspan='9' align='center'><b>Actividades</b></td>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                if (obj_solicitudes_programadas[15].equals("F")) {
                                    if (Cont_fa == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Farmaceutico</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_fa++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        Object[] obj_actividades = (Object[]) lst_actividades.get(j);

                                        out.print("<input type='hidden' id='Id_programacion_detalle' name='Id_programacion_detalle' value='" + obj_actividades[0] + "'/>");
                                        out.print("<input type='hidden' id='Id_solicitud' name='Id_solicitud' value='" + obj_actividades[6] + "'/>");
                                        out.print("<input type='hidden' id='ContA' name='ContA' value='" + lst_actividad_programadas.size() + "'/>");
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td  style='width:50%' colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:40%;'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:15%;'><b>Ubicacion</b></td>");
                                        out.print("<td align='center' style='width:15%;'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:30%'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<input type='hidden' id='Id_actividad" + k + obj_actividades[6] + "' name='Id_actividad" + k + obj_actividades[6] + "' value='" + obj_actividades_programadas[0] + "'/>");
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            out.print("<td>" + obj_actividades_programadas[3] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO REQUERIDA")) {
                                                out.print("<td align='center'><b>No se requiere de</br> area lista</b><input type='hidden' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO REQUERIDA'/></td>");
                                            } else if (obj_actividades_programadas[2].equals("SI REQUERIDA")) {
                                                out.print("<td align='center'><b>Si</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO'/></td>");
                                            } else if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'><b>Si</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='SI' '/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO' checked='checked'/></td>");
                                            } else if (obj_actividades_programadas[2].equals("SI")) {
                                                out.print("<td align='center'><b>Si</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO'/></td>");
                                            }
                                            if (obj_actividades_programadas[8] == null) {
                                                out.print("<td align='center'><b>SI</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='NO' />");
                                                out.print("<br /><textarea name='Txt_observaciones" + k + obj_actividades[6] + "' id='Txt_observaciones" + k + obj_actividades[6] + "' placeholder='Observacion' style='text-transform:uppercase;'></textarea></td>");
                                            } else if (obj_actividades_programadas[8].equals("SI")) {
                                                out.print("<td align='center'><b>SI</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='NO'/>");
                                                out.print("<br /><textarea name='Txt_observaciones" + k + obj_actividades[6] + "' id='Txt_observaciones" + k + obj_actividades[6] + "' placeholder='Observacion' style='text-transform:uppercase;'>" + obj_actividades_programadas[9] + "</textarea></td>");
                                            } else {
                                                out.print("<td align='center'><b>SI</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='SI' />" + "<b>NO</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='NO' checked='checked' />");
                                                out.print("<br /><textarea name='Txt_observaciones" + k + obj_actividades[6] + "' id='Txt_observaciones" + k + obj_actividades[6] + "' placeholder='Observacion' style='text-transform:uppercase;'>" + obj_actividades_programadas[9] + "</textarea></td>");
                                            }
                                            out.print("</tr>");
                                        }
                                        out.print("</table></td></tr>");
                                    }
                                } else if (obj_solicitudes_programadas[15].equals("I")) {
                                    if (Cont_in == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Insumos</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_in++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        Object[] obj_actividades = (Object[]) lst_actividades.get(j);

                                        out.print("<input type='hidden' id='Id_programacion_detalle' name='Id_programacion_detalle' value='" + obj_actividades[0] + "'/>");
                                        out.print("<input type='hidden' id='Id_solicitud' name='Id_solicitud' value='" + obj_actividades[6] + "'/>");
                                        out.print("<input type='hidden' id='ContA' name='ContA' value='" + lst_actividad_programadas.size() + "'/>");
                                        out.print("<tr>");
                                        out.print("<td align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td  style='width:50%' colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:40%;'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:15%;'><b>Ubicacion</b></td>");
                                        out.print("<td align='center' style='width:15%;'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:30%'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<input type='hidden' id='Id_actividad" + k + obj_actividades[6] + "' name='Id_actividad" + k + obj_actividades[6] + "' value='" + obj_actividades_programadas[0] + "'/>");
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            out.print("<td>" + obj_actividades_programadas[3] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO REQUERIDA")) {
                                                out.print("<td align='center'><b>No se requiere de</br> area lista</b><input type='hidden' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO REQUERIDA'/></td>");
                                            } else if (obj_actividades_programadas[2].equals("SI REQUERIDA")) {
                                                out.print("<td align='center'><b>Si</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO'/></td>");
                                            } else if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'><b>Si</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='SI' '/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO' checked='checked'/></td>");
                                            } else if (obj_actividades_programadas[2].equals("SI")) {
                                                out.print("<td align='center'><b>Si</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' id='Rdb_area_lista_ejecutor" + k + obj_actividades[6] + "' value='NO'/></td>");
                                            }
                                            if (obj_actividades_programadas[8] == null) {
                                                out.print("<td align='center'><b>SI</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='NO' />");
                                                out.print("<br /><textarea name='Txt_observaciones" + k + obj_actividades[6] + "' id='Txt_observaciones" + k + obj_actividades[6] + "' placeholder='Observacion' style='text-transform:uppercase;'></textarea></td>");
                                            } else if (obj_actividades_programadas[8].equals("SI")) {
                                                out.print("<td align='center'><b>SI</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='SI' checked='checked'/>" + "<b>NO</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='NO'/>");
                                                out.print("<br /><textarea name='Txt_observaciones" + k + obj_actividades[6] + "' id='Txt_observaciones" + k + obj_actividades[6] + "' placeholder='Observacion' style='text-transform:uppercase;'>" + obj_actividades_programadas[9] + "</textarea></td>");
                                            } else {
                                                out.print("<td align='center'><b>SI</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='SI' />" + "<b>NO</b><input type='radio' name='Rdb_ejecutado" + k + obj_actividades[6] + "' id='Rdb_ejecutado" + k + obj_actividades[6] + "' value='NO' checked='checked' />");
                                                out.print("<br /><textarea name='Txt_observaciones" + k + obj_actividades[6] + "' id='Txt_observaciones" + k + obj_actividades[6] + "' placeholder='Observacion' style='text-transform:uppercase;'>" + obj_actividades_programadas[9] + "</textarea></td>");
                                            }
                                            out.print("</tr>");
                                        }
                                        out.print("</table></td></tr>");
                                    }
                                }
                            }
                            out.print("</table>");
                        } else {
                            lst_actividad_programadas = jpacact.Consultar_actividades_programacion(id_programacion);
                            Cont_de_actividades = jpacacta.Contador_de_actividades(id_programacion);
                            Object[] cont_acti = (Object[]) Cont_de_actividades.get(0);
                            out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver'/></a>");
                            if (Integer.parseInt(cont_acti[0].toString()) > 0) {
                                out.print("<div style='float:right;'>| <a href='#' onclick='mostrar_" + id_programacion + "();' ><img src='Interfaz/Contenido/Iconos/Plus.png' style=\"padding-right: 10px;\" alt='edit' title='Actividades adicionales' /></a>Actividades adicionales " + "<span style='background:none repeat scroll 0 0 #dc143c;border-radius: 3px 3px 3px 3px;text-align: center;padding:2px 6px;color:#fff;'>" + cont_acti[0] + "</span> | " + "</div>");
                            }
                            out.print("<script type='text/javascript'>");
                            out.print("function mostrar_" + id_programacion + "(){");
                            out.print("document.getElementById('oculto_" + id_programacion + "').style.display = 'block';}");
                            out.print("</script>");
                            out.print("<div id='oculto_" + id_programacion + "' style='display:none;float:left'>");
                            out.print("<div class='sweet-local' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' style='width:600px;visibility:visible;position:fixed;top: 100px;left: 27%;height:500px;overflow:scroll;'>");
                            out.print("<div align='right' style='float:rigth'><a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Minimizar'></a></div>");
                            out.print("<h3 align='left'>Actividades adicionales</h3>");
                            lst_actividades_adicionales = jpacacta.Consultar_actividades_adicionales(id_programacion);
                            if (lst_actividades_adicionales != null) {
                                for (int i = 0; i < lst_actividades_adicionales.size(); i++) {
                                    Object[] obj_actividades = (Object[]) lst_actividades_adicionales.get(i);
                                    out.print("<b>Ubicación:</b></br>");
                                    out.print("" + obj_actividades[1] + "</br>");
                                    out.print("<b>Actividad:</b></br>");
                                    out.print("" + obj_actividades[2] + "");
                                    out.print("<hr/>");
                                }
                            }
                            out.print("</fieldset>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4' style='width:25%;'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                            out.print("<td align='center' colspan='4' style='width:30%;'>Locativos Programados para </br><b>" + obj_programacion[2] + "</b> Hasta <b>" + obj_programacion[3] + "</b></td>");
                            out.print("<td align='center' style='width:35%;'><b>" + obj_programacion[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='9'><b>Observaciones :</b>" + obj_programacion[4] + "</br>");
                            out.print("<b>Responsable interno :</b>" + obj_programacion[6] + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table' style='width:100%'>");
                            out.print("<tr>");
                            out.print("<td style='width:3%' align='center'><b>Item<hr />ID</b></td>");
                            out.print("<td colspan='9' align='center'><b>Actividades</b></td>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                                Object[] obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                                lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                if (obj_solicitudes_programadas[15].equals("F")) {
                                    if (Cont_f == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Farmaceutico</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_f++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        out.print("<tr>");

                                        out.print("<td align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width: 50%;'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:25%'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:25%'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'>No requerida</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            }
                                            if (!obj_actividades_programadas[9].equals("N/A")) {
                                                out.print("<td align='center' style='background:rgba(205, 162, 10, 0.41);'>" + obj_actividades_programadas[8] + "</br>");
                                                out.print("<b>Observación:</b></br>" + obj_actividades_programadas[9] + "");
                                                out.print("</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[8] + "</br>");
                                                out.print("</td>");
                                            }
                                        }
                                        out.print("</tr>");

                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    }
                                } else if (obj_solicitudes_programadas[15].equals("I")) {
                                    if (Cont_i == 0) {
                                        out.print("<tr>");
                                        out.print("<th colspan='10'>Insumos</th>");
                                        out.print("</tr>");
                                    }
                                    Cont_i++;
                                    lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                    for (int j = 0; j < lst_actividades.size(); j++) {
                                        out.print("<tr>");

                                        out.print("<td align='center'><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td colspan='5' valign='top'>");
                                        out.print("<table width='100%'>");
                                        out.print("<tr>");
                                        out.print("<td align='center' style='width:width: 50%;'><b>Trabajos a ejecutar</b></td>");
                                        out.print("<td align='center' style='width:25%'><b>Area lista para trabajar</b></td>");
                                        out.print("<td align='center' style='width:25%'><b>Ejecutado</b></td>");
                                        out.print("</tr>");
                                        out.print("<tr>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                            out.print("<tr>");
                                            out.print("<td>" + obj_actividades_programadas[1] + "</td>");
                                            if (obj_actividades_programadas[2].equals("NO")) {
                                                out.print("<td align='center'>No requerida</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[2] + "</td>");
                                            }
                                            if (!obj_actividades_programadas[9].equals("N/A")) {
                                                out.print("<td align='center' style='background:rgba(205, 162, 10, 0.41);'>" + obj_actividades_programadas[8] + "</br>");
                                                out.print("<b>Observación:</b></br>" + obj_actividades_programadas[9] + "");
                                                out.print("</td>");
                                            } else {
                                                out.print("<td align='center'>" + obj_actividades_programadas[8] + "</br>");
                                                out.print("</td>");
                                            }
                                        }
                                        out.print("</tr>");

                                        out.print("</tr>");
                                        out.print("</table></td>");
                                    }
                                }
                            }
                            out.print("</table>");
                        }
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                }
            }
            try {
                Imprimir = this.pageContext.getRequest().getAttribute("Imprimir").toString();
            } catch (Exception e) {
                Imprimir = "";
            }
            if (Imprimir.equals("Imprimir")) {
                id_programacion = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_programacion").toString());
                lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                List Proveedores = (List) this.pageContext.getRequest().getAttribute("Proveedores");
                Object[] obj_programacion_filtro = (Object[]) lst_programacion.get(0);
                lst_solicitudes_programadas = (List) this.pageContext.getRequest().getAttribute("lst_solicitudes_filtro");
                try {
                    lectura_I = this.pageContext.getRequest().getAttribute("lectura_I").toString();
                } catch (Exception e) {
                    lectura_I = "";
                }
                out.print("<a href='Programacion?opc=1&Id_programacion=0'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");

                out.print("<div style='float:right;'>|<a onclick='Imprimir();'><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"padding-right: 10px;\" alt=\"\" title='Imprimir' /></a>Imprimir lista |<a style='text-decoration:none;float:right;'><img  id=\"Menu_registro\" style='float:right;' src='Interfaz/Contenido/Iconos/Search.png' width='20px' height='20px' alt='edit' title='menu_flotante'/></div>");

                out.print("<script>");
                out.print("$(Menu_registro).click(function() {");
                out.print("$(\"#toggle\").toggle(\"slide\");");
                out.print("});");
                out.print("</script>");
                out.print("<div style='display:none;width:800px;float:right;position:static;' id=\"toggle\">");
                out.print("<form action=\"Programacion?opc=23\" method=\"post\">");
                out.print("<select name='Proveedor'>");
                out.print("<option value='0'>Seleccione Proveedor</option>");
                for (int i = 1; i < Proveedores.size(); i++) {
                    Object[] Proveedor = (Object[]) Proveedores.get(i);
                    out.print("<option value=\"" + Proveedor[0] + "\">" + Proveedor[2] + "</option>");
                }
                out.print("</select>");
                out.print("<select name='Txt_ubicacion' id='Txt_ubicacion' title='Txt_ubicacion'/>");
                out.print("<option value=''>Seleccione Ubicacion</option>");
                if (lst_ubiccacion == null) {
                    out.print("<option value=''>No hay Ubicaciones</option>");
                } else {
                    for (int i = 0; i < lst_ubiccacion.size(); i++) {
                        Object[] objet_ubi = (Object[]) lst_ubiccacion.get(i);
                        out.print("<option>" + objet_ubi[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("<input type=\"hidden\" name='Id_programacion' value=\"" + id_programacion + "\">");
                out.print("<input type=\"submit\" value=\"Buscar\">");
                out.print("</form>");
                out.print("</div>");

                out.print("<div id='Imprimir'>");

                out.print("<table class='table2' style='width:100%'>");
                out.print("<tr><td colspan='8' style='background-color:#CCC;'align='center'><b>COPIA NO CONTROLADA</b></td></tr>");
                out.print("<tr>");
                out.print("<td align='center' style='width:70%' colspan='3'><img src='Interfaz/Contenido/images/Logo.png' alt='logo' style='width:170.5px; height:69.5px'/></td>");
                out.print("<td align='center' style='width:60%' colspan='3'><b style='color: #292929;'>Listado de locativos programados</b></td>");
                out.print("<td align='center'style='width:10%' colspan='2'>R-MT-054 <hr> Versión 000 </td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='4'><b>Nombre de la programación :</b></br>" + obj_programacion_filtro[1] + "</td>");
                out.print("<td colspan='4'><b>Observación :</b></br>" + obj_programacion_filtro[4] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<th style='width:5%'>Item<hr />ID</th>");
                out.print("<th style='width:10%'>Personal</th>");
                out.print("<th style='width:10%'>Ubicación</th>");
                out.print("<th colspan='2' style='width:35%'>Actividades</th>");
                out.print("<th style='width:10%'>Area Lista</th>");
                out.print("<th style='width:40%'colspan='2'>Ejecutado(&radic;) no(X)<hr>Observaciones</th></tr>");
                out.print("</table>");
                int Daggo = 0;
                for (int z = 0; z < 2; z++) {
                    String tipado = "F";
                    String Tipa2 = "Farmaceutico";
                    if (z == 1) {
                        Daggo = 0;
                        tipado = "I";
                        Tipa2 = "Insumos";
                    }
                    for (int i = 0; i < lst_solicitudes_programadas.size(); i++) {
                        Object[] obj_solicitudes_programadas = null;
                        try {
                            obj_solicitudes_programadas = (Object[]) lst_solicitudes_programadas.get(i);
                        } catch (Exception e) {
                            faster2 = (List) lst_solicitudes_programadas.get(i);
                            obj_solicitudes_programadas = (Object[]) faster2.get(0);
                        }
                        lst_actividades = jpacpdt.Consultar_programacion_detalle(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                        if (obj_solicitudes_programadas[15].equals(tipado)) {
                            if (Daggo == 0) {
                                out.print("<table id='resultados2' class='table' style='width:100%'>");
                                out.print("<tr>");
                                out.print("<th colspan='10'>" + Tipa2 + "</th>");
                                out.print("</tr>");
                            }
                            if (lst_actividades != null) {
                                for (int j = 0; j < lst_actividades.size(); j++) {
                                    if (lectura_I.equals("lectura_I")) {
                                        lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                        row = lst_actividad_programadas.size();
                                    } else {
                                        lst_actividad_programadas = jpacact.Consultar_actividades_programacion(Integer.parseInt(obj_solicitudes_programadas[9].toString()));
                                        for (int k = 0; k < faster2.size(); k++) {
                                            Object[] Obj_row = (Object[]) faster2.get(k);
                                            String id_solici = this.pageContext.getRequest().getAttribute("id_solici").toString();
                                            String[] vector_Soli_Act = id_solici.split("-");
                                            for (int x = 0; x < vector_Soli_Act.length; x++) {
                                                int Vel = Integer.parseInt(vector_Soli_Act[x].split("/")[1]);
                                                if (Vel == Integer.parseInt(Obj_row[19].toString())) {
                                                    row++;
                                                }
                                            }
                                        }
                                    }
                                    Object[] obj_actividades = (Object[]) lst_actividades.get(j);
                                    if (lst_actividad_programadas != null) {
                                        out.print("<tr>");
                                        out.print("<td style='width:5%' align='center' " + (row > 0 ? "rowspan='" + row + "'" : "") + "><b>" + (i + 1) + "<hr /># " + obj_solicitudes_programadas[11] + obj_solicitudes_programadas[1].toString().replace("_", "") + "</b></td>");
                                        out.print("<td style='width:10%' valign='top'" + (row > 0 ? "rowspan='" + row + "'" : "") + ">" + obj_solicitudes_programadas[18] + "");
                                        lst_personal_externo = jpacprovd.Traer_personal_externo(id_programacion);
                                        if (lst_personal_externo != null) {
                                            for (int k = 0; k < lst_personal_externo.size(); k++) {
                                                Object[] obj_personal_externo = (Object[]) lst_personal_externo.get(k);
                                                String id_solicitudes = obj_personal_externo[3].toString().replace("][", "-").replace("]", "").replace("[", "");
                                                String[] vector_Solicitud = id_solicitudes.split("-");
                                                for (int l = 0; l < vector_Solicitud.length; l++) {
                                                    String id_solicitud_vector = vector_Solicitud[l].toString();
                                                    if (id_solicitud_vector == obj_solicitudes_programadas[10]) {
                                                        out.print("" + obj_personal_externo[2] + "\n");
                                                    }
                                                }
                                            }
                                        }
                                        out.print("</td>");
                                        out.print("<td style='width:10%' align='center' " + (row > 0 ? "rowspan='" + row + "'" : "") + "><b>" + obj_actividades[5] + "</b></td>");
                                        for (int k = 0; k < lst_actividad_programadas.size(); k++) {
                                            Object[] obj_actividades_programadas = null;
                                            if (lectura_I.equals("lectura_I")) {
                                                obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                                out.print("<td style='width:35%' colspan='2' style='width:35%'>" + obj_actividades_programadas[1] + "</td>");
                                                out.print("<td style='width:10%' align='center' style='width:10%'><b>Si </b>[&nbsp;&nbsp;&nbsp;&nbsp]</br><b> No</b> [&nbsp;&nbsp;&nbsp;&nbsp] </td>");
                                                out.print("<td style='width:40%' valign='top' colspan='2' style='width:40%'>[&nbsp;&nbsp;&nbsp;&nbsp]</td>");
                                                //out.print("<td style='width:40%' align='center' colspan='2' style='width:40%'><textarea name='Txt_imprimir_observaciones' style='width:90%'>[]</textarea></td>");
                                                out.print("</tr>");
                                            } else {
                                                obj_actividades_programadas = (Object[]) lst_actividad_programadas.get(k);
                                                String id_solici = this.pageContext.getRequest().getAttribute("id_solici").toString();
                                                String[] vector_Soli_Act = id_solici.split("-");
                                                for (int x = 0; x < vector_Soli_Act.length; x++) {
                                                    int Vel = Integer.parseInt(vector_Soli_Act[x].split("/")[1]);
                                                    if (Vel == Integer.parseInt(obj_actividades_programadas[0].toString())) {
                                                        out.print("<td style='width:35%' colspan='2' style='width:35%'>" + obj_actividades_programadas[1] + "</td>");
                                                        out.print("<td style='width:10%' align='center' style='width:10%'><b>Si </b>[  ]</br><b> No</b> [  ] </td>");
                                                        out.print("<td style='width:40%' align='center' colspan='2' style='width:40%'><textarea name='Txt_imprimir_observaciones' style='width:90%'>[]</textarea></td>");
                                                        out.print("</tr>");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    row = 0;
                                }
                            }
                            if (Daggo == lst_solicitudes_programadas.size()) {
                                out.print("</table>");
                            }
                            Daggo++;
                        }
                    }
                }
                out.print("</div>");
            }
            if ((this.pageContext.getRequest().getAttribute("Permiso_ingreso") != null) && (this.pageContext.getRequest().getAttribute("Permiso_ingreso").toString().equals("Permiso_ingreso"))) {
                id_programacion = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_programacion").toString());
                lst_programacion = jpacpro.Traer_programacion_id(id_programacion);
                lst_empresas_externas = jpacprovd.Traer_todas_las_empresas_externas(id_programacion);
                out.print("<a href='Programacion?opc=7&Id_programacion=" + id_programacion + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Volver' /></a>");
                if (lst_empresas_externas == null) {
                    out.print("<center>");
                    out.print("<h3>Permisos de ingreso</h3>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                    out.print("<b>No existe ninguna empresa asociada a esta programación</b>");
                    out.print("</center>");
                } else {
                    for (int j = 0; j < lst_empresas_externas.size(); j++) {
                        Object[] obj_id_empresas = (Object[]) lst_empresas_externas.get(j);
                        out.print("<button class='accordion" + obj_id_empresas[0] + "' style='background-color: #eee;color: #444;cursor: pointer;padding: 9px;width:100%;border: none;text-align:center;outline: none;font-size: 15px;transition: 0.4s;-webkit-box-shadow: 0 0 2px #fff, inset 0 1px 1px #ffffff;'>" + "<b>CARTA DE INGRESO | " + obj_id_empresas[5].toString().substring(0, 1).toUpperCase() + obj_id_empresas[5].toString().substring(1).toUpperCase() + "</b></button>");
                        out.print("<div class='panel'>");
                        out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"Interfaz/HTML_Editor/demo/demo.css\" />");
                        out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"Interfaz/HTML_Editor/jquery-te-1.4.0.css\" />");
                        out.print("<script type=\"text/javascript\" src=\"Interfaz/HTML_Editor/HtmlEditor.js\" charset=\"utf-8\"></script>");
                        out.print("<script type=\"text/javascript\" src=\"Interfaz/HTML_Editor/jquery-te-1.4.0.min.js\" charset=\"utf-8\"></script>");
                        out.print("<div style='float:left'><a onclick='Imprimir_cartas(" + obj_id_empresas[0] + ");'><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir</br> " + "</div>");
                        out.print("<div style='display:none'>");
                        out.print("<textarea name='Txt_formato' id='Txt_formato'></textarea>");
                        out.print("</div>");
                        out.print("<textarea id='Txt_plantilla' class='jqte-test' contenteditable='false'>");
                        Object[] obj_fecha_programacion = (Object[]) lst_programacion.get(0);
                        int fechaVal = Integer.parseInt(obj_fecha_programacion[2].toString().replace("-", ""));
                        String cabeza = "<table style='width:100%'><tr><td style='background:#ccc;width:100%;' colspan='9'><center>COPIA NO CONTROLADA</center></td></tr><tr><td style='border:2px solid;width:25%;padding:11px;'><center><img src='Interfaz/Contenido/images/Logo.png' style='width:170.5px; height:69.5px;'/></center><td style='border:2px solid;width:45%;'><center><b style='color: #292929;'>Registro <hr style='border: 1px solid #000;'/>Permiso de ingreso a personal externo</b></center></td><td style='border:2px solid;width:30%'><center><b style='color: #292929;'>Codigo </br> R-MT-055 <hr style='border: 1px solid #000;'/> Versión <br> " + ((fechaVal >= 20250506) ? "003" : (fechaVal >= 20240508 && fechaVal <= 20250505) ? "002" : "001") + "</b></center></td></tr></table><b style='color: #292929;'>Fecha de generación: </b><div id='fecha" + j + "'></div>";
                        String fecha_programacion = "";
                        if (obj_fecha_programacion[2] == obj_fecha_programacion[3]) {
                            fecha_programacion = "" + obj_fecha_programacion[2];
                        } else {
                            if (fechaVal >= 20240508) {
                                fecha_programacion = "Permiso valido de " + obj_fecha_programacion[2] + " a " + obj_fecha_programacion[3] + "";
                            } else {
                                fecha_programacion = "Permiso valido de " + obj_fecha_programacion[2] + " a " + obj_fecha_programacion[3] + "</br>" + "Planta:";
                            }
                        }
                        String datos_empresa = "";
                        if (fechaVal >= 20240508) {
                            datos_empresa = "<table><b style='color: #292929;'>Empresa</b> :" + obj_id_empresas[5].toString().substring(0, 1).toUpperCase() + obj_id_empresas[5].toString().substring(1).toLowerCase() + "</br>" + "<b style='color: #292929;'>Telefono</b> :" + obj_id_empresas[7].toString().substring(0, 1).toUpperCase() + obj_id_empresas[7].toString().substring(1).toLowerCase() + "</br>" + "<b style='color: #292929;'>Correo</b> :" + obj_id_empresas[8].toString().substring(0, 1).toUpperCase() + obj_id_empresas[8].toString().substring(1).toLowerCase() + "</br>" + "</table>";
                        } else {
                            datos_empresa = "<table><b style='color: #292929;'>PERSONA ENCARGADA DE SERVICIOS GENERALES:</b></br><b style='color: #292929;'>Empresa</b> :" + obj_id_empresas[5].toString().substring(0, 1).toUpperCase() + obj_id_empresas[5].toString().substring(1).toLowerCase() + "</br>" + "<b style='color: #292929;'>Telefono</b> :" + obj_id_empresas[7].toString().substring(0, 1).toUpperCase() + obj_id_empresas[7].toString().substring(1).toLowerCase() + "</br>" + "<b style='color: #292929;'>Correo</b> :" + obj_id_empresas[8].toString().substring(0, 1).toUpperCase() + obj_id_empresas[8].toString().substring(1).toLowerCase() + "</br>" + "</table>";
                        }
                        String mensaje = "<br/><p style='color: #292929;'>Respetados señores:</br> Me permito comunicar a ustedes que el personal relacionado puede ingresar a las instalaciones de PLASTITEC en los días indicados para la ejecución de trabajos en el área:</p>";
                        if (fechaVal >= 20240508) {
                            dotacion = "<b style='color: #292929;'>TODO EL PERSONAL USARA UNIFORME DOS PIEZAS SI INGRESA AREA FARMACEUTICA.</br>";
                        } else {
                            dotacion = "<b style='color: #292929;'>TODO EL PERSONAL USARA UNIFORME DOS PIEZAS SI INGRESA AREA FARMACEUTICA.</br> ES SUMINISTRADA POR: </b></br>";
                        }
                        String titulo_tabla = "<br />Cualquier inquietud comunicarse <b style='color: #292929;'>Ing Luis Cely 315-341-14-65</b>";
                        String tabla_personal = "";
                        String personal = "";
                        personal = personal + obj_id_empresas[3];
                        String[] arg_personal = personal.split("---");
                        if (fechaVal >= 20240508) {
                            tabla_personal = tabla_personal + "" + "Responsable de entrada:______________________________________" + "&ensp;&ensp;MTTO FARM ( ) MTTO INS ( ) T.I ( ) </br></br>" + "<table style='width:100%;font-size:9px;'>" + "<tr>" + "<th witdh='10%' style='border:1px solid;'><b style='color: #292929;'>Nombres</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Cedulas</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Hora de ingreso</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Hora de salida</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Dotación y </br> elementos de seguridad</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Documentos de </br>ARP verificados </b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Firmas </b></th>" + "</tr>";
                        } else {
                            tabla_personal = tabla_personal + "" + "Responsable de entrada:______________________________________" + "&ensp;&ensp;MTTO FARM ( ) MTTO INS ( )</br></br>" + "<table style='width:100%;font-size:9px;'>" + "<tr>" + "<th witdh='10%' style='border:1px solid;'><b style='color: #292929;'>Nombres</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Cedulas</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Hora de ingreso</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Hora de salida</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Dotación y </br> elementos de seguridad</b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Documentos de </br>ARP verificados </b></th>" + "<th  style='border:1px solid;'><b style='color: #292929;'>Firmas </b></th>" + "</tr>";
                        }
                        for (int i = 0; i < arg_personal.length; i++) {
                            if ((arg_personal[i].toString() == null ? "" != null : !arg_personal[i].toString().equals(""))
                                    && (!tabla_personal.contains(arg_personal[i].toUpperCase()))) {
                                tabla_personal = tabla_personal + "<tr>" + "<td style='border:1px solid;'><b style='color: #292929;'>" + arg_personal[i].toUpperCase() + "</b></td>" + "<td style='border:1px solid;width:13%'><br /><br /><br /></td>" + "<td style='border:1px solid;'></td>" + "<td style='border:1px solid;'></td>" + "<td style='border:1px solid;'></td>" + "<td style='border:1px solid;width:14%'>SI(  ) Copias a Folder(  )</td>" + "<td style='border:1px solid;width:15%'></td>" + "</tr>";
                            }
                        }
                        tabla_personal = tabla_personal + "</table>" + "Trabajo terminado SI( ) NO ( ) </br> Trabajo recibido por:_______________________________________  </br>" + "Orden y aseo verificado por: ________________________________ </br>" + "Vigilante turno salida:______________________________________</br>" + "Observaciones:</br>_________________________________________________________________________________________________________________" + "</br>_________________________________________________________________________________________________________________" + "</br>_________________________________________________________________________________________________________________";
                        String pie_pagina = "Cordialmente,</br>Ing Luis A Cely R</br>Dpto de Mantenimiento</br><b style='color: #292929;'>PLASTITEC</b></br>";
                        out.print("<div id='Imprimir" + obj_id_empresas[0] + "' style='background-color:#fff;'>");
                        out.print("<dir style='background-color:#fff;padding-left:5px;'>");
                        out.print("" + cabeza + "</br>");
                        out.print("" + fecha_programacion + "</br>");
                        out.print("" + datos_empresa);
                        out.print("" + mensaje);
                        out.print("" + dotacion + "</br>");
                        out.print("" + tabla_actividades + "</br>");
                        out.print("" + tabla_personal + "</br></br>");
                        out.print("" + titulo_tabla + "</br>");
                        out.print("" + pie_pagina);
                        if (fechaVal >= 20250506) {
                            out.print("<div style='font-style: italic;font-size:10px; margin-top:2%'>La información personal en este documento será tratada y protegida de acuerdo con nuestra politica de protección de datos.</div>");
                        }

                        out.print("</div>");
                        out.print("</textarea>");
                        out.print("<script language='JavaScript'>function Htmlpass() {var m = document.getElementById('Txt_plantilla').value;document.getElementById('Txt_formato').value = m;document.FormSaveDespeje.submit()}</script>");

                        out.print("<script>");
                        out.print("$('.jqte-test').jqte();");
                        out.print("var jqteStatus = true;");
                        out.print("$('.status').click(function(){");
                        out.print("jqteStatus = jqteStatus ? false : true;");
                        out.print("$('.jqte-test').jqte({'status' : jqteStatus})");
                        out.print("});");
                        out.print("</script>");
                        out.print("<script language='JavaScript'>var meses = new Array ('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre');var f=new Date();var div = document.getElementById('fecha" + j + "');" + "div.textContent = (f.getDate() + ' de ' + meses[f.getMonth()] + ' de ' + f.getFullYear());" + "</script>");

                        out.print("</div>");
                        out.print("<script>");
                        out.print("var acc = document.getElementsByClassName('accordion" + obj_id_empresas[0] + "');" + "var i;" + "for (i = 0; i < acc.length; i++) {" + "acc[i].onclick = function(){" + "this.classList.toggle('active');" + "this.nextElementSibling.classList.toggle('show');" + "}}");

                        out.print("</script>");
                        out.print("</form>");
                        out.print("</span>");
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_programacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
