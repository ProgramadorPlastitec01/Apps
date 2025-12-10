package Vista;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.http.HttpSession;

public class AyudanteVistaUsuarios extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            HttpSession sesion = pageContext.getSession();
            String nombre_sesion = sesion.getAttribute("nombre").toString();
            out.print("<div id='sidebar'>");
            //<editor-fold defaultstate="collapsed" desc="1. MODIFICAR">
            if (pageContext.getRequest().getAttribute("unusuario") != null) {
                List usua = (List) pageContext.getRequest().getAttribute("unusuario");
                Object[] obj_MoUsuario = (Object[]) usua.get(0);
                out.print("<h3>Modificar Usuario<a href='Usuarios?l=1'><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' /></a></h3>");
                out.print("<form onsubmit='registroU()' action='Usuarios?l=4&id=" + obj_MoUsuario[0] + "&Mestado=" + obj_MoUsuario[9] + "' method='post' name='form1'>");
                out.print("<input type='hidden' name='Mresponsable' value='" + nombre_sesion + "'/>");
                out.print("<b>Documento:<b>");
                out.print("<input id='validateDoc' type='text' name='Mdocumento' value='" + obj_MoUsuario[3] + "'  placeholder='Documento' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print(" var validation = new LiveValidation('validateDoc')");
                out.print("validation.add( Validate.Presence )");
                out.print("</script>");
                out.print("<b>Nombre:</b>");
                out.print("<input id='validateNom' type='text' name='Mnombre' value='" + obj_MoUsuario[4] + "' class='input_full' onchange='javascript:this.value=this.value.toUpperCase();'/><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateNom');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Apellido:</b>");
                out.print("<input id='validateApl' type='text' name='Mapellido' value='" + obj_MoUsuario[5] + "' class='input_full' onchange='javascript:this.value=this.value.toUpperCase();'/><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateApl');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Rol:</b>");
                out.print("<select name='Mrol' id='lstrol' class='input_full' placeholder='Apellido'>");
                out.print("<option style='display:none;' value='" + obj_MoUsuario[8] + "'>" + obj_MoUsuario[8] + "</opction>");
                out.print("<option value='Directora_calidad'>Directora calidad</option>");
                out.print("<option value='Coordinador_calidad'>Coordinador calidad</option>");
                out.print("<option value='Coordinador_sgc'>Coordinador sgc</option>");
                out.print("<option value='Inspector_calidad'>Inspector  calidad</option>");
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('lstrol');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: ''} );");
                out.print("</script>");
                out.print("<b>Usuario:</b>");
                out.print("<input id='validateUsu' type='text' name='Muser' value='" + obj_MoUsuario[6] + "' class='input_full' onchange='javascript:this.value=this.value.toUpperCase();'/><br />");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateUsu');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='hidden' name='passM' id='passM-id' value='' />");
                out.print("<input type='submit' id='btsubmit' value='Modificar' style='width:187px;'/>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("<br /><br />");
                out.print("<center><a href='#' onclick='contrasenaM()'><b class='naranja'>Restablecer contraseña</b></a></center>");
                out.print("</form>");
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="2. INSERTAR">
            } else {
                out.print("<h3>Registrar usuario</h3>");
                out.print("<form onsubmit='registroU()' action='Usuarios?l=2&estado=1' method='post' name='form1'>");
                out.print("<input type='hidden' name='responsable' value='" + nombre_sesion + "'/>");
                out.print("<b>Documento:<b>");
                out.print("<input class='input_full' id='validateDoc' type='text' name='txtdocumento'  placeholder='Documento' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateDoc');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Nombres:<b>");
                out.print("<input class='input_full' id='validateNom' type='text' name='txtnombreus'  placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateNom');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Apellidos:</b>");
                out.print("<input class='input_full' id='validateApll' type='text' name='txtapel'  placeholder='Apellido' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateApll');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Rol:</b>");
                out.print("<select name='lstrol' id='lstrol' class='input_full' placeholder='Apellido'>");
                out.print("<option style='display:none;'>Seleccione un rol</opction>");
                out.print("<option value='Directora_calidad'>Directora calidad</option>");
                out.print("<option value='Coordinador_calidad'>Coordinador calidad</option>");
                out.print("<option value='Coordinador_sgc'>Coordinador sgc</option>");
                out.print("<option value='Inspector_calidad'>Inspector  calidad</option>");
                out.print("<option value='Administrador'>Administrador</option>");
                out.print("</select>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('lstrol');");
                out.print("validation.add( Validate.Exclusion, { within: ['0'], failureMessage: ''} );");
                out.print("</script>");
                out.print("<b>Usuario:</b>");
                out.print("<input class='input_full' id='validateUsu' type='text' name='txtuser'  placeholder='Usuario' placeholder='Apellido' onchange='javascript:this.value=this.value.toUpperCase();'/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('validateUsu');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Registrar' style='width:187px;'/>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
            }
//</editor-fold>
            out.print("<div class='cleaner'></div></div>");
            //<editor-fold defaultstate="collapsed" desc="3. CONSULTA">
            if (pageContext.getRequest().getAttribute("usuarios") != null) {
                List usuarios = (List) pageContext.getRequest().getAttribute("usuarios");
                out.print("<div id='content'>");
                out.print("<h3>Usuarios registrados<div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Documento</th>");
                out.print("<th>Nombres</th>");
                out.print("<th>Apellidos</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < usuarios.size(); i++) {
                    Object[] obj_ConUsuarios = (Object[]) usuarios.get(i);
                    out.print("<tr>");
                    out.print("<td style='text-transform: uppercase; text-align: justify;'>" + obj_ConUsuarios[3] + "</td>");
                    out.print("<td style='text-transform: uppercase; text-align: justify;'>" + obj_ConUsuarios[4] + "</td>");
                    out.print("<td style='text-transform: uppercase; text-align: justify;'>" + obj_ConUsuarios[5] + "</td>");
                    out.print("<td style='text-transform: uppercase; text-align: justify;'>" + obj_ConUsuarios[6] + "</td>");
                    if (obj_ConUsuarios[8].equals("Directora_calidad")) {
                        out.print("<td style='text-transform: uppercase; text-align: justify;'>Directora de calidad</td>");
                    } else if (obj_ConUsuarios[8].equals("Coordinador_calidad")) {
                        out.print("<td style='text-transform: uppercase; text-align: justify;'>Coordinador de calidad</td>");
                    } else if (obj_ConUsuarios[8].equals("Coordinador_sgc")) {
                        out.print("<td style='text-transform: uppercase; text-align: justify;'>Coordinador sgc</td>");
                    } else if (obj_ConUsuarios[8].equals("Inspector_calidad")) {
                        out.print("<td style='text-transform: uppercase; text-align: justify;'>Inspector calidad</td>");
                    } else {
                        out.print("<td style='text-transform: uppercase; text-align: justify;'>Administrador del sistema</td>");
                    }
                    out.print("<td style='text-align: center;'><a href='Usuarios?l=3&d=" + obj_ConUsuarios[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' alt='Logo' width='30' height='30.5' /></a></td>");
                    if (obj_ConUsuarios[9].equals(1)) {
                        out.print("<td style='text-align: center;'><a href='Usuarios?l=5&id=" + obj_ConUsuarios[0] + "&Mestado=0'><img src='Interfaz/Contenido/Iconos/Check.png' alt='Logo' width='30' height='30.5' title='Usuario activo'/></a></td>");
                    } else {
                        out.print("<td style='text-align: center;'><a href='Usuarios?l=5&id=" + obj_ConUsuarios[0] + "&Mestado=1'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='25' height='25.5' title='Usuario inactivo'/></a></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 9);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div></div>");
            }
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(AyudanteVistaUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
