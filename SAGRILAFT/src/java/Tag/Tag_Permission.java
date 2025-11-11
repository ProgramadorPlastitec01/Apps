package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.PermissionControllerJpa;
import java.util.List;

public class Tag_Permission extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        PermissionControllerJpa PermissionJpa = new PermissionControllerJpa();
        List lst_Permisssion = null, lst_PermissionExist = null, lst_PermissionId = null;
        int IdPermission = 0;
        try {
            IdPermission = Integer.parseInt(pageContext.getRequest().getAttribute("IdPermission").toString());
        } catch (Exception e) {
            IdPermission = 0;
        }
        try {
            if (IdPermission != 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE PERMISSION">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_form_permi'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Permisos</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                lst_PermissionId = PermissionJpa.ConsultExistingPermissionId(IdPermission);
                if (lst_PermissionId != null) {
                    Object[] obj_PermissionId = (Object[]) lst_PermissionId.get(0);
                    out.print("<div class='cont_form_temp'>");
                    out.print("<form action='Permission?opt=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdPermission' value='" + IdPermission + "'>");
                    out.print("<div class=''>");
                    out.print("<div class='col-12' style='display: flex; justify-content:space-between;'>");
                    out.print("<div style='width: 48%;' id='select2' data-toggle='tooltip' data-placement='top' title='Modulo'>");
                    out.print("<select class='select2' name='Txt_module' id='select2'>");
                    out.print("<option>" + obj_PermissionId[1] + "</option>");
                    lst_PermissionExist = PermissionJpa.ConsultExistingPermission();
                    for (int i = 0; i < lst_PermissionExist.size(); i++) {
                        Object[] obj_permExist = (Object[]) lst_PermissionExist.get(i);
                        if (!obj_PermissionId[1].equals(obj_permExist[1])) {
                            out.print("<option>" + obj_permExist[1].toString() + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</div>");
                    out.print("<div style='width: 48%;'>");
                    out.print("<input type='text' class='form-control' name='Txt_option' value='" + obj_PermissionId[2] + "' id='Txt_option' placeholder='Opcion' style='margin-bottom: 12px;' required data-toggle='tooltip' data-placement='top' title='Opcion'>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-12' style='margin-bottom: 12px;'>");
                    out.print("<input type='text' class='form-control' name='Txt_description' value='" + obj_PermissionId[3] + "' id='Txt_description' placeholder='Descripcion' required data-toggle='tooltip' data-placement='top' title='Descripcion'>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
                    out.print("<button class='btn btn-blue btn-lg'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                } else {

                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER PERMISSION">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_form_permi'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Permisos</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_temp'>");
            out.print("<form action='Permission?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<input type='hidden' name='State' value='1'>");
            out.print("<div class=''>");
            out.print("<div class='col-12' style='display: flex; justify-content:space-between;'>");
            out.print("<div style='width: 48%;' data-toggle='tooltip' data-placement='top' title='Modulo'>");
            out.print("<select class='form-control select2 is-valid' name='Txt_module' id='select2' required>");
            out.print("<option selected disabled value=''>Seleccione modulo...</option>");
            lst_PermissionExist = PermissionJpa.ConsultExistingPermission();
            for (int i = 0; i < lst_PermissionExist.size(); i++) {
                Object[] obj_permExist = (Object[]) lst_PermissionExist.get(i);
                out.print("<option>" + obj_permExist[1].toString() + "</option>");
            }
            out.print("</select>");
            out.print("</div>");
            out.print("<div style='width: 48%;'>");
            out.print("<input type='text' class='form-control' name='Txt_option' id='Txt_option' placeholder='Opcion' style='margin-bottom: 12px;' required data-toggle='tooltip' data-placement='top' title='Opcion'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-12' style='margin-bottom: 12px;'>");
            out.print("<input type='text' class='form-control' name='Txt_description' id='Txt_code' placeholder='Descripcion' required data-toggle='tooltip' data-placement='top' title='Descripcion'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
            out.print("<button class='btn btn-blue btn-lg'>Registrar</button>");
            out.print("</div>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Permisos</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de permisos</h4>");
            out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registar'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr style='text-align: center;'>");
            out.print("<th>Id</th>");
            out.print("<th>Modulo</th>");
            out.print("<th>Opcion</th>");
            out.print("<th>Descripción</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_Permisssion = PermissionJpa.ConsultPermission();
            if (lst_Permisssion != null) {
                for (int i = 0; i < lst_Permisssion.size(); i++) {
                    Object[] obj_permission = (Object[]) lst_Permisssion.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_permission[0] + "</td>");
                    out.print("<td>" + obj_permission[1] + "</td>");
                    out.print("<td>" + obj_permission[2] + "</td>");
                    out.print("<td>" + obj_permission[3] + "</td>");
                    int State = Integer.parseInt(obj_permission[4].toString());
                    out.print("<td style='text-align: center;'><div class='badge badge-" + ((State == 1) ? "success'>Activo" : "danger'>Inactivo") + " </div></td>");
                    out.print("<td style='display: flex;justify-content: center;'>");
                    out.print("<button onclick=\"window.location.href='Permission?opt=1&IdPermission=" + obj_permission[0] + "'\" class='btn btn-warning mr-2' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></button>");
                    out.print("<button onclick=\"window.location.href='Permission?opt=3&IdPermission=" + obj_permission[0] + "&State="+ ((State == 1) ? "0" : "1") +"'\" class='btn btn-" + ((State == 1) ? "success" : "danger") + " mr-2' data-toggle='tooltip' data-placement='top' title='" + ((State == 1) ? "Activo" : "Inactivo") + "'><i class='fas fa-" + ((State == 1) ? "check" : "times") + "'></i></button>");
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
            Logger.getLogger(Tag_Permission.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
