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
            //<editor-fold defaultstate="collapsed" desc="FORMULARIO CAMBIAR CONTRASEÑA">
            if (pageContext.getRequest().getAttribute("cambio_contraseña") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("cambio_contraseña").toString());
                int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usa").toString());
                if (resultado) {
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:25%;width:45%;'>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p style='color:#03899C'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Sesion?opc=2' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='id_usuario' value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input'  placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' name='txt_passw' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
                    out.print("</center>");
                    out.print("<div style='float:right;'><img src='Interfaz/Contenido/images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                    out.print("<div class='Ayuda'>");
                    out.print("<div class='label_info'><label style='color:#008063'>El cambio de Contraseña debe contener:<br />"
                            + "-Mínimo 8 caracteres<br/>\n"
                            + "-Máximo 15 caracteres<br/>\n"
                            + "-Al menos una letra mayúscula<br/>\n"
                            + "-Al menos una letra minúscula<br/>\n"
                            + "-Al menos un dígito ( Número )<br/>\n"
                            + "-No espacios en blanco<br/>\n"
                            + "-Al menos 1 caracter especial ( $@$!%*?&#- )</label></div>");
                    out.print("</div>");
                    out.print("<center>");
                    out.print("<br><input type='submit' value='Cambiar'>");
                    out.print("</center>");
                    out.print("</form>");
                    out.print("</fieldset>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Los datos ingresados son incorrectos...','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="RESULTADO ACTUALIZAR CONTRASEÑA">
            if (pageContext.getRequest().getAttribute("resultado_contraseña") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_contraseña").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha actualizado la contraseña.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','El usuario se encuentra  inactivo.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("resultado_contraseñaR") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_contraseñaR").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Iniciar sesión con usuario actual y como contraseña el año en curso.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Error al actualizar...','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="RESULTADO ESTADO INACTIVO">
            if (pageContext.getRequest().getAttribute("estadoInactivo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estadoInactivo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Los campos al iniciar la sesión se encuentran vacios.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>		
            //<editor-fold defaultstate="collapsed" desc="RESULTADO DATOS INCORRECTOS">
            if (pageContext.getRequest().getAttribute("ingreso_sistema") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ingreso_sistema").toString());
                pageContext.getRequest().removeAttribute("ingreso_sistema");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Los campos al iniciar la sesión se encuentran vacios.','error');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Los datos ingresados son incorrectos.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            if (pageContext.getRequest().getAttribute("Alerta") != null) {
                //<editor-fold defaultstate="collapsed" desc="SESIÓN">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_sesion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Sesion','El tiempo en la sesión expiro','info');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ALERTA USUARIOS">
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
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El usuario " + var + " se encuentra desactivado.','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ALERTA MATERIA PRIMA">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_materia")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La materia prima " + var + " se ha registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_materia")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La materia prima " + var + " ya se encuentra registrada.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_materia_equivalente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La materia prima equivalente " + var + " se ha asignado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Quitar_materia_equivalente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La materia prima equivalente " + var + " se ha quitado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_materia_equivalente")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La materia prima equivalente " + var + " ya se encuentra asignada.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_formula_materia_prima")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La asignación de materia(s) prima(s) a la formula se genero correctamente.','success');");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ALERTA REGISTRO PI">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_formula")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La formula " + var + " se ha registrado correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_formula")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La formula " + var + " ya se encuentra registrada.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_formula")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','Formula " + var + " modificada correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Modificar")) {
                    String var = pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','Error al modificar " + var + ".','error');");
                    out.print("</script>");
                }
                //FORMULA REGISTRO
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_PI")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El registro se genero correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_PI")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El registro no se ha generado correctamente.','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_PI_guardar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','Se ha almacenado el registro ver en historial de la formula.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_PI_guardar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se ha podido guardar el registro.','error');");
                    out.print("</script>");
                }
                //FORMULA REGISTRO DETALLE
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_PI_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','El registro se genero correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_PI_detalle")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','El registro no se ha generado correctamente.','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ALERTA OBSERVACIONES">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_PI_observaciones")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La observación se genero correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_PI_observaciones")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','La observación no se ha generado correctamente.','error');");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ALERTA DUREZAS">
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_Dureza")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','La dureza se registro correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registro_Dureza")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo Registrar...!','error');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Modifica_Dureza")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Exito','Dureza modificada correctamente.','success');");
                    out.print("</script>");
                }
                if (pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_Dureza")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("swal('Error','No se pudo Modificar...!','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_alertas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
