package Tags;

import Controladores.AreaJpaController;
import Controladores.RolJpaController;
import Controladores.UsuarioJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Usuario extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            //<editor-fold defaultstate="collapsed" desc="VARIABLES">
            UsuarioJpaController jpa_usuarios = new UsuarioJpaController();
            AreaJpaController jpa_areas = new AreaJpaController();
            RolJpaController jpa_roles = new RolJpaController();
            int idUsuario = Integer.parseInt(pageContext.getRequest().getAttribute("idUsuario").toString());
            List lst_usuarios, lst_usuario = null;
            List lst_areas = null;
            List lst_roles = null;
            lst_usuarios = jpa_usuarios.consultarUsuarios();
            lst_usuario = jpa_usuarios.consultaUsuario(idUsuario);
            lst_areas = jpa_areas.consultarAreas();
            lst_roles = jpa_roles.consultarRoles();
//</editor-fold>
            if (idUsuario == 0) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:475px; height:370px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Usuario?opc=1&idUsuario=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Registrar Usuario</h3>");
                out.print("<form action='Usuario?opc=2' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Nombre(s) :</b>");
                out.print("<br><input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombres(s)' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script></td>");
                out.print("<td><b>Apellido(s) :</b>");
                out.print("</br><input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)'autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Documento :</b>");
                out.print("</br><input type='text' name='Txt_documento' id='Txt_documento' placeholder='Documento' tilte='Documento'autocomplete='off' />"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Documento);</script></td>");
                out.print("<td><b>Código :</b>");
                out.print("</br><input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código'autocomplete='off'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script></td></tr>");
                out.print("<tr><td><b>Usuario :</b>");
                out.print("<br><input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' autocomplete='off' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script></td>");
                out.print("<td><b>Rol :</b>");
                out.print("</br><select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                out.print("<option value='0' style='display:none;'>Seleccionar Rol</option>");
                for (int i = 0; i < lst_roles.size(); i++) {
                    Object[] obj_roles = (Object[]) lst_roles.get(i);
                    out.print("<option value='" + obj_roles[0] + "' >" + obj_roles[1] + "</option>");
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
                out.print("<tr><td><b>Área :</b>");
                out.print("</br><select name='Cbx_area' id='Cbx_area' title='Área'>");
                out.print("<option value='0' style='display:none;'>Seleccionar Area</option>");
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                        out.print("<option value='" + obj_areas[0] + "' >" + obj_areas[1] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script><br></td>");
                out.print("</table>");
                out.print("<br /><div style='float:center;'><input type='submit' value='Registrar' /></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div> <!-- END of sidebar -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                out.print("<fieldset class='popup_local scrollbar' id='styleScroll' style='width:475px; height:370px; position: absolute;top:15%; left:5%;text-align:left '>");
                out.print("<div style='float:right;'><a href='Usuario?opc=1&idUsuario=0' style='color:black;'><span class='fas fa-times fa-size_small'></span></a></div>");
                out.print("<h3>Modificar Usuario</h3>");
                out.print("<form action='Usuario?opc=3' method='post'>");
                out.print("<table>");
                out.print("<tr><td><b>Nombre(s) :</b>");
                out.print("<br><input type='text' name='Txt_nombreM' id='Txt_nombreM' placeholder='Nombre(s)' title='Nombres(s)' value='" + obj_usuario[1] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombreM');val1.add(Validate.Presence);</script></td>");
                out.print("<td><b>Apellido(s) :</b>");
                out.print("<br><input type='text' name='Txt_apellidoM' id='Txt_apellidoM' placeholder='Apellido(s)' value='" + obj_usuario[2] + "' title='Apellido(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellidoM');val1.add(Validate.Presence);</script></td></tr>");
                out.print("<tr><td><b>Documento :</b>");
                out.print("<br><input type='text' name='Txt_documentoM' id='Txt_documentoM' placeholder='Documento' value='" + obj_usuario[3] + "' tilte='Documento' />"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documentoM');val1.add(Validate.Presence);val1.add(Validate.Documento);</script></td>");
                out.print("<td><b>Código :</b>");
                out.print("<br><input type='text' name='Txt_codigoM' id='Txt_codigoM' placeholder='Código' value='" + obj_usuario[4] + "' title='Código'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigoM');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script></td></tr>");
                out.print("<tr><td><b>Usuario :</b>");
                out.print("<br><input type='text' name='Txt_usuarioM' id='Txt_usuarioM' placeholder='Usuario' title='Usuario' value='" + obj_usuario[5] + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuarioM');val1.add(Validate.Presence);</script></td>");
                out.print("<td><b>Rol :</b>");
                out.print("<br><select name='Cbx_rolM' id='Cbx_rolM' title='Rol'>");
                for (int i = 0; i < lst_roles.size(); i++) {
                    Object[] obj_roles = (Object[]) lst_roles.get(i);
                    out.print("<option value='" + obj_usuario[6] + "' style='display:none;'>" + obj_usuario[9] + "</option>");
                    out.print("<option value='" + obj_roles[0] + "' >" + obj_roles[1] + "</option>");
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rolM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
                out.print("<tr><td><b>Área :</b>");
                out.print("<br><select name='Cbx_areaM' id='Cbx_areaM' title='Área'>");
                for (int i = 0; i < lst_areas.size(); i++) {
                    Object[] obj_areas = (Object[]) lst_areas.get(i);
                    if (Integer.parseInt(obj_areas[4].toString()) == 1) {
                        out.print("<option value='" + obj_usuario[7] + "' style='display:none'>" + obj_usuario[10] + "</option>");
                        out.print("<option value='" + obj_areas[0] + "' >" + obj_areas[1] + "</option>");
                    }
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_areaM');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script></td></tr>");
                out.print("<input type='hidden' name='idUsuario' value='" + obj_usuario[0] + "' />");
//                out.print("<center><a href='Usuario?opc=6&idUsuario=" + obj_usuario[0] + "'><i class='naranja'>Restablecer contraseña</i></a></center>");
                out.print("</table>");
                out.print("<br /><div style='float:center;>'><input type='submit' value='Modificar' /></div>");
                out.print("</form>");
                out.print("</fieldset>");
                out.print("</div> <!-- END of sidebar -->");
                out.print("<div class='cleaner'></div>");
                //</editor-fold>
            }
            out.print("<div id='sin-content'>");
            out.print("<h3>Usuarios</h3>");
            out.print("<div style='float: right;'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div>"
                    + "<div style'float:left;'><i onclick='mostrarVentana(4);' class='fas fa-plus fa-size_small'></i></div>");
            if (lst_usuarios == null) {
                out.print("<center>");
                out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px; margin-left:23%; width:100.5px;height:80.75px' alt='edit' title='No se encontraron datos' /><br />");
                out.print("</center>");
                out.print("</table>");
            } else {
                //<editor-fold defaultstate="collapsed" desc="CONSULTAR USUARIOS">
                out.print("<div align='left' id='NavPosicion0'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Apellido</th>");
                out.print("<th>Documento</th>");
                out.print("<th>Código</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Área</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Restablecer</th>");

                out.print("</tr>");
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                    out.print("" + ((Integer.parseInt(obj_usuarios[10].toString()) == 1) ? "<tr>" : "<tr class='rojo'>") + "");
                    out.print("<td>" + obj_usuarios[1] + "</td>");
                    out.print("<td>" + obj_usuarios[2] + "</td>");
                    out.print("<td>" + obj_usuarios[3] + "</td>");
                    out.print("<td>" + obj_usuarios[4] + "</td>");
                    out.print("<td>" + obj_usuarios[5] + "</td>");
                    out.print("<td>" + obj_usuarios[7] + "</td>");
                    out.print("<td>" + obj_usuarios[9] + "</td>");
                    if (Integer.parseInt(obj_usuarios[10].toString()) == 1) {
                        out.print("<td align='center'><a href='#' " + ((Integer.parseInt(obj_usuarios[10].toString()) == 1) ? "onclick='DesactivarUsuario(" + obj_usuarios[0] + ")' style='color:black;' ><span class='fas fa-check fa-size_small' title='Desactivar Usuario' /></span>"
                                : "onclick='ActivarUsuario(" + obj_usuarios[0] + ")' style='color:black;' ><span class='fas fa-times fa-size_small' title='Desactivar Usuario' /></span>") + " </a></td>");
                        out.print("<td align='center'><a href='Usuario?opc=1&idUsuario=" + obj_usuarios[0] + "' style='color:black;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Usuario' /></span></a></td>");
                        out.print("<td align='center'><a href='Usuario?opc=6&idUsuario=" + obj_usuarios[0] + "' style='color:black;'><span class='fas fa-key fa-size_small' title='Restablecer Contraseña' /></span></a></td>");
                    } else {
                        out.print("<td align='center'><a href='#' " + ((Integer.parseInt(obj_usuarios[10].toString()) == 1) ? "onclick='DesactivarUsuario(" + obj_usuarios[0] + ")' style='color:black;'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Usuario' />"
                                : "onclick='ActivarUsuario(" + obj_usuarios[0] + ")' style='color:black;'><span class='fas fa-times fa-size_small' title='Activar Usuario' /></span>") + " </a></td>");
                        out.print("<td align='center' style='color: #b7b7b7;'><span class='fas fa-pencil-alt fa-size_small' title='Modificar Usuario' /></td>");
                        out.print("<td align='center' style='color: #b7b7b7;'><span class='fas fa-key fa-size_small' title='Restablecer Contraseña' /></td>");

                    }
                    out.print("</tr>");
                }
                out.print("</table>");
//</editor-fold>
            }
            out.print("<script type='text/javascript'>");
            out.print("var pager0 = new Pager0('resultados', 10);");
            out.print("pager0.init();");
            out.print("pager0.showPageNav('pager0','NavPosicion0');");
            out.print("pager0.showPage(1);");
            out.print("</script>");
            out.print("</div> <!-- END of content -->");
            out.print("<div class='cleaner'></div>");
        } catch (Exception e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
