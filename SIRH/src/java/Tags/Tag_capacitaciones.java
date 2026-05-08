package Tags;

import Controladores_BD.CapacitacionJpaController;
import Controladores_BD.PersonalJpaController;
import Metodos.ConnectionSignature;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores_BD.ParametrosJpa;

public class Tag_capacitaciones extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {

            LocalDate DateLocal = LocalDate.now();
            CapacitacionJpaController jpaccpc = new CapacitacionJpaController();
            PersonalJpaController jpacpsn = new PersonalJpaController();
            ConnectionSignature firmasJpa = new ConnectionSignature();
            ParametrosJpa ParametroJpa = new ParametrosJpa();
            List lst_capacitacion = null;
            List lst_capacitaciones = null;
            List lst_personal = null;
            List lst_persona = null;
            List lst_firma = null;
            List lst_parametro = null;

            int formulario = 0;
            int id_capacitacion = 0;
            try {
                formulario = Integer.parseInt(pageContext.getRequest().getAttribute("Formulario").toString());
            } catch (Exception e) {
                formulario = 0;
            }
            try {
                id_capacitacion = Integer.parseInt(pageContext.getRequest().getAttribute("Id_capacitacion").toString());
            } catch (Exception e) {
                id_capacitacion = 0;
            }
            int idCapacDetalil = 0;
            try {
                idCapacDetalil = Integer.parseInt(pageContext.getRequest().getAttribute("Id_capDetall").toString());
            } catch (Exception e) {
                idCapacDetalil = 0;
            }
//            int id_area_s = Integer.parseInt(pageContext.getSession().getAttribute("Id_areaS").toString());
            int consulta_personal_s = 2;
            try {
                consulta_personal_s = Integer.parseInt(pageContext.getSession().getAttribute("Consulta_personalS").toString());

            } catch (Exception e) {
                consulta_personal_s = 2;
            }
            int doc = 0;
            int cod = 0;
            try {
                doc = Integer.parseInt(pageContext.getRequest().getAttribute("txtDocument").toString());
                cod = Integer.parseInt(pageContext.getRequest().getAttribute("txtCode").toString());
            } catch (Exception e) {
                doc = 0;
                cod = 0;
            }
            int docCapt = 0;
            int codCapt = 0;
            try {
                docCapt = Integer.parseInt(pageContext.getRequest().getAttribute("docCapt").toString());
            } catch (Exception e) {
                docCapt = 0;
            }
            try {
                codCapt = Integer.parseInt(pageContext.getRequest().getAttribute("codCapt").toString());
            } catch (Exception e) {
                codCapt = 0;
            }

            int dia = DateLocal.getDayOfMonth();
            int mes = DateLocal.getMonthValue();
            int anio = DateLocal.getYear();

            String day = String.format("%02d", dia);
            String month = String.format("%02d", mes);

//            String fechaps_incio = anio + "-" + month + "-" + 1;
//            String fechaps_fin = anio + "-" + month + "-" + day;
            String fecha_inicio = "";
            String fecha_fin = "";

            out.print("<div id='content_sin' style='overflow: auto;'>");
            if (formulario == 1 && id_capacitacion == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Control_pet' style='opacity: 1.03; display: block;'>");
                out.print("<fieldset class='popup_local' id='Fiel_informe' style='width:70%;position: absolute;top: 10%;left:15%;'>");
                out.print("<div style='float:right;'><a href='Capacitacion?opc=22&mnu=23&fml=0&docCapt=" + docCapt + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                out.print("<h3>Nueva Capacitacion</h3>");
                out.print("<form method='post' action='Capacitacion?opc=23'>");
                out.print("<table>");
                out.print("<tr>");
                out.print("<td valign='top'rowspan='2' style='width:50%'>");
                out.print("<input type='text' name='docCapt' value='" + docCapt + "'>");
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
                out.print("<div style='float:right;'><a href='Capacitacion?opc=22&mnu=23&fml=0'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                out.print("<h3>Modificar Capacitacion</h3>");
                out.print("<form method='post' action='Capacitacion?opc=23&icp=" + id_capacitacion + "'>");
                out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
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
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;# Folio: <input type='text' name='Txt_folio' id='Txt_folio' style='width:20%' placeholder='# Folio' value='" + obj_capacitacion_cabecera[8] + "'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_folio');val1.add(Validate.Presence);</script>");
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
                out.print("<div style='float:right;margin-top: 5px;margin-right: 5px;margin-bottom: 5px;'><a href='Capacitacion?opc=22&mnu=23&fml=0&docCapt=" + docCapt + "'><span class='fa fa-times fa-size_super_small'></span></a></div>");
                out.print("<div style='float:left;margin-top: 5px;margin-left: 5px;margin-bottom: 5px;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('Excel', 'DETALLE_CAPACITACION')\" title='Generar EXCEL'></span></div>");
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
                out.print("<form action='Capacitacion?opc=38&icp=" + id_capacitacion + "&temp=1' method='post'>");
                out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
                out.print("<div class='' style='display: flex; justify-content: center; margin-bottom: 15px;margin-top: 10px;'>");
                out.print("<div class='' style='width: 18%;'>");
                out.print("<b>TIPO DE ACTIVIDAD:</b>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Capacitación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Capacitacion' onclick='ActiveRadioData(2,\"Otro_one\")' " + ((TipoAct.equals("Capacitacion")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Charla</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Charla' onclick='ActiveRadioData(2,\"Otro_one\")' " + ((TipoAct.equals("Charla")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Divulgación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Divulgacion' onclick='ActiveRadioData(2,\"Otro_one\")' " + ((TipoAct.equals("Divulgacion")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_TypeAC' value='Otro' onclick='ActiveRadioData(1,\"Otro_one\")' " + ((TipoAct.contains("Otro")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='width: 100%; display: " + ((TipoAct.contains("Otro")) ? "block" : "none") + ";' id='Otro_one'><input type='text' class='' name='Otro_one' " + ((TipoAct.contains("Otro")) ? "value='" + TipoAct.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div >");
                out.print("</div>");
                out.print("<div class='' style='width: 22%;'>");
                out.print("<b>DIRIGIDO A:</b>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Colaborador(es)</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Colaborador' onclick='ActiveRadioData(2,\"Otro_two\")' " + ((Dirg.equals("Colaborador")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Proveedor(es) y/o contratista</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Proveedor' onclick='ActiveRadioData(2,\"Otro_two\")' " + ((Dirg.equals("Proveedor")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Visitante</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Visitante' onclick='ActiveRadioData(2,\"Otro_two\")' " + ((Dirg.equals("Visitante")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_Dirg' value='Otro' onclick='ActiveRadioData(1,\"Otro_two\")' " + ((Dirg.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='width: 100%; display: " + ((Dirg.contains("Otro")) ? "block" : "none") + ";' id='Otro_two'><input type='text' class='' name='Otro_two' " + ((Dirg.contains("Otro")) ? "value='" + Dirg.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div>");
                out.print("</div>");
                out.print("<div class='' style='width: 18%;'>");
                out.print("<b>ALCANCE:</b>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Individual</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Individual' onclick='ActiveRadioData(2,\"Otro_three\")' " + ((Alca.equals("Individual")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Grupal</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Grupal' onclick='ActiveRadioData(2,\"Otro_three\")' " + ((Alca.equals("Grupal")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Puesto de trabajo</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='PuestoTrabajo' onclick='ActiveRadioData(2,\"Otro_three\")' " + ((Alca.equals("PuestoTrabajo")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_alca' value='Otro' onclick='ActiveRadioData(1,\"Otro_three\")' " + ((Alca.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='width: 100%; display: " + ((Alca.contains("Otro")) ? "block" : "none") + ";' id='Otro_three'><input type='text' class='' name='Otro_three' " + ((Alca.contains("Otro")) ? "value='" + Alca.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div>");
                out.print("</div>");
                out.print("<div class='' style='width: 18%;'>");
                out.print("<b>METODOLOGÍA:</b>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Explicación</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Explicacion' onclick='ActiveRadioData(2,\"Otro_four\")' " + ((Metodo.equals("Explicacion")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Práctica</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Practica' onclick='ActiveRadioData(2,\"Otro_four\")' " + ((Metodo.equals("Practica")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Juego de roles, Lúdica</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='JuegoRoles' onclick='ActiveRadioData(2,\"Otro_four\")' " + ((Metodo.equals("JuegoRoles")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Otro, ¿Cual?</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_metod' value='Otro' onclick='ActiveRadioData(1,\"Otro_four\")' " + ((Metodo.contains("Otro")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
                out.print("<div class='' style='width: 100%; display: " + ((Metodo.contains("Otro")) ? "block" : "none") + ";' id='Otro_four'><input type='text' class='' name='Otro_four' " + ((Metodo.contains("Otro")) ? "value='" + Metodo.split("/")[1] + "'" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div>");
                out.print("</div>");
                out.print("<div class='' style='width: 18%;'>");
                out.print("<b>EVALUACIÓN DE EFICACIA:</b>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Escrita</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='Escrita' onclick='ActiveRadioData(2,\"Otro_five\")' " + ((Evalu.equals("Escrita")) ? "checked" : "") + " " + ((estad == 1) ? "disabled" : "") + " ></div></div>");
                out.print("<div class='' style='display: flex;width: 100%;'><div style='width: 80%;'><span>Oral</span></div><div style='width: 20%;'><input type='radio' class='' name='Txt_eva' value='Oral' onclick='ActiveRadioData(2,\"Otro_five\")' " + ((Evalu.equals("Oral")) ? "checked" : "") + "  " + ((estad == 1) ? "disabled" : "") + "></div></div>");
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
                out.print("<form action='Capacitacion?opc=24&icp=" + id_capacitacion + "&txtResponsable=" + obj_capacitacion_cabecera[5] + "' method='post' class=''>");
                out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
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
                    out.print("<form action='Capacitacion?opc=24&icp=" + id_capacitacion + "' method='post'>");
                    out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
                    out.print("<a style='font-size: 22px; color: #df3e3e; cursor: pointer;' onclick='activeShield(2,\"contReg\")' title='Cancelar'><i class='fas fa-times-circle'></i></a>&nbsp;<input type='text' class='form-control' style='width: 290px;' name='Txt_manual' id='Txt_manual' placeholder='Datos del empleado' list='Personal'>&nbsp;");
                    out.print("<datalist id='Personal'><label><select name='Personal'>");
                    lst_personal = jpacpsn.Consultar_empleados(1, 7, consulta_personal_s);
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
                    out.print("<input type='text' class='form-control' name='' id='Txt_filtro' onkeyup='Filtrar()' placeholder='Buscar...'>");
                    out.print("</div>");

                    out.print("<div class='display: flex; position: fixed; right: 9%; bottom: 11%;'>");
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
                out.print("<a href='Capacitacion?opc=22&mnu=23&fml=3&icp=" + id_capacitacion + "&docCapt=" + docCapt + "' style='height: 30px;padding: 3px;width: 30px;' placeholder='Numero'><i class='fas fa-times'></i></a>");
                out.print("</div>");

                out.print("<div class='' style='display: flex;'>");
                out.print("<div class='' style='text-align: center;border-right: 1px solid #9d9c9c;padding-right: 10px;margin-right: 10px;width: 30%;'>");

                out.print("<form action='Capacitacion?opc=35&icp=" + id_capacitacion + "' method='post' id='formUsers'>");
                out.print("<h1>Consultar</h1>");
                out.print("<div class='' style=''>");
                if (doc == 0 && cod == 0) {
                    out.print("<input type='hidden' class='form-control' name='idCapDetalle' id='Id_valId'>");
                    out.print("<input type='hidden' class='form-control' name='' id='Id_valdDoc'>");
                    out.print("<input type='hidden' class='form-control' name='' id='Id_valdCod'>");
                    out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
                } else {
                    out.print("<input type='hidden' class='form-control' name='idCapDetalle' value='" + idCapacDetalil + "'>");
                    out.print("<input type='hidden' class='form-control' name='' id='Id_valdDoc' value='" + doc + "'>");
                    out.print("<input type='hidden' class='form-control' name='' id='Id_valdCod' value='" + cod + "'>");
                    out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
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

                    out.print("<form action='Personal?opc=10&icp=" + id_capacitacion + "&idCapDetalle=" + idCapacDetalil + "&event=0' method='post' name='formPersonal' id='formPersonal'>");
                    out.print("<input type='hidden' name='dcm' value='" + doc + "'>");
                    out.print("<input type='hidden' name='cdg' value='" + cod + "'>");
                    out.print("<input type='hidden' name='abc' value='" + init + "'>");
                    out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
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
//                    out.print(")" + firma + "");
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
                        out.print("<form action='Capacitacion?opc=36&icp=" + id_capacitacion + "' method='post' name='formSave'>");
                        out.print("<input type='hidden' class='form-control' name='idCapDetalle' value='" + idCapacDetalil + "' >");
                        out.print("<input type='hidden' class='form-control' name='idSignature' id='' value='" + idFirma + "' >");
                        out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
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
                out.print("<table class='table table-bordered' id='resultados'>");
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
                        out.print("<td style='text-align:center;'>" + ((signa > 0) ? "<b style='color: green;'>Firmado</b>" : "<a href='#' onclick='mostrarConvencion(1);PassData(" + obj_capacitacion[0] + "," + obj_capacitacion[9] + "," + obj_capacitacion[2] + ",\"Id_valdDoc\", " + docCapt + ");'><b style='color: red;'>Sin firma</b></a>") + "</td>");

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
                                out.print("<td align='center'><a href='Capacitacion?opc=26&icp=" + id_capacitacion + "&icd=" + obj_capacitacion[0] + "&docCapt=" + docCapt + "'><span class='fa fa-times-circle fa-size_small'></span></a></td>");
                            }

                        } else {
                            out.print("<td align='center'><a href='#' disabled style='color: #d9d9d9;'><span class='fa fa-times-circle fa-size_small'></span></a></td>");
                        }
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
//                out.print("<script type='text/javascript'>");
//                out.print("var pager = new Pager('resultados', " + lst_capacitacion.size() + ");");
//                out.print("pager.init();");
//                out.print("pager.showPageNav('pager','NavPosicion');");
//                out.print("pager.showPage(1);");
//                out.print("</script>");

                out.print("<div style='bottom: 13px;position: absolute;right: 16px;display: flex;'>");
                out.print("<form action='Capacitacion?opc=37&icp=" + id_capacitacion + "' method='post' id='FormEvalu'>");
                out.print("<input type='hidden' name='docCapt' value='" + docCapt + "'>");
                out.print("<input type='hidden' id='selectedIds' name='selectedIds' value=''>");
                out.print("<input type='hidden' id='validac' name='validac' value='" + idCapacDetalil + "'>");
                out.print("<div class='' style='display: flex;'>");
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
            out.print("<div style='display:flex; justify-content: space-between;'>");
            out.print("<a style='text-decoration:none' href='index.jsp'><span class='fa fa-arrow-left fa-size_super_small'></span>&nbsp; Volver al inicio</a>");
//            out.print("<a style='text-decoration:none' href='Capacitacion?opc=5&mnu=23&fpi=" + (anio + "-" + mes + "-01") + "&fpf=" + (anio + "-" + mes + "-" + dia) + "&fml=4&icp=0'>Fecha de proceso&nbsp; <span class='fa fa-calendar'></span></a>");
            out.print("</div>");
            out.print("<h3>");
            out.print("<a style='text-decoration:none' href='Capacitacion?opc=22&mnu=23&fml=1&docCapt=" + docCapt + "'><span class='fa fa-money-check fa-size_super_small'></span></a>");
            out.print("&nbsp;Listado Maestro de capacitaciones<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
            if (fecha_inicio.equals("")) {
                String f_fn = anio + "-" + mes + "-" + dia;
                lst_parametro = ParametroJpa.ConsultarParametrosxCategoria("FechaConsulta");
                Object[] ObjDat = (Object[]) lst_parametro.get(0);
                fecha_inicio = ObjDat[2].toString();
                lst_capacitaciones = jpaccpc.Consultar_capacitaciones_v2(fecha_inicio, f_fn, docCapt + "");
            } else {
                lst_capacitaciones = jpaccpc.Consultar_capacitaciones_v2(fecha_inicio, fecha_fin, docCapt + "");
            }
            if (lst_capacitaciones == null) {
                out.print("<center><img src='Interfaz/MasterPage/images/No_data.png' style='width:394px;height:257px' /><br />Sin datos en el mes de proceso ajustado.</center>");
            } else {
//                    out.print("<div style='float:right;'><span class='far fa-file-excel fa-size_super_small' onclick=\"tableToExcel('resultados', 'CAPACITACIONES')\" title='Generar EXCEL'></span></div>");
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th>Folio</th>");
                out.print("<th style='width:10%;'>Fecha</th>");
                out.print("<th>Titulo</th>");
                out.print("<th>Entidad</th>");
                out.print("<th>Duración (Min)</th>");
                out.print("<th>Capacitador</th>");
//                out.print("<th>Observaciones</th>");
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
//                    out.print("<td>" + obj_capacitaciones[6] + "</td>");
//                        ///(select count(d.id_capacitacion_detalle) from capacitacion_detalle d where d.id_capacitacion = c.id_capacitacion limit 1)
//                        try {
//                            lst_capacitacion = jpaccpc.Consultar_capacitacion_detalle(Integer.parseInt(obj_capacitaciones[0].toString()));
//                            out.print("<td align='center'>" + lst_capacitacion.size() + "</td>");
//                        } catch (Exception e) {
//                            out.print("<td align='center'>0</td>");
//                        }
                    out.print("<td align='center' style='width:10%'>");
                    out.print("<a href='Capacitacion?opc=22&mnu=23&fml=3&icp=" + obj_capacitaciones[0] + "&docCapt=" + docCapt + "'><span class='fa " + ((Integer.parseInt(obj_capacitaciones[9].toString()) == 0) ? "fa-plus" : "fa-eye") + " fa-size_small'></span></a>");
                    out.print("</td>");
                    out.print("<td align='center' style='width:10%;'>");
                    if (Integer.parseInt(obj_capacitaciones[9].toString()) == 0) {
//                        out.print("<span class='fa fa-unlock-alt fa-size_small' style='opacity: 0.8;' title='No tiene permisos'></span>");
                        out.print("&nbsp;&nbsp;&nbsp;<a href='Capacitacion?opc=22&mnu=23&fml=2&icp=" + obj_capacitaciones[0] + "&docCapt=" + docCapt + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                    } else {
                        out.print("<span class='fa fa-lock fa-size_small' style='opacity: 0.8;' title='No tiene permisos'></span>");

                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
                out.print("</table>");

            }
//</editor-fold>
            out.print("</div>");
            out.print("<div class=\"clear\"></div>");
        } catch (Exception e) {
        }

        return super.doStartTag();
    }

}
