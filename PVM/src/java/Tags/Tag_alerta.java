package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_alerta extends TagSupport {

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
                            + "title:\"Error\","
                            + "text:\"Su sesion ha expirado\","
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
            if (pageContext.getRequest().getAttribute("estadoInactivo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estadoInactivo").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"El usuario se encuentra inactivo\","
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
            if (pageContext.getRequest().getAttribute("ingreso_sistema") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ingreso_sistema").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal({"
                            + "title:\"Error\","
                            + "text:\"Los datos de ingreso son incorrectos, favor verificar\","
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
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha realizado el cambio de contraseña (2023).',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro, comunicarse con el admin.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("welcome") != null) {
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-2\").ready(function() {\n"
                        + "  iziToast.success({\n"
                        + "    title: 'Bienvenido!',\n"
                        + "    message: 'App Metrologia',\n"
                        + "    position: 'bottomRight'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("getBack") != null) {
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-5\").ready(function() {\n"
                        + "  iziToast.show({\n"
                        + "    title: 'Adios',\n"
                        + "    message: 'has salido del aplicativo.',\n"
                        + "    position: 'bottomRight'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("cambio_contraseña") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("cambio_contraseña").toString());
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
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha registrado el usuario.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro, comunicarse con el admin.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_usuario").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha modificado el usuario.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro, comunicarse con el admin.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Estado_usuario") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_usuario").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'El estado del usuario ha sido cambiado.',\n"
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
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="alertas modulo area">

            if (pageContext.getRequest().getAttribute("Registro_area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_area").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha registrado una nueva area.',\n"
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
            if (pageContext.getRequest().getAttribute("Modificar_area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_area").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha modificado el area.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al modificar los datos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Estado_area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_area").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'Se ha inactivado el area.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'Se ha activado el area.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al cambiar el estado.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="alertas modulo accesorio">
            if (pageContext.getRequest().getAttribute("Registro_accesorio") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_accesorio").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha registrado un nuevo accesorio.',\n"
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
            if (pageContext.getRequest().getAttribute("Modificar_accesorio") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_accesorio").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha modificado un accesorio.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la modificacion.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Estado_accesorio") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_accesorio").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'Se ha inhabilitado un accesorio.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'Se ha habilitado un accesorio.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en favor comunicarse con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="alertas modulo tipo instrumento">
            if (pageContext.getRequest().getAttribute("Registro_tipo_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_tipo_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha registrado el tipo de instrumento.',\n"
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
            if (pageContext.getRequest().getAttribute("Modificar_tipo_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_tipo_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha modificado el tipo de instrumento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema con al modificacion de datos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Estado_tipo_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_tipo_instrumento").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 0) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'Se ha cambiado el estado del instrumento.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Atención',\n"
                                + "    message: 'Se ha cambiado el estado del instrumento.',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema con al modificacion de datos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="alertas modulo verificacion">
            if (pageContext.getRequest().getAttribute("Registro_tipo_verificacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_tipo_verificacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha registrado un nuevo tipo de verificación.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema con al registrar los datos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Modificar_tipo_verificacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_tipo_verificacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha modificado el tipo de verificación.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema con al registrar los datos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Estado_tipo_verificacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_tipo_verificacion").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha cambiado el estado de la verificación.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema con al cambiar de estado.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="alertas intrumento de medicion">

            if (pageContext.getRequest().getAttribute("Registro_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un instrumento.',\n"
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
            if (pageContext.getRequest().getAttribute("RegistroEventos") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("RegistroEventos").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado anulacion del registro.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, favor comunicarse con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Modificar_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modificado el instrumento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Registro_plantilla_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_plantilla_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado la plantilla del instrumento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Guardar_plantilla_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Guardar_plantilla_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha guardado la plantilla del instrumento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_Verificacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Verificacion").toString());
//                boolean resultadoFechas = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Fechas").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado la verificacion.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Resultado_Instrumento_estado") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Instrumento_estado").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'se ha activado el instrumento',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'se ha inactivado el instrumento',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
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

            if (pageContext.getRequest().getAttribute("Estado_plantilla_instrumento") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_plantilla_instrumento").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'se ha finalizado la plantilla y se modifico la ultima fecha de verificacion',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Estado_Verificacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Estado_Verificacion").toString());
                int estado = Integer.parseInt(pageContext.getRequest().getAttribute("estado").toString());
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'Se ha modificado el estado de la verificacion, Estado: Abierto',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-2\").ready(function() {\n"
                                + "  iziToast.success({\n"
                                + "    title: 'Correcto',\n"
                                + "    message: 'Se ha modificado el estado de la verificacion, Estado: Finalizado',\n"
                                + "    position: 'bottomRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Modificar_FechaVrf") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Modificar_FechaVrf").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la fecha de verificación.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

// </editor-fold>
//<editor-fold defaultstate="collapsed" desc="alertas modulo no conformidad">
            if (pageContext.getRequest().getAttribute("Registro_noconformidad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Registro_noconformidad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado la no conformidad.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("MRegistro_noconformidad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("MRegistro_noconformidad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha guardado la plantilla.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("MEstRegistro_noconformidad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("MEstRegistro_noconformidad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha finalizado la plantilla.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("MEnvio_noconformidad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("MEnvio_noconformidad").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha enviado el correo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("AnularVerificaciones") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("AnularVerificaciones").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.warning({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'Se ha eliminado la verificación.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comuniquese con el administrador.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="ALERTAS MODULO SOPORTE">
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_alerta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
