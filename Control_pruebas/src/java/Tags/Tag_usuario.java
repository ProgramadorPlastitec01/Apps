package Tags;

import Controladores.RolJpaController;
import Controladores.UsuarioJpaController;
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
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            List lst_usuarios = null;
            List lst_usuario = null;
            if (pageContext.getRequest().getAttribute("Usuario") != null) {
                if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Modulo_usuarios")) {
                    int id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("Id_usuario").toString());
                    if (id_usuario > 0) {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                        lst_usuario = jpacusa.Traer_usuario(id_usuario);
                        Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                        out.print("<div id='sidebar'>");
                        out.print("<div align='right'><a href='Usuario?opc=1&fto='><span class='fa fa-times-circle fa-size_small'></span></a></div>");
                        out.print("<h3>Modificar Usuario</h3>");
                        out.print("<form action='Usuario?opc=4' method='post'>");
                        out.print("<b>Nombre(s) :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombre(s)' value='" + obj_usuario[1].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Apellidos(s) :</b>");
                        out.print("<input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' value='" + obj_usuario[2].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");
                        out.print("<b>Documento :</b>");
                        out.print("<input type='text' name='Txt_documento' id='Txt_documento' placeholder='Documento' title='Documento' value='" + obj_usuario[3].toString().toUpperCase() + "' readonly='true'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                        out.print("<b>Código :</b>");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código' value='" + obj_usuario[4].toString().toUpperCase() + "'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        out.print("<b>Usuario :</b>");
                        out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' value='" + obj_usuario[5].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
//                    out.print("<b>Contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password' id='Txt_password' placeholder='Contraseña' title='Contraseña' value='" + obj_usuario[6].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password');val1.add(Validate.Presence);</script></td>");
//                    out.print("<b>Confirmar contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña' title='Confirmar contraseña' value='" + obj_usuario[6].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);"
//                            + "val1.add(Validate.Confirmation, { match: 'Txt_password'} );</script>");
                        List lst_rol = jpacrol.Roles();
                        out.print("<b>Rol :</b>");
                        out.print("<select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
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
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='hidden' name='Id_usuario' id='Id_usuario' value='" + obj_usuario[0] + "' />");
                        out.print("<input type='submit' value='Actualizar' />");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR">
                        out.print("<div id='sidebar'>");
                        out.print("<h3>Registrar Usuario</h3>");
                        out.print("<form action='Usuario?opc=2' method='post'>");
                        out.print("<b>Nombre(s) :</b>");
                        out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombres(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                        out.print("<b>Apellido(s) :</b>");
                        out.print("<input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");
                        out.print("<b>Documento :</b>");
                        out.print("<input type='text' name='Txt_documento' id='Txt_documento' placeholder='Documento' tilte='Documento' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");
                        out.print("<b>Código :</b>");
                        out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");
                        out.print("<b>Usuario :</b>");
                        out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
//                    out.print("<b>Contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password' id='Txt_password' placeholder='Contraseña' title='Contraseña' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password');val1.add(Validate.Presence);</script></td>");
//                    out.print("<b>Confirmar contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña' title='Confirmar contraseña' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);"
//                            + "val1.add(Validate.Confirmation, { match: 'Txt_password'} );</script>");
                        List lst_rol = jpacrol.Roles();
                        out.print("<b>Rol :</b>");
                        out.print("<select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                        out.print("<option value='0' >Seleccionar Rol</option>");
                        for (int i = 0; i < lst_rol.size(); i++) {
                            Object[] obj_rol = (Object[]) lst_rol.get(i);
                            out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                        }
                        out.print("</select>"
                                + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                                + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("</form>");
                        out.print("<div class='cleaner'></div>");
                        out.print("</div> <!-- END of sidebar -->");
//</editor-fold>
                    }
                    out.print("<div id='content'>");
                    out.print("<h3>Usuarios<div style='float:right;'><input type='text' id='Txt_filtro' placeholder='Buscar' onkeyup='Filtrar()'/></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Documento</th>");
                    out.print("<th>Código</th>");
                    out.print("<th>Usuario</th>");
                    out.print("<th>Rol</th>");
                    out.print("<th>Opciones</th>");
                    out.print("</tr>");
                    lst_usuarios = jpacusa.Usuarios();
                    for (int i = 0; i < lst_usuarios.size(); i++) {
                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                        if (Integer.parseInt(obj_usuarios[6].toString()) == 1) {
                            out.print("<tr>");
                        } else {
                            out.print("<tr class='rojo'>");
                        }
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[8] + "</td>");
                        out.print("<td align='center' style='width:10%'>");
                        if (Integer.parseInt(obj_usuarios[6].toString()) == 1) {
                            out.print("<span onclick='DesactivarUsuario(" + obj_usuarios[0] + ")' class='fa fa-check-circle fa-size_small'></span>");
                            out.print("&nbsp;&nbsp;<a href='Usuario?opc=1&Id_usuario=" + obj_usuarios[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>");
                            out.print("&nbsp;&nbsp;&nbsp;<span onclick='RestablecerPassword(" + obj_usuarios[0] + ")' class='fa fa-key fa-size_small'></span></a>");
                        } else {
                            out.print("<span onclick='ActivarUsuario(" + obj_usuarios[0] + ")' class='fa fa-times-circle fa-size_small'></span>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div> <!-- END of content -->");
                    out.print("<div class='cleaner'></div>");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
