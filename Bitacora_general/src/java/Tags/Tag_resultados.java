package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_resultados extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="INICIAR SESION">
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

            if (pageContext.getRequest().getAttribute("resultado_contraseña") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_contraseña").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha actualizado la contraseña','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','El usuario se encuentra  incactivo.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("resultado_contraseñaC") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_contraseñaC").toString());
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha actualizado la contraseña al año en curso, favor volver a ingresar','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','El usuario se encuentra  incactivo.','error');");
                    out.print("</script>");
                }
            }

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
            //<editor-fold defaultstate="collapsed" desc="ALERTA AREA">
            if (pageContext.getRequest().getAttribute("Resultado_Area") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Area").toString());
                pageContext.getRequest().removeAttribute("Resultado_Area");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha registrado correctamente el area.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_AreaM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_AreaM").toString());
                pageContext.getRequest().removeAttribute("Resultado_AreaM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente el area.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_EstadoM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_EstadoM").toString());
                int estado = Integer.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                pageContext.getRequest().removeAttribute("Resultado_Area");
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El area se ha activado correctamente.','success');");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El area se ha desactivado correctamente.','success');");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_FormularioM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_FormularioM").toString());
                pageContext.getRequest().removeAttribute("ResuResultado_FormularioMltado_AreaM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente el campo.','sucess');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA CARGO">
            if (pageContext.getRequest().getAttribute("Resultado_CargoM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_CargoM").toString());
                pageContext.getRequest().removeAttribute("Resultado_CargoM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente el cargo.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_Cargo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Cargo").toString());
                pageContext.getRequest().removeAttribute("Resultado_Cargo");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha registrado correctamente el cargo.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_CargoE") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_CargoE").toString());
                int estado = Integer.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                pageContext.getRequest().removeAttribute("Resultado_CargoE");
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El cargo se ha activado correctamente.','success');");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El cargo se ha desactivado correctamente.','success');");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_CargoPer") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_CargoPer").toString());
                pageContext.getRequest().removeAttribute("Resultado_CargoPer");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha asignado los permisos al usuario.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA FORMULARIO">
            if (pageContext.getRequest().getAttribute("Resultado_FormularioE") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_FormularioE").toString());
                int estado = Integer.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                pageContext.getRequest().removeAttribute("Resultado_FormularioE");
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El Campo se ha activado correctamente.','success');");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El campo se ha desactivado correctamente.','success');");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_FormularioM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_FormularioM").toString());
                pageContext.getRequest().removeAttribute("ResuResultado_FormularioMltado_AreaM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente el campo.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_FormularioSelect") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_FormularioSelect").toString());
                pageContext.getRequest().removeAttribute("Resultado_FormularioSelect");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','No se permite el registro.','error');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Por favor seleccione un tipo de campo.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA USUARIOS">
            if (pageContext.getRequest().getAttribute("Resultado_Usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Usuario").toString());
                pageContext.getRequest().removeAttribute("Resultado_Usuario");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha registrado correctamente el usuario.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_UsuarioM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_UsuarioM").toString());
                pageContext.getRequest().removeAttribute("Resultado_UsuarioM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente el usuario.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_UsuarioE") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_UsuarioE").toString());
                int estado = Integer.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                pageContext.getRequest().removeAttribute("Resultado_UsuarioE");
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El usuario se ha activado correctamente.','success');");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','El usuario se ha desactivado correctamente.','success');");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA NOTAS">
            if (pageContext.getRequest().getAttribute("ResultadoNota") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ResultadoNota").toString());
                pageContext.getRequest().removeAttribute("ResultadoNota");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha registrado correctamente la nota.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("ResultadoNotaM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ResultadoNotaM").toString());
                pageContext.getRequest().removeAttribute("ResultadoNotaM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente la nota.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ResultadoNotaEn") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ResultadoNotaEn").toString());
                pageContext.getRequest().removeAttribute("ResultadoNotaEn");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se envio correctamente el correo.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA ACTIVIDAD">
            if (pageContext.getRequest().getAttribute("Resultado_Actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Actividad").toString());
                pageContext.getRequest().removeAttribute("Resultado_Actividad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha registrado correctamente la actividad.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Resultado_MActividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_MActividad").toString());
                pageContext.getRequest().removeAttribute("Resultado_MActividad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha modificado correctamente la actividad.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Resultado_FActividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_FActividad").toString());
                int cier = Integer.parseInt(pageContext.getRequest().getAttribute("cier").toString()); // SE OBTIENE EL CIERRE Y SE EVALUA EL MENSAJE A ENVIAR
                pageContext.getRequest().removeAttribute("Resultado_FActividad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    if (cier == 0) {
                        out.print("swal('Exito','Se abrio correctamente la actividad.','success');");
                    } else {
                        out.print("swal('Exito','Se finalizo correctamente la actividad.','success');");
                    }
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_RActividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_RActividad").toString());
                pageContext.getRequest().removeAttribute("Resultado_RActividad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Reviso correctamente la actividad.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA UBICACION">
            if (pageContext.getRequest().getAttribute("Resultado_Ubicacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Ubicacion").toString());
                pageContext.getRequest().removeAttribute("Resultado_Ubicacion");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se registro correctamente la ubicación.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Resultado_UbicacionM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_UbicacionM").toString());
                pageContext.getRequest().removeAttribute("Resultado_UbicacionM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se modifico correctamente la ubicación.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_UbicacionE") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_UbicacionE").toString());
                int estado = Integer.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                pageContext.getRequest().removeAttribute("Resultado_UbicacionE");
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','La ubicación se ha activado correctamente.','success');");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','La ubicación se ha desactivado correctamente.','success');");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA MAQUINA">
            if (pageContext.getRequest().getAttribute("Resultado_Maquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Maquina").toString());
                pageContext.getRequest().removeAttribute("Resultado_Maquina");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se registro correctamente la maquina.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_MaquinaM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_MaquinaM").toString());
                pageContext.getRequest().removeAttribute("Resultado_MaquinaM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se modifico correctamente la maquina.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_MaquinaE") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_MaquinaE").toString());
                int estado = Integer.valueOf(pageContext.getRequest().getAttribute("estado").toString());
                pageContext.getRequest().removeAttribute("Resultado_MaquinaE");
                if (resultado) {
                    if (estado == 1) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','La maquina se ha activado correctamente.','success');");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal('Exito','La maquina se ha desactivado correctamente.','success');");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA NOVEDAD">
            if (pageContext.getRequest().getAttribute("Resultado_Novedad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Novedad").toString());
                pageContext.getRequest().removeAttribute("Resultado_Novedad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se registro correctamente la novedad.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_NovedadM") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_NovedadM").toString());
                pageContext.getRequest().removeAttribute("Resultado_NovedadM");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se modifico correctamente la novedad.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ecurrio un error en el registro por favor comunicarse con el administrador.','error');");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ALERTA REGISTRO MTF">
            if (pageContext.getRequest().getAttribute("Resultado_Firma") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Firma").toString());
                pageContext.getRequest().removeAttribute("Resultado_Firma");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha firmado correctamente.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error no se encontro el usuario del area de calidad.','error');");
                    out.print("</script>");
                }
            }

            if (this.pageContext.getRequest().getAttribute("Alerta") != null) {
                if (this.pageContext.getRequest().getAttribute("Alerta").toString().equals("Error_firma")) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','El usuario firmante no es del area de calidad.','error');");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Resultado_Registro_Mtf011") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Registro_Mtf011").toString());
                pageContext.getRequest().removeAttribute("Resultado_Registro_Mtf011");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exito','Se ha creado correctamente el Registro.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error, no se creo el Registro.','error');");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Resultado_Modificar_Mtf011") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Resultado_Modificar_Mtf011").toString());
                pageContext.getRequest().removeAttribute("Resultado_Modificar_Mtf011");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Exíto','Se ha modificado correctamente el registro.','success');");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal('Error','Ocurrio un error, no se hizo modificacion.','error');");
                    out.print("</script>");
                }
            }
            //</editor-fold>

        } catch (IOException ex) {
            Logger.getLogger(Tag_resultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
