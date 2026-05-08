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
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Export_data")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Export','El archivo " + var + " ya se encuentra el la carpeta de reportes SIRH','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contraseña")) {
                    int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("var1").toString());
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:25%;margin-top:1%;width:45%;text-align: justify'>");
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
                    out.print("<div style='float:right;'><img src='Interfaz/MasterPage/images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
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
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Password_actualizado_year")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha actualizado la contraseña por el año en curso','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Password_restablecido")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se han actualizado las credenciales de inicio de sesion.','success');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS CALENDARIO">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_calendario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Actividad programada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_calendario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido registrar la actividad al calendario','error');");
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
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario_sin")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + " se ha registrado correctamente pero sin permisos','success');");
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
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario_sin")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El usuario " + var + "  se ha modificado correctamente pero sin permisos','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " no ha sido modificado','error');");
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
                // <editor-fold defaultstate="collapsed" desc="ALERTAS AREA">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_area")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El area de " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_area")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El area de " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS CARGO">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_cargo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El cargo " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cargo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El cargo " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS # TRABAJADORES">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_num_trabajadores")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Numero de trabajadores registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_num_trabajadores")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Numero de trabajadores en el mes y año ya se encuentra registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_num_trabajadores")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Numero de trabajadores actualizado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_num_trabajadores")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Numero de trabajadores en el mes y año no se han actualizado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS CATEGORIA">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_categoria")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La categoria  " + var + " ha sido registrada correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_categoria")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La categoria " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PERSONAL">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_empleado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El empleado  " + var + " ha sido registrada correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_empleado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El empleado " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_empleado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El empleado  " + var + " ha sido modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_empleado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El empleado " + var + " no se ha modificado.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_firmas")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha registrado firma para el usuario " + var + " correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizacion_firmas")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha actualizado firma para el usuario " + var + " correctamente.','success');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS SEGUIMIENTO">
                //<editor-fold defaultstate="collapsed" desc="ACCIDENTE">

                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_accidente")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Accidente registrado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_accidente")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El accidente no se ha podido registrar','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_accidente")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Accidente modificado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_accidente")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El accidente no se ha podido modificar','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ENFERMEDAD">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_enfermedad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Enfermedad registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_enfermedad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La enfermedad no se ha podido registrar','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_enfermedad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Enfermedad modificada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_enfermedad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La enfermedad no se ha podido modificar','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INCAPACIDAD">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_incapacidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Incapacidad registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_incapacidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La incapacidad no se ha podido registrar','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_incapacidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Incapacidad modificada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_incapacidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La incapacidad no se ha podido modificar','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="AUSENCIA">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_ausencia")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Ausencia registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_ausencia")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ausencia no se ha podido registrar','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_ausencia")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Ausencia modificada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_ausencia")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ausencia no se ha podido modificar','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DISCIPLINA">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_disciplina")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Disciplina registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_disciplina")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido registrar','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_disciplina")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Disciplina modificada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_disciplina")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido modificar','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="RETIRO">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_retiro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Retiro registrado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_retiro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido registrar','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_retiro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Retiro modificado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_retiro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido modificar','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DOTACION">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_dotacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Dotación asignada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_dotacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido asignar la dotación','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_dotacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Dotación modificada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_dotacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido modificar la dotación','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAPACITACION">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_capacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Capacitacion registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("DesactivarCapacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha cerrado la capacitaicon y se ha registrado el numero de Folio','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ActivarCapacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha activado correctamente la capacitacion','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EliminarCapacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha eliminado la capacitacion','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ParametrosActualizados")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se han registrado correctamente los datos','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("CapacitacionCalificacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha registrado calificacion','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("CapacitacionFirmada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha firmado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_capacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido registrar la capacitacion','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EConsultarCapacitador")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha encontrado al capacitador','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_capacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Capacitacion modificarda','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_capacitacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido modificar la capacitacion','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_capacitación_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Empleado asignado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_capacitación_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido asignar el empleado a la capacitación','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EXAMEN">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_examen")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Examen registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_examen")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido registrar el examen','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_examen")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Examen modificardo','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_examen")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido modificar el examen','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EPP">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_epp")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Dotación asignada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_epp")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido asignar la dotación','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_epp")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Dotación modificada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_epp")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido modificar la dotación','error');");
                    out.print("</script>");
                }
//</editor-fold>
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS COMPETENCIAS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_competencia_personal")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Calificaciones programadas correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_formato_competencia")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El formato de calificación de competencias " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_formato_competencia")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El formato de calificación de competencias " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_formato_competencia_ext")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El formato de calificación de competencias " + var + " Ya se encuentra registrado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ACTUALIZACION SALARIOS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizacion_salarios")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Actualización de salarios realizado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizacion_salarios_error")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La actualizacion de salario no se pudo llevar a cabo, comunicar al addministrador.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizacion_salarios_vacio")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La actualizacion no se llevara a cabo, no se reflejan cambios de salario en el personal.','error');");
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
