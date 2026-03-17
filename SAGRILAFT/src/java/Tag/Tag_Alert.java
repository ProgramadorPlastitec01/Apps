package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_Alert extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="SESSION MODULE">
            if (pageContext.getRequest().getAttribute("welcome") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("welcome").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Bienvenid@',\n"
                            + "    message: 'Has iniciado sesion correctamente.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("getBack") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("getBack").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Adios',\n"
                            + "    message: 'Has finalizado session correctamente.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Usuario_no_existe") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Usuario_no_existe").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'El usuario no existe o las credenciales son incorrectas.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

            if (pageContext.getRequest().getAttribute("Usuario_bloqueado") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Usuario_bloqueado").toString());
                String expired = "";
                try {
                    expired = pageContext.getRequest().getAttribute("Tiempo_bloqueo").toString();
                } catch (Exception e) {
                    expired = "";
                }
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Usuario bloqueado temporalmente. Intenta más tarde. " + expired + "',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("password_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("password_update").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado la nueva contraseña.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, comunicarse al area encargada.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Cambio_contraseña") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Cambio_contraseña").toString());
                String id_usuario = "";
                try {
                    id_usuario = pageContext.getRequest().getAttribute("idUsuario").toString();
                } catch (Exception e) {
                    id_usuario = "";
                }
                out.print("<link rel=\"stylesheet\" href=\"Interfaz/Contenido/Validacion/StyleSheetLiveValidation.css\">");
                out.print("<script src=\"Interfaz/Contenido/Validacion/LiveValidation.js\"></script>");
                out.print("<div class='sweet-local' style='opacity: 1.03; display: flex; margin:auto;'>");
                out.print("<fieldset class='cont_reg'>");
                out.print("<div>");

                out.print("<div class='form_pass' style='justify-content: space-between;'>");

                out.print("<div>");
                out.print("<h2>Cambiar Contraseña </h2>");
                out.print("</div>");
                out.print("<div>");
                out.print("<a href='index.jsp' class='btn btn-outline-secondary' style='height: 31px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></a>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div>");
                out.print("</div>");
                out.print("<p style='text-align: center;'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Plastitec) y en este Aplicativo.</p>");
                out.print("<div style='width:100%' class='camb_body'>");
                out.print("<form action='Login?opt=2' method='post'>");
                out.print("<div class='form_pass'>");
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
                out.print("<div style='display: flex; width: 100%; margin-top: 15px;'>");
                out.print("<div style='width: 72%;margin-left: 3%; text-align: initial;'>");
                out.print("<label style='color:#00281b'>El cambio de Contraseña debe contener:<br />"
                        + "                        -Minimo 8 caracteres<br/>"
                        + "                        -Maximo 15 caracteres<br/>"
                        + "                        -Al menos una letra mayúscula<br/>"
                        + "                        -Al menos una letra minúscula<br/>"
                        + "                        -Al menos un dígito ( Numero )<br/>"
                        + "                        -No espacios en blanco<br/>"
                        + "                        -Al menos 1 caracter especial ( $@$!%*?&#- )</label>");
                out.print("</div>");
                out.print("<div style='float:right;'><img src='Interfaz/Contenido/Imagen/spy.gif' alt='Logo' width='200' height='150' style='margin-right: 40px;' /></div>");
                out.print("</div>");
                out.print("<div style='text-align:center;'>");
                out.print("<button class='btn' style=\"box-shadow: 1px 2px 5px 0px #959595;\">Cambiar</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div>");
                out.print("</div>");
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MODULE USER">
            if (pageContext.getRequest().getAttribute("UserRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo usuario.',\n"
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
                            + "    message: 'Se ha actualizado la información.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la actualización.',\n"
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
                            + "  iziToast.warning({\n"
                            + "    title: 'Atencion',\n"
                            + "    message: 'Se ha cambiado estado del usuario.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la actualización.',\n"
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
                            + "    title: 'Atencion',\n"
                            + "    message: 'Se ha reestablecido la contraseña del usuario.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la actualización.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MODULE PERMISSION">
            if (pageContext.getRequest().getAttribute("PermissionRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PermissionRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo permiso.',\n"
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
            if (pageContext.getRequest().getAttribute("PermissionUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PermissionUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modificado el permiso.',\n"
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
            if (pageContext.getRequest().getAttribute("PermissionUpdateState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PermissionUpdateState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha cambiado el estado correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="MODULE CIIU">
            if (pageContext.getRequest().getAttribute("CIIURegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CIIURegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado nuevo codigo CIIU.',\n"
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
            if (pageContext.getRequest().getAttribute("CIIUUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CIIUUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modificado correctamente el código.',\n"
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
            if (pageContext.getRequest().getAttribute("CIIUUpdateState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CIIUUpdateState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha cambiado el estado correctamente.',\n"
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
                            + "    message: 'Se ha registrado nuevo Rol.',\n"
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
                            + "    message: 'Se ha modificado el rol correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("RoleUpdateState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleUpdateState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha cambiado el estado del rol correctamente.',\n"
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
                            + "    message: 'Se ha registrado los permisos correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="SETTING">
            if (pageContext.getRequest().getAttribute("SettingRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SettingRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado una nueva configuración.',\n"
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
            if (pageContext.getRequest().getAttribute("SettingUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SettingUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modificado la configuración.',\n"
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
            if (pageContext.getRequest().getAttribute("SettingUpdateState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SettingUpdateState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="MODULE DOCUMENT">
            if (pageContext.getRequest().getAttribute("DocRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DocRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo documento.',\n"
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
            if (pageContext.getRequest().getAttribute("DocUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DocUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado un documento.',\n"
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
            if (pageContext.getRequest().getAttribute("UpdateFormClient") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdateFormClient").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Guardado / Saved.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Error.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("StateDocument") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("StateDocument").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha finalizado el proceso correctamente.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el cambio de estado.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("DocumentStateBack") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DocumentStateBack").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.info({\n"
                            + "    title: 'Atencion!',\n"
                            + "    message: 'Se ha cambiado el modulo, recuerde volver al ultimo modulo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el cambio de estado.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ReturnDocument") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ReturnDocument").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto!',\n"
                            + "    message: 'Se ha devuelto el documento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al devolver el documento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RegisterSegmentation") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterSegmentation").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto!',\n"
                            + "    message: 'El documento ha sido anexado a la segmentación.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("DocumentAprove") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DocumentAprove").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto!',\n"
                            + "    message: 'El documento ha sido aprobado.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ResendClientCredencials") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ResendClientCredencials").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto!',\n"
                            + "    message: 'Se ha enviado recordatorio del usuario al cliente.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al enviar el correo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ConcludeDocument") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ConcludeDocument").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto!',\n"
                            + "    message: 'Se ha concluido el documento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al concluir el documento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("DocRenew") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("DocRenew").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto!',\n"
                            + "    message: 'Se ha renovado el documento',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al renovar el documento.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MODULE SEGMENTATION">
            if (pageContext.getRequest().getAttribute("SegmentationRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SegmentationRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado control OEA.',\n"
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
            if (pageContext.getRequest().getAttribute("SegmentationRegisterINT") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SegmentationRegisterINT").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado control OEA y nivel de riesgo del pais.',\n"
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
            if (pageContext.getRequest().getAttribute("SegmentationUpdateState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SegmentationUpdateState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="SECTION VISIT">
            if (pageContext.getRequest().getAttribute("VisitRegistrer") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("VisitRegistrer").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se registro correctamente la visita.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("VisitUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("VisitUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se modifico correctamente la visita.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_Alert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
