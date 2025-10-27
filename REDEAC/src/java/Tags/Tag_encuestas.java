package Tags;

import Controladoras.CalificacionJpaController;
import Controladoras.EquipoJpaController;
import Controladoras.UsuarioJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_encuestas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        int id_usuario = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
        int id_rol = Integer.parseInt(pageContext.getSession().getAttribute("Id_rol").toString());
        String fecha_inicial = pageContext.getSession().getAttribute("Fch_inicial").toString();
        String fecha_final = pageContext.getSession().getAttribute("Fch_final").toString();
        EquipoJpaController jpa_equipo = new EquipoJpaController();
        UsuarioJpaController jpa_usuario = new UsuarioJpaController();
        CalificacionJpaController jpa_calificacion = new CalificacionJpaController();
        String modulo = pageContext.getRequest().getAttribute("Encuesta").toString();
        List lst_encuesta = null;
        List lst_anios = null;
        List lst_meses = null;
        List lst_usuario = null;
        List lst_programacion = null;
        List lst_encuestaEq = null;
        List lst_calificar = null;
        List lst_calificacion = null;
        List lst_promedio = null;
        List lst_promedioT = null;
        List lst_maximo = null;
        List lst_minimo = null;
        int cont = 0, anio = 0, mes = 0, id_equipo = 0;
        String meses = "Enero,Febrero,Marzo,Abril,Mayo,Junio,Julio,Agosto,Septiembre,Octubre,Noviembre,Diciembre";
        String filtro = "";
        try {
            if (modulo.equals("Rect")) {
                //<editor-fold defaultstate="collapsed" desc="programar encuestas">
                lst_encuesta = jpa_equipo.consultarEquiposEncuesta();
                out.print("<h3>Encuesta Equipos</h3>");
                out.print("<form action='Encuesta?opc=2' name='form1' method='post'>");
                out.print("<input type='hidden' name='Cont' value='" + lst_encuesta.size() + "' />");
                out.print("<div style='width: 100%; height:94%; max-width: 100%; max-height:94%; overflow:auto'>");
                out.print("<table id='resultados' class='table' style='width:100%;'>");
                for (int i = 0; i < lst_encuesta.size(); i++) {
                    Object[] obj_Encuesta = (Object[]) lst_encuesta.get(i);
                    out.print("<tr>");
                    if (i == 0) {
                        out.print("<th style='background: #eee;'>");
                        out.print("<a href='#' onclick='seleccionar_todo();'><i class='far fa-check-square fa-lg' style='color:#5356ad'></i></a>&nbsp;&nbsp;&nbsp;");
                        out.print("<a href='#' onclick='deseleccionar_todo();'><i class='far fa-square fa-lg' style='color:#5356ad'></i></a>");
                        out.print("</th>");
                    }
                    if (cont != (Integer) obj_Encuesta[4]) {
                        out.print("<th colspan='" + ((i == 0) ? "4" : "5") + "'>" + obj_Encuesta[14] + "</th>");
                        cont++;
                        out.print("<th>Copias</th>");
                    } else {
                        cont = (Integer) obj_Encuesta[4];
                    }
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'><label class='control control-checkbox'><input type='checkbox' id='Cbox" + i + "' name='checkboxes[" + i + "]' value='1' onchange='mostrarC(" + i + ")'><div class='control_indicator'></div></label></td>");
                    out.print("<td align='center'>" + obj_Encuesta[1] + "</td>");
                    out.print("<td align='center'>" + obj_Encuesta[3] + "</td>");
                    out.print("<td>" + obj_Encuesta[2] + "</td>");
                    out.print("<td align='center'>" + obj_Encuesta[7] + "</td>");
                    out.print("<td align='center'><input type='number' name='txt_copia" + i + "' id='txt_cop" + i + "' style='width:70px;display:none;' min='1' max='3'></td>");
                    out.print("</tr>");
                    out.print("<input type='hidden' name='id" + i + "' value='" + obj_Encuesta[0] + "' />");
                }
                out.print("</table>");
                out.print("<div class='btn-flotante'><img class='imagenPro' src='Interfaz/Contenido/Images/send2.png' onclick=\"javascript:document.getElementById('Btn_accion').click();\" style='width: 10%;margin-left: 84%;'></div>");
                out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                out.print("<div></div>");
                out.print("</div>");
                out.print("</form>");
//</editor-fold>
            }
            if (modulo.equals("CPE")) {
                //<editor-fold defaultstate="collapsed" desc="consulta programacion">
                lst_anios = jpa_equipo.consultaAniosEncuestas();
                out.print("<h3>Programacion</h3>");
                if (lst_anios != null) {
                    out.print("<div style='width: 100%; height:94%; max-width: 100%; max-height:94%; overflow:auto'>");
                    out.print("<table class='table'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4'>Año</th>");
                    out.print("<th class='sticky4'>Mes</th>");
                    out.print("<th class='sticky4'>Tecnicos</th>");
                    out.print("<th class='sticky4'>Encuesta</th>");
                    out.print("<th class='sticky4'>Reporte</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_anios.size(); i++) {
                        Object[] obj_ano = (Object[]) lst_anios.get(i);
                        lst_meses = jpa_equipo.consultarMesesEncuestas(Integer.parseInt(obj_ano[0].toString()));
                        out.print("<tr>");
                        out.print("<td rowspan='" + lst_meses.size() + "' align='center'><b>" + obj_ano[0] + "</b></td>");
                        for (int j = 0; j < lst_meses.size(); j++) {
                            out.print("" + ((lst_meses.size() == 1) ? "" : ((j > 0) ? "<tr>" : "")) + "");
                            Object[] obj_mes = (Object[]) lst_meses.get(j);
                            String mess = meses.split(",")[((Integer) obj_mes[0] - 1)];
                            out.print("<td align='center'><b style='color:black;'>" + mess + "</b></td>");
                            lst_programacion = jpa_equipo.consultaProgramacionEncuesta(Integer.parseInt(obj_ano[0].toString()), Integer.parseInt(obj_mes[0].toString()));
                            out.print("<td>");
                            for (int k = 0; k < lst_programacion.size(); k++) {
                                Object[] obj_pro = (Object[]) lst_programacion.get(k);
                                out.print("<b style='color:black;'>" + obj_pro[5] + "</b><br />");
                            }
                            out.print("</td>");
                            out.print("<td align='center'><a href='Encuesta?opc=1&mod=CEE&anio=" + obj_ano[0] + "&mes=" + obj_mes[0] + "'><i class='far fa-eye fa-lg' style='color:#292929'></i></td>");
                            out.print("<td align='center'><a href='Encuesta?opc=1&mod=RPE&anio=" + obj_ano[0] + "&mes=" + obj_mes[0] + "&txt_bus='><i class='far fa-chart-bar fa-lg' style='color:#292929'></i></td>");
                            out.print("</tr>");
                        }
                    }
                } else {
                    out.print("<b class='title'>No se encontraron resultados</b>");
                }
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
            }
            if (modulo.equals("CEE")) {
                //<editor-fold defaultstate="collapsed" desc="consulta encuestas equipos">
                anio = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                mes = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                lst_usuario = jpa_usuario.consultarUsuarios();
                lst_programacion = jpa_equipo.consultaProgramacionEquipos(anio, mes);
                out.print("<a href='Encuesta?opc=1&mod=CPE'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Encuestas</h3>");
                out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<div><input type='text' class='form-control' style='margin:5px' class='form-control' name='txt_bus'  id='Txt_filtro' onkeyup='FiltrarLst()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("</div>");
                out.print("<div style='width: 100%; height:87%; max-width: 100%; max-height:88%; overflow:auto'>");
                out.print("<table class='table' id='resultados'>");
                out.print("<tr>");
                out.print("<th class='sticky4'>Equipo</th>");
                out.print("<th class='sticky4'>Tipo</th>");
                out.print("<th class='sticky4'>Tecnicos</th>");
                out.print("<th class='sticky4'>Ver</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_programacion.size(); i++) {
                    out.print("<tr>");
                    Object[] obj_prog = (Object[]) lst_programacion.get(i);
                    if (id_equipo != (Integer) obj_prog[3]) {
                        lst_usuario = jpa_equipo.ConsultaProgramacionIdEquipo(anio, mes, (Integer) obj_prog[3]);
                        out.print("<td align='center'>" + obj_prog[4] + "</td>");
                        out.print("<td>" + obj_prog[6] + "</td>");
                        out.print("<td>");
                        for (int j = 0; j < lst_usuario.size(); j++) {
                            Object[] obj_usa = (Object[]) lst_usuario.get(j);
                            lst_calificar = jpa_equipo.ConsultaTotalCalificacion((Integer) obj_prog[3], (Integer) obj_usa[1], (Integer) obj_usa[0]);
                            out.print("" + obj_usa[2] + "");
                            if (lst_calificar == null) {
                                cont = 0;
                            } else {
                                cont = lst_calificar.size();
                            }

                            if (cont == (Integer) obj_usa[4]) {
                                out.print("&nbsp;&nbsp;|&nbsp;&nbsp;" + cont + " de " + obj_usa[4] + "" + ((j == (lst_usuario.size() - 1)) ? "" : "<hr />") + "");
                            } else {
                                out.print("&nbsp;&nbsp;|&nbsp;&nbsp;" + cont + " de " + obj_usa[4] + "&nbsp;<b class='title'>Pendiente</b>" + ((j == (lst_usuario.size() - 1)) ? "" : "<hr />") + "");
                            }

                        }
                        out.print("</td>");
                        out.print("<td align='center'><a href='Encuesta?opc=1&mod=CEE&idE=" + obj_prog[3] + "&anio=" + anio + "&mes=" + mes + "'><i class='far fa-eye fa-lg' style='color:#292929'></i></a></td>");
                        id_equipo = Integer.parseInt(obj_prog[3].toString());
                    } else {
                        id_equipo = Integer.parseInt(obj_prog[3].toString());
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</div>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',30);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                id_equipo = 0;
                id_equipo = Integer.parseInt(pageContext.getRequest().getAttribute("id_equipo").toString());
                if (id_equipo != 0) {
                    out.print("<div class='modal fade' id='VerEnc' role='dialog' data-backdrop='static' data-keyboard='false'>");
                    out.print("<div class='modal-dialog modal-lg'>");
                    out.print("<div class='modal-content'>");
                    out.print("<div class='modal-header'>");
                    out.print("<a href='Encuesta?opc=1&mod=CEE&anio=" + anio + "&mes=" + mes + "' class='close'>&times;</a>");
                    out.print("<h4 class='modal-title'>Encuestas</h4>");
                    out.print("</div>");
                    out.print("<div class='modal-body' align='center'>");
                    //Tabs
                    lst_encuestaEq = jpa_equipo.ConsultaProgramacionIdEquipo(anio, mes, id_equipo);
                    out.print("<ul class=\"nav nav-tabs\">");
                    for (int i = 0; i < lst_encuestaEq.size(); i++) {
                        Object[] obj_encEqp = (Object[]) lst_encuestaEq.get(i);
                        out.print("<li " + ((i == 0) ? "class='active'" : "") + "><a data-toggle='tab' href='#" + obj_encEqp[2].toString().replace(" ", "_") + "'>" + obj_encEqp[2] + "</a></li>");
                    }
                    out.print("</ul>");
                    out.print("<div class='tab-content'>");
                    for (int i = 0; i < lst_encuestaEq.size(); i++) {
                        Object[] obj_encEqp = (Object[]) lst_encuestaEq.get(i);
                        out.print("<div id='" + obj_encEqp[2].toString().replace(" ", "_") + "' " + ((i == 0) ? "class='tab-pane fade in active'" : "class='tab-pane fade'") + " >");
                        lst_calificacion = jpa_calificacion.ConsultaCalificaconEncuesta(anio, mes, (Integer) obj_encEqp[3], (Integer) obj_encEqp[1]);
                        if (lst_calificacion != null) {
                            String copia = "";
                            if (lst_calificacion.size() != 1) {
                                out.print("<ul class='nav nav-tabs'>");
                                for (int k = 0; k < (Integer) obj_encEqp[4]; k++) {
                                    out.print("<li " + ((k == 0) ? "class='active'" : "") + "><a data-toggle='tab' href='#" + obj_encEqp[2].toString().replace(" ", "_") + "_Enc" + (k + 1) + "'>Encuesta " + (k + 1) + "</a></li>");
                                }
                                out.print("</ul>");
                            }
                            out.print("<div class='tab-content'>");
                            //<editor-fold defaultstate="collapsed" desc="content tabs">
                            for (int j = 0; j < (Integer) obj_encEqp[4]; j++) {
                                out.print("<div id='" + obj_encEqp[2].toString().replace(" ", "_") + "_Enc" + (j + 1) + "' " + ((j == 0) ? "class='tab-pane fade in active'" : "class='tab-pane fade'") + " >");
                                if ((j + 1) <= lst_calificacion.size()) {
                                    Object[] obj_cal = (Object[]) lst_calificacion.get(j);
                                    copia = copia + "-" + obj_cal[14];
                                    out.println("<table class='table'>");
                                    out.println("<tr>");
                                    out.println("<td align='center' colspan='4'>");
                                    out.println("<h5><b class='title'>Equipo: </b><b>" + obj_cal[2] + "</b></h5><hr/>");
                                    out.println("<h5><b class='title'>Tecnico a calificar: </b><b style='color:black;'>" + obj_cal[15] + "</h5>");
                                    out.println("</td>");
                                    out.println("<td colspan='2' align='center'>"
                                            + "<img src='Interfaz/Fotos/" + obj_cal[16] + ".jpg' width='120px' height='120px'>"
                                            + "</td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                    out.println("<th>CALIFICACIÓN</th>");
                                    out.println("<th>Muy<br />Insatisfecho</th>");
                                    out.println("<th>Insatisfecho</th>");
                                    out.println("<th>Poco<br />Satisfecho</th>");
                                    out.println("<th>Satisfecho</th>");
                                    out.println("<th>Muy<br />Satisfecho</th>");
                                    out.println("</tr>");
                                    String[] arg_preguntas = obj_cal[5].toString().split(" / ");
                                    for (int k = 0; k < arg_preguntas.length; k++) {
                                        out.println("<tr>");
                                        out.println("<td>" + arg_preguntas[k] + "</td>");
                                        for (int l = 1; l <= 5; l++) {
                                            if ((k + 1) == 1) {
                                                if (obj_cal[6].equals(l)) {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta" + obj_cal[0] + k + "' value='" + l + "' disabled='true' checked/></td>");
                                                } else {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta" + obj_cal[0] + k + "' value='" + l + "' disabled='true'/></td>");
                                                }
                                            } else if ((k + 1) == 2) {
                                                if (obj_cal[7].equals(l)) {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true' checked/></td>");
                                                } else {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true'/></td>");
                                                }
                                            } else if ((k + 1) == 3) {
                                                if (obj_cal[8].equals(l)) {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true' checked/></td>");
                                                } else {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true'/></td>");
                                                }
                                            } else if ((k + 1) == 4) {
                                                if (obj_cal[9].equals(l)) {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true' checked/></td>");
                                                } else {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true'/></td>");
                                                }
                                            } else if ((k + 1) == 5) {
                                                if (obj_cal[10].equals(l)) {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true' checked/></td>");
                                                } else {
                                                    out.println("<td align='center'><input type='radio' class='radioB' name='Rdb_pregunta_" + obj_cal[0] + k + "' value='" + l + "' disabled='true' /></td>");
                                                }
                                            }
                                        }
                                        out.println("</tr>");
                                    }
                                    out.println("<tr>");
                                    out.println("<td>7) Observaciones</td>");
                                    out.println("<td align='center' colspan='5'><textarea name='txt_observaciones' id='Txt_observaciones' style='width:400px;height:60px' readonly='true' placeholder='Ingresar observación'>" + obj_cal[11].toString().toUpperCase() + "</textarea></td>");
                                    out.println("</tr>");
                                    out.println("<tr>");
                                    out.println("<td align='center' colspan='7'><b class='title'>Responsable: </b><b>" + obj_cal[12] + "</b></td>");
                                    out.println("</tr>");
                                    out.println("</table>");
                                } else {
                                    out.println("<table class='table'>");
                                    out.println("<tr>");
                                    out.println("<td align='center'>");
                                    out.print("<b style='color:orange;'>La Encuesta no ha sido Calificada</b><hr><b>Abrir encuesta</b><br><br><a href='Caso?opc=1&mod=CE&idE=" + obj_encEqp[3] + "&idU=" + obj_encEqp[1] + "&idP=" + obj_encEqp[1] + "&cop=" + ((copia.contains("1")) ? ((copia.contains("2")) ? ((copia.contains("3")) ? "" : "3") : "2") : "1") + "' target='_blank'><span class='fa-stack fa-2x'><i class='far fa-clipboard fa-lg fa-stack-2x' style='color:#292929'></i><i class='fas fa-tasks fa-stack-1x ' style='color:#292929'></i></span></a>");
                                    out.println("</td>");
                                    out.println("</tr>");
                                    out.println("</table>");
                                }
                                out.print("</div>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                        } else {
                            out.print("<b>Encuestas no contestadas</b>");
                        }
                        out.print("</div>");
                    }
                    out.print("</div>");
                    //fin tabs
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print("$(\"#VerEnc\").modal(\"show\");");
                    out.print("</script>");
                }
                //</editor-fold>
            }
            if (modulo.equals("RPE")) {
                //<editor-fold defaultstate="collapsed" desc="reporte encuestas">
                anio = Integer.parseInt(pageContext.getRequest().getAttribute("Anio").toString());
                mes = Integer.parseInt(pageContext.getRequest().getAttribute("Mes").toString());
                filtro = pageContext.getRequest().getAttribute("filtro").toString();
                out.print("<a href='Encuesta?opc=1&mod=CPE'><i class='fa fa-arrow-left fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;&nbsp;");
                out.print("<h3>Reporte</h3>");
                if (filtro.equals("")) {
                    lst_promedio = jpa_calificacion.consultaPromedioEquipos(anio, mes);
                } else {
                    lst_promedio = jpa_calificacion.consultaPromedioEquiposFiltro(anio, mes, filtro);
                }
                lst_promedioT = jpa_calificacion.consultaPromedioTotal(anio, mes);
                lst_maximo = jpa_calificacion.consultaMaximo(anio, mes);
                lst_minimo = jpa_calificacion.consultaMinimo(anio, mes);
                out.print("<div style='display:flex;'>");
                out.print("<div>");
                out.print("<a href='#' onclick='Imprimir(1);' style='float: left; title='Imprimir / PDF'><i class='fa fa-print fa-lg' style='color:#292929'></i></a>&nbsp;&nbsp;<b>Imprimir / PDF</b>");
                out.print("</div>");
                out.print("<div style='margin-left: 25px'>");
                out.print("<a onclick=\"tableToExcel2('testTable', 'W3C Example Table','encuesta.xls')\" value=\"Export to Excel\"><i class='far fa-file-excel fa-lg' style='color:#292929'></i></a><b>Exportar a excel</b>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div style='display:flex;justify-content: space-between; align-items: center;'>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<div><input type='text' class='form-control' style='margin:5px' class='form-control' name='txt_bus'  id='Txt_filtro' onkeyup='FiltrarEncuesta()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();'></div>");
                out.print("</div>");
                if (lst_promedio != null) {
                    Object[] obj_maximo = (Object[]) lst_maximo.get(0);
                    Object[] obj_promedioT = (Object[]) lst_promedioT.get(0);
                    Object[] obj_minimo = (Object[]) lst_minimo.get(0);
                    //<editor-fold defaultstate="collapsed" desc="IMPRIMIR">
                    out.print("<div style='display:none'>");
                    out.print("<div id='testTable' style='display:none;'>");
                    out.print("<div id='Imprimir1'>");
                    out.print("<table style='widht:100%' class='table'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4'>Equipo</th>");
                    out.print("<th class='sticky4'>Disponibilidad</th>");
                    out.print("<th class='sticky4'>Satisfacción</th>");
                    out.print("<th class='sticky4'>Solucion</th>");
                    out.print("<th class='sticky4'>Claridad de la informacion</th>");
                    out.print("<th class='sticky4'>Tiempo respuesta</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_promedio.size(); i++) {
                        Object[] obj_promedio = (Object[]) lst_promedio.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_promedio[2] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[4] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[5] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[6] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[7] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[8] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<th>Promedio</th>");
                    out.print("<td align='center'>" + obj_promedioT[2] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[3] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[4] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[5] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Maximo</th>");
                    out.print("<td align='center'>" + obj_maximo[2] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[3] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[4] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[5] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Minimo</th>");
                    out.print("<td align='center'>" + obj_minimo[2] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[3] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[4] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[5] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[6] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("<div style='width: 100%; height:82%; max-width: 100%; max-height:83%; overflow:auto'>");
                    out.print("<table style='widht:100%' class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th class='sticky4'>Equipo</th>");
                    out.print("<th class='sticky4'>Disponibilidad</th>");
                    out.print("<th class='sticky4'>Satisfacción</th>");
                    out.print("<th class='sticky4'>Solucion</th>");
                    out.print("<th class='sticky4'>Claridad de la informacion</th>");
                    out.print("<th class='sticky4'>Tiempo respuesta</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_promedio.size(); i++) {
                        Object[] obj_promedio = (Object[]) lst_promedio.get(i);
                        out.print("<tr>");
                        out.print("<td align='center'>" + obj_promedio[2] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[4] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[5] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[6] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[7] + "</td>");
                        out.print("<td align='center'>" + obj_promedio[8] + "</td>");
                        out.print("</tr>");
                    }
                    out.print("<tr>");
                    out.print("<th>Promedio</th>");
                    out.print("<td align='center'>" + obj_promedioT[2] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[3] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[4] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[5] + "</td>");
                    out.print("<td align='center'>" + obj_promedioT[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Maximo</th>");
                    out.print("<td align='center'>" + obj_maximo[2] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[3] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[4] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[5] + "</td>");
                    out.print("<td align='center'>" + obj_maximo[6] + "</td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<th>Minimo</th>");
                    out.print("<td align='center'>" + obj_minimo[2] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[3] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[4] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[5] + "</td>");
                    out.print("<td align='center'>" + obj_minimo[6] + "</td>");
                    out.print("</tr>");
                    out.print("</div>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',30);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    out.print("<b>No se ha calificado ninguna encuesta</b>");
                }
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_encuestas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
