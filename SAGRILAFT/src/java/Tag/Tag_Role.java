package Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.RoleControllerJpa;
import Controller.PermissionControllerJpa;

public class Tag_Role extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        PermissionControllerJpa PermissionJpa = new PermissionControllerJpa();
        List lst_Role = null, lst_roleId = null, lstPermission = null;
        int IdRole = 0, state = 0, IdPermissionRol = 0;
        String txtPermission = "", Modules = "", ConsModules = "";
        try {
            IdRole = Integer.parseInt(pageContext.getRequest().getAttribute("IdRole").toString());
            lst_roleId = RoleJpa.ConsultRoleId(1);
            Object[] obj_permi = (Object[]) lst_roleId.get(0);
            txtPermission = obj_permi[2].toString();
        } catch (Exception e) {
            IdRole = 0;
            txtPermission = "";
        }
        try {
            IdPermissionRol = Integer.parseInt(pageContext.getRequest().getAttribute("IdPermissionRol").toString());
        } catch (Exception e) {
            IdPermissionRol = 0;
        }
        try {
            if (IdRole > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE ROLE">
                lst_roleId = RoleJpa.ConsultRoleId(IdRole);
                if (lst_roleId != null) {
                    Object[] obj_updateRole = (Object[]) lst_roleId.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_role'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h5>Editar Rol " + obj_updateRole[1].toString() + "</h5>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Role?opt=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdRole' value='" + obj_updateRole[0] + "'> ");
                    out.print("<div class='' style='display: flex;'>");

                    out.print("<div class='col-lg-12 mb-2' >");
                    out.print("<input style='margin: 0;' type='text' class='form-control' name='Txt_Name' id='Txt_Name' placeholder='Nombre' value='" + obj_updateRole[1] + "' required='' data-toggle='tooltip' data-placement='top' title='Nombre Rol'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");



                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-blue btn-lg'>Editar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER ROLE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_role'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Registrar Rol</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Role?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='' style='display: flex;'>");
            out.print("<div class='col-lg-12 col-md-6 mb-2'>");
            out.print("<input type='text' class='form-control' name='Txt_Name' id='Txt_Name' placeholder='Nombre' required='' data-toggle='tooltip' data-placement='top' title='Nombre rol'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-blue btn-lg'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            if (IdPermissionRol > 0) {
                //<editor-fold defaultstate="collapsed" desc="PERMISSION">
                lst_roleId = RoleJpa.ConsultRoleId(IdPermissionRol);
                if (lst_roleId != null) {
                    Object[] obj_RolPermission = (Object[]) lst_roleId.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_role_permission'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Permisos</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Role?opt=4' method='post'>");
                    out.print("<input type='hidden' name='Cbx_permission' id='Cbx_permission' value='" + obj_RolPermission[2] + "'>");
                    out.print("<input type='hidden' value='" + IdPermissionRol + "' name='IdRole' id='IdRole'>");
                    out.print("<div class=\"card-body\">");
                    out.print("<div class=\"row\">");
                    out.print("<div class='col-12 col-sm-12 col-md-4' style='max-height:318px;'>");
                    out.print("<div class='scrollbar'>");
                    out.print("<ul class=\"nav nav-pills flex-column\" id=\"myTab4\" role=\"tablist\">");
                    //<editor-fold defaultstate="collapsed" desc="MODULES">
                    lstPermission = PermissionJpa.ConsultExistingPermission();
                    if (lstPermission != null) {
                        for (int i = 0; i < lstPermission.size(); i++) {
                            Object[] Obj_module = (Object[]) lstPermission.get(i);
                            String module = Obj_module[1].toString().replace(" ", "_").replace("-", "_");
                            out.print("<li class=\"nav-item\">");
                            out.print("<a class=\"nav-link " + ((i == 0) ? "active" : "") + " \" id=\"" + module + "-tab\" data-toggle=\"tab\" href=\"#" + module + "\" role=\"tab\" aria-controls=\"" + module + "\" aria-selected=\"true\">" + Obj_module[1] + "</a>");
                            out.print("</li>");
                            Modules += "[" + module + "]";
                            ConsModules += "[" + Obj_module[1] + "]";
                        }
                    } else {
                        out.print("<li class=\"nav-item\">");
                        out.print("<a class=\"nav-link active\" id=\"-tab\" data-toggle=\"tab\" href=\"#\" role=\"tab\" aria-controls=\"\" aria-selected=\"true\">Ha ocurrido un error, favor comunicarse a T.I</a>");
                        out.print("</li>");
                    }
                    //</editor-fold>
                    out.print("</ul>");
                    out.print("</div>");
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="PERMISSION LIST">
                    out.print("<div class=\"col-12 col-sm-12 col-md-8\">");
                    out.print("<div class=\"tab-content no-padding\" id=\"myTab2Content\">");
                    try {
                        String[] Arr_modules = Modules.replace("][", "//").replace("[", "").replace("]", "").split("//");
                        String[] Arr_modules_cons = ConsModules.replace("][", "//").replace("[", "").replace("]", "").split("//");
                        for (int i = 0; i < Arr_modules.length; i++) {
                            out.print("<div class='tab-pane fade " + ((i == 0) ? "show active" : "") + "' id='" + Arr_modules[i] + "' role='tabpanel' aria-labelledby='" + Arr_modules[i] + "-tab'>");
                            List lst_ficha = PermissionJpa.ConsultExistingPermissionOnly(Arr_modules_cons[i]);
                            out.print("<h4>Permisos " + Arr_modules_cons[i] + "</h4>");
                            if (lst_ficha != null) {
                                out.print("<div class='module_permss'>");
                                for (int j = 0; j < lst_ficha.size(); j++) {
                                    Object[] Obj_module = (Object[]) lst_ficha.get(j);
                                    if (obj_RolPermission[2].toString().contains("[" + Obj_module[0] + "]")) {
                                        out.print("<input type='checkbox' name='#' id='' value='" + Obj_module[0] + "' onclick='Masivo(this.value);' checked><span>" + Obj_module[2] + "</span><br>");
                                    } else {
                                        out.print("<input type='checkbox' name='#' id='' value='" + Obj_module[0] + "' onclick='Masivo(this.value);'><span>" + Obj_module[2] + "</span><br>");
                                    }
                                }
                                out.print("</div>");
                            } else {
                                out.print("<div class='' style='text-align: center;'>");
                                out.print("<h4 style='margin-top: 5%;'>Se ha producido un error al cargar los permisos, favor comunicarse con T.I</h4>");
                                out.print("<i class=\"fas fa-sad-tear\" style='font-size: 80px;'></i>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        }
                    } catch (Exception e) {
                        out.print("<div class='' style='text-align: center;'>");
                        out.print("<h4 style='margin-top: 5%;'>Se ha producido un error al cargar los permisos, favor comunicarse con T.I</h4>");
                        out.print("<i class=\"fas fa-sad-tear\" style='font-size: 80px;'></i>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
//</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-blue btn-lg'>Registrar</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Rol</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Rol</h4>");
            out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>Nombre</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Permisos</th>");
            out.print("<th style='text-align: center;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_Role = RoleJpa.ConsultRoles();
            if (lst_Role != null) {
                for (int i = 0; i < lst_Role.size(); i++) {
                    Object[] obj_role = (Object[]) lst_Role.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_role[0] + "</td>");
                    out.print("<td>" + obj_role[1] + "</td>");
                    state = Integer.parseInt(obj_role[3].toString());
                    out.print("<td>" + ((state == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    if (txtPermission.contains("[1]")) {
                        out.print("<td><a href='Role?opt=1&IdPermissionRol=" + obj_role[0] + "' style='background: #00c396' class='btn btn-permission btn-icon' data-toggle='tooltip' data-placement='top' title='Asignar permisos'><i class='fas fa-shield-alt'></i></a></td>");
                    } else {
                        out.print("<td><a href='#' style='background: #00c396; opacity: 0.5;' class='btn btn-permission btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-shield-alt'></i></a></td>");
                    }
                    out.print("<td class=\"text-center\">");
                    if (txtPermission.contains("[1]")) {
                        out.print("<a href='Role?opt=1&IdRole=" + obj_role[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermission.contains("[1]")) {
                        out.print("<a href='Role?opt=3&IdRole=" + obj_role[0] + "&State=" + state + "' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a style='opacity: 0.5;' href='#' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
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
            Logger.getLogger(Tag_Role.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
