package Tag;

import Controladores.CorreoJpaController;
import Controladores.GeneralJpaController;
import Controladores.InstruccionJpaController;
import Controladores.LineaJpaController;
import Controladores.ParametroGeneralJpaController;
import Controladores.ParametroJpaController;
import Controladores.ParametroZonaJpaController;
import Controladores.RegistroJpaController;
import Controladores.TipoEquipoJpaController;
import Controladores.TipoMttoJpaController;
import Controladores.ZonaJpaController;
import Controladores.RolJpaController;
import Controladores.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_complementos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            ///JPAS
            CorreoJpaController jpaccro = new CorreoJpaController();
            TipoEquipoJpaController jpacteq = new TipoEquipoJpaController();
            ParametroJpaController jpacprm = new ParametroJpaController();
            LineaJpaController jpaclna = new LineaJpaController();
            ZonaJpaController jpaczna = new ZonaJpaController();
            GeneralJpaController jpacgnr = new GeneralJpaController();
            ParametroZonaJpaController jpacpzn = new ParametroZonaJpaController();
            ParametroGeneralJpaController jpacpgn = new ParametroGeneralJpaController();
            InstruccionJpaController jpacitc = new InstruccionJpaController();
            RegistroJpaController jpacrgt = new RegistroJpaController();
            TipoMttoJpaController jpactmt = new TipoMttoJpaController();
            RolJpaController jpactrl = new RolJpaController();
            UsuarioJpaController jpausr = new UsuarioJpaController();
            ///VARIABLES
            int id_correo = 0;
            List lst_correos = null;
            List lst_instrucciones = null;
            List lst_correo = null;
            List lst_tipo_mtto = null;
            int id_tipo_equipo = 0;
            List lst_tipo_equipos = null;
            List lst_tipo_equipo = null;
            int id_parametro = 0;
            int id_tipo_mtto = 0;
            List lst_parametros = null;
            List lst_parametro = null;
            int id_linea = 0;
            List lst_lineas = null;
            List lst_linea = null;
            int id_zona = 0;
            List lst_zonas = null;
            List lst_zona = null;
            int id_general = 0;
            List lst_generales = null;
            List lst_general = null;
            int id_registro = 0;
            List lst_registro = null;
            List lst_registros = null;
            if (pageContext.getRequest().getAttribute("Complementos") != null) {
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Correos")) {
                    //<editor-fold defaultstate="collapsed" desc="CORREOS">
                    id_correo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_correo").toString());
                    //<editor-fold defaultstate="collapsed" desc="Registrar">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Correo</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                    out.print("<div class=\"card-body\" >");
                    out.print("<form action='Complementos?opc=2&Id_correo=0' method='post' id='FormMailRegister'>");
                    out.print("<div style='width:40%;float:left;'>");
                    out.print("<b>Función:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_funcion' id='Txt_funcion' placeholder='Función' title='Función' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_funcion');val1.add(Validate.Presence);</script>");
                    out.print("<b>Emisor :</b>");
                    out.print("<input class='form-control' type='email' name='Txt_emisor' id='Txt_emisor' placeholder='Emisor' title='Emisor' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_emisor');val1.add(Validate.Presence);</script>");
                    out.print("<b>Contraseña:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_password' id='Txt_password' placeholder='Contraseña' title='Contraseña' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password');val1.add(Validate.Presence);</script>");
                    out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                    out.print("<span onclick=\"javascript:location.href='Complementos?opc=1&Id_correo=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                    out.print("</div>");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                    out.print("<b>Host:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_host' id='Txt_hostM' placeholder='Host' title='Host' onchange='javascript:this.value=this.value.toLowerCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hostM');val1.add(Validate.Presence);</script>");
                    out.print("<b>Puerto:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_port' id='Txt_portM' placeholder='Port' title='Port'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_portM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                    out.print("<b>Receptor(es) :</b>");
                    out.print("<textarea class='form-control' type='text' name='Txt_receptor' id='Txt_receptor' placeholder='Receptor(es)' title='Receptor(es))' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_receptor');val1.add(Validate.Presence);</script>");
                    out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Modificar">
                    if (id_correo > 0) {
                        lst_correo = jpaccro.Correo_id(id_correo);
                        Object[] obj_correo = (Object[]) lst_correo.get(0);
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Correo</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\">");
                        out.print("<form action='Complementos?opc=2&Id_correo=" + id_correo + "' method='post' id='FormMailUpdate'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Función:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_funcion' id='Txt_funcionM' placeholder='Función' title='Función' value='" + obj_correo[1] + "' readonly='true' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_funcionM');val1.add(Validate.Presence);</script>");
                        out.print("<b>Emisor:</b>");
                        out.print("<input class='form-control' type='email' name='Txt_emisor' id='Txt_emisorM' placeholder='Emisor' title='Emisor' value='" + obj_correo[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_emisorM');val1.add(Validate.Presence);</script>");
                        out.print("<b>Contraseña:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_password' id='Txt_passwordM' placeholder='Contraseña' title='Contraseña' value='" + obj_correo[3] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_passwordM');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=1&Id_correo=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Host:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_host' id='Txt_hostM' placeholder='Host' title='Host' value='" + obj_correo[6] + "' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hostM');val1.add(Validate.Presence);</script>");
                        out.print("<b>Emisor:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_port' id='Txt_portM' placeholder='Port' title='Port' value='" + obj_correo[7] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_portM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Receptor(es) :</b>");
                        out.print("<textarea class='form-control' type='text' name='Txt_receptor' id='Txt_receptorM' placeholder='Receptor(es)' title='Receptor(es))' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_correo[4] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_receptorM');val1.add(Validate.Presence);</script>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Consulta">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Correos</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Funcion</th>");
                    out.print("<th>Emisor</th>");
                    out.print("<th>Password</th>");
                    out.print("<th>Host</th>");
                    out.print("<th>Port</th>");
                    out.print("<th>Receptor(es)</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Funcion</th>");
                    out.print("<th>Emisor</th>");
                    out.print("<th>Password</th>");
                    out.print("<th>Host</th>");
                    out.print("<th>Port</th>");
                    out.print("<th>Receptor(es)</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_correos = jpaccro.Correos();
                    if (lst_correos != null) {
                        for (int i = 0; i < lst_correos.size(); i++) {
                            Object[] obj_correos = (Object[]) lst_correos.get(i);
                            if (Integer.parseInt(obj_correos[5].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td>" + obj_correos[1] + "</td>");
                            out.print("<td>" + obj_correos[2] + "</td>");
                            out.print("<td>" + obj_correos[3] + "</td>");
                            out.print("<td>" + obj_correos[6] + "</td>");
                            out.print("<td>" + obj_correos[7] + "</td>");
                            out.print("<td>" + obj_correos[4] + "</td>");
                            if (Integer.parseInt(obj_correos[5].toString()) == 1) {
                                out.print("<td align='center'><span onclick='DesactivarCorreo(" + obj_correos[0] + ")' class='fa fa-check fa-size_small' title='Desactivar Correo'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Complementos?opc=1&Id_correo=" + obj_correos[0] + "'\" class='fa fa-pen fa-size_small' title='Editar Correo'></span></td>");
                            } else {
                                out.print("<td align='center'><span onclick='ActivarCorreo(" + obj_correos[0] + ")' class='fa fa-times fa-size_small' title='Activar Correo'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Correos Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Tipo_equipos")) {
                    //<editor-fold defaultstate="collapsed" desc="TIPO EQUIPOS">
                    id_tipo_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_equipo").toString());
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Tipo Equipo</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=5&Id_tipo_equipo=0' method='post' id='FormTypeEquipRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Tipo de equipo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_tipo_equipo' id='Txt_tipo_equipo' placeholder='Tipo de equipo' title='Tipo de equipo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo_equipo');val1.add(Validate.Presence);</script>");
                        out.print("<b>Frecuencia en dias:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_frecuencia' id='Txt_frecuencia' placeholder='Frecuencia' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Frecuencia en dias de alerta :</b>");
                        out.print("<input class='form-control' type='text' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alerta' placeholder='Frecuencia alerta' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alerta');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=4&Id_tipo_equipo=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Registros: </b><div id='Div_reg' class='input-group'>");
                        out.print("<div class='form-check form-check-inline'>");
                        out.print("<input class='form-check-input' onclick='TipoRegistroMtto(this.value)' type='radio' name='Rdb_tipo_registro' checked id='Rdb_tipo_registro' value='1'>");
                        out.print("<label class='form-check-label' for='inlineRadio1'>Mtto Inspección y Mayor</label>");
                        out.print("</div>");
                        out.print("<div class='form-check form-check-inline'>");
                        out.print("<input class='form-check-input' onclick='TipoRegistroMtto(this.value)' type='radio' name='Rdb_tipo_registro' id='Rdb_tipo_registro' value='0'>");
                        out.print("<label class='form-check-label' for='inlineRadio2'>Mtto Lubricación</label>");
                        out.print("</div></div>");
                        out.print("<b>Codigo MM:</b><div id='Div_mm' class='input-group'>");
//                    out.print("<input class='form-control' type='text' name='Txt_codigo_mm' id='Txt_codigo_mm' placeholder='Codigo MM' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_mm');val1.add(Validate.Presence);</script></div>");
                        out.print("<select class='form-control' name='Txt_codigo_mm' id='Txt_codigo_mm' >");
                        out.print("<option value='0'>Seleccionar registro</option>");
                        out.print("<option value='N/A'>N/A</option>");
                        lst_registros = jpacrgt.Traer_registros_tipo_mtto(1);
                        if (lst_registros != null) {
                            for (int i = 0; i < lst_registros.size(); i++) {
                                Object[] obj_registros = (Object[]) lst_registros.get(i);
                                out.print("<option value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_codigo_mm');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("<b>Codigo INSP:</b><div id='Div_insp' class='input-group'>");
//                    out.print("<input class='form-control' type='text' name='Txt_codigo_insp' id='Txt_codigo_insp' placeholder='Codigo INSP' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_insp');val1.add(Validate.Presence);</script></div>");
                        out.print("<select class='form-control' name='Txt_codigo_insp' id='Txt_codigo_insp' >");
                        out.print("<option value='0'>Seleccionar registro</option>");
                        out.print("<option value='N/A'>N/A</option>");
                        lst_registros = jpacrgt.Traer_registros_tipo_mtto(2);
                        if (lst_registros != null) {
                            for (int i = 0; i < lst_registros.size(); i++) {
                                Object[] obj_registros = (Object[]) lst_registros.get(i);
                                out.print("<option value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_codigo_insp');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("<b>Codigo LUB:</b><div id='Div_lub' class='input-group'>");
//                    out.print("<input class='form-control' type='text' name='Txt_codigo_lub' id='Txt_codigo_lub' readonly placeholder='Codigo LUB' value='N/A' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_lub');val1.add(Validate.Presence);</script></div>");
                        out.print("<select class='form-control' disabled name='Txt_codigo_lub' id='Txt_codigo_lub' >");
                        out.print("<option value='0'>Seleccionar registro</option>");
                        out.print("<option selected value='N/A'>N/A</option>");
                        lst_registros = jpacrgt.Traer_registros_tipo_mtto(5);
                        if (lst_registros != null) {
                            for (int i = 0; i < lst_registros.size(); i++) {
                                Object[] obj_registros = (Object[]) lst_registros.get(i);
                                out.print("<option value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_codigo_lub');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_tipo_equipo > 0) {
                            lst_tipo_equipo = jpacteq.Tipo_equipos_id(id_tipo_equipo);
                            Object[] obj_tipo_equipo = (Object[]) lst_tipo_equipo.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Tipo Equipo</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=5&Id_tipo_equipo=" + id_tipo_equipo + "&Rdb_tipo_registro=" + obj_tipo_equipo[7] + "' method='post' id='FormTypeEquipUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Tipo de equipo:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_tipo_equipo' id='Txt_tipo_equipoM' placeholder='Tipo de equipo' title='Tipo de equipo' value='" + obj_tipo_equipo[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tipo_equipoM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Frecuencia en dias:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_frecuencia' id='Txt_frecuenciaM' placeholder='Frecuencia' value='" + obj_tipo_equipo[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuenciaM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            out.print("<b>Frecuencia en dias de alerta :</b>");
                            out.print("<input class='form-control' type='text' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alertaM' placeholder='Frecuencia alerta' value='" + obj_tipo_equipo[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alertaM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=4&Id_tipo_equipo=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Codigo MM:</b><div id='Div_mm' class='input-group'>");
//                        out.print("<input class='form-control' " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) ? "readonly" : "") + " type='text' name='Txt_codigo_mm' id='Txt_codigo_mmM' placeholder='Codigo MM' value='" + ((obj_tipo_equipo[4].toString().contains("V")) ? obj_tipo_equipo[4].toString().split("V")[0] : "") + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_mmM');val1.add(Validate.Presence);</script></div>");
                            out.print("<select class='form-control' " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) ? "disabled" : "") + " name='Txt_codigo_mm' id='Txt_codigo_mmM' >");
                            out.print("<option value='0'>Seleccionar registro</option>");
                            out.print("<option " + ((obj_tipo_equipo[4].toString().equals("N/A") || obj_tipo_equipo[4].toString().equals("null")) ? "selected" : "") + " value='N/A'>N/A</option>");
                            lst_registros = jpacrgt.Traer_registros_tipo_mtto(1);
                            if (lst_registros != null) {
                                for (int i = 0; i < lst_registros.size(); i++) {
                                    Object[] obj_registros = (Object[]) lst_registros.get(i);
                                    out.print("<option " + ((obj_registros[1].toString().equals(obj_tipo_equipo[4])) ? "selected" : "") + " value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_codigo_mmM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<b>Codigo INSP:</b><div id='Div_insp' class='input-group'>");
//                        out.print("<input class='form-control' " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) ? "readonly" : "") + " type='text' name='Txt_codigo_insp' id='Txt_codigo_inspM' placeholder='Codigo INSP' value='" + ((obj_tipo_equipo[5].toString().contains("V")) ? obj_tipo_equipo[5].toString().split("V")[0] : "") + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_inspM');val1.add(Validate.Presence);</script></div>");
                            out.print("<select class='form-control' " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) ? "disabled" : "") + " name='Txt_codigo_insp' id='Txt_codigo_inspM' >");
                            out.print("<option value='0'>Seleccionar registro</option>");
                            out.print("<option " + ((obj_tipo_equipo[5].toString().equals("N/A") || obj_tipo_equipo[5].toString().equals("null")) ? "selected" : "") + " value='N/A'>N/A</option>");
                            lst_registros = jpacrgt.Traer_registros_tipo_mtto(2);
                            if (lst_registros != null) {
                                for (int i = 0; i < lst_registros.size(); i++) {
                                    Object[] obj_registros = (Object[]) lst_registros.get(i);
                                    out.print("<option " + ((obj_registros[1].toString().equals(obj_tipo_equipo[5])) ? "selected" : "") + " value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_codigo_inspM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("<b>Codigo LUB:</b><div id='Div_lub' class='input-group'>");
//                        out.print("<input class='form-control' " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 1) ? "readonly" : "") + " type='text' name='Txt_codigo_lub' id='Txt_codigo_lubM' placeholder='Codigo LUB' value='" + ((obj_tipo_equipo[6].toString().contains("V")) ? obj_tipo_equipo[6].toString().split("V")[0] : "") + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo_lubM');val1.add(Validate.Presence);</script></div>");
                            out.print("<select class='form-control' " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 1) ? "disabled" : "") + " name='Txt_codigo_lub' id='Txt_codigo_lubM' >");
                            out.print("<option value='0'>Seleccionar registro</option>");
                            out.print("<option " + ((obj_tipo_equipo[6].toString().equals("N/A") || obj_tipo_equipo[6].toString().equals("null")) ? "selected" : "") + " value='N/A'>N/A</option>");
                            lst_registros = jpacrgt.Traer_registros_tipo_mtto(5);
                            if (lst_registros != null) {
                                for (int i = 0; i < lst_registros.size(); i++) {
                                    Object[] obj_registros = (Object[]) lst_registros.get(i);
                                    out.print("<option " + ((obj_registros[1].toString().equals(obj_tipo_equipo[6])) ? "selected" : "") + " value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Txt_codigo_lubM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
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
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Tipo Equipos</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Tipo Equipo</th>");
                    out.print("<th>Frecuencia MM</th>");
                    out.print("<th>Alerta</th>");
                    out.print("<th>Registro MM</th>");
                    out.print("<th>Registro INSP</th>");
                    out.print("<th>Registro LUB</th>");
                    out.print("<th>Parametros</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Tipo Equipo</th>");
                    out.print("<th>Frecuencia MM</th>");
                    out.print("<th>Alerta</th>");
                    out.print("<th>Registro MM</th>");
                    out.print("<th>Registro INSP</th>");
                    out.print("<th>Registro LUB</th>");
                    out.print("<th>Parametros</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_tipo_equipos = jpacteq.Tipo_equipos();
                    if (lst_tipo_equipos != null) {
                        for (int i = 0; i < lst_tipo_equipos.size(); i++) {
                            Object[] obj_tipo_equipos = (Object[]) lst_tipo_equipos.get(i);
                            if (Integer.parseInt(obj_tipo_equipos[8].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td>" + obj_tipo_equipos[1] + "</td>");
                            out.print("<td align='center'>" + obj_tipo_equipos[2] + "</td>");
                            out.print("<td align='center'>" + obj_tipo_equipos[3] + "</td>");
                            out.print("<td>" + obj_tipo_equipos[4] + "</td>");
                            out.print("<td>" + obj_tipo_equipos[5] + "</td>");
                            out.print("<td>" + obj_tipo_equipos[6] + "</td>");
                            out.print("<td align='center'><span onclick=\"location.href='Complementos?opc=7&Id_tipo_equipo=" + obj_tipo_equipos[0] + "&Id_parametro=0&Id_tipo_mtto=1'\" class='fa fa-bars fa-size_small' title='Ver parametros'></span></td>");
                            if (Integer.parseInt(obj_tipo_equipos[8].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarTipoEquipo(" + obj_tipo_equipos[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Tipo Equipo'></span></td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=4&Id_tipo_equipo=" + obj_tipo_equipos[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Tipo Equipo'></span></td>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarTipoEquipo(" + obj_tipo_equipos[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Tipo Equipo'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Tipos de Equipo Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Parametros")) {
                    //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                    id_tipo_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_equipo").toString());
                    lst_tipo_equipo = jpacteq.Tipo_equipos_id(id_tipo_equipo);
                    Object[] obj_tipo_equipo = (Object[]) lst_tipo_equipo.get(0);
                    id_tipo_mtto = Integer.parseInt(pageContext.getRequest().getAttribute("Id_tipo_mtto").toString());
                    id_parametro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_parametro").toString());
                    if (Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) {
                        id_tipo_mtto = 3;
                    }
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Complementos?opc=4&Id_tipo_equipo=0' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Parametro " + obj_tipo_equipo[1] + " " + ((id_tipo_mtto == 1) ? "MM" : ((id_tipo_mtto == 2) ? "INSP" : "LUB")) + "");
                        out.print("<div style='float:right;width:15%;font-weight: none;color:grey;'>"
                                + "<input type='radio' name='Rdb_tipo_mtto' value='1' onclick=\"location.href='Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=1&Id_parametro=0'\" " + ((id_tipo_mtto == 1) ? "checked" : "") + " " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) ? "disabled" : "") + "/> MM | "
                                + "<input type='radio' name='Rdb_tipo_mtto' value='2' onclick=\"location.href='Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=2&Id_parametro=0'\" " + ((id_tipo_mtto == 2) ? "checked" : "") + " " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 0) ? "disabled" : "") + "/> INSP | "
                                + "<input type='radio' name='Rdb_tipo_mtto' value='2' onclick=\"location.href='Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=3&Id_parametro=0'\" " + ((id_tipo_mtto == 5) ? "checked" : "") + " " + ((Integer.parseInt(obj_tipo_equipo[7].toString()) == 1) ? "disabled" : "") + "/> LUB</div></h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=8&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=" + id_tipo_mtto + "&Id_parametro=0' method='post' id='FormParameterRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Grupo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_grupo' id='Txt_grupo' placeholder='Grupo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_grupo');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_parametro=0&Id_tipo_mtto=" + id_tipo_mtto + "'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Actividad:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_actividad' id='Txt_actividad' placeholder='Actividad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_parametro > 0) {
                            lst_parametro = jpacprm.Parametro_id(id_parametro);
                            Object[] obj_parametro = (Object[]) lst_parametro.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Parametro " + obj_tipo_equipo[1] + " " + ((id_tipo_mtto == 1) ? "MM" : ((id_tipo_mtto == 2) ? "INSP" : "LUB")) + "</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=8&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=" + id_tipo_mtto + "&Id_parametro=" + id_parametro + "' method='post' id='FormParameterRegister'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Grupo:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_grupo' readonly id='Txt_grupoM' placeholder='Grupo' value='" + obj_parametro[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_grupoM');val1.add(Validate.Presence);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_parametro=0&Id_tipo_mtto=" + id_tipo_mtto + "'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Actividad:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_actividad' id='Txt_actividadM' placeholder='Actividad' value='" + obj_parametro[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividadM');val1.add(Validate.Presence);</script>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                            out.print("</div>");
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
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Parametros " + obj_tipo_equipo[1] + " " + ((id_tipo_mtto == 1) ? "MM" : ((id_tipo_mtto == 2) ? "INSP" : "LUB")) + "</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Orden</th>");
                    out.print("<th>Grupo</th>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Orden</th>");
                    out.print("<th>Grupo</th>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_parametros = jpacprm.Parametros_id_tipo_equipo_mtto(id_tipo_equipo, id_tipo_mtto);
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (Integer.parseInt(obj_parametros[6].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td style='width:5%'><input class='form-control' type='text' value='" + obj_parametros[3] + "'></td>");
                            out.print("<td>" + obj_parametros[1] + "</td>");
                            out.print("<td>" + obj_parametros[2] + "</td>");
                            if (Integer.parseInt(obj_parametros[6].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarParametro(" + id_tipo_equipo + "," + id_tipo_mtto + "," + obj_parametros[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Parametro'></span></td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=7&Id_tipo_equipo=" + id_tipo_equipo + "&Id_tipo_mtto=" + id_tipo_mtto + "&Id_parametro=" + obj_parametros[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Parametro'></span></td>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarParametro(" + id_tipo_equipo + "," + id_tipo_mtto + "," + obj_parametros[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Parametro'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Parametro Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Lineas")) {
                    //<editor-fold defaultstate="collapsed" desc="LINEAS">
                    id_linea = Integer.parseInt(pageContext.getRequest().getAttribute("Id_linea").toString());
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Linea</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=11&Id_linea=0' method='post' id='FormLineRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Codigo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=10&Id_linea=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Linea:</b>");
                        out.print("<input class='form-control' type='text'  name='Txt_linea' id='Txt_linea' placeholder='Linea' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_linea');val1.add(Validate.Presence);</script>");
                        out.print("<b>Zona: </b>");
                        out.print("<select class='form-control' name='Cbx_zona' id='Cbx_zona' >");
                        out.print("<option value='0' >Seleccionar Zona</option>");
                        lst_zonas = jpaczna.Zonas();
                        for (int i = 0; i < lst_zonas.size(); i++) {
                            Object[] obj_zonaS = (Object[]) lst_zonas.get(i);
                            out.print("<option value='" + obj_zonaS[0] + "' >" + obj_zonaS[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_zona');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_linea > 0) {
                            lst_linea = jpaclna.Linea_id(id_linea);
                            Object[] obj_linea = (Object[]) lst_linea.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Linea</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=11&Id_linea=" + id_linea + "' method='post' id='FormLineaUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Codigo:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_codigo' id='Txt_codigoM' placeholder='Codigo' value='" + obj_linea[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigoM');val1.add(Validate.Presence);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=10&Id_linea=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Linea:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_linea' id='Txt_lineaM' placeholder='Linea' value='" + obj_linea[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_lineaM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Zona: </b>");
                            out.print("<select class='form-control' name='Cbx_zona' id='Cbx_zona' >");
                            out.print("<option value='0' >Seleccionar Zona</option>");
                            lst_zonas = jpaczna.Zonas();
                            for (int i = 0; i < lst_zonas.size(); i++) {
                                Object[] obj_zonas = (Object[]) lst_zonas.get(i);
                                out.print("<option value='" + obj_zonas[0] + "' " + (((Integer) obj_zonas[0] == (Integer) obj_linea[7]) ? "selected" : "") + ">" + obj_zonas[1] + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_equipo');"
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
                    //<editor-fold defaultstate="collapsed" desc="Consulta">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Lineas</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Linea</th>");
                    out.print("<th>Zona</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Linea</th>");
                    out.print("<th>Zona</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_lineas = jpaclna.Lineas();
                    if (lst_lineas != null) {
                        for (int i = 0; i < lst_lineas.size(); i++) {
                            Object[] obj_lineas = (Object[]) lst_lineas.get(i);
                            if (Integer.parseInt(obj_lineas[3].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td align='center'>" + obj_lineas[2] + "</td>");
                            out.print("<td>" + obj_lineas[1] + "</td>");
                            out.print("<td>" + obj_lineas[6] + "</td>");
                            if (Integer.parseInt(obj_lineas[3].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarLinea(" + obj_lineas[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Linea'></span></td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=10&Id_linea=" + obj_lineas[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Linea'></span></td>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarLinea(" + obj_lineas[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Linea'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Lineas Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Zonas")) {
                    //<editor-fold defaultstate="collapsed" desc="ZONAS">
                    id_zona = Integer.parseInt(pageContext.getRequest().getAttribute("Id_zona").toString());
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Zona</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=14&Id_zona=0' method='post' id='FormZonaRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Zona:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_zona' id='Txt_zona' placeholder='Zona' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_zona');val1.add(Validate.Presence);</script>");
                        out.print("<b>Registro: </b>");
                        out.print("<select class='form-control' name='Cbx_registro' id='Cbx_registro' >");
                        out.print("<option value='0'>Seleccionar registro</option>");
                        lst_registros = jpacrgt.Traer_registros_tipo_mtto(3);
                        if (lst_registros != null) {
                            for (int i = 0; i < lst_registros.size(); i++) {
                                Object[] obj_registros = (Object[]) lst_registros.get(i);
                                out.print("<option value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_registro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=13&Id_zona=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Frecuencia en días:</b>");
                        out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia' id='Txt_frecuencia' placeholder='Frecuencia' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia');val1.add(Validate.Presence.Number);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Frecuencia en días de alerta :</b>");
                        out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alerta' placeholder='Frecuencia alerta' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alerta');val1.add(Validate.Presence.Number);val1.add(Validate.Enteros2);</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_zona > 0) {
                            lst_zona = jpaczna.Zona_id(id_zona);
                            Object[] obj_zona = (Object[]) lst_zona.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Zona</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=14&Id_zona=" + id_zona + "' method='post' id='FormZonaUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Zona:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_zona' id='Txt_zonaM' placeholder='Zona' value='" + obj_zona[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_zonaM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Registro: </b>");
                            out.print("<select class='form-control' name='Cbx_registro' id='Cbx_registroM' >");
                            out.print("<option value='0'>Seleccionar registro</option>");
                            lst_registros = jpacrgt.Traer_registros_tipo_mtto(3);
                            if (lst_registros != null) {
                                for (int i = 0; i < lst_registros.size(); i++) {
                                    Object[] obj_registros = (Object[]) lst_registros.get(i);
                                    out.print("<option " + ((obj_registros[1].toString().equals(obj_zona[8])) ? "selected" : "") + " value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_registroM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=13&Id_zona=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Frecuencia en días:</b>");
                            out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia' id='Txt_frecuenciaM' placeholder='Frecuencia' value='" + obj_zona[3] + "' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuenciaM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            out.print("<b>Frecuencia en días de alerta :</b>");
                            out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alertaM' placeholder='Frecuencia alerta' value='" + obj_zona[4] + "' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alertaM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
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
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Zonas</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Zona</th>");
                    out.print("<th>Frecuencia</th>");
                    out.print("<th>Frecuencia alerta</th>");
                    out.print("<th>Lineas</th>");
                    out.print("<th>Parametros</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Zona</th>");
                    out.print("<th>Frecuencia</th>");
                    out.print("<th>Frecuencia alerta</th>");
                    out.print("<th>Lineas</th>");
                    out.print("<th>Parametros</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_zonas = jpaczna.Zonas();
                    if (lst_zonas != null) {
                        for (int i = 0; i < lst_zonas.size(); i++) {
                            Object[] obj_zonas = (Object[]) lst_zonas.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_zonas[1] + "</td>");
                            out.print("<td align='center'>" + obj_zonas[3] + "</td>");
                            out.print("<td align='center'>" + obj_zonas[4] + "</td>");
                            out.print("<td style='width:50%'>" + ((obj_zonas[2] == null) ? "<b class='naranja'>La zona no tiene lineas relacionadas</b>" : obj_zonas[2]) + "</td>");
                            out.print("<td align='center'><span onclick=\"location.href='Complementos?opc=16&Id_zona=" + obj_zonas[0] + "&Id_parametro=0'\" class='fa fa-bars fa-size_small' title='Ver parametros'></span></td>");
                            out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=13&Id_zona=" + obj_zonas[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Zona'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("General")) {
                    //<editor-fold defaultstate="collapsed" desc="GENERAL">
                    id_general = Integer.parseInt(pageContext.getRequest().getAttribute("Id_general").toString());
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Actividades Generales</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=20&Id_general=0' method='post' id='FormGeneralRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Actividad:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_general' id='Txt_general' placeholder='General' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_general');val1.add(Validate.Presence);</script>");
                        out.print("<b>Frecuencia en dias:</b>");
                        out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia' id='Txt_frecuencia' placeholder='Frecuencia' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Frecuencia en dias de alerta :</b>");
                        out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alerta' placeholder='Frecuencia alerta' onchange='javascript:this.value=this.value.toUpperCase();' required/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alerta');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Registro: </b>");
                        out.print("<select class='form-control' name='Cbx_registro' id='Cbx_registro' >");
                        out.print("<option value='0'>Seleccionar registro</option>");
                        lst_registros = jpacrgt.Traer_registros_tipo_mtto(4);
                        if (lst_registros != null) {
                            for (int i = 0; i < lst_registros.size(); i++) {
                                Object[] obj_registros = (Object[]) lst_registros.get(i);
                                out.print("<option value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_registro');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Descripción :</b>");
                        out.print("<textarea class='form-control' name='Txt_descripcion' id='Txt_descripcion' placeholder='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=19&Id_general=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_general > 0) {
                            lst_general = jpacgnr.Actividad_general_id(id_general);
                            Object[] obj_general = (Object[]) lst_general.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Actividad General</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=20&Id_general=" + id_general + "' method='post' id='FormGeneralUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Actividad:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_general' id='Txt_generalM' placeholder='General' value='" + obj_general[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_generalM');val1.add(Validate.Presence);</script>");
                            out.print("<b>Frecuencia en dias:</b>");
                            out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia' id='Txt_frecuenciaM' placeholder='Frecuencia' value='" + obj_general[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuenciaM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            out.print("<b>Frecuencia en dias de alerta :</b>");
                            out.print("<input class='form-control' type='number' min='1' name='Txt_frecuencia_alerta' id='Txt_frecuencia_alertaM' placeholder='Frecuencia alerta' value='" + obj_general[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia_alertaM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Registro: </b>");
                            out.print("<select class='form-control' name='Cbx_registro' id='Cbx_registroM' >");
                            out.print("<option value='0'>Seleccionar registro</option>");
                            lst_registros = jpacrgt.Traer_registros_tipo_mtto(4);
                            if (lst_registros != null) {
                                for (int i = 0; i < lst_registros.size(); i++) {
                                    Object[] obj_registros = (Object[]) lst_registros.get(i);
                                    out.print("<option " + ((obj_registros[1].toString().equals(obj_general[8])) ? "selected" : "") + " value='" + obj_registros[1] + "' >" + obj_registros[1] + " Versión " + obj_registros[3] + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_registroM');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("<b>Descripción :</b>");
                            out.print("<textarea class='form-control' name='Txt_descripcion' id='Txt_descripcion' placeholder='Descripción' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_general[2] + "</textarea>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=19&Id_general=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
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
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Actividades Generales</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Descripción</th>");
                    out.print("<th>Frecuencia</th>");
                    out.print("<th>Frecuencia alerta</th>");
                    out.print("<th>Parametros</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Descripción</th>");
                    out.print("<th>Frecuencia</th>");
                    out.print("<th>Frecuencia alerta</th>");
                    out.print("<th>Parametros</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_generales = jpacgnr.Actividades_generales();
                    if (lst_generales != null) {
                        for (int i = 0; i < lst_generales.size(); i++) {
                            Object[] obj_generales = (Object[]) lst_generales.get(i);
                            if (Integer.parseInt(obj_generales[5].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td align='center'>" + obj_generales[1] + "</td>");
                            out.print("<td align='center'>" + obj_generales[2] + "</td>");
                            out.print("<td align='center'>" + obj_generales[3] + "</td>");
                            out.print("<td align='center'>" + obj_generales[4] + "</td>");
                            out.print("<td align='center'><span onclick=\"location.href='Complementos?opc=22&Id_general=" + obj_generales[0] + "&Id_parametro=0'\" class='fa fa-bars fa-size_small' title='Ver parametros'></span></td>");
                            if (Integer.parseInt(obj_generales[5].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarGeneral(" + obj_generales[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Actividad General'></span></td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=19&Id_general=" + obj_generales[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Actividad General'></span></td>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarGeneral(" + obj_generales[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Actividad General'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Actividades Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Parametros_zona")) {
                    //<editor-fold defaultstate="collapsed" desc="PARAMETROS ZONAS">
                    id_zona = Integer.parseInt(pageContext.getRequest().getAttribute("Id_zona").toString());
                    lst_zona = jpaczna.Zona_id(id_zona);
                    Object[] obj_zona = (Object[]) lst_zona.get(0);
                    id_parametro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_parametro").toString());
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Complementos?opc=13&Id_zona=0' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Parametro LIMP_AI " + obj_zona[1] + "</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=17&Id_zona=" + id_zona + "&Id_parametro=0' method='post' id='FormParameterZoneRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Grupo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_grupo' id='Txt_grupo' placeholder='Grupo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_grupo');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=16&Id_zona=" + id_zona + "&Id_parametro=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Actividad:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_actividad' id='Txt_actividad' placeholder='Actividad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_parametro > 0) {
                            lst_parametro = jpacpzn.Parametro_id(id_parametro);
                            Object[] obj_parametro_zona = (Object[]) lst_parametro.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Parametro LIMP_AI " + obj_zona[1] + "</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=17&Id_zona=" + id_zona + "&Id_parametro=" + id_parametro + "' method='post' id='FormParameterZoneUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Grupo:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_grupo' readonly id='Txt_grupoM' placeholder='Grupo' value='" + obj_parametro_zona[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_grupoM');val1.add(Validate.Presence);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=16&Id_zona=" + id_zona + "&Id_parametro=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Actividad:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_actividad' id='Txt_actividadM' placeholder='Actividad' value='" + obj_parametro_zona[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividadM');val1.add(Validate.Presence);</script>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                            out.print("</div>");
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
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Parametros LIMP_AI " + obj_zona[1] + "</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Orden</th>");
                    out.print("<th>Grupo</th>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Orden</th>");
                    out.print("<th>Grupo</th>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_parametros = jpacpzn.Parametro_id_zona(id_zona);
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (Integer.parseInt(obj_parametros[4].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td style='width:5%'><input class='form-control' type='text' value='" + obj_parametros[3] + "'></td>");
                            out.print("<td>" + obj_parametros[1] + "</td>");
                            out.print("<td>" + obj_parametros[2] + "</td>");
                            if (Integer.parseInt(obj_parametros[4].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarParametroZona(" + id_zona + "," + obj_parametros[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Parametro'></span></td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=16&Id_zona=" + id_zona + "&Id_parametro=" + obj_parametros[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Parametro'></span></td>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarParametroZona(" + id_zona + "," + obj_parametros[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Parametro'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Parametro Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Parametros_general")) {
                    //<editor-fold defaultstate="collapsed" desc="PARAMETROS GENERAL">
                    id_general = Integer.parseInt(pageContext.getRequest().getAttribute("Id_general").toString());
                    lst_general = jpacgnr.Actividad_general_id(id_general);
                    Object[] obj_general = (Object[]) lst_general.get(0);
                    id_parametro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_parametro").toString());
                    out.print("<input type='hidden' id='Txt_enlace_volver' value='Complementos?opc=19&Id_general=0' />");
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar">
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Parametro General " + obj_general[1] + "</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\" >");
                        out.print("<form action='Complementos?opc=23&Id_general=" + id_general + "&Id_parametro=0' method='post' id='FormParameterGeneralRegister'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Grupo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_grupo' id='Txt_grupo' placeholder='Grupo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_grupo');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=22&Id_general=" + id_general + "&Id_parametro=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Actividad:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_actividad' id='Txt_actividad' placeholder='Actividad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividad');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    if (nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) {
                        //<editor-fold defaultstate="collapsed" desc="Modificar">
                        if (id_parametro > 0) {
                            lst_parametro = jpacpgn.Parametro_id(id_parametro);
                            Object[] obj_parametro_general = (Object[]) lst_parametro.get(0);
                            out.print("<div class=\"card shadow mb-4\">");
                            out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                            out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Parametro General " + obj_general[1] + "</h6>");
                            out.print("</a>");
                            out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                            out.print("<div class=\"card-body\">");
                            out.print("<form action='Complementos?opc=23&Id_general=" + id_general + "&Id_parametro=" + id_parametro + "' method='post' id='FormParameterGeneralUpdate'>");
                            out.print("<div style='width:40%;float:left;'>");
                            out.print("<b>Grupo:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_grupo' readonly id='Txt_grupoM' placeholder='Grupo' value='" + obj_parametro_general[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_grupoM');val1.add(Validate.Presence);</script>");
                            out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                            out.print("<span onclick=\"javascript:location.href='Complementos?opc=22&Id_general=" + id_general + "&Id_parametro=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                            out.print("</div>");
                            out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                            out.print("<b>Actividad:</b>");
                            out.print("<input class='form-control' type='text' name='Txt_actividad' id='Txt_actividadM' placeholder='Actividad' value='" + obj_parametro_general[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_actividadM');val1.add(Validate.Presence);</script>");
                            out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                            out.print("</div>");
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
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Parametros General " + obj_general[1] + "</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Orden</th>");
                    out.print("<th>Grupo</th>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Orden</th>");
                    out.print("<th>Grupo</th>");
                    out.print("<th>Actividad</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_parametros = jpacpgn.Parametro_id_general(id_general);
                    if (lst_parametros != null) {
                        for (int i = 0; i < lst_parametros.size(); i++) {
                            Object[] obj_parametros = (Object[]) lst_parametros.get(i);
                            if (Integer.parseInt(obj_parametros[4].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td style='width:5%'><input class='form-control' type='text' value='" + obj_parametros[3] + "'></td>");
                            out.print("<td>" + obj_parametros[1] + "</td>");
                            out.print("<td>" + obj_parametros[2] + "</td>");
                            if (Integer.parseInt(obj_parametros[4].toString()) == 1) {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='DesactivarParametroGeneral(" + id_general + "," + obj_parametros[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-check fa-size_small' title='Desactivar Parametro'></span></td>");
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick=\"location.href='Complementos?opc=22&Id_general=" + id_general + "&Id_parametro=" + obj_parametros[0] + "'\"" : "style='cursor:not-allowed'") + " class='fa fa-pen fa-size_small' title='Editar Parametro'></span></td>");
                            } else {
                                out.print("<td align='center'><span " + ((nombre_rol.equals("Administrador") || nombre_rol.equals("Jefe_Mtto") || nombre_rol.equals("Asistente")) ? "onclick='ActivarParametroGeneral(" + id_general + "," + obj_parametros[0] + ")'" : "style='cursor:not-allowed'") + " class='fa fa-times fa-size_small' title='Activar Parametro'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Parametro Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Instrucciones_seguridad")) {
                    //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES SEGURIDAD">
                    lst_instrucciones = jpacitc.Instruccion_seguridad();
                    Object[] obj_instruccion = (Object[]) lst_instrucciones.get(0);
                    //<editor-fold defaultstate="collapsed" desc="Registro">
                    out.print("<script src=\"Interfaz/EditorHtml/htmljquery-3.5.1.min.js\" type=\"text/javascript\"></script>");
                    out.print("<script src=\"Interfaz/EditorHtml/htmlpopper.min.js\" type=\"text/javascript\"></script>");
                    out.print("<link href=\"Interfaz/EditorHtml/htmlbootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                    out.print("<script src=\"Interfaz/EditorHtml/htmlbootstrap.min.js\" type=\"text/javascript\"></script>");
                    out.print("<link href=\"Interfaz/EditorHtml/htmlsummernote-bs4.min.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                    out.print("<script src=\"Interfaz/EditorHtml/htmlsummernote-bs4.min.js\" type=\"text/javascript\"></script>");
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Instrucciones de seguridad</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div style='float:right'><span class='fa fa-save fa-size_small' onclick=\"ConstruirEditor()\"></span></div><br /><br />");
                    out.print("<div style='height:500px' id=\"summernote\">" + obj_instruccion[2] + "</div>");
                    out.print("<script>");
                    out.print("$('#summernote').summernote({");
                    out.print("placeholder: 'Instrucciones de seguridad MTF',");
                    out.print("tabsize: 2,");
                    out.print("height: 100");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='display:none'><form action='Complementos?opc=26' method='post' id='FormInstruccion'>");
                    out.print("<input type='text' name='Id_instruccion' id='Id_instruccion' value='" + obj_instruccion[0] + "'/>");
                    out.print("<input type='text' name='Txt_titulo' id='Txt_titulo' value='" + obj_instruccion[1] + "' />");
                    out.print("<textarea name='Txt_instruccion_seguridad' id='Txt_instruccion_seguridad'></textarea>");
                    out.print("</form></div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Registros")) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTROS">
                    id_registro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_registro").toString());
                    //<editor-fold defaultstate="collapsed" desc="Registrar">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                    out.print("<div class=\"card-body\" >");
                    out.print("<form action='Complementos?opc=28&Id_registro=0' method='post' id='FormRegistroRegister'>");
                    out.print("<div style='width:40%;float:left;'>");
                    out.print("<b>Codigo:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script>");
                    out.print("<b>Versión:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_version' id='Txt_version' placeholder='Versión' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_version');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                    out.print("<b>Titulo:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_titulo' id='Txt_titulo' placeholder='Titulo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_titulo');val1.add(Validate.Presence);</script>");
                    out.print("<b>Fecha Vigencia:</b>");
                    out.print("<input class='form-control' type='text' name='Txt_fecha' id='datepicker' placeholder='Fecha vigencia' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                    out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                    out.print("<span onclick=\"javascript:location.href='Complementos?opc=27&Id_registro=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                    out.print("</div>");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                    out.print("<b>Tipo Mtto: </b>");
                    out.print("<select class='form-control' name='Cbx_tipo_mtto' id='Cbx_tipo_mtto' >");
                    out.print("<option value='0'>Seleccionar tipo mtto</option>");
                    lst_tipo_mtto = jpactmt.Tipos_mtto();
                    for (int i = 0; i < lst_tipo_mtto.size(); i++) {
                        Object[] obj_tipo_mtto = (Object[]) lst_tipo_mtto.get(i);
                        out.print("<option value='" + obj_tipo_mtto[0] + "' >" + obj_tipo_mtto[1] + " / " + obj_tipo_mtto[2] + "</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_mtto');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<b>Nota importante :</b>");
                    out.print("<textarea class='form-control' name='Txt_nota' id='Txt_nota' placeholder='Nota importante' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nota');val1.add(Validate.Presence);</script>");
                    out.print("<b>Observaciones generales :</b>");
                    out.print("<textarea class='form-control' name='Txt_observacion' id='Txt_observacion' placeholder='Observaciones generales' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observacion');val1.add(Validate.Presence);</script>");
                    out.print("</div>");
                    out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Modificar">
                    if (id_registro > 0) {
                        lst_registro = jpacrgt.Traer_registros_id(id_registro);
                        Object[] obj_registro = (Object[]) lst_registro.get(0);
                        out.print("<div class=\"card shadow mb-4\">");
                        out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                        out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Registro</h6>");
                        out.print("</a>");
                        out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                        out.print("<div class=\"card-body\">");
                        out.print("<form action='Complementos?opc=28&Id_registro=" + id_registro + "' method='post' id='FormRegistroUpdate'>");
                        out.print("<div style='width:40%;float:left;'>");
                        out.print("<b>Codigo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_codigo' id='Txt_codigoM' value='" + obj_registro[1] + "' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigoM');val1.add(Validate.Presence);</script>");
                        out.print("<b>Versión:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_version' id='Txt_versionM' value='" + obj_registro[3] + "' placeholder='Versión' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_versionM');val1.add(Validate.Presence);val1.add(Validate.Enteros2);</script>");
                        out.print("<b>Titulo:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_titulo' id='Txt_tituloM' value='" + obj_registro[2] + "' placeholder='Titulo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_tituloM');val1.add(Validate.Presence);</script>");
                        out.print("<b>Fecha Vigencia:</b>");
                        out.print("<input class='form-control' type='text' name='Txt_fecha' id='datepicker2' placeholder='Fecha vigencia' value='" + obj_registro[6] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker2');val1.add(Validate.Presence);</script>");
                        out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                        out.print("<span onclick=\"javascript:location.href='Complementos?opc=27&Id_registro=0'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                        out.print("</div>");
                        out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%;'>");
                        out.print("<b>Tipo Mtto: </b>");
                        out.print("<select class='form-control' name='Cbx_tipo_mtto' id='Cbx_tipo_mttoM' >");
                        out.print("<option value='0'>Seleccionar tipo mtto</option>");
                        lst_tipo_mtto = jpactmt.Tipos_mtto();
                        for (int i = 0; i < lst_tipo_mtto.size(); i++) {
                            Object[] obj_tipo_mtto = (Object[]) lst_tipo_mtto.get(i);
                            out.print("<option " + (((Integer) obj_registro[7] == (Integer) obj_tipo_mtto[0]) ? "selected" : "") + " value='" + obj_tipo_mtto[0] + "' >" + obj_tipo_mtto[1] + " / " + obj_tipo_mtto[2] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_mttoM');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<b>Nota importante :</b>");
                        out.print("<textarea class='form-control' name='Txt_nota' id='Txt_notaM' placeholder='Nota importante' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_registro[4] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_notaM');val1.add(Validate.Presence);</script>");
                        out.print("<b>Observaciones generales :</b>");
                        out.print("<textarea class='form-control' name='Txt_observacion' id='Txt_observacionM' placeholder='Observaciones generales' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_registro[5] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observacionM');val1.add(Validate.Presence);</script>");
                        out.print("</div>");
                        out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="Consulta">
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<div class=\"card-header py-3\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Registros</h6>");
                    out.print("</div>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"table-responsive\">");
                    out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Versión</th>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tfoot>");
                    out.print("<tr>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Versión</th>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    out.print("</tfoot>");
                    out.print("<tbody>");
                    lst_registros = jpacrgt.Consultar_registros();
                    if (lst_registros != null) {
                        for (int i = 0; i < lst_registros.size(); i++) {
                            Object[] obj_registros = (Object[]) lst_registros.get(i);
                            if (Integer.parseInt(obj_registros[8].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='tr_rojo'>");
                            }
                            out.print("<td align='center'>" + obj_registros[1] + "</td>");
                            out.print("<td align='center'>" + obj_registros[3] + "</td>");
                            out.print("<td>" + obj_registros[2] + "</td>");
                            if (Integer.parseInt(obj_registros[8].toString()) == 1) {
                                out.print("<td align='center'><span onclick='DesactivarRegistro(" + obj_registros[0] + ")' class='fa fa-check fa-size_small' title='Desactivar Registro'></span></td>");
                                out.print("<td align='center'><span onclick=\"location.href='Complementos?opc=27&Id_registro=" + obj_registros[0] + "'\" class='fa fa-pen fa-size_small' title='Editar Registro'></span></td>");
                            } else {
                                out.print("<td align='center'><span onclick='ActivarRegistro(" + obj_registros[0] + ")' class='fa fa-times fa-size_small' title='Activar Registro'></span></td>");
                                out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Registros Inactivos'></span></td>");
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
                } else if (pageContext.getRequest().getAttribute("Complementos").toString().equals("Cambiar_rol")) {
                    //<editor-fold defaultstate="collapsed" desc="CAMBIAR ROL">}
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"True\" aria-controls=\"collapseCardExample\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Cambiar Rol</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                    out.print("<div class=\"card-body\" >");
                    out.print("<form action='Complementos?opc=31' method='post' id='FormRegistroRegister'>");
                    out.print("<div style='width:50%;float:left;'>");
                    out.print("<b>Usuario: </b>");
                    out.print("<select class='form-control' name='Id_usuario' id='Id_usuario' >");
                    out.print("<option value='0'>Seleccionar Usuario</option>");
                    List lst_usuario = jpausr.Usuarios_cambio_rol();
                    for (int j = 0; j < lst_usuario.size(); j++) {
                        Object[] obj_usuario = (Object[]) lst_usuario.get(j);
                        out.print("<option value='" + obj_usuario[0] + "'>" + obj_usuario[1] + "</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Id_usuario');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("<div style='width:50%;float:left;'>");
                    out.print("<b>Rol: </b>");
                    out.print("<select class='form-control' name='Cbx_rol' id='Cbx_rol' >");
                    out.print("<option value='0'>Seleccionar Rol</option>");
                    List lst_rol = jpactrl.Cambiar_rol();
                    for (int i = 0; i < lst_rol.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_rol.get(i);
                        out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</div>");
                    out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                    out.print("<span onclick=\"javascript:location.href='Complementos?opc=30'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span></div>");
                    out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_complementos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
