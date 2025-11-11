package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.UserControllerJpa;
import Controller.RoleControllerJpa;
import Controller.ConfigurationControllerJpa;
import java.util.List;

public class Tag_User extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        UserControllerJpa UserJpa = new UserControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        ConfigurationControllerJpa ConfigJpa = new ConfigurationControllerJpa();
        List lst_User = null;
        List lst_Role = null;
        List lst_Config = null;
        String permiss = "";
        try {
            permiss = pageContext.getRequest().getAttribute("permiss").toString();
        } catch (Exception e) {
            permiss = "Error al consultar permisos";
        }

        int IdUser = 0;

        try {
            IdUser = Integer.parseInt(pageContext.getRequest().getAttribute("IdUser").toString());
        } catch (Exception e) {
            IdUser = 0;
        }
        boolean isAllowed = false;
        if (permiss.contains("[28]")) {
            isAllowed = true;
        }
        try {
            if (IdUser != 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE USER">
                lst_User = UserJpa.ConsultUsersId(IdUser);
                if (lst_User != null) {
                    Object[] objUser = (Object[]) lst_User.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Actualizar información </h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='User?opt=3&IdUser=" + objUser[0] + "' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='mb-3' style='display: flex;'>");
                    out.print("<input type='text' class='form-control mr-3' name='TxtName' id='TxtName' value='" + objUser[1] + "' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' required>");
                    out.print("<input type='text' class='form-control mr-3' name='TxtLst' id='TxtLst' value='" + objUser[2] + "' placeholder='Apellido' data-toggle='tooltip' data-placement='top' title='Apellido' required>");
                    out.print("<input type='number' class='form-control mr-3' name='TxtDoc' id='TxtDoc' value='" + objUser[3] + "' placeholder='Nro. Documento' data-toggle='tooltip' data-placement='top' title='Nro. Documento' required>");
                    out.print("<input type='text' class='form-control' name='TxtUser' id='TxtUser' value='" + objUser[4] + "' placeholder='Usuario' data-toggle='tooltip' data-placement='top' title='Usuario' required>");
                    out.print("</div>");
                    out.print("<div class='' style='display: flex;'>");
                    out.print("<div class='wdtFixe mr-3' data-toggle='tooltip' data-placemente='top' title='Rol'>");
                    out.print("<select class='form-control' name='CbxRole' style='' required>");
                    out.print("<option value='" + objUser[6] + "'>" + objUser[7] + "</option>");
                    lst_Role = RoleJpa.ConsultRoles();
                    if (lst_Role != null) {
                        for (int i = 0; i < lst_Role.size(); i++) {
                            Object[] obj_role = (Object[]) lst_Role.get(i);
                            if (obj_role[0] != objUser[6]) {
                                out.print("<option value='" + obj_role[0] + "'>" + obj_role[1].toString() + "</option>");
                            }
                        }
                    } else {
                        out.print("<option>No se han encontrado Roles</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<input type='mail' class='form-control wdtFixe mr-3' name='TxtMail' id='TxtMail' value='" + objUser[8] + "' placeholder='Correo@plastitec-sa.com' data-toggle='tooltip' data-placement='top' title='Correo' required>");
                    out.print("<div class='wdtFixe' data-toggle='tooltip' data-placemente='top' title='Cargo'>");
                    out.print("<select class='form-control' name='CbxPosit' style='' required>");
                    out.print("<option value='" + objUser[9] + "'>" + objUser[9] + "</option>");
                    lst_Config = ConfigJpa.ConsultSettingsByCategorie("PositionUser");
                    if (lst_Config != null) {
                        Object[] obj_conf = (Object[]) lst_Config.get(0);
                        String[] Position = obj_conf[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int i = 0; i < Position.length; i++) {
                            if (Position[i] != objUser[9]) {
                                out.print("<option value='" + Position[i] + "'>" + Position[i] + "</option>");
                            }
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='mt-3' style='text-align: center;'>");
                    out.print("<button class='btn btn-blue'>Actualizar</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                } else {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Atencion! </h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<h3>Ha ocurrido un problema, favor comunicarse al area de TI.</h3>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER USER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Usuario</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='User?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='mb-3' style='display: flex;'>");
            out.print("<input type='text' class='form-control mr-3' name='TxtName' id='TxtName' placeholder='Nombre' data-toggle='tooltip' data-placement='top' title='Nombre' required>");
            out.print("<input type='text' class='form-control mr-3' name='TxtLst' id='TxtLst' placeholder='Apellido' data-toggle='tooltip' data-placement='top' title='Apellido' required>");
            out.print("<input type='number' class='form-control mr-3' name='TxtDoc' id='TxtDoc' placeholder='Nro. Documento' data-toggle='tooltip' data-placement='top' title='Nro. Documento' required>");
            out.print("<input type='text' class='form-control' name='TxtUser' id='TxtUser' placeholder='Usuario' data-toggle='tooltip' data-placement='top' title='Usuario' required>");
            out.print("</div>");
            out.print("<div class='' style='display: flex;'>");
            out.print("<div class='wdtFixe mr-3' data-toggle='tooltip' data-placemente='top' title='Rol'>");
            out.print("<select class='form-control' name='CbxRole' style='' required>");
            out.print("<option value=''>Seleccione rol</option>");
            lst_Role = RoleJpa.ConsultRoles();
            if (lst_Role != null) {
                for (int i = 0; i < lst_Role.size(); i++) {
                    Object[] obj_role = (Object[]) lst_Role.get(i);
                    out.print("<option value='" + obj_role[0] + "'>" + obj_role[1].toString() + "</option>");
                }
            } else {
                out.print("<option>No se han encontrado Roles</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("<input type='mail' class='form-control wdtFixe mr-3' name='TxtMail' id='TxtMail' placeholder='Correo@plastitec-sa.com' data-toggle='tooltip' data-placement='top' title='Correo' required>");
            out.print("<div class='wdtFixe' data-toggle='tooltip' data-placemente='top' title='Cargo'>");
            out.print("<select class='form-control' name='CbxPosit' style='' required>");
            out.print("<option value=''>Seleccione cargo</option>");
            lst_Config = ConfigJpa.ConsultSettingsByCategorie("PositionUser");
            if (lst_Config != null) {
                Object[] obj_conf = (Object[]) lst_Config.get(0);
                String[] Position = obj_conf[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < Position.length; i++) {
                    out.print("<option value='" + Position[i] + "'>" + Position[i] + "</option>");
                }
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='mt-3' style='text-align: center;'>");
            out.print("<button class='btn btn-blue'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Usuarios</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de usuarios</h4>");
            if (isAllowed) {
                out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registar'><i class='fas fa-plus' ></i></button>");
            } else {
                out.print("<span> </span>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr style='text-align: center;'>");
            out.print("<th>Nombre</th>");
            out.print("<th>Documento</th>");
            out.print("<th>Usuario</th>");
            out.print("<th>Contraseña</th>");
            out.print("<th>Rol</th>");
            out.print("<th>Correo</th>");
            out.print("<th>Cargo</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");

            if (isAllowed) {
                lst_User = UserJpa.ConsultUsers();
            } else {
                lst_User = UserJpa.ConsultClients();
            }

            if (lst_User != null) {
                for (int i = 0; i < lst_User.size(); i++) {
                    Object[] obj_user = (Object[]) lst_User.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_user[1] + "</td>");
                    out.print("<td>" + obj_user[2] + "</td>");
                    out.print("<td>" + obj_user[3] + "</td>");
                    String pss = obj_user[4].toString();
                    if (pss.length() == 4) {
                        out.print("<td>" + pss + "</td>");
                    } else {
                        out.print("<td>********</td>");
                    }
                    out.print("<td>" + obj_user[6] + "</td>");
                    out.print("<td>" + obj_user[7] + "</td>");
                    out.print("<td>" + obj_user[8] + "</td>");
                    int State = Integer.parseInt(obj_user[9].toString());
                    out.print("<td style='text-align: center;'><div class='badge badge-" + ((State == 1) ? "success'>Activo" : "danger'>Inactivo") + " </div></td>");
                    out.print("<td style='display: flex;justify-content: center;'>");
                    out.print("<button class='btn btn-dark btn-sm' type='button' id='dropdownMenubutton2' data-toggle='dropdown' aria-haspopup='true' aria-expended='false'><i class=\"fas fa-ellipsis-h\"></i></button>");
                    out.print("<div class='dropdown-menu'>\n");
                    if (isAllowed) {
                        out.print("<a class='dropdown-item has-icon' href='#' onclick=\"window.location.href='User?opt=4&IdUser=" + obj_user[0] + "&State=" + ((State == 1) ? "0" : "1") + "'\"><i class='fas fa-" + ((State == 0) ? "check" : "times") + "'></i> " + ((State == 0) ? "Activar usuario" : "Desactivar usuario") + "</a>");
                        out.print("<a class='dropdown-item has-icon' href='#' onclick=\"window.location.href='User?opt=1&IdUser=" + obj_user[0] + "'\"><i class='fas fa-pen'></i> Editar usuario</a>");
                    }
                    out.print("<a class='dropdown-item has-icon' href='#' onclick=\"window.location.href='User?opt=5&IdUser=" + obj_user[0] + "'\"><i class='fas fa-key'></i> Reestablecer contraseña</a>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
