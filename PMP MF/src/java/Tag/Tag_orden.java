package Tag;

import Controladores.EquipoJpaController;
import Controladores.FichaTecnicaJpaController;
import Controladores.GeneralJpaController;
import Controladores.InstruccionJpaController;
import Controladores.OrdenTrabajoDetalleJpaController;
import Controladores.OrdenTrabajoJpaController;
import Controladores.ParametroGeneralJpaController;
import Controladores.ParametroJpaController;
import Controladores.ParametroZonaJpaController;
import Controladores.RegistroJpaController;
import Controladores.TipoMttoJpaController;
import Controladores.UsuarioJpaController;
import Controladores.ZonaJpaController;
import Metodos.Connection_mysql_sirh;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_orden extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            ///JPAC
            EquipoJpaController jpaceqp = new EquipoJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            OrdenTrabajoJpaController jpacotb = new OrdenTrabajoJpaController();
            TipoMttoJpaController jpactmt = new TipoMttoJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            ParametroGeneralJpaController jpacpgn = new ParametroGeneralJpaController();
            ParametroZonaJpaController jpacpzn = new ParametroZonaJpaController();
            OrdenTrabajoDetalleJpaController jpacotd = new OrdenTrabajoDetalleJpaController();
            ZonaJpaController jpaczna = new ZonaJpaController();
            GeneralJpaController jpacgnr = new GeneralJpaController();
            FichaTecnicaJpaController jpacftn = new FichaTecnicaJpaController();
            InstruccionJpaController jpacitc = new InstruccionJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            Connection_mysql_sirh mtdcms = new Connection_mysql_sirh();
            ///VARIABLES
            String registro = "";
            String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
            String version = "";
            String titulo = "";
            String titulo2 = "";
            String instrucciones = "";
            String nota = "";
            String arg_actividades_id = "";
            String arg_actividades = "";
            String arg_actividades_pendientes = "";
            String arg_grupos = "";
            String[] arg_grupos_ot = null;
            String[] arg_actividades_ot = null;
            int cont_actividades_programadas = 0;
            int id_orden = 0;
            int id_origen = 0;
            int estado_ot = 0;
            int documento = 0;
            int id_equipo = 0;
            int id_tipo_mtto = 0;
            int id_tipo_equipo = 0;
            List lst_ordenes = null;
            List lst_instruccion_seguridad = null;
            List lst_ficha = null;
            List lst_registro = null;
            List lst_orden_ot = null;
            List lst_orden = null;
            List lst_origen = null;
            List lst_origenes = null;
            List lst_orden_detalle = null;
            List lst_equipo = null;
            List lst_grupos_parametros = null;
            List lst_parametros = null;
            List lst_tipo_mtto = null;
            List lst_usuarios = null;
            List lst_actividades_pedientes = null;
            List lst_numero_orden = null;
            if (pageContext.getRequest().getAttribute("Orden") != null) {
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_equipo")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_equipo">
                    id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_equipo").toString());
                    id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden").toString());
                    lst_equipo = jpaceqp.Equipo_id(id_equipo);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Equipo?opc=1' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Orden de trabajo">
                        if (id_orden > 0) {
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                                //<editor-fold defaultstate="collapsed" desc="MODIFICAR ORDEN TRABAJO ROL">
                                lst_orden = jpacotb.Orden_trabajo_equipo_id(id_orden);
                                Object[] obj_orden = (Object[]) lst_orden.get(0);
                                String[] arg_registros_equipo = obj_equipo[28].toString().split(",");
                                out.print("<div class=\"card shadow mb-4\">");
                                out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                                out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Orden de trabajo</h6>");
                                out.print("</a>");
                                out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                                out.print("<div class=\"card-body\">");
                                out.print("<form action='Orden?opc=2&Id_equipo=" + id_equipo + "&Id_orden=" + id_orden + "' method='post' id='FormOrderUpdate'>");
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
                                        out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[6].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + ">" + obj_usuarios[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ejecutor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                if (Integer.parseInt(obj_orden[14].toString()) == 1) {
                                    out.print("<b>Tipo de mtto: </b>");
                                    out.print("<select class='form-control' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto'>");
                                    out.print("<option value='" + obj_orden[2] + "' >" + (Integer.parseInt(obj_orden[2].toString()) == 1 ? "MM Mtto Mayor" : "INSP Inspección") + "</option>");
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
                                }
                                out.print("<span onclick=\"javascript:document.getElementById('Btn_accionO').click();\" class='far fa-edit fa-size_small naranja' title='Registrar'></span>&nbsp;&nbsp;");
                                out.print("<span onclick=\"javascript:location.href='Equipo?opc=1'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                                out.print("</div>");
                                out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                                out.print("<b>Numero OT: </b>");
                                out.print("<input class='form-control' type='text' name='Txt_numero_ot' id='Txt_numero_ot' placeholder='Numero OT' value='" + obj_orden[1] + "' readonly/>");
                                out.print("<b>Programador: </b>");
                                out.print("<input class='form-control' type='text' name='Txt_programador' id='Txt_programador' placeholder='Programador' value='" + obj_orden[4] + "' readonly/>");
                                id_equipo = Integer.parseInt(obj_orden[3].toString());
                                if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                                    out.print("<b>Aprovador: </b>");
                                    out.print("<select class='form-control' name='Cbx_aprobador' id='Cbx_aprobador'>");
                                    out.print("<option value='0' >Seleccionar Revisor</option>");
                                    lst_usuarios = jpacusa.Usuarios_rol(3);
                                    if (lst_usuarios != null) {
                                        for (int j = 0; j < lst_usuarios.size(); j++) {
                                            Object[] obj_usuarios = (Object[]) lst_usuarios.get(j);
                                            out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[8].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + " >" + obj_usuarios[1] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_aprobador');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                } else {
                                    out.print("<b>Revisor: </b>");
                                    out.print("<select class='form-control' name='Cbx_revisor' id='Cbx_revisor'>");
                                    out.print("<option value='0' >Seleccionar Revisor</option>");
                                    lst_usuarios = jpacusa.Usuarios_rol(6);
                                    if (lst_usuarios != null) {
                                        for (int i = 0; i < lst_usuarios.size(); i++) {
                                            Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                            out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[8].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + " >" + obj_usuarios[1] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_revisor');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                }
                                out.print("<b>Tipo de mtto: </b>");
                                lst_tipo_mtto = jpactmt.Tipos_mtto_estado((Integer) obj_equipo[2]);
                                if (lst_tipo_mtto != null) {
                                    for (int i = 0; i < lst_tipo_mtto.size(); i++) {
                                        Object[] obj_tipo_mtto = (Object[]) lst_tipo_mtto.get(i);
                                        out.print("" + ((Integer.parseInt(obj_orden[2].toString()) == Integer.parseInt(obj_tipo_mtto[0].toString())) ? obj_tipo_mtto[1] + " " + obj_tipo_mtto[2] : "") + "");
                                    }
                                }
                                if (Integer.parseInt(obj_orden[14].toString()) >= 2) {
                                    out.print("<input type='hidden' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto' value='" + obj_orden[2] + "' />");
                                }
                                out.print("</div>");
                                out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionO' /></div>");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold> 
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="MODIFICAR ORDEN TRABAJO">
                                lst_orden = jpacotb.Orden_trabajo_equipo_id(id_orden);
                                Object[] obj_orden = (Object[]) lst_orden.get(0);
                                out.print("<div class=\"card shadow mb-4\">");
                                out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                                out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Orden de trabajo</h6>");
                                out.print("</a>");
                                out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                                out.print("<div class=\"card-body\">");
                                out.print("<form action='Orden?opc=2&Id_equipo=" + id_equipo + "&Id_orden=" + id_orden + "' method='post' id='FormOrderUpdate'>");
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
                                        out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[6].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + ">" + obj_usuarios[1] + "</option>");
                                    }
                                }
                                out.print("</select>"
                                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ejecutor');"
                                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                out.print("<span onclick=\"javascript:document.getElementById('Btn_accionO').click();\" class='far fa-edit fa-size_small naranja' title='Registrar'></span>&nbsp;&nbsp;");
                                out.print("<span onclick=\"javascript:location.href='Equipo?opc=1'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                                out.print("</div>");
                                out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                                out.print("<b>Numero OT: </b>");
                                out.print("<input class='form-control' type='text' name='Txt_numero_ot' id='Txt_numero_ot' placeholder='Numero OT' value='" + obj_orden[1] + "' readonly/>");
                                out.print("<b>Programador: </b>");
                                out.print("<input class='form-control' type='text' name='Txt_programador' id='Txt_programador' placeholder='Programador' value='" + obj_orden[4] + "' readonly/>");
                                id_equipo = Integer.parseInt(obj_orden[3].toString());
                                if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                                    out.print("<b>Aprovador: </b>");
                                    out.print("<select class='form-control' name='Cbx_aprobador' id='Cbx_aprobador'>");
                                    out.print("<option value='0' >Seleccionar Revisor</option>");
                                    lst_usuarios = jpacusa.Usuarios_rol(3);
                                    if (lst_usuarios != null) {
                                        for (int j = 0; j < lst_usuarios.size(); j++) {
                                            Object[] obj_usuarios = (Object[]) lst_usuarios.get(j);
                                            out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[8].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + " >" + obj_usuarios[1] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_aprobador');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                } else {
                                    out.print("<b>Revisor: </b>");
                                    out.print("<select class='form-control' name='Cbx_revisor' id='Cbx_revisor'>");
                                    out.print("<option value='0' >Seleccionar Revisor</option>");
                                    lst_usuarios = jpacusa.Usuarios_rol(6);
                                    if (lst_usuarios != null) {
                                        for (int i = 0; i < lst_usuarios.size(); i++) {
                                            Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                            out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[8].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + " >" + obj_usuarios[1] + "</option>");
                                        }
                                    }
                                    out.print("</select>"
                                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_revisor');"
                                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                                }
                                out.print("<b>Tipo de mtto: </b>");
                                lst_tipo_mtto = jpactmt.Tipos_mtto_estado((Integer) obj_equipo[2]);
                                if (lst_tipo_mtto != null) {
                                    for (int i = 0; i < lst_tipo_mtto.size(); i++) {
                                        Object[] obj_tipo_mtto = (Object[]) lst_tipo_mtto.get(i);
                                        out.print("" + ((Integer.parseInt(obj_orden[2].toString()) == Integer.parseInt(obj_tipo_mtto[0].toString())) ? obj_tipo_mtto[1] + " " + obj_tipo_mtto[2] : "") + "");
                                    }
                                }
                                out.print("<input type='hidden' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto' value='" + obj_orden[2] + "' />");
                                out.print("</div>");
                                out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionO' /></div>");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                        }
//</editor-fold>
                    }
                    try {
                        documento = Integer.parseInt(pageContext.getRequest().getAttribute("Documento").toString());
                    } catch (Exception e) {
                        documento = 0;
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Actualizar fecha programada">
                        out.print("<div class=\"modal fade\" id=\"AjusteFechaProg\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\">");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"AjusteFechaProg\">Actualizar fecha programación</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        out.print("<form action='Orden?opc=17' method='post'>");
                        out.print("<input type='hidden' name='Txt_id' id='Txt_id'>");
                        out.print("<input type='hidden' name='Equipo' id='Equipo'>");
                        out.print("<b>Numero OT: </b><input class='form-control' type='text' id='Txt_orden' readonly />");
                        out.print("<b>Firmar MTF </b><input class='form-control' type='text' name='Txt_documento_mtf' id='Txt_documento_mtf' required/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento_mtf');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                        out.print("<b>Firmar PRF </b><input class='form-control' type='text' name='Txt_documento_prf' id='Txt_documento_prf' required/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento_prf');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                        out.print("<b>Justificación:</b><textarea class='form-control' name='Txt_justificacion' id='Txt_justificacion' style='width:90%;' placeholder='Observación' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>");
                        out.print("<script type='text/javascript'>var val1 = new LiveValidation('Txt_justificacion');val1.add(Validate.Presence);</script>");
                        out.print("<div style='display:none'><input type='submit' id='Btn_fecha_old' /></div>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_fecha_old').click();\">Autorizar</a>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="Consulta">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Ordenes de trabajo " + obj_equipo[1] + "</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>No.</th>");
                    out.print("<th style='width:5px'>Tipo Mtto</th>");
                    out.print("<th style='width:5px'>Semana</th>");
                    out.print("<th>Programador</th>");
                    out.print("<th>Ejecutor</th>");
                    out.print("<th>Revisor</th>");
                    out.print("<th style='width:40%'>Estado</th>");
                    out.print("<th style='width:5px'>Ver</th>");
                    out.print("<th style='width:5px'>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>No.</th>");
                    out.print("<th style='width:5px'>Tipo Mtto</th>");
                    out.print("<th style='width:5px'>Semana</th>");
                    out.print("<th>Programador</th>");
                    out.print("<th>Ejecutor</th>");
                    if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                        out.print("<th>Aprovador</th>");
                    } else {
                        out.print("<th>Revisor</th>");
                    }
                    out.print("<th style='width:40%'>Estado</th>");
                    out.print("<th style='width:5px'>Ver</th>");
                    out.print("<th style='width:5px'>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_ordenes = jpacotb.Orden_trabajo_equipo_id_equipo(id_equipo);
                    if (lst_ordenes != null) {
                        for (int i = 0; i < lst_ordenes.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                            estado_ot = Integer.parseInt(obj_ordenes[14].toString());
                            out.print("<tr>");
                            out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + " align='center'>" + obj_ordenes[1] + "</td>");
                            out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + ">" + obj_ordenes[18] + "</td>");
                            out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + ">" + obj_ordenes[19] + "</td>");
                            out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + ">" + obj_ordenes[4] + "</td>");
                            out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + ">" + obj_ordenes[6] + "</td>");
                            if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                                out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + ">" + obj_ordenes[36] + "</td>");
                            } else {
                                out.print("<td " + ((obj_ordenes[35] != null) ? "style='color:red; font-weight:bold;'" : "") + ">" + obj_ordenes[8] + "</td>");
                            }
                            out.print("<td>");
                            if (obj_ordenes[35] != null) {
                                //<editor-fold defaultstate="collapsed" desc="JUSTIFICACION OT NO EJECUTADA">
                                out.print("<center><b>ORDEN DE TRABAJO JUSTIFICADA</b><br>");
                                out.print("<b>JUSTIFICACION: </b><div class='tooltip_r'><b style='color:red;'> " + obj_ordenes[35] + "</b>");
                                out.print("<span class='tooltip_rtext'>");
                                String[] arr_documento = obj_ordenes[34].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                for (int j = 0; j < arr_documento.length; j++) {
                                    List lst_empleado = mtdcms.Empleado_sirh(Integer.parseInt(arr_documento[j]) + "");
                                    if (lst_empleado != null) {
                                        String nombre_personal = lst_empleado.toString();
                                        if (j == 0) {
                                            out.print("<b>FIRMA MANTENIMIENTO: </b><br>" + nombre_personal.replace("[", "").replace("]", "").replace("/", "Cod: ") + "<br>");
                                        } else {
                                            out.print("<b>FIRMA PRODUCCIÓN: </b><br>" + nombre_personal.replace("[", "").replace("]", "").replace("/", "Cod: ") + "");
                                        }
                                    } else {
                                        out.print("N/A");
                                    }
                                }
                                out.print("</span></div></center>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="progress_bar">
                                out.print("<section class='progress_bar'>");
                                out.print("<ol class='progress_bar'>");
                                out.print("<li class='is-complete" + ((estado_ot == 1) ? " is-hovered" : "") + "'><span>Programado</span></li>");
                                out.print("<li class='" + ((estado_ot >= 2) ? "is-complete" : "") + ((estado_ot == 2) ? " is-hovered" : "") + "'><span>Emitido</span>" + ((estado_ot == 1) ? "<span class='has-changes'></span>" : "") + "</li>");
                                out.print("<li class='" + ((estado_ot >= 3) ? "is-complete" : "") + ((estado_ot == 3) ? " is-hovered" : "") + "'><span>Ejecutado</span>" + ((estado_ot == 2) ? "<span class='has-changes'></span>" : "") + "</li>");
                                out.print("<li class='" + ((estado_ot >= 4) ? "is-complete" : "") + ((estado_ot == 4) ? " is-hovered" : "") + "'><span>Verificado</span>" + ((estado_ot == 3) ? "<span class='has-changes'></span>" : "") + "</li>");
                                out.print("<li class='" + ((estado_ot >= 5) ? "is-complete" : "") + ((estado_ot == 5) ? " is-hovered" : "") + "'><span>Recibido</span>" + ((estado_ot == 4) ? "<span class='has-changes'></span>" : "") + "</li>");
                                out.print("<li class='" + ((estado_ot >= 6) ? "is-complete" : "") + ((estado_ot == 6) ? " is-hovered" : "") + "'><span>Cerrado</span>" + ((estado_ot == 5) ? "<span class='has-changes2'></span>" : "") + "</li>");
                                out.print("</ol>");
                                out.print("</section>");
//</editor-fold> 
                            }
                            out.print("</td>");
                            out.print("<td align='center'><span onclick=\"location.href='Orden?opc=" + ((estado_ot > 1) ? 5 : 3) + "&Id_orden=" + obj_ordenes[0] + "'\" class='far fa-file-alt fa-size_small' title='Ver OT'></span></td>");
                            if (obj_ordenes[35] != null) {
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' style='color:#b9b9b9;' title='Actualizar'></span>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Orden?opc=1&Id_equipo=" + id_equipo + "&Id_orden=" + obj_ordenes[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Actualizar'></span>");
                                if (estado_ot <= 2) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-calendar fa-size_small' title='Fecha old' data-toggle=\"modal\" data-target=\"#AjusteFechaProg\" " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActualizarFechasProg(" + obj_ordenes[0] + "," + obj_ordenes[1] + "," + id_equipo + ");'" : "style='cursor:not-allowed'") + "></span></td>");
                                }
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
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_zona_general")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_zona_general">
                    id_tipo_mtto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_mtto").toString());
                    id_origen = Integer.parseInt(pageContext.getRequest().getAttribute("Id_origen").toString());
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Orden de trabajo">
                        if (id_origen > 0) {
                            if (id_tipo_mtto == 3) {
                                lst_origen = jpaczna.Zona_id(id_origen);
                            } else {
                                lst_origen = jpacgnr.Actividad_general_id(id_origen);
                            }
                            Object[] obj_origen = (Object[]) lst_origen.get(0);
                            lst_numero_orden = jpacotb.Numero_ot();
                            Object[] obj_numero_orden = (Object[]) lst_numero_orden.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Orden de trabajo " + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + "</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Orden?opc=9&Id_origen=" + id_origen + "' method='post' id='FormOrderRegister'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>" + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + ": </b>");
                            out.print("<input class='form-control' type='text' value='" + obj_origen[1] + "' readonly/>");
                            out.print("<b>Programador: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_programador' id='Txt_programador' placeholder='Programador' value='" + nombre_usuario + "' readonly/>");
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
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionO').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Orden?opc=8&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                            out.print("<b>Numero OT: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_numero_ot' id='Txt_numero_ot' placeholder='Numero OT' value='" + obj_numero_orden[1] + "' readonly/>");
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
                            out.print("<b>Tipo de mtto: </b>" + ((id_tipo_mtto == 3) ? "INSP_LIMP" : "GENERAL") + "");
                            out.print("<input hidden='hidden' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto' value='" + id_tipo_mtto + "' />");
                            out.print("<input hidden='hidden' name='Id_orden' id='Id_orden' value='0' />");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionO' /></div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="Consulta ">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Orden de Trabajo " + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + "</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>Semaforo</th>");
                    out.print("<th>" + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + "</th>");
                    out.print("<th style='width:" + ((id_tipo_mtto == 3) ? "50%" : "20%") + "'>" + ((id_tipo_mtto == 3) ? "Lineas" : "Descripcion") + "</th>");
                    out.print("<th colspan='3'>OT</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>Semaforo</th>");
                    out.print("<th>" + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + "</th>");
                    out.print("<th style='width:" + ((id_tipo_mtto == 3) ? "50%" : "20%") + "'>" + ((id_tipo_mtto == 3) ? "Lineas" : "Descripcion") + "</th>");
                    out.print("<th colspan='3'>OT</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    if (id_tipo_mtto == 3) {
                        lst_origenes = jpaczna.Zonas_ot();
                    } else {
                        lst_origenes = jpacgnr.Actividades_generales_ot();
                    }
                    if (lst_origenes != null) {
                        for (int i = 0; i < lst_origenes.size(); i++) {
                            Object[] obj_origenes = (Object[]) lst_origenes.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><i style='color:#fff;display:none'>" + obj_origenes[14] + "</i><span class='fas fa-circle fa-size_small " + obj_origenes[14].toString().replace("3", "").replace("2", "").replace("1", "").replace("0", "") + "'></span></td>");
                            out.print("<td>" + obj_origenes[1] + "</td>");
                            out.print("<td>" + ((id_tipo_mtto == 3) ? ((obj_origenes[2] == null) ? "<b class='naranja'>La zona no tiene lineas asignadas.</b>" : obj_origenes[2]) : obj_origenes[2]) + "</td>");
                            if (obj_origenes[8] != null) {
                                out.print("<td><b>Ultima OT :</b><a href='Orden?opc=13&Id_orden=" + obj_origenes[11] + "&Id_tipo_mtto=" + obj_origenes[17] + "'>" + obj_origenes[8] + "<b></a> | Fecha :</b>" + obj_origenes[9] + "<b> | Proxima :</b>" + obj_origenes[12] + "</td>");
                            } else {
                                out.print("<td><b class='naranja'>No se encuentran OT en el sistema</b></td>");
                            }
                            if (Integer.parseInt(obj_origenes[5].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Orden?opc=8&Id_origen=" + obj_origenes[0] + "&Id_tipo_mtto=" + id_tipo_mtto + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-plus fa-size_small' title='Nueva OT'></span></td>");
                            } else {
                                out.print("<td align='center'><span class='fa fa-plus fa-size_small' title='Nueva OT inhabilitado'></span></td>");
                            }
                            out.print("<td align='center'><span onclick=\"location.href='Orden?opc=10&Id_origen=" + obj_origenes[0] + "&Id_tipo_mtto=" + id_tipo_mtto + "'\" class='fa fa-bars fa-size_small' title='Ver OT'></span></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_zona_general_historial")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_zona_general_historial">
                    id_tipo_mtto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_mtto").toString());
                    id_origen = Integer.parseInt(pageContext.getRequest().getAttribute("Id_origen").toString());
                    id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden").toString());
                    if (id_tipo_mtto == 3) {
                        lst_origen = jpaczna.Zona_id(id_origen);
                        lst_orden = jpacotb.Orden_trabajo_zona_id(id_orden);
                        lst_ordenes = jpacotb.Orden_trabajo_zona_id_zona(id_origen);
                    } else {
                        lst_origen = jpacgnr.Actividad_general_id(id_origen);
                        lst_orden = jpacotb.Orden_trabajo_general_id(id_orden);
                        lst_ordenes = jpacotb.Orden_trabajo_general_id_general(id_origen);
                    }
                    Object[] obj_origen = (Object[]) lst_origen.get(0);
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Orden?opc=8&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=0' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Orden de trabajo">
                        if (id_orden > 0) {
                            Object[] obj_orden = (Object[]) lst_orden.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Actualizar Orden de trabajo " + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + "</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Orden?opc=9&Id_origen=" + id_origen + "' method='post' id='FormOrderUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>" + ((id_tipo_mtto == 3) ? "Zona" : "Actividad General") + ": </b>");
                            out.print("<input class='form-control' type='text' value='" + obj_origen[1] + "' readonly/>");
                            out.print("<b>Programador: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_programador' id='Txt_programador' placeholder='Programador' value='" + nombre_usuario + "' readonly/>");
                            out.print("<b>Tecnico Revisor: </b>");
                            out.print("<select class='form-control' name='Cbx_revisor' id='Cbx_revisor'>");
                            out.print("<option value='0' >Seleccionar Revisor</option>");
                            lst_usuarios = jpacusa.Usuarios_rol(6);
                            if (lst_usuarios != null) {
                                for (int i = 0; i < lst_usuarios.size(); i++) {
                                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                    out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[8].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + ">" + obj_usuarios[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_revisor');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Fecha Programación: </b>");
                            out.print("<input class='form-control' name='Txt_fecha_prog' type='text' id='datepicker' value='" + obj_orden[5] + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
//                            out.print("<input class='form-control' type='text' id='datepicker' value='" + obj_orden[5] + "' />"
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionO').click();\" class='far fa-check-circle fa-size_small verde' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Orden?opc=8&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                            out.print("<b>Numero OT: </b>");
                            out.print("<input class='form-control' type='text' name='Txt_numero_ot' id='Txt_numero_ot' placeholder='Numero OT' value='" + obj_orden[1] + "' readonly/>");
                            out.print("<b>Tecnico Ejecutor: </b>");
                            out.print("<select class='form-control' name='Cbx_ejecutor' id='Cbx_ejecutor'>");
                            out.print("<option value='0' >Seleccionar Ejecutor</option>");
                            lst_usuarios = jpacusa.Usuarios_rol(5);
                            if (lst_usuarios != null) {
                                for (int i = 0; i < lst_usuarios.size(); i++) {
                                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                    out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[6].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + ">" + obj_usuarios[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_ejecutor');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Tecnico Aprobador: </b>");
                            out.print("<select class='form-control' name='Cbx_aprobador' id='Cbx_aprobador'>");
                            out.print("<option value='0' >Seleccionar Aprobador</option>");
                            lst_usuarios = jpacusa.Usuarios_rol(3);
                            if (lst_usuarios != null) {
                                for (int i = 0; i < lst_usuarios.size(); i++) {
                                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                                    out.print("<option value='" + obj_usuarios[1] + "' " + ((obj_orden[20].toString().equals(obj_usuarios[1].toString())) ? "selected" : "") + ">" + obj_usuarios[1] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_aprobador');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Tipo de mtto: </b>" + ((id_tipo_mtto == 3) ? "INSP_LIMP" : "GENERAL") + "");
                            out.print("<input hidden='hidden' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto' value='" + id_tipo_mtto + "' />");
                            out.print("<input hidden='hidden' name='Id_orden' id='Id_orden' value='" + obj_orden[0] + "' />");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionO' /></div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="Consulta">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Ordenes de trabajo " + obj_origen[1] + "</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>No.</th>");
                    out.print("<th style='width:5px'>Tipo Mtto</th>");
                    out.print("<th style='width:5px'>Semana</th>");
                    out.print("<th>Programador</th>");
                    out.print("<th>Ejecutor</th>");
                    out.print("<th>Revisor</th>");
                    out.print("<th>Aprobador</th>");
                    out.print("<th style='width:40%'>Estado</th>");
                    out.print("<th style='width:5px'>Ver</th>");
                    out.print("<th style='width:5px'>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th style='width:5px'>No.</th>");
                    out.print("<th style='width:5px'>Tipo Mtto</th>");
                    out.print("<th style='width:5px'>Semana</th>");
                    out.print("<th>Programador</th>");
                    out.print("<th>Ejecutor</th>");
                    out.print("<th>Revisor</th>");
                    out.print("<th>Aprobador</th>");
                    out.print("<th style='width:40%'>Estado</th>");
                    out.print("<th style='width:5px'>Ver</th>");
                    out.print("<th style='width:5px'>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    if (lst_ordenes != null) {
                        for (int i = 0; i < lst_ordenes.size(); i++) {
                            Object[] obj_ordenes = (Object[]) lst_ordenes.get(i);
                            estado_ot = Integer.parseInt(obj_ordenes[14].toString());
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_ordenes[1] + "</td>");
                            out.print("<td>" + obj_ordenes[18] + "</td>");
                            out.print("<td>" + obj_ordenes[19] + "</td>");
                            out.print("<td>" + obj_ordenes[4] + "</td>");
                            out.print("<td>" + obj_ordenes[6] + "</td>");
                            out.print("<td>" + obj_ordenes[8] + "</td>");
                            out.print("<td>" + obj_ordenes[20] + "</td>");
                            out.print("<td>");
                            //<editor-fold defaultstate="collapsed" desc="progress_bar">
                            out.print("<section class='progress_bar'>");
                            out.print("<ol class='progress_bar'>");
                            out.print("<li class='is-complete" + ((estado_ot == 1) ? " is-hovered" : "") + "'><span>Programado</span></li>");
                            out.print("<li class='" + ((estado_ot >= 2) ? "is-complete" : "") + ((estado_ot == 2) ? " is-hovered" : "") + "'><span>Emitido</span>" + ((estado_ot == 1) ? "<span class='has-changes'></span>" : "") + "</li>");
                            out.print("<li class='" + ((estado_ot >= 3) ? "is-complete" : "") + ((estado_ot == 3) ? " is-hovered" : "") + "'><span>Ejecutado</span>" + ((estado_ot == 2) ? "<span class='has-changes'></span>" : "") + "</li>");
                            out.print("<li class='" + ((estado_ot >= 4) ? "is-complete" : "") + ((estado_ot == 4) ? " is-hovered" : "") + "'><span>Revisado</span>" + ((estado_ot == 3) ? "<span class='has-changes'></span>" : "") + "</li>");
                            out.print("<li class='" + ((estado_ot >= 6) ? "is-complete" : "") + ((estado_ot == 6) ? " is-hovered" : "") + "'><span>Aprobado</span>" + ((estado_ot >= 4 && estado_ot < 6) ? "<span class='has-changes2'></span>" : "") + "</li>");
                            out.print("</ol>");
                            out.print("</section>");
//</editor-fold>
                            out.print("</td>");
                            out.print("<td align='center'><span onclick=\"location.href='Orden?opc=" + ((estado_ot > 1) ? 13 : 11) + "&Id_orden=" + obj_ordenes[0] + "&Id_tipo_mtto=" + id_tipo_mtto + "'\" class='far fa-file-alt fa-size_small' title='Ver OT'></span></td>");
                            out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Orden?opc=10&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=" + id_origen + "&Id_orden=" + obj_ordenes[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Actualizar'></span></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_equipo_trabajo_emision")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_equipo_trabajo_emision MM Y INSP">
                    id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden").toString());
                    lst_orden = jpacotb.Orden_trabajo_equipo_id(id_orden);
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    id_tipo_mtto = Integer.parseInt(obj_orden[2].toString());
                    id_equipo = Integer.parseInt(obj_orden[3].toString());
                    lst_equipo = jpaceqp.Equipo_id(id_equipo);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    estado_ot = Integer.parseInt(obj_orden[14].toString());
                    id_tipo_equipo = Integer.parseInt(obj_orden[26].toString());
                    registro = ((id_tipo_mtto == 1) ? obj_orden[30].toString() : ((id_tipo_mtto == 2) ? obj_orden[31].toString() : obj_orden[32].toString()));
                    lst_registro = jpacrgt.Traer_registro_ot(registro, obj_orden[5].toString());
                    if (lst_registro != null) {
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        registro = obj_registro[1].toString();
                        version = obj_registro[3].toString();
                        titulo = obj_registro[2].toString();
                        titulo2 = ((id_tipo_mtto == 1) ? "Listado de actividades de Mtto mayor" : ((id_tipo_mtto == 2) ? "Listado de actividades de inspección" : "Listado de actividades de lubricación"));
                        nota = obj_registro[4].toString();
                        instrucciones = obj_registro[5].toString();
                    } else {
                        registro = registro;
                        version = "N/A";
                        titulo = "N/A";
                        titulo2 = "N/A";
                        nota = "N/A";
                        instrucciones = "N/A";
                    }
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Orden?opc=1&Id_equipo=" + id_equipo + "' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        out.print("<div style='float:right'><span class='fas fa-ellipsis-h fa-size_small' data-toggle=\"modal\" data-target=\"#ProgressBarModal\"></span></div>");
                    }
                    if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                        //<editor-fold defaultstate="collapsed" desc="AUTOCLAVE MM E INPS">
                        //<editor-fold defaultstate="collapsed" desc="CABECERA">
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                        out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                        out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                        out.print("<td align='center' style='width:50%;'>CODIGO " + registro + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                        out.print("<td align='center'>VERSIÓN: " + version + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DETALLE CABECERA">
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr>");
                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' colspan='6' align='center'>" + titulo2.toUpperCase() + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>SEMANA</th>");
                        out.print("<td style='width:15%;' align='center'><b>" + obj_orden[19] + "</b></td>");
                        out.print("<th>FECHA DE EJECUCIÓN</th>");
                        out.print("<td align='center'>AAAA-MM-DD</td>");
                        out.print("<th>O.T No :</th>");
                        out.print("<td align='center'><b>" + obj_orden[1] + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>EJECUTO</th>");
                        out.print("<td colspan='3' align='center'>" + obj_orden[6] + "</td>");
                        out.print("<th>T.T. MIN:</th>");
                        out.print("<td align='center'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>EQUIPO AUTOCLAVE</th>");
                        out.print("<td colspan='5' align='center'>" + obj_orden[24] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='6'><b>NOTA IMPORTANTE :</b>" + nota + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
                        if (id_tipo_mtto == 2) {
                            //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                            out.print("<table class=\"table2\">");
                            out.print("<tr>");
                            out.print("<td rowspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>SISTEMA ELECTRICO/ MECANICO</td>");
                            out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>EJECUTO</td>");
                            if (id_tipo_mtto > 1) {
                                out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>Observaciones</td>");
                            }
                            out.print("</tr>");
                            lst_grupos_parametros = jpacprm.Grupos_parametro_tipo(id_tipo_mtto, id_tipo_equipo);
                            for (int i = 0; i < lst_grupos_parametros.size(); i++) {
                                Object[] obj_grupos_parametro = (Object[]) lst_grupos_parametros.get(i);
                                lst_parametros = jpacprm.Parametros_grupo(id_tipo_mtto, id_tipo_equipo, obj_grupos_parametro[0].toString());
                                if (lst_parametros != null) {
                                    arg_grupos += "[" + obj_grupos_parametro[0] + "]";
                                    out.print("<tr>");
                                    if (!obj_grupos_parametro[0].equals("SISTEMA ELECTRICO/ MECANICO")) {
                                        out.print("<th>" + obj_grupos_parametro[0] + "</th>");
                                    }
                                    out.print("<th>SI</th>");
                                    out.print("<th>NO</th>");
                                    out.print("<th>N/A</th>");
                                    if (id_tipo_mtto > 1) {
                                        out.print("<th></th>");
                                    }
                                    out.print("</tr>");
                                    for (int j = 0; j < lst_parametros.size(); j++) {
                                        Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                                        arg_actividades += "[" + obj_parametros[0] + "_/_NO_/_N/A]";
                                        if (i == 0 && j == 0) {
                                            arg_actividades_id = "" + obj_parametros[0] + "";
                                        } else {
                                            arg_actividades_id = arg_actividades_id + "," + obj_parametros[0] + "";
                                        }
                                        cont_actividades_programadas++;
                                        out.print("<tr>");
                                        out.print("<td>" + obj_parametros[2] + "</td>");
                                        out.print("<td><b></b></td>");
                                        out.print("<td><b></b></td>");
                                        out.print("<td><b></b></td>");
                                        if (id_tipo_mtto > 1) {
                                            out.print("<td><b></b></td>");
                                        }
                                        out.print("</tr>");
                                    }
                                }
                            }
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REVISIÓN">
                            out.print("</table>");
                            out.print("<table class='table2'>");
                            out.print("<tr>");
                            out.print("<td aling='center' style='height:108px;'><i>___________________________________________</i><br /><b>VB. JEFE DE MANTENIMIENTO</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>    
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                            out.print("<table class=\"table2\">");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;'>DESCRIPCIÓN DE LA ACTIVIDAD A REALIZAR</td>");
                            out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>EJECUTO</td>");
                            if (id_tipo_mtto > 1) {
                                out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>Observaciones</td>");
                            }
                            out.print("</tr>");
                            lst_grupos_parametros = jpacprm.Grupos_parametro_tipo(id_tipo_mtto, id_tipo_equipo);
                            for (int i = 0; i < lst_grupos_parametros.size(); i++) {
                                Object[] obj_grupos_parametro = (Object[]) lst_grupos_parametros.get(i);
                                lst_parametros = jpacprm.Parametros_grupo(id_tipo_mtto, id_tipo_equipo, obj_grupos_parametro[0].toString());
                                if (lst_parametros != null) {
                                    arg_grupos += "[" + obj_grupos_parametro[0] + "]";
                                    out.print("<tr>");
                                    out.print("<th>ITEM</th>");
                                    out.print("<th>" + obj_grupos_parametro[0] + "</th>");
                                    out.print("<th>SI</th>");
                                    out.print("<th>NO</th>");
                                    out.print("<th>N/A</th>");
                                    if (id_tipo_mtto > 1) {
                                        out.print("<th></th>");
                                    }
                                    out.print("</tr>");
                                    for (int j = 0; j < lst_parametros.size(); j++) {
                                        Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                                        arg_actividades += "[" + obj_parametros[0] + "_/_NO_/_N/A]";
                                        if (i == 0 && j == 0) {
                                            arg_actividades_id = "" + obj_parametros[0] + "";
                                        } else {
                                            arg_actividades_id = arg_actividades_id + "," + obj_parametros[0] + "";
                                        }
                                        cont_actividades_programadas++;
                                        out.print("<tr>");
                                        out.print("<td align='center' >" + obj_parametros[3] + "</td>");
                                        out.print("<td>" + obj_parametros[2] + "</td>");
                                        out.print("<td><b></b></td>");
                                        out.print("<td><b></b></td>");
                                        out.print("<td><b></b></td>");
                                        if (id_tipo_mtto > 1) {
                                            out.print("<td><b></b></td>");
                                        }
                                        out.print("</tr>");
                                    }
                                }
                            }
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="CONTENIDO">
                            out.print("</table>");
                            out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td aling='center' style='height:80px;'><b>Observaciones: REALIZAR PRUEBAS DE FUNCIONAMIENTO AL EQUIPO.</b>"
                                    + "<br>Si durante la ejecucion de las pruebas observa fallas en el funcionamiento del equipo, detenga la prueba, corrija la falla y realice nuevamente la prueba.</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;'>VERIFICACIÓN DE FUNCIONALIDAD DE EQUIPO</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td aling='center'>Indique con una (x) el estado del ciclo en el momento de la verificación del equipo</td>");
                            out.print("<td>Esterilización</td>");
                            out.print("<td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                            out.print("<td>Enfriamiento</td>");
                            out.print("<td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='5' style='height:60px;'>OBSERVACIÓN (Diligencie este espacio si encuentra observaciones o "
                                    + "variaciones que puedan afectar los resultados de la prueba. Corrija la falla y repita nuevamente)</td>");
                            out.print("</tr><tr><td colspan='5' style='height:60px;'><b>NOTA:</b></br> "
                                    + "Al finalizar el mantenimiento realice un ciclo de prueba, para realizar la verificación es "
                                    + "necesario que el equipo se encuentre en funcionamiento.En caso de no realizarlas se debe indicar porque no se efectuo la prueba. </td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table2'>");
                            out.print("<tr>");
                            out.print("<td aling='center' style='height:50px;'><i>___________________________________________</i><br /><b>FIRMA COORDINADOR Y/O TECNICO QUE EJECUTA MTTO. Y PRUEBAS</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("<table class='table2'>");
                            out.print("<tr>");
                            out.print("<td aling='center' style='height:50px;'><i>___________________________________________</i><br /><b>V.B. Jefe de Mantenimiento</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</tr>");
                            out.print("</table>");
                            //</editor-fold>   
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTROS MTF">
                        //<editor-fold defaultstate="collapsed" desc="CABECERA">
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                        out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                        out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                        out.print("<td align='center' style='width:50%;'>CODIGO " + registro + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                        out.print("<td align='center'>VERSIÓN: " + version + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="DETALLE CABECERA">
                        out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                        out.print("<tr>");
                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' colspan='6' align='center'>" + titulo2.toUpperCase() + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>SEMANA</th>");
                        out.print("<td align='center'><b>" + obj_orden[19] + "</b></td>");
                        out.print("<th>FECHA DE EJECUCIÓN</th>");
                        out.print("<td align='center'>AAAA-MM-DD</td>");
                        out.print("<th>O.T No :</th>");
                        out.print("<td align='center'><b>" + obj_orden[1] + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th>EJECUTO</th>");
                        out.print("<td align='center'>" + obj_orden[6] + "</td>");
                        out.print("<th>LÍNEA</th>");
                        out.print("<td align='center'>" + obj_orden[24] + "</td>");
                        out.print("<th>T.T. MIN:</th>");
                        out.print("<td align='center'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='6'><b>NOTA IMPORTANTE :</b>" + nota + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                        out.print("<table class=\"table2\">");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;'>DESCRIPCIÓN DE LA ACTIVIDAD A REALIZAR</td>");
                        out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>EJECUTO</td>");
                        if (id_tipo_mtto > 1) {
                            out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>TRABAJOS A EJECUTAR</td>");
                        }
                        out.print("</tr>");
                        lst_grupos_parametros = jpacprm.Grupos_parametro_tipo(id_tipo_mtto, id_tipo_equipo);
                        for (int i = 0; i < lst_grupos_parametros.size(); i++) {
                            Object[] obj_grupos_parametro = (Object[]) lst_grupos_parametros.get(i);
                            lst_parametros = jpacprm.Parametros_grupo(id_tipo_mtto, id_tipo_equipo, obj_grupos_parametro[0].toString());
                            if (lst_parametros != null) {
                                arg_grupos += "[" + obj_grupos_parametro[0] + "]";
                                out.print("<tr>");
                                out.print("<th style='width:5%'>ITEM</th>");
                                out.print("<th>" + obj_grupos_parametro[0] + "</th>");
                                out.print("<th>SI</th>");
                                out.print("<th>NO</th>");
                                out.print("<th>N/A</th>");
                                if (id_tipo_mtto > 1) {
                                    out.print("<th></th>");
                                }
                                out.print("</tr>");
                                for (int j = 0; j < lst_parametros.size(); j++) {
                                    Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                                    arg_actividades += "[" + obj_parametros[0] + "_/_NO_/_N/A]";
                                    if (i == 0 && j == 0) {
                                        arg_actividades_id = "" + obj_parametros[0] + "";
                                    } else {
                                        arg_actividades_id = arg_actividades_id + "," + obj_parametros[0] + "";
                                    }
                                    cont_actividades_programadas++;
                                    out.print("<tr>");
                                    out.print("<td align='center'>" + (j + 1) + "</td>");
                                    out.print("<td>" + obj_parametros[2] + "</td>");
                                    out.print("<td><b></b></td>");
                                    out.print("<td><b></b></td>");
                                    out.print("<td><b></b></td>");
                                    if (id_tipo_mtto > 1) {
                                        out.print("<td><b></b></td>");
                                    }
                                    out.print("</tr>");
                                }
                            }
                        }
//</editor-fold>
                        if (id_tipo_mtto > 1) {
                            //<editor-fold defaultstate="collapsed" desc="REVISIÓN">
                            out.print("</table>");
                            out.print("<table class='table2'>");
                            out.print("<tr>");
                            out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' >RESPONSABLES</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td aling='center' style='height:50px;'><i>___________________________________________</i><br /><b>Verifico Mantenimiento</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                        }
                        if (id_tipo_mtto == 1) {
                            //                        //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES NO USADO">
                            //                        out.print("<tr>");
                            //                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>OBSERVACIONES Y TRABAJOS PENDIENTES</td>");
                            //                        out.print("</tr>");
                            //                        out.print("<tr>");
                            //                        out.print("<td colspan='5'>N/A</td>");
                            //                        out.print("</tr>");
                            ////</editor-fold>
                            //                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCIÓN NO USADO">
                            //                        out.print("<tr>");
                            //                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>DESCRIPCIÓN DE LA ACTVIDAD REALIZADA</td>");
                            //                        out.print("</tr>");
                            //                        out.print("<tr>");
                            //                        out.print("<td colspan='5'>N/A</td>");
                            //                        out.print("</tr>");
                            ////</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES GENERALES">
                            out.print("<tr>");
                            out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>NOVEDADES GENERALES</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='5'>N/A</td>");
                            out.print("</tr>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REPUESTOS">
                            out.print("<tr>");
                            out.print("<td colspan='5' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>REPUESTOS UTILIZADOS</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:5%'>1</td>");
                            out.print("<td colspan='4'>N/A</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="NOVEDADES">
                            out.print("<table class='table2'>");
                            out.print("<tr>");
                            out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>NOVEDAD</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td aling='right'><b>NO SE REALIZA MANTENIMIENTO POR</b></td>");
                            out.print("<td>PRIORIDAD PRODUCCIÓN : </td>");
                            out.print("<td>PRIORIDAD CALIDAD : </td>");
                            out.print("<td>FALTA DE PERSONAL : </td>");
                            out.print("<td>N/A : </td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td aling='center' colspan='2'><b>FIRMA : </b></td>");
                            out.print("<td aling='center'><b>SEMANA : </b></td>");
                            out.print("<td aling='center' colspan='2'><b>SE REALIZA EN LA FECHA : </b></td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REVISIÓN">
                            out.print("<table class='table2'>");
                            out.print("<tr>");
                            out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='3'>RESPONSABLES</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td aling='center' style='height:50px;width:33%'><i>___________________________________________</i><br /><b>Verifico Mantenimiento</b></td>");
                            out.print("<td aling='center' style='height:50px;width:33%'><i>___________________________________________</i><br /><b>Recibio Producción</b></td>");
                            out.print("<td aling='center' style='height:50px;width:34%'><i>___________________________________________</i><br /><b>Recibio Calidad</b></td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                        }
                        //</editor-fold>
                    }
                    if (instrucciones.equals("")) {
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES">
                        out.print("<table class='table2'>");
                        out.print("<tr>");
                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>OBSERVACIONES GENERALES</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='padding:20px'>" + instrucciones
                                + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="PROGRESS BAR MODAL">
                        out.print("<div class=\"modal fade\" id=\"ProgressBarModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\">");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Avanzar Orden de Trabajo</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        //<editor-fold defaultstate="collapsed" desc="progress_bar">
                        out.print("<section class='progress_bar'>");
                        out.print("<ol class='progress_bar'>");
                        out.print("<li class='is-complete" + ((estado_ot == 1) ? " is-hovered" : "") + "'><span>Programado</span></li>");
                        out.print("<li><span>Emitido</span><span class='has-changes'></span></li>");
                        out.print("<li><span>Ejecutado</span></li>");
                        out.print("<li><span>Verificado</span></li>");
                        out.print("<li><span>Recibido</span></li>");
                        out.print("<li><span>Cerrado</span></li>");
                        out.print("</ol>");
                        out.print("</section>");
//</editor-fold>
                        out.print("<form action='Orden?opc=4' method='post'>");
                        out.print("<input type='hidden' value='" + id_equipo + "' name='Id_equipo' />");
                        out.print("<input type='hidden' value='" + id_orden + "' name='Id_orden' />");
                        out.print("<input type='hidden' value='" + estado_ot + "' name='Estado_ot' />");
                        if (estado_ot >= 1) {
                            out.print("<p>La OT se encuentra pendiente por emitir, al pulsar avanzar el sistema libera la OT para iniciar con la Ejecución por parte del tecnico ejecutor.</p>");
                            out.print("<input type='hidden' value='" + arg_grupos + "' name='Arg_grupos' />");
                            out.print("<input type='hidden' value='" + arg_actividades_id + "' name='Arg_id_actividades' />");
                            out.print("<input type='hidden' value='[" + arg_actividades_id.replace(",", "][") + "]' name='Arg_id_actividades_pendientes' />");
                            out.print("<input type='hidden' value='" + arg_actividades + "' name='Arg_actividades' />");
                            out.print("<input type='hidden' value='" + cont_actividades_programadas + "' name='Cont_actividades_programadas' />");
                            out.print("<input type='hidden' value='N/A' name='Arg_id_lineas_zona' />");
                        }
                        out.print("<input type='hidden' value='" + estado_ot + "' name='Estado_ot' />");
                        out.print("<input type='submit' value='Enviar' style='display:none' id='Btn_ot_estado' />");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_equipo_trabajo_gestion")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_equipo_trabajo_gestion MM Y INSP">
                    id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden").toString());
                    lst_orden = jpacotb.Orden_trabajo_equipo_id(id_orden);
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    id_equipo = Integer.parseInt(obj_orden[3].toString());
                    lst_orden_detalle = jpacotd.Consulta_detalle_orden_trabajo(id_orden);
                    if (lst_orden_detalle != null) {
                        Object[] obj_orden_detalle = (Object[]) lst_orden_detalle.get(0);
                        arg_grupos_ot = obj_orden_detalle[2].toString().replace("][", "_/_").replace("[", "").replace("]", "").split("_/_");
                        arg_actividades_ot = obj_orden_detalle[4].toString().replace("][", "_//_").replace("[", "").replace("]", "").split("_//_");
                        id_tipo_mtto = Integer.parseInt(obj_orden[2].toString());
                        id_equipo = Integer.parseInt(obj_orden[3].toString());
                        lst_equipo = jpaceqp.Equipo_id(id_equipo);
                        Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                        estado_ot = Integer.parseInt(obj_orden[14].toString());
                        id_tipo_equipo = Integer.parseInt(obj_orden[26].toString());
                        registro = ((id_tipo_mtto == 1) ? obj_orden[30].toString() : ((id_tipo_mtto == 2) ? obj_orden[31].toString() : obj_orden[32].toString()));
                        lst_registro = jpacrgt.Traer_registro_ot(registro, obj_orden[5].toString());
                        if (lst_registro != null) {
                            Object[] obj_registro = (Object[]) lst_registro.get(0);
                            registro = obj_registro[1].toString();
                            version = obj_registro[3].toString();
                            titulo = obj_registro[2].toString();
                            titulo2 = ((id_tipo_mtto == 1) ? "Listado de actividades de Mtto mayor" : ((id_tipo_mtto == 2) ? "Listado de actividades de inspección" : "Listado de actividades de lubricación"));
                            nota = obj_registro[4].toString();
                            instrucciones = obj_registro[5].toString();
                        } else {
                            registro = registro;
                            version = "N/A";
                            titulo = "N/A";
                            titulo2 = "N/A";
                            nota = "N/A";
                            instrucciones = "N/A";
                        }
                        try {
                            lst_actividades_pedientes = jpacotd.Traer_pendientes(id_orden, id_tipo_mtto, id_equipo);
                            Object[] obj_act_pendientes = (Object[]) lst_actividades_pedientes.get(0);
                            arg_actividades_pendientes = obj_act_pendientes[1].toString();
                        } catch (Exception e) {
                            arg_actividades_pendientes = "";
                        }
                        out.print("<input type='hidden' id='Txt_enlace_volver' value='Orden?opc=1&Id_equipo=" + id_equipo + "' />");
                        out.print("<div style='float:right'>");
                        out.print("<span class='fas fa-print fa-size_small' onclick=\"Imprimir();\" title='Imprimir'></span>");
                        out.print("&nbsp;&nbsp;&nbsp;<span class='far fa-file-alt fa-size_small' data-toggle=\"modal\" data-target=\"#FTModal\" title='FT'></span>");
                        out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-hard-hat fa-size_small' data-toggle=\"modal\" data-target=\"#SeguridadModal\" title='Instrucciones de Seguridad'></span>");
                        if (obj_orden[36] == null) {
                            if (estado_ot >= 3 && estado_ot <= 5) {
                                if (!nombre_rol.equals("Tecnico") && !nombre_rol.equals("Consulta")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-exchange-alt fa-size_small' onclick='Devolver(" + id_equipo + "," + id_orden + "," + estado_ot + "," + 1 + ")'  title='Devolver'></span>");
                                }
                            }
                            if (estado_ot <= 5) {
                                if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                                    if (id_tipo_mtto == 1) {
                                        out.print("&nbsp;&nbsp;&nbsp;<span class='fa fa-save fa-size_small' onclick=\"ConstruirDatosAutoclaveMM('" + obj_orden_detalle[3] + "')\"></span>");
                                    } else {
                                        out.print("&nbsp;&nbsp;&nbsp;<span class='fa fa-save fa-size_small' onclick=\"ConstruirDatosAutoclave('" + obj_orden_detalle[3] + "')\"></span>");
                                    }
                                } else {
                                    out.print("&nbsp;&nbsp;&nbsp;<span class='fa fa-save fa-size_small' onclick=\"ConstruirDatos('" + obj_orden_detalle[3] + "')\"></span>");
                                }
                            }
                            if (estado_ot <= 5) {
                                out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-ellipsis-h fa-size_small' data-toggle=\"modal\" data-target=\"#ProgressBarModal\" title='Gestión OT'></span>");
                            }
                        }
                        out.print("</div>");
                        out.print("<div id='Div_export'>");
                        if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                            //<editor-fold defaultstate="collapsed" desc="REGISTRO AUTOCLAVE">
                            //<editor-fold defaultstate="collapsed" desc="CABECERA">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                            out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                            out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                            out.print("<td align='center' style='width:50%;'>CODIGO " + registro + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                            out.print("<td align='center'>VERSIÓN: " + version + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DETALLE CABECERA">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' colspan='6' align='center'>" + titulo2.toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>SEMANA</th>");
                            out.print("<td style='width:15%;'  align='center'><b>" + obj_orden[19] + "</b></td>");
                            out.print("<th>FECHA DE EJECUCIÓN</th>");
                            out.print("<td align='center'>" + ((obj_orden[7] == null) ? "AAAA-MM-DD" : obj_orden[7]) + "</td>");
                            out.print("<th>O.T No :</th>");
                            out.print("<td align='center'><b>" + obj_orden[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>EJECUTO</th>");
                            out.print("<td colspan='3' align='center'>" + obj_orden[6] + "</td>");
                            out.print("<th>T.T. MIN:</th>");
                            if (estado_ot == 2) {
                                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                                    out.print("<td align='center'><input style='width:150px' class='form-control' type='number' id='Txt_tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "' /></td>");
                                } else {
                                    out.print("<td align='center'><input style='width:150px' class='form-control' type='hidden' id='Txt_tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "' />" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "</td>");
                                }
                            } else {
                                out.print("<td align='center'><input style='width:150px' class='form-control' type='hidden' id='Txt_tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "' />" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "</td>");
                            }
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>EQUIPO AUTOCLAVE</th>");
                            out.print("<td colspan='6' align='center'>" + obj_orden[24] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='6'><b>NOTA IMPORTANTE :</b>" + nota + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            if (id_tipo_mtto == 1) {
                                out.print("<td colspan='2'align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>DESCRIPCIÓN DE LA ACTIVIDAD A REALIZAR</td>");
                            } else {
                                out.print("<td rowspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>SISTEMA ELECTRICO/ MECANICO</td>");
                            }
                            out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>EJECUTO</td>");
                            if (id_tipo_mtto == 2) {
                                out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff; font-size: 12px;'>OBSERVACIONES</td>");
                            }
                            out.print("</tr>");
                            for (int i = 0; i < arg_grupos_ot.length; i++) {
                                out.print("<tr>");
                                if (id_tipo_mtto == 1) {
                                    out.print("<th>ITEM</th>");
                                    out.print("<th>" + arg_grupos_ot[i] + "</th>");
                                } else if (!arg_grupos_ot[i].equals("SISTEMA ELECTRICO/ MECANICO")) {
                                    out.print("<th>" + arg_grupos_ot[i] + "</th>");
                                }
                                out.print("<th>SI</th>");
                                out.print("<th>NO</th>");
                                out.print("<th>N/A</th>");
                                if (id_tipo_mtto > 1) {
                                    out.print("<th></th>");
                                }
                                out.print("</tr>");
                                lst_parametros = jpacotd.Consulta_actividades_grupo_ids(arg_grupos_ot[i], obj_orden_detalle[3].toString());
                                for (int j = 0; j < lst_parametros.size(); j++) {
                                    Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                                    out.print("<tr>");
                                    if (id_tipo_mtto == 1) {
                                        out.print("<td align='center'>" + obj_parametros[3] + "</td>");
                                    }
                                    out.print("<td " + ((arg_actividades_pendientes.contains("[" + obj_parametros[0] + "]")) ? "class='naranja'" : "") + ">" + obj_parametros[2] + "</td>");
                                    String valor_ejecuto = "";
                                    String valor_observacion = "";
                                    int cont_control = 0;
                                    for (int k = 0; k < arg_actividades_ot.length; k++) {
                                        if (arg_actividades_ot[k].contains(obj_parametros[0] + "_/_")) {
                                            valor_ejecuto = arg_actividades_ot[k].split("_/_")[1];
                                            valor_observacion = arg_actividades_ot[k].split("_/_")[2];
                                            cont_control++;
                                            break;
                                        }
                                        if (cont_control > 0) {
                                            break;
                                        }
                                    }
                                    if (estado_ot >= 3) {
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("SI")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("NO")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("N/A")) ? "X" : "") + "</td>");
                                    } else if (estado_ot <= 2 && nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                                        out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("SI")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",1)' value='1' /></td>");
                                        out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("NO")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",2)' value='2' /></td>");
                                        out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("N/A")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",3)' value='3' /></td>");
                                    } else {
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("SI")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("NO")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("N/A")) ? "X" : "") + "</td>");
                                    }
                                    out.print("<i style='display:none' id='Txt_ejecuto_" + obj_parametros[0] + "'>" + valor_ejecuto + "</i>");
                                    if (id_tipo_mtto > 1) {
                                        out.print("<td valign='top' id='Txt_observacion_" + obj_parametros[0] + "' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "'>" + valor_observacion + "</td>");
                                    } else {
                                        out.print("<i style='display:none' id='Txt_observacion_" + obj_parametros[0] + "'>" + valor_observacion + "</i>");
                                    }
                                    out.print("</tr>");
                                }
                            }
//</editor-fold>
                            if (id_tipo_mtto == 1) {
                                //<editor-fold defaultstate="collapsed" desc="MM AUTOCLAVE">
                                out.print("</table>");
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td aling='center' style='height:80px;'><b>Observaciones: REALIZAR PRUEBAS DE FUNCIONAMIENTO AL EQUIPO.</b>"
                                        + "<br>Si durante la ejecucion de las pruebas observa fallas en el funcionamiento del equipo, detenga la prueba, corrija la falla y realice nuevamente la prueba.</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' >VERIFICACIÓN DE FUNCIONALIDAD DE EQUIPO</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td aling='center'>Indique con una (x) el estado del ciclo en el momento de la verificación del equipo</td>");
                                if (obj_orden_detalle[16] != null) {
                                    if (estado_ot >= 3) {
                                        out.print("<td>Esterilización</td>");
                                        out.print("<td align='center'>" + ((Integer.parseInt(obj_orden_detalle[16].toString()) == 1) ? "X" : "&nbsp;&nbsp;&nbsp;") + "</td>");
                                        out.print("<td>Enfriamiento</td>");
                                        out.print("<td align='center'>" + ((Integer.parseInt(obj_orden_detalle[16].toString()) == 2) ? "X" : "&nbsp;&nbsp;&nbsp;") + "</td>");
                                    } else {
                                        out.print("<td>Esterilización</td>");
                                        out.print("<td align='center'><input style='width:15px' onclick='Verificacion(1)' "
                                                + ((Integer.parseInt(obj_orden_detalle[16].toString()) == 1) ? "checked" : "") + " class='form-control' name='Cbx_verificacion' type='radio' "
                                                + "id='Txt_ver_equipo' value='1'/></td>");
                                        out.print("<td>Enfriamiento</td>");
                                        out.print("<td align='center'><input style='width:15px' onclick='Verificacion(2)' "
                                                + ((Integer.parseInt(obj_orden_detalle[16].toString()) == 2) ? "checked" : "") + " class='form-control' name='Cbx_verificacion' type='radio'"
                                                + "id='Txt_ver_equipo' value='2'/></td>");
                                    }
                                } else {
                                    out.print("<td>Esterilización</td>");
                                    out.print("<td align='center'><input style='width:15px' onclick='Verificacion(1)' class='form-control' name='Cbx_verificacion' type='radio' "
                                            + "id='Txt_ver_equipo' value='1'/></td>");
                                    out.print("<td>Enfriamiento</td>");
                                    out.print("<td align='center'><input style='width:15px' onclick='Verificacion(2)' class='form-control' name='Cbx_verificacion' type='radio'"
                                            + "id='Txt_ver_equipo' value='2'/></td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td colspan='5' style='height:60px;'>OBSERVACIÓN (Diligencie este espacio si encuentra observaciones o "
                                        + "variaciones que puedan afectar los resultados de la prueba. Corrija la falla y repita nuevamente)</td>");
                                out.print("</tr><tr><td colspan='5' style='height:60px;'><b>NOTA:</b></br> "
                                        + "Al finalizar el mantenimiento realice un ciclo de prueba, para realizar la verificación es "
                                        + "necesario que el equipo se encuentre en funcionamiento.En caso de no realizarlas se debe indicar porque no se efectuo la prueba. </td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<table class='table2'>");
                                out.print("<tr>");
                                out.print("<td aling='center' style='height:70px;width:33%'>" + ((obj_orden[7] != null) ? "<u>" + obj_orden[6] + " " + obj_orden[7] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>FIRMA COORDINADOR Y/O TECNICO QUE EJECUTA MTTO. Y PRUEBAS.</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<table class='table2'>");
                                out.print("<tr>");
                                out.print("<td aling='center' style='height:70px;width:33%'>" + ((obj_orden[38] != null) ? "<u>" + obj_orden[37] + " " + obj_orden[38] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>V.B. Jefe de Mantenimiento</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("</tr>");
                                //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES GENERALES">
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>OBSERVACIONES GENERALES</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td valign='top' style='padding:20px'>" + instrucciones
                                        + "</td>");
                                out.print("</tr>");
                                out.print("</table>");
                                //</editor-fold>
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="INSPECCION AUTOCLAVE">
                                //<editor-fold defaultstate="collapsed" desc="REVISIÓN">
                                out.print("</table>");
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td aling='center' style='height:108px;'>" + ((obj_orden[38] != null) ? "<u>" + obj_orden[37] + " " + obj_orden[38] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>V.B Jefe de Mantenimiento</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
//</editor-fold>
                                out.print("<div style='display:none'>");
                                out.print("<table style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr><td id='Td_pendientes_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;padding:20px'>" + obj_orden_detalle[7] + "</td>");
                                out.print("<td id='Td_descripcion_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;;padding:20px'>" + obj_orden_detalle[8] + "</td></tr>");
                                out.print("</table>");
                                out.print("<table style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tbody id='Tr_respuestos_ot'>");
                                if (obj_orden_detalle[9].toString().length() > 0) {
                                    out.print(obj_orden_detalle[9]);
                                } else {
                                    out.print("<tr>");
                                    out.print("<td align='center' style='width:5%'>1</td><td colspan='4' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "'>N/A</td>");
                                    out.print("</tr>");
                                }
                                //</editor-fold>
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                            out.print("</div>");
                            if (estado_ot <= 5) {
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIO">
                                out.print("<div style='display:none'>");
                                out.print("<form action='Orden?opc=16' method='post' id='FormGuardarOt'>");
                                out.print("<input type='text' name='Id_orden' value='" + id_orden + "'>");
                                out.print("<input type='text' name='Id_orden_detalle' value='" + obj_orden_detalle[0] + "'>");
                                out.print("<input type='text' name='Arg_grupos' value='" + obj_orden_detalle[2] + "'>");
                                out.print("<input type='text' name='Arg_id_actividades' value='" + obj_orden_detalle[3] + "'>");
                                out.print("<input type='text' id='Arg_id_actividades_pendientes' name='Arg_id_actividades_pendientes' value='" + obj_orden_detalle[14] + "'>");
                                out.print("<textarea id='Arg_actividades' name='Arg_actividades'>" + obj_orden_detalle[4] + "</textarea>");
                                out.print("<input type='text' name='Cont_actividades_programadas' value='" + obj_orden_detalle[5] + "'>");
                                out.print("<input type='text' id='Cont_actividades_ejecutadas' name='Cont_actividades_ejecutadas' value='" + obj_orden_detalle[6] + "'>");
                                out.print("<textarea id='Txt_datos_descripcion' name='Txt_datos_descripcion'>" + obj_orden_detalle[8] + "</textarea>");
                                out.print("<input type='text' id='No_pmp' name='No_pmp' value='" + obj_orden_detalle[10] + "'>");
                                out.print("<input type='text' id='Arg_id_lineas_zona' name='Arg_id_lineas_zona' value='" + obj_orden_detalle[15] + "'>");
                                out.print("<input type='text' id='Tiempo_trabajo' name='Tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "'>");
                                out.print("<input type='text' id='Verificacion' name='Txt_ver_equipo' value='" + obj_orden_detalle[16] + "'>");
                                out.print("</form>");
                                out.print("</div>");
                                //</editor-fold>
                            }
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="REGISTROS">
                            //<editor-fold defaultstate="collapsed" desc="CABECERA">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                            out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                            out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                            out.print("<td align='center' style='width:50%;'>CODIGO " + registro + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                            out.print("<td align='center'>VERSIÓN: " + version + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="DETALLE CABECERA">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' colspan='6' align='center'>" + titulo2.toUpperCase() + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>SEMANA</th>");
                            out.print("<td align='center'><b>" + obj_orden[19] + "</b></td>");
                            out.print("<th>FECHA DE EJECUCIÓN</th>");
                            out.print("<td align='center'>" + ((obj_orden[7] == null) ? "AAAA-MM-DD" : obj_orden[7]) + "</td>");
                            out.print("<th>O.T No :</th>");
                            out.print("<td align='center'><b>" + obj_orden[1] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th>EJECUTO</th>");
                            out.print("<td align='center'>" + obj_orden[6] + "</td>");
                            out.print("<th>LINEA</th>");
                            out.print("<td align='center'>" + obj_orden[24] + "</td>");
                            out.print("<th>T.T. MIN:</th>");
                            if (estado_ot == 2) {
                                if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                                    out.print("<td align='center'><input style='width:150px' class='form-control' type='number' id='Txt_tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "' /></td>");
                                } else {
                                    out.print("<td align='center'><input style='width:150px' class='form-control' type='hidden' id='Txt_tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "' />" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "</td>");
                                }
                            } else {
                                out.print("<td align='center'><input style='width:150px' class='form-control' type='hidden' id='Txt_tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "' />" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "</td>");
                            }

                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='6'><b>NOTA IMPORTANTE :</b>" + nota + "</td>");
                            out.print("</tr>");
                            out.print("</table>");
//</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                            out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                            out.print("<tr>");
                            out.print("<td colspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;'>DESCRIPCIÓN DE LA ACTIVIDAD A REALIZAR</td>");
                            out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>EJECUTO</td>");
                            if (id_tipo_mtto > 1) {
                                out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>TRABAJOS A EJECUTAR</td>");
                            }
                            out.print("</tr>");
                            for (int i = 0; i < arg_grupos_ot.length; i++) {
                                out.print("<tr>");
                                out.print("<th style='width:5%'>ITEM</th>");
                                out.print("<th>" + arg_grupos_ot[i] + "</th>");
                                out.print("<th>SI</th>");
                                out.print("<th>NO</th>");
                                out.print("<th>N/A</th>");
                                if (id_tipo_mtto > 1) {
                                    out.print("<th></th>");
                                }
                                out.print("</tr>");
                                lst_parametros = jpacotd.Consulta_actividades_grupo_ids(arg_grupos_ot[i], obj_orden_detalle[3].toString());
                                for (int j = 0; j < lst_parametros.size(); j++) {
                                    Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                                    out.print("<tr>");
                                    out.print("<td align='center'>" + (j + 1) + "</td>");
                                    out.print("<td " + ((arg_actividades_pendientes.contains("[" + obj_parametros[0] + "]")) ? "class='naranja'" : "") + ">" + obj_parametros[2] + "</td>");
                                    String valor_ejecuto = "";
                                    String valor_observacion = "";
                                    int cont_control = 0;
                                    for (int k = 0; k < arg_actividades_ot.length; k++) {
                                        if (arg_actividades_ot[k].contains(obj_parametros[0] + "_/_")) {
                                            valor_ejecuto = arg_actividades_ot[k].split("_/_")[1];
                                            valor_observacion = arg_actividades_ot[k].split("_/_")[2];
                                            cont_control++;
                                            break;
                                        }
                                        if (cont_control > 0) {
                                            break;
                                        }
                                    }
                                    if (estado_ot >= 3) {
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("SI")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("NO")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("N/A")) ? "X" : "") + "</td>");
                                    } else if (estado_ot <= 2 && nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                                        out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("SI")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",1)' value='1' /></td>");
                                        out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("NO")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",2)' value='2' /></td>");
                                        out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("N/A")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",3)' value='3' /></td>");
                                    } else {
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("SI")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("NO")) ? "X" : "") + "</td>");
                                        out.print("<td align='center'>" + ((valor_ejecuto.equals("N/A")) ? "X" : "") + "</td>");
                                    }
                                    out.print("<i style='display:none' id='Txt_ejecuto_" + obj_parametros[0] + "'>" + valor_ejecuto + "</i>");
                                    if (id_tipo_mtto > 1) {
                                        out.print("<td valign='top' id='Txt_observacion_" + obj_parametros[0] + "' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "'>" + valor_observacion + "</td>");
                                    } else {
                                        out.print("<i style='display:none' id='Txt_observacion_" + obj_parametros[0] + "'>" + valor_observacion + "</i>");
                                    }
                                    out.print("</tr>");
                                }
                            }
                            if (id_tipo_mtto > 1) {
                                out.print("</table>");
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' >RESPONSABLES</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td aling='center' style='height:50px;'>" + ((obj_orden[9] != null) ? "<u>" + obj_orden[8] + " " + obj_orden[9] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>Verifico Mantenimiento</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
                                out.print("<div style='display:none'>");
                                out.print("<table style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr><td id='Td_pendientes_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;padding:20px'>" + obj_orden_detalle[7] + "</td>");
                                out.print("<td id='Td_descripcion_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;;padding:20px'>" + obj_orden_detalle[8] + "</td></tr>");
                                out.print("</table>");
                                out.print("<table style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tbody id='Tr_respuestos_ot'>");
                                if (obj_orden_detalle[9].toString().length() > 0) {
                                    out.print(obj_orden_detalle[9]);
                                } else {
                                    out.print("<tr>");
                                    out.print("<td align='center' style='width:5%'>1</td><td colspan='4' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "'>N/A</td>");
                                    out.print("</tr>");
                                }
                                out.print("</tbody>");
                                out.print("</table>");
                                out.print("</div>");
                            }
                            //</editor-fold>
                            if (id_tipo_mtto == 1) {
                                //                        //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES NO USADO">
//                        out.print("<tr>");
//                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>OBSERVACIONES Y TRABAJOS PENDIENTES</td>");
//                        out.print("</tr>");
//                        out.print("<tr>");
//                        out.print("<td id='Td_pendientes_ot' colspan='5' contenteditable='" + ((estado_ot >= 4) ? "false" : "true") + "' valign='top' style='height:100px;padding:20px'>" + obj_orden_detalle[7] + "</td>");
//                        out.print("</tr>");
////</editor-fold>
                                //                        //<editor-fold defaultstate="collapsed" desc="DESCRIPCIÓN NO USADO">
//                        out.print("<tr>");
//                        out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>DESCRIPCIÓN DE LA ACTVIDAD REALIZADA</td>");
//                        out.print("</tr>");
//                        out.print("<tr>");
//                        out.print("<td id='Td_descripcion_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;;padding:20px'>" + obj_orden_detalle[8] + "</td>");
//                        out.print("</tr>");
////</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES GENERALES">
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>NOVEDADES GENERALES</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td id='Td_pendientes_ot' colspan='5' contenteditable='" + ((estado_ot <= 5 && (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Coordinador"))) ? "true" : "false") + "' valign='top' style='height:100px;padding:20px'>" + obj_orden_detalle[7] + "</td>");
                                out.print("</tr>");
                                out.print("<tr style='display:none'>");
                                out.print("<td id='Td_descripcion_ot' colspan='5' contenteditable='" + ((estado_ot <= 5 && (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico") || nombre_rol.equals("Coordinador"))) ? "true" : "false") + "' valign='top' style='height:100px;;padding:20px'>" + obj_orden_detalle[8] + "</td>");
                                out.print("</tr>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="REPUESTOS">
                                out.print("</table>");
                                out.print("<table class='table2' id='Table_repuestos' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                if (estado_ot >= 3) {
                                    out.print("<td colspan='5' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>REPUESTOS UTILIZADOS</td>");
                                } else {
                                    out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'><span class='fa fa-plus fa-size_small' " + ((estado_ot == 2 && nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) ? "onclick='AddRepuesto()'" : "style='cursor:not-allowed'") + "></span></td>");
                                    out.print("<td colspan='4' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>REPUESTOS UTILIZADOS</td>");
                                }
                                out.print("</tr>");
                                out.print("<tbody id='Tr_respuestos_ot'>");
                                if (obj_orden_detalle[9].toString().length() > 0) {
                                    if (estado_ot >= 3) {
                                        out.print(obj_orden_detalle[9].toString().replace("true", "false"));
                                    } else {
                                        out.print(obj_orden_detalle[9]);
                                    }
                                } else {
                                    out.print("<tr>");
                                    out.print("<td align='center' style='width:5%'>1</td><td colspan='4' contenteditable='" + ((estado_ot >= 3 && nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) ? "true" : "false") + "'>N/A</td>");
                                    out.print("</tr>");
                                }
                                out.print("</tbody>");
                                out.print("</table>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="NOVEDADES">
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='5'>NOVEDAD</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td aling='right'><b>NO SE REALIZA MANTENIMIENTO POR </b></td>");
                                if (estado_ot >= 3) {
                                    out.print("<td>PRIORIDAD PRODUCCIÓN : " + ((obj_orden_detalle[10].equals("PRF")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                    out.print("<td>PRIORIDAD CALIDAD : " + ((obj_orden_detalle[10].equals("GC")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                    out.print("<td>FALTA DE PERSONAL : " + ((obj_orden_detalle[10].equals("PER")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                    out.print("<td>N/A : " + ((obj_orden_detalle[10].equals("N/A")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                } else if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                                    out.print("<td>PRIORIDAD PRODUCCIÓN : <input type='radio' name='Rdb_no_pmp' id='Rdb_no_pmp' value='1' onclick='NoPmp(1)' " + ((obj_orden_detalle[10].equals("PRF")) ? "checked" : "") + " " + ((estado_ot >= 3) ? "disabled" : "") + " /></td>");
                                    out.print("<td>PRIORIDAD CALIDAD : <input type='radio' name='Rdb_no_pmp' id='Rdb_no_pmp' value='2' onclick='NoPmp(2)' " + ((obj_orden_detalle[10].equals("GC")) ? "checked" : "") + " " + ((estado_ot >= 3) ? "disabled" : "") + " /></td>");
                                    out.print("<td>FALTA DE PERSONAL : <input type='radio' name='Rdb_no_pmp' id='Rdb_no_pmp' value='3' onclick='NoPmp(3)' " + ((obj_orden_detalle[10].equals("PER")) ? "checked" : "") + " " + ((estado_ot >= 3) ? "disabled" : "") + " /></td>");
                                    out.print("<td>N/A : <input type='radio' name='Rdb_no_pmp' id='Rdb_no_pmp' value='0' onclick='NoPmp(0)' " + ((obj_orden_detalle[10].equals("N/A")) ? "checked" : "") + " " + ((estado_ot >= 3) ? "disabled" : "") + " /></td>");
                                } else {
                                    out.print("<td>PRIORIDAD PRODUCCIÓN : " + ((obj_orden_detalle[10].equals("PRF")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                    out.print("<td>PRIORIDAD CALIDAD : " + ((obj_orden_detalle[10].equals("GC")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                    out.print("<td>FALTA DE PERSONAL : " + ((obj_orden_detalle[10].equals("PER")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                    out.print("<td>N/A : " + ((obj_orden_detalle[10].equals("N/A")) ? "<b class='negro'>X</b>" : "N/A") + "</td>");
                                }
                                out.print("</tr>");
                                out.print("<tr>");
                                if (!obj_orden_detalle[10].toString().equals("N/A")) {
                                    out.print("<td aling='center' colspan='2'><b>FIRMA : </b>" + obj_orden_detalle[12] + "</td>");
                                    out.print("<td aling='center'><b>SEMANA : </b>" + obj_orden_detalle[13] + "</td>");
                                    out.print("<td aling='center' colspan='2'><b>SE REALIZA EN LA FECHA : </b>" + obj_orden_detalle[11] + "</td>");
                                } else {
                                    out.print("<td aling='center' colspan='2'><b>FIRMA : </b>N/A</td>");
                                    out.print("<td aling='center'><b>SEMANA : </b>N/A</td>");
                                    out.print("<td aling='center' colspan='2'><b>SE REALIZA EN LA FECHA : </b>N/A</td>");
                                }
                                out.print("</tr>");
                                out.print("</table>");
//</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="REVISIÓN">
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center' colspan='3'>RESPONSABLES</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td aling='center' style='height:50px;width:33%'>" + ((obj_orden[9] != null) ? "<u>" + obj_orden[8] + " " + obj_orden[9] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>Verifico Mantenimiento</b></td>");
                                out.print("<td aling='center' style='height:50px;width:33%' " + ((estado_ot == 4) ? "data-toggle=\"modal\" data-target=\"#SignaturePRFModal\"" : "style='cursor:not-allowed'") + ">" + ((obj_orden[11] != null) ? "<u>" + obj_orden[10] + " " + obj_orden[11] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>Recibio Producción</b></td>");
                                out.print("<td aling='center' style='height:50px;width:34%' " + ((estado_ot == 4) ? "data-toggle=\"modal\" data-target=\"#SignatureGCModal\"" : "style='cursor:not-allowed'") + ">" + ((obj_orden[13] != null) ? "<u>" + obj_orden[12] + " " + obj_orden[13] + "</u>" : "<i>___________________________________________</i>") + "<br /><b>Recibio Calidad</b></td>");
                                out.print("</tr>");
                                out.print("</table>");
//</editor-fold>
                            }
                            if (instrucciones.equals("")) {
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES">
                                out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                                out.print("<tr>");
                                out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>OBSERVACIONES GENERALES</td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td valign='top' style='padding:20px'>" + instrucciones
                                        + "</td>");
                                out.print("</tr>");
                                out.print("</table>");
//</editor-fold>
                            }

                            out.print("</div>");
                            if (estado_ot <= 5) {
                                //<editor-fold defaultstate="collapsed" desc="FORMULARIO">
                                out.print("<div style='display:none'>");
                                out.print("<form action='Orden?opc=6' method='post' id='FormGuardarOt'>");
                                out.print("<input type='text' name='Id_orden' value='" + id_orden + "'>");
                                out.print("<input type='text' name='Id_orden_detalle' value='" + obj_orden_detalle[0] + "'>");
                                out.print("<input type='text' name='Arg_grupos' value='" + obj_orden_detalle[2] + "'>");
                                out.print("<input type='text' name='Arg_id_actividades' value='" + obj_orden_detalle[3] + "'>");
                                out.print("<input type='text' id='Arg_id_actividades_pendientes' name='Arg_id_actividades_pendientes' value='" + obj_orden_detalle[14] + "'>");
                                out.print("<textarea id='Arg_actividades' name='Arg_actividades'>" + obj_orden_detalle[4] + "</textarea>");
                                out.print("<input type='text' name='Cont_actividades_programadas' value='" + obj_orden_detalle[5] + "'>");
                                out.print("<input type='text' id='Cont_actividades_ejecutadas' name='Cont_actividades_ejecutadas' value='" + obj_orden_detalle[6] + "'>");
                                out.print("<textarea id='Txt_datos_pendientes' name='Txt_datos_pendientes'>" + obj_orden_detalle[7] + "</textarea>");
                                out.print("<textarea id='Txt_datos_descripcion' name='Txt_datos_descripcion'>" + obj_orden_detalle[8] + "</textarea>");
                                out.print("<textarea id='Txt_datos_repuestos' name='Txt_datos_repuestos'>" + obj_orden_detalle[9] + "</textarea>");
                                out.print("<input type='text' id='No_pmp' name='No_pmp' value='" + obj_orden_detalle[10] + "'>");
                                out.print("<input type='text' id='Arg_id_lineas_zona' name='Arg_id_lineas_zona' value='" + obj_orden_detalle[15] + "'>");
                                out.print("<input type='text' id='Tiempo_trabajo' name='Tiempo_trabajo' value='" + ((obj_orden[33] == null) ? 0 : obj_orden[33]) + "'>");
                                out.print("</form>");
                                out.print("</div>");
//</editor-fold>
                            }
                            if (estado_ot <= 4) {
                                //<editor-fold defaultstate="collapsed" desc="FIRMA PRF">
                                out.print("<div class=\"modal fade\" id=\"SignaturePRFModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                                out.print("<div class=\"modal-dialog\" role=\"document\">");
                                out.print("<div class=\"modal-content\">");
                                out.print("<div class=\"modal-header\">");
                                out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Firma PRF</h5>");
                                out.print("</div>");
                                out.print("<div class=\"modal-body\">");
                                out.print("<form action='Orden?opc=7&Id_orden=" + id_orden + "&tfm=0' method='post'>");
                                out.print("<b>No de documento PRF: </b><input class='form-control' type='text' name='Txt_documento' id='Txt_documentoPRF' required/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documentoPRF');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                                out.print("<div style='display:none'><input type='submit' id='Btn_firma_prf' /></div>");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("<div class=\"modal-footer\">");
                                out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                                out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_firma_prf').click();\">Firmar</a>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="FIRMA GC">
                                out.print("<div class=\"modal fade\" id=\"SignatureGCModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                                out.print("<div class=\"modal-dialog\" role=\"document\">");
                                out.print("<div class=\"modal-content\">");
                                out.print("<div class=\"modal-header\">");
                                out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Firma gc</h5>");
                                out.print("</div>");
                                out.print("<div class=\"modal-body\">");
                                out.print("<form action='Orden?opc=7&Id_orden=" + id_orden + "&tfm=1' method='post'>");
                                out.print("<b>No de documento GC: </b><input class='form-control' type='text' name='Txt_documento' id='Txt_documentoGC' required/>"
                                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documentoGC');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                                out.print("<div style='display:none'><input type='submit' id='Btn_firma_gc' /></div>");
                                out.print("</form>");
                                out.print("</div>");
                                out.print("<div class=\"modal-footer\">");
                                out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                                out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_firma_gc').click();\">Firmar</a>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
//</editor-fold>
                            }
                            //</editor-fold>
                        }
                        //<editor-fold defaultstate="collapsed" desc="PROGRESS BAR MODAL">
                        out.print("<div class=\"modal fade\" id=\"ProgressBarModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\">");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Avanzar Orden de Trabajo</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        //<editor-fold defaultstate="collapsed" desc="progress_bar">
                        out.print("<section class='progress_bar'>");
                        out.print("<ol class='progress_bar'>");
                        out.print("<li class='is-complete" + ((estado_ot == 1) ? " is-hovered" : "") + "'><span>Programado</span></li>");
                        out.print("<li class='" + ((estado_ot >= 2) ? "is-complete" : "") + ((estado_ot == 2) ? " is-hovered" : "") + "'><span>Emitido</span>" + ((estado_ot == 1) ? "<span class='has-changes'></span>" : "") + "</li>");
                        out.print("<li class='" + ((estado_ot >= 3) ? "is-complete" : "") + ((estado_ot == 3) ? " is-hovered" : "") + "'><span>Ejecutado</span>" + ((estado_ot == 2) ? "<span class='has-changes'></span>" : "") + "</li>");
                        out.print("<li class='" + ((estado_ot >= 4) ? "is-complete" : "") + ((estado_ot == 4) ? " is-hovered" : "") + "'><span>Verificado</span>" + ((estado_ot == 3) ? "<span class='has-changes'></span>" : "") + "</li>");
                        out.print("<li class='" + ((estado_ot >= 5) ? "is-complete" : "") + ((estado_ot == 5) ? " is-hovered" : "") + "'><span>Recibido</span>" + ((estado_ot == 4) ? "<span class='has-changes'></span>" : "") + "</li>");
                        out.print("<li class='" + ((estado_ot >= 6) ? "is-complete" : "") + ((estado_ot == 6) ? " is-hovered" : "") + "'><span>Cerrado</span>" + ((estado_ot == 5) ? "<span class='has-changes2'></span>" : "") + "</li>");
                        out.print("</ol>");
                        out.print("</section>");
//</editor-fold>
                        out.print("<form action='Orden?opc=4' method='post'>");
                        out.print("<input type='hidden' value='" + id_equipo + "' name='Id_equipo' />");
                        out.print("<input type='hidden' value='" + id_orden + "' name='Id_orden' />");
                        out.print("<input type='hidden' value='" + estado_ot + "' name='Estado_ot' />");
                        if (estado_ot == 2) {
                            out.print("<p>La OT se encuentra en estado de ejecución , al pulsar avanzar el sistema envia la OT para revisión.</p>");
                        } else if (estado_ot == 3) {
                            out.print("<p>La OT se encuentra en Ejecutada, al pulsar avanzar el sistema envia la OT como verificada para recibir por Calidad y Producción.</p>");
                        } else if (estado_ot == 4) {
                            out.print("<p>La OT se encuentra en Verificada, al pulsar avanzar el sistema envia la OT como recibida por  Calidad y Producción.</p>");
                        } else if (estado_ot == 5) {
                            out.print("<p>La OT se encuentra recibida, al pulsar avanzar el sistema da por terminada la OT.</p>");
                        }
                        out.print("<input type='submit' value='Enviar' style='display:none' id='Btn_ot_estado' />");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                            if (estado_ot == 2) {
                                out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                            } else if (estado_ot == 3) {
                                if (id_tipo_mtto == 1) {
                                    out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                                }
                            } else if (estado_ot == 4) {
                                if (id_tipo_mtto == 1) {
                                    out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                                }
                            }
                        }
                        if (estado_ot >= 3) {
                            if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinador") || nombre_rol.equals("Asistente") || nombre_rol.equals("Jefe_Mtto")) {
                                out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                            }
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES DE SEGURIDAD">
                        lst_instruccion_seguridad = jpacitc.Instruccion_seguridad();
                        Object[] obj_instruccion = (Object[]) lst_instruccion_seguridad.get(0);
                        out.print("<div class=\"modal fade\" id=\"SeguridadModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\" style='width:250%;font-size:none;font-size:9px;left:-350px'>");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Instrucciones de Seguridad</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        out.print("<table class=\"table2\" style='font-size:9px; margin:0px'>");
                        out.print("<tr>");
                        out.print("<td align='center' style='width:25%;' colspan='2'>");
                        out.print("<img src='Interfaz/Images/Logo.png' alt='Logo'style='width:60%' /></td>");
                        out.print("<td colspan='4' align='center' style='width:50%;'>" + obj_instruccion[1] + "</td>");
                        out.print("<td align='center' style='width:20%;'><b>ULT ACTUALIZADO : </b><br />" + obj_instruccion[4] + "<br />" + obj_instruccion[5] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th colspan='7'>CONTENIDO</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='7'>" + obj_instruccion[2] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="FICHA TECNICA">
                        out.print("<div class=\"modal fade\" id=\"FTModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel2\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\" style='width:250%;font-size:none;font-size:9px;left:-350px'>");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel2\">Ficha Tecnica del Equipo</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO">
                        out.print("<table class=\"table2\" style='font-size:9px; margin:0px'>");
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
                        out.print("<table class=\"table2\" style='font-size:9px; margin:0px;'>");
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
                        out.print("<table class=\"table2\" style='font-size:9px;margin:0px;'>");
                        out.print("<tr>");
                        out.print("<td colspan='6' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>CARACTERISITICAS TECNICAS</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%;font-weight:bold;text-align:center;'>ITEM</td>");
                        out.print("<td style='width:20%;font-weight:bold;text-align:center;'>CARACTERISTICAS GENERAL</td>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;'>UNIDAD</td>");
                        out.print("<td style='width:17%;font-weight:bold;text-align:center;'>VALOR</td>");
                        out.print("<td style='width:15%;font-weight:bold;text-align:center;'>ESTADO</td>");
                        out.print("<td style='width:30%;font-weight:bold;text-align:center;'>INFORMACIÓN ADICIONAL</td>");
                        out.print("</tr>");
                        if (lst_ficha != null) {
                            for (int i = 0; i < lst_ficha.size(); i++) {
                                Object[] obj_ficha = (Object[]) lst_ficha.get(i);
                                if (obj_ficha[4].equals("N/A")) {
                                    out.print("<form action='Equipo?opc=6&Id_equipo=" + id_equipo + "' method='post' id='FormCaracteristicasT'>");
                                    out.print("<tr>");
                                    out.print("<input type='hidden' name='Txt_tipo'  value='1'>");
                                    out.print("<input type='hidden' name='Id_ficha_tecnica' value='" + obj_ficha[0] + "'>");
                                    out.print("<td style='width:20%;' align='center'><input type='number' class='form-control' name='Txt_item' value='" + obj_ficha[3] + "' required /></td>");
                                    out.print("<td style='width:30%' align='center'><input type='text' class='form-control' name='Txt_caracteristicas_generales' required /></td>");
                                    out.print("<td style='width:10%;' align='center'><input type='text' class='form-control' name='Txt_unidad' required /></td>");
                                    out.print("<td style='width:15%;' align='center'><input type='text' class='form-control' name='Txt_valor' required /></td>");
                                    out.print("<td style='width:15%;' align='center'><input type='text' class='form-control' name='Txt_estado' required /></td>");
                                    out.print("<td style='width:30%;' align='center'><input type='text' class='form-control'  name='Txt_informacion_adicional' required /></td>");
                                    out.print("<div style='display:none'><input type='submit' id='Btn_ft_fase1' /></div>");
                                    out.print("</tr>");
                                    out.print("</form>");
                                } else {
                                    String arrg_datos[] = obj_ficha[4].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                    out.print("<tr>");
                                    out.print("<td style='width:20%;' align='center'>" + obj_ficha[3] + "</td>");
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
                        out.print("<table class=\"table2\" style='font-size:9px; margin:0px;'>");
                        out.print("<tr>");
                        out.print("<td colspan='11' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>CARACTERISITICAS ESPECIFICAS</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>ITEM</td>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>CODIGO</td>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>NOMBRE INSTRUMENTO</td>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>FABRICANTE</td>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>CODIGO/MODELO</td>");
                        out.print("<td style='width:6%;font-weight:bold;text-align:center;' rowspan='2'>No SERIE</td>");
                        out.print("<td style='width:6%;font-weight:bold;text-align:center;' rowspan='2'>RANGO</td>");
                        out.print("<td style='width:10%;font-weight:bold;text-align:center;' rowspan='2'>UBICACIÓN</td>");
                        out.print("<td style='width:4%;font-weight:bold;text-align:center;'  colspan='2'>V.I</td>");
                        out.print("<td style='width:21%;font-weight:bold;text-align:center;' rowspan='2'>OBSERVACIONES</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='text-align:center;'>SI</td>");
                        out.print("<td style='text-align:center;'>NO</td>");
                        out.print("</tr>");
                        if (lst_ficha != null) {
                            for (int r = 0; r < lst_ficha.size(); r++) {
                                Object[] obj_ficha = (Object[]) lst_ficha.get(r);
                                if (obj_ficha[4].equals("N/A")) {
                                    out.print("<form action='Equipo?opc=6&Id_equipo=" + id_equipo + "' method='post' id='FormCaracteristicasE'>");
                                    out.print("<tr>");
                                    out.print("<input type='hidden' name='Id_ficha_tecnica' value='" + obj_ficha[0] + "'>");
                                    out.print("<input type='hidden' name='Txt_tipo'  value='2'>");
                                    out.print("<td align='center'><input type='text' class='form-control' name='Txt_item' id='Txt_item' value='" + obj_ficha[3] + "' required /></td>");
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
                                } else {
                                    String arrg_datos[] = obj_ficha[4].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                    out.print("<tr>");
                                    out.print("<td align='center'>" + obj_ficha[3] + "</td>");
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
                        out.print("<table class='table2' style='font-size:9px;'>");
                        out.print("<tr><td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;text-align: center;'>OBSERVACIONES</td></tr>");
                        out.print("<tr><td valign='top' style='padding:20px'>" + obj_equipo[23] + "</td></tr>");
                        out.print("</table>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    } else if (id_equipo == 47 || id_equipo == 37 || id_equipo == 48) {
                        out.print("<center><h5>Esta orden de trabajo no contiene información, debido a historico en los equipos de Autoclave</h5></center>");
                    } else {
                        out.print("<center><h5>No existe información de la Orden.</h5></center>");
                    }
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_origen_trabajo_emision")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_origen_trabajo_emision ZONAS Y GENERAL">
                    id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden").toString());
                    id_tipo_mtto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_mtto").toString());
                    if (id_tipo_mtto == 3) {
                        lst_orden = jpacotb.Orden_trabajo_zona_id(id_orden);
                    } else {
                        lst_orden = jpacotb.Orden_trabajo_general_id(id_orden);
                    }
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    estado_ot = Integer.parseInt(obj_orden[14].toString());
                    if (id_tipo_mtto == 3) {
                        lst_origen = jpaczna.Zona_id(Integer.parseInt(obj_orden[3].toString()));
                    } else {
                        lst_origen = jpacgnr.Actividad_general_id(Integer.parseInt(obj_orden[3].toString()));
                    }
                    Object[] obj_origen = (Object[]) lst_origen.get(0);
                    registro = obj_origen[8].toString();
                    lst_registro = jpacrgt.Traer_registro_ot(registro, obj_orden[5].toString());
                    if (lst_registro != null) {
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        registro = obj_registro[1].toString();
                        version = obj_registro[3].toString();
                        titulo = obj_registro[2].toString();
                        titulo2 = "N/A";
                        nota = obj_registro[4].toString();
                        instrucciones = obj_registro[5].toString();
                    } else {
                        registro = registro;
                        version = "N/A";
                        titulo = "N/A";
                        titulo2 = "N/A";
                        nota = "N/A";
                        instrucciones = "N/A";
                    }
//                    titulo = ((id_tipo_mtto == 3) ? "Listado de actividades OT Zonas" : "Listado de actividades OT General");
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Orden?opc=10&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=" + obj_orden[3] + "' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        out.print("<div style='float:right'><span class='fas fa-ellipsis-h fa-size_small' data-toggle=\"modal\" data-target=\"#ProgressBarModal\"></span></div>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                    out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                    out.print("<td align='center' style='width:50%;'>CODIGO " + registro + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                    out.print("<td align='center'>VERSIÓN: " + version + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE CABECERA">
                    out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr>");
                    out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' colspan='6' align='center'>Descripción " + ((id_tipo_mtto == 3) ? "de Zona" : "General") + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>SEMANA</th>");
                    out.print("<td align='center'><b>" + obj_orden[19] + "</b></td>");
                    out.print("<th>O.T No :</th>");
                    out.print("<td align='center'><b>" + obj_orden[1] + "</b></td>");
                    out.print("<th>T.T. MIN:</th>");
                    out.print("<td align='center'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>" + ((id_tipo_mtto == 3) ? "ZONA" : "ACTIVIDAD GENERAL") + "</th>");
                    out.print("<td align='center'>" + obj_origen[1] + "</td>");
                    out.print("<th>" + ((id_tipo_mtto == 3) ? "LINEAS" : "DESCRIPCIÓN") + "</th>");
                    out.print("<td align='center' colspan='3' style='width:60%'>" + obj_origen[2] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='6'><b>NOTA IMPORTANTE :</b>" + nota + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                    out.print("<table class=\"table2\">");
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;'>DESCRIPCIÓN DE LA ACTIVIDAD A REALIZAR</td>");
                    out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>EJECUTO</td>");
                    if (id_tipo_mtto > 1) {
                        out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>TRABAJOS A EJECUTAR</td>");
                    }
                    out.print("</tr>");
                    if (id_tipo_mtto == 3) {
                        lst_grupos_parametros = jpacpzn.Grupos_parametro_zona(Integer.parseInt(obj_orden[3].toString()));
                    } else {
                        lst_grupos_parametros = jpacpgn.Grupos_parametro_general(Integer.parseInt(obj_orden[3].toString()));
                    }
                    for (int i = 0; i < lst_grupos_parametros.size(); i++) {
                        Object[] obj_grupos_parametro = (Object[]) lst_grupos_parametros.get(i);
                        if (id_tipo_mtto == 3) {
                            lst_parametros = jpacpzn.Parametros_grupo_zona(Integer.parseInt(obj_orden[3].toString()), obj_grupos_parametro[0].toString());
                        } else {
                            lst_parametros = jpacpgn.Parametros_grupo_general(Integer.parseInt(obj_orden[3].toString()), obj_grupos_parametro[0].toString());
                        }
                        if (lst_parametros != null) {
                            arg_grupos += "[" + obj_grupos_parametro[0] + "]";
                            out.print("<tr>");
                            out.print("<th style='width:5%'>ITEM</th>");
                            out.print("<th>" + obj_grupos_parametro[0] + "</th>");
                            out.print("<th>SI</th>");
                            out.print("<th>NO</th>");
                            out.print("<th>N/A</th>");
                            if (id_tipo_mtto > 1) {
                                out.print("<th></th>");
                            }
                            out.print("</tr>");
                            for (int j = 0; j < lst_parametros.size(); j++) {
                                Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                                arg_actividades += "[" + obj_parametros[0] + "_/_NO_/_N/A]";
                                if (i == 0 && j == 0) {
                                    arg_actividades_id = "" + obj_parametros[0] + "";
                                } else {
                                    arg_actividades_id = arg_actividades_id + "," + obj_parametros[0] + "";
                                }
                                cont_actividades_programadas++;
                                out.print("<tr>");
                                out.print("<td align='center'>" + (j + 1) + "</td>");
                                out.print("<td>" + obj_parametros[2] + "</td>");
                                out.print("<td><b></b></td>");
                                out.print("<td><b></b></td>");
                                out.print("<td><b></b></td>");
                                if (id_tipo_mtto > 1) {
                                    out.print("<td><b></b></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REPONSABLES">
                    out.print("<table class='table2'>");
                    out.print("<tr>");
                    out.print("<td colspan='4' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>RESPONSABLES</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Programador</b><br />" + obj_orden[4] + "<br />" + ((obj_orden[5] == null) ? "Pendiente" : obj_orden[5]) + "</td>");
                    out.print("<td><b>Ejecutor</b><br />" + obj_orden[6] + "<br />" + ((obj_orden[7] == null) ? "Pendiente" : obj_orden[7]) + "</td>");
                    out.print("<td><b>Revisor</b><br />" + obj_orden[8] + "<br />" + ((obj_orden[9] == null) ? "Pendiente" : obj_orden[9]) + "</td>");
                    out.print("<td><b>Aprobador</b><br />" + obj_orden[20] + "<br />" + ((obj_orden[21] == null) ? "Pendiente" : obj_orden[21]) + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES GENERALES">
                    out.print("<table class='table2'>");
                    out.print("<tr>");
                    out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>OBSERVACIONES GENERALES</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign='top' style='padding:20px'>"
                            + "<b>1-</b>COLOQUE UNA X SI Ó NO REALIZO LA ACTIVIDAD DE ACUERDO A LO SOLICITADO, EN CASO DE NO EJECUTAR LA ACTIVIDAD ESPECIFIQUE EN OBSERVACIONES EL MOTIVO POR EL CUAL NO SE REALIZO."
                            + "<br /><b>2-</b>VERIFIQUE LA FUNCIONALIDAD DEL EQUIPO ANTES Y DESPUÉS DE REALIZAR LAS ACTIVIDADES DE MANTENIMIENTO, ENTREGUE EL EQUIPO AL COORDINADOR. JEFE DE ÁREA, PERSONA ENCARGADA O PERSONAL DE CALIDAD."
                            + "<br /><b>3-</b>LA FECHA DE EJECUCIÓN DEL MANTENIMIENTO PUEDE SER MODIFICADA DE ACUERDO A LAS PRIORIDADES DE PRODUCCIÓN, CALIDAD O MANTENIMIENTO SEGÚN SE ENCUENTRE INDICADO EN EL REGISTRO, PARA LO CUAL SE PROCEDERÁ A REALIZARLO EN EL MOMENTO DE DISPONIBILIDAD DEL MISMO Y ANTES DE LA PRÓXIMA FECHA DE MANTENIMIENTO PREVENTIVO."
                            + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="PROGRESS BAR MODAL">
                        out.print("<div class=\"modal fade\" id=\"ProgressBarModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                        out.print("<div class=\"modal-dialog\" role=\"document\">");
                        out.print("<div class=\"modal-content\">");
                        out.print("<div class=\"modal-header\">");
                        out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Avanzar Orden de Trabajo</h5>");
                        out.print("</div>");
                        out.print("<div class=\"modal-body\">");
                        //<editor-fold defaultstate="collapsed" desc="progress_bar">
                        out.print("<section class='progress_bar'>");
                        out.print("<ol class='progress_bar'>");
                        out.print("<li class='is-complete" + ((estado_ot == 1) ? " is-hovered" : "") + "'><span>Programado</span></li>");
                        out.print("<li><span>Emitido</span><span class='has-changes'></span></li>");
                        out.print("<li><span>Ejecutado</span></li>");
                        out.print("<li><span>Revisado</span></li>");
                        out.print("<li><span>Aprobado</span></li>");
                        out.print("</ol>");
                        out.print("</section>");
//</editor-fold>
                        out.print("<form action='Orden?opc=12' method='post'>");
                        out.print("<input type='hidden' value='" + obj_orden[3] + "' name='Id_origen' />");
                        out.print("<input type='hidden' value='" + id_orden + "' name='Id_orden' />");
                        out.print("<input type='hidden' value='" + id_tipo_mtto + "' name='Id_tipo_mtto' />");
                        out.print("<input type='hidden' value='" + estado_ot + "' name='Estado_ot' />");
                        if (estado_ot >= 1) {
                            out.print("<p>La OT se encuentra pendiente por emitir, al pulsar avanzar el sistema libera la OT para iniciar con la Ejecución por parte del tecnico ejecutor.</p>");
                            out.print("<input type='hidden' value='" + arg_grupos + "' name='Arg_grupos' />");
                            out.print("<input type='hidden' value='" + arg_actividades_id + "' name='Arg_id_actividades' />");
                            out.print("<input type='hidden' value='[" + arg_actividades_id.replace(",", "][") + "]' name='Arg_id_actividades_pendientes' />");
                            out.print("<input type='hidden' value='" + arg_actividades + "' name='Arg_actividades' />");
                            out.print("<input type='hidden' value='" + cont_actividades_programadas + "' name='Cont_actividades_programadas' />");
                            out.print("<input type='hidden' value='" + ((id_tipo_mtto == 3) ? obj_origen[7] : "N/A") + "' name='Arg_id_lineas_zona' />");
                        }
                        out.print("<input type='hidden' value='" + estado_ot + "' name='Estado_ot' />");
                        out.print("<input type='submit' value='Enviar' style='display:none' id='Btn_ot_estado' />");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("<div class=\"modal-footer\">");
                        out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                        out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Orden").toString().equals("Orden_origen_trabajo_gestion")) {
                    //<editor-fold defaultstate="collapsed" desc="Orden_origen_trabajo_gestion ZONAS Y GENERAL">
                    id_orden = Integer.parseInt(pageContext.getRequest().getAttribute("Id_orden").toString());
                    id_tipo_mtto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_mtto").toString());
                    if (id_tipo_mtto == 3) {
                        lst_orden = jpacotb.Orden_trabajo_zona_id(id_orden);
                    } else {
                        lst_orden = jpacotb.Orden_trabajo_general_id(id_orden);
                    }
                    Object[] obj_orden = (Object[]) lst_orden.get(0);
                    estado_ot = Integer.parseInt(obj_orden[14].toString());
                    lst_orden_detalle = jpacotd.Consulta_detalle_orden_trabajo(id_orden);
                    Object[] obj_orden_detalle = (Object[]) lst_orden_detalle.get(0);
                    arg_grupos_ot = obj_orden_detalle[2].toString().replace("][", "_/_").replace("[", "").replace("]", "").split("_/_");
                    arg_actividades_ot = obj_orden_detalle[4].toString().replace("][", "_//_").replace("[", "").replace("]", "").split("_//_");
                    if (id_tipo_mtto == 3) {
                        lst_origen = jpaczna.Zona_id(Integer.parseInt(obj_orden[3].toString()));
                    } else {
                        lst_origen = jpacgnr.Actividad_general_id(Integer.parseInt(obj_orden[3].toString()));
                    }
                    Object[] obj_origen = (Object[]) lst_origen.get(0);
                    registro = obj_origen[8].toString();
                    lst_registro = jpacrgt.Traer_registro_ot(registro, obj_orden[5].toString());
                    if (lst_registro != null) {
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        registro = obj_registro[1].toString();
                        version = obj_registro[3].toString();
                        titulo = obj_registro[2].toString();
                        titulo2 = "N/A";
                        nota = obj_registro[4].toString();
                        instrucciones = obj_registro[5].toString();
                    } else {
                        registro = registro;
                        version = "N/A";
                        titulo = "N/A";
                        titulo2 = "N/A";
                        nota = "N/A";
                        instrucciones = "N/A";
                    }
//                    titulo = ((id_tipo_mtto == 3) ? "Listado de actividades OT Zonas" : "Listado de actividades OT General");
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Orden?opc=10&Id_tipo_mtto=" + id_tipo_mtto + "&Id_origen=" + obj_orden[3] + "' />");
                    out.print("<div style='float:right'>");
                    out.print("<span class='fas fa-print fa-size_small' onclick=\"Imprimir();\" title='Imprimir'></span>");
                    out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-hard-hat fa-size_small' data-toggle=\"modal\" data-target=\"#SeguridadModal\" title='Instrucciones de Seguridad'></span>");
                    if (estado_ot <= 5) {
                        out.print("&nbsp;&nbsp;&nbsp;<span class='fa fa-save fa-size_small' onclick=\"ConstruirDatos('" + obj_orden_detalle[3] + "')\"></span>");
                    }
                    if (estado_ot >= 3 && estado_ot <= 5) {
                        if (!nombre_rol.equals("Tecnico") && !nombre_rol.equals("Consulta")) {
                            out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-exchange-alt fa-size_small' onclick='DevolverOTGZ(" + id_equipo + "," + id_orden + "," + estado_ot + "," + id_tipo_mtto + "," + 2 + ")'  title='Devolver'></span>");
                        }
                    }
                    if (estado_ot <= 5) {
                        out.print("&nbsp;&nbsp;&nbsp;<span class='fas fa-ellipsis-h fa-size_small' data-toggle=\"modal\" data-target=\"#ProgressBarModal\" title='Gestión OT'></span>");
                    }
                    out.print("</div>");
                    out.print("<div id='Div_export'>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr><td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Images/Logo.png' alt='Logo' style='width:60%' /></td>");
                    out.print("<td colspan='4' align='center' style='width:50%;'>REGISTRO</td>");
                    out.print("<td align='center' style='width:50%;'>CODIGO " + registro + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4' align='center'>" + titulo + "</td>");
                    out.print("<td align='center'>VERSIÓN: " + version + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE CABECERA">
                    out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr>");
                    out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' colspan='6' align='center'>Descripción " + ((id_tipo_mtto == 3) ? "de Zona" : "General") + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>SEMANA</th>");
                    out.print("<td align='center'><b>" + obj_orden[19] + "</b></td>");
                    out.print("<th>O.T No :</th>");
                    out.print("<td align='center'><b>" + obj_orden[1] + "</b></td>");
                    out.print("<th>T.T. MIN:</th>");
                    if (estado_ot == 2) {
                        if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                            out.print("<td align='center'><input style='width:150px' class='form-control' type='number' id='Txt_tiempo_trabajo' value='" + ((obj_orden[27] == null) ? 0 : obj_orden[27]) + "' /></td>");
                        } else {
                            out.print("<td align='center'>" + ((obj_orden[27] == null) ? 0 : obj_orden[27]) + "<input type='hidden' id='Txt_tiempo_trabajo' value='" + ((obj_orden[27] == null) ? 0 : obj_orden[27]) + "' /></td>");
                        }
                    } else {
                        out.print("<td align='center'>" + ((obj_orden[27] == null) ? 0 : obj_orden[27]) + "<input type='hidden' id='Txt_tiempo_trabajo' value='" + ((obj_orden[27] == null) ? 0 : obj_orden[27]) + "' /></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>" + ((id_tipo_mtto == 3) ? "ZONA" : "ACTIVIDAD GENERAL") + "</th>");
                    out.print("<td align='center'>" + obj_origen[1] + "</td>");
                    out.print("<th>" + ((id_tipo_mtto == 3) ? "LINEAS" : "DESCRIPCIÓN") + "</th>");
                    out.print("<td align='center' colspan='3' style='width:60%'>" + obj_origen[2] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='6'><b>NOTA IMPORTANTE :</b>" + nota + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES">
                    out.print("<table class=\"table2\" style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr>");
                    out.print("<td colspan='2' align='center' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;'>DESCRIPCIÓN DE LA ACTIVIDAD A REALIZAR</td>");
                    out.print("<td colspan='3' align='center' style='width:15%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>EJECUTO</td>");
                    out.print("<td align='center' style='width:35%;color: #fff;background-color:#4e73df;border: 1px solid #fff;'>TRABAJOS A EJECUTAR</td>");
                    out.print("</tr>");
                    for (int i = 0; i < arg_grupos_ot.length; i++) {
                        out.print("<tr>");
                        out.print("<th style='width:5%'>ITEM</th>");
                        out.print("<th>" + arg_grupos_ot[i] + "</th>");
                        out.print("<th>SI</th>");
                        out.print("<th>NO</th>");
                        out.print("<th>N/A</th>");
                        out.print("<th></th>");
                        out.print("</tr>");
                        if (id_tipo_mtto == 3) {
                            lst_parametros = jpacotd.Consulta_actividades_grupo_ids_zona(arg_grupos_ot[i], obj_orden_detalle[3].toString());
                        } else {
                            lst_parametros = jpacotd.Consulta_actividades_grupo_ids_general(arg_grupos_ot[i], obj_orden_detalle[3].toString());
                        }
                        for (int j = 0; j < lst_parametros.size(); j++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(j);
                            out.print("<tr>");
                            out.print("<td align='center'>" + (j + 1) + "</td>");
                            out.print("<td>" + obj_parametros[2] + "</td>");
                            String valor_ejecuto = "";
                            String valor_observacion = "";
                            int cont_control = 0;
                            for (int k = 0; k < arg_actividades_ot.length; k++) {
                                if (arg_actividades_ot[k].contains(obj_parametros[0] + "_/_")) {
                                    valor_ejecuto = arg_actividades_ot[k].split("_/_")[1];
                                    valor_observacion = arg_actividades_ot[k].split("_/_")[2];
                                    cont_control++;
                                    break;
                                }
                                if (cont_control > 0) {
                                    break;
                                }
                            }
                            if (estado_ot >= 3) {
                                out.print("<td align='center'>" + ((valor_ejecuto.equals("SI")) ? "X" : "") + "</td>");
                                out.print("<td align='center'>" + ((valor_ejecuto.equals("NO")) ? "X" : "") + "</td>");
                                out.print("<td align='center'>" + ((valor_ejecuto.equals("N/A")) ? "X" : "") + "</td>");
                            } else if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                                out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("SI")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",1)' value='1' /></td>");
                                out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("NO")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",2)' value='2' /></td>");
                                out.print("<td align='center'><input type='radio' name='Cbx_ejecuto_" + obj_parametros[0] + "' " + ((valor_ejecuto.equals("N/A")) ? "checked" : "") + " onclick='ActividadEjecuto(" + obj_parametros[0] + ",3)' value='3' /></td>");
                            } else {
                                out.print("<td align='center'>" + ((valor_ejecuto.equals("SI")) ? "X" : "") + "</td>");
                                out.print("<td align='center'>" + ((valor_ejecuto.equals("NO")) ? "X" : "") + "</td>");
                                out.print("<td align='center'>" + ((valor_ejecuto.equals("N/A")) ? "X" : "") + "</td>");
                            }
                            out.print("<i style='display:none' id='Txt_ejecuto_" + obj_parametros[0] + "'>" + valor_ejecuto + "</i>");
                            out.print("<td valign='top' id='Txt_observacion_" + obj_parametros[0] + "' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "'>" + valor_observacion + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="NO USADOS">
                    out.print("<div style='display:none'>");
                    out.print("<table>");
                    out.print("<tr><td id='Td_pendientes_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;padding:20px'>" + obj_orden_detalle[7] + "</td>");
                    out.print("<td id='Td_descripcion_ot' colspan='5' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "' valign='top' style='height:100px;;padding:20px'>" + obj_orden_detalle[8] + "</td></tr>");
                    out.print("</table>");
                    out.print("<table>");
                    out.print("<tbody id='Tr_respuestos_ot'>");
                    if (obj_orden_detalle[9].toString().length() > 0) {
                        out.print(obj_orden_detalle[9]);
                    } else {
                        out.print("<tr>");
                        out.print("<td align='center' style='width:5%'>1</td><td colspan='4' contenteditable='" + ((estado_ot >= 3) ? "false" : "true") + "'>N/A</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REPONSABLES">
                    out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr>");
                    out.print("<td colspan='4' style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>RESPONSABLES</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Programador</b><br />" + obj_orden[4] + "<br />" + ((obj_orden[5] == null) ? "Pendiente" : obj_orden[5]) + "</td>");
                    out.print("<td><b>Ejecutor</b><br />" + obj_orden[6] + "<br />" + ((obj_orden[7] == null) ? "Pendiente" : obj_orden[7]) + "</td>");
                    out.print("<td><b>Revisor</b><br />" + obj_orden[8] + "<br />" + ((obj_orden[9] == null) ? "Pendiente" : obj_orden[9]) + "</td>");
                    out.print("<td><b>Aprobador</b><br />" + obj_orden[20] + "<br />" + ((obj_orden[21] == null) ? "Pendiente" : obj_orden[21]) + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="OBSERVACIONES GENERALES">
                    out.print("<table class='table2' style='font-size:12px; width:100%;margin:0px'>");
                    out.print("<tr>");
                    out.print("<td style='color: #fff;background-color:#4e73df;border: 1px solid #fff;' align='center'>OBSERVACIONES GENERALES</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign='top' style='padding:20px'>" + instrucciones
                            + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
//</editor-fold>
                    out.print("</div>");
                    if (estado_ot <= 5) {
                        //<editor-fold defaultstate="collapsed" desc="FORMULARIO">
                        out.print("<div style='display:none'>");
                        out.print("<form action='Orden?opc=14' method='post' id='FormGuardarOt'>");
                        out.print("<input type='text' name='Id_orden' value='" + id_orden + "'>");
                        out.print("<input type='text' name='Id_tipo_mtto' value='" + id_tipo_mtto + "'>");
                        out.print("<input type='text' name='Id_orden_detalle' value='" + obj_orden_detalle[0] + "'>");
                        out.print("<input type='text' name='Arg_grupos' value='" + obj_orden_detalle[2] + "'>");
                        out.print("<input type='text' name='Arg_id_actividades' value='" + obj_orden_detalle[3] + "'>");
                        out.print("<input type='text' id='Arg_id_actividades_pendientes' name='Arg_id_actividades_pendientes' value='" + obj_orden_detalle[14] + "'>");
                        out.print("<textarea id='Arg_actividades' name='Arg_actividades'>" + obj_orden_detalle[4] + "</textarea>");
                        out.print("<input type='text' name='Cont_actividades_programadas' value='" + obj_orden_detalle[5] + "'>");
                        out.print("<input type='text' id='Cont_actividades_ejecutadas' name='Cont_actividades_ejecutadas' value='" + obj_orden_detalle[6] + "'>");
                        out.print("<textarea id='Txt_datos_pendientes' name='Txt_datos_pendientes'>" + obj_orden_detalle[7] + "</textarea>");
                        out.print("<textarea id='Txt_datos_descripcion' name='Txt_datos_descripcion'>" + obj_orden_detalle[8] + "</textarea>");
                        out.print("<textarea id='Txt_datos_repuestos' name='Txt_datos_repuestos'>" + obj_orden_detalle[9] + "</textarea>");
                        out.print("<input type='text' id='No_pmp' name='No_pmp' value='" + obj_orden_detalle[10] + "'>");
                        out.print("<input type='text' id='Arg_id_lineas_zona' name='Arg_id_lineas_zona' value='" + obj_orden_detalle[15] + "'>");
                        out.print("<input type='text' id='Tiempo_trabajo' name='Tiempo_trabajo' value='" + ((obj_orden[27] == null) ? 0 : obj_orden[27]) + "'>");
                        out.print("</form>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="PROGRESS BAR MODAL">
                    out.print("<div class=\"modal fade\" id=\"ProgressBarModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                    out.print("<div class=\"modal-dialog\" role=\"document\">");
                    out.print("<div class=\"modal-content\">");
                    out.print("<div class=\"modal-header\">");
                    out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Avanzar Orden de Trabajo</h5>");
                    out.print("</div>");
                    out.print("<div class=\"modal-body\">");
                    //<editor-fold defaultstate="collapsed" desc="progress_bar">
                    out.print("<section class='progress_bar'>");
                    out.print("<ol class='progress_bar'>");
                    out.print("<li class='is-complete" + ((estado_ot == 1) ? " is-hovered" : "") + "'><span>Programado</span></li>");
                    out.print("<li class='" + ((estado_ot >= 2) ? "is-complete" : "") + ((estado_ot == 2) ? " is-hovered" : "") + "'><span>Emitido</span>" + ((estado_ot == 1) ? "<span class='has-changes'></span>" : "") + "</li>");
                    out.print("<li class='" + ((estado_ot >= 3) ? "is-complete" : "") + ((estado_ot == 3) ? " is-hovered" : "") + "'><span>Ejecutado</span>" + ((estado_ot == 2) ? "<span class='has-changes'></span>" : "") + "</li>");
                    out.print("<li class='" + ((estado_ot >= 4) ? "is-complete" : "") + ((estado_ot == 4) ? " is-hovered" : "") + "'><span>Revisado</span>" + ((estado_ot == 3) ? "<span class='has-changes'></span>" : "") + "</li>");
                    out.print("<li class='" + ((estado_ot >= 6) ? "is-complete" : "") + ((estado_ot == 6) ? " is-hovered" : "") + "'><span>Aprobado</span>" + ((estado_ot >= 4 && estado_ot < 6) ? "<span class='has-changes2'></span>" : "") + "</li>");
                    out.print("</ol>");
                    out.print("</section>");
//</editor-fold>
                    out.print("<form action='Orden?opc=12' method='post'>");
                    out.print("<input type='hidden' value='" + obj_orden[3] + "' name='Id_origen' />");
                    out.print("<input type='hidden' value='" + id_tipo_mtto + "' name='Id_tipo_mtto' />");
                    out.print("<input type='hidden' value='" + id_orden + "' name='Id_orden' />");
                    out.print("<input type='hidden' value='" + estado_ot + "' name='Estado_ot' />");
                    if (estado_ot == 2) {
                        out.print("<p>La OT se encuentra en estado de ejecución , al pulsar avanzar el sistema envia la OT para revisión.</p>");
                    } else if (estado_ot == 3) {
                        out.print("<p>La OT se encuentra en Ejecutada, al pulsar avanzar el sistema envia la OT como Revisada para Aprobación.</p>");
                    } else if (estado_ot >= 4) {
                        out.print("<p>La OT se encuentra en Revisada, al pulsar avanzar el sistema envia la OT como Aprobado.</p>");
                    }
                    out.print("<input type='submit' value='Enviar' style='display:none' id='Btn_ot_estado' />");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<div class=\"modal-footer\">");
                    out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Tecnico")) {
                        if (estado_ot == 2) {
                            out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                        }
                    } else if (estado_ot == 3) {
                        if (nombre_rol.equals("Administrador") || nombre_rol.equals("Coordinador")) {
                            out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                        }
                    } else if (estado_ot >= 4) {
                        if (nombre_rol.equals("Administrador") || nombre_rol.equals("Asistente") || nombre_rol.equals("Jefe_Mtto")) {
                            out.print("<a class=\"btn btn-primary\" href='#' onclick=\"javascript:document.getElementById('Btn_ot_estado').click();\">Avanzar</a>");
                        }
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES DE SEGURIDAD">
                    lst_instruccion_seguridad = jpacitc.Instruccion_seguridad();
                    Object[] obj_instruccion = (Object[]) lst_instruccion_seguridad.get(0);
                    out.print("<div class=\"modal fade show\" id=\"SeguridadModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
                    out.print("<div class=\"modal-dialog\" role=\"document\">");
                    out.print("<div class=\"modal-content\" style='width:250%;font-size:none;font-size:9px;left:-350px'>");
                    out.print("<div class=\"modal-header\">");
                    out.print("<h5 class=\"modal-title\" id=\"exampleModalLabel\">Instrucciones de Seguridad</h5>");
                    out.print("</div>");
                    out.print("<div class=\"modal-body\">");
                    out.print("<table class=\"table2\" style='font-size:9px; margin:0px'>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:25%;' colspan='2'>");
                    out.print("<img src='Interfaz/Images/Logo.png' alt='Logo'style='width:60%' /></td>");
                    out.print("<td colspan='4' align='center' style='width:50%;'>" + obj_instruccion[1] + "</td>");
                    out.print("<td align='center' style='width:20%;'><b>ULT ACTUALIZADO : </b><br />" + obj_instruccion[4] + "<br />" + obj_instruccion[5] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='7'>CONTENIDO</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='7'>" + obj_instruccion[2] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class=\"modal-footer\">");
                    out.print("<button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancelar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
//</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_orden.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_orden.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
