package Tag;

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
                out.print("");
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
                // <editor-fold defaultstate="collapsed" desc="SESION">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_sesion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesion','El tiempo en la sesión expiro o los modulos complementarios no se encuentran parametrizados.','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contraseña")) {
                    int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("var1").toString());
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:30%;padding:10px;background-color:#fff;margin-top:-3%;width:40%;text-align: justify;border-radius:25px'>");
//                    out.print("<a href='Sesion?opc=2' style='float:right'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:22px;height:22px;' title='Cerra modulo de registro' /></a>");
                    out.print("<center><h1>Cambiar Contraseña</h1></center>");
                    out.print("<p style=\"color:#34495e\">Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Sesion?opc=3' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='Id_usuario' value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input' class='form-control' class='placeholder-white'  placeholder='Nueva Contraseña' style='position:relative;top:2px;width:30%'>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' class='form-control' class='placeholder-white' name='Txt_password' placeholder='Confirmar Contraseña' style='position:relative;top:2px;width:30%' >");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
                    out.print("</center>");
                    out.print("<div style='float:right;'><img src='Interfaz/StylePage/Images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                    out.print("<div class='Ayuda'>");
                    out.print("<div class='label_info'><label style='color:#34495e'>El cambio de Contraseña debe contener:<br />"
                            + "-Minimo 8 caracteres<br/>\n"
                            + "-Maximo 15 caracteres<br/>\n"
                            + "-Al menos una letra mayúscula<br/>\n"
                            + "-Al menos una letra minúscula<br/>\n"
                            + "-Al menos un dígito ( Numero )<br/>\n"
                            + "-No espacios en blanco<br/>\n"
                            + "-Al menos 1 caracter especial ( $@$!%*?&#- )</label></div>");
                    out.print("</div>");
                    out.print("<center>");
                    out.print("<br><input type='submit' class='btn btn-primary btn-user' value='Cambiar'>");
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
                //<editor-fold defaultstate="collapsed" desc="CORREOS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_correo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El correo " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_correo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El correo " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_correo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El correo " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_correo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El correo " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Correo_semanal")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El correo se ha enviado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Fallo_Correo_semanal")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El correo no se ejecuto correctamente','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="TIPO EQUIPOS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_tipo_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El tipo de equipo " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_tipo_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El tipo de equipo " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_tipo_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El tipo de equipo " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_tipo_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El tipo de equipo " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LINEAS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La línea " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La línea " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La línea " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_linea")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La línea " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ZONAS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_zona")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La zona " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_zona")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La zona " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_zona")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La zona " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_zona")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La zona " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES GENERALES">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_general")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La actividad general " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_general")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La actividad general " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_general")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La actividad general " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_general")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La actividad general " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PARAMETROS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_parametro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El parametro se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_parametro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El parametro no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_parametro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El parametro  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_parametro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El parametro no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="INSTRUCCIONES DE SEGURIDAD">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizacion_seguridad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Las instrucciones de seguridad se han actualizado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizacion_seguridad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Las instrucciones de seguridad no se han actualizado','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTROS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_registro")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El registro " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_registro")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El registro " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_registro")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El registro " + var + "  se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_registro")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El registro " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EQUIPOS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El equipo " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El equipo " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El equipo " + var + " se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_equipo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El equipo " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizar_equipo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La ficha tecnica del equipo ha sido actualizada correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizar_equipo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ficha tecnica del equipo no ha sido actualizada.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Verificar_ft_equipo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La ficha tecnica del equipo ha sido verificada correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_verificar_ft_equipo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ficha tecnica del equipo no ha sido verificada.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizar_equipo_fechas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se actualizan fechas de anterior PMP correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizar_equipo_fechas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se actualizan fechas del anterior PMP.','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ORDEN DE TRABAJO">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La OT # " + var + " se ha registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La OT # " + var + " no ha sido registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La OT # " + var + " se ha modificado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La OT # " + var + " no ha sido modificado por datos invalidos','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizar_orden_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha actualizado el detalle de la orden de trabajo.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizar_orden_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha actualizado el detalle de la orden de trabajo.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firma_OT")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var1 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    if (Integer.parseInt(var) == 0) {
                        out.print("swal('Correcto','Recibido por Producción " + var1 + "','success');");
                    } else {
                        out.print("swal('Correcto','Recibido por Calidad  " + var1 + "','success');");
                    }
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_firma_OT")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha firmado la OT.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firma_autorizacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se confirma autorización.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Empleado_inexistente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El documento " + var + " ingresado no se encuentra en la lista de empleados.','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR ROL">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambiar_rol")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Cambio realizado','Se realizo cambio de Rol, favor indicar al usuario,cerrar sesión para efectuar los cambios.','success');");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="NOTA">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_nota")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La nota se registro correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_nota")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La nota se modifico correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_nota")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Problemas con formulario, intente nuevamente o informe a T.I','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Estado_nota")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La nota se inactivado correctamente','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Estado_nota")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Intente nuevamente o informe a T.I','success');");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="RETO">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_reto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se registro correctamente el reto del día','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Fallo_reto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Fallo','Intente nuevamente o informe a T.I','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_reto_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se registro correctamente el reto','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_producto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha registrado el producto correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_reto_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha modificado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Inactivar_item")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha eliminado el item correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_cabecera")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha registrado la cabecera correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firma_Produccion")) {
                    String var1 = pageContext.getRequest().getAttribute("var").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Recibido por Producción " + var1 + "','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Validacion_Reto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','Ya existe un reto con este día, favor verificar.');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EliminarFirmaPR")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha eliminado la firma de producción.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EliminarCabeceraReto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se elimino la cabecera correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("FalloEliminarReto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Alerta','No es posible eliminar la cabecera del reto, para eliminar no debe tener retos asociados.','info');");
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
