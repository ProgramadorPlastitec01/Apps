package Tags;

import Controladores_BD.AreaJpaController;
import Controladores_BD.CargoJpaController;
import Controladores_BD.CompetenciaJpaController;
import Controladores_BD.MenuJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_competencia extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //JPAS
            MenuJpaController jpacmnu = new MenuJpaController();
            CargoJpaController jpaccgo = new CargoJpaController();
            CompetenciaJpaController jpaccpt = new CompetenciaJpaController();
            AreaJpaController jpacara = new AreaJpaController();
            //VARIABLES GLOBALES
            String fechaps_incio = pageContext.getSession().getAttribute("FechaPS_inicio") + "";
            String fechaps_fin = pageContext.getSession().getAttribute("FechaPS_fin") + "";
            String usuario_nmb_apl = pageContext.getSession().getAttribute("Nombre_apellido") + "";
            String semaforo_comp = "uno,dos,tres,cuatro";
            String rangos_comp = "0 a 2.9,3.0 a 3.7,3.8 a 4.1,4.2 a 5";
            String titulos_comp = "REENTRENAMIENTO INMEDIATO practica supervisada y evaluación de Competencia en un tiempo no  superior a 2 meses por parte del jefe inmediato,Competente con deficiencias para realizar la labor (REENTRENAMIENTO)  y evaluación en un periodo no mayor a 2 meses por parte del jefe inmediato,Competente con recomendaciones para realizar su labor por parte del jefe inmediato.,Competente para realizar su labor";
            int formulario = 0;
            int id_cargo = 0;
            int origen = 0;
            int id_area = 0;
            long documento = 0;
            int id_especialidad = 0;
            int id_mc_cargo = 0;
            int alerta_control = 0;
            int id_mc_calificacion = 0;
            double cal_min = 0;
            double cal_max = 5;
            List lst_cargos = null;
            List lst_cargos_especiales = null;
            List lst_areas = null;
            List lst_mc_cargo = null;
            List lst_mc_cargos = null;
            List lst_mc_calificaciones_realizadas = null;
            List lst_mc_grupos = null;
            List lst_mc_definiciones = null;
            List lst_mc_calificacion = null;
            List lst_mc_sst_calificacion = null;
            List lst_mc_sst_rendicion = null;
            List lst_mc_sst_definicion = null;
            List lst_grupos = null;
            List lst_personal = null;
            List lst_grupos_porcentajes = null;
            List lst_opciones_permisos = null;
            List lst_personal_calificado = null;
            String permisos = "";
            String titulo = "";
            String version = "0";
            String usuario_registro = "";
            String codigo = "";
            String frecuencia = "";
            String nombres = "";
            String apellidos = "";
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            int id_area_s = Integer.parseInt(pageContext.getSession().getAttribute("Id_areaS").toString());
            int consulta_personal_s = Integer.parseInt(pageContext.getSession().getAttribute("Consulta_personalS").toString());
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int idUSer = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());

            int id_opcion_menu = 0;
            if (pageContext.getRequest().getAttribute("Competencias") != null) {
                //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                id_opcion_menu = Integer.parseInt(pageContext.getRequest().getAttribute("Permisos").toString());
                lst_opciones_permisos = jpacmnu.Opciones_usuario_id(id_opcion_menu, menu);
                if (lst_opciones_permisos != null) {
                    Object[] obj_permisos = (Object[]) lst_opciones_permisos.get(0);
                    permisos = obj_permisos[3].toString();
                } else {
                    permisos = "";
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR FORMATOS COMPETENCIA">
                if (pageContext.getRequest().getAttribute("Competencias").equals("Registrar_competencia")) {
                    id_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_cargo").toString());
                    id_mc_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_mc_cargo").toString());
                    //<editor-fold defaultstate="collapsed" desc="PLANTILLA">
                    out.print("<div id='sidebar' >");
                    out.print("<h3>Registrar Competencia</h3>");
                    out.print("<form action='Competencia?opc=1&mnu=27' method='post' id='FormCargo'>");
                    lst_cargos = jpaccgo.Consultar_cargos();
                    out.print("Cargo :");
                    out.print("<select name='Cbx_cargo' id='Cbx_cargo' onchange=\"javascript:document.forms['FormCargo'].submit();\">");
                    out.print("<option value='0'>Click para seleccionar</option>");
                    for (int i = 0; i < lst_cargos.size(); i++) {
                        Object[] obj_cargos = (Object[]) lst_cargos.get(i);
                        if (Integer.parseInt(obj_cargos[5].toString()) == 1) {
                            out.print("<option value='" + obj_cargos[0] + "' " + (((Integer) obj_cargos[0] == id_cargo) ? "selected" : "") + ">" + obj_cargos[4] + " / " + obj_cargos[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_cargo');");
                    out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("</form>");
                    if (id_cargo > 0) {
                        lst_mc_cargos = jpaccpt.Consultar_formatos_vigentes_cargo(id_cargo);
                        if (lst_mc_cargos != null) {
                            out.print("<form action='Competencia?opc=1&mnu=27' method='post' id='FormMcCargo'>");
                            out.print("Formatos Competencia :");
                            out.print("<input type='hidden' name='Cbx_cargo' value='" + id_cargo + "' />");
                            out.print("<select name='Cbx_mc_cargo' id='Cbx_mc_cargo' onchange=\"javascript:document.forms['FormMcCargo'].submit();\">");
                            out.print("<option value='0'>Click para seleccionar</option>");
                            for (int i = 0; i < lst_mc_cargos.size(); i++) {
                                Object[] obj_mc_cargos = (Object[]) lst_mc_cargos.get(i);
                                out.print("<option value='" + obj_mc_cargos[0] + "' " + (((Integer) obj_mc_cargos[0] == id_mc_cargo) ? "selected" : "") + ">" + obj_mc_cargos[6] + " versión " + obj_mc_cargos[7] + "</option>");
                            }
                            out.print("</select>");
                            out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_mc_cargo');");
                            out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</form>");
                        }
                    }
                    lst_mc_cargo = jpaccpt.Consultar_cargos_competencias_id(id_mc_cargo);
                    if (lst_mc_cargo != null) {
                        Object[] obj_mc_cargo = (Object[]) lst_mc_cargo.get(0);
                        codigo = obj_mc_cargo[6].toString();
                        version = ((Integer) obj_mc_cargo[7] + 1) + "";
                        titulo = obj_mc_cargo[8].toString();
                        frecuencia = obj_mc_cargo[9].toString();
                    }
                    if (id_cargo > 0) {
                        out.print("<form action='Competencia?opc=2&Id_cargo=" + id_cargo + "' method='post' name='FormMcCargoFinal'>");
                    }
                    out.print("Codigo Registro:");
                    //out.print("<input type='text' onkeyup='Verificar_datos();' name='Txt_codigo' id='Txt_codigo' value='" + codigo + "' " + ((id_mc_cargo > 0) ? "readonly='true'" : "") + " placeholder='Codigo registro' onchange='javascript:this.value=this.value.toUpperCase();' />"
                    out.print("<input type='text' onkeyup='Verificar_datos();' name='Txt_codigo' id='Txt_codigo' value='" + codigo + "' " + ((id_mc_cargo > 0) ? "" : "") + " placeholder='Codigo registro' onchange='javascript:this.value=this.value.toUpperCase();' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script>");
                    out.print("Versión :");
                    out.print("<input type='number' onkeyup='Verificar_datos();' onkeypress='return event.charCode >= 48 && event.charCode <= 57' name='Txt_version' id='Txt_version' value='" + version + "' min='" + version + "' placeholder='Versión' required='true' />");
                    out.print("Titulo :");
                    out.print("<textarea onkeyup='Verificar_datos();' name='Txt_titulo' id='Txt_titulo' placeholder='Titulo registro' onchange='javascript:this.value=this.value.toUpperCase();' />" + titulo + "</textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_titulo');val1.add(Validate.Presence);</script>");
                    out.print("Frecuencia :");
                    out.print("<input type='text' onkeyup='Verificar_datos();' onkeypress='return event.charCode >= 48 && event.charCode <= 57' name='Txt_frecuencia' id='Txt_frecuencia' value='" + frecuencia + "' placeholder='Frecuencia' onchange='javascript:this.value=this.value.toUpperCase();' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_frecuencia');val1.add(Validate.Presence);</script>");
                    lst_grupos = jpaccpt.Consultar_grupos_competencias();
                    lst_grupos_porcentajes = jpaccpt.Porcentajes_grupo_formato_cargo(id_mc_cargo);
                    String arg_grupos = "";
                    String arg_grupos_id = "";
                    if (lst_grupos != null) {
                        out.print("<table class='table'>");
                        out.print("<tr>");
                        out.print("<th>Grupo</th>");
                        out.print("<th>%</th>");
                        out.print("</tr>");
                        int sum_grupos = 0;
                        for (int i = 0; i < lst_grupos.size(); i++) {
                            Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                            if ((Integer) obj_grupos[4] == 0) {
                                out.print("<div style='display:none'><input type='text' id='Txt_" + obj_grupos[1] + "' name='Txt_" + obj_grupos[1] + "' /></div>");
                                if (arg_grupos.length() > 1) {
                                    arg_grupos = arg_grupos + "-" + obj_grupos[1];
                                    arg_grupos_id = arg_grupos_id + "-" + obj_grupos[0];
                                } else {
                                    arg_grupos = obj_grupos[1].toString();
                                    arg_grupos_id = obj_grupos[0].toString();
                                }
                                out.print("<tr>");
                                out.print("<td>" + obj_grupos[1] + "</td>");
                                if (lst_grupos_porcentajes != null) {
                                    for (int j = 0; j < lst_grupos_porcentajes.size(); j++) {
                                        Object[] obj_grupos_porcentaje = (Object[]) lst_grupos_porcentajes.get(j);
                                        if ((Integer) obj_grupos[0] == (Integer) obj_grupos_porcentaje[0]) {
                                            out.print("<td><input type='number' value='" + obj_grupos_porcentaje[1] + "' onchange=\"Verificar_datos();Calcular_total_grupos()\" onkeyup=\"Verificar_datos();Calcular_total_grupos()\" onclick=\"Verificar_datos();Calcular_total_grupos()\"  id='Txt_valor_" + obj_grupos[0] + "' name='Txt_valor_" + obj_grupos[0] + "' min='5' max='100' step='5' placeholder='%' style='width:80%;height:20px;' /></td>");
                                            sum_grupos += (Integer) obj_grupos_porcentaje[1];
                                        }
                                    }
                                    if ((Integer) obj_grupos[0] == 7 && lst_grupos_porcentajes.size() == 6) {
                                        out.print("<td><input type='number' value='0' onchange=\"Verificar_datos();Calcular_total_grupos()\" onkeyup=\"Verificar_datos();Calcular_total_grupos()\" onclick=\"Verificar_datos();Calcular_total_grupos()\" id='Txt_valor_7' name='Txt_valor_7' min='5' max='100' step='5' placeholder='%' style='width:80%;height:20px;' /></td>");
                                    }
                                } else {
                                    out.print("<td><input type='number' value='0' onchange=\"Verificar_datos();Calcular_total_grupos()\" onkeyup=\"Verificar_datos();Calcular_total_grupos()\" onclick=\"Verificar_datos();Calcular_total_grupos()\"  id='Txt_valor_" + obj_grupos[0] + "' name='Txt_valor_" + obj_grupos[0] + "' min='5' max='100' step='5' placeholder='%' style='width:80%;height:20px;' /></td>");
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("<tr>");
                        out.print("<th>Total</th>");
                        out.print("<td align='center'><b id='Total_grupo'>" + ((lst_grupos_porcentajes != null) ? "" + sum_grupos : "---") + "</b></td>");
                        out.print("</tr>");
                        out.print("</table>");
                    }
                    out.print("<input type='hidden' id='Txt_grupos' value='" + arg_grupos + "' />");
                    out.print("<input type='hidden' id='Txt_grupos_id' value='" + arg_grupos_id + "' />");
                    out.print("<input type='submit' style='display:" + ((lst_grupos_porcentajes != null) ? "block" : "none") + "' id='Btn_registrar_competencia' value='Contruir Formato' />");
                    if (id_cargo > 0) {
                        out.print("</form>");
                    }
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CUERPO">
                    out.print("<div id='content'>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<td colspan='20' style='background-color:#c0392b;border-radius:20px' align='center'><b style='color:white;'>FORMATO EN ELABORACIÓN</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' style='width:20%' >"
                            + "<img src='Interfaz/MasterPage/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='10' style='width:60%' align='center'><b class='negro'>REGISTRO<hr />" + titulo + "</b></td>");
                    //out.print("<td colspan='3'>CODIGO R-RH-009<hr />VERSION 4</td>");
                    out.print("<td colspan='5' style='width:20%' align='center'><b class='negro'>CODIGO " + codigo + "<hr />VERSIÓN " + version + "</b></td>");
                    out.print("</tr>");
                    //DETALLE
                    if (id_mc_cargo > 0) {
                        lst_mc_grupos = jpaccpt.Consultar_grupos_definicion_id_cargo(id_mc_cargo);
                        for (int i = 0; i < lst_mc_grupos.size(); i++) {
                            Object[] obj_mc_grupos = (Object[]) lst_mc_grupos.get(i);
                            out.print("<tr>");
                            if ((Integer) obj_mc_grupos[0] == 7) {
                                out.print("<td align='center' ><b>" + obj_mc_grupos[1] + "</b></td>");
                            } else {
                                out.print("<td align='center' ><b>" + obj_mc_grupos[1] + "</b>"
                                        + "<br /><span onclick=\"Detalle_formato_competencia_add('" + obj_mc_grupos[1] + "')\" class=\"fa fa-plus fa-size_small\"></span>"
                                        + "<br /><span onclick=\"Detalle_formato_competencia_delete('" + obj_mc_grupos[1] + "')\" class=\"fa fa-times fa-size_small\"></span></td>");
                            }
                            lst_mc_definiciones = jpaccpt.Consultar_definicion_competencias_id_cargo(id_mc_cargo, Integer.parseInt(obj_mc_grupos[0].toString()));
                            String detalle_grupo = "";
                            for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                                Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                                if (j == 0) {
                                    detalle_grupo = obj_mc_definicion[0] + "";
                                } else {
                                    detalle_grupo = detalle_grupo + "-" + obj_mc_definicion[0];
                                }
                            }
                            out.print("<td colspan='19' id='Txt_td_" + obj_mc_grupos[1] + "'>");
                            for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                                Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                                if (j == 0) {
                                    out.print("<b>DEFINICIÓN :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_mc_grupos[1] + "')\">" + obj_mc_definicion[5] + "</div><br />");
                                    out.print("<b>CONDUCTA :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_mc_grupos[1] + "')\">" + obj_mc_definicion[6].toString().replace("*", "<br />*") + "</div>");
                                } else {
                                    out.print("<hr>");
                                    out.print("<b>DEFINICIÓN :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_mc_grupos[1] + "')\">" + obj_mc_definicion[5] + "</div><br />");
                                    out.print("<b>CONDUCTA :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_mc_grupos[1] + "')\">" + obj_mc_definicion[6].toString().replace("*", "<br />*") + "</div>");
                                }
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        if (lst_mc_grupos.size() == 6) {
                            out.print("<td align='center' ><b>SGSST</b></td>");
                            out.print("<td colspan='19' id='Txt_td_SGSST'>");
                            out.print("<b>DEFINICIÓN :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('SGSST')\">SGSST</div><br />");
                            out.print("<b>CONDUCTA :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('SGSST')\">RENDICION DE CUENTAS FRENTE AL SGSST</div>");
                            out.print("</td>");
                        }
                    } else {
                        for (int i = 0; i < lst_grupos.size(); i++) {
                            Object[] obj_grupos = (Object[]) lst_grupos.get(i);
                            out.print("<tr>");
                            if ((Integer) obj_grupos[4] == 0) {
                                if ((Integer) obj_grupos[0] == 7) {
                                    out.print("<td align='center' ><b>" + obj_grupos[1] + "</b></td>");
                                    out.print("<td colspan='19' id='Txt_td_" + obj_grupos[1] + "'>");
                                    out.print("<b>DEFINICIÓN :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_grupos[1] + "')\">SGSST</div><br />");
                                    out.print("<b>CONDUCTA :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_grupos[1] + "')\">RENDICION DE CUENTAS FRENTE AL SGSST</div>");
                                    out.print("</td>");
                                } else {
                                    // out.print("<td align='center' " + ((Integer.parseInt(obj_mc_grupos[3].toString()) > 0) ? "rowspan='" + obj_mc_grupos[3] + "'" : "  ") + "'><b>" + obj_mc_grupos[1] + "<br />" + obj_mc_grupos[2] + " %</b></td>");
                                    out.print("<td align='center' ><b>" + obj_grupos[1] + "</b>"
                                            + "<br /><span onclick=\"Detalle_formato_competencia_add('" + obj_grupos[1] + "')\" class=\"fa fa-plus fa-size_small\"></span>"
                                            + "<br /><span onclick=\"Detalle_formato_competencia_delete('" + obj_grupos[1] + "')\" class=\"fa fa-times fa-size_small\"></span></td>");
                                    out.print("<td colspan='19' id='Txt_td_" + obj_grupos[1] + "'>");
                                    out.print("<b>DEFINICIÓN :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_grupos[1] + "')\"></div><br />");
                                    out.print("<b>CONDUCTA :</b><div contenteditable='true' onkeyup=\"Detalle_formato_competencia('" + obj_grupos[1] + "')\"></div>");
                                    out.print("</td>");
                                }
                            }
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("</div>");
//</editor-fold>
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LISTA FORMATOS DE CALIFICACION DE COMPETENCIAS">
                else if (pageContext.getRequest().getAttribute("Competencias").equals("Consultar_competencias")) {
                    out.print("<div id='content_sin'>");
                    lst_mc_cargos = jpaccpt.Consultar_cargos_competencias();
                    out.print("<h3>Formatos de matriz de competencias<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Cargo</th>");
                    out.print("<th>Registro</th>");
                    out.print("<th>Titulo</th>");
                    out.print("<th>Opc.</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_mc_cargos.size(); i++) {
                        Object[] obj_mc_cargos = (Object[]) lst_mc_cargos.get(i);
                        out.print("<tr " + ((Integer.parseInt(obj_mc_cargos[11].toString()) == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_mc_cargos[2] + "</td>");
                        out.print("<td>" + obj_mc_cargos[6] + " versión " + obj_mc_cargos[7] + "</td>");
                        out.print("<td>" + obj_mc_cargos[8] + "</td>");
                        out.print("<td align='center'>");
                        if (Integer.parseInt(obj_mc_cargos[11].toString()) == 1) {
                            if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='DesactivarMcCargo(" + obj_mc_cargos[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                            }
                        } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                            out.print("<span onclick='ActivarMcCargo(" + obj_mc_cargos[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                        }
                        if (permisos.contains("V") || rol.equals("ADMINISTRADOR")) {
                            out.print("&nbsp;&nbsp;&nbsp;<a href='Competencia?opc=4&mnu=27&imccgo=" + obj_mc_cargos[0] + "'><span class='fa fa-file-alt fa-size_small'></span></a>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
                } //</editor-fold>                
                //<editor-fold defaultstate="collapsed" desc="CONSULTA FORMATO CARGO">
                else if (pageContext.getRequest().getAttribute("Competencias").equals("Consultar_competencias_cargo")) {
                    out.print("<div id='content_sin'>");
                    id_mc_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_mc_cargo").toString());
                    lst_mc_cargo = jpaccpt.Consultar_cargos_competencias_id(id_mc_cargo);
                    lst_mc_grupos = jpaccpt.Consultar_grupos_definicion_id_cargo(id_mc_cargo);
                    Object[] obj_mc_cargo = (Object[]) lst_mc_cargo.get(0);
                    out.print("<br /><a href='Competencia?opc=3&mnu=27'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_basico()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("<div id='Imprimir_basico'>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<td colspan='20' style='background-color:#ccc;border-radius:20px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' style='width:20%' >"
                            + "<img src='Interfaz/MasterPage/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='10' style='width:60%' align='center'><b class='negro'>REGISTRO<hr />" + obj_mc_cargo[8] + "</b></td>");
                    //out.print("<td colspan='3'>CODIGO R-RH-009<hr />VERSION 4</td>");
                    out.print("<td colspan='5' style='width:20%' align='center'>CODIGO " + obj_mc_cargo[6] + "<hr />VERSIÓN " + obj_mc_cargo[7] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='15'>IDENTIFICACIÓN</th>");
                    out.print("<th colspan='5'>EVALUADORES</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='5'><b>NOMBRE</b></td>");
                    out.print("<td colspan='10'></td>");
                    out.print("<td colspan='5'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='5'><b>CARGO</b></td>");
                    out.print("<td colspan='10'>" + obj_mc_cargo[2] + "</td>");
                    out.print("<td colspan='5'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='5'><b>FECHA</b></td>");
                    out.print("<td colspan='10'></td>");
                    out.print("<td colspan='5'></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>GRUPO</th>");
                    out.print("<th rowspan='2' >DEFINICIÓN</th>");
                    out.print("<th rowspan='2' colspan='8'>CONDUCTA</th>");
                    out.print("<th colspan='5'>CALIFICACION</th>");
                    out.print("<th rowspan='2' colspan='5'>OBSERVACIÓN</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:2%' align='center'><b>1</b></td>");
                    out.print("<td style='width:2%' align='center'><b>2</b></td>");
                    out.print("<td style='width:2%' align='center'><b>3</b></td>");
                    out.print("<td style='width:2%' align='center'><b>4</b></td>");
                    out.print("<td style='width:2%' align='center'><b>5</b></td>");
                    out.print("</tr>");
                    //DETALLE
                    for (int i = 0; i < lst_mc_grupos.size(); i++) {
                        out.print("<tr>");
                        Object[] obj_mc_grupos = (Object[]) lst_mc_grupos.get(i);
                        out.print("<td align='center' " + ((Integer.parseInt(obj_mc_grupos[3].toString()) > 0) ? "rowspan='" + obj_mc_grupos[3] + "'" : "  ") + "'><b>" + obj_mc_grupos[1] + "<br />" + obj_mc_grupos[2] + " %</b></td>");
                        lst_mc_definiciones = jpaccpt.Consultar_definicion_competencias_id_cargo(id_mc_cargo, Integer.parseInt(obj_mc_grupos[0].toString()));
                        String detalle_grupo = "";
                        for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                            Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                            if (j == 0) {
                                detalle_grupo = obj_mc_definicion[0] + "";
                            } else {
                                detalle_grupo = detalle_grupo + "-" + obj_mc_definicion[0];
                            }
                        }
                        for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                            Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                            if (j > 0) {
                                out.print("<tr>");
                            }
                            out.print("<td>" + obj_mc_definicion[5] + "</td>");
                            out.print("<td colspan='8'>" + obj_mc_definicion[6].toString().replace("*", "<br />*") + "</td>");
                            //<editor-fold defaultstate="collapsed" desc="RANGE">
                            out.print("<td style='width:3%'></td>");
                            out.print("<td style='width:3%'></td>");
                            out.print("<td style='width:3%'></td>");
                            out.print("<td style='width:3%'></td>");
                            out.print("<td style='width:3%'></td>");
//                            //</editor-fold>
                            out.print("<td colspan='5'></td>");
                            if (j == 0) {
                                out.print("</tr>");
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td colspan='20' style='background-color:#eee'></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td align='center'><b>100%</b></td>");
                    out.print("<td colspan='9' align='center' style='color: #2C3A47'><b>CALIFICACION FINAL</b></td>");
                    out.print("<td colspan='10' style='color: #2C3A47' align='center'><b id='Calificacion_final'></b></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA CALIFICACION DE COMPETENCIAS">
                else if (pageContext.getRequest().getAttribute("Competencias").equals("Consultar_calificaciones_realizadas")) {
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    nombres = pageContext.getRequest().getAttribute("Filtro_nombres").toString();
                    apellidos = pageContext.getRequest().getAttribute("Filtro_apellidos").toString();
                    documento = Long.parseLong(pageContext.getRequest().getAttribute("Filtro_documento").toString());
                    id_area = Integer.parseInt(pageContext.getRequest().getAttribute("Filtro_area").toString());
                    id_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("Filtro_cargo").toString());
                    id_especialidad = Integer.parseInt(pageContext.getRequest().getAttribute("Filtro_especialidad").toString());
//                    id_mc_calificacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_mc_calificacion").toString());
                    cal_min = Double.parseDouble(pageContext.getRequest().getAttribute("Cal_min").toString());
                    cal_max = Double.parseDouble(pageContext.getRequest().getAttribute("Cal_max").toString());
                    lst_mc_calificaciones_realizadas = jpaccpt.Consultar_calificacion_realizadas(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s, cal_min + "", cal_max + "");
                    out.print("<div id='content_sin'>");
                    lst_areas = jpacara.Consultar_areas();
                    if (id_area_s == 7 || rol.equals("Administrador")) {
                        lst_cargos = jpaccgo.Consultar_cargos();
                    } else {
                        lst_cargos = jpaccgo.Consultar_cargos_area(id_area_s);
                    }
                    if (formulario == 1) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;position: absolute;top: 5%;left:5%;'>");
                        out.print("<div style='float:right;'><a href='Competencia?opc=5&mnu=28&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Programar calificación de competencia</h3>");
                        out.print("<table style='width:100%'>");
                        out.print("<tr>");
                        //<editor-fold defaultstate="collapsed" desc="FORMULARIO">
                        out.print("<td valign='top' id='sidebar' style='width:10%;float:none;'>");
                        out.print("<form action='Competencia?opc=5&mnu=28&fml=1' method='post'>");
                        out.print("Nombres :<br /><input type='text' name='Txt_nombres' id='Txt_nombres' placeholder='Nombres' /><br />");
                        out.print("Apellidos :<br /><input type='text' name='Txt_apellidos' id='Txt_apellidos' placeholder='Apellidos' /><br />");
                        out.print("# Documento :<br /><input type='text' name='Txt_documento' id='Txt_documento' placeholder='CC' /><br />");
                        if (id_area_s == 7 || rol.equals("Administrador")) {
                            out.print("Área :");
                            out.print("<select name='Cbx_area' id='Cbx_area'>");
                            out.print("<option value='0'>Click para seleccionar</option>");
                            for (int i = 0; i < lst_areas.size(); i++) {
                                Object[] obj_areas = (Object[]) lst_areas.get(i);
                                out.print("<option value='" + obj_areas[0] + "'>" + obj_areas[1] + "</option>");
                            }
                            out.print("</select><br />");
                        } else {
                            out.print("<input type='hidden' id='Cbx_area' name='Cbx_area' value='" + id_area_s + "'>");
                        }
                        out.print("Cargo :");
                        out.print("<select name='Cbx_cargo' id='Cbx_cargo' >");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_cargos.size(); i++) {
                            Object[] obj_cargos = (Object[]) lst_cargos.get(i);
                            if (Integer.parseInt(obj_cargos[5].toString()) == 1 && Integer.parseInt(obj_cargos[6].toString()) == 0) {
                                out.print("<option value='" + obj_cargos[0] + "'>" + obj_cargos[4] + " / " + obj_cargos[1] + "</option>");
                            }
                        }
                        out.print("</select><br />");
                        if (id_area_s == 7 || rol.equals("Administrador")) {
                            lst_cargos_especiales = jpaccgo.Consultar_cargos();
                        } else {
                            lst_cargos_especiales = jpaccgo.Consultar_cargos_area(id_area_s);
                        }
                        if (lst_cargos_especiales == null || lst_cargos_especiales.size() == 0 || lst_cargos_especiales.isEmpty()) {
                            out.print("<input type='hidden' name='Cbx_especialidad' value='0' />");
                        } else {
                            int cont_especiales = 0;
                            for (int i = 0; i < lst_cargos_especiales.size(); i++) {
                                Object[] obj_cargos_especiales = (Object[]) lst_cargos_especiales.get(i);
                                if (Integer.parseInt(obj_cargos_especiales[5].toString()) == 1 && Integer.parseInt(obj_cargos_especiales[6].toString()) == 1) {
                                    cont_especiales++;
                                }
                            }
                            if (cont_especiales > 0) {
                                out.print("Especialidades :");
                                out.print("<select name='Cbx_especialidad' id='Cbx_especialidad' onchange=\"javascript:document.forms['FormCargo'].submit();\">");
                                out.print("<option value='0'>Click para seleccionar</option>");
                                for (int i = 0; i < lst_cargos_especiales.size(); i++) {
                                    Object[] obj_cargos_especiales = (Object[]) lst_cargos_especiales.get(i);
                                    if (Integer.parseInt(obj_cargos_especiales[5].toString()) == 1 && Integer.parseInt(obj_cargos_especiales[6].toString()) == 1) {
                                        out.print("<option value='" + obj_cargos_especiales[0] + "'>" + obj_cargos_especiales[4] + " / " + obj_cargos_especiales[1] + "</option>");
                                    }
                                }
                                out.print("</select><br />");
                            } else {
                                out.print("<input type='hidden' name='Cbx_especialidad' value='0' />");
                            }
                        }
                        out.print("<input type='submit' value='Consultar' />");
                        out.print("</form>");
                        out.print("</td>");
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="TABLA">
                        out.print("<td valign='top'>");
                        if ((nombres + apellidos).length() > 0 || (documento + id_area + id_cargo + id_especialidad) > 0) {
                            lst_personal = jpaccpt.Filtro_personal_competencias(nombres, apellidos, documento, id_area, id_cargo, id_especialidad);
                            if (lst_personal == null || lst_personal.isEmpty()) {
                                out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se encuentran resultados</center>");
                            } else {
                                out.print("<div style='float:right'><span id='Btn_save' style='display:none' class='fa fa-save fa-size_super_small' onclick='javascript:FormCalificacionPersonal.submit()'></span></div>");
                                out.print("<form action='Competencia?opc=10' method='post' id='FormCalificacionPersonal'>"
                                        + "<input type='hidden' id='Txt_seleccion_personal_calificacion' name='Txt_seleccion_personal_calificacion' />"
                                        + "</form>");
                                out.print("<div align='left' id='NavPosicion2'></div>");
                                out.print("<table class='table' id='resultados2' style=''>");
                                out.print("<tr>");
                                out.print("<th>Documento</th>");
                                out.print("<th>Apellido(s)</th>");
                                out.print("<th>Nombre(s)</th>");
                                out.print("<th>Área</th>");
                                out.print("<th>Competencia</th>");
                                out.print("</tr>");
                                lst_cargos_especiales = jpaccpt.Consultar_cargos_especiales_mc();
                                for (int i = 0; i < lst_personal.size(); i++) {
                                    Object[] obj_personal = (Object[]) lst_personal.get(i);
                                    out.print("<tr>");
                                    out.print("<td>" + obj_personal[0] + "</td>");
                                    out.print("<td>" + obj_personal[2] + "</td>");
                                    out.print("<td>" + obj_personal[1] + "</td>");
                                    out.print("<td>" + obj_personal[7] + "</td>");
                                    out.print("<td>"
                                            + "<input type='checkbox' id='Cbx_mc_cargo' value='[" + obj_personal[0] + "/" + obj_personal[11] + "]' onclick='SeleccionarPersonalCalificar(this)'/> "
                                            + "" + obj_personal[12] + " V " + obj_personal[13] + " " + obj_personal[14] + " " + obj_personal[5] + "");
                                    if (!obj_personal[15].toString().equals("N/A")) {
                                        for (int j = 0; j < lst_cargos_especiales.size(); j++) {
                                            Object[] obj_cargos_especiales = (Object[]) lst_cargos_especiales.get(j);
                                            if (obj_personal[15].toString().contains("[" + obj_cargos_especiales[0] + "]")) {
                                                out.print("<br />"
                                                        + "<input type='checkbox' id='Cbx_mc_cargo' value='[" + obj_personal[0] + "/" + obj_cargos_especiales[10] + "]' onclick='SeleccionarPersonalCalificar(this)'/> "
                                                        + "" + obj_cargos_especiales[7] + " V " + obj_cargos_especiales[8] + " " + obj_cargos_especiales[9] + " " + obj_cargos_especiales[1] + "");
                                            }
                                        }
                                    }
                                    out.print("</td>");
                                    out.print("</tr>");
                                }
                                out.print("</table>");
                                out.print("<script type='text/javascript'>");
                                out.print("var pager2 = new Pager2('resultados2', 10);");
                                out.print("pager2.init();");
                                out.print("pager2.showPageNav('pager2','NavPosicion2');");
                                out.print("pager2.showPage(1);");
                                out.print("</script>");
                            }
                        } else {
                            out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin valor filtrado</center>");
                        }
                        out.print("</td>");
                        //</editor-fold>
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                        //                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
//                    } else if (formulario == 2 && id_mc_calificacion > 0) {
////                        lst_enfermedad = jpacefm.Consultar_enfermedad_id(id_enfermedad);
////                        Object[] obj_enfermedad = (Object[]) lst_enfermedad.get(0);
////                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_enfermedad[1].toString());
////                        Object[] obj_persona = (Object[]) lst_persona.get(0);
//                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
//                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
//                        out.print("<div style='float:right;'><a href='Competencia?opc=5&mnu=28&fml=0'><span class='fa fa-times-circle fa-size_super_small'></span></a></div>");
//                        out.print("<h3>Modificar calificación de competencia</h3>");
//                        out.print("</fieldset>");
//                        out.print("</div>");
//                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONVENCIONES">
                    out.print("<div class='sweet-local' tabindex='-1' id='Convenciones' style='opacity: 1.03; display: none;'>");
                    out.print("<fieldset class='popup_local' style='width:30%;position: absolute;top: 15%;left:30%;'>");
                    out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Convenciones').style.display='none'\" class='fa fa-times fa-size_super_small'></span></div>");
                    out.print("<h3>Convenciones Calificación de Competencias</h3>");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Color</th>");
                    out.print("<th>Rango</th>");
                    out.print("<th>Clasificación</th>");
                    out.print("</tr>");
                    for (int i = 0; i < 4; i++) {
                        out.print("<tr>");
                        out.print("<td align='center'><span class='comp_graf " + semaforo_comp.split(",")[i] + "'></span></td>");
                        out.print("<td>" + rangos_comp.split(",")[i] + "</td>");
                        out.print("<td>" + titulos_comp.split(",")[i] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</fieldset>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<form action='Competencia?opc=5&mnu=28' id='FormRangoCalificacion' method='post'><h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Competencia?opc=5&mnu=28&fml=1'><span class='fa fa-user-check fa-size_super_small'></span></a>");
                    }
                    out.print("Calificaciones realizadas");
                    out.print("<div style='float:right'>"
                            + "<i onclick=\"javascript:document.getElementById('Convenciones').style.display='block'\">Convenciones "
                            + "<span class='comp_graf uno'></span> "
                            + "<span class='comp_graf dos'></span> "
                            + "<span class='comp_graf tres'></span> "
                            + "<span class='comp_graf cuatro'></span> </i>"
                            + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /> "
                            + "<input min='0' required max='5' type='number' step='any' value='" + cal_min + "' name='Txt_cal_min' style='width:50px' /> Entre "
                            + "<input type='number' name='Txt_cal_max' min='0' step='any' max='5' required value='" + cal_max + "' style='width:50px' />"
                            + "<input type='submit' style='display:none' id='Btn_buscar' /><span class=\"fas fa-search fa-size_super_small\" onclick=\"javascript:document.getElementById('Btn_buscar').click();\"></span>"
                            + "</div></h3></form>");
                    if (lst_mc_calificaciones_realizadas == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th>Fecha</th>");
                        out.print("<th>Formato</th>");
                        out.print("<th>Personal</th>");
                        out.print("<th>Calificación y recomendaciones</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_mc_calificaciones_realizadas.size(); i++) {
                            Object[] obj_mc_calificaciones = (Object[]) lst_mc_calificaciones_realizadas.get(i);
                            int result = Integer.parseInt(obj_mc_calificaciones[17].toString());
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_mc_calificaciones[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_mc_calificaciones[1] + ".jpg' style='width:200px;heigth:200px' /></span></b>");
                            //out.print("<div class='content_comp_graf' title='" + titulos_comp.split(",")[(result - 1)] + "'>");
                            out.print("<div class='content_comp_graf'>");
                            for (int j = 1; j < 5; j++) {
                                if (j == result) {
                                    out.print("<span class='comp_graf " + semaforo_comp.split(",")[(result - 1)] + "'></span> ");
                                } else if (j > result) {
                                    out.print("<span class='comp_graf cero'></span> ");
                                } else {
                                    out.print("<span class='comp_graf vacio'></span> ");
                                }
                            }
                            out.print("</div></td>");
                            out.print("<td align='center' style='width:8%'>" + obj_mc_calificaciones[8] + "</td>");
                            out.print("<td style='width:10%'>" + obj_mc_calificaciones[4] + " <b>V</b> " + obj_mc_calificaciones[5] + "</td>");
                            out.print("<td style='width:25%' valign='top'><b>Nombre :</b> " + obj_mc_calificaciones[2] + "<br /><b>Cargo :</b>" + obj_mc_calificaciones[6] + "<br /><b>Área :</b>" + obj_mc_calificaciones[7] + "</td>");
                            out.print("<td style='width:35%' valign='top'><b class='naranja'>Valor Calificación :</b>" + obj_mc_calificaciones[12] + "% | "
                                    + "<b class='negro'>" + obj_mc_calificaciones[16] + "</b><br />"
                                    + "" + titulos_comp.split(",")[(result - 1)]);
                            out.print("<hr /><b>Recomendaciones :</b>" + obj_mc_calificaciones[18]);
                            out.print("</td>");
                            out.print("<td align='center' style='width:8%'>");
                            if (Integer.parseInt(obj_mc_calificaciones[13].toString()) == 1) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarCalificacion(" + obj_mc_calificaciones[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarCalificacion(" + obj_mc_calificaciones[0] + ")' class='fa fa-lock fa-size_small'></span>");
                            }
                            if (permisos.contains("V") || rol.equals("ADMINISTRADOR")) {
                                out.print("&nbsp;&nbsp;&nbsp;<a href='Competencia?opc=6&mnu=28&imcclf=" + obj_mc_calificaciones[0] + "&act=0'><span class='fa fa-file-alt fa-size_small'></span></a>");
                            }
                            if (Integer.parseInt(obj_mc_calificaciones[13].toString()) == 1) {
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarCalificacion(" + obj_mc_calificaciones[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            }
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', 10);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                    }
                    out.print("</div>");
//</editor-fold>
                } //</editor-fold>         
                //<editor-fold defaultstate="collapsed" desc="CALIFICAR COMPETENCIA">
                else if (pageContext.getRequest().getAttribute("Competencias").equals("Calificar_competencias_cargo")) {
                    out.print("<div id='content_sin'>");
                    id_mc_calificacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_mc_calificacion").toString());
                    alerta_control = Integer.parseInt(pageContext.getRequest().getAttribute("Alerta_control").toString());
                    origen = Integer.parseInt(pageContext.getRequest().getAttribute("Origen").toString());
                    lst_mc_calificacion = jpaccpt.Consultar_calificacion_realizada_id(id_mc_calificacion);
                    Object[] obj_mc_calificacion = (Object[]) lst_mc_calificacion.get(0);
                    id_mc_cargo = Integer.parseInt(obj_mc_calificacion[3].toString());
                    String detalle_calificacion = obj_mc_calificacion[10].toString();
                    String[] arg_detalle_calificacion = obj_mc_calificacion[10].toString().split("]");
                    lst_mc_cargo = jpaccpt.Consultar_cargos_competencias_id(id_mc_cargo);
                    lst_mc_grupos = jpaccpt.Consultar_grupos_definicion_id_cargo(id_mc_cargo);
                    Object[] obj_mc_cargo = (Object[]) lst_mc_cargo.get(0);
                    String detalle_grupo_total = "";
                    if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 0) {
                        alerta_control = 0;
                    }
                    lst_mc_sst_calificacion = jpaccpt.Consultar_sst_rendicion_codigo(id_mc_calificacion);
                    for (int i = 0; i < lst_mc_grupos.size(); i++) {
                        Object[] obj_mc_grupos = (Object[]) lst_mc_grupos.get(i);
                        lst_mc_definiciones = jpaccpt.Consultar_definicion_competencias_id_cargo(id_mc_cargo, Integer.parseInt(obj_mc_grupos[0].toString()));
                        for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                            Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                            if (detalle_grupo_total.length() == 0) {
                                detalle_grupo_total = obj_mc_definicion[0] + "";
                            } else {
                                detalle_grupo_total = detalle_grupo_total + "-" + obj_mc_definicion[0];
                            }
                        }
                    }
                    int result = Integer.parseInt(obj_mc_calificacion[17].toString());
                    if (origen == 0) {
                        out.print("<br /><a href='Competencia?opc=5&mnu=28'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    } else {
                        out.print("<br /><a href='Competencia?opc=11&mnu=34&dcm=" + obj_mc_calificacion[1] + "'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    }
                    if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) {
                        if (permisos.contains("U")) {
                            out.print("<div style='float:right;' id='Guardar1'><span class='fa fa-save fa-size_super_small' onclick=\"Arreglo_calificacion('" + detalle_grupo_total + "');javascript:FormCalificacionComp.submit()\"></span></div>");
                        }
                    } else if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 0 && (idUSer == 4 || idUSer == 52)) {
                        out.print("<div style='float:right;' id='Guardar1'><span class='fa fa-save fa-size_super_small' onclick=\"javascript:FormCalificacionComp.submit()\"></span></div>");
                    }
                    
                    out.print("<h3 onclick=\"javascript:document.getElementById('Convenciones').style.display='block'\">Convenciones "
                            + "<span class='comp_graf uno'></span> "
                            + "<span class='comp_graf dos'></span> "
                            + "<span class='comp_graf tres'></span> "
                            + "<span class='comp_graf cuatro'></span> </h3>");
                    //<editor-fold defaultstate="collapsed" desc="CONVENCIONES">
                    out.print("<div class='sweet-local' tabindex='-1' id='Convenciones' style='opacity: 1.03; display: none;'>");
                    out.print("<fieldset class='popup_local' style='width:30%;position: absolute;top: 15%;left:30%;'>");
                    out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Convenciones').style.display='none'\" class='fa fa-times fa-size_super_small'></span></div>");
                    out.print("<h3>Convenciones Calificación de Competencias</h3>");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Color</th>");
                    out.print("<th>Rango</th>");
                    out.print("<th>Clasificación</th>");
                    out.print("</tr>");
                    for (int i = 0; i < 4; i++) {
                        out.print("<tr>");
                        out.print("<td align='center'><span class='comp_graf " + semaforo_comp.split(",")[i] + "'></span></td>");
                        out.print("<td>" + rangos_comp.split(",")[i] + "</td>");
                        out.print("<td>" + titulos_comp.split(",")[i] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</fieldset>");
                    out.print("</div>");
//</editor-fold>
                    out.print("<form action='Competencia?opc=7' method='post' id='FormCalificacionComp'>");
                    out.print("<input type='hidden' name='imcclf' value='" + id_mc_calificacion + "'>");
                    out.print("<input type='hidden' id='Cant_grupos_mc' value='" + lst_mc_grupos.size() + "'>");
                    out.print("<table class='table'>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<td colspan='20' style='background-color:#ccc;border-radius:20px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' style='width:20%' >"
                            + "<img src='Interfaz/MasterPage/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='10' style='width:60%' align='center'><b class='negro'>REGISTRO<hr />" + obj_mc_cargo[8] + "</b></td>");
                    //out.print("<td colspan='3'>CODIGO R-RH-009<hr />VERSION 4</td>");
                    out.print("<td colspan='5' style='width:20%' align='center'>CODIGO " + obj_mc_cargo[6] + "<hr />VERSIÓN " + obj_mc_cargo[7] + "</td>");
                    out.print("</tr>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS">
                    out.print("<tr>");
                    out.print("<th colspan='15'>IDENTIFICACIÓN</th>");
                    out.print("<th colspan='5'>EVALUADORES</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>NOMBRE</b></td>");
                    out.print("<td colspan='11'>" + obj_mc_calificacion[2] + "</td>");
                    out.print("<td valign='top' colspan='5' rowspan='3' id='Txt_td_evaluadores' " + ((Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) ? "contenteditable='true'" : "") + ">" + ((obj_mc_calificacion[9] == null || obj_mc_calificacion[9].toString().isEmpty() || obj_mc_calificacion[9].toString().toLowerCase().equals("null")) ? usuario_nmb_apl : obj_mc_calificacion[9]) + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>CARGO</b></td>");
                    out.print("<td colspan='11'>" + obj_mc_calificacion[6] + " / " + obj_mc_calificacion[7] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>FECHA</b></td>");
                    if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1 || idUSer == 52 || idUSer == 4) {
                        if (idUSer == 52 || idUSer == 4) {
                            out.print("<td colspan='11'><input type='text' id='datepicker' name='Txt_fecha' placeholder='Fecha de calificación' value='" + obj_mc_calificacion[8] + "' />"
                                    + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></td>");
                        } else {
                            out.print("<td colspan='11'>" + obj_mc_calificacion[8] + "<input type='hidden' name='Txt_fecha' value='" + obj_mc_calificacion[8] + "' /></td>");
                        }
                    } else {
                        out.print("<td colspan='11'>" + obj_mc_calificacion[8] + "</td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='4'><b>RECOMENDACIONES</b></td>");
                    out.print("<td valign='top' " + ((result == 2) ? "style='background-color:#ffffe3'" : (result == 1) ? "style='background-color:#ffdef2'" : "") + " colspan='11' id='Txt_td_recomendacion' " + ((Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) ? "contenteditable='true'" : "") + " onkeyup='Control_recomendaciones()' onfocus='Control_recomendaciones()' onkeydown='Control_recomendaciones()'>" + ((obj_mc_calificacion[18] == null || obj_mc_calificacion[18].toString().equals("null")) ? "NINGUNA" : obj_mc_calificacion[18]) + "</td>");
                    out.print("<td valign='top' colspan='5'><b class='negro'>CLASIFICACIÓN </b>");
                    for (int j = 1; j < 5; j++) {
                        if (j == result) {
                            out.print("<span class='comp_graf " + semaforo_comp.split(",")[(result - 1)] + "'></span> ");
                        } else if (j > result) {
                            out.print("<span class='comp_graf cero'></span> ");
                        } else {
                            out.print("<span class='comp_graf vacio'></span> ");
                        }
                    }
                    out.print("<br />" + (titulos_comp.split(",")[(result - 1)]) + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th rowspan='2'>GRUPO</th>");
                    out.print("<th rowspan='2'>DEFINICIÓN</th>");
                    out.print("<th rowspan='2' colspan='8'>CONDUCTA</th>");
                    out.print("<th colspan='5'>CALIFICACION</th>");
                    out.print("<th rowspan='2' colspan='5'>OBSERVACIÓN</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:2%' align='center'><b>1</b></td>");
                    out.print("<td style='width:2%' align='center'><b>2</b></td>");
                    out.print("<td style='width:2%' align='center'><b>3</b></td>");
                    out.print("<td style='width:2%' align='center'><b>4</b></td>");
                    out.print("<td style='width:2%' align='center'><b>5</b></td>");
                    out.print("</tr>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE">
                    for (int i = 0; i < lst_mc_grupos.size(); i++) {
                        out.print("<tr>");
                        Object[] obj_mc_grupos = (Object[]) lst_mc_grupos.get(i);
                        String total_grupo = "";
                        if (obj_mc_calificacion[11].toString().contains("[" + obj_mc_grupos[1] + "/")) {
                            String[] arg_total_grupo = obj_mc_calificacion[11].toString().split("]");
                            for (int j = 0; j < arg_total_grupo.length; j++) {
                                if (arg_total_grupo[j].contains("[" + obj_mc_grupos[1] + "/")) {
                                    total_grupo = arg_total_grupo[j].split("/")[1];
                                }
                            }
                        }
                        if ((Integer) obj_mc_grupos[0] == 7) {
                            if (lst_mc_sst_calificacion == null) {
                                out.print("<td align='center' style='border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757;border-left:1px dashed #3AA757'" + ((Integer.parseInt(obj_mc_grupos[3].toString()) > 0) ? "rowspan='" + obj_mc_grupos[3] + "'" : "  ") + "'><b>" + obj_mc_grupos[1] + "<br />" + obj_mc_grupos[2] + " %</b><br /><b id='Result_" + obj_mc_grupos[0] + "' style='color:orange'>Total " + 0 + "%</b></td>");
                            } else {
                                Object[] obj_mc_sst_calificacion = (Object[]) lst_mc_sst_calificacion.get(0);
                                total_grupo = "Total " + (((Double) obj_mc_sst_calificacion[5] * (Integer) obj_mc_grupos[2]) / 100) + "%";
                                out.print("<td align='center' style='border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757;border-left:1px dashed #3AA757'" + ((Integer.parseInt(obj_mc_grupos[3].toString()) > 0) ? "rowspan='" + obj_mc_grupos[3] + "'" : "  ") + "'><b>" + obj_mc_grupos[1] + "<br />" + obj_mc_grupos[2] + " %</b><br /><b id='Result_" + obj_mc_grupos[0] + "' style='color:orange'>" + total_grupo + "</b></td>");
                            }
                        } else {
                            out.print("<td align='center' " + ((Integer.parseInt(obj_mc_grupos[3].toString()) > 0) ? "rowspan='" + obj_mc_grupos[3] + "'" : "  ") + "'><b>" + obj_mc_grupos[1] + "<br />" + obj_mc_grupos[2] + " %</b><br /><b id='Result_" + obj_mc_grupos[0] + "' style='color:orange'>" + total_grupo + "</b></td>");
                        }
                        lst_mc_definiciones = jpaccpt.Consultar_definicion_competencias_id_cargo(id_mc_cargo, Integer.parseInt(obj_mc_grupos[0].toString()));
                        String detalle_grupo = "";
                        for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                            Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                            if (j == 0) {
                                detalle_grupo = obj_mc_definicion[0] + "";
                            } else {
                                detalle_grupo = detalle_grupo + "-" + obj_mc_definicion[0];
                            }
                        }
                        for (int j = 0; j < lst_mc_definiciones.size(); j++) {
                            Object[] obj_mc_definicion = (Object[]) lst_mc_definiciones.get(j);
                            if (j > 0) {
                                out.print("<tr>");
                            }
                            out.print("<td " + (((Integer) obj_mc_grupos[0] == 7) ? "style='border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757'" : "") + ">" + obj_mc_definicion[5] + "</td>");
                            out.print("<td " + (((Integer) obj_mc_grupos[0] == 7) ? "style='border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757'" : "") + " colspan='8' style='width:40%'>" + obj_mc_definicion[6].toString().replace("*", "<br />*") + "</td>");
                            if (detalle_calificacion.contains("[" + obj_mc_definicion[0] + "°")) {
                                for (int k = 0; k < arg_detalle_calificacion.length; k++) {
                                    if (arg_detalle_calificacion[k].contains("[" + obj_mc_definicion[0] + "°")) {
                                        String valor = arg_detalle_calificacion[k].split("°")[1];
                                        String observacion = arg_detalle_calificacion[k].split("°")[2];
                                        if ((Integer) obj_mc_grupos[0] == 7) {
                                            if (lst_mc_sst_calificacion == null || lst_mc_sst_calificacion.isEmpty()) {
                                                out.print("<td colspan='5' style='width:3%;font-weight:bold;border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757'>"
                                                        + "<input type='hidden' id='Id" + obj_mc_definicion[0] + "' value='" + obj_mc_definicion[0] + "' />"
                                                        + "<input type='hidden' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_definicion[0] + "' value='0' />"
                                                        + "<a href='Competencia?opc=12&mnu=28&Id_mc_calificacion=" + obj_mc_calificacion[0] + "&Txt_codigo_sst=" + obj_mc_cargo[14] + "' style='color:#3AA757;'><i>" + obj_mc_cargo[14] + "</i></a><br /><i>Valor : 0</i></td>");
                                            } else {
                                                Object[] obj_mc_sst_calificacion = (Object[]) lst_mc_sst_calificacion.get(0);
                                                out.print("<td colspan='5' style='width:3%;font-weight:bold;border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757'>"
                                                        + "<input type='hidden' id='Id" + obj_mc_definicion[0] + "' value='" + obj_mc_definicion[0] + "' />"
                                                        + "<input type='hidden' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_definicion[0] + "' value='" + obj_mc_sst_calificacion[9] + "' />"
                                                        + "<a href='Competencia?opc=12&mnu=28&Id_mc_calificacion=" + obj_mc_calificacion[0] + "&Txt_codigo_sst=" + obj_mc_cargo[14] + "' style='color:#3AA757;'><i>" + obj_mc_cargo[14] + "</i></a><br /><i>Valor : " + obj_mc_sst_calificacion[9] + "</i>"
                                                        + "</td>");
                                                if (alerta_control == 1) {
                                                    out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                                                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:35%;font-size:16px'>");
                                                    out.print("<p>Favor dar clik en el enlace para actualizar la calificación de competencias con el valor de la calificación de rendición de cuentas.</p>");
                                                    out.print("<a href='#' onclick=\"Calcular_calificacion(5, " + obj_mc_grupos[2] + ", " + (lst_mc_definiciones.size() * 5) + ", 7,'" + detalle_grupo + "');Arreglo_calificacion('" + detalle_grupo_total + "');javascript:FormCalificacionComp.submit();\"><i>Actualizar calificación</i></a>");
                                                    out.print("</fieldset>");
                                                    out.print("</div>");
                                                    alerta_control = 0;
                                                }
                                            }
                                        } else if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) {
                                            out.print("<td colspan='5' style='width:3%'>"
                                                    + "<input type='hidden' id='Id" + obj_mc_definicion[0] + "' value='" + obj_mc_definicion[0] + "' />"
                                                    + "<input type='range' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_definicion[0] + "' value='" + valor + "' "
                                                    + "onchange=\"Calcular_calificacion(this.value," + obj_mc_grupos[2] + "," + (lst_mc_definiciones.size() * 5) + "," + obj_mc_grupos[0] + ",'" + detalle_grupo + "');"
                                                    + "Arreglo_calificacion('" + detalle_grupo_total + "')\">"
                                                    + "<i id='Dato_" + obj_mc_definicion[0] + "'>Valor : " + valor + "</i></td>");
                                        } else {
                                            for (int l = 1; l <= 5; l++) {
                                                if (l == Integer.parseInt(valor)) {
                                                    out.print("<td style='width:3%' align='center'><b class='negro'>X</b></td>");
                                                } else {
                                                    out.print("<td style='width:3%'></td>");
                                                }
                                            }
                                        }
                                        out.print("<td colspan='5' valign='top' " + (((Integer) obj_mc_grupos[0] == 7) ? "style='border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757;border-right:1px dashed #3AA757;width:30%'" : "style='width:30%'") + " id='Txt_observacion" + obj_mc_definicion[0] + "' " + ((Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) ? "contenteditable='true'" : "") + " onkeyup=\"Arreglo_calificacion('" + detalle_grupo_total + "')\" >" + ((observacion.length() > 0) ? observacion : "N/A") + "</td>");
                                        break;
                                    }
                                }
                            } else if ((Integer) obj_mc_grupos[0] == 7) {
                                out.print("<td colspan='5' style='width:3%;font-weight:bold;border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757'>"
                                        + "<input type='hidden' id='Id" + obj_mc_definicion[0] + "' value='" + obj_mc_definicion[0] + "' />"
                                        + "<input type='hidden' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_definicion[0] + "' value='0' />"
                                        + "<a href='Competencia?opc=12&mnu=28&Id_mc_calificacion=" + obj_mc_calificacion[0] + "&Txt_codigo_sst=" + obj_mc_cargo[14] + "' style='color:#3AA757;'><i>" + obj_mc_cargo[14] + "</i></a><br /><i>VALOR : 0</i></td>");
                                out.print("<td colspan='5' valign='top' style='border-top:1px dashed #3AA757;border-bottom:1px dashed #3AA757;border-right:1px dashed #3AA757;width:30%' id='Txt_observacion" + obj_mc_definicion[0] + "' contenteditable='true' onkeyup=\"Arreglo_calificacion('" + detalle_grupo_total + "')\" >N/A</td>");
                            } else {
                                out.print("<td colspan='5' style='width:3%'><input type='hidden' id='Id" + obj_mc_definicion[0] + "' value='" + obj_mc_definicion[0] + "' /><input type='range' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_definicion[0] + "' value='1' onchange=\"Calcular_calificacion(this.value," + obj_mc_grupos[2] + "," + (lst_mc_definiciones.size() * 5) + "," + obj_mc_grupos[0] + ",'" + detalle_grupo + "');Arreglo_calificacion('" + detalle_grupo_total + "')\"><i id='Dato_" + obj_mc_definicion[0] + "'></i></td>");
                                out.print("<td colspan='5' valign='top' style='width:30%' id='Txt_observacion" + obj_mc_definicion[0] + "' contenteditable='true' onkeyup=\"Arreglo_calificacion('" + detalle_grupo_total + "')\" >N/A</td>");
                            }
                            if (j == 0) {
                                out.print("</tr>");
                            }
                            out.print("</tr>");
                        }
                        out.print("<tr>");
                        out.print("<td colspan='20' style='background-color:#eee'></td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<td align='center'><b>100%</b></td>");
                    out.print("<td colspan='9' align='center' style='color: #2C3A47'><b>CALIFICACION ACUMULADA</b></td>");
                    out.print("<td colspan='10' style='color: #2C3A47' align='center'><b id='Calificacion_final' style='font-size:22px;color:#2C3A47'>" + obj_mc_calificacion[12] + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>5%</b></td>");
                    out.print("<td colspan='9' align='center' style='color: #2C3A47'><b>CALIFICACION FINAL</b></td>");
                    out.print("<td colspan='10' style='color: #2C3A47' align='center'><b id='Calificacion_final_A5' style='font-size:22px;color:#2C3A47'>" + obj_mc_calificacion[16] + "</b></td>");
                    out.print("</tr>");
//</editor-fold>
                    out.print("</table>");
                    out.print("<input type='hidden' id='Txt_evaluadores' name='Txt_evaluadores' value='" + obj_mc_calificacion[9] + "'/>");
                    out.print("<input type='hidden' id='Txt_recomendacion' name='Txt_recomendacion' value='" + obj_mc_calificacion[18] + "'/>");
                    out.print("<input type='hidden' id='Txt_arg_calificacion' name='Txt_arg_calificacion' value='" + obj_mc_calificacion[10] + "'/>");
                    out.print("<input type='hidden' id='Txt_calificacion' name='Txt_calificacion' value='" + obj_mc_calificacion[12] + "'/>");
                    out.print("<input type='hidden' id='Txt_calificacion_grupos' name='Txt_calificacion_grupos' value='" + obj_mc_calificacion[11] + "'/>");
                    out.print("</form>");
                    if (origen == 0) {
                        out.print("<br /><a href='Competencia?opc=5&mnu=28'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    } else {
                        out.print("<br /><a href='Competencia?opc=11&mnu=34&dcm=" + obj_mc_calificacion[1] + "'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    }
                    if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) {
                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;' id='Guardar2'><span class='fa fa-save fa-size_super_small' onclick=\"Arreglo_calificacion('" + detalle_grupo_total + "');javascript:FormCalificacionComp.submit()\"></span></div>");
                        }
                    }
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REPORTE PERSONAL CALIFICADO">
                else if (pageContext.getRequest().getAttribute("Competencias").equals("Personal_calificado")) {
                    id_area = Integer.parseInt(pageContext.getRequest().getAttribute("Id_area").toString());
                    documento = Long.parseLong(pageContext.getRequest().getAttribute("Documento").toString());
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="CONVENCIONES">
                    out.print("<div class='sweet-local' tabindex='-1' id='Convenciones' style='opacity: 1.03; display: none;'>");
                    out.print("<fieldset class='popup_local' style='width:30%;position: absolute;top: 15%;left:30%;'>");
                    out.print("<div style='float:right;'><span onclick=\"javascript:document.getElementById('Convenciones').style.display='none'\" class='fa fa-times fa-size_super_small'></span></div>");
                    out.print("<h3>Convenciones Personal calificado</h3>");
                    out.print("<table class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Descripción</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><span class='comp_graf " + semaforo_comp.split(",")[0] + "'></span></td>");
                    out.print("<td >La calificación se encuentra sobrepasada de la frecuencia anual</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><span class='comp_graf " + semaforo_comp.split(",")[1] + "'></span></td>");
                    out.print("<td>La calificación se encuentra proxima, debe realizar programación y ejecución.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><span class='comp_graf " + semaforo_comp.split(",")[3] + "'></span></td>");
                    out.print("<td>La calificación se encuentra vigente dentro de la frecuencia anual</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</fieldset>");
                    out.print("</div>");
//</editor-fold>
                    out.print("<h3>");
                    if (documento > 0) {
                        out.print("<a href='Competencia?opc=11&mnu=34&dcm=0&iar=" + id_area + "'><span class='fa fa-arrow-left fa-size_super_small'></span></a> ");
                    }
                    out.print(" Personal calificado por área<div style='float:right'>"
                            + "<i onclick=\"javascript:document.getElementById('Convenciones').style.display='block'\">Convenciones "
                            + "<span class='comp_graf uno'></span> "
                            + "<span class='comp_graf dos'></span> "
                            + "<span class='comp_graf cuatro'></span> </i>"
                            + "<input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /> "
                            + "</div></h3>");
                    if (documento > 0) {
                        lst_mc_calificaciones_realizadas = jpaccpt.Consultar_calificacion_realizada_documento_general(documento);
                        if (lst_mc_calificaciones_realizadas == null) {
                            out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                        } else {
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th>Documento</th>");
                            out.print("<th>Fecha</th>");
                            out.print("<th>Formato</th>");
                            out.print("<th>Personal</th>");
                            out.print("<th>Calificación y recomendaciones</th>");
                            out.print("<th>Opc.</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_mc_calificaciones_realizadas.size(); i++) {
                                Object[] obj_mc_calificaciones = (Object[]) lst_mc_calificaciones_realizadas.get(i);
                                int result = Integer.parseInt(obj_mc_calificaciones[17].toString());
                                out.print("<tr>");
                                out.print("<td align='center'><b class='tooltip'>" + obj_mc_calificaciones[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_mc_calificaciones[1] + ".jpg' style='width:200px;heigth:200px' /></span></b>");
                                //out.print("<div class='content_comp_graf' title='" + titulos_comp.split(",")[(result - 1)] + "'>");
                                out.print("<div class='content_comp_graf'>");
                                for (int j = 1; j < 5; j++) {
                                    if (j == result) {
                                        out.print("<span class='comp_graf " + semaforo_comp.split(",")[(result - 1)] + "'></span> ");
                                    } else if (j > result) {
                                        out.print("<span class='comp_graf cero'></span> ");
                                    } else {
                                        out.print("<span class='comp_graf vacio'></span> ");
                                    }
                                }
                                out.print("</div></td>");
                                out.print("<td align='center' style='width:8%'>" + obj_mc_calificaciones[8] + "</td>");
                                out.print("<td style='width:10%'>" + obj_mc_calificaciones[4] + " <b>V</b> " + obj_mc_calificaciones[5] + "</td>");
                                out.print("<td style='width:25%' valign='top'><b>Nombre :</b> " + obj_mc_calificaciones[2] + "<br /><b>Cargo :</b>" + obj_mc_calificaciones[6] + "<br /><b>Área :</b>" + obj_mc_calificaciones[7] + "</td>");
                                out.print("<td style='width:35%' valign='top'><b class='naranja'>Valor Calificación :</b>" + obj_mc_calificaciones[12] + "% | "
                                        + "<b class='negro'>" + obj_mc_calificaciones[16] + "</b><br />"
                                        + "" + titulos_comp.split(",")[(result - 1)]);
                                out.print("<hr /><b>Recomendaciones :</b>" + obj_mc_calificaciones[18]);
                                out.print("</td>");
                                out.print("<td align='center' style='width:8%'>");
                                out.print("<a href='Competencia?opc=6&mnu=28&imcclf=" + obj_mc_calificaciones[0] + "&act=0&org=1'><span class='fa fa-file-alt fa-size_small'></span></a>");
                                out.print("</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados', 10);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                        }
                    } else {
                        lst_areas = jpacara.Consultar_areas();
                        if (id_area_s == 7) {
                            for (int i = 0; i < lst_areas.size(); i++) {
                                Object[] obj_areas = (Object[]) lst_areas.get(i);
                                if (Integer.parseInt(obj_areas[5].toString()) == 1) {
                                    if (id_area == Integer.parseInt(obj_areas[0].toString())) {
                                        out.print("<a href='Competencia?opc=11&mnu=34&iar=" + obj_areas[0] + "' title='" + obj_areas[1] + "'><i><b>" + obj_areas[2] + "</b></i></a> | ");
                                    } else {
                                        out.print("<a href='Competencia?opc=11&mnu=34&iar=" + obj_areas[0] + "' title='" + obj_areas[1] + "'><i>" + obj_areas[2] + "</i></a> | ");
                                    }
                                }
                            }
                        } else {
                            id_area = id_area_s;
                        }
                        lst_personal_calificado = jpacmnu.Informe_personal_calificado(id_area);
                        if (id_area > 0 && lst_personal_calificado != null) {
                            out.print("<br /><hr />");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%'>Documento</th>");
                            out.print("<th>Nombre</th>");
                            out.print("<th>Área / Cargo</th>");
                            out.print("<th>Formato vigente al cargo</th>");
                            out.print("<th>Ult. Calificación</th>");
//                        out.print("<th>Alert. Calificación</th>");
                            out.print("<th>Prox. Calificación</th>");
                            out.print("<th>Calificación</th>");
                            out.print("<th>Ver</th>");
                            out.print("</tr>");
                            for (int j = 0; j < lst_personal_calificado.size(); j++) {
                                Object[] obj_personal_calificado = (Object[]) lst_personal_calificado.get(j);
                                out.print("<tr>");
                                out.print("<td align='center' style='color:" + obj_personal_calificado[18] + "'><span class='fa fa-circle fa-size_super_small' ></span><br /><b class='tooltip'>" + obj_personal_calificado[0] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_personal_calificado[0] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                                out.print("<td>" + obj_personal_calificado[2] + "<br />" + obj_personal_calificado[1] + "</td>");
                                out.print("<td>" + obj_personal_calificado[5] + "<br />" + obj_personal_calificado[7] + "</td>");
                                if (obj_personal_calificado[14] == null) {
                                    out.print("<td align='center' colspan='5' style=\"background-color:#ffedd3\">Calificación no encontrada , consultar en el AIRH la ultima calificación.</td>");
                                } else {
                                    out.print("<td>" + obj_personal_calificado[9] + " V " + obj_personal_calificado[10] + "<br />" + obj_personal_calificado[11] + " cada " + obj_personal_calificado[12] + " días</td>");
                                    out.print("<td align='center' ><b class='verde'>" + obj_personal_calificado[15] + "</b></td>");
                                    //out.print("<td align='center' style=\"background-color:#FFFACD\"><b class='naranja'>" + obj_personal_calificado[17] + "</b></td>");
                                    out.print("<td align='center' ><b class='" + ((obj_personal_calificado[18].toString().equals("#F6921E")) ? "naranja" : "rojo") + "'>" + obj_personal_calificado[16] + "</b></td>");
                                    out.print("<td align='center'>" + obj_personal_calificado[13] + "</td>");
                                    out.print("<td align='center'><a href='Competencia?opc=11&mnu=34&iar=" + id_area + "&dcm=" + obj_personal_calificado[0] + "'><span class='fa fa-file-alt fa-size_small'></span></a></td>");
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
                        } else {
                            out.print("<br /><br /><center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se encuentran resultados</center>");
                        }
                    }
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="RENDICION DE CUENTAS">
                else if (pageContext.getRequest().getAttribute("Competencias").equals("Calificar_rendicion_cargo")) {
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="PARAMETROS INICIO">
                    id_mc_calificacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_mc_calificacion").toString());
                    lst_mc_calificacion = jpaccpt.Consultar_calificacion_realizada_id(id_mc_calificacion);
                    Object[] obj_mc_calificacion = (Object[]) lst_mc_calificacion.get(0);
                    codigo = pageContext.getRequest().getAttribute("Codigo_sst").toString();
                    usuario_registro = pageContext.getRequest().getAttribute("Usuario_registro").toString();
                    lst_mc_sst_calificacion = jpaccpt.Consultar_sst_rendicion_codigo(id_mc_calificacion);
                    if (lst_mc_sst_calificacion == null) {
                        lst_mc_sst_rendicion = jpaccpt.Consultar_sst_rendicion_codigo(codigo);
                        Object[] obj_mc_sst_rendicion = (Object[]) lst_mc_sst_rendicion.get(0);
                        jpaccpt.Registrar_mc_sst_calificacion(id_mc_calificacion, (Integer) obj_mc_sst_rendicion[0], usuario_registro);
                        lst_mc_sst_calificacion = jpaccpt.Consultar_sst_rendicion_codigo(id_mc_calificacion);
                    }
                    Object[] obj_mc_sst_calificacion = (Object[]) lst_mc_sst_calificacion.get(0);
                    lst_mc_sst_rendicion = jpaccpt.Consultar_sst_rendicion_id((Integer) obj_mc_sst_calificacion[2]);
                    Object[] obj_mc_sst_rendicion = (Object[]) lst_mc_sst_rendicion.get(0);
                    lst_mc_grupos = jpaccpt.Consultar_sst_grupos_definicion_id_rendicion((Integer) obj_mc_sst_calificacion[2]);
                    ///TRAER DATOS FORMATO
                    String detalle_calificacion = obj_mc_sst_calificacion[3].toString();
                    String[] arg_detalle_calificacion = detalle_calificacion.split("]");
                    String detalle_grupo_total = "";
                    for (int i = 0; i < lst_mc_grupos.size(); i++) {
                        Object[] obj_mc_grupos = (Object[]) lst_mc_grupos.get(i);
                        lst_mc_sst_definicion = jpaccpt.Consultar_sst_rendicion_definiciones_id((Integer) obj_mc_sst_calificacion[2], (Integer) obj_mc_grupos[0]);
                        for (int j = 0; j < lst_mc_sst_definicion.size(); j++) {
                            Object[] obj_mc_sst_definicion = (Object[]) lst_mc_sst_definicion.get(j);
                            if (detalle_grupo_total.length() == 0) {
                                detalle_grupo_total = obj_mc_sst_definicion[0] + "";
                            } else {
                                detalle_grupo_total = detalle_grupo_total + "-" + obj_mc_sst_definicion[0];
                            }
                        }
                    }
                    //</editor-fold>
                    out.print("<br /><a href='Competencia?opc=6&mnu=28&imcclf=" + id_mc_calificacion + "&act=1'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) {
                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right' id='Guardar1'><span class='fa fa-save fa-size_super_small' onclick=\"Arreglo_calificacion_sst('" + detalle_grupo_total + "');javascript:FormCalificacionCompSST.submit()\"></span></div>");
                            //out.print("<div style='float:right' id='Guardar1'><span class='fa fa-save fa-size_super_small' onclick=\"Arreglo_calificacion('" + detalle_grupo_total + "');\"></span></div>");
                        }
                    }
                    out.print("<table class='table'>");
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<tr>");
                    out.print("<td colspan='20' style='background-color:#ccc;border-radius:20px' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center' colspan='5' style='width:20%' >"
                            + "<img src='Interfaz/MasterPage/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                            + "</td>");
                    out.print("<td colspan='10' style='width:60%' align='center'><b class='negro'>REGISTRO<hr />" + obj_mc_sst_rendicion[3] + "</b></td>");
                    //out.print("<td colspan='3'>CODIGO R-RH-009<hr />VERSION 4</td>");
                    out.print("<td colspan='5' style='width:50%' align='center'>CODIGO " + obj_mc_sst_rendicion[1] + "<hr />VERSIÓN " + obj_mc_sst_rendicion[2] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='20' style='padding:10px 10px 10px 10px'><b>NOTA:</b> Evalúe al personal acorde con el desempeño presentado frente al Sistema de Gestión de Seguridad y Salud en el Trabajo en el periodo a evaluar.</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th colspan='20'>ELEMENTOS A EVALUAR</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td rowspan='2' colspan='8' style='width:50%' align='center'><b>GRUPO</b></td>");
                    out.print("<td colspan='5' align='center'><b>CALIFICACION</b></td>");
                    out.print("<td rowspan='2' colspan='7' style='width:40%' align='center'><b>OBSERVACIONES</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:2%' align='center'><b>1</b></td>");
                    out.print("<td style='width:2%' align='center'><b>2</b></td>");
                    out.print("<td style='width:2%' align='center'><b>3</b></td>");
                    out.print("<td style='width:2%' align='center'><b>4</b></td>");
                    out.print("<td style='width:2%' align='center'><b>5</b></td>");
                    out.print("</tr>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE">
                    for (int i = 0; i < lst_mc_grupos.size(); i++) {
                        Object[] obj_mc_grupos = (Object[]) lst_mc_grupos.get(i);
                        String total_grupo = "";
                        if (obj_mc_sst_calificacion[4].toString().contains("[" + obj_mc_grupos[1] + "/")) {
                            String[] arg_total_grupo = obj_mc_sst_calificacion[4].toString().split("]");
                            for (int j = 0; j < arg_total_grupo.length; j++) {
                                if (arg_total_grupo[j].contains("[" + obj_mc_grupos[1] + "/")) {
                                    total_grupo = arg_total_grupo[j].split("/")[1];
                                }
                            }
                        }
                        out.print("<tr>");
                        out.print("<td colspan='20' style='background-color:#eee'><b>" + obj_mc_grupos[1] + "</b><div style='float:right'><b>" + obj_mc_grupos[2] + " %</b><br /><b id='Result_" + obj_mc_grupos[0] + "' style='color:orange'>" + total_grupo + "</b></td>");
                        out.print("</tr>");
                        lst_mc_sst_definicion = jpaccpt.Consultar_sst_rendicion_definiciones_id((Integer) obj_mc_sst_calificacion[2], (Integer) obj_mc_grupos[0]);
                        String detalle_grupo = "";
                        for (int j = 0; j < lst_mc_sst_definicion.size(); j++) {
                            Object[] obj_mc_sst_definicion = (Object[]) lst_mc_sst_definicion.get(j);
                            if (j == 0) {
                                detalle_grupo = obj_mc_sst_definicion[0] + "";
                            } else {
                                detalle_grupo = detalle_grupo + "-" + obj_mc_sst_definicion[0];
                            }
                        }
                        for (int j = 0; j < lst_mc_sst_definicion.size(); j++) {
                            Object[] obj_mc_sst_definicion = (Object[]) lst_mc_sst_definicion.get(j);
                            out.print("<tr>");
                            out.print("<td colspan='8'>" + obj_mc_sst_definicion[4] + "</td>");
                            if (detalle_calificacion.contains("[" + obj_mc_sst_definicion[0] + "°")) {
                                for (int k = 0; k < arg_detalle_calificacion.length; k++) {
                                    if (arg_detalle_calificacion[k].contains("[" + obj_mc_sst_definicion[0] + "°")) {
                                        String valor = arg_detalle_calificacion[k].split("°")[1];
                                        String observacion = arg_detalle_calificacion[k].split("°")[2];
                                        if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) {
                                            out.print("<td colspan='5' style='width:3%'>"
                                                    + "<input type='hidden' id='Id" + obj_mc_sst_definicion[0] + "' value='" + obj_mc_sst_definicion[0] + "' />"
                                                    + "<input type='range' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_sst_definicion[0] + "' value='" + valor + "' "
                                                    + "onchange=\"Calcular_calificacion_sst(this.value," + obj_mc_grupos[2] + "," + (lst_mc_sst_definicion.size() * 5) + "," + obj_mc_grupos[0] + ",'" + detalle_grupo + "');"
                                                    + "Arreglo_calificacion_sst('" + detalle_grupo_total + "')\">"
                                                    + "<i id='Dato_" + obj_mc_sst_definicion[0] + "'>Valor : " + valor + "</i></td>");
                                        } else {
                                            for (int l = 1; l <= 5; l++) {
                                                if (l == Integer.parseInt(valor)) {
                                                    out.print("<td style='width:3%' align='center'><b class='negro'>X</b></td>");
                                                } else {
                                                    out.print("<td style='width:3%'></td>");
                                                }
                                            }
                                        }
                                        out.print("<td colspan='5' valign='top' id='Txt_observacion" + obj_mc_sst_definicion[0] + "' " + ((Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) ? "contenteditable='true'" : "") + " onkeyup=\"Arreglo_calificacion('" + detalle_grupo_total + "')\" >" + ((observacion.length() > 0) ? observacion : "N/A") + "</td>");
                                        break;
                                    }
                                }
                            } else {
                                out.print("<td colspan='5' style='width:3%'>"
                                        + "<input type='hidden' id='Id" + obj_mc_sst_definicion[0] + "' value='" + obj_mc_sst_definicion[0] + "' />"
                                        + "<input type='range' style='width:98%' min='1' max='5' id='Rdb_definicion" + obj_mc_sst_definicion[0] + "' value='1' "
                                        + "onchange=\"Calcular_calificacion_sst(this.value," + obj_mc_grupos[2] + "," + (lst_mc_sst_definicion.size() * 5) + "," + obj_mc_grupos[0] + ",'" + detalle_grupo + "');"
                                        + "Arreglo_calificacion_sst('" + detalle_grupo_total + "')\">"
                                        + "<i id='Dato_" + obj_mc_sst_definicion[0] + "'></i></td>");
                                out.print("<td colspan='7' valign='top' style='width:30%' id='Txt_observacion" + obj_mc_sst_definicion[0] + "' contenteditable='true' onkeyup=\"Arreglo_calificacion_sst('" + detalle_grupo_total + "')\" >N/A</td>");
                            }
                            out.print("</tr>");
                        }
                    }
//</editor-fold>
                    out.print("<tr>");
                    out.print("<td align='center'><b>100%</b></td>");
                    out.print("<td colspan='9' align='center' style='color: #2C3A47'><b>CALIFICACION ACUMULADA</b></td>");
                    out.print("<td colspan='10' style='color: #2C3A47' align='center'><b id='Calificacion_final' style='font-size:22px;color:#2C3A47'>" + obj_mc_sst_calificacion[5] + "</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><b>5%</b></td>");
                    out.print("<td colspan='9' align='center' style='color: #2C3A47'><b>CALIFICACION FINAL</b></td>");
                    out.print("<td colspan='10' style='color: #2C3A47' align='center'><b id='Calificacion_final_A5' style='font-size:22px;color:#2C3A47'>" + obj_mc_sst_calificacion[9] + "</b></td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<form action='Competencia?opc=13' method='post' id='FormCalificacionCompSST'>");
                    out.print("<input type='hidden' id='Id_mc_calificacion' name='Id_mc_calificacion' value='" + id_mc_calificacion + "'/>");
                    out.print("<input type='hidden' id='Txt_codigo_sst' name='Txt_codigo_sst' value='" + codigo + "'/>");
                    out.print("<input type='hidden' id='Txt_arg_calificacion_sst' name='Txt_arg_calificacion_sst' value='" + obj_mc_sst_calificacion[3] + "'/>");
                    out.print("<input type='hidden' id='Txt_calificacion_sst_grupos' name='Txt_calificacion_sst_grupos' value='" + obj_mc_sst_calificacion[4] + "'/>");
                    out.print("<input type='hidden' id='Txt_calificacion_sst' name='Txt_calificacion_sst' value='" + obj_mc_sst_calificacion[5] + "'/>");
                    out.print("</form>");
                    out.print("<br /><a href='Competencia?opc=6&mnu=28&imcclf=" + id_mc_calificacion + "&act=1'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    if (Integer.parseInt(obj_mc_calificacion[13].toString()) == 1) {
                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right' id='Guardar1'><span class='fa fa-save fa-size_super_small' onclick=\"Arreglo_calificacion_sst('" + detalle_grupo_total + "');javascript:FormCalificacionCompSST.submit()\"></span></div>");
                            //out.print("<div style='float:right' id='Guardar1'><span class='fa fa-save fa-size_super_small' onclick=\"Arreglo_calificacion('" + detalle_grupo_total + "');\"></span></div>");
                        }
                    }
                    out.print("</div>");
                }
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_competencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
