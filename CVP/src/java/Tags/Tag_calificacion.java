package Tags;

import Controladores.AreaJpaController;
import Controladores.CalificacionJpaController;
import Controladores.GrupoJpaController;
import Controladores.InformeJpaController;
import Controladores.TipoCalificacionJpaController;
import Controladores.TipoInformeJpaController;
import Metodos.Connection_mysql_daruma;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_calificacion extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //PERMISOS POR ROL
            String[] rol_usuario = pageContext.getSession().getAttribute("Rol/Nombres").toString().split("/");
            String rol = rol_usuario[0];
            String usuario = rol_usuario[1];
            String[] cod_area = pageContext.getSession().getAttribute("Id/Area").toString().split("/");
            int cod = Integer.parseInt(cod_area[0].toString());
            String area = cod_area[1];
            //FIN PERMISOS
            AreaJpaController jpacara = new AreaJpaController();
            TipoCalificacionJpaController jpactcl = new TipoCalificacionJpaController();
            TipoInformeJpaController jpactif = new TipoInformeJpaController();
            GrupoJpaController jpacgpo = new GrupoJpaController();
            InformeJpaController jpacifm = new InformeJpaController();
            CalificacionJpaController jpacclf = new CalificacionJpaController();
            Connection_mysql_daruma mtdcmd = new Connection_mysql_daruma();
            //VARIABLE GLOBALES
            int id_calificacion = 0;
            int dependencias = 0;
            int id_dependencia = 0;
            int id_informe = 0;
            int id_informe_visor = 0;
            List lst_area = null;
            List lst_informes = null;
            List lst_informe = null;
            List lst_grupos = null;
            List lst_protocolos = null;
            List lst_tipo_calificacion = null;
            List lst_tipo_informe = null;
            List lst_calificaciones = null;
            List lst_calificacion = null;
            String filtro = "";
            String dependencias_informe = "";
            if (pageContext.getRequest().getAttribute("Calificacion") != null) {
                // <editor-fold defaultstate="collapsed" desc="CALIFICACION">
                if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("Modulo_calificacion")) {
                    id_dependencia = Integer.parseInt(pageContext.getRequest().getAttribute("Id_dependencia").toString());
                    id_calificacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_calificacion").toString());
                    out.print("<div id='content_sin'>");
                    if (!rol.equals("Consulta")) {
                        out.print("<h3><img id=\"Menu_registro\" src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Desplegar Menu' />"
                                + "Calificaciones Prospectiva | <b id='Convenciones'>Convenciones</b><div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else {
                        out.print("<h3>Calificaciones Prospectiva<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    }
                    lst_calificaciones = jpacclf.Calificaciones_area(cod);
                    lst_grupos = jpacgpo.Grupos();
                    lst_tipo_calificacion = jpactcl.Tipos_calificacion();
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
                    out.print("<td align='center'><div class='ribbon_verde' style='height:40px'></div></td>");
                    out.print("<td>Se asigna a las calificaciones que tienen actualizados sus informes y estan a mas de tres meses de recalificación.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='ribbon_naranja' style='height:40px'></div></td>");
                    out.print("<td>Se asigna a las calificaciones que estan a tres mese de cumplir el tiempo para recalificar segun la frecuencia asiganda.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='ribbon_rojo' style='height:40px'></div></td>");
                    out.print("<td>Se asigna a las calificaciones que inclumplen con la frecuencia de recalificación.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='ribbon_azul' style='height:40px'></div></td>");
                    out.print("<td>Se asigna a califiaciones que no tienen frecuencia de alerta para las recalificaciones, como calificaiones de materia prima o validaciones retrospectivas.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><div class='ribbon_gris' style='height:40px'></div></td>");
                    out.print("<td>Se asigna a las calificaciones que estan inactivas.</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                    out.print("<script>");
                    out.print("$(Menu_registro).click(function() {");
                    out.print("$(\"#toggle\").toggle(\"slide\");");
                    out.print("});");
                    out.print("</script>");
                    out.print("<div style='display:" + ((id_calificacion == 0) ? "none" : "block") + ";border: 1px solid #016279;border-right:none;background-color:#fff;position:absolute;' id=\"toggle\">");
                    out.print("<div id='sidebar_big'>");
                    if (id_calificacion == 0) {
                        lst_protocolos = mtdcmd.Protocolos_area(cod);
                        out.print("<form action='Calificacion?opc=3' method='post'>");
                        out.print("<h3>Registrar Calificación " + area + "</h3>");
                        out.print("<div style='float:left;width:50%'>");
                        out.print("<b>Documento :</b><br />");
                        if (lst_protocolos != null) {
                            out.print("<select name='Cbx_documento' id='Cbx_documento' title='Documento' onchange='PostProtocolo(this.value)'>");
                            out.print("<option value='0' >Protocolo</option>");
                            for (int i = 0; i < lst_protocolos.size(); i++) {
                                String protocolo = lst_protocolos.get(i).toString();
                                out.print("<option value='" + protocolo + "'>" + protocolo + "</option>");
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_documento');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<input type='text' name='Cbx_documento' id='Cbx_documento' placeholder='Codigo Documento' title='Codigo Documento' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_documento');val1.add(Validate.Presence);</script>");
                        }
                        out.print("<b>Nombre de calificacion :</b><br />");
                        out.print("<textarea name='Txt_calificacion' id='Txt_calificacion' placeholder='Nombre de la calificación' title='Nombre de la calificación' style='height:87px' onchange='javascript:this.value=this.value.toUpperCase();'/></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_calificacion');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Tipo de calificación :</b><br />");
                        out.print("<select name='Cbx_tipo_calificacion' id='Cbx_tipo_calificacion' title='Tipo de calificación' >");
                        out.print("<option value='0' >Tipo de calificación</option>");
                        for (int i = 0; i < lst_tipo_calificacion.size(); i++) {
                            Object[] obj_tipo_calificacion = (Object[]) lst_tipo_calificacion.get(i);
                            out.print("<option value='" + obj_tipo_calificacion[0] + "' title='" + obj_tipo_calificacion[2] + "'>" + obj_tipo_calificacion[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_calificacion');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Grupo :</b><br />");
                        out.print("<select name='Cbx_grupo' id='Cbx_grupo' title='Grupo' >");
                        out.print("<option value='0' >Grupo</option>");
                        for (int i = 0; i < lst_grupos.size(); i++) {
                            Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                            if (obj_grupos[2].toString().equals("N/A") && Integer.parseInt(obj_grupos[3].toString()) > 0) {
                                out.print("<optgroup label='" + obj_grupos[1] + "'>");
                                for (int j = 0; j < lst_grupos.size(); j++) {
                                    Object[] obj_sub_grupos = (Object[]) lst_grupos.get(j);
                                    if (!"N/A".equals(obj_sub_grupos[2].toString()) && obj_sub_grupos[2].equals(obj_grupos[1].toString()) && Integer.parseInt(obj_sub_grupos[3].toString()) > 0) {
                                        out.print("<option value='" + obj_sub_grupos[0] + "' >" + obj_sub_grupos[1] + "</option>");
                                    }
                                }
                                out.print("</optgroup>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_grupo');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='hidden' name='Id_area' value='" + cod + "'/>");
                        out.print("<br /><br /><input type='submit' value='Registrar' /><br /><br />");
                        out.print("</div>");
                        out.print("<div style='float:left;width:49%'>");
                        out.print("<b>Frecuencia en dias:</b><br />");
                        out.print("<input type='text' name='Txt_frecuencia' id='Txt_frecuencia' placeholder='Frecuencia' title='Frecuencia' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Ejecuta Informe:</b><br />");
                        out.print("<input type='text' name='Txt_ejecutor' id='Txt_ejecutor' placeholder='Nombre Ejecutor' title='Nombre Ejecutor' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ejecutor');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Revisa Informe:</b><br />");
                        out.print("<input type='text' name='Txt_revisor' id='Txt_revisor' placeholder='Nombre Revisor' title='Nombre Revisor' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_revisor');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Aprueba Informe:</b><br />");
                        out.print("<input type='text' name='Txt_aprobador' id='Txt_aprobador' placeholder='Nombre Aprobador' title='Nombre Aprobador' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_aprobador');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Txt_dependencia' id='Txt_dependencia' value='N/A'/>");
                    } //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                    else {
                        lst_protocolos = mtdcmd.Protocolos_area(cod);
                        lst_calificacion = jpacclf.Traer_calificacion_id(id_calificacion);
                        Object[] obj_calificacion = (Object[]) lst_calificacion.get(0);
                        out.print("<form action='Calificacion?opc=4&icl=" + id_calificacion + "' method='post'>");
                        out.print("<h3>Modificar Calificación " + area + "</h3>");
                        out.print("<div style='float:left;width:50%'>");
                        out.print("<b>Documento :</b><br />");
                        if (lst_protocolos != null) {
                            out.print("<select name='Cbx_documento' id='Cbx_documento' title='Documento' onchange='PostProtocolo(this.value)'>");
                            out.print("<option value='0' >Protocolo</option>");
                            for (int i = 0; i < lst_protocolos.size(); i++) {
                                String protocolo = lst_protocolos.get(i).toString();
                                if (protocolo.contains("" + obj_calificacion[13])) {
                                    out.print("<option selected value='" + protocolo + "'>" + protocolo + "</option>");
                                } else {
                                    out.print("<option value='" + protocolo + "'>" + protocolo + "</option>");
                                }
                            }
                            out.print("</select>"
                                    + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_documento');"
                                    + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        } else {
                            out.print("<input type='text' name='Cbx_documento' id='Cbx_documento' value='" + obj_calificacion[13] + "' placeholder='Codigo Documento' title='Codigo Documento' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('Cbx_documento');val1.add(Validate.Presence);</script>");
                        }
                        out.print("<b>Nombre de calificacion :</b><br />");
                        out.print("<textarea name='Txt_calificacion' id='Txt_calificacion' placeholder='Nombre de la calificación' title='Nombre de la calificación' style='height:87px' onchange='javascript:this.value=this.value.toUpperCase();'/>" + obj_calificacion[1] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_calificacion');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Tipo de calificación :</b><br />");
                        out.print("<select name='Cbx_tipo_calificacion' id='Cbx_tipo_calificacion' title='Tipo de calificación' >");
                        out.print("<option value='0' >Tipo de calificación</option>");
                        for (int i = 0; i < lst_tipo_calificacion.size(); i++) {
                            Object[] obj_tipo_calificacion = (Object[]) lst_tipo_calificacion.get(i);
                            if (Integer.parseInt(obj_calificacion[3].toString()) == Integer.parseInt(obj_tipo_calificacion[0].toString())) {
                                out.print("<option selected value='" + obj_tipo_calificacion[0] + "' title='" + obj_tipo_calificacion[2] + "'>" + obj_tipo_calificacion[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_tipo_calificacion[0] + "' title='" + obj_tipo_calificacion[2] + "'>" + obj_tipo_calificacion[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_calificacion');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><b>Grupo :</b><br />");
                        out.print("<select name='Cbx_grupo' id='Cbx_grupo' title='Grupo' >");
                        out.print("<option value='0' >Grupo</option>");
                        for (int i = 0; i < lst_grupos.size(); i++) {
                            Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                            if (obj_grupos[2].toString().equals("N/A") && Integer.parseInt(obj_grupos[3].toString()) > 0) {
                                out.print("<optgroup label='" + obj_grupos[1] + "'>");
                                for (int j = 0; j < lst_grupos.size(); j++) {
                                    Object[] obj_sub_grupos = (Object[]) lst_grupos.get(j);
                                    if (!"N/A".equals(obj_sub_grupos[2].toString()) && obj_sub_grupos[2].equals(obj_grupos[1].toString()) && Integer.parseInt(obj_sub_grupos[3].toString()) > 0) {
                                        if (obj_calificacion[10].toString().equals(obj_sub_grupos[0].toString())) {
                                            out.print("<option selected value='" + obj_sub_grupos[0] + "' >" + obj_sub_grupos[1] + "</option>");
                                        } else {
                                            out.print("<option value='" + obj_sub_grupos[0] + "' >" + obj_sub_grupos[1] + "</option>");
                                        }
                                    }
                                }
                                out.print("</optgroup>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_grupo');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='hidden' name='Id_area' value='" + cod + "'/>");
                        out.print("<br /><br /><input type='submit' value='Registrar' /><br /><br />");
                        out.print("</div>");
                        out.print("<div style='float:left;width:49%'>");
                        out.print("<b>Frecuencia en dias:</b><br />");
                        out.print("<input type='text' name='Txt_frecuencia' id='Txt_frecuencia' value='" + obj_calificacion[2] + "' placeholder='Frecuencia' title='Frecuencia' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Ejecuta Informe:</b><br />");
                        out.print("<input type='text' name='Txt_ejecutor' id='Txt_ejecutor' value='" + obj_calificacion[14] + "' placeholder='Nombre Ejecutor' title='Nombre Ejecutor' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_ejecutor');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Revisa Informe:</b><br />");
                        out.print("<input type='text' name='Txt_revisor' id='Txt_revisor' value='" + obj_calificacion[15] + "' placeholder='Nombre Revisor' title='Nombre Revisor' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_revisor');val1.add(Validate.Presence);</script>");
                        out.print("<br /><b>Aprueba Informe:</b><br />");
                        out.print("<input type='text' name='Txt_aprobador' id='Txt_aprobador' value='" + obj_calificacion[16] + "' placeholder='Nombre Aprobador' title='Nombre Aprobador' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_aprobador');val1.add(Validate.Presence);</script>");
                        out.print("<input type='hidden' name='Txt_dependencia' id='Txt_dependencia' value='N/A'/>");
                    }
                    out.print("</div>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                    if (lst_calificaciones == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de líneas registrados</b>");
                        out.print("</center>");
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<th>#</th>");
                        out.print("<th>Calificacion</th>");
                        out.print("<th>Fechas</th>");
                        out.print("<th>Flujo de trabajo</th>");
                        out.print("<th>Historial</th>");
                        out.print("<th>Dep.</th>");
                        if (!rol.equals("Consulta")) {
                            out.print("<th>Editar</th>");
                            out.print("<th>Estado</th>");
                        }
                        out.print("</tr>");
                        for (int i = 0; i < lst_calificaciones.size(); i++) {
                            Object[] obj_calificaciones = (Object[]) lst_calificaciones.get(i);
                            if (Integer.parseInt(obj_calificaciones[18].toString()) == 1) {
                                out.print("<tr>");
                            } else {
                                out.print("<tr class='rojo'>");
                            }
                            if (Integer.parseInt(obj_calificaciones[18].toString()) == 1) {
                                if (Integer.parseInt(obj_calificaciones[2].toString()) > 0) {
                                    if (Integer.parseInt(obj_calificaciones[23].toString()) < -90 && !(obj_calificaciones[21].toString().equals("SIN_REALIZAR"))) {
                                        out.print("<td><div class='ribbon_verde'><div class='radius_cal'>" + obj_calificaciones[0] + "</div></div></td>");
                                    } else if (Integer.parseInt(obj_calificaciones[23].toString()) >= -90 && Integer.parseInt(obj_calificaciones[23].toString()) <= 0 && !(obj_calificaciones[21].toString().equals("SIN_REALIZAR"))) {
                                        out.print("<td><div class='ribbon_naranja'><div class='radius_cal'>" + obj_calificaciones[0] + "</div></div></td>");
                                    } else if (Integer.parseInt(obj_calificaciones[23].toString()) > 0 && !(obj_calificaciones[21].toString().equals("SIN_REALIZAR"))) {
                                        out.print("<td><div class='ribbon_rojo'><div class='radius_cal'>" + obj_calificaciones[0] + "</div></div></td>");
                                    } else {
                                        out.print("<td><div class='ribbon_gris'><div class='radius_cal'>" + obj_calificaciones[0] + "</div></div></td>");
                                    }
                                } else {
                                    out.print("<td><div class='ribbon_azul'><div class='radius_cal'>" + obj_calificaciones[0] + "</div></div></td>");
                                }
                            } else {
                                out.print("<td><div class='ribbon_gris'><div class='radius_cal'>" + obj_calificaciones[0] + "</div></div></td>");
                            }
                            out.print("<td><b>Calificación :</b>" + obj_calificaciones[1] + "<br />");
                            out.print("<b>Tipo :</b>" + obj_calificaciones[4] + "<br />");
                            out.print("<b>Grupo :</b>" + obj_calificaciones[11] + "<br />");
                            out.print("<b>Documento :</b>" + obj_calificaciones[13] + "</td>");
                            if (Integer.parseInt(obj_calificaciones[2].toString()) > 0) {
                                out.print("<td ><b>ULT.</b>" + obj_calificaciones[21] + "<br /><b>PROX.</b>" + obj_calificaciones[22] + "</td>");
                            } else {
                                out.print("<td ><b class='naranja'>No aplica frecuencia de alerta</b></td>");
                            }
                            out.print("<td ><b>Ejecuta : </b>" + obj_calificaciones[14] + "<br />"
                                    + "<b>Revisa : </b>" + obj_calificaciones[15] + "<br />"
                                    + "<b>Aprueba : </b>" + obj_calificaciones[16] + "</td>");
                            out.print("<td align='center'><a href='Calificacion?opc=5&icl=" + obj_calificaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' alt='edit' title='Historial' /></a></td>");
                            out.print("<td align='center'><a href='Calificacion?opc=1&idp=" + obj_calificaciones[0] + "'><img src='Interfaz/Contenido/Iconos/List.png' alt='edit' title='Historial' /></a></td>");
                            if (!rol.equals("Consulta")) {
                                out.print("<td align='center'><a href='Calificacion?opc=1&icl=" + obj_calificaciones[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='edit' title='Historial' /></a></td>");
                                if (Integer.parseInt(obj_calificaciones[18].toString()) == 1) {
                                    out.print("<td align='center'><a href='#'  onclick='DesactivarCalificacion(" + obj_calificaciones[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar' /></a></td>");
                                } else {
                                    out.print("<td align='center'><a href='#'  onclick='ActivarCalificacion(" + obj_calificaciones[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar' /></a></td>");
                                }
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
                        //</editor-fold> 
                        //<editor-fold defaultstate="collapsed" desc="DEPENDENCIAS">
                        if (id_dependencia > 0) {
                            lst_calificacion = jpacclf.Traer_calificacion_id(id_dependencia);
                            Object[] obj_calificacion = (Object[]) lst_calificacion.get(0);
                            out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: block;'>");
                            out.print("<fieldset class='popup_local' id='Asignar_dependencia" + id_dependencia + "' style='width:900px;height:400px;overflow:scroll;position: absolute;top: 5px;left: 15%;'>");
                            out.print("<div align='right'><a href='Calificacion?opc=1'><img src='Interfaz/Contenido/Iconos/Delete.png'  alt='edit' title='Cerrar' /></a></div>");
                            out.print("<h3>Asignación dependencia de calificaciones</h3>");
                            if (!rol.equals("Consulta")) {
                                out.print("<form action='Calificacion?opc=8&icl=" + id_dependencia + "' method='post'>");
                                out.print("<input type='hidden' name='Txt_seleccion_dependencias' id='Txt_seleccion_dependencias' value='" + obj_calificacion[17].toString().replace("N/A", "") + "' />");
                                out.print("<input type='submit' value='Asignar'/>");
                                out.print("</form>");
                            }
                            out.print("<input style='float:right' id='Txt_filtro_1' type='text' onkeyup='Filtrar_2()' placeholder='Buscar'/><br />");
                            out.print("<div align='left' id='NavPosicion1'></div>");
                            out.print("<table class='table' id='resultados_1'>");
                            out.print("<tr>");
                            out.print("<td colspan='5'></td>");
                            out.print("</tr>");
                            lst_area = jpacara.Areas();
                            for (int i = 0; i < lst_area.size(); i++) {
                                Object[] obj_areas = (Object[]) lst_area.get(i);
                                List lst_calificaciones_dependencia = jpacclf.Calificaciones_area(Integer.parseInt(obj_areas[0].toString()));
                                if (lst_calificaciones_dependencia != null) {
                                    out.print("<tr align='left'>");
                                    out.print("<td colspan='5' align='center'><b>" + obj_areas[1] + "<b></td>");
//                                    out.print("<th colspan='6' >" + obj_areas[1] + "</th>");
                                    out.print("</tr>");
                                    for (int j = 0; j < lst_calificaciones_dependencia.size(); j++) {
                                        Object[] obj_dependencias = (Object[]) lst_calificaciones_dependencia.get(j);
                                        if (!obj_dependencias[0].toString().equals("" + id_dependencia)) {
                                            out.print("<tr align='left'>");
                                            if (obj_calificacion[17].toString().contains("[" + obj_dependencias[0] + "]")) {
                                                out.print("<td><input type='checkbox' " + ((rol.equals("Consulta")) ? " disabled='true' " : "") + " checked id='Cbx_dependencia' name='Cbx_dependencia' value='[" + obj_dependencias[0] + "]' onclick=\"SeleccionCalificaciones(this);\"/></td>");
                                            } else {
                                                out.print("<td><input type='checkbox' " + ((rol.equals("Consulta")) ? " disabled='true' " : "") + " id='Cbx_dependencia' name='Cbx_dependencia' value='[" + obj_dependencias[0] + "]' onclick=\"SeleccionCalificaciones(this);\"/></td>");
                                            }
                                            out.print("<td>" + obj_dependencias[1] + "</td>");
                                            out.print("<td>" + obj_dependencias[4] + "</td>");
                                            out.print("<td>" + obj_dependencias[13] + "</td>");
                                            out.print("<td>" + obj_dependencias[11] + "</td>");
                                            out.print("</tr>");
                                        }
                                    }
                                }
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager1 = new Pager1('resultados_1', 10);");
                            out.print("pager1.init_1();");
                            out.print("pager1.showPageNav_1('pager1','NavPosicion1');");
                            out.print("pager1.showPage_1(1);");
                            out.print("</script>");
                            out.print("</fieldset>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                    }
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                } // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODULO INFORME CALIFICACIONES">
                else if (pageContext.getRequest().getAttribute("Calificacion").toString().equals("Modulo_informes_calificacion")) {
                    id_calificacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_calificacion").toString());
                    id_informe = Integer.parseInt(pageContext.getRequest().getAttribute("Id_informe").toString());
                    id_informe_visor = Integer.parseInt(pageContext.getRequest().getAttribute("Id_informe_visor").toString());
                    lst_calificacion = jpacclf.Traer_calificacion_id(id_calificacion);
                    Object[] obj_calificacion = (Object[]) lst_calificacion.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("");
                    //<editor-fold defaultstate="collapsed" desc="INFORME">
                    if (id_informe_visor > 0) {
                        lst_informe = jpacifm.Informes_id_informe(id_informe_visor);
                        Object[] obj_informe = (Object[]) lst_informe.get(0);
                        lst_calificacion = jpacclf.Traer_calificacion_id(Integer.parseInt(obj_informe[1].toString()));
                        dependencias_informe = obj_informe[26].toString();
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;height:600px;overflow:scroll;position: absolute;top: 2px;left:2%;'>");
                        out.print("<div style='float:right;'><a href='Calificacion?opc=5&icl=" + id_calificacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                        out.print("<div style='float:left;'><a onclick='Imprimir();' ><img src=\"Interfaz/Contenido/Iconos/Printer.png\" alt=\"\" title='Imprimir' /></a> Imprimir o PDF </div>");
                        out.print("<div id='Imprimir'>");
//                        if (obj_informe[18].toString().equals("VALIDACION")) {
                        //<editor-fold defaultstate="collapsed" desc="INFORME DE VALIDACION">
                        out.print("<table class='table' style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td colspan='9' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center'>"
                                + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='5' align='center'><b class='negro'>REGISTRO</b></td>");
                        out.print("<td colspan='3' align='center'><b class='negro'>NO CODIFICADO</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='center'><b>Calificacion</b></td>");
                        out.print("<td align='center'><b>Tipo</b></td>");
                        out.print("<td colspan='2' align='center'><b>Frecuencia</b></td>");
                        out.print("<td align='center'><b>Documento</b></td>");
                        out.print("<td align='center'><b>Grupo</b></td>");
                        out.print("<td colspan='3' align='center'><b>Flujo de trabajo</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' align='left'>" + obj_calificacion[1] + "</td>");
                        out.print("<td align='center' align='left'>" + obj_calificacion[4] + "</td>");
                        out.print("<td colspan='2' align='left'><b>ULT.</b>" + obj_calificacion[21] + "<br /><b>PROX.</b>" + obj_calificacion[22] + "</td>");
                        out.print("<td align='left'>" + obj_calificacion[13] + "</td>");
                        out.print("<td align='left'>" + obj_calificacion[11] + "</td>");
                        out.print("<td colspan='3' align='left'><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
                                + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
                                + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<th COLSPAN='9'>INFORMES DE VALIDACION</th>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td COLSPAN='3' style='width:33%'><h2>PQ</h2><b>Validación</b></td>");
                        out.print("<td COLSPAN='3' style='width:33%'><h2>OQ</h2><b>Calificacion de operación</b></td>");
                        out.print("<td COLSPAN='3' style='width:34%'><h2>IQ</h2><b>Calificacion de instalación</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        if (Integer.parseInt(obj_informe[9].toString()) == 6 || Integer.parseInt(obj_informe[9].toString()) == 5 || Integer.parseInt(obj_informe[9].toString()) == 4 || Integer.parseInt(obj_informe[9].toString()) == 2) {
                            //<editor-fold defaultstate="collapsed" desc="PQ">
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                            out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                            out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                            out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                            out.print(obj_informe[3].toString() + "");
                            out.print("</div>");
                            out.print("</td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
                                    if (obj_informes[20].toString().contains("/OQ") || obj_informes[20].toString().equals("OQ")) {
                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                        out.print(obj_informes[3].toString() + "");
                                        out.print("</div>");
                                    }
                                }
                            }
                            out.print("</td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
                                    if (obj_informes[20].toString().equals("IQ")) {
                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                        out.print(obj_informes[3].toString() + "");
                                        out.print("</div>");
                                    }
                                }
                            }
                            out.print("</td>");
                            //</editor-fold>
                        } else if (Integer.parseInt(obj_informe[9].toString()) == 1) {
                            //<editor-fold defaultstate="collapsed" desc="IQ">
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
                                    if (obj_informes[20].toString().equals("IQ")) {
                                        out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                        out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                        out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                                + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                        out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                        out.print(obj_informes[3].toString() + "");
                                        out.print("</div>");
                                    }
                                }
                            } else {
                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                                out.print(obj_informe[3].toString() + "");
                                out.print("</div>");
                            }
                            out.print("</td>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="OQ">
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                            out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
                            out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
                            out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
                            out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
                            out.print(obj_informe[3].toString() + "");
                            out.print("</div>");
                            out.print("</td>");
                            if (!dependencias_informe.equals("N/A")) {
                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                for (int i = 0; i < arg_dependencia.length; i++) {
                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
//                                    if (obj_informes[20].toString().contains("/OQ") || obj_informes[20].toString().equals("OQ") || obj_informes[20].toString().equals("IQ")) {
                                    out.print("<td COLSPAN='3' valign='top' style='text-align:left'>");
                                    out.print("<button class='accordion'>" + obj_informes[7] + "</button>");
                                    out.print("<div class='panel' style='border: 2px solid " + obj_informes[27] + "'>");
                                    out.print("<h3 ><b style='color:" + obj_informes[27] + ";'>" + obj_informes[18] + " " + obj_informes[6] + "</b></h3>");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Documento : </b>" + obj_informes[12] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                            + "<b style='color:" + obj_informes[27] + ";'>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<hr />");
                                    out.print("<b style='color:" + obj_informes[27] + ";'>Contenido : </b>" + obj_informes[28] + "<hr />");
                                    out.print(obj_informes[3].toString() + "");
                                    out.print("</div>");
                                    out.print("</td>");
//                                    }
                                }
                            } else {
//                                out.print("<button class='accordion'>" + obj_informe[7] + "</button>");
//                                out.print("<div class='panel' style='border: 2px solid " + obj_informe[27] + "'>");
//                                out.print("<h3 ><b style='color:" + obj_informe[27] + ";'>" + obj_informe[18] + " " + obj_informe[6] + "</b></h3>");
//                                out.print("<b style='color:" + obj_informe[27] + ";'>Contenido : </b>" + obj_informe[28] + "<hr />");
//                                out.print(obj_informe[3].toString() + "");
//                                out.print("</div>");
                            }
//                            out.print("<td COLSPAN='3' valign='top' style='text-align:left'></td>");
                            //</editor-fold>
                        }

                        out.print("</tr>");
                        out.print("</table>");
                        //</editor-fold>
//                        } else {
//                            //<editor-fold defaultstate="collapsed" desc="INFORME DE CALIFICACION">
//                            out.print("<h3>Detalle Informe de calificación</h3>");
//                            out.print("<table class='table2' style='width:100%' >");
//                            //<editor-fold defaultstate="collapsed" desc="CABECERA">
//                            out.print("<tr>");
//                            out.print("<td colspan='10' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td align='center'>"
//                                    + "<img src='Interfaz/Contenido/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
//                                    + "</td>");
//                            out.print("<td colspan='6' align='center'><b class='negro'>REGISTRO</b></td>");
//                            out.print("<td colspan='3' align='center'><b class='negro'>NO CODIFICADO</b></td>");
//                            out.print("</tr>");
////</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="CALIFICACCION">
//                            out.print("<tr>");
//                            out.print("<th colspan='10'>Calificación</th>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td colspan='2' align='center'><b>Calificacion</b></td>");
//                            out.print("<td align='center'><b>Tipo</b></td>");
//                            out.print("<td colspan='2' align='center'><b>Frecuencia</b></td>");
//                            out.print("<td align='center'><b>Documento</b></td>");
//                            out.print("<td align='center'><b>Grupo</b></td>");
//                            out.print("<td colspan='3' align='center'><b>Flujo de trabajo</b></td>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<td colspan='2' align='left'>" + obj_calificacion[1] + "</td>");
//                            out.print("<td align='center' align='left'>" + obj_calificacion[4] + "</td>");
//                            out.print("<td colspan='2' align='left'><b>ULT.</b>" + obj_calificacion[21] + "<br /><b>PROX.</b>" + obj_calificacion[22] + "</td>");
//                            out.print("<td align='left'>" + obj_calificacion[13] + "</td>");
//                            out.print("<td align='left'>" + obj_calificacion[11] + "</td>");
//                            out.print("<td colspan='3' align='left'><b>Ejecuta : </b>" + obj_calificacion[14] + "<br />"
//                                    + "<b>Revisa : </b>" + obj_calificacion[15] + "<br />"
//                                    + "<b>Aprueba : </b>" + obj_calificacion[16] + "</td>");
//                            out.print("</tr>");
////</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="INFORME PRINCIPAL">
//                            out.print("<tr>");
//                            out.print("<th colspan='10'>Informe Principal</th>");
//                            out.print("</tr>");
//                            out.print("<tr>");
//                            out.print("<th style='background-color:" + obj_informe[27] + ";width:5%'>" + obj_informe[18] + "<br />" + obj_informe[6] + "</th>");
//                            out.print("<td valign='top' colspan='4' style='width:25%' align='left'>"
//                                    //                                + "<b>" + obj_informe[15] + " </b>" + ((obj_informe[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informe[6].toString()) + "<br />"
//                                    //                                + "<b>" + obj_informe[16] + " </b>" + ((obj_informe[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informe[7].toString()) + "<br />"
//                                    //                                + "<b>" + obj_informe[17] + " </b>" + ((obj_informe[8] == null && obj_informe[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informe[7] != null) ? obj_informe[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                    + "<b>Califiacción : </b><br />" + obj_informe[7] + "<br />"
//                                    + "<b>Documento : </b><br />" + obj_informe[12] + "<br />"
//                                    + "<b>Tipo calificación : </b><br />" + obj_informe[20] + "<br />"
//                                    + "<b>Grupo : </b><br />" + obj_informe[22] + " / " + obj_informe[23] + "<br />"
//                                    //+ "<b>Dependencia(s) : </b>" + obj_informe[22] + "</td>");
//                                    + "</td>");
//                            out.print("<td valign='top' colspan='6' align='left'>");
//                            out.print("<b>Contenido : </b>" + obj_informe[28]);
//                            out.print("<button class='accordion'>Informe</button>");
//                            out.print("<div class='panel'>");
//                            out.print(obj_informe[3].toString().split("<hr />")[0] + "");
//                            out.print("</div>");
//                            out.print("<button class='accordion'>Conclusión</button>");
//                            out.print("<div class='panel'>");
//                            out.print(obj_informe[3].toString().split("<hr />")[1] + "");
//                            out.print("</div>");
//                            out.print("<button class='accordion'>Desviaciones</button>");
//                            out.print("<div class='panel'>");
//                            out.print(obj_informe[3].toString().split("<hr />")[2] + "");
//                            out.print("</div>");
//                            out.print("<button class='accordion'>Responsables</button>");
//                            out.print("<div class='panel'>"
//                                    + "<b>" + obj_informe[13] + " </b><br />"
//                                    + "<b>" + obj_informe[14] + " </b><br />"
//                                    + "<b>" + obj_informe[15] + " </b><hr />"
//                                    //                                + "<b>" + obj_informe[15] + " </b>" + ((obj_informe[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informe[6].toString()) + "<br />"
//                                    //                                + "<b>" + obj_informe[16] + " </b>" + ((obj_informe[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informe[7].toString()) + "<br />"
//                                    //                                + "<b>" + obj_informe[17] + " </b>" + ((obj_informe[8] == null && obj_informe[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informe[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informe[7] != null) ? obj_informe[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                    + "");
//                            out.print("</div>");
//                            out.print("</td>");
//                            out.print("</tr>");
////</editor-fold>
//                            //<editor-fold defaultstate="collapsed" desc="INFORME DEPENDENCIA">
//                            if (!dependencias_informe.equals("N/A")) {
//                                out.print("<tr>");
//                                out.print("<th colspan='10'>Informe(s) de dependencia</th>");
//                                out.print("</tr>");
//                                String[] arg_dependencia = dependencias_informe.replace("][", "-").replace("[", "").replace("]", "").split("-");
//                                for (int i = 0; i < arg_dependencia.length; i++) {
//                                    lst_informes = jpacifm.Informes_id_informe(Integer.parseInt(arg_dependencia[i]));
//                                    Object[] obj_informes = (Object[]) lst_informes.get(0);
//                                    out.print("<tr>");
//                                    out.print("<th style='background-color:" + obj_informes[27] + ";width:5%'>" + obj_informes[18] + "<br />" + obj_informes[6] + "</th>");
//                                    out.print("<td valign='top' colspan='4' style='width:25%'align='left'>"
//                                            //                                        + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
//                                            //                                        + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
//                                            //                                        + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                            + "<b>Calificación : </b><br />" + obj_informes[7] + "<br />"
//                                            + "<b>Documento : </b><br />" + obj_informes[12] + "<br />"
//                                            + "<b>Tipo calificación : </b><br />" + obj_informes[20] + "<br />"
//                                            + "<b>Grupo : </b><br />" + obj_informes[22] + " / " + obj_informes[23] + "<br />"
//                                            //+ "<b>Dependencia(s) : </b>" + obj_informes[22] + "</td>");
//                                            + "</td>");
//                                    out.print("<td valign='top' colspan='6' align='left'>");
//                                    out.print("<b>Contenido : </b>" + obj_informes[28]);
//                                    out.print("<button class='accordion'>Informe</button>");
//                                    out.print("<div class='panel'>");
//                                    out.print(obj_informes[3].toString().split("<hr />")[0] + "");
//                                    out.print("</div>");
//                                    out.print("<button class='accordion'>Conclusión</button>");
//                                    out.print("<div class='panel'>");
//                                    out.print(obj_informes[3].toString().split("<hr />")[1] + "");
//                                    out.print("</div>");
//                                    out.print("<button class='accordion'>Desviaciones</button>");
//                                    out.print("<div class='panel'>");
//                                    out.print(obj_informes[3].toString().split("<hr />")[2] + "");
//                                    out.print("</div>");
//                                    out.print("<button class='accordion'>Responsables</button>");
//                                    out.print("<div class='panel'>"
//                                            + "<b>" + obj_informes[13] + " </b><br />"
//                                            + "<b>" + obj_informes[14] + " </b><br />"
//                                            + "<b>" + obj_informes[15] + " </b><hr />"
//                                            //                                        + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
//                                            //                                        + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
//                                            //                                        + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente revisión</b>")) + "<hr />"
//                                            + "");
//                                    out.print("</div>");
//                                    out.print("</td>");
//                                    out.print("</tr>");
//                                }
//                            }
////</editor-fold>
//                            out.print("</table>");
//                            //</editor-fold>
//                        }
                        out.print("</div>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    out.print("<div style='float:left;'><a href='Calificacion?opc=1&fto='><img src='Interfaz/Contenido/Iconos/Volver.png' width='22' height='22' title='Listar calificaciones'></a></div><br />");
                    if (!rol.equals("Consulta")) {
                        out.print("<h3><a href='Calificacion?opc=5&icl=" + id_calificacion + "&iif=-1'><img src='Interfaz/Contenido/Iconos/Plus.png' width='20px' height='20px' alt='edit' title='Desplegar Menu' /></a>"
                                + "Informes<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    } else {
                        out.print("<h3>Informes<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    }
                    dependencias = ((obj_calificacion[17].equals("N/A")) ? 0 : 1);
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR INFORME">
                    lst_tipo_informe = jpactif.Tipos_informe();
                    if (id_informe == -1) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:" + ((dependencias > 0) ? "1050px" : "500px") + ";position: absolute;top: 5px;left:" + ((dependencias > 0) ? "5%" : "25%") + ";'>");
                        out.print("<div style='float:right;'><a href='Calificacion?opc=5&icl=" + id_calificacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22' height='22' title='Cancelar'></a></div>");
                        out.print("<h3>Nuevo Informe</h3>");
                        if (dependencias > 0) {
                            out.print("<div style='width:500px;float:left'>");
                        }
                        out.print("<form action='Calificacion?opc=6&icl=" + id_calificacion + "&iif=0' onsubmit='Informe();' method='post' id='Form_informe'>");
                        out.print("<table align='left'><tr>");
                        out.print("<td><b>Fecha:</b></td>");
                        out.print("<td><input type='text' name='Txt_fecha' id=\"datepicker\"  placeholder='Fecha'>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script></td></tr>");
                        out.print("<tr><td><b>Tipo informe:</b></td>");
                        out.print("<td><select name='Cbx_tipo_informe' id='Cbx_tipo_informe' title='Tipo de informe' >");
                        out.print("<option value='0' >Tipo de informe</option>");
                        for (int i = 0; i < lst_tipo_informe.size(); i++) {
                            Object[] obj_tipo_informe = (Object[]) lst_tipo_informe.get(i);
                            if (Integer.parseInt(obj_tipo_informe[4].toString()) > 0) {
                                out.print("<option value='" + obj_tipo_informe[0] + "' style='color:" + obj_tipo_informe[3] + "'>" + obj_tipo_informe[1] + "</option>");
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_informe');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
                        out.print("<tr><td colspan='2'><b>Contenido:</b></td></tr>");
                        out.print("<tr><td colspan='2'><textarea name='Txt_contenido' id='Txt_contenido' style='width: 500px;' placeholder='Contenido' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_contenido');validation.add( Validate.Presence );</script></td></tr>");
                        out.print("</table>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 500px; height: 400' placeholder='descripcion'>");
                        out.print("<b>Informe de calificación : </b><br/>");
                        out.print("<div contenteditable='true'><p>*</p><p></p></div>");
                        out.print("<hr /><b>Conclusion : </b><br/>");
                        out.print("<div contenteditable='true'><p>*</p><p></p></div>");
                        out.print("<hr /><b>Desviaciones : </b><br/>");
                        out.print("<div contenteditable='true'><p>*</p><p></p></div>");
                        out.print("</textarea>");
                        out.print("<input type='submit' id='Btn_guardar_informe' style='display:" + ((dependencias > 0) ? "none" : "block") + ";' value='Guardar' />");
                        if (dependencias > 0) {
                            String[] arg_dependencias = obj_calificacion[17].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            out.print("<input type='hidden' name='Txt_seleccion_dependencias' id='Txt_seleccion_dependencias' value='' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones' id='Txt_seleccion_calificaciones' value='' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones_temp' id='Txt_seleccion_calificaciones_temp' value='' />");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("<div style='width:490px;float:right;overflow:scroll;height:480px'>");
                            for (int i = 0; i < arg_dependencias.length; i++) {
                                List lst_calificaciones_dependencia = jpacifm.Informes_id_calificacion(Integer.parseInt(arg_dependencias[i]));
                                if (lst_calificaciones_dependencia != null) {
                                    for (int j = 0; j < lst_calificaciones_dependencia.size(); j++) {
                                        Object[] obj_dependencias = (Object[]) lst_calificaciones_dependencia.get(j);
                                        if (j == 0) {
                                            out.print("<button class='accordion'><input type='checkbox' value='[" + obj_dependencias[1] + "]' onclick=\"ProgramarCalificaciones(this);\"/>" + obj_dependencias[25] + " | " + obj_dependencias[7] + "</button>");
                                            out.print("<div class='panel' align='left' style='padding-top: 10px;padding-right: 20px;padding-bottom: 30px;padding-left: 40px;'>");
                                        }
                                        out.print("<input type='checkbox' id='Cbx_dependencia' name='Cbx_dependencia' value='[" + obj_dependencias[0] + "]' onclick=\"SeleccionCalificacionesInforme(this);\"/> ");
                                        out.print("<b style='color:" + obj_dependencias[27] + "'>" + obj_dependencias[18] + " " + obj_dependencias[6] + " " + obj_dependencias[20] + "</b>");
                                        out.print("<br />");
                                    }
                                    out.print("</div>");
                                }
                            }
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            out.print("<input type='hidden' name='Txt_seleccion_dependencias' id='Txt_seleccion_dependencias' value='N/A' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones' id='Txt_seleccion_calificaciones' value='N/A' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones_temp' id='Txt_seleccion_calificaciones_temp' value='N/A' />");
                            out.print("</form>");
                        }
                        out.print("</fieldset>");
                        out.print("</div>");
                    } //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR INFORME">
                    else if (id_informe > 0) {
                        lst_informe = jpacifm.Informes_id_informe(id_informe);
                        Object[] obj_informe = (Object[]) lst_informe.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:" + ((dependencias > 0) ? "1050px" : "500px") + ";position: absolute;top: 5px;left:" + ((dependencias > 0) ? "5%" : "25%") + ";'>");
                        out.print("<div style='float:right;'><a href='Calificacion?opc=5&icl=" + id_calificacion + "'><img src='Interfaz/Contenido/Iconos/Delete.png' title='Cancelar'></a></div>");
                        out.print("<h3>Modificar Informe</h3>");
                        if (dependencias > 0) {
                            out.print("<div style='width:500px;float:left'>");
                        }
                        out.print("<form action='Calificacion?opc=6&icl=" + id_calificacion + "&iif=" + id_informe + "' onsubmit='Informe();' method='post' id='Form_informe'>");
                        out.print("<table align='left'><tr>");
                        out.print("<td><b>Fecha:</b></td>");
                        out.print("<td><input type='text' name='Txt_fecha' id=\"datepicker\" placeholder='Fecha' value='" + obj_informe[6] + "'/>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('datepicker');validation.add( Validate.Presence );</script></td></tr>");
                        out.print("<tr><td><b>Tipo informe:</b></td>");
                        out.print("<td><select name='Cbx_tipo_informe' id='Cbx_tipo_informe' title='Tipo de informe' >");
                        out.print("<option value='0' >Tipo de informe</option>");
                        for (int i = 0; i < lst_tipo_informe.size(); i++) {
                            Object[] obj_tipo_informe = (Object[]) lst_tipo_informe.get(i);
                            if (Integer.parseInt(obj_tipo_informe[4].toString()) > 0) {
                                if (Integer.parseInt(obj_tipo_informe[0].toString()) == Integer.parseInt(obj_informe[2].toString())) {
                                    out.print("<option value='" + obj_tipo_informe[0] + "' style='color:" + obj_tipo_informe[3] + "' selected>" + obj_tipo_informe[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_tipo_informe[0] + "' style='color:" + obj_tipo_informe[3] + "'>" + obj_tipo_informe[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo_informe');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
                        out.print("<tr><td colspan='2'><b>Contenido:</b></td></tr>");
                        out.print("<tr><td colspan='2'><textarea name='Txt_contenido' id='Txt_contenido' style='width: 500px;' placeholder='Contenido' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_informe[28] + "</textarea>");
                        out.print("<script type='text/javascript'>var validation = new LiveValidation('Txt_contenido');validation.add( Validate.Presence );</script></td></tr>");
                        out.print("</table>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 500px; height: 400' placeholder='descripcion'>");
                        out.print("" + obj_informe[3].toString().replace("<div>", "<div contenteditable='true'>") + "");
                        out.print("</textarea>");
                        if (obj_informe[26].toString().replace("N/A", "").length() > 0 || obj_informe[29].toString().replace("N/A", "").length() > 0) {
                            out.print("<input style='float:left' type='submit' id='Btn_guardar_informe' value='Guardar'>");
                        } else {
                            out.print("<input type='submit' id='Btn_guardar_informe' style='display:" + ((dependencias > 0) ? "none" : "block") + "' value='Guardar'>");
                        }
                        if (dependencias > 0) {
                            String[] arg_dependencias = obj_calificacion[17].toString().replace("][", "-").replace("[", "").replace("]", "").split("-");
                            out.print("<input type='hidden' name='Txt_seleccion_dependencias' id='Txt_seleccion_dependencias' value='" + obj_informe[26].toString().replace("N/A", "") + "' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones' id='Txt_seleccion_calificaciones' value='" + obj_informe[29].toString().replace("N/A", "") + "' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones_temp' id='Txt_seleccion_calificaciones_temp' value='" + obj_informe[29].toString().replace("N/A", "") + "' />");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("<div style='width:490px;float:right;overflow:scroll;height:480px'>");
                            for (int i = 0; i < arg_dependencias.length; i++) {
                                List lst_calificaciones_dependencia = jpacifm.Informes_id_calificacion(Integer.parseInt(arg_dependencias[i]));
                                if (lst_calificaciones_dependencia != null) {
                                    for (int j = 0; j < lst_calificaciones_dependencia.size(); j++) {
                                        Object[] obj_dependencias = (Object[]) lst_calificaciones_dependencia.get(j);
                                        if (j == 0) {
                                            out.print("<button class='accordion'><input type='checkbox' value='[" + obj_dependencias[1] + "]' " + ((obj_informe[29].toString().contains("[" + obj_dependencias[1] + "]")) ? "checked" : "") + " onclick=\"ProgramarCalificaciones(this);\"/>" + obj_dependencias[25] + " | " + obj_dependencias[7] + "</button>");
                                            out.print("<div class='panel' align='left' style='padding-top: 10px;padding-right: 20px;padding-bottom: 30px;padding-left: 40px;'>");
                                        }
                                        out.print("<input type='checkbox' id='Cbx_dependencia' name='Cbx_dependencia' value='[" + obj_dependencias[0] + "]' " + ((obj_informe[26].toString().contains("[" + obj_dependencias[0] + "]")) ? "checked" : "") + " onclick=\"SeleccionCalificacionesInforme(this);\"/> ");
                                        out.print("<b style='color:" + obj_dependencias[27] + "'>" + obj_dependencias[18] + " " + obj_dependencias[6] + " " + obj_dependencias[20] + "</b>");
                                        out.print("<br />");
                                    }
                                    out.print("</div>");
                                }
                            }
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            out.print("<input type='hidden' name='Txt_seleccion_dependencias' id='Txt_seleccion_dependencias' value='N/A' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones' id='Txt_seleccion_calificaciones' value='N/A' />");
                            out.print("<input type='hidden' name='Txt_seleccion_calificaciones_temp' id='Txt_seleccion_calificaciones_temp' value='N/A' />");
                            out.print("</form>");
                        }
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    lst_informes = jpacifm.Informes_id_calificacion(id_calificacion);
                    if (lst_informes == null) {
                        out.print("<center>");
                        out.print("<br /><br /><img src='Interfaz/Contenido/Iconos/Alert.png' style='width:126.5px;height:112.75px' alt='edit' title='No hay datos en la consulta' /><br />");
                        out.print("<b>No hay datos de informes registrados</b>");
                        out.print("</center>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width:100%'>");
                        out.print("<tr>");
                        //out.print("<th colspan='6'>Informes</th>");
                        out.print("<td colspan='6'></td>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_informes.size(); i++) {
                            Object[] obj_informes = (Object[]) lst_informes.get(i);
                            out.print("<tr>");
                            out.print("<th style='background-color:" + obj_informes[27] + ";width:5%'>" + obj_informes[18] + "<br />" + obj_informes[6] + "</th>");
                            out.print("<td valign='top' style='width:45%'>"
                                    //                                    + "<b>" + obj_informes[15] + " </b>" + ((obj_informes[6] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(1," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin ejecutar</b></a>" : obj_informes[6].toString()) + "<br />"
                                    //                                    + "<b>" + obj_informes[16] + " </b>" + ((obj_informes[7] == null) ? "<a href='#' onclick='ResponsabilidadesInforme(2," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin revisar</b></a>" : obj_informes[7].toString()) + "<br />"
                                    //                                    + "<b>" + obj_informes[17] + " </b>" + ((obj_informes[8] == null && obj_informes[7] != null) ? "<a href='#' onclick='ResponsabilidadesInforme(3," + obj_informes[0] + "," + id_calificacion + ")'><b class='rojo'>Sin aprobar</b></a>" : ((obj_informes[7] != null) ? obj_informes[8].toString() : "<b class='rojo'>Pendiente aprobar</b>")) + "<hr />"
                                    + "<b>" + obj_informes[13] + " </b><br />"
                                    + "<b>" + obj_informes[14] + " </b><br />"
                                    + "<b>" + obj_informes[15] + " </b><hr />"
                                    + "<b>Documento : </b>" + obj_informes[12] + "<br />"
                                    + "<b>Tipo calificación : </b>" + obj_informes[20] + "<br />"
                                    + "<b>Grupo : </b>" + obj_informes[22] + " / " + obj_informes[23] + "<br />"
                                    //+ "<b>Dependencia(s) : </b>" + obj_informes[22] + "</td>");
                                    + "</td>");
                            out.print("<td valign='top' style='width:50%'>");
                            out.print("<b>Contenido : </b>" + obj_informes[28]);
                            out.print("<button class='accordion'>Informe</button>");
                            out.print("<div class='panel'>");
                            out.print(obj_informes[3].toString().split("<hr />")[0] + "");
                            out.print("</div>");
                            out.print("<button class='accordion'>Conclusión</button>");
                            out.print("<div class='panel'>");
                            out.print(obj_informes[3].toString().split("<hr />")[1] + "");
                            out.print("</div>");
                            out.print("<button class='accordion'>Desviaciones</button>");
                            out.print("<div class='panel'>");
                            out.print(obj_informes[3].toString().split("<hr />")[2] + "");
                            out.print("</div>");
                            if (!rol.equals("Consulta")) {
                                if (i == 0) {
                                    out.print("<a href='Calificacion?opc=5&icl=" + id_calificacion + "&iif=" + obj_informes[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' title='Modificar informe'></a> ");
                                }
                            }
                            out.print("| <a href='Calificacion?opc=5&icl=" + id_calificacion + "&iiv=" + obj_informes[0] + "'><img src='Interfaz/Contenido/Iconos/Ver.png' title='Ver informe'></a> ");
                            if (Integer.parseInt(obj_informes[5].toString()) == 0 && Integer.parseInt(obj_informes[19].toString()) == 1) {
                                out.print("| <a href='#' onclick='InformeVigente(" + id_calificacion + "," + obj_informes[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' title='Ver informe'></a> ");
                            }
                            out.print("</td>");
                            out.print("</tr>");
//                            if (!rol.equals("Consulta")) {
//                                if (Integer.parseInt(obj_informes[5].toString()) == 1) {
//                                    out.print("| <a href='#' onclick='DesactivarTipoCalificacion(" + obj_informes[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar' /></a>");
//                                } else {
//                                    out.print("| <a href='#' onclick='ActivarTipoCalificacion(" + obj_informes[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar' /></a>");
//                                }
//                            }
                        }
                        out.print("</table>");
                        out.print("<script src='Interfaz/Acordeon/Js_accordeon.js'></script>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    //</editor-fold>
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
//</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_calificacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
