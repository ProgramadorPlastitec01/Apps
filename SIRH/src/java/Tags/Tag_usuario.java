package Tags;

import Controladores_BD.AreaJpaController;
import Controladores_BD.MenuJpaController;
import Controladores_BD.UsuarioJpaController;
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
            MenuJpaController jpacmnu = new MenuJpaController();
            UsuarioJpaController jpacusa = new UsuarioJpaController();
            AreaJpaController jpacara = new AreaJpaController();
            int id_usuario = 0;
            List lst_usuario = null;
            String permisos = "";
            List lst_opciones_permisos = null;
            int id_opcion_menu = 0;
            int menu = Integer.parseInt(pageContext.getSession().getAttribute("Menu").toString());
            String rol = pageContext.getSession().getAttribute("Rol").toString();
            if (pageContext.getRequest().getAttribute("Usuario") != null) {
                //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                id_opcion_menu = Integer.parseInt(pageContext.getRequest().getAttribute("Permisos").toString());
                lst_opciones_permisos = jpacmnu.Opciones_usuario_id(id_opcion_menu, menu);
                if (lst_opciones_permisos != null) {
                    Object[] obj_permisos = (Object[]) lst_opciones_permisos.get(0);
                    permisos = obj_permisos[3].toString();
                } else {
                    permisos = "";
                }
//</editor-fold>
                if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Registrar_usuario")) {
                    id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("Id_usuario").toString());
                    if (id_usuario == 0) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRAR USUARIO">
                        out.print("<div id='sidebar'>");
                        out.print("<form action='Usuario?opc=4' method='post'>");
                        out.print("<h3>Nuevo Usuario</h3>");
                        out.print("Nombres :");
                        out.print("<input type='text' name='Txt_nombres' id='Txt_nombres' onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Nombres' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombres');val1.add(Validate.Presence);</script>");
                        out.print("Apellidos :");
                        out.print("<input type='text' name='Txt_apellidos' id='Txt_apellidos' onchange='javascript:this.value=this.value.toUpperCase();' placeholder='Apellidos' />"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellidos');val1.add(Validate.Presence);</script>");
                        out.print("Documento :");
                        out.print("<input type='text' name='Txt_documento' id='Txt_documento' placeholder='CC'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');"
                                + "val1.add(Validate.Numericality);"
                                + "val1.add(Validate.Presence);</script>");
                        out.print("Usuario :");
                        out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' placeholder='Usuario'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
                        out.print("Firma :");
                        out.print("<input type='text' name='Txt_firma' id='Txt_firma' placeholder='Firma'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_firma');val1.add(Validate.Presence);</script>");
                        out.print("Correo :");
                        out.print("<input type='text' name='Txt_correo' id='Txt_correo' placeholder='Correo'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);val1.add(Validate.Email);</script>");
                        List lst_roles = jpacusa.Consultar_roles();
                        out.print("Rol :");
                        out.print("<select name='Cbx_rol' id='Cbx_rol'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_roles.size(); i++) {
                            Object[] obj_roles = (Object[]) lst_roles.get(i);
                            out.print("<option value='" + obj_roles[0] + "'>" + obj_roles[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br />Personal :<br />");
                        out.print("<input type='radio' name='Rdb_personal' id='Rdb_personal' value='2' checked />Todos <br />");
                        out.print("<input type='radio' name='Rdb_personal' id='Rdb_personal' value='1' />Directo <br />");
                        out.print("<input type='radio' name='Rdb_personal' id='Rdb_personal' value='0' />Temporal <br />");
                        List lst_areas = jpacara.Consultar_areas();
                        out.print("<br />Área :");
                        out.print("<select name='Cbx_area' id='Cbx_area'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_areas.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_areas.get(i);
                            out.print("<option value='" + obj_areas[0] + "'>" + obj_areas[2] + " / " + obj_areas[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='submit' value='Registrar' />");
                        out.print("<input type='hidden' name='Txt_seleccion_permisos' id='Txt_seleccion_permisos' value='[1][2][3][4][5][30]' />");
                        out.print("<input type='hidden' name='Txt_seleccion_permisos_detallados' id='Txt_seleccion_permisos_detallados' value='' />");
                        out.print("</form>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                        out.print("<div id='content'>");
                        out.print("<h3>Permisos de usuario</h3>");
                        List lst_menu = jpacmnu.Menu_todo();
                        List lst_opciones = null;
                        int cantidad_opciones = 1;
                        out.print("<table class='table'>");
                        for (int i = 0; i < lst_menu.size(); i++) {
                            Object[] obj_menu = (Object[]) lst_menu.get(i);
                            out.print("<tr>");
                            out.print("<td align='center' colspan='2'><b>" + obj_menu[1] + "</b></td>");
                            out.print("<td align='center'><b>Opc<b></td>");
                            out.print("</tr>");
                            lst_opciones = jpacmnu.Opciones_todas(Integer.parseInt(obj_menu[0].toString()));
                            for (int j = 0; j < lst_opciones.size(); j++) {
                                Object[] obj_opciones = (Object[]) lst_opciones.get(j);
                                out.print("<tr>");
                                if (obj_menu[1].equals("Inicio") || obj_menu[1].equals("Ajustes") || obj_menu[1].equals("Sesión")) {
                                    out.print("<td align='center'><input type='checkbox' value='[" + obj_opciones[0] + "]' onclick=\"SeleccionPermisos(this," + obj_opciones[0] + ");\" checked  /></td>");
                                } else {
                                    out.print("<td align='center'><input type='checkbox' value='[" + obj_opciones[0] + "]' onclick=\"SeleccionPermisos(this," + obj_opciones[0] + ");\" /></td>");
                                }
                                out.print("<td>" + obj_opciones[1] + "</td>");
                                if (obj_opciones[4].toString().equals("")) {
                                    out.print("<td>" + obj_opciones[4] + "</td>");
                                } else {
                                    String[] arg_permisos = obj_opciones[4].toString().split("-");
                                    out.print("<td><form method='post' action='#' id='Div_permisos_" + obj_opciones[0] + "' style='display:none'>");
                                    for (int k = 0; k < arg_permisos.length; k++) {
                                        out.print("<input type='checkbox' id='Ckb_permisos_" + obj_opciones[0] + k + "' value='[" + obj_opciones[0] + "/" + arg_permisos[k] + "]' onclick=\"SeleccionPermisosDetallados(this);\" />" + arg_permisos[k].replace("I", "Registrar").replace("U", "Actualizar").replace("D", "Quitar").replace("S", "Cambiar estados").replace("V", "Ver").replace("P", "Imprimir").replace("M", "Salario").replace("E", "Exportar").replace("Z", "Sindicalizado") + "<br />");
                                    }
                                    out.print("</form></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td colspan='3' style='background-color:#ddd'></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
                        //</editor-fold>
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="MODIFICAR USUARIO">
                        lst_usuario = jpacusa.Traer_usuario_id(id_usuario);
                        Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                        out.print("<div id='sidebar'>");
                        out.print("<a href='Usuario?opc=3&mnu=7'><span class='fa fa-arrow-left fa-size_super_small'></span></a>");
                        out.print("<form action='Usuario?opc=4&Id_usuario=" + obj_usuario[0] + "' method='post'>");
                        out.print("<h3>Modificar Usuario</h3>");
                        out.print("Nombres :");
                        out.print("<input type='text' name='Txt_nombres' id='Txt_nombres' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_usuario[1] + "' placeholder='Nombres'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_nombres');val1.add(Validate.Presence);</script>");
                        out.print("Apellidos :");
                        out.print("<input type='text' name='Txt_apellidos' id='Txt_apellidos' onchange='javascript:this.value=this.value.toUpperCase();' value='" + obj_usuario[2] + "' placeholder='Apellidos'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_apellidos');val1.add(Validate.Presence);</script>");
                        out.print("Documento :");
                        out.print("<input type='text' name='Txt_documento' id='Txt_documento' value='" + obj_usuario[3] + "' placeholder='CC'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_documento');"
                                + "val1.add(Validate.Numericality);"
                                + "val1.add(Validate.Presence);</script>");
                        out.print("Usuario :");
                        out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' value='" + obj_usuario[4] + "' placeholder='Usuario'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script>");
                        out.print("Firma :");
                        out.print("<input type='text' name='Txt_firma' id='Txt_firma' value='" + obj_usuario[6] + "' placeholder='Firma'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_firma');val1.add(Validate.Presence);</script>");
                        out.print("Correo :");
                        out.print("<input type='text' name='Txt_correo' id='Txt_correo' value='" + obj_usuario[7] + "' placeholder='Correo'/>"
                                + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_correo');val1.add(Validate.Presence);val1.add(Validate.Email);</script>");
                        List lst_roles = jpacusa.Consultar_roles();
                        out.print("Rol :");
                        out.print("<select name='Cbx_rol' id='Cbx_rol'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_roles.size(); i++) {
                            Object[] obj_roles = (Object[]) lst_roles.get(i);
                            out.print("<option value='" + obj_roles[0] + "' " + (((Integer) obj_roles[0] == (Integer) obj_usuario[8]) ? "selected" : "") + ">" + obj_roles[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_rol');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<br /><br />Personal :<br />");
                        out.print("<input type='radio' name='Rdb_personal' id='Rdb_personal' value='2' " + ((Integer.parseInt(obj_usuario[18].toString()) == 2) ? "checked" : "") + "  />Todos <br />");
                        out.print("<input type='radio' name='Rdb_personal' id='Rdb_personal' value='1' " + ((Integer.parseInt(obj_usuario[18].toString()) == 1) ? "checked" : "") + " />Directo <br />");
                        out.print("<input type='radio' name='Rdb_personal' id='Rdb_personal' value='0' " + ((Integer.parseInt(obj_usuario[18].toString()) == 0) ? "checked" : "") + " />Temporal <br />");
                        List lst_areas = jpacara.Consultar_areas();
                        out.print("<br />Área :");
                        out.print("<select name='Cbx_area' id='Cbx_area'>");
                        out.print("<option value='0'>Click para seleccionar</option>");
                        for (int i = 0; i < lst_areas.size(); i++) {
                            Object[] obj_areas = (Object[]) lst_areas.get(i);
                            if (Integer.parseInt(obj_usuario[17].toString()) == Integer.parseInt(obj_areas[0].toString())) {
                                out.print("<option value='" + obj_areas[0] + "' selected>" + obj_areas[2] + " / " + obj_areas[1] + "</option>");
                            } else {
                                out.print("<option value='" + obj_areas[0] + "'>" + obj_areas[2] + " / " + obj_areas[1] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<script type='text/javascript'>var mySelect = new LiveValidation('Cbx_area');");
                        out.print("mySelect.add(Validate.Exclusion, { within: ['0'], failureMessage: \"\"});</script>");
                        out.print("<input type='submit' value='Modificar' />");
                        out.print("<input type='hidden' name='Txt_seleccion_permisos' id='Txt_seleccion_permisos' value='" + ((obj_usuario[14] == null) ? "" : obj_usuario[14]) + "' />");
                        out.print("<input type='hidden' name='Txt_seleccion_permisos_detallados' id='Txt_seleccion_permisos_detallados' value='" + ((obj_usuario[16] == null) ? "" : obj_usuario[16]) + "' />");
                        out.print("</form>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="PERMISOS">
                        out.print("<div id='content'>");
                        out.print("<h3>Permisos de usuario</h3>");
                        List lst_menu = jpacmnu.Menu_todo();
                        List lst_opciones = null;
                        int cantidad_opciones = 1;
                        out.print("<table class='table'>");
                        for (int i = 0; i < lst_menu.size(); i++) {
                            Object[] obj_menu = (Object[]) lst_menu.get(i);
                            out.print("<tr>");
                            out.print("<td align='center' colspan='2'><b>" + obj_menu[1] + "</b></td>");
                            out.print("<td align='center'><b>Opc<b></td>");
                            out.print("</tr>");
                            lst_opciones = jpacmnu.Opciones_todas(Integer.parseInt(obj_menu[0].toString()));
                            for (int j = 0; j < lst_opciones.size(); j++) {
                                Object[] obj_opciones = (Object[]) lst_opciones.get(j);
                                out.print("<tr>");
//                                if (obj_menu[1].equals("Inicio") || obj_menu[1].equals("Ajustes") || obj_menu[1].equals("Sesión")) {
//                                    out.print("<td align='center'><input type='checkbox' value='[" + obj_opciones[0] + "]' onclick=\"SeleccionPermisos(this," + obj_opciones[0] + ");\" " + ((permisos.contains("[" + obj_opciones[0] + "]")) ? "checked" : "") + "  /></td>");
//                                } else {
                                if (obj_usuario[14] == null) {
                                    out.print("<td align='center'><input type='checkbox' value='[" + obj_opciones[0] + "]' onclick=\"SeleccionPermisos(this," + obj_opciones[0] + ");\" /></td>");
                                } else {
                                    out.print("<td align='center'><input type='checkbox' value='[" + obj_opciones[0] + "]' onclick=\"SeleccionPermisos(this," + obj_opciones[0] + ");\" " + ((obj_usuario[14].toString().contains("[" + obj_opciones[0] + "]")) ? "checked" : "") + "/></td>");
                                }
//                                }
                                out.print("<td>" + obj_opciones[1] + "</td>");
                                if (obj_opciones[4].toString().equals("")) {
                                    out.print("<td>" + obj_opciones[4] + "</td>");
                                } else {
                                    String[] arg_permisos = obj_opciones[4].toString().split("-");
                                    out.print("<td><form method='post' action='#' id='Div_permisos_" + obj_opciones[0] + "' style='display:block'>");
                                    for (int k = 0; k < arg_permisos.length; k++) {
                                        if (obj_usuario[16] == null) {
                                            out.print("<input type='checkbox' id='Ckb_permisos_" + obj_opciones[0] + k + "' value='[" + obj_opciones[0] + "/" + arg_permisos[k] + "]' onclick=\"SeleccionPermisosDetallados(this);\" />" + arg_permisos[k].replace("I", "Registrar").replace("U", "Actualizar").replace("D", "Quitar").replace("S", "Cambiar estados").replace("V", "Ver").replace("P", "Imprimir").replace("M", "Salario").replace("E", "Exportar").replace("Z", "Sindicalizado")  + "<br />");
                                        } else {
                                            out.print("<input type='checkbox' id='Ckb_permisos_" + obj_opciones[0] + k + "' value='[" + obj_opciones[0] + "/" + arg_permisos[k] + "]' " + ((obj_usuario[16].toString().contains("[" + obj_opciones[0] + "/" + arg_permisos[k] + "]")) ? "checked" : "") + " onclick=\"SeleccionPermisosDetallados(this);\" />" + arg_permisos[k].replace("I", "Registrar").replace("U", "Actualizar").replace("D", "Quitar").replace("S", "Cambiar estados").replace("V", "Ver").replace("P", "Imprimir").replace("M", "Salario").replace("E", "Exportar").replace("Z", "Sindicalizado") + "<br />");
                                        }
                                    }
                                    out.print("</form></td>");
                                }
                                out.print("</tr>");
                            }
                            out.print("<tr>");
                            out.print("<td colspan='3' style='background-color:#ddd'></td>");
                            out.print("</tr>");
                        }
                        out.print("</table>");
                        out.print("</div>");
//                        //</editor-fold>
//</editor-fold>
                    }
                } else if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Consultar_usuarios")) {
                    //<editor-fold defaultstate="collapsed" desc="CONSULTAR, MODIFICAR, ESTADO">
                    List lst_usuarios = jpacusa.Consultar_usuarios();
                    out.print("<div id='content_sin'>");
                    out.print("<h3>Usuarios SIRH <div style='float:right'><input id='Txt_filtro' type='text' onkeyup='Filtrar()' placeholder='Buscar' onchange='javascript:this.value=this.value.toUpperCase();' /></div></h3>");
                    out.print("<div align='left' id='NavPosicion'></div>");
                    out.print("<table class='table' id='resultados'>");
                    out.print("<tr>");
                    out.print("<th>Apellidos</th>");
                    out.print("<th>Nombres</th>");
                    out.print("<th>Documento</th>");
                    out.print("<th>Correo</th>");
                    out.print("<th>Usuario</th>");
                    out.print("<th>Rol</th>");
                    out.print("<th>Opc.</th>");
                    out.print("</tr>");
                    for (int i = 0; i < lst_usuarios.size(); i++) {
                        Object[] obj_usuarios = (Object[]) lst_usuarios.get(i);
                        out.print("<tr " + (((Integer) obj_usuarios[11] == 1) ? "" : "class='rojo'") + ">");
                        out.print("<td>" + obj_usuarios[2] + "</td>");
                        out.print("<td>" + obj_usuarios[1] + "</td>");
                        out.print("<td>" + obj_usuarios[3] + "</td>");
                        out.print("<td>" + obj_usuarios[7] + "</td>");
                        out.print("<td>" + obj_usuarios[4] + "</td>");
                        out.print("<td>" + obj_usuarios[9] + "</td>");
                        out.print("<td align='center'>");
                        if (permisos.contains("S") || rol.equals("ADMINISTRADOR")) {
                            out.print("<span onclick='" + (((Integer) obj_usuarios[11] == 1) ? "Desactivar" : "Activar") + "Usuario(" + obj_usuarios[0] + ")' class='" + (((Integer) obj_usuarios[11] == 1) ? "fa fa-check-circle fa-size_small" : "fa fa-times-circle fa-size_small") + "'></span>");
                        }
                        if (permisos.contains("U") || rol.equals("ADMINISTRADOR")) {
                            out.print("" + (((Integer) obj_usuarios[11] == 1) ? "&nbsp;&nbsp;&nbsp;<a href='Usuario?opc=1&mnu=6&Id_usuario=" + obj_usuarios[0] + "'><span class='fa fa-pencil-alt fa-size_small'></span></a>"
                                    + "&nbsp;&nbsp;&nbsp;<span onclick='RestablecerPassword(" + obj_usuarios[0] + ")' class='fa fa-key fa-size_small'></span></a>" : "") + "");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("<script type='text/javascript'>");
                    out.print("var pager = new Pager('resultados', 10);");
                    out.print("pager.init();");
                    out.print("pager.showPageNav('pager','NavPosicion');");
                    out.print("pager.showPage(1);");
                    out.print("</script>");
                    out.print("</div>");
//</editor-fold>
                } else if (pageContext.getRequest().getAttribute("Usuario").toString().equals("Cuenta")) {
                    //<editor-fold defaultstate="collapsed" desc="CUENTA">
                    id_usuario = Integer.parseInt(pageContext.getRequest().getAttribute("Id_usuario").toString());
                    lst_usuario = jpacusa.Traer_usuario_id(id_usuario);
                    Object[] obj_usuario = (Object[]) lst_usuario.get(0);
                    out.print("<div id='content_sin'>");
                    out.print("<div style='display:block' align='center'><br /><br />");
                    out.print("<div style='float:left;width:40%;border-right:2px solid #969696;text-align : justify;'><dir /><dir /><dir />"
                            + "<h3>Datos de usuario</h3>"
                            + "<b>Rol : </b>" + obj_usuario[9] + "<br />"
                            + "<b>Nombre : </b>" + obj_usuario[1] + " " + obj_usuario[2] + "<br />"
                            + "<b>Documento : </b>" + obj_usuario[3] + "<br />"
                            + "<b>Codigo firma : </b>" + obj_usuario[6] + "<br />"
                            + "<b>Correo : </b>" + obj_usuario[7] + "<br /><br />"
                            + "<b>Ultimo ingreso : </b>" + obj_usuario[15] + ""
                            + "<br /><br /><center><img src='Fotos/" + obj_usuario[3] + ".jpg' style='border-radius: 25%;border: 2px solid #fff;max-width: 50%;margin-right: 3px;-webkit-filter: grayscale(100%);'></center><br />"
                            + "</div>");
                    out.print("<div style='float:left;width:59%;text-align:justify;'><dir />"
                            + "<h3>Cambiar datos de sesión</h3>"
                            + "Se permite cambiar información de inición de sesión.<br /><br /><br />"
                            + "<form action='Usuario?opc=6' method='post'>");
                    out.print("<table><tr><td>");
                    out.print("Usuario :<br />");
                    out.print("<input type='text' name='Txt_usuario' id='Txt_usuario' value='" + obj_usuario[4] + "' placeholder='Usuario'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_usuario');val1.add(Validate.Presence);</script><br />");
                    out.print("Contraseña :<br />");
                    out.print("<input type='password' name='Txt_password' id='Txt_password' placeholder='Contraseña'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password');val1.add(Validate.Presence);val1.add(Validate.Password);</script><br />");
                    out.print("Confirmar contraseña :<br />");
                    out.print("<input type='password' name='Txt_password_confirm' id='Txt_password_confirm' placeholder='Confirmar contraseña'/>"
                            + "<script type='text/javascript'>var val1 = new LiveValidation('Txt_password_confirm');val1.add(Validate.Presence);val1.add(Validate.Confirmation,{match:'Txt_password'});</script><br />");
                    out.print("<input type='submit' value='Modificar' />"
                            + "<input type='hidden' name='Id_usuario' value='" + id_usuario + "' /></td>");
                    out.print("<td>El cambio de Contraseña debe contener:<br />"
                            + "-Minimo 8 caracteres<br/>\n"
                            + "-Maximo 15 caracteres<br/>\n"
                            + "-Al menos una letra mayúscula<br/>\n"
                            + "-Al menos una letra minúscula<br/>\n"
                            + "-Al menos un dígito ( Numero )<br/>\n"
                            + "-No espacios en blanco<br/>\n"
                            + "-Al menos 1 caracter especial ( $@$!%*?&#- )</td></tr></table>");
                    out.print("</form>"
                            + "</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_usuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
