package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_resultado extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            //<editor-fold defaultstate="collapsed" desc="CAMBIAR CONTRASEÑA">
            if (pageContext.getRequest().getAttribute("Cambio_contraseña") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambio_contraseña").toString());
                String id_usuario = "";
                try {
                    id_usuario = pageContext.getRequest().getAttribute("idUsuario").toString();
                } catch (Exception e) {
                    id_usuario = "";
                }
                out.print("<div class='sweet-local' style='opacity: 1.03; display: flex; margin:auto;'>");
                out.print("<fieldset class='camb_field'>");
                out.print("<div class='camb_title'>");
                out.print("<h2> Cambiar Contraseña </h2>");
                out.print("</div>");
                out.print("<p style='text-align: center;'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec) y en este Aplicativo.</p>");
                out.print("<div class='camb_body'>");
                out.print("<form action='Login?opc=2' method='post'>");
                out.print("<div class='camb_form'>");
                out.print("<input type='hidden' name='Id_usuario' id='usuario' value='" + id_usuario + "'>");
                out.print("<input class='form-control' type='password' id='pass-input' placeholder='Nueva Contraseña' style='margin-right: 4%;'>");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('pass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Presence);");
                out.print("</script>");
                out.print("<input class='form-control' type='password' name='Txt_password' id='confpass-input' placeholder='Confirmar Contraseña' >");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('confpass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                out.print("</script>");
                out.print("</div>");
                out.print("<div style='display: flex; width: 100%; margin-bottom: 10px;'>");
                out.print("<div style='width: 72%;margin-left: 3%; text-align: initial;'>");
                out.print("<label style='color:#008063'>El cambio de Contraseña debe contener:<br />"
                        + "                        -Minimo 8 caracteres<br/>"
                        + "                        -Maximo 15 caracteres<br/>"
                        + "                        -Al menos una letra mayúscula<br/>"
                        + "                        -Al menos una letra minúscula<br/>"
                        + "                        -Al menos un dígito ( Numero )<br/>"
                        + "                        -No espacios en blanco<br/>"
                        + "                        -Al menos 1 caracter especial ( $@$!%*?&#- )</label>");
                out.print("</div>");
                out.print("<div style='float:right;'><img src='Interfaz/Contenido/Imagenes/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                out.print("</div>");
                out.print("<div>");
                out.print("<button class='btn_camb'>Cambiar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div>");
                out.print("</div>");

            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS SESION">

            if (pageContext.getRequest().getAttribute("Usuario_no_existe") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Usuario_no_existe").toString());
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','El usuario ingresado no se encuentra registrado.','error');");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Usuario_desactivado") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Usuario_desactivado").toString());
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Error','El usuario " + var + " se encuentra desactivado.','error');");
                out.print("</script>");
            }

            if (pageContext.getRequest().getAttribute("password_actualizada") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("password_actualizada").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha actualizado la contraseña correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en la actualizacion de la contraseña.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Error_app") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Error_app").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error en el proceso, favor comuniquese con el area T.I.\","
                            + "type:\"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS MAQUINAS">

            if (pageContext.getRequest().getAttribute("Registro_maquina") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_maquina").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la Maquina.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("EditarMaquina") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("EditarMaquina").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha actualizado la Maquina.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en la actualizacion.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("CambiarEstadoMaquina") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CambiarEstadoMaquina").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha cambiado el estado de la maquina.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en la actualizacion.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS RECIPIENTE">
            if (pageContext.getRequest().getAttribute("Registro_recipiente") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_recipiente").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el recipiente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_recipiente") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_recipiente").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el recipiente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ModificarEst_recipiente") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ModificarEst_recipiente").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el estado del recipiente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema al cambiar estado.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS TIEMPO DESCONTABLE">
            if (pageContext.getRequest().getAttribute("Registro_tiempo") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_tiempo").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el tiempo descontable.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_tiempo") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_tiempo").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el tiempo descontable.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en la modificacion.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("EstadoTiempoDescontable") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("EstadoTiempoDescontable").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el estado del tiempo descontable.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en la modificacion.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA USUARIOS">
            if (pageContext.getRequest().getAttribute("Registro_Usuario") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_Usuario").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el usuario correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_Usuario") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_Usuario").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el usuario correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambiar_estado_Usuario") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambiar_estado_Usuario").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se realiza cambio de estado.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Restablecer_Password") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Restablecer_Password").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se restablecío contraseña al año en curso.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA ROL">
            if (pageContext.getRequest().getAttribute("Registro_Rol") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_Rol").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el rol correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_Usuario") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_Rol").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el rol correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambiar_estado_Rol") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambiar_estado_Rol").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se realiza cambio de estado.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA ORDEN">
            if (pageContext.getRequest().getAttribute("Registar_Orden") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registar_Orden").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la orden de producción correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"La Orden de producción ya se encuentra generada, de no ser asi comuniquese con el administrador.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambiar_Estado_Orden") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambiar_Estado_Orden").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el estado correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ErrorCambiarEstado") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ErrorCambiarEstado").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"Se ha producido un error en la consulta de estados<br> Favor comunicarse a T.I si esta alerta continua apareciendo.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("registros_abiertos_Detalle") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("registros_abiertos_Detalle").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"No se puede cambiar el estado por que contiene <b>detalles</b> abiertos.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("registros_abiertos_Registros") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("registros_abiertos_Registros").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"No se puede cambiar el estado por que contiene <b>registros</b> abiertos.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_Orden") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_Orden").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"Se modifico correctamente la orden de producción.\","
                            + "type:\"success\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ha ocurrido un problema en el registro.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
//            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA REGISTRO">
            if (pageContext.getRequest().getAttribute("Registar_registro") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registar_registro").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registro el turno correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_registro") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_registro").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modifico el registro correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambiar_estado_registro") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambiar_estado_registro").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se cambio el estado del registro correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Fallo en cambio, comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ErrorCambiarEstado_r") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ErrorCambiarEstado_r").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"Se ha producido un error en la consulta de estados<br> Favor comunicarse a T.I si esta alerta continua apareciendo.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ValidacionCambiarEstadoOrdenAbierta") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ValidacionCambiarEstadoOrdenAbierta").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"No se puede abrir registro, debido a que la orden se encuentra abierta.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Fallo en cambio, comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistroDespeje") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroDespeje").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el registro despeje.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Fallo en cambio, comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS REGISTRO DETALLE">
            if (pageContext.getRequest().getAttribute("RegistrarDetalle") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistrarDetalle").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha realizado correctamente el registro.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ActualizarDetalle") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ActualizarDetalle").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificador correctamente el registro.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Conf_regHora") != null) {
                int result = Integer.parseInt(pageContext.getRequest().getAttribute("Conf_regHora").toString());
                if (result > 0) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title: '¿Desea iniciar con el registro de hora " + result + "?',\n"
                            + "  text: \"Se guardara la hora actual!\",\n"
                            + "  icon: 'warning',\n"
                            + "  showCancelButton: true,\n"
                            + "  confirmButtonColor: '#3085d6',\n"
                            + "  cancelButtonColor: '#d93232eb',\n"
                            + "  confirmButtonText: 'Confirmar!'"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ActualizarHora") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ActualizarHora").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado correctamente el peso y se ha guardado la hora de cierre.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("EditarHora") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("EditarHora").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el tiempo correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistroDefecto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroDefecto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han registrado los defectos correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha ingresado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("EditarDefecto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("EditarDefecto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han modificado los defectos correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha modificado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Reg_EditarDefecto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Reg_EditarDefecto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han modificado los defectos correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha modificado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ActualizarTiempo") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ActualizarTiempo").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han descontado los minutos correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha ingresado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("EditarTiempo") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("EditarTiempo").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han descontado los minutos correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha modificado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("EditarObservacion") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("EditarObservacion").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado la observación correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha modificado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("NoHayPeso") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("NoHayPeso").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Avertencia!\","
                            + "text:\"No hay una cantidad de peso para registrar<br>Revisar conexion a la bascula.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Intente de nuevo, si el error persiste favor comunicarse con los programadores de TI.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistrarObservacion") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistrarObservacion").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la observación correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia\","
                            + "text:\"No se ha registrado ningun valor.\","
                            + "type: \"warning\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cerrar_registroDetalle") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cerrar_registroDetalle").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha cambiado el estado del registro a CERRADO.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Abir_registroDetalle") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Abir_registroDetalle").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha cambiado el estado del registro a ABIERTO.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("NoHayPermiso_estado") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("NoHayPermiso_estado").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"No tiene permiso para cambiar el estado de del registro.\","
                            + "type:\"warning\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("SinUsuarios") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SinUsuarios").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"No ha ingresado ningun usuario al grupo.\","
                            + "type:\"warning\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("CambioMasivoEstados") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CambioMasivoEstados").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Terminado!\","
                            + "text:\"Se ha completado el peso total de la orden<br> Toda la orden fue cerrada.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("validacionTaraPeso") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("validacionTaraPeso").toString());
                double peso_r = Double.parseDouble(pageContext.getRequest().getAttribute("peso_r").toString());
                double peso_t = Double.parseDouble(pageContext.getRequest().getAttribute("peso_t").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Orden completa. \", "
                            + "text:\"El peso registrado ha superado la meta<br> Se ha registrado: <b style='color: #21d321;'>" + peso_t + "</b><br> Se debe descontar: <b style='color: #eec130;'>" + Math.round(peso_r) + "</b> \", "
                            + "type: \"success\", "
                            + "showCancelButton: false, "
                            + "showConfirmButton: true,"
                            + "html: true "
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ValidacionRegistroAbiertoDellCerrado") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ValidacionRegistroAbiertoDellCerrado").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Advertencia!\","
                            + "text:\"No se puede abrir el turno,debido a que el registro se encuentra cerrado.\","
                            + "type:\"warning\","
                            + "html: true"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
                
            }
            if (pageContext.getRequest().getAttribute("RegistroControl") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroControl").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado un nuevo control para las cuarentenas.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistrarCuarentena") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistrarCuarentena").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado una cuarentena.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("FirmarCuarentena") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("FirmarCuarentena").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha guardado la firma.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("CierreCuarentena") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CierreCuarentena").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha hambiado el estado de la cuarentena a FINALIZADO.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("InicioDespeje") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("InicioDespeje").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha iniciado correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se ha producido un error, favor comunicarse con el area T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS DE PERFIL">
            if (pageContext.getRequest().getAttribute("CambioFoto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CambioFoto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"El icono ha sido modificado correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("CambiarNombreApellido") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CambiarNombreApellido").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"El nombre y apellido han sido modificados correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS DEFECTO">
            if (pageContext.getRequest().getAttribute("Registrar_defecto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_defecto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro el defecto correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_defecto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_defecto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro el defecto correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambiar_estado_defecto") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambiar_estado_defecto").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se cambia de estado correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS ENTRADA MATERIAL">
            if (pageContext.getRequest().getAttribute("Registrar_entrada") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_entrada").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro la entrada de material correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_entrada") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_entrada").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se modifico la entrada correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("FirmaEntrada") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("FirmaEntrada").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro la firma correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar intentar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_resultado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
