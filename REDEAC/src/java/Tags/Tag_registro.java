package Tags;

import Controladoras.ActividadReportadaJpaController;
import Controladoras.AplicativoJpaController;
import Controladoras.AreaJpaController;
import Controladoras.CasoJpaController;
import Controladoras.CronogramaJpaController;
import Controladoras.ListasVerificacionJpaController;
import Controladoras.SeguimientoActividadJpaController;
import Controladoras.RegistroJpaController;
import SQL.Connection_mysql_sirh;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_registro extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int documentoU = Integer.parseInt(pageContext.getSession().getAttribute("Documento").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String nombreUsa = pageContext.getSession().getAttribute("Nombre_apellido").toString();
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        String modulo = pageContext.getRequest().getAttribute("Registro").toString();
        AreaJpaController jpa_area = new AreaJpaController();
        ActividadReportadaJpaController jpa_actividadR = new ActividadReportadaJpaController();
        SeguimientoActividadJpaController jpa_seguimiento = new SeguimientoActividadJpaController();
        ListasVerificacionJpaController jpa_lstVer = new ListasVerificacionJpaController();
        AplicativoJpaController jpa_aplicativo = new AplicativoJpaController();
        CronogramaJpaController jpa_cronograma = new CronogramaJpaController();
        RegistroJpaController jpa_registro = new RegistroJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        Connection_mysql_sirh ConsultSirh = new Connection_mysql_sirh();
        List lst_areas = jpa_area.consultarAreas();
        List lst_actividadesR = null;
        List lst_actividades = null;
        List lst_verificacion = null;
        List lst_seguimientosE = null;
        List lst_cronograma = null;
        List lst_casos = null;
        List lst_digitalizacion = null;
        List lst_digitalizacionM = null;
        List lst_programacion = null;
        List lst_actaM = null;
        List lst_acta = null;
        List lst_registro = null;
        List lst_registroP = null;
        List lst_plantilla = null;
        List lst_rp = null;
        List lst_consultarSirh = null;
        int id_registro = 0, documentou = 0, codigou = 0, con_acta = 0;
        boolean acta = false;
        int id_area = 0;
        int tipo = 0, anio = 0, id_actaA = 0, fto = 0;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String fechaI = "", fechaF = "", fechaA = "", asuntoA = "";
        try {
            if (modulo.equals("R005")) {
                //<editor-fold defaultstate="collapsed" desc="registro 005">
                int id_programacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_programacion").toString());
                int id_seguimiento = Integer.parseInt(pageContext.getRequest().getAttribute("id_seguimiento").toString());
                out.print("<div style='float:right;'>");
                out.print("<a href='#' data-toggle='modal' data-target='#Registrar'><i class='fa fa-plus fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("</div>");
                out.print("<h3>R-TI-005</h3>");
                if (id_seguimiento == 0) {
                    //<editor-fold defaultstate="collapsed" desc="registrar programacion">
                    out.print("<div class='modal fade' id='Registrar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog modal-lg' style='width:70%'>");
                    out.print("<div class='modal-content' style='height:87%;overflow-y:scroll;'>");
                    out.print("<form action='Registro?opc=3' name='formA' method='post'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Registro?opc=1&mod=R005' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='width:90%'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Actividad: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_actividad' id='actividad-id' placeholder='Asunto' style='width:90%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    int año = year - 1990;
                    out.print("<b>Semana: </b><br>");
                    out.print("<select name='slc_semana' id='semana-id' data-live-search='true' required>");
                    out.print("<option value='' style='display:none'>Seleccione Semana</option>");
                    for (int i = 1; i < 53; i++) {
                        out.print("<option>Semana " + año + "" + i + "</option>");
                    }
                    out.print("</select>");
                    out.print("</td>");
                    out.print("<td align='center'>");
                    out.print("<input type='submit' value='Registrar'>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<input type='hidden' id='equipos' name='txt_equipos' value='' required>");
                    lst_verificacion = jpa_lstVer.consultaListasVerificacionR5();
                    if (lst_verificacion != null) {
                        //<editor-fold defaultstate="collapsed" desc="LISTADO DE EQUIPOS">
                        out.print("<div class='panel-group' id='accordion'>");
                        for (int i = 0; i < lst_verificacion.size(); i++) {
                            Object[] obj_lstVer = (Object[]) lst_verificacion.get(i);
                            out.print("<div class='panel panel-default' style='text-align: left;'>");
                            out.print("<div class='panel-heading'>");
                            out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#collapse" + i + "'>" + obj_lstVer[1] + "</a></h4>");
                            out.print("</div>");
                            out.print("<div id='collapse" + i + "' class='panel-collapse collapse'>");
                            out.print("<div class='panel-body' style='overflow-y: scroll;'>");
                            if (obj_lstVer[2] != null) {
                                String[] equipos = obj_lstVer[2].toString().replace("][", "---").replace("]", "").replace("[", "").split("---");
                                int cont = 0;
                                out.print("<table class='table'>");
                                for (int j = 0; j < equipos.length; j++) {
                                    if (cont == 0) {
                                        out.print("<tr>");
                                    }
                                    out.print("<td>");
                                    out.print("<label class='control control-checkbox' style='margin-right: 10px;'><input type='checkbox' name='cbx_equipo" + i + "' id='cbx_equipo" + i + "' value='" + equipos[j] + "' onclick='RegistroE(this)'><div class='control_indicator'></div></label>");
                                    out.print("<b class='" + ((equipos[j].contains("/B")) ? "verde" : ((equipos[j].contains("/R")) ? "naranja" : "rojo")) + "'>" + equipos[j].split("/")[0] + "</b>");
                                    out.print("</td>");
                                    cont++;
                                    if (cont == 8) {
                                        out.print("</tr>");
                                        cont = 0;
                                    }
                                }
                                out.print("</table>");
                            } else {
                                out.print("<b>No se encuentran resultados</b>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                    } else {
                        out.print("<b>No se encontro resultados</b>");
                    }
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>    
                } else {
                    // <editor-fold defaultstate="collapsed" desc="Modificar programacion">
                    lst_programacion = jpa_seguimiento.consultaActividadesProgramadasId(id_seguimiento);
                    Object[] obj_programacion = (Object[]) lst_programacion.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Modificar' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:964px; height:502px; position: absolute;top:9%; overflow-y:scroll; left:20%;text-align:left '>");
                    out.print("<form action='Registro?opc=18' name='formA' method='post'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Registro?opc=1&mod=R005' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("</div>");
                    out.print("<input type='hidden' id='idS' name='idS' value='" + id_seguimiento + "' required>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='width:90%'>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Actividad: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_actividad' id='actividad-id' value='" + obj_programacion[1] + "' placeholder='Asunto' style='width:90%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td>");
                    out.print("<td>");
                    int añoM = year - 1990;
                    out.print("<b>Semana: </b><br>");
                    out.print("<select name='slc_semana' id='semana-id' data-live-search='true' required>");
                    out.print("<option value='" + obj_programacion[2] + "' style='display:none'>" + obj_programacion[2] + "</option>");
                    for (int i = 1; i < 53; i++) {
                        out.print("<option>Semana " + añoM + "" + i + "</option>");
                    }
                    out.print("</select>");
                    out.print("</td>");
                    out.print("<td align='center'>");
                    out.print("<input type='submit' value='Modificar'>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("<input type='hidden' id='equipos' name='txt_equipos' value='' required>");
                    lst_verificacion = jpa_lstVer.consultaListasVerificacionR5();
                    if (lst_verificacion != null) {
                        //<editor-fold defaultstate="collapsed" desc="LISTADO DE EQUIPOS">
                        out.print("<div class='panel-group' id='accordionM'>");
                        for (int i = 0; i < lst_verificacion.size(); i++) {
                            Object[] obj_lstVer = (Object[]) lst_verificacion.get(i);
                            out.print("<div class='panel panel-default' style='text-align: left;'>");
                            out.print("<div class='panel-heading'>");
                            out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordionM' href='#collapse" + i + "'>" + obj_lstVer[1] + "</a></h4>");
                            out.print("</div>");
                            out.print("<div id='collapse" + i + "' class='panel-collapse collapse'>");
                            out.print("<div class='panel-body' style='overflow-y: scroll;'>");
                            if (obj_lstVer[2] != null) {
                                String[] equipos = obj_lstVer[2].toString().replace("][", "---").replace("]", "").replace("[", "").split("---");
                                int cont = 0;
                                out.print("<table class='table'>");
                                for (int j = 0; j < equipos.length; j++) {
                                    if (cont == 0) {
                                        out.print("<tr>");
                                    }
                                    out.print("<td>");
                                    out.print("<label class='control control-checkbox' style='margin-right: 10px;'><input type='checkbox' name='cbx_equipo" + i + "' id='cbx_equipo" + i + "' value='" + equipos[j] + "' onclick='RegistroE(this)'><div class='control_indicator'></div></label>");
                                    out.print("<b class='" + ((equipos[j].contains("/B")) ? "verde" : ((equipos[j].contains("/R")) ? "naranja" : "rojo")) + "'>" + equipos[j].split("/")[0] + "</b>");
                                    out.print("</td>");
                                    cont++;
                                    if (cont == 8) {
                                        out.print("</tr>");
                                        cont = 0;
                                    }
                                }
                                out.print("</table>");
                            } else {
                                out.print("<b>No se encuentran resultados</b>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                    } else {
                        out.print("<b>No se encontro resultados</b>");
                    }
                    //</editor-fold>
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                lst_actividades = jpa_seguimiento.consultaActividadesProgramadas();
                if (lst_actividades != null) {
                    out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<div><input type='text' class='form-control' style='margin:5px' class='form-control' name='txt_bus'  id='Txt_filtro' onkeyup='FiltrarLst()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                    out.print("</div>");
                    out.print("<div id='testTable'>");
                    out.print("<div style='width: 100%; height:83%; max-width: 100%; max-height:87%; overflow:auto'>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4' >Actividad</th>");
                    out.print("<th class='sticky4'>Semana</th>");
                    out.print("<th class='sticky4'>Fecha</th>");
                    out.print("<th class='sticky4'>Usuario</th>");
                    out.print("<th class='sticky4'>Estado</th>");
                    if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                        out.print("<th class='sticky4'>Opc</th>");
                    }
                    out.print("</tr>");
                    for (int i = 0; i < lst_actividades.size(); i++) {
                        Object[] obj_actvidades = (Object[]) lst_actividades.get(i);
                        out.print("<tr>");
                        out.print("<td><a href='Registro?opc=1&mod=R005&idP=" + obj_actvidades[0] + "'>" + obj_actvidades[1] + "</a></td>");
                        out.print("<td>" + obj_actvidades[2] + "</td>");
                        out.print("<td>" + obj_actvidades[3] + "</td>");
                        out.print("<td>" + obj_actvidades[4] + "</td>");
                        out.print("<td style='width:15%'>");
                        out.print("<div class='pb_informe'>");
                        double estado = 0;
                        if (obj_actvidades[9] == null) {
                            estado = 0;
                        } else {
                            estado = Double.parseDouble(obj_actvidades[9].toString());
                        }
                        out.print("<div class='pb_informe_det " + ((estado == 0) ? "" : (estado <= 59) ? "red" : (((estado <= 80)) ? "orange" : (((estado <= 99) ? "green" : "purple")))) + "' style='width:" + estado + "%;text-align:center;padding:3px'>" + estado + "%</div>");
                        out.print("</div>");
                        out.print("</td>");
                        if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                            out.print("<td><a href='Registro?opc=1&mod=R005&idS=" + obj_actvidades[0] + "'><i class='icon'><i class='fas fa-pencil-alt fa-lg' title='Modificar adjunto'></i></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    //<editor-fold defaultstate="collapsed" desc="R-TI-005">
                    if (id_programacion != 0) {
                        lst_seguimientosE = jpa_seguimiento.consultaSeguimientosEquipos(id_programacion);
                        out.print("<div class='modal fade' id='registro005' role='dialog' data-backdrop='static' data-keyboard='false'>");
                        out.print("<div class='modal-dialog modal-lg' style='width:85%'>");
                        out.print("<div class='modal-content' style='height:87%;overflow-y:scroll;'>");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Registro?opc=1&mod=R005' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>R-TI-005 </h4>");
                        out.print("<br><br><a href='#' onclick='Imprimir(1);HabilitarModal();'  title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b><br>");
                        out.print("</div>");
//                        out.print("<div id='Hardware' class='tab-pane fade in active' style='overflow:scroll;'>");
                        out.print("<div class='modal-body' align='center'>");
                        if (lst_seguimientosE != null) {
                            Object[] obj_seguimientoE = (Object[]) lst_seguimientosE.get(0);
                            out.print("<table class='table'>");
                            out.print("<tr> <td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:30%;' colspan='2' rowspan='2'>");
                            out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:82%' /></td>");
                            out.print("<td colspan='4' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
                            out.print("<td align='center' style='width:50%;'><b class='negro'>CODIGO<br> R-TI-005 </b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='4' align='center'><b class='negro'>SEGUIMIENTO ACTIVIDADES EQUIPOS</b></td>");
                            out.print("<td align='center'><b class='negro'>VERSIÓN: 000</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='7'>ACTIVIDAD A REALIZAR: <b>" + obj_seguimientoE[8] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th class='sticky2' style='width: 15%;'># EQUIPO</th>");
                            out.print("<th class='sticky2' style='width: 10%;'>FECHA PROGRAMADA</th>");
                            out.print("<th class='sticky2' style='width: 25%;'>VERIFICACION ANTES DE LA ACTIVIDAD</th>");
                            out.print("<th class='sticky2' style='width: 10%;'>FECHA ACTIVIDAD</th>");
                            out.print("<th class='sticky2' style='width: 15%;'>PERSONA QUE REALIZA LA ACTIVIDAD</th>");
                            out.print("<th class='sticky2' style='width: 25%;'colspan='2'>VERIFICACION DESPUES DE LA ACTIVIDAD</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_seguimientosE.size(); i++) {
                                Object[] obj_seguimientosE = (Object[]) lst_seguimientosE.get(i);
                                String[] equipos = obj_seguimientosE[1].toString().split("/");
                                out.print("<tr>");
                                out.print("<td>" + equipos[0] + "</td>");
                                out.print("<td>" + obj_seguimientoE[9] + "</td>");
                                out.print("<td " + ((obj_seguimientosE[2] != null) ? ">" + obj_seguimientosE[2] + "" : "align='center'><a href='#' onclick='ejecutar(" + obj_seguimientosE[0] + "," + id_programacion + ")' ><b class='naranja'>Ejecutar</a></b>") + ""
                                        + "<br />" + ((obj_seguimientosE[3] != null) ? "<b class='verde'>Ejecutado</b>" : "<b class='naranja'>En Proceso</b>") + "</td>");
                                if (id_rol == 3 || id_rol == 4 || id_rol == 1) {
                                    out.print("<center><td align='center'>" + ((obj_seguimientosE[3] != null) ? "<input class='td_fecha form-control' type='text' id='" + obj_seguimientosE[0] + "' value='" + obj_seguimientosE[3] + "'>" : "---") + "</td></center>");
                                } else {
                                    out.print("<td align='center'>" + ((obj_seguimientosE[3] != null) ? "" + obj_seguimientosE[3] + "" : "---") + "</td>");
                                }
                                if (id_rol == 4 || id_rol == 1 || id_rol == 3) {
                                    out.print("<td align='center'>" + ((obj_seguimientosE[4] != null) ? "<input class='td_responsable form-control' type='text' id='" + obj_seguimientosE[0] + "' value='" + obj_seguimientosE[4] + "'>" : "----") + "</td>");
                                } else {
                                    out.print("<td align='center'>" + ((obj_seguimientosE[4] != null) ? obj_seguimientosE[4] : "----") + "</td>");
                                }
                                if (obj_seguimientosE[4] != null) {
                                    if (id_rol == 4 || id_rol == 1 || id_rol == 3) {
                                        out.print("<td colspan='2'><div style='display:flex;align-items: flex-end;'><div>" + ((obj_seguimientosE[5] != null) ? "" + obj_seguimientosE[5] + "<br><input class='td_responsableV form-control' type='text'  id='" + obj_seguimientosE[0] + "' value='" + obj_seguimientosE[7] + "'></div>"
                                                + "<div><input class='td_fechaV form-control' type='text' id='" + obj_seguimientosE[0] + "' value='" + obj_seguimientosE[6] + "'>" + "" : "" + ((obj_seguimientosE[3] != null) ? "<a href='#' onclick='verificar(" + obj_seguimientosE[0] + "," + id_programacion + ")' ><b class='naranja'>Verificar</a>" : "<b class='naranja'>Verificar</b>") + "") + "</div></div></td>");
                                    } else {
                                        out.print("<td colspan='2'>" + ((obj_seguimientosE[5] != null) ? "" + obj_seguimientosE[5] + "<br>" + obj_seguimientosE[7] + "-" + obj_seguimientosE[6] + "" : "" + ((obj_seguimientosE[3] != null) ? "<a href='#' onclick='verificar(" + obj_seguimientosE[0] + "," + id_programacion + ")' ><b class='naranja'>Verificar</a>" : "<b class='naranja'>Verificar</b>") + "") + "</td>");
                                    }
                                } else {
                                    out.print("<td " + ((obj_seguimientosE[4] != null) ? " colspan='2' style='width:50%'" : "") + "" + ((obj_seguimientosE[5] != null) ? ">" + obj_seguimientosE[5] + "<hr>" + obj_seguimientosE[7] + "-" + obj_seguimientosE[6] : " align='center'><b class='naranja'>En proceso</b>") + "</td>");
                                }
                                if (obj_seguimientosE[3] == null) {
                                    out.print("<td align='center'><a href='#' onclick='EliminarE(" + obj_seguimientosE[0] + "," + id_programacion + ")' style='color:#292929'><i class='far fa-trash-alt fa-lg'></i></a></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        } else {
                            out.print("<b>No se encuentra resultados</b>");
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<script>");
                        out.print("$('#registro005').modal('show');");
                        out.print("</script>");
                        out.print("<div style='width:100%' id='Imprimir1'>");
                        //<editor-fold defaultstate="collapsed" desc="R-TI-005 IMPRIMIR">
                        out.print("<div class='modal-body'  id='R005' style='z-index:-1;display:block' align='center'>");
                        if (lst_seguimientosE != null) {
                            Object[] obj_seguimientoE = (Object[]) lst_seguimientosE.get(0);
                            out.print("<table class='table'>");
                            out.print("<tr> <td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                            out.print("<tr>");
                            out.print("<td align='center' style='width:30%;' colspan='2' rowspan='2'>");
                            out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:82%' /></td>");
                            out.print("<td colspan='4' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
                            out.print("<td align='center' style='width:50%;'><b class='negro'>CODIGO<br> R-TI-005 </b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='4' align='center'><b class='negro'>SEGUIMIENTO ACTIVIDADES EQUIPOS</b></td>");
                            out.print("<td align='center'><b class='negro'>VERSIÓN: 000</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td colspan='7'>ACTIVIDAD A REALIZAR: <b>" + obj_seguimientoE[8] + "</b></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<th class='sticky2' style='width: 15%;'># EQUIPO</th>");
                            out.print("<th class='sticky2' style='width: 10%;'>FECHA PROGRAMADA</th>");
                            out.print("<th class='sticky2' style='width: 25%;'>VERIFICACION ANTES DE LA ACTIVIDAD</th>");
                            out.print("<th class='sticky2' style='width: 10%;'>FECHA ACTIVIDAD</th>");
                            out.print("<th class='sticky2' style='width: 15%;'>PERSONA QUE REALIZA LA ACTIVIDAD</th>");
                            out.print("<th class='sticky2' style='width: 25%;'colspan='2'>VERIFICACION DESPUES DE LA ACTIVIDAD</th>");
                            out.print("</tr>");
                            for (int i = 0; i < lst_seguimientosE.size(); i++) {
                                Object[] obj_seguimientosE = (Object[]) lst_seguimientosE.get(i);
                                String[] equipos = obj_seguimientosE[1].toString().split("/");
                                out.print("<tr>");
                                out.print("<td>" + equipos[0] + "</td>");
                                out.print("<td>" + obj_seguimientoE[9] + "</td>");
                                out.print("<td " + ((obj_seguimientosE[2] != null) ? ">" + obj_seguimientosE[2] + "" : "align='center'><b class='naranja'>Ejecutar</b>") + ""
                                        + "<br />" + ((obj_seguimientosE[3] != null) ? "<b class='verde'>Ejecutado</b>" : "<b class='naranja'>En Proceso</b>") + "</td>");
                                out.print("<td align='center'>" + ((obj_seguimientosE[3] != null) ? "" + obj_seguimientosE[3] + "" : "---") + "</td>");
                                out.print("<td align='center'>" + ((obj_seguimientosE[4] != null) ? obj_seguimientosE[4] : "----") + "</td>");
                                if (obj_seguimientosE[4] != null && !obj_seguimientosE[4].equals(nombreUsa)) {
                                    out.print("<td colspan='2'>" + ((obj_seguimientosE[5] != null) ? "" + obj_seguimientosE[5] + "<br>" + obj_seguimientosE[7] + "-" + obj_seguimientosE[6] + "" : "" + ((obj_seguimientosE[3] != null) ? "<a href='#' onclick='verificar(\'" + obj_seguimientosE[0] + "\',\'" + id_programacion + "\')' ><b class='naranja'>Verificar</a>" : "<b class='naranja'>Verificar</b>") + "") + "</td>");
                                } else {
                                    out.print("<td " + ((obj_seguimientosE[4] != null) ? " colspan='2' style='width:50%'" : "") + "" + ((obj_seguimientosE[5] != null) ? ">" + obj_seguimientosE[5] + "<hr>" + obj_seguimientosE[7] + "-" + obj_seguimientosE[6] : " align='center'><b class='naranja'>En proceso</b>") + "</td>");
                                }
                                if (obj_seguimientosE[3] == null) {
                                    out.print("<td align='center'><a href='#' onclick='EliminarE(" + obj_seguimientosE[0] + "," + id_programacion + ")' style='color:#292929'><i class='far fa-trash-alt fa-lg'></i></a></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");
                        } else {
                            out.print("<b>No se encuentra resultados</b>");
                        }
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                    }
                    //</editor-fold>
                } else {
                    out.print("<br><b>No se encontraron resultados</b>");
                }
                //</editor-fold>
            }
            if (modulo.equals("ACTA")) {
                //<editor-fold defaultstate="collapsed" desc="ACTAS">
                int id_acta = Integer.parseInt(pageContext.getRequest().getAttribute("idRA").toString());
                int id_rp = Integer.parseInt(pageContext.getRequest().getAttribute("idRP").toString());
                int idRP2 = Integer.parseInt(pageContext.getRequest().getAttribute("idRP2").toString());
                String filtro = pageContext.getRequest().getAttribute("filtro").toString();
                if (filtro.equals("")) {
                    lst_acta = jpa_registro.consultarActas();
                } else {
                    lst_acta = jpa_registro.FiltroActa(filtro);
                }
                if (id_rp != 0) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA DETALLE">
                    lst_rp = jpa_registro.consultaPlantilla(id_rp);
                    if (lst_rp != null) {
                        Object[] obj_regp = (Object[]) lst_rp.get(0);
                        if (obj_regp[4] != null) {
                            //<editor-fold defaultstate="collapsed" desc="EDITAR USAURIOS DE ACTAS">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana22' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_reg' style='width: 40%; height: auto;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Editar Asistentes</h2>");
                            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(22)'><i class='fas fa-times'></i></button>");
                            out.print("</div>");

                            out.print("<button id='btn_cambio' class='btn_in' onclick='HabilitarCampos()'>Externo <i class='fas fa-external-link-alt'></i></button>");

                            out.print("<div style='width: 100%;'>");
                            out.print("<div id='user_in' class='user_in' style='margin-top: 10px;'>");
                            out.print("<div style='display: flex;'>");
                            out.print("<div style='width: 100%;'>");
                            out.print("<input style='width: 100%; border-radius: 8px;' type='text' class='form-control' class='form-control' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' placeholder='Ingresar codigo del empleado' list='Personal'>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<button style='height: 75%;' type='button' id='addRow' class='btn btn-success' onclick='FiltroAvanzado()'><i class='fas fa-plus'></i></button>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div id='Buscar_valores'>");
                            if (obj_regp[4] != null) {
                                if (obj_regp[4].toString().equals("")) {
                                } else {
                                    String[] personal = obj_regp[4].toString().replace("][", "//").replace("]", "").replace("[", "").split("//");
                                    for (int i = 0; i < personal.length; i++) {
                                        if (personal[i].toString().contains("{")) {
                                            out.print("<div style='display: flex;'><input class='form-control' value='" + personal[i] + "' style='text-decoration:none;cursor:pointer;color:black;background:#d8dae9;pointer-events: none;'><button title='Este usuario ya ha firmado el acta, no se puede eliminar' type='button' class='btn btn-success'><i class='fas fa-signature'></i></button></div><br>");
                                        } else {
                                            String datos = personal[i];
                                            out.print("<div style='display: flex;'><input class='form-control' value='" + personal[i] + "' style='text-decoration:none;cursor:pointer;color:black;background:#d8dae9;pointer-events: none;'><a type='button' class='btn btn-danger' onclick=\"FiltroAvanzadoQuitar('" + datos + "')\"><img src ='Interfaz/Fotos/trash-can.png' alt='Logo' width='16'></a></div><br>");
                                        }
                                    }
                                }
                            } else {

                            }
                            out.print("</div>");
                            out.print("<form action='Registro?opc=24&fto=3' method='post'>");
                            //FTO ES UNA VARAIBLE TEMPORA CON LA QUE ESTOY IDENTTIFICADO EL TIPO DE SOLICITUD EN LOS CASOS DEL SERVLET//EN ESTE CASO FTO = 3 ES MODIFICAR LOS USUARIOS
                            out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                            if (obj_regp[4] != null) {
                                out.print("<input type='hidden' name='Txt_valores_filtro' id='Txt_valores_filtro' oninput='javascript:this.value+=document.getElementById('Buscar_valores').innerHTML' value='" + obj_regp[4].toString() + "'/>");
                            }

                            out.print("<datalist id='Personal'><label><select name='Personal'>");
                            lst_consultarSirh = ConsultSirh.Empleado_sirh();
                            if (lst_consultarSirh != null && lst_consultarSirh.size() > 0 && !lst_consultarSirh.isEmpty()) {
                                for (int i = 0; i < lst_consultarSirh.size(); i++) {
                                    String[] Arg_personal = lst_consultarSirh.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                                    out.print("<option value='" + Arg_personal[i] + "'></option>");
                                }
                            } else {
                                out.print("<option value='Error'></option>");
                            }
                            out.print("</select></label></datalist>");
                            out.print("<div id='newRow' style='margin-bottom: 10px;'>");
                            out.print("<input type='hidden' name='idRA'value='" + obj_regp[0] + "'>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<button class='btn_regAc' style='margin-left: 43%; height: 34px;'>Guardar <i class='fas fa-save'></i></button>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");

                            //<editor-fold defaultstate="collapsed" desc="EDITAR PERSONAL EXTERNO">
                            out.print("<div id='user_ex' class='user_ex'>");
                            out.print("<form action='Registro?opc=24&fto=4' method='post'>");
                            out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                            out.print("<div style='display: flex; width: 100%;margin-top: 10px;justify-content: right;'>");
                            out.print("<div style='width: 48%;margin-top: 10px;'>");
                            out.print("<input type='number' class='form-control' class='form-control' name='documento_ex' placeholder='Documento' value='' required>");
                            out.print("<input type='text' style='margin-top: 10px;' class='form-control' name='nombre_ex' placeholder='Nombres y apellidos' value='' required>");
                            out.print("</div>");
                            out.print("<div style='width: 48%;margin-top: 10px;'>");
                            out.print("<input type='text' class='form-control' class='form-control' name='cargo_ex' placeholder='Cargo' value='' required>");
                            out.print("<input type='number' style='margin-top: 10px;' class='form-control' name='codigo_ex' placeholder='Codigo' value='' required>");
                            out.print("<input type='hidden' class='form-control' name='firmas_ex' placeholder='' value='XXFIRMASXX'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div style='height: 40px; text-align: center; margin-top: 10px;'>");
                            out.print("<button class='btn_regAc'>Guardar <i class='fas fa-save'></i></button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");

                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>

                            out.print("</div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="AGREGAR USUARIOS A ACTAS">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana21' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='cont_reg' style='width: 40%; height: auto;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Agregar Asistentes</h2>");
                            out.print("<button class='btn_clsRg' onclick='mostrarConvencion(21)'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<button id='btn_cambio' class='btn_in' onclick='HabilitarCampos()'>Externo <i class='fas fa-external-link-alt'></i></button>");
                            out.print("<div style='width: 100%; margin-top: 10px;' id='user_in'>");
                            //<editor-fold defaultstate="collapsed" desc="REGISTRO DE USUARIOS INTERNOS">                            
                            out.print("<div style='display: flex;'>");
                            out.print("<div style='width: 100%;'>");
                            out.print("<input style='width: 100%; border-radius: 8px;' type='text' class='form-control' class='form-control' name='Txt_filtro_avanzado' id='Txt_filtro_avanzado' placeholder='Ingresar codigo del empleado' list='Personal'>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<button style='height: 100%;' type='button' id='addRow' class='btn btn-success' onclick='FiltroAvanzado()'><i class='fas fa-plus'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div id='Buscar_valores'></div>");
                            out.print("<form action='Registro?opc=24&fto=2' method='post'>");
                            out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                            out.print("<input type='hidden' name='Txt_valores_filtro' id='Txt_valores_filtro' oninput='javascript:this.value+=document.getElementById('Buscar_valores').innerHTML' value=''/>");
                            out.print("<datalist id='Personal'><label><select name='Personal'>");
                            lst_consultarSirh = ConsultSirh.Empleado_sirh();
                            if (lst_consultarSirh != null && lst_consultarSirh.size() > 0 && !lst_consultarSirh.isEmpty()) {
                                for (int i = 0; i < lst_consultarSirh.size(); i++) {
                                    String[] Arg_personal = lst_consultarSirh.toString().replace("[", "").replace("]", "").replace(",", "").split("///");
                                    out.print("<option value='" + Arg_personal[i] + "'></option>");
                                }
                            } else {
                                out.print("<option value='Error'></option>");
                            }
                            out.print("</select></label></datalist>");
                            out.print("<div id='newRow' style='margin-bottom: 10px;'>");
                            out.print("</div>");
                            out.print("<div>");
                            out.print("<button class='btn_regAc' style='margin-left: 43%; height: 34px;'>Guardar <i class='fas fa-save'></i></button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REGISTRO DE PERSONAL EXTERNO">
                            out.print("<div id='user_ex' class='user_ex'>");
                            out.print("<form action='Registro?opc=24&fto=4' method='post'>");
                            out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                            out.print("<div style='display: flex; width: 100%;margin-top: 10px;justify-content: right;'>");
                            out.print("<div style='width: 48%;margin-top: 10px;'>");
                            out.print("<input type='number' class='form-control' class='form-control' name='documento_ex' placeholder='Documento' value='' required>");
                            out.print("<input type='text' style='margin-top: 10px;' class='form-control' name='nombre_ex' placeholder='Nombres y apellidos' value='' required>");
                            out.print("</div>");
                            out.print("<div style='width: 48%;margin-top: 10px;'>");
                            out.print("<input type='text' class='form-control' class='form-control' name='cargo_ex' placeholder='Cargo' value='' required>");
                            out.print("<input type='number' style='margin-top: 10px;' class='form-control' name='codigo_ex' placeholder='Codigo' value='' required>");
                            out.print("<input type='hidden' class='form-control' name='firmas_ex' placeholder='' value='XXFIRMASXX'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div style='height: 40px; text-align: center; margin-top: 10px;'>");
                            out.print("<button class='btn_regAc'>Guardar <i class='fas fa-save'></i></button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        }
                        //<editor-fold defaultstate="collapsed" desc="CONTENIDO DE FIRMAS">
                        try {
                            documentou = Integer.parseInt(pageContext.getRequest().getAttribute("documento_usuario").toString());
                        } catch (Exception e) {
                            documentou = 0;
                        }
                        try {
                            codigou = Integer.parseInt(pageContext.getRequest().getAttribute("codigo_usuario").toString());
                        } catch (Exception e) {
                            codigou = 0;
                        }
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:" + ((documentou == 0) ? "none" : "block") + ";'>");
                        out.print("<div class='cont_reg' style='width: 40%; height: auto;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Firmar registro</h2>");
                        out.print("<button class='btn_clsRg' onclick='mostrarConvencion(3)'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div style='width: 100%;'>");
                        out.print("<form action='Registro?opc=26&fto=4' name='formA' id='formA' method='post' style='margin:0px;'>");
                        out.print("<center>");
                        out.print("<div style='display: flex;justify-content: space-evenly;'>");
                        out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                        out.print("<input type='text' class='form-control' name='id_documento' id='documento-id' value='" + ((documentou != 0) ? documentou : "") + "' placeholder='Documento' style='margin: 0px;height: 30px;width: 50%;border-radius: 6px;'  required>&nbsp;&nbsp;&nbsp;");
                        out.print("<input type='text' class='form-control' name='txt_codigo' id='codigo-id' value='" + ((codigou != 0) ? codigou : "") + "' placeholder='Codigo' style='width:80px;margin: 0px;height: 30px;width: 25%;border-radius: 6px;'  required>&nbsp;&nbsp;&nbsp;");
                        out.print("<button type='submit' class='btn_regAc' style='width:100px;height: 32px;'>Buscar <i class='fas fa-search'></i></button>");
                        out.print("</div>");
                        out.print("</center>");
                        out.print("</form>");

                        //pad firma
                        if (documentou != 0 && codigou != 0) {
                            List lst_firma = jpa_caso.Traer_firmas(documentou, codigou);
                            //<editor-fold defaultstate="collapsed" desc="MOSTRAR FIRMAS">
                            if (lst_firma != null) {
                                Object[] obj_firma = (Object[]) lst_firma.get(0);
                                if (obj_firma[3] != null) {
                                    out.print("<div class='sigPad signed' style='width:100%;height:217px;'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<div class='codigo' style='display:block;margin: 150px 0px 0px 217px;'>" + obj_firma[2] + "</div>");
                                    out.print("<canvas class='pad' width='440' height='210'></canvas>");
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
                                    if (obj_firma[3] != null) {
                                        out.print("<form action='Registro?opc=27' method='post'>");
                                        out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                                        out.print("<input type='hidden' name='txt_firma' value='" + obj_firma[3] + "'>");
                                        out.print("<input type='hidden' name='id_documento' value='" + documentou + "'>");
                                        out.print("<div style='height: 30px;'>");
                                        out.print("<button id='btn_regAc' class='btn_regAc' style='float: right;height: 30px;'>Firmar</button>");
                                        out.print("</div>");
                                        out.print("</form>");
                                    }
                                } else {
                                    out.print("<center><h4><b style='color:orange;'>El usuario No tiene firma registrada</b></h4><center>");
                                }
                            } else {
                                out.print("<form action='Registro?opc=26&fto=4' method='post'>");
                                out.print("<input type='hidden' name='idRA' value='" + obj_regp[0] + "'>");
                                out.print("<input type='hidden' name='id_documento' id='' value='" + documentou + "'>");
                                out.print("<input type='hidden' name='txt_codigo' id='' value='" + codigou + "'>");
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
                                out.print("<div style='height: 30px; margin-top:10px'>");
                                out.print("<button class='btn_regAc' style='float: right;height: 30px;'>Firmar</button>");
                                out.print("</div>");
                                out.print("</form>");
                            }
//</editor-fold>
                            //Fin pad firma
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ENVIAR DATOS A REGISTRO">
                        out.print("<div>");
                        out.print("<div class='cons_head'>");
                        out.print("<a href='Registro?opc=1&mod=ACTA&txt_filtro='><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                        out.print("<h4>" + obj_regp[3] + "</h4>");
//                        out.print("<div style='margin-bottom:3px;'><a onclick='Imprimir(5);'  title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b></div>");
                        out.print("</div>");

                        out.print("<div class='cons_body' style='max-height: 96%;overflow: auto;'>");
                        out.print("<div style='width:100%' id='Imprimir5'>");
                        String btn_ag = "";
                        if ((Integer) obj_regp[11] != id_rol) {
                            if (obj_regp[4] == null || obj_regp[4].toString().equals("")) {
                                btn_ag = "<button class='btn_regAc' title='No tiene permiso para editar usuarios de este registro' style='height: 25px;width: 60px; cursor: no-drop;'><i class='fas fa-plus' style='color: #c8c8c8;'></i></button>";
                            } else {
                                btn_ag = "<button class='btn_regAc' title='No tiene permiso para editar usuarios de este registro' style='height: 25px;width: 60px; cursor: no-drop;'><i class='fas fa-pen' style='color: #c8c8c8;'></i></button>";
                            }
                        } else {
                            if (obj_regp[4] == null || obj_regp[4].toString().equals("")) {
                                btn_ag = "<button class='btn_regAc' onclick='mostrarConvencion(21)' style='height: 25px;width: 60px;'><i class='fas fa-plus'></i></button>";
                            } else {
                                btn_ag = "<button class='btn_regAc' onclick='mostrarConvencion(22)' style='height: 25px;width: 60px;'><i class='fas fa-pen'></i></button>";
                            }
                        }
                        String reg = obj_regp[5].toString()
                                .replace("XXFECHAXX", "" + obj_regp[2] + "")
                                .replace("XXASUNTOXX", "" + obj_regp[3] + "")
                                .replace("XXNOMBREXX", " - ")
                                .replace("XXCARGOXX", "" + btn_ag + "")
                                .replace("XXFIRMAXX", " - ")
                                .replace("<textarea disabled", "<textarea id='editor'")
                                .replace("<input disabled", "<input type='text' class='form-control'")
                                .replace("<input name=\"XXIDXX\"", "<input name='idRA' value=" + obj_regp[0] + "");

                        if ((Integer) obj_regp[11] != id_rol) {
                            reg = reg.replace("button type='submit'", "button type='button' style='cursor: no-drop;' title='No tiene permiso de editar este registro'");
                        }

                        if (obj_regp[4] != null) {
                            String[] personal = obj_regp[4].toString().replace("][", "//").replace("]", "").replace("[", "").split("//");

                            String btn_firmas = "<a class='btn_regAc' onclick='mostrarConvencion(3)'><i style='height: 22px; width: 45px;' class='fas fa-signature'></i></a>";

                            int row = 2;
                            for (int i = 0; i < personal.length; i++) {
                                if (personal[i].equals("")) {

                                } else {
                                    String[] datosPersonal = personal[i].toString().split("-");
                                    reg = reg.replace("**", "<tr>\n"
                                            + "	               <td colspan='2' align='center' style='vertical-align: middle;'>XXNOMBREXX</td>\n"
                                            + "	               <td colspan='2' align='center' style='vertical-align: middle;'>XXCARGOXX</td>\n"
                                            + "	               <td colspan='2' align='center'>XXFIRMAXX</td>\n"
                                            + "	           </tr>**")
                                            .replace("XXNOMBREXX", datosPersonal[1])
                                            .replace("XXCARGOXX", datosPersonal[2]);
//                                            .replace("XXFIRMAXX", "" + ((datosPersonal[4].toString().equals(" XXFIRMASXX")) ? btn_firmas : "<b style='color: green;'>Firmado</b>") + "");
//                                        .replace("XXFIRMAXX", "" + ((datosPersonal[4].toString().equals(" XXFIRMASXX")) ? btn_firmas : "<b style='color: green;'>Firmado</b> <a class='btn btn-success' onclick='mostrarConvencion(6)'><i class='fas fa-eye'></i></a>") + "");

                                    //<editor-fold defaultstate="collapsed" desc="PAD DE FIRMAS">
                                    if (datosPersonal[4].toString().equals(" XXFIRMASXX")) {
                                        reg = reg.replace("XXFIRMAXX", btn_firmas);
                                    } else {
                                        String pad_firmas = "<div style='display: block;width: 50%;'>"
                                                + "<div class='sigPad" + i + " signed' style='width:100%;height: 40px;display: block;position: relative; margin-top: -10px; margin-bottom: -4px;'>"
                                                + "<div class='sigWrapper'>"
                                                + "<canvas class='pad' width='95px' height='40px'></canvas>"
                                                + "</div>"
                                                + "<div class='codigo' style='display:block; margin: 9px 0px 0px 90px; font-size: 18px;'>" + datosPersonal[3].toString() + "</div>"
                                                + "</div>"
                                                + "</div>"
                                                + "<script>"
                                                + "$(document).ready(function () {"
                                                + "$('.sigPad" + i + "').signaturePad("
                                                + "{"
                                                + "displayOnly:true,"
                                                + "penColour : '#000',"
                                                + "scale : [0.25,0.25]"
                                                + "}"
                                                + ").regenerate([" + datosPersonal[4].toString() + "]);"
                                                + "});"
                                                + "</script>";
                                        reg = reg.replace("XXFIRMAXX", pad_firmas);
                                    }

                                    //</editor-fold>
                                    row++;
                                }

                            }
                            reg = reg.replace("**", "")
                                    .replace("id=\"asis\" rowspan=\"2\"", "id='asis' rowspan='" + row + "'");
                        } else {
                            reg = reg.replace("**", "")
                                    .replace("id='asis' rowspan='2'", "id='asis' rowspan='2'");
                        }

                        out.print("" + reg + "");
                        out.print("</div>");
                        out.print("</div>");
//                        out.print("<script>");
//                        out.print("$('#summernote').summernote({");
//                        out.print("placeholder: 'Agregar contenido del acta',");
//                        out.print("tabsize: 2,");
//                        out.print("height: 150");
//                        out.print("});");
//                        out.print("</script>");

                        //</editor-fold>
                    } else {
                        out.print("no existe registro");
                    }
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTA PRINCIPAL">
                    out.print("<div style='float:right;'>");
                    out.print("<form action='Registro?opc=1&mod=ACTA' name='formA' method='post'>");
                    out.print("<div style='display: flex;align-items: center;'>");
                    out.print("<a href='#' data-toggle='modal' data-target='#Registrar'><i class='fa fa-plus fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                    out.print("<input type='text' class='form-control' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar' onkeyup='Filtrar()' onchange='javascript:this.value=this.value.toUpperCase();'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("<h3>ACTAS</h3>");
                    if (id_acta == 0) {
                        //<editor-fold defaultstate="collapsed" desc="registrar ACTA">

                        //<editor-fold defaultstate="collapsed" desc="SELECCION DE DATOS">                        
                        out.print("<div class='modal fade' id='Registrar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                        out.print("<div class='modal-dialog modal-lg' style='width:45%'>");
                        out.print("<div class='modal-content' style=''>");
                        out.print("<div class='modal-header'>");
                        out.print("<a href='Registro?opc=1&mod=ACTA&txt_filtro=' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Registrar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align=''>");
                        out.print("<form action='Registro?opc=24' name='formA' method='post'>");
                        out.print("<input type='hidden' id='usuariosF-id' name='txt_usa' value=''>");
                        out.print("<input type='hidden' name='txt_personal' id='filtroA' required>");
                        lst_registro = jpa_registro.Registros();
                        out.print("<div style='display: flex; justify-content: space-evenly;'>");
                        out.print("<div>");
                        out.print("<select name='idRA' class='form-control' id='registroA-id' data-live-search='true' required>");
                        out.print("<option value='' style=''>Seleccione Registro</option>");
                        out.print("<option value='16'>R-TI-014 V- 0</option>");
//                        for (int i = 0; i < lst_registro.size(); i++) {
//                            Object[] obj_registro = (Object[]) lst_registro.get(i);
//                            out.print("<option value='" + obj_registro[0] + "' >" + obj_registro[1] + "</option>");
//                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div style='margin-left: 16px;'>");
                        out.print("<input class='form-control' style='height: 34px; padding: 5px;' type='text' class='form-control' onblur='document.getElementById('uno').value=this.value' autocomplete='off' name='txt_fecha' id='datepicker' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</div>");
                        out.print("<div>");
                        out.print("<input class='form-control' style='height: 34px;' type='text' class='form-control' onblur='document.getElementById('asunto_id').value=this.value' name='txt_asunto' id='asunto-id' placeholder='Asunto' style='' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</div>");
                        out.print("<div style='font-size:12px;' id='filtroVistaa'>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div style='margin-top: 10px;'>");
                        out.print("<button type='submit' style='margin-left: 40%; height: 32px;' class='btn_regAc'>Previsualizar <i class='fas fa-eye'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR 014">
                        lst_registro = jpa_registro.Consultar_registros_id(16);
                        Object[] obj_reg = (Object[]) lst_registro.get(0);

                        try {
                            id_actaA = Integer.parseInt(pageContext.getRequest().getAttribute("id_actaA").toString());
                        } catch (Exception e) {
                            id_actaA = 0;
                        }
                        try {
                            fechaA = pageContext.getRequest().getAttribute("txt_fechaA").toString();
                        } catch (Exception e) {
                            fechaA = "";
                        }
                        try {
                            asuntoA = pageContext.getRequest().getAttribute("txt_asuntoA").toString();
                        } catch (Exception e) {
                            asuntoA = "";
                        }

                        if (id_actaA == 16 && !asuntoA.equals("")) {
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana20' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='cont_acta'>");
                            out.print("<div class='cont_head'>");
                            out.print("<h3>Previsualizar Registro: " + "  </h3>");
                            out.print("<button onclick='mostrarConvencion(20)' class='btn_rega'><i class='fas fa-undo'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_body' style='overflow-y: scroll;'>");
                            String reg = obj_reg[5].toString()
                                    .replace("XXFECHAXX", "" + fechaA + "")
                                    .replace("XXASUNTOXX", "" + asuntoA + "")
                                    .replace("<textarea", "<textarea disabled")
                                    .replace("<td contenteditable='true'", "<td disabled")
                                    .replace("<button type='submit'", "<button type='submit' style='display: none;'");
                            out.print("" + reg + "");

                            reg = reg.replace("" + fechaA + "", "XXFECHAXX")
                                    .replace("" + asuntoA + "", "XXASUNTOXX")
                                    .replace("<button type='submit' style='display: none;'", "<button type='submit'");
                            out.print("</div>");
                            out.print("<div class='cont_foot'>");
                            out.print("<form action='Registro?opc=24&fto=1' method='post'>");
//                            La variable fto es igual a "funcionamiento" y es para el motivo de envio del formulario en este caso 1 es para REGISTRAR 
                            out.print("<input type='hidden' name='idRA' id='txt_regAc' value='" + id_actaA + "'>");
                            out.print("<input type='hidden' name='contenidoA' id='txt_regAc' value='" + reg + "'>");
                            out.print("<input type='hidden' name='txt_fecha' id='' placeholder='' value='" + fechaA + "'>");
                            out.print("<input type='hidden' name='txt_asunto' id='' placeholder='' value='" + asuntoA + "'>");
                            out.print("<div style='height: 40px;'>");
                            out.print("<button class='btn_regAc' style='float: right; margin-right: 18px; margin-top: 15px;' onclick='mostrarConvencion(20)'>Confirmar Registro</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        } else if (id_actaA == 0) {

                        }
                        //</editor-fold>

                        //</editor-fold>    
                    } else {
                        //                    // <editor-fold defaultstate="collapsed" desc="Modificar ACTA FA">
//                    lst_actaM = jpa_registro.consultarActaId(id_acta);
//                    lst_registroP = jpa_registro.consultarActas();
//                    Object[] obj_registroA = (Object[]) lst_actaM.get(0);
//                    Object[] obj_consultaP = (Object[]) lst_registroP.get(0);
//                    out.print("<div class='sweet-local' tabindex='-1' id='Modificar' style='opacity: 1.03; display:block;'>");
//                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:964px; height:502px; position: absolute;top:9%; overflow-y:scroll; left:20%;text-align:left '>");
//                    out.print("<form action='Registro?opc=23' name='formA' method='post'>");
//                    out.print("<div class='modal-header'>");
//                    out.print("<a href='Registro?opc=1&mod=ACTA&txt_filtro=' class='close'>&times;</a>");
//                    out.print("<h4 class='modal-title'>Modificar</h4>");
//                    out.print("</div>");
//                    out.print("<input type='hidden' type='text' class='form-control' name='idRA' value='" + obj_registroA[0] + "'>");
//                    out.print("<div class='modal-body' align='center'>");
//                    out.print("<table style='width:90%'>");
//                    out.print("<tr>");
//
//                    out.print("<td>");
//                    out.print("<b>FECHA: </b><br>");
//                    out.print("<input type='text' class='form-control' onblur='document.getElementById('uno').value=this.value'  name='txt_fecha' id='datepicker' value='" + obj_registroA[2] + "' placeholder='Fecha' style='width:90%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
//                    out.print("</td>");
////                    out.print("<td>");
////                    out.print("<b>recibe FECHA: </b><br>");
////                    out.print("<input type='text' class='form-control' name='txt_fecha' id='uno' value='' placeholder='Fecha' style='width:90%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
////                    out.print("</td>");
//                    
//                    out.print("<td>");
//                    out.print("<b>PERSONAS: </b><br>");
//                    out.print("<input type='text' class='form-control' name='txt_personal' id='personal-id' value='" + obj_registroA[4] + "' placeholder='Personal' style='width:90%' onchange='javascript:this.value=this.value.toUpperCase();' required>");
//                    out.print("</td>");
//                    out.print("<td align='center'>");
//                    out.print("<input type='submit' value='Modificar'>");
//                    out.print("</td>");
//                    out.print("</tr>");
//                    out.print("</br>");
//                    out.print("<input type='hidden' name='idRA' value='Modificar' required>");
//                    out.print("<hr> ");
//                    out.print("" + obj_registroA[5] + "");
//                    out.print("<textarea  name='txt_asunto' id='small_descripcion-id'>"+ obj_registroA[3] + "<div contenteditable='true'><p></p></div></textarea>");
//                    
//                    out.print("</table>");
//                    out.print("</form>");
//                    out.print("</div>");
//                    out.print("</fieldset>");
//                    out.print("</div>");
//                    //</editor-fold>
                        // <editor-fold defaultstate="collapsed" desc="Modificar ACTA">
                        lst_actaM = jpa_registro.consultarActaId(id_acta);
                        lst_registroP = jpa_registro.consultarActas();
                        Object[] obj_registroA = (Object[]) lst_actaM.get(0);
                        Object[] obj_consultaP = (Object[]) lst_registroP.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Modificar' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='modal-content' style='width: 45%;margin: auto;margin-top: 15%;height: 190px;'>");

                        out.print("<div class='modal-header' style='height: 47px;padding-top: 15px;padding-right: 15px;padding-left: 15px;'>");
                        out.print("<form action='Registro?opc=23' name='formA' method='post'>");
                        out.print("<a href='Registro?opc=1&mod=ACTA&txt_filtro=' class='close'>&times;</a>");
                        out.print("<h4 class='modal-title'>Modificar</h4>");
                        out.print("</div>");
                        out.print("<div class='modal-body' align='center'>");
                        out.print("<input type='hidden' type='text' class='form-control' name='idRA' value='" + obj_registroA[0] + "'>");

                        out.print("<div style='display:flex;justify-content: space-evenly;'>");

                        out.print("<div class='cont_modi'>");
                        out.print("<p>Registro: </p>");
                        out.print("<input type='text' class='form-control' value='" + obj_registroA[12] + "' style='background: #dedede;' disabled>");
                        out.print("</div>");

                        out.print("<div class='cont_modi'>");
                        out.print("<p>Fecha: </p>");
                        out.print("<input type='text' class='form-control' onblur='document.getElementById('uno').value=this.value'  name='txt_fecha' id='datepicker' value='" + obj_registroA[2] + "' placeholder='Fecha' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</div>");

                        out.print("<div class='cont_modi'>");
                        out.print("<p>Asunto: </p>");
                        out.print("<input type='text' class='form-control' onblur='document.getElementById('asunto_id').value=this.value' name='txt_asunto' id='asunto-id' value='" + obj_registroA[3] + "' placeholder='Asunto' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<button type='submit' class='btn_regAc' style='height: 33px;width: 18%;'>Modificar <i class='fas fa-pencil-alt'></i></button>");
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="IMPRIMIR">      
                    if (idRP2 > 0) {
                        int row = 1;
                        List lst_rps = jpa_registro.consultaPlantilla(idRP2);
                        out.print("<div class='sweet-local' tabindex='-1' style='opacity: 1.03; display:block;' >");
                        out.print("<div class='comt_imp_e'>");
                        out.print("<div class='cont_imp'>");
                        out.print("<div style='display: flex; justify-content: space-between;align-items: center;'>");
                        out.print("<button class='fas fa-print' onclick='Imprimir(5)'></button>");
                        out.print("<h2>Impresion de registro 014</h2>");
                        out.print("<a href='Registro?opc=1&mod=ACTA&txt_filtro='><i class='fas fa-times'></i></a>");
                        out.print("</div>");
                        out.print("<div id='Imprimir5'>");
                        if (lst_rps != null) {
                            Object[] obj_acta = (Object[]) lst_rps.get(0);
                            String tab = obj_acta[5].toString();
                            tab = tab.replace("XXFECHAXX", "" + obj_acta[2].toString() + "");
                            tab = tab.replace("XXASUNTOXX", "" + obj_acta[3].toString() + "");
                            tab = tab.replace("tb_reg", "tb_reg2");
                            tab = tab.replace("id=\"editor\"", "");
                            tab = tab.replace("<textarea placeholder=\"Escribir contenido\"", "");
                            tab = tab.replace("name=\"txt_contenidoActa\"", "");
                            tab = tab.replace("style=\"width: 100%; height: 260px; overflow-y: scroll;\">", "");
                            tab = tab.replace("contetenditable=\"false\"", "");
                            tab = tab.replace("</textarea>", "");
                            if (obj_acta[4] != null) {
                                String[] users = obj_acta[4].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                for (int i = 0; i < users.length; i++) {
                                    String[] user = users[i].toString().split("-");
                                    tab = tab.replace("XXNOMBREXX", user[1].toString()).replace("XXCARGOXX", user[2].toString());
                                    //<editor-fold defaultstate="collapsed" desc="PAD FIRMAS">
                                    int id_us = Integer.parseInt(user[3].toString().replace(" ", ""));
                                    List lst_firma = jpa_caso.Traer_firmas_codigo(id_us);
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    String pad_firmas = "<div style='display: block;width: 50%;'>"
                                            + "<div class='sigPad" + i + " signed' style='width:100%;height: 40px;display: block;position: relative; margin-top: -10px; margin-bottom: -4px;'>"
                                            + "<div class='sigWrapper'>"
                                            + "<canvas class='pad' width='95px' height='40px'></canvas>"
                                            + "</div>"
                                            + "<div class='codigo' style='display:block; margin: 9px 0px 0px 90px; font-size: 18px;'>" + obj_firma[2] + "</div>"
                                            + "</div>"
                                            + "</div>"
                                            + "<script>"
                                            + "$(document).ready(function () {"
                                            + "$('.sigPad" + i + "').signaturePad("
                                            + "{"
                                            + "displayOnly:true,"
                                            + "penColour : '#000',"
                                            + "scale : [0.25,0.25]"
                                            + "}"
                                            + ").regenerate(" + obj_firma[3].toString() + ");"
                                            + "});"
                                            + "</script>";
                                    tab = tab.replace("XXFIRMAXX", pad_firmas);
                                    //</editor-fold>
                                    if (i == users.length - 1) {

                                    } else {
                                        tab = tab.replace("**", "<tr>\n"
                                                + "	               <td colspan=\"2\" align=\"center\" style='vertical-align: middle;'>XXNOMBREXX</td>\n"
                                                + "	               <td colspan=\"2\" align=\"center\" style='vertical-align: middle;'>XXCARGOXX</td>\n"
                                                + "	               <td colspan=\"2\" align=\"center\">XXFIRMAXX</td>\n"
                                                + "	           </tr>**");
                                    }
                                    row++;
                                }
                            } else {

                            }
                            tab = tab.replace("<tr>\n"
                                    + "	               <td colspan=\"2\" align=\"center\" style='vertical-align: middle;'>XXNOMBREXX</td>\n"
                                    + "	               <td colspan=\"2\" align=\"center\" style='vertical-align: middle;'>XXCARGOXX</td>\n"
                                    + "	               <td colspan=\"2\" align=\"center\">XXFIRMAXX</td>\n"
                                    + "	           </tr>**, 0", "");

                            tab = tab.replace("**", "")
                                    .replace("id=\"asis\" rowspan=\"2\"", "id='asis' rowspan='" + row + "'");
                            tab = tab.replace("name=\"txt_responsable\"", "name='txt_responsable' disabled");
                            tab = tab.replace("btn_regAct", "btn_regAct2");

                            out.print("<div>");
                            out.print("" + tab + "");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    if (lst_acta != null) {
                        //<editor-fold defaultstate="collapsed" desc="TABLA PRINCIPAL">
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados'>");
                        out.print("<th style='width: 120px;'>Registro</th>");
                        out.print("<th style='width: 35%;'>Asunto</th>");
                        out.print("<th style='width: 120px;'>Fecha</th>");
                        out.print("<th>Personal</th>");
                        out.print("<th>Usuario Registro</th>");
                        out.print("<th colspan='3' style='width: 30px;'>OPC</th>");
                        List lst_firma = null;
                        for (int i = 0; i < lst_acta.size(); i++) {
                            Object[] obj_acta = (Object[]) lst_acta.get(i);
                            out.print("<tr>");
                            out.print("<td  align='center'>" + obj_acta[1] + "</td>");
                            out.print("<td><a href='Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + obj_acta[0] + "'>" + obj_acta[3] + "</a></td>");
                            out.print("<td align='center'>" + obj_acta[2] + "</td>");
                            out.print("<td align='center'>");
                            String personal = "";

                            if (obj_acta[4] == null || obj_acta[4].toString().equals("null")) {
                                out.print("<b style='color:#b2068c; '> Sin Asistentes </b><br>");
                            } else if (obj_acta[4].toString().equals("")) {
                                out.print("<b style='color:#b2068c; '> Sin Asistentes </b><br>");
                            } else {
                                personal = obj_acta[4].toString().replace("][", "///").replace("[", "").replace("]", "") + "";
                                if (personal != null) {
                                    String[] arg_personal = personal.split("///");
                                    out.print("<b class='tooltip_css' style='color: black;'>ASISTENTES<span style='background: black; padding-left: 10px; padding-right: 10px;' class='tooltiptext_css' valign='top'>");
                                    for (int j = 0; j < arg_personal.length; j++) {
                                        String[] arg_datos = arg_personal[j].toString().split("-");
                                        out.print("<b style='color: white;'>" + "> " + arg_datos[1].toString() + " | " + arg_datos[2].toString() + "</b><br>");
                                    }
                                } else {
                                    out.print("<b style='color: prurple;'>- Sin asistentes</b><br>");
                                }
                            }
                            out.print("</span></b></td>");

                            out.print("<td align='center'>" + obj_acta[7] + "</td>");

                            try {
                                String personal_s = obj_acta[4].toString();
                                if (personal_s.contains("XXFIRMASXX")) {
                                    out.print("<td align='center'><p class='tooltip_4'><span><i style='color: #cacaca;' class='fa fa-print fa-lg'></i></span><span class='tooltiptext'>EL registro no se ha firmado</span></p></td>");
                                } else {
                                    out.print("<td align='center'><a href='Registro?opc=1&mod=ACTA&txt_filtro=&idRP2=" + obj_acta[0] + "' style='background: transparent; border: none; color: black;'><i style='color: black;' class='fa fa-print fa-lg'></i></a></td>");
                                }
                            } catch (Exception e) {
                                out.print("<td align='center'><p class='tooltip_4'><span><i style='color: #cacaca;' class='fa fa-print fa-lg'></i></span><span class='tooltiptext'>El registro no tiene asistentes</span></p></td>");

                            }
                            if ((Integer) obj_acta[10] != id_rol) {
                                out.print("<td align='center'><a href='Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + obj_acta[0] + "' title='No tiene permiso para editar este registro' style='cursor:no-drop;'><i class='fa fa-pencil-alt fa-lg' style='color:#c8c8c8; width: 10.5px; heigth:11px;' ></i></a></td>");
                                out.print("<td align='center'><a href='Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + obj_acta[0] + "' title='Ingresar a firmar documento' style='font-size: 20px;'><i class='fas fa-file-signature' style='color:#292929; width: 10.5px; heigth:11px;' ></i></a></td>");
                            } else {
                                out.print("<td align='center'><a href='Registro?opc=1&mod=ACTA&txt_filtro&idRA=" + obj_acta[0] + "' title='Editar cabecera del acta'><i class='fa fa-pencil-alt fa-lg' style='color:#292929; width: 10.5px; heigth:11px;' ></i></a></td>");
                                out.print("<td align='center'><a href='Registro?opc=1&mod=ACTA&txt_filtro=&idRP=" + obj_acta[0] + "' title='Editar contenido del acta' style='font-size: 20px;'><i class='fas fa-file-signature' style='color:#292929; width: 10.5px; heigth:11px;' ></i></a></td>");
                            }

                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("<script type='text/javascript'>");
                        out.print("var pager = new Pager('resultados',15);");
                        out.print("pager.init();");
                        out.print("pager.showPageNav('pager','NavPosicion');");
                        out.print("pager.showPage(1);");
                        out.print("</script>");
//</editor-fold>

                    } else {
                        out.print("<table class='table' id='resultados'>");
                        out.print("<th style='width: 35%;'>Asunto</th>");
                        out.print("<th style='width: 120px;'>Registro</th>");
                        out.print("<th style='width: 120px;'>Fecha</th>");
                        out.print("<th>Personal</th>");
                        out.print("<th>Estado</th>");
                        out.print("<th style='width: 30px;'>OPC</th>");
                        out.print("<tr><td align='center' colspan='6'>NO EXISTEN DATOS</td></tr>");
                    }
//</editor-fold>
                }
                //</editor-fold>
            }
            if (modulo.equals("R017")) {
                //<editor-fold defaultstate="collapsed" desc="registro 017">
                int id_digitalizacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_digitalizacion").toString());
                String filtro = pageContext.getRequest().getAttribute("filtro").toString();
                String FechaI = pageContext.getRequest().getAttribute("fechaI").toString();
                String FechaF = pageContext.getRequest().getAttribute("fechaF").toString();
                if (filtro.equals("")) {
                    lst_digitalizacion = jpa_registro.consultaDigitalizacion();
                } else if (FechaI.equals("") || FechaF.equals("")) {
                    lst_digitalizacion = jpa_registro.consultaFiltroDigitalizacion(filtro);
                } else {
                    lst_digitalizacion = jpa_registro.consultaFiltroFecha(FechaI, FechaF, filtro);
                }
                if (id_digitalizacion == 0) {
                    //<editor-fold defaultstate="collapsed" desc="REGISTRO DIGILITALIZACION">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana12' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:978px; height:532px; position: absolute;top:7%; left:16%;text-align:left '>");
                    out.print("<a href='Registro?opc=1&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=' class='close'>&times;</a>");
                    out.print("<form action='Registro?opc=11' name='formD' method='post'>");
                    out.print("<h4 class='modal-title'>Registrar</h4>");
                    out.print("<hr>");
                    out.print("<table align='center' style='width:80%;font-size:12px;'>");
                    out.print("<tr><td><b>Fecha Digitalización:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaD' id='datepicker' value='' placeholder='Fecha Digitalizacion' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required     >");
                    out.print("</td>");
                    out.print("<td><b>Carpeta o Documento:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombre' id='nombre-id' value='' placeholder='Carpeto o Documento' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>No. Carpeta:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_num_cap' id='item-id' value='' placeholder='Numero Carpeta' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td></tr>");
                    out.print("</table>");
                    out.print("<br/>");
                    out.print("<textarea id='editor' name='txt_descripcion'><div contenteditable='true'><p></p></div></textarea>");
                    out.print("<br><div style='float:right;'><input type='submit' value='Registrar' style='width:100%'></div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="MODIFICAR DIGITALIZACION">
                    lst_digitalizacionM = jpa_registro.consultaModificarDigitalizacion(id_digitalizacion);
                    Object[] obj_diM = (Object[]) lst_digitalizacionM.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana13' style='opacity: 1.03; display:block;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:978px; height:532px; position: absolute;top:7%; left:16%;text-align:left '>");
                    out.print("<a href='Registro?opc=1&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=' class='close'>&times;</a>");
                    out.print("<form action='Registro?opc=12' name='formD' method='post'>");
                    out.print("<h4 class='modal-title'>Modificar</h4>");
                    out.print("<hr>");
                    out.print("<input type='hidden' name='idD' id='idD-id' value='" + id_digitalizacion + "'>");
                    out.print("<table align='center' style='width:80%;font-size:12px;'>");
                    out.print("<tr><td><b>Fecha Digitalización:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaDM' id='datepicker' value='" + obj_diM[1] + "' placeholder='Fecha Digitalizacion' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required     >");
                    out.print("</td>");
                    out.print("<td><b>Carpeta o Documento:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_nombreM' id='nombre-id' value='" + obj_diM[2] + "' placeholder='Carpeto o Documento' onchange='javascript:this.value=this.value.toUpperCase();' required >");
                    out.print("</td>");
                    out.print("<td><b>No. Carpeta:</b><br>");
                    out.print("<input type='text' class='form-control' name='txt_num_capM' id='item-id' value='" + obj_diM[3] + "' placeholder='Numero Carpeta' onchange='javascript:this.value=this.value.toUpperCase();' required>");
                    out.print("</td></tr>");
                    out.print("</table>");
                    out.print("<br/>");
                    out.print("<textarea id='editor'  name='txt_descripcionM'>" + obj_diM[4] + "</textarea>");
                    out.print("<br><div style='float:right;'><input type='submit' value='Modificar' style='width:100%'></div>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="FILTRO DE FECHAS">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana13' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:223px; height:290px; position: absolute;top:15%; left:64%;text-align:left '>");
                out.print("<a href='Registro?opc=1&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF=' class='close'>&times;</a>");
                out.print("<form action='Registro?opc=1&idD=0&mod=R017' name='formD' method='post'>");
                out.print("<h4 class='modal-title'>Filtro fechas</h4>");
                out.print("<hr>");
                out.print("<table align='center' style='width:80%;font-size:12px'>");
                out.print("<tr><td><b>Fecha Inicio:</b><br>");
                out.print("<input type='text' class='form-control' name='txt_fechaI' id='start' value='' placeholder='Fecha Inicio' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required     >");
                out.print("</td></tr>");
                out.print("<tr><td><b>Fecha Fin:</b><br>");
                out.print("<input type='text' class='form-control' name='txt_fechaF' id='end' value='' placeholder='Fecha Fin' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required     >");
                out.print("</td></tr>");
                out.print("<tr><td><br><input type='text' class='form-control' name='txt_filtro' id='filtro-id' value='' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' autocomplete='off' required >");
                out.print("</td><tr>");
                out.print("</table>");
                out.print("<br><div style='float:right;'><input type='submit' value='Buscar' style='width:100%'></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div style='float:right'><i class='fa fa-plus fa-lg' onclick='mostrarConvencion(12)' style='color:#292929'></i></div>");
                out.print("<h3>R-TI-017</h3>");
                out.print("<form action='Registro?opc=1&idD=0&mod=R017&txt_fechaI=&txt_fechaF=' name='formFiltro' method='post'>"
                        + "<div style='display:flex;justify-content: space-between;align-items: flex-end;'>"
                        + "<div id='NavPosicion'></div>"
                        + "<div style='display:flex;'><div style='margin:6px;'><i class='icon'><i class='fas fa-search fa-lg' onclick='mostrarConvencion(13)' title='Filtro'></i></i></div>"
                        + "<div><input type='text' class='form-control' name='txt_filtro' id='filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></form></div></div></div>");
                out.print("<table class='table' >");
                out.print("<tr> <td colspan='7' style='background-color:#CCC; text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                out.print("<tr>");
                out.print("<td align='center' style='width:25%;' colspan='2' rowspan='2'>");
                out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:65%' /></td>");
                out.print("<td colspan='4' align='center' style='width:25%;'><b class='negro'>REGISTRO</b></td>");
                out.print("<td align='center' style='width:25%;'><b class='negro'>CODIGO<br> R-TI-017 </b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td colspan='4' align='center'><b class='negro'>SEGUIMIENTO DIGITALIZACIÓN</b></td>");
                out.print("<td align='center'><b class='negro'>VERSIÓN: 000</b></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("<div align='center' style='font-size:12px;margin:15px'><b class='title'>AREA: </b>TECNOLOGÍA DE INFORMACIÓN&nbsp;&nbsp;");
                out.print("<b class='title'>RESPONSABLE DEL AREA: </b>YAMILE TAFUR&nbsp;&nbsp; ");
                out.print("<b class='title'>DIGITALIZACION: </b>DUVAN VASQUEZ</div>");
                out.print("<div style='width: 100%; height:57%; max-width: 100%; max-height:57%; overflow-x:hidden'>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th class='sticky4'>FECHA DIGILITACIÓN</th>");
                out.print("<th class='sticky4'>CARPETAS O DOCUMETOS</th>");
                out.print("<th class='sticky4'>N° DE CARPETA</th>");
                out.print("<th class='sticky4'>RUTA</th>");
                out.print("<th class='sticky4'>FECHA ENTREGA</th>");
                out.print("<th class='sticky4'>VERIFICADO</th>");
                out.print("<th class='sticky4' colspan='2'>OPC</th>");
                out.print("</tr>");
                if (lst_digitalizacion != null) {
                    for (int i = 0; i < lst_digitalizacion.size(); i++) {
                        Object[] obj_digi = (Object[]) lst_digitalizacion.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_digi[1] + "</td>");
                        out.print("<td>" + obj_digi[2] + "</td>");
                        out.print("<td>" + obj_digi[3] + "</td>");
                        String link = obj_digi[4].toString();
                        link = link.replaceAll("\\<.*?>", "").replaceAll("\\s", "");
                        if (link.contains("https") || link.contains("HTTPS")) {
                            link = link.replace("HTTPS://CLOUD.PLASTITEC-SA.COM/INDEX.PHP/APPS/FILES/?DIR", "https://cloud.plastitec-sa.com/index.php/apps/files/?dir");
                            out.print("<td><b><a href='" + link + "' target='_blank'>Contenido</a></b></td>");
                        } else {
                            out.print("<td><b>" + link + "</b></td>");
                        }
//                        out.print("<td><div class='tooltip_2'><b><a href='"+ link +"' tarjet='_blank'>Contenido</a></b><span class='tooltiptext'>" + obj_digi[4] + "</span></div></td>");
                        out.print("<td>" + obj_digi[5] + "</td>");
                        if (obj_digi[6] == null) {
                            if (id_rol == 2) {
                                out.print("<td><a href='Registro?opc=14&idD=" + obj_digi[0] + "&mod=R017''>Verificar</a></td>");
                            } else {
                                out.print("<td><b style='color:orange'>Pendiente verificar</b></td>");
                            }
                        } else {
                            int documento = Integer.parseInt(obj_digi[7].toString());
                            int codigo = Integer.parseInt(obj_digi[8].toString());
                            List lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                            if (lst_firma != null) {
                                out.print("<td align='center' align='center'>");
                                //<editor-fold defaultstate="collapsed" desc="FIRMA ELECTRONICA">
                                Object[] obj_firma = (Object[]) lst_firma.get(0);
                                out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "'  style='width:auto;min-height:50px'>");
                                out.print("<div class='sigWrapper'>");
                                out.print("<canvas class='pad' width='80' height='60px'></canvas>");
                                out.print("</div>");
//                            out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                out.print("</div>");
                                out.print("<script>");
                                out.print("$(document).ready(function () {");
                                out.print("$('#S" + obj_firma[1] + "" + i + "').signaturePad(");
                                out.print("{");
                                out.print("displayOnly:true,");
                                out.print("penColour : '#292929',");
                                out.print("scale : [0.25,0.25]");
                                out.print("}");
                                out.print(").regenerate(" + obj_firma[3] + "  );");
                                out.print("});");
                                out.print("</script>");
                                //</editor-fold>
                                out.print("</td>");
                            } else {
                                out.print("<td>" + obj_digi[9] + "</td>");
                            }
                        }
                        if (id_rol == 1 || id_rol == 4 || id_rol == 3) {
                            out.print("<td align='center'><a href='Registro?opc=1&idD=" + obj_digi[0] + "&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF='><i class='icon'><i class='fas fa-pencil-alt fa-lg' title='Modificar'></i></i></a></td>");
                            if (obj_digi[9] != null) {
                                out.print("<td align='center'><i class='icon'><i class='fas fa-file-prescription fa-lg' style='color:#d3cdcd' title='No se puede inabilitar porque contine firma'></i></i></td>");
                            } else {
                                out.print("<td align='center'><i class='icon'><i class='fas fa-file-prescription fa-lg' onclick='InabilitarRegistro(" + obj_digi[0] + ")' title='Cambiar estado'></i></i></td>");
                            }
//                        out.print("<td align='center'><a href='Registro?opc=13&idD=" + obj_digi[0] + "&mod=R017&txt_filtro=&txt_fechaI=&txt_fechaF='><i class='icon'><i class='fas fa-file-prescription fa-lg' title='Cambiar estado'></i></i></a></td>");
                        } else if (obj_digi[6] == null) {
                            out.print("<td align='center' colspan='2'><i class='icon'><i class='fas fa-check fa-lg' title='Pendiente verificar'></i></i></a></td>");
                        } else {
                            out.print("<td align='center' colspan='2'><i class='icon'><i class='fas fa-check-double fa-lg' title='Verificado'></i></i></a></td>");
                        }
                        out.print("</tr>");
                    }
                } else {
                    out.print("<td align='center' colspan='9'>NO EXISTEN REGISTROS</td>");
                }
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',25);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<script>");
                out.print("$('#summernote').summernote({");
                out.print("placeholder: 'Descripción',");
                out.print("tabsize: 2,");
                out.print("height: 100");
                out.print("});");
                out.print("</script>");
//</editor-fold>   
            }
            if (modulo.equals("R026")) {
                //<editor-fold defaultstate="collapsed" desc="registro 026">
                try {
                    tipo = Integer.parseInt(pageContext.getRequest().getAttribute("tipo").toString());
                } catch (Exception e) {
                    tipo = 0;
                }
                try {
                    anio = Integer.parseInt(pageContext.getRequest().getAttribute("anioC").toString());
                } catch (Exception e) {
                    anio = year;
                }
                List lst_anios = jpa_cronograma.consultaAniosCronograma();
                String meses = "Ene,Feb,Mar,Abr,May,Jun,Jul,Ago,Sep,Oct,Nov,Dic";
                String[] mes = meses.split(",");
                out.print("<div style='float:right;'>");
                out.print("<form action='Registro?opc=1&mod=R026' name='formF' id='formF' method='post'>");
                out.print("<a href='#' data-toggle='modal' data-target='#Registrar'><i class='fa fa-plus fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<select name='txt_bus' id='filtro-id' onchange=\"this.form.submit()\">");
                out.print("<option value='' style='display:none'>" + ((anio != 0) ? anio : "Seleccione Año") + "</option>");
                for (int i = 0; i < lst_anios.size(); i++) {
                    Object[] obj_anios = (Object[]) lst_anios.get(i);
                    out.print("<option>" + obj_anios[0].toString().split("-")[0] + "</option>");
                }
                out.print("</select>");
                out.print("</form>");
                out.print("</div>");
                out.print("<h3>R-TI-026 <b>" + ((anio != 0) ? anio : "") + "</b></h3>");
                //<editor-fold defaultstate="collapsed" desc="registrar programacion">
                out.print("<div class='modal fade' id='Registrar' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog modal-sm' style='width:45%;height:50%'>");
                out.print("<div class='modal-content' style='height:87%;overflow-y:scroll;'>");
//                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                out.print("<div class='modal-header'>");
                out.print("<a href='Registro?opc=1&mod=R026&txt_bus=" + anio + "' class='close'>&times;</a>");
                out.print("<h4 class='modal-title'>Registrar</h4>");
                out.print("</div>");
                out.print("<div class='modal-body' align='center'>");
                out.print("<form action='Registro?opc=7' name='formA' method='post'>");
                out.print("<table style='font-size:12px;width:100%'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Tipo: </b><br>");
                out.print("<select name='slc_tipo' id='tipo-id' onchange='TipoR(this.value);' required>");
                out.print("<option value='' style='display:none'>Seleccione tipo</option>");
                out.print("<option value='1'>Hardware</option>");
                out.print("<option value='2'>Software</option>");
                out.print("</select>");
                out.print("</td>");
                out.print("<td rowspan='3'>");
                //<editor-fold defaultstate="collapsed" desc="meses">
                out.print("<table class='table'>");
                for (int i = 0; i < mes.length; i++) {
                    if (i == 0 || i == 6) {
                        out.print("<tr>");
                        for (int k = ((i == 0) ? 0 : 6); k < ((i == 0) ? 6 : 12); k++) {
                            out.print("<th>" + mes[k] + "</th>");
                        }
                        out.print("</tr>");
                        out.print("<tr>");
                    }
                    out.print("<td align='center'><label class='control control-checkbox'><input type='checkbox' id='mes" + (i + 1) + "' value='" + (i + 1) + "' onclick='RegistrarC(this.value);'><div class='control_indicator'></div></label></td>");
                    if ((i + 1) == 6 || (i + 1) == 12) {
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("<input type='hidden' name='slc_meses' id='mes-id' value=''>");
//</editor-fold>
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<div id='divH' style='display:none'>");
                out.print("<b>Actividad: </b><br>");
                out.print("<input type='text' class='form-control' name='txt_actividad' id='actividad-id' placeholder='Actividad' style='margin-bottom:0px;' onchange='Javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("<div id='divS' style='display:none'>");
                List lst_aplicativos = jpa_aplicativo.consultarAplicativos();
                out.print("<b>Aplicativo: </b><br>");
                out.print("<select name='slc_aplicativo' id='aplicativo-id' data-live-search='true'>");
                out.print("<option value='1' style='display:none'>Seleccione App</option>");
                for (int i = 0; i < lst_aplicativos.size(); i++) {
                    Object[] obj_aplicativos = (Object[]) lst_aplicativos.get(i);
                    out.println("<option value='" + obj_aplicativos[0] + "'>" + obj_aplicativos[1] + "</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td><input type='submit' value='Registrar'></td>");
                out.print("</tr>");
                out.print("</table>");
                out.print("</form><br>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                if (tipo != 0) {
                    out.print("<script>");
                    out.print("$('#Registrar').modal('show');");
                    out.print("</script>");
                }
//</editor-fold>
                out.print("<ul class='nav nav-tabs'>");
                if (id_rol == 4 || id_rol == 2 || id_rol == 3) {
                    out.print("<li class='active'><a data-toggle='tab' href='#Hardware'>HARDWARE</a></li>");
                    out.print("<li><a data-toggle='tab' href='#Software'>SOFTWARE</a></li>");
                }
                out.print("</ul>");
                out.print("<div class='tab-content'>");
//                if (id_rol == 3) {
//                    lst_cronograma = jpa_cronograma.consultaCronograma(1, ((anio != 0) ? "" + anio : "0000"));
//                    //<editor-fold defaultstate="collapsed" desc="consulta hardware">
//                    out.print("<div id='Hardware' class='tab-pane fade in active'>");
//                    out.print("<br><a href='#' onclick='Imprimir(2);' title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b><br>");
//                    out.print("<div style='height:90%; max-height:90%; overflow-y: auto;' id='Imprimir2'>");
//                    out.print("<table class='table'>");
//                    out.print("<tr><td colspan='17' style='background-color:#CCC;text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
//                    out.print("<tr>");
//                    out.print("<td align='center' style='width:30%;' colspan='2' rowspan='2'>");
//                    out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:70%' /></td>");
//                    out.print("<td colspan='10' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
//                    out.print("<td colspan='10' align='center' style='width:20%;'><b class='negro'>CODIGO<br> R-TI-026 </b></td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td colspan='10' align='center'><b class='negro'>CRONOGRAMA ANUAL DE <br> VERIFICACIONES TECNOLOGÍA DE INFORMACIÓN</b></td>");
//                    out.print("<td colspan='10' align='center'><b class='negro'>VERSIÓN: 000</b></td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td colspan='3'><b class='title'>Asunto: </b>Actividades Programadas</td>");
//                    out.print("<td colspan='7'><b class='title'>Tipo: </b>Hardware</td>");
//                    out.print("<td colspan='7'><b class='title'>Año: </b>" + ((anio != 0) ? anio : year) + "</td>");
//                    out.print("</tr>");
//                    out.print("<tr>");
//                    out.print("<td style='width:20%' align='center'><b class='title'>Actividad</b></td>");
//                    out.print("<td style='width:10%' align='center'><b class='title'>Verificado por</b></td>");
//                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
//                    out.print("<td style='width:10%' align='center'><b class='title'>Revisado por</b></td>");
//                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
//                    for (int i = 0; i < mes.length; i++) {
//                        out.print("<th class='sticky2'>" + mes[i] + "</th>");
//                    }
//                    out.print("</tr>");
//                    if (lst_cronograma != null) {
//                        for (int i = 0; i < lst_cronograma.size(); i++) {
//                            List lst_firma = null;
//                            Object[] obj_cronograma = (Object[]) lst_cronograma.get(i);
//                            out.print("<tr>");
//                            out.print("<td>" + obj_cronograma[4] + "</td>");
//                            if (obj_cronograma[8] != null) {
//                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
//                                int documento = Integer.parseInt(obj_cronograma[8].toString());
//                                int codigo = Integer.parseInt(obj_cronograma[9].toString());
//                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
//                                out.print("<td align='center'>");
//                                if (lst_firma != null) {
//                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
//                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "' style='width:120px;min-height:65px'>");
//                                    out.print("<div class='sigWrapper'>");
//                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
//                                    out.print("</div>");
//                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
//                                    out.print("</div>");
//                                    out.print("<script>");
//                                    out.print("$(document).ready(function () {");
//                                    out.print("$('#S" + obj_firma[1] + "" + i + "').signaturePad(");
//                                    out.print("{");
//                                    out.print("displayOnly:true,");
//                                    out.print("penColour : '#292929',");
//                                    out.print("scale : [0.25,0.25]");
//                                    out.print("}");
//                                    out.print(").regenerate(" + obj_firma[3] + ");");
//                                    out.print("});");
//                                    out.print("</script>");
//                                } else {
//                                    out.print("" + obj_cronograma[17] + "</td>");
//                                }
//                                out.print("</td>");
//                                //</editor-fold>
//                                out.print("<center><td align='center'><input class='td_fechaVe form-control' type='text' id='" + obj_cronograma[0] + "' value='" + obj_cronograma[10].toString().split(" ")[0] + "'></td></center>");
//                            } else {
//                                if (id_rol == 3) {
//                                    out.print("<td align='center'><a href='Registro?opc=8&idC=" + obj_cronograma[0] + "&tipo=2'>Verificar</a></td>");
//                                } else {
//                                    out.print("<td align='center'><b class='naranja'>Pendiente Verificar</b></td>");
//                                }
//                                out.print("<td align='center'>---</td>");
//                            }
//                            if (obj_cronograma[12] != null) {
//                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
//                                int documento = Integer.parseInt(obj_cronograma[12].toString());
//                                int codigo = Integer.parseInt(obj_cronograma[13].toString());
//                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
//                                out.print("<td align='center'>");
//                                if (lst_firma != null) {
//                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
//                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "' style='width:120px;min-height:65px'>");
//                                    out.print("<div class='sigWrapper'>");
//                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
//                                    out.print("</div>");
//                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
//                                    out.print("</div>");
//                                    out.print("<script>");
//                                    out.print("$(document).ready(function () {");
//                                    out.print("$('#S" + obj_firma[1] + "" + i + "').signaturePad(");
//                                    out.print("{");
//                                    out.print("displayOnly:true,");
//                                    out.print("penColour : '#292929',");
//                                    out.print("scale : [0.25,0.25]");
//                                    out.print("}");
//                                    out.print(").regenerate(" + obj_firma[3] + ");");
//                                    out.print("});");
//                                    out.print("</script>");
//                                    out.print("</td>");
//                                } else {
//                                    out.print("" + obj_cronograma[18] + "</td>");
//                                }
//                                //</editor-fold>
//                                out.print("<td align='center'>" + obj_cronograma[14].toString().split(" ")[0] + "</td>");
//                            } else {
//                                if (id_rol == 2) {
//                                    out.print("<td align='center'><a href='" + ((obj_cronograma[8] != null) ? "Registro?opc=9&idC=" + obj_cronograma[0] + "" : "") + "'>Validar</a></td>");
//                                } else {
//                                    out.print("<td align='center'><b class='naranja'>Pendiente Validar</b></td>");
//                                }
//                                out.print("<td align='center'>---</td>");
//                            }
//                            for (int j = 0; j < 12; j++) {
//                                String[] mesC = obj_cronograma[5].toString().split("-");
//                                out.print("<td align='center'>" + ((j + 1) == Integer.parseInt(mesC[1]) ? "<b class='title'>X</b>" : "") + "</td>");
//                            }
//                            out.print("</tr>");
//                        }
//                    } else {
//                        out.print("<tr><td>No se encuentran resultados</td></tr>");
//                    }
//                    out.print("</table>");
//                    out.print("</div>");
//                    out.print("</div>");
//                    //</editor-fold>
//                } else
                if (id_rol == 5) {
                    lst_cronograma = jpa_cronograma.consultaCronograma(2, ((anio != 0) ? "" + anio : "0000"));
                    //<editor-fold defaultstate="collapsed" desc="consulta software">
                    if (id_rol == 5) {
                        out.print("<div id='Software' class='tab-pane fade in active' style='overflow:scroll;'>");
                    } else {
                        out.print("<div id='Software' class='tab-pane fade' style='overflow:scroll;'>");
                    }
                    out.print("<br><a href='#' onclick='Imprimir(3);' title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b><br>");
                    out.print("<div style='height:90%; max-height:90%; overflow-y: auto;' id='Imprimir3'>");
                    out.print("<div class='table-container'>");
                    out.print("<table class='table'>");
                    out.print("<tr><td colspan='17' style='background-color:#CCC;text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:30%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:70%' /></td>");
                    out.print("<td colspan='10' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
                    out.print("<td colspan='10' align='center' style='width:20%;'><b class='negro'>CODIGO<br> R-TI-026 </b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='10' align='center'><b class='negro'>CRONOGRAMA ANUAL DE <br> VERIFICACIONES TECNOLOGÍA DE INFORMACIÓN</b></td>");
                    out.print("<td colspan='10' align='center'><b class='negro'>VERSIÓN: 000</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='3'><b class='title'>Asunto: </b>Actividades Programadas</td>");
                    out.print("<td colspan='7'><b class='title'>Tipo: </b>Software</td>");
                    out.print("<td colspan='7'><b class='title'>Año: </b>" + ((anio != 0) ? anio : "2020") + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:20%' align='center'><b class='title'>Actividad</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Verificado por</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Revisado por</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
                    for (int i = 0; i < mes.length; i++) {
                        out.print("<th class='sticky2'>" + mes[i] + "</th>");
                    }
                    out.print("</tr>");
                    if (lst_cronograma != null) {
                        for (int i = 0; i < lst_cronograma.size(); i++) {
                            List lst_firma = null;
                            Object[] obj_cronograma = (Object[]) lst_cronograma.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_cronograma[4] + " " + obj_cronograma[3] + "</td>");
                            if (obj_cronograma[8] != null) {
                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                                int documento = Integer.parseInt(obj_cronograma[8].toString());
                                int codigo = Integer.parseInt(obj_cronograma[9].toString());
                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                                out.print("<td align='center'>");
                                if (lst_firma != null) {
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "' style='width:120px;min-height:65px'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('#S" + obj_firma[1] + "" + i + "').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                } else {
                                    out.print("" + obj_cronograma[17] + "</td>");
                                }
                                out.print("</td>");
                                //</editor-fold>
                                out.print("<center><td align='center'><input class='td_fechaVe form-control' type='text2' id='" + obj_cronograma[0] + "' value='" + obj_cronograma[10].toString().split(" ")[0] + "'></td></center>");
//                                out.print("<td align='center'>" + obj_cronograma[10].toString().split(" ")[0] + "</td>");
                            } else {
                                if (id_rol == 5) {
                                    out.print("<td align='center'><a href='Registro?opc=8&idC=" + obj_cronograma[0] + "&tipo=2'>Verificar</a></td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Pendiente Verificar</b></td>");
                                }
                                out.print("<td align='center'>---</td>");
                            }
                            if (obj_cronograma[12] != null) {
                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                                int documento = Integer.parseInt(obj_cronograma[12].toString());
                                int codigo = Integer.parseInt(obj_cronograma[13].toString());
                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                                out.print("<td align='center'>");
                                if (lst_firma != null) {
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "s" + i + "' style='width:120px;min-height:65px'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('#S" + obj_firma[1] + "s" + i + "').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                    out.print("</td>");
                                } else {
                                    out.print("" + obj_cronograma[18] + "</td>");
                                }
                                //</editor-fold>
                                out.print("<td align='center'>" + obj_cronograma[14].toString().split(" ")[0] + "</td>");
                            } else {
                                if (id_rol == 2) {
                                    out.print("<td align='center'><a href='" + ((obj_cronograma[8] != null) ? "Registro?opc=9&idC=" + obj_cronograma[0] + "" : "") + "'>Validar</a></td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Pendiente Validar</b></td>");
                                }
                                out.print("<td align='center'>---</td>");
                            }
                            for (int j = 0; j < 12; j++) {
                                String[] mesC = obj_cronograma[5].toString().split("-");
                                out.print("<td align='center'>" + ((j + 1) == Integer.parseInt(mesC[1]) ? "<b class='title'>X</b>" : "") + "</td>");
                            }
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr><td>No se encuentran resultados</td></tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    lst_cronograma = jpa_cronograma.consultaCronograma(1, ((anio != 0) ? "" + anio : "0000"));
                    //<editor-fold defaultstate="collapsed" desc="consulta hardware">
                    out.print("<div id='Hardware' class='tab-pane fade in active'>");
                    out.print("<br><a href='#' onclick='Imprimir(2);' title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b><br>");
                    out.print("<div style='height:90%; max-height:90%; overflow-y: auto;' id='Imprimir2'>");
                    out.print("<table class='table'>");
                    out.print("<tr><td colspan='17' style='background-color:#CCC;text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:30%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:70%' /></td>");
                    out.print("<td colspan='10' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
                    out.print("<td colspan='10' align='center' style='width:20%;'><b class='negro'>CODIGO<br> R-TI-026 </b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='10' align='center'><b class='negro'>CRONOGRAMA ANUAL DE <br> VERIFICACIONES TECNOLOGÍA DE INFORMACIÓN</b></td>");
                    out.print("<td colspan='10' align='center'><b class='negro'>VERSIÓN: 000</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='3'><b class='title'>Asunto: </b>Actividades Programadas</td>");
                    out.print("<td colspan='7'><b class='title'>Tipo: </b>Hardware</td>");
                    out.print("<td colspan='7'><b class='title'>Año: </b>" + ((anio != 0) ? anio : year) + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:20%' align='center'><b class='title'>Actividad</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Verificado por</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Revisado por</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
                    for (int i = 0; i < mes.length; i++) {
                        out.print("<th class='sticky2'>" + mes[i] + "</th>");
                    }
                    out.print("</tr>");
                    if (lst_cronograma != null) {
                        for (int i = 0; i < lst_cronograma.size(); i++) {
                            List lst_firma = null;
                            Object[] obj_cronograma = (Object[]) lst_cronograma.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_cronograma[4] + "</td>");
                            if (obj_cronograma[8] != null) {
                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                                int documento = Integer.parseInt(obj_cronograma[8].toString());
                                int codigo = Integer.parseInt(obj_cronograma[9].toString());
                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                                out.print("<td align='center'>");
                                if (lst_firma != null) {
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "' style='width:120px;min-height:65px'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('#S" + obj_firma[1] + "" + i + "').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                } else {
                                    out.print("" + obj_cronograma[17] + "</td>");
                                }
                                out.print("</td>");
                                //</editor-fold>
                                out.print("<center><td align='center'><input class='td_fechaVe form-control' type='text' id='" + obj_cronograma[0] + "' value='" + obj_cronograma[10].toString().split(" ")[0] + "'></td></center>");
                            } else {
                                if (id_rol == 3) {
                                    out.print("<td align='center'><a href='Registro?opc=8&idC=" + obj_cronograma[0] + "&tipo=2'>Verificar</a></td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Pendiente Verificar</b></td>");
                                }
                                out.print("<td align='center'>---</td>");
                            }
                            if (obj_cronograma[12] != null) {
                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                                int documento = Integer.parseInt(obj_cronograma[12].toString());
                                int codigo = Integer.parseInt(obj_cronograma[13].toString());
                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                                out.print("<td align='center'>");
                                if (lst_firma != null) {
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "' style='width:120px;min-height:65px'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('#S" + obj_firma[1] + "" + i + "').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                    out.print("</td>");
                                } else {
                                    out.print("" + obj_cronograma[18] + "</td>");
                                }
                                //</editor-fold>
                                out.print("<td align='center'>" + obj_cronograma[14].toString().split(" ")[0] + "</td>");
                            } else {
                                if (id_rol == 2) {
                                    out.print("<td align='center'><a href='" + ((obj_cronograma[8] != null) ? "Registro?opc=9&idC=" + obj_cronograma[0] + "" : "") + "'>Validar</a></td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Pendiente Validar</b></td>");
                                }
                                out.print("<td align='center'>---</td>");
                            }
                            for (int j = 0; j < 12; j++) {
                                String[] mesC = obj_cronograma[5].toString().split("-");
                                out.print("<td align='center'>" + ((j + 1) == Integer.parseInt(mesC[1]) ? "<b class='title'>X</b>" : "") + "</td>");
                            }
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr><td>No se encuentran resultados</td></tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    lst_cronograma = jpa_cronograma.consultaCronograma(2, ((anio != 0) ? "" + anio : "0000"));
                    //<editor-fold defaultstate="collapsed" desc="consulta software">
                    if (id_rol == 5) {
                        out.print("<div id='Software' class='tab-pane fade in active' style='overflow:scroll;'>");
                    } else {
                        out.print("<div id='Software' class='tab-pane fade' style='overflow:scroll;'>");
                    }
                    out.print("<br><a href='#' onclick='Imprimir(3);' title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b><br>");
                    out.print("<div style='height:90%; max-height:90%; overflow-y: auto;' id='Imprimir3'>");
                    out.print("<div class='table-container'>");
                    out.print("<table class='table'>");
                    out.print("<tr><td colspan='17' style='background-color:#CCC;text-align:center;'><b style='color:white;'>COPIA NO CONTROLADA</b></td></tr>");
                    out.print("<tr>");
                    out.print("<td align='center' style='width:30%;' colspan='2' rowspan='2'>");
                    out.print("<img src='Interfaz/Contenido/Images/Logo_PT.png' alt='Logo' style='width:70%' /></td>");
                    out.print("<td colspan='10' align='center' style='width:50%;'><b class='negro'>REGISTRO</b></td>");
                    out.print("<td colspan='10' align='center' style='width:20%;'><b class='negro'>CODIGO<br> R-TI-026 </b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='10' align='center'><b class='negro'>CRONOGRAMA ANUAL DE <br> VERIFICACIONES TECNOLOGÍA DE INFORMACIÓN</b></td>");
                    out.print("<td colspan='10' align='center'><b class='negro'>VERSIÓN: 000</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='3'><b class='title'>Asunto: </b>Actividades Programadas</td>");
                    out.print("<td colspan='7'><b class='title'>Tipo: </b>Software</td>");
                    out.print("<td colspan='7'><b class='title'>Año: </b>" + ((anio != 0) ? anio : "2020") + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:20%' align='center'><b class='title'>Actividad</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Verificado por</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Revisado por</b></td>");
                    out.print("<td style='width:10%' align='center'><b class='title'>Fecha</b></td>");
                    for (int i = 0; i < mes.length; i++) {
                        out.print("<th class='sticky2'>" + mes[i] + "</th>");
                    }
                    out.print("</tr>");
                    if (lst_cronograma != null) {
                        for (int i = 0; i < lst_cronograma.size(); i++) {
                            List lst_firma = null;
                            Object[] obj_cronograma = (Object[]) lst_cronograma.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_cronograma[4] + " " + obj_cronograma[3] + "</td>");
                            if (obj_cronograma[8] != null) {
                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                                int documento = Integer.parseInt(obj_cronograma[8].toString());
                                int codigo = Integer.parseInt(obj_cronograma[9].toString());
                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                                out.print("<td align='center'>");
                                if (lst_firma != null) {
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "t' style='width:120px;min-height:65px'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('#S" + obj_firma[1] + "" + i + "t').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                } else {
                                    out.print("" + obj_cronograma[17] + "</td>");
                                }
                                out.print("</td>");
                                //</editor-fold>
                                out.print("<center><td align='center'><input class='td_fechaVe form-control' type='text2' id='" + obj_cronograma[0] + "' value='" + obj_cronograma[10].toString().split(" ")[0] + "'></td></center>");
//                                out.print("<td align='center'>" + obj_cronograma[10].toString().split(" ")[0] + "</td>");
                            } else {
                                if (id_rol == 5) {
                                    out.print("<td align='center'><a href='Registro?opc=8&idC=" + obj_cronograma[0] + "&tipo=2'>Verificar</a></td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Pendiente Verificar</b></td>");
                                }
                                out.print("<td align='center'>---</td>");
                            }
                            if (obj_cronograma[12] != null) {
                                //<editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                                int documento = Integer.parseInt(obj_cronograma[12].toString());
                                int codigo = Integer.parseInt(obj_cronograma[13].toString());
                                lst_firma = jpa_caso.Traer_firmas(documento, codigo);
                                out.print("<td align='center'>");
                                if (lst_firma != null) {
                                    Object[] obj_firma = (Object[]) lst_firma.get(0);
                                    out.print("<div class='sigPad signed' id='S" + obj_firma[1] + "" + i + "e' style='width:120px;min-height:65px'>");
                                    out.print("<div class='sigWrapper'>");
                                    out.print("<canvas class='pad' width='130' height='80px'></canvas>");
                                    out.print("</div>");
                                    out.print("<div class='codigoV' style='display:block'>" + obj_firma[2] + "</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("$(document).ready(function () {");
                                    out.print("$('#S" + obj_firma[1] + "" + i + "e').signaturePad(");
                                    out.print("{");
                                    out.print("displayOnly:true,");
                                    out.print("penColour : '#292929',");
                                    out.print("scale : [0.25,0.25]");
                                    out.print("}");
                                    out.print(").regenerate(" + obj_firma[3] + ");");
                                    out.print("});");
                                    out.print("</script>");
                                    out.print("</td>");
                                } else {
                                    out.print("" + obj_cronograma[18] + "</td>");
                                }
                                //</editor-fold>
                                out.print("<td align='center'>" + obj_cronograma[14].toString().split(" ")[0] + "</td>");
                            } else {
                                if (id_rol == 2) {
                                    out.print("<td align='center'><a href='" + ((obj_cronograma[8] != null) ? "Registro?opc=9&idC=" + obj_cronograma[0] + "" : "") + "'>Validar</a></td>");
                                } else {
                                    out.print("<td align='center'><b class='naranja'>Pendiente Validar</b></td>");
                                }
                                out.print("<td align='center'>---</td>");
                            }
                            for (int j = 0; j < 12; j++) {
                                String[] mesC = obj_cronograma[5].toString().split("-");
                                out.print("<td align='center'>" + ((j + 1) == Integer.parseInt(mesC[1]) ? "<b class='title'>X</b>" : "") + "</td>");
                            }
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr><td>No se encuentran resultados</td></tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("</div>");
                //</editor-fold>
            }

        } catch (IOException ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
