package Tag;

import Controller.RoleControllerJpa;
import Controller.AppControllerJpa;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Tag_application extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        try {
            RoleControllerJpa RoleJpa = new RoleControllerJpa();
            AppControllerJpa AppJpa = new AppControllerJpa();
            int IdRol = 0, IdApplication = 0;
            String txtPermissions = "";
            List lst_role = null, lst_app = null, lst_appId = null;
            try {
                IdRol = Integer.parseInt(pageContext.getRequest().getAttribute("IdRol").toString());
                lst_role = RoleJpa.ConsultRoleId(IdRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                txtPermissions = obj_permi[2].toString();
            } catch (NumberFormatException e) {
                IdRol = 0;
                txtPermissions = "";
            }
            try {
                IdApplication = Integer.parseInt(pageContext.getRequest().getAttribute("IdApplication").toString());
            } catch (NumberFormatException e) {
                IdApplication = 0;
            }

            if (IdApplication > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE APPLICATION">
                lst_appId = AppJpa.ConsultAppIdActive(IdApplication);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Modificar Aplicacion</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                if (lst_appId != null) {
                    Object[] ObjAppId = (Object[]) lst_appId.get(0);

                    out.print("<form action='Application?opt=2' method='post' name='FormPending' id='FormPending' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdApplication' value='" + IdApplication + "' id='IdApplication'>");

                    out.print("<div class='col-12 mb-2'>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_name' id='Txt_name' placeholder='Nombre aplicación'  data-toggle='tooltip' data-placement='top' title='Nombre aplicación' value='" + ObjAppId[1] + "' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='mb-3' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

                    out.print("<div class='col-6'>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_owner' id='Txt_owner' placeholder='Responsable'  data-toggle='tooltip' data-placement='top' title='Responsable' value='" + ObjAppId[2] + "' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-6'>");
                    out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_protocol' id='Txt_protocol' placeholder='Protocolo'  data-toggle='tooltip' data-placement='top' value='" + ObjAppId[3] + "' title='Protocolo' required autocomplete='off' >");
                    out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Modificar</button>");
                    out.print("</div>");

                    out.print("</form>");
                }

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER APPLICATION">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Registrar Aplicacion</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Application?opt=2' method='post' name='FormPending' id='FormPending' class='needs-validation' novalidate=''>");

            out.print("<div class='col-12 mb-2'>");
            out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_name' id='Txt_name' placeholder='Nombre aplicación'  data-toggle='tooltip' data-placement='top' title='Nombre aplicación' required autocomplete='off' >");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='mb-3' style='display: flex;justify-content: space-around;  align-items: baseline;'>");

            out.print("<div class='col-6'>");
            out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_owner' id='Txt_owner' placeholder='Responsable'  data-toggle='tooltip' data-placement='top' title='Responsable' required autocomplete='off' >");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-6'>");
            out.print("<input type='text' class='form-control' onchange='javascript:this.value=this.value.toUpperCase();' name='Txt_protocol' id='Txt_protocol' placeholder='Protocolo'  data-toggle='tooltip' data-placement='top' title='Protocolo' required autocomplete='off' >");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div class='d-flex'>"
                    + "<div class='mr-2'>"
                    + "<button class='btn btn-outline-primary btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" >"
                    + "<i class=\"far fa-hand-point-left\"></i>"
                    + "</button>"
                    + "</div>"
                    + "<h4>Listado de aplicaciones</h4>"
                    + "</div>");
            if (txtPermissions.contains("[34]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.7;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>Aplicacion</th>");
            out.print("<th>Responsable</th>");
            out.print("<th>Protocolo</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_app = AppJpa.ConsulteApp();
            if (lst_app != null) {
                for (int i = 0; i < lst_app.size(); i++) {
                    Object[] ObjApp = (Object[]) lst_app.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjApp[0] + "</td>");
                    out.print("<td>" + ObjApp[1] + "</td>");
                    out.print("<td>" + ObjApp[2] + "</td>");
                    out.print("<td>" + ObjApp[3] + "</td>");
                    int State = Integer.parseInt(ObjApp[4].toString());
                    out.print("<td>" + ((State == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    out.print("<td class=\"text-center\">");
                    if (txtPermissions.contains("[35]")) {
                        out.print("<a href='Application?opt=1&IdApplication=" + ObjApp[0] + "' style='background: orange;' class='btn btn-warning btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon btn-sm' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermissions.contains("[36]")) {
                        out.print("<a href='Application?opt=3&IdApplication=" + ObjApp[0] + "&State=" + State + "' class='btn btn-" + ((State == 1) ? "success" : "danger") + " btn-sm' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((State == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a style='opacity: 0.5;' href='#' class='btn btn-" + ((State == 1) ? "success" : "danger") + " btn-sm' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((State == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_application.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
