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
                // <editor-fold defaultstate="collapsed" desc="ALERTAS USUARIOS">
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
                    out.print("swal('Sesión','Lo datos de sesion ingresados estan incorrectos o vacios.','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesión','El usuario " + var + " se encuentra desactivado.','info');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS LINEA">
                //ALERTAS LINEA
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
                // <editor-fold defaultstate="collapsed" desc="ALERTAS ALGORTIMO">
                //ALERTAS ALGORTIMO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_algoritmo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El algoritmo " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_algoritmo")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El algoritmo " + var + " ya tiene existencia','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS FICHA TECNICA">
                //ALERTAS FICHA TECNICA
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_ficha")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La ficha " + var + " en versión " + var2 + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_ficha")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ficha " + var + " en versión " + var2 + " no se ha registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Ficha_existente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La ficha " + var + " en versión " + var2 + " ya se encuentra registrada','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS ORDEN DE PRODUCCION">
                //ALERTAS ORDEN DE PRODUCCION
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La orden de producción " + var + " ha sido registrada correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_orden")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La orden de producción " + var + " ya existe.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cerrar_orden")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La orden de producción no se puede cerrar, hay productos aun abiertos.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cerrar_orden_2")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La orden de producción no se puede cerrar, no hay productos asociados.','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS PRODUCTO">
                //ALERTAS PRODUCTO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_producto")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El producto " + var + " ha sido asignado a la orden de producción " + var2 + "','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_producto")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto " + var + " no ha podido ser asignado a la orden de producción " + var2 + "','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Producto_existente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    String var2 = pageContext.getRequest().getAttribute("var2").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Info','El producto " + var + " ya esta asociado a la orden " + var2 + "','info');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_orden")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede abrir debido a que la orden de producción se encuentra cerrada','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_orden_2")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede cerrar debido a que no hay registros asociados','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_orden_3")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede abrir debido a que no hay registros asociados','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_producto_registro")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El producto no se puede cerrar debido a que hay registros aun abiertos','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS TURNOS">
                //ALERTAS TURNOS
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El turno se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El turno no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Codigo_linea_errado")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El código ingresado no es el de la línea ','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','La actualización del turno se ha generado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La actualización del turno no se ha generado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_abrir_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El turno no se puede abrir debido a que el producto se encuentra cerrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firmar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Ha añadido su firma en el registro','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_firmar_turno")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha añadido la firma en el registro','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_equipos_medicion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Asignación de equipos de medición realizada.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_equipos_medicion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha asignado los equipos de medición','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS SERIAL">
                //ALERTAS SERIAL
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El serial " + var + " ha sido registrado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El serial " + var + " no se ha registrado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Actualizar_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El serial " + var + " ha sido actualizado correctamente','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_actualizar_serial")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El serial " + var + " no se ha actualizado','error');");
                    out.print("</script>");
                }
                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="ALERTAS ROLLO">
                //ALERTAS ROLLO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_rollo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El rollo se ha registrado.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_rollo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El rollo se ha modificado.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_rollo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se registro el rollo.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_americio")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','El americio se ha registrado.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_americio")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se registro el americio.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Bajar_rollo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Siguiente rollo registrado.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("PasoRollos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha realizado el paso de rollos.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_bajar_rollo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha bajado el siguiente rollo.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Rollo_aprobado")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Aprobado','Rollo aprobado','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Rollo_cuarentena")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Cuarentena','Rollo en cuarentena','warning');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Rollo_rechazado")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Rechazado','Rollo rechazado','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_estado_calidad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo determinar el estado de caldiad del rollo','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("err_PasoRollos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo realizar el paso de rollos.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("RegisterCurvatura")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo realizar el paso de rollos.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("RegisterCurvatura") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterCurvatura").toString());
                if (result) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Aprobado','Se ha registrado la curvatura','success');");
                    out.print("</script>");
                } else {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo registrar la curvatura.','error');");
                    out.print("</script>");
                }
            }
                // </editor-fold>
                //<editor-fold defaultstate="collapsed" desc="R-PI-034">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Entrada de material registrada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha registrado la entrada de material','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Entrada de material actualizada','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modifica_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha actualizado la entrada de material','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Firmar_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Correcto','Se ha firmado la entrada de material','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_firmar_entrada_material")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha firmado la entrada de material','error');");
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
