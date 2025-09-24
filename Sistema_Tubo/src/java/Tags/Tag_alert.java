package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_alert extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="PASSWORD CHANGE">
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
                out.print("<p style='text-align: center;'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec) y en este Aplicativo.</p>");
                out.print("<div style='width:100%' class='camb_body'>");
                out.print("<form action='Login?opc=2' method='post'>");
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
            //<editor-fold defaultstate="collapsed" desc="SESION">
            if (pageContext.getRequest().getAttribute("welcome") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("welcome").toString());
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-2\").ready(function() {\n"
                        + "  iziToast.success({\n"
                        + "    title: 'Bienvenido!',\n"
                        + "    message: 'Sistema de Tubo',\n"
                        + "    position: 'bottomRight'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("getBack") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("getBack").toString());
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
            if (pageContext.getRequest().getAttribute("Usuario_no_existe") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Usuario_no_existe").toString());
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-4\").ready(function() {\n"
                        + "  iziToast.error({\n"
                        + "    title: 'Error',\n"
                        + "    message: 'El usuario ingresado no se encuentra registrado.',\n"
                        + "    position: 'bottomRight'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("Usuario_desactivado") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Usuario_desactivado").toString());
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-4\").ready(function() {\n"
                        + "  iziToast.error({\n"
                        + "    title: 'Error',\n"
                        + "    message: 'El usuario " + var + " se encuentra desactivado.',\n"
                        + "    position: 'bottomRight'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }

            if (pageContext.getRequest().getAttribute("password_actualizada") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("password_actualizada").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la contraseña.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problmea en la actualizacion.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Error_app") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Error_app").toString());
                if (result) {
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
            //<editor-fold defaultstate="collapsed" desc="USER">
            if (pageContext.getRequest().getAttribute("User_register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("User_register").toString());
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
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("User_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("User_update").toString());
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
            if (pageContext.getRequest().getAttribute("User_ChangeStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("User_ChangeStatus").toString());
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
            if (pageContext.getRequest().getAttribute("User_ResetPassw") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("User_ResetPassw").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'La contraseña del usuario ha sido reestablecida al año actual.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualzair la contraseña.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ROLE">
            if (pageContext.getRequest().getAttribute("Role_register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Role_register").toString());
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
            if (pageContext.getRequest().getAttribute("Role_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Role_update").toString());
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
            if (pageContext.getRequest().getAttribute("Role_ChangeStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Role_ChangeStatus").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-3\").ready(function() {\n"
                            + "  iziToast.warning({\n"
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
            if (pageContext.getRequest().getAttribute("Role_UpdatePermission") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Role_UpdatePermission").toString());
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
            //<editor-fold defaultstate="collapsed" desc="TEMPLATE">
            if (pageContext.getRequest().getAttribute("Template_register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Template_register").toString());
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
            if (pageContext.getRequest().getAttribute("Template_estateChange") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Template_estateChange").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha realizado el cambio de estado correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Template_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Template_update").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la informacion correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Template_estateformatter") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Template_estateformatter").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el formato html correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="PERMISSION">
            if (pageContext.getRequest().getAttribute("PermissionChangeEstate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PermissionChangeEstate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha cambiado correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("PermissionRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PermissionRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha realizado el cambio de estado correctamente.',\n"
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
                            + "    message: 'Se ha realizado el cambio de estado correctamente.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="PARAMETER">
            if (pageContext.getRequest().getAttribute("Parameter_register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Parameter_register").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado el parametro correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Parameter_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Parameter_update").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la informacion del parametro correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Parameter_status") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Parameter_status").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha realizado el cambio de estado correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("TableParamData_no") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("TableParamData_no").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al consultar la tabla de parametros.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="LINE">
            if (pageContext.getRequest().getAttribute("Line_register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Line_register").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado una nueva linea.',\n"
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
            if (pageContext.getRequest().getAttribute("Line_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Line_update").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modificado la linea.',\n"
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
            if (pageContext.getRequest().getAttribute("Line_ChangeStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Line_ChangeStatus").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-3\").ready(function() {\n"
                            + "  iziToast.warning({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'El estado de la linea ha sido cambiado.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="DATA SHEET">
            if (pageContext.getRequest().getAttribute("Data_Sheet_register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Data_Sheet_register").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado una nueva Ficha Tecnica.',\n"
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
            if (pageContext.getRequest().getAttribute("Data_Sheet_modify") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Data_Sheet_modify").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modificado la Ficha Tecnica.',\n"
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
            if (pageContext.getRequest().getAttribute("Data_Sheet_update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Data_Sheet_update").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la Ficha Tecnica.',\n"
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
            if (pageContext.getRequest().getAttribute("Data_Sheet_Change_State") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Data_Sheet_Change_State").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha modifica el estado de la Ficha Tecnica.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="PRODUCTION ORDER">
            if (pageContext.getRequest().getAttribute("registerOrder") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("registerOrder").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado una nueva orden.',\n"
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
            if (pageContext.getRequest().getAttribute("UdateOrder") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UdateOrder").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la informacion de la orden.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la actualizacion.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Order_StatuChange") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Order_StatuChange").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'El estado de la orden ha sido cambiado.',\n"
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
            if (pageContext.getRequest().getAttribute("OrderValidationState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("OrderValidationState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.info({\n"
                            + "    title: 'Atención',\n"
                            + "    message: 'No se puede cerrar la orden debido que contiene registros abiertos.',\n"
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
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="RECORD">
            if (pageContext.getRequest().getAttribute("Record_Register") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Record_Register").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se registro correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Record_Update") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Record_Update").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se modifico correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Update_Serial") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Update_Serial").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se registro serial correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("Signature_responsible") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Signature_responsible").toString());
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
                }
            }
            if (pageContext.getRequest().getAttribute("Signature_not_permissions") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Signature_not_permissions").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.warning({\n"
                            + "    title: 'Alerta',\n"
                            + "    message: 'El usuario no tiene permisos para firmar el despeje.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("Update_state_Clearance") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Update_state_Clearance").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se libero correctamente el registro de despeje.',\n"
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
            if (pageContext.getRequest().getAttribute("TemplateSave") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("TemplateSave").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se guardo correctamente el registro de despeje.',\n"
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
            if (pageContext.getRequest().getAttribute("Update_State_Signature_Record") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("Update_State_Signature_Record").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se cambio estado y se asigno responsables correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("RegisterFootage") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterFootage").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se registro la verificacion de metraje correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("UpdateFootage") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdateFootage").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se modifico la verificación de metraje correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("ValidationBatchShift") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ValidationBatchShift").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.info({\n"
                            + "    title: 'Atencion',\n"
                            + "    message: 'No se permite registrar, debido a que existe un registro con el mismo lote, turno y linea.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, favor comunicarce con el area de T.I.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("ValidationRollReserved") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ValidationRollReserved").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.info({\n"
                            + "    title: 'Atencion',\n"
                            + "    message: 'No se permite registrar, debido a que los rollos indicados ya se encuentran reservados.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, favor comunicarce con el area de T.I.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("MotionRollAssigned") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("MotionRollAssigned").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se realiza correctamente los movimientos de Rollos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema, favor comunicarce con el area de T.I.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ROLLO">
            if (pageContext.getRequest().getAttribute("RegisterRoll") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterRoll").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo rollo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'No se ha registrado el rollo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("PressureRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PressureRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado los valores de presión.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'No se ha registrado el valor.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("UpdateRoll") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdateRoll").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el rollo.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la actualizacion.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("CleanRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("CleanRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado correctamente la limpieza.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="PRESSURE && WEIGHT">

            if (pageContext.getRequest().getAttribute("RegisterPressure") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterPressure").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado correctamente los datos.',\n"
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
            if (pageContext.getRequest().getAttribute("SinRollo") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SinRollo").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado correctamente los datos.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'No se ha seleccionado ningun rollo',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ROLLO HISTORY">
            if (pageContext.getRequest().getAttribute("RegisterHistroy") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterHistroy").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha cambiado el estado del rollo.',\n"
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
            if (pageContext.getRequest().getAttribute("RegisterRoll_event") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterRoll_event").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-3\").ready(function() {\n"
                            + "  iziToast.warning({\n"
                            + "    title: 'Atencion',\n"
                            + "    message: 'El rollo se ha registrado en cuarentena.',\n"
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
            if (pageContext.getRequest().getAttribute("RegisterReplace") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterReplace").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Atencion',\n"
                            + "    message: 'Se ha registrado un rollo de reemplazo.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="CONTROL DIAMETRO BOBINA">
            if (pageContext.getRequest().getAttribute("RegisterControl") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterControl").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo control.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="REGISTRO GC 40">
            if (pageContext.getRequest().getAttribute("registerGeneration") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("registerGeneration").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha generado correctamente el registro.',\n"
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
            //<editor-fold defaultstate="collapsed" desc="SUMMARY">
            if (pageContext.getRequest().getAttribute("SummaryRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("SummaryRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado el resumen correctamente.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro del resumen.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("UpdateSummary") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UpdateSummary").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha completado el resumen correctamente.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro del resumen.',\n"
                            + "    position: 'bottomRight'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ROLL EVENTS">
            if (pageContext.getRequest().getAttribute("AprovedRollEvents") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AprovedRollEvents").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha aprobado el rollo correctamente.',\n"
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
            if (pageContext.getRequest().getAttribute("RefusedRollEvents") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RefusedRollEvents").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha rechazo el rollo correctamente.',\n"
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
        //<editor-fold defaultstate="collapsed" desc="NOZZLE">
        if (pageContext.getRequest().getAttribute("RegisterNozzle") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RegisterNozzle").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado el control correctamente.',\n"
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
            Logger.getLogger(Tag_alert.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
