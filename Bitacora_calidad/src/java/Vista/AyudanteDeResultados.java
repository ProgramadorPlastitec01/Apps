package Vista;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class AyudanteDeResultados extends TagSupport {

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
                    out.print("<fieldset class='popup_local' style='margin-left:25%;width:45%; border-radius:20px'>");
                    out.print("<center><b>Cambiar Contraseña</b></center>");
                    out.print("<p style='color:#03899C'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");
                    out.print("<form action='Ingreso?opc=2' method='post'>");
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
            //<editor-fold defaultstate="collapsed" desc="Alerta_Finalizado_actividad">
            if (pageContext.getRequest().getAttribute("estado_actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estado_actividad").toString());
                String estado = pageContext.getRequest().getAttribute("estado").toString();
                if (resultado) {
                    if (estado.equals("a")) {
                        out.print("<script type='text/javascript'>");
                        out.print("swal(\"Correcto!\",\"Se ha abierto la actividad!\",\"success\");");
                        out.print("</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("swal(\"Correcto!\",\"Se ha finalizado la actividad!\",\"success\");");
                        out.print("</script>");
                    }
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"No se finalizo la actividad!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Actualizar_actividad">        
            if (pageContext.getRequest().getAttribute("resultado_modifica_actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_modifica_actividad").toString());
                pageContext.getRequest().removeAttribute("resultado_modifica_actividad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Se actualizó correctamente su actividad');");
                    out.print("swal(\"Correcto!\",\"Se ha actualizo la actividad!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"No se actualizo la actividad!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Registro_actividad">        
            if (pageContext.getRequest().getAttribute("resultado_inserta_actividad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_inserta_actividad").toString());
                pageContext.getRequest().removeAttribute("resultado_inserta_actividad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Correcto!\",\"Se ha registrado la actividad!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Error!\",\"No se registrado la actividad!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>           
            //<editor-fold defaultstate="collapsed" desc="Alerta_Consulta_actividades">          
            if (pageContext.getRequest().getAttribute("alerta_actividades") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_actividades").toString());
                pageContext.getRequest().removeAttribute("alerta_actividades");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('No se encontro información respecto a la fecha ingresada');");
                    out.print("swal(\"Precausion!\",\"No se encontro información respecto a la fecha ingresada!\",\"warning\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta Revisar Actividad">
            if (pageContext.getRequest().getAttribute("Revisar") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("Revisar").toString());
                pageContext.getRequest().removeAttribute("Revisar");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Correcto!\",\"Se ha Revisado la Actividad!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Error!\",\"No se registrado la ubicacion!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Registro_ubicacion">
            if (pageContext.getRequest().getAttribute("registroUbicacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("registroUbicacion").toString());
                pageContext.getRequest().removeAttribute("registroUbicacion");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('se registro correctamente la ubicacion');");
                    out.print("swal(\"Correcto!\",\"Se ha registrado la ubicacion!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                  out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"No se registrado la ubicacion!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Estado_ubicacion">
            if (pageContext.getRequest().getAttribute("EstadoUbicacion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("EstadoUbicacion").toString());
                pageContext.getRequest().removeAttribute("EstadoUbicacion");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('se actualizo correctamente la ubicacion');");
                    out.print("swal(\"Correcto!\",\"Se actualizo la ubicacion!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"No se actualizo la ubicacion!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Registro_nota">
            if (pageContext.getRequest().getAttribute("ingeso_nota") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ingeso_nota").toString());
                pageContext.getRequest().removeAttribute("ingeso_nota");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print(" alert('Se registro correctamente la nota');");
                    out.print("swal(\"Correcto!\",\"Se ha registrado la nota!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"No se registro la nota!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Actualizacion_nota">
            if (pageContext.getRequest().getAttribute("alerta_modnotas") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_modnotas").toString());
                pageContext.getRequest().removeAttribute("alerta_modnotas");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Se actualizó correctamente la nota');");
                    out.print("swal(\"Correcto!\",\"Se actualizo la nota!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"no se actualizo la nota!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Consulta_vacia">           
            if (pageContext.getRequest().getAttribute("alerta_consultavacia") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_consultavacia").toString());
                pageContext.getRequest().removeAttribute("alerta_consultavacia");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('No se encontro información respecto a la fecha ingresada');");
                    out.print("swal(\"Precausion!\",\"No se encontro información respecto a la fecha ingresada!\",\"warning\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Registro_usuario">
            if (pageContext.getRequest().getAttribute("alerta_usuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_usuario").toString());
                pageContext.getRequest().removeAttribute("alerta_usuario");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Se registro correctamente el usuario');");
                    out.print("swal(\"Correcto!\",\"Se registro el usuario!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"no se registro el usuario!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Actualizar_estado_usuario">           
            if (pageContext.getRequest().getAttribute("alerta_modusuario") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_modusuario").toString());
                pageContext.getRequest().removeAttribute("alerta_modusuario");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Se actualizó correctamente el usuario');");
                    out.print("swal(\"Correcto!\",\"Se actualizó el usuario!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"no se actualizó el usuario!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Consulta_novedad">
            if (pageContext.getRequest().getAttribute("alerta_buscar_novedad") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_buscar_novedad").toString());
                pageContext.getRequest().removeAttribute("alerta_buscar_novedad");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('No se encuentra información');");
                    out.print("swal(\"Correcto!\",\"No se encuentra información!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"El sistema genero error cominique al administrador!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Session_Incorrectos">            
            if (pageContext.getRequest().getAttribute("ingreso_sistema") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("ingreso_sistema").toString());
                pageContext.getRequest().removeAttribute("ingreso_sistema");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Los campos al iniciar la sesión se encuentran vacios.');");
                    out.print("swal(\"Precaucion!\",\"Los campos se encuentran vacios!\",\"warning\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print(" alert('Los datos ingresados son incorrectos.');");
                    out.print("swal(\"Precaucion!\",\"Los datos ingresados son incorrectos!\",\"warning\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Session_inactivo">
            if (pageContext.getRequest().getAttribute("estadoIncactivo") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("estadoIncactivo").toString());
                pageContext.getRequest().removeAttribute("estadoIncactivo");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Los campos al iniciar la sesión se encuentran vacios.');");
                    out.print("swal(\"Precaucion!\",\"Los campos se encuentran vacios!\",\"warning\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print(" alert('El usuario se encuentra  incactivo.');");
                    out.print("swal(\"Precaucion!\",\"El usuario se encuentra  incactivo!\",\"warning\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Modificacion_extrusion">
            if (pageContext.getRequest().getAttribute("resultado_modificacion_extrusion") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("resultado_modificacion_extrusion").toString());
                pageContext.getRequest().removeAttribute("resultado_modificacion_extrusion");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('Se actualizo correctamente su información');");
                    out.print("swal(\"Correcto!\",\"Se ha Actualizado Correctamente!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('El sistema genero error cominique al administrador');");
                    out.print("swal(\"Error!\",\"No se Actualizo Correctamente!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Esta_Maquina">
            if (pageContext.getRequest().getAttribute("alerta_modmaquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_modmaquina").toString());
                pageContext.getRequest().removeAttribute("alerta_modmaquina");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Correcto!\",\"Se actualizo el estado correctamente!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Error!\",\"No se actualizo el estado!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Registar_maquina">
            if (pageContext.getRequest().getAttribute("alerta_maquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_maquina").toString());
                pageContext.getRequest().removeAttribute("alerta_maquina");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Correcto!\",\"Se registro correctamente la maquina!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Error!\",\"El registro ya existe!\",\"error\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Modificar_maquina">
            if (pageContext.getRequest().getAttribute("alerta_maquinaI") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_maquinaI").toString());
                pageContext.getRequest().removeAttribute("alerta_maquinaI");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Correcto!\",\"Se Modifica la maquina!\",\"success\");");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("swal(\"Error!\",\"No se Modifico la maquina!\",\"error\");");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Alerta_Novedad_maquina">           
            if (pageContext.getRequest().getAttribute("alerta_novedad_maquina") != null) {
                boolean resultado = Boolean.valueOf(pageContext.getRequest().getAttribute("alerta_novedad_maquina").toString());
                pageContext.getRequest().removeAttribute("alerta_novedad_maquina");
                if (resultado) {
                    out.print("<script type='text/javascript'>");
//                    out.print("alert('No se ha ingresado información seleccione la maquina');");
                    out.print("swal(\"Precausion!\",\"No se ha ingresado información seleccione la maquina!\",\"warning\");");
                    out.print("</script>");
                }
            }
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(AyudanteDeResultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
