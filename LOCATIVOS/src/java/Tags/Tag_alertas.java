package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_alertas
        extends TagSupport {

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            if (this.pageContext.getRequest().getAttribute("Alerta") != null) {
                //<editor-fold defaultstate="collapsed" desc="USUARIOS">

                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario ingresado no se encuentra registrado','info');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario " + var + " se encuentra desactivado.','info');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PROVEEDORES">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_proveedor")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El proveedor " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_proveedor")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El proveedor " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_proveedor")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El proveedor " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_proveedor")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El proveedor " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="UBICACION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_ubicacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La ubicacion " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_ubicacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ubicacion " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_ubicacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La ubicacion " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_ubicacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ubicacion " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CLASIFICACION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_clasificacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La clasificación " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_clasificacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La clasificación " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_clasificacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La clasificación " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_clasificacion")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La clasificacion " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SOLICITUD">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_solicitud")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La solicitud " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_solicitud")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La solicitud" + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_Solicitud")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La solicitud correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Modificar_solicitud")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La solicitud no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PROGRAMACION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Programacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La Programacion se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Programacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Error al registrar','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_programacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se registra la modificado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_programacion_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Error al modificado la programacion','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_programacion_solictud")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se registran las solicitudes correctamente.','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Programacion_enviada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Programación cerrada.','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Sin_Prrogra")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No ha seleccionado programación.','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVIDAD">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_actividad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Actividad(es) registrada(s)','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actividad_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Error al registrar','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Eliminar_actividad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Seguro que desea elimina la actividad ?','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_eliminar_actividad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Error al eliminar la actividad','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="OTROS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Correo_vacio")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Info','Elija una solicitud para enviar','info');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Terminar_solicitud")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La solicitud se a terminado efectivamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Adjunto_vacio")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Verifique que halla seleccionado un adjunto','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Adjunto_bien")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha adjuntado correctamente','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Agrupar_solicitud")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Asegurece que haya seleccionado una solicitud para la agrupación','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Validacion_min_dos_solicitudes")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Seleccione mas de dos solicitudes para poder hacer la agrupación','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("no_registrado")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Ingrese alguna actividad','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Good_Insert")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','Registro Correcto','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Bad_Insert")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Error de Registro','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Mod_Good")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','Modificacion Correcta','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Mod_Bad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Error de Modificacion','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Estado_Good")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','Modificacion Correcta','success');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Estado_Bad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Error de Modificacion','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Sin_Sol")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','No Se Han Seleccionado Solicitudes','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Agrupacion_Vacia")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','No se encuentra historial de la solicitud','warning');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Filtro_Vacio")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','No se ha encontrado ningun dato','warning');");
                    out.print("</script>");
                }
//</editor-fold>
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
                //<editor-fold defaultstate="collapsed" desc="DECLINAR">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Declinacion_rechazada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Rechazado','La solicitud declinada ha sido rechazada y devuelto a su estado anterior.','error');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Solicitud_liberada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Info','Esta petición ya ha sido realizada.','info');");
                    out.print("</script>");
                }
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Declinacion_confirmada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Confimado','Se ha confirmado la declinación de la solicitud','success');");
                    out.print("</script>");
                }
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_alertas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
