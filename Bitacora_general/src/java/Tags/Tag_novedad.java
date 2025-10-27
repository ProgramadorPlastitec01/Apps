package Tags;

import Controladoras.CargoJpaController;
import Controladoras.FormularioJpaController;
import Controladoras.MaquinaJpaController;
import Controladoras.UbicacionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_novedad extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        int CargoUsa = Integer.parseInt(sesion.getAttribute("Cargo").toString());
        MaquinaJpaController jpa_maquinas = new MaquinaJpaController();
        FormularioJpaController jpa_formulario = new FormularioJpaController();
        CargoJpaController jpa_cargo = new CargoJpaController();
        List lst_actividad = (List) pageContext.getRequest().getAttribute("consultaActividad");
        int idCargo = Integer.parseInt(sesion.getAttribute("Cargo").toString());
        Object[] obj_actividad = (Object[]) lst_actividad.get(0);
        List lst_cargo = jpa_cargo.ConsultaCargosPorIdActividad((Integer) obj_actividad[0]);
        Object[] obj_cargo = (Object[]) lst_cargo.get(0);
        List Consultaform = null;
        List NomCampo = null;
        UbicacionJpaController jpa_ubicacion = new UbicacionJpaController();
        List lst_ubicacion = jpa_ubicacion.ConsultaUbicacion();
        try {
            if (obj_actividad[8].equals(0)) {
                out.print("<div id='sidebar'>");
                if (pageContext.getRequest().getAttribute("consultaNovedadM") != null) {
                    List MoNovedad = (List) pageContext.getRequest().getAttribute("consultaNovedadM");
                    int idNovedadM = (Integer) pageContext.getRequest().getAttribute("idNM");
                    List lst_maquina = jpa_maquinas.ConsultaMaquinas();
                    Object[] obj_novedadM = (Object[]) MoNovedad.get(0);
                    // <editor-fold defaultstate="collapsed"  desc="Modificar novedad">
                    out.print("<h3>Modificar novedad ");
                    out.print("<div style='float: right;'>");
                    out.print("<a href='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "&txtbus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                    out.print("</div>");
                    out.print("</h3>");
                    Object[] obj_maquinaM = (Object[]) lst_maquina.get(0);
                    out.print("<form action='Novedad?op=3&idA=" + obj_novedadM[1] + "&idN=" + obj_novedadM[0] + "' method='post' onsubmit='ModificarN();' name='form1' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='txt_registroM' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<b>Fecha: </b><br />");
                    out.print("<input type='text' id='fecha-id' name='txtfechaM' class='required input_field' value='" + obj_novedadM[4] + "' readonly='true'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('fecha-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<b>Maquina: </b><br />");
                    out.print("<select name='slc_maquinaM' >");
                    out.print("<option style='display:none;' value='" + obj_novedadM[2] + "'>" + obj_novedadM[6] + "</option>");
                    for (int i = 0; i < lst_maquina.size(); i++) {
                        Object[] obj_maquina = (Object[]) lst_maquina.get(i);
                        if (obj_maquina[5].equals(1)) {
                            out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[4] + "-" + obj_maquina[7] + "-" + obj_maquina[6] + "</option>");
                        } else {
                        }
                    }
                    out.print("</select>");
                    out.print("<b>Novedad: </b><br />");
                    out.print("<textarea id='novedad_id' name='text_novedadM' class='input_full' rows='5'  placeholder='novedad' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_novedadM[5] + "</textarea><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('novedad_id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                    out.print("<input type='submit' id='btsubmit' value='Modificar'><br />");
                    out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                    out.print("</form>");
                    // </editor-fold>
                } else {
                    // <editor-fold defaultstate="collapsed"  desc="registro novedad">
                    List lst_maquina = (List) pageContext.getRequest().getAttribute("Maquinas");
                    out.print("<h3>Nueva novedad</h3>");
                    if (lst_maquina != null) {
                        Object[] obj_maquinaM = (Object[]) lst_maquina.get(0);
                        out.print("<form action='Novedad?op=6&idA=" + obj_actividad[0] + "' method='post' name='formUbicacion' onsubmit='checkSubmit();'>");
                        out.print("<b>Ubicacion:</b>");
                        out.print("<select name='idU' onchange='PostBackUbicacion()'>");
                        out.print("<option style='display:none;'>" + obj_maquinaM[7] + "</option>");
                        for (int i = 0; i < lst_ubicacion.size(); i++) {
                            Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                            if (obj_ubicacion[3].equals(1)) {
                                out.print("<option value='" + obj_ubicacion[0] + "'>" + obj_ubicacion[2] + "</option>");
                            } else {
                            }
                        }
                        out.print("</select>");
                        out.print("</form>");
                        
                        out.print("<form action='Novedad?op=2&idA=" + obj_actividad[0] + "' onsubmit='RegistroN();'  method='post' name='form1'>");
                        out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                        out.print("<b>Fecha: </b><br />");
                        out.print("<input type='text' id='fecha-id' name='txtfecha' class='required input_field' placeholder='Seleccionar fecha' readonly='true'><br />");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('fecha-id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<b>Maquina: </b><br />");
                        out.print("<select name='slc_maquina' >");
                        out.print("<option style='display:none;'>SELECCIONE LA MAQUINA</option>");
                        for (int i = 0; i < lst_maquina.size(); i++) {
                            Object[] obj_maquina = (Object[]) lst_maquina.get(i);
                            if (obj_maquina[5].equals(1)) {
                                out.print("<option value='" + obj_maquina[0] + "'>" + obj_maquina[4] + "</option>");
                            } else {
                            }
                        }
                        out.print("</select>");
                        out.print("<b>Novedad: </b><br />");
                        out.print("<textarea id='novedad_id' name='text_novedad' class='input_full' rows='5'  placeholder='novedad' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                        out.print("<script type='text/javascript'>");
                        out.print("var validation = new LiveValidation('novedad_id');");
                        out.print("validation.add( Validate.Presence );");
                        out.print("</script>");
                        out.print("<input type='submit' id='btsubmit' value='Registrar'><br />");
                        out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "          <div></div>\n"
                                + "        </div>");
                        out.print("</form>");
                    } else {
                        out.print("<form action='Novedad?op=6&idA=" + obj_actividad[0] + "' method='post' name='formUbicacion'>");
                        out.print("<b>Ubicacion:</b>");
                        out.print("<select name='idU' onchange='PostBackUbicacion()'>");
                        out.print("<option style='display:none;'>SELECCIONE LA UBICACION</option>");
                        for (int i = 0; i < lst_ubicacion.size(); i++) {
                            Object[] obj_ubicacion = (Object[]) lst_ubicacion.get(i);
                            if (obj_ubicacion[3].equals(1)) {
                                out.print("<option value='" + obj_ubicacion[0] + "'>" + obj_ubicacion[2] + "</option>");
                            } else {
                            }
                        }
                        out.print("</select>");
                        out.print("</form>");
                    }
                    // </editor-fold>
                }
                out.print("<div class='cleaner'></div></div>");
                out.print("<div id='content'>");
                if (pageContext.getRequest().getAttribute("consultaNovedad") != null) {
                    // <editor-fold defaultstate="collapsed"  desc="Consulta novedad">
                    String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                    List ConNovedad = (List) pageContext.getRequest().getAttribute("consultaNovedad");
                    out.print("<div style='float: right;'>");
                    out.print("<form action='Novedad?op=1&idA=" + obj_actividad[0] + "&idN=" + 0 + "' method='post' >");
                    out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                    out.print("</form>");
                    out.print("</div>");
                    if (CargoUsa == 8) {
                            out.print("<a href='Actividad?op=1&idC=" + CargoUsa + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                    } else {
                            out.print("<a href='Actividad?op=1&idC=" + idCargo + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                    }
                    out.print("<h3>Novedades registradas</h3>");
                    if (ConNovedad == null || ConNovedad.isEmpty()) {
                        out.print("<h3>No se encuentran novedades registradas<h3>");
                    } else {
                        out.print("<div id='NavPosicion'></div>");
                        out.print("<table class='table' id='resultados' style='width: 100%;'>");
                        out.print("<tr>");
                        out.print("<th>Fecha</th>");
                        out.print("<th>Maquina</th>");
                        out.print("<th>Novedad</th>");
                        out.print("<th>Modificar</th>");
                        out.print("</tr>");
                        for (int i = 0; i < ConNovedad.size(); i++) {
                            Object[] obj_novedad = (Object[]) ConNovedad.get(i);
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_novedad[4] + "</td>");
                            out.print("<td align='center'>" + obj_novedad[6] + "</td>");
                            out.print("<td align='center'>" + obj_novedad[5] + "</td>");
                            out.print("<td align='center' ><a href='Novedad?op=1&idA=" + obj_novedad[1] + "&idN=" + obj_novedad[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    // </editor-fold>
                }
                out.print("<div class='cleaner'></div></div>");

            } else {
                // <editor-fold defaultstate="collapsed"  desc="registro virtualizado">
                List ConNovedad = (List) pageContext.getRequest().getAttribute("consultaNovedad");
                if(rol.contains("JEFE")) {                         
                    out.print("<a href='Actividad?op=1&idC=" + 0 + "&idA=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Atras' /></a>");
                }else{
                    out.print("<a href='Actividad?op=1&idC=" + idCargo + "&idA=" + 0 + "&idU=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Atras' /></a>");
                }
                out.print("<div style='display:inline; float: right;'>");
                out.print("<a onclick='Imprimir();'><img src=\"Interfaz/Contenido/Iconos/Printer.png\" style=\"width: 30px; height: 30.5px\" alt=\"\" title='Imprimir' />");
                out.print("</a> Imprimir o PDF");
                out.print("</div>");
                out.print("<div id='Imprimir'>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<td colspan='6' style='background-color:#979595;' align='center'><b style='color:white;'>COPIA NO CONTROLADA</b></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' rowspan='2' style='width: 30%;'><img src=\"Interfaz/Contenido/images/Logo.png\" style=\"width: 160px; height: 70px\" alt=\"\"/></td>");
                out.print("<td align='center' rowspan='2'style='width: 30%;'><h3 style='color: black;'>" + obj_cargo[2] + "<h3></td>");
                out.print("<td align='center' style='width: 40%;' colspan='2'><b>CODIGO:</b> " + obj_cargo[4] + "</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td align='center' style='width: 40%;' colspan='2'><b>Versión: </b>" + obj_cargo[5] + "<br /></td>");
                out.print("<tr>");
                out.print("<th colspan='5'>ACTIVIDADES</th>");
                out.print("<tr>");
                out.print("<td style='width: 30%;'><b>Fecha: </b>" + obj_actividad[5] + "</td>");
                out.print("<td style='width: 30%;'><b>Consecutivo: </b>" + obj_actividad[2] + "</td>");
                out.print("<td><b>Turno: </b>" + obj_actividad[7] + "</td>");
                out.print("<td><b>Usuario: </b>" + obj_actividad[3] + "</td>");
                out.print("</tr>");
                out.print("</tr>");
                NomCampo = jpa_formulario.ConsultaNombreCamposPorIdArea((Integer) obj_actividad[22], (Integer) obj_actividad[0]);
                for (int c = 0; c < NomCampo.size(); c++) {
                    Object[] obj_NomCam = (Object[]) NomCampo.get(c);
                    String[] arg_campos = obj_NomCam[2].toString().split("-");
                    for (int i = 0; i < lst_actividad.size(); i++) {
                        Object[] obj_actividades = (Object[]) lst_actividad.get(i);
                        if (obj_actividades[21].equals(2)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 2 campos">
                            out.print("<tr>");
                            out.print("<td colspan='2'><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td style='width:50%;' colspan='3'><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(3)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 3 campos">
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td style='width:50'><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("<td style='width:50%;' colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(4)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 4 campos">
                            out.print("<tr>");
                            out.print("<td colspan='2'><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td style='width:50' colspan='3'><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='width:50%;' colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("<td style='width:50%;' colspan='3'><b>" + arg_campos[3] + ": </b>" + obj_actividades[15] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(5)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 5 campos">
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td style='width:50' colspan='2'><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("<td style='width:50' colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividades[15] + "</td>");
                            out.print("<td style='width:50%;' colspan='3'><b>" + arg_campos[4] + ": </b>" + obj_actividades[16] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(6)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 6 campos">
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("<td colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td ><b>" + arg_campos[3] + ": </b>" + obj_actividades[15] + "</td>");
                            out.print("<td ><b>" + arg_campos[4] + ": </b>" + obj_actividades[16] + "</td>");
                            out.print("<td colspan='2'><b>" + arg_campos[5] + ": </b>" + obj_actividades[17] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(7)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 7 campos">
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("<td ><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("<td ><b>" + arg_campos[3] + ": </b>" + obj_actividades[15] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td ><b>" + arg_campos[4] + ": </b>" + obj_actividades[16] + "</td>");
                            out.print("<td ><b>" + arg_campos[5] + ": </b>" + obj_actividades[17] + "</td>");
                            out.print("<td colspan='2'><b>" + arg_campos[6] + ": </b>" + obj_actividades[18] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(8)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 8 campos">
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("<td><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividades[15] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[4] + ": </b>" + obj_actividades[16] + "</td>");
                            out.print("<td><b>" + arg_campos[5] + ": </b>" + obj_actividades[17] + "</td>");
                            out.print("<td><b>" + arg_campos[6] + ": </b>" + obj_actividades[18] + "</td>");
                            out.print("<td><b>" + arg_campos[7] + ": </b>" + obj_actividades[19] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        } else if (obj_actividades[21].equals(9)) {
                            // <editor-fold defaultstate="collapsed"  desc="Consulta 9 campos">
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[0] + ": </b>" + obj_actividades[12] + "</td>");
                            out.print("<td><b>" + arg_campos[1] + ": </b>" + obj_actividades[13] + "</td>");
                            out.print("<td colspan='2'><b>" + arg_campos[2] + ": </b>" + obj_actividades[14] + "</td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[3] + ": </b>" + obj_actividades[15] + "</td>");
                            out.print("<td><b>" + arg_campos[4] + ": </b>" + obj_actividades[16] + "</td>");
                            out.print("<td colspan='2'><b>" + arg_campos[5] + ": </b>" + obj_actividades[17] + "</td>");
                            out.print("<tr>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>" + arg_campos[6] + ": </b>" + obj_actividades[18] + "</td>");
                            out.print("<td><b>" + arg_campos[7] + ": </b>" + obj_actividades[19] + "</td>");
                            out.print("<td colspan='2'><b>" + arg_campos[8] + ": </b>" + obj_actividades[20] + "</td>");
                            out.print("</tr>");
                            // </editor-fold>
                        }
                    }
                }
                out.print("<tr>");
                if (obj_actividad[11].equals(0)) {
                } else {
                    out.print("<th colspan='5' align='center'>NOVEDADES DE MAQUINA</th>");
                    out.print("</tr>");
                    for (int i = 0; i < ConNovedad.size(); i++) {
                        Object[] obj_novedad = (Object[]) ConNovedad.get(i);
                        out.print("<tr>");
                        out.print("<td><b>Maquina: </b>" + obj_novedad[6] + "</td>");
                        out.print("<td colspan='3'><b>Novedad: </b>" + obj_novedad[5] + "</td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("</div>");
                // </editor-fold>
            }


        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
