package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.RolJpaController;
import Controladores.PermisosJpaController;
import java.util.List;

import Controladores.RolJpaController;

public class Tag_role extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        RolJpaController RolJpa = new RolJpaController();
        PermisosJpaController PermisosJpa = new PermisosJpaController();
        List lst_role = null;
        List lst_role_id = null;
        int est = 0, id_rol = 0, state = 0, id_rol_permission = 0, rol_permission = 0, id_permission = 0, idRol = 0;
        String txtPermisos = "";
        try {
            try {
                id_rol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
            } catch (Exception e) {
                id_rol = 0;
            }
            try {
                idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
                lst_role = RolJpa.Consult_role_id(idRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                id_rol = 0;
                txtPermisos = "";
            }
            try {
                id_rol_permission = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol_permission").toString());
            } catch (Exception e) {
                id_rol_permission = 0;
            }
            if (id_rol > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE ROLE">
                lst_role_id = RolJpa.Consult_role_id(id_rol);
                if (lst_role_id != null) {
                    Object[] obj_updateRole = (Object[]) lst_role_id.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_role_update'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Editar Rol " + obj_updateRole[1].toString() + "</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Role?opc=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='id_rol' value='" + obj_updateRole[0] + "'> ");
                    out.print("<div class='' style='display: flex;'>");

                    out.print("<div class='col-lg-12'>");
                    out.print("<div class='col-lg-12'>");
                    out.print("<input style='margin: 0;' type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' value='" + obj_updateRole[1] + "' required='' data-toggle='tooltip' data-placement='top' title='Nombre Rol'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-12'>");
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
            //<editor-fold defaultstate="collapsed" desc="ROL REGISTRER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_role'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Registrar Rol</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Role?opc=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='' style='display: flex;'>");
            out.print("<div class='col-lg-9 col-md-6'>");
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
            if (id_rol_permission > 0) {
                //<editor-fold defaultstate="collapsed" desc="PERMISSIONS">
                lst_role_id = RolJpa.Consult_role_id(id_rol_permission);
                Object[] obj_rol_permission = (Object[]) lst_role_id.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_role_permission'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Permisos</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Role?opc=4' method='post'>");
                out.print("<input type='hidden' name='Cbx_permission' id='Cbx_permission' value='" + obj_rol_permission[2] + "'>");
                out.print("<input type='hidden' value='" + id_rol_permission + "' name='id_rol' id='id_rol'>");
                out.print("<div class=\"card-body\">");
                out.print("<div class=\"row\">");
                out.print("<div class='col-12 col-sm-12 col-md-4' style='max-height:318px;'>");
                out.print("<div class='scrollbar'>");
                out.print("<ul class=\"nav nav-pills flex-column\" id=\"myTab4\" role=\"tablist\">");
                //<editor-fold defaultstate="collapsed" desc="MODULES">
                lst_role = RolJpa.Consult_Modules();
                String modules = "", cons_modules = "";
                if (lst_role != null) {
                    for (int i = 0; i < lst_role.size(); i++) {
                        Object[] Obj_module = (Object[]) lst_role.get(i);
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
//                out.print(modules);
//                out.print(cons_modules);
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
                        List lst_ficha = PermisosJpa.Consult_permissions_only(Arr_modules_cons[i]);
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
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Rol</h1>");
            out.print("</div>");
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-12\">");
            out.print("<div class=\"card\">");
            out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
            out.print("<h4>Listado de Rol</h4>");
            if (txtPermisos.contains("[20]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"table-responsive\">");
            //<editor-fold defaultstate="collapsed" desc="CONSULT TABLE">
            out.print("<table class=\"table table-striped\" id=\"table-1\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class=\"text-center\">Id</th>");
            out.print("<th>Nombre</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Permisos</th>");
            out.print("<th class=\"text-center\">Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody> ");
            lst_role = RolJpa.Consult_role();
            if (lst_role != null || lst_role.isEmpty()) {
                for (int i = 0; i < lst_role.size(); i++) {
                    Object[] obj_role = (Object[]) lst_role.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_role[0] + "</td>");
                    out.print("<td>" + obj_role[1] + "</td>");
                    state = Integer.parseInt(obj_role[3].toString());
                    out.print("<td>" + ((state == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");

                    if (txtPermisos.contains("[23]")) {
                        out.print("<td><a href='Role?opc=1&id_rol_permission=" + obj_role[0] + "' style='background: #00c396' class='btn btn-permission btn-icon' data-toggle='tooltip' data-placement='top' title='Asignar permisos'><i class='fas fa-shield-alt'></i></a></td>");
                    } else {
                        out.print("<td><a href='#' style='background: #00c396; opacity: 0.5;' class='btn btn-permission btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-shield-alt'></i></a></td>");
                    }
                    out.print("<td class=\"text-center\">");
                    if (txtPermisos.contains("[21]")) {
                        out.print("<a href='Role?opc=1&id_rol=" + obj_role[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[22]")) {
                        out.print("<a href='Role?opc=3&id_rol=" + obj_role[0] + "&state=" + state + "' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a style='opacity: 0.5;' href='#' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

        } catch (Exception ex) {
            Logger.getLogger(Tag_role.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
