package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_alertas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (pageContext.getRequest().getAttribute("Alerta") != null) {
                // <editor-fold defaultstate="collapsed" desc="SESION">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_sesion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesion','El tiempo en la sesión expiro','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contraseña")) {
                    int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("var1").toString());
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:-25%;margin-top:-15%;width:45%;text-align: justify'>");
//                    out.print("<a href='Sesion?opc=2' style='float:right'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:22px;height:22px;' title='Cerra modulo de registro' /></a>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p style='color:#03899C'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Sesion?opc=3' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='Id_usuario' value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input' class='placeholder-white'  placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' class='placeholder-white' name='Txt_password' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
                    out.print("</center>");
                    out.print("<div style='float:right;'><img src='Interfaz/Contenido/images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                    out.print("<div class='Ayuda'>");
                    out.print("<div class='label_info'><label style='color:#008063'>El cambio de Contraseña debe contener:<br />"
                            + "-Minimo 8 caracteres<br/>\n"
                            + "-Maximo 15 caracteres<br/>\n"
                            + "-Al menos una letra mayúscula<br/>\n"
                            + "-Al menos una letra minúscula<br/>\n"
                            + "-Al menos un dígito ( Numero )<br/>\n"
                            + "-No espacios en blanco<br/>\n"
                            + "-Al menos 1 caracter especial ( $@$!%*?&#- )</label></div>");
                    out.print("</div>");
                    out.print("<center>");
                    out.print("<br><input type='submit' value='Cambiar'>");
                    out.print("</center>");
                    out.print("</form>");
                    out.print("</fieldset>");
                    out.print("</div>");
                }

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Password_actualizado")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha actualizado la contraseña','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Password_restablecido")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha restablecido la contraseña por el año en curso','success');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS USUARIO">
                //ALERTAS USUARIOS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario ingresado no se encuentra registrado','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario " + var + " se encuentra desactivado.','info');");
                    out.print("</script>");
                }
                // </editor-fold>
                //                // <editor-fold defaultstate="collapsed" desc="ALERTAS USUARIOS_OLD">
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
//                    String var = pageContext.getRequest().getAttribute("var1").toString();
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Correcto','El usuario " + var + " se ha registrado correctamente','success');");
//                    out.print("</script>");;
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario")) {
//                    String var = pageContext.getRequest().getAttribute("var1").toString();
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Error','El usuario " + var + " no ha sido registrado','error');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
//                    String var = pageContext.getRequest().getAttribute("var1").toString();
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Correcto','El usuario " + var + "  se ha modificado correctamente','success');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
//                    String var = pageContext.getRequest().getAttribute("var1").toString();
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Error','El usuario " + var + " no ha sido modificado por datos invalidos','error');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_test")) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('¡¡ERROR!!','Las respuestas seleccinadas no son Corresctas!!','info');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("usuer_not_exis")) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('¡¡ERROR!!','El documento no existe en el sistema!!','info');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("no_user")) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('¡¡ERROR!!','Usuario o contraseña invalido!!','info');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("")) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Sesión','El usuario ingresado no se encuentra registrado','info');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
//                    String var = pageContext.getRequest().getAttribute("var1").toString();
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Sesión','El usuario " + var + " se encuentra desactivado.','info');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("users_desactivado")) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Recuperacion de contraseña exitosa','El usuario se encuentra desactivado, informar a sistemas.','success');");
//                    out.print("</script>");
//                }
//                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("contrasena_exitosa")) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Cambio de Contraseña exitoso','.','success');");
//                    out.print("</script>");
//                }
//                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS LINEA">

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La línea " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La línea " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PRUEBAS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_prueba")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La prueba " + var + " ha sido registrada correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_prueba")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La prueba " + var + " no se pudo registrar.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_prueba_existencia")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La prueba " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PROCESOS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_proceso")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El proceso " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_proceso")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El proceso " + var + " ya tiene existencia.','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS TURNO">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_turno")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El " + var + " del dia " + var2 + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_turno")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El " + var + " del dia " + var2 + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firmar_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Ha firmado correctamente el registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_firmar_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Error al firmar registro','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Campos vacios, no se permite el registro','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS VARIABLES">

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_variable")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La variable " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_variable")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La variable " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_alertas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
