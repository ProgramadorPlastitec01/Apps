package Tags;

import Controladoras.AreaJpaController;
import Controladoras.CasoJpaController;
import Controladoras.EquipoJpaController;
import Controladoras.HorometrosJpaController;
import Controladoras.HvEquipoJpaController;
import Controladoras.RegistroJpaController;
import Controladoras.AplicativoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_equipos extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        HorometrosJpaController jpa_horometro = new HorometrosJpaController();
        RegistroJpaController jpa_registro = new RegistroJpaController();
        HvEquipoJpaController jpa_HojaV = new HvEquipoJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        AplicativoJpaController jpa_aplicativo = new AplicativoJpaController();
        String filtro = pageContext.getRequest().getAttribute("filtro").toString();
        List lst_equipos = null;
        List lst_equipo = null;
        List lst_movimientos = null;
        List lst_actividades = null;
        List lst_horometro = null;
        List lst_casos = null;
        List lst_movimiento = null;
        List lst_aplicativo = null;
        List lst_aplicativo_id = null;
        List lst_protocolo = null;
        String modulo = pageContext.getRequest().getAttribute("Equipo").toString();
        int id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("id_equipo").toString());
        int hojaV = Integer.parseInt(pageContext.getRequest().getAttribute("hojaV").toString());
        int id_h_equipos = Integer.parseInt(pageContext.getRequest().getAttribute("id_hv_equipo").toString());
        List lst_area = jpa_area.consultarAreas();
        try {
            if (modulo.equals("Epo")) {
                //<editor-fold defaultstate="collapsed" desc="modulo equipos">
                out.print("<div style='float:right;'>");
                out.print("<form action='Equipo?opc=1&mod=Epo' name='formA' method='post'>");
                out.print("<div style='display:flex;'>");
                out.print("<div style='margin:7px;'><a href='#' data-toggle=\"modal\" data-target=\"#Registrar\"><i class='fa fa-plus fa-lg' style='color:#292929'></i></a></div>");
                out.print("<div><input type='text' id='Txt_filtro' class='form-control' name='txt_bus' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("<h3>Equipo</h3>");
                if (id_equipo == 0) {
                    //<editor-fold defaultstate="collapsed" desc="registrar equipo">
                    out.print("<div class='modal fade' id='Registrar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog modal-lg'>");
                    out.print("<div class='modal-content'>");
                    out.print("<form action='Equipo?opc=2' name='formA' method='post'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='font-size:12px;width:100%'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Equipo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_equipo' id='equipo-id' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Cargo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_cargo' id='cargo-id' placeholder='Cargo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Area: </b><br>");
                    out.print("<select data-live-search=\"true\" name='slc_area' id='area-id' required>");
                    out.print("<option value='' style='display:none'>Seleccione Tipo</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[0].toString()) > 1) {
                            out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Fecha Asignacion: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_fechaA' id='start' value='' autocomplete='off' placeholder='Fecha Asignacion' required><br>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Tipo: </b><br>");
                    out.print("<select name='slc_tipoE' id='tipoE-id' required>");
                    out.print("<option value='' style='display:none'>Seleccionar Tipo</option>");
                    out.print("<option value='SOPORTE'>SOPORTE</option>");
                    out.print("<option>ADMINISTRATIVO</option>");
                    out.print("<option>PROCESO</option>");
                    out.print("<option>SERVIDOR</option>");
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Tipo Equipo: </b><br>");
                    out.print("<select name='slc_tipo' id='tipoE-id' required>");
                    out.print("<option value='' style='display:none'>Seleccionar Tipo</option>");
                    out.print("<option>PC</option>");
                    out.print("<option>PORTATIL</option>");
                    out.print("<option>TODO EN UNO</option>");
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Responsable: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_responsable' id='responsable-id' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Descripción: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_descripcion' id='txt_descripcion' placeholder='Descripcion' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td></tr>");
                    out.print("<tr><td>");
                    out.print("<b>Estado: </b><br>");
                    out.print("<select name='slc_estado' id='estado-id' required>");
                    out.print("<option value='' style='display:none'>Seleccionar Estado</option>");
                    out.print("<option value='B' style='color: #51cf66;'>Bueno</option>");
                    out.print("<option value='R' style='color: #ff922b;'>Revisión</option>");
                    out.print("<option value='D' style='color: #ff6b6b;'>Dañado</option>");
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Aplica Protocolo? </b>");
                    out.print("<input type='radio' class='radioB' id='rdo_prioridad' onclick='MostrarInput(this.value)' name='rdo_prioridad' value='1'>Si");
                    out.print("<input type='radio' class='radioB' id='rdo_prioridad' onclick='MostrarInput(this.value)' name='rdo_prioridad' value='0' checked>No");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<div id='Txt_aplicativo' class='scrollbar' style=' text-align:justify; width:20%;  display:none;'>");
                    lst_aplicativo = jpa_aplicativo.consultarAplicativosProtocolo();
                    if (lst_aplicativo != null) {
                        for (int i = 0; i < lst_aplicativo.size(); i++) {
                            Object[] obj_aplicativo = (Object[]) lst_aplicativo.get(i);
                            out.print("<input type='checkbox' id='' name='Txt_ids'  onclick='Masivo(this.value)' value='" + obj_aplicativo[0] + "'> " + obj_aplicativo[1] + "<br/>");
                        }
                        out.print("<input  type='hidden' id='Txt_ids' name='txt_app'>");
                    } else {
                        out.print("No existe aplicativos");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<div style='display:flex; justify-content: space-between;'>");
                    out.print("<div style='text-align:left; margin-left:4%'>");
                    out.print("<b>Correo: </b><br/>");
                    out.print("<input type='text'  class='form-control' name='txt_correo' id='correo-id' style='width: 300px;' placeholder='Correo@plastitec-sa.com' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</div>");
                    out.print("<div style='margin-top:2%'>");
                    out.print("<input type='submit' value='Registrar'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="modificar equipo">
                    lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    out.print("<div class='modal fade' id='Modificar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog modal-lg'>");
                    out.print("<div class='modal-content'>");
                    out.print("<form action='Equipo?opc=3' name='formA' method='post'>");
                    out.print("<input type='hidden' name='idE' value='" + id_equipo + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='font-size:12px;width:100%'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Equipo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_equipo' id='equipo-id' value='" + obj_equipo[1] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Cargo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_cargo' id='cargo-id' value='" + obj_equipo[6] + "' placeholder='Cargo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Area: </b><br>");
                    out.print("<select data-live-search=\"true\" name='slc_area' id='area-id' required>");
                    out.print("<option value='" + obj_equipo[4] + "' style='display:none'>" + obj_equipo[5] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[0].toString()) > 1) {
                            out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Fecha Asignacion: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_fechaA' id='start' value='" + obj_equipo[11] + "' autocomplete='off' placeholder='Fecha Asignacion' required><br>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Tipo: </b><br>");
                    out.print("<select name='slc_tipoE' id='tipoE-id' required>");
                    out.print("<option value='" + obj_equipo[3] + "' style='display:none'>" + obj_equipo[3] + "</option>");
                    out.print("<option value='N/A'>Sin asignar</option>");
                    out.print("<option value='ADMINISTRATIVO'>ADMINISTRATIVO</option>");
                    out.print("<option value='PROCESO'>PROCESO</option>");
                    out.print("<option value='SERVIDOR'>SERVIDOR</option>");
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Tipo Equipo: </b><br>");
                    out.print("<select name='slc_tipoM' id='tipoEM-id' required>");
                    out.print("<option value='" + obj_equipo[14] + "' style='display:none'>" + obj_equipo[14] + "</option>");
                    out.print("<option value='PC-TORRE'>PC-TORRE</option>");
                    out.print("<option value='PORTATIL'>PORTATIL</option>");
                    out.print("<option value='TODO EN UNO'>TODO EN UNO</option>");
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Responsable: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_responsable' id='responsable-id' value='" + obj_equipo[2] + "' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Descripción: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_descripcion' id='txt_descripcion' value='" + obj_equipo[8] + "' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</td></tr>");
                    out.print("<tr><td>");
                    out.print("<b>Estado: </b><br>");
                    out.print("<select name='slc_estado' id='estado-id' required>");
                    out.print("<option value='" + obj_equipo[7] + "' style='display:none'>" + ((obj_equipo[7].equals("B")) ? "Bueno" : (((obj_equipo[7].equals("R")) ? "Revisión" : "Dañado"))) + "</option>");
                    out.print("<option value='B' style='color: #51cf66;'>Bueno</option>");
                    out.print("<option value='R' style='color: #ff922b;'>Revisión</option>");
                    out.print("<option value='D' style='color: #ff6b6b;'>Dañado</option>");
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td><b>Aplica Protocolo? </b>&nbsp;");
                    out.print("<input type='radio' class='radioB' name='rdo_prioridad' onclick='MostrarInput(this.value)' value='1' " + ((Integer.parseInt(obj_equipo[12].toString()) == 1) ? "checked" : "") + ">&nbsp;Si&nbsp;");
                    out.print("<input type='radio' class='radioB' name='rdo_prioridad' onclick='MostrarInput(this.value)' value='0' " + ((Integer.parseInt(obj_equipo[12].toString()) == 0) ? "checked" : "") + ">&nbsp;No&nbsp;</td>");
                    out.print("</tr>");
                    out.print("</tr>");
                    out.print("</table>");
                    if (obj_equipo[15].toString().contains("[")) {
                        out.print("<div id='Txt_aplicativo' class='scrollbar' style=' text-align:justify; width:20%;  display:block;'>");
                        String[] Arg_app = obj_equipo[15].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int j = 0; j < Arg_app.length; j++) {
                            int id_aplicativo = Integer.parseInt(Arg_app[j].toString());
                            lst_aplicativo_id = jpa_aplicativo.consultarAplicativosProtocoloId(id_aplicativo);
                            if (lst_aplicativo_id != null) {
                                Object[] obj_aplicativo = (Object[]) lst_aplicativo_id.get(0);
                                out.print("<input type='checkbox' id='' name='Txt_ids' onclick='Masivo(this.value)' value='" + obj_aplicativo[0] + "' checked>" + obj_aplicativo[1] + "<br/>");
                            }
                        }
                        out.print("<input value='" + obj_equipo[15] + "' type='hidden' id='Txt_ids' name='txt_app'>");
                        String aplicativo = obj_equipo[15].toString().replace("][", ",").replace("[", "").replace("]", "");
                        lst_aplicativo = jpa_aplicativo.consultarAplicativosNOTIN(aplicativo);
                        if (lst_aplicativo != null) {
                            for (int i = 0; i < lst_aplicativo.size(); i++) {
                                Object[] obj_aplicativo = (Object[]) lst_aplicativo.get(i);
                                out.print("<input type='checkbox' id='' name='Txt_ids' onclick='Masivo(this.value)' value='" + obj_aplicativo[0] + "'>" + obj_aplicativo[1] + "<br/>");
                            }
                        } else {
                            out.print("No existe aplicativos");
                        }
                        out.print("</div>");
                    } else {
                        out.print("<div id='Txt_aplicativo' class='scrollbar' style=' text-align:justify; width:20%;  display:none;'>");
                        lst_aplicativo = jpa_aplicativo.consultarAplicativosProtocolo();
                        if (lst_aplicativo != null) {
                            for (int i = 0; i < lst_aplicativo.size(); i++) {
                                Object[] obj_aplicativo = (Object[]) lst_aplicativo.get(i);
                                out.print("<input type='checkbox' id='' name='Txt_ids'  onclick='Masivo(this.value)' value='" + obj_aplicativo[0] + "'> " + obj_aplicativo[1] + "<br/>");
                            }
                            out.print("<input  type='hidden' id='Txt_ids' name='txt_app'>");
                        } else {
                            out.print("No existe aplicativos");
                        }
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<div style='display:flex; justify-content: space-between;'>");
                    out.print("<div style='text-align:left; margin-left:4%'>");
                    out.print("<b>Correo: </b>");
                    out.print("<input type='text'  class='form-control' name='txt_correo' id='correo-id' style='width: 300px;' value='" + obj_equipo[13] + "' placeholder='Correo@plastitec-sa.com' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</div>");
                    out.print("<div style='margin-top:2%'>");
                    out.print("<input type='submit' value='Modificar'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print("$(\"#Modificar\").modal(\"show\");");
                    out.print("</script>");
                    //</editor-fold>   
                }
                if (filtro.equals("")) {
                    lst_equipos = jpa_equipo.consultaEquipos();
                } else {
                    lst_equipos = jpa_equipo.consultarEquiposFiltro(filtro);
                }
                if (lst_equipos != null) {
                    //<editor-fold defaultstate="collapsed" desc="CONTADOR PROTOCOLOS">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana16'   style='opacity: 1.03;  display:none;'>");
                    out.print("<div style='width:66%;margin:auto;margin-top:7%;'>");
                    out.print("<div class='modal-dialog modal-lg'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Equipos que aplican Protocolo</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table class='table' style='width:90%;font-size:12px'>");
                    lst_aplicativo = jpa_aplicativo.consultarAplicativosProtocolo();
                    out.print("<tr>");
                    out.print("<th style='width:5%'>CANT</th>");
                    out.print("<th>APLICATIVO</th>");
                    out.print("<th style='width:14%'>PROTOCOLO</th>");
                    out.print("<th>PC</th>");
                    out.print("</tr>");
                    if (lst_aplicativo != null) {
                        for (int i = 0; i < lst_aplicativo.size(); i++) {
                            Object[] obj_aplicativo = (Object[]) lst_aplicativo.get(i);
                            lst_protocolo = jpa_equipo.ContadorProtocoloEquipo(obj_aplicativo[0].toString());
                            if (lst_protocolo != null) {
                                Object[] obj_contador = (Object[]) lst_protocolo.get(0);
                                out.print("<tr>");
                                out.print("<td>" + obj_contador[0] + "</td>");
                                out.print("<td>" + obj_aplicativo[1] + "</td>");
                                out.print("<td>" + obj_aplicativo[5] + "</td>");
                                out.print("<td>");
                                lst_equipo = jpa_equipo.EquiposProtocolo(obj_aplicativo[0].toString());
                                if (lst_equipo != null) {
                                    out.print("<b class='tooltip_cont'>Equipos<span class='tooltiptext_css' valign='top'>");
                                    for (int j = 0; j < lst_equipo.size(); j++) {
                                        Object[] obj_equipo = (Object[]) lst_equipo.get(j);
                                        out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=" + obj_equipo[1].toString() + "' style='color:white;'>" + obj_equipo[1] + "</a>");
                                        if (j++ != lst_equipo.size()) {
                                            out.print(",");
                                        }
                                    }
                                    out.print("</span></b>");
                                } else {
                                    out.print("N/A");
                                }
                                out.print("</td>");
                                out.print("</tr>");
                            } else {
                                out.print("<tr><td colspan='4'>NO EXISTE INFORMACIÓN</td></tr>");
                            }
                        }
                    } else {
                        out.print("<tr><td>NO EXISTE APLICATIVOS</td></tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='modal-body' >");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="consulta equipos">
                    out.print("<div style='display: flex; justify-content: space-between; align-items: baseline;'>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<div style='padding: 0px 0px 7px 0px'>");
                    out.print("<i class='fa fa-calculator fa-lg' onclick='mostrarConvencion(16)' style='color:#292929'></i>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='height:90%; max-height:90%; overflow-y: auto;'>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4' style='width:10%'>Estado</th>");
                    out.print("<th class='sticky4' style='width:5%'>Equipo</th>");
                    out.print("<th class='sticky4' style='width:10%'>Responsable</th>");
                    out.print("<th class='sticky4' style='width:10%'>Tipo</th>");
                    out.print("<th class='sticky4' style='width:40%'>Area</th>");
                    out.print("<th class='sticky4' style='width:10%'>Fecha</th>");
                    out.print("<th class='sticky4' style='width:5%'>Modificar</th>");
                    out.print("<th class='sticky4' style='width:5%'>Movimientos</th>");
                    out.print("<th class='sticky4' style='width:5%'>Hoja de vida</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>");
                        if (obj_equipos[7].equals("B")) {
                            out.print("<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #51cf66;'></i>");
                        } else if (obj_equipos[7].equals("R")) {
                            out.print("<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #ff922b;'></i>&nbsp;<i class='far fa-circle'></i>");
                        } else {
                            out.print("<i class='fa fa-circle' style='color: #ff6b6b;'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>");
                        }
                        out.print("</td>");
                        out.print("<td>" + obj_equipos[1] + "</td>");
                        out.print("<td>" + obj_equipos[2] + "</td>");
                        out.print("<td>" + obj_equipos[3] + "<hr>" + ((obj_equipos[14] == null) ? "N/A" : obj_equipos[14]) + "</td>");
                        out.print("<td>" + obj_equipos[5] + "&nbsp;|&nbsp;<b class='title'>Cargo: </b>" + obj_equipos[6] + "</td>");
                        out.print("<td>" + obj_equipos[11] + "</td>");
                        out.print("<td align='center'><a href='Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "&idE=" + obj_equipos[0] + "' class='icon' title='Modificar'><i class='fa fa-pencil-alt fa-lg'></i></a></td>");
                        out.print("<td align='center'><a href='Equipo?opc=1&mod=Mvt&txt_bus=" + filtro + "&idE=" + obj_equipos[0] + "' class='icon' title='Movimiento'><i class='fa fa-exchange-alt fa-lg'></i></a></td>");
//                        out.print("<td align='center'><a href='Equipo?opc=1&mod=Hmt&txt_bus=" + filtro + "&idE=" + obj_equipos[0] + "' class='icon' title='Horometro'><i class='fa fa-history fa-lg'></i></a></td>");
                        out.print("<td align='center'>");
                        out.print("<a href='Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + obj_equipos[0] + "' class='icon' title='Ver historial'><i class='far fa-folder-open fa-lg'></i></a>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',20);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
//</editor-fold>
                } else {
                    out.print("<br><b>No se encuentran resultados</b>");
                }
                //</editor-fold>
            }
            if (modulo.equals("Mvt")) {
                //<editor-fold defaultstate="collapsed" desc="submodulo movimientos">
                int id_movimiento = Integer.parseInt(pageContext.getRequest().getAttribute("id_movimiento").toString());
                lst_movimientos = jpa_equipo.consultaMovimientosEquipoId(id_equipo);
                lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                out.print("<div style='float:right;'>");
                out.print("<i class='fa fa-plus fa-lg' onclick='mostrarConvencion(14)' style='color:#292929'></i>");
                out.print("</div>");
                out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Movimientos " + obj_equipo[1] + "</h3>");

                if (id_movimiento == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO MOVIMIENTO">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana14' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:807px; height:auto; position: absolute;top:15%; left:27%;text-align:left '>");
                    out.print("<form action='Equipo?opc=4' name='formA' method='post'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<input type='hidden' name='idE' value='" + id_equipo + "'>");
                    out.print("<div class='modal-header' style='padding:7px;'>");
                    out.print("<a href='Equipo?opc=1&mod=Mvt&txt_bus=" + filtro + "&idE=" + id_equipo + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<div style='display:flex;'>");
                    out.print("<div>");
                    out.print("<b>Equipo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_equipo' id='equipo-id' value='" + obj_equipo[1] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' reaonly='false'><br/>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Cargo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_cargo' id='cargo-id' placeholder='Cargo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Area: </b><br>");
                    out.print("<select name='slc_area' id='area-id' required>");
                    out.print("<option value='' style='display:none'>Seleccione Tipo</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[0].toString()) > 1) {
                            out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Fecha Asignacion: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_fechaA' id='start' value='' autocomplete='off' placeholder='Fecha Asignacion' required><br>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;'>");
                    out.print("<div>");
                    out.print("<b>Tipo: </b><br>");
                    out.print("<select name='slc_tipoE' id='tipoE-id' required>");
                    out.print("<option value='' style='display:none'>Seleccionar Tipo</option>");
                    out.print("<option value='N/A'>Sin asignar</option>");
                    out.print("<option>ADMINISTRATIVO</option>");
                    out.print("<option>PROCESO</option>");
                    out.print("<option>SERVIDOR</option>");
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Responsable: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_responsable' id='responsable-id' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Estado: </b><br>");
                    out.print("<select name='slc_estado' id='estado-id' required>");
                    out.print("<option value='' style='display:none'>Seleccionar Estado</option>");
                    out.print("<option value='B' style='color: #51cf66;'>Bueno</option>");
                    out.print("<option value='R' style='color: #ff922b;'>Revisión</option>");
                    out.print("<option value='D' style='color: #ff6b6b;'>Dañado</option>");
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Descripción: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_descripcion' id='txt_descripcion' placeholder='Descripción' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<input type='submit' value='Registrar'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR MOVIMIENTO">
                    lst_movimientos = jpa_equipo.consultaMovimientosId(id_movimiento);
                    Object[] obj_movimiento = (Object[]) lst_movimientos.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana14' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:807px; height:292px; position: absolute;top:15%; left:27%;text-align:left '>");
                    out.print("<form action='Equipo?opc=8' name='formA' method='post'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<input type='hidden' name='idE' value='" + id_equipo + "'>");
                    out.print("<input type='hidden' name='idM' value='" + id_movimiento + "'>");
                    out.print("<div class='modal-header' style='padding:7px;'>");
                    out.print("<a href='Equipo?opc=1&mod=Mvt&txt_bus=" + filtro + "&idE=" + id_equipo + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<div style='display:flex;'>");
                    out.print("<div>");
                    out.print("<b>Equipo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_equipoM' id='equipo-id' value='" + obj_movimiento[1] + "' placeholder='Equipo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Cargo: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_cargoM' id='cargo-id' value='" + obj_movimiento[6] + "' placeholder='Cargo' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Area: </b><br>");
                    out.print("<select name='slc_areaM' id='area-id' required>");
                    out.print("<option value='" + obj_movimiento[4] + "' style='display:none'>" + obj_movimiento[5] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[0].toString()) > 1) {
                            out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Fecha Asignacion: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_fechaAM' id='start' value='" + obj_movimiento[9] + "' autocomplete='off' placeholder='Fecha Asignacion' required><br>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;'>");
                    out.print("<div>");
                    out.print("<b>Tipo: </b><br>");
                    out.print("<select name='slc_tipoEM' id='tipoE-id' required>");
                    out.print("<option value='" + obj_movimiento[3] + "' style='display:none'>" + obj_movimiento[3] + "</option>");
                    out.print("<option value='N/A'>Sin asignar</option>");
                    out.print("<option>ADMINISTRATIVO</option>");
                    out.print("<option>PROCESO</option>");
                    out.print("<option>SERVIDOR</option>");
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Responsable: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_responsableM' id='responsable-id' value='" + obj_movimiento[2] + "' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Estado: </b><br>");
                    out.print("<select name='slc_estadoM' id='estado-id' required>");
                    if (obj_movimiento[7].equals("B")) {
                        out.print("<option value='" + obj_movimiento[7] + "' style='display:none'>BUENO</option>");
                    } else if (obj_movimiento[7].equals("R")) {
                        out.print("<option value='" + obj_movimiento[7] + "' style='display:none'>REVISADO</option>");
                    } else {
                        out.print("<option value='" + obj_movimiento[7] + "' style='display:none'>DAÑADO</option>");
                    }
                    out.print("<option value='B' style='color: #51cf66;'>Bueno</option>");
                    out.print("<option value='R' style='color: #ff922b;'>Revisión</option>");
                    out.print("<option value='D' style='color: #ff6b6b;'>Dañado</option>");
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Descripción: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_descripcionM' id='txt_descripcion' value='" + obj_movimiento[8] + "' placeholder='Responsable' onchange='javascript:this.value=this.value.toUpperCase();' required><br/>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='modal-footer' style='padding: 8px 16px 4px 0px'>");
                    out.print("<input type='submit' value='Modificar'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</filedset>");
                    out.print("</div>");
                    //</editor-fold>
                }

                if (lst_movimientos != null) {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th style='width:10%'>Estado</th>");
                    out.print("<th style='width:20%'>Responsable</th>");
                    out.print("<th style='width:10%'>Tipo</th>");
                    out.print("<th style='width:45%'>Area</th>");
                    out.print("<th style='width:10%'>Fecha</th>");
                    out.print("<th style='width:5%'>Opc</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_movimientos.size(); i++) {
                        Object[] obj_movimientos = (Object[]) lst_movimientos.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>");
                        if (obj_movimientos[7].equals("B")) {
                            out.print("<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #51cf66;'></i>");
                        } else if (obj_movimientos[7].equals("R")) {
                            out.print("<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #ff922b;'></i>&nbsp;<i class='far fa-circle'></i>");
                        } else {
                            out.print("<i class='fa fa-circle' style='color: #ff6b6b;'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>");
                        }
                        out.print("</td>");
                        out.print("<td>" + obj_movimientos[2] + "</td>");
                        out.print("<td>" + obj_movimientos[3] + "</td>");
                        out.print("<td>" + obj_movimientos[5] + "&nbsp;|&nbsp;<b class='title'>Cargo: </b>" + obj_movimientos[6] + "</td>");
                        out.print("<td>" + obj_movimientos[9] + "</td>");
                        out.print("<td align='center'><a href='Equipo?opc=1&mod=Mvt&txt_bus=" + filtro + "&idE=" + id_equipo + "&idM=" + obj_movimientos[0] + "' class='icon'><i class='fas fa-pencil-alt fa-lg' ></i></a></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',8);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<br><b>No se han realizado movimientos</b>");
                }
//</editor-fold>
            }
            if (modulo.equals("Hmt")) {
                //<editor-fold defaultstate="collapsed" desc="Hoja de movimientos">
                lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                lst_horometro = jpa_horometro.consultaHorormetroidEquipo(id_equipo);
                out.print("<div style='float:right;'>");
                out.print("<a href='#' data-toggle=\"modal\" data-target=\"#Registrar\"><i class='fa fa-plus fa-lg' style='color:#292929'></i></a>");
                out.print("</div>");
                out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Horometro " + obj_equipo[1] + "</h3>");
                //</editor-fold>
            }
            if (modulo.equals("HVE")) {
                //<editor-fold defaultstate="collapsed" desc="hoja de vida">
                if (hojaV == 1) {
                    //<editor-fold defaultstate="collapsed" desc="Registrar Adjunto">
                    lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                    Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                    String generar = pageContext.getRequest().getAttribute("generar").toString();
                    List lst_registros = jpa_registro.consultaRegistros();
                     out.print(" <script language=\"javascript\" type = \"text/javascript\" src = \"tinyfck/tiny_mce.js\"></script>\n"
                            + "            <script language=\"javascript\" type = \"text/javascript\" src = \"tinyfck/HTMLEditor.js\"></script>");
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                    out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                    out.print("<div class='modal-dialog modal-lg' style='width: 987px;'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + id_equipo + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar Adjunto</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<form action='Equipo?opc=1&mod=HVE&idHV=1' name='formA' method='post'>");
                    out.print("<input type='hidden' name='idE' value='" + id_equipo + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<input type='hidden' name='txt_generar' id='generar' value='" + ((!generar.equals("") ? generar : "")) + "' required>");
                    out.print("<div style='display:flex; justify-content:space-evenly;'>");
                    out.print("<div>");
                    out.print("<b>Fecha: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_fecha' id='datepicker' placeholder='Fecha' autocomplete='off' required>");
                    out.print("</div>");
                    out.print("<div style='display:flex;justify-content: space-evenly;align-items: flex-end;'>");
                    out.print("<div>");
                    out.print("<b>Registro: </b><br>");
                    out.print("<select data-live-search=\"true\" name='slc_registro' id='registro-id' required>");
                    out.print("<option value='' style='display:none'>Seleccione Registro</option>");
                    for (int i = 0; i < lst_registros.size(); i++) {
                        Object[] obj_registros = (Object[]) lst_registros.get(i);
                        if (Integer.parseInt(obj_registros[4].toString()) != 0) {
                            out.println("<option value='" + obj_registros[1] + "//" + obj_registros[2] + "//" + obj_registros[3] + "' data-subtext='" + obj_registros[1] + " V" + obj_registros[2] + "'>" + obj_registros[3] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div style='margin: 8px'>");
                    out.print("<i class='fas fa-plus fa-lg' onclick='agregar()'></i>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    if (!generar.equals("")) {
                        String[] campos = generar.replace("][", "##").replace("]", "").replace("[", "").split("##");
                        out.print("<table class='table' id='tableG' style='width:50%'>");
                        for (int i = 0; i < campos.length; i++) {
                            String[] campo = campos[i].split("//");
                            out.print("<tr>");
                            out.print("<td>" + campo[0] + "</td>");
                            out.print("<td>" + campo[3] + "</td>");
                            out.print("<td></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<input type='submit' value='Generar' style='float: right;'>");
                        out.print("</form>");
                        out.print("<form action='Equipo?opc=5' name='formA' method='post'>");
                        out.print("<input type='hidden' name='idE' value='" + id_equipo + "'>");
                        out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                        out.print("<input type='hidden' name='txt_generar' id='generar' value='" + ((!generar.equals("") ? generar : "")) + "' required>");
                        out.print("</div>");
                        out.print("<div class='modal-body'>");
                        out.print("<textarea id='small_descripcion-id' name='txt_descripcion'>");
                        for (int i = 0; i < campos.length; i++) {
                            String[] campo = campos[i].split("//");
                            out.print("" + ((i != 0) ? "<hr />" : "") + "");
                            out.print("" + campo[3].replace(" ", "_") + "_" + campo[0].replace("-", "") + "");
                        }
                        out.print("</textarea>");
                        out.print("</div>");
                        out.print("<div class='modal-footer'>");
                        out.print("<input type='submit' value='Guardar'>");
                        out.print("</div>");
                        out.print("</form>");
                    } else {
                        out.print("<table class='table' id='tableG' style='width:50%'>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div class='modal-footer'>");
                        out.print("<input type='submit' value='Generar' style='float: right;'>");
                        out.print("</form>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else if (hojaV == 2) {
                    //<editor-fold defaultstate="collapsed" desc="Modificar Adjunto">
                    List lst_hojaV2 = jpa_equipo.consultaEquipoIdRegistro(id_h_equipos);
                    List lst_registros = jpa_registro.consultaRegistros();
                    Object[] obj_equipos = (Object[]) lst_hojaV2.get(0);
                    out.print(" <script language=\"javascript\" type = \"text/javascript\" src = \"tinyfck/tiny_mce.js\"></script>\n"
                            + "            <script language=\"javascript\" type = \"text/javascript\" src = \"tinyfck/HTMLEditor.js\"></script>");
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                    out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                    out.print("<div class='modal-dialog modal-lg'  style='width: 987px;'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + id_equipo + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar Adjunto</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<form action='Equipo?opc=7&mod=HVE&idE=" + id_equipo + "'name='formA' method='post'>");
                    out.print("<input type='hidden' name='idHVR' value='" + obj_equipos[0] + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<div style='display:flex; justify-content: space-evenly'>");
                    out.print("<div>");
                    out.print("<b>Fecha: </b><br>");
                    out.print("<input type='text'  class='form-control' name='txt_Mfecha' id='datepicker' placeholder='Fecha' value='" + obj_equipos[8] + "' autocomplete='off'></td>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Registro: </b><br>");
                    out.print("<select data-live-search=\"true\" name='Mslc_registro' id='registro-id'>");
                    out.println("<option value='" + obj_equipos[3] + "//" + obj_equipos[5] + "//" + obj_equipos[4] + "'>" + obj_equipos[4] + "</option>");
                    for (int i = 0; i < lst_registros.size(); i++) {
                        Object[] obj_registros = (Object[]) lst_registros.get(i);
                        if (Integer.parseInt(obj_registros[4].toString()) != 0) {
                            out.println("<option value='" + obj_registros[1] + "//" + obj_registros[2] + "//" + obj_registros[3] + "' data-subtext='" + obj_registros[1] + " V" + obj_registros[2] + "'>" + obj_registros[3] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<textarea id='small_descripcion-id'  name='txt_descripcion'><div contentenditable='true'>" + obj_equipos[7] + "</div>");
                    out.print("</textarea>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<input type='submit' value='Modificar'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//                    //</editor-fold>
                }
                out.print("<div style='width: 100%; height:100%; max-width: 100%; max-height:100%; overflow:auto'>");
                //<editor-fold defaultstate="collapsed" desc="Consulta hoja de vida">
                lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                out.print("<a href='Equipo?opc=1&mod=Epo&txt_bus=" + filtro + "'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Hoja de Vida " + obj_equipo[1] + "</h3>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th style='width:10%'>Estado</th>");
                out.print("<th style='width:5%'>Equipo</th>");
                out.print("<th style='width:10%'>Responsable</th>");
                out.print("<th style='width:10%'>Tipo</th>");
                out.print("<th style='width:25%'>Observaciones/Correo</th>");
                out.print("<th style='width:30%'>Area</th>");
                out.print("<th style='width:10%'>Fecha</th>");
                out.print("<th style='width:5%'>Opc</th>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center'>");
                if (obj_equipo[7].equals("B")) {
                    out.print("<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #51cf66;'></i>");
                } else if (obj_equipo[7].equals("R")) {
                    out.print("<i class='far fa-circle'></i>&nbsp;<i class='fa fa-circle' style='color: #ff922b;'></i>&nbsp;<i class='far fa-circle'></i>");
                } else {
                    out.print("<i class='fa fa-circle' style='color: #ff6b6b;'></i>&nbsp;<i class='far fa-circle'></i>&nbsp;<i class='far fa-circle'></i>");
                }
                out.print("</td>");
                out.print("<td align='center'>" + obj_equipo[1] + "</td>");
                out.print("<td align='center'>" + obj_equipo[2] + "</td>");
                out.print("<td align='center'>" + obj_equipo[3] + "</td>");
                out.print("<td valign='top'>" + obj_equipo[8] + "<hr>" + obj_equipo[13] + "</td>");
                out.print("<td valign='top'>" + obj_equipo[5] + "<hr><b class='title'>Cargo: </b>" + obj_equipo[6] + "</td>");
                out.print("<td align='center'>" + obj_equipo[11] + "</td>");
                out.print("<td align='center'><a href='Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + obj_equipo[0] + "&idHV=1' class='icon' title='Agregar'><i class='fas fa-plus fa-lg'></i></a></td>");
                out.print("</td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<div class='panel-group' id='accordion'>");
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Actividades'>ACTIVIDADES</a></h4>");
                out.print("</div>");
                out.print("<div id='Actividades' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="Consulta actividades reportadas">
                lst_actividades = jpa_equipo.consultaActividadesIdEquipo(id_equipo);
                if (lst_actividades != null) {
                    out.print("<div id='NavPosicionA' style='display: flex;'></div>");
                    out.print("<table class='table' id='resultadosA'>");
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='5' style='background-color: #ddd;'></d>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:18%'><b class='title'>Fecha: </b>" + obj_actividades[15] + "</td>");
                        out.print("<td style='width:23%'><b class='title'>Reportante: </b>" + obj_actividades[1] + "</td>");
                        if (id_rol == 5) {
                            out.print("<td style='width:18%'><b class='title'>Aplicativo: </b>" + obj_actividades[8] + "</td>");
                        } else {
                            out.print("<td style='width:18%'><b class='title'>Equipo: </b>" + obj_actividades[3] + "</td>");
                        }
                        out.print("<td style='width:23%' colspan='2'><b class='title'>Tipo Soporte: </b>" + obj_actividades[6] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2' valign='top'>" + obj_actividades[12] + "</td>");
                        out.print("<td colspan='3' valign='top'>" + obj_actividades[13] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='title'>Fecha Reportante: </b>" + obj_actividades[9] + "</td>");
                        out.print("<td><b class='title'>Fecha Ejecucion: </b>" + obj_actividades[10] + "</td>");
                        out.print("<td><b class='title'>Fecha Fin: </b>" + obj_actividades[11] + "</td>");
                        out.print("<td colspan='2' align='center'><b>Parada Equipo: " + obj_actividades[16] + "&nbsp;|&nbsp;Produccion: " + obj_actividades[17] + "</b></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager2 = new Pager2('resultadosA',12);");
                    out.print("pager2.init();");
                    out.print("pager2.showPageNav('pager2','NavPosicionA');");
                    out.print("pager2.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b class='title'>No se encontraron resultados</b>");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Casos'>CASOS</a></h4>");
                out.print("</div>");
                out.print("<div id='Casos' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="Consulta Casos">
                lst_casos = jpa_caso.consultarCasosidEquipo(id_equipo);
                out.print("<div id='NavPosicionC' style='display: flex;'></div>");
                if (lst_casos != null) {
                    out.print("<table class='table' id='resultadosC'>");
                    for (int i = 0; i < lst_casos.size(); i++) {
                        Object[] obj_casos = (Object[]) lst_casos.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                        out.print("<td style='width:15%' rowspan='2' align='center'><b class='title'>De: </b>" + obj_casos[4] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:70%' valign='top'><b class='title'>Solución: </b>" + obj_casos[9] + "<br><div style='float:right'><b>Responsable: </b>" + obj_casos[10] + "&nbsp;|&nbsp;" + obj_casos[8] + "</div></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager3 = new Pager3('resultadosC',15);");
                    out.print("pager3.init();");
                    out.print("pager3.showPageNav('pager3','NavPosicionC');");
                    out.print("pager3.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b class='title'>No se encontraron resultados</b>");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='panel panel-default'>");
                out.print("<div class='panel-heading'>");
                out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#Adjuntos'>ADJUNTOS</a></h4>");
                out.print("</div>");
                out.print("<div id='Adjuntos' class='panel-collapse collapse'>");
                out.print("<div class='panel-body'>");
                //<editor-fold defaultstate="collapsed" desc="Adjuntos">
                List lst_hojaV = jpa_HojaV.consultaHojaDeVidaIdEquipo(id_equipo);
                if (lst_hojaV != null) {
                    out.print("<table class='table' id='resultadosHV'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4' style='width:10%'>Codigo</th>");
                    out.print("<th class='sticky4' style='width:20%'>Nombre</th>");
                    out.print("<th class='sticky4' style='width:40%'>Adjunto</th>");
                    out.print("<th class='sticky4' style='width:10%'>Fecha</th>");
                    out.print("<th class='sticky4' style='width:15%'>Responsable</th>");
                    if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                        out.print("<th class='sticky4' style='width:15%' colspan='2'>Opc</th>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_hojaV.size(); i++) {
                        Object[] obj_hojaV = (Object[]) lst_hojaV.get(i);
                        out.print("<tr>");
                        if (obj_hojaV[4].equals("LICENCIA") || obj_hojaV[4].equals("FACTURA") || obj_hojaV[4].equals("SERIAL") || obj_hojaV[4].equals("OTROS")) {
                            out.print("<td>N/A</td>");
                            out.print("<td>" + obj_hojaV[4] + "</td>");
                        } else {
                            out.print("<td>" + obj_hojaV[3] + "</td>");
                            out.print("<td>" + obj_hojaV[4] + "</td>");

                        }
                        out.print("<td>" + obj_hojaV[7] + "</td>");
                        out.print("<td align='center'><span onclick='javascript:window.open(" + obj_hojaV[8] + ", '', 'width=1024,height=720,left=50,top=50,toolbar=yes')';>" + obj_hojaV[8] + "</span></td>");
                        out.print("<td>" + obj_hojaV[10] + "</td>");
                        if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                            out.print("<td align='center'><a href='Equipo?opc=1&mod=HVE&txt_bus=" + filtro + "&idE=" + obj_equipo[0] + "&idHR=" + obj_hojaV[0] + "&idHV=2' class='icon'><i class='fas fa-pencil-alt fa-lg' onclick='mostrarConvencion(6)'></i></a></td>");
                            out.print("<td align='center'><i class='icon'><i class='fas fa-file-prescription fa-lg' onclick='EliminarRegistro(" + obj_equipo[0] + "," + obj_hojaV[0] + ")'></i></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                } else {
                    out.print("<b class='title'>No se han registrados ajduntos</b>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("<script>");
                out.print("$('#summernote').summernote({");
                out.print("placeholder: 'Descripción',");
                out.print("tabsize: 2,");
                out.print("height: 150");
                out.print("});");
                out.print("</script>");
                //</editor-fold>
            }

        } catch (IOException ex) {
            Logger.getLogger(Tag_equipos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
