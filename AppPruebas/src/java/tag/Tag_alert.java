package tag;

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
            //<editor-fold defaultstate="collapsed" desc="LOGIN">
            if (pageContext.getRequest().getAttribute("SwitchPass") != null) {
                String id_usuario = "";
                try {
                    id_usuario = pageContext.getRequest().getAttribute("idUser").toString();
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
                out.print("<form action='Login?opt=2' method='post'>");
                out.print("<div class='form_pass'>");
                out.print("<input type='hidden' name='idUser' id='usuario' value='" + id_usuario + "'>");
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
            if (pageContext.getRequest().getAttribute("welcome") != null) {
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-2\").ready(function() {\n"
                        + "  iziToast.success({\n"
                        + "    title: 'Bienvenido!',\n"
                        + "    message: 'Aplicativo de soporte',\n"
                        + "    position: 'topCenter'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("UserNotExist") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserNotExist").toString());
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-4\").ready(function() {\n"
                        + "  iziToast.error({\n"
                        + "    title: 'Error',\n"
                        + "    message: 'El usuario ingresado no se encuentra registrado.',\n"
                        + "    position: 'topCenter'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("UserInactive") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserInactive").toString());
                String var = pageContext.getRequest().getAttribute("var1").toString();
                out.print("<script type='text/javascript'>");
                out.print("$(\"#toastr-4\").ready(function() {\n"
                        + "  iziToast.error({\n"
                        + "    title: 'Error',\n"
                        + "    message: 'El usuario " + var + " se encuentra desactivado.',\n"
                        + "    position: 'topCenter'\n"
                        + "  });\n"
                        + "});");
                out.print("</script>");
            }
            if (pageContext.getRequest().getAttribute("PassUpdated") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("PassUpdated").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la contraseña.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en la actualizacion.',\n"
                            + "    position: 'topCenter'\n"
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
                            + "    message: 'Se ha registrado un nuevo usuario.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RoleRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo rol.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'topCenter'\n"
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
                            + "    message: 'Se ha actualizado el usuario.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar.',\n"
                            + "    position: 'topCenter'\n"
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
                            + "    message: 'Se ha actualizado el rol.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("UserUpdateStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("UserUpdateStatus").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado del usuario.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al cambiar el estado.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("RoleUpdateStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("RoleUpdateStatus").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado del rol.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al cambiar el estado.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="APP">
            if (pageContext.getRequest().getAttribute("AppRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AppRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado una nueva aplicación.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("AppUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AppUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la aplicación.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("AppUpdateStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AppUpdateStatus").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado de la aplicación.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar el estado.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="CASE">
            if (pageContext.getRequest().getAttribute("caseRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("caseRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado un nuevo caso.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("caseUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("caseUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el caso.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("caseUpdateStatus") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("caseUpdateStatus").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado del caso.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Setting advanced">
            if (pageContext.getRequest().getAttribute("AdvSettRegister") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AdvSettRegister").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha registrado nuevas configuraciones.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema en el registro.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("AdvSettUpdate") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AdvSettUpdate").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado la configuración.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
            if (pageContext.getRequest().getAttribute("AdvSettUpdateState") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("AdvSettUpdateState").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha actualizado el estado de la configuración.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'Ha ocurrido un problema al actualizar estado.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                }
            }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="SUPPORT">
if (pageContext.getRequest().getAttribute("ExecuteData") != null) {
                boolean result = Boolean.valueOf(pageContext.getRequest().getAttribute("ExecuteData").toString());
                if (result) {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-2\").ready(function() {\n"
                            + "  iziToast.success({\n"
                            + "    title: 'Correcto',\n"
                            + "    message: 'Se ha ejecutado exitosamente el caso.',\n"
                            + "    position: 'topCenter'\n"
                            + "  });\n"
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("<script type='text/javascript'>");
                    out.print("$(\"#toastr-4\").ready(function() {\n"
                            + "  iziToast.error({\n"
                            + "    title: 'Error',\n"
                            + "    message: 'La ejecucion del caso ha fallado.',\n"
                            + "    position: 'topCenter'\n"
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
