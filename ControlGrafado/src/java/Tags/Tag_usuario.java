package Tags;

import Controladores.ClienteJpaController;
import Controladores.RolJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RolJpaController jpa_rol = new RolJpaController();
        ClienteJpaController jpa_usuario = new ClienteJpaController();
        List lst_rol = jpa_rol.consultaRoles();
        List lst_usuario = null;
        List lst_usuarios = (List) pageContext.getRequest().getAttribute("consulta_usuarios");
        String filtro = (String) pageContext.getRequest().getAttribute("filtro");
        int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("id_usuario").toString());
        try {
            out.print("<div id='sidebar'>");
            if (id_usuario == 0) {
                //<editor-fold defaultstate="collapsed" desc="registrar usuario">
                out.print("<form method='post' action='Usuario?opc=2' onsubmit='registroU();'>");
                out.print("<h3>Registro Usuario</h3>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombre-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Apellido:</b><br/>");
                out.print("<input type='text' name='txt_apellido' id='apellido-id' placeholder='Apellido' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('apellido-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Documento:</b><br/>");
                out.print("<input type='text' name='txt_documento' id='documento-id' placeholder='Documento' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('documento-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script><br/>");
                out.print("<b>Rol:</b><br/>");
                out.print("<select name='slct_rol' id='rol-id'>");
                out.print("<option value='0' style='display:none;'>SELECCIONE ROL</option>");
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_rol = (Object[]) lst_rol.get(i);
                    out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[2] + "</option>");
                }
                out.print("</select><br/><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('rol-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Usuario:</b><br/>");
                out.print("<input type='text' name='txt_usuario' id='usuario-id' placeholder='Usuario' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('usuario-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                //</editor-fold>
            } else {
                // <editor-fold defaultstate="collapsed"  desc="Modificar Usuario.">
                lst_usuario = jpa_usuario.consultausuarioId(id_usuario);
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                out.print("<h3>Modificar Usuario &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='Usuario?opc=1&idU=" + 0 + "&txt_bus=" + filtro + "'><img src='Interfaz/Contenido/Iconos/Delete.png' alt='Logo' width='20' height='20' title='Cancelar' /></a></h3>");
                out.print("<form method='post' action='Usuario?opc=3' onsubmit='registroU();'>");
                out.print("<input type='hidden' name='idU' value='" + obj_usuario[0] + "'>");
                out.print("<input type='hidden' name='txt_bus' value='" + filtro + "'>");
                out.print("<b>Nombre:</b><br/>");
                out.print("<input type='text' name='txt_nombre' id='nombre-id' placeholder='Nombre' value='" + obj_usuario[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('nombre-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Apellido:</b><br/>");
                out.print("<input type='text' name='txt_apellido' id='apellido-id' placeholder='Apellido' value='" + obj_usuario[2] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('apellido-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Documento:</b><br/>");
                out.print("<input type='text' name='txt_documento' id='documento-id' placeholder='Ingresar documento' value='" + obj_usuario[3] + "' onchange='javascript:this.value=this.value.toUpperCase();'>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('documento-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Rol:</b><br/>");
                out.print("<select name='slct_rol' id='rol-id'>");
                out.print("<option value='" + obj_usuario[6] + "' style='display:none;'>" + obj_usuario[7] + "</option>");
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_ = (Object[]) lst_rol.get(i);
                    out.print("<option value='" + obj_[0] + "'>" + obj_[2] + "</option>");
                }
                out.print("</select><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('rol-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<b>Usuario:</b><br/>");
                out.print("<input type='text' name='txt_usuario' id='usuario-id' placeholder='Usuario' value='" + obj_usuario[4] + "' onchange='javascript:this.value=this.value.toUpperCase();'><br/>");
                out.print("<script type='text/javascript'>");
                out.print("var validation = new LiveValidation('usuario-id');");
                out.print("validation.add( Validate.Presence );");
                out.print("</script>");
                out.print("<center><a href='Usuario?opc=6&idU=" + id_usuario + "'><b class='naranja'>Restablecer contraseña</b></a></center><br/>");
                out.print("<input type='submit' id='btsubmit' value='Guardar'>");
                out.print("<div class=\"la-ball-fall\" style='bottom: 24px;left: 72px;display:none;' id='puntos'>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "          <div></div>\n"
                        + "        </div>");
                out.print("</form>");
                // </editor-fold>     
            }
            out.print("<div class='cleaner'></div></div>");
            out.print("<div id='content'>");
            //<editor-fold defaultstate="collapsed" desc="consulta usuarios">
            out.print("<div style='float:right;'>");
            out.print("<form method='post' action='Usuario?opc=1&idU=" + 0 + "'>");
            out.print("<input name='txt_bus' type='text' class='input_field' placeholder='Buscar'><br/>");
            out.print("</form>");
            out.print("</div>");
            if (!filtro.equals("")) {
                out.print("<a href='Usuario?opc=1&idU=" + 0 + "&txt_bus='><img src='Interfaz/Contenido/Iconos/Volver.png' alt='Logo' width='25' height='25.5' /></a>");
            }
            out.print("<h3>Usuarios</h3>");
            if (lst_usuarios == null) {
                out.print("<h3>No se encontraron resultados</h3>");
            } else {
                out.print("<div id='NavPosicion'></div>");
                out.print("<table class='table' id='resultados' style='width:100%;'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Documento</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Rol asignado</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Estado</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                    if ((Integer) obj_usuarios[7] == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_usuarios[2] + " " + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[5] + "</td>");
                        out.print("<td>" + obj_usuarios[9] + "</td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-pencil-alt fa-size_small' onclick='Editar(" + obj_usuarios[0] + ")' title='Modificar'></span></td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-check fa-size_small' onclick='Inactivar(" + obj_usuarios[0] + ")' title='Inactivar'></span></td>");
                    } else {
                        out.print("<tr>");
                        out.print("<td><b style='color:red'>" + obj_usuarios[2] + " " + obj_usuarios[3] + "</b></td>");
                        out.print("<td><b style='color:red'>" + obj_usuarios[4] + "</b></td>");
                        out.print("<td><b style='color:red'>" + obj_usuarios[5] + "</b></td>");
                        out.print("<td><b style='color:red'>" + obj_usuarios[9] + "</b></td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-pencil-alt fa-size_small span_color' title='No se puede modificar'></span></td>");
                        out.print("<td style='text-align: center;'><span class='fas fa-times fa-size_small' onclick='Aprobar(" + obj_usuarios[0] + ")' title='Activar'></span></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados',10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div>");
            }
            // </editor-fold>
            out.print("<div class='cleaner'></div></div>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
