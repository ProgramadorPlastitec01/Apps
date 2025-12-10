package Tags;

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
            //RolJpaController jpacrol = new RolJpaController();
            if (pageContext.getRequest().getAttribute("Usuario") != null) {
                List lst_usuarios = (List) pageContext.getRequest().getAttribute("Lista_usuarios");
                if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Registro")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Usuario</h3>");
                    out.print("<form action='Usuario?opc=2' method='post'>");

                    out.print("<b>Identificacion :</b>");
                    out.print("<input type='text' name='Txt_identificacion' id='Txt_identificacion' placeholder='Numero Identificacion' title='Identificacion' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_identificacion');val1.add(Validate.Presence);</script>");

                    out.print("<b>Nombre(s) :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombres(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Apellido(s) :</b>");
                    out.print("<input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");
//                   
                    out.print("<b>Codigo :</b>");
                    out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Codigo' title='Codigo' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script></td>");

                    out.print("<b>Usuario :</b>");
                    out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");

//                    
//                    out.print("<b>Confirmar contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña' title='Confirmar contraseña' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);"
//                            + "val1.add(Validate.Confirmation, { match: 'Txt_password'} );</script>");
//                    
                    out.print("<b>Rol :</b>");
                    out.print("<select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                    out.print("<option value='0' >Seleccionar Rol</option> onchange='javascript:this.value=this.value.toUpperCase();'/>");
                    out.print("<option value='Administrador'>Administrador</option>");
                    out.print("<option value='Calidad'>Calidad</option>");
                    out.print("<option value='Consulta'>Consulta</option>");

                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<br><br /><input type='submit' value='Registrar' />");

                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                } else if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Modificar")) {
                    //List lst_usuarios(List) pageContext.getRequest().getAttribute("Lista_usuarios");
                    List lst_usuario = (List) pageContext.getRequest().getAttribute("Datos_usuario");
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<div align='right'><a href='Usuario?opc=1&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Cancelar Modificación' /></a></div>");
                    out.print("<h3>Modificar Usuario</h3>");
                    out.print("<form action='Usuario?opc=4' method='post'>");
                    out.print("<b>Identificacion :</b>");
                    out.print("<input type='text' name='Txt_identificacion' id='Txt_identificacion' placeholder='Identificacion' title='Identificacion' value='" + obj_usuario[0].toString().toUpperCase() + "' readonly onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_identificacion');val1.add(Validate.Presence);</script>");
                    out.print("<b>Nombre(s) :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombre(s)' value='" + obj_usuario[1].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");
                    out.print("<b>Apellidos(s) :</b>");
                    out.print("<input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' value='" + obj_usuario[2].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");

                    out.print("<b>Codigo :</b>");
                    out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Codigo' title='Codigo' value='" + obj_usuario[7].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);</script></td>");

                    out.print("<b>Usuario :</b>");
                    out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' value='" + obj_usuario[3].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");

//                    out.print("<b>Confirmar contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña' title='Confirmar contraseña' value='" + obj_usuario[4].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>"
//                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);"
//                            + "val1.add(Validate.Confirmation, { match: 'Txt_password'} );</script>");
                    out.print("<b>Rol :</b>");
                    out.print("<select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                    out.print("<option value='0' >Seleccionar Rol</option>");
                    out.print("<option value='Administrador' " + ((obj_usuario[4].toString().contains("Administrador")) ? "selected" : "") + ">Administrador</option>");
                    out.print("<option value='Calidad' " + ((obj_usuario[4].toString().contains("Calidad")) ? "selected" : "") + ">Calidad</option>");
                    out.print("<option value='Consulta' " + ((obj_usuario[4].toString().contains("Consulta")) ? "selected" : "") + ">Consulta</option>");
                    out.print("</select>"
                            + "<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');"
                            + "mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                    out.print("<input type='hidden' name='Id_usuario' id='Id_usuario' value='" + obj_usuario[6] + "' />");

//                    out.print("<input type='hidden' name='Id_usuario' value='" + obj_usuario[0] + "' />");
//                    out.print("<center><a href='Usuario?opc=6&Id_usuario=" + obj_usuario[0] + "'><i class='morado'>Restablecer contraseña</i></a></center>");
                    out.print("<br /><input type='submit' value='Actualizar' />");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
//                   
                }
                out.print("<div id='content'>");
                out.print("<h3>Usuarios</h3>");
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Identificacion</th>");
                out.print("<th>Nombre</th>");
                out.print("<th>Usuario</th>");
                out.print("<th>Rol</th>");
                out.print("<th>Codigo</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");

                out.print("</tr>");
                for (int i = 0; i < lst_usuarios.size(); i++) {
                    Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                    if (Integer.parseInt(obj_usuarios[5].toString()) == 1) {
                        out.print("<tr>");
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[6] + "</td>");
                        out.print("<td align='center'><a href='#' onclick='DesactivarUsuario(" + obj_usuarios[1] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='22px' height='22px' alt='edit' title='Desactivar Usuario' /></a></td>");
                        out.print("<td align='center'><a href='Usuario?opc=3&Id_usuario=" + obj_usuarios[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='22px' height='22px' alt='edit' title='Modificar Registro' /></a></td>");
                        out.print("</tr>");
                    } else {
                        out.print("<tr class='morado'>");
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[6] + "</td>");
                        out.print("<td align='center'><a href='#' onclick='ActivarUsuario(" + obj_usuarios[1] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='22px' height='22px' alt='edit' title='Activar Usuario' /></a></td>");
                        out.print("<td align='center'><a href='Usuario?opc=3&Id_usuario=" + obj_usuarios[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='22px' height='22px' alt='edit' title='Modificar Registro' /></a></td>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 10);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("</div> <!-- END of content -->");
                out.print("<div class='cleaner'></div>");

            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
