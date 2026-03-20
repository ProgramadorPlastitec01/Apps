/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author prog.sistemas2
 */
public class Alertas extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            
            if(pageContext.getRequest().getAttribute("ErrorActualizacionPass") != null){
                out.print("<script language=\"javascript\" type=\"text/javascript\">");
                out.print("swal(\"Error\", \"Fallo en la solicitud\", \"error\");");
                out.print("</script>");
            }
            if(pageContext.getRequest().getAttribute("ActualizacionPass") != null){
                out.print("<script language=\"javascript\" type=\"text/javascript\">");
                out.print("swal(\"Listo\", \"La contraseña fue actualizada correctamente\", \"success\");");
                out.print("</script>");
            }
            
            
            //<editor-fold defaultstate="collapsed" desc="FORMULARIO CAMBIAR CONTRASEÑA">
            if (pageContext.getRequest().getAttribute("Cambio_contraseña") != null) {
                int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("idUsuario").toString());
                //out.print("<div class='sweet-local' tabindex='-1' id='emergente' style='opacity: 1.03; display:block;'>");
                //out.print("<fieldset class='popup_local  scrollbar' id='styleScroll' style='width:81%; height:78%; position: absolute;top:10%; left:10%; '>");
                out.print("<div class='sweet-local' style='opacity: 1.03; display: flex; margin:auto;align-items: center;'>");
                out.print("<fieldset class='popup_local' style='margin-left:25%;margin-top:-10%;width:45%;'>");
                out.print("<center><b>Cambiar Contraseña</b></center>");
                out.print("<p>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                out.print("<form action='Login?opc=5' method='post'>");
                out.print("<center>");
                out.print("<input type='hidden' id='Id_usuario' name='Id_usuario' value='" + id_usuario + "' />");
                out.print("<input class='respass' type='password' id='pass-input' class='placeholder-white' placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>&nbsp;&nbsp;&nbsp;");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('pass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Presence);");
                out.print("</script>");
                out.print("<input class='respass' type='password' id='confpass-input' class='placeholder-white' name='Txt_password' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('confpass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                out.print("</script>");
                out.print("</center>");
                out.print("<div style='float:right;'><img src='Interfaz/Graficas/api/images/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
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
            if (pageContext.getRequest().getAttribute("password_actualizada") != null) {
                out.print("<script language=\"javascript\" type=\"text/javascript\">");
                out.print("swal(\"Listo\", \"La contraseña fue actualizada correctamente\", \"success\");");
                out.print("</script>");
            
            }
            
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="SESION">
            if(pageContext.getRequest().getAttribute("Error_sesion") != null) {
                out.print("<script language='javascript' type='text/javascript'>");
                out.print("swal('Sesion','Rectifique los datos','error');");
                out.print("</script>");
            }
            if(pageContext.getRequest().getAttribute("pass_restablecida") != null) {
                out.print("<script language=\"javascript\" type=\"text/javascript\">");
                out.print("swal(\"Listo\", \"Contraseña restablecida por el año en curso\", \"success\");");
                out.print("</script>");
            }
            if(pageContext.getRequest().getAttribute("error_restablecerPass") != null) {
                out.print("<script language=\"javascript\" type=\"text/javascript\"");
                out.print("swal(\"Error\", \"Fallo en la solicitud\", \"error\");");
                out.print("</script>");
            }
            if(pageContext.getRequest().getAttribute("CamposVacios") != null) {
                out.print("<script language=\"javascript\" type=\"text/javascript\"");
                out.print("swal(\"Alerta\", \"Debe llenar todos los campos\", \"warning\");");
                out.print("</script>");
            }
            if(pageContext.getRequest().getAttribute("Usuario_no_existe") != null) {
                out.print("<script language=\"javascript\" type=\"text/javascript\"");
                out.print("swal(\"El usuario no existe\", \"Las credenciales ingresadas no concuerdan con algun registro\", \"warning\");");
                out.print("</script>");
            }
            if(pageContext.getRequest().getAttribute("Usuario_desactivado") != null) {
                String nombre = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script language=\"javascript\" type=\"text/javascript\"");
                out.print("swal(\"El usuario está desactivado\", \""
                        + "El ususario "+nombre+" no se encuentra activo\", \"warning\");");
                out.print("</script>");
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="alertas modulo prueba">
            if (pageContext.getRequest().getAttribute("registro_prueba") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_prueba").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la prueba\","
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
            if (pageContext.getRequest().getAttribute("modificar_prueba") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_prueba").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la prueba\","
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
            if (pageContext.getRequest().getAttribute("estado_prueba") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_prueba").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado la prueba\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado la prueba\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo tipo proceso">
            if (pageContext.getRequest().getAttribute("registro_tipoP") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_tipoP").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el tipo de proceso\","
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
            if (pageContext.getRequest().getAttribute("Modificar_tipoP") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_tipoP").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado el tipo de proceso\","
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

            if (pageContext.getRequest().getAttribute("estado_tipoP") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_tipoP").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado el tipo proceso\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado el tipo proceso\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo preguntas">
            if (pageContext.getRequest().getAttribute("registro_pregunta") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_pregunta").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la pregunta\","
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
            if (pageContext.getRequest().getAttribute("modificar_pregunta") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_pregunta").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la pregunta\","
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
            if (pageContext.getRequest().getAttribute("preguntas_prueba") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("preguntas_prueba").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha asociado la pregunta a la prueba\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo area">
            if (pageContext.getRequest().getAttribute("registro_area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_area").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la area\","
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
            if (pageContext.getRequest().getAttribute("modificar_area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_area").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la area\","
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

            if (pageContext.getRequest().getAttribute("estado_area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_area").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado la area\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado la area\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo capacitador">
            if (pageContext.getRequest().getAttribute("registro_capacitador") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_capacitador").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la area\","
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
            if (pageContext.getRequest().getAttribute("modificar_capacitador") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_capacitador").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la area\","
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

            if (pageContext.getRequest().getAttribute("estado_capacitador") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_capacitador").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado la area\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado la area\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo cargo">
            if (pageContext.getRequest().getAttribute("registro_cargo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_cargo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el cargo\","
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
            if (pageContext.getRequest().getAttribute("modificar_cargo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_cargo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado el cargo\","
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

            if (pageContext.getRequest().getAttribute("estado_cargo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_cargo").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado el cargo\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado el cargo\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo personal">
            if (pageContext.getRequest().getAttribute("registro_empleado") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_empleado").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado el personal\","
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
            if (pageContext.getRequest().getAttribute("modificar_empleado") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_empleado").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado el personal\","
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

            if (pageContext.getRequest().getAttribute("estado_personal") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_personal").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estadop").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado el personal\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado el personal\","
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
            //<editor-fold defaultstate="collapsed" desc="alertas modulo capacitacion/programar">
            if (pageContext.getRequest().getAttribute("registro_capacitacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registro_capacitacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha registrado la capacitacion\","
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
            if (pageContext.getRequest().getAttribute("modificar_programacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("modificar_programacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha modificado la programacion\","
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
            if (pageContext.getRequest().getAttribute("Borrar_programacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Borrar_programacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha eliminado la programacion\","
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
            if (pageContext.getRequest().getAttribute("finalizar_programacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("finalizar_programacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Correcto\","
                            + "text:\"se ha finalizado la programacion\","
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

            if (pageContext.getRequest().getAttribute("estado_personal") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_personal").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estadop").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha in-activado el personal\","
                                + "type:\"success\""
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal({"
                                + "title:\"Correcto\","
                                + "text:\"se ha activado el personal\","
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

        } catch (IOException ex) {
            Logger.getLogger(Alertas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
