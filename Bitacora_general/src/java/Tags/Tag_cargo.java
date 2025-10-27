package Tags;

import Controladoras.AreaJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_cargo extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        AreaJpaController jpa_area = new AreaJpaController();
        List lst_area = jpa_area.ConsultaAreas();
        try {
            String rol = sesion.getAttribute("Rol").toString();
            String nombre = sesion.getAttribute("Nombre").toString();
            List sigla = (List) pageContext.getRequest().getAttribute("sigla");
            String filtro = (String) pageContext.getRequest().getAttribute("filtro");
            out.print("<div id='sidebar'>");
            if (pageContext.getRequest().getAttribute("ModificarCargo") != null) {
                // <editor-fold defaultstate="collapsed"  desc="Modificar Cargo">
                List ConCargoM = (List) pageContext.getRequest().getAttribute("ModificarCargo");
                Object[] obj_cargoM = (Object[]) ConCargoM.get(0);
                out.print("<h3>Modificar cargo");
                out.print("<div style='float: right;'>");
                out.print("<a href='Cargo?op=3&idC=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                out.print("</div>");
                out.print("</h3>");
                out.print("<form method='post' name='formarea' action='' onsubmit='checkSubmit();'>");
                out.print("<b>Área:</b>");
                if (sigla == null || sigla.isEmpty()) {
                    out.print("<select name='txt_area' onchange=\"document.formarea.action=\'Cargo?op=1&idC=" + obj_cargoM[0] + "&txt_bus=" + filtro + "\';document.formarea.submit();\">");
                    out.print("<option style='display:none;' value='" + obj_cargoM[1] + "'>" + obj_cargoM[10] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (obj_area[5].equals(1)) {
                            out.print("<option value='" + obj_area[0] + "'>" + obj_area[3] + "</option>");
                        } else {
                        }
                    }
                    out.print("</select>");
                } else {
                    Object[] obj_sigla = (Object[]) sigla.get(0);
                    out.print("<select name='txt_area' onchange=\"document.formarea.action=\'Cargo?op=1&idC=" + obj_cargoM[0] + "&txt_bus=" + filtro + "\';document.formarea.submit();\">");
                    out.print("<option style='display:none;' value='" + obj_sigla[0] + "'>" + obj_sigla[3] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (obj_area[5].equals(1)) {
                            out.print("<option value='" + obj_area[0] + "'>" + obj_area[3] + "</option>");
                        } else {
                        }
                    }
                    out.print("</select>");
                }
                out.print("<input type='hidden' name='txt_registroM' value='" + nombre + "//" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<b>Cargo: </b><br />");
                out.print("<input type='text' name='txt_cargoM' id='cargo-id' value='" + obj_cargoM[5] + "' placeholder='Cargo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('cargo-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Nombre registro: </b><br />");
                out.print("<input type='text' name='txt_nomRegistroM' id='nomRegistro-id' value='" + obj_cargoM[4] + "' placeholder='Nombre registro' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nomRegistro-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                if (sigla == null || sigla.isEmpty()) {
                    out.print("<b>Codigo: </b><br />");
                    out.print("<input type='text' name='txt_codigoM' id='codigo-id' value='" + obj_cargoM[6] + "' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('codigo-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                } else {
                    Object[] obj_sigla1 = (Object[]) sigla.get(0);
                    out.print("<b>Codigo: </b><br />");
                    out.print("<input type='text' name='txt_codigoM' id='codigo-id' value='R-" + obj_sigla1[4] + "-' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('codigo-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                }
                out.print("<b>Versión: </b><br />");
                out.print("<input type='text' name='txt_versionM' id='version-id' value='" + obj_cargoM[7] + "' placeholder='Versión' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('version-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.EnterosNA );");
                out.print("</script>");
                out.print("<b>Correo: </b><br />");
                if (obj_cargoM[9].equals(1)) {
                    out.print("<input type='radio' name='rd_correoM' value='1' checked > SI");
                    out.print("<input type='radio' name='rd_correoM' value='0'> NO <br /><br />");
                } else {
                    out.print("<input type='radio' name='rd_correoM' value='1'> SI");
                    out.print("<input type='radio' name='rd_correoM' value='0' checked > NO <br /><br />");
                }
                out.print("<input type='submit' value='Modificar' onClick=\"document.forms.formarea.action=\'Cargo?op=4&idC=" + obj_cargoM[0] + "&txt_bus=" + filtro + "\';\"><br />");
                out.print("</form>");
                // </editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="Registrar Cargo">
                out.print("<h3>Nuevo cargo</h3>");
                out.print("<form method='post' name='formarea' action='' onsubmit='checkSubmit();'>");
                out.print("<b>Área:</b>");
                if (sigla == null || sigla.isEmpty()) {
                    out.print("<select name='txt_area' onchange=\"document.formarea.action=\'Cargo?op=1&idC=" + 0 + "&txt_bus=\';document.formarea.submit();\">");
                    out.print("<option style='display:none;'>SELECCIONE EL AREA</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (obj_area[5].equals(1)) {
                            out.print("<option value='" + obj_area[0] + "'>" + obj_area[3] + "</option>");
                        } else {
                        }
                    }
                    out.print("</select>");
                } else {
                    Object[] obj_sigla = (Object[]) sigla.get(0);
                    out.print("<select name='txt_area' onchange=\"document.formarea.action=\'Cargo?op=1&idC=" + 0 + "&txt_bus=\';document.formarea.submit();\">");
                    out.print("<option style='display:none;' value='" + obj_sigla[0] + "'>" + obj_sigla[3] + "</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (obj_area[5].equals(1)) {
                            out.print("<option value='" + obj_area[0] + "'>" + obj_area[3] + "</option>");
                        } else {
                        }
                    }
                    out.print("</select>");
                }
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<b>Cargo: </b><br />");
                out.print("<input type='text' name='txt_cargo' id='cargo-id' placeholder='Cargo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('cargo-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Nombre registro: </b><br />");
                out.print("<input type='text' name='txt_nomRegistro' id='nomRegistro-id' placeholder='Nombre registro' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nomRegistro-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                if (sigla == null || sigla.isEmpty()) {
                    out.print("<b>Codigo: </b><br />");
                    out.print("<input type='text' name='txt_codigo' id='codigo-id' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('codigo-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                } else {
                    Object[] obj_sigla1 = (Object[]) sigla.get(0);
                    out.print("<b>Codigo: </b><br />");
                    out.print("<input type='text' name='txt_codigo' id='codigo-id' value='R-" + obj_sigla1[4] + "-' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                    out.print("<script type='text/javascript'>");
                    out.print("var validation = new LiveValidation('codigo-id');");
                    out.print("validation.add( Validate.Presence );");
                    out.print("</script>");
                }
                out.print("<b>Versión: </b><br />");
                out.print("<input type='text' name='txt_version' id='version-id' placeholder='Versión' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('version-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.EnterosNA );");
                out.print("</script>");
                out.print("<b>Correo: </b><br />");
                out.print("<input type='radio' name='rd_correo' value='1'> SI");
                out.print("<input type='radio' name='rd_correo' value='0'> NO <br /><br />");
                out.print("<input type='submit' value='Registrar' onClick=\"document.forms.formarea.action=\'Cargo?op=2\';\"><br />");
                out.print("</form>");
                // </editor-fold>
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            // <editor-fold defaultstate="collapsed"  desc="Consultar Cargo">
            if (pageContext.getRequest().getAttribute("consultaCargo") != null) {
                List ConCargo = (List) pageContext.getRequest().getAttribute("consultaCargo");
                out.print("<form action='Cargo?op=3&idC=" + 0 + "' method='post' >");
                out.print("<div style='float: right;'>");
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("</form>");
                out.print("<h3>Cargos registrados</h3>");
                if (ConCargo == null || ConCargo.isEmpty()) {
                    out.print("<h2>No se encuentran cargos registrados</h2>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width:100%;'>");
                    out.print("<tr>");
                    out.print("<th>Cargo</th>");
                    out.print("<th>Nombre del registro</th>");
                    out.print("<th>Codigo</th>");
                    out.print("<th>Versión</th>");
                    out.print("<th>Modificar</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Formulario</th>");
                    out.print("<th>Permisos</th>");
                    out.print("</tr>");
                    for (int i = 0; i < ConCargo.size(); i++) {
                        Object[] obj_cargo = (Object[]) ConCargo.get(i);
                        out.print("<script language='Javascript'>"
                                + "function mostrar" + i + "() {"
                                + "var panel, mostrarr ;var pagina =''; panel = document.getElementById('permisos" + i + "');"
                                + "if(panel.style.visibility == 'hidden') {"
                                + "panel.style.visibility = 'visible';"
                                + "mostrar = document.getElementById('mostrar" + i + "');"
                                + "document.getElementById('cambiar" + i + "').src='Interfaz/Contenido/Iconos/Min.png';"
                                + "document.getElementById('cambiar" + i + "').title = 'Cancelar';"
                                + "}else {"
                                + "panel.style.visibility = 'hidden';"
                                + "mostrar= document.getElementById('mostrar" + i + "');"
                                + "document.getElementById('cambiar" + i + "').src = 'Interfaz/Contenido/Iconos/Plus.png';"
                                + "document.getElementById('cambiar" + i + "').title = 'Permisos';"
                                + "}"
                                + "}</script>");
                        out.print("<fieldset class='resalta_field' id='permisos" + i + "' style='width: 200px;visibility: hidden;position: absolute;top: 300px;left: 45%;'>");
                        out.print("<legend>Consulta Actividades</legend>");
                        out.print("<form action='Cargo?op=5&idC=" + obj_cargo[0] + "&txt_bus=" + filtro + "' method='post' >");
                        out.print("<b>Revisa actividades :</b><br />");
                        if (obj_cargo[13].equals(0)) {
                            out.print("<input type='radio' name='rdo_atd' value='1'>SI");
                            out.print("<input type='radio' name='rdo_atd' value='0' checked='checked'>NO");
                            out.print("<br /><br />");
                        } else {
                            out.print("<input type='radio' name='rdo_atd' value='1' checked='checked'>SI");
                            out.print("<input type='radio' name='rdo_atd' value='0'>NO");
                            out.print("<br /><br />");
                        }
                        out.print("<b>Registro de notas :</b><br />");
                        if (obj_cargo[14].equals(0)) {
                            out.print("<input type='radio' name='rdo_nta' value='1'>SI");
                            out.print("<input type='radio' name='rdo_nta' value='0' checked='checked'>NO");
                        } else {
                            out.print("<input type='radio' name='rdo_nta' value='1' checked='checked'>SI");
                            out.print("<input type='radio' name='rdo_nta' value='0'>NO");
                        }

                        out.print("<input type='submit' value='Asignar'><br />");
                        out.print("</form>");
                        out.print("</fieldset>");
                        if (obj_cargo[8].equals(1)) {
                            out.print("<tr>");
                            out.print("<td align='center'>" + obj_cargo[5] + "</td>");
                            out.print("<td align='center'>" + obj_cargo[4] + "</td>");
                            out.print("<td align='center'>" + obj_cargo[6] + "</td>");
                            out.print("<td align='center'>" + obj_cargo[7] + "</td>");
                            out.print("<td align='center' ><a href='Cargo?op=3&idC=" + obj_cargo[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Cargo?op=6&idC=" + obj_cargo[0] + "&est=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' /></a></td>");
                            out.print("<td align='center' ><a href='Formulario?op=1&idC=" + obj_cargo[0] + "&idF=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Document.png' alt='Logo' width='30' height='30.5' /></a></td>");
                            out.print("<td align='center' ><a id='mostrarr' href='javascript:mostrar" + i + "();'><img id='cambiar" + i + "' src='Interfaz/Contenido/Iconos/Plus.png' width='25px' height='25px' alt='edit' alt='edit' title='Permisos'></a></td>");
                            out.print("</tr>");
                        } else {
                            out.print("<tr>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_cargo[5] + "</b></td>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_cargo[4] + "</b></td>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_cargo[6] + "</b></td>");
                            out.print("<td align='center'><b style='color:red;'>" + obj_cargo[7] + "</b></td>");
                            out.print("<td align='center' ><a href='Cargo?op=3&idC=" + obj_cargo[0] + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Cargo?op=6&idC=" + obj_cargo[0] + "&est=" + 1 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a href='Formulario?op=1&idC=" + obj_cargo[0] + "&idF=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Document.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td align='center' ><a id='mostrarr' href='javascript:mostrar" + i + "();'><img id='cambiar" + i + "' src='Interfaz/Contenido/Iconos/Plus.png' width='25px' height='25px' alt='edit' alt='edit' title='Permisos'></a></td>");
                            out.print("</tr>");
                        }
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
            }
            // </editor-fold>
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
