package Tags;

import Controladores.ActividadJpaController;
import Controladores.InstrumentoJpaController;
import Controladores.ParametroJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.UnidadMedidaJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_complemento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            //FIN PERMISOS
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            ActividadJpaController jpacatv = new ActividadJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            UnidadMedidaJpaController jpacumd = new UnidadMedidaJpaController();
            InstrumentoJpaController jpacitm = new InstrumentoJpaController();
            //VARIABLE GLOBALES
            String filtro = "";
            int filtro_vacio = 0;
            int id_tipo_equipo = 0;
            List lst_tipos_equipo = null;
            List lst_tipo_equipo = null;
            List lst_actividades = null;
            List lst_parametros = null;
            List lst_unidades_medida = null;
            List lst_instrumentos = null;
            if (pageContext.getRequest().getAttribute("Complemento") != null) {
                //<editor-fold defaultstate="collapsed" desc="TIPOS DE EQUIPOS">
                if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Tipo_equipos")) {
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Tipo Equipo</h3>");
                    if (rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=9' method='post'>");
                        out.print("<b>Tipo Equipo :</b>");
                        out.print("<input type='text' name='Txt_tipo_equipo' id='Txt_tipo_equipo' placeholder='Nombre Tipo Equipo' title='Nombre tipo de equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo_equipo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Frecuencia de MTTO :</b>");
                        out.print("<input type='text' name='Txt_frecuencia_mtto' id='Txt_frecuencia_mtto' placeholder='Frecuencia MTTO' title='Frecuencia MTTO' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_mtto');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        out.print("<b>Frecuencia de Alerta :</b>");
                        out.print("<input type='text' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alerta' placeholder='Frecuencia Alerta' title='Frecuencia Alerta' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alerta');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    lst_tipos_equipo = jpacteq.Tipos_equipo();
                    if (filtro == null ? "" == null : filtro.equals("")) {
                        lst_tipos_equipo = jpacteq.Tipos_equipo();
                    } else {
                        lst_tipos_equipo = jpacteq.Tipos_equipo();
                        lst_tipos_equipo = jpacteq.Filtrar_tipos_equipo(filtro);
                        if (lst_tipos_equipo == null) {
                            lst_tipos_equipo = jpacteq.Tipos_equipo();
                            filtro_vacio++;
                        }
                    }
                    out.print("<div id='content'>");
                    if (lst_tipos_equipo == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de tipos de equipos registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<h3>Tipos de Equipos");
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            out.print("<div style='float:right'><form action='Complemento?opc=1' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div></h3>");
                        } else if (filtro_vacio > 0) {
                            out.print("<div style='float:right'><form action='Complemento?opc=1' method='post'><b class='rojo'>El valor filtrado no obtubo resultados  </b><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div></h3>");
                        } else {
                            out.print("<div style='float:right'><form action='Complemento?opc=1' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div></h3>");
                        }
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%;'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Tipo Equipo</th>");
                        out.print("<th>Frecuencia PMP</th>");
                        out.print("<th>Frecuencia Alerta</th>");
                        out.print("<th>Actividades</th>");
                        out.print("<th>Parámetros</th>");
                        if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_tipos_equipo.size(); i++) {
                            Object[] obj_tipos_equipo = (Object[]) lst_tipos_equipo.get(i);
                            if (Integer.parseInt(obj_tipos_equipo[3].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center'><b>" + (i + 1) + "</b></td>");
                                out.print("<td>" + obj_tipos_equipo[1] + "</td>");
                                out.print("<td align='center'>" + obj_tipos_equipo[2] + "</td>");
                                out.print("<td align='center'>" + obj_tipos_equipo[6] + "</td>");
                                out.print("<td align='center'><a href='Complemento?opc=3&ite=" + obj_tipos_equipo[0] + "' ><img src='Interfaz/Contenido/Iconos/Actividades.png' alt='edit' title='Ver actividades' /></a></td>");
                                out.print("<td align='center'><a href='Complemento?opc=4&ite=" + obj_tipos_equipo[0] + "' ><img src='Interfaz/Contenido/Iconos/Parametros.png' alt='edit' title='Ver parámetros' /></a></td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarTipoEquipo(" + obj_tipos_equipo[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar tipo equipo' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center'><b class='rojo'>" + (i + 1) + "</b></td>");
                                out.print("<td>" + obj_tipos_equipo[1] + "</td>");
                                out.print("<td align='center'>" + obj_tipos_equipo[2] + "</td>");
                                out.print("<td align='center'>" + obj_tipos_equipo[6] + "</td>");
                                out.print("<td align='center'><a href='Complemento?opc=3&ite=" + obj_tipos_equipo[0] + "' ><img src='Interfaz/Contenido/Iconos/Actividades.png'  alt='edit' title='Ver actividades' /></a></td>");
                                out.print("<td align='center'><a href='Complemento?opc=4&ite=" + obj_tipos_equipo[0] + "' ><img src='Interfaz/Contenido/Iconos/Parametros.png' alt='edit' title='Ver parámetros' /></a></td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarTipoEquipo(" + obj_tipos_equipo[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar tipo equipo' /></a></td>");
                                }
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
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Actividades")) {
                    id_tipo_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_equipo").toString());
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Actividades</h3>");
                    if (rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=7' method='post'>");
                        out.print("<b>Actividad :</b>");
                        out.print("<textarea name='Txt_actividad' id='Txt_actividad' style='height:80px' placeholder='Nombre actividad' title='Nombre actividad' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Id_tipo_equipo' id='Id_tipo_equipo' value='" + id_tipo_equipo + "' />");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    lst_tipo_equipo = jpacteq.Traer_tipo_equipo(id_tipo_equipo);
                    Object[] obj_tipo_equipo = (Object[]) lst_tipo_equipo.get(0);
                    out.print("<h3><form action='Complemento?opc=1&fto=' method='post' name='FormVolver' id='FormVolver'>"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='edit' title='Volver a tipos de equipo' /></a>Actividades <b>" + obj_tipo_equipo[1] + "</b> frecuencia MTTO <b>" + obj_tipo_equipo[2] + "</b>"
                            + "<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar actividad' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan='8'>Actividad</th>");
                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                        out.print("<th colspan='2'>Estado</th>");
                    }
                    out.print("</tr>");
                    lst_actividades = jpacatv.Traer_actividades_tipo_equipo(id_tipo_equipo);
                    if (lst_actividades == null) {
                        out.print("<tr><td colspan='11'>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de actividades asignadas al tipo de equipo</b>");
                        out.print("</center>");
                        out.print("</td></tr>");
                    } else {
                        for (int i = 0; i < lst_actividades.size(); i++) {
                            Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                            if (Integer.parseInt(obj_actividades[4].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center' style='width:50px'><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='8'>" + obj_actividades[1] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='DesactivarActividad(" + obj_actividades[0] + "," + id_tipo_equipo + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar actividad' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center' style='width:50px'>" + (i + 1) + "</td>");
                                out.print("<td colspan='8'>" + obj_actividades[1] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='ActivarActividad(" + obj_actividades[0] + "," + id_tipo_equipo + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar actividad' /></a></td>");
                                }
                            }
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Parametros")) {
                    id_tipo_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_equipo").toString());
                    out.print("<div id='sidebar' style='width:310px'>");
                    out.print("<h3>Registrar Parámetro</h3>");
                    if (rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<script language='JavaScript'>");
                        out.print("function muestra_oculta(){");
                        out.print("var sz = document.forms['Parametros'].elements['Rdb_validador'];");
                        out.print("for (var i=0, len=sz.length; i<len; i++) {");
                        out.print("sz[i].onclick = function() {");
                        out.print("if (this.value == 'Numero') {");
                        out.print("var el = document.getElementById('Datos_parametro');");
                        out.print("el.style.display = (el.style.display == 'none') ? 'block' : 'block'; ");
                        out.print("this.form.Txt_especificacion.value = '';");
                        out.print("this.form.Txt_especificacion_max.value = '';");
                        out.print("this.form.Txt_especificacion_min.value = '';");
                        out.print("}else if (this.value == 'Estado'){");
                        out.print("var el = document.getElementById('Datos_parametro');");
                        out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                        out.print("this.form.Txt_especificacion.value = '0';");
                        out.print("this.form.Txt_especificacion_max.value = '0';");
                        out.print("this.form.Txt_especificacion_min.value = '0';");
                        out.print("}else if (this.value == 'Caracter'){");
                        out.print("var el = document.getElementById('Datos_parametro');");
                        out.print("el.style.display = (el.style.display == 'block') ? 'none' : 'none';");
                        out.print("this.form.Txt_especificacion.value = '0';");
                        out.print("this.form.Txt_especificacion_max.value = '0';");
                        out.print("this.form.Txt_especificacion_min.value = '0';");
                        out.print("}};}}");
                        out.print("window.onload = function(){");
                        out.print("muestra_oculta('Datos_ficha');");
                        out.print("}");
                        out.print("</script>");
                        //FORMULARIO PARAMETROS
                        out.print("<form action='Complemento?opc=8' method='post' id='Parametros'>");
                        out.print("<b>Parámetro :</b><br />");
                        out.print("<textarea name='Txt_parametro' id='Txt_parametro' style='width:293px' placeholder='Nombre parámetro' title='Nombre parámetro' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parametro');val1.add(Validate.Presence);</script><br />");
                        out.print("<b>Instrumento :</b>");
                        out.print("<select name='Cbx_instrumento' id='Cbx_instrumento' style='width:303px' title='Instrumentos'>");
                        out.print("<option value='0' >Seleccionar instrumento</option>");
                        lst_instrumentos = jpacitm.Instrumentos();
                        for (int i = 0; i < lst_instrumentos.size(); i++) {
                            Object[] obj_instrumento = (Object[]) lst_instrumentos.get(i);
                            out.print("<option value='" + obj_instrumento[0] + "' >" + obj_instrumento[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_instrumento');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                        out.print("<b>Unidad de medida :</b>");
                        out.print("<select name='Cbx_unidad_medida' id='Cbx_unidad_medida' style='width:303px' title='Unidad de medida'>");
                        out.print("<option value='0' >Seleccionar unidad de medida</option>");
                        lst_unidades_medida = jpacumd.Unidad_medida();
                        for (int i = 0; i < lst_unidades_medida.size(); i++) {
                            Object[] obj_unidades_medida = (Object[]) lst_unidades_medida.get(i);
                            out.print("<option value='" + obj_unidades_medida[0] + "' >" + obj_unidades_medida[2] + " / " + obj_unidades_medida[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_unidad_medida');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                        //ESPECIFICACIÓN
                        out.print("<b>Tomas :</b>");
                        out.print("<select name='Cbx_toma' id='Cbx_toma' style='width:303px' title='Tomas'>");
                        out.print("<option value='0' >Seleccionar tomas</option>");
                        out.print("<option value='1' >1</option>");
                        out.print("<option value='2' >2</option>");
                        out.print("<option value='3' >3</option>");
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_toma');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                        //VALIDADOR DE CAMPOS
                        out.print("<b>Validador :</b><br />");
                        out.print("<input type='radio' name='Rdb_validador' value='Numero' />Comparador<br />");
                        out.print("<input type='radio' name='Rdb_validador' value='Estado' />Cumple<br />");
                        out.print("<input type='radio' name='Rdb_validador' value='Caracter' />Campo<br />");
                        out.print("<div style='display: none' id='Datos_parametro'>");
                        out.print("<b>Especificación :</b><br />");
                        out.print("<input style='width:150px' type='text' name='Txt_especificacion' id='Txt_especificacion' placeholder='Especificación' title='Especificación' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_especificacion');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>+</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_especificacion_max' id='Txt_especificacion_max' placeholder='Desv +' title='Desviación especificación'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_especificacion_max');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("<b>-</b>");
                        out.print("<input style='width:50px' type='text' name='Txt_especificacion_min' id='Txt_especificacion_min' placeholder='Desv -' title='Desviación especificación'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_especificacion_min');val1.add(Validate.Presence);val1.add(Validate.Decimal);</script>");
                        out.print("</div>");
                        //FIN VALIDADOR DE CAMPOS
                        out.print("<input type='hidden' name='Id_tipo_equipo' id='Id_tipo_equipo' value='" + id_tipo_equipo + "' />");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content' style='width:870px'>");
                    lst_tipo_equipo = jpacteq.Traer_tipo_equipo(id_tipo_equipo);
                    Object[] obj_tipo_equipo = (Object[]) lst_tipo_equipo.get(0);
                    out.print("<h3><form action='Complemento?opc=1&fto=' method='post' name='FormVolver' id='FormVolver'>"
                            + "<a href='JAVASCRIPT:FormVolver.submit()'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='edit' title='Volver a tipos de equipo' /></a>Parámetros <b>" + obj_tipo_equipo[1] + "</b> frecuencia MTTO <b>" + obj_tipo_equipo[2] + "</b>"
                            + "<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar parámetro' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan='5'>Parámetro</th>");
                    out.print("<th>Instrumento</th>");
                    out.print("<th>Unidad de medida</th>");
                    out.print("<th>Especificación</th>");
                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                        out.print("<th colspan='2'>Estado</th>");
                    }
                    out.print("</tr>");
                    lst_parametros = jpacprm.Traer_parametros_tipo_equipo(id_tipo_equipo);
                    if (lst_parametros == null) {
                        out.print("<tr><td colspan='11'>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de parametros asignados al tipo de equipo</b>");
                        out.print("</center>");
                        out.print("</td></tr>");
                    } else {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (Integer.parseInt(obj_parametros[14].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center' style='width:50px'><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='5'>" + obj_parametros[1] + "</td>");
                                out.print("<td>" + obj_parametros[5] + "</td>");
                                out.print("<td>" + obj_parametros[7] + " / " + obj_parametros[8] + "</td>");
                                if (obj_parametros[12].toString().equals("Numero")) {
                                    if ((Double) obj_parametros[9] == 0) {
                                        out.print("<td align='center'><b>MIN (</b>" + (Double) obj_parametros[11] + "<b>)-(</b><b class='negro'> O.T </b><b>)-(</b>" + (Double) obj_parametros[10] + "<b>) MAX</b></td>");
                                    } else {
                                        out.print("<td align='center'><b>MIN (</b>" + ((Double) obj_parametros[9] - (Double) obj_parametros[11]) + "<b>)-(</b><b class='negro'>" + obj_parametros[9] + "</b><b>)-(</b>" + ((Double) obj_parametros[9] + (Double) obj_parametros[10]) + "<b>) MAX</b></td>");
                                    }
                                } else if (obj_parametros[12].toString().equals("Estado")) {
                                    out.print("<td align='center'><b class='negro'> Cumple </b></td>");
                                } else if (obj_parametros[12].toString().equals("Caracter")) {
                                    out.print("<td align='center'><b class='negro'> Campo </b></td>");
                                }
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='DesactivarParametro(" + obj_parametros[0] + "," + id_tipo_equipo + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar parametro' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center' style='width:50px'>" + (i + 1) + "</td>");
                                out.print("<td colspan='5'>" + obj_parametros[1] + "</td>");
                                out.print("<td>" + obj_parametros[5] + "</td>");
                                out.print("<td>" + obj_parametros[7] + " / " + obj_parametros[8] + "</td>");
                                if (obj_parametros[12].toString().equals("Numero")) {
                                    if ((Double) obj_parametros[9] == 0) {
                                        out.print("<td align='center'><b>MIN (</b>" + (Double) obj_parametros[11] + "<b>)-(</b><b class='negro'> O.T </b><b>)-(</b>" + (Double) obj_parametros[10] + "<b>) MAX</b></td>");
                                    } else {
                                        out.print("<td align='center'><b>MIN (</b>" + ((Double) obj_parametros[9] - (Double) obj_parametros[11]) + "<b>)-(</b><b class='negro'>" + obj_parametros[9] + "</b><b>)-(</b>" + ((Double) obj_parametros[9] + (Double) obj_parametros[10]) + "<b>) MAX</b></td>");
                                    }
                                } else if (obj_parametros[12].toString().equals("Estado")) {
                                    out.print("<td align='center'>Cumple</td>");
                                } else if (obj_parametros[12].toString().equals("Caracter")) {
                                    out.print("<td align='center'>Campo</td>");
                                }
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='ActivarParametro(" + obj_parametros[0] + "," + id_tipo_equipo + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar parametro' /></a></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="UNIDADES DE MEDIDA">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Unidades_medida")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Unidad Medidad</h3>");
                    if (rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=11' method='post'>");
                        out.print("<b>Unidad de medida :</b>");
                        out.print("<input type='text' name='Txt_unidad_medida' id='Txt_unidad_medida' placeholder='Unidad de medida' title='Unidad de medida' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad_medida');val1.add(Validate.Presence);</script>");
                        out.print("<b>Sigla:</b>");
                        out.print("<input type='text' name='Txt_sigla' id='Txt_sigla' placeholder='Sigla(s)' title='Sigla(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_sigla');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    out.print("<h3>Unidades de medida"
                            + "<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar unidad de medida' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan='6'>Unidad de medida</th>");
                    out.print("<th colspan='2'>Siglas</th>");
                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                        out.print("<th colspan='2'>Estado</th>");
                    }
                    out.print("</tr>");
                    lst_unidades_medida = jpacumd.Unidad_medida();
                    if (lst_unidades_medida == null) {
                        out.print("<tr><td colspan='11'>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de actividades asignadas al tipo de equipo</b>");
                        out.print("</center>");
                        out.print("</td></tr>");
                    } else {
                        for (int i = 0; i < lst_unidades_medida.size(); i++) {
                            Object[] obj_unidades_medida = (Object[]) lst_unidades_medida.get(i);
                            if (Integer.parseInt(obj_unidades_medida[3].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center' style='width:50px'><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='6'>" + obj_unidades_medida[1] + "</td>");
                                out.print("<td colspan='2'>" + obj_unidades_medida[2] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='DesactivarUnidad(" + obj_unidades_medida[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar actividad' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center' style='width:50px'><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='6'>" + obj_unidades_medida[1] + "</td>");
                                out.print("<td colspan='2'>" + obj_unidades_medida[2] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='ActivarUnidad(" + obj_unidades_medida[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar parametro' /></a></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INSTRUMENTOS">
                else if (pageContext.getRequest().getAttribute("Complemento").toString().equals("Instrumentos")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Instrumento</h3>");
                    if (rol.equals("Consulta")) {
                        out.print("<center>");
                        out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='Sin permisos' /><br />");
                        out.print("<b>Sin permisos de registro</b>");
                        out.print("</center>");
                    } else {
                        out.print("<form action='Complemento?opc=13' method='post'>");
                        out.print("<b>Instrumento :</b>");
                        out.print("<input type='text' name='Txt_instrumento' id='Txt_instrumento' placeholder='Instrumento' title='Instrumento' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_instrumento');val1.add(Validate.Presence);</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                    }
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                    out.print("<div id='content'>");
                    out.print("<h3>Instrumentos"
                            + "<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar instrumento' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>#</th>");
                    out.print("<th colspan='8'>Instrumento</th>");
                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                        out.print("<th colspan='2'>Estado</th>");
                    }
                    out.print("</tr>");
                    lst_instrumentos = jpacitm.Instrumentos();
                    if (lst_instrumentos == null) {
                        out.print("<tr><td colspan='11'>");
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de actividades asignadas al tipo de equipo</b>");
                        out.print("</center>");
                        out.print("</td></tr>");
                    } else {
                        for (int i = 0; i < lst_instrumentos.size(); i++) {
                            Object[] obj_instrumentos = (Object[]) lst_instrumentos.get(i);
                            if (Integer.parseInt(obj_instrumentos[2].toString()) == 1) {
                                out.print("<tr>");
                                out.print("<td align='center' style='width:50px'><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='8'>" + obj_instrumentos[1] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='DesactivarInstrumento(" + obj_instrumentos[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar actividad' /></a></td>");
                                }
                                out.print("</tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center' style='width:50px'><b>" + (i + 1) + "</b></td>");
                                out.print("<td colspan='8'>" + obj_instrumentos[1] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<td colspan='2' align='center'><a href='#'  onclick='ActivarInstrumento(" + obj_instrumentos[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar parametro' /></a></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_complemento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
