package Tags;

import Controladoras.CargoJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String rol = sesion.getAttribute("Rol").toString();
        String nombre = sesion.getAttribute("Nombre").toString();
        CargoJpaController jpa_cargo = new CargoJpaController();
        List lst_cargos = null;
        lst_cargos = jpa_cargo.ConsultaCargos();
        int IdCargo = (Integer) pageContext.getRequest().getAttribute("idCargo");
        try {
            out.print("<div id='sidebar'>");
            if (pageContext.getRequest().getAttribute("consultaUsuarioM") != null) {
                // <editor-fold defaultstate="collapsed"  desc="Modificar Usuario">
                List ConUsuariosM = (List) pageContext.getRequest().getAttribute("consultaUsuarioM");
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                Object[] obj_usuarioM = (Object[]) ConUsuariosM.get(0);
                out.print("<h3>Modificar usuario");
                out.print("<div style='float: right;'>");
                out.print("<a href='Usuario?op=1&idU=" + 0 + "&txt_bus=&idC=" + 0 + "'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' title='Volver' /></a>");
                out.print("</div>");
                out.print("</h3>");
                out.print("<form action='Usuario?op=4&idU=" + obj_usuarioM[0] + "&txt_bus=" + filtro + "&idC=" + IdCargo + "' method='post' name='form1' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registroM' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Cargo: </b><br />");
                out.print("<select name='slc_cargoM' >");
                out.print("<option style='display:none;' value='" + obj_usuarioM[1] + "'>" + obj_usuarioM[12] + "</option>");
                for (int i = 0; i < lst_cargos.size(); i++) {
                    Object[] obj_cargo = (Object[]) lst_cargos.get(i);
                    if (obj_cargo[8].equals(1)) {
                        out.print("<option value='" + obj_cargo[0] + "'>" + obj_cargo[5] + "</option>");
                    } else {
                    }
                }
                out.print("</select><br /><br />");
                out.print("<b>Nombre: </b><br />");
                out.print("<input type='text' name='txt_nombreM' id='nombre-id' placeholder='Nombre' value='" + obj_usuarioM[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombre-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Apellido: </b><br />");
                out.print("<input type='text' name='txt_apellidoM' id='apellido-id' placeholder='Apellido' value='" + obj_usuarioM[5] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('apellido-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Documento: </b><br />");
                out.print("<input type='text' name='txt_documentoM' id='documento-id' placeholder='Documento' value='" + obj_usuarioM[6] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('documento-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Codigo: </b><br />");
                out.print("<input type='text' name='txt_codigoM' id='codigo-id' placeholder='Codigo' value='" + obj_usuarioM[7] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('codigo-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Correo: </b><br />");
                out.print("<input type='text' name='txt_mailM' id='mail-id' placeholder='correo' value='" + obj_usuarioM[17] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('mail-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Email );");
                out.print("</script>");
                out.print("<b>Usuario: </b><br />");
                out.print("<input type='text' name='txt_usuarioM' id='usuario-id' placeholder='Usuario' value='" + obj_usuarioM[8] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('usuario-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Contraseña: </b><br />");
                out.print("<input type='password' name='txt_passM' id='passM-id' placeholder='Contraseña' value='" + obj_usuarioM[9] + "' readonly><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('pass-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' value='Modificar'>");
                out.print("<br /><br />");
                out.print("</form>");
                out.print("<form action='Usuario?op=5&idU=" + obj_usuarioM[0] + "&validacion=1' method='post' name='formMRC' onsubmit='checkSubmit();'>");
                out.print("<center><span onclick='contrasenaM()'><b class='naranja'>Restablecer contraseña</b></span></center>");
                out.print("</form>");
                // </editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="Registrar Usuario">
                out.print("<h3>Nuevo usuario</h3>");
                out.print("<form action='Usuario?op=2' method='post' name='form1' onsubmit='checkSubmit();'>");
                out.print("<input type='hidden' name='txt_registro' value='" + nombre + "/" + rol + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<b>Cargo: </b><br />");
                out.print("<select name='slc_cargo' >");
                out.print("<option style='display:none;'>SELECCIONE EL CARGO</option>");
                for (int i = 0; i < lst_cargos.size(); i++) {
                    Object[] obj_cargo = (Object[]) lst_cargos.get(i);
                    if (obj_cargo[8].equals(1)) {
                        out.print("<option value='" + obj_cargo[0] + "'>" + obj_cargo[5] + "</option>");
                    } else {
                    }
                }
                out.print("</select><br /><br />");
                out.print("<b>Nombre: </b><br />");
                out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombre-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Apellido: </b><br />");
                out.print("<input type='text' name='txt_apellido' id='apellido-id' placeholder='Apellido' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('apellido-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Documento: </b><br />");
                out.print("<input type='text' name='txt_documento' id='documento-id' placeholder='Documento' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('documento-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.EnterosNA );");
                out.print("</script>");
                out.print("<b>Codigo: </b><br />");
                out.print("<input type='text' name='txt_codigo' id='codigo-id' placeholder='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('codigo-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.EnterosNA );");
                out.print("</script>");
                out.print("<b>Correo: </b><br />");
                out.print("<input type='text' name='txt_mail' id='mail-id' placeholder='correo' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('mail-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("validation.add( Validate.Email );");
                out.print("</script>");
                out.print("<b>Usuario: </b><br />");
                out.print("<input type='text' name='txt_usuario' id='usuario-id' placeholder='Usuario' onchange='javascript:this.value=this.value.toUpperCase();'><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('usuario-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Contraseña: </b><br />");
                out.print("<input type='password' id='pass-input' placeholder='Contraseña'><br />");
                out.print("<script>");
                out.print("var validation = new LiveValidation('pass-input');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Confirmar Contraseña: </b><br />");
                out.print("<input type='password' name='txt_pass' id='passC' placeholder='Confirmar Contraseña'><br />");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('passC');");
                out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                out.print("</script>");
                out.print("<input type='submit' value='Registrar'>");
                out.print("</form>");
                // </editor-fold>
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            // <editor-fold defaultstate="collapsed"  desc="Consultar Usuario">
            if (pageContext.getRequest().getAttribute("consultaUsuario") != null) {
                String filtro = (String) pageContext.getRequest().getAttribute("filtro");
                List ConUsuarios = (List) pageContext.getRequest().getAttribute("consultaUsuario");
                out.print("<form action='Usuario?op=1&idU=" + 0 + "&idC=" + 0 + "' name='formCargo' method='post' >");
                out.print("<div style='float: right;'>");
                out.print("<select name='idC' onchange=\"document.formCargo.action=\'Usuario?op=1&idU=" + 0 + "&txt_bus=" + filtro + "\';document.formCargo.submit();\">");
                out.print("<option style='display:none;'>Seleccione un cargo</option>");
                for (int i = 0; i < lst_cargos.size(); i++) {
                    Object[] obj_cargoC = (Object[]) lst_cargos.get(i);
                    if (obj_cargoC[8].equals(1)) {
                        out.print("<option value='" + obj_cargoC[0] + "'>" + obj_cargoC[5] + " / " + obj_cargoC[11] + "</option>");
                    } else {
                    }
                }
                out.print("</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
                out.print("<input type='text' name='txt_bus' aling='right' placeholder='Busqueda' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("</div>");
                out.print("</form>");
                out.print("<h3>Usuarios registrados</h3>");
                if (ConUsuarios == null || ConUsuarios.isEmpty()) {
                    out.print("<h2>No se encuentran usuarios registrados<h2>");
                } else {
                    out.print("<div id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados' style='width: 100%;'>");
                    for (int i = 0; i < ConUsuarios.size(); i++) {
                        Object[] obj_usuario = (Object[]) ConUsuarios.get(i);
                        if (obj_usuario[10].equals(1)) {
                            out.print("</tr>");
                            out.print("<td colspan='8'>");
                            out.print("</td>");
                            out.print("<tr>");
                            out.print("<tr>");
                            out.print("<th rowspan='3'>Documento:<br />" + obj_usuario[6] + "</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Nombre: </b>" + obj_usuario[4] + "</td>");
                            out.print("<td><b>Apellido: </b>" + obj_usuario[5] + "</td>");
                            out.print("<td><b>Codigo: </b>" + obj_usuario[7] + "</td>");
                            out.print("<td><b>Usuario: </b>" + obj_usuario[8] + "</td>");
                            out.print("<td rowspan='3' align='center'><a href='Usuario?op=1&idU=" + obj_usuario[0] + "&txt_bus=" + filtro + "&idC=" + IdCargo + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td rowspan='3' align='center'><a href='Usuario?op=3&idU=" + obj_usuario[0] + "&est=" + 0 + "&txt_bus=" + filtro + "&idC=" + IdCargo + "'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' /></a></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td><b>Cargo: </b>" + obj_usuario[12] + "</td>");
                            out.print("<td colspan='2'><b>Area: </b>" + obj_usuario[15] + "</td>");
                            out.print("<td><b>Correo: </b>" + obj_usuario[17] + "</td>");
                            out.print("</tr>");
                        } else {
                            out.print("</tr>");
                            out.print("<td colspan='8'>");
                            out.print("</td>");
                            out.print("<tr>");
                            out.print("<tr>");
                            out.print("<th rowspan='3'>Documento:<br />" + obj_usuario[6] + "</th>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='color:red;'><b style='color:red;'>Nombre: </b>" + obj_usuario[4] + "</td>");
                            out.print("<td style='color:red;'><b style='color:red;'>Apellido: </b>" + obj_usuario[5] + "</td>");
                            out.print("<td style='color:red;'><b style='color:red;'>Codigo: </b>" + obj_usuario[7] + "</td>");
                            out.print("<td style='color:red;'><b style='color:red;'>Usuario: </b>" + obj_usuario[8] + "</td>");
                            out.print("<td rowspan='3' align='center'><a href='Usuario?op=1&idU=" + obj_usuario[0] + "&txt_bus=" + filtro + "&idC=" + IdCargo + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("<td rowspan='3' align='center'><a href='Usuario?op=3&idU=" + obj_usuario[0] + "&est=" + 1 + "&txt_bus=" + filtro + "&idC=" + IdCargo + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' /></a></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td style='color:red;'><b style='color:red;'>Cargo: </b>" + obj_usuario[12] + "</td>");
                            out.print("<td style='color:red;'  colspan='2'><b style='color:red;'>Area: </b>" + obj_usuario[15] + "</td>");
                            out.print("<td style='color:red;'><b style='color:red;'>Correo: </b>" + obj_usuario[17] + "</td>");
                            out.print("</tr>");
                        }
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 45);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
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
