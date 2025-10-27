package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_Resultado extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="Login">
            if (pageContext.getRequest().getAttribute("expirar_session") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("expirar_session").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"su session ha expirado\","
                            + "text:\"Ha sobrepasado el tiempo de actividad en su session\","
                            + "type:\"warning\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("DatosIncorrectos") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("DatosIncorrectos").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Usuario o contraseña incorrectos, favor verificar los datos ingresados\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("UsuarioInactivo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("UsuarioInactivo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"El usuario se encuentra in-activo\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("CamposVacios") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("CamposVacios").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Los campos estan vacios, favor verificar\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("password_actualizada") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("password_actualizada").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha realizado el cambio de la contraseña\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambio_contraseña") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambio_contraseña").toString());
                int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usa").toString());
                if (resultado) {
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                    out.print("<fieldset class='popup_local' style='margin-left:25%;width:45%;'>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p style='color:#03899C'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Login?opc=3' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='id_usuario' value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input'  placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' name='txt_passw' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
                    out.print("</center>");
                    out.print("<div style='float:right;'><img src='Interfaz/Contenido/Images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
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
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Los datos ingresados son incorrectos.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alertas usuario">
            if (pageContext.getRequest().getAttribute("Registro_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_usuario").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el usuario\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_usuario").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el usuario\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Password_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Password_usuario").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha Reestaurado la contraseña al año en curso\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Estado_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_usuario").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"Se ha activado el usuario\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"Se ha in-activado el usuario\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Modulo Pendiente">
            if (pageContext.getRequest().getAttribute("Registro_pendiente") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_pendiente").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el pendiente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_pendiente") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_pendiente").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el pendiente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Solucion_pendiente") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Solucion_pendiente").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha solucionado el pendiente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados actividad">
            if (pageContext.getRequest().getAttribute("Registro_actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_actividad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la actividad\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_actividad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado la actividad\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Registrar_actividadR") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_actividadR").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la actividad\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_actividadR") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_actividadR").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado la actividad\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados caso">
            if (pageContext.getRequest().getAttribute("Registro_Caso") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_Caso").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado y enviado el caso\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Solucion_Caso") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Solucion_Caso").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha solucionado el caso\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("casoEliminado") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("casoEliminado").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha eliminado el caso.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados modulo equipo">
            if (pageContext.getRequest().getAttribute("Registro_equipo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_equipo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Modificar_equipo_Lst") != null) {
                boolean jpa_lstVer = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_equipo").toString());
                if (jpa_lstVer) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_equipo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_equipo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Registrar_HV") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_HV").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado los adjuntos en el equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_HV") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_HV").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se modifico los adjuntos en el equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("EliminarRegistroEquipo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("EliminarRegistroEquipo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se elimino el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados encuestas">
            if (pageContext.getRequest().getAttribute("Encuesta_Enviada") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Encuesta_Enviada").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha enviado las encuestas\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Calificar_encuestas") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Calificar_encuestas").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha calificado la encuesta\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados listado Equipos">
            if (pageContext.getRequest().getAttribute("Registro_lista") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_lista").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro el tipo de equiipo correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_lista") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_lista").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Registro_equipo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_equipo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se realizo el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_equipo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_equipo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se modifico el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Registrar_anexo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_anexo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro el anexo correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_anexo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_anexo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se modifico el anexo correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Eliminar_anexo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Eliminar_anexo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se elimino el anexo correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Registrar_movimiento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_movimiento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro el movimiento correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_movimiento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_movimiento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se modifico el movimiento correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultado detalle de equipo">
            if (pageContext.getRequest().getAttribute("RegistroDetalle") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroDetalle").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha realizado el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados registro 005">
            if (pageContext.getRequest().getAttribute("Registro_Programacion_Actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_Programacion_Actividad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la programacion de actividades\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_Programacion_Actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_Programacion_Actividad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado la programacion de actividades\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Eliminar_Equipo_Programacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Eliminar_Equipo_Programacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha eliminado el equipo de la programacion\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Ejecutar_Equipo_Programacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Ejecutar_Equipo_Programacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha ejecutado la actividad del equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Verificar_Equipo_Programacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Verificar_Equipo_Programacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha verificado la actividad del equipo\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="RESULTADO ACTA">
            if (pageContext.getRequest().getAttribute("RegistroActa") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroActa").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el acta correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error al modificar el acta por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_ACTA") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_ACTA").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado el ACTA correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error al modificar el acta por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistroUsuariosActa") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroUsuariosActa").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han agregado correctamente los asistentes.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error al modificar el acta por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistroUsuariosActaVacio") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroUsuariosActaVacio").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Atención!\","
                            + "text:\"No se ha seleccionado ningun usuario.\","
                            + "type:\"warning\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error al modificar el acta por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ModificarUsuariosActa") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ModificarUsuariosActa").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han modificado correctamente los asistentes.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error al modificar el acta por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ModificarContenidoActa") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ModificarContenidoActa").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han modificado correctamente el contenido del acta.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error al modificar el acta por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistrarNuevaFirma") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistrarNuevaFirma").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se han registrado firma a este usuario correctamente.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error al registrar la firma en el sistema favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegistroFirmasActa") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroFirmasActa").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha firmado la acta correctamente.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error al firmar la en el sistema favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("NoExisteUsuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("NoExisteUsuario").toString());
                int documento_u = Integer.parseInt(pageContext.getRequest().getAttribute("NoExisteUsuario_documento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"El numero de documento: "+ documento_u +" no existe en el sistema.\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error al consultar los usuarios en el sistema favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Registro_usuariosExternos") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_usuariosExternos").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado un usuario externo en el acta\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Ocurrio un error al consultar los usuarios en el sistema favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados R-TI-026">
            if (pageContext.getRequest().getAttribute("Registro_R026") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_R026").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado correctamente.\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="resultados R-TI-017">
            if (pageContext.getRequest().getAttribute("Registrar_digitalizacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registrar_digitalizacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se realizo el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_digitalizacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_digitalizacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se modifico el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Inabilitar_digitalizacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Inabilitar_digitalizacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha inabilitado el registro correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alertas Bitacora">
            if (pageContext.getRequest().getAttribute("Registro_bitacora") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_bitacora").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado y enviado la bitacora\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Revizar_bitacora") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Revizar_bitacora").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha revisado la bitacora\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Reportante">
            if (pageContext.getRequest().getAttribute("Registro_reportante") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_reportante").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado el usuario\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("LoginCaso") != null) {
                out.print("<script type='text/javascript'>");
                out.print("swal({"
                        + "title:\"Error\","
                        + "text:\"Documento y codigo incorrectos\","
                        + "type:\"error\""
                        + "});");
                out.print("</script>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Calificar Caso">
             if (pageContext.getRequest().getAttribute("Calificar_caso") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Calificar_caso").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro la Calificación Correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTAS DE REGISTRO 001">
            if (pageContext.getRequest().getAttribute("Registro_actividad01") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_actividad01").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha registrado la actividad correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Editar_actividad01") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Editar_actividad01").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha modificado la actividad correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Firma_registrdaActividad") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Firma_registrdaActividad").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha añadido la firma correctamente.\","
                            + "type:\"success\","
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Verifique los datos ingresados y vuelva a enviar, de no ser asi comuniquese con el area de T.I.\","
                            + "type: \"error\","
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_Resultado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
