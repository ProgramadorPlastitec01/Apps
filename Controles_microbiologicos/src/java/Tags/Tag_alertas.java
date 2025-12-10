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
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contraseña")) {
                    String id_usuario = pageContext.getRequest().getAttribute("idUsuario").toString();
//                out.print("<div class='sweet-local' tabindex='-1' id='emergente' style='opacity: 1.03; display:block;'>");
//                out.print("<fieldset class='popup_local  scrollbar' id='styleScroll' style='width:81%; height:78%; position: absolute;top:10%; left:10%; '>");
                    out.print("<div class='overlay' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:25%;margin-top:0%;width:45%;'>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Sesion?opc=2' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='Id_usuario'Id_usuario value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input' name='password' class='placeholder-white' placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
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
                    out.print("<div class='label_info' style='text-align:left'><label style='color:#008063'>El cambio de Contraseña debe contener:<br />"
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
                    out.print("</fieldset></div>");
                }
                //ALERTAS USUARIOS

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El usuario " + var + " se ha registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido registrado.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El usuario " + var + "  se ha modificado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido modificado por datos invalidos.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario ingresado no se encuentra registrado.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("userpassword_incorrecta")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Usuario y/o contraseña incorrectos.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("password_reestablecida")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La contraseña fue reestablecida al año en curso.','success');");
                    out.print("</script>");
                }

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("password_actualizada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La contraseña fue actualizada correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("usuario_sin_digitar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Digite usuario y/o contraseña.','error');");
                    out.print("</script>");
                }

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El usuario " + var + " ahora se encuentra desactivado.','success');");
                    out.print("</script>");
                }
                //ALERTAS DESINFECTANTES
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_desinfectante")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El desinfectante " + var + " ha sido registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_desinfectante")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El desinfectante " + var + " ya tiene existencia.','error');");
                    out.print("</script>");
                }
                //ALERTAS AREAS MUESTRADAS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_area_muestrada")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El área muestrada " + var + " ha sido registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_area_muestrada")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El área muestrada " + var + " ya tiene existencia.','error');");
                    out.print("</script>");
                }
                //ALERTAS TIPOS AREAS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_tipo_area")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El tipo de área " + var + " ha sido registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_tipo_area")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El tipo de área" + var + " ya tiene existencia.','error');");
                    out.print("</script>");
                }
                //UNIDADES MEDIDA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_unidad_medida")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La unidad de medida " + var + " ha sido registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_unidad_medida")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La unidad de medida " + var + " ya tiene existencia.','error');");
                    out.print("</script>");
                }
                //CONTROLES
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_control_cabecera")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El analisis " + var + " ha sido registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_control_cabecera")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El analisis " + var + " no se ha registrado.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_control_detalle")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El analisis " + var + " detalle ha sido registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_control_detalle")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El analisis " + var + " detalle no se ha registrado.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Estado_control")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    int estado = Integer.parseInt(pageContext.getRequest().getAttribute("est").toString());
                    out.print("<script language='javascript' type='text/javascript'>");
                    if (estado == 1) {
                        out.print("swal('Exito','El analisis " + var + " se ha activado.','success');");
                    } else {
                        out.print("swal('Exito','El analisis " + var + " se ha finalizado.','success');");
                    }
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_estado_control")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El analisis " + var + " no se ha modificado de estado','error');");
                    out.print("</script>");
                }
                //TIPO DE NIVEL
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_tipo_nivel")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El tipo de nivel " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_tipo_nivel")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El tipo de nivel " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
                //Proovedor
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_proovedor")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El proovedor " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_proovedor")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El proovedor " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_alertas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
