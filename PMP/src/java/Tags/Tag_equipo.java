package Tags;

import Controladores.EquipoJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.UsuarioJpaController;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_equipo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
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
            String fecha_dia = anio + "-" + mes + "-" + dia;
            //FIN PERMISOS
            EquipoJpaController jpaceqp = new EquipoJpaController();
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            //VARIABLE GLOBALES
            String filtro = "";
            List lst_equipos = null;
            List lst_equipo = null;
            List lst_tipos_equipo = null;
            List lst_usuarios = null;
            List lst_orden = null;
            int id_equipo = 0;
            int estado = 0;
            int id_equipo_mod = 0;
            int id_orden = 0;
            int programar_ot = 0;
            int filtro_vacio = 0;
            int temp = 0;
            try {
                temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
            } catch (Exception e) {
                temp = 0;
            }
            if (pageContext.getRequest().getAttribute("Equipos") != null) {
                //<editor-fold defaultstate="collapsed" desc="PROGRAMA DE MTTO PREVENTIVO">
                if (pageContext.getRequest().getAttribute("Equipos").toString().equals("Equipos_PMP")) {
                    out.print("<div id='content_sin'>");
                    id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo").toString());
                    id_equipo_mod = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo_mod").toString());
                    programar_ot = Integer.parseInt(pageContext.getRequest().getAttribute("Programar").toString());
                    estado = Integer.parseInt(pageContext.getRequest().getAttribute("Estado").toString());
                    filtro = pageContext.getRequest().getAttribute("Filtro").toString();
                    out.print("<div class=''>");
                    out.print("<div class='' style='display: flex; align-items: center;justify-content: space-between;'>");
                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                        out.print("<h3><img onclick='Form_registro_equipo()' src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Registro de equipos' />Equipos | <b id='Convenciones'>Convenciones</b></h3>");
                    } else {
                        out.print("<h3>Equipos  | <b id='Convenciones'>Convenciones</b></h3>");
                    }
                    out.print("<div class=''>");
//                    out.print("<span><input type='radio' name='Consult' id='' onclick=''> Equipos</span>");
//                    out.print("<span><input type='radio' name='Consult' id='' onclick=''> Carros</span>");
//                    out.print("<a href='Equipo?opc=1&amp;ieq=0&amp;ot=0&amp;temp=0&amp;fto='>Equipos</a>");
//                    out.print("<a href='Equipo?opc=1&amp;ieq=0&amp;ot=0&amp;temp=1&amp;fto='>Carros</a>");
                    out.print("<button class='" + ((temp == 0) ? "btn_filters_select" : "btn_filters") + "' onclick=\"window.location='Equipo?opc=1&amp;ieq=0&amp;ot=0&amp;temp=0&amp;fto='\" style='margin-right: 6px;'>Equipos</button>");
                    out.print("<button class='" + ((temp == 1) ? "btn_filters_select" : "btn_filters") + "' onclick=\"window.location='Equipo?opc=1&amp;ieq=0&amp;ot=0&amp;temp=1&amp;fto='\">Carros</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='float:right' ><a href='Equipo?opc=1&amp&ieq=0&amp&ot=0&amp&fto=&estado=1'><b style='color:green;'>Activo</b></a> | <a href='Equipo?opc=1&amp&ieq=0&amp&ot=0&amp&fto=&estado=0'><b style='color:red;'>Inactivo</b></a></div></br></br>");
                    out.print("</div>");

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
                    out.print("<td align='center'><div class='semicirculo_verde'></div></td>");
                    out.print("<td>Equipos que presentan mantenimiento preventivo vigente a la frecuencia.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='semicirculo_naranja'></div></td>");
                    out.print("<td>Equipos que estan en la frecuencia de alerta de tolerancia minima y maxima para el mantenimiento preventivo.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='semicirculo_rojo'></div></div></td>");
                    out.print("<td>Equipos que no tienen PMP y sobrepasan las frecuencias de alerta.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='semicirculo_gris'></div></div></td>");
                    out.print("<td>Equipos Inactivos.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='semicirculo_azul'></div></div></td>");
                    out.print("<td>Equipos que no aplican Programa de mantenimiento preventivo.</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR EQUIPO">
                    if (id_equipo_mod == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                        out.print("<div class='sweet-local' id='Form_registro_equipo' style='opacity: 1.03; display: none;'>");
                        out.print("<fieldset class='popup_local' style='width:65%;height:400px;position;top: 5%;left:10%;'>");
                        out.print("<div align='right' style='margin-right:10px;margin-top:10px;'><img onclick='Form_registro_equipo_cerrar()' src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar Modificación' /></div>");
                        out.print("<dir><h3>Registrar Equipo</h3>");
                        out.print("<form action='Equipo?opc=3' method='post'>");
                        out.print("<div style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Equipo :</b>");
                        out.print("<input type='text' name='Txt_equipo' id='Txt_equipo' placeholder='Nombre Equipo' title='Nombre Equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_equipo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Marca :</b>");
                        out.print("<input type='text' name='Txt_marca' id='Txt_marca' placeholder='Marca' title='Marca' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_marca');val1.add(Validate.Presence);</script>");
                        out.print("<b>Modelo :</b>");
                        out.print("<input type='text' name='Txt_modelo' id='Txt_modelo' placeholder='Modelo' title='Modelo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_modelo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Serie :</b>");
                        out.print("<input type='text' name='Txt_serie' id='Txt_serie' placeholder='Serie' title='Serie' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_serie');val1.add(Validate.Presence);;</script>");
                        out.print("</div>");
                        out.print("<div style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Tipo :</b>");
                        out.print("<input type='text' name='Txt_tipo' id='Txt_tipo' placeholder='Descricpcion' title='Descricpcion' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo');val1.add(Validate.Presence);</script>");

                        out.print("<b>Tipo de equipo :</b>");
                        out.print("<select name='Cbx_tipo_equipo' id='Cbx_tipo_equipo' title='Tipo de equipo'>");
                        out.print("<option value='0' >Seleccionar tipo de equipo</option>");
                        lst_tipos_equipo = jpacteq.Tipos_equipo();
                        for (int i = 0; i < lst_tipos_equipo.size(); i++) {
                            Object[] obj_tipos_equipo = (Object[]) lst_tipos_equipo.get(i);
                            if ((Integer) obj_tipos_equipo[3] == 1) {
                                out.print("<option value='" + obj_tipos_equipo[0] + "' >" + obj_tipos_equipo[1] + " / " + obj_tipos_equipo[2] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_equipo');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                        out.print("<b>Año :</b>");
                        out.print("<input type='text' name='Txt_anio' id='Txt_anio' placeholder='Año' title='Año' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_anio');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Ubicación :</b>");
                        out.print("<input type='text' name='Txt_ubicacion' id='Txt_ubicacion' placeholder='Ubicación' title='Ubicación' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);</script>");
                        
                         out.print("<b>Descripcion :</b><br >");
                        out.print("<input type='text' name='Txt_descricpcion' id='Txt_descricpcion' placeholder='Descricpcion' title='Descricpcion' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descricpcion');val1.add(Validate.Presence);</script>");
                        
                        out.print("</div>");
                        
                        
                        out.print("<div style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Voltaje de trabajo :</b>");
                        out.print("<input type='text' name='Txt_voltaje' id='Txt_voltaje' placeholder='Voltaje de trabajo' title='Voltaje de trabajo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_voltaje');val1.add(Validate.Presence);</script>");
                        out.print("<b>Capacidad de trabajo :</b>");
                        out.print("<input type='text' name='Txt_capacidad' id='Txt_capacidad' placeholder='Capacidad de trabajo' title='Capacidad de trabajo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_capacidad');val1.add(Validate.Presence);</script>");
                        out.print("<b>Tipo de horometro :</b><br />");
                        out.print("Analogo <input type='radio' name='Rdb_tipo_horometro' value='0' checked/> | ");
                        out.print("<input type='radio' name='Rdb_tipo_horometro' value='1' /> Digital<br /><br />");
                        out.print("<b>Aplica PMP :</b><br />");
                        out.print("SI <input type='radio' onclick=\"AplicaPMP('1')\" name='Rdb_pmp' value='1' checked/> | ");
                        out.print("<input type='radio' onclick=\"AplicaPMP('0')\" name='Rdb_pmp' value='0' /> NO<br /><br />");
                        
                        out.print("<br ><input type='submit' value='Registrar' />");
                        out.print("</div>");

                        out.print("<div id='Div_PMP' style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Horometro ult. OT :</b>");
                        out.print("<input type='text' name='Txt_horometro_pmp' value='0' id='Txt_horometro_pmp' placeholder='Horometro ult. OT' title='Horometro ult. OT' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_horometro_pmp');val1.add(Validate.Presence);</script>");
                        out.print("<b>Fecha horometro ult. OT :</b>");
                        out.print("<input type='text' name='Txt_fecha_pmp' value='" + fecha_dia + "' id='datepicker' placeholder='Fecha horometro ult. OT' title='Fecha horometro ult. OT' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<b>Horometro actual :</b>");
                        out.print("<input type='text' name='Txt_horometro_actual' value='0' id='Txt_horometro_actual' placeholder='Horometro actual' title='Horometro actual' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_horometro_actual');val1.add(Validate.Presence);</script>");
                        out.print("<b>Fecha horometro actual :</b>");
                        out.print("<input type='text' name='Txt_fecha_actual' value='" + fecha_dia + "' id='datepicker2' placeholder='Fecha horometro actual' title='Fecha horometro actual' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker2');val1.add(Validate.Presence);</script>");
                        out.print("</div>");


                        

                        out.print("</form><br /><br />");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_equipo = jpaceqp.Traer_equipo(id_equipo_mod);
                        Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                        out.print("<div class='sweet-local' id='Form_modificar_equipo' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' style='width:50%;height:395px;position;top: 5%;left:20%;'>");
                        out.print("<div align='right' style='margin-right:10px;margin-top:10px;'><a href='Equipo?opc=1&ieq=0&ot=0&fto=" + obj_equipo[1] + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar Modificación' /></a></div>");
                        out.print("<dir><h3>Modificar Equipo</h3>");
                       out.print("<form action='Equipo?opc=5' method='post'>");
                        out.print("<div style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Equipo :</b>");
                        out.print("<input type='text' value='" + obj_equipo[1] + "' name='Txt_equipo' id='Txt_equipo' placeholder='Nombre Equipo' title='Nombre Equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_equipo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Marca :</b>");
                        out.print("<input type='text' value='" + obj_equipo[2] + "' name='Txt_marca' id='Txt_marca' placeholder='Marca' title='Marca' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_marca');val1.add(Validate.Presence);</script>");
                        out.print("<b>Modelo :</b>");
                        out.print("<input type='text' value='" + obj_equipo[3] + "' name='Txt_modelo' id='Txt_modelo' placeholder='Modelo' title='Modelo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_modelo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Serie :</b>");
                        out.print("<input type='text' value='" + obj_equipo[4] + "' name='Txt_serie' id='Txt_serie' placeholder='Serie' title='Serie' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_serie');val1.add(Validate.Presence);;</script>");
                        out.print("</div>");
                        out.print("<div style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Descricpcion :</b>");
                        out.print("<input type='text' value='" + obj_equipo[5] + "' name='Txt_descricpcion' id='Txt_descricpcion' placeholder='Descricpcion' title='Descricpcion' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descricpcion');val1.add(Validate.Presence);</script>");
                        out.print("<b>Tipo de equipo :</b>");
                        out.print("<select name='Cbx_tipo_equipo' id='Cbx_tipo_equipo' title='Tipo de equipo'>");
                        out.print("<option value='0' >Seleccionar tipo de equipo</option>");
                        lst_tipos_equipo = jpacteq.Tipos_equipo();
                        for (int i = 0; i < lst_tipos_equipo.size(); i++) {
                            Object[] obj_tipos_equipo = (Object[]) lst_tipos_equipo.get(i);
                            if ((Integer) obj_tipos_equipo[3] == 1) {
                                if (obj_equipo[6] == obj_tipos_equipo[0]) {
                                    out.print("<option value='" + obj_tipos_equipo[0] + "' selected >" + obj_tipos_equipo[1] + " / " + obj_tipos_equipo[2] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_tipos_equipo[0] + "' >" + obj_tipos_equipo[1] + " / " + obj_tipos_equipo[2] + "</option>");
                                }
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_equipo');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                        out.print("<b>Año :</b>");
                        out.print("<input type='text' value='" + obj_equipo[8] + "' name='Txt_anio' id='Txt_anio' placeholder='Año' title='Año' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_anio');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Ubicación :</b>");
                        out.print("<input type='text' value='" + obj_equipo[9] + "' name='Txt_ubicacion' id='Txt_ubicacion' placeholder='Ubicación' title='Ubicación' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ubicacion');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<div style='width:200px;margin-right:10px;float:left'>");
                        out.print("<b>Voltaje de trabajo :</b>");
                        out.print("<input type='text' value='" + obj_equipo[10] + "' name='Txt_voltaje' id='Txt_voltaje' placeholder='Voltaje de trabajo' title='Voltaje de trabajo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_voltaje');val1.add(Validate.Presence);</script>");
                        out.print("<b>Capacidad de trabajo :</b>");
                        out.print("<input type='text' value='" + obj_equipo[11] + "' name='Txt_capacidad' id='Txt_capacidad' placeholder='Capacidad de trabajo' title='Capacidad de trabajo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_capacidad');val1.add(Validate.Presence);</script>");
                        out.print("<b>Tipo de horometro :</b><br />");
                        out.print("Analogo <input type='radio' name='Rdb_tipo_horometro' value='0' " + ((Integer.parseInt(obj_equipo[29].toString()) == 0) ? "checked" : "") + "/> | ");
                        out.print("<input type='radio' name='Rdb_tipo_horometro' value='1' " + ((Integer.parseInt(obj_equipo[29].toString()) == 0) ? "" : "checked") + " /> Digital<br /><br />");
                        out.print("<br /><input type='submit' value='Modificar' /><br /><br />");
                        out.print("</div>");
                        out.print("<input type='hidden' name='ieq' value='" + id_equipo_mod + "' />");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //</editor-fold>
                    if (estado == 0) {
                        //<editor-fold defaultstate="collapsed" desc="CONSULTA EQUIPOS INACTIVOS">
                        if (filtro == null ? "" == null : filtro.equals("")) {
                            lst_equipos = jpaceqp.Equipos_inactivos();
                        } else {
                            lst_equipos = jpaceqp.Filtrar_equipos_inactivo(filtro);
                            if (lst_equipos == null) {
                                lst_equipos = jpaceqp.Equipos_inactivos();
                                filtro_vacio++;
                            }
                        }
                        if (lst_equipos == null) {
                            out.print("<center>");
                            out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                            out.print("<b>No hay datos de equipos registrados</b>");
                            out.print("</center>");
                        } else {
                            if (filtro == null ? "" == null : filtro.equals("")) {
                                out.print("<div align='right' style='margin:0px;width:200px;float:right'><form action='Equipo?opc=1&ieq=0&ot=0&estado=0' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                            } else if (filtro_vacio > 0) {
                                out.print("<div align='right' style='margin:0px;width:200px;float:right'><form action='Equipo?opc=1&ieq=0&ot=0&estado=0' method='post'><b class='rojo'>El valor filtrado no obtubo resultados  </b><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                            } else {
                                out.print("<div align='right' style='margin:0px;width:200px;float:right'><form action='Equipo?opc=1&ieq=0&ot=0&estado=0' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                            }
                            out.print("<div id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados' style='width:100%;'>");
                            out.print("<tr>");
                            out.print("<th>#</th>");
                            out.print("<th colspan='4'>EQUIPO</th>");
                            out.print("<th colspan='2'>JUSTIFICACIÓN</th>");
                            out.print("<th>FECHA INACTIVDAD</th>");
                            if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico") || rol.equals("Asistente_MTI"))) {
                                out.print("<th>ESTADO</th>");
                            }
                            out.print("</tr>");
                            for (int i = 0; i < lst_equipos.size(); i++) {
                                Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                                out.print("<tr class='rojo'>");
                                out.print("<td align='center'><div class='semicirculo_gris'></div></td>");
                                out.print("<td colspan='2'><b>Nombre :</b>" + obj_equipos[1] + "</a>");
                                out.print("<br /><b>Ubicación :</b>" + obj_equipos[9] + "");
                                out.print("<br /><b>Tipo :</b>" + obj_equipos[7] + "</td>");
                                out.print("<td colspan='2'><b>Marca:</b>" + obj_equipos[2] + "</a>");
                                out.print("<br /><b>Modelo :</b>" + obj_equipos[3] + "");
                                out.print("<br /><b>Serie :</b>" + obj_equipos[4] + "");
                                out.print("<br /><b>Descripción :</b>" + obj_equipos[5] + "</td>");
                                out.print("<td colspan='2' align='center'>" + obj_equipos[18] + "</td>");
                                out.print("<td align='center'>" + obj_equipos[19] + "</td>");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico") || rol.equals("Asistente_MTI"))) {
                                    out.print("<td align='center'><a href='#' onclick='ActivarEquipo(" + obj_equipos[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar Linea' /></a></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados', 17);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="CONSULTA EQUIPOS">
                        if (temp == 1) {
                            lst_equipos = jpaceqp.ConsultarEtiposTipo();
                        } else {
                            if (filtro == null ? "" == null : filtro.equals("")) {
                                lst_equipos = jpaceqp.Equipos();
                            } else {
                                lst_equipos = jpaceqp.Filtrar_equipos(filtro);
                                if (lst_equipos == null) {
                                    lst_equipos = jpaceqp.Equipos();
                                    filtro_vacio++;
                                }
                            }

                        }
                        if (lst_equipos == null) {
                            out.print("<center>");
                            out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                            out.print("<b>No hay datos de equipos registrados</b>");
                            out.print("</center>");
                        } else {
                            if (filtro == null ? "" == null : filtro.equals("")) {
                                out.print("<div align='right' style='margin:0px;width:200px;float:right'><form action='Equipo?opc=1&ieq=0&ot=0' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                            } else if (filtro_vacio > 0) {
                                out.print("<div align='right' style='margin:0px;width:200px;float:right'><form action='Equipo?opc=1&ieq=0&ot=0' method='post'><b class='rojo'>El valor filtrado no obtubo resultados  </b><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                            } else {
                                out.print("<div align='right' style='margin:0px;width:200px;float:right'><form action='Equipo?opc=1&ieq=0&ot=0' method='post'><input type='text' name='fto' id='fto' placeholder='Buscar' value='" + filtro + "' onkeyup='javascript:this.value=this.value.toUpperCase();'/></form></div>");
                            }
                            out.print("<div id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados' style='width:100%;'>");
                            out.print("<tr>");
                            out.print("<th>#</th>");
                            out.print("<th colspan='3'>EQUIPO</th>");
                            out.print("<th colspan='2'>HOROMETROS</th>");
                            out.print("<th colspan='" + ((temp == 1) ? "2" : "3") + "'>PMP</th>");
                            if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                out.print("<th>ESTADO</th>");
                                out.print("<th>MODIFICAR</th>");
                            }
                            out.print("</tr>");
                            for (int i = 0; i < lst_equipos.size(); i++) {
                                Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                                if (Integer.parseInt(obj_equipos[14].toString()) == 1) {
                                    out.print("<tr>");
                                    if (Integer.parseInt(obj_equipos[32].toString()) == 0) {
                                        out.print("<td align='center'><div class='semicirculo_azul'></div></td>");
                                    } else if (Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[21].toString())) {
                                        out.print("<td align='center'><div class='semicirculo_verde'></div></td>");
                                    } else if (Double.parseDouble(obj_equipos[22].toString()) > Double.parseDouble(obj_equipos[21].toString()) && Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[19].toString())) {
                                        out.print("<td align='center'><div class='semicirculo_naranja'></div></td>");
                                    } else {
                                        out.print("<td align='center'><div class='semicirculo_rojo'></div></td>");
                                    }
                                    out.print("<td colspan='3'><b>Nombre :</b><a href='Equipo?opc=1&ieq=" + obj_equipos[0] + "&ot=0&fto=" + obj_equipos[1] + "'>" + obj_equipos[1] + "</a>");
                                    out.print("<br /><b>Ubicación :</b>" + obj_equipos[9] + "");
                                    out.print("<br /><b>Tipo :</b>" + obj_equipos[7] + "</td>");
                                    if (Integer.parseInt(obj_equipos[32].toString()) == 0) {
                                        out.print("<td colspan='5' align='center'><b class='naranja'>No aplica programa de mantenimiento preventivo.</b></td>");
                                    } else {
                                        out.print("<td colspan='2' align='center'>");
                                        out.print("<b>Ultimo :</b>" + obj_equipos[12] + " / " + obj_equipos[23] + "");
                                        out.print("<br />");
                                        if (temp != 1) {
                                            out.print("<b>Actual :</b>" + obj_equipos[13] + " / " + obj_equipos[24] + "");
                                        }
                                        out.print("</td>");
                                        if (rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico")) {
                                            out.print("<td align='center'><a href='Orden_trabajo?opc=1&ieq=" + obj_equipos[0] + "&ot=0&fto='><img src='Interfaz/Contenido/Iconos/History.png' alt='edit' title='Historial OT' /></a></td>");
                                            out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Calendar.png' alt='edit' title='Sin permisos de programación' /></a></td>");
                                        } else {
                                            out.print("<td align='center'><a href='Orden_trabajo?opc=1&ieq=" + obj_equipos[0] + "&ot=0&fto='><img src='Interfaz/Contenido/Iconos/History.png' alt='edit' title='Historial OT' /></a></td>");
                                            out.print("<td align='center'><a href='Equipo?opc=1&ieq=0&ot=" + obj_equipos[0] + "&fto=" + obj_equipos[1] + "'><img src='Interfaz/Contenido/Iconos/Calendar.png'  alt='edit' title='Programación' /></a></td>");
                                        }
                                        if (temp != 1) {
                                            if (Integer.parseInt(obj_equipos[29].toString()) == 0) {
                                                int horometro_actual = Integer.parseInt(obj_equipos[18].toString());
                                                int calcula = 0;
                                                if (horometro_actual > 100000) {
                                                    calcula = (horometro_actual / 100000) * 100000;
                                                }
                                                out.print("<td align='center'><b>Proximo : </b>" + ((Integer.parseInt(obj_equipos[18].toString()) >= 100000) ? "" + (Integer.parseInt(obj_equipos[18].toString()) - calcula) : "" + obj_equipos[18]) + "<br />" + obj_equipos[25] + "</td>");
                                            } else {
                                                out.print("<td align='center'><b>Proximo : </b>" + obj_equipos[18].toString() + "<br />" + obj_equipos[25] + "</td>");
                                            }
                                        }
                                    }
                                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                        out.print("<td align='center'><a href='#' onclick='DesactivarEquipo(" + obj_equipos[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' alt='edit' title='Desactivar Equipo' /></a></td>");
                                        out.print("<td align='center'><a href='Equipo?opc=1&ieq=0&ot=0&ieqm=" + obj_equipos[0] + "&fto=" + obj_equipos[1] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='edit' title='Modificar Equipo' /></a></td>");
                                    }
                                    out.print("</tr>");
                                } else {
                                    out.print("<tr class='rojo'>");
                                    if (Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[21].toString())) {
                                        out.print("<td align='center'><div class='semicirculo_gris'></div></td>");
                                    } else if (Double.parseDouble(obj_equipos[22].toString()) > Double.parseDouble(obj_equipos[21].toString()) && Double.parseDouble(obj_equipos[22].toString()) <= Double.parseDouble(obj_equipos[19].toString())) {
                                        out.print("<td align='center'><div class='semicirculo_gris'></div></td>");
                                    } else {
                                        out.print("<td align='center'><div class='semicirculo_gris'></div></td>");
                                    }
                                    out.print("<td colspan='3'><b>Nombre :</b><a href='Equipo?opc=1&ieq=" + obj_equipos[0] + "&ot=0&fto=" + obj_equipos[1] + "'>" + obj_equipos[1] + "</a>");
                                    out.print("<br /><b>Ubicación :</b>" + obj_equipos[9] + "");
                                    out.print("<br /><b>Tipo :</b>" + obj_equipos[7] + "</td>");
                                    if (Integer.parseInt(obj_equipos[32].toString()) == 0) {
                                        out.print("<td colspan='5' align='center'><b class='naranja'>No aplica programa de mantenimiento preventivo.</b></td>");
                                    } else {
                                        out.print("<td colspan='2' align='center'><b>Ultimo :</b>" + obj_equipos[12] + " / " + obj_equipos[23] + "");
                                        out.print("<br /><b>Actual :</b>" + obj_equipos[13] + " / " + obj_equipos[24] + "</td>");
                                        if (rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico")) {
                                            out.print("<td align='center'><a href='Orden_trabajo?opc=1&ieq=" + obj_equipos[0] + "&ot=0&fto='><img src='Interfaz/Contenido/Iconos/History.png' alt='edit' title='Historial OT' /></a></td>");
                                            out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Calendar.png' alt='edit' title='Sin permisos de programación' /></a></td>");
                                        } else {
                                            out.print("<td align='center'><a href='Orden_trabajo?opc=1&ieq=" + obj_equipos[0] + "&ot=0&fto='><img src='Interfaz/Contenido/Iconos/History.png' alt='edit' title='Historial OT' /></a></td>");
                                            out.print("<td align='center'><a href='Equipo?opc=1&ieq=0&ot=" + obj_equipos[0] + "&fto=" + obj_equipos[1] + "'><img src='Interfaz/Contenido/Iconos/Calendar.png'  alt='edit' title='Programación' /></a></td>");
                                        }
                                        out.print("<td align='center'><b>Proximo : </b>N/A</td>");
                                    }
                                    if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                        out.print("<td align='center'><a href='#' onclick='ActivarEquipo(" + obj_equipos[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Activar Linea' /></a></td>");
                                        out.print("<td align='center'><a href='#'><img src='Interfaz/Contenido/Iconos/Warning.png' alt='edit' title='Modificar Equipo' /></a></td>");
                                        //out.print("<td align='center'><a href='Equipo?opc=1&ieq=0&ot=0&ieqm=" + obj_equipos[0] + "&fto='><img src='Interfaz/Contenido/Iconos/Edit.png' alt='edit' title='Modificar Equipo' /></a></td>");
                                    }
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados', 17);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DETALLE EQUIPO">
                            if (id_equipo != 0) {
                                lst_equipo = jpaceqp.Traer_equipo(id_equipo);
                                Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                                out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                out.print("<fieldset class='popup_local' style='width:280px;visibility: visible;position: absolute;top: 200px;left: 35%;'>");
                                out.print("<div style='float:right'><a href='Equipo?opc=1&ieq=0&ot=0&fto=" + obj_equipo[1] + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar' /></a></div>");
                                out.print("<form align='left' action='Equipo?opc=4&ieq=" + obj_equipo[0] + "' method='post'>");
                                out.print("<h3>" + obj_equipo[1] + "</h3>");
                                out.print("<b>Marca : </b>" + obj_equipo[2] + "<br />");
                                out.print("<b>Modelo : </b>" + obj_equipo[3] + "<br />");
                                out.print("<b>Serie : </b>" + obj_equipo[4] + "<br />");
                                out.print("<b>Descricpción : </b>" + obj_equipo[5] + "<br />");
                                out.print("<b>Tipo de equipo : </b>" + obj_equipo[7] + "<br />");
                                out.print("<b>Año : </b>" + obj_equipo[8] + "<br />");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<b>Ubicación : </b><br /><input type='text' name='Txt_ubicacion' id='Txt_ubicacion' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_equipo[9] + "' /><br />");
                                } else {
                                    out.print("<b>Ubicación :</b>" + obj_equipo[9] + "<br />");
                                }
                                out.print("<b>Voltaje : </b>" + obj_equipo[10] + "<br />");
                                out.print("<b>Capacidad : </b>" + obj_equipo[11] + "<br />");
                                if (!(rol.equals("Consulta") || rol.equals("Tecnico_Encargado") || rol.equals("Tecnico"))) {
                                    out.print("<b>Horometro Ult. OT : </b><br /><input type='text' name='Txt_horometro_pmp' id='Txt_horometro_pmp' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_equipo[12] + "' /><br />");
                                    out.print("<b>Fecha Ult. OT : </b><br /><input type='text' name='Txt_fecha_pmp_act' id='datepicker3' onkeyup='javascript:this.value=this.value.toUpperCase();' value='" + obj_equipo[23] + "' /><br />");
                                } else {
                                    out.print("<b>Horometro Ult. OT :</b>" + obj_equipo[12] + "<br />");
                                    out.print("<b>Fecha Ult. OT :</b>" + obj_equipo[23] + "<br /><br />");
                                }
                                out.print("<b>Horometro actual : </b>" + obj_equipo[13] + "<br /><br />");
                                out.print("<input type='submit' value='Registrar' />");
                                out.print("</form>");
                                out.print("</fieldset>");
                                out.print("</div>");
                            }
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="PROGRAMAR ORDEN">
                            if (programar_ot != 0) {
                                lst_equipo = jpaceqp.Traer_equipo(programar_ot);
                                Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                                int prox_ot = 0;
                                if (obj_equipo[28] == null) {
                                    prox_ot = 0;
                                } else {
                                    prox_ot = (Integer) obj_equipo[28];
                                }
                                out.print("<div class='sweet-overlay' style='opacity: 1.03; display: block;'>");
                                out.print("<fieldset class='popup_local' style='width:280px;visibility: visible;position: fixed;top: 190px;left: 35%;'>");
                                out.print("<div style='float:right'><a href='Equipo?opc=1&ieq=0&ot=0&fto=" + obj_equipo[1] + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' title='Cancelar' /></a></div>");
                                out.print("<form align='left' action='Orden_trabajo?opc=4' method='post' name='Form_prog_" + obj_equipo[0] + "' id='Form_prog_" + obj_equipo[0] + "'>");
                                out.print("<h3>O.T " + (prox_ot + 1) + "</h3>");
                                //out.print("<b>Cambiar numero O.T : </b>" + obj_equipo[25] + "<br /><br />");
                                out.print("<b>Equipo : </b><b class='negro'>" + obj_equipo[1] + "</b><br />");
                                if (Integer.parseInt(obj_equipo[29].toString()) == 0) {
                                    out.print("<b>Horometro PMP : </b><b class='negro'>" + ((Integer.parseInt(obj_equipo[18].toString()) >= 100000) ? "" + (Integer.parseInt(obj_equipo[18].toString()) - 100000) : "" + obj_equipo[18]) + "</b><br />");
                                    out.print("<input type='hidden' name='Txt_horometro' value='" + ((Integer.parseInt(obj_equipo[18].toString()) >= 100000) ? "" + (Integer.parseInt(obj_equipo[18].toString()) - 100000) : "" + obj_equipo[18]) + "' />");
                                } else {
                                    out.print("<b>Horometro PMP : </b><b class='negro'>" + obj_equipo[18] + "</b><br />");
                                    out.print("<input type='hidden' name='Txt_horometro' value='" + obj_equipo[18] + "' />");
                                }
                                out.print("<b>Horometro actual : </b>" + obj_equipo[13] + "<br />");
                                out.print("<b>Fecha estimada : </b>" + obj_equipo[25] + "<br /><br />");
                                out.print("<b>Quien Programa: </b>" + usuario + "<br /><br />");
                                out.print("<b>Tiempo estimado : </b><br />");
                                out.print("Cant. <input type='text' style='width:35px' name='Txt_tiempo_estimado' id='Txt_tiempo_estimado' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tiempo_estimado');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                                out.print(" Tiempo <select name='Cbx_tiempo_estimado' id='Cbx_tiempo_estimado' style='width:100px'><option value='0'>Tiempo</option><option value='Minuto(s)'>Minuto(s)</option><option value='Hora(s)'>Hora(s)</option><option value='Dia(s)'>Dia(s)</option></select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tiempo_estimado');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                                lst_usuarios = jpacusa.Usuarios();
                                out.print("<b>Quien Ejecuta : </b><br />");
                                out.print("<select style='width:230px' name='Cbx_tecnico_ejecutor' id='Cbx_tecnico_ejecutor' title='Tecnico ejecutor'>");
                                out.print("<option value='0' >Seleccionar ejecutor</option>");
                                for (int i = 0; i < lst_usuarios.size(); i++) {
                                    Object[] obj_tecnicos_ejecutores = (Object[]) lst_usuarios.get(i);
                                    if (obj_tecnicos_ejecutores[8].equals("Tecnico") || obj_tecnicos_ejecutores[8].equals("Tecnico_Encargado")) {
                                        out.print("<option value='" + obj_tecnicos_ejecutores[1] + "'>" + obj_tecnicos_ejecutores[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tecnico_ejecutor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br />");
                                out.print("<b>Quien Revisa : </b><br />");
                                out.print("<select style='width:230px' name='Cbx_tecnico_revisor' id='Cbx_tecnico_revisor' title='Tecnico revisor'>");
                                out.print("<option value='0' >Seleccionar revisor</option>");
                                for (int i = 0; i < lst_usuarios.size(); i++) {
                                    Object[] obj_tecnicos_revisores = (Object[]) lst_usuarios.get(i);
                                    if (obj_tecnicos_revisores[8].equals("Tecnico_Encargado")) {
                                        out.print("<option value='" + obj_tecnicos_revisores[1] + "'>" + obj_tecnicos_revisores[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tecnico_revisor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br /><br />");
                                out.print("<input type='hidden' name='Txt_numero_orden' value='" + (prox_ot + 1) + "' />");
                                out.print("<input type='hidden' name='Id_equipo' value='" + obj_equipo[0] + "' />");
                                out.print("<input type='submit' value='Registrar' />");
                                out.print("</form>");
                                out.print("</fieldset>");
                                out.print("</div>");
                            }
                        }
                        //</editor-fold>
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LISTADO MAESTRO DE EQUIPOS">
                else if (pageContext.getRequest().getAttribute("Equipos").toString().equals("Maestro_equipos")) {
                    out.print("<div id='content_sin'>");
                    lst_tipos_equipo = jpacteq.Tipos_equipo();
                    String filtro_masivo = "";
                    for (int i = 0; i < lst_tipos_equipo.size(); i++) {
                        Object[] obj_tipo_equipos = (Object[]) lst_tipos_equipo.get(i);
                        filtro_masivo = filtro_masivo + "Filtrar_tabla('TablaTE" + obj_tipo_equipos[0] + "');";
                    }
                    out.print("<br /><div style='float:left;'>"
                            + "<a onclick=\"tableToExcel('Excel', 'LISTADO_MAESTRO_EQUIPOS')\" ><img src=\"Interfaz/Contenido/Iconos/Excel.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Generar a EXCEL' /></a>  Exportar a Excel "
                            + "<a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 22px;height: 22px\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF <br />"
                            //                            + "<input name='key' type='text' id='key' placeholder='Buscar' onkeyup='buscar(this.value)' />"
                            + "</div>");
                    out.print("<div style='float:right'><input id='Txt_filtro' type='text' onkeyup=\"" + filtro_masivo + "\" placeholder='Buscar Equipo' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                    out.print("<br /><br /><div id='Imprimir'>");
                    out.print("<table id='Excel' style='width:100%'><tr><td align='center'><b>LISTADO MAESTRO DE EQUIPOS<br />MTTO INSUMOS<br /></b></td></tr><tr><td>");
                    for (int i = 0; i < lst_tipos_equipo.size(); i++) {
                        Object[] obj_tipo_equipos = (Object[]) lst_tipos_equipo.get(i);
                        lst_equipos = jpaceqp.Traer_equipo_id_tipo_sin_pmp(Integer.parseInt(obj_tipo_equipos[0].toString()));
                        out.print("<table class='table' style='width:100%' id='TablaTE" + obj_tipo_equipos[0] + "'>");
                        out.print("<tr>");
                        out.print("<th colspan='11'>" + obj_tipo_equipos[1] + " | Frecuencia " + obj_tipo_equipos[2] + "</th>");
                        out.print("</tr>");
                        if (lst_equipos != null) {
                            out.print("<tr>");
                            out.print("<td style='width:1%'><b>#</b></td>");
                            out.print("<td style='width:20%'><b>Nombre</b></td>");
                            out.print("<td style='width:15%'><b>Descripcion</b></td>");
                            out.print("<td style='width:15%'><b>Marca</b></td>");
                            out.print("<td style='width:15%' width='15%'><b>Modelo</b></td>");
                            out.print("<td style='width:10%'><b>Serie</b></td>");
//                            out.print("<td style='width:15%'><b>Tipo</b></td>");
                            out.print("<td style='width:5%'><b>Año</b></td>");
                            out.print("<td style='width:9%'><b>Ubicación</b></td>");
                            out.print("<td style='width:5%'><b>Voltaje</b></td>");
                            out.print("<td style='width:5%'><b>Capacidad</b></td>");
                            out.print("</tr>");
                            for (int j = 0; j < lst_equipos.size(); j++) {
                                Object[] obj_equipos = (Object[]) lst_equipos.get(j);
                                out.print("<tr>");
                                out.print("<td><b>" + (j + 1) + "</b></td>");
                                out.print("<td>" + obj_equipos[1] + "</td>");
                                out.print("<td>" + obj_equipos[5] + "</td>");
                                out.print("<td>" + obj_equipos[2] + "</td>");
                                out.print("<td>" + obj_equipos[3] + "</td>");
//                                out.print("<td>" + obj_equipos[5] + "</td>");
                                out.print("<td>" + obj_equipos[4] + "</td>");
                                out.print("<td>" + obj_equipos[8] + "</td>");
                                out.print("<td>" + obj_equipos[9] + "</td>");
                                out.print("<td>" + obj_equipos[10] + "</td>");
                                out.print("<td>" + obj_equipos[11] + "</td>");
                                out.print("</tr>");
                            }
                        } else {
                            out.print("<tr>");
                            out.print("<td colspan='10' align='center'><b class='naranja'>Este tipo no tiene equipos registrados</b></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                    }
                    out.print("<td></tr></table></div>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_equipo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
