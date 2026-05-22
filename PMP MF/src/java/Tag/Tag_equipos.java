package Tag;

import Controladores.EquipoJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.LineaJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.TipoMttoJpaController;
import Controladores.UsuarioJpaController;
import Controladores.VerificacionFtJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_equipos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            ///JPAC
            EquipoJpaController jpaceqp = new EquipoJpaController();
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            TipoMttoJpaController jpactmt = new TipoMttoJpaController();
            VerificacionFtJpaController jpacvft = new VerificacionFtJpaController();
            ///VARIABLES
            String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
            int id_equipo = 0, temp = 0;
            int id_ficha_tecnica = 0;
            int estado = 0;
            int tipo_ficha = 0;
            int id_nota = 0;
            int temp1 = 0;
            List lst_equipos = null;
            List lst_equipo = null;
            List lst_lineas = null;
            List lst_ficha = null;
            List lst_tipo_mtto = null;
            List lst_usuarios = null;
            List lst_numero_orden = null;
            List lst_verificacion_ft = null;
            List lst_tipo_equipos = null;
            List lst_notas = null;
            List lst_notas_id = null;
            lst_lineas = jpaclna.Lineas();
            lst_tipo_equipos = jpacteq.Tipo_equipos();

            try {
                temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
            } catch (Exception e) {
                temp = 0;
            }

            if (pageContext.getRequest().getAttribute("Equipos") != null) {
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                if (pageContext.getRequest().getAttribute("Equipos").toString().equals("Equipos_home")) {
                    //<editor-fold defaultstate="collapsed" desc="EQUIPO">
                    id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo").toString());
                    estado = Integer.parseInt(pageContext.getRequest().getAttribute("Estado").toString());
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        if (id_equipo == 0 && estado == 0) {
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Equipo</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse\" id=\"collapseCardExample\">");
                            out.print("<div class=\"card-body\" >");
                            out.print("<div style='width:40%;float:left'>");
                            out.print("<form action='Equipo?opc=2&Id_equipo=0' method='post' id='FormEquipRegister'>");
                            out.print("<b>Tipo de equipo: </b>");
                            out.print("<select class='form-control' name='Cbx_tipo_equipo' id='Cbx_tipo_equipo' >");
                            out.print("<option value='0' >Seleccionar Tipo de equipo</option>");
                            for (int i = 0; i < lst_tipo_equipos.size(); i++) {
                                Object[] obj_tipo_equipos = (Object[]) lst_tipo_equipos.get(i);
                                out.print("<option value='" + obj_tipo_equipos[0] + "' >" + obj_tipo_equipos[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_equipo');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Nombre de equipo: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_nombre_equipo' id='Txt_nombre_equipo' placeholder='Nombre de equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_equipo');val1.add(Validate.Presence);</script>");
                            out.print("<b>Fabricante :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_marca' id='Txt_marca' placeholder='Marca' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_marca');val1.add(Validate.Presence);</script>");
                            out.print("<b>Tipo/Modelo :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_tipo_modelo' id='Txt_tipo_modelo' placeholder='Tipo/modelo' title='Tipo/modelo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo_modelo');val1.add(Validate.Presence);</script>");
                            out.print("<b>Año fabricación :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_ano_fabricacion' id='Txt_ano_fabricacion' placeholder='Año fabricación' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ano_fabricacion');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" style='cursor: pointer;' class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Equipo?opc=1'\" style='cursor: pointer;' class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                            out.print("<b>Serie: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_serie' id='Txt_serie' placeholder='Serie' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_serie');val1.add(Validate.Presence);</script>");
                            out.print("<b>Codigo interno: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_codigo_interno' id='Txt_codigo_interno' placeholder='Codigo interno' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_interno');val1.add(Validate.Presence);</script>");
                            out.print("<b>No. Maquina: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_numero_maquina' id='Txt_numero_maquina' placeholder='Numero de maquina' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_numero_maquina');val1.add(Validate.Presence);</script>");
                            out.print("<b>Localización: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_localizacion' id='Txt_localizacion' placeholder='Localización' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_localizacion');val1.add(Validate.Presence);</script>");
                            out.print("<b>Linea: </b>");
                            out.print("<select class='form-control' name='Cbx_linea' id='Cbx_linea' title='Linea'>");
                            out.print("<option value='0' >Seleccionar Linea</option>");
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                out.print("<option value='" + obj_lineas[0] + "' >" + obj_lineas[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_linea');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_equipo > 0 && estado == 0) {
                            lst_equipo = jpaceqp.Equipo_id(id_equipo);
                            Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Tipo Equipo</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Equipo?opc=2&Id_equipo=" + id_equipo + "' method='post' id='FormEquipUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Tipo de equipo: </b>");
                            out.print("<select class='form-control' name='Cbx_tipo_equipo' id='Cbx_tipo_equipoM' title='Tipo de equipo'>");
                            out.print("<option value='0' >Seleccionar Tipo de equipo</option>");
                            for (int i = 0; i < lst_tipo_equipos.size(); i++) {
                                Object[] obj_tipo_equipos = (Object[]) lst_tipo_equipos.get(i);
                                out.print("<option value='" + obj_tipo_equipos[0] + "' " + (((Integer) obj_equipo[2] == (Integer) obj_tipo_equipos[0]) ? "selected" : "") + ">" + obj_tipo_equipos[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_equipoM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Nombre de equipo: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_nombre_equipo' id='Txt_nombre_equipoM' placeholder='Nombre de equipo' value='" + obj_equipo[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre_equipoM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Fabricante :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_marca' id='Txt_marcaM' placeholder='Fabricante' value='" + obj_equipo[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_marcaM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Tipo/Modelo :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_tipo_modelo' id='Txt_tipo_modeloM' placeholder='Tipo/modelo' value='" + obj_equipo[5] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo_modeloM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Año fabricación :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_ano_fabricacion' id='Txt_ano_fabricacionM' placeholder='Año fabricación' value='" + obj_equipo[6] + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ano_fabricacionM');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" style='cursor: pointer;' class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Equipo?opc=1'\" style='cursor: pointer;' class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                            out.print("<b>Serie: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_serie' id='Txt_serieM' placeholder='SerieM' title='Serie' value='" + obj_equipo[7] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_serieM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Codigo interno: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_codigo_interno' id='Txt_codigo_internoM' placeholder='Codigo interno' value='" + obj_equipo[8] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_internoM');val1.add(Validate.Presence);</script>");
                            out.print("<b>No. Maquina: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_numero_maquina' id='Txt_numero_maquinaM' placeholder='Numero de maquina' value='" + obj_equipo[13] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_numero_maquinaM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Localización: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_localizacion' id='Txt_localizacionM' placeholder='Localización' value='" + obj_equipo[21] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_localizacionM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Linea: </b>");
                            out.print("<select class='form-control' name='Cbx_linea' id='Cbx_lineaM' title='LineaM'>");
                            out.print("<option value='0' >Seleccionar Linea</option>");
                            for (int i = 0; i < lst_lineas.size(); i++) {
                                Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                                out.print("<option value='" + obj_lineas[0] + "' " + (((Integer) obj_equipo[19] == (Integer) obj_lineas[0]) ? "selected" : "") + ">" + obj_lineas[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_lineaM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Orden de trabajo">
                        if (id_equipo > 0 && estado > 0) {
                            lst_equipo = jpaceqp.Equipo_id(id_equipo);
                            Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                            String[] arg_registros_equipo = obj_equipo[28].toString().split(",");
                            lst_numero_orden = jpacotb.Numero_ot();
                            Object[] obj_numero_orden = (Object[]) lst_numero_orden.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Orden de trabajo</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Orden?opc=2&Id_equipo=" + id_equipo + "&Id_orden=0' method='post' id='FormOrderRegister'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Equipo: </b>");
                            out.print("<input class='form-control' type='text' placeholder='Nombre de equipo' value='" + obj_equipo[1] + "' readonly/>");
                            out.print("<b>Linea: </b>");
                            out.print("<input class='form-control' type='text' placeholder='Linea' value='" + obj_equipo[20] + "' readonly/>");
                            out.print("<b>Tecnico Ejecutor: </b>");
                            out.print("<select class='form-control' name='Cbx_ejecutor' id='Cbx_ejecutor'>");
                            out.print("<option value='0' >Seleccionar Ejecutor</option>");
                            lst_usuarios = jpacusa.Usuarios_rol(5);
                            if (lst_usuarios != null) {
                                for (int i = 0; i < lst_usuarios.size(); i++) {
                                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                    out.print("<option value='" + obj_usuarios[1] + "' >" + obj_usuarios[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ejecutor');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                                out.print("<b>Tecnico Aprobador: </b>");
                                out.print("<select class='form-control' name='Cbx_aprobador' id='Cbx_aprobador'>");
                                out.print("<option value='0' >Seleccionar Aprobador</option>");
                                lst_usuarios = jpacusa.Usuarios_rol(3);
                                if (lst_usuarios != null) {
                                    for (int i = 0; i < lst_usuarios.size(); i++) {
                                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                        out.print("<option value='" + obj_usuarios[1] + "' >" + obj_usuarios[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_aprobador');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            }
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionO').click();\" style='cursor: pointer;' class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Equipo?opc=1'\" style='cursor: pointer;' class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                            out.print("<b>Numero OT: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_numero_ot' id='Txt_numero_ot' placeholder='Numero OT' value='" + obj_numero_orden[1] + "' readonly/>");
                            out.print("<b>Programador: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_programador' id='Txt_programador' placeholder='Programador' value='" + nombre_usuario + "' readonly/>");
                            if (id_equipo != 47 && id_equipo != 37 && id_equipo != 48) {
                                out.print("<b>Tecnico Revisor: </b>");
                                out.print("<select class='form-control' name='Cbx_revisor' id='Cbx_revisor'>");
                                out.print("<option value='0' >Seleccionar Revisor</option>");
                                lst_usuarios = jpacusa.Usuarios_rol(6);
                                if (lst_usuarios != null) {
                                    for (int i = 0; i < lst_usuarios.size(); i++) {
                                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                        out.print("<option value='" + obj_usuarios[1] + "' >" + obj_usuarios[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_revisor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            }
                            out.print("<b>Tipo de mtto: </b>");
                            out.print("<select class='form-control' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto'>");
                            out.print("<option value='0' >Seleccionar Tipo Mtto</option>");
                            lst_tipo_mtto = jpactmt.Tipos_mtto_estado((Integer) obj_equipo[2]);
                            if (lst_tipo_mtto != null) {
                                for (int i = 0; i < lst_tipo_mtto.size(); i++) {
                                    Object[] obj_tipo_mtto = (Object[]) lst_tipo_mtto.get(i);
                                    if (!arg_registros_equipo[i].equals("N/A")) {
                                        out.print("<option value='" + obj_tipo_mtto[0] + "' >" + obj_tipo_mtto[1] + " " + obj_tipo_mtto[2] + "</option>");
                                    }
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_mtto');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionO' /></div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Actualizar fechas old">
                        out.print("<div class=\"modal fade\" id=\"DateOldModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\">");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"FechaOldModalLabel\">Actualizar fechas PMP old</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        out.print("<form action='Equipo?opc=9' method='post'>");
                        out.print("</b><input class='form-control' type='hidden'name='Id_equipo' id='Id_equipo_old' />");
                        out.print("<b>Equipo: </b><input class='form-control' type='text' id='Txt_nombre_equipo_old' readonly />");
                        out.print("<b>Fecha old MM: </b><input class='form-control' type='text' name='Txt_fecha_mm' id='datepicker_mm' autocomplete='off' />");
                        out.print("<b>Fecha old INSP:</b><input class='form-control' type='text' name='Txt_fecha_insp' id='datepicker_insp' autocomplete='off' />");
                        out.print("<b>Fecha old LUB:</b><input class='form-control' type='text' name='Txt_fecha_lub' id='datepicker_lub' autocomplete='off' />");
                        out.print("<div style='display:none'><input type='submit' id='Btn_fecha_old' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_fecha_old').click();\">Firmar</a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="Consulta">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Equipos</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class='mb-3' style='float: right; display: flex; width: 210px;justify-content: space-evenly;' >");
                    out.print("<button class='btn btn-" + ((temp == 0) ? "blue" : "secondary") + " btn-sm' onclick='CambiarListadoEquipos(0)'>MM</button>");
                    out.print("<button class='btn btn-" + ((temp == 2) ? "blue" : "secondary") + " btn-sm' onclick='CambiarListadoEquipos(2)'>INSP</button>");
                    out.print("<button class='btn btn-" + ((temp == 3) ? "blue" : "secondary") + " btn-sm' onclick='CambiarListadoEquipos(3)'>LUB</button>");
                    out.print("<button class='btn btn-" + ((temp == 1) ? "blue" : "secondary") + " btn-sm' onclick='CambiarListadoEquipos(1)'>TODO</button>");
                    out.print("</div>");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>MM</th>");
                    out.print("<th style='width:5px'>INPS</th>");
                    out.print("<th style='width:5px'>LUB</th>");
                    out.print("<th style='width:5px'>No.</th>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Linea</th>");
                    out.print("<th style='width:20%' colspan='3'>OT</th>");
                    out.print("<th style='width:3px;padding-right:12px;'>FT</th>");
                    out.print("<th style='width:3px;padding-right:12px;'>Notas</th>");
                    out.print("<th style='width:3px;padding-right:12px;'>Modificar</th>");
                    out.print("<th style='width:5px'>Estado</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>MM</th>");
                    out.print("<th style='width:5px'>INPS</th>");
                    out.print("<th style='width:5px'>LUB</th>");
                    out.print("<th style='width:5px'>No.</th>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Linea</th>");
                    out.print("<th style='width:20%' colspan='3'>OT</th>");
                    out.print("<th style='width:3px;padding-right:12px;'>FT</th>");
                    out.print("<th style='width:3px;padding-right:12px;'>Notas</th>");
                    out.print("<th style='width:3px;padding-right:12px;'>Modificar</th>");
                    out.print("<th style='width:5px'>Estado</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    if (temp == 1) {
                        lst_equipos = jpaceqp.Equipos();
                    } else if (temp == 2) {
                        lst_equipos = jpaceqp.Equipos_insp();
                    } else if (temp == 3) {
                        lst_equipos = jpaceqp.Equipos_lub();
                    } else {
                        lst_equipos = jpaceqp.Equipos_mm();
                    }
                    if (lst_equipos != null) {
                        for (int i = 0; i < lst_equipos.size(); i++) {
                            Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                            String[] arg_registros_equipos = obj_equipos[28].toString().split(",");
                            if (temp == 1) {
                                //<editor-fold defaultstate="collapsed" desc="TODO">
                                if (Integer.parseInt(obj_equipos[24].toString()) == 1) {
                                    out.print("<tr>");
                                } else {
                                    out.print("<tr class='tr_rojo'>");
                                }
                                if (Integer.parseInt(obj_equipos[27].toString()) == 1) {
                                    if (!arg_registros_equipos[0].equals("N/A")) {
                                        out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_equipos[35] + "</i><span class='fas fa-circle fa-size_small " + obj_equipos[35].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                                    } else {
                                        out.print("<td align='center'>N/A</td>");
                                    }
                                    if (!arg_registros_equipos[1].equals("N/A")) {
                                        out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_equipos[42] + "</i><span class='fas fa-circle fa-size_small " + obj_equipos[42].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                                    } else {
                                        out.print("<td align='center'>N/A</td>");
                                    }
                                    out.print("<td align='center'>N/A</td>");
                                } else {
                                    out.print("<td align='center'>N/A</td>");
                                    out.print("<td align='center'>N/A</td>");
                                    out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_equipos[49] + "</i><span class='fas fa-circle fa-size_small " + obj_equipos[49].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                                }
                                out.print("<td>" + obj_equipos[13] + "</td>");
                                out.print("<td>" + obj_equipos[1] + "</td>");
                                out.print("<td>" + obj_equipos[3] + "</td>");
                                out.print("<td>" + obj_equipos[20] + "</td>");
                                out.print("<td>");
                                if (Integer.parseInt(obj_equipos[27].toString()) == 1) {
                                    if (!arg_registros_equipos[0].equals("N/A")) {
                                        if (obj_equipos[29] != null) {
                                            out.print("<b>Ultima OT MM:</b><a href='Orden?opc=5&Id_orden=" + obj_equipos[32] + "'>" + obj_equipos[29] + "</a><b> | Fecha :</b>" + obj_equipos[30] + "<b> | Proxima :</b>" + obj_equipos[33] + "");
                                        } else {
                                            out.print("<b class='naranja'>No se encuentran OT MM en el sistema Proxima : " + obj_equipos[33] + "</b>");
                                        }
                                    }
                                    if (!arg_registros_equipos[1].equals("N/A")) {
                                        out.print("<hr style='margin:0px' />");
                                        if (obj_equipos[36] != null) {
                                            out.print("<b>Ultima OT INSP :</b><a href='Orden?opc=5&Id_orden=" + obj_equipos[39] + "'>" + obj_equipos[36] + "</a><b> | Fecha :</b>" + obj_equipos[37] + "<b> | Proxima :</b>" + obj_equipos[40] + "");
                                        } else {
                                            out.print("<b class='naranja'>No se encuentran OT INSP en el sistema Proxima :" + obj_equipos[40] + " </b>");
                                        }
                                    }
                                } else if (obj_equipos[43] != null) {
                                    out.print("<b>Ultima OT LUB:</b><a href='Orden?opc=5&Id_orden=" + obj_equipos[46] + "'>" + obj_equipos[43] + "</a><b> | Fecha :</b>" + obj_equipos[44] + "<b> | Proxima :</b>" + obj_equipos[47] + "");
                                } else {
                                    out.print("<b class='naranja'>No se encuentran OT LUB en el sistema Proxima :" + obj_equipos[47] + "</b>");
                                }
                                out.print("</td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Equipo?opc=1&Id_equipo=" + obj_equipos[0] + "&etd=1'\"" : "style='cursor:not-allowed'") + " class='fa fa-plus fa-size_small' title='Nueva OT'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Orden?opc=1&Id_equipo=" + obj_equipos[0] + "'\" class='fa fa-bars fa-size_small' title='Ver OT'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Equipo?opc=4&Id_equipo=" + obj_equipos[0] + "'\" class='far fa-file-alt fa-size_small' title='FT'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Equipo?opc=10&Id_equipo=" + obj_equipos[0] + "'\" class='far fa-sticky-comment-dots fa-size_small' title='Nota'></span></td>");
                                if (Integer.parseInt(obj_equipos[24].toString()) == 1) {
                                    out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Equipo?opc=1&Id_equipo=" + obj_equipos[0] + "&etd=0'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Equipo'></span></td>");
//                                            + "&nbsp;&nbsp;<span class='fas fa-history fa-size_small' title='Fecha old' data-toggle=\"modal\" data-target=\"#DateOldModal\" " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"ActualizarFechasOld('" + obj_equipos[0] + "','" + obj_equipos[1] + "','" + obj_equipos[27] + "','" + obj_equipos[28] + "','" + ((temp == 1) ? obj_equipos[50] : obj_equipos[36]) + "');\"" : "style='cursor:not-allowed'") + "></span></td>");
                                    out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarEquipo(" + obj_equipos[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Equipo'></span></td>");
                                } else {
                                    out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Tipos de Equipo Inactivos'></span></td>");
                                    out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarEquipo(" + obj_equipos[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Equipo'></span></td>");
                                }
                                out.print("</tr>");

                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="OTROS">
                                if (Integer.parseInt(obj_equipos[24].toString()) == 1) {
                                    out.print("<tr>");
                                } else {
                                    out.print("<tr class='tr_rojo'>");
                                }
                                if (Integer.parseInt(obj_equipos[27].toString()) == 1 && temp != 3) {
                                    if (!obj_equipos[35].equals("N/A")) {
                                        if (temp == 0) {
                                            out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_equipos[35] + "</i><span class='fas fa-circle fa-size_small " + obj_equipos[35].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                                        } else {
                                            out.print("<td align='center'>N/A</td>");
                                        }
                                    } else {
                                        out.print("<td align='center'>N/A</td>");
                                    }
                                    if (temp == 2) {
                                        out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_equipos[35] + "</i><span class='fas fa-circle fa-size_small " + obj_equipos[35].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                                    } else {
                                        out.print("<td align='center'>N/A</td>");
                                    }
                                    out.print("<td align='center'>N/A</td>");
                                } else {
                                    out.print("<td align='center'>N/A</td>");
                                    out.print("<td align='center'>N/A</td>");
                                    if (obj_equipos[35].equals("N/A")) {
                                        out.print("<td align='center'>N/A</td>");
                                    } else {
                                        out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_equipos[35] + "</i><span class='fas fa-circle fa-size_small " + obj_equipos[35].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                                    }
                                }
                                out.print("<td>" + obj_equipos[13] + "</td>");
                                out.print("<td>" + obj_equipos[1] + "</td>");
                                out.print("<td>" + obj_equipos[3] + "</td>");
                                out.print("<td>" + obj_equipos[20] + "</td>");
                                out.print("<td>");
                                if (temp == 0) {
                                    if (obj_equipos[29] != null) {
                                        out.print("<b>Ultima OT MM:</b><a href='Orden?opc=5&Id_orden=" + obj_equipos[32] + "'>" + obj_equipos[29] + "</a><b> | Fecha :</b>" + obj_equipos[30] + "<b> | Proxima :</b>" + obj_equipos[33] + "");
                                    } else {
                                        out.print("<b class='naranja'>No se encuentran OT MM en el sistema Proxima : " + obj_equipos[33] + "</b>");
                                    }
                                }
                                if (temp == 2) {
                                    out.print("<hr style='margin:0px' />");
                                    if (obj_equipos[29] != null) {
                                        out.print("<b>Ultima OT INSP :</b><a href='Orden?opc=5&Id_orden=" + obj_equipos[32] + "'>" + obj_equipos[29] + "</a><b> | Fecha :</b>" + obj_equipos[30] + "<b> | Proxima :</b>" + obj_equipos[33] + "");
                                    } else {
                                        out.print("<b class='naranja'>No se encuentran OT INSP en el sistema Proxima :" + obj_equipos[33] + " </b>");
                                    }
                                }
                                if (temp == 3) {
                                    if (obj_equipos[32] != null) {
                                        out.print("<b>Ultima OT LUB:</b><a href='Orden?opc=5&Id_orden=" + obj_equipos[32] + "'>" + obj_equipos[29] + "</a><b> | Fecha :</b>" + obj_equipos[30] + "<b> | Proxima :</b>" + obj_equipos[33] + "");
                                    } else {
                                        out.print("<b class='naranja'>No se encuentran OT LUB en el sistema Proxima :" + obj_equipos[33] + "</b>");
                                    }
                                }
//                                else {
//                                    out.print("<b class='naranja'>No se encuentran OT LUB en el sistema Proxima :" + obj_equipos[33] + "</b>");
//                                }
                                out.print("</td>");

                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Equipo?opc=1&Id_equipo=" + obj_equipos[0] + "&etd=1'\"" : "style='cursor:not-allowed'") + " class='fa fa-plus fa-size_small' title='Nueva OT'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Orden?opc=1&Id_equipo=" + obj_equipos[0] + "'\" class='fa fa-bars fa-size_small' title='Ver OT'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Equipo?opc=4&Id_equipo=" + obj_equipos[0] + "'\" class='far fa-file-alt fa-size_small' title='FT'></span></td>");
//                                out.print("<td align='center'><span onclick=\"location.href='Equipo?opc=10&Id_equipo=" + obj_equipos[0] + "'\" class='far fa-sticky-note fa-size_small' title='FT'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Equipo?opc=10&Id_equipo=" + obj_equipos[0] + "'\" class='far fa-comment-dots fa-size_small' title='Nota'></span></td>");
                                if (Integer.parseInt(obj_equipos[24].toString()) == 1) {
                                    out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Equipo?opc=1&Id_equipo=" + obj_equipos[0] + "&etd=0'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Equipo'></span></td>");
//                                            + "&nbsp;&nbsp;<span class='fas fa-history fa-size_small' title='Fecha old' data-toggle=\"modal\" data-target=\"#DateOldModal\" " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"ActualizarFechasOld('" + obj_equipos[0] + "','" + obj_equipos[1] + "','" + obj_equipos[27] + "','" + obj_equipos[28] + "','" + obj_equipos[36] + "');\"" : "style='cursor:not-allowed'") + "></span></td>");
                                    out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarEquipo(" + obj_equipos[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Equipo'></span></td>");
                                } else {
                                    out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Tipos de Equipo Inactivos'></span></td>");
                                    out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarEquipo(" + obj_equipos[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Equipo'></span></td>");
                                }
                                out.print("</tr>");
                                //</editor-fold>
                            }
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Equipos").toString().equals("Ficha_tecnica")) {
                    //<editor-fold defaultstate="collapsed" desc="FICHA TECNICA">
                    id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo").toString());
                    lst_equipo = jpaceqp.Equipo_id(id_equipo);
                    id_ficha_tecnica = Integer.parseInt(pageContext.getRequest().getAttribute("Id_ficha_tecnica").toString());
                    tipo_ficha = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_ficha").toString());
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Equipo?opc=1' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        if (obj_equipo[1] != null) {
                            //<editor-fold defaultstate="collapsed" desc="Modificar">
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Actualizar Ficha tecnica</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse\" id=\"collapseCardExample\">");
                            out.print("<div class=\"card-body\" >");
                            out.print("<div style='width:40%;float:left'>");
                            out.print("<form action='Equipo?opc=5&Id_equipo=" + id_equipo + "' method='post' id='FormFichaRegistro'>");
                            out.print("<input type='hidden' name='Txt_tipo' value='2'>");
                            out.print("<b>Fecha de instalación: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_fecha_instalacion' id='datepicker' value='" + ((obj_equipo[9] == null) ? "" : obj_equipo[9]) + "' placeholder='Fecha de instalacion' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                            out.print("<b>Proveedor: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_proveedor' id='Txt_proveedor' value='" + ((obj_equipo[10] == null) ? "" : obj_equipo[10]) + "' placeholder='Proveedor'  />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_proveedor');val1.add(Validate.Presence);</script>");
                            out.print("<b>Numero contacto :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_numero_contacto' id='Txt_numero_contacto' value='" + ((obj_equipo[11] == null) ? "" : obj_equipo[11]) + "' placeholder='Numero contacto' title='Numero contacto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_numero_contacto');val1.add(Validate.Presence);</script>");
                            out.print("<b>Pais origen  :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_pais_origen' id='Txt_pais_origen' value='" + ((obj_equipo[12] == null) ? "" : obj_equipo[12]) + "' placeholder='Pais origen' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_pais_origen');val1.add(Validate.Presence);</script>");
                            out.print("<b>Observación: </b>");
                            out.print("<textarea class='form-control' name='Txt_observacion' id='Txt_observacion' style='width:208%;' placeholder='Observación' onchange='javascript:this.value=this.value.toUpperCase();'>" + ((obj_equipo[22] == null) ? "" : obj_equipo[22]) + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observacion');val1.add(Validate.Presence);</script>");
                            out.print("<b>Observación generales: </b>");
                            out.print("<textarea class='form-control' name='Txt_observacion_generales' id='Txt_observacion_generales' style='width:208%;'placeholder='Observación' onchange='javascript:this.value=this.value.toUpperCase();'>" + ((obj_equipo[23] == null) ? "" : obj_equipo[23]) + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observacion_generales');val1.add(Validate.Presence);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" style='cursor: pointer;' class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Equipo?opc=4&Id_equipo=" + id_equipo + "'\" style='cursor: pointer;' class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                            out.print("<b>Longitud: </b>");
                            out.print("<div class='input-group'>");
                            out.print("<input class='form-control' type='text' name='Txt_longitud' id='Txt_longitud' value='" + obj_equipo[14] + "'  placeholder='Longitud' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_longitud');val1.add(Validate.Presence);</script>");
                            out.print("<input class='form-control' type='text' onkeyup='ConcatUnidadMedida()' name='Txt_unidad_medida1' id='Txt_unidad_medida1' value='" + ((obj_equipo[18] == null) ? "" : obj_equipo[18].toString().split("//")[0]) + "' placeholder='Unidad medida' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad_medida1');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("<b>Ancho: </b>");
                            out.print("<div class='input-group'>");
                            out.print("<input class='form-control' type='text' name='Txt_ancho' id='Txt_ancho' value='" + obj_equipo[15] + "' placeholder='Ancho' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ancho');val1.add(Validate.Presence);</script>");
                            out.print("<input class='form-control' type='text' onkeyup='ConcatUnidadMedida()' name='Txt_unidad_medida2' id='Txt_unidad_medida2' value='" + ((obj_equipo[18] == null) ? "" : obj_equipo[18].toString().split("//")[1]) + "' placeholder='Unidad medida' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad_medida2');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("<b>Alto: </b>");
                            out.print("<div class='input-group'>");
                            out.print("<input class='form-control' type='text' name='Txt_alto' id='Txt_alto' value='" + obj_equipo[16] + "' placeholder='Alto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_alto');val1.add(Validate.Presence);</script>");
                            out.print("<input class='form-control' type='text' onkeyup='ConcatUnidadMedida()' name='Txt_unidad_medida3' id='Txt_unidad_medida3' value='" + ((obj_equipo[18] == null) ? "" : obj_equipo[18].toString().split("//")[2]) + "' placeholder='Unidad medida' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad_medida3');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("<b>Peso total (KG): </b>");
                            out.print("<div class='input-group'>");
                            out.print("<input class='form-control' type='text' name='Txt_peso_total' id='Txt_peso_total' value='" + obj_equipo[17] + "' placeholder='Peso total (KG)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_peso_total');val1.add(Validate.Presence);</script>");
                            out.print("<input class='form-control' type='text' onkeyup='ConcatUnidadMedida()' name='Txt_unidad_medida4' id='Txt_unidad_medida4' value='" + ((obj_equipo[18] == null) ? "" : obj_equipo[18].toString().split("//")[3]) + "' placeholder='Unidad medida' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_unidad_medida4');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /><input type='hidden' name='Txt_unidad_medida' id='Txt_unidad_medida' value='" + ((obj_equipo[18] == null) ? "" : obj_equipo[18]) + "'/></div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                    }
                    out.print("<div style='float:right'>");
                    out.print("<span class='fas fa-print fa-size_small' onclick=\"Imprimir();\" title='Imprimir'></span>");
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="R-MTF-002">
                    out.print("<div id='Div_export'>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO">
                    out.print("<table class=\"table2\" style='font-size:12px; margin:0px'>");
                    out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Images/Logo.png' alt='Logo'style='width:60%' /></td>");
                    out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                    out.print("<td align='center' style='width:50%;'>CODIGO R-MTF-002</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'>HOJA DE VIDA DE EQUIPO</td>");
                    out.print("<td align='center'>VERSIÓN: 002</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<table class=\"table2\" style='font-size:12px; margin:0px;'>");
                    out.print("<tr>");
                    out.print("<td style='color: #fff;background-color:#4e73df;border:1px solid #fff;text-align: center;' colspan='6'>Equipo</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th style='width: 25%text-align: center;' >NOMBRE DE EQUIPO</th>");
                    out.print("<th style='width: 15%;text-align: center;' >MARCA</th>");
                    out.print("<th style='width: 20%;text-align: center;' >TIPO/MODELO</th>");
                    out.print("<th style='width: 10%;text-align: center;' >AÑO FABRICACIÓN</th>");
                    out.print("<th style='width: 10%;text-align: center;' >SERIE</th>");
                    out.print("<th style='width: 25%;text-align: center;' >CODIGO INTERNO</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width: 25%;' align='center'>" + obj_equipo[1] + "</td>");
                    out.print("<td style='width: 20%;' align='center'>" + obj_equipo[4] + "</td>");
                    out.print("<td style='width: 20%;' align='center'>" + obj_equipo[5] + "</td>");
                    out.print("<td style='width: 10%;' align='center'>" + obj_equipo[6] + "</td>");
                    out.print("<td style='width: 10%;' align='center'>" + obj_equipo[7] + "</td>");
                    out.print("<td style='width: 25%;' align='center'>" + obj_equipo[8] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th style='width: 20%;text-align: center;'>FECHA INSTALACIÓN</th>");
                    out.print("<th style='width: 20%;text-align: center;'>PROVEEDOR</th>");
                    out.print("<th style='width: 20%;text-align: center;'>NUMERO CONTACTO</th>");
                    out.print("<th style='width: 20%;text-align: center;' colspan='2'>PAIS ORIGEN</th>");
                    out.print("<th style='width: 20%;text-align: center;'>MAQUINA No.</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:20%;' align='center'>" + obj_equipo[9] + "</td>");
                    out.print("<td style='width:20%;' align='center'>" + obj_equipo[10] + "</td>");
                    out.print("<td style='width:20%;'align='center'>" + obj_equipo[11] + "</td>");
                    out.print("<td style='width:20%;' align='center' colspan='2' >" + obj_equipo[12] + "</td>");
                    out.print("<td style='width:20%;' align='center'>" + obj_equipo[13] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<table class=\"table2\" style='font-size:12px; margin:0px;'>");
                    out.print("<tr>");
                    out.print("<th style='width:30%;' colspan='5' style='text-align: center;'>DIMENSIONES</th>");
                    out.print("<th style='width:40%;text-align: center;'>OBSERVACIONES</th>");
                    out.print("<th style='width:15%;text-align: center;'>LINEA</th>");
                    out.print("<th style='width:15%;text-align: center;'>LOCALIZACIÓN</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align:center;font-weight:bold;'>LONGITUD</td>");
                    out.print("<td style='text-align:center;font-weight:bold;'>ANCHO</td>");
                    out.print("<td style='text-align:center;font-weight:bold;'>ALTO</td>");
                    out.print("<td style='text-align:center;font-weight:bold;' colspan='2'>PESO TOTAL(KG)</td>");
                    out.print("<td rowspan='3'>" + obj_equipo[22] + "</td>");
                    out.print("<td rowspan='3' align='center'>" + obj_equipo[20] + "</td>");
                    out.print("<td rowspan='3' align='center'>" + obj_equipo[21] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td rowspan='2' align='center'>" + obj_equipo[14] + " " + ((obj_equipo[18] == null) ? "" : "(" + obj_equipo[18].toString().split("//")[0] + ")") + "</td>");
                    out.print("<td rowspan='2' align='center'>" + obj_equipo[15] + " " + ((obj_equipo[18] == null) ? "" : "(" + obj_equipo[18].toString().split("//")[1] + ")") + "</td>");
                    out.print("<td rowspan='2' align='center'>" + obj_equipo[16] + " " + ((obj_equipo[18] == null) ? "" : "(" + obj_equipo[18].toString().split("//")[2] + ")") + "</td>");
                    out.print("<td rowspan='2' align='center' colspan='2'>" + obj_equipo[17] + " " + ((obj_equipo[18] == null) ? "" : "(" + obj_equipo[18].toString().split("//")[3] + ")") + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CARACTERISITICAS TECNICAS">
                    lst_ficha = jpacftn.Consultar_ficha_tecnica(id_equipo, 1);
                    out.print("<table class=\"table2\" style='font-size:12px;margin:0px;'>");
                    out.print("<tr>");
                    if (Integer.parseInt(obj_equipo[51].toString()) > 0 || (id_ficha_tecnica > 0 && tipo_ficha == 1)) {
//                        out.print("<td style='color: #fff;background-color:#4e73df;border:1px solid #fff;text-align: center;' ><span onclick='RegistroFichaTecnica(" + id_equipo + ",1);' class='fas fa-plus fa-size_small'></span></td>");
                        out.print("<td colspan='5' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>CARACTERISITICAS TECNICAS</td>");
                        out.print("<td style='color: #fff;background-color:#4e73df;border:1px solid #fff;text-align: center;'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"javascript:document.getElementById('Btn_ft_fase1').click();\"" : "style='cursor:not-allowed'") + " class='far fa-save fa-size_small'></span></td>");
                    } else {
                        out.print("<td style='width:3%;color: #fff;background-color:#4e73df;border:1px solid #fff;text-align: center;' ><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='RegistroFichaTecnica(" + id_equipo + ",1);'" : "style='cursor:not-allowed'") + " class='fas fa-plus fa-size_small'></span></td>");
                        out.print("<td colspan='5' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>CARACTERISITICAS TECNICAS</td>");
//                        out.print("<td style='color: #fff;background-color:#4e73df;border:1px solid #fff;text-align: center;'><span onclick=\"javascript:document.getElementById('Btn_ft_fase1').click();\" class='far fa-save fa-size_small'></span></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:3%;font-weight:bold;text-align:center;'>ITEM</td>");
                    out.print("<td style='width:25%;font-weight:bold;text-align:center;'>CARACTERISTICAS GENERAL</td>");
                    out.print("<td style='width:10%;font-weight:bold;text-align:center;'>UNIDAD</td>");
                    out.print("<td style='width:17%;font-weight:bold;text-align:center;'>VALOR</td>");
                    out.print("<td style='width:15%;font-weight:bold;text-align:center;'>ESTADO</td>");
                    out.print("<td style='width:30%;font-weight:bold;text-align:center;'>INFORMACIÓN ADICIONAL</td>");
                    out.print("</tr>");
                    if (lst_ficha != null) {
                        for (int i = 0; i < lst_ficha.size(); i++) {
                            Object[] obj_ficha = (Object[]) lst_ficha.get(i);
                            if (id_ficha_tecnica == Integer.parseInt(obj_ficha[0].toString()) && tipo_ficha == 1) {
                                String arrg_datos[] = obj_ficha[4].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<form action='Equipo?opc=6&Id_equipo=" + id_equipo + "' method='post' id='FormCaracteristicasT'>");
                                out.print("<tr>");
                                out.print("<input type='hidden' name='Txt_tipo'  value='1'>");
                                out.print("<input type='hidden' name='Id_ficha_tecnica' value='" + obj_ficha[0] + "'>");
                                out.print("<td style='width:10%;' align='center'><input type='number' class='form-control' name='Txt_item' value='" + obj_ficha[3] + "' required /></td>");
                                out.print("<td style='width:30%' align='center'><input type='text' class='form-control' name='Txt_caracteristicas_generales' required value='" + arrg_datos[0] + "' /></td>");
                                out.print("<td style='width:10%;' align='center'><input type='text' class='form-control' name='Txt_unidad' required value='" + arrg_datos[1] + "'/></td>");
                                out.print("<td style='width:15%;' align='center'><input type='text' class='form-control' name='Txt_valor' required value='" + arrg_datos[2] + "'/></td>");
                                out.print("<td style='width:15%;' align='center'><input type='text' class='form-control' name='Txt_estado' required value='" + arrg_datos[3] + "'/></td>");
                                out.print("<td style='width:30%;' align='center'><input type='text' class='form-control'  name='Txt_informacion_adicional' required value='" + arrg_datos[4] + "'/></td>");
                                out.print("<div style='display:none'><input type='submit' id='Btn_ft_fase1' /></div>");
                                out.print("</tr>");
                                out.print("</form>");
                            }
                            if (obj_ficha[4].equals("N/A") && id_ficha_tecnica == 0 && tipo_ficha != 1) {
                                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                                    out.print("<form action='Equipo?opc=6&Id_equipo=" + id_equipo + "' method='post' id='FormCaracteristicasT'>");
                                    out.print("<tr>");
                                    out.print("<input type='hidden' name='Txt_tipo'  value='1'>");
                                    out.print("<input type='hidden' name='Id_ficha_tecnica' value='" + obj_ficha[0] + "'>");
                                    out.print("<td style='width:10%;' align='center'><input type='number' class='form-control' name='Txt_item' value='" + obj_ficha[3] + "' required /></td>");
                                    out.print("<td style='width:30%' align='center'><input type='text' class='form-control' name='Txt_caracteristicas_generales' required /></td>");
                                    out.print("<td style='width:10%;' align='center'><input type='text' class='form-control' name='Txt_unidad' required /></td>");
                                    out.print("<td style='width:15%;' align='center'><input type='text' class='form-control' name='Txt_valor' required /></td>");
                                    out.print("<td style='width:15%;' align='center'><input type='text' class='form-control' name='Txt_estado' required /></td>");
                                    out.print("<td style='width:30%;' align='center'><input type='text' class='form-control'  name='Txt_informacion_adicional' required /></td>");
                                    out.print("<div style='display:none'><input type='submit' id='Btn_ft_fase1' /></div>");
                                    out.print("</tr>");
                                    out.print("</form>");
                                }
                            } else if (id_ficha_tecnica != Integer.parseInt(obj_ficha[0].toString())) {
                                String arrg_datos[] = obj_ficha[4].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<tr>");
                                if (Integer.parseInt(obj_equipo[51].toString()) > 0 || Integer.parseInt(obj_equipo[52].toString()) > 0) {
                                    out.print("<td style='width:10%;' align='center'>" + obj_ficha[3] + "</td>");
                                } else {
                                    out.print("<td style='width:10%;' align='center'><a href='Equipo?opc=4&Id_equipo=" + id_equipo + "&Id_ficha_tecnica=" + obj_ficha[0] + "&Tipo_ficha=1'>" + obj_ficha[3] + "</a></td>");
                                }
                                out.print("<td style='width:30%' align='center'> " + arrg_datos[0] + "</td>");
                                out.print("<td style='width:10%;' align='center'>" + arrg_datos[1] + "</td>");
                                out.print("<td style='width:15%;' align='center'>" + arrg_datos[2] + "</td>");
                                out.print("<td style='width:15%;' align='center'>" + arrg_datos[3] + "</td>");
                                out.print("<td style='width:30%;' align='center'>" + arrg_datos[4] + "</td>");
                                out.print("</tr>");
                            }
                        }
                    }
                    out.print("</table>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CARACTERISTICAS ESPECIFICAS">
                    lst_ficha = jpacftn.Consultar_ficha_tecnica(id_equipo, 2);
                    out.print("<table class=\"table2\" style='font-size:12px; margin:0px;'>");
                    out.print("<tr>");
                    if (Integer.parseInt(obj_equipo[52].toString()) > 0 || (id_ficha_tecnica > 0 && tipo_ficha == 2)) {
//                    if (Integer.parseInt(obj_equipo[52].toString()) > 0) {
//                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'><span onclick='RegistroFichaTecnica(" + id_equipo + ",2);' class='fas fa-plus fa-size_small'></span></td>");
                        out.print("<td colspan='10' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>CARACTERISITICAS ESPECIFICAS</td>");
                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"javascript:document.getElementById('Btn_ft_fase2').click();\"" : "style='cursor:not-allowed'") + " class='far fa-save fa-size_small'></span></td>");
                    } else {
                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='RegistroFichaTecnica(" + id_equipo + ",2);'" : "style='cursor:not-allowed'") + " class='fas fa-plus fa-size_small'></span></td>");
                        out.print("<td colspan='10' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>CARACTERISITICAS ESPECIFICAS</td>");
//                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'><span  onclick=\"javascript:document.getElementById('Btn_ft_fase2').click();\" class='far fa-save fa-size_small'></span></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:3%;font-weight:bold;text-align:center;' rowspan='2'>ITEM</td>");
                    out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>CODIGO</td>");
                    out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>NOMBRE INSTRUMENTO</td>");
                    out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>FABRICANTE</td>");
                    out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>CODIGO/MODELO</td>");
                    out.print("<td style='width:6%;font-weight:bold;text-align:center;' rowspan='2'>No SERIE</td>");
                    out.print("<td style='width:6%;font-weight:bold;text-align:center;' rowspan='2'>RANGO</td>");
                    out.print("<td style='width:18%;font-weight:bold;text-align:center;' rowspan='2'>UBICACIÓN</td>");
                    out.print("<td style='width:4%;font-weight:bold;text-align:center;'  colspan='2'>V.I</td>");
                    out.print("<td style='width:23%;font-weight:bold;text-align:center;' rowspan='2'>OBSERVACIONES</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='text-align:center;'>SI</td>");
                    out.print("<td style='text-align:center;'>NO</td>");
                    out.print("</tr>");
                    if (lst_ficha != null) {
                        for (int r = 0; r < lst_ficha.size(); r++) {
                            Object[] obj_ficha = (Object[]) lst_ficha.get(r);
                            if (id_ficha_tecnica == Integer.parseInt(obj_ficha[0].toString()) && tipo_ficha == 2) {
                                String arrg_datos[] = obj_ficha[4].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<form action='Equipo?opc=6&Id_equipo=" + id_equipo + "' method='post' id='FormCaracteristicasE'>");
                                out.print("<tr>");
                                out.print("<input type='hidden' name='Id_ficha_tecnica' value='" + obj_ficha[0] + "'>");
                                out.print("<input type='hidden' name='Txt_tipo'  value='2'>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_item' id='Txt_item' value='" + obj_ficha[3] + "' required /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_codigo' id='Txt_codigo' required value='" + arrg_datos[0] + "' /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_nombre_instrumento' id='Txt_nombre_instrumento' value='" + arrg_datos[1] + "'  required /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_fabricante' id='Txt_fabricante' value='" + arrg_datos[2] + "'  required /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_codigo_modelo' id='Txt_codigo_modelo' value='" + arrg_datos[3] + "'  required /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_serie' id='Txt_serie' required value='" + arrg_datos[4] + "'  /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_rango' id='Txt_rango' required value='" + arrg_datos[5] + "'  /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_ubicacion' id='Txt_ubicacion' value='" + arrg_datos[6] + "' required /></td>");
                                out.print("<td align='center'><input type='radio' name='Txt_verificacion' id='txt_verificacion' value='SI' " + ((arrg_datos[7].contains("SI") ? "checked" : "")) + " required /></td>");
                                out.print("<td align='center'><input type='radio' name='Txt_verificacion' id='txt_verificacion' value='NO' " + ((arrg_datos[7].contains("SI") ? "" : "checked")) + " required /></td>");
                                out.print("<td align='center'><input type='text' class='form-control' name='Txt_observacion' id='Txt_observacion' value='" + arrg_datos[8] + "'  required /></td>");
                                out.print("<div style='display:none'><input type='submit' id='Btn_ft_fase2' /></div>");
                                out.print("</tr>");
                                out.print("</form>");
                            }
                            if (obj_ficha[4].equals("N/A") && id_ficha_tecnica == 0 && tipo_ficha != 2) {
                                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                                    out.print("<form action='Equipo?opc=6&Id_equipo=" + id_equipo + "' method='post' id='FormCaracteristicasE'>");
                                    out.print("<tr>");
                                    out.print("<input type='hidden' name='Id_ficha_tecnica' value='" + obj_ficha[0] + "'>");
                                    out.print("<input type='hidden' name='Txt_tipo'  value='2'>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_item' id='Txt_item' value='" + ((obj_ficha[3] == null) ? "" : obj_ficha[3]) + "' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_codigo' id='Txt_codigo' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_nombre_instrumento' id='Txt_nombre_instrumento' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_fabricante' id='Txt_fabricante' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_codigo_modelo' id='Txt_codigo_modelo' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_serie' id='Txt_serie' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_rango' id='Txt_rango' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_ubicacion' id='Txt_ubicacion' required /></td>");
                                    out.print("<td align='center'><input type='radio' name='Txt_verificacion' id='txt_verificacion' value='SI' checked required /></td>");
                                    out.print("<td align='center'><input type='radio' name='Txt_verificacion' id='txt_verificacion' value='NO' required /></td>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_observacion' id='Txt_observacion' required /></td>");
                                    out.print("<div style='display:none'><input type='submit' id='Btn_ft_fase2' /></div>");
                                    out.print("</tr>");
                                    out.print("</form>");
                                }
                            } else if (id_ficha_tecnica != Integer.parseInt(obj_ficha[0].toString())) {
                                String arrg_datos[] = obj_ficha[4].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<tr>");
                                if (Integer.parseInt(obj_equipo[52].toString()) > 0 || Integer.parseInt(obj_equipo[51].toString()) > 0) {
                                    out.print("<td style='width:10%;' align='center'>" + obj_ficha[3] + "</td>");
                                } else {
                                    out.print("<td style='width:10%;' align='center'><a href='Equipo?opc=4&Id_equipo=" + id_equipo + "&Id_ficha_tecnica=" + obj_ficha[0] + "&Tipo_ficha=2'>" + obj_ficha[3] + "</a></td>");
                                }
                                out.print("<td align='center'>" + arrg_datos[0] + "</td>");
                                out.print("<td align='center'>" + arrg_datos[1] + "</td>");
                                out.print("<td align='center'>" + arrg_datos[2] + "</td>");
                                out.print("<td align='center'>" + arrg_datos[3] + "</td>");
                                out.print("<td align='center'>" + arrg_datos[4] + "</td>");
                                out.print("<td align='center'>" + arrg_datos[5] + "</td>");
                                out.print("<td align='center'>" + arrg_datos[6] + "</td>");
//                                out.print("<td align='center'><input type='radio' name='" + r + 1 + "' id='txt_verificacion' " + ((arrg_datos[7].contains("SI") ? "value='SI'  checked disabled" : "disabled")) + "></td>");
//                                out.print("<td align='center'><input type='radio' name='" + r + 1 + "' id='txt_verificacion' " + ((arrg_datos[7].contains("NO") ? "value='NO'  checked disabled" : "disabled")) + "></td>");
                                out.print("<td align='center'>" + ((arrg_datos[7].contains("SI") ? "X" : "")) + "</td>");
                                out.print("<td align='center'>" + ((arrg_datos[7].contains("NO") ? "X" : "")) + "</td>");
                                out.print("<td align='center'>" + arrg_datos[8] + "</td>");
                                out.print("</tr>");
                            }
                        }
                    }
                    out.print("</table>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES">
                    out.print("<table class='table2'>");
                    out.print("<tr><td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>OBSERVACIONES</td></tr>");
                    out.print("<tr><td valign='top' style='padding:20px'>" + obj_equipo[23] + "</td></tr>");
                    out.print("</table>");
                    //</editor-fold>
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="VERIFICACIONES">
                    out.print("<div class=\"card shadow mb-4\" style='width:50%;margin-top: 1.5rem!important;'>");
                    out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Verificaciones</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse\" id=\"collapseCardExample2\">");
                    out.print("<div class=\"card-body\" >");
                    out.print("<span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto")) ? "onclick='VerificarFT(" + id_equipo + ");'" : "style='cursor:not-allowed'") + " data-toggle=\"modal\" data-target=\"#VerificarFTModal\" class='fa fa-plus fa-size_small' title='Verificar Ficha Tecnica'></span><br />");
                    out.print("<table class='table2'>");
                    out.print("<tr>");
                    out.print("<th >Fecha verificación</th>");
                    out.print("<th >Responsable</th>");
                    out.print("<th >Observación</th>");
                    out.print("</tr>");
                    lst_verificacion_ft = jpacvft.Consulta_verificaciones_ft(id_equipo);
                    if (lst_verificacion_ft == null) {
                        out.print("<td colspan='3' align='center'><b class='naranja'>Sin verificaciones realizadas</b></td>");
                    } else {
                        for (int i = 0; i < lst_verificacion_ft.size(); i++) {
                            Object[] obj_verificaciones_ft = (Object[]) lst_verificacion_ft.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + obj_verificaciones_ft[4] + "</b></td>");
                            out.print("<td align='center'>" + obj_verificaciones_ft[3] + "</td>");
                            out.print("<td valign='top'>" + obj_verificaciones_ft[2] + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto")) {
                        //<editor-fold defaultstate="collapsed" desc="FORM VERIFICAR">
                        out.print("<div class=\"modal fade\" id=\"VerificarFTModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\">");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Verificar Orden de trabajo</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        out.print("<form action='Equipo?opc=8&Id_equipo=" + id_equipo + "' id='FormVerificarFT' method='post'>"
                                + "<b>Observación : </b>"
                                + "<textarea class='form-control' name='Txt_observacion' onchange='javascript:this.value=this.value.toUpperCase();' required></textarea>"
                                + "<input style='display:none' id='Btn_verificarFT' type='submit' value='Enviar'/>"
                                + "</form>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_verificarFT').click();\">Avanzar</a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //</editor-fold>
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Equipos").toString().equals("Notas")) {
                    //<editor-fold defaultstate="collapsed" desc="NOTAS">
                    id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo").toString());
                    try {
                        id_nota = Integer.parseInt(pageContext.getRequest().getAttribute("Id_nota").toString());
                    } catch (Exception e) {
                        id_nota = 0;
                    }
                    try {
                        temp1 = Integer.parseInt(pageContext.getRequest().getAttribute("temp1").toString());
                    } catch (Exception e) {
                        temp1 = 0;
                    }
                    if (temp1 == 1) {
                        out.print("<input type='hidden' id='Txt_enlace_volver' value='Reportes?opc=1' />");
                    } else {
                        out.print("<input type='hidden' id='Txt_enlace_volver' value='Equipo?opc=1' />");
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente") || nombre_rol.equals("Coordinador")) {
                        if (id_nota != 0) {
                            //<editor-fold defaultstate="collapsed" desc="Modificar">
                            lst_notas_id = jpaceqp.Notas_Id(id_nota);
                            Object[] obj_notaM = (Object[]) lst_notas_id.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Nota</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample\">");
                            out.print("<div class=\"card-body\" >");

                            out.print("<form action='Equipo?opc=11&Id_equipo=" + id_equipo + "' method='post' id='FormEquipRegister'>");
                            out.print("<input type='hidden' name='id_nota' value='" + id_nota + "'>");
                            out.print("<div style='display:flex;'>");
                            out.print("<div style='width:49%;'>");
                            out.print("<b>Fecha: </b>");
                            out.print("<input class='form-control' type='date' name='Txt_Fecha' id='Txt_Fecha' placeholder='Fecha' value='" + obj_notaM[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_Fecha');val1.add(Validate.Presence);</script>");
                            out.print("</div>");

                            out.print("<div style='width:49%;'>");
                            out.print("<b>Asunto: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_asunto' id='Txt_asunto' placeholder='Asunto' value='" + obj_notaM[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_asunto');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div style='width:100%'>");
                            out.print("<b>Observaciones: </b>");
                            out.print("<textarea name='Txt_observacion' id='small_descripcion-id'>");
                            out.print(obj_notaM[4].toString().replace("<div>", "<div contenteditable='true'>"));
                            out.print("</textarea>");
                            out.print("</div>");

                            out.print("<div style='width:100%;;margin-top:10px;float:rigth;'>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" style='cursor: pointer;' class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Equipo?opc=10&Id_equipo=" + id_equipo + "'\" style='cursor: pointer;' class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");

                            out.print("<div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                            out.print("</form>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
//</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="Registrar">
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Nota</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse\" id=\"collapseCardExample\">");
                            out.print("<div class=\"card-body\" >");

                            out.print("<form action='Equipo?opc=11&Id_equipo=" + id_equipo + "' method='post' id='FormEquipRegister'>");

                            out.print("<div style='display:flex;'>");
                            out.print("<div style='width:49%;'>");
                            out.print("<b>Fecha: </b>");
                            out.print("<input class='form-control' type='date' name='Txt_Fecha' id='Txt_Fecha' placeholder='Nombre de equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_Fecha');val1.add(Validate.Presence);</script>");
                            out.print("</div>");

                            out.print("<div style='width:49%;'>");
                            out.print("<b>Asunto: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_asunto' id='Txt_asunto' placeholder='Asunto' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_asunto');val1.add(Validate.Presence);</script>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div style='width:100%'>");
                            out.print("<b>Observaciones: </b>");
                            out.print("<textarea name='Txt_observacion' id='small_descripcion-id'><div contenteditable='true'>*</div></textarea>");
                            out.print("</div>");

                            out.print("<div style='width:100%;;margin-top:10px;float:rigth;'>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" style='cursor: pointer;' class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Equipo?opc=10&Id_equipo=" + id_equipo + "'\" style='cursor: pointer;' class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");

                            out.print("<div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                            out.print("</form>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                    }
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    lst_equipo = jpaceqp.Equipo_id(id_equipo);
                    if (lst_equipo != null) {
                        Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Notas - " + obj_equipo[1] + "</h6>");
                    }
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Fecha</th>");
                    out.print("<th style='width:20%;'>Asunto</th>");
                    out.print("<th style='width:50%;'>Descripción</th>");
                    out.print("<th>Responsable</th>");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        out.print("<th>Editar</th>");
                        out.print("<th>Estado</th>");
                    }
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_notas = jpaceqp.Notas_IdEquipo(id_equipo);
                    if (lst_notas != null) {
                        for (int i = 0; i < lst_notas.size(); i++) {
                            Object[] obj_notas = (Object[]) lst_notas.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_notas[2] + "</td>");
                            out.print("<td>" + obj_notas[3] + "</td>");
                            out.print("<td>" + obj_notas[4] + "</td>");
                            out.print("<td>" + obj_notas[7] + "</td>");
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                                out.print("<td align='center'><span onclick=\"location.href='Equipo?opc=10&Id_equipo=" + id_equipo + "&id_nota=" + obj_notas[0] + "'\" class='fa fa-pen fa-size_small' title='Editar Nota'></span></td>");
                                out.print("<td align='center'><span onclick='Inactivar(" + id_equipo + "," + obj_notas[0] + ")' class='fa fa-check fa-size_small' style='color:green' title='Inactivar Nota'></span></td>");
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_equipos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
