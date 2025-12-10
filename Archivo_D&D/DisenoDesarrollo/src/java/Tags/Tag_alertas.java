/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Prog.Aprendiz1
 */
public class Tag_alertas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (this.pageContext.getRequest().getAttribute("Alerta") != null) {
                //<editor-fold defaultstate="collapsed" desc="CAMBIO DE CONTRASEÑA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_contrasena")) {
                    String id_usuario = this.pageContext.getRequest().getAttribute("idUsuario").toString();
                    out.print("<div class='sweet-local' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left: 25%;margin-top: -10%;width: 50%;background-color: white;padding: 15px;border: 3px solid #4893D9;border-radius: 43px;'>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p>Recordar que la protección de datos, usuario y contraseña ayuda a evitar fraudes o alteraciones en la Organización (Plastitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Sesion?opc=2' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='Id_usuario'Id_usuario value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input' class='placeholder-white' placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px; background-color: #fff; color: #000;border-radius: 16px;' required>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' class='placeholder-white' name='Txt_password' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px; background-color: #fff; color: #000;border-radius: 16px;' required>");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
                    out.print("</center>");
                    out.print("<br><div style='float:right;'><img src='Interfaz/Contenido/Img/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                    out.print("<div class='Ayuda'>");
                    out.print("<br><div class='label_info' style='text-align:left'><label style='color:#008063'>El cambio de contraseña debe contener:<br />&bull; Mínimo 8 caracteres<br/>\n&bull; Máximo 15 caracteres<br/>\n&bull; Al menos una letra mayúscula,\n al menos una letra minúscula,\n al menos un número<br/>\n&bull; No espacios en blanco<br/>\n&bull; Al menos 1 carácter especial ($@$!%*?&#-)</label></div>");
                    out.print("</div>");
                    out.print("<br><div align='center'>");
                    out.print("<input type='submit' value='Cambiar' style='border-radius: 17px;width: 20%;background-color: #4288C9;border: 3px solid #4288C9;'>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</fieldset></div>");
                }
                //</editor-fold>
                //Alertas https://izitoast.marcelodolza.com/
                //<editor-fold defaultstate="collapsed" desc="ERROR DE SESION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_sesion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.warning({title: 'Sesión',message: 'Rectifique los datos.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="USUARIO NO REGISTRADO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_no_existe")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.info({title: 'Error',message: 'El usuario no se encuentra registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="USUARIO DESACTIVADO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Usuario_desactivado")) {
                    String var = this.pageContext.getRequest().getAttribute("var1").toString();
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.warning({title: 'Usuario',message: 'El usuario " + var + " se encuentra desactivado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PASSWORD RESTABLECIDA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("password_reestablecida")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title: 'Exito', message:'La contraseña fue restablecida correctamente.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR RESABLECER CONTRASEÑA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("error_restablecimiento")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title: 'Error',message: 'Hubo un fallo en la solicitud',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONTRASEÑA ACTUALIZADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("password_actualizada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title: 'Exito',message: 'La contraseña fue actualizada correctamente.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGSTRO USUARIO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_usuario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title: 'Correcto', message:'El usuario se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR REGISTRAR USUARIO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title: 'Error',message: 'El usuario no ha sido registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR USUARIO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_usuario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title: 'Correcto',message: 'El usuario se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR USUARIO MODIFICAR">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_usuario_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title: 'Error',message:'El usuario no ha sido modificada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO AREA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_area")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El área se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR AREA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_area")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title: 'Error',message: 'El área  no ha sido registrado',})");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR AREA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_area")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold> 
                //<editor-fold defaultstate="collapsed" desc="ERROR AL MODIFICAR AREA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_area_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message'El  no ha sido modificada.',});");
                    out.print("</script>");
                }
                //</editor-fold> 
                //<editor-fold defaultstate="collapsed" desc="REGISTRO CARGO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_cargo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El cargo  se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CARGO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cargo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'El cargo  no ha sido registrado'});");
                    out.print("</script>");
                }
                //</editor-fold> 
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR CARGO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_cargo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El cargo se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CARGO MODIFICAR">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cargo_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({tilte:'Error',message:'El cargo no ha sido modificado',});");
                    out.print("</script>");
                }
                //</editor-fold> 
                //<editor-fold defaultstate="collapsed" desc="REGISTRO ETAPA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_etapa")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La etapa  se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR ETAPA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_etapa")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La etapa no ha sido registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR ETAPA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_etapa")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La etapa se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR ETAPA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_etapa_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La etapa no ha sido modificada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO FASE">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_fase")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La fase se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR FASE">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_fase")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast({title:'Error',message'La fase  no ha sido registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR FASE">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_fase")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La fase se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR FASE">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_fase_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La fase no ha sido modificada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La prueba se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message'La prueba no ha sido registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La prueba se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR PRUEBA MODIFICAR">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_prueba_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La prueba no ha sido modificada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO CATEGORIA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_categoria")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La categoria se ha registrado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CATEGORIA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_categoria")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La categoria  no ha sido registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR CATEGORIA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_categoria")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La categoria se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CATEGORIA MODIFICAR">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_categoria_modificar")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message'La categoria no ha sido modificada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRO PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title: 'Correcto',message: 'El proyecto se ha registrado correctamente',});");
//                    out.print("iziToast.success({title:'Correcto',message:'El proyecto se ha registrado correctamente',});");
                    out.print("</script>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'El proyecto no ha sido registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR NUMERAL DETALLE PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_numeral_detalle_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Actividad cambiada de numeral',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR NUMERAL DETALLE PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_numeral_detalle_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast({title:'Error',message:'No se ha podido cambiar la actividad de numeral',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El proyecto se ha modificado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'El proyecto no ha sido modificado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR DETALLE PROYECTO RESP">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_detalle_proyecto_resp")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Actividad con responsable registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR REGISTRAR DETALLE PROYECTO RESP">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registrar_detalle_proyecto_resp")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La actividad con responsable no se ha registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR DETALLE PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_detalle_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Actividad registrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR REGISTRAR DETALLE PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registrar_detalle_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La actividad no se ha registrado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR DETALLE PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_detalle_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Actividad modificada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR DETALLE PROYECTO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_detalle_proyecto")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'La actividad no se ha modificado',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ATENDER ACTIVIDAD">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Atender_actividad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Actividad correctamente atendida',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR ATENDER ACTIVIDAD">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_atender_actividad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title'Error',message:'No se ha podido atender la actividad',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LISTA DISTRIBUCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Lista_distribucion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Lista de distribucion actualizada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LISTA DISTRIBUCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Lista_distribucion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se pudo actualizar la lista de distribucion.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CORREO LISTA DISTRIBUCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Correo_lista_distribucion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Notificacion',message:'Correo enviado a participes',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR PROGRAMACION PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_programacion_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Programación de pruebas registrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR REGISTRAR PROGRAMACION PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registrar_programacion_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido registrar la programacide pruebas',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PROGRAMACION PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_programacion_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Programación de pruebas modificada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR PROGRAMACION PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_programacion_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido modificar la programacide pruebas',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="PRUEBA ASIGNADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Prueba_asignada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Prueba asignada correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR PRUEBA ASIGNADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_prueba_asignada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido asignar la prueba',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADA PRODUCCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_entrada_produccion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Entrada registrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERRROR REGISTRAR ENTRADA PRODUCCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registrar_entrada_produccion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido registrar la entrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR ENTRADA PRODUCCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_entrada_produccion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Entrada modificada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR ENTRADA PRODUCCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_entrada_produccion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido modificar la entrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR OTRAS ENTRADAS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_otras_entradas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Entrada registrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR OTRAS ENTRADAS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_otras_entradas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido registrar la entrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR OTRAS ENTRADAS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_otras_entradas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Entrada modificada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR OTRAS ENTRADAS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_otras_entradas")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido modificar la entrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR ENTRADAS PROYECTOS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registrar_entradas_proyectos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Entrada registrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR REGISTRAR ENTRADAS PROYECTOS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_registrar_entradas_proyectos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido registrar la entrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR ENTRADAS PROYECTOS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_entradas_proyectos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Entrada modificada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR ENTRADAS PROYECTOS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_entradas_proyectos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha podido modificar la entrada',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO ACTIVIDAD A PROCESO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("actividad_proceso")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Actividad',message:'La actividad ha vuelto a estar en proceso.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO ACTIVIDAD A REVISION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("actividad_revision")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Actividad',message:'La actividad ha pasado a estar en revisión.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO ACTIVIDAD A FINALIZADO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("actividad_finalizacion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Actividad',message:'La actividad ha finalizado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR A CAMBIAR EL ESTADO DE ACTIVIDAD">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("error_estado_actividad")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Ha ocurrido un problema al cambiar la actividad de estado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DESACTIVAR ENTRADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Entrada_desactivada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se ha desctivado la entrada correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVAR ENTRADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Entrada_activada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La entrada se ha activado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR DESACTIVAR ENTRADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Entrada_desactivada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Se ha producido un error al desactivar la entrada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR ACTIVAR ENTRADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Entrada_activada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Se ha producido un error al activar la entrada.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DESACTIVAR PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("prueba_desactivada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se ha desctivado la prueba correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR DESACTIVAR PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_prueba_desactivada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Se ha producido un error al desactivar la prueba.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVAR PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Prueba_activada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'La prueba se ha activado correctamente',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR ACTIVAR ENTRADA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Prueba_activada")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Se ha producido un error al activar la prueba.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CORREO LISTA DE DISTRIBUCION">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Correo_lista_distribucion")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Se ha producido un error al enviar el correo.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO USUARIO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_usuario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se ha cambiado el estado del usuario.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CAMBIO ESTADO USUARIO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_Cambio_usuario")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Error al cambiar el estado del usuario.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO ETAPA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_estado_etapa")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El estado de la etapa ha cambiado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR CAMBIO ESTADO ETAPA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_cambio_estado_etapa")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'Ha ocurrido un problema al cambiar el estado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO AREA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_estado_area")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El estado de la área ha cambiado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO FASE">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_estado_fase")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El estado de la fase ha cambiado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO CARGO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_estado_cargo")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El estado del cargo ha cambiado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO PRUEBA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_estado_prueba")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El estado de la prueba ha cambiado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIO ESTADO CATEGORIA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Cambio_estado_catego")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'El estado de la categoria ha cambiado.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SUPPORT">
                if (pageContext.getRequest().getAttribute("RegisterRepotant") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterRepotant").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'Se ha registrado correcamente el reportante.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-4\").ready(function() {\n"
                                + "  iziToast.error({\n"
                                + "    title: 'Error',\n"
                                + "    message: 'Ocurrio un error en el proceso, favor comuniquese con el area T.I',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                }
                if (pageContext.getRequest().getAttribute("RegisterCase") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterCase").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'Se ha registrado correcamente el caso en REDEAC.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-4\").ready(function() {\n"
                                + "  iziToast.error({\n"
                                + "    title: 'Error',\n"
                                + "    message: 'Ocurrio un error en el proceso, favor comuniquese con el area T.I',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MAIL">
                if (pageContext.getRequest().getAttribute("MailRegister") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("MailRegister").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'Se ha registrado el correo correctamente.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-4\").ready(function() {\n"
                                + "  iziToast.error({\n"
                                + "    title: 'Error',\n"
                                + "    message: 'Ha ocurrido un problema en el registro.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                }
                if (pageContext.getRequest().getAttribute("MailUpdate") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("MailUpdate").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'Se ha actualizado la informacion.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-4\").ready(function() {\n"
                                + "  iziToast.error({\n"
                                + "    title: 'Error',\n"
                                + "    message: 'Ha ocurrido un problema en el registro.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                }
                if (pageContext.getRequest().getAttribute("MailChangeState") != null) {
                    boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("MailChangeState").toString());
                    if (result) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-3\").ready(function() {\n"
                                + "  iziToast.warning({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'El estado ha sido cambiado.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-4\").ready(function() {\n"
                                + "  iziToast.error({\n"
                                + "    title: 'Error',\n"
                                + "    message: 'Ha ocurrido un problema en el registro.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR PERMISO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Registro_permiso")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se ha registrado el nuevo permiso.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR REGISTRAR PERMISO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("error_Registro_permiso")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha registrado el nuevo permiso.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR PERMISO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Modificar_permiso")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se ha modificado el permiso.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR MODIFICAR PERMISO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_modificar_permiso")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se ha modficado el permiso.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CAMBIAR ESTADO PERMISO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("permiso_estado")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se ha cambiado el estado del permiso.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ASIGNAR PERMISOS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Permisos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.success({title:'Correcto',message:'Se han asignado permisos al cargo.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ERROR ASIGNAR PERMISOS">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_permisos")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.error({title:'Error',message:'No se han podido asignar los permisos.',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="BIENVENIDO">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Bienvenido")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.info({title:'¡Bienvenido!',message:'Al aplicativo Archivo Diseño & Desarrollo.',icon:'fas fa-door-open',});");
                    out.print("</script>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="SALIDA">
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Salida")) {
                    out.print("<script language='javascript' type='text/javascript'>");
                    out.print("iziToast.show({title:'¡Hasta luego!', message:'Que vuelvas pronto.', icon:'fas fa-door-closed'});");
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
