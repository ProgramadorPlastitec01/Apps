package Tags;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_formulario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        try {
            // <editor-fold defaultstate="collapsed"  desc="script mostrar ventana">
            out.print("<script language='Javascript'>"
                    + "function mostrarV() {"
                    + "var panel, mostrarr ;var pagina =''; panel = document.getElementById('formProceso');"
                    + "if(panel.style.visibility == 'hidden') {"
                    + "panel.style.visibility = 'visible';"
                    + "mostrarr = document.getElementById('mostrar').childNodes[i];"
                    + "}else {"
                    + "panel.style.visibility = 'hidden';"
                    + "mostrarr = document.getElementById('mostrar').childNodes[0];"
                    + "}}</script>");
            // </editor-fold>
            String rol = sesion.getAttribute("Rol").toString();
            String nombre = sesion.getAttribute("Nombre").toString();
            int IdCargo = (Integer) pageContext.getRequest().getAttribute("id_Cargo");
            List PoscionC = (List) pageContext.getRequest().getAttribute("Posicion");
            List Consultaform = (List) pageContext.getRequest().getAttribute("ConsultaForm");
            Object[] obj_pos = (Object[]) PoscionC.get(0);
            int cont = 0;
            String HTMLeditor = "";
            out.print("<div id='sidebar'>");
            // <editor-fold defaultstate="collapsed"  desc="modificar campos  formulario">
            if (pageContext.getRequest().getAttribute("consultaformularioM") != null) {
                List ConCampoM = (List) pageContext.getRequest().getAttribute("consultaformularioM");
                Object[] obj_campoM = (Object[]) ConCampoM.get(0);
                // <editor-fold defaultstate="collapsed"  desc="script nombre campo">
                out.print("<script   type = 'text/javascript'>");
                out.print("$(document).ready(function(){");
                out.print("$('#nombreC-idM').keyup(function(){");
                out.print("var texto_escrito = $(this).val();");
                out.print("$('#un_div').html(texto_escrito);})})");
                out.print("</script>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed"  desc="script seleccionar tipo">
                out.print("<script type='text/javascript'>");
                out.print("$(document).ready(function(){");
                out.print("$('#tipoM-id').change(function(){");
                out.print("var texto_seleccionado = $('#tipoM-id').val();");
                out.print("if (texto_seleccionado == \"Campo hora\" ){");
                out.print("$('#dos_div').html(\"<input type='time' name='hora'>\");");
                out.print("}else if (texto_seleccionado == \"Campo fecha\" ){");
                out.print("$('#dos_div').html(\"<input id='dtp_inicio' type='text' name='fecha' class='required input_field' placeholder='Seleccionar fecha'>\");");
                out.print("}else if (texto_seleccionado == \"Campo texto\" ){");
                out.print("$('#dos_div').html(\"<input type='text' name='texto' placeholder='texto' onchange='javascript:this.value=this.value.toUpperCase();'>\");");
                out.print("}else if (texto_seleccionado == \"Campo detallado\" ){");
                out.print("$('#dos_div').html(\"<textarea id='text_area' name='text_area' class='input_full' rows='5'  placeholder='Detallado' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>\");");
                out.print("}else if (texto_seleccionado == \"Campo editorTexto\" ){");
                out.print("$('#dos_div').html(\"<textarea id='descripcion-id' name='txt_descripcion' class='input_full' rows='5'  placeholder='Detallado' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>\");");
                out.print("}else if (texto_seleccionado == \"Campo archivo\" ){");
                out.print("$('#dos_div').html(\"");
                out.print("<div class='fileUpload btn btn-primary'>");
                out.print("<input type='file' class='upload' id='uploadBtn' name='archivo' >");
                out.print("<span>Cargar</span>");
                out.print("</div>");
                out.print("<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled'>\");");
                out.print("}else{");
                out.print("$('#dos_div').html(texto_seleccionado);}");
                out.print("})})");
                out.print("</script>");
                // </editor-fold>
                out.print("<h3>Modificar  campo</h3>");
                out.print("<form method='post' name='formareaM' action='Formulario?op=5&idC=" + obj_campoM[1] + "&idF=" + obj_campoM[0] + "' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<b>Nombre del campo: </b><br />");
                out.print("<input type='text' name='txt_nombreC' id='nombreC-idM' placeholder='Nombre Campo' value='" + obj_campoM[4] + "'><br />");
                out.print("<b>Tipo :</b><br />");
                out.print("<select name='txt_tipoM' id='tipoM-id' onchange='modificarC(this)'>");
                out.print("<option style='display:none;'>" + obj_campoM[5] + "</option>");
                out.print("<option>Campo hora</option>");
                out.print("<option>Campo fecha</option>");
                out.print("<option>Campo texto</option>");
                out.print("<option>Campo detallado</option>");
                out.print("<option>Campo seleccion</option>");
                out.print("<option>Campo lista</option>");
                out.print("<option>Campo archivo</option>");
                out.print("<option>Campo editorTexto</option>");
                out.print("</select><br /><br />");
                if (obj_campoM[5].equals("Campo lista")) {
                    out.print("<div id='datos-id' style='display:block;'>");
                    out.print("<b>Datos:</b><br />");
                    out.print("<input type='text' name='txt_datos' id='datos-idd' placeholder='Datos' style='width: 150px;' value='" + obj_campoM[6] + "' >&nbsp;&nbsp;<a href='#' onClick='Agregar()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a><br />");
                    out.print("</div>");
                    out.print("<div id='datosR-id' style='display:none;'>");
                    out.print("<b>Datos:</b><br />");
                    out.print("<input type='text' name='txt_datosR' id='datos-iddR' value='sd' placeholder='Datos' style='width: 150px;'>&nbsp;&nbsp;<a href='#' onClick='AgregarR()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a><br />");
                    out.print("</div>");
                } else {
                    if (obj_campoM[5].equals("Campo seleccion")) {
                        out.print("<div id='datosR-id' style='display:block;'>");
                        out.print("<b>Datos:</b><br />");
                        out.print("<input type='text' name='txt_datosR' id='datos-iddR' placeholder='Datos' style='width: 150px;' value='" + obj_campoM[6] + "'>&nbsp;&nbsp;<a href='#' onClick='AgregarR()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a><br />");
                        out.print("</div>");
                        out.print("<div id='datos-id' style='display:none;'>");
                        out.print("<b>Datos:</b><br />");
                        out.print("<input type='text' name='txt_datos' id='datos-idd' value='ds' placeholder='Datos' style='width: 150px;' >&nbsp;&nbsp;<a href='#' onClick='Agregar()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a><br />");
                        out.print("</div>");
                    } else {
                        out.print("<div id='datos-id' style='display:none;'>");
                        out.print("<input type='text' name='txt_datos'  id='datos-idd' value='" + obj_campoM[6] + "'  placeholder='Datos' style='width: 150px;'>&nbsp;&nbsp;<a href='#' onClick='Agregar()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a>");
                        out.print("</div>");
                        out.print("<div id='datosR-id' style='display:none;'>");
                        out.print("<input type='text' name='txt_datosR' id='datos-iddR'   value='" + obj_campoM[6] + "' placeholder='Datos' style='width: 150px;'>&nbsp;&nbsp;<a href='#' onClick='AgregarR()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a>");
                        out.print("</div>");
                    }
                }
                out.print("<input type='submit' value='Modificar'><br />");
                out.print("</form>");
                // </editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="registro del formulario">
                for (int i = 0; i < Consultaform.size(); i++) {
                    Object[] obj_form = (Object[]) Consultaform.get(i);
                    if (obj_form[5].equals("Campo editorTexto") || obj_form[5].equals("Campo archivo")) {
                        cont++;
                    }
                }
                // <editor-fold defaultstate="collapsed"  desc="script nombre campo">
                out.print("<script   type = 'text/javascript'>");
                out.print("$(document).ready(function(){");
                out.print("$('#nombreC-id').keyup(function(){");
                out.print("var texto_escrito = $(this).val();");
                out.print("$('#un_div').html(texto_escrito);})})");
                out.print("</script>");
                // </editor-fold>
                // <editor-fold defaultstate="collapsed"  desc="script seleccionar tipo">
                out.print("<script type='text/javascript'>");
                out.print("$(document).ready(function(){");
                out.print("$('#tipo-id').change(function(){");
                out.print("var texto_seleccionado = $('#tipo-id').val();");
                out.print("if (texto_seleccionado == \"Campo hora\" ){");
                out.print("$('#dos_div').html(\"<input type='time' name='hora'>\");");
                out.print("}else if (texto_seleccionado == \"Campo fecha\" ){");
                out.print("$('#dos_div').html(\"<input id='dtp_inicio' type='text' name='fecha' class='required input_field' placeholder='Seleccionar fecha'>\");");
                out.print("}else if (texto_seleccionado == \"Campo texto\" ){");
                out.print("$('#dos_div').html(\"<input type='text' name='texto' placeholder='texto' onchange='javascript:this.value=this.value.toUpperCase();'>\");");
                out.print("}else if (texto_seleccionado == \"Campo detallado\" ){");
                out.print("$('#dos_div').html(\"<textarea id='text_area' name='text_area' class='input_full' rows='5'  placeholder='Detallado' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>\");");
                out.print("}else if (texto_seleccionado == \"Campo editorTexto\" ){");
                out.print("$('#dos_div').html(\"<img src='Interfaz/Contenido/images/EditText.PNG' alt='Logo' width='220' height='190.5' />\");");
//                out.print("$('#dos_div').html(\"<textarea id='descripcion-id' name='txt_descripcion' class='input_full' rows='5'  placeholder='Detallado' onchange='javascript:this.value=this.value.toUpperCase();'></textarea>\");");
                out.print("}else if (texto_seleccionado == \"Campo archivo\" ){");
                out.print("$('#dos_div').html(\"");
                out.print("<div class='fileUpload btn btn-primary'>");
                out.print("<input type='file' class='upload' id='uploadBtn' name='archivo' >");
                out.print("<span>Cargar</span>");
                out.print("</div>");
                out.print("<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled'>\");");
                out.print("}else{");
                out.print("$('#dos_div').html(texto_seleccionado);}");
                out.print("})})");
                out.print("</script>");
                // </editor-fold>
                out.print("<h3>Nuevo formulario</h3>");
                out.print("<form method='post' name='formarea' action='Formulario?op=2&idC=" + IdCargo + "&posC=" + obj_pos[2] + "' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<b>Nombre del campo: </b><br />");
                out.print("<input type='text' name='txt_nombreC' id='nombreC-id' placeholder='Nombre Campo' onkeyup='javascript:this.value=this.value.charAt(0).toUpperCase() + this.value.slice(1)'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombreC-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Tipo :</b><br />");
                out.print("<select name='txt_tipo' id='tipo-id' onchange='mostrar(this)'>");
                out.print("<option style='display:none;' value='N/A'>SELECCIONE UN TIPO</option>");
                out.print("<option>Campo hora</option>");
                out.print("<option>Campo fecha</option>");
                out.print("<option>Campo texto</option>");
                out.print("<option>Campo detallado</option>");
                out.print("<option>Campo seleccion</option>");
                out.print("<option>Campo lista</option>");
                if (cont == 0) {
                    out.print("<option>Campo archivo</option>");
                    out.print("<option>Campo editorTexto</option>");
                }
                out.print("</select><br /><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('tipo-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");

                out.print("<div id='datos-id' style='display:none;'>");
                out.print("<b>Datos:</b><br />");
                out.print("<input type='text' name='txt_datos' id='datos-idd' placeholder='Datos' style='width: 150px;' >&nbsp;&nbsp;<a href='#' onClick='Agregar()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('datos-idd');");
                out.print("validation.add( Validate.Materiales );");
                out.print("</script>");
                out.print("</div>");

                out.print("<div id='datosR-id' style='display:none;'>");
                out.print("<b>Datos:</b><br />");
                out.print("<input type='text' name='txt_datosR' id='datos-iddR' placeholder='Datos' style='width: 150px;'>&nbsp;&nbsp;<a href='#' onClick='AgregarR()'><img src='Interfaz/Contenido/Iconos/Plus.png' alt='Logo' width='15' height='15' /></a><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('datos-iddR');");
                out.print("validation.add( Validate.Materiales );");
                out.print("</script>");
                out.print("</div>");

                out.print("<input type='submit' value='Registrar'><br />");
                out.print("</form>");
                // </editor-fold>
            }
            // <editor-fold defaultstate="collapsed"  desc="tiempo real vista formulario">
            out.print("<br />");
            out.print("<h3>Vista preliminar</h3>");
            out.print("<b><div id='un_div'>");
            out.print("</div></b>");
            out.print("<div id='dos_div' style='display:none;'>");
            out.print("</div>");
            out.print("<div id='radio_div' style='display:none;'>");
            out.print("</div>");
            out.print("<div id='lista'  style='display:none;'>");
            out.print("<select name='select' id='selectDatos' style='width:162px;'>");
            out.print("<option style='display:none;'>Seleccionar Campos</option>");
            out.print("<select>&nbsp;&nbsp;<a href='#' onClick='Eliminar()'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='15' height='15' /></a>");
            out.print("</div>");
            // </editor-fold>
            out.print("<div class='cleaner'></div></div>");
            // <editor-fold defaultstate="collapsed"  desc=" formulario en proceso">
            out.print("<fieldset class='resalta_field' id='formProceso' style='width: 210px; visibility: hidden; position: absolute; top: 200px; left: 45%;'>");
            if (pageContext.getRequest().getAttribute("ConsultaForm") != null) {
                Consultaform = (List) pageContext.getRequest().getAttribute("ConsultaForm");
                out.print("<table class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Hora: </b><br />");
                out.print("<input type='time' name='tmhora' id='tmhora_id' readonly='true'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('tmhora_id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</td>");
                out.print("<td>");
                out.print("<b>Fecha: </b><br />");
                out.print("<input id='fecha-id' type='text' name='txtfecha' class='required input_field' placeholder='Seleccionar fecha' readonly='true'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('fecha-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("</td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td>");
                out.print("<b>Turno: </b><br />");
                out.print("<select name='slc_turno' id='turno-id' class='input_full' >");
                out.print("<option style='display:none;' value=''>Seleccione turno</option>");
                out.print("<option value='1'>TURNO 1</option>");
                out.print("<option value='2'>TURNO 2</option>");
                out.print("<option value='3'>TURNO 3</option>");
                out.print("<option value='1/12'>TURNO 1/12</option>");
                out.print("<option value='2/12'>TURNO 2/12</option>");
                out.print("<option value='OFICINA'>TURNO OFICINA</option>");
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('turno-id');");
                out.print(" validation.add(Validate.Presence);");
                out.print("</script>");
                out.print("</td>");
                out.print("<td align='center'>");
                out.print("<b>Registra novedades <br /> de maquina: </b>");
                out.print("<input type='radio' name='rdo_tnov' value='1'>SI");
                out.print("<input type='radio' name='rdo_tnov' value='0' checked='checked'>NO");
                out.print("</td>");
                out.print("</tr>");
                for (int i = 0; i < Consultaform.size(); i++) {
                    Object[] obj_formulario = (Object[]) Consultaform.get(i);
                    if (obj_formulario[8].equals(1)) {
                        String[] arg_nameId = obj_formulario[4].toString().split(" ");
                        String[] arg_datos = obj_formulario[6].toString().split("-");
                        out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto")) ? "" : "<td>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "<tr><td>" : "<td>")) + "");
                        if (obj_formulario[5].equals("Campo hora")) {
                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                            out.print("<input type='time' name='rd_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id'><br />");
                            out.print("<script type='text/javascript'>");
                            out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                            out.print("validation.add( Validate.Presence );");
                            out.print("</script>");
                        } else {
                            if (obj_formulario[5].equals("Campo fecha")) {
                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                out.print("<input id='fecha" + (i + 1) + "' type='text' name='dtp_" + arg_nameId[0] + "' class='required input_field' placeholder='Seleccionar " + obj_formulario[4] + "'><br />");
                                out.print("<script type='text/javascript'>");
                                out.print("var validation = new LiveValidation('fecha" + (i + 1) + "');");
                                out.print("validation.add( Validate.Presence );");
                                out.print("</script>");
                                out.print("<script type='text/javascript'>");
                                out.print("$(function() { $( '#fecha" + (i + 1) + "' ).datepicker({ altFormat: 'yy, d MM, DD' }); });");
                                out.print("</script>");
                            } else {
                                if (obj_formulario[5].equals("Campo texto")) {
                                    out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                    out.print("<input type='text' name='txt_" + arg_nameId[0] + "' id='" + arg_nameId[0] + "_id' placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                                    out.print("<script type='text/javascript'>");
                                    out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                    out.print("validation.add( Validate.Presence );");
                                    out.print("</script>");
                                } else {
                                    if (obj_formulario[5].equals("Campo detallado")) {
                                        out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                        out.print("<textarea id='" + arg_nameId[0] + "_id' name='text_" + arg_nameId[0] + "' class='input_full' rows='5'  placeholder='" + obj_formulario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><br />");
                                        out.print("<script type='text/javascript'>");
                                        out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                        out.print("validation.add( Validate.Presence );");
                                        out.print("</script>");
                                    } else {
                                        if (obj_formulario[5].equals("Campo seleccion")) {
                                            out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                            for (int j = 0; j < arg_datos.length; j++) {
                                                if (j == arg_datos.length - 1) {
                                                    out.print("<input type='radio' name='rdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "<br />");
                                                } else {
                                                    out.print("<input type='radio' name='rdo_" + arg_nameId[0] + "'>" + arg_datos[j] + "");
                                                }

                                            }
                                        } else {
                                            if (obj_formulario[5].equals("Campo lista")) {
                                                out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                                out.print("<select name='select' id='" + arg_nameId[0] + "_id'>");
                                                out.print("<option style='display:none;'>Seleccionar Campos</option>");
                                                for (int j = 0; j < arg_datos.length; j++) {
                                                    if (j == arg_datos.length - 1) {
                                                        out.print("<option>" + arg_datos[j] + "</option>");
                                                    } else {
                                                        out.print("<option>" + arg_datos[j] + "</option>");
                                                    }
                                                }
                                                out.print("</select><br /><br />");
                                                out.print("<script type='text/javascript'>");
                                                out.print("var validation = new LiveValidation('" + arg_nameId[0] + "_id');");
                                                out.print("validation.add( Validate.Presence );");
                                                out.print("</script>");
                                            } else {
                                                if (obj_formulario[5].equals("Campo archivo")) {
                                                    out.print("<b>" + obj_formulario[4] + ": </b><br />");
                                                    out.print("<div class='fileUpload btn btn-primary'>");
                                                    out.print("<input type='file' class='upload' id='uploadBtn' name='archivo' >");
                                                    out.print("<span>Cargar</span>");
                                                    out.print("</div>");
                                                    out.print("<p style='display:inline'></p>");
                                                    out.print("<input id='uploadFile' placeholder='No ha seleccionado ningun archivo' disabled='disabled' /><br /><br />");
                                                    out.print("</p>");
                                                    out.print("<script type='text/javascript'>");
                                                    out.print("document.getElementById('uploadBtn').onchange = function () {");
                                                    out.print("document.getElementById('uploadFile').value = this.value;};");
                                                    out.print("</script>");
                                                } else {
                                                    if (obj_formulario[5].equals("Campo editorTexto")) {
                                                        HTMLeditor = "<b>" + obj_formulario[4] + ": </b><br />"
                                                                + "<textarea id='descripcion-id' name='text_" + arg_nameId[0] + "' class='input_full' placeholder='" + obj_formulario[4] + "' style='width: 440px; height: 400px'><div contenteditable='true'><p>*</p></div></textarea><br />";
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        out.print("" + ((i == (Consultaform.size() - 1)) ? ((obj_formulario[5].equals("Campo editorTexto")) ? "" : "</td>") : ((i == 0 || i == 2 || i == 4 || i == 6) ? "</td>" : "</td></tr>")) + "");
                    } else {
                    }
                }
                if (!HTMLeditor.equals("")) {
                    out.print("<tr>");
                    out.print("<td colspan='2'>" + HTMLeditor + "</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
            }
            out.print("</fieldset>");
            // </editor-fold>
            out.print("<div id='content'>");
            // <editor-fold defaultstate="collapsed"  desc="consulta campos registrados">
            if (pageContext.getRequest().getAttribute("ConsultaForm") != null) {
                Consultaform = (List) pageContext.getRequest().getAttribute("ConsultaForm");
                out.print("<a href='Cargo?op=3&idC=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>&nbsp;&nbsp;&nbsp;");
                out.print("<a href='javascript:mostrarV();'><img src='Interfaz/Contenido/Iconos/Document.png' alt='Logo' width='25' height='25.5' title='Formulario en proceso' /></a>");
                out.print("<h3>Formulario registrado</h3>");
                if (Consultaform == null || Consultaform.isEmpty()) {
                    out.print("<h3>No se han registrado campos</h3>");
                } else {
                    out.print("<table class='table' id='resultados' style='width: 100%;'>");
                    out.print("<tr>");
                    out.print("<th>Posicion</th>");
                    out.print("<th>Campo</th>");
                    out.print("<th>tipo</th>");
                    out.print("<th>valor</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Estado</th>");
                    out.print("</tr>");
                    for (int i = 0; i < Consultaform.size(); i++) {
                        Object[] obj_formulario = (Object[]) Consultaform.get(i);
                        if (obj_formulario[8].equals(1)) {
                            out.print("<tr>");
                            out.print("<form method='post' name='formposicion' action='Formulario?op=4&idC=" + obj_formulario[1] + "&idF=" + obj_formulario[0] + "&pos=" + obj_formulario[7] + "'>");
                            out.print("<td align='center' style='width:25px;'><input type='number' max='" + ((cont == 0) ? Consultaform.size() : (Consultaform.size() - 1)) + "' min='1' name='ubicacion' id='" + obj_formulario[7] + "_id' value='" + obj_formulario[7] + "' style='width:32px;border:none;margin:5px;text-align: center;' placeholder='0' " + ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo") ) ? "disabled" : "") + "></td>");
                            out.print("</form>");
                            out.print("<td align='center' >" + obj_formulario[4] + "</td>");
                            out.print("<td align='center' >" + obj_formulario[5] + "</td>");
                            out.print("<td align='center' >" + obj_formulario[6] + "</td>");
                            out.print("<td align='center' ><a href='Formulario?op=1&idC=" + IdCargo + "&idF=" + obj_formulario[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Formulario?op=3&idF=" + obj_formulario[0] + "&est=" + 0 + "&idC=" + obj_formulario[1] + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' /></a></td>");
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<form method='post' name='formposicion' action='Formulario?op=4&idC=" + obj_formulario[1] + "&idF=" + obj_formulario[0] + "'>");
                            out.print("<td align='center'><input type='number' max='" + ((cont == 0) ? Consultaform.size() : (Consultaform.size() - 1)) + "' min='1' name='ubicacion' id='" + obj_formulario[7] + "_id' value='" + obj_formulario[7] + "' style='width:32px;border:none;color:red;margin:5px;text-align: center;' placeholder='0' " + ((obj_formulario[5].equals("Campo editorTexto") || obj_formulario[5].equals("Campo archivo")) ? "disabled" : "") + "></td>");
                            out.print("</form>");
                            out.print("<td align='center' ><b style='color:red;'>" + obj_formulario[4] + "</b></td>");
                            out.print("<td align='center' ><b style='color:red;'>" + obj_formulario[5] + "</b></td>");
                            out.print("<td align='center' ><b style='color:red;'>" + obj_formulario[6] + "</b></td>");
                            out.print("<td align='center' ><a href='Formulario?op=1&idC=" + IdCargo + "&idF=" + obj_formulario[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Formulario?op=3&idF=" + obj_formulario[0] + "&est=" + 1 + "&idC=" + obj_formulario[1] + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25' /></a></td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                }
            }
            // </editor-fold>
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
