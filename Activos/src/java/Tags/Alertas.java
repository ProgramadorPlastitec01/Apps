package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Alertas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="FORMULARIO CAMBIAR CONTRASEÑA">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contrasena")) {
                String id_usuario = "";
                try {
                    id_usuario = pageContext.getRequest().getAttribute("idUsuario").toString();
                } catch (Exception e) {
                    id_usuario = "";
                }
                out.print("<div class='sweet-local' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                out.print("<fieldset class='popup_local' style='margin-left:-25%;margin-top:-10%;width:45%;'>");
                out.print("<center><b>Cambiar Contraseña</b></center>");
                out.print("<p>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec) y en este Aplicativo.</p>");
                out.print("<form action='Sesion?opc=2' method='post'>");
                out.print("<center>");
                out.print("<input type='hidden' id='usuario'  name='Id_usuario'Id_usuario value='" + id_usuario + "' />");
                out.print("<input type='password' id='pass-input' class='placeholder-white' placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('pass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Presence);");
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
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA USUARIOS">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El usuario " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }

            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El usuario " + var + "  se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','El usuario ingresado no se encuentra registrado.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','El usuario " + var + " se encuentra desactivado.','error');");
                out.print("</script>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS ÁREAS">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Area")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Area " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_area")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Area " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS REQUISICION">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Requisicion")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Material " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Duplicar_Requisicion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Material se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Masivo")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Se ha registrado " + var + " requisiciones masivamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_Requisicion")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Material " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EnviarOrden")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La Solicitud se ha sido enviado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Email_Alerta")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Requisiciones con fecha estimada vencida enviadas por Email.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Email_Error")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('error','Error de envio de correos.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("RegistroCantidad")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Las cantidades se han registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorCantidades")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','Datos incorrectos,de no ser asi comuniquese con el administrador.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorDetalles")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','Debe completar todos los datos de requisicion antes de enviar.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EnvioRequisiciones")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','La requisicion(nes) se actualizaron y se enviaron correctamente. ','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("LiberarRequisicion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La Requisicion se finalizo correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("DevolucionSolicitud")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La requisición se devolvio exitosamente,se notificara al área correspondiente a través de Email.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ActualizarDetalle")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La descripcion se ha actualizado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ModificarDetalle")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito',' El detalle se modifico correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorReq")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se ha seleccionado ninguna Requisición.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorDetalle")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se ha escrito ningun texto.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorFecha")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se encontraron requisiciones en este rango.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorDescripcion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','Datos incorrectos,de no ser asi comuniquese con el administrador.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EnvioReq")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','La requisición se envio correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Declinado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La requisición declinada se ha notificado al área correspondiente a través de Email.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorJustDeclinado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se ha escrito ninguna justificación.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorReg_Masivo")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','Registro incorrecto, realize el registro en base a la pantilla.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorReg_Mas")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No es escrito ninguno registro.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("RetornarRequisicion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Se ha retornado correctamente la requisición.','success');");
                out.print("</script>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS UNIDAD">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Unidad")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La Unidad " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_Unidad")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La Unidad " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS CLASIFICACION">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Clasificacion")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La clasificación " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_Clasificacion")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La clasificación " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA UBICACIONES">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_ubicacion")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La Ubicación " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_ubicacion")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','La Ubicación " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA PROCESO">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_proceso")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Proceso " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_proceso")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Proceso " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ModificacionActivosTomados")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Proceso se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("CambioEstado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Proceso se ha notificado al área correspondiente a través de Email.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorNotificar")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Ha ocurrido un error. Comuniquese con el Administrador','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("CambioEstado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Proceso cambio de estado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("LiberarProceso")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El proceso ha sido verificado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("DevolverProceso")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El proceso se regreso a Activos en proceso.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorJustificacion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se especifico justificación.','info');");
                out.print("</script>");
            }

//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ARLETA SESIÓN">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','El usuario no se encuentra registrado','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','El usuario " + var + " se encuentra desactivado.','error');");
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
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ARLETA ACTIVO">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_activo")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Equipo " + var + " se ha registrado correctamente.','success');");
                out.print("</script>");
            }

            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_activo")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El Equipo " + var + " se ha modificado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_adicion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Se Añadio al activo','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("cambio_estado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Se ha cambiado de estado correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_adicion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','Se modifico correctamente la adicion.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_M_Adicion")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','En la modificación de la adicion.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("ErrorAtvDeclinado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se ha especificado la justificación.','info');");
                out.print("</script>");
            }

//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ARLETA ERRORES">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','Ha ocurrido un error al registrar.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar")) {
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','Ha ocurrido un error al modificar " + var + ".','error');");
                out.print("</script>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="NOTIFICACION MENSUAL - CORREOS">
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EmailEnviado")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Exito','El correo mensual se envio correctamente.','success');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EmailVacios")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se encontraron proyectos en procesos de este mes.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EmailVacioBaja")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se encontro Maquinaria dada de baja de este mes.','info');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Alerta").toString().equals("EmailVacioProyectosCerrados")) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Alerta','No se encontro Maquinaria dada de baja de este mes.','info');");
                out.print("</script>");
            }
//            if (pageContext.getRequest().getAttribute("Email_Alerta").toString() != null) {
//                boolean resultado = Boolean.parseBoolean(pageContext.getRequest().getAttribute("Email_Alerta").toString());
//                if (resultado) {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Alerta','Se ha enviado el correo correctamente.','info');");
//                    out.print("</script>");
//                } else {
//                    out.print("<script language='javascript' type='text/javascript'>");
//                    out.print("swal('Alerta','No se han encontrado requisiciones sobrepasadas.','info');");
//                    out.print("</script>");
//                }
//            }
            //</editor-fold>
        } catch (Exception e) {
            Logger.getLogger(Alertas.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
