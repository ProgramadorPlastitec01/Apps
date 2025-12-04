package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Alert extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="CERTIFICATE">
            //<editor-fold defaultstate="collapsed" desc="UPDATE CERTIFICATE">
            if (pageContext.getRequest().getAttribute("UpdateCertificate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdateCertificate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el certificado correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="REGISTER CERTIFICATE">
            if (pageContext.getRequest().getAttribute("RegisterCertificates") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterCertificates").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado el certificado correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="REGISTER SIGNATURE">
            if (pageContext.getRequest().getAttribute("RegisterSignature") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterSignature").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se registro la firma correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="DELETE CERTIFICATE">
            if (pageContext.getRequest().getAttribute("DeleteCertificates") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DeleteCertificates").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.warning({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se elimino el certificado correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="GENERATE CERTIFICATE">
            if (pageContext.getRequest().getAttribute("GenerateCertificate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("GenerateCertificate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se genero el certificado correctamente.',\n"
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
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="SESSION">
            //<editor-fold defaultstate="collapsed" desc="UPDATE PASSWORD">
            if (pageContext.getRequest().getAttribute("Update_password") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Update_password").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se actualizo la contraseña correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="MAIL RESTE PASS">
            if (pageContext.getRequest().getAttribute("Mail_Reset_Pass") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Mail_Reset_Pass").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se acaba de enviar un correo notificando el cambio de contraseña.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="UNIDENTIFIED_USER">
            if (pageContext.getRequest().getAttribute("Unidentified_User") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Unidentified_User").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.warning({\n"
                            + "    title: 'Alerta',\n"
                            + "    message: 'Los datos ingresados no coinciden con un usuario del sistema, revisar nuevamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="NON EXISTENT USER">
            if (pageContext.getRequest().getAttribute("Non_existent_user") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Non_existent_user").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Alerta',\n"
                            + "    message: 'El usuario ingresado no existen, favor verifique usuario y contraseña.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="DEACTIVIDADES USER">
            if (pageContext.getRequest().getAttribute("Deactivaded_user") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Deactivaded_user").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.info({\n"
                            + "    title: 'Alerta',\n"
                            + "    message: 'El usuario ingresado no se encuentra desactivado.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="PASSWORD CHANGE">
            if (pageContext.getRequest().getAttribute("Change_Password") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Change_Password").toString());
                String IdUser = "";
                try {
                    IdUser = pageContext.getRequest().getAttribute("IdUser").toString();
                } catch (Exception e) {
                    IdUser = "";
                }
                out.print("<div class='sweet-local' style='opacity: 1.03; display: flex; margin:auto;'>");
                out.print("<link rel='stylesheet' href='Interface/Content/Validation/StyleSheetLiveValidation.css'>");
                out.print("<script src='Interface/Content/Validation/LiveValidation.js'></script>");

                out.print("<div class='reset-pass-container'>");
                out.print("    <fieldset class='cont-pass'>");

                out.print("        <!-- Header -->");
                out.print("        <div class='header_reset'>");
                out.print("            <h2>Cambiar Contraseña</h2>");
                out.print("            <a href='index.jsp' class='btn-green BtnSt'><i class='fas fa-times i_bnt'></i></a>");
                out.print("        </div>");

                out.print("        <!-- Texto informativo -->");
                out.print("        <div><p class='info-text'>");
                out.print("            Proteger tus datos ayuda a evitar fraudes o alteraciones en PLASTITEC y en este Aplicativo.");
                out.print("        </p></div>");

                out.print("        <!-- Formulario -->");
                out.print("        <form action='Session?opt=2' method='post' autocomplete='off'>");
                out.print("            <input type='hidden' name='IdUser' id='IdUser' value='" + IdUser + "'>");
                out.print("            <div class='inputs-container'>");
                out.print("                <input class='form-control' type='password' name='Txt_password_new' id='pass-input' placeholder='Nueva Contraseña' autocomplete='new-password' readonly>");
                out.print("    <div id='pass-msg' class='validation-msg'></div>"); // Mensaje de contraseña

                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('pass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Presence);");
                out.print("</script>");
                out.print("                <input class='form-control' type='password' name='Txt_password' id='confpass-input' placeholder='Confirmar Contraseña' autocomplete='new-password' readonly>");
                out.print("<script>");
                out.print("var validatedObj = new LiveValidation('confpass-input');");
                out.print("validatedObj.add(Validate.Password);");
                out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                out.print("</script>");
                out.print("            </div>");

                out.print("            <script>");
                out.print("                document.addEventListener('DOMContentLoaded', function() {");
                out.print("                    var passInput = document.getElementById('pass-input');");
                out.print("                    var confPassInput = document.getElementById('confpass-input');");
                out.print("                    if (passInput && confPassInput) {");
                out.print("                        passInput.removeAttribute('readonly');");
                out.print("                        confPassInput.removeAttribute('readonly');");
                out.print("                        console.log('Campos habilitados correctamente');");

                out.print("                        var validatedObj = new LiveValidation('pass-input');");
                out.print("                        validatedObj.add(Validate.Password);");
                out.print("                        validatedObj.add(Validate.Presence);");
                out.print("  validatedPass.onValid = function(){ passMsg.innerHTML='<span style=\"color:green;\">Contraseña válida</span>'; };");
                out.print("  validatedPass.onInvalid = function(){ passMsg.innerHTML='<span style=\"color:red;\">'+validatedPass.getMessage()+'</span>'; };");

                out.print("                        var validatedObj2 = new LiveValidation('confpass-input');");
                out.print("                        validatedObj2.add(Validate.Password);");
                out.print("                        validatedObj2.add(Validate.Confirmation, { match: 'pass-input' });");
                out.print("                        console.log('Validaciones inicializadas');");
                out.print("                    } else {");
                out.print("                        console.error('No se encontraron los campos de contraseña');");
                out.print("                    }");
                out.print("                });");
                out.print("            </script>");

                out.print("            <div class='requirements-logo'>");
                out.print("                <div class='requirements'>");
                out.print("                    <strong>Requisitos de la contraseña:</strong><br>");
                out.print("                    - 8 a 15 caracteres<br>");
                out.print("                    - Al menos una letra mayúscula y una minúscula<br>");
                out.print("                    - Al menos un número<br>");
                out.print("                    - Al menos un carácter especial ($@!%*?&#-)<br>");
                out.print("                    - No usar espacios en blanco");
                out.print("                </div>");
                out.print("                <div class='logo'>");
                out.print("                    <img src='Interface/Imagen/Espia3.gif' alt='Logo'>");
                out.print("                </div>");
                out.print("            </div>");

                out.print("            <div class='submit-btn'>");
                out.print("                <button type='submit'>Cambiar</button>");
                out.print("            </div>");

                out.print("        </form>");
                out.print("    </fieldset>");
                out.print("</div>");

                out.print("</div>");
            }
//</editor-fold>
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="PERMISSION">
            if (pageContext.getRequest().getAttribute("RegisterPermission") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterPermission").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se registrado el permiso correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("UpdatePermission") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdatePermission").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se modificar el permiso correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("UpdatePermissionState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdatePermissionState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="ROLE">
            if (pageContext.getRequest().getAttribute("RoleRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("RoleUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado la información correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("RoleChangeState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleChangeState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-3\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'El estado del rol ha sido cambiado.',\n"
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
            if (pageContext.getRequest().getAttribute("RoleUpdatePermission") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleUpdatePermission").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se han asignado los permisos correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="USER">
            if (pageContext.getRequest().getAttribute("UserRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
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
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("UserUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la información del usuario.',\n"
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
            if (pageContext.getRequest().getAttribute("UserState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se realizado cambio de estado al usuario',\n"
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
            if (pageContext.getRequest().getAttribute("UserPass") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserPass").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se restablecido la contraseña del usuario.',\n"
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
        } catch (IOException ex) {
            Logger.getLogger(Alert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
