package Tags;

import Controladoras.AreaJpaController;
import Controladoras.CalificacionJpaController;
import Controladoras.CasoJpaController;
import Controladoras.EquipoJpaController;
import Controladoras.ReportanteJpaController;
import Controladoras.TipoSoporteJpaController;
import Controladoras.UsuarioJpaController;
import Controladoras.ListasVerificacionJpaController;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_caso extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        AreaJpaController jpa_area = new AreaJpaController();
        ReportanteJpaController jpa_resportante = new ReportanteJpaController();
        CasoJpaController jpa_caso = new CasoJpaController();
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        TipoSoporteJpaController jpa_tipoS = new TipoSoporteJpaController();
        CalificacionJpaController jpa_calificacion = new CalificacionJpaController();
        ListasVerificacionJpaController jpa_listaEquipo = new ListasVerificacionJpaController();
        Date fecha = new Date();
        String modulo = "";
        try {
            modulo = pageContext.getRequest().getAttribute("modulo").toString();
        } catch (Exception e) {
            modulo = "CLC";
        }
        int id_area = 0, id_usuario = 0, id_equipo = 0, id_programacion = 0, copias = 0;
        List lst_areas = null;
        List lst_contCasosIdA = null;
        List lst_contCasosIdR = null;
        List lst_casosIdA = null;
        List lst_reportantes = null;
        List lst_listaEquipos = null;
        List lst_caso = null;
        List lst_equipos = null;
        List lst_tipoS = null;
        List lst_equipo = null;
        List lst_tecnico = null;
        List lst_calificar = null;
        try {
            if (modulo.equals("Sp")) {
                //<editor-fold defaultstate="collapsed" desc="registro reportante">
                String modulo2 = "";
                try {
                    modulo2 = pageContext.getRequest().getAttribute("modulo2").toString();
                } catch (Exception e) {
                    modulo2 = "CR";
                }
                String filtro = pageContext.getRequest().getAttribute("filtro").toString();
                List lst_turno = jpa_usuario.traerUsuarioTurno();
                List lst_usuario = (List) pageContext.getRequest().getAttribute("lst_usuario");
                List lst_reportante = (List) pageContext.getRequest().getAttribute("lst_reportante");
                List lst_area = jpa_area.consultarAreas();
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                if (lst_reportante == null) {
                    //<editor-fold defaultstate="collapsed" desc="FIRMA ELECTRONICA - REGISTRAR REPORTANTE">
                    out.print("<div class='modal fade' id='Firma' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog' style='width:40%'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    if (Integer.parseInt(obj_usuario[1].toString()) != 0 && Integer.parseInt(obj_usuario[2].toString()) != 0) {
                        out.print("<a href='index.jsp' class='close'>&times;</a>");
                    } else {
                        out.print("<a href='index.jsp' class='close'>&times;</a>");
                    }
                    String reportante_p = obj_usuario[6] + " " + obj_usuario[7];
                    int document_p = Integer.parseInt(obj_usuario[1].toString());
                    int codigo_p = Integer.parseInt(obj_usuario[2].toString());
                    out.print("<h4 class='modal-title'>Registro Reportante</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<form action='Caso?opc=3&mod=Sp' name='formA' id='formA' method='post' style='margin:0px;'>");
                    out.print("<table>");
                    out.print("<tr>");
                    out.print("<td>Reportante: ");
                    out.print("&nbsp;<input type='text' class='form-control' name='Txt_reportante' id='Txt_reportante' value='" + reportante_p + "' placeholder='Reportante' style='width:270px;margin: 0px;'  readonly='true'><br><br>");
                    out.print("Documento: ");
                    out.print("<input type='text' class='form-control' name='Txt_documento' id='Txt_documento' value='" + document_p + "' placeholder='Documento' style='width:270px; margin: 0px;'  readonly='true'><br><br>");
                    out.print("Correo: ");
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<input type='text' class='form-control' name='Txt_correo' id='txt_correo' value='' placeholder='Ejemplo@platitec-sa.com' style='width:270px;margin: 0px;'  required><br><br>");
                    out.print("Codigo: ");
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<input type='text' class='form-control' name='Txt_codigo' id='Txt_codigo' value='" + codigo_p + "' placeholder='Codigo' style='width:80px;margin: 0px;'  readonly='true'><br><br>");
                    out.print("Area:");
                    out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "<select name='Cbx_area' id='Cbx_area' class='dropdown-toggle2' required>");
                    out.print("<option style='width:0px; margin: 0px;display:none;' value=''>Seleccionar Area</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (Integer.parseInt(obj_area[2].toString()) == 1) {
                            out.print("<option value='" + obj_area[0] + "' >" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</td></tr>");
                    out.print("</table>");
                    out.print("<br>");
                    out.print("<center>");
                    out.print("</center>");
                    //       <editor-fold defaultstate="collapsed" desc="PAD FIRMA">
                    if (Integer.parseInt(obj_usuario[1].toString()) != 0 && Integer.parseInt(obj_usuario[2].toString()) != 0) {
                        List lst_firma = jpa_caso.Traer_firmas(Integer.parseInt(obj_usuario[1].toString()), Integer.parseInt(obj_usuario[2].toString()));
                        //<editor-fold defaultstate="collapsed" desc="mostrar firma">
                        if (lst_firma != null) {
                            out.print("<td align='center' align='center'>");
                            if (lst_firma != null) {
                                Object[] obj_firma = (Object[]) lst_firma.get(0);
                                out.print("<td>");
                                out.print("<div class='sigPad' id='smoothed' style='width:100%;'>");
                                out.print("<div class='sig sigWrapper current' style='height: auto; display: block;pointer-events: none;'>");
                                out.print("<div class='codigo' style='display: block; color:#596275' >" + obj_firma[2] + "</div>");
                                out.print("<canvas class='pad' width=440' height='250'></canvas>");
                                out.print("<input type='hidden' name='Txt_firma' class='output' value='" + obj_firma[3] + "'  required>");
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
                                out.print("penColour : '#596275'");
                                out.print("}");
                                out.print(")" + ((obj_firma[3].toString().length() > 0) ? ".regenerate(" + obj_firma[3] + ");" : ""));
                                out.print("});");
                                out.print("</script>");
                                out.print("</td>");
                            }
                            out.print("</td>");
                            out.print("<center><br><input type='submit' value='Registrar' style='width:100px;'></center>");
                        } else {
                            out.print("<center><b class='rojo'>El usuario no tiene firma registrada</b></center>");
                            out.print("<center><br><input type='submit' value='Registrar' style='width:100px;'></center>");
                        }
                        //</editor-fold>
                    }
//           //</editor-fold>
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    if (Integer.parseInt(obj_usuario[1].toString()) != 0 && Integer.parseInt(obj_usuario[2].toString()) != 0) {
                        out.print("<script>");
                        out.print("$(\"#Registrar,#Firma\").modal(\"show\");");
                        out.print("</script>");
                    }
                    //</editor-fold>
                } else if (lst_reportante != null) {
                    //<editor-fold defaultstate="collapsed" desc="MODULO REPORTANTE">
                    Object[] obj_reportante = (Object[]) lst_reportante.get(0);
                    //</editor-fold>
                    out.print("<div style='display:flex; justify-content: space-between;'>");
                    if (modulo2.equals("CR")) {
                        out.print("<div><h3>Casos Registrados</h3></div>");
                    } else if (modulo2.equals("CSR")) {
                        out.print("<div><h3>Casos Solucionados</h3></div>");
                    } else {
                        out.print("<div><h3>Casos</h3></div>");
                    }
                    out.print("<div style='display:flex; justify-content: space-between;'>");
                    out.print("<div><span class='fa fa-plus fa-lg' onclick='mostrarConvencion(1)'></span>&nbsp;&nbsp;<b class='title'>Registrar Caso</b></div>");
                    out.print("<div style='margin-left:29px'><span class='fab fa-cuttlefish fa-lg' onclick='mostrarConvencion(2)'></span>&nbsp;&nbsp;<b class='title'>Consultar</b></div>");
                    out.print("</div>");
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="¿QUE DESEA CONSULTAR?">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:400px; height:200px; position: absolute;top:15%; left:38%;text-align:left '>");
                    out.print("<center>");
                    out.print("<a href='Caso?opc=6&mod=Sp&mod2=&txt_bus=&txt_documento=" + obj_reportante[4] + "&txt_codigo=" + obj_reportante[5] + "' class='close'>&times;</a>");
                    out.print("<h4>¿Que desea consulta?</h4>");
                    out.print("<form action='Caso?opc=6&mod=Sp&txt_bus=&txt_documento=" + obj_reportante[4] + "&txt_codigo=" + obj_reportante[5] + "' name='formCon' method='post'>");
                    out.print("<br><b>Mis casos</b>&nbsp;<input type='radio' class='radioB' name='mod2' value='CR' onchange='javascript:this.value=this.value.toUpperCase();' >&nbsp;<b class='title'> | </b>");
                    out.print("&nbsp;<b>Casos solucionados</b>&nbsp;<input type='radio' class='radioB' name='mod2' value='CSR'  onchange='javascript:this.value=this.value.toUpperCase();'>");
                    out.print("<br/><br/><input type='submit' value='Consultar'>");
                    out.print("</form>");
                    out.print("</center>");
                    out.print("</div>");
                    out.print("</fieldset>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="REGISTRAR CASO">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                    out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:951px; height:567px; position: absolute;top:3%; left:19%;text-align:left '>");
                    out.print("<center>");
                    out.print("<a href='Caso?opc=6&mod=Sp&mod2=&txt_bus=&txt_documento=" + obj_reportante[4] + "&txt_codigo=" + obj_reportante[5] + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Registrar Caso</h4>");
                    out.print("<br><form action='Caso?opc=2&mod=Sp' name='formRC' method='post'>");
                    out.print("<input type='hidden' value='" + obj_reportante[0] + "' id='idR' name='idR'>");
                    out.print("<input type='hidden' value='" + obj_reportante[3] + "' id='idA' name='idA'>");
                    out.print("<input type='hidden' value='" + obj_reportante[4] + "' id='idA' name='dcm'>");
                    out.print("<input type='hidden' value='" + obj_reportante[5] + "' id='idA' name='cdg'>");
                    String tecnicos = "";
                    for (int i = 0; i < lst_turno.size(); i++) {
                        Object[] obj_turno = (Object[]) lst_turno.get(i);
                        tecnicos = tecnicos + "[" + obj_turno[0] + "]";
                    }
                    out.print("<input type='hidden' name='idU' value='" + tecnicos + "'>");
                    out.print("<table style='width:90%;font-size:12px'>");
                    out.print("<tr>");
                    out.print("<td style='width:35%'>");
                    out.print("<b>Fecha: </b><b id='fecha_hora'></b><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Reportante: </b>" + obj_usuario[6] + "&nbsp;" + obj_usuario[7] + "");
                    out.print("</td>");
                    out.print("<td>");
                    //<editor-fold defaultstate="collapsed" desc="AREA">
//                out.print("<b>Area: </b>" + obj_usuario[6] + "");
//                out.print("</td>");
//                out.print("<td style='width:30%'>");
//                out.print("<b>Area: </b><br>");
//                out.print("<select name='idA' id='area-id' onchange='javascript:document.getElementById(\"idA\").value=this.value;document.formSA.submit();'>");
//                if (lst_reportantes != null) {
//                    Object[] obj_reportante = (Object[]) lst_reportantes.get(0);
//                    out.print("<option value='" + obj_reportante[4] + "' style='display:none'>" + obj_reportante[5] + "</option>");
//                } else {
//                    out.print("<option value='' style='display:none'>Seleccionar area</option>");
//                }
//                for (int i = 0; i < lst_areas.size(); i++) {
//                    Object[] obj_area = (Object[]) lst_areas.get(i);
//                    out.println("<option value=" + obj_area[0] + ">" + obj_area[1] + "</option>");
//                }
//                out.print("</select><br><br>");
//                out.print("</td>");
//                out.print("<td style='width:31%'>");
//                out.print("<b>Reportante: </b><br>");
//                out.print("<select name='idR' id='usuario-id' required>");
//                out.print("<option value='' style='display:none'>Seleccionar reportante</option>");
//                if (lst_reportantes != null) {
//                    for (int i = 0; i < lst_reportantes.size(); i++) {
//                        Object[] obj_reportante = (Object[]) lst_reportantes.get(i);
//                        out.println("<option value=" + obj_reportante[0] + ">" + obj_reportante[1] + " " + obj_reportante[2] + "</option>");
//                    }
//                }
//                out.print("</select><br><br>");
//                out.print("</td>");
//                out.print("<td style='width:5%'>");
//                out.print("<a href='#' class='icon' title='Registrar Reportante' data-toggle='modal' data-target='#Registrar'><i class='fa fa-Plus fa-lg'></i></a>");
//                out.print("</td>");
                    //</editor-fold>
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<br><b>Prioridad: </b>");
                    out.print("&nbsp;Alta&nbsp;<input type='radio' class='radioB' name='rdo_prioridad' value='Alta'>");
                    out.print("&nbsp;Media&nbsp;<input type='radio' class='radioB' name='rdo_prioridad' value='Media'>");
                    out.print("&nbsp;Baja&nbsp;<input type='radio' class='radioB' name='rdo_prioridad' value='Baja' checked>");
                    out.print("</td>");
                    out.print("<td valign='top' colspan='2'>");
                    out.print("<br><b>Tecnicos en turno: </b>");
                    for (int i = 0; i < lst_turno.size(); i++) {
                        Object[] obj_turno = (Object[]) lst_turno.get(i);
                        out.print("" + obj_turno[1] + " " + obj_turno[2] + " - ");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table><br>");
                    out.print("</center>");
                    out.print("<textarea id='editor'  name='txt_descripcion' width='100%' height='50%'><b style='color:#5356ad'>Asunto</b><p>*</p></textarea>");
                    out.print("<br/><input type='submit' onclick='timer()' value='Enviar Caso'>");
                    out.print("</form>");
                    out.print("<script>");
                    out.print("$(\"#Registrar_caso\").modal(\"show\");");
                    out.print("</script>");
                    out.print("</div>");
                    //</editor-fold>
                    if (modulo2.equals("CR")) {
                        //<editor-fold defaultstate="collapsed" desc="CONSULTA CASOS REPORTADOS">
                        List lst_casos = null;
                        if (filtro.equals("")) {
                            lst_casos = jpa_caso.consultaCasosUsuarioReportante((Integer) obj_reportante[0]);
                        } else {
                            lst_casos = jpa_caso.consultaCasosUsuarioFiltroReportante((Integer) obj_reportante[0], filtro);
                        }
                        out.print("<div style='float:right;'><form action='Caso?opc=6&mod=Sp&mod2=CR&txt_documento=" + obj_reportante[4] + "&txt_codigo=" + obj_reportante[5] + "' name='formA' method='post'>");
                        out.print("<br><input type='text' class='form-control' name='txt_bus' id='filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'>");
                        out.print("</form></div>");
                        out.print("<div id='NavPosicion'></div>");
                        if (lst_casos != null) {
                            out.print("<table class='table'>");
                            for (int k = 0; k < lst_casos.size(); k++) {
                                Object[] obj_casosR = (Object[]) lst_casos.get(k);
                                out.print("<tr>");
                                out.print("<td colspan='4' style='background:#ddd'></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:15%'><b class='title'>Fecha: </b><br>" + obj_casosR[1] + "<hr><b class='title'>Prioridad: </b><br>" + obj_casosR[6] + "</td>");
                                out.print("<td valign='top'><b class='title'>Caso: </b>" + obj_casosR[5] + "</td>");
                                out.print("<td><b class='title'>Reportante: </b><br>" + obj_casosR[4] + "</td>");
                                out.print("</tr>");

                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados',15);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                            //fin contenido
                        } else {
                            out.print("<br><b>No se encuentran resultados</b>");
                        }
                        //</editor-fold>
                    } else if (modulo2.equals("CSR")) {
                        //<editor-fold defaultstate="collapsed" desc="CONSULTAR CASOS SOLUCIONADOS">
                        int year_caso = 0, month_caso = 0, year_actual = 0, month_actual = 0;
                        if (filtro.equals("")) {
                            lst_caso = jpa_caso.consultarCasosSolucionadosReportante((Integer) obj_reportante[0]);
                        } else {
                            lst_caso = jpa_caso.consultarCasosSolucionadosFiltroReportante((Integer) obj_reportante[0], filtro);
                        }
                        out.print("<div style='height:89%; width:100%; max-height:89%; overflow:auto;'>");
                        out.print("<div style='display:flex; justify-content: space-between; align-items: center;'>");
                        out.print("<div id='NavPosicion'></div>"
                                + "<div style='float:right;'><form action='Caso?opc=6&mod=Sp&mod2=CSR&txt_documento=" + obj_reportante[4] + "&txt_codigo=" + obj_reportante[5] + "' name='formA' method='post'>");
                        out.print("<input type='text' class='form-control' name='txt_bus' id='filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'>");
                        out.print("</form></div>");
                        out.print("</div>");
                        if (lst_caso != null) {
                            out.print("<table class='table' id='resultados'>");
                            for (int i = 0; i < lst_caso.size(); i++) {
                                Object[] obj_casos = (Object[]) lst_caso.get(i);
                                out.print("<tr>");
                                out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td style='width:15%' rowspan='3'  align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                                out.print("<td style='width:70%' valign='top' colspan='2'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                                out.print("<td style='width:15%' rowspan='3' align='center'><b class='title'>Reportante: </b>" + obj_casos[4] + ""
                                        + "<br><b class='title'>Área:</b>" + obj_casos[11] + "<hr>");
                                if (Integer.parseInt(obj_casos[13].toString()) != 0) {
                                    out.print("<b>Puntuación: </b><br>");
                                    if (Integer.parseInt(obj_casos[13].toString()) > 0) {
                                        int contador_star = Integer.parseInt(obj_casos[13].toString());
                                        for (int j = 0; j < contador_star; j++) {
                                            if (contador_star == 1) {
                                                out.print("<i style='color:#F62;'class=\"fas fa-star\"></i> ");
                                            } else {
                                                out.print("<i style='color:#DCC624;'class=\"fas fa-star\"></i> ");
                                            }
                                        }
                                    } else {
                                        out.print("N/A");
                                    }
                                    out.print("<br><b>Opinion: </b><br>" + ((obj_casos[14] != null) ? obj_casos[14].toString() : "N/A") + "</td>");
                                    out.print("</tr>");
                                } else {
                                    year_caso = Integer.parseInt(obj_casos[16].toString());
                                    month_caso = Integer.parseInt(obj_casos[17].toString());
                                    year_actual = Integer.parseInt(obj_casos[18].toString());
                                    month_actual = Integer.parseInt(obj_casos[19].toString());
                                    if (year_caso == year_actual && month_caso == month_actual) {
                                        out.print("<b style=' color:#ff9700;'>Pendiente Calificar Caso: <br></b><div style='margin-left:28%;margin-top:6;display:flex;'>"
                                                + "<div style='margin-top: 5px;' class='animation'><i class='fas fa-long-arrow-alt-right fa-lg-cs-fc'></i></div>"
                                                + "<div style='margin-left: 9px;'><i onclick=\"javascript:window.open('http://172.16.2.117:8084/REDEAC/Calificar_caso?opc=1&id_caso=" + obj_casos[0] + "', '', 'width=1024,height=720,left=50,\\n\\\n"
                                                + "										top=50,toolbar=yes');\n"
                                                + "                                                                         void 0\" style='cursor:pointer;'  class='far fa-id-badge fa-lg-cs'></i></a></div>"
                                                + "<div style='margin-top: 5px;'  class='animation2'><i class=\"fas fa-long-arrow-alt-left fa-lg-cs-fc\"></i></div></div>");
                                    } else {
                                        out.print("<b>Caso finalizado <br/> (Sin calificación)</b>");
                                    }
                                }
                                out.print("<tr><td colspan='2' valign='top'><b class='title'>Solución: </b></td></tr>");
                                out.print("<tr><td style='width:35%'>" + obj_casos[9] + "</td>"
                                        + "<td style='width:35%'><div style='float:left'><b>Responsable: </b>" + obj_casos[10] + ""
                                        + "<br><b>Fecha Ejecución: </b>" + obj_casos[12] + ""
                                        + "<br><b>Fecha Solución: </b>" + obj_casos[8] + "</div></td>");
                                out.print("</tr>");
                            }
                            out.print("</table>");
                            out.print("<script type='text/javascript'>");
                            out.print("var pager = new Pager('resultados',20);");
                            out.print("pager.init();");
                            out.print("pager.showPageNav('pager','NavPosicion');");
                            out.print("pager.showPage(1);");
                            out.print("</script>");
                            out.print("</div>");
                        } else {
                            out.print("<br><b class='title'>No se encontraron resultados</b>");
                        }
                        //</editor-fold>   
                    }
                }
                //</editor-fold>
            }
            if (modulo.equals("CA")) {
                //<editor-fold defaultstate="collapsed" desc="consulta casos reportados">
                int id_rol = 0, id_caso = 0;
                try {
                    id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
                } catch (Exception e) {
                    id_rol = 0;
                }
                try {
                    id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
                } catch (Exception e) {
                    id_usuario = 0;
                }
                try {
                    id_caso = Integer.parseInt(pageContext.getRequest().getAttribute("id_caso").toString());
                } catch (Exception e) {
                    id_caso = 0;
                }
                List lst_casos = jpa_caso.consultaCasosUsuario();
                out.print("<h3>Casos Reportados</h3>");
                if (lst_casos != null) {
                    out.print("<div style='height:93%; max-height:94%; overflow-y: auto;'>");
                    out.print("<div class='panel-group' style='width:99%' id='accordion'>");
                    lst_areas = jpa_area.consultarAreas();
                    for (int i = 0; i < lst_areas.size(); i++) {
                        Object[] obj_areas = (Object[]) lst_areas.get(i);
                        lst_reportantes = jpa_resportante.consultarReportantesIdArea(Integer.parseInt(obj_areas[0].toString()));
                        int contCA = 0;
                        lst_contCasosIdA = jpa_caso.consultaCasosAreaContador(Integer.parseInt(obj_areas[0].toString()));
                        if (lst_contCasosIdA != null) {
                            Object[] obj_contCasosIdA = (Object[]) lst_contCasosIdA.get(0);
                            contCA = Integer.parseInt(obj_contCasosIdA[0].toString());
                        } else {
                            contCA = 0;
                        }
                        out.print("<div class='panel panel-default'>");
                        out.print("<div class='panel-heading'>");
                        out.print("<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordion' href='#collapse" + i + "'>" + obj_areas[1] + "</a></h4>" + ((contCA != 0) ? "<span class='label pull-right label-warning'>" + contCA + "</span>" : "") + "");
                        out.print("</div>");
                        out.print("<div id='collapse" + i + "' class='panel-collapse collapse'>");
                        out.print("<div class='panel-body'>");
                        //contenido
                        lst_casosIdA = jpa_caso.consultaCasosAreaId((Integer) obj_areas[0]);
                        if (lst_casosIdA != null) {
                            out.print("<table class='table'>");
                            for (int k = 0; k < lst_casosIdA.size(); k++) {
                                Object[] obj_casosR = (Object[]) lst_casosIdA.get(k);
                                out.print("<tr>");
                                out.print("<td colspan='4' style='background:#ddd'></td>");
                                out.print("</tr>");
                                out.print("<tr>");
                                out.print("<td><b class='title'>Fecha: </b>" + obj_casosR[1] + "<hr><b class='title'>Reportante: </b>" + obj_casosR[4] + "</td>");
                                out.print("<td valign='top'><b class='title'>Caso: </b>" + obj_casosR[5] + "</td>");
                                out.print("<td><b class='title'>Prioridad: </b>" + obj_casosR[6] + "</td>");
                                if (id_rol == 3 || id_rol == 5 || id_rol == 6 || id_rol == 7) {
                                    out.print("<td align='center'><a href='Caso?opc=1&mod=CA&idC=" + obj_casosR[0] + "&txt_bus=' class='icon' title='Solucionar'><i class='fa fa-file-signature fa-lg'></i></a></td>");
                                } else if (id_rol == 1) {
                                    int idC = Integer.parseInt(obj_casosR[0].toString());
                                    out.print("<td align='center'><a href='#' onclick='alertas(" + idC + ")' style='color: black;'><i class=\"fas fa-trash\" style='font-size: 20px;'></i></a></td>");
                                }
                                out.print("</tr>");

                            }
                            out.print("</table>");
                        } else {
                            out.print("<b>El usuario no ha registrado casos</b>");
                        }
                        //fin contenido
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<br><b>No se encuentran resultados</b>");
                }
                if (id_caso != 0) {
                    lst_caso = jpa_caso.consultaCasoId(id_caso);
                    Object[] obj_caso = (Object[]) lst_caso.get(0);
                    lst_equipos = jpa_equipo.consultaEquipos();
                    lst_listaEquipos = jpa_listaEquipo.consultaListaDetalleVerificacionGeneral();
                    lst_tipoS = jpa_tipoS.consultarTipoSoporteIdRol(3);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2'   style='opacity: 1.03;  display:block;'>");
                    out.print("<div style='width:69%;margin:auto;margin-top:auto;'>");
                    out.print("<div class='modal-content' style='height: 98%;width:987px;overflow-y: auto;'>");
                    out.print("<form action='Caso?opc=4' name='formA' method='post'>");
                    out.print("<input type='hidden' name='idC' value='" + id_caso + "'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Caso?opc=1&mod=CA' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Solucion caso</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    out.print("<table style='width:90%;font-size:12px' class='table'>");
                    out.print("<tr>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Caso</th>");
                    out.print("<th>Reportante</th>");
                    out.print("<th>Prioridad</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td style='width:20%;'>" + obj_caso[1] + "</td>");
                    out.print("<td valign='top' style='width:60%;'>" + obj_caso[4] + "</td>");
                    out.print("<td style='width:20%;'>" + obj_caso[3] + " - " + obj_caso[13] + "</td>");
                    out.print("<td style='width:20%;'>" + obj_caso[5] + "</td>");
                    out.print("</tr>");
                    out.print("</table></br>");
                    out.print("<table style='width:90%;font-size:12px; margin-left:auto;'>");
                    out.print("<tr>");
                    out.print("<td style='width:30%'>");
                    out.print("<b>Tipo Soporte: </b><br>");
                    out.print("<select name='slc_tipoS' id='tipoS-id' required>");
                    out.print("<option value='' style='display:none'>Seleccionar soporte</option>");
                    for (int i = 0; i < lst_tipoS.size(); i++) {
                        Object[] obj_tipoS = (Object[]) lst_tipoS.get(i);
                        if (Integer.parseInt(obj_tipoS[0].toString()) > 1) {
                            out.println("<option value=" + obj_tipoS[0] + ">" + obj_tipoS[1] + "</option>");
                        }
                    }
                    out.print("</select><br><br>");
                    out.print("</td>");
                    out.print("<td style='width:30%'>");
                    out.print("<b>Fecha Ejecucion: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaI' id='start' value=''   autocomplete='off' placeholder='Fecha inicio' required><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Hora Ejecucion: </b><br>");
                    out.print("<input type='time' class='form-control' name='txt_horaI' id='horaI-id' value='' style='width:50%' placeholder='Hora Inicio' required><br>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td>");
                    out.print("<b>Equipo: </b><br>");
                    out.print("<select name='slc_equipo' id='equipo-id' data-live-search='true' required>");
                    out.print("<option value='' style='display:none'>Seleccionar PC</option>");
                    for (int i = 0; i < lst_equipos.size(); i++) {
                        Object[] obj_equipos = (Object[]) lst_equipos.get(i);
                        out.println("<option value='" + obj_equipos[0] + "'>" + obj_equipos[1] + "</option>");
                    }
                    out.print("</select>");
                    out.print("<select name='slc_l_equipo' id='equipo-id' data-live-search='true' style='width:35%;' required>");
                    out.print("<option value='' style='display:none'>Seleccione Equipo</option>");
                    for (int k = 0; k < lst_listaEquipos.size(); k++) {
                        Object[] obj_Listequipos = (Object[]) lst_listaEquipos.get(k);
                        out.println("<option value='" + obj_Listequipos[0] + "'>" + obj_Listequipos[3] + "</option>");
                    }
                    out.print("</select><br/><br/>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Fecha fin: </b><br>");
                    out.print("<input type='text' class='form-control' name='txt_fechaF' id='end' value='' autocomplete='off' placeholder='Fecha fin' required><br>");
                    out.print("</td>");
                    out.print("<td>");
                    out.print("<b>Hora fin: </b><br>");
                    out.print("<input type='time' class='form-control' name='txt_horaF' id='horaF-id' value='' style='width:50%' placeholder='Hora fin' required><br>");
                    out.print("</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<div class='modal-body'>");
                    out.print("<textarea id='editor' name='txt_descripcion' width='100%' height='50%'><div contenteditable='true'><p>*</p></div></textarea>");
                    out.print("</div>");
                    out.print("<div class='modal-footer'>");
                    out.print("<input type='submit' value='Solucionar'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print("$(\"#Solucion\").modal(\"show\");");
                    out.print("</script>");
                    out.print("<script>");
                    out.print("$('#summernote').summernote({");
                    out.print("placeholder: 'Descripción',");
                    out.print("tabsize: 2,");
                    out.print("height: 150");
                    out.print("});");
                    out.print("</script>");
                }
                //</editor-fold>
            }
            if (modulo.equals("CS")) {
                //<editor-fold defaultstate="collapsed" desc="consulta casos solucionados">
                String filtro = pageContext.getRequest().getAttribute("filtro").toString();
                if (filtro.equals("")) {
                    lst_caso = jpa_caso.consultarCasosSolucionados();
                } else {
                    lst_caso = jpa_caso.consultaCasoSolucionFiltro(filtro);
                }
                out.print("<h3>Casos Solucionados</h3>");
                out.print("<form action='Caso?opc=1&mod=CS' name='formA' method='post'>");
                out.print("<div style='float: right;width:20%; margin:-7px'><input type='text' class='form-control' class='form-control' id='Txt_filtro' name='txt_bus'  id='filtro' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("<div style='width:100%' id='NavPosicion'></div>");
                out.print("</form>");
                out.print("<div style='height:88%; max-height:89%; overflow-y: auto;'>");
                if (lst_caso != null) {
                    out.print("<table class='table' id='resultados'>");
                    for (int i = 0; i < lst_caso.size(); i++) {
                        Object[] obj_casos = (Object[]) lst_caso.get(i);
                        out.print("<tr>");
                        out.print("<td colspan='4' style='background-color: #ddd;'></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td style='width:15%' rowspan='3' align='center'><b class='title'>Fecha: </b>" + obj_casos[1] + "<hr/><b class='title'>Prioridad: </b>" + obj_casos[6] + "</td>");
                        out.print("<td style='width:70%' colspan='2' valign='top'><b class='title'>Caso: </b>" + obj_casos[5] + "</td>");
                        out.print("<td style='width:15%' rowspan='3' align='center'><b class='title'>Reportante: </b>" + obj_casos[4] + "<br><b class='title'>Área:</b>" + obj_casos[11] + "");
                        out.print("<hr><b>Puntuación:<br></b>");
                        if (Integer.parseInt(obj_casos[17].toString()) > 0) {
                            int contador_star = Integer.parseInt(obj_casos[17].toString());
                            for (int j = 0; j < contador_star; j++) {
                                out.print("<i style='color:#DCC624;'class=\"fas fa-star\"></i> ");
                            }
                        } else {
                            out.print("N/A");
                        }
                        out.print("<br><b>Opinión:</b><br>" + ((obj_casos[18] != null) ? obj_casos[18].toString() : "N/A") + "");
                        out.print("</td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        out.print("<td valign='top' colspan='2'><b class='title'>Solución: </b></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                        String con_solucion = obj_casos[9].toString().replace("contenteditable=\"true\"", "contenteditable=\"false\"");
                        out.print("<td style='width:35%' valign='top'>" + con_solucion + "</td>"
                                + "<td style='width:35%' valign='top' ><div style='float:left'><b>Responsable: </b>" + obj_casos[10] + "&nbsp;|&nbsp;" + obj_casos[8] + ""
                                + "<br><b>FECHA EJECUCIÓN:</b> " + obj_casos[8] + "<br/>"
                                + "<b>PARADA EQUIPO: </b>" + obj_casos[13] + "&nbsp;|&nbsp;<b>PARADA PRODUCCIÓN: " + obj_casos[14] + "</b><br>"
                                + "<b>PC: </b> " + obj_casos[15] + "<br/>"
                                + "<b>EQUIPO: </b> " + obj_casos[16] + "</div></td>");
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
                } else {
                    out.print("<br><b class='title'>No se encontraron resultados</b>");
                }
                //</editor-fold>
            }
            if (modulo.equals("CE")) {
                //<editor-fold defaultstate="collapsed" desc="Casos Encuesta">
                id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
                id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("id_equipo").toString());
                id_programacion = Integer.parseInt(pageContext.getRequest().getAttribute("id_programacion").toString());
                copias = Integer.parseInt(pageContext.getRequest().getAttribute("copias").toString());
                lst_equipo = jpa_equipo.consultaEquipoId(id_equipo);
                Object[] obj_equipo = (Object[]) lst_equipo.get(0);
                lst_tecnico = jpa_usuario.consultaUsuarioId(id_usuario);
                Object[] obj_usuario = (Object[]) lst_tecnico.get(0);
                lst_calificar = jpa_calificacion.consultaCalificacionCaso(id_programacion, copias);
                String Foto = "\"" + "Interfaz/Fotos/User.png" + "\"";
                String preguntas = "1) DISPONIBILIDAD POR PARTE DEl TECNICO PARA ATENDER SUS REQUERIMIENTOS? / "
                        + "2) CUAL ES EL NIVEL DE SATISFACCIÓN CON LA ATENCIÓN PRESTADA POR EL TECNICO? / "
                        + "3) LAS SOLUCIONES QUE OFRECE EL ÁREA DEl TECNICO SON LAS REQUERIDAS? / "
                        + "4) EL TECNICO QUE LE ATENDIÓ FUE CLARO AL BRINDAR LA INFORMACIÓN DE SU SOLICITUD? / "
                        + "5) EL TIEMPO DE RESPUESTA A LOS INCONVENIENTES REPORTADOS ES EL ADECUADO?";
                out.print("<div class='modal fade' id='Registrar_caso' role='dialog' data-backdrop='static' data-keyboard='false'>");
                out.print("<div class='modal-dialog modal-lg'>");
                out.print("<div class='modal-content'>");
                out.print("<div class='modal-header'>");
                out.print("<h4 class='modal-title'>Encuesta</h4>");
                out.print("</div>");
                out.print("<div class='modal-body' align='center'>");
                out.println("<form action='Caso?opc=5' method='post'>");
                out.println("<input type='hidden' name='idE' value='" + id_equipo + "' />");
                out.println("<input type='hidden' name='idU' value='" + id_usuario + "' />");
                out.println("<input type='hidden' name='idP' value='" + id_programacion + "' />");
                out.println("<input type='hidden' name='copias' value='" + copias + "' />");
                out.println("<input type='hidden' name='txt_preguntas' value='" + preguntas + "'/>");
                out.println("<table class='table' style='width:100%'>");
                out.println("<td align='center' colspan='4'>");
                out.println("<h5><b class='title'>Equipo: </b><b>" + obj_equipo[1] + "</b></h5><hr/>");
                out.println("<h5><b class='title'>Tecnico a calificar: </b><b style='color:black;'>" + obj_usuario[1] + " " + obj_usuario[2] + "</h5>");
                out.println("</td>");
                out.println("<td colspan='2' align='center'>"
                        + "<img src='Interfaz/Fotos/" + obj_usuario[3] + ".jpg' width='120px' height='122px'>"
                        + "</td>");
                out.println("</tr>");
                out.println("<th>CALIFICACIÓN</th>");
                out.println("<th>Muy<br />Insatisfecho</th>");
                out.println("<th>Insatisfecho</th>");
                out.println("<th>Poco<br />Satisfecho</th>");
                out.println("<th>Satisfecho</th>");
                out.println("<th>Muy<br />Satisfecho</th>");
                out.println("</tr>");
                // <editor-fold defaultstate="collapsed" desc="2.1 CONSULTA ENCUESTA">
                if (lst_calificar != null) {
                    Object[] obj_calificar = (Object[]) lst_calificar.get(0);
                    String[] arg_preguntas = obj_calificar[4].toString().split(" / ");
                    for (int i = 0; i < arg_preguntas.length; i++) {
                        out.println("<tr>");
                        out.println("<td>" + arg_preguntas[i] + "</td>");
                        for (int j = 1; j <= 5; j++) {
                            if ((i + 1) == 1) {
                                if (obj_calificar[5].equals(j)) {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true' checked/></td>");
                                } else {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true'/></td>");
                                }
                            } else if ((i + 1) == 2) {
                                if (obj_calificar[6].equals(j)) {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true' checked/></td>");
                                } else {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true'/></td>");
                                }
                            } else if ((i + 1) == 3) {
                                if (obj_calificar[7].equals(j)) {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true' checked/></td>");
                                } else {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true'/></td>");
                                }
                            } else if ((i + 1) == 4) {
                                if (obj_calificar[8].equals(j)) {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true' checked/></td>");
                                } else {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true'/></td>");
                                }
                            } else if ((i + 1) == 5) {
                                if (obj_calificar[9].equals(j)) {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true' checked/></td>");
                                } else {
                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + (i + 1) + "' value='" + j + "' disabled='true' /></td>");
                                }
                            }
                        }
                        out.println("</tr>");
                    }
                    out.println("<tr>");
                    out.println("<td>7) Observaciones</td>");
                    out.println("<td align='center' colspan='5'><textarea name='txt_observaciones' id='Txt_observaciones' style='width:400px;height:60px' readonly='true' placeholder='Ingresar observación'>" + obj_calificar[10].toString().toUpperCase() + "</textarea></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td align='center' colspan='7'><b class='title'>Responsable: </b><b>" + obj_calificar[11] + "</b></td>");
                    out.println("</tr>");
                } // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="2.2 REGISTRAR ENCUESTA ">
                else {
                    out.println("<tr>");
                    out.println("<td>1) DISPONIBILIDAD POR PARTE DEl TECNICO PARA ATENDER SUS REQUERIMIENTOS?</td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_1' value='1'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_1' value='2'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_1' value='3'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_1' value='4' checked/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_1' value='5'/></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>2) CUAL ES EL NIVEL DE SATISFACCIÓN CON LA ATENCIÓN PRESTADA POR EL TECNICO?</td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_2' value='1'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_2' value='2'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_2' value='3'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_2' value='4' checked/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_2' value='5'/></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>3) LAS SOLUCIONES QUE OFRECE EL ÁREA DEl TECNICO SON LAS REQUERIDAS?</td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_3' value='1'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_3' value='2'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_3' value='3'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_3' value='4' checked/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_3' value='5'/></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>4)  EL TECNICO QUE LE ATENDIÓ FUE CLARO AL BRINDAR LA INFORMACIÓN DE SU SOLICITUD?</td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_4' value='1'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_4' value='2'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_4' value='3'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_4' value='4' checked/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_4' value='5'/></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>5)  EL TIEMPO DE RESPUESTA A LOS INCONVENIENTES REPORTADOS ES EL ADECUADO?</td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_5' value='1'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_5' value='2'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_5' value='3'/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_5' value='4' checked/></td>");
                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_5' value='5'/></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td>6) Observaciones</td>");
                    out.println("<td align='center' colspan='5'><textarea name='txt_observaciones' id='Txt_observaciones' style='width:400px;height:60px' onkeyup='javascript:this.value=this.value.toUpperCase();' placeholder='Ingresar observación'>N/A</textarea>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_observaciones');val1.add(Validate.Presence);</script></td>");
                    out.println("</tr>");
                    out.println("<tr>");
                    out.println("<td align='center'><b>Responsable:  </b><input type='text' class='form-control' class='form-control' name='txt_responsable' id='Txt_responsable' value='" + obj_equipo[2] + "' /></td>");
                    out.println("<td align='center' colspan='5'><input type='submit' name='Btn_calificar' value='Calificar' /></td>");
                    out.println("</tr>");
                }
                // </editor-fold>
                out.println("</table>");
                out.println("</form>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<script>");
                out.print("$(\"#Registrar_caso\").modal(\"show\");");
                out.print("</script>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_caso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
