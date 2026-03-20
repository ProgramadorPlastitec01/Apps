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
            // <editor-fold defaultstate="collapsed" desc="inicio session">
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
            if (pageContext.getRequest().getAttribute("resultado_contraseña") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_contraseña").toString());
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
//                    out.print("<a href='Sesion?opc=2' style='float:right'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:22px;height:22px;' title='Cerra modulo de registro' /></a>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p style='color:#03899C'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Login?opc=2' method='post'>");
                    out.print("<center>");
                    out.print("<input type='hidden' id='usuario'  name='id_usuario' value='" + id_usuario + "' />");
                    out.print("<input type='password' id='pass-input'  placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("validatedObj.add(Validate.Password);");
//                    out.print("validatedObj.add(Validate.Password_1);");
                    out.print("</script>");
                    out.print("<input type='password' id='confpass-input' name='txt_passw' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
//                    out.print("validatedObj.add(Validate.Password_1);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");
//                    out.print("<input type='hidden' value='" + menu + "' name='id_usuario'>");
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
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Los datos ingresados son incorrectos.','error');");
                    out.print("</script>");
                }
            }
// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="alertas modulo usuarios">
            if (pageContext.getRequest().getAttribute("Registro_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_usuario").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el usuario\","
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
                            + "text:\"se ha modificado el usuario\","
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
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha des-activado el usuario\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado el usuario\","
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
            if (pageContext.getRequest().getAttribute("resultado_contraseñaR") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_contraseñaR").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha restaurado la contraseña al año en curso\","
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
// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="alertas modulo defectos">
            if (pageContext.getRequest().getAttribute("registro_defecto") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_defecto").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el defecto\","
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
            if (pageContext.getRequest().getAttribute("modificar_defecto") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_defecto").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado el defecto\","
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
            if (pageContext.getRequest().getAttribute("estado_defecto") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_defecto").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha des-activado el defecto\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado el defecto\","
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
// </editor-fold>
            //<editor-fold defaultstate="collapsed" desc="alertas modulo maquina">
            if (pageContext.getRequest().getAttribute("registro_maquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_maquina").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la maquina\","
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
            if (pageContext.getRequest().getAttribute("modificar_maquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_maquina").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la maquina\","
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
            if (pageContext.getRequest().getAttribute("estado_maquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_maquina").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha des-activado la maquina\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado la maquina\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo nota">
            if (pageContext.getRequest().getAttribute("registro_nota") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_nota").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la nota\","
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
            if (pageContext.getRequest().getAttribute("modificar_nota") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_nota").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la nota\","
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
            if (pageContext.getRequest().getAttribute("estado_nota") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_nota").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha revisado la nota\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo orden">
            if (pageContext.getRequest().getAttribute("registro_orden") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_orden").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la orden de produccion\","
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
            if (pageContext.getRequest().getAttribute("estado_orden") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_orden").toString());
                String estado = pageContext.getRequest().getAttribute("estado").toString();
                if (resultado) {
                    if (estado.equals("abierto")) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha abierto la orden\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha cerrado la orden\","
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
            if (pageContext.getRequest().getAttribute("estado_logParametro") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_logParametro").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado los dimesionales\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo turno">
            if (pageContext.getRequest().getAttribute("registro_turno") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_turno").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
//                            + "position:\"right:20px\","
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el turno\","
                            + "type:\"success\","
                            + "timer:\"1200\","
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
            if (pageContext.getRequest().getAttribute("falla_rd") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("falla_rd").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Alerta\","
                            + "text:\"No se puede realizar registro, primero se debe finalizar registro de despeje de turno anterior.\","
                            + "type:\"warning\","
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
            if (pageContext.getRequest().getAttribute("Ingreso_Usu_despeje") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Ingreso_Usu_despeje").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Usuario Cambiado\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error, verifique los datos\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("seriales_turno") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("seriales_turno").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado los seriales en el turno\","
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
            if (pageContext.getRequest().getAttribute("modificar_turno") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_turno").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado modificado el turno\","
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

            if (pageContext.getRequest().getAttribute("registro_seguimiento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_seguimiento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el seguimiento del turno\","
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

            if (pageContext.getRequest().getAttribute("estado_estacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_estacion").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha des-activado la estacion\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado la estacion\","
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

            if (pageContext.getRequest().getAttribute("Defecto_estacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Defecto_estacion").toString());
                int cantidad = Integer.parseInt(pageContext.getRequest().getAttribute("cantidadD").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado " + cantidad + " defecto(s) en el turno\","
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
            if (pageContext.getRequest().getAttribute("validar_Tomas") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("validar_Tomas").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha validado las tomas en el turno recordar finalizar el turno\","
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

            if (pageContext.getRequest().getAttribute("estado_turno") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_turno").toString());
                String estado = String.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado.equals("abierto")) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha abierto el turno\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha finalizado el turno\","
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
            if (pageContext.getRequest().getAttribute("cambio_calidad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("cambio_calidad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"El turno se rechazo correctamente\","
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

            if (pageContext.getRequest().getAttribute("Cuarentena_turno") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Cuarentena_turno").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha aprobado la cuarentena del turno\","
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
            if (pageContext.getRequest().getAttribute("resultado_aprobar_pruebaF") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_aprobar_pruebaF").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha aprobado la prueba funcional\","
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
            if (pageContext.getRequest().getAttribute("prueba_funcional") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("prueba_funcional").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                String lote = pageContext.getRequest().getAttribute("lote_ensamble").toString();
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado una prueba funcional para el lote " + lote + "\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else if (estado == 0) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"No se ha registrado la prueba funcional,favor dar resultado a la ultima prueba registrada del lote " + lote + "\","
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
            if (pageContext.getRequest().getAttribute("ValidacionLote") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ValidacionLote").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Se seleccionaron dos lotes diferentes.\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="alertas modulo ficha tecnica">
            if (pageContext.getRequest().getAttribute("Registro_ficha") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_ficha").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la ficha tecnica\","
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
            if (pageContext.getRequest().getAttribute("estado_ficha") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_ficha").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha des-activado la ficha tecnica\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado la ficha tecnica\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo resumen">
            if (pageContext.getRequest().getAttribute("Completar_Resumen") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Completar_Resumen").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha completado el resumen\","
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
            if (pageContext.getRequest().getAttribute("Registro_resumen") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_resumen").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se registro el resumen correctamente\","
                            + "type:\"success\""
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"ocurrio un error en el registro revise la información o por favor comunicarse con el administrador\","
                            + "type:\"error\""
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="alertas despeje">
            if (pageContext.getRequest().getAttribute("Guardar_despeje") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Guardar_despeje").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Se ha guardado el despeje\","
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
            if (pageContext.getRequest().getAttribute("Error_Datos_Despeje") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Error_Datos_Despeje").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"Datos incorrectos\","
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_resultado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
