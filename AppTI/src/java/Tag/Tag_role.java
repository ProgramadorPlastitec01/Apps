package Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.RoleControllerJpa;
import java.io.IOException;

public class Tag_role extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_role = null;
        List lst_role_id = null;
        List lst_module = null;
        List lst_permission = null;
        List lst_permissionId = null;
        int IdRole = 0, state = 0, IdRolePermission = 0, idRolSession = 0, IdPermission = 0;
        String txtPermissions = "", Access = "";
        try {
            try {
                Access = pageContext.getRequest().getAttribute("Access").toString();
            } catch (Exception e) {
                Access = "Role";
            }
            try {
                idRolSession = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                lst_role = RoleJpa.ConsultRoleId(idRolSession);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                txtPermissions = obj_permi[2].toString();
            } catch (Exception e) {
                idRolSession = 0;
                txtPermissions = "";
            }
            if (Access.equals("Role")) {
                //<editor-fold defaultstate="collapsed" desc="MODULE ROLE">
                try {
                    IdRole = Integer.parseInt(pageContext.getRequest().getAttribute("idRole").toString());
                } catch (NumberFormatException e) {
                    IdRole = 0;
                }
                try {
                    IdRolePermission = Integer.parseInt(pageContext.getRequest().getAttribute("IdRolePermission").toString());
                } catch (NumberFormatException e) {
                    IdRolePermission = 0;
                }
                //<editor-fold defaultstate="collapsed" desc="ROL REGISTRER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar Rol</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Role?opt=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='' style='display: flex;'>");
                out.print("<div class='col-lg-11 col-md-6'>");
                out.print("<input type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' required='' data-toggle='tooltip' data-placement='top' title='Nombre rol'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                if (IdRole > 0) {
                    //<editor-fold defaultstate="collapsed" desc="UPDATE ROLE">
                    lst_role_id = RoleJpa.ConsultRoleId(IdRole);
                    if (lst_role_id != null) {
                        Object[] obj_updateRole = (Object[]) lst_role_id.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4>Editar Rol " + obj_updateRole[1].toString() + "</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Role?opt=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='idRole' value='" + obj_updateRole[0] + "'> ");
                        out.print("<div class='' style='display: flex;'>");

                        out.print("<div class='col-lg-12'>");

                        out.print("<div class='col-lg-11'>");
                        out.print("<input style='margin: 0;' type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' value='" + obj_updateRole[1] + "' required='' data-toggle='tooltip' data-placement='top' title='Nombre Rol'>");
                        out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-11'>");
                        out.print("<label class='custom-switch mt-2 mb-2' style='' onclick='SwitchValue()'>");
                        state = Integer.parseInt(obj_updateRole[3].toString());
                        out.print("<span class='custom-switch-description'>Estado del Rol &nbsp;&nbsp;</span>");
                        out.print("<input type='checkbox' name='state' class='custom-switch-input' id='State' value='" + state + "' " + ((state == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                        out.print("<span class='custom-switch-indicator'></span>");
                        out.print("</label>");
                        out.print("</div>");

                        out.print("</div>");

                        out.print("</div>");

                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                        out.print("</div>");

                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                }
                if (IdRolePermission > 0) {
                    //<editor-fold defaultstate="collapsed" desc="ASSIGN PERMISSION">
                    lst_role_id = RoleJpa.ConsultRoleId(IdRolePermission);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Permisos</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    if (lst_role_id != null) {
                        Object[] obj_rol_permission = (Object[]) lst_role_id.get(0);
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Role?opt=4' method='post'>");
                        out.print("<input type='hidden' name='Cbx_permission' id='Cbx_permission' value='" + obj_rol_permission[2] + "'>");
                        out.print("<input type='hidden' value='" + IdRolePermission + "' name='idRole' id='idRole'>");
                        out.print("<div class=\"card-body\">");
                        out.print("<div class=\"row\">");
                        out.print("<div class='col-12 col-sm-12 col-md-4' style='max-height:318px;'>");
                        out.print("<div class='scrollbar'>");
                        out.print("<ul class=\"nav nav-pills flex-column\" id=\"myTab4\" role=\"tablist\">");
                        //<editor-fold defaultstate="collapsed" desc="MODULES">
                        lst_module = RoleJpa.ConsultPermisssionModuleActive();
                        String modules = "", cons_modules = "";
                        if (lst_module != null) {
                            for (int i = 0; i < lst_module.size(); i++) {
                                Object[] Obj_module = (Object[]) lst_module.get(i);
                                String module = Obj_module[1].toString().replace(" ", "_").replace("-", "_");
                                out.print("<li class=\"nav-item\">");
                                out.print("<a class=\"nav-link " + ((i == 0) ? "active" : "") + " \" id=\"" + module + "-tab\" data-toggle=\"tab\" href=\"#" + module + "\" role=\"tab\" aria-controls=\"" + module + "\" aria-selected=\"true\">" + Obj_module[1] + "</a>");
                                out.print("</li>");
                                modules += "[" + module + "]";
                                cons_modules += "[" + Obj_module[1] + "]";
                            }
                        } else {
                            out.print("<li class=\"nav-item\">");
                            out.print("<a class=\"nav-link active\" id=\"-tab\" data-toggle=\"tab\" href=\"#\" role=\"tab\" aria-controls=\"\" aria-selected=\"true\">Ha ocurrido un error, favor comunicarse a T.I</a>");
                            out.print("</li>");
                        }
                        out.print("</ul>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="PERMISSION LIST">
                        out.print("<div class=\"col-12 col-sm-12 col-md-8\">");
                        out.print("<div class=\"tab-content no-padding\" id=\"myTab2Content\">");
                        try {
                            String[] Arr_modules = modules.replace("][", "//").replace("[", "").replace("]", "").split("//");
                            String[] Arr_modules_cons = cons_modules.replace("][", "//").replace("[", "").replace("]", "").split("//");
                            for (int i = 0; i < Arr_modules.length; i++) {
                                out.print("<div class='tab-pane fade " + ((i == 0) ? "show active" : "") + "' id='" + Arr_modules[i] + "' role='tabpanel' aria-labelledby='" + Arr_modules[i] + "-tab'>");
                                List lst_ficha = RoleJpa.ConsultPermisssionXModule(Arr_modules_cons[i]);
                                out.print("<h4>Permisos " + Arr_modules_cons[i] + "</h4>");
                                if (lst_ficha != null) {
                                    out.print("<div class='module_permss'>");
                                    for (int j = 0; j < lst_ficha.size(); j++) {
                                        Object[] Obj_module = (Object[]) lst_ficha.get(j);
                                        if (obj_rol_permission[2].toString().contains("[" + Obj_module[0] + "]")) {
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
                        } catch (IOException e) {
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
                        if (lst_module != null) {
                            out.print("<div class='' style='width: 100%; text-align:center;'>");
                            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                            out.print("</div>");
                        }
                        out.print("</form>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                out.print("<section class='section'>");
                out.print("<div class=\"row\">");
                out.print("<div class=\"col-12\">");
                out.print("<div class=\"card\">");
                out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
                out.print("<div class='d-flex'>"
                        + "<div class='mr-2'>"
                        + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" >"
                        + "<i class=\"far fa-hand-point-left\"></i>"
                        + "</button>"
                        + "</div>"
                        + "<h4>Módulo Rol</h4>"
                        + "</div>");

                out.print("<div style='display:flex;justify-content: space-between;width: 10%;'>");

                out.print("<div>");
                if (txtPermissions.contains("[7]")) {
                    out.print("<button class='btn btn-info' style='border-radius: 4px;' onclick=\"javascript:location.href='Role?opt=1&Access=Permission'\"  data-toggle='tooltip' data-placement='top' title='Módulo Permisos'><i class='fas fa-shield-alt'></i></button>");
                } else {
                    out.print("<button class='btn btn-info' style='border-radius: 4px;opacity: 0.5' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-shield-alt'></i></button>");
                }
                out.print("</div>");

                out.print("<div>");
                if (txtPermissions.contains("[7]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                } else {
                    out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.5' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
                }
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");

                //<editor-fold defaultstate="collapsed" desc="CONSULT ROLE">
                out.print("<div class=\"card-body\">");
                out.print("<div class=\"table-responsive\">");
                out.print("<table class=\"table table-striped\" id=\"table-1\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th class=\"text-center\">Id</th>");
                out.print("<th>Nombre</th>");
                out.print("<th>Permisos</th>");
                out.print("<th>Estado</th>");
                out.print("<th class=\"text-center\">Opc</th>");
                out.print("</tr>");
                out.print("</thead>");

                out.print("<tbody>");
                lst_role = RoleJpa.ConsultRole();
                if (lst_role != null) {
                    for (int i = 0; i < lst_role.size(); i++) {
                        Object[] obj_role = (Object[]) lst_role.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_role[0] + "</td>");
                        out.print("<td>" + obj_role[1] + "</td>");
                        if (txtPermissions.contains("[10]")) {
                            out.print("<td><a href='Role?opt=1&IdRolePermission=" + obj_role[0] + "' style='background: #00c396' class='btn btn-permission btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Asignar permisos'><i class='fas fa-user-shield'></i></a></td>");
                        } else {
                            out.print("<td><a href='#' style='background: #00c396; opacity: 0.5;' class='btn btn-permission btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-shield-alt'></i></a></td>");
                        }
                        state = Integer.parseInt(obj_role[3].toString());
                        out.print("<td>" + ((state == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                        out.print("<td class=\"text-center\">");
                        if (txtPermissions.contains("[8]")) {
                            out.print("<a href='Role?opt=1&idRole=" + obj_role[0] + "' style='background: orange;' class='btn btn-warning btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                        } else {
                            out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                        }
                        if (txtPermissions.contains("[9]")) {
                            out.print("<a href='Role?opt=3&idRole=" + obj_role[0] + "&state=" + state + "' class='btn btn-" + ((state == 1) ? "success" : "danger") + " btn-sm' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                        } else {
                            out.print("<a style='opacity: 0.5;' href='#' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr><td colspan='5'></td aling='center;'>No existe rol(es) registrados</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else {
                //<editor-fold defaultstate="collapsed" desc="MODULE PERMISSION">
                try {
                    IdPermission = Integer.parseInt(pageContext.getRequest().getAttribute("IdPermission").toString());
                } catch (NumberFormatException e) {
                    IdPermission = 0;
                }
                //<editor-fold defaultstate="collapsed" desc="PERMISSION REGISTER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar Permisos</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<form action='Role?opt=5' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='col-12' style='display: flex; justify-content:space-between;'>");
                out.print("<div style='width: 48%;' data-toggle='tooltip' data-placement='top' title='Modulo'>");
                out.print("<input class=\"form-control\" name='Txt_module' list=\"dataListModule\" id=\"\" placeholder=\"Seleccionar módulo\">");
                out.print("<datalist id='dataListModule'>");
                lst_module = RoleJpa.ConsultPermisssionModuleActive();
                for (int i = 0; i < lst_module.size(); i++) {
                    Object[] obj_module = (Object[]) lst_module.get(i);
                    out.print("<option value='" + obj_module[1].toString() + "'></option>");
                }
                out.print("</datalist>");
                out.print("</div>");
                out.print("<div style='width: 48%;'>");
                out.print("<input type='text' class='form-control' name='Txt_option' id='Txt_option' placeholder='Opcion' style='margin-bottom: 12px;' required data-toggle='tooltip' data-placement='top' title='Opcion' autocomplete='off' >");
                out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-12' style='margin-bottom: 12px;'>");
                out.print("<input type='text' class='form-control' name='Txt_description' id='Txt_description' autocomplete='off' placeholder='Descripcion' required data-toggle='tooltip' data-placement='top' title='Descripcion'>");
                out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
//</editor-fold>
                if (IdPermission > 0) {
                    //<editor-fold defaultstate="collapsed" desc="PERMISSION EDIT">
                    lst_permissionId = RoleJpa.ConsultPermissionId(IdPermission);
                    if (lst_permissionId != null) {
                        Object[] obj_permissId = (Object[]) lst_permissionId.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral'>");

                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Editar Permiso</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");

                        out.print("<form action='Role?opt=5&IdPermission=" + obj_permissId[0] + "' method='post' class='needs-validation' novalidate=''>");

                        out.print("<div class='col-12' style='display: flex;margin-top: 10px;'>");
                        out.print("<div class='col-6' style='width: 100%;' id='select2' data-toggle='tooltip' data-placement='top' title='Modulo'>");
                        out.print("<input class=\"form-control\" name='Txt_module' list=\"dataListModule\" id=\"\"  value='" + obj_permissId[1] + "' placeholder=\"Seleccionar módulo\">");
                        out.print("<datalist id='dataListModule'>");
                        lst_module = RoleJpa.ConsultPermisssionModuleActive();
                        for (int i = 0; i < lst_module.size(); i++) {
                            Object[] obj_module = (Object[]) lst_module.get(i);
                            out.print("<option value='" + obj_module[1].toString() + "'></option>");
                        }
                        out.print("</datalist>");
                        out.print("</div>");
                        out.print("<div class='col-6'>");
                        out.print("<input type='text' class='form-control' name='Txt_option' id='Txt_option' placeholder='Opcion' value='" + obj_permissId[2] + "' required data-toggle='tooltip' data-placement='top' title='Opcion'>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='col-12' style='display: flex;'>");

                        out.print("<div class='col-12'>");
                        out.print("<input type='text' class='form-control' name='Txt_description' id='Txt_description' placeholder='Descripcion' value='" + obj_permissId[3] + "' required data-toggle='tooltip' data-placement='top' title='Descripcion'>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");

                        out.print("<script>"
                                + " document.addEventListener('DOMContentLoaded', function() {"
                                + "    function toggleClass() {"
                                + "        const body = document.body;"
                                + "        body.classList.add('modal-open');"
                                + "    }"
                                + "    toggleClass();"
                                + " });"
                                + "</script>");
                    } else {
                        out.print("<script type='text/javascript'>");
                        out.print("$(\"#toastr-4\").ready(function() {\n"
                                + "  iziToast.error({\n"
                                + "    title: 'Error',\n"
                                + "    message: 'Ha ocurrido un problema en el registro.',\n"
                                + "    position: 'topRight'\n"
                                + "  });\n"
                                + "});");
                        out.print("</script>");
                        out.print("<script>"
                                + " document.addEventListener('DOMContentLoaded', function() {"
                                + "    function toggleClass() {"
                                + "        const body = document.body;"
                                + "        body.classList.add('modal-open');"
                                + "    }"
                                + "    toggleClass();"
                                + " });"
                                + "</script>");
                    }
                    //</editor-fold>
                }
                out.print("<section class='section'>");
                out.print("<div class=\"row\">");
                out.print("<div class=\"col-12\">");
                out.print("<div class=\"card\">");
                out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
                out.print("<div style='display:flex;'>");
                out.print("<div><a class='btn btn-green btn-sm LeftArrowReturn' href='Role?opt=1&Access=Role'><i class=\"fas fa-arrow-left\"></i></a></div>");
                out.print("&nbsp;&nbsp;&nbsp;&nbsp;<div><h4>Módulo Permiso</h4></div>");
                out.print("</div>");
                if (txtPermissions.contains("[12]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;'  onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
                } else {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
                }
                out.print("</div>");

                //<editor-fold defaultstate="collapsed" desc="CONSULT PERMISSSION">
                out.print("<div class=\"card-body\">");
                out.print("<div class=\"table-responsive\">");
                out.print("<table class=\"table table-striped\" id=\"table-1\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th class=\"text-center\">Id</th>");
                out.print("<th>Módulo</th>");
                out.print("<th>Opción</th>");
                out.print("<th>Descripción</th>");
                out.print("<th>Estado</th>");
                out.print("<th class=\"text-center\">Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_permission = RoleJpa.ConsultPermission();
                if (lst_permission != null || !lst_permission.isEmpty()) {
                    for (int i = 0; i < lst_permission.size(); i++) {
                        Object[] obj_permis = (Object[]) lst_permission.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_permis[0] + "</td>");
                        out.print("<td>" + obj_permis[1] + "</td>");
                        out.print("<td>" + obj_permis[2] + "</td>");
                        out.print("<td>" + obj_permis[3] + "</td>");
                        state = Integer.parseInt(obj_permis[4].toString());
                        out.print("<td align='center'>" + ((state == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                        out.print("<td align='center' style='display:flex;'>");
                        if (txtPermissions.contains("[13]")) {
                            out.print("<a href='Role?opt=1&Access=Permission&IdPermission=" + obj_permis[0] + "' style='background: orange;' class='btn btn-warning btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                        } else {
                            out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                        }
                        if (txtPermissions.contains("[14]")) {
                            out.print("<a href='Role?opt=6&IdPermission=" + obj_permis[0] + "&state=" + state + "' class='btn btn-" + ((state == 1) ? "success" : "danger") + " btn-sm' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                        } else {
                            out.print("<a href='#' style='opacity: 0.5;' class='btn btn-" + ((state == 1) ? "success" : "danger") + " btn-sm' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='6'>No se han encontrado datos</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_role.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
