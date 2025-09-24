package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.PermisosJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controladores.RolJpaController;

public class Tag_permissions extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int est = 0, id_user = 0;
        PermisosJpaController PermissionJpa = new PermisosJpaController();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_permission = null;
        List lst_roll = null;
        int id_permiss = 0, UserRol = 0;
        String txtPermisos = "";
        try {
            try {
                id_permiss = Integer.parseInt(pageContext.getRequest().getAttribute("id_permiss").toString());
            } catch (Exception e) {
                id_permiss = 0;
            }
            try {
                UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
                lst_roll = RoleJpa.Consult_role_id(UserRol);
                Object[] obj_permi = (Object[]) lst_roll.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                UserRol = 0;
                txtPermisos = "";
            }
            if (id_permiss > 0) {
                //<editor-fold defaultstate="collapsed" desc="PERMISSION EDIT">
                lst_permission = PermissionJpa.Consult_permissions_id(id_permiss);
                if (lst_permission != null) {
                    Object[] obj_permiss = (Object[]) lst_permission.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_form_permi'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Editar Permisos</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_temp'>");
                    out.print("<form action='Permissions?opc=2&id_permiss=" + obj_permiss[0] + "' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class=''>");
                    out.print("<div class='col-12' style='display: flex;margin-top: 10px;'>");
                    out.print("<div class='col-6' style='width: 100%;' id='select2' data-toggle='tooltip' data-placement='top' title='Modulo'>");
                    out.print("<select class='select2' name='Txt_module' id='select2'>");
                    out.print("<option value='" + obj_permiss[1] + "'>" + obj_permiss[1] + "</option>");
                    lst_permission = PermissionJpa.Consult_ExistPermissions();
                    for (int i = 0; i < lst_permission.size(); i++) {
                        Object[] obj_perm = (Object[]) lst_permission.get(i);
                        out.print("<option>" + obj_perm[1].toString() + "</option>");
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("&nbsp;&nbsp;&nbsp;");
                    out.print("<div class='col-6'>");
                    out.print("<input type='text' class='form-control' name='Txt_option' id='Txt_option' placeholder='Opcion' value='" + obj_permiss[2] + "' required data-toggle='tooltip' data-placement='top' title='Opcion'>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-12' style='display: flex;'>");
                    out.print("<div class='col-8'>");
                    out.print("<input type='text' class='form-control' name='Txt_description' id='Txt_description' placeholder='Descripcion' value='" + obj_permiss[3] + "' required data-toggle='tooltip' data-placement='top' title='Descripcion'>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-4'>");
                    out.print("<label class='custom-switch mt-2'>");
                    est = Integer.parseInt(obj_permiss[4].toString());
                    out.print("<span class='custom-switch-description'>Estado del permiso &nbsp;&nbsp;</span>");
                    out.print("<input style='' type='checkbox' class='custom-switch-input' value='" + est + "' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                    out.print("<span class='custom-switch-indicator'></span>");
                    out.print("</label>");
                    out.print("<input type='hidden' name='Nmb_est' id='Nmb_est' value='" + est + "'>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
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
                }
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="PERMISSION REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana66' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_form_permi'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Permisos</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(66)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_temp'>");
            out.print("<form action='Permissions?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class=''>");
            out.print("<div class='col-12' style='display: flex; justify-content:space-around;'>");
            out.print("<div style='width: 45%;' id='select2' data-toggle='tooltip' data-placement='top' title='Modulo'>");
            out.print("<select class='select2' name='Txt_module' id='select2'>");
            out.print("<option>Seleccione modulo...</option>");
            lst_permission = PermissionJpa.Consult_ExistPermissions();
            for (int i = 0; i < lst_permission.size(); i++) {
                Object[] obj_perm = (Object[]) lst_permission.get(i);
                out.print("<option>" + obj_perm[1].toString() + "</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("<div style='width: 45%;'>");
//            out.print("<input type='text' class='form-control' nasme='Txt_module' id='Txt_module' placeholder='Module' required >");
            out.print("<input type='text' class='form-control' name='Txt_option' id='Txt_option' placeholder='Opcion' style='margin-bottom: 12px;' required data-toggle='tooltip' data-placement='top' title='Opcion'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-12' style='margin-bottom: 12px;'>");
            out.print("<input type='text' class='form-control' name='Txt_description' id='Txt_code' placeholder='Descripcion' required data-toggle='tooltip' data-placement='top' title='Descripcion'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN TABLE">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Permisos</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Permisos</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (txtPermisos.contains("[13]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(66)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }else{
                out.print("<button class='btn btn-green' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos' ><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Modulo</th>");
            out.print("<th>Opcion</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center;min-width: 120px;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_permission = PermissionJpa.Consult_Allpermissions();
            if (lst_permission != null || lst_permission.size() != 0) {
                for (int i = 0; i < lst_permission.size(); i++) {
                    Object[] obj_permis = (Object[]) lst_permission.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_permis[1] + "</td>");
                    out.print("<td>" + obj_permis[2] + "</td>");
                    out.print("<td>" + obj_permis[3] + "</td>");
                    est = Integer.parseInt(obj_permis[4].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    out.print("<td align='center'>");
                    if (txtPermisos.contains("[14]")) {
                        out.print("<a href='Permissions?opc=1&id_permiss=" + obj_permis[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[15]")) {
                        out.print("<a href='Permissions?opc=3&id_permiss=" + obj_permis[0] + "&est=" + est + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5;' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
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
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
//</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_permissions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
