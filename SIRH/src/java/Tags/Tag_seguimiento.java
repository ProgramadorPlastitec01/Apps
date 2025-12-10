package Tags;

import Controladores_BD.AccidenteJpaController;
import Controladores_BD.AreaJpaController;
import Controladores_BD.AusenciaJpaController;
import Controladores_BD.CapacitacionJpaController;
import Controladores_BD.CargoJpaController;
import Controladores_BD.CategoriaJpaController;
import Controladores_BD.DisciplinaJpaController;
import Controladores_BD.DotacionJpaController;
import Controladores_BD.EnfermedadJpaController;
import Controladores_BD.EppJpaController;
import Controladores_BD.ExamenJpaController;
import Controladores_BD.IncapacidadJpaController;
import Controladores_BD.MenuJpaController;
import Controladores_BD.PersonalJpaController;
import Controladores_BD.RetiroJpaController;
import Factory.DotacionMANT;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.time.LocalDate;
import Metodos.ConnectionSignature;
import java.util.HashMap;
import Controladores_BD.ParametrosJpa;
import java.util.ArrayList;
import java.util.Map;

public class Tag_seguimiento extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            ///JPAS
            AreaJpaController jpacara = new AreaJpaController();
            CargoJpaController jpaccgo = new CargoJpaController();
            MenuJpaController jpacmnu = new MenuJpaController();
            PersonalJpaController jpacpsn = new PersonalJpaController();
            AccidenteJpaController jpacacd = new AccidenteJpaController();
            AusenciaJpaController jpacasc = new AusenciaJpaController();
            IncapacidadJpaController jpacicp = new IncapacidadJpaController();
            EnfermedadJpaController jpacefm = new EnfermedadJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            DisciplinaJpaController jpacdcp = new DisciplinaJpaController();
            DotacionJpaController jpacdtc = new DotacionJpaController();
            CapacitacionJpaController jpaccpc = new CapacitacionJpaController();
            DotacionMANT mtddtm = new DotacionMANT();
            ExamenJpaController jpacexm = new ExamenJpaController();
            EppJpaController jpacepp = new EppJpaController();
            RetiroJpaController jpacrtr = new RetiroJpaController();
            ConnectionSignature firmasJpa = new ConnectionSignature();

            ParametrosJpa ParametrosJpa = new ParametrosJpa();

            LocalDate DateLocal = LocalDate.now();
            ///VARIABLES
            String fechaps_incio = pageContext.getSession().getAttribute("FechaPS_inicio") + "";
            String fechaps_fin = pageContext.getSession().getAttribute("FechaPS_fin") + "";
            int formulario = 0;
            int categoria_modulo = 0;
            int id_capacitacion = 0;
            int id_accidente = 0;
            int id_enfermedad = 0;
            int id_incapacidad = 0;
            int id_ausencia = 0;
            int id_disciplina = 0;
            int id_retiro = 0;
            int id_dotacion = 0;
            int id_examen = 0;
            int id_epp = 0;
            int id_area = 0;
            int id_cargo = 0;
            int tipo_consulta = 0;
            int dia_inicial = 0;
            int dia_final = 0;
            List lst_categorias = null;
            List lst_areas = null;
            List lst_cargos = null;
            List lst_capacitaciones = null;
            List lst_capacitacion = null;
            List lst_persona = null;
            List lst_personal = null;
            List lst_accidentes = null;
            List lst_accidente = null;
            List lst_enfermedades = null;
            List lst_enfermedad = null;
            List lst_disciplina = null;
            List lst_incapacidades = null;
            List lst_incapacidad = null;
            List lst_ausencias = null;
            List lst_ausencia = null;
            List lst_dotaciones = null;
            List lst_dotacion = null;
            List lst_epps = null;
            List lst_epp = null;
            List lst_marcaciones = null;
            List lst_marc_calculados = null;
            List lst_inv_dotaciones = null;
            List lst_examenes = null;
            List lst_retiro = null;
            List lst_retiros = null;
            List lst_examen = null;
            List lst_parametros = null;
            List lst_firma = null;
            String consulta = "";
            String documento = "";
            List lst_opciones_permisos = null;
            String permisos = "";
            String fecha_ajuste = "";
            String all_ent = "", all_sal = "";
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            int id_area_s = Integer.parseInt(pageContext.getSession().getAttribute("Id_areaS").toString());
            int consulta_personal_s = Integer.parseInt(pageContext.getSession().getAttribute("Consulta_personalS").toString());
            int dias_trabajados_seg = 0;
            int modulo = 0;
            float horas_trabajo = 0;
            float total_horas_extra = 0;
            float horas_extra_diurnas = 0;
            float horas_extra_nocturnas = 0;
            float horas_recargo_nocturno = 0;
            float horas_extra_dominical = 0;
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int id_opcion_menu = 0;
            if (pageContext.getRequest().getAttribute("Seguimiento") != null) {
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
                if (pageContext.getRequest().getAttribute("Seguimiento").equals("Accidentes")) {
                    //<editor-fold defaultstate="collapsed" desc="ACCIDENTES">
                    categoria_modulo = 1;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_accidente = Integer.parseInt(pageContext.getRequest().getAttribute("Id_accidente").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_accidente == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=1&mnu=14&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nuevo Accidente</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=2'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required /><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de accidente:<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha de accidente' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br />Parte afectada :<br /><input type='text' id='Txt_parte_afectada' name='Txt_parte_afectada' style='width:80%' placeholder='Descripción de la parte afectada' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parte_afectada');val1.add(Validate.Presence);</script>");
                        out.print("<br />Agente de la lesión<br /><input type='text' id='Txt_agente' name='Txt_agente' style='width:80%' placeholder='Descripción del agente de la lesión' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_agente');val1.add(Validate.Presence);</script>");
                        out.print("<br />Genero incapacidad :<br /><input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='1' checked onclick='Habilitar_incapacidad(this.value)' /> SI "
                                + "<input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='0' onclick='Habilitar_incapacidad(this.value)' /> NO");
                        out.print("&nbsp;&nbsp;&nbsp;Dias : <input type='number' name='Txt_incapacidad' id='Txt_incapacidad' min='0' max='30' style='width:50px' placeholder='#' required />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'></textarea>");
                        out.print("<input type='submit' value='Guardar Accidente' />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    } else if (formulario == 2 && id_accidente > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_accidente = jpacacd.Consultar_accidente_id(id_accidente);
                        Object[] obj_accidente = (Object[]) lst_accidente.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_accidente[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_accidente[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=1&mnu=14&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Accidente</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=2&iac=" + id_accidente + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_accidente[8] + "' /><hr />");
                        out.print("Fecha de accidente :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha de accidente' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_accidente[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                if (obj_categoria[1].toString().equals(obj_accidente[3])) {
                                    out.print("<option value='" + obj_categoria[1] + "' selected>" + obj_categoria[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br />Parte afectada :<br /><input type='text' id='Txt_parte_afectada' name='Txt_parte_afectada' style='width:80%' placeholder='Descripción de la parte afectada' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_accidente[5] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_parte_afectada');val1.add(Validate.Presence);</script>");
                        out.print("<br />Agente de la lesión<br /><input type='text' id='Txt_agente' name='Txt_agente' style='width:80%' placeholder='Descripción del agente de la lesión' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_accidente[6] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_agente');val1.add(Validate.Presence);</script>");
                        out.print("<br />Genero incapacidad :<br /><input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='1' " + (((Integer.parseInt(obj_accidente[4].toString()) > 0) ? "checked" : "")) + " onclick='Habilitar_incapacidad(this.value)' /> SI "
                                + "<input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='0' " + (((Integer.parseInt(obj_accidente[4].toString()) > 0) ? "" : "checked")) + " onclick='Habilitar_incapacidad(this.value)' /> NO");
                        out.print("&nbsp;&nbsp;&nbsp;Dias : <input type='number' name='Txt_incapacidad' id='Txt_incapacidad' min='0' max='30' style='width:50px' placeholder='#' value='" + obj_accidente[4] + "' required />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>" + obj_accidente[7] + "</textarea>");
                        out.print("<input type='submit' value='Modificar Accidente' />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA ACCIDENTES">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=1&mnu=14&fml=1'><span class='fa fa-user-injured fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Accidentes<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_accidentes = jpacacd.Consultar_accidentes(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_accidentes == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'ACCIDENTES_TRABAJO')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Parte afectada</th>");
                        out.print("<th>Agente de lesión</th>");
                        out.print("<th>Incapacidad</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_accidentes.size(); i++) {
                            Object[] obj_accidentes = (Object[]) lst_accidentes.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_accidentes[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_accidentes[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_accidentes[2] + "</td>");
                            out.print("<td>" + obj_accidentes[3] + "</td>");
                            out.print("<td>" + obj_accidentes[5] + "</td>");
                            out.print("<td>" + obj_accidentes[6] + "</td>");
                            out.print("<td align='center'>" + obj_accidentes[4] + "</td>");
                            out.print("<td>" + obj_accidentes[7] + "</td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_accidentes[9].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarAccidente(" + obj_accidentes[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=1&mnu=14&fml=2&iac=" + obj_accidentes[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarAccidente(" + obj_accidentes[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarAccidente(" + obj_accidentes[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Enfermedades")) {
                    //<editor-fold defaultstate="collapsed" desc="ENFERMEDADES">
                    categoria_modulo = 2;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_enfermedad = Integer.parseInt(pageContext.getRequest().getAttribute("Id_enfermedad").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_enfermedad == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=4&mnu=15&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nuevo Enfermedad Profesional</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=5'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de enfermedad profesional:<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha de enfermedad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br />Diagnostico ARL:<br /><textarea name='Txt_diagnostico' id='Txt_diagnostico' style='width: 100%; height: 100px' placeholder='Diagnostico de ARL' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_diagnostico');val1.add(Validate.Presence);</script>");
                        out.print("<br />Genero incapacidad :<br /><input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='1' checked onclick='Habilitar_incapacidad(this.value)' /> SI "
                                + "<input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='0' onclick='Habilitar_incapacidad(this.value)' /> NO");
                        out.print("&nbsp;&nbsp;&nbsp;Dias : <input type='number' name='Txt_incapacidad' id='Txt_incapacidad' min='0' max='30' style='width:50px' placeholder='#' value='0' required /><br />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'></textarea>");
                        out.print("<input type='submit' value='Guardar Enfermedad' />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    } else if (formulario == 2 && id_enfermedad > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_enfermedad = jpacefm.Consultar_enfermedad_id(id_enfermedad);
                        Object[] obj_enfermedad = (Object[]) lst_enfermedad.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_enfermedad[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_enfermedad[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=4&mnu=15&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Enfermedad Profesional</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=5&ief=" + id_enfermedad + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_enfermedad[7] + "' /><hr />");
                        out.print("Fecha de enfermedad profesional:<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha de enfermedad' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_enfermedad[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                if (obj_categoria[1].toString().equals(obj_enfermedad[3].toString())) {
                                    out.print("<option value='" + obj_categoria[1] + "' selected>" + obj_categoria[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br />Diagnostico ARL:<br /><textarea name='Txt_diagnostico' id='Txt_diagnostico' style='width: 100%; height: 100px' placeholder='Diagnostico de ARL' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_enfermedad[5] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_diagnostico');val1.add(Validate.Presence);</script>");
                        out.print("<br />Genero incapacidad :<br /><input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='1' checked onclick='Habilitar_incapacidad(this.value)' id='Rdb_incapacidad' value='1' " + (((Integer.parseInt(obj_enfermedad[4].toString()) > 0) ? "checked" : "")) + " /> SI "
                                + "<input type='radio' name='Rdb_incapacidad' id='Rdb_incapacidad' value='0' onclick='Habilitar_incapacidad(this.value)' id='Rdb_incapacidad' value='1' " + (((Integer.parseInt(obj_enfermedad[4].toString()) > 0) ? "" : "checked")) + " /> NO");
                        out.print("&nbsp;&nbsp;&nbsp;Dias : <input type='number' name='Txt_incapacidad' id='Txt_incapacidad' min='0' max='30' style='width:50px' placeholder='#' value='0' required /><br />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>" + obj_enfermedad[6] + "</textarea>");
                        out.print("<input type='submit' value='Modificar Enfermedad' />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=4&mnu=15&fml=1'><span class='fa fa-allergies fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Enfermedades<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_enfermedades = jpacefm.Consultar_enfermedades(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_enfermedades == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'ENFERMEDAD_PROFESIONAL')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Diagnostico ARL</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_enfermedades.size(); i++) {
                            Object[] obj_enfermedades = (Object[]) lst_enfermedades.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_enfermedades[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_enfermedades[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_enfermedades[2] + "</td>");
                            out.print("<td>" + obj_enfermedades[3] + "</td>");
                            out.print("<td valign='top' style='width:35%'><b>Incapacidad</b>:" + obj_enfermedades[4] + "<br />" + obj_enfermedades[5] + "</td>");
                            out.print("<td valign='top' style='width:35%'>" + obj_enfermedades[6] + "</td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_enfermedades[8].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarEnfermedad(" + obj_enfermedades[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=4&mnu=15&fml=2&ief=" + obj_enfermedades[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarEnfermedad(" + obj_enfermedades[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarEnfermedad(" + obj_enfermedades[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Incapacidades")) {
                    //<editor-fold defaultstate="collapsed" desc="INCAPACIDADES">
                    categoria_modulo = 4;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_incapacidad = Integer.parseInt(pageContext.getRequest().getAttribute("Id_incapacidad").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_incapacidad == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=7&mnu=16&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nueva Incapacidad</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=8'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b><br />"
                                // + "Salario Hora : <b id='Label_salario' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Clasificar como :<br /><input type='radio' name='Rdb_clasificacion' id='Rdb_clasificacion' value='Nueva' checked /> Nueva "
                                + "<input type='radio' name='Rdb_clasificacion' id='Rdb_clasificacion' value='Prórroga' /> Prórroga<br /><br />");
                        out.print("Fecha de incapacidad :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha incapacidad' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                            }
                        }
                        out.print("</select><br />");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br />Dia(s) : <input type='number' id='Txt_hora' name='Txt_hora' min='0' required onkeyup='Costo_empleado_dia_horas()' style='width:25%' placeholder='Dias'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora');val1.add(Validate.Presence);</script>");
                        // out.print("&nbsp;&nbsp;&nbsp;Valor : <b id='Label_costo_empleado' ></b><br />");
                        out.print("<br /><input type='submit' value='Guardar Incapacidad' />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:500px' >Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'></textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_incapacidad > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_incapacidad = jpacicp.Consultar_incapacidad_id(id_incapacidad);
                        Object[] obj_incapacidad = (Object[]) lst_incapacidad.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_incapacidad[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_incapacidad[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=7&mnu=16&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Incapacidad</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=8&iic=" + id_incapacidad + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_incapacidad[6] + "' /><hr />");
                        out.print("Clasificar como :<br /><input type='radio' name='Rdb_clasificacion' id='Rdb_clasificacion' value='Nueva' " + (((obj_incapacidad[10].toString().equals("Nueva")) ? "checked" : "")) + " /> Nueva "
                                + "<input type='radio' name='Rdb_clasificacion' id='Rdb_clasificacion' value='Prórroga' " + (((obj_incapacidad[10].toString().equals("Nueva")) ? "" : "checked")) + " /> Prórroga<br />");
                        out.print("<br />Fecha de incapacidad:<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha incapacidad' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_incapacidad[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                if (obj_categoria[1].equals(obj_incapacidad[3])) {
                                    out.print("<option value='" + obj_categoria[1] + "' selected>" + obj_categoria[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br />Dia(s) : <input type='number' id='Txt_hora' name='Txt_hora' min='0' required onkeyup='Costo_empleado_dia_horas()' style='width:25%' placeholder='# Dias' value='" + (Integer.parseInt(obj_incapacidad[4].toString()) / 8) + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora');val1.add(Validate.Presence);</script>");
//                        out.print("&nbsp;&nbsp;&nbsp;Valor : <b id='Label_costo_empleado'>" + (Double.parseDouble(obj_incapacidad[6].toString()) * Integer.parseInt(obj_incapacidad[4].toString())) + "</b><br />");
                        out.print("<br /><input type='submit' value='Modificar Incapacidad' />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:500px' >Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>" + obj_incapacidad[5] + "</textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=7&mnu=16&fml=1'><span class='fa fa-user-md fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Incapacidades<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_incapacidades = jpacicp.Consultar_incapacidades(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_incapacidades == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'INCAPACIDADES')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Clasificación</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Observacion</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_incapacidades.size(); i++) {
                            Object[] obj_incapacidades = (Object[]) lst_incapacidades.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_incapacidades[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_incapacidades[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_incapacidades[2] + "</td>");
                            out.print("<td>" + obj_incapacidades[10] + "</td>");
                            out.print("<td>" + obj_incapacidades[3] + "</td>");
                            out.print("<td valign='top' style='width:60%'><b>Dia(s)</b> :" + (Integer.parseInt(obj_incapacidades[4].toString()) / 8) + "<br />" + obj_incapacidades[5] + "</td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_incapacidades[7].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarIncapacidad(" + obj_incapacidades[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=7&mnu=16&fml=2&iic=" + obj_incapacidades[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarIncapacidad(" + obj_incapacidades[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarIncapacidad(" + obj_incapacidades[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Ausencias")) {
                    //<editor-fold defaultstate="collapsed" desc="AUSENCIAS">
                    categoria_modulo = 3;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_ausencia = Integer.parseInt(pageContext.getRequest().getAttribute("Id_ausencia").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_ausencia == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=10&mnu=17&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nueva Ausencia</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' onchange='Empleado_seleccionado(this.value);' list='Personal' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal' >");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=11'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b><br />"
                                //                                + "Salario Hora : <b id='Label_salario' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de ausencia :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha Ausencia' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br />Horas : <input type='number' id='Txt_hora' name='Txt_hora' required min='0' onkeyup='Costo_empleado_horas_minutos()' style='width:25%' placeholder='Horas'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora');val1.add(Validate.Presence);</script>");
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;Minutos : <input type='number' style='width:25%' id='Txt_minutos' required name='Txt_minutos' onkeyup='Costo_empleado_horas_minutos()' placeholder='Minutos'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_minutos');val1.add(Validate.Presence);</script>");
//                        out.print("<br />Valor : <b id='Label_costo_empleado' ></b><br />");
                        out.print("<input type='submit' value='Guardar Ausencia' />");
//                        out.print("&nbsp;&nbsp;&nbsp;Valor : <b id='Label_costo_empleado' ></b><br />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' />");
                        out.print("<br />Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'></textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_ausencia > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_ausencia = jpacasc.Consultar_ausencia_id(id_ausencia);
                        Object[] obj_ausencia = (Object[]) lst_ausencia.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_ausencia[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_ausencia[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=10&mnu=17&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Ausencia</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=11&ias=" + id_ausencia + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_ausencia[6] + "' /><hr />");
                        out.print("Fecha de ausencia :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha Ausencia' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_ausencia[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                if (obj_categoria[1].equals(obj_ausencia[3].toString())) {
                                    out.print("<option value='" + obj_categoria[1] + "' selected>" + obj_categoria[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        //int horas = (Integer)obj_ausencia[4];
                        //double min = (Double.parseDouble(obj_ausencia[4].toString()) - horas);
                        out.print("<br /><br />Horas : <input type='number' id='Txt_hora' name='Txt_hora' required min='0' onkeyup='Costo_empleado_horas_minutos()' style='width:25%' placeholder='Horas' value='" + obj_ausencia[4] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hora');val1.add(Validate.Presence);</script>");
                        //out.print("&nbsp;&nbsp;&nbsp;&nbsp;Minutos : <input type='number' style='width:25%' id='Txt_minutos' required name='Txt_minutos' onkeyup='Costo_empleado_horas_minutos()' placeholder='Minutos' value='" + (min * 60) + "'/>"
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;Minutos : <input type='number' style='width:25%' id='Txt_minutos' required name='Txt_minutos' onkeyup='Costo_empleado_horas_minutos()' placeholder='Minutos' value='0'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_minutos');val1.add(Validate.Presence);</script>");
//                        out.print("<br />Valor : <b id='Label_costo_empleado' >" + (Double.parseDouble(obj_ausencia[6].toString()) * Double.parseDouble(obj_ausencia[4].toString())) + "</b><br />");
                        out.print("<input type='submit' value='Modificar Ausencia' />");
//                        out.print("&nbsp;&nbsp;&nbsp;Valor : <b id='Label_costo_empleado' ></b><br />");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' />");
                        out.print("<br />Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>" + obj_ausencia[5] + "</textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=10&mnu=17&fml=1'><span class='fa fa-running fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Ausencias<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_ausencias = jpacasc.Consultar_ausencias(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_ausencias == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'AUSENCIAS')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Observacion</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_ausencias.size(); i++) {
                            Object[] obj_ausencias = (Object[]) lst_ausencias.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_ausencias[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_ausencias[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_ausencias[2] + "</td>");
                            out.print("<td>" + obj_ausencias[3] + "</td>");
                            out.print("<td valign='top' style='width:60%'><b>Horas</b> :" + obj_ausencias[4] + "<br />" + obj_ausencias[5] + "</td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_ausencias[7].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarAusencia(" + obj_ausencias[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=10&mnu=17&fml=2&ias=" + obj_ausencias[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarAusencia(" + obj_ausencias[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (Integer.parseInt(obj_ausencias[7].toString()) == 1) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='ActivarAusencia(" + obj_ausencias[0] + ")' class='fa fa-lock fa-size_small'></span>");
                                }
                            } else {
                                out.print("<b class='naranja'><span onclick='AlertaDiciplina()' class='fa fa-lock fa-size_small' title='Manipular registro desde Disciplina'></span></b>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Disciplina_descargos")) {
                    //<editor-fold defaultstate="collapsed" desc="DISIPLINA Y DESCARGOS">
                    categoria_modulo = 5;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_disciplina = Integer.parseInt(pageContext.getRequest().getAttribute("Id_disciplina").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_disciplina == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=13&mnu=18&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nuevo Disciplina / Descargos</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=14'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de disciplina / descargo :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br />Motivo:<br /><textarea name='Txt_motivo' id='Txt_motivo' style='width: 100%; height: 100px' placeholder='Descripcion del motivo' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_motivo');val1.add(Validate.Presence);</script>");
                        out.print("<br />Dias : <input type='number' name='Txt_dias' id='Txt_dias' min='0' max='30' style='width:50px;' placeholder='#' value='0' required />");
                        out.print("<br /><input type='submit' value='Guardar Disciplina / Descargo' /></td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'></textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_disciplina > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_disciplina = jpacdcp.Consultar_disciplina_id(id_disciplina);
                        Object[] obj_disciplina = (Object[]) lst_disciplina.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_disciplina[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_disciplina[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=13&mnu=18&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Disciplina / Descargos</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=14&idc=" + id_disciplina + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_disciplina[6] + "' /><hr />");
                        out.print("Fecha de disciplina / descargo :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_disciplina[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        if (obj_disciplina[3].toString().equals("Sancion")) {
                            out.print("<br />Tipo :<br /><b class='naranja'>" + obj_disciplina[3].toString().toUpperCase() + "</b>");
                            out.print("<input type='hidden' name='Cbx_tipo' id='Cbx_tipo' value='" + obj_disciplina[3] + "'>");
                        } else {
                            lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                            out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                            out.print("<option value='0'>Seleccionar tipo</option>");
                            for (int i = 0; i < lst_categorias.size(); i++) {
                                Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                                if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                    if (obj_categoria[1].equals(obj_disciplina[3])) {
                                        out.print("<option value='" + obj_categoria[1] + "' selected>" + obj_categoria[1] + "</option>");
                                    } else {
                                        out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                                    }
                                }
                            }
                            out.print("</select>");
                            out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                            out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        }
                        out.print("<br />Motivo:<br /><textarea name='Txt_motivo' id='Txt_motivo' style='width: 100%; height: 100px' placeholder='Descripcion del motivo' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_disciplina[4] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_motivo');val1.add(Validate.Presence);</script>");
                        out.print("<br />Dias : <input type='number' name='Txt_dias' id='Txt_dias' min='0' max='30' style='width:50px;' placeholder='#' value='" + obj_disciplina[10] + "' required />");
                        out.print("<br /><input type='submit' value='Modificar' /></td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>" + obj_disciplina[5] + "</textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=13&mnu=18&fml=1'><span class='fa fa-thumbs-down fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Disciplina / Descargos<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_disciplina = jpacdcp.Consultar_disciplina(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_disciplina == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'DISIPLINA_DESCARGOS')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Tipo</th>");
                        out.print("<th>Dias</th>");
                        out.print("<th>Motivo</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_disciplina.size(); i++) {
                            Object[] obj_disciplina = (Object[]) lst_disciplina.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_disciplina[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_disciplina[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_disciplina[2] + "</td>");
                            out.print("<td>" + obj_disciplina[3] + "</td>");
                            out.print("<td>" + obj_disciplina[10] + "</td>");
                            out.print("<td valign='top' style='width:35%'>" + obj_disciplina[4] + "</td>");
                            out.print("<td valign='top' style='width:35%'>" + obj_disciplina[5] + "</td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_disciplina[7].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarDisciplina(" + obj_disciplina[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=13&mnu=18&fml=2&idc=" + obj_disciplina[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarDisciplina(" + obj_disciplina[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarDisciplina(" + obj_disciplina[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Retiros")) {
                    //<editor-fold defaultstate="collapsed" desc="RETIROS">
                    categoria_modulo = 7;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_retiro = Integer.parseInt(pageContext.getRequest().getAttribute("Id_retiro").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_retiro == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=16&mnu=19&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nuevo Retiro</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=17'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/></div><hr />");
                        out.print("Fecha de retiro :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Guardar Retiro' /></td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'></textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_retiro > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_retiro = jpacrtr.Consultar_retiro_id(id_retiro);
                        Object[] obj_retiro = (Object[]) lst_retiro.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_retiro[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_retiro[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=16&mnu=19&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Disciplina / Descargos</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=17&irt=" + id_retiro + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><hr />");
                        out.print("Fecha de retiro :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_retiro[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Tipo :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
                        out.print("<option value='0'>Seleccionar tipo</option>");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                if (obj_categoria[1].equals(obj_retiro[7])) {
                                    out.print("<option value='" + obj_categoria[1] + "' selected>" + obj_categoria[1] + "</option>");
                                } else {
                                    out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><input type='submit' value='Modificar' /></td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>" + obj_retiro[8] + "</textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=16&mnu=19&fml=1'><span class='fa fa-user-alt-slash fa-size_super_small'></span></a>");
                    }
                    out.print("Retiros<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_retiros = jpacrtr.Consultar_retiros(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_retiros == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'RETIROS')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Area / Cargo</th>");
                        out.print("<th>Motivo</th>");
                        out.print("<th>Observación</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_retiros.size(); i++) {
                            Object[] obj_retiros = (Object[]) lst_retiros.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_retiros[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_retiros[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_retiros[2] + "</td>");
                            out.print("<td>" + obj_retiros[4] + " / " + obj_retiros[6] + "</td>");
                            out.print("<td>" + obj_retiros[7] + "</td>");
                            out.print("<td>" + obj_retiros[8] + "</td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_retiros[9].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarRetiro(" + obj_retiros[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=16&mnu=19&fml=2&irt=" + obj_retiros[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarRetiro(" + obj_retiros[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                //out.print("<span onclick='ActivarRetiro(" + obj_retiros[0] + ")' class='fa fa-lock fa-size_small'></span>");
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<a href='Seguimiento?opc=16&mnu=19&fml=2&irt=" + obj_retiros[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                out.print("&nbsp;&nbsp;&nbsp<span class='fa fa-lock fa-size_small' title='No se permite abrir el registro por que ya se confirmo el retiro'></span>");
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
//</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
//</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Dotaciones")) {
                    //<editor-fold defaultstate="collapsed" desc="DOTACIONES">
                    categoria_modulo = 1;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_dotacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_dotacion").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_dotacion == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=19&mnu=20&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nueva Asignacion de Dotación</h3>");
                        out.print("<table style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=20'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de asignación de dotación :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        try {
                            lst_inv_dotaciones = mtddtm.Productos();
                        } catch (Exception ex) {
                            lst_inv_dotaciones = null;
                        }
                        out.print("<br /><label>Dotación:<br/>");
                        out.print("<input type='text' type='text' style='width:80%' id='Txt_dotacion' list='Dotaciones' placeholder='Listado de dotación'/>");
                        out.print("<datalist id='Dotaciones'><label><select name='Dotaciones'>");
                        if (lst_inv_dotaciones != null) {
                            for (int i = 0; i < lst_inv_dotaciones.size(); i++) {
                                String dotacion = lst_inv_dotaciones.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                                out.print("<option value='" + dotacion + "'>");
                            }
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("<br />Cantidad : <input type='number' id='Txt_cantidad' name='Txt_cantidad' min='0' value='0' style='width:15%' placeholder='Cant.'/>");
                        // + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad');val1.add(Validate.Presence);</script>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='return Asignar_li()'><span class='fa fa-shopping-cart fa-size_super_small'></span></a>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='Refrescar_asignar()'><span class='fa fa-repeat fa-size_super_small'></span></a>");
                        out.print("<input type='hidden' name='Txt_asignacion_dotacion' id='Txt_asignacion_dotacion' />");
                        out.print("<div style='width:100%;height:150px;overflow:scroll;'>");
                        out.print("<ul id='lst_asiganacion'></ul>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/><textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>Dotación asignada.</textarea>"
                                + "<br /><input type='submit' id='Btn_asignar_dotacion' style='display:none' value='Asignar Dotacion' />");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_dotacion > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_dotacion = jpacdtc.Consultar_dotacion_id(id_dotacion);
                        Object[] obj_dotacion = (Object[]) lst_dotacion.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_dotacion[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_dotacion[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=19&mnu=20&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Asignacion de Dotación</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=20&idt=" + id_dotacion + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_persona[0] + "' /><hr />");
                        out.print("Fecha de asignación de dotación :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_dotacion[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        try {
                            lst_inv_dotaciones = mtddtm.Productos();
                        } catch (Exception ex) {
                            lst_inv_dotaciones = null;
                        }
                        out.print("<br /><label>Dotación:<br/>");
                        out.print("<input type='text' type='text' style='width:80%' id='Txt_dotacion' list='Dotaciones' placeholder='Listado de dotación'/>");
                        out.print("<datalist id='Dotaciones'><label><select name='Dotaciones'>");
                        if (lst_inv_dotaciones != null) {
                            for (int i = 0; i < lst_inv_dotaciones.size(); i++) {
                                String dotacion = lst_inv_dotaciones.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                                out.print("<option value='" + dotacion + "'>");
                            }
                        }
                        out.print("</select></label></datalist>");
                        out.print("<br />Cantidad : <input type='number' id='Txt_cantidad' name='Txt_cantidad' min='0' value='0' style='width:15%' placeholder='Cant.'/>");
                        // + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad');val1.add(Validate.Presence);</script>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='return Asignar_li()'><span class='fa fa-shopping-cart fa-size_super_small'></span></a>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='Refrescar_asignar()'><span class='fa fa-repeat fa-size_super_small'></span></a>");
                        out.print("<input type='hidden' name='Txt_asignacion_dotacion' id='Txt_asignacion_dotacion' value='" + obj_dotacion[3] + "'/>");
                        out.print("<div style='width:100%;height:150px;overflow:scroll;'>");
                        out.print("<ul id='lst_asiganacion'>");
                        //<editor-fold defaultstate="collapsed" desc="DOTACIÓN ASIGNADA">
                        String[] arg_dotacion = obj_dotacion[3].toString().replace("][", "<br />").replace("[", "").replace("]", "").split("<br />");
                        for (int i = 0; i < arg_dotacion.length; i++) {
                            out.print("<li id='" + arg_dotacion[i] + "' style=\"font-size: 14px;\"><span onclick=\"Eliminar_li(this)\" class=\"fa fa-trash-alt fa-size_super_small\"></span>" + arg_dotacion[i] + "</li>");
                        }
//</editor-fold>
                        out.print("</ul>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/><textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>" + obj_dotacion[4] + "</textarea>"
                                + "<br /><input type='submit' id='Btn_asignar_dotacion' style='display:block' value='Modificar Dotacion' />");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=19&mnu=20&fml=1'><span class='fa fa-tshirt fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Dotaciones<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_dotaciones = jpacdtc.Consultar_dotaciones(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_dotaciones == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'DOTACION')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Entrega</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th style='width:10%'>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_dotaciones.size(); i++) {
                            Object[] obj_dotaciones = (Object[]) lst_dotaciones.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_dotaciones[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_dotaciones[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_dotaciones[2] + "</td>");
                            String[] arg_asignacion = obj_dotaciones[3].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                            out.print("<td valign='top' style='width:50%'>");
                            out.print("<table style='width:100%'>");
//                        if (arg_asignacion.length > 1) {
                            for (int j = 0; j < arg_asignacion.length; j++) {
                                out.print("<tr>");
                                //out.print("<td style='width:20%'><b>COD: </b>" + arg_asignacion[j].toString().split(" / ")[0] + "</td>");
                                out.print("<td><b>REF: </b>" + arg_asignacion[j].toString().split("/")[0] + " </td>");
                                out.print("<td style='width:20%'><b>CANT: </b>" + arg_asignacion[j].toString().split("/")[1] + "</td>");
                                out.print("</tr>");
                            }
//                        } else {
//                            out.print("<tr>");
//                            //out.print("<td style='width:20%'><b>COD: </b>" + arg_asignacion[j].toString().split(" / ")[0] + "</td>");
//                            out.print("<td><b>REF: </b>" + arg_asignacion[0].toString().split("/")[0] + " </td>");
//                            out.print("<td style='width:20%'><b>CANT: </b>" + arg_asignacion[0].toString().split("/")[1] + "</td>");
//                            out.print("</tr>");
//                        }
                            out.print("</table>");
                            out.print("</td>");
                            out.print("<td valign='top' style='width:25%'>" + obj_dotaciones[4] + "</td>");
                            out.print("<td align='center'>");
                            if (Integer.parseInt(obj_dotaciones[5].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarDotacion(" + obj_dotaciones[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=19&mnu=20&fml=2&idt=" + obj_dotaciones[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarDotacion(" + obj_dotaciones[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarDotacion(" + obj_dotaciones[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Epp")) {
                    //<editor-fold defaultstate="collapsed" desc="EPP">
                    categoria_modulo = 1;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_epp = Integer.parseInt(pageContext.getRequest().getAttribute("Id_epp").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_epp == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=30&mnu=33&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nueva Asignacion de EPP</h3>");
                        out.print("<table style='width:100%'>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=31'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de asignación de dotación :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        try {
                            lst_inv_dotaciones = mtddtm.ProductosEpp();
                        } catch (Exception ex) {
                            lst_inv_dotaciones = null;
                        }
                        out.print("<br /><label>Elemento:<br/>");
                        out.print("<input type='text' type='text' style='width:80%' id='Txt_dotacion' list='Dotaciones' placeholder='Listado de dotación'/>");
                        out.print("<datalist id='Dotaciones'><label><select name='Dotaciones'>");
                        if (lst_inv_dotaciones != null) {
                            for (int i = 0; i < lst_inv_dotaciones.size(); i++) {
                                String dotacion = lst_inv_dotaciones.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                                out.print("<option value='" + dotacion + "'>");
                            }
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("<br />Cantidad : <input type='number' id='Txt_cantidad' name='Txt_cantidad' min='0' value='0' style='width:15%' placeholder='Cant.'/>");
                        // + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad');val1.add(Validate.Presence);</script>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='return Asignar_li()'><span class='fa fa-shopping-cart fa-size_super_small'></span></a>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='Refrescar_asignar()'><span class='fa fa-repeat fa-size_super_small'></span></a>");
                        out.print("<input type='hidden' name='Txt_asignacion_dotacion' id='Txt_asignacion_dotacion' />");
                        out.print("<div style='width:100%;height:150px;overflow:scroll;'>");
                        out.print("<ul id='lst_asiganacion'></ul>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/><textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>Dotación asignada.</textarea>"
                                + "<br /><input type='submit' id='Btn_asignar_dotacion' style='display:none' value='Asignar EPP' />");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_epp > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_epp = jpacepp.Consultar_epp_id(id_epp);
                        Object[] obj_epp = (Object[]) lst_epp.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_epp[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_epp[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 5%;left:10%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=30&mnu=33&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Asignacion de EPP</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=31&iep=" + id_epp + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_persona[0] + "' /><hr />");
                        out.print("Fecha de asignación de dotación :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_epp[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        try {
                            lst_inv_dotaciones = mtddtm.ProductosEpp();
                        } catch (Exception ex) {
                            lst_inv_dotaciones = null;
                        }
                        out.print("<br /><label>Elementos:<br/>");
                        out.print("<input type='text' type='text' style='width:80%' id='Txt_dotacion' list='Dotaciones' placeholder='Listado de EPP'/>");
                        out.print("<datalist id='Dotaciones'><label><select name='Dotaciones'>");
                        if (lst_inv_dotaciones != null) {
                            for (int i = 0; i < lst_inv_dotaciones.size(); i++) {
                                String dotacion = lst_inv_dotaciones.get(i).toString().replace("[", "").replace("]", "").replace("0,", "0.").replace(",", ".");
                                out.print("<option value='" + dotacion + "'>");
                            }
                        }
                        out.print("</select></label></datalist>");
                        out.print("<br />Cantidad : <input type='number' id='Txt_cantidad' name='Txt_cantidad' min='0' value='0' style='width:15%' placeholder='Cant.'/>");
                        // + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_cantidad');val1.add(Validate.Presence);</script>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='return Asignar_li()'><span class='fa fa-shopping-cart fa-size_super_small'></span></a>");
                        out.print("&nbsp;&nbsp;&nbsp;<a style='text-decoration:none' onclick='Refrescar_asignar()'><span class='fa fa-repeat fa-size_super_small'></span></a>");
                        out.print("<input type='hidden' name='Txt_asignacion_dotacion' id='Txt_asignacion_dotacion' value='" + obj_epp[3] + "'/>");
                        out.print("<div style='width:100%;height:150px;overflow:scroll;'>");
                        out.print("<ul id='lst_asiganacion'>");
                        //<editor-fold defaultstate="collapsed" desc="DOTACIÓN ASIGNADA">
                        String[] arg_dotacion = obj_epp[3].toString().replace("][", "<br />").replace("[", "").replace("]", "").split("<br />");
                        for (int i = 0; i < arg_dotacion.length; i++) {
                            out.print("<li id='" + arg_dotacion[i] + "' style=\"font-size: 14px;\"><span onclick=\"Eliminar_li(this)\" class=\"fa fa-trash-alt fa-size_super_small\"></span>" + arg_dotacion[i] + "</li>");
                        }
                        out.print("</ul>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:150px;heigth:150px' /><br />");
                        out.print("Observaciones : <br/><textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='Descripcion'>" + obj_epp[4] + "</textarea>"
                                + "<br /><input type='submit' id='Btn_asignar_dotacion' style='display:block' value='Modificar Dotacion' />");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=30&mnu=33&fml=1'><span class='fa fa-hard-hat fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de EPP asignados<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_epps = jpacepp.Consultar_epps(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_epps == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'EPP')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Entrega</th>");
                        out.print("<th>Observaciones</th>");
                        out.print("<th style='width:10%'>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_epps.size(); i++) {
                            Object[] obj_epps = (Object[]) lst_epps.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_epps[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_epps[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td>" + obj_epps[2] + "</td>");
                            String[] arg_asignacion = obj_epps[3].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                            out.print("<td valign='top' style='width:50%'>");
                            out.print("<table style='width:100%'>");
                            for (int j = 0; j < arg_asignacion.length; j++) {
                                out.print("<tr>");
                                //out.print("<td style='width:20%'><b>COD: </b>" + arg_asignacion[j].toString().split(" / ")[0] + "</td>");
                                out.print("<td><b>REF: </b>" + arg_asignacion[j].toString().split("/")[0] + " </td>");
                                out.print("<td style='width:20%'><b>CANT: </b>" + arg_asignacion[j].toString().split("/")[1] + "</td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("</td>");
                            out.print("<td valign='top' style='width:25%'>" + obj_epps[4] + "</td>");
                            out.print("<td align='center'>");
                            if (Integer.parseInt(obj_epps[5].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarEpp(" + obj_epps[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=30&mnu=33&fml=2&iep=" + obj_epps[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarEpp(" + obj_epps[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarEpp(" + obj_epps[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Capacitaciones")) {
                    //<editor-fold defaultstate="collapsed" desc="CAPACITACIONES">
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_capacitacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_capacitacion").toString());
                    int idCapacDetalil = Integer.parseInt(pageContext.getRequest().getAttribute("Id_capDetall").toString());
                    long doc = 0;
                    long cod = 0;
                    try {
                        doc = Long.parseLong(pageContext.getRequest().getAttribute("txtDocument").toString());
                        cod = Long.parseLong(pageContext.getRequest().getAttribute("txtCode").toString());
                    } catch (Exception e) {
                        doc = 0;
                        cod = 0;
                    }

                    int dia = DateLocal.getDayOfMonth();
                    int mes = DateLocal.getMonthValue();
                    int anio = DateLocal.getYear();

                    String day = String.format("%02d", dia);
                    String month = String.format("%02d", mes);

                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_capacitacion == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 10%;left:15%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=22&mnu=23&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nueva Capacitacion</h3>");
                        out.print("<form method='post' action='Seguimiento?opc=23'>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("Fecha de capacitación :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + anio + "-" + month + "-" + day + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<br />Titulo :<br /><input type='text' name='Txt_titulo' id='Txt_titulo' style='width:80%' placeholder='Titulo de la capacitación'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_titulo');val1.add(Validate.Presence);</script>");
                        out.print("<br />Entidad :<br /><input type='text' name='Txt_entidad' id='Txt_entidad' style='width:80%' placeholder='Entidad que dicta la capacitación' value='PLASTITEC'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_entidad');val1.add(Validate.Presence);</script>");
                        out.print("<br />Capacitador :<br /><input type='text' name='Txt_capacitador' id='Txt_capacitador' style='width:80%' placeholder='Nombre del capacitador'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_capacitador');val1.add(Validate.Presence);</script>");
                        out.print("<br />Duración: <input type='number' name='Txt_duracion' id='Txt_duracion' required min='1' style='width:20%' placeholder='#' value='0'/> min");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:500px' >Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'></textarea>");
                        out.print("<input type='submit' value='Guardar Capacitación' />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    } else if (formulario == 2 && id_capacitacion > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_capacitacion = jpaccpc.Consultar_capacitacion_id(id_capacitacion);
                        Object[] obj_capacitacion_cabecera = (Object[]) lst_capacitacion.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 10%;left:15%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=22&mnu=23&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Capacitacion</h3>");
                        out.print("<form method='post' action='Seguimiento?opc=23&icp=" + id_capacitacion + "'>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("Fecha de capacitación :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_capacitacion_cabecera[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<br />Titulo :<br /><input type='text' name='Txt_titulo' id='Txt_titulo' style='width:80%' placeholder='Titulo de la capacitación' value='" + obj_capacitacion_cabecera[3] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_titulo');val1.add(Validate.Presence);</script>");
                        out.print("<br />Entidad :<br /><input type='text' name='Txt_entidad' id='Txt_entidad' style='width:80%' placeholder='Entidad que dicta la capacitación' value='" + obj_capacitacion_cabecera[1] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_entidad');val1.add(Validate.Presence);</script>");
                        out.print("<br />Capacitador :<br /><input type='text' name='Txt_capacitador' id='Txt_capacitador' style='width:80%' placeholder='Nombre del capacitador' value='" + obj_capacitacion_cabecera[5] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_capacitador');val1.add(Validate.Presence);</script>");
                        out.print("<br />Duración: <input type='number' name='Txt_duracion' id='Txt_duracion' required min='1' style='width:20%' placeholder='#' value='" + obj_capacitacion_cabecera[4] + "'/> min");
//                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;# Folio: <input type='text' name='Txt_folio' id='Txt_folio' style='width:20%' placeholder='# Folio' value='" + obj_capacitacion_cabecera[8] + "'/>"
//                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_folio');val1.add(Validate.Presence);</script>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' style='width:500px' >Observaciones :<br />");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>" + obj_capacitacion_cabecera[6] + "</textarea>");
                        out.print("<input type='submit' value='Modificar Capacitación' />");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>
                    } else if (formulario == 3 && id_capacitacion > 0) {
                        //<editor-fold defaultstate="collapsed" desc="VISOR R-RH-009">
                        lst_capacitacion = jpaccpc.Consultar_capacitacion_id(id_capacitacion);
                        Object[] obj_capacitacion_cabecera = (Object[]) lst_capacitacion.get(0);

                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:80%;padding:10px;height:85%;position: absolute;top: 2%;left:10%;overflow-y:auto;'>");

                        //<editor-fold defaultstate="collapsed" desc="CABECERA REGISTRO">
                        out.print("<div style='float:right;margin-top: 5px;margin-right: 5px;margin-bottom: 5px;'><a href='Seguimiento?opc=22&mnu=23&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:left;margin-top: 5px;margin-left: 5px;margin-bottom: 5px;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('Excel', 'DETALLE_CAPACITACION')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div class='headCap'>");
                        out.print("<table class='table' id='Excel'>");
                        out.print("<tr>");
                        out.print("<td colspan='10' style='background-color:#ccc;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td align='center' colspan='2' style='width:20%' >"
                                + "<img src='Interfaz/MasterPage/images/Logo.png' alt='Logo' style='width:180px;height:60px' />"
                                + "</td>");
                        out.print("<td colspan='6' style='width:60%'  align='center'><b class='negro'>REGISTRO<hr />CAPACITACIÓN DE PERSONAL</b></td>");
                        int feReg = Integer.parseInt(obj_capacitacion_cabecera[2].toString().replace("-", ""));
                        if (feReg >= 20220325) {
                            out.print("<td colspan='2' style='width:20%' align='center'>CODIGO R-RH-009<hr />VERSIÓN 6</td>");
                        } else if (feReg >= 20180523) {
                            out.print("<td colspan='2' style='width:20%' align='center'>CODIGO R-RH-009<hr />VERSIÓN 5</td>");
                        } else {
                            out.print("<td colspan='2' style='width:20%' align='center'>CODIGO R-RH-009<hr />VERSIÓN 4</td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2'><b>Fecha : </b></td>");
                        out.print("<td colspan='2'>" + obj_capacitacion_cabecera[2] + "</td>");
                        out.print("<td><b>Entidad : </b></td>");
                        out.print("<td colspan='3'>" + obj_capacitacion_cabecera[1] + "</td>");
                        out.print("<td><b>Personas : </b></td>");
                        out.print("<td># " + obj_capacitacion_cabecera[7] + " </td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='3'><b>Nombre de la entidad o tematica: </b></td>");
                        out.print("<td colspan='5'>" + obj_capacitacion_cabecera[3] + "</td>");
                        out.print("<td><b>Duración : </b></td>");
                        out.print("<td>" + obj_capacitacion_cabecera[4] + " min</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='3'><b>Capacitador : </b></td>");
                        out.print("<td colspan='5'>" + obj_capacitacion_cabecera[5] + "</td>");
                        out.print("<td><b>Folio : </b></td>");
                        out.print("<td>" + obj_capacitacion_cabecera[8] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='10' style='width:50%' valign='top'><b>Observaciones : </b>" + obj_capacitacion_cabecera[6] + "</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="PARAMETROS DIFERENTES">
                        lst_capacitacion = jpaccpc.ConsultParameterbyId(id_capacitacion);
                        String TipoAct = "", Dirg = "", Alca = "", Metodo = "", Evalu = "";
                        int estad = 0;
                        if (lst_capacitacion != null) {
                            Object[] ObjCap = (Object[]) lst_capacitacion.get(0);
                            estad = Integer.parseInt(ObjCap[6].toString());
                            try {
                                TipoAct = ObjCap[1].toString();
                                Dirg = ObjCap[2].toString();
                                Alca = ObjCap[3].toString();
                                Metodo = ObjCap[4].toString();
                                Evalu = ObjCap[5].toString();
                            } catch (Exception e) {
                                TipoAct = "";
                                Dirg = "";
                                Alca = "";
                                Metodo = "";
                                Evalu = "";
                            }
                        }
                        out.print("<form action='Seguimiento?opc=38&icp=" + id_capacitacion + "' method='post'>");
                        out.print("<div class='' style='display: flex; justify-content: center; margin-bottom: 15px;margin-top: 10px;'>");
                        out.print("<div class='' style='width: 18%;'>");
                        out.print("<b>TIPO DE ACTIVIDAD:</b>");

                        if (Integer.parseInt(obj_capacitacion_cabecera[0].toString()) <= 380219) {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Capacitación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Capacitacion' checked></div></div>");
                        } else {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Capacitación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Capacitacion' onclick='ActiveRadioData(2,\"Otro_one\")' " + ((TipoAct.equals("Capacitacion")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        }
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Charla</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Charla' onclick='ActiveRadioData(2,\"Otro_one\")' " + ((TipoAct.equals("Charla")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Divulgación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Divulgacion' onclick='ActiveRadioData(2,\"Otro_one\")' " + ((TipoAct.equals("Divulgacion")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Otro' onclick='ActiveRadioData(1,\"Otro_one\")' " + ((TipoAct.contains("Otro")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='width: 100%; display: " + ((TipoAct.contains("Otro")) ? "block" : "none") + ";' id='Otro_one'><input type='text' class='' name='Otro_one' " + ((TipoAct.contains("Otro")) ? "value='" + TipoAct.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div >");
                        out.print("</div>");
                        out.print("<div class='' style='width: 22%;'>");
                        out.print("<b>DIRIGIDO A:</b>");
                        if (Integer.parseInt(obj_capacitacion_cabecera[0].toString()) <= 380219) {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Colaborador(es)</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Colaborador' checked ></div></div>");
                        } else {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Colaborador(es)</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Colaborador' onclick='ActiveRadioData(2,\"Otro_two\")' " + ((Dirg.equals("Colaborador")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        }
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Proveedor(es) y/o contratista</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Proveedor' onclick='ActiveRadioData(2,\"Otro_two\")' " + ((Dirg.equals("Proveedor")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Visitante</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Visitante' onclick='ActiveRadioData(2,\"Otro_two\")' " + ((Dirg.equals("Visitante")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Otro' onclick='ActiveRadioData(1,\"Otro_two\")' " + ((Dirg.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='width: 100%; display: " + ((Dirg.contains("Otro")) ? "block" : "none") + ";' id='Otro_two'><input type='text' class='' name='Otro_two' " + ((Dirg.contains("Otro")) ? "value='" + Dirg.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div>");
                        out.print("</div>");
                        out.print("<div class='' style='width: 18%;'>");
                        out.print("<b>ALCANCE:</b>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Individual</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Individual' onclick='ActiveRadioData(2,\"Otro_three\")' " + ((Alca.equals("Individual")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        if (Integer.parseInt(obj_capacitacion_cabecera[0].toString()) <= 380219) {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Grupal</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Grupal' checked></div></div>");
                        } else {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Grupal</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Grupal' onclick='ActiveRadioData(2,\"Otro_three\")' " + ((Alca.equals("Grupal")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        }
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Puesto de trabajo</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='PuestoTrabajo' onclick='ActiveRadioData(2,\"Otro_three\")' " + ((Alca.equals("PuestoTrabajo")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Otro' onclick='ActiveRadioData(1,\"Otro_three\")' " + ((Alca.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='width: 100%; display: " + ((Alca.contains("Otro")) ? "block" : "none") + ";' id='Otro_three'><input type='text' class='' name='Otro_three' " + ((Alca.contains("Otro")) ? "value='" + Alca.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div>");
                        out.print("</div>");
                        out.print("<div class='' style='width: 18%;'>");
                        out.print("<b>METODOLOGÍA:</b>");
                        if (Integer.parseInt(obj_capacitacion_cabecera[0].toString()) <= 380219) {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Explicación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Explicacion' checked></div></div>");
                        } else {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Explicación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Explicacion' onclick='ActiveRadioData(2,\"Otro_four\")' " + ((Metodo.equals("Explicacion")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        }
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Práctica</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Practica' onclick='ActiveRadioData(2,\"Otro_four\")' " + ((Metodo.equals("Practica")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Juego de roles, Lúdica</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='JuegoRoles' onclick='ActiveRadioData(2,\"Otro_four\")' " + ((Metodo.equals("JuegoRoles")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Otro' onclick='ActiveRadioData(1,\"Otro_four\")' " + ((Metodo.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='width: 100%; display: " + ((Metodo.contains("Otro")) ? "block" : "none") + ";' id='Otro_four'><input type='text' class='' name='Otro_four' " + ((Metodo.contains("Otro")) ? "value='" + Metodo.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div>");
                        out.print("</div>");
                        out.print("<div class='' style='width: 18%;'>");
                        out.print("<b>EVALUACIÓN DE EFICACIA:</b>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Escrita</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='Escrita' onclick='ActiveRadioData(2,\"Otro_five\")' " + ((Evalu.equals("Escrita")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + " ></div></div>");
                        if (Integer.parseInt(obj_capacitacion_cabecera[0].toString()) <= 380219) {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Oral</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='Oral' checked></div></div>");
                        } else {
                            out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Oral</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='Oral' onclick='ActiveRadioData(2,\"Otro_five\")' " + ((Evalu.equals("Oral")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        }
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Práctica Supervisada</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='PracticaSuper' onclick='ActiveRadioData(2,\"Otro_five\")' " + ((Evalu.equals("PracticaSuper")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='Otro' onclick='ActiveRadioData(1,\"Otro_five\")' " + ((Evalu.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                        out.print("<div class='' style='width: 100%; display: " + ((Evalu.contains("Otro")) ? "block" : "none") + ";' id='Otro_five'><input type='text' class='' name='Otro_five' " + ((Evalu.contains("Otro")) ? "value='" + Evalu.split("/")[1] + "'" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='' style='display: flex;justify-content: center;'>");
                        out.print("<button style='display: none; background: #939393;\n"
                                + "    color: white;\n"
                                + "    border: 1px solid #fdfdfd00;\n"
                                + "    height: 35px;\n"
                                + "    border-radius: 7px;\n"
                                + "    width: 100px;\n"
                                + "    box-shadow: 1px 2px 7px 2px #c1c1c1;\n"
                                + "    cursor: pointer;' id='updateButton'>Actualizar</button>");
                        out.print("</div>");
                        out.print("</form>");
//</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="PERSONAL EXTERNO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='cont_reg' style='width: 15%; margin-left: 45%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Personal Externo </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Seguimiento?opc=24&icp=" + id_capacitacion + "' method='post' class=''>");
                        out.print("<input type='hidden' name='Txt_manual' id='Txt_manual' value='External'>");
                        out.print("<div style='text-align: center;'>");
                        out.print("<span>Numero de documento</span><br>");
                        out.print("<input type='text' class='form-control' name='NmbDoc' id='' placeholder='Numero documento' title='' value='' required>");
                        out.print("</div>");
                        out.print("<div style='text-align: center;'>");
                        out.print("<span>Nombres y apellidos</span><br>");
                        out.print("<input type='text' class='form-control' name='TxtName' id='' placeholder='Nombre y apellidos' title='' value='' required>");
                        out.print("</div>");
                        out.print("<div style='text-align: center;'>");
                        out.print("<span>Cargo</span><br>");
                        out.print("<input type='text' class='form-control' name='TxtCargo' id='' placeholder='Cargo' title='' value='' required>");
                        out.print("</div>");
                        out.print("<div class='' style='text-align: center;'>");
                        out.print("<input type='submit' value='Registro'>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="NUEVO PERSONAL">
                        if (Integer.parseInt(obj_capacitacion_cabecera[9].toString()) == 0) {

                            out.print("<div class='' style='display: flex;align-items: baseline;justify-content: space-between;'>");
                            out.print("<div class='' style='display: flex;'>");
                            out.print("<div class='' style='display: flex;'>");
                            out.print("<a style='font-size: 22px; cursor: pointer;margin-top: 10px; margin-bottom: 10px;' onclick='activeShield(1,\"contReg\")' title='Agregar asistente'><i class='fas fa-plus-circle'></i></a>");
                            out.print("<a href='#' style='font-size: 22px;cursor: pointer;margin-top: 10px;margin-bottom: 10px;margin-left: 7px;' onclick='mostrarConvencion(2)'><i class=\"fas fa-hands-helping\"></i></a>");
                            out.print("</div>");
                            out.print("<div class='' id='contReg' style='transition: 1s;display: none;margin-left: 13px;'>");
                            out.print("<form action='Seguimiento?opc=24&icp=" + id_capacitacion + "' method='post'>");
                            out.print("<a style='font-size: 22px; color: #df3e3e; cursor: pointer;' onclick='activeShield(2,\"contReg\")' title='Cancelar'><i class='fas fa-times-circle'></i></a>&nbsp;<input type='text' class='form-control' style='width: 290px;' name='Txt_manual' id='Txt_manual' placeholder='Datos del empleado' list='Personal'>&nbsp;");
                            out.print("<datalist id='Personal'><label><select name='Personal'>");
                            lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                            for (int i = 0; i < lst_personal.size(); i++) {
                                Object[] obj_personal = (Object[]) lst_personal.get(i);
                                out.print("<option value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "'>");
                            }
                            out.print("</select></label></datalist>");
                            out.print("<input type='submit' value='Registrar' />");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='daxSearch' style='justify-content: center; display: flex; margin-bottom: 12px;'>");
                            out.print("<input type='text' class='form-control' name='' id='Txt_filtro' onkeyup='Filtrar(\"resultadosx\")' placeholder='Buscar...'>");
                            out.print("</div>");

                            out.print("<div class=''>");
                            out.print("<button class='btn-select' onclick=\"toggleCheckboxes(true)\" style='margin-right: -10px;'><i class=\"fas fa-check-square\"></i></button>\n"
                                    + "    <button class='btn-select' onclick=\"toggleCheckboxes(false)\"><i class=\"far fa-square\"></i></button>");
                            out.print("</div>");

                            out.print("</div>");
                        }
//</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display: " + ((doc == 0 && cod == 0) ? "none" : "block") + ";'>");
                        out.print("<div class='cont_reg' style='margin-left: 22%;'>");
                        out.print("<div style='display: flex; justify-content: end'>");
                        out.print("<a href='Seguimiento?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "' style='height: 30px;padding: 3px;width: 30px;' placeholder='Numero'><i class='fas fa-times'></i></a>");
                        out.print("</div>");

                        out.print("<div class='' style='display: flex;'>");
                        out.print("<div class='' style='text-align: center;border-right: 1px solid #9d9c9c;padding-right: 10px;margin-right: 10px;width: 30%;'>");

                        out.print("<form action='Seguimiento?opc=35&icp=" + id_capacitacion + "' method='post' id='formUsers'>");
                        out.print("<h1>Consultar</h1>");
                        out.print("<div class='' style=''>");
                        if (doc == 0 && cod == 0) {
                            out.print("<input type='hidden' class='form-control' name='idCapDetalle' id='Id_valId'>");
                            out.print("<input type='hidden' class='form-control' name='' id='Id_valdDoc'>");
                            out.print("<input type='hidden' class='form-control' name='' id='Id_valdCod'>");
                        } else {
                            out.print("<input type='hidden' class='form-control' name='idCapDetalle' value='" + idCapacDetalil + "'>");
                            out.print("<input type='hidden' class='form-control' name='' id='Id_valdDoc' value='" + doc + "'>");
                            out.print("<input type='hidden' class='form-control' name='' id='Id_valdCod' value='" + cod + "'>");
                        }
                        out.print("<input type='text' name='txtDocument' value='" + ((doc == 0 && cod == 0) ? "" : doc) + "' id='IdDocument' placeholder='Documento' data-toggle='tooltip' data-placement='top' title='' onkeyup='CompareData(\"Id_valdDoc\",\"IdDocument\")' autocomplete='off' required><br>");
                        out.print("<span style='color: red; display: none; margin-bottom: 16px;' id='NonCoin'>El documento no coincide con <br>el usuario seleccionado!</span>");
                        out.print("</div>");
                        out.print("<div class='' style=''>");
                        out.print("<input type='text' name='txtCode' value='" + ((doc == 0 && cod == 0) ? "" : cod) + "' id='idCodUSer' placeholder='Codigo' data-toggle='tooltip' data-placement='top' title='' onkeyup='CompareCode(\"Id_valdCod\",\"idCodUSer\")' autocomplete='off' required>");
                        out.print("<span style='color: red; display: none; margin-bottom: 16px;' id='NonCoinCod'>El codigo no coincide con <br>el usuario seleccionado!</span>");
                        out.print("</div>");
                        out.print("<input type='submit' id='ButtonConsul' style='width: 57%;' value='Consultar'>");
                        out.print("</form>");
                        out.print("</div>");
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="FIRMAS">
                        out.print("<div style='width: 70%;'>");
                        out.print("<h2> Firma </h2>");

                        if (doc == 0 && cod == 0) {
                            out.print("<div style='text-align: center;'>");
                            out.print("<h3><b style='color: #858585;'>No se ha consultado alguna firma</b></h3>");
                            out.print("<i style='font-size: 70px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                        } else {
                            String name = "", cargo = "", area = "", sig = "";
                            char init;
                            if (doc == cod) {
                                lst_persona = jpacpsn.Consultar_CapacitacionExternal(id_capacitacion, doc);
                                Object[] obj_persona = (Object[]) lst_persona.get(0);
                                name = obj_persona[3].toString();
                                area = "";
                                cargo = obj_persona[4].toString();
                                sig = "EX";
                                init = 'A';

                            } else {
                                lst_persona = jpacpsn.Consultar_empleado_documento("" + doc + "");
                                Object[] obj_persona = (Object[]) lst_persona.get(0);
                                name = obj_persona[1].toString() + " " + obj_persona[2].toString();
                                area = obj_persona[9].toString();
                                cargo = obj_persona[7].toString();
                                sig = obj_persona[10].toString();
                                init = obj_persona[2].toString().charAt(0);
                            }

                            lst_firma = firmasJpa.TraerFirmas(doc, cod);
                            String firma = "";
                            int idFirma = 0;
                            if (lst_firma != null && lst_firma.size() > 0) {
                                out.print("<div class='' style='display: flex;'>");
                                out.print("<div class=''>");
                                out.print("<b>" + name + "</b><br><h4 style='margin-bottom:0px;'>" + area + "</h4><h4 style='margin-top:0px;'>" + cargo + "</h4>");
                                out.print("</div>");
                                out.print("<img id='Img_foto' src='Fotos/" + doc + ".jpg' style='width:130px;heigth:130px;margin-top: -64px;margin-left: 36px;' />");
                                out.print("</div>");

                                String[] obj_firma = lst_firma.toString().replace("[", "").replace("]", "").split("---");
                                idFirma = Integer.parseInt(obj_firma[0].toString());
                                try {
                                    if (obj_firma[3] != null) {
                                        firma = ".regenerate([" + obj_firma[3] + "]);";
                                    } else {
                                        firma = "0";
                                    }
                                } catch (Exception e) {
                                    firma = "0";
                                }
                            } else {
                                firma = "0";
                            }
//                            out.print("<div style='float:right;'><a href='Personal?opc=4&mnu=22&abc=" + obj_persona[2].toString().charAt(0) + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");

                            out.print("<form action='Personal?opc=10&icp=" + id_capacitacion + "&idCapDetalle=" + idCapacDetalil + "&event=1' method='post' name='formPersonal' id='formPersonal'>");
                            out.print("<input type='hidden' name='dcm' value='" + doc + "'>");
                            out.print("<input type='hidden' name='cdg' value='" + cod + "'>");
                            out.print("<input type='hidden' name='abc' value='" + init + "'>");
                            out.print("<table class='table' style='width:50%'>");
                            out.print("<tr>");
                            out.print("<td>");
                            out.print("<div class='sigPad' id='smoothed' style='width:100%;'>");
                            out.print("<ul class='sigNav' style='display: block;'>");
                            if (firma.equals("0")) {
                                out.print("<li class='clearButton' style='display: list-item;'><a href='#clear'><span class='fa fa-eraser fa-size_super_small'></span></a></li>");
                            }
                            out.print("</ul>");
                            out.print("<div class='sig sigWrapper current' style='height: auto; display: block;'>");
                            out.print("<div class='codigo' style='display: block;" + ((!sig.toString().equals("GC")) ? "color:#596275" : "color:#2b5797") + "'>" + cod + "</div>");
                            out.print("<canvas class='pad' width='440px' height='250px'></canvas>");
                            out.print("<input type='hidden' name='Txt_firma' class='output' value='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<script>");
                            out.print("$(document).ready(function () {");
                            out.print("$('#smoothed').signaturePad({");
                            out.print("drawOnly: true,");
                            out.print("drawBezierCurves:true,");
                            if (firma.equals("0")) {
                            } else {
                                out.print("displayOnly:true,");
                            }
                            out.print("lineTop: 200,");
                            out.print("bgColour : 'transparent',");
                            out.print("penColour : '" + ((!sig.toString().equals("GC")) ? "#596275" : "#2b5797") + "'");
                            out.print("}");

                            out.print(")" + (!(firma.equals("0")) ? firma : ""));
                            out.print("});");
                            out.print("</script>");
                            out.print("</td>");
                            out.print("</tr>");
                            out.print("<tr>");

                            out.print("<td valign='top'><input type='hidden' name='Rdb_tipo_firma' value='0'/></td>");
                            out.print("</tr>");
                            out.print("</table>");
                            out.print("</form>");

                            if (!firma.equals("0")) {
                                out.print("<form action='Seguimiento?opc=36&icp=" + id_capacitacion + "' method='post' name='formSave'>");
                                out.print("<input type='hidden' class='form-control' name='idCapDetalle' value='" + idCapacDetalil + "' >");
                                out.print("<input type='hidden' class='form-control' name='idSignature' id='' value='" + idFirma + "' >");
                                out.print("</form>");
                            }

                            if (firma.equals("0")) {
                                out.print("<input type='submit' class='form-control' name='' id='' value='Registrar Firma' onclick='formPersonal.submit()'>");
                            } else {
                                out.print("<input type='submit' class='form-control' name='' id='' value='Firmar' onclick='formSave.submit()'>");
                            }
                            out.print("<div class=\"clear\"></div>");
                        }
                        out.print("</div>");
                        out.print("</div>");

                        out.print("</div>");
                        out.print("</div>");

                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="LISTA DE PERSONAL">
                        out.print("<table class='table table-bordered' id='resultadosx'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th colspan='2'>Documento</th>");
                        out.print("<th colspan='" + ((Integer.parseInt(obj_capacitacion_cabecera[9].toString()) == 0) ? "4" : "5") + "'>Nombres</th>");
                        out.print("<th colspan='3'>Cargo</th>");
                        out.print("<th>Firma</th>");
                        out.print("<th>Aprobo<br>Evaluacion</th>");
                        out.print("<th>Quitar</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        int countSig = 0;
                        lst_capacitacion = jpaccpc.Consultar_capacitacion_detalle(id_capacitacion);
                        if (lst_capacitacion != null) {
                            for (int i = 0; i < lst_capacitacion.size(); i++) {
                                Object[] obj_capacitacion = (Object[]) lst_capacitacion.get(i);
                                out.print("<tr>");
                                out.print("<td align='center' colspan='2'><b class='tooltip'>" + obj_capacitacion[2] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_capacitacion[2] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                                out.print("<td colspan='" + ((Integer.parseInt(obj_capacitacion_cabecera[9].toString()) == 0) ? "4" : "5") + "'>" + obj_capacitacion[3] + "</td>");
                                out.print("<td colspan='3'>" + obj_capacitacion[4] + "</td>");
                                int signa = 0;
                                try {
                                    signa = Integer.parseInt(obj_capacitacion[8].toString());
                                } catch (Exception e) {
                                    signa = 0;
                                    countSig++;
                                }
                                out.print("<td style='text-align:center;'>" + ((signa > 0) ? "<b style='color: green;'>Firmado</b>" : "<a href='#' onclick='mostrarConvencion(1);PassData(" + obj_capacitacion[0] + "," + obj_capacitacion[9] + "," + obj_capacitacion[2] + ",\"Id_valdDoc\");'><b style='color: red;'>Sin firma</b></a>") + "</td>");

                                out.print("<td style='text-align: center;'>");
                                if (obj_capacitacion[10] != null) {
                                    int Aprov = Integer.parseInt(obj_capacitacion[10].toString());
                                    if (Aprov == 0) {
                                        out.print("<span style='color: #db3d3dde;'><i class='fas fa-times-circle fa-size_small'></i></span>");
                                    } else if (Aprov == 1) {
                                        out.print("<span style='color: #3ddb49de;'><i class='fas fa-check-circle fa-size_small'></i></span>");
                                    }

                                } else {
                                    out.print("<input type='checkbox' class='exam-checkbox' id='" + obj_capacitacion[0] + "' onchange='updateHiddenField()'>");
                                }
                                out.print("</td>");
                                if (Integer.parseInt(obj_capacitacion_cabecera[9].toString()) == 0) {
                                    if (signa > 0) {
                                        out.print("<td align='center'><a href='#' disabled style='color: #d9d9d9;'><span class='fa fa-times-circle fa-size_small'></span></a></td>");
                                    } else {
                                        out.print("<td align='center'><a href='Seguimiento?opc=26&icp=" + id_capacitacion + "&icd=" + obj_capacitacion[0] + "'><span class='fa fa-times-circle fa-size_small'></span></a></td>");
                                    }

                                } else {
                                    out.print("<td align='center'><a href='#' disabled style='color: #d9d9d9;'><span class='fa fa-times-circle fa-size_small'></span></a></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</tbody>");
                            out.print("</table>");
                        } else {
                            out.print("<tr>");
                            out.print("<td>Error</td>");
                            out.print("</tr>");
                            out.print("</tbody>");
                            out.print("</table>");
                        }

                        out.print("<div style='bottom: 13px;position: absolute;right: 16px;display: flex;'>");
                        out.print("<form action='Seguimiento?opc=37&icp=" + id_capacitacion + "' method='post' id='FormEvalu'>");
                        out.print("<input type='hidden' id='selectedIds' name='selectedIds' value=''>");
                        out.print("<input type='hidden' id='validac' name='validac' value='" + idCapacDetalil + "'>");
                        out.print("<div class='' style='display: flex; position: fixed; right: 9%; bottom: 11%;'>");
                        out.print("<button class='btn-aprob' type='button' id='actionButton2' style='display: none;' onclick='validForm(1)'><i class=\"fa fa-check\"></i></button>");
                        out.print("<button class='btn-noApr' type='button' id='actionButton' style='display: none;' onclick='validForm(0)'><i class=\"fa fa-times\"></i></button>");
                        out.print("</div>");
                        out.print("</form>");

                        out.print("</fieldset>");
                        out.print("</div>");
                        //</editor-fold>

                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=22&mnu=23&fml=1'><span class='fa fa-money-check fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de capacitaciones<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_capacitaciones = jpaccpc.Consultar_capacitaciones(fechaps_incio, fechaps_fin);
                    if (lst_capacitaciones == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'CAPACITACIONES')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Folio</th>");
                        out.print("<th style='width:10%;'>Fecha</th>");
                        out.print("<th>Titulo</th>");
                        out.print("<th>Entidad</th>");
                        out.print("<th>Duración (Min)</th>");
                        out.print("<th>Capacitador</th>");
                        out.print("<th>Observaciones</th>");
//                    out.print("<th>Personal</th>");
                        out.print("<th>Añadir</th>");
                        out.print("<th>Estado</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_capacitaciones.size(); i++) {
                            Object[] obj_capacitaciones = (Object[]) lst_capacitaciones.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b>" + ((obj_capacitaciones[8].equals("")) ? "-" : obj_capacitaciones[8]) + "</b></td>");
                            out.print("<td>" + obj_capacitaciones[2] + "</td>");
                            out.print("<td>" + obj_capacitaciones[3] + "</td>");
                            out.print("<td>" + obj_capacitaciones[1] + "</td>");
                            out.print("<td>" + obj_capacitaciones[4] + "</td>");
                            out.print("<td>" + obj_capacitaciones[5] + "</td>");
                            out.print("<td>" + obj_capacitaciones[6] + "</td>");
//                        ///(select count(d.id_capacitacion_detalle) from capacitacion_detalle d where d.id_capacitacion = c.id_capacitacion limit 1)
//                        try {
//                            lst_capacitacion = jpaccpc.Consultar_capacitacion_detalle(Integer.parseInt(obj_capacitaciones[0].toString()));
//                            out.print("<td align='center'>" + lst_capacitacion.size() + "</td>");
//                        } catch (Exception e) {
//                            out.print("<td align='center'>0</td>");
//                        }
                            out.print("<td align='center' style='width:10%'>");
                            if (permisos.contains("V") || rol.equals("ADMINISTRADOR")) {
                                out.print("<a href='Seguimiento?opc=22&mnu=23&fml=3&icp=" + obj_capacitaciones[0] + "'><span class='fa " + ((Integer.parseInt(obj_capacitaciones[9].toString()) == 0) ? "fa-plus" : "fa-eye") + " fa-size_small'></span></a>");
                            }
                            out.print("</td>");
                            out.print("<td align='center' style='width:10%;'>");
                            if (Integer.parseInt(obj_capacitaciones[9].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    if (obj_capacitaciones[12].toString().equals("SI") && obj_capacitaciones[13].toString().equals("SI")) {
                                        out.print("<span onclick='DesactivarCapacitacion(" + obj_capacitaciones[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                    } else if (obj_capacitaciones[12].toString().equals("NO") && obj_capacitaciones[13].toString().equals("NO")) {
                                        out.print("<span onclick='AlertaAmbasData()' class='fa fa-unlock-alt fa-size_small'></span>");
                                    } else if (obj_capacitaciones[12].toString().equals("SI") && obj_capacitaciones[13].toString().equals("NO")) {
                                        out.print("<span onclick='AlertaEvaluacion()' class='fa fa-unlock-alt fa-size_small'></span>");
                                    } else if (obj_capacitaciones[12].toString().equals("NO") && obj_capacitaciones[13].toString().equals("SI")) {
                                        out.print("<span onclick='AlertaFirmas()' class='fa fa-unlock-alt fa-size_small'></span>");
                                    }
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=22&mnu=23&fml=2&icp=" + obj_capacitaciones[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarCapacitacion(" + obj_capacitaciones[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarCapacitacion(" + obj_capacitaciones[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
//</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Examenes")) {
                    //<editor-fold defaultstate="collapsed" desc="EXAMENES">
                    categoria_modulo = 6;
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_examen = Integer.parseInt(pageContext.getRequest().getAttribute("Id_examen").toString());
                    out.print("<div id='content_sin'>");
                    if (formulario == 1 && id_examen == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO">
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;height:85%;position: absolute;top: 2%;left:10%;overflow:scroll;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=27&mnu=25&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Nuevo Examen</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<button class='accordion'>Empleado</button>");
                        out.print("<div class='panel'>");
                        try {
                            consulta = pageContext.getSession().getAttribute("Consulta").toString();
                            if ("".equals(consulta)) {
                            } else {
                                String[] arg_consulta = consulta.replace("][", "-").replace("[", "").replace("]", "").split("-");
                                out.print("Personal :<br /><select style='width:100%' onchange='Empleado_seleccionado(this.value);'>");
                                out.print("<option>Seleccionar</option>");
                                for (int i = 0; i < arg_consulta.length; i++) {
                                    lst_persona = jpacpsn.Consultar_empleado_documento(arg_consulta[i]);
                                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                                    out.print("<option value='" + obj_persona[2] + " " + obj_persona[1] + " / " + obj_persona[0] + " / " + obj_persona[9] + " / " + obj_persona[7] + " / " + obj_persona[12] + "' onchange='Empleado_seleccionado(this.value)'>" + obj_persona[2] + " " + obj_persona[1] + "</option>");
                                }
                                out.print("</select><br />");
                            }
                        } catch (Exception e) {
                            out.print("");
                        }
                        out.print("Ingreso manual :<br />");
                        out.print("<input type='text' style='width:100%' id='Txt_manual' list='Personal' onchange='Empleado_seleccionado(this.value);' placeholder='Num. documento' />");
                        out.print("<datalist id='Personal'><label><select name='Personal'>");
                        lst_personal = jpacpsn.Consultar_empleados(1, id_area_s, consulta_personal_s);
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<option id='" + obj_personal[0] + "' data-value='" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + " / " + obj_personal[12] + "'>" + obj_personal[2] + " " + obj_personal[1] + " / " + obj_personal[0] + " / " + obj_personal[9] + " / " + obj_personal[7] + "</option>");
                        }
                        out.print("</select></label></datalist></label>");
                        out.print("</div><br />");
                        out.print("<form method='post' action='Seguimiento?opc=28'>");
                        out.print("Empleado : <b id='Label_nombre' ></b><br />"
                                + "Documento : <b id='Label_documento' ></b><br />"
                                + "Area : <b id='Label_area' ></b><br />"
                                + "Cargo : <b id='Label_cargo' ></b>"
                                + "<div style='display:none'><input type='text' id='Txt_documento' name='Txt_documento' required/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' /></div><hr />");
                        out.print("Fecha de examen :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<br />Centro medico:<br /><input type='text' name='Txt_centro_medico' id='Txt_centro_medico' style='width: 80%;' placeholder='Centro medico' onchange='javascript:this.value=this.value.toUpperCase();' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centro_medico');val1.add(Validate.Presence);</script>");
                        out.print("<br />Tipo de examen :<br /><input type='radio' name='Rdb_tipo' id='Rdb_tipo' value='Ingreso' checked /> Ingreso "
                                + "| <input type='radio' name='Rdb_tipo' id='Rdb_tipo' value='Periodico' />Periodico <br />");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        //<editor-fold defaultstate="collapsed" desc="comment">
//                        out.print("<br />Examenes :<br /><select name='Cbx_tipo' id='Cbx_tipo' style='width:80%' >");
//                        out.print("<option value='0'>Seleccionar tipo</option>");
//                        for (int i = 0; i < lst_categorias.size(); i++) {
//                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
//                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
//                                out.print("<option value='" + obj_categoria[1] + "'>" + obj_categoria[1] + "</option>");
//                            }
//                        }
//                        out.print("</select>");
//                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');");
//                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
//</editor-fold>
                        out.print("<br />Examenes :<br />");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<input type='checkbox' name='Ckb_examenes" + obj_categoria[4] + "' id='Ckb_examenes" + obj_categoria[4] + "' onclick=\"SeleccionExamenes(this)\" value='[" + obj_categoria[1] + "]' /> " + obj_categoria[1].toString().toUpperCase() + "<br />");
                            }
                        }
                        out.print("<input type='hidden' name='Txt_examenes' id='Txt_examenes' value='' />");
                        out.print("<br />Concepto:<br /><input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='Apto' checked />Apto<br />"
                                + "<input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='No apto' />No apto<br />"
                                + "<input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='Aplazado' />Aplazado<br />"
                                + "<input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='Continua' />Continua<br />");
                        out.print("<br /><input type='submit' value='Guardar Examen' /></td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/No_encontrado.png' style='width:130px;heigth:130px' /><br />");
                        out.print("<br />Recomendación:<br /><textarea name='Txt_recomendacion' id='Txt_recomendacion' style='width: 100%; height: 60px' placeholder='Descripcion de las recomendaciones' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_recomendacion');val1.add(Validate.Presence);</script>");
                        out.print("<br />Compromiso:<br /><textarea name='Txt_compromiso' id='Txt_compromiso' style='width: 100%; height: 60px' placeholder='Descripcion del compromiso' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_compromiso');val1.add(Validate.Presence);</script>");
                        out.print("<br />Restricciones:<br /><textarea name='Txt_restriciones' id='Txt_restriciones' style='width: 100%; height: 60px' placeholder='Restricciones' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_restriciones');val1.add(Validate.Presence);</script>");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 230px' placeholder='Descripcion'></textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    } else if (formulario == 2 && id_examen > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_examen = jpacexm.Consultar_examen_id(id_examen);
                        Object[] obj_examen = (Object[]) lst_examen.get(0);
                        lst_persona = jpacpsn.Consultar_empleado_documento(obj_examen[1].toString());
                        if (lst_persona == null) {
                            lst_persona = jpacpsn.Consultar_empleado_documento_old(obj_examen[1].toString());
                        }
                        Object[] obj_persona = (Object[]) lst_persona.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;height:85%;position: absolute;top: 2%;left:10%;overflow:scroll;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=27&mnu=25&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Modificar Examen</h3>");
                        out.print("<table>");
                        out.print("<tr>");
                        out.print("<td valign='top'rowspan='2' style='width:50%'>");
                        out.print("<form method='post' action='Seguimiento?opc=28&iex=" + id_examen + "'>");
                        out.print("Empleado : <b id='Label_nombre' >" + obj_persona[2] + " " + obj_persona[1] + "</b><br />"
                                + "Documento : <b id='Label_documento' >" + obj_persona[0] + "</b><br />"
                                + "Area : <b id='Label_area' >" + obj_persona[9] + "</b><br />"
                                + "Cargo : <b id='Label_cargo' >" + obj_persona[7] + "</b>"
                                + "<input type='hidden' id='Txt_documento' name='Txt_documento' value='" + obj_persona[0] + "'/><input type='hidden' id='Txt_salario_hora' name='Txt_salario_hora' value='" + obj_examen[0] + "' /><hr />");
                        out.print("Fecha de examen :<br /><input type='text' id='datepicker' name='Txt_fecha' autocomplete='off' style='width:80%' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_examen[2] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script>");
                        out.print("<br />Centro medico:<br /><input type='text' name='Txt_centro_medico' id='Txt_centro_medico' style='width: 80%;' placeholder='Centro medico' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_examen[5] + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_centro_medico');val1.add(Validate.Presence);</script>");
                        out.print("<br />Tipo de examen :<br />"
                                + "<input type='radio' name='Rdb_tipo' id='Rdb_tipo' value='Ingreso' " + ((obj_examen[3].equals("Ingreso")) ? "checked" : "") + " /> Ingreso "
                                + "| <input type='radio' name='Rdb_tipo' id='Rdb_tipo' value='Periodico' " + ((obj_examen[3].equals("Periodico")) ? "checked" : "") + " />Periodico <br />");
                        lst_categorias = jpacctg.Consultar_categorias_id_tipo(categoria_modulo);
                        out.print("<br />Examenes :<br />");
                        for (int i = 0; i < lst_categorias.size(); i++) {
                            Object[] obj_categoria = (Object[]) lst_categorias.get(i);
                            if (Integer.parseInt(obj_categoria[4].toString()) > 0) {
                                out.print("<input type='checkbox' " + ((obj_examen[13].toString().contains("[" + obj_categoria[1] + "]")) ? "checked" : "") + " name='Ckb_examenes" + obj_categoria[4] + "' id='Ckb_examenes" + obj_categoria[4] + "' onclick=\"SeleccionExamenes(this)\" value='[" + obj_categoria[1] + "]' /> " + obj_categoria[1].toString().toUpperCase() + "<br />");
                            }
                        }
                        out.print("<input type='hidden' name='Txt_examenes' id='Txt_examenes' value='" + obj_examen[13] + "' />");
                        out.print("<br />Concepto:<br /><input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='Apto' " + ((obj_examen[4].toString().equals("Apto")) ? "checked" : "") + " />Apto<br />"
                                + "<input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='No apto' " + ((obj_examen[4].toString().contains("No apto")) ? "checked" : "") + "/> No apto<br />"
                                + "<input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='Aplazado' " + ((obj_examen[4].toString().contains("Aplazado")) ? "checked" : "") + "/> Aplazado<br />"
                                + "<input type='radio' name='Rdb_concepto' id='Rdb_concepto' value='Continua' " + ((obj_examen[4].toString().contains("Continua")) ? "checked" : "") + "/> Continua<br />");
                        out.print("<br /><input type='submit' value='Modificar Examen' /></td>");
                        out.print("<td style='width:50%' valign='top'><img id='Img_foto' src='Fotos/" + obj_persona[0] + ".jpg' style='width:130px;heigth:130px' /><br />");
                        out.print("<br />Recomendación:<br /><textarea name='Txt_recomendacion' id='Txt_recomendacion' style='width: 100%; height: 60px' placeholder='Descripcion de las recomendaciones' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_examen[6] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_recomendacion');val1.add(Validate.Presence);</script>");
                        out.print("<br />Compromiso:<br /><textarea name='Txt_compromiso' id='Txt_compromiso' style='width: 100%; height: 60px' placeholder='Descripcion del compromiso' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_examen[8] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_compromiso');val1.add(Validate.Presence);</script>");
                        out.print("<br />Restricciones:<br /><textarea name='Txt_restriciones' id='Txt_restriciones' style='width: 100%; height: 60px' placeholder='Restricciones' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_examen[9] + "</textarea>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_restriciones');val1.add(Validate.Presence);</script>");
                        out.print("Observaciones : <br/>");
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 200px' placeholder='Descripcion'>" + obj_examen[7] + "</textarea>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    if (permisos.contains("I") || rol.equals("ADMINISTRADOR")) {
                        out.print("<a style='text-decoration:none' href='Seguimiento?opc=27&mnu=25&fml=1'><span class='fa fa-vials fa-size_super_small'></span></a>");
                    }
                    out.print("Listado Maestro de Examenes<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    lst_examenes = jpacexm.Consultar_examenes(fechaps_incio, fechaps_fin, id_area_s, consulta_personal_s);
                    if (lst_examenes == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
                    } else {
                        if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                            out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'EXAMENES')\" title='Generar EXCEL'></span></div>");
                        }
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th colspan='4'>Información</th>");
                        out.print("<th>Opc.</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_examenes.size(); i++) {
                            Object[] obj_examenes = (Object[]) lst_examenes.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><b class='tooltip'>" + obj_examenes[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_examenes[1] + ".jpg' style='width:200px;heigth:200px' /></span></b></td>");
                            out.print("<td valign='top' style='width:15%'><b>Fecha : </b> " + obj_examenes[2] + "<br />"
                                    + "<b>Tipo de examen: </b> " + obj_examenes[3] + "<br />"
                                    + "<b>Concepto : </b> " + obj_examenes[4] + "<br />"
                                    + "<b>Centro medico : </b> " + obj_examenes[5] + "<br /></td>");
                            out.print("<td valign='top' style='width:15%'><b>Examenes realizados : <br /></b> " + obj_examenes[13].toString().replace("][", "<br />").replace("[", "").replace("]", "") + "<br /></td>");
                            out.print("<td valign='top' style='width:25%'><b>Recomendaciones : </b> " + obj_examenes[6] + "<br />"
                                    + "<b>Observaciones : </b> " + obj_examenes[7] + "<br /></td>");
                            out.print("<td valign='top' style='width:25%'><b>Compromiso : </b> " + obj_examenes[8] + "<br />"
                                    + "<b>Restricciones : </b> " + obj_examenes[9] + "<br /></td>");
                            out.print("<td align='center' style='width:10%'>");
                            if (Integer.parseInt(obj_examenes[10].toString()) == 0) {
                                if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                    out.print("<span onclick='DesactivarExamen(" + obj_examenes[0] + ")' class='fa fa-unlock-alt fa-size_small'></span>");
                                }
                                if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<a href='Seguimiento?opc=27&mnu=25&fml=2&iex=" + obj_examenes[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                                }
                                if (permisos.contains("D") || rol.equals("ADMINISTRADOR")) {
                                    out.print("&nbsp;&nbsp;&nbsp;<span onclick='EliminarExamen(" + obj_examenes[0] + ")' class='fa fa-trash-alt fa-size_small'></span>");
                                }
                            } else if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                                out.print("<span onclick='ActivarExamen(" + obj_examenes[0] + ")' class='fa fa-lock fa-size_small'></span>");
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
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Seguimiento").equals("Marcaciones")) {
                    //<editor-fold defaultstate="collapsed" desc="MARCACIONES">
                    formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
                    id_area = Integer.parseInt(pageContext.getRequest().getAttribute("Id_area").toString());
                    id_cargo = Integer.parseInt(pageContext.getRequest().getAttribute("Id_cargo").toString());
                    tipo_consulta = Integer.parseInt(pageContext.getRequest().getAttribute("Tipo_consulta").toString());
                    dia_inicial = Integer.parseInt(pageContext.getRequest().getAttribute("Dia_inicio").toString());
                    dia_final = Integer.parseInt(pageContext.getRequest().getAttribute("Dia_fin").toString());
                    documento = pageContext.getRequest().getAttribute("Documento").toString();
                    fecha_ajuste = pageContext.getRequest().getAttribute("Fecha_ajuste").toString();
                    modulo = Integer.parseInt(pageContext.getRequest().getAttribute("Modulo").toString());
                    String arr_entD = "", arr_salD = "";
                    if (dia_inicial == 0 && dia_final == 0) {
                        dia_inicial = Integer.parseInt(fechaps_incio.split("-")[2]);
                        dia_final = Integer.parseInt(fechaps_fin.split("-")[2]);
                    }
                    String arg_dias_bd[] = {"8,9", "10,11", "12,13", "14,15", "16,17", "18,19", "20,21", "22,23", "24,25", "26,27", "28,29", "30,31", "32,33", "34,35", "36,37", "38,39", "40,41", "42,43", "44,45", "46,47", "48,49", "50,51", "52,53", "54,55", "56,57", "58,59", "60,61", "62,63", "64,65", "66,67", "68,69"};
                    if (id_area == 0) {
                        id_area = id_area_s;
                    }
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO">
                    lst_areas = jpacara.Consultar_areas();
                    if (formulario == 1) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:20%;height:auto;position: absolute;top: 20%;left:20%;'>");
                        out.print("<div style='float:right;'><a href='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=0&Cbx_area=" + id_area + "&Cbx_cargo=" + id_cargo + "&Rdb_tipo_consulta=" + tipo_consulta + "&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=" + modulo + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Consultar Marcaciones</h3>");
                        if (id_area_s == 7 || rol.equals("Administrador")) {
                            out.print("<form action='Seguimiento?opc=33&mnu=38&faj=&fml=1' method='post' id='FormSelectAreas'>");
                            out.print("<input type='hidden' name='Modulo' value='" + modulo + "'>");
                            out.print("<b>Areas : </b>");
                            out.print("<select name='Cbx_area' id='Cbx_area' onchange=\"javascript:document.getElementById('FormSelectAreas').submit();\">");
                            out.print("<option value='0'>Click para seleccionar</option>");
                            for (int i = 0; i < lst_areas.size(); i++) {
                                Object[] obj_areas = (Object[]) lst_areas.get(i);
                                out.print("<option value='" + obj_areas[0] + "' " + ((id_area == Integer.parseInt(obj_areas[0].toString())) ? "selected" : "") + " >" + obj_areas[1] + "</option>");
                            }
                            out.print("</select>");
                            out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');");
                            out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                            out.print("</form>");
                        } else {
                            for (int i = 0; i < lst_areas.size(); i++) {
                                Object[] obj_areas = (Object[]) lst_areas.get(i);
                                if (id_area_s == (Integer) obj_areas[0]) {
                                    out.print("<b>Areas : </b>" + obj_areas[1] + "");
                                    break;
                                }
                            }
                        }

                        out.print("<form action='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=0' method='post'>");
                        out.print("<input type='hidden' name='Modulo' value='" + modulo + "'>");
                        if (id_area_s == 7 || rol.equals("Administrador")) {
                            lst_cargos = jpaccgo.Consultar_cargos_area(id_area);
                            out.print("<input type='hidden' name='Cbx_area' value='" + id_area + "'>");
                        } else {
                            lst_cargos = jpaccgo.Consultar_cargos_area(id_area_s);
                            out.print("<input type='hidden' name='Cbx_area' value='" + id_area_s + "'>");
                        }
                        out.print("<b>Cargo : </b>");
                        out.print("<select name='Cbx_cargo' id='Cbx_cargo'>");
                        out.print("<option value='0'>" + ((id_area_s != 9) ? "Todos" : "Seleccionar Cargo") + "</option>");
                        for (int i = 0; i < lst_cargos.size(); i++) {
                            Object[] obj_cargo = (Object[]) lst_cargos.get(i);
                            out.print("<option value='" + obj_cargo[0] + "' " + ((id_cargo == Integer.parseInt(obj_cargo[0].toString())) ? "selected" : "") + ">" + obj_cargo[4] + " / " + obj_cargo[1] + "</option>");
                        }
                        out.print("</select>");
                        if (id_area_s == 9) {
                            out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_cargo');");
                            out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        }
                        out.print("<br />");
                        //out.print("Marcación <input type='range' name='Rdb_tipo_consulta' value='" + tipo_consulta + "'  min='0' max='1' /> Detalle <br />");
                        out.print("<input type='hidden' name='Rdb_tipo_consulta' value='0' />");
                        out.print("<input type='hidden' name='dcm' value='" + documento + "' />");
                        out.print("<br /><b>Dia Inicial : </b>");
                        if (dia_inicial == 0 && dia_final == 0) {
                            dia_inicial = Integer.parseInt(fechaps_incio.split("-")[2]);
                            dia_final = Integer.parseInt(fechaps_fin.split("-")[2]);
                        }
                        out.print("<input type='number' style='width:50px' name='Txt_dia_inicial' id='Txt_dia_inicial' onchange='ValDiasMarcacion();' min='1' max='31' value='" + dia_inicial + "' /> ");
                        out.print(" <b>Dia Fin : </b>");
                        out.print("<input type='number' style='width:50px' name='Txt_dia_final' id='Txt_dia_final' onchange='ValDiasMarcacion();' min='1' max='31' value='" + dia_final + "' />");
                        out.print("<br /><input type='submit' value='Consultar' >");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR MARCACION">
                    out.print("<div class='sweet-local' tabindex='-1' id='Modificar_marcacion' style='opacity: 1.03; display: none;'>");
                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;height:auto;position: absolute;top: 20%;left:20%;'>");
                    out.print("<div style='float:right;'><span onclick='CerrarModMarcacion()' class='fa fa-times fa-size_super_small'></span></div>");
                    out.print("<form action='Seguimiento?opc=34' method='post'>");
                    out.print("<h3>Modificar Marcación</h3>");
                    out.print("<input type='hidden' name='tcs' value='" + tipo_consulta + "' />");
                    out.print("<input type='hidden' name='iar' value='" + id_area + "' />");
                    out.print("<input type='hidden' name='icgs' value='" + id_cargo + "' />");
                    out.print("<input type='hidden' name='din' value='" + dia_inicial + "' />");
                    out.print("<input type='hidden' name='dfn' value='" + dia_final + "' />");
                    out.print("<input type='hidden' name='dcm' id='dcm' value='0' />");
                    out.print("<input type='hidden' name='icg' id='icg' value='0' />");
                    out.print("<input type='hidden' name='anio' id='anio' value='0' />");
                    out.print("<input type='hidden' name='mes' id='mes' value='0' />");
                    out.print("<input type='hidden' name='dia' id='dia' value='0' />");
                    out.print("<input type='hidden' name='Modulo' id='dia' value='" + modulo + "' />");
                    out.print("<table style='width:90%'>");
                    out.print("<tr>");
                    out.print("<td style='width:50%'>");
                    out.print("<b>Documento : </b><i id='Title_dcm'></i><br />");
                    out.print("<b>Año : </b><i id='Title_anio'></i> ");
                    out.print("<b>Mes : </b><i id='Title_mes'></i> ");
                    out.print("<b>Día : </b><i id='Title_dia'></i><br />");
                    out.print("<b>Fecha Inicio :</b><br /><input type='text' name='fin' id='start' autocomplete='off' placeholder='Fecha inicial' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('start');val1.add(Validate.Presence);</script>");
                    out.print("<br /><b>Hora Inicio :</b><br /><input type='time' name='hin' id='hin' autocomplete='off' value='' required />");
                    out.print("<br /><b>Fecha Fin :</b><br /><input type='text' name='ffn' id='end' autocomplete='off' placeholder='Fecha final' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('end');val1.add(Validate.Presence);</script>");
                    out.print("<br /><b>Hora Fin :</b><br /><input type='time' name='hfn' id='hfn' autocomplete='off' value='' required />");
                    out.print("</td>");
                    out.print("<td style='width:50%' valign='top'>");
                    out.print("<b>Observaciones :</b><br /><textarea style='width:200px;height:200px' name='obs' id='obs' placeholder='Observaciones del cambio o asignación de marcación'></textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('obs');val1.add(Validate.Presence);</script>");
                    out.print("<input type='submit' value='Modificar' >");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA">
                    out.print("<h3>");
                    String nombre_area = "";
                    for (int i = 0; i < lst_areas.size(); i++) {
                        Object[] obj_areas = (Object[]) lst_areas.get(i);
                        if (id_area == (Integer) obj_areas[0]) {
                            nombre_area = obj_areas[1].toString();
                            break;
                        }
                    }
                    out.print("<div style='display:flex;justify-content:space-between;'>");

                    if (modulo == 1) {
                        //<editor-fold defaultstate="collapsed" desc="ACCESO A MARCACIONES">
                        out.print("<div>"
                                + "<a style='text-decoration:none' href='Seguimiento?opc=33&mnu=38&faj=&fml=1&dcm=0&Cbx_area=" + id_area + "&Cbx_cargo=" + id_cargo + "&Rdb_tipo_consulta=" + tipo_consulta + "&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=1'><span class='fas fa-search fa-size_super_small'></span></a>&nbsp;&nbsp;&nbsp;");
                        out.print("<b>MARCACIONES</b> " + nombre_area + "</div>");
                        out.print("<div style='margin-bottom:9px;'>");
                        out.print("<div><a style='text-decoration:none' href='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=0&Cbx_area=" + id_area + "&Cbx_cargo=" + id_cargo + "&Rdb_tipo_consulta=" + tipo_consulta + "&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=2'><span style='color:#ff8600;' class='fas fa-fingerprint fa-size_small'></span></a></div>");
                        out.print("</div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="ACCESO A CAFE">
                        out.print("<div>"
                                + "<a style='text-decoration:none' href='Seguimiento?opc=33&mnu=38&faj=&fml=1&dcm=0&Cbx_area=" + id_area + "&Cbx_cargo=" + id_cargo + "&Rdb_tipo_consulta=" + tipo_consulta + "&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=2'><span class='fas fa-search fa-size_super_small'></span></a>&nbsp;&nbsp;&nbsp;");
                        out.print("<b>CAFÉ</b> " + nombre_area + "</div>");
                        out.print("<div style='margin-bottom:9px;'>");
                        out.print("<div><a style='text-decoration:none' href='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=0&Cbx_area=" + id_area + "&Cbx_cargo=" + id_cargo + "&Rdb_tipo_consulta=" + tipo_consulta + "&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=1'><span style='color:#000000;' class='fas fa-mug-hot fa-size_small'></span></a></div>");
                        out.print("</div>");
                        //</editor-fold>
                    }

                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-between'>");
                    out.print("<div>La  marcaciones se realiza con las fechas establecidas en la Fecha de proceso actualmente <b>" + fechaps_incio + "</b> a <b>" + fechaps_fin + "</b></div>");

                    out.print("<div style='display:flex;justify-content:space-around'><div><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>");
                    if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div ><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'MARCACIONES_" + ((documento.equals("0")) ? "" : documento) + "')\" title='Generar EXCEL'></span></div>");
                    }
                    out.print("</div>");

                    out.print("</div>");
                    try {
                        if (modulo == 1) {
                            //<editor-fold defaultstate="collapsed" desc="MARCACIONES">
                            if (tipo_consulta == 1) {
                                lst_marcaciones = jpacpsn.Historial_marcaciones(fechaps_incio, fechaps_fin, id_area, id_cargo, 0, documento);
                                for (int i = 0; i < lst_marcaciones.size(); i++) {
                                    Object[] obj_marcaciones = (Object[]) lst_marcaciones.get(i);
                                    int dia_ini = Integer.parseInt(arg_dias_bd[dia_inicial - 1].split(",")[0]);
                                    int dia_fin = Integer.parseInt(arg_dias_bd[dia_inicial - 1].split(",")[1]);
                                    int increment_dia = dia_inicial;

                                    all_ent += obj_marcaciones[70].toString();
                                    all_sal += obj_marcaciones[71].toString();

                                    for (int j = dia_inicial; j <= dia_final; j++) {
                                        //<editor-fold defaultstate="collapsed" desc="CALCULOS">
                                        if (obj_marcaciones[dia_ini] != null && obj_marcaciones[dia_fin] != null) {
                                            lst_marc_calculados = jpacpsn.Consultar_datos_calculados(obj_marcaciones[1].toString(), obj_marcaciones[2].toString(), Integer.parseInt(obj_marcaciones[6].toString()), Integer.parseInt(obj_marcaciones[7].toString()), increment_dia);
                                            if (lst_marc_calculados != null) {
                                                Object[] obj_marc_calculados = (Object[]) lst_marc_calculados.get(0);
                                                if (obj_marc_calculados[0] == null) {
                                                    jpacpsn.Calculos_marcacion(obj_marcaciones[1].toString(), obj_marcaciones[2].toString(), Integer.parseInt(obj_marcaciones[6].toString()), obj_marcaciones[7].toString(), increment_dia + "", "");
                                                }
                                            }
                                        }
                                        dia_ini = dia_ini + 2;
                                        increment_dia++;
                                        dia_fin = dia_fin + 2;
//</editor-fold>
                                    }
                                }
                                lst_marcaciones = jpacpsn.Historial_marcaciones(fechaps_incio, fechaps_fin, id_area, id_cargo, tipo_consulta, documento);
                            } else if (tipo_consulta == 2) {
                                jpacpsn.Calculos_marcacion(documento, id_cargo + "", Integer.parseInt(fecha_ajuste.split("_")[0]), fecha_ajuste.split("_")[1], fecha_ajuste.split("_")[2], "");
                                lst_marcaciones = jpacpsn.Historial_marcaciones(fechaps_incio, fechaps_fin, id_area, id_cargo, 1, documento);
                                tipo_consulta = 1;
                            } else {
                                lst_marcaciones = jpacpsn.Historial_marcaciones(fechaps_incio, fechaps_fin, id_area, id_cargo, tipo_consulta, documento);
                            }
                            //</editor-fold>    
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="CAFE">
                            if (tipo_consulta == 1) {
                                lst_marcaciones = jpacpsn.Historial_marcacionesCafe(fechaps_incio, fechaps_fin, id_area, id_cargo, 0, documento);
                                for (int i = 0; i < lst_marcaciones.size(); i++) {
                                    Object[] obj_marcaciones = (Object[]) lst_marcaciones.get(i);
                                    int dia_ini = Integer.parseInt(arg_dias_bd[dia_inicial - 1].split(",")[0]);
                                    int dia_fin = Integer.parseInt(arg_dias_bd[dia_inicial - 1].split(",")[1]);
                                    int increment_dia = dia_inicial;

                                    all_ent += obj_marcaciones[70].toString();
                                    all_sal += obj_marcaciones[71].toString();
                                    for (int j = dia_inicial; j <= dia_final; j++) {
                                        //<editor-fold defaultstate="collapsed" desc="CALCULOS">
                                        if (obj_marcaciones[dia_ini] != null && obj_marcaciones[dia_fin] != null) {
                                            lst_marc_calculados = jpacpsn.Consultar_datos_calculados_Cafe(obj_marcaciones[1].toString(), obj_marcaciones[2].toString(), Integer.parseInt(obj_marcaciones[6].toString()), Integer.parseInt(obj_marcaciones[7].toString()), increment_dia);
                                            if (lst_marc_calculados != null) {
                                                Object[] obj_marc_calculadosCafe = (Object[]) lst_marc_calculados.get(0);
                                                if (obj_marc_calculadosCafe[0] == null) {
                                                    jpacpsn.Calculos_marcacionCafe(obj_marcaciones[1].toString(), obj_marcaciones[2].toString(), Integer.parseInt(obj_marcaciones[6].toString()), obj_marcaciones[7].toString(), increment_dia + "", "");
                                                }
                                            }
                                        }
                                        dia_ini = dia_ini + 2;
                                        increment_dia++;
                                        dia_fin = dia_fin + 2;
                                        //</editor-fold>
                                    }
                                }
                                lst_marcaciones = jpacpsn.Historial_marcacionesCafe(fechaps_incio, fechaps_fin, id_area, id_cargo, tipo_consulta, documento);
                            } else if (tipo_consulta == 2) {
                                jpacpsn.Calculos_marcacion(documento, id_cargo + "", Integer.parseInt(fecha_ajuste.split("_")[0]), fecha_ajuste.split("_")[1], fecha_ajuste.split("_")[2], "");
                                lst_marcaciones = jpacpsn.Historial_marcacionesCafe(fechaps_incio, fechaps_fin, id_area, id_cargo, 1, documento);
                                tipo_consulta = 1;
                            } else {
                                lst_marcaciones = jpacpsn.Historial_marcacionesCafe(fechaps_incio, fechaps_fin, id_area, id_cargo, tipo_consulta, documento);
                            }
                            //</editor-fold>
                        }

                    } catch (Exception e) {
                        lst_marcaciones = null;
                    }
                    if (lst_marcaciones == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso o filtro ajustado.</center>");
                    } else {
                        out.print("<div class='table-container scrollbar'>");
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='" + ((tipo_consulta == 1) ? "table" : "table-zebra") + "' id='resultados'>");
                        if (tipo_consulta == 1) {
                            try {
                                Object[] obj_marcaciones = (Object[]) lst_marcaciones.get(0);
                                out.print("<tr>");
                                out.print("<td align='center' colspan='2'><b onclick=\"location.href='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=0&Cbx_area=" + id_area + "&Cbx_cargo=" + obj_marcaciones[2] + "&Rdb_tipo_consulta=0&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "'\">" + obj_marcaciones[1] + "</b></td>");
                                out.print("<td colspan='2'>" + obj_marcaciones[3].toString().split("/")[5] + "</td>");
                                out.print("<td colspan='6'>" + obj_marcaciones[3].toString().split("/")[1] + " " + obj_marcaciones[3].toString().split("/")[2] + "</td>");
                                out.print("</tr>");
                            } catch (Exception e) {
                            }
                        }
                        //marcaciones calculadas
                        if (tipo_consulta == 1) {
                            //<editor-fold defaultstate="collapsed" desc="TH DE SEGUIMIENTO">
                            out.print("<tr>");
                            out.print("<th><span class='far fa-window-restore fa-size_small'></span></th>");
                            out.print("<th>Inicio</th>");
                            out.print("<th>Fin</th>");
                            out.print("<th>Turno</th>");
                            if (modulo == 1) {
                                out.print("<th>Horas Trabajo</th>");
                                out.print("<th>Total Extras</th>");
                                out.print("<th>Extras Diurnas</th>");
                                out.print("<th>Extras Nocturnas</th>");
                                out.print("<th>Recargo Nocturno</th>");
                                out.print("<th>Dominicales</th>");
                            } else {
                                out.print("<th>Minutos Trabajo</th>");
                            }
                            out.print("<th>Observaciones</th>");
                            out.print("<th>Marcacion<br>Entrada</th>");
                            out.print("<th>Marcacion<br>Salida</th>");
                            out.print("</tr>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="TH GENERAL">
                            out.print("<thead>");
                            out.print("<tr>");
                            //maraciones detallado
                            out.print("<th>Documento</th>");
                            out.print("<th>Año / Mes</th>");
                            out.print("<th>Cargo</th>");
                            out.print("<th class='sticky'>Empleado</th>");
                            for (int i = dia_inicial; i <= dia_final; i++) {
                                out.print("<td align='center'><b>Dia " + i + "</b></td>");
                            }
                            out.print("<th>Dias trabajados</th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            //</editor-fold>
                        }
                        out.print("<tbody>");
                        int var1 = 0;
                        int var2 = 0;
                        int vaf = 0;
                        int vaf2 = 0;

                        //<editor-fold defaultstate="collapsed" desc="CONSTRUCCION MAPAS">
//                        ESTOS MAPAS SE CREAN CON EL OBJETIVO DE REALIZAR UNA CONSULTA HACIA UN DIA PARA OBTENER LA UBICACION DE LA MARCACION
//                         SIN NECESIDAD DE RECORRER EL ARREGLO DEL HISTORIAL DE UBICACIONES CADA QUE SE CONULTA UNA MARCACION
//                          TAMBIEN SE CREA UN MAPA DE IPS PARA CONUTLAR PUNTOS DE MARCACION, CON EL OBJETIVO DE EVITAR LAS CONSUTLAR REPTERITIVAS
//                        MAPA DE ENTRADAS
                        String[] arr_ent = all_ent.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        HashMap<String, String> mapEnt = new HashMap<>();
                        for (String entx : arr_ent) {
                            String[] EntDetail = entx.split("/");
                            if (EntDetail.length == 2) {
                                mapEnt.put(EntDetail[0].toLowerCase(), EntDetail[1]);
                            }
                        }

//                        MAPA SALIDAS
                        String[] arr_sal = all_sal.replace("][", "///").replace("[", "").replace("]", "").split("///");
                        HashMap<String, String> mapSal = new HashMap<>();
                        for (String salx : arr_sal) {
                            String[] SalDetail = salx.split("/");
                            if (SalDetail.length == 2) {
                                mapSal.put(SalDetail[0].toLowerCase(), SalDetail[1]);
                            }
                        }

//                        MAPA DE IPS POR PUTNO
                        List<String> ipLocat = new ArrayList<>();
                        lst_parametros = ParametrosJpa.ConsultarParametrosxCategoria("ip_marcacion");
                        if (lst_parametros != null) {
                            for (int i = 0; i < lst_parametros.size(); i++) {
                                Object[] objPram = (Object[]) lst_parametros.get(i);
                                String dataIp = objPram[2].toString() + "/" + objPram[3].toString();
                                ipLocat.add(dataIp);
                            }
                        }

                        String[] ipBase = ipLocat.toArray(new String[0]);

                        HashMap<String, String> mapip = new HashMap<>();

                        for (String ip : ipBase) {
                            String[] ipda = ip.split("/");
                            if (ipda.length == 2) {
                                mapip.put(ipda[0].toString(), ipda[1].toString());
                            }
                        }

//</editor-fold>
                        for (int i = 0; i < lst_marcaciones.size(); i++) {
                            //<editor-fold defaultstate="collapsed" desc="CONSULTA DE DATOS">
                            Object[] obj_marcaciones = (Object[]) lst_marcaciones.get(i);
                            if (tipo_consulta == 0) {
                                out.print("<tr>");
                                if (modulo == 1) {
                                    out.print("<td align='center'><b onclick=\"location.href='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=" + obj_marcaciones[1] + "&Cbx_area=" + id_area + "&Cbx_cargo=" + obj_marcaciones[2] + "&Rdb_tipo_consulta=1&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=1'\">" + obj_marcaciones[1] + "</b></td>");
                                } else {
                                    out.print("<td align='center'><b onclick=\"location.href='Seguimiento?opc=33&mnu=38&faj=&fml=0&dcm=" + obj_marcaciones[1] + "&Cbx_area=" + id_area + "&Cbx_cargo=" + obj_marcaciones[2] + "&Rdb_tipo_consulta=1&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&Modulo=2'\">" + obj_marcaciones[1] + "</b></td>");
                                }
                                out.print("<td align='center'><b class='negro'>" + obj_marcaciones[6] + " / " + obj_marcaciones[7] + "</b></td>");
                                out.print("<td>" + obj_marcaciones[3].toString().split("/")[5] + "</td>");
                                out.print("<td class='sticky'>" + obj_marcaciones[3].toString().split("/")[1] + " " + obj_marcaciones[3].toString().split("/")[2] + "</td>");
                            }
                            int dia_ini = Integer.parseInt(arg_dias_bd[dia_inicial - 1].split(",")[0]);
                            int dia_fin = Integer.parseInt(arg_dias_bd[dia_inicial - 1].split(",")[1]);
                            int dias_trabajados = 0;
                            int increment_dia = dia_inicial;
                            if (tipo_consulta == 1) {
                                for (int j = dia_inicial; j <= dia_final; j++) {
                                    if (obj_marcaciones[dia_ini] == null && obj_marcaciones[dia_fin] == null) {
                                    } else {
                                        out.print("<tr>");
                                        out.print("<td align='center'><span class='far fa-window-restore fa-size_small' onclick=\"location.href='Seguimiento?opc=33&mnu=38&fml=0&dcm=" + obj_marcaciones[1] + "&Cbx_area=" + id_area + "&Cbx_cargo=" + obj_marcaciones[2] + "&Rdb_tipo_consulta=2&Txt_dia_inicial=" + dia_inicial + "&Txt_dia_final=" + dia_final + "&faj=" + obj_marcaciones[6] + "_" + obj_marcaciones[7] + "_" + increment_dia + "'\"></span></td>");
                                        String calculos_marcacion = "";
                                        if (modulo == 1) {
                                            calculos_marcacion = obj_marcaciones[dia_ini].toString().replace("Dia_entrada:", "").replace("Entrada:", "").replace("Dia_salida:", "").replace("Salida:", "").replace("Turno:", "").replace("Horas_trabajo:", "").replace("Total_horas_extra:", "").replace("Extras_diurnas:", "").replace("Extras_nocturnas:", "").replace("Recargo_nocturno:", "").replace("Horas_dominical:", "");
                                        } else {
                                            calculos_marcacion = obj_marcaciones[dia_ini].toString().replace("Dia_entrada:", "").replace("Entrada:", "").replace("Dia_salida:", "").replace("Salida:", "").replace("Turno:", "").replace("Minutos_trabajo:", "");
                                        }
                                        String[] arg_calculos_marcacion = calculos_marcacion.split("/");
                                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                            out.print("<td align='center' onclick=\"ModMarcacion('" + obj_marcaciones[1] + "','" + obj_marcaciones[2] + "','" + obj_marcaciones[6] + "','" + obj_marcaciones[7] + "','" + j + "','" + ((arg_calculos_marcacion[1] == null) ? "NO" : arg_calculos_marcacion[1]) + "','" + ((arg_calculos_marcacion[3] == null) ? "NO" : arg_calculos_marcacion[3]) + "')\">" + arg_calculos_marcacion[0] + " " + arg_calculos_marcacion[1] + "</td>");
                                            out.print("<td align='center' onclick=\"ModMarcacion('" + obj_marcaciones[1] + "','" + obj_marcaciones[2] + "','" + obj_marcaciones[6] + "','" + obj_marcaciones[7] + "','" + j + "','" + ((arg_calculos_marcacion[1] == null) ? "NO" : arg_calculos_marcacion[1]) + "','" + ((arg_calculos_marcacion[3] == null) ? "NO" : arg_calculos_marcacion[3]) + "')\">" + arg_calculos_marcacion[2] + " " + arg_calculos_marcacion[3] + "</td>");
                                        } else {
                                            out.print("<td align='center'>" + arg_calculos_marcacion[0] + " " + arg_calculos_marcacion[1] + "</td>");
                                            out.print("<td align='center'>" + arg_calculos_marcacion[2] + " " + arg_calculos_marcacion[3] + "</td>");
                                        }
                                        out.print("<td align='center'>" + arg_calculos_marcacion[4] + "</td>");
                                        out.print("<td align='center'><b class='" + ((Float.parseFloat(arg_calculos_marcacion[5]) > 8.5) ? "naranja" : ((Float.parseFloat(arg_calculos_marcacion[5]) < 8) ? "rojo" : "verde")) + "'>" + arg_calculos_marcacion[5] + "</td>");
                                        if (modulo == 1) {
                                            out.print("<td align='center'>" + arg_calculos_marcacion[6] + "</td>");
                                            out.print("<td align='center'>" + arg_calculos_marcacion[7] + "</td>");
                                            out.print("<td align='center'>" + arg_calculos_marcacion[8] + "</td>");
                                            out.print("<td align='center'>" + arg_calculos_marcacion[9] + "</td>");
                                            out.print("<td align='center'>" + arg_calculos_marcacion[10] + "</td>");
                                        }
                                        out.print("<td style='width:250px'>" + ((obj_marcaciones[dia_fin] == null) ? "Sin Observaciones" : obj_marcaciones[dia_fin]) + "</td>");

                                        String day_ent = "";
                                        String day_Sal = "";
                                        try {
                                            String date_ent = arg_calculos_marcacion[1];
                                            String[] aar_dateEnt = date_ent.split(" ");
                                            date_ent = aar_dateEnt[0];
                                            String[] obt_dateEnt = date_ent.split("-");
                                            day_ent = obt_dateEnt[2];
                                        } catch (Exception e) {
                                            day_ent = "Sin datos";
                                        }
                                        String shrEnt = "start_" + day_ent;
                                        try {
                                            String date_sal = arg_calculos_marcacion[3];
                                            String[] aar_dateSal = date_sal.split(" ");
                                            date_sal = aar_dateSal[0];
                                            String[] obt_dateSal = date_sal.split("-");
                                            day_Sal = obt_dateSal[2];
                                        } catch (Exception e) {
                                            day_Sal = "Sin datos";
                                        }
                                        String shrSal = "end_" + day_Sal;

                                        String LocEnter = "";
                                        String LocSalid = "";

                                        //<editor-fold defaultstate="collapsed" desc="LUGAR MARCACION ENTRADA">
                                        String locationEnt = mapEnt.get(shrEnt.toLowerCase());
                                        if (locationEnt != null) {
                                            String ipEnt = mapip.get(locationEnt);
                                            if (ipEnt != null) {
                                                LocEnter = "<b style='color: green;'>" + ipEnt + "</b>";
                                            } else {
                                                LocEnter = "<span style='color: grey;' title='" + locationEnt + "'>Ubicacion desconocida</span>";
                                            }
                                        } else {
                                            LocEnter = "<b> Ubicacion no encontrada</b>";
                                        }
                                        out.print("<td> " + LocEnter + " </td>");
                                        //</editor-fold>

                                        //<editor-fold defaultstate="collapsed" desc="LUGAR MARCACION SALIDA">
                                        String LocationSal = mapSal.get(shrSal);

                                        if (LocationSal != null) {
                                            if (LocationSal.equals("NA")) {
                                                LocSalid = "<b style='color: #1584e3;'>Cierre Automatico</b>";
                                            } else {
                                                String ipSal = mapip.get(LocationSal);
                                                if (ipSal != null) {
                                                    LocSalid = "<b style='color: orange;'>" + ipSal + "</b>";
                                                } else {
                                                    LocSalid = "<span style='color: grey;' title='" + LocationSal + "'>Ubicacion desconocida</span>";
                                                }
                                            }
                                        } else {
                                            LocSalid = "<b> Ubicacion no encontrada</b>";
                                        }

                                        out.print("<td> " + LocSalid + " </td>");
                                        //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="CODE ANTES">
//                                            int DayE = Integer.parseInt(arr_ent[k].replace("Start_", "").split("/")[0]);
//                                            String UbicationE = arr_ent[k].split("/")[1];
//                                            if (arr_ent[k].contains("NA")) {
//                                                lst_parametros = jpacacd.Consultar_ipMarcaciones(UbicationE);
//                                                if (lst_parametros != null) {
//                                                    Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                    resultE = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                } else {
//                                                    if (!finalEnt.equals("")) {
//                                                        resultE = "<span style='color: orange;'>Punto no registrado</span>";
//                                                    } else {
//                                                        resultE = "<span style='color: orange;'>Punto no registrado</span>";
//                                                    }
//                                                }
//                                                resultF = "<span style='color: #009aff;'>Cerrada automaticamente</span>";
//                                            } else {
//                                                int DayF = Integer.parseInt(arr_sal[k].replace("End_", "").split("/")[0]);
//                                                String UbicationF = arr_sal[k].split("/")[1];
//                                                if (DayF != DayE) {
//                                                    DayF = DayE;
//                                                    if (DayE == DayF) {
//                                                        lst_parametros = jpacacd.Consultar_ipMarcaciones(UbicationE);
//                                                        if (lst_parametros != null) {
//                                                            Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                            resultE = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                        } else {
//                                                            if (!finalEnt.equals("")) {
//                                                                resultE = "<span style='color: orange;'>Punto no registrado</span>";
//                                                            } else {
//                                                                resultE = "<span style='color: red;'>Punto no registrado</span>";
//                                                            }
//                                                        }
//                                                        lst_parametros = jpacacd.Consultar_ipMarcaciones(UbicationF);
//                                                        if (lst_parametros != null) {
//                                                            Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                            resultF = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                        } else {
//                                                            if (!finalEnt.equals("")) {
//                                                                resultF = "<span style='color: orange;'>Punto no registrado</span>";
//                                                            } else {
//                                                                resultF = "<span style='color: red;'>Punto no registrado</span>";
//                                                            }
//                                                        }
//                                                    }
//                                                } else {
//                                                    if (DayE == DayF) {
//                                                        lst_parametros = jpacacd.Consultar_ipMarcaciones(UbicationE);
//                                                        if (lst_parametros != null) {
//                                                            Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                            resultE = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                        } else {
//                                                            if (!finalEnt.equals("")) {
//                                                                resultE = "<span style='color: orange;'>Punto no registrado</span>";
//                                                            } else {
//                                                                resultE = "<span style='color: red;'>Punto no registrado</span>";
//                                                            }
//                                                        }
//                                                        lst_parametros = jpacacd.Consultar_ipMarcaciones(UbicationF);
//                                                        if (lst_parametros != null) {
//                                                            Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                            resultF = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                        } else {
//                                                            if (!finalEnt.equals("")) {
//                                                                resultF = "<span style='color: orange;'>Punto no registrado</span>";
//                                                            } else {
//                                                                resultF = "<span style='color: red;'>Punto no registrado</span>";
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                            k = arr_ent.length;
//                                            vaf++;
//                                        }
//</editor-fold>
//                                        //<editor-fold defaultstate="collapsed" desc="UBICACION DE ENTRADA Y SALIDA">
//                                        for (int k = var1; k < arr_ent.length; k++) {
//                                            finalEnt = "Start_" + day_ent;
//                                            finalEnt = arr_ent[k];
//                                            String[] arr_entF = finalEnt.split("/");
//                                            arr_entD = arr_entF[0].replace("Start_", "");
//                                            finalEnt = arr_entF[1];
//                                            if (arr_ent[k].contains(finalEnt)) {
//                                                lst_parametros = jpacacd.Consultar_ipMarcaciones(finalEnt);
//                                                if (lst_parametros != null) {
//                                                    Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                    finalEnt = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                } else {
//                                                    if (!finalEnt.equals("")) {
//                                                        finalEnt = "<span style='color: orange;'>Punto no registrado</span>";
//                                                    } else {
//                                                        finalEnt = "<span style='color: red;'>Error</span>";
//                                                    }
//                                                }
//                                            } else {
//                                                lst_parametros = jpacacd.Consultar_ipMarcaciones(finalEnt);
//                                                if (lst_parametros != null) {
//                                                    Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                    finalEnt = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                } else {
//                                                    if (!finalEnt.equals("")) {
//                                                        finalEnt = "<span style='color: orange;'>Punto no registrado</span>";
//                                                    } else {
//                                                        finalEnt = "<span style='color: red;'>Error</span>";
//                                                    }
//                                                }
//                                            }
//                                            var1++;
//                                            k = arr_ent.length;
//                                        }
//                                        
//                                        
//                                        
//                                        
//                                        for (int k = var2; k < arr_sal.length; k++) {
//                                            finalSal = "End_" + day_Sal;
//                                            finalSal = arr_sal[k];
//                                            String[] arr_salF = finalSal.split("/");
//                                            finalSal = arr_salF[1];
//                                            arr_salD = arr_salF[0].replace("End_", "");
//                                            if (arr_salD.equals(arr_entD)) {
//                                                lst_parametros = jpacacd.Consultar_ipMarcaciones(finalSal);
//                                                if (lst_parametros != null) {
//                                                    Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                    finalSal = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                } else {
//                                                    if (!finalSal.equals("")) {
//                                                        finalSal = "<span style='color: orange;'>Punto no registrado</span>";
//                                                    } else {
//                                                        finalSal = "<span style='color: red;'>Error</span>";
//                                                    }
//                                                }
//                                                var2++;
//                                            } else if (day_Sal.contains(day_ent)) {
//                                                lst_parametros = jpacacd.Consultar_ipMarcaciones(finalSal);
//                                                if (lst_parametros != null) {
//                                                    Object[] obj_ip = (Object[]) lst_parametros.get(0);
//                                                    finalSal = "<b style='color: green;'>" + obj_ip[3].toString() + "</b>";
//                                                } else {
//                                                    if (!finalSal.equals("")) {
//                                                        finalSal = "<span style='color: orange;'>Punto no registrado</span>";
//                                                    } else {
//                                                        finalSal = "<span style='color: red;'>Error</span>";
//                                                    }
//                                                }
//                                                var2++;
//                                            } else {
//                                                finalSal = "<span style='color: orange;'>No encotrado</span>";
//                                            }
//                                            k = arr_sal.length;
//                                        }
//                                        out.print("<td align='center'>" + finalEnt + "</td>");
//                                        out.print("<td align='center'> " + finalSal + " </td>");
//
//                                        //</editor-fold>
                                        out.print("</tr>");
                                        dias_trabajados_seg++;
                                        horas_trabajo = horas_trabajo + Float.parseFloat(arg_calculos_marcacion[5]);
                                        if (modulo == 1) {
                                            total_horas_extra = total_horas_extra + Float.parseFloat(arg_calculos_marcacion[6]);
                                            horas_extra_diurnas = horas_extra_diurnas + Float.parseFloat(arg_calculos_marcacion[7]);
                                            horas_extra_nocturnas = horas_extra_nocturnas + Float.parseFloat(arg_calculos_marcacion[8]);
                                            horas_recargo_nocturno = horas_recargo_nocturno + Float.parseFloat(arg_calculos_marcacion[9]);
                                            horas_extra_dominical = horas_extra_dominical + Float.parseFloat(arg_calculos_marcacion[10]);
                                        }
                                    }
                                    increment_dia++;
                                    dia_ini = dia_ini + 2;
                                    dia_fin = dia_fin + 2;
                                }
                            } else {
                                for (int j = dia_inicial; j <= dia_final; j++) {
                                    if (obj_marcaciones[dia_ini] == null && obj_marcaciones[dia_fin] == null) {
                                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR MARCACIONES">
                                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                            out.print("<td align='center'><a onclick=\"ModMarcacion('" + obj_marcaciones[1] + "','" + obj_marcaciones[2] + "','" + obj_marcaciones[6] + "','" + obj_marcaciones[7] + "','" + j + "','NO','NO')\" href='#'><b class='rojo'>Sin marcar</b></a></td>");
                                        } else {
                                            out.print("<td align='center'><b class='rojo'>Sin marcar</b></td>");
                                        }
                                    } else {
                                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                            out.print("<td align='center'><a onclick=\"ModMarcacion('" + obj_marcaciones[1] + "','" + obj_marcaciones[2] + "','" + obj_marcaciones[6] + "','" + obj_marcaciones[7] + "','" + j + "','" + ((obj_marcaciones[dia_ini] == null) ? "NO" : obj_marcaciones[dia_ini]) + "','" + ((obj_marcaciones[dia_fin] == null) ? "NO" : obj_marcaciones[dia_fin]) + "')\" href='#'><b class='verde'>" + ((obj_marcaciones[dia_ini] == null) ? "Sin entrada" : obj_marcaciones[dia_ini]) + "</b><br /><b class='naranja'>" + ((obj_marcaciones[dia_fin] == null) ? "Sin salida" : obj_marcaciones[dia_fin]) + "</b></a></td>");
                                        } else {
                                            out.print("<td align='center'><b class='verde'>" + ((obj_marcaciones[dia_ini] == null) ? "Sin entrada" : obj_marcaciones[dia_ini]) + "</b><br /><b class='naranja'>" + ((obj_marcaciones[dia_fin] == null) ? "Sin salida" : obj_marcaciones[dia_fin]) + "</b></td>");
                                        }
                                        dias_trabajados++;
                                        //</editor-fold>
                                    }
                                    dia_ini = dia_ini + 2;
                                    increment_dia++;
                                    dia_fin = dia_fin + 2;
                                }
                                out.print("<td align='center'><b>" + dias_trabajados + "</b></td>");
                                out.print("</tr>");
                            }
                            //</editor-fold>
                        }
                        if (tipo_consulta == 1) {
                            out.print("<tr>");
                            out.print("<td align='center' colspan='4'><b>TOTALES</b></td>");
                            //<editor-fold defaultstate="collapsed" desc="TOTALES">
                            BigDecimal big_horas_trabajo = null;
                            big_horas_trabajo = new BigDecimal(horas_trabajo);
                            big_horas_trabajo = big_horas_trabajo.setScale(2, BigDecimal.ROUND_HALF_UP);
                            out.print("<td align='center'><b class='negro'>" + big_horas_trabajo.setScale(2, BigDecimal.ROUND_HALF_UP) + "</b></td>");
                            if (modulo == 1) {
                                BigDecimal big_total_horas_extra = null;
                                big_total_horas_extra = new BigDecimal(total_horas_extra);
                                big_total_horas_extra = big_total_horas_extra.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='center'><b class='negro'>" + big_total_horas_extra + "</b></td>");
                                BigDecimal big_horas_extra_diurnas = null;
                                big_horas_extra_diurnas = new BigDecimal(horas_extra_diurnas);
                                big_horas_extra_diurnas = big_horas_extra_diurnas.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='center'><b class='negro'>" + big_horas_extra_diurnas + "</b></td>");
                                BigDecimal big_horas_extra_nocturnas = null;
                                big_horas_extra_nocturnas = new BigDecimal(horas_extra_nocturnas);
                                big_horas_extra_nocturnas = big_horas_extra_nocturnas.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='center'><b class='negro'>" + big_horas_extra_nocturnas + "</b></td>");
                                BigDecimal big_horas_recargo_nocturno = null;
                                big_horas_recargo_nocturno = new BigDecimal(horas_recargo_nocturno);
                                big_horas_recargo_nocturno = big_horas_recargo_nocturno.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='center'><b class='negro'>" + big_horas_recargo_nocturno + "</b></td>");
                                BigDecimal big_horas_extra_dominical = null;
                                big_horas_extra_dominical = new BigDecimal(horas_extra_dominical);
                                big_horas_extra_dominical = big_horas_extra_dominical.setScale(2, BigDecimal.ROUND_HALF_UP);
                                out.print("<td align='center'><b class='negro'>" + big_horas_extra_dominical + "</b></td>");
                            }
                            out.print("<td align='center'><b>Dias trabajados : </b><b class='negro'>" + dias_trabajados_seg + "</b></td>");
                            out.print("</tr>");
                            //</editor-fold>
                        }
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados', " + ((tipo_consulta == 1) ? "31" : "7") + ");");

                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                    //</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_seguimiento.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
