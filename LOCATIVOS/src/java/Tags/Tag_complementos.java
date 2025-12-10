package Tags;

import Controladores.AreaJpaController;
import Controladores.RolJpaController;
import Controladores.UbicacionJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_complementos
        extends TagSupport {

    public int doStartTag()
            throws JspException {
        JspWriter out = this.pageContext.getOut();
        try {
            RolJpaController jpacrol = new RolJpaController();
            AreaJpaController jpacarea = new AreaJpaController();
            UbicacionJpaController UbiJpa = new UbicacionJpaController();
            if (this.pageContext.getRequest().getAttribute("Usuario") != null) {
                //<editor-fold defaultstate="collapsed" desc="REGISTRAR USUARIO">
                if (this.pageContext.getRequest().getAttribute("Usuario").toString().equals("Registro_usuarios")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Usuario</h3>");
                    out.print("<form action='Complementos?opc=2' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>Nombre(s) :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombres(s)' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Apellido(s) :</b>");
                    out.print("<input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");

                    out.print("<b>Documento :</b>");
                    out.print("<input type='text' name='Txt_documento' id='Txt_documento' placeholder='Documento' tilte='Documento' /><script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");

                    out.print("<b>Código :</b>");
                    out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");

                    out.print("<b>Correo :</b>");
                    out.print("<input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo' title='Correo'/>");
                    out.print("<b>Usuario :</b>");
                    out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");

//                    out.print("<b>Contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password' id='Txt_password' placeholder='Contraseña' title='Contraseña' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_password');val1.add(Validate.Presence);</script></td>");
//
//                    out.print("<b>Confirmar contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña' title='Confirmar contraseña' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);val1.add(Validate.Confirmation, { match: 'Txt_password'} );</script>");
                    List lst_rol = jpacrol.Roles();
                    out.print("<b>Rol :</b>");
                    out.print("<select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                    out.print("<option value='0' style='display:none;'>Seleccionar Rol</option>");
                    for (int i = 0; i < lst_rol.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_rol.get(i);
                        out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                    }
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    List lst_area = jpacarea.Areas();
                    out.print("<b>Area :</b>");
                    out.print("<select name='Cbx_area' id='Cbx_area' title='Area'>");
                    out.print("<option value='0'  style='display:none;'>Seleccionar Area</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "-" + obj_area[2] + "</option>");
                    }
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<input type='submit' value='Registrar' /></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                } //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODIFICAR">
                else if (this.pageContext.getRequest().getAttribute("Usuario").toString().equals("Modificar_usuarios")) {
                    List lst_usuario = (List) this.pageContext.getRequest().getAttribute("Datos_usuario");
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<div align='right'><a href='Complementos?opc=1&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Cancelar Modificación' /></a></div>");
                    out.print("<h3>Modificar Usuario</h3>");
                    out.print("<form action='Complementos?opc=4' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>Nombre(s) :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombre(s)' value='" + obj_usuario[1].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Apellidos(s) :</b>");
                    out.print("<input type='text' name='Txt_apellido' id='Txt_apellido' placeholder='Apellido(s)' title='Apellido(s)' value='" + obj_usuario[2].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellido');val1.add(Validate.Presence);</script>");

                    out.print("<b>Documento :</b>");
                    out.print("<input type='text' name='Txt_documento' id='Txt_documento' placeholder='Documento' title='Documento' value='" + obj_usuario[3].toString().toUpperCase() + "' readonly='true'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");

                    out.print("<b>Código :</b>");
                    out.print("<input type='text' name='Txt_codigo' id='Txt_codigo' placeholder='Código' title='Código' value='" + obj_usuario[4].toString().toUpperCase() + "'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_codigo');val1.add(Validate.Presence);val1.add(Validate.Enteros);</script>");

                    out.print("<b>Correo:</b>");
                    out.print("<input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo' title='Correo' value='" + obj_usuario[5].toString().toUpperCase() + "'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);val1.add(Validate.Email);</script>");

                    out.print("<b>Usuario :</b>");
                    out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario' title='Usuario' value='" + obj_usuario[6].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");

//                    out.print("<b>Contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password' id='Txt_password' placeholder='Contraseña' title='Contraseña' value='" + obj_usuario[7].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password');val1.add(Validate.Presence);</script></td>");
//
//                    out.print("<b>Confirmar contraseña :</b>");
//                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña' title='Confirmar contraseña' value='" + obj_usuario[6].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);" + "val1.add(Validate.Confirmation, { match: 'Txt_password'} );</script>");
                    List lst_rol = jpacrol.Roles();
                    out.print("<b>Rol :</b>");
                    out.print("<select name='Cbx_rol' id='Cbx_rol' title='Rol'>");
                    out.print("<option value='0' style='display:none;'>Seleccionar Rol</option>");
                    for (int i = 0; i < lst_rol.size(); i++) {
                        Object[] obj_rol = (Object[]) lst_rol.get(i);
                        if (obj_usuario[9] == obj_rol[0]) {
                            out.print("<option value='" + obj_rol[0] + "' selected>" + obj_rol[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_rol[0] + "'>" + obj_rol[1] + "</option>");
                        }
                    }
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    List lst_area = jpacarea.Areas();
                    out.print("<b>Área :</b>");
                    out.print("<select name='Cbx_area' id='Cbx_area' title='Área'>");
                    out.print("<option value='0' style='display:none;'>Seleccionar area</option>");
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] obj_area = (Object[]) lst_area.get(i);
                        if (obj_usuario[11] == obj_area[0]) {
                            out.print("<option  value='" + obj_area[0] + "' selected>" + obj_area[1] + "</option>");
                        } else {
                            out.print("<option value='" + obj_area[0] + "'>" + obj_area[1] + "</option>");
                        }
                    }
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<input type='hidden' name='Id_usuario' id='Id_usuario' value='" + obj_usuario[0] + "' />");
                    out.print("<input type='submit' value='Actualizar' /></br></br>");
                    out.print("</form>");
                    out.print("<hr /><a href='Complementos?opc=25&Id_usuario=" + obj_usuario[0] + "' class='naranja'>Restablecer contraseña</a>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div>");
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULTA USUARIOS">
                List lst_usuarios = (List) this.pageContext.getRequest().getAttribute("Lista_usuarios");
                out.print("<div id='content'>");
                if (lst_usuarios == null) {
                    out.print("<h3>Usuarios</h3>");
                } else {
                    out.print("<h3>Usuarios<div style='float:right;width:200px'><input type='text'  onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar'/></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table id='resultados' class='table' style='width:100%'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Documento</th>");
                    out.print("<th>Código</th>");
                    out.print("<th>Correo</th>");
                    out.print("<th>Usuario</th>");
                    out.print("<th>Rol</th>");
                    out.print("<th>Area</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_usuarios.size(); i++) {
                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                        if (Integer.parseInt(obj_usuarios[7].toString()) == 1) {
                            out.print("<tr>");
                        } else {
                            out.print("<tr class='rojo'>");
                        }
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[5] + "</td>");
                        out.print("<td>" + obj_usuarios[9] + "</td>");
                        out.print("<td>" + obj_usuarios[10] + "</td>");
                        if (Integer.parseInt(obj_usuarios[7].toString()) == 1) {
                            out.print("<td align='center'><a href='#' onclick='DesactivarUsuario(" + obj_usuarios[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Usuario' /></a></td>");
                            out.print("<td align='center'><a href='Complementos?opc=3&Id_usuario=" + obj_usuarios[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Modificar Registro' /></a></td>");
                        } else {
                            out.print("<td align='center'><a href='#' onclick='ActivarUsuario(" + obj_usuarios[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar Usuario' /></a></td>");
                            out.print("<td align='center'><b class='naranja'>Sin permisos</b></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                }
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of content -->");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="PROVEEDORES">
            if (this.pageContext.getRequest().getAttribute("Proveedor") != null) {
                if (this.pageContext.getRequest().getAttribute("Proveedor").toString().equals("Registro_proveedor")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Externos</h3>");
                    out.print("<form action='Complementos?opc=8' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>Contacto :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombres(s)' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Empresa :</b>");
                    out.print("<input type='text' name='Txt_empresa' id='Txt_empresa' placeholder='Empresa' title='Empresa' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_empresa');val1.add(Validate.Presence);</script>");

                    out.print("<b>Telefono :</b>");
                    out.print("<input type='text' name='Txt_telefono' id='Txt_telefono' placeholder='Telefono' tilte='Telefono' /><script type='text/javascript'>var val1 = new LiveValidation('Txt_telefono');val1.add(Validate.Presence);val1.add(Validate.Telefono);</script>");

                    out.print("<b>Correo :</b>");
                    out.print("<input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo' title='Correo'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);</script>");

                    out.print("<b>Descripcion :</b>");
                    out.print("<textarea name='Txt_descripcion' id='Txt_descripcion' placeholder='Descripcion' title='Descripcion' onchange='javascript:this.value=this.value.toUpperCase();'></textarea><script type='text/javascript'>var val1 = new LiveValidation('Txt_Descripcion');val1.add(Validate.Presence);</script>");

                    out.print("<input type='submit' value='Registrar'/></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                } else if (this.pageContext.getRequest().getAttribute("Proveedor").toString().equals("Modificar_proveedor")) {
                    List lst_proveedor = (List) this.pageContext.getRequest().getAttribute("Datos_proveedor");
                    Object[] obj_proveedor = (Object[]) lst_proveedor.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<div align='right'><a href='Complementos?opc=7&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Cancelar Modificación' /></a></div>");
                    out.print("<h3>Modificar externos</h3>");
                    out.print("<form action='Complementos?opc=10' method='post' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='Id_proveedor' id='Id_proveedor' value='" + obj_proveedor[0] + "'/>");
                    out.print("<b>Contacto :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre(s)' title='Nombre(s)' value='" + obj_proveedor[1].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Empresa :</b>");
                    out.print("<input type='text' name='Txt_empresa' id='Txt_empresa' placeholder='Empresa' title='Empresa' value='" + obj_proveedor[2].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_empresa');val1.add(Validate.Presence);</script>");

                    out.print("<b>Telefono :</b>");
                    out.print("<input type='text' name='Txt_telefono' id='Txt_telefono' placeholder='Telefono' title='Telefono' value='" + obj_proveedor[3].toString().toUpperCase() + "'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_telefono');val1.add(Validate.Presence);val1.add(Validate.Documento);</script>");

                    out.print("<b>Correo:</b>");
                    out.print("<input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo' title='Correo' value='" + obj_proveedor[4].toString().toUpperCase() + "'/>");
                    out.print("<b>Descripcion :</b>");
                    out.print("<textarea type='text' name='Txt_descripcion' id='Txt_descripcion'  title='Descripcion' onchange='javascript:this.value=this.value.toUpperCase();'>" + obj_proveedor[6].toString().toUpperCase() + "</textarea>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_descripcion');val1.add(Validate.Presence);</script>");

                    out.print("<input type='submit' value='Actualizar' /></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                }
                List lst_proveedor = (List) this.pageContext.getRequest().getAttribute("Lista_proveedor");
                out.print("<div id='content'>");
                out.print("<h3>Externos<div style='float:right;width:200px'><input type='text' onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar'/></div></h3>");
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Contacto</th>");
                out.print("<th>Empresa</th>");
                out.print("<th>Telefono</th>");
                out.print("<th>Correo</th>");
                out.print("<th>Descripción</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_proveedor.size(); i++) {
                    Object[] obj_proveedor = (Object[]) lst_proveedor.get(i);
                    if (Integer.parseInt(obj_proveedor[0].toString()) > 1) {
                        if (Integer.parseInt(obj_proveedor[5].toString()) == 1) {
                            out.print("<tr>");
                        } else {
                            out.print("<tr class='rojo'>");
                        }
                        out.print("<td>" + obj_proveedor[1] + "</td>");
                        out.print("<td>" + obj_proveedor[2] + "</td>");
                        out.print("<td>" + obj_proveedor[3] + "</td>");
                        out.print("<td>" + obj_proveedor[4] + "</td>");
                        out.print("<td>" + obj_proveedor[6] + "</td>");
                        if (Integer.parseInt(obj_proveedor[5].toString()) == 1) {
                            out.print("<td align='center'><a href='#' onclick='DesactivarProveedor(" + obj_proveedor[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar Proveedor' /></a></td>");
                            out.print("<td align='center'><a href='Complementos?opc=9&Id_proveedor=" + obj_proveedor[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Modificar Registro' /></a></td>");
                        } else {
                            out.print("<td align='center'><a href='#' onclick='ActivarProveedor(" + obj_proveedor[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar proveedor' /></a></td>");
                            out.print("<td align='center'><b class='naranja'>Sin permisos</b></td>");
                        }
                        out.print("</tr>");
                        out.print("</tr>");
                    }
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 15);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of content -->");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CLASIFICACION">
            if (this.pageContext.getRequest().getAttribute("Clasificacion") != null) {
                if (this.pageContext.getRequest().getAttribute("Clasificacion").toString().equals("Registro_clasificacion")) {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Clasificacion</h3>");
                    out.print("<form action='Complementos?opc=20' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>Nombre de clasificacion:</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre de la clasificacion' title='Nombre de la clasificacion' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Tipo de clasificacion :</b>");
                    out.print("<select name='Cbx_tipo' id='Cbx_tipo' title='Tipo'>");
                    out.print("<option value='0' >Seleccionar Tipo</option>");
                    out.print("<option value='Solicitud'>Solicitud</option>");
                    out.print("<option value='Entrega'>Entrega</option>");
                    out.print("<option value='Recibe'>Recibe</option>");
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<input type='submit' value='Registrar'/></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                } else if (this.pageContext.getRequest().getAttribute("Clasificacion").toString().equals("Modificar_clasificacion")) {
                    List lst_clasificacion = (List) this.pageContext.getRequest().getAttribute("Datos_clasificacion");
                    Object[] obj_clasificacion = (Object[]) lst_clasificacion.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<div align='right'><a href='Complementos?opc=19&fto='><img src='Interfaz/Contenido/Iconos/Delete.png' width='26px' height='26px' alt='edit' title='Cancelar Modificación' /></a></div>");
                    out.print("<h3>Modificar clasificacion</h3>");
                    out.print("<form action='Complementos?opc=22' method='post' onsubmit='checkSubmit();'>");
                    out.print("<input type='hidden' name='Id_clasificacion' id='Id_clasificacion' value='" + obj_clasificacion[0] + "'/>");
                    out.print("<b>Nombre de la clasificacion(s) :</b>");
                    out.print("<input type='text' name='Txt_nombre' id='Txt_nombre' placeholder='Nombre' title='Nombre' value='" + obj_clasificacion[1].toString().toUpperCase() + "' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Tipo :</b>");
                    out.print("<select name='Cbx_tipo' id='Cbx_tipo' title='Tipo'>");
                    out.print("<option value='" + obj_clasificacion[2] + "' style='display:none;'>" + obj_clasificacion[2] + "</option>");
                    out.print("<option value='Solicitud'>Solicitud</option>");
                    out.print("<option value='Entrega'>Entrega</option>");
                    out.print("<option value='Recibe'>Recibe</option>");
                    out.print("</select></br></br><script type='text/javascript'>var mySelect = new LiveValidation('Cbx_tipo');mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");

                    out.print("<input type='submit' value='Actualizar' /></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                }
                List lst_clasificaciones = (List) this.pageContext.getRequest().getAttribute("Lista_clasificacion");
                out.print("<div id='content'>");
                out.print("<h3>Clasificaciones<div style='float:right;width:200px'><input type='text' onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar'/></div></h3>");
                out.print("<div align='left' id='NavPosicion'></div>");
                out.print("<table id='resultados' class='table' style='width:100%'>");
                out.print("<tr>");
                out.print("<th>Nombre</th>");
                out.print("<th>Tipo</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Modificar</th>");
                out.print("</tr>");
                for (int i = 0; i < lst_clasificaciones.size(); i++) {
                    Object[] obj_clasificacion = (Object[]) lst_clasificaciones.get(i);
                    if (Integer.parseInt(obj_clasificacion[3].toString()) == 1) {
                        out.print("<tr>");
                    } else {
                        out.print("<tr class='rojo'>");
                    }
                    out.print("<td>" + obj_clasificacion[1] + "</td>");
                    out.print("<td>" + obj_clasificacion[2] + "</td>");
                    if (Integer.parseInt(obj_clasificacion[3].toString()) == 1) {
                        out.print("<td align='center'><a href='#' onclick='DesactivarClasificacion(" + obj_clasificacion[0] + ")'><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Desactivar clasificacion' /></a></td>");
                        out.print("<td align='center'><a href='Complementos?opc=21&Id_clasificacion=" + obj_clasificacion[0] + "'><img src='Interfaz/Contenido/Iconos/Edit.png' width='20px' height='20px' alt='edit' title='Modificar Registro' /></a></td>");
                    } else {
                        out.print("<td align='center'><a href='#' onclick='ActivarClasificacion(" + obj_clasificacion[0] + ")'><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Activar clasificacion' /></a></td>");
                        out.print("<td align='center'><b class='naranja'>Sin permisos</b></td>");
                    }
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("<script type='text/javascript'>");
                out.print("var pager = new Pager('resultados', 15);");
                out.print("pager.init();");
                out.print("pager.showPageNav('pager','NavPosicion');");
                out.print("pager.showPage(1);");
                out.print("</script>");
                out.print("<div class='cleaner'></div>");
                out.print("</div> <!-- END of content -->");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="UBICACION">
            if ((this.pageContext.getRequest().getAttribute("Ubicacion") != null) && (this.pageContext.getRequest().getAttribute("Ubicacion").toString().equals("Ubicacion"))) {
                List Lista_ubicacion = (List) this.pageContext.getRequest().getAttribute("Lista_ubicacion");
                List Areas = (List) this.pageContext.getRequest().getAttribute("Areas");
                if (this.pageContext.getRequest().getAttribute("Action").toString().equals("Modificar")) {
                    List Ubicacion = (List) this.pageContext.getRequest().getAttribute("Lista_ubicacion_mod");
                    int Id_Ubicacion = Integer.parseInt(this.pageContext.getRequest().getAttribute("Id_Ubicacion").toString());
                    Object[] Ubica = (Object[]) Ubicacion.get(0);
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Modificar Ubicacion</h3>");
                    out.print("<form action='Complementos?opc=16&Id_Ubicacion=" + Id_Ubicacion + "' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>Nombre :</b>");
                    out.print("<input type='text' name='Nombre' id='Nombre' value='" + Ubica[1] + "' " + "title='Nombres' onchange='javascript:this.value=this.value.toUpperCase();'/>" + "<script type='text/javascript'>var val1 = new LiveValidation('Nombre');val1.add(Validate.Presence);</script>");

                    out.print("<b>Tipo :</b>");
                    out.print("<select name='Tipo' id='Tipo' title='Tipo'/>");
                    if (Ubica[2].toString().equals("Farmacéuticos")) {
                        out.print("<option value=\"\">Seleccione Tipo</option>");
                        out.print("<option>Insumos</option>");
                        out.print("<option selected=\"true\">Farmacéuticos</option>");
                        out.print("<option>General</option>");
                    } else if (Ubica[2].toString().equals("General")) {
                        out.print("<option value=\"\">Seleccione Tipo</option>");
                        out.print("<option>Farmacéuticos</option>");
                        out.print("<option>Insumos</option>");
                        out.print("<option selected=\"true\">General</option>");
                    } else {
                        out.print("<option value=\"\">Seleccione Tipo</option>");
                        out.print("<option>Farmacéuticos</option>");
                        out.print("<option selected=\"true\">Insumos</option>");
                        out.print("<option>General</option>");
                    }
                    out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Tipo');val1.add(Validate.Presence);val1.add(Validate.Tipo);</script>");

                    out.print("<input type='hidden' name='Area' id='Id_solicitud_externos' value='" + Ubica[4] + "'>");
                    out.print("<b>Area :</b><br />");
                    out.print("<div style='overflow:scroll;height:350px;'>");
                    for (int i = 0; i < Areas.size(); i++) {
                        Object[] Obj_areas = (Object[]) Areas.get(i);
                        if (Ubica[4].toString().contains("[" + Obj_areas[0].toString() + "]")) {
                            out.print("<input type='checkbox' checked name='box" + Obj_areas[0] + "' id='box" + Obj_areas[0] + "' value='[" + Obj_areas[0] + "]' onclick='externos_sub(this);'> " + Obj_areas[2] + "<hr />");
                        } else {
                            out.print("<input type='checkbox' name='box" + Obj_areas[0] + "' id='box" + Obj_areas[0] + "' value='[" + Obj_areas[0] + "]' onclick='externos_sub(this);'> " + Obj_areas[2] + "<hr />");
                        }
                    }
                    out.print("</div>");
                    out.print("<br/>");
                    out.print("<input type='submit' value='Modificar'/></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                } else {
                    out.print("<div id='sidebar'>");
                    out.print("<h3>Registrar Ubicacion</h3>");
                    out.print("<form action='Complementos?opc=14' method='post' onsubmit='checkSubmit();'>");
                    out.print("<b>Nombre :</b>");
                    out.print("<input type='text' name='Nombre' id='Nombre' placeholder='Nombre' title='Nombres' onchange='javascript:this.value=this.value.toUpperCase();'/><script type='text/javascript'>var val1 = new LiveValidation('Nombre');val1.add(Validate.Presence);</script>");
                    out.print("<b>Tipo :</b>");
                    out.print("<select name='Tipo' id='Tipo' title='Tipo'/>");
                    out.print("<option value=\"\">Seleccione Tipo</option>");
                    out.print("<option>Insumos</option>");
                    out.print("<option>Farmacéuticos</option>");
                    out.print("<option>General</option>");
                    out.print("</select><script type='text/javascript'>var val1 = new LiveValidation('Tipo');val1.add(Validate.Presence);val1.add(Validate.Tipo);</script>");
                    out.print("<input type='hidden' name='Area' id='Id_solicitud_externos'>");
                    out.print("<b>Area :</b><br />");
                    out.print("<div style='overflow:scroll;height:350px;'>");
                    for (int i = 0; i < Areas.size(); i++) {
                        Object[] Obj_areas = (Object[]) Areas.get(i);
                        out.print("<input type='checkbox' name='box" + Obj_areas[0] + "' id='box" + Obj_areas[0] + "' value='[" + Obj_areas[0] + "]' onclick='externos_sub(this);'> " + Obj_areas[2] + "<hr />");
                    }
                    out.print("</div>");
                    out.print("<br />");
                    out.print("<input type='submit' value='Registrar'/></br></br>");
                    out.print("</form>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of sidebar -->");
                }
                if (Lista_ubicacion == null) {
                    out.print("<center>");
                    out.print("<h3>Ubicaciones</h3>");
                    out.print("<img src='Interfaz/Contenido/Iconos/Alert.png' style='margin-top:100px;width:100.5px;height:80.75px' alt='edit' title='Sin permisos' /><br />");
                    out.print("<b>No existe ninguna Ubicacion</b>");
                    out.print("</center>");
                } else {
                    out.print("<div id='content'>");
                    out.print("<h3>Ubicaciones<div style='float:right;width:200px'><input type='text'  onkeyup='Filtrar()' name='Txt_filtro' id='Txt_filtro' placeholder='Buscar'/></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table id='resultados' class='table'>");
                    out.print("<tr>");
                    out.print("<th>Nombre</th>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Area</th>");
                    out.print("<th>Estado </th>");
                    out.print("<th>Modificar</th>");
                    out.print("</tr>");
                    for (int i = 0; i < Lista_ubicacion.size(); i++) {
                        Object[] obj_ubicacion = (Object[]) Lista_ubicacion.get(i);
                        out.print("<tr " + ((Integer.parseInt(obj_ubicacion[3].toString()) == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_ubicacion[1] + "</td>");
                        out.print("<td>" + obj_ubicacion[2] + "</td>");
                        if ("".equals(obj_ubicacion[4].toString())) {
                            out.print("<td><b class='naranja'>Ninguna</b></td>");
                        } else {
                            out.print("<td>");
                            for (int j = 0; j < Areas.size(); j++) {
                                Object[] Obj_areas = (Object[]) Areas.get(j);
                                if (obj_ubicacion[4].toString().contains("[" + Obj_areas[0].toString() + "]")) {
                                    out.print("" + Obj_areas[2] + " | ");
                                }
                            }
                            out.print("</td>");
                        }
                        if (Integer.parseInt(obj_ubicacion[3].toString()) == 1) {
                            out.print("<td><center><a href=\"Complementos?opc=17&Estado=0&Id_Ubicacion=" + obj_ubicacion[0] + "\"><img src='Interfaz/Contenido/Iconos/Check.png' width='20px' height='20px' alt='edit' title='Estado Ubicacion'/></center></a></td>");
                            out.print("<td><center> <a href=\"Complementos?opc=15&Id_Ubicacion=" + obj_ubicacion[0] + "\"><img src='Interfaz/Contenido/Iconos/Edit.png'" + " width='20px' height='20px' alt='edit' title='Modificar Registro'/></a><center></td>");
                        } else {
                            out.print("<td><center><a href=\"Complementos?opc=17&Estado=1&Id_Ubicacion=" + obj_ubicacion[0] + "\"><img src='Interfaz/Contenido/Iconos/Delete.png' width='20px' height='20px' alt='edit' title='Estado Ubicacion'/></center></a></td>");
                            out.print("<td align='center'><b class='naranja'>Sin permisos</b></td>");
                        }

                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 15);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("<div class='cleaner'></div>");
                    out.print("</div> <!-- END of content -->");
                }
            }
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_complementos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
