package Tag;

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
        try {
            RolJpaController jpacrol = new RolJpaController();
            if (pageContext.getRequest().getAttribute("Usuario") != null) {
                //<editor-fold defaultstate="collapsed" desc="Registrar">
                out.print("<div class=\"card shadow mb-4\">");
                out.print("<a href=\"#collapseCardExample\" class=\"d-block card-header py-3 collapse\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"false\" aria-controls=\"collapseCardExample\">");
                out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Registrar Usuario</h6>");
                out.print("</a>");
                out.print("<div class=\"collapse\" id=\"collapseCardExample\" style='padding-bottom: 20px;'>");
                out.print("<div class=\"card-body\" >");
                out.print("<form action='Usuario?opc=2' method='post' id='FormUserRegister'>");
                out.print("<div style='width:40%;float:left'>");
                out.print("<b>Nombre(s) :</b>");
                out.print("<input class='form-control' type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombres(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                out.print("<b>Documento :</b>");
                out.print("<input class='form-control' type='text' name='Txt_documento' id='Txt_documento' placeholder='Documento' tilte='Documento' />"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                out.print("<b>Usuario :</b>");
                out.print("<input class='form-control' type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
                out.print("<span onclick=\"javascript:document.getElementById('Btn_accion').click();\" class='far fa-check-circle fa-size_small verde' title='Registrar'></span>&nbsp;&nbsp;");
                out.print("<span onclick=\"javascript:location.href='Usuario?opc=1'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                out.print("</div>");
                out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                out.print("<b>Apellido(s) :</b>");
                out.print("<input class='form-control' type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");
                out.print("<b>Código :</b>");
                out.print("<input class='form-control' type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código'/>"
                        + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                List lst_rol = jpacrol.Roles();
                out.print("<b>Rol :</b>");
                out.print("<select class='form-control' name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                out.print("<option value='0' >Seleccionar Rol</option>");
                for (int i = 0; i < lst_rol.size(); i++) {
                    Object[] obj_rol = (Object[]) lst_rol.get(i);
                    out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                }
                out.print("</select>"
                        + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                        + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                out.print("</div>");
                out.print("<br /><div style='display:none'><input type='submit' value='Registrar' id='Btn_accion' /></div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Modificar">
                if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Modificar")) {
                    List lst_usuario = (List) pageContext.getRequest().getAttribute("Datos_usuario");
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("<div class=\"card shadow mb-4\">");
                    out.print("<a href=\"#collapseCardExample2\" class=\"d-block card-header py-3\" data-toggle=\"collapse\" role=\"button\" aria-expanded=\"true\" aria-controls=\"collapseCardExample2\">");
                    out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Modificar Usuario</h6>");
                    out.print("</a>");
                    out.print("<div class=\"collapse show\" id=\"collapseCardExample2\" style='padding-bottom: 20px;'>");
                    out.print("<div class=\"card-body\">");
                    out.print("<form action='Usuario?opc=4' method='post' id='FormUserUpdate' >");
                    out.print("<div style='width:40%;float:left'>");
                    out.print("<b>Nombre(s) :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_nombre' id='Txt_nombreM' placeholder='Nombre(s)' title='Nombre(s)' value='" + obj_usuario[1].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombreM');val1.add(Validate.Presence);</script>");
                    out.print("<b>Documento :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_documento' id='Txt_documentoM' placeholder='Documento' title='Documento' value='" + obj_usuario[3].toString().toUpperCase() + "' readonly='true'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documentoM');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                    out.print("<b>Usuario :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_usuario' id='Txt_usuarioM' placeholder='Usuario' title='Usuario' value='" + obj_usuario[5].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuarioM');val1.add(Validate.Presence);</script>");
                    out.print("<span onclick=\"javascript:document.getElementById('Btn_accionM').click();\" class='far fa-edit fa-size_small naranja' title='Actualizar'></span>&nbsp;&nbsp;");
                    out.print("<span onclick=\"javascript:location.href='Usuario?opc=1'\" class='far fa-times-circle fa-size_small rojo' title='Cancelar'></span>");
                    out.print("</div>");
                    out.print("<div style='width:40%;float:left;margin-left:3%;margin-right:2%'>");
                    out.print("<b>Apellidos(s) :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_apellido' id='Txt_apellidoM' placeholder='Apellido(s)' title='Apellido(s)' value='" + obj_usuario[2].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellidoM');val1.add(Validate.Presence);</script>");
                    out.print("<b>Código :</b>");
                    out.print("<input class='form-control' type='text' name='Txt_codigo' id='Txt_codigoM' placeholder='Código' title='Código' value='" + obj_usuario[4].toString().toUpperCase() + "'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigoM');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                    out.print("<b>Rol :</b>");
                    out.print("<select class='form-control' name='Cbx_rol' id='Cbx_rolM' title='Rol'>");
                    out.print("<option value='0' >Seleccionar Rol</option>");
                    for (int i = 0; i < lst_rol.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_rol.get(i);
                        if (obj_usuario[8] == obj_rol[0]) {
                            out.print("<option value='" + obj_rol[0] + "' selected>" + obj_rol[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                        }
                    }
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rolM');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<input type='hidden' name='Id_usuario' id='Id_usuario' value='" + obj_usuario[0] + "' />");
                    out.print("</div>");
                    out.print("<br /><div style='display:none'><input type='submit' value='Actualizar' id='Btn_accionM' /></div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="Consulta">
                List lst_usuarios = (List) pageContext.getRequest().getAttribute("Lista_usuarios");
                out.print("<div class=\"card shadow mb-4\">");
                out.print("<div class=\"card-header py-3\">");
                out.print("<h6 class=\"m-0 font-weight-bold text-primary\">Tabla Usuarios</h6>");
                out.print("</div>");
                out.print("<div class=\"card-body\">");
                out.print("<div class=\"table-responsive\">");
                out.print("<table class=\"table2 table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Documento</th>");
                out.print("<th>Código</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Contraseña</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Restablecer</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tfoot>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Documento</th>");
                out.print("<th>Código</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Contraseña</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("<th>Restablecer</th>");
                out.print("</tr>");
                out.print("</tfoot>");
                out.print("<tbody>");
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                    if (Integer.parseInt(obj_usuarios[6].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[5] + "</td>");
                        out.print("<td>" + obj_usuarios[8] + "</td>");
                        //out.print("<td align='center'><a href='#' onclick='DesactivarUsuario(" + obj_usuarios[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Usuario' /></a></td>");
                        //out.print("<td align='center'><a href='Usuario?opc=3&Id_usuario=" + obj_usuarios[0] + "' title='Modificar' class='icon_font'><span class='fa fa-pen fa-size_small'></span></a></td>");
                        out.print("<td align='center'><span onclick='DesactivarUsuario(" + obj_usuarios[0] + ")' class='fa fa-check fa-size_small' title='Desactivar Usuario'></span></td>");
                        out.print("<td align='center'><span onclick=\"location.href='Usuario?opc=3&Id_usuario=" + obj_usuarios[0] + "'\" class='fa fa-pen fa-size_small' title='Editar Usuario'></span></td>");
                        out.print("<td align='center'><span onclick=\"location.href='Usuario?opc=7&amp;Id_usuario=" + obj_usuarios[0] + "'\" class='fa fa-key fa-size_small' title='Restablecer Password'></span></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class='tr_rojo'>");
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[5] + "</td>");
                        out.print("<td>" + obj_usuarios[8] + "</td>");
                        //out.print("<td align='center'><a href='#' onclick='ActivarUsuario(" + obj_usuarios[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar Usuario' /></a></td>");
                        out.print("<td align='center'><span onclick='ActivarUsuario(" + obj_usuarios[0] + ")' class='fa fa-times fa-size_small' title='Activar Usuario'></span></td>");
                        out.print("<td align='center'><span class='fa fa-pen fa-size_small' title='No se permite Editar Usuarios Inactivos'></span></td>");
                        out.print("<td align='center'><span onclick=\"location.href='Usuario?opc=7&amp;Id_usuario=" + obj_usuarios[0] + "'\" class='fa fa-key fa-size_small' title='Restablecer Password'></span></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
