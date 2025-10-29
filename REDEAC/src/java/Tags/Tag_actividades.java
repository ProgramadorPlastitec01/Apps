package Tags;

import Controladoras.ActividadGeneralJpaController;
import Controladoras.ActividadReportadaJpaController;
import Controladoras.AplicativoJpaController;
import Controladoras.AreaJpaController;
import Controladoras.CasoJpaController;
import Controladoras.EquipoJpaController;
import Controladoras.TipoSoporteJpaController;
import Controladoras.ListasVerificacionJpaController;
import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_actividades extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        ActividadGeneralJpaController jpa_actividad = new ActividadGeneralJpaController();
        ActividadReportadaJpaController jpa_actividadR = new ActividadReportadaJpaController();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        TipoSoporteJpaController jpa_tipoP = new TipoSoporteJpaController();
        AplicativoJpaController jpa_aplicativo = new AplicativoJpaController();
        ListasVerificacionJpaController jpa_listaEquipo = new ListasVerificacionJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        List lst_areas = jpa_area.consultarAreas();
        List lst_actividades = null;
        List lst_actividad = null;
        List lst_plantilla = null;
        List lst_tipoSpt = null;
        List lst_equipos = null;
        List lst_aplicativos = null;
        List lst_listaEquipos = null;
        List lst_reportante = null;
        int val = 0, id_actividad = 0;
        String modulo = "";
        try {
            id_actividad = Integer.parseInt(pageContext.getRequest().getAttribute("id_actividad").toString());
        } catch (Exception e) {
            id_actividad = 0;
        }
        try {
            modulo = pageContext.getRequest().getAttribute("Actividad").toString();
        } catch (Exception e) {
            modulo = "";
        }
        Date fecha = new Date();
        try {
            if (modulo.equals("Ac")) {
                if (id_rol == 3 || id_rol == 4 || id_rol == 5) {
                    //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES POR ROL">
                    lst_actividades = jpa_actividad.consultarActividades(id_usuario, fecha_inicial, fecha_final);
                    lst_plantilla = jpa_actividad.consultarPlantilla(id_rol);
                    Object[] obj_plantilla = (Object[]) lst_plantilla.get(0);
                    out.print("<div style='float:right;'><a href='#' data-toggle=\"modal\" ><i class='fa fa-plus fa-lg' style='color:#c4c4c4; cursor: no-drop;'></i></a></div>");
                    out.print("<h3>Actividad</h3>");
                    if (id_actividad == 0) {
                        //<editor-fold defaultstate="collapsed" desc="Registrar actividad">
                        out.print("<script src='Interfaz/EditorHtml/htmlpopper.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlbootstrap.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlbootstrap.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlsummernote-bs4.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlsummernote-bs4.min.js' type='text/javascript'></script>");
                        List lst_usuarios = jpa_usuario.consultaUsuarioId(id_usuario);
                        Object[] obj_usuario = (Object[]) lst_usuarios.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2'   style='opacity: 1.03;  display:none;'>");
                        out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                        out.print("<div class='modal-dialog modal-lg' style='width: 977px'>");
                        out.print("<div class='modal-content'>");
                        out.print("<form action='Actividad?opc=2' name='formA' method='post'>");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Actividad?opc=1&idA=0&mod=Ac' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Registrar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align='center'>");
                        out.print("<table style='width:90%;font-size:12px'>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Inicio: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaI' id='start' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha inicio' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Inicio: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("<td rowspan='2'>");
                        out.print("<b>Asunto: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_asunto' id='asunto-id' placeholder='Asunto' value='BITACORA DE " + obj_usuario[1] + "' style='width:100%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Fin: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaF' id='end' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha fin' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Fin: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div class='modal-body' >");
                        //<editor-fold defaultstate="collapsed" desc="CONDICIONES DE PLANTILLA POR ROL">
                        if (id_rol == 5) {
                            out.print("<textarea id='editor' name='txt_actividad'>" + obj_plantilla[1] + "</textarea>");
                        } else if (id_rol == 3) {
                            out.print("<textarea id='editor' name='txt_actividad'>" + obj_plantilla[1] + "</textarea>");
                        } else if (id_rol == 4) {
                            out.print("<textarea id='editor' name='txt_actividad'>" + obj_plantilla[1] + "</textarea>");
                        } else {
                            out.print("<textarea id='editor' name='txt_actividad'></textarea>");
                        }
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class='modal-footer'>");
                        out.print("<input type='submit' value='Registrar'>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="modificar actividad">
                        out.print("<script src='Interfaz/EditorHtml/htmlpopper.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlbootstrap.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlbootstrap.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlsummernote-bs4.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlsummernote-bs4.min.js' type='text/javascript'></script>");
                        lst_actividad = jpa_actividad.consultarActividadId(id_actividad);
                        Object[] obj_actividad = (Object[]) lst_actividad.get(0);
                        String[] fechaI = obj_actividad[1].toString().replace("|", "-").split("-");
                        String[] fechaF = obj_actividad[2].toString().replace("|", "-").split("-");
                        out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                        out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                        out.print("<div class='modal-dialog modal-lg' style='width: 977px'>");
                        out.print("<div class='modal-content'>");
                        out.print("<form action='Actividad?opc=3' name='formA' method='post'>");
                        out.print("<input type='hidden' name='idA' value='" + id_actividad + "' id='idA'>");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Actividad?opc=1&idA=0&mod=Ac' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Modificar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align='center'>");
                        out.print("<table style='width:90%;font-size:12px'>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Inicio: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaI' id='datepicker' value='" + fechaI[0] + "' autocomplete='off' placeholder='Fecha inicio' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Inicio: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' value='" + fechaI[1] + "' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("<td rowspan='2'>");
                        out.print("<b>Asunto: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_asunto' id='asunto-id' value='" + obj_actividad[3] + "' placeholder='Asunto' style='width:100%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Fin: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaF' id='datepicker2' value='" + fechaF[0] + "' autocomplete='off' placeholder='Fecha fin' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Fin: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' value='" + fechaF[1] + "' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div class='modal-body' >");
                        out.print("<textarea id='editor'name='txt_actividad'>" + obj_actividad[4].toString() + "</textarea>");
                        out.print("</div>");
                        out.print("<div style='height:40px;'>");
                        out.print("<input style='float:right; margin-right:10px;' type='submit' value='Modificar'>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    }
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES EN GENERAL">
                    lst_actividades = jpa_actividad.consultarActividades(id_usuario, fecha_inicial, fecha_final);
                    out.print("<div style='float:right;'><a href='#' data-toggle=\"modal\"><i class='fa fa-plus fa-lg' style='color:#c4c4c4; cursor: no-drop;'></i></a></div>");
                    out.print("<h3>Actividad</h3>");
                    if (id_actividad == 0) {
                        //                        //<editor-fold defaultstate="collapsed" desc="Registrar actividad">
                        out.print("<script src='Interfaz/EditorHtml/htmljquery-3.5.1.min.js' type='text/javascript'></script>");
                        out.print("<script src='Interfaz/EditorHtml/htmlpopper.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlbootstrap.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlbootstrap.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlsummernote-bs4.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlsummernote-bs4.min.js' type='text/javascript'></script>");
                        List lst_usuarios = jpa_usuario.consultaUsuarioId(id_usuario);
                        Object[] obj_usuario = (Object[]) lst_usuarios.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2'   style='opacity: 1.03; overflow:auto; display:none;'>");
                        out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                        out.print("<div class='modal-dialog modal-lg' style='width: 977px'>");
                        out.print("<div class='modal-content'>");
                        out.print("<form action='Actividad?opc=2' name='formA' method='post'>");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Actividad?opc=1&idA=0&mod=Ac' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Registrar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align='center'>");
                        out.print("<table style='width:90%;font-size:12px'>");
                        out.print("<div>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Inicio: </b><br>");
                        out.print("<input type='text' style='width:61%' class='form-control' name='txt_fechaI' id='start' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha inicio' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Inicio: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("<td rowspan='2'>");
                        out.print("<b>Asunto: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_asunto' id='asunto-id' placeholder='Asunto' value='BITACORA DE " + obj_usuario[1] + "' style='width:100%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Fin: </b><br>");
                        out.print("<input type='text' style='width:61%'  class='form-control' name='txt_fechaF' id='end' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha fin' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Fin: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</div>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div class='modal-body' >");
                        out.print("<textarea id='editor' name='txt_actividad'><p>*</p></textarea>");
                        out.print("</div>");
                        out.print("<div class='modal-footer'>");
                        out.print("<input type='submit' value='Registrar'>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="modificar actividad">
                        out.print("<script src='Interfaz/EditorHtml/htmljquery-3.5.1.min.js' type='text/javascript'></script>");
                        out.print("<script src='Interfaz/EditorHtml/htmlpopper.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlbootstrap.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlbootstrap.min.js' type='text/javascript'></script>");
                        out.print("<link href='Interfaz/EditorHtml/htmlsummernote-bs4.min.css' rel='stylesheet' type='text/css'/>");
                        out.print("<script src='Interfaz/EditorHtml/htmlsummernote-bs4.min.js' type='text/javascript'></script>");
                        lst_actividad = jpa_actividad.consultarActividadId(id_actividad);
                        Object[] obj_actividad = (Object[]) lst_actividad.get(0);
                        String[] fechaI = obj_actividad[1].toString().replace("|", "-").split("-");
                        String[] fechaF = obj_actividad[2].toString().replace("|", "-").split("-");
                        out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                        out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                        out.print("<div class='modal-content' style='width: 977px'>");
                        out.print("<form action='Actividad?opc=3' name='formA' method='post'>");
                        out.print("<input type='hidden' name='idA' value='" + id_actividad + "' id='idA'>");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Actividad?opc=1&idA=0&mod=Ac' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Modificar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align='center'>");
                        out.print("<table style='width:90%;font-size:12px'>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Inicio: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaI' id='datepicker' value='" + fechaI[0] + "' autocomplete='off' placeholder='Fecha inicio' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Inicio: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' value='" + fechaI[1] + "' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("<td rowspan='2'>");
                        out.print("<b>Asunto: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_asunto' id='asunto-id' value='" + obj_actividad[3] + "' placeholder='Asunto' style='width:100%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td>");
                        out.print("<b>Fecha Fin: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaF' id='datepicker2' value='" + fechaF[0] + "' autocomplete='off' placeholder='Fecha fin' required>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<b>Hora Fin: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' value='" + fechaF[1] + "' placeholder='Hora Inicio' required>");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<div class='modal-body' >");
                        out.print("<textarea id='editor'  name='txt_actividad' width='100%' height='50%'>" + obj_actividad[4].toString() + "</textarea>");
                        out.print("</div>");
                        out.print("<div style='height:40px;'>");
                        out.print("<input style='float:right; margin-right:10px;' type='submit' value='Modificar'>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                }
                if (lst_actividades != null) {
                    //<editor-fold defaultstate="collapsed" desc="consulta actividades">
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<div style='height:89%; width:100%; max-height:89%; overflow:auto;'>");
                    out.print("<table class='table' id='resultados'>");
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='3' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:30%'><b class='title'>Asunto: </b>" + obj_actividades[3] + "</td>");
                        out.print("<td style='width:65%'><b class='title'>Inicio: </b>" + obj_actividades[1] + "&nbsp;|&nbsp;<b class='title'>Fin: </b>" + obj_actividades[2] + "</td>");
//                        out.print("<td style='width:5%' align='center'><a href='Actividad?opc=1&idA=" + obj_actividades[0] + "&mod=Ac' class='icon' title='Modificar'><i class='fa fa-pencil-alt fa-lg'></i></a></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' colspan='3'>" + obj_actividades[4] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',9);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<br><b class='title'>No se encontraron resultados</b>");
                    //</editor-fold>
                }
            }
            if (modulo.equals("AcR")) {
                try {
                    val = Integer.parseInt(pageContext.getRequest().getAttribute("val").toString());
                } catch (Exception e) {
                    val = 0;
                }
                //<editor-fold defaultstate="collapsed" desc="ACTIVIDAD REPORTANTE">
                lst_actividades = jpa_actividadR.consultarActividadesReportante();
                lst_tipoSpt = jpa_tipoP.consultarTipoSoporteIdRol(id_rol);
                int documento = Integer.parseInt(pageContext.getRequest().getAttribute("Documento").toString());
                int codigo = Integer.parseInt(pageContext.getRequest().getAttribute("Codigo").toString());
                out.print("<div style='float:right'><a href='#' data-toggle=\"modal\" ><i class='fa fa-plus fa-lg' style='color:#c4c4c4; cursor: no-drop;'></i></a></div>");
                out.print("<h3>Actividad Reportante</h3>");
                if (id_actividad == 0) {
                    if (val == 1) {
                        //<editor-fold defaultstate="collapsed" desc="registrar actividad">
                        out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03; display:block;'>");
                        out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                        out.print("<div class='modal-dialog modal-lg' style='width: 977px' >");
                        out.print("<div class='modal-content' >");
                        out.print("<form action='Actividad?opc=4' name='formA' id='formA' method='post' style='margin:0px;'>");
                        out.print("<input type='hidden' name='txt_codigo' value='" + codigo + "' >");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Actividad?opc=1&idA=0&mod=AcR' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Registrar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align='center' style='padding:2px;'>");
                        lst_reportante = jpa_actividadR.consultaFirmaUsuarioCodigo(codigo);
                        out.print("<div style='display:flex; justify-content:space-around; height:55px;'>");
                        out.print("<div>");
                        out.print("<b>Reportante: </b><br>");
                        String user_name = "";
                        try {
                            Object[] obj_repor = (Object[]) lst_reportante.get(0);
                            user_name = obj_repor[9].toString();
                        } catch (Exception e) {
                            user_name = "";
                        }
                        if (lst_reportante == null) {
                            out.print("<input type='text' class='form-control' name='txt_reportante' id='reportante-id' placeholder='Reportante' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        } else {
                            Object[] obj_reportante = (Object[]) lst_reportante.get(0);
                            out.print("<input type='text' class='form-control' value='" + obj_reportante[9] + "' name='txt_reportante' id='reportante-id' placeholder='Reportante' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        }
                        out.print("</div>");
                        out.print("<div>");
                        out.print("<b>Area:</b><br>");
                        out.print("<select name='slc_area' id='area-id' data-live-search='true' required>");
                        if (lst_reportante != null) {
                            Object[] obj_reportante = (Object[]) lst_reportante.get(0);
                            if (obj_reportante[10] != null) {
                                for (int i = 0; i < lst_areas.size(); i++) {
                                    Object[] obj_area = (Object[]) lst_areas.get(i);
                                    String area_sirh = obj_reportante[11].toString();
                                    String area_redeac = obj_area[3].toString();
                                    if (area_sirh.equals(area_redeac)) {
                                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < lst_areas.size(); i++) {
                                Object[] obj_area = (Object[]) lst_areas.get(i);
                                out.println("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div>");
                        out.print("<b>Tipo Soporte: </b><br>");
                        out.print("<select name='slc_tipoS' id='tipoS-id' required>");
                        out.print("<option value='' style='display:none'>Seleccione Tipo</option>");
                        if (lst_tipoSpt != null) {
                            for (int i = 0; i < lst_tipoSpt.size(); i++) {
                                Object[] obj_tipoS = (Object[]) lst_tipoSpt.get(i);
                                if (Integer.parseInt(obj_tipoS[0].toString()) > 1) {
                                    out.println("<option value='" + obj_tipoS[0] + "'>" + obj_tipoS[1] + "</option>");
                                } else {
                                    out.println("<option value='" + obj_tipoS[0] + "'>" + obj_tipoS[1] + "</option>");

                                }
                            }
                        }
                        out.print("</select>");
                        out.print("</div>");
                        if (id_rol == 5) {
                            out.print("<div>");
                            lst_aplicativos = jpa_aplicativo.consultarAplicativos();
                            out.print("<b>Aplicativo: </b><br>");
                            out.print("<select name='slc_aplicativo' id='aplicativo-id' data-live-search='true' required>");
                            out.print("<option value='' style='display:none'>Seleccione App</option>");
                            for (int i = 0; i < lst_aplicativos.size(); i++) {
                                Object[] obj_aplicativos = (Object[]) lst_aplicativos.get(i);
                                out.println("<option value='" + obj_aplicativos[0] + "'>" + obj_aplicativos[1] + "</option>");
                            }
                            out.print("</select>");
                            out.print("</div>");
                        } else {
                            lst_equipos = jpa_equipo.consultaEquipos();
                            lst_listaEquipos = jpa_listaEquipo.consultaListaDetalleVerificacionGeneral();
                            out.print("<div>");
                            out.print("<b>PC: </b><br>");
                            out.print("<select name='slc_equipo' id='equipo-id' data-live-search='true' required>");
                            out.print("<option value='' style='display:none'>Seleccione PC</option>");
                            for (int i = 0; i < lst_equipos.size(); i++) {
                                Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                                out.println("<option value='" + obj_equipos[0] + "'>" + obj_equipos[1] + "</option>");
                            }
                            out.print("</select>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<b>Listado de  Equipo: </b><br>");
                            out.print("<select name='slc_l_equipo' id='equipo-id' data-live-search='true' required>");
                            out.print("<option value='' style='display:none'>Seleccione Equipo</option>");
                            for (int k = 0; k < lst_listaEquipos.size(); k++) {
                                Object[] obj_Listequipos = (Object[]) lst_listaEquipos.get(k);
                                out.println("<option value='" + obj_Listequipos[0] + "'>" + obj_Listequipos[3] + "</option>");
                            }
                            out.print("</select>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("<div style='display:flex; justify-content:space-around; height:55px;'>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Prod: </b>");
                        out.print("<input type='number' class='form-control' name='txt_prodPrd' id='produccion-id' min='0' placeholder='Produccion' required>");
                        out.print("</div>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Equipo: </b>");
                        out.print("<input type='number' class='form-control' name='txt_equipoPrd' id='equipo-id' min='0' placeholder='Equipo'  required>");
                        out.print("</div>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Fecha Inicio: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaI' id='start' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha inicio' required></td>");
                        out.print("</div>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Hora Inicio: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' placeholder='Hora Inicio' required><br>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div style='display:flex; justify-content:space-around; height:55px; width:100%'>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Fecha Ejecucion: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaE' id='datepicker' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha fin' required><br>");
                        out.print("</div>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Hora Ejecucion: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaE' id='horaF-id' placeholder='Hora Inicio' required><br>");
                        out.print("</div>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Fecha Fin: </b><br>");
                        out.print("<input type='text' class='form-control' name='txt_fechaF' id='end' value='" + (fecha.getYear() + 1900) + "-" + (((fecha.getMonth() + 1) < 10) ? "0" : "") + "" + (fecha.getMonth() + 1) + "-" + ((fecha.getDate() < 10) ? "0" : "") + "" + fecha.getDate() + "' autocomplete='off' placeholder='Fecha fin' required>");
                        out.print("</div>");
                        out.print("<div style='width:25%'>");
                        out.print("<b>Hora Fin: </b><br>");
                        out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' placeholder='Hora Inicio' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='modal-body' style='padding:6px;'>");
                        out.print("<textarea  id='editor' contenteditable='false' name='txt_actividad'>"
                                + "<p>Asunto</p>"
                                + "<p>*</p>"
                                + "<hr>"
                                + "<div contetenditable='false'>Solución:</div>"
                                + "<p>*</p>"
                                + "</textarea>");
                        out.print("</div>");
                        out.print("<div class='modal-footer'>");
                        if (documento != 0 && codigo != 0) {
                            List lst_firma = jpa_actividadR.consultaFirmaUsuario(documento, codigo);
                            out.print("<input type='hidden' name='txt_codigo' value='" + codigo + "' required>");
                            out.print("<input type='hidden' name='txt_documento' value='" + documento + "' required>");
                            if (lst_firma != null) {
                                Object[] obj_firma = (Object[]) lst_firma.get(0);
                                out.print("<input type='hidden' name='txt_firma' value='" + ((obj_firma[3] != null) ? "1" : "") + "' required>");
                            } else {
                                out.print("<input type='hidden' name='txt_firma' value='' required>");
                            }
                        } else {
                            out.print("<input type='hidden' name='txt_codigo' value='' required>");
                            out.print("<input type='hidden' name='txt_documento' value='' required>");
                            out.print("<input type='hidden' name='txt_firma' value='' required>");
                        }
                        out.print("<input type='submit' value='Registrar'>");
                        out.print("<div style='float:left; margin:0px; margin-left:20px;' >");
                        out.print("<a href='#' class='icon' title='Firma' data-toggle='modal' onclick='mostrarConvencion(1)'><i class='fa fa-signature fa-lg'></i></a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="FIRMA">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1'  style='opacity: 1.03;  display:block;'>");
                        out.print("<div style='width:66%;margin:32%;margin-top:5%;'>");
                        out.print("<div class='modal-dialog' style='width:55%'>");
                        out.print("<div class='modal-content'>");
                        out.print("<div class='modal-header'>");
                        if (codigo != 0 && !user_name.equals("")) {
                            out.print("<a onclick='mostrarConvencion(1)' class='close'>&times;</a>");
                        } else if (documento == 0) {
                            out.print("<a href='Actividad?opc=1&idA=0&mod=AcR' class='close'>&times;</a>");
                        } else {
                            out.print("<a href='Actividad?opc=1&idA=0&mod=AcR' class='close'>&times;</a>");
                        }
                        out.print("<h4 class='modal-title'>Firma Reportante</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body'>");
                        List lst_firma = jpa_caso.Traer_firmas_codigo(codigo);
                        if (codigo != 0) {
                            if (lst_firma != null) {
                                //<editor-fold defaultstate="collapsed" desc="Mostrar firma Personal SIRH">
                                out.print("<form action='Actividad?opc=1&mod=AcR&val=1' name='formA' id='formA' method='post' style='margin:0px;'>");
                                out.print("<center>");
                                out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigo != 0) ? codigo : "") + "' placeholder='Codigo' style='width:90%;margin: 0px;'  required>&nbsp;&nbsp;&nbsp;");
                                out.print("<input type='submit' value='Buscar' style='width:150px; margin:12px; margin-left:58%;'>");
                                out.print("</center>");
                                //pad firma
                                Object[] obj_firma = (Object[]) lst_firma.get(0);
                                if (obj_firma[3] != null) {
                                    out.print("<div class='sigPad signed' style='width:100%;height:38%'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<div class='codigo' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("<canvas class='pad' width='440' height='250'></canvas>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('.sigPad').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [1,1]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                } else {
                                    out.print("<center><h4><b style='color:orange;'>El usuario No tiene firma registrada</b></h4><center>");
                                }
                                out.print("</form>");
                                //</editor-fold>
                            } else {
                                out.print("<b>Registrar Firma</b><br/>");
                                //<editor-fold defaultstate="collapsed" desc="generar firma">
                                out.print("<form action='Actividad?opc=6' name='formA' id='formA' method='post' style='margin:0px;'>");
                                out.print("<center>");
                                out.print("<div style='display:flex; width:100%'>");
                                out.print("<div style='width:50%'>");
                                out.print("<b>Documento</b>");
                                out.print("<input type='number' class='form-control' name='txt_documento' id='documento-id' value='' placeholder='Documento' style='width:90%;margin: 0px;'  required>&nbsp;&nbsp;&nbsp;");
                                out.print("</div>");
                                out.print("<div style='width:50%'>");
                                out.print("<b>Codigo</b>");
                                out.print("<input type='number' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigo != 0) ? codigo : "") + "' placeholder='Codigo' style='width:90%;margin: 0px;'  required>&nbsp;&nbsp;&nbsp;");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='submit' value='Registar'>");
                                out.print("</center>");
                                out.print("<div class='sigPad' id='smoothed' style='width:100%;'>");
                                out.print("<ul class='sigNav' style='display: block;'>");
                                out.print("<li class='clearButton' style='display: list-item;'><a href='#clear'>Borrar</a></li>");
                                out.print("</ul>");
                                out.print("<div class='sig sigWrapper current' style='height: auto; display: block;'>");
                                out.print("<div class='codigo' style='display: block;'>0</div>");
                                out.print("<canvas class='pad' width=440' height='250'></canvas>");
                                out.print("<input type='hidden' name='txt_firma' class='output' value='' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<script>");
                                out.print("$(document).ready(function () {");
                                out.print("$('#smoothed').signaturePad(");
                                out.print("{");
                                out.print("drawOnly: true,");
                                out.print("drawBezierCurves:true,");
                                out.print("lineTop: 200,");
                                out.print("bgColour : 'transparent',");
                                out.print("penColour : '#292929'");
                                out.print("}");
                                out.print(")});");
                                out.print("</script>");
//</editor-fold>
                            }
//</editor-fold>
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        if (codigo != 0) {
                            out.print("<script>");
                            out.print("$(\"#Registrar,#Firma\").modal(\"show\");");
                            out.print("</script>");
                        }
                    }
                    //<editor-fold defaultstate="collapsed" desc="FIRMA">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1'  style='opacity: 1.03;  display:none;'>");
                    out.print("<div style='width:66%;margin:32%;margin-top:5%;'>");
                    out.print("<div class='modal-dialog' style='width:55%'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    if (codigo != 0) {
                        out.print("<a onclick='mostrarConvencion(1)' class='close'>&times;</a>");
                    } else {
                        out.print("<a href='Actividad?opc=1&idA=0&mod=AcR' class='close'>&times;</a>");
                    }
                    out.print("<h4 class='modal-title'>Firma Reportante</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<form action='Actividad?opc=1&mod=AcR&val=1' name='formA' id='formA' method='post' style='margin:0px;'>");
                    out.print("<center>");
                    out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigo != 0) ? codigo : "") + "' placeholder='Codigo' style='width:90%;margin: 0px;'  required>&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='submit' value='Buscar' style='width:150px; margin:12px; margin-left:58%;'>");
                    out.print("</center>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    if (codigo != 0) {
                        out.print("<script>");
                        out.print("$(\"#Registrar,#Firma\").modal(\"show\");");
                        out.print("</script>");
                    }
                    //</editor-fold>
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="modificar actividad">
                    lst_actividad = jpa_actividadR.consultaActividadReportante(id_actividad);
                    Object[] obj_actividad = (Object[]) lst_actividad.get(0);
                    String[] horaI = obj_actividad[9].toString().replace("|", "-").split("-");
                    String[] horaE = obj_actividad[10].toString().replace("|", "-").split("-");
                    String[] horaF = obj_actividad[11].toString().replace("|", "-").split("-");
                    out.print("<div class='sweet-local' tabindex='-1'  style='opacity: 1.03;  display:block;'>");
                    out.print("<div style='width:66%;margin:auto;margin-top:1%;'>");
                    out.print("<div class='modal-dialog modal-lg' style='width: 977px'>");
                    out.print("<div class='modal-content'>");
                    out.print("<form action='Actividad?opc=5' name='formA' method='post'>");
                    out.print("<input type='hidden' name='idA' value='" + id_actividad + "' id='idA'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Actividad?opc=1&idA=0&mod=AcR' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center' style='padding:2px'>");
                    out.print("<div style='display:flex; justify-content:space-around; height:55px;'>");
                    out.print("<div>");
                    out.print("<b>Reportante: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_reportante' id='reportante-id' value='" + obj_actividad[1] + "' placeholder='Reportante' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Area:</b><br>");
                    out.print("<select name='slc_area' id='area-id' data-live-search='true' required>");
                    if (obj_actividad[22] == null) {
                        out.print("<option value='' style='display:none'>" + ((obj_actividad[23] == null) ? "SELECCIONAR AREA" : "") + "</option>");
                    } else {
                        out.print("<option value='" + obj_actividad[22] + "' style='display:none'>" + obj_actividad[23] + "</option>");
                    }
                    for (int i = 0; i < lst_areas.size(); i++) {
                        Object[] obj_area = (Object[]) lst_areas.get(i);
                        out.println("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<b>Tipo Soporte: </b><br>");
                    out.print("<select name='slc_tipoS' id='tipoS-id' required>");
                    out.print("<option value='" + obj_actividad[5] + "' style='display:none'>" + obj_actividad[6] + "</option>");
                    for (int i = 0; i < lst_tipoSpt.size(); i++) {
                        Object[] obj_tipoS = (Object[]) lst_tipoSpt.get(i);
                        if (Integer.parseInt(obj_tipoS[0].toString()) > 1) {
                            out.println("<option value=" + obj_tipoS[0] + ">" + obj_tipoS[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    if (id_rol == 5) {
                        out.print("<div>");
                        lst_aplicativos = jpa_aplicativo.consultarAplicativos();
                        out.print("<b>Aplicativo: </b>");
                        out.print("<select name='slc_aplicativo' id='aplicativo-id' data-live-search='true' required>");
                        out.print("<option value='" + obj_actividad[7] + "' style='display:none'>" + obj_actividad[8] + "</option>");
                        for (int i = 0; i < lst_aplicativos.size(); i++) {
                            Object[] obj_aplicativos = (Object[]) lst_aplicativos.get(i);
                            out.println("<option value='" + obj_aplicativos[0] + "'>" + obj_aplicativos[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div>");
                        lst_equipos = jpa_equipo.consultaEquipos();
                        lst_listaEquipos = jpa_listaEquipo.consultaListaDetalleVerificacionGeneral();
                        out.print("<b>PC: </b><br>");
                        out.print("<select name='slc_equipo' id='equipo-id' data-live-search='true' required>");
                        out.print("<option value='" + obj_actividad[2] + "' style='display:none'>" + obj_actividad[3] + "</option>");
                        for (int i = 0; i < lst_equipos.size(); i++) {
                            Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                            out.println("<option value='" + obj_equipos[0] + "'>" + obj_equipos[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div>");
                        out.print("<b>Equipo: </b><br>");
                        out.print("<select name='slc_l_equipoM' id='equipo-id' data-live-search='true' style='width:35%;' required>");
                        out.print("<option value='" + obj_actividad[20] + "' style='display:none'>" + obj_actividad[21] + "</option>");
                        for (int k = 0; k < lst_listaEquipos.size(); k++) {
                            Object[] obj_Listequipos = (Object[]) lst_listaEquipos.get(k);
                            out.println("<option value='" + obj_Listequipos[0] + "'>" + obj_Listequipos[3] + "</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("<div style='display:flex; justify-content:space-around; height:55px;'>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Prod: </b><br>");
                    out.print("<input type='number' class='form-control' name='txt_prodPrd' id='produccion-id' min='0' value='" + obj_actividad[19] + "' placeholder='Produccion'  required>");
                    out.print("</div>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Equipo: </b><br>");
                    out.print("<input type='number' class='form-control' name='txt_equipoPrd' id='equipo-id' min='0' value='" + obj_actividad[18] + "' placeholder='Equipo' required>");
                    out.print("</div>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Fecha Inicio: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaI' id='start' value='" + horaI[0] + "' autocomplete='off' placeholder='Fecha inicio' required><br>");
                    out.print("</div>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Hora Inicio: </b><br>");
                    out.print("<input type='time'  class='form-control' name='txt_horaI' id='horaI-id' value='" + horaI[1] + "' placeholder='Hora Inicio' required><br>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='display:flex; justify-content:space-around; height:55px;'>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Fecha Ejecucion: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaE' id='end' value='" + horaE[0] + "' autocomplete='off' placeholder='Fecha fin' required><br>");
                    out.print("</div>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Hora Ejecucion: </b><br>");
                    out.print("<input type='time'  class='form-control' name='txt_horaE' id='horaF-id' value='" + horaE[1] + "' placeholder='Hora Inicio' required><br>");
                    out.print("</div>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Fecha Fin: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaF' id='end' value='" + horaF[0] + "' autocomplete='off' placeholder='Fecha fin' required>");
                    out.print("</div>");
                    out.print("<div style='width:25%'>");
                    out.print("<b>Hora Fin: </b><br>");
                    out.print("<input type='time'  class='form-control' name='txt_horaF' id='horaF-id' value='" + horaF[1] + "' placeholder='Hora Inicio' required>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<textarea id='editor'  name='txt_actividad'>" + obj_actividad[12].toString() + "<hr>" + obj_actividad[13].toString() + "</textarea>");
                    out.print("</div>");
                    out.print("<div class='modal-footer' style='padding: 2px 16px 4px 0px;'>");
                    out.print("<input type='submit' value='Modificar'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                if (lst_actividades != null) {
                    //<editor-fold defaultstate="collapsed" desc="Consulta actividades reportadas">
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<div style='height:89%; width:100%; max-height:89%; overflow:auto;'>");
                    out.print("<table class='table' id='resultados'>");
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividades.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='5' style='background-color: #ddd;'></d>");
                        out.print("</tr>");

                        out.print("<tr>");
                        out.print("<td style='width:20%'><b class='title'>Fecha: </b>" + obj_actividades[15] + "</td>");
                        out.print("<td style='width:25%'><b class='title'>Reportante: </b>" + obj_actividades[1] + "</td>");
                        out.print("<td style='width:30%'><b class='title'>Area: </b>" + ((obj_actividades[20] == null) ? "N/A" : obj_actividades[20]) + "</td>");
                        out.print("<td style='width:25%'><b class='title'>Tecnico: </b>" + obj_actividades[19] + "</td>");
                        if (id_usuario == (Integer) obj_actividades[14]) {
//                            out.print("<td rowspan='5'style='width:5%' align='center'><a href='Actividad?opc=1&idA=" + obj_actividades[0] + "&mod=AcR' class='icon' title='Modificar'><i class='fa fa-pencil-alt fa-lg'></i></a></td>");
                        } else {
                            out.print("<td rowspan='5' style='width:5%' align='center'><i style='color:#b2b4b7' title='Sin permiso' class='fa fa-pencil-alt fa-lg'></i></td>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
//                        out.print("<td style='width:23%'><b class='title'>Reportante: </b>" + obj_actividades[1] + "</td>");
                        out.print("<td style='width:20%'><b class='title'>Tipo Soporte: </b>" + obj_actividades[6] + "</td>");
                        if (id_rol == 5) {
                            out.print("<td  colspan='2' style='width:25%'><b class='title'>Aplicativo: </b>" + ((obj_actividades[8] == null) ? "N/A" : obj_actividades[8]) + "</td>");
                        } else {
                            out.print("<td style='width:25%'><b class='title'>PC: </b>" + ((obj_actividades[3] == null) ? "N/A" : obj_actividades[3]) + "</td>");
                            out.print("<td style='width:30%'><b class='title'>Equipo: </b>" + ((obj_actividades[18] == null) ? "N/A" : obj_actividades[18]) + "</td>");
                        }
                        out.print("<td style='width:25%' align='top'><b>Parada Equipo: " + obj_actividades[16] + "<br>Produccion: " + obj_actividades[17] + "</b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td colspan='2'style='width:18%' valign='top'>" + obj_actividades[12] + "</td>");
                        out.print("<td colspan='2'style='width:18%' valign='top'>" + obj_actividades[13] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td><b class='title'>Fecha Inicio: </b>" + obj_actividades[9] + "</td>");
                        out.print("<td colspan='2'><b class='title'>Fecha Ejecucion: </b>" + obj_actividades[10] + "</td>");
                        out.print("<td><b class='title'>Fecha Fin: </b>" + obj_actividades[11] + "</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',50);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<br><b class='title'>No se encontraron resultados</b>");
//</editor-fold>
                }
            }
            out.print("<script>");
            out.print("$('#summernote').summernote({");
            out.print("placeholder: 'Descripción',");
            out.print("tabsize: 2,");
            out.print("height: 200");
            out.print("});");
            out.print("</script>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_actividades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
