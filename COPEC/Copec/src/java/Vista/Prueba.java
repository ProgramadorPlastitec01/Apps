package Vista;

import Controlador.PreguntaJpaController;
import Controlador.PruebaJpaController;
import Controlador.TipoProcesoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Prueba extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        PruebaJpaController jpa_prueba = new PruebaJpaController();
        TipoProcesoJpaController jpa_tipoP = new TipoProcesoJpaController();
        PreguntaJpaController jpa_pregunta = new PreguntaJpaController();
        try {
            int id_prueba = Integer.parseInt(pageContext.getRequest().getAttribute("id_prueba").toString());
            int id_pregunta = Integer.parseInt(pageContext.getRequest().getAttribute("id_pregunta").toString());
            int tipoC = Integer.parseInt(pageContext.getRequest().getAttribute("tipoC").toString());
            String filtro = pageContext.getRequest().getAttribute("filtro").toString();
            List lst_pruebas = (List) pageContext.getRequest().getAttribute("lst_pruebas");
            List lst_preguntas = (List) pageContext.getRequest().getAttribute("lst_preguntas");
            int id_area = Integer.parseInt(sesion.getAttribute("l_id_area").toString());
            List lst_tipoP = jpa_tipoP.ConsultaTiposProceso(id_area);
            List lst_prueba = null;
            List lst_pregunta = null;
            if (tipoC == 1) {
                //<editor-fold defaultstate="collapsed" desc="consulta en pruebas">
                out.print("<div id='sidebar'>");
                if (id_prueba == 0) {
                    //<editor-fold defaultstate="collapsed" desc="registrar prueba">
                    out.print("<h3>Nueva Prueba</h3>");
                    out.print("<form method='post' action='Prueba?opc=2'>");
                    out.print("<b>Nombre: </b>");
                    out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('nombre-id', {onlyOnSubmit: true });");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Tiempo limite: </b>");
                    out.print("<input type='number' name='txt_tiempoLmt' id='tiempoLmt-id' min='0' value='0' placeholder='Tiempo limite' required />");
                    out.print("<b>Promedio de aceptacion: </b>");
                    out.print("<input type='number' name='txt_promedio' id='promedio-id' min='0' max='100' value='0' placeholder='promedio' required/>");
                    out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                    out.print("</form>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="modificar prueba">
                    lst_prueba = jpa_prueba.ConsultapruebaId(id_prueba);
                    Object[] obj_prueba = (Object[]) lst_prueba.get(0);
                    out.print("<h3>Modificar calificación</h3>");
                    out.print("<form method='post' action='Prueba?opc=3'>");
                    out.print("<input type='hidden' name='idP' value='" + id_prueba + "' />");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "' />");
                    out.print("<b>Nombre: </b>");
                    out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' value='" + obj_prueba[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('nombre-id', {onlyOnSubmit: true });");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Tiempo limite: </b>");
                    out.print("<input type='number' name='txt_tiempoLmt' id='tiempoLmt-id' min='0' value='" + obj_prueba[3] + "' placeholder='Tiempo limite' required />");
                    out.print("<b>Promedio: </b>");
                    out.print("<input type='number' name='txt_promedio' id='promedio-id' min='0' value='" + obj_prueba[4] + "' placeholder='promedio' required/>");
                    out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                    out.print("</form>");
                    //</editor-fold>
                }
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                out.print("<h3>Consulta</h3>");
                if (!filtro.equals("")) {
                    out.print("<a href='Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 1 + "&txt_bus='><img src='Interfaz/Template/images/Volver.png' alt='Logo' width='22' height='22.5' /></a>");
                }
                out.print("<div style='float:right;'>");
                out.print("<form method='post' action='Prueba?opc=1&idP=" + 0 + "&idPr=" + 0 + "&TpC=" + 1 + "'>");
                out.print("<input  type='text' name='txt_bus' placeholder='Buscar' /><br/>");
                out.print("</form>");
                out.print("</div>");
                out.print("<input type='radio' name='tipoC' id='tipoC-id' onclick='Consulta(0,2,\"\");'> <i>Preguntas</i>");
                out.print("<input type='radio' name='tipoC' id='tipoC-id' onclick='Consulta(0,1,\"\");' checked> <i>Pruebas</i>");
                out.print("<br />");
                out.print("<br />");
                if (!lst_pruebas.isEmpty()) {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th style='width:50%'>Nombre</th>");
                    out.print("<th style='width:13%'>Tiempo limite</th>");
                    out.print("<th style='width:13%'>Promedio</th>");
                    out.print("<th>Preguntas</th>");
                    out.print("<th colspan='2'>Modificar / Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_pruebas.size(); i++) {
                        Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_pruebas[1] + "</td>");
                        out.print("<td align='center'>" + obj_pruebas[3] + "<b> minuto(s)</b></td>");
                        out.print("<td align='center'>" + obj_pruebas[4] + "</td>");
                        out.print("<td align='center'><a href='Pregunta?opc=1&idP=" + obj_pruebas[0] + "&idPr=" + 0 + "&txt_bus='><img src='Interfaz/Template/images/Copy.png' width='22' height='22'></a></td>");
//                        out.print("<td align='center'><a href='Prueba?opc=1&idP=" + obj_pruebas[4] + "&txt_bus=" + filtro + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a></td>");
                        out.print("<td align='center'><a href='Prueba?opc=1&idP=" + obj_pruebas[0] + "&idPr=" + 0 + "&TpC=" + 1 + "&txt_bus=" + filtro + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22'></a></td>");
                        if ((Integer) obj_pruebas[6] == 0) {
                            out.print("<td align='center'><a href='Prueba?opc=4&idP=" + obj_pruebas[0] + "&txt_bus=" + filtro + "&est=1'><img src='Interfaz/Template/images/Check.png' width='22' height='22'></a></td>");
                        } else {
                            out.print("<td align='center'><a href='Prueba?opc=4&idP=" + obj_pruebas[0] + "&txt_bus=" + filtro + "&est=0'><img src='Interfaz/Template/images/Delete.png' width='22' height='22'></a></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',5);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");

                } else {
                    out.print("<h3>No se encontraron resultados</h3>");
                }
                out.print("<div class='cleaner'></div></div>");
//</editor-fold>
            } else if (tipoC == 2) {
                //<editor-fold defaultstate="collapsed" desc="consulta en preguntas">
                lst_prueba = (List) pageContext.getRequest().getAttribute("lst_prueba");
                out.print("<div id='sidebar'>");
                if (id_pregunta == 0) {
                    //<editor-fold defaultstate="collapsed" desc="registrar preguntas">
                    out.print("<h3>Nueva Pregunta</h3>");
                    out.print("<form method='post' action='Pregunta?opc=2'>");
                    out.print("<b>Proceso: </b><br />");
                    out.print("<select name='slc_proceso' id='proceso-id'>");
                    out.print("<option value='' style='display:none;'>Seleccione un proceso</option>");
                    for (int i = 0; i < lst_tipoP.size(); i++) {
                        Object[] obj_tipoP = (Object[]) lst_tipoP.get(i);
                        if ((Integer) obj_tipoP[2] == 0) {
                            out.print("<option value='" + obj_tipoP[0] + "'>" + obj_tipoP[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('proceso-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script><br /><br />");
                    out.print("<b>Pregunta: </b><br />");
                    out.print("<textarea name='txt_pregunta' id='pregunta-id' style='width:90%;' placeholder='pregunta' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('pregunta-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script><br />");
                    out.print("<b>Respuesta: </b><br />");
                    out.print("<textarea name='txt_ResCorrecta' id='ResCorrecta-id' style='width:90%;' placeholder='Respuesta Correcta' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('ResCorrecta-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Opciones: </b><a href='#' style='float:right' onclick='AñadirP()'><img src='Interfaz/Template/images/Plus.png' width='18' height='18' title='Añadir Opción'></a>");
                    out.print("<br /><br />");
                    out.print("<textarea name='txt_opcion0' id='opcion0-id' style='width:90%;' placeholder='Opcion 1' onchange='javascript:this.value=this.value.toUpperCase();' required></textarea>");
                    out.print("<div id='opciones'>");
                    out.print("</div>");
                    out.print("<input type='hidden' name='cont' id='cont' value='1'>");
                    out.print("<input type='hidden' name='idP' value='" + id_prueba + "'>");
                    out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                    out.print("</form>");
                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="modificar preguntas">
                    lst_pregunta = jpa_pregunta.ConsultaPreguntaId(id_pregunta);
                    Object[] obj_pregunta = (Object[]) lst_pregunta.get(0);
                    String[] opciones = obj_pregunta[5].toString().split("//");
                    int cont = 0;
                    out.print("<h3>Modificar Pregunta</h3>");
                    out.print("<form method='post' action='Pregunta?opc=3'>");
                    out.print("<b>Proceso: </b><br />");
                    out.print("<select name='slc_proceso' id='proceso-id'>");
                    out.print("<option value='" + obj_pregunta[1] + "' style='display:none;'>" + obj_pregunta[2] + "</option>");
                    for (int i = 0; i < lst_tipoP.size(); i++) {
                        Object[] obj_tipoP = (Object[]) lst_tipoP.get(i);
                        if ((Integer) obj_tipoP[2] == 0) {
                            out.print("<option value='" + obj_tipoP[0] + "'>" + obj_tipoP[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('proceso-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script><br /><br />");
                    out.print("<b>Pregunta: </b><br />");
                    out.print("<textarea name='txt_pregunta' id='pregunta-id' style='width:90%;' placeholder='pregunta' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_pregunta[3] + "</textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('pregunta-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script><br />");
                    out.print("<b>Respuesta: </b><br />");
                    out.print("<textarea name='txt_ResCorrecta' id='ResCorrecta-id' style='width:90%;' placeholder='Respuesta Correcta' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_pregunta[4] + "</textarea>");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('ResCorrecta-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script><br />");
                    out.print("<b>Opciones: </b><a href='#' style='float:right' onclick='AñadirP()'><img src='Interfaz/Template/images/Plus.png' width='18' height='18' title='Añadir Opción'></a>");
                    out.print("<br /><br />");
                    out.print("<textarea name='txt_opcion0' id='opcion0-id' style='width:90%;' placeholder='Opcion 1' onchange='javascript:this.value=this.value.toUpperCase();' required>" + opciones[0] + "</textarea>");
                    out.print("<div id='opciones'>");
                    for (int i = 1; i < opciones.length; i++) {
                        out.print("<textarea name='txt_opcion" + i + "' id='opcion" + i + "-id' style='width:85%;max-width;' placeholder='Opcion " + i + "' onchange='javascript:this.value=this.value.toUpperCase();' required>" + opciones[i] + "</textarea>");
                        out.print("<a id='eliminar" + i + "' style='margin-top: 15px;float: right;' onclick='EliminarP(" + i + ")'><img src='Interfaz/Template/images/Cancel-min.png' width='15' height='15'></a>");
                        cont++;
                    }
                    out.print("</div>");
                    out.print("<input type='hidden' name='cont' id='cont' value='" + (cont + 1) + "'>");
                    out.print("<input type='hidden' name='idPr' value='" + id_pregunta + "'>");
                    out.print("<input type='hidden' name='idP' value='" + id_prueba + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    out.print("<input type='submit' id='btsubmit' name='' value='Registrar'>");
                    out.print("</form>");
                    //</editor-fold>
                }
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                //<editor-fold defaultstate="collapsed" desc="consulta preguntas">
                out.print("<h3>Consulta</h3>");
                out.print("<input type='radio' name='tipoC' id='tipoC-id' onclick='Consulta(0,2,\"\");' checked> <i>Preguntas</i>");
                out.print("<input type='radio' name='tipoC' id='tipoC-id' onclick='Consulta(0,1,\"\");'> <i>Pruebas</i>");
                out.print("<div style='float:right;'>");
                out.print("<form method='post' action='Prueba?opc=1&idP=" + id_prueba + "&idPr=" + 0 + "&TpC=" + 2 + "'>");
                out.print("<input  type='text' name='txt_bus' placeholder='Buscar' /><br/>");
                out.print("</form>");
                out.print("</div>");
                out.print("<br />");
                out.print("<br />");
                out.print("<select name='slc_proceso' id='proceso-id' onchange='Consulta(this.value,2,\"\");'>");
                for (int i = 0; i < lst_pruebas.size(); i++) {
                    Object[] obj_pruebas = (Object[]) lst_pruebas.get(i);
                    if ((Integer) obj_pruebas[6] == 0) {
                        if (id_prueba != 0) {
                            if (id_prueba == (Integer) obj_pruebas[0]) {
                                out.print("<option value='" + obj_pruebas[0] + "' style='display:none;' selected>" + obj_pruebas[1] + "</option>");
                            }
                            out.print("<option value='" + obj_pruebas[0] + "'>" + obj_pruebas[1] + "</option>");
                        } else {
                            out.print("<option value='' style='display:none;'>Seleccione una prueba</option>");
                            out.print("<option value='" + obj_pruebas[0] + "'>" + obj_pruebas[1] + "</option>");
                        }
                    }
                }
                out.print("</select>");
                out.print("<br />");
                out.print("<br />");
                out.print("<div id='NavPosicion'></div>");
                if (lst_prueba == null || lst_prueba.isEmpty()) {
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th align='center' colspan='6' style='padding-top: 3px;padding-bottom: 3px;'>");
                    out.print("<select name='slc_procesoF' id='procesoF-id' onchange='Consulta(0,2,this.value);'>");
                    out.print("<option value='' style='display:none;'>Seleccione un proceso</option>");
                    out.print("<option value=''>Todos</option>");
                    for (int i = 0; i < lst_tipoP.size(); i++) {
                        Object[] obj_tipoP = (Object[]) lst_tipoP.get(i);
                        if ((Integer) obj_tipoP[2] == 0) {
                            out.print("<option value='" + obj_tipoP[1] + "'>" + obj_tipoP[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'>#</td>");
                    out.print("<td align='center'><b>Pregunta</b></td>");
                    out.print("<td align='center'><b>Respuesta</b></td>");
                    out.print("<td align='center'><b>Opciones</b></td>");
                    out.print("<td align='center'><b>Proceso</b></td>");
                    out.print("<td align='center'><b>Modificar</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_preguntas.size(); i++) {
                        Object[] obj_preguntas = (Object[]) lst_preguntas.get(i);
                        String[] opciones = obj_preguntas[5].toString().split("//");
                        out.print("<tr>");
                        out.print("<td align='center'><input type='checkbox' name='check" + i + "' id='check" + i + "' disabled></td>");
                        out.print("<td>" + obj_preguntas[3] + "</td>");
                        out.print("<td>" + obj_preguntas[4] + "</td>");
                        out.print("<td valign='top'>");
                        out.print("<ul style='margin:0px;'>");
                        for (int j = 0; j < opciones.length; j++) {
                            out.print("<li>" + opciones[j] + "</li>");
                        }
                        out.print("<ul>");
                        out.print("</td>");
                        out.print("<td align='center'>" + obj_preguntas[2] + "</td>");
                        out.print("<td align='center'><a href='Prueba?opc=1&idP=" + id_prueba + "&idPr=" + obj_preguntas[0] + "&TpC=" + 2 + "&txt_bus=" + filtro + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22' title='Modificar'></a></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',5);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                } else {
                    Object[] obj_prueba = (Object[]) lst_prueba.get(0);
                    out.print("<form method='post' action='Prueba?opc=5' name='formPrg'>");
                    out.print("<input type='hidden' name='idP' value='" + id_prueba + "'>");
                    out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                    if (obj_prueba[5] == null) {
                        out.print("<input type='hidden' id='idPrs' name='idPrs' value=''>");
                    } else {
                        out.print("<input type='hidden' id='idPrs' name='idPrs' value='" + obj_prueba[5] + "'>");
                    }
                    out.print("</form>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th align='center' colspan='6' style='padding-top: 3px;padding-bottom: 3px;'>");
                    out.print("<select name='slc_procesoF' id='procesoF-id' onchange='Consulta(" + id_prueba + ",2,this.value);'>");
                    out.print("<option value='' style='display:none;'>Seleccione un proceso</option>");
                    out.print("<option value=''>Todos</option>");
                    for (int i = 0; i < lst_tipoP.size(); i++) {
                        Object[] obj_tipoP = (Object[]) lst_tipoP.get(i);
                        if ((Integer) obj_tipoP[2] == 0) {
                            out.print("<option value='" + obj_tipoP[1] + "'>" + obj_tipoP[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td align='center'>#</td>");
                    out.print("<td align='center'><b>Pregunta</b></td>");
                    out.print("<td align='center'><b>Respuesta</b></td>");
                    out.print("<td align='center'><b>Opciones</b></td>");
                    out.print("<td align='center'><b>Proceso</b></td>");
                    out.print("<td align='center'><b>Modificar</b></td>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_preguntas.size(); i++) {
                        Object[] obj_preguntas = (Object[]) lst_preguntas.get(i);
                        String[] opciones = obj_preguntas[5].toString().split("//");
                        out.print("<tr>");
                        if (obj_prueba[5] != null) {
                            if (obj_prueba[5].toString().contains("[" + obj_preguntas[0] + "]")) {
                                out.print("<td align='center'><input type='checkbox' name='check" + i + "' id='check" + i + "' onclick='RegistroPrg(" + obj_preguntas[0] + ");' checked></td>");
                            } else {
                                out.print("<td align='center'><input type='checkbox' name='check" + i + "' id='check" + i + "' onclick='RegistroPrg(" + obj_preguntas[0] + ");'></td>");
                            }
                        } else {
                            out.print("<td align='center'><input type='checkbox' name='check" + i + "' id='check" + i + "' onclick='RegistroPrg(" + obj_preguntas[0] + ");'></td>");
                        }
                        out.print("<td>" + obj_preguntas[3] + "</td>");
                        out.print("<td>" + obj_preguntas[4] + "</td>");
                        out.print("<td valign='top'>");
                        out.print("<ul style='margin:0px;'>");
                        for (int j = 0; j < opciones.length; j++) {
                            out.print("<li>" + opciones[j] + "</li>");
                        }
                        out.print("<ul>");
                        out.print("</td>");
                        out.print("<td align='center'>" + obj_preguntas[2] + "</td>");
                        out.print("<td align='center'><a href='Prueba?opc=1&idP=" + id_prueba + "&idPr=" + obj_preguntas[0] + "&TpC=" + 2 + "&txt_bus=" + filtro + "'><img src='Interfaz/Template/images/Edit.png' width='22' height='22' title='Modificar'></a></td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados',5);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
                //</editor-fold>
                out.print("<div class='cleaner'></div></div>");
//</editor-fold>
            } else {
                out.print("<div id='sidebar'>");
                out.print("<div align='center'><img src='Interfaz/Template/images/Alert.png' alt='Logo' width='55' height='55.5' /><br /><b>No se permite <br />el registro</b></div>");
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                out.print("<h3>Consulta</h3>");
                out.print("<input type='radio' name='tipoC' id='tipoC-id' onclick='Consulta(0,2,\"\");'> <i>Preguntas</i>");
                out.print("<input type='radio' name='tipoC' id='tipoC-id' onclick='Consulta(0,1,\"\");'> <i>Pruebas</i>");
                out.print("<div class='cleaner'></div></div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
