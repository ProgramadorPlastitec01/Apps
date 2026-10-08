package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_menu extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (pageContext.getSession().getAttribute("Menu") != null) {
                int menu = Integer.parseInt(pageContext.getSession().getAttribute("Id_usuario").toString());
                String nombre_usuario = pageContext.getSession().getAttribute("Nombres").toString();
                String nombre_rol = pageContext.getSession().getAttribute("Nombre_rol").toString();
                int cambio_contrasena = 0;
                try {
                    cambio_contrasena = Integer.parseInt(pageContext.getRequest().getAttribute("cambio_contrasena").toString());
                } catch (Exception ex) {
                    cambio_contrasena = 0;
                }

                //<editor-fold defaultstate="collapsed" desc="Formulario Cambio de contraseña">
                if (cambio_contrasena != 0) {
                    out.print("<div class='sweet-local' id='Control_pet' style='opacity: 1.03; display: block; margin:auto'>");
                    out.print("<fieldset class='popup_local' style='position:absolute;left:22%;top:80px;width:45%;'>");
//                    out.print("<a href='Sesion?opc=2' style='float:right'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='edit' style='width:22px;height:22px;' title='Cerra modulo de registro' /></a>");

                    out.print("<b>Es Tiempo de cambiar contraseña</b>");
                    out.print("<p style='color:#03899C'>Recordar que la protección de datos, usuario y contraseña, ayuda a evitar fraudes o alteraciones en la Organización (Platitec S.A) y en este Aplicativo.</p>");

                    out.print("<form action='Sesion?opc=7' method='post'>");

                    out.print("<input type='password' id='pass-input'  placeholder='Nueva Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px' >&nbsp;&nbsp;&nbsp;");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('pass-input');");
                    out.print("validatedObj.add(Validate.Presence);");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Password_1);");
                    out.print("</script>");

                    out.print("<input type='password' id='confpass-input' name='ccontrasena' placeholder='Confirmar Contraseña' style='border-bottom: solid 1px gray; border-left: none;border-right: none;border-top: none;position:relative;top:2px'>");
                    out.print("<script>");
                    out.print("var validatedObj = new LiveValidation('confpass-input');");
                    out.print("validatedObj.add(Validate.Password);");
                    out.print("validatedObj.add(Validate.Password_1);");
                    out.print("validatedObj.add(Validate.Confirmation, { match: 'pass-input' });");
                    out.print("</script>");

                    out.print("<input type='hidden' value='" + menu + "' name='id_usuario'>");
                    out.print("<div class='Ayuda'>");
                    out.print("<div class='label_info'><label style='color:#008063'>El cambio de Contraseña debe contener minimo 6 caracteres incluyendo numeros y letras</label></div>");
                    out.print("</div>");

                    out.print("<br><input type='submit'>");

                    out.print("</form>");

                    out.print("</fieldset>");
                    out.print("</div>");
                }

//</editor-fold>
                out.print("<div id='templatemo_header'>");
                out.print("<table style='width:100%;font-size:20px;margin-top:20px;'>");
                out.print("<tr>");
                out.print("<td rowspan='2'><b style=\"margin-left:20px;font-size:25px;color:#10ac84\">Control </b><b style=\"font-size:25px;color:#34495e;\">Pruebas</b></td>");
                out.print("<td style='font-size:14px'>CONTROL PRUEBAS PRODUCCIÓN FARMACEUTICA</td>");
                out.print("<td rowspan='2' style='font-size:10px;text-align:right' width='50%'><span onclick='CerrarSesion();' class='fa fa-running fa-size_normal' title='Salir de control pruebas'></span></td>");
//                out.print("<td rowspan='2' style='font-size:10px;text-align:right' width='50%'><div style='float:right'><span style=\\\"color:#10ac84;font-size: 12em;\" class='fa fa-clipboard-check'></span></div></td>");
                out.print("</tr>");
                out.print("<tr>");
                out.print("<td style='font-size:14px'><b class='negro'>" + nombre_rol + "/</b><b>" + nombre_usuario.toUpperCase() + "</b></td>");
                out.print("</tr>");
                out.print("</table>");
//                //out.print("<div style='float:right'><img src='Interfaz/Contenido/images/Control_pruebas.png' alt='logo' width='75px' height='100px' /></div>");
//                out.print("<div style='float:right'><span style=\"color:#10ac84;font-size: 12em;\" class='fa fa-clipboard-check'></span></div>");
//                out.print("<div><h1><a href='#' onclick='CerrarSesion();' ><b>" + nombre_rol + "/</b><b class='negro'>" + nombre_usuario.toString().toUpperCase() + "</b></a></h1></div>");
                out.print("</div>");
                out.print("<div id='templatemo_menu' class='ddsmoothmenu'>");
                out.print("<ul>");
                if (nombre_rol.equals("Administrador")) {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Usuario?opc=1&fto='>Usuarios</a></li>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4'>Pruebas</a></li>");
                    out.print("<li><a href='Complemento?opc=7'>Proceso</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Registro?opc=1'>Turno</a></li>");
                    out.print("<li><a href='Reporte?opc=1'>Reporte</a></li>");
                } else {
                    out.print("<li><a href='Sesion?opc=2'>Inicio</a></li>");
                    out.print("<li><a href='#'>Complementos</a>");
                    out.print("<ul>");
                    out.print("<li><a href='Complemento?opc=1'>Líneas</a></li>");
                    out.print("<li><a href='Complemento?opc=4'>Pruebas</a></li>");
                    out.print("<li><a href='Complemento?opc=7'>Proceso</a></li>");
                    out.print("</ul>");
                    out.print("</li>");
                    out.print("<li><a href='Registro?opc=1'>Turno</a></li>");
                    out.print("<li><a href='Reporte?opc=1'>Reporte</a></li>");
                }
                out.print("</ul>");
                out.print("<br style='clear: left' />");
                out.print("</div>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
