package Tags;

import Controladores_BD.AccidenteJpaController;
import Controladores_BD.AreaJpaController;
import Controladores_BD.AusenciaJpaController;
import Controladores_BD.CapacitacionJpaController;
import Controladores_BD.CargoJpaController;
import Controladores_BD.CategoriaJpaController;
import Controladores_BD.CompetenciaJpaController;
import Controladores_BD.DisciplinaJpaController;
import Controladores_BD.DotacionJpaController;
import Controladores_BD.EnfermedadJpaController;
import Controladores_BD.EppJpaController;
import Controladores_BD.ExamenJpaController;
import Controladores_BD.IncapacidadJpaController;
import Controladores_BD.MenuJpaController;
import Controladores_BD.PersonalJpaController;
import Controladores_BD.RetiroJpaController;

import Metodos.ConnectionSignature;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_personal extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            MenuJpaController jpacmnu = new MenuJpaController();
            AreaJpaController jpacars = new AreaJpaController();
            CargoJpaController jpaccgs = new CargoJpaController();
            PersonalJpaController jpacpsn = new PersonalJpaController();
            AccidenteJpaController jpacacd = new AccidenteJpaController();
            AusenciaJpaController jpacasc = new AusenciaJpaController();
            IncapacidadJpaController jpacicp = new IncapacidadJpaController();
            EnfermedadJpaController jpacefm = new EnfermedadJpaController();
            CategoriaJpaController jpacctg = new CategoriaJpaController();
            DisciplinaJpaController jpacdcp = new DisciplinaJpaController();
            DotacionJpaController jpacdtc = new DotacionJpaController();
            CapacitacionJpaController jpaccpc = new CapacitacionJpaController();
            CompetenciaJpaController jpaccpt = new CompetenciaJpaController();
            RetiroJpaController jpacrtr = new RetiroJpaController();
            ExamenJpaController jpacexm = new ExamenJpaController();
            EppJpaController jpacepp = new EppJpaController();
            ConnectionSignature firmasJpa = new ConnectionSignature();
            //FECHA
            Calendar cal = Calendar.getInstance();
            int anio = cal.get(Calendar.YEAR);
            String mes = (cal.get(Calendar.MONTH) + 1) + "";
            String dia = "";
            if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
                dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
            } else {
                dia = cal.get(Calendar.DAY_OF_MONTH) + "";
            }
            String fecha_dia = anio + "-" + mes + "-" + dia;
            List lst_personal = null;
            List lst_firma = null;
            List lst_cargos = null;
            List lst_cargos_especiales = null;
            List lst_persona = null;
            List lst_capacitaciones = null;
            List lst_accidentes = null;
            List lst_enfermedades = null;
            List lst_disciplina = null;
            List lst_incapacidades = null;
            List lst_ausencias = null;
            List lst_dotaciones = null;
            List lst_epps = null;
            List lst_examenes = null;
            List lst_retiros = null;
            List lst_mc_calificacion_competencia = null;
            String documento = "";
            String semaforo_comp = "uno,dos,tres,cuatro";
            String titulos_comp = "REENTRENAMIENTO INMEDIATO practica supervisada y evaluación de Competencia en un tiempo no  superior a 2 meses por parte del jefe inmediato,Competente con deficiencias para realizar la labor (REENTRENAMIENTO)  y evaluación en un periodo no mayor a 2 meses por parte del jefe inmediato,Competente con recomendaciones para realizar su labor por parte del jefe inmediato.,Competente para realizar su labor";
            String grupo_sanguineo = "A+,A-,B+,B-,O-,O+,AB+,AB-";
            String nivel_educativo = "Primaria,Bachiller,Tecnico,Tecnologo,Profesional,Posgrado,Otro";
            String filtro_abc = "";
            int existencia = 0;
            int estado_reg = 0;
            int reintegro = 0;
            int anio_consulta = 0;
            int anio_inicio = 0;
            int anio_fin = 0;
            int modulo = 0;
            List lst_opciones_permisos = null;
            String permisos = "";
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            int id_area_s = Integer.parseInt(pageContext.getSession().getAttribute("Id_areaS").toString());
            int consulta_personal_s = Integer.parseInt(pageContext.getSession().getAttribute("Consulta_personalS").toString());
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            int id_opcion_menu = 0;
            if (pageContext.getRequest().getAttribute("Personal") != null) {
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
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR EMPLEADOS">
                if (pageContext.getRequest().getAttribute("Personal").equals("Consulta")) {
                    try {
                        filtro_abc = pageContext.getRequest().getAttribute("Filtro_abc").toString();
                    } catch (Exception e) {
                        filtro_abc = "A";
                    }
                    estado_reg = Integer.parseInt(pageContext.getRequest().getAttribute("Estado_reg").toString());
                    out.print("<div id='content_sin'>");
                    out.print("<h3>Listado Maestro de Personal<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    String abc = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
                    String[] arg_abc = abc.split(",");
                    out.print("<div style='color:#bbb'>");
                    for (int i = 0; i < arg_abc.length; i++) {
                        if (filtro_abc.equals(arg_abc[i])) {
                            out.print("<a href='Personal?opc=4&mnu=22&abc=" + arg_abc[i] + "&etd=" + estado_reg + "'><i> <b>" + arg_abc[i] + "</b></i></a> -");
                        } else {
                            out.print("<a href='Personal?opc=4&mnu=22&abc=" + arg_abc[i] + "&etd=" + estado_reg + "'><i> " + arg_abc[i] + "</i></a> -");
                        }
                    }
                    if (filtro_abc.equals("TODOS")) {
                        //out.print("<a href='Personal?opc=4&mnu=22&abc=TODOS&etd=" + estado_reg + "'><i><b>TODOS</b></i></a>");
                        out.print("<a style='cursor:pointer;' onclick=\"javascript:document.getElementById('Control_carga').style.display='block';javascript:location.href='Personal?opc=4&mnu=22&abc=TODOS&etd=" + estado_reg + "';\"><i><b>TODOS</b></i></a>");
                    } else {
                        out.print("<a style='cursor:pointer;' onclick=\"javascript:document.getElementById('Control_carga').style.display='block';javascript:location.href='Personal?opc=4&mnu=22&abc=TODOS&etd=" + estado_reg + "';\"><i>TODOS</i></a>");
                        //out.print("<a href='Personal?opc=4&mnu=22&abc=TODOS&etd=" + estado_reg + "'><i>TODOS</i></a>");
                    }
                    out.print("<div style='float:right;'><a href='Personal?opc=4&mnu=22&abc=A&etd=1' class='verde'><i>" + ((estado_reg == 1) ? "<b class='verde'>Activos</b>" : "Activos") + "</i></a> / "
                            + "<a href='Personal?opc=4&mnu=22&abc=A&etd=0' class='rojo'><i>" + ((estado_reg == 0) ? "<b class='rojo'>Inactivos</b>" : "Inactivos") + "</i></a></div>");
                    out.print("</div><br />");
                    if (permisos.contains("E") || rol.equals("ADMINISTRADOR")) {
                        out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'PERSONAL')\" title='Generar EXCEL'></span></div>");
                    }
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE PERSONAL">
//                    out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
//                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 25%;left:10%;'>");
//                    out.print("<div style='float:right;'><a href='Personal?opc=4&mnu=22'><span class='fa fa-times-circle fa-size_super_small'></span></a></div>");
//                    out.print("<h3>Filtro de personal</h3>");
//                    out.print("</fieldset>");
//                    out.print("</div>");
//</editor-fold>
                    if (filtro_abc.equals("TODOS")) {
                        lst_personal = jpacpsn.Consultar_empleados(estado_reg, id_area_s, consulta_personal_s);
                    } else {
                        lst_personal = jpacpsn.Consultar_empleados_abc(filtro_abc, estado_reg, id_area_s, consulta_personal_s);
                    }
                    if (lst_personal == null) {
                        out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />No se encuentran resultados</center>");
                    } else {
                        out.print("<div align='left' id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<tr>");
                        out.print("<th>Documento</th>");
                        out.print("<th>Apellidos</th>");
                        out.print("<th>Nombres</th>");
                        out.print("<th>Genero</th>");
                        out.print("<th>Cargo /Area</th>");
                        out.print("<th>Codigo</th>");
                        out.print("<th>Contrato</th>");
                        out.print("<th>Sindicalizado</th>");
                        out.print("<th>Opc</th>");
                        out.print("</tr>");
                        for (int i = 0; i < lst_personal.size(); i++) {
                            Object[] obj_personal = (Object[]) lst_personal.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'><a href='Personal?opc=5&Txt_documento=" + obj_personal[0] + "&abc=" + filtro_abc + "'><b class='tooltip'>" + obj_personal[0] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_personal[0] + ".jpg' style='width:200px;heigth:200px' /></span></b></a></td>");
//                            out.print("<td></td>");
                            out.print("<td>" + obj_personal[2] + "</td >");
                            out.print("<td>" + obj_personal[1] + "</td >");
                            out.print("<td>" + obj_personal[3] + "</td >");
                            out.print("<td>" + obj_personal[7] + " / " + obj_personal[9] + "</td >");
                            out.print("<td>" + obj_personal[5] + "</td >");
                            out.print("<td>" + ((Integer.parseInt(obj_personal[13].toString()) == 1) ? "Directo" : "Temporal") + "</td >");
                            if (permisos.contains("Z") || rol.equals("ADMINISTRADOR")) {
                                out.print("<td  align='center'>"
                                        + "             <label class='switch'>"
                                        + "             <input class='label_sc' id='Cmb" + i + "' name='" + obj_personal[0] + "' value='" + obj_personal[28] + "' onclick='CambiarValor(" + i + ");' type='checkbox' " + ((obj_personal[28].equals("SI")) ? "checked" : "") + ">"
                                        + "             <span class=\"slider\"></span>"
                                        + "             </label></td >");
                            } else {
                                out.print("<td align='center'>" + obj_personal[28] + "</td>");
                            }
                            out.print("<td align='center' style='width:10%'>");
                            if (permisos.contains("V") || rol.equals("ADMINISTRADOR")) {
                                //out.print("<a href='Personal?opc=8&mnu=22&dcm=" + obj_personal[0] + "'><span class='fa fa-eye fa-size_small'></span></a>");
                                out.print("<a onclick=\"javascript:document.getElementById('Control_carga').style.display='block';javascript:location.href='Personal?opc=8&mnu=22&dcm=" + obj_personal[0] + "';\"><span class='fa fa-eye fa-size_small'></span></a>");
                            }
                            if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                                out.print("&nbsp;&nbsp;&nbsp;<a href='Personal?opc=7&mnu=22&dcm=" + obj_personal[0] + "" + ((estado_reg == 1) ? "" : "&rit=1") + "'><span class='fa fa-" + ((estado_reg == 1) ? "pencil-alt" : "arrows-alt-h") + " fa-size_small'></span></a>");
                                if (estado_reg == 1) {
                                    lst_firma = firmasJpa.TraerFirmas(Long.parseLong(obj_personal[0].toString()), Integer.parseInt(obj_personal[5].toString()));
                                    if (lst_firma.size() > 0) {
                                        out.print("&nbsp;&nbsp;&nbsp;<a href='Personal?opc=9&mnu=22&dcm=" + obj_personal[0] + "' title='Firma registrada'><span class='fa fa-signature fa-size_small' style='color: green;'></span></a>");
                                    } else {
                                        out.print("&nbsp;&nbsp;&nbsp;<a href='Personal?opc=9&mnu=22&dcm=" + obj_personal[0] + "' title='Sin firma'><span class='fa fa-signature fa-size_small' style='color: red;'></span></a>");
                                    }
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
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR EMPLEADO">
                else if (pageContext.getRequest().getAttribute("Personal").toString().equals("Registrar")) {
                    try {
                        documento = pageContext.getRequest().getAttribute("Documento").toString();
                    } catch (Exception e) {
                        documento = "";
                    }
                    try {
                        lst_persona = jpacpsn.Consultar_empleado_documento(documento);
                        existencia = ((lst_persona == null) ? 0 : 1);
                    } catch (Exception e) {
                        existencia = 0;
                    }
                    out.print("<div id='content_sin'>");
                    //<editor-fold defaultstate="collapsed" desc="FILTRO DE PERSONAL">
                    if (existencia == 1 || documento.length() == 0 || documento == null) {
                        out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;margin-left:10px'>");
                        out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:25%;'>");
                        out.print("<div style='float:right;'><a href='Personal?opc=4&mnu=22'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                        out.print("<h3>Filtro de personal</h3>");
                        out.print("Ingresar numero de documento del empleado para verificar existencia y proceder con el registro.<br /><br />");
                        out.print("<form action='Personal?opc=1&mnu=21' method='post' name='Foto'>");
                        out.print("<b>No. documento :</b><br /><input type='text' name='Txt_documento' id='Txt_documento' min='6' placeholder='CC' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Numericality);</script>");
                        out.print("</form>");
                        out.print("</fieldset>");
                        out.print("</div>");
                    }
//</editor-fold>
                    out.print("<h3>Registrar Empleado</h3>");
                    out.print("<table class='table'>");
                    //<editor-fold defaultstate="collapsed" desc="DATOS PERSONALES">
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='29' valign='top' style='width:30%'>");

                    if (documento == null ? "" == null : documento.equals("")) {
                        out.print("<img id='Img_foto' src='Fotos/No_encontrado.png' alt='No existe la foto del empleado' style='width:300px;heigth:300px'></center>");
                    } else {
                        out.print("<img id='Img_foto' src='Fotos/" + documento.trim() + ".jpg' alt='No existe la foto del empleado' style='width:300px;heigth:300px' /></center>");
                    }

                    if (!documento.equals("")) {
                        out.print("<form action='UploadFile.jsp' method='post' enctype='multipart/form-data'>");
                        out.print("<input type='hidden' name='txtDoc' id='id_txtDoc' value=''>");
                        out.print("<input type='hidden' name='txtTypex' id='typeMov' value='Registrar'>");
                        out.print("<input type='file' name='txtFile' style='margin-top: 10px' required>");
                        out.print("<input type='submit' id='tButn' value='Subir foto'>");
                        out.print("<span id='txtMess'>No se ha ingresado documento</span>");
                        out.print("</form>");
                    } 

                    if (existencia == 1) {
                        out.print("<h3>Empleado ya existe</h3>");
                    }
                    out.print("</td>");
                    out.print("<th colspan='2'>Datos personales</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Documento :</td>");
                    out.print("<td><input type='text' value='" + documento + "' readonly='true' placeholder='CC' /><a href='Personal?opc=1&mnu=21'><span class='fa fa-plus fa-size_super_small'></span></a></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    if (existencia != 1 && documento.length() > 0) {
                        out.print("<form action='Personal?opc=2' method='post'>");
                    }
                    out.print("<input type='hidden' name='Txt_documento' id='Txt_documento' value='" + documento + "' />");
                    out.print("<td>Nombres :</td>");
                    out.print("<td><input type='text' name='Txt_nombres' id='Txt_nombres' onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Nombres'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombres');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Apellidos :</td>");
                    out.print("<td><input type='text' name='Txt_apellidos' id='Txt_apellidos' onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Apellidos'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellidos');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Sexo :</td>");
                    out.print("<td><input type='radio' name='Rdb_genero' value='F' checked='checked' />Femenino ");
                    out.print("<input type='radio' name='Rdb_genero' value='M' />Masculino</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Fecha de nacimiento :</td>");
                    out.print("<td><input type='text' name='Txt_nacimiento' id='datepicker' autocomplete='off' placeholder='Fecha de nacimiento'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS PLASTITEC">
                    out.print("<tr>");
                    out.print("<th colspan='2'>Datos Plastitec</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    lst_cargos = jpaccgs.Consultar_cargos();
                    out.print("<td>Cargo :</td>");
                    if (lst_cargos != null) {
                        out.print("<td><select  name='Cbx_cargo' id='Cbx_cargo'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_cargos.size(); i++) {
                            Object[] obj_cargos = (Object[]) lst_cargos.get(i);
                            if (Integer.parseInt(obj_cargos[5].toString()) == 1 && Integer.parseInt(obj_cargos[6].toString()) == 0) {
                                out.print("<option value='" + obj_cargos[0] + "'>" + obj_cargos[4] + " / " + obj_cargos[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_cargo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td>");
                    } else {
                        out.print("<td><font style='color:#ee1111'>Sin datos</font></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Especialidad :</td>");
                    out.print("<td>");
                    lst_cargos_especiales = jpaccpt.Consultar_cargos_especiales();
                    for (int i = 0; i < lst_cargos_especiales.size(); i++) {
                        Object[] obj_cargos_especiles = (Object[]) lst_cargos_especiales.get(i);
                        if (Integer.parseInt(obj_cargos_especiles[5].toString()) == 1) {
                            out.print("<input type='checkbox' value='[" + obj_cargos_especiles[0] + "]' onclick='SeleccionarEspecialidadPersonal(this)' /> " + obj_cargos_especiles[1] + "");
                            out.print("<br />");
                        }
                    }
                    out.print("<input type='hidden' name='Txt_especialidad' id='Txt_especialidad' /></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Fecha Ingreso :</td>");
                    out.print("<td><input type='text' name='Txt_fecha_ingreso' id='datepicker2' autocomplete='off' placeholder='Fecha de ingreso' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker2');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Codigo Firma :</td>");
                    out.print("<td><input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Codigo firma' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Numericality);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Salario :</td>");
                    out.print("<td><input type='text' name='Txt_salario' id='Txt_salario' placeholder='Salario' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_salario');val1.add(Validate.Presence);val1.add(Validate.Numericality);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Tipo de contrato:</td>");
                    out.print("<td><input type='radio' name='Rdb_contrato' value='1' />Directo");
                    out.print("<input type='radio' name='Rdb_contrato' value='0' checked='checked' />Temporal</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Fecha de contrato :</td>");
                    out.print("<td><input type='text' name='Txt_fecha_contrato' id='datepicker3' autocomplete='off' placeholder='Fecha de contrato' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker3');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Estado :</td>");
                    out.print("<td><input type='radio' name='Rdb_estado' value='1' checked='checked' />Activo");
                    out.print("<input type='radio' name='Rdb_estado' value='0' />Retirado</td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS DE CONTACTO">
                    out.print("<tr>");
                    out.print("<th colspan='2'>Datos Contacto</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Correo :</td>");
                    out.print("<td><input type='text' name='Txt_correo' id='Txt_correo' value='Ninguno' onchange=\"CompletarCampo('Txt_correo')\" placeholder='Correo'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Telefono fijo :</td>");
                    out.print("<td><input type='text' name='Txt_fijo' id='Txt_fijo' value='Ninguno' onchange=\"CompletarCampo('Txt_fijo')\" placeholder='Tel. fijo'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fijo');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td># Celular Principal:</td>");
                    out.print("<td><input type='text' name='Txt_movil' id='Txt_movil' value='Ninguno' onchange=\"CompletarCampo('Txt_movil')\" placeholder='# Movil 1'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_movil');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td># Celular alternativo:</td>");
                    out.print("<td><input type='text' name='Txt_movil2' id='Txt_movil2' value='Ninguno' onchange=\"CompletarCampo('Txt_movil2')\" placeholder='# Movil 2'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_movil2');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Nombre de Contacto Urgencias:</td>");
                    out.print("<td><input type='text' name='Txt_contacto' id='Txt_contacto' value='Ninguno' onchange=\"CompletarCampo('Txt_contacto')\" placeholder='Nombre de contacto urg.' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_contacto');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td># de Contacto Urgencias:</td>");
                    out.print("<td><input type='text' name='Txt_num_contacto' id='Txt_num_contacto' value='Ninguno' onchange=\"CompletarCampo('Txt_num_contacto')\" placeholder='# de contacto urg.'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_num_contacto');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS COMPLEMENTARIOS">
                    out.print("<tr>");
                    out.print("<th colspan='2'>Complementarios</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Numero de hijos :</td>");
                    out.print("<td><input type='text' name='Txt_hijos' id='Txt_hijos' maxlength='2' placeholder='# Hijos'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hijos');val1.add(Validate.Numericality);val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Localidad:</td>");
                    out.print("<td><input type='text' name='Txt_localidad' id='Txt_localidad' value='' required placeholder='Localidad'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_localidad');val1.add(Validate.text);val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Brigadista :</td>");
                    out.print("<td><input type='radio' name='Rdb_brigadista' value='1' />Si ");
                    out.print("<input type='radio' name='Rdb_brigadista' value='0' checked />No</td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS EXTRA">
//                    out.print("<tr>");
//                    out.print("<th colspan='2'></th>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Grupo Sanguineo :</td>");
                    String[] arg_grupo_sanguineo = grupo_sanguineo.split(",");
                    out.print("<td>");
                    for (int i = 0; i < arg_grupo_sanguineo.length; i++) {
                        out.print("<input type='radio' name='Rdb_grupo_sanguineo' value='" + arg_grupo_sanguineo[i] + "' />" + arg_grupo_sanguineo[i] + " | ");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Nivel Educativo :</td>");
                    String[] arg_nivel_educativo = nivel_educativo.split(",");
                    out.print("<td>");
                    for (int i = 0; i < arg_nivel_educativo.length; i++) {
                        out.print("<input type='radio' name='Rdb_nivel_educativo' value='" + arg_nivel_educativo[i] + "' " + ((arg_nivel_educativo[i].equals("Bachiller")) ? "checked" : "") + "/>" + arg_nivel_educativo[i] + " | ");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Restricciones :</td>");
                    out.print("<td>");
                    out.print("<textarea id='descripcion-id' name='Txt_descripcion' contenteditable='false' style='width: 100%; height: 180px' placeholder='descripcion'>"
                            + "<b contenteditable='false'>Fisica</b>"
                            + "<div contenteditable='true'><br/>*Ninguna<br/><br/><br/></div>"
                            + "<hr />"
                            + "<b contenteditable='false'>Medica</b>"
                            + "<div contenteditable='true'><br/>*Ninguna<br/><br/><br/></div>"
                            + "</textarea>");
                    out.print("</td>");
                    out.print("</tr>");
                    //</editor-fold>
                    if (existencia != 1 && documento.length() > 0) {
                        out.print("<tr>");
                        out.print("<td colspan='2'><input type='submit' value='Registrar' /></td>");
                        out.print("</tr>");
                        out.print("</form>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DETALLE EMPLEADO">
                else if (pageContext.getRequest().getAttribute("Personal").toString().equals("Detalle_empleado")) {
                    documento = pageContext.getRequest().getAttribute("Documento").toString();
                    anio_inicio = Integer.parseInt(pageContext.getRequest().getAttribute("Anio_inicio").toString());
                    anio_fin = Integer.parseInt(pageContext.getRequest().getAttribute("Anio_fin").toString());
                    modulo = Integer.parseInt(pageContext.getRequest().getAttribute("Modulo").toString());
                    if (anio_inicio == 0) {
                        anio_inicio = anio;
                        anio_fin = anio;
                    }
                    lst_persona = jpacpsn.Consultar_empleado_documento(documento);
                    if (lst_persona == null) {
                        lst_persona = jpacpsn.Consultar_empleado_documento_old(documento);
                    }
                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<br /><a href='Personal?opc=4&mnu=22&abc=" + obj_persona[2].toString().charAt(0) + "'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    if (permisos.contains("P") || rol.equals("ADMINISTRADOR")) {
                        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Imprimir_informe()' style='float:right'><span class='fa fa-print fa-size_super_small'></span></a>");
                    }
                    out.print("<form id='FormAnio' name='FormAnio' action='Personal?opc=8&mnu=22&dcm=" + documento + "' method='post' >");
                    out.print("<h3>Detalle Empleado de ");
                    out.print("<input type='hidden' id='Txt_anio_min' value='" + Integer.parseInt(obj_persona[11].toString().split("-")[0]) + "' />");
                    out.print("<input type='hidden' id='Txt_anio_max' value='" + anio + "' />");
                    out.print("<input type='number' name='Txt_anio_ini' id='Txt_anio_ini' onchange='RangoAnios()' onkeyup='RangoAnios()' style='width:80px' min='" + Integer.parseInt(obj_persona[11].toString().split("-")[0]) + "' max='" + anio + "' step='1' value='" + anio_inicio + "' />");
                    out.print(" a <input type='number' name='Txt_anio_fin' id='Txt_anio_fin' onchange='RangoAnios()' onkeyup='RangoAnios()' style='width:80px' min='" + Integer.parseInt(obj_persona[11].toString().split("-")[0]) + "' max='" + anio + "' step='1' value='" + anio_fin + "' />");
                    //out.print("<input id='Btn_consultar_anios' style='width:80px' type='submit' value='Consultar' /></form></h3>");
                    String arg_modulos[] = {"1) ACCIDENTES", "2) ENFERMEDAD PROFESIONAL", "3) INCAPACIDADES", "4) AUSENCIAS", "5) DISCIPLINA/DESCARGOS", "6) RETIROS", "7) DOTACIÓN", "8) CAPACITACIONES", "9) EXAMENES", "10) EPP", "11) COMPETENCIAS"};
                    out.print(" modulo a  consultar <select id='Cbx_modulo' name='Cbx_modulo'>");
                    out.print("<option value='0' " + ((modulo == 0) ? "selected" : "") + ">N/A</option>");
                    for (int i = 0; i < arg_modulos.length; i++) {
                        out.print("<option value='" + (i + 1) + "' " + ((modulo == (i + 1)) ? "selected" : "") + ">" + arg_modulos[i] + "</option>");
                    }
                    out.print("</select>");
                    out.print(" <span id='Btn_consultar_anios' style='visibility:visible ;width:20px;height:20px' onclick=\"javascript:document.getElementById('Control_carga').style.display='block';javascript:document.getElementById('FormAnio').submit();\" class='fa fa-sync fa-size_super_small'></span></h3></form>");
                    String arg_meses[] = {"1) ENERO", "2) FEBRERO", "3) MARZO", "4) ABRIL", "5) MAYO", "6) JUNIO", "7) JULIO", "8) AGOSTO", "9) SEPTIEMBRE", "10) OCTUBRE", "11) NOVIEMBRE", "12) DICIEMBRE"};
                    //<editor-fold defaultstate="collapsed" desc="CABECERA">
                    out.print("<div id='Imprimir_informe'>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th colspan='15'>MATRIZ DE EMPLEADO " + anio_consulta + "</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td><b>Foto</b></td>");
                    out.print("<td colspan='4'><b>Datos Personales</b></td>");
                    out.print("<td colspan='5'><b>Datos Plastitec</b></td>");
                    out.print("<td colspan='5'><b>Datos Contacto</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:8%'><img id='Img_foto' src='Fotos/" + documento.trim() + ".jpg' alt='No existe la foto del empleado' style='width:150px;heigth:150px' /></td>");
                    out.print("<td colspan='4' valign='top'>"
                            + "<b>Documento : </b>" + obj_persona[0] + "<br />"
                            + "<b>Nombre : </b>" + obj_persona[1] + "<br />"
                            + "<b>Apellidos : </b>" + obj_persona[2] + "<br />"
                            + "<b>Genero : </b>" + obj_persona[3] + "<br />"
                            + "<b>Fecha de Nacimiento : </b>" + obj_persona[4] + "<br />"
                            + "<b>Grupo Sanguineo : </b>" + obj_persona[22] + "<br />"
                            + "<b>Nivel Educativo : </b>" + obj_persona[26] + "<br />"
                            + "<b># de Hijos : </b>" + obj_persona[17] + "<br />"
                            + "</td>");
                    out.print("<td colspan='5' valign='top'>");
                    if (permisos.contains("M")) {
                        out.print("<b>Salario : </b>" + obj_persona[12] + "<br />");
                    }
                    out.print("<b>Contrato : </b>" + ((Integer.parseInt(obj_persona[13].toString()) > 0) ? "DIRECTO" : "TEMPORAL") + "<br />"
                            + "<b>Estado : </b>" + ((Integer.parseInt(obj_persona[14].toString()) > 0) ? "ACTIVO" : "RETIRADO") + "<br />"
                            + "<b>Fecha de Ingreso : </b>" + obj_persona[11] + "<br />"
                            + "<b>Fecha de Contrato : </b>" + ((obj_persona[27] == null) ? "N/A" : obj_persona[27]) + "<br />"
                            + "<b>Codigo Firma : </b>" + obj_persona[5] + "<br />"
                            + "<b>Cargo : </b>" + obj_persona[7] + "<br />"
                            + "<b>Área : </b>" + obj_persona[9] + " / " + obj_persona[10] + "<br />"
                            + "<b>Brigadista : </b>" + ((Integer.parseInt(obj_persona[18].toString()) > 0) ? "SI" : "NO") + "<br />"
                            + "</td>");
                    out.print("<td colspan='5' valign='top'>"
                            + "<b>Correo : </b>" + obj_persona[15] + "<br />"
                            + "<b>Telefonos : </b>" + obj_persona[16] + "<br />"
                            + "<b>Contacto en caso de Urgencia : </b>" + obj_persona[23] + "<br />"
                            + "<b>Localidad: </b>" + ((obj_persona[28] == null) ? "N/A" : obj_persona[28]) + "<br />"
                            + "<hr />"
                            + "<b>Restricciones Fisicas : </b>" + obj_persona[24] + "<br />"
                            + "<b>Restricciones Medicas : </b>" + obj_persona[25] + "<br />"
                            + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DETALLE AÑO">
                    if (modulo > 0) {
                        out.print("<tr>");
                        out.print("<td><b>Modulo<b></td>");
                        for (int i = 0; i < 12; i++) {
                            out.print("<td><b>" + arg_meses[i].replace(") ", "/").split("/")[1].substring(0, 3) + "</b></td>");
                        }
                        out.print("<td><b>Total</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 1) {
                        out.print("<tr>");
                        lst_accidentes = jpacacd.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_accidentes_anio = (Object[]) lst_accidentes.get(0);
                        out.print("<td><b class='negro'>" + obj_accidentes_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_accidentes_anio[i].toString()) > 0) ? obj_accidentes_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_accidentes_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 2) {
                        out.print("<tr>");
                        lst_enfermedades = jpacefm.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_enfermedad_anio = (Object[]) lst_enfermedades.get(0);
                        out.print("<td><b class='negro'>" + obj_enfermedad_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_enfermedad_anio[i].toString()) > 0) ? obj_enfermedad_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_enfermedad_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 3) {
                        out.print("<tr>");
                        lst_incapacidades = jpacicp.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_incapacidades_anio = (Object[]) lst_incapacidades.get(0);
                        out.print("<td><b class='negro'>" + obj_incapacidades_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_incapacidades_anio[i].toString()) > 0) ? obj_incapacidades_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_incapacidades_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 4) {
                        out.print("<tr>");
                        lst_ausencias = jpacasc.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_ausencias_anio = (Object[]) lst_ausencias.get(0);
                        out.print("<td><b class='negro'>" + obj_ausencias_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_ausencias_anio[i].toString()) > 0) ? obj_ausencias_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_ausencias_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 5) {
                        out.print("<tr>");
                        lst_disciplina = jpacdcp.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_disciplina_anio = (Object[]) lst_disciplina.get(0);
                        out.print("<td><b class='negro'>" + obj_disciplina_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_disciplina_anio[i].toString()) > 0) ? obj_disciplina_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_disciplina_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 6) {
                        out.print("<tr>");
                        lst_retiros = jpacrtr.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_retiros_anio = (Object[]) lst_retiros.get(0);
                        out.print("<td><b class='negro'>" + obj_retiros_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_retiros_anio[i].toString()) > 0) ? obj_retiros_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_retiros_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 7) {
                        out.print("<tr>");
                        lst_dotaciones = jpacdtc.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_dotaciones_anio = (Object[]) lst_dotaciones.get(0);
                        out.print("<td><b class='negro'>" + obj_dotaciones_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_dotaciones_anio[i].toString()) > 0) ? obj_dotaciones_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_dotaciones_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 8) {
                        out.print("<tr>");
                        lst_capacitaciones = jpaccpc.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        Object[] obj_capacitaciones_anio = (Object[]) lst_capacitaciones.get(0);
                        out.print("<td><b class='negro'>" + obj_capacitaciones_anio[0] + "</b></td>");
                        for (int i = 1; i <= 12; i++) {
                            out.print("<td align='center'>" + ((Integer.parseInt(obj_capacitaciones_anio[i].toString()) > 0) ? obj_capacitaciones_anio[i] : "-") + "</td>");
                        }
                        out.print("<td align='center'><b>" + obj_capacitaciones_anio[13] + "</b></td>");
                        out.print("</tr>");
                    }
                    if (modulo == 9) {
                        out.print("<tr>");
                        if (lst_examenes != null) {
                            lst_examenes = jpacexm.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                            Object[] obj_examenes_anio = (Object[]) lst_examenes.get(0);
                            out.print("<td><b class='negro'>" + obj_examenes_anio[0] + "</b></td>");
                            for (int i = 1; i <= 12; i++) {
                                out.print("<td align='center'>" + ((Integer.parseInt(obj_examenes_anio[i].toString()) > 0) ? obj_examenes_anio[i] : "-") + "</td>");
                            }
                            out.print("<td align='center'><b>" + obj_examenes_anio[13] + "</b></td>");
                        }
                        out.print("</tr>");
                    }
                    if (modulo == 10) {
                        out.print("<tr>");
                        lst_epps = jpacepp.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_epps != null) {
                            lst_epps = jpacepp.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                            Object[] obj_epps_anio = (Object[]) lst_epps.get(0);
                            out.print("<td><b class='negro'>" + obj_epps_anio[0] + "</b></td>");
                            for (int i = 1; i <= 12; i++) {
                                out.print("<td align='center'>" + ((Integer.parseInt(obj_epps_anio[i].toString()) > 0) ? obj_epps_anio[i] : "-") + "</td>");
                            }
                            out.print("<td align='center'><b>" + obj_epps_anio[13] + "</b></td>");
                        }
                        out.print("</tr>");
                    }
                    if (modulo == 11) {
                        out.print("<tr>");
                        if (lst_mc_calificacion_competencia != null) {
                            lst_mc_calificacion_competencia = jpaccpt.Matriz_empleado(Long.parseLong(documento), anio_inicio, anio_fin);
                            Object[] obj_competencia_anio = (Object[]) lst_mc_calificacion_competencia.get(0);
                            out.print("<td><b class='negro'>" + obj_competencia_anio[0] + "</b></td>");
                            for (int i = 1; i <= 12; i++) {
                                out.print("<td align='center'>" + ((Integer.parseInt(obj_competencia_anio[i].toString()) > 0) ? obj_competencia_anio[i] : "-") + "</td>");
                            }
                            out.print("<td align='center'><b>" + obj_competencia_anio[13] + "</b></td>");
                        }
                        out.print("</tr>");
                    }
//                    //</editor-fold>
                    out.print("</table>");
                    out.print("<div id='tab-container'>");
                    //                    //<editor-fold defaultstate="collapsed" desc="TABS">
                    if (modulo == 1) {
                        lst_accidentes = jpacacd.Consultar_accidente_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_accidentes != null) {
                            //<editor-fold defaultstate="collapsed" desc="ACCIDENTES">
                            out.print("<h3>Accidentes<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Tipo</th>");
                            out.print("<th>Parte afectada</th>");
                            out.print("<th>Agente de lesión</th>");
                            out.print("<th>Incapacidad</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_accidentes.size(); i++) {
                                Object[] obj_accidentes = (Object[]) lst_accidentes.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_accidentes[2] + "</td>");
                                out.print("<td>" + obj_accidentes[3] + "</td>");
                                out.print("<td>" + obj_accidentes[5] + "</td>");
                                out.print("<td>" + obj_accidentes[6] + "</td>");
                                out.print("<td align='center'>" + obj_accidentes[4] + "</td>");
                                out.print("<td>" + obj_accidentes[7] + "</td>");
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
                        }
                    }
                    if (modulo == 2) {
                        lst_enfermedades = jpacefm.Consultar_enfermedad_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_enfermedades != null) {
                            //<editor-fold defaultstate="collapsed" desc="ENFERMEDADES">
                            out.print("<h3>Enfermedades<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Tipo</th>");
                            out.print("<th>Diagnostico ARL</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_enfermedades.size(); i++) {
                                Object[] obj_enfermedades = (Object[]) lst_enfermedades.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_enfermedades[2] + "</td>");
                                out.print("<td>" + obj_enfermedades[3] + "</td>");
                                out.print("<td valign='top' style='width:35%'><b>Incapacidad</b>:" + obj_enfermedades[4] + "<br />" + obj_enfermedades[5] + "</td>");
                                out.print("<td valign='top' style='width:35%'>" + obj_enfermedades[6] + "</td>");
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
                        }
                    }
                    if (modulo == 3) {
                        lst_incapacidades = jpacicp.Consultar_incapacidad_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_incapacidades != null) {
                            //<editor-fold defaultstate="collapsed" desc="INCAPACIDADES">
                            out.print("<h3>Incapacidades<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Clasificación</th>");
                            out.print("<th>Tipo</th>");
                            out.print("<th>Observacion</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_incapacidades.size(); i++) {
                                Object[] obj_incapacidades = (Object[]) lst_incapacidades.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_incapacidades[2] + "</td>");
                                out.print("<td>" + obj_incapacidades[10] + "</td>");
                                out.print("<td>" + obj_incapacidades[3] + "</td>");
                                out.print("<td valign='top' style='width:60%'><b>Horas</b> :" + obj_incapacidades[4] + "<br />" + obj_incapacidades[5] + "</td>");
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
                        }
                    }
                    if (modulo == 4) {
                        lst_ausencias = jpacasc.Consultar_ausencia_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_ausencias != null) {
                            //<editor-fold defaultstate="collapsed" desc="AUSENCIAS">
                            out.print("<h3>Ausencias<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Tipo</th>");
                            out.print("<th>Observacion</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_ausencias.size(); i++) {
                                Object[] obj_ausencias = (Object[]) lst_ausencias.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_ausencias[2] + "</td>");
                                out.print("<td>" + obj_ausencias[3] + "</td>");
                                out.print("<td valign='top' style='width:60%'><b>Horas</b> :" + obj_ausencias[4] + "<br />" + obj_ausencias[5] + "</td>");
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
                        }
                    }
                    if (modulo == 5) {
                        lst_disciplina = jpacdcp.Consultar_disciplina_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_disciplina != null) {
                            //<editor-fold defaultstate="collapsed" desc="DISCIPLINA / DESCAGOS">
                            out.print("<h3>Disciplina<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Tipo</th>");
                            out.print("<th>Dias</th>");
                            out.print("<th>Motivo</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_disciplina.size(); i++) {
                                Object[] obj_disciplina = (Object[]) lst_disciplina.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_disciplina[2] + "</td>");
                                out.print("<td>" + obj_disciplina[3] + "</td>");
                                out.print("<td>" + obj_disciplina[10] + "</td>");
                                out.print("<td valign='top' style='width:35%'>" + obj_disciplina[4] + "</td>");
                                out.print("<td valign='top' style='width:35%'>" + obj_disciplina[5] + "</td>");
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
                        }
                    }
                    if (modulo == 6) {
                        lst_retiros = jpacrtr.Consultar_retiro_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_retiros != null) {
                            //<editor-fold defaultstate="collapsed" desc="RETIROS">
                            out.print("<h3>Retiros<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Area / Cargo</th>");
                            out.print("<th>Motivo</th>");
                            out.print("<th>Observación</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_retiros.size(); i++) {
                                Object[] obj_retiros = (Object[]) lst_retiros.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_retiros[2] + "</td>");
                                out.print("<td>" + obj_retiros[4] + " / " + obj_retiros[6] + "</td>");
                                out.print("<td>" + obj_retiros[7] + "</td>");
                                out.print("<td>" + obj_retiros[8] + "</td>");
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
                        }
                    }
                    if (modulo == 7) {
                        lst_dotaciones = jpacdtc.Consultar_dotacion_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_dotaciones != null) {
                            //<editor-fold defaultstate="collapsed" desc="DOTACIONES">
                            out.print("<h3>Dotaciones<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Entrega</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_dotaciones.size(); i++) {
                                Object[] obj_dotaciones = (Object[]) lst_dotaciones.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_dotaciones[2] + "</td>");
                                String[] arg_asignacion = obj_dotaciones[3].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                                out.print("<td valign='top' style='width:60%'>");
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
                                out.print("<td valign='top' style='width:25%'>" + obj_dotaciones[4] + "</td>");
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
                        }
                    }

                    if (modulo == 8) {
                        lst_capacitaciones = jpaccpc.Consultar_capacitacion_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_capacitaciones != null) {
                            //<editor-fold defaultstate="collapsed" desc="CAPACITACIONES">
                            out.print("<h3>Capacitaciones<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
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
                            out.print("</tr>");
                            for (int i = 0; i < lst_capacitaciones.size(); i++) {
                                Object[] obj_capacitaciones = (Object[]) lst_capacitaciones.get(i);
                                out.print("<tr>");
                                out.print("<td align='center'><b>" + obj_capacitaciones[8] + "</b></td>");
                                out.print("<td>" + obj_capacitaciones[2] + "</td>");
                                out.print("<td>" + obj_capacitaciones[3] + "</td>");
                                out.print("<td>" + obj_capacitaciones[1] + "</td>");
                                out.print("<td>" + obj_capacitaciones[4] + "</td>");
                                out.print("<td>" + obj_capacitaciones[5] + "</td>");
                                out.print("<td>" + obj_capacitaciones[6] + "</td>");
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
                        }
                    }
                    if (modulo == 9) {
                        lst_examenes = jpacexm.Consultar_examen_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_examenes != null) {
                            //<editor-fold defaultstate="collapsed" desc="EXAMENES">
                            out.print("<h3>Examenes<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th>Fecha de examen</th>");
                            out.print("<th colspan='4'>Información</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_examenes.size(); i++) {
                                Object[] obj_examenes = (Object[]) lst_examenes.get(i);
                                out.print("<tr>");
                                out.print("<td align='center'>" + obj_examenes[2] + "</td>");
                                out.print("<td valign='top' style='width:15%'>"
                                        + "<b>Tipo de examen: </b> " + obj_examenes[3] + "<br />"
                                        + "<b>Concepto : </b> " + obj_examenes[4] + "<br />"
                                        + "<b>Centro medico : </b> " + obj_examenes[5] + "<br /></td>");
                                out.print("<td valign='top' style='width:15%'><b>Examenes realizados : <br /></b> " + obj_examenes[13].toString().replace("][", "<br />").replace("[", "").replace("]", "") + "<br /></td>");
                                out.print("<td valign='top' style='width:25%'><b>Recomendaciones : </b> " + obj_examenes[6] + "<br />"
                                        + "<b>Observaciones : </b> " + obj_examenes[7] + "<br /></td>");
                                out.print("<td valign='top' style='width:25%'><b>Compromiso : </b> " + obj_examenes[8] + "<br />"
                                        + "<b>Restricciones : </b> " + obj_examenes[9] + "<br /></td>");
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
                        }
                    }
                    if (modulo == 10) {
                        lst_epps = jpacepp.Consultar_epp_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_epps != null) {
                            //<editor-fold defaultstate="collapsed" desc="EPP">
                            out.print("<h3>EPP<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th style='width:10%;'>Fecha</th>");
                            out.print("<th>Entrega</th>");
                            out.print("<th>Observaciones</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_epps.size(); i++) {
                                Object[] obj_epps = (Object[]) lst_epps.get(i);
                                out.print("<tr>");
                                out.print("<td>" + obj_epps[2] + "</td>");
                                String[] arg_asignacion = obj_epps[3].toString().replace("][", "-").replace("]", "").replace("[", "").split("-");
                                out.print("<td valign='top' style='width:60%'>");
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
                        }
                    }
                    if (modulo == 11) {
                        lst_mc_calificacion_competencia = jpaccpt.Consultar_calificacion_realizada_documento(Long.parseLong(documento), anio_inicio, anio_fin);
                        if (lst_mc_calificacion_competencia != null) {
                            //<editor-fold defaultstate="collapsed" desc="COMPETENCIAS">
                            out.print("<h3>Calificación de Competencias<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                            out.print("<div align='left' id='NavPosicion'></div>");
                            out.print("<table class='table' id='resultados'>");
                            out.print("<tr>");
                            out.print("<th>Estado</th>");
                            out.print("<th>Fecha</th>");
                            out.print("<th>Formato</th>");
                            out.print("<th>Personal</th>");
                            out.print("<th>Calificación y recomendaciones</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_mc_calificacion_competencia.size(); i++) {
                                Object[] obj_mc_calificaciones = (Object[]) lst_mc_calificacion_competencia.get(i);
                                int result = Integer.parseInt(obj_mc_calificaciones[17].toString());
                                out.print("<tr>");
                                out.print("<td align='center'><b class='tooltip'>" + obj_mc_calificaciones[1] + "<span class='tooltiptext' valign='top'><img id='Img_foto' src='Fotos/" + obj_mc_calificaciones[1] + ".jpg' style='width:200px;heigth:200px' /></span></b>");
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
                                out.print("<td style='width:35%' valign='top'><b class='naranja'>Valor alcanzado :</b>" + obj_mc_calificaciones[12] + "% | "
                                        + "<b class='negro'>" + obj_mc_calificaciones[16] + "</b><br />"
                                        + "" + titulos_comp.split(",")[(result - 1)]);
                                out.print("<hr /><b>Recomendaciones :</b>" + obj_mc_calificaciones[18]);
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
//</editor-fold>
                        }
                    }
////</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR EMPLEADO">
                else if (pageContext.getRequest().getAttribute("Personal").toString().equals("Modificar")) {
                    documento = pageContext.getRequest().getAttribute("Documento").toString();
                    reintegro = Integer.parseInt(pageContext.getRequest().getAttribute("Reintegro").toString());
                    if (reintegro == 0) {
                        lst_persona = jpacpsn.Consultar_empleado_documento(documento);
                    } else {
                        lst_persona = jpacpsn.Consultar_empleado_documento_old(documento);
                    }
                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<br /><a href='Personal?opc=4&mnu=22&abc=" + obj_persona[2].toString().charAt(0) + "'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                    out.print("<h3>" + ((reintegro == 0) ? "Modificar" : "Reintegro de ") + " Empleado</h3>");
                    out.print("<table class='table'>");

                    //<editor-fold defaultstate="collapsed" desc="DATOS PERSONALES">
                    out.print("<tr>");
                    out.print("<td align='center' rowspan='29' valign='top' style='width:30%'>");

                    out.print("<img id='Img_foto' src='Fotos/" + documento.trim() + ".jpg' alt='No existe la foto del empleado' style='width:300px;heigth:300px' /></center>");
                    out.print("<form action='UploadFile.jsp' method='post' enctype='multipart/form-data'>");
                    out.print("<input type='hidden' name='txtDoc' id='id_txtDoc' value=''>");
                    out.print("<input type='hidden' name='txtTypex' id='typeMov' value='Modificar'>");
                    out.print("<input type='file' name='txtFile' style='margin-top: 10px' required>");
                    out.print("<input type='submit' id='tButn' value='Subir foto'>");
                    out.print("<span id='txtMess'>No se ha ingresado documento</span>");
                    out.print("</form>");

                    out.print("</td>");
                    out.print("<th colspan='2'>Datos personales</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<form action='Personal?opc=3' method='post'>");
                    out.print("<td>Documento :</td>");
                    out.print("<td><input type='text' name='Txt_documento' id='Txt_documento' value='" + documento + "' readonly='true' /></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Nombres :</td>");
                    out.print("<td><input type='text'name='Txt_nombres' id='Txt_nombres' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_persona[1] + "' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombres');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Apellidos :</td>");
                    out.print("<td><input type='text' name='Txt_apellidos' id='Txt_apellidos' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_persona[2] + "' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellidos');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Sexo :</td>");
                    out.print("<td><input type='radio' name='Rdb_genero' value='F' " + ((obj_persona[3].toString().equals("F")) ? "checked" : "") + " />Femenino ");
                    out.print("<input type='radio' name='Rdb_genero' value='M' " + ((obj_persona[3].toString().equals("M")) ? "checked" : "") + "  />Masculino</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Fecha de nacimiento :</td>");
                    out.print("<td><input type='text' name='Txt_nacimiento' id='datepicker' autocomplete='off' value='" + obj_persona[4] + "'  />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepickers');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS PLASTITEC">
                    out.print("<tr>");
                    out.print("<th colspan='2'>Datos Plastitec</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    lst_cargos = jpaccgs.Consultar_cargos();
                    out.print("<td>Cargo :</td>");
                    if (lst_cargos != null) {
                        out.print("<td><select  name='Cbx_cargo' id='Cbx_cargo'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_cargos.size(); i++) {
                            Object[] obj_cargos = (Object[]) lst_cargos.get(i);
                            if (Integer.parseInt(obj_cargos[0].toString()) == Integer.parseInt(obj_persona[6].toString())) {
                                out.print("<option value='" + obj_cargos[0] + "' selected>" + obj_cargos[4] + " / " + obj_cargos[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_cargos[0] + "'>" + obj_cargos[4] + " / " + obj_cargos[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_cargo');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td>");
                    } else {
                        out.print("<td><font style='color:#ee1111'>Sin datos</font></td>");
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Especialidad :</td>");
                    out.print("<td>");
                    lst_cargos_especiales = jpaccpt.Consultar_cargos_especiales();
                    for (int i = 0; i < lst_cargos_especiales.size(); i++) {
                        Object[] obj_cargos_especiles = (Object[]) lst_cargos_especiales.get(i);
                        if (Integer.parseInt(obj_cargos_especiles[5].toString()) == 1) {
                            if (!obj_persona[21].toString().equals("N/A")) {
                                if (obj_persona[21].toString().contains("[" + obj_cargos_especiles[0] + "]")) {
                                    out.print("<input type='checkbox' value='[" + obj_cargos_especiles[0] + "]' onclick='SeleccionarEspecialidadPersonal(this)' checked /> " + obj_cargos_especiles[1] + "");
                                } else {
                                    out.print("<input type='checkbox' value='[" + obj_cargos_especiles[0] + "]' onclick='SeleccionarEspecialidadPersonal(this)' /> " + obj_cargos_especiles[1] + "");
                                }
                            } else {
                                out.print("<input type='checkbox' value='[" + obj_cargos_especiles[0] + "]' onclick='SeleccionarEspecialidadPersonal(this)' /> " + obj_cargos_especiles[1] + "");
                            }
                            out.print("<br />");
                        }
                    }
                    out.print("<input type='hidden' name='Txt_especialidad' id='Txt_especialidad' value='" + obj_persona[21] + "' /></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Fecha " + ((reintegro == 0) ? "Ingreso" : "de Reintegro") + " :</td>");
                    out.print("<td><input type='text' name='Txt_fecha_ingreso' id='datepicker2' autocomplete='off' value='" + ((reintegro == 0) ? obj_persona[11] : "") + "' placeholder='" + ((reintegro == 1) ? "Fecha de reintegro" : "Fecha de ingreso") + "'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker2');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Codigo Firma :</td>");
                    out.print("<td><input type='text' name='Txt_codigo' id='Txt_codigo' value='" + obj_persona[5] + "' placeholder='Codigo firma' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Numericality);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Salario :</td>");
                    out.print("<td><input type='text' name='Txt_salario' id='Txt_salario' value='" + ((reintegro == 0) ? obj_persona[12] : "") + "' placeholder='Salario' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_salario');val1.add(Validate.Presence);val1.add(Validate.Numericality);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Tipo de contrato:</td>");
                    out.print("<td><input type='radio' name='Rdb_contrato' value='1' " + ((Integer.parseInt(obj_persona[13].toString()) == 1) ? "checked" : "") + " />Directo");
                    out.print("<input type='radio' name='Rdb_contrato' value='0' " + ((Integer.parseInt(obj_persona[13].toString()) == 0) ? "checked" : "") + " />Temporal</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Fecha de contrato :</td>");
                    out.print("<td><input type='text' name='Txt_fecha_contrato' id='datepicker3' autocomplete='off' value='" + ((obj_persona[27] == null) ? "" : obj_persona[27]) + "' placeholder='Fecha de contrato' />"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('datepicker3');val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Estado :</td>");
                    if (reintegro == 0) {
                        out.print("<td><input type='radio' name='Rdb_estado' value='1' " + ((Integer.parseInt(obj_persona[14].toString()) == 1) ? "checked" : "") + " />Activo");
                        out.print("<input type='radio' name='Rdb_estado' value='0' " + ((Integer.parseInt(obj_persona[14].toString()) == 0) ? "checked" : "") + " />Retirado</td>");
                    } else {
                        out.print("<td><input type='radio' name='Rdb_estado' value='1' checked />Activo");
                        out.print("<input type='radio' name='Rdb_estado' value='0' disabled='true' />Retirado</td>");
                    }
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS DE CONTACTO">
                    out.print("<tr>");
                    out.print("<th colspan='2'>Datos Contacto</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Correo :</td>");
                    out.print("<td><input style='width:90%' type='text' name='Txt_correo' id='Txt_correo' value='" + obj_persona[15] + "'  onchange=\"CompletarCampo('Txt_correo')\" placeholder='Correo'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Telefono fijo :</td>");
                    out.print("<td><input type='text' name='Txt_fijo' id='Txt_fijo' maxlength='7' value='" + obj_persona[16].toString().split("-")[0] + "'  onchange=\"CompletarCampo('Txt_fijo')\" placeholder='Tel. fijo'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_fijo');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    try {
                        out.print("<td># Celular Principal:</td>");
                        out.print("<td><input type='text' name='Txt_movil' id='Txt_movil' maxlength='10' value='" + obj_persona[16].toString().split("-")[1] + "'  onchange=\"CompletarCampo('Txt_movil')\" placeholder='# Movil 1'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_movil');</script></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td># Celular alternativo:</td>");
                        out.print("<td><input type='text' name='Txt_movil2' id='Txt_movil2' maxlength='10' value='" + obj_persona[16].toString().split("-")[2] + "'  onchange=\"CompletarCampo('Txt_movil2')\" placeholder='# Movil 2'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_movil2');</script></td>");
                        out.print("</tr>");
                        out.print("<tr>");

                    } catch (Exception e) {
                        out.print("<td># Celular Principal:</td>");
                        out.print("<td><input type='text' name='Txt_movil' id='Txt_movil' maxlength='10' value='" + ((obj_persona[16] == null) ? 0 : "") + "'  onchange=\"CompletarCampo('Txt_movil')\" placeholder='# Movil 1'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_movil');</script></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td># Celular alternativo:</td>");
                        out.print("<td><input type='text' name='Txt_movil2' id='Txt_movil2' maxlength='10' value='" + ((obj_persona[16] == null) ? 0 : "") + "'  onchange=\"CompletarCampo('Txt_movil2')\" placeholder='# Movil 2'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_movil2');</script></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                    }
                    out.print("<td>Nombre de Contacto Urgencias:</td>");
                    out.print("<td><input type='text' name='Txt_contacto' id='Txt_contacto' value='" + ((obj_persona[23] == null) ? "Ninguno" : obj_persona[23].toString().split("-")[0]) + "' onchange=\"CompletarCampo('Txt_contacto')\" placeholder='Nombre de contacto urg.'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_contacto');</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td># de Contacto Urgencias:</td>");
                    out.print("<td><input type='text' name='Txt_num_contacto' id='Txt_num_contacto' value='" + ((obj_persona[23] == null) ? "Ninguno" : obj_persona[23].toString().split("-")[1]) + "'  onchange=\"CompletarCampo('Txt_num_contacto')\" placeholder='# de contacto urg.'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_num_contacto');</script></td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS COMPLEMENTARIOS">
                    out.print("<tr>");
                    out.print("<th colspan='2'>Complementarios</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Numero de hijos:</td>");
                    out.print("<td><input type='text' name='Txt_hijos' id='Txt_hijos' maxlength='2' value='" + obj_persona[17] + "' placeholder='# Hijos'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_hijos');val1.add(Validate.Numericality);val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Localidad:</td>");
                    out.print("<td><input type='text' name='Txt_localidad' id='Txt_localidad' value='" + ((obj_persona[28] == null) ? "" : obj_persona[28]) + "' required placeholder='Localidad'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_localidad');val1.add(Validate.text);val1.add(Validate.Presence);</script></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Brigadista :</td>");
                    out.print("<td><input type='radio' name='Rdb_brigadista' value='1' " + ((Integer.parseInt(obj_persona[18].toString()) == 1) ? "checked" : "") + " />Si ");
                    out.print("<input type='radio' name='Rdb_brigadista' value='0' " + ((Integer.parseInt(obj_persona[18].toString()) == 0) ? "checked" : "") + " />No</td>");
                    out.print("</tr>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATOS EXTRA">
//                    out.print("<tr>");
//                    out.print("<th colspan='2'></th>");
//                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Grupo Sanguineo :</td>");
                    String[] arg_grupo_sanguineo = grupo_sanguineo.split(",");
                    out.print("<td>");
                    if (obj_persona[22] == null || obj_persona[22].toString().equals("N/A") || obj_persona[22].toString().length() == 0) {
                        out.print("<input type='radio' name='Rdb_grupo_sanguineo' value='N/A' checked />N/A | ");
                    }
                    for (int i = 0; i < arg_grupo_sanguineo.length; i++) {
                        if (obj_persona[22] != null) {
                            if (obj_persona[22].toString().contains(arg_grupo_sanguineo[i])) {
                                out.print("<input type='radio' name='Rdb_grupo_sanguineo' value='" + arg_grupo_sanguineo[i] + "' checked />" + arg_grupo_sanguineo[i] + " | ");
                            } else {
                                out.print("<input type='radio' name='Rdb_grupo_sanguineo' value='" + arg_grupo_sanguineo[i] + "' required/>" + arg_grupo_sanguineo[i] + " | ");
                            }
                        } else {
                            out.print("<input type='radio' name='Rdb_grupo_sanguineo' value='" + arg_grupo_sanguineo[i] + "' required/>" + arg_grupo_sanguineo[i] + " | ");
                        }
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Nivel Educativo :</td>");
                    String[] arg_nivel_educativo = nivel_educativo.split(",");
                    out.print("<td>");
                    if (obj_persona[26] == null || obj_persona[26].toString().equals("Ninguno") || obj_persona[26].toString().length() == 0) {
                        out.print("<input type='radio' name='Rdb_nivel_educativo' value='Ninguno' checked />Ninguno | ");
                    }
                    for (int i = 0; i < arg_nivel_educativo.length; i++) {
                        if (obj_persona[26] != null) {
                            if (obj_persona[26].toString().contains(arg_nivel_educativo[i])) {
                                out.print("<input type='radio' name='Rdb_nivel_educativo' value='" + arg_nivel_educativo[i] + "' checked />" + arg_nivel_educativo[i] + " | ");
                            } else {
                                out.print("<input type='radio' name='Rdb_nivel_educativo' value='" + arg_nivel_educativo[i] + "' required />" + arg_nivel_educativo[i] + " | ");
                            }
                        } else {
                            out.print("<input type='radio' name='Rdb_nivel_educativo' value='" + arg_nivel_educativo[i] + "' required />" + arg_nivel_educativo[i] + " | ");
                        }
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>Restricciones :</td>");
                    out.print("<td>");
                    if (obj_persona[24] == null) {
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>"
                                + "<b contenteditable='false'>Fisica</b>"
                                + "<div contenteditable='true'><br/><br/><br/><br/></div>"
                                + "<hr />"
                                + "<b contenteditable='false'>Medica</b>"
                                + "<div contenteditable='true'><br/><br/><br/><br/></div>"
                                + "</textarea>");
                    } else {
                        out.print("<textarea id='descripcion-id' name='Txt_descripcion' style='width: 100%; height: 180px' placeholder='descripcion'>" + obj_persona[24] + "<br /><br /><hr />" + obj_persona[25] + "</textarea>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    //</editor-fold>
                    out.print("<tr>");
                    out.print("<td colspan='2'><input type='submit' value='Modificar' /></td>");
                    out.print("</tr>");
                    out.print("</form>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FIRMA EMPLEADO">
                else if (pageContext.getRequest().getAttribute("Personal").toString().equals("Firma_electronica")) {
                    documento = pageContext.getRequest().getAttribute("Documento").toString();
                    lst_persona = jpacpsn.Consultar_empleado_documento(documento);
                    Object[] obj_persona = (Object[]) lst_persona.get(0);
                    lst_firma = firmasJpa.TraerFirmas(Long.parseLong(documento), Integer.parseInt(obj_persona[5].toString()));
                    String firma = "";
                    if (lst_firma != null && lst_firma.size() > 0) {
                        String[] obj_firma = lst_firma.toString().replace("[", "").replace("]", "").split("---");
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
                    out.print("<div id='content_sin'>");
                    out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;margin-left:10px'>");
                    out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:30%;position: absolute;top: 25%;left:20%;'>");
                    out.print("<div style='float:right;'><a href='Personal?opc=4&mnu=22&abc=" + obj_persona[2].toString().charAt(0) + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                    out.print("<h3>Firma electronica</h3>");
                    out.print("<form action='Personal?opc=10' method='post'>");
                    out.print("<input type='hidden' name='dcm' value='" + documento + "'>");
                    out.print("<input type='hidden' name='cdg' value='" + obj_persona[5] + "'>");
                    out.print("<input type='hidden' name='abc' value='" + obj_persona[2].toString().charAt(0) + "'>");
                    out.print("<table class='table' style='width:50%'>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:30%' valign='bottom'>");
                    out.print("<img id='Img_foto' src='Fotos/" + documento.trim() + ".jpg' alt='No existe la foto del empleado' style='width:300px;heigth:300px' />");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<div class='sigPad' id='smoothed' style='width:100%;'>");
                    out.print("<ul class='sigNav' style='display: block;'>");
                    out.print("<li class='clearButton' style='display: list-item;'><a href='#clear'><span class='fa fa-eraser fa-size_super_small'></span></a></li>");
                    out.print("</ul>");
                    out.print("<div class='sig sigWrapper current' style='height: auto; display: block;'>");
                    out.print("<div class='codigo' style='display: block;" + ((!obj_persona[10].toString().equals("GC")) ? "color:#596275" : "color:#2b5797") + "'>" + obj_persona[5] + "</div>");
                    out.print("<canvas class='pad' width='440px' height='250px'></canvas>");
                    out.print("<input type='hidden' name='Txt_firma' class='output' value='' required>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print("$(document).ready(function () {");
                    out.print("$('#smoothed').signaturePad({");
                    out.print("drawOnly: true,");
                    out.print("drawBezierCurves:true,");
                    out.print("lineTop: 200,");
                    out.print("bgColour : 'transparent',");
                    out.print("penColour : '" + ((!obj_persona[10].toString().equals("GC")) ? "#596275" : "#2b5797") + "'");
                    out.print("}");
//                    out.print(")" + firma + "");
                    out.print(")" + (!(firma.equals("0")) ? firma : ""));
                    out.print("});");
                    out.print("</script>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td valign='top'>"
                            + "<b>Documento : </b>" + obj_persona[0] + "<br />"
                            + "<b>Nombre : </b>" + obj_persona[1] + "<br />"
                            + "<b>Apellidos : </b>" + obj_persona[2] + "<br />"
                            + "<b>Genero : </b>" + obj_persona[3] + "<br />"
                            + "</td>");
                    out.print("<td valign='top'>"
                            + "<input type='radio' name='Rdb_tipo_firma' value='0' checked />Nueva Firma<br />"
                            + "<input type='radio' name='Rdb_tipo_firma' value='1' />Cambiar Actual<br />"
                            + "<input type='submit' value='Firmar' />"
                            + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class=\"clear\"></div>");
                }    //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_personal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
