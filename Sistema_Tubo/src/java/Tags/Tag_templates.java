package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.PlantillaJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controladores.RolJpaController;

public class Tag_templates extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        int est = 0, id_user = 0;

        PlantillaJpaController TemplateJpa = new PlantillaJpaController();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_template = null;
        List lst_roll = null;
        int id_templ = 0, UserRol = 0;
        String fto = "", txtPermisos = "";

        try {
            try {
                id_templ = Integer.parseInt(pageContext.getRequest().getAttribute("id_temp").toString());
            } catch (Exception e) {
                id_templ = 0;
            }
            try {
                fto = pageContext.getRequest().getAttribute("fto").toString();
            } catch (Exception e) {
                fto = "";
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

            //<editor-fold defaultstate="collapsed" desc="REGISTRO DE PLANTILLA FORMATO">
            if (fto.equals("codeEdit")) {
                lst_template = TemplateJpa.Consult_templates_id(id_templ);
                Object[] obj_formatter = (Object[]) lst_template.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_form_templete'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar contenido de la plantilla</h2><br>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<p style='color: black;'><b>Codigo: </b>" + obj_formatter[1] + " <b> v</b>" + obj_formatter[2] + "</p>");

                out.print("<ul class='nav nav-tabs' id='myTab' role='tablist'>");
                if (txtPermisos.contains("[19]")) {
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link active' id='home-tab' data-toggle='tab' href='#home' role='tab' aria-controls='home' aria-selected='true'><i class='fas fa-code'></i></a>");
                    out.print("</li>");
                } else {
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link' id='home-tab' data-toggle='tooltip' data-placement='top' title='No tiene permisos' role='tab' aria-controls='home' aria-selected='true' ><i class='fas fa-code'></i></a>");
                    out.print("</li>");
                }
                if (txtPermisos.contains("[60]")) {
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link "+ ((txtPermisos.contains("[19]")) ? "" : "active") +"' id='profile-tab' data-toggle='tab' href='#profile' role='tab' aria-controls='profile' aria-selected='false'><i class='fas fa-eye'></i></a>");
                    out.print("</li>");
                } else {
                    out.print("<li class='nav-item'>");
                    out.print("<a class='nav-link' id='profile-tab' data-toggle='tooltip' data-placement='top' title='No tiene permisos' role='tab' aria-controls='profile' aria-selected='false'><i class='fas fa-eye'></i></a>");
                    out.print("</li>");
                }

                out.print("</ul>");

                out.print("<div class='cont_form_temp'>");
                out.print("<div class='tab-content' id='myTabContent'>");
                if (txtPermisos.contains("[19]")) {
                    out.print("<div class='tab-pane fade show active' id='home' role='tabpanel' aria-labelledby='home-tab'>");
                    out.print("<form action='Templates?opc=4&id_temp=" + obj_formatter[0] + "' method='post'>");
                    if (obj_formatter[3] == null) {
                        out.print("<textarea name='formatter' id='' class='codeeditor'>NO SE HA INGRESADO CONTENIDO HTML</textarea>");
                    } else {
                        out.print("<textarea name='formatter' id='' class='codeeditor'>" + obj_formatter[3] + "</textarea>");
                    }
                    out.print("<div class='' style='margin: auto; margin-top: 10px; margin-bottom: 10px; text-align: center;'>");
                    out.print("<button class='btn btn-primary'><i class='fas fa-save'></i> &nbsp;Guardar Cambios</button>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                } else {
                    out.print("<div class='tab-pane fade "+((txtPermisos.contains("60")) ? "" : "show active") +"' id='home' role='tabpanel' aria-labelledby='home-tab'>");
                    out.print("<div class='' style='text-align: center;'>");
                    out.print("<h4>Upss! No tiene permisos para registrar un formato.</h4>");
                    out.print("<i style='font-size: 100px;' class='fas fa-exclamation-circle'></i>");
                    out.print("</div>");
                    out.print("</div>");
                }
                if (txtPermisos.contains("60")) {
                    out.print("<div class='tab-pane fade "+ ((txtPermisos.contains("[19]")) ? "" : "show active") +"' id='profile' role='tabpanel' aria-labelledby='profile-tab'>");
                    out.print("<div class='' style='max-height: 400px; overflow-y: auto;'>");
                    if (obj_formatter[3] == null) {
                        out.print("NO SE HA INGRESADO CONTENIDO HTML");
                    } else {
                        out.print("" + obj_formatter[3] + "");
                    }
                    out.print("</div>");
                    out.print("</div>");
                }else{
                    out.print("<div class='tab-pane fade' id='home' role='tabpanel' aria-labelledby='home-tab'>");
                    out.print("<div class='' style='text-align: center;'>");
                    out.print("<h4>Upss! No tiene permisos para visualizar el formato.</h4>");
                    out.print("<i style='font-size: 100px;' class='fas fa-exclamation-circle'></i>");
                    out.print("</div>");
                    out.print("</div>");
                }

                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            } else if (id_templ > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR PLANTILLAS">
                lst_template = TemplateJpa.Consult_templates_id(id_templ);
                Object[] obj_temp = (Object[]) lst_template.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_templ'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Plantilla</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_temp'>");
                out.print("<form action='Templates?opc=2&id_temp=" + obj_temp[0] + "' method='post' class='needs-validation' novalidate=''>");

                out.print("<div class=''>");

                out.print("<div class='col-12' style='display: flex;margin-top: 10px;'>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='Txt_code' id='Txt_code' placeholder='Codigo' value='" + obj_temp[1] + "' required style='' data-toggle='tooltip' data-placement='top' title='Codigo'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='number' class='form-control' name='Nmb_version' id='Nmb_version' placeholder='Version' value='" + obj_temp[2] + "' required style='' data-toggle='tooltip' data-placement='top' title='Version'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-6 col-md-6' style='margin: auto; margin-buttom: 15px;'>");
                out.print("<label class='custom-switch mt-2' style='margin: 12px;' onclick='SwitchValue()'>");
                est = Integer.parseInt(obj_temp[4].toString());
                out.print("<span class='custom-switch-description'>Estado de la plantilla &nbsp;&nbsp;</span>");
                out.print("<input style='margin-left: 10px;' type='checkbox' class='custom-switch-input' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                out.print("<span class='custom-switch-indicator'></span>");
                out.print("</label>");
                out.print("<input type='hidden' name='Nmb_est' id='Nmb_est' value='" + est + "'>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                out.print("</div>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR PLANTILLAS">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display: none;'>");
            out.print("<div class='cont_templ'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Plantilla</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_temp'>");
            out.print("<form action='Templates?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class=''>");
            out.print("<div class='col-12' style='display: flex;margin-top: 10px;'>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='Txt_code' id='Txt_code' placeholder='Codigo' required style='' data-toggle='tooltip' data-placement='top' title='Codigo'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='number' class='form-control' name='Nmb_version' id='Nmb_version' placeholder='Version' required style='' data-toggle='tooltip' data-placement='top' title='Version'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
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
            out.print("<h1>Modulo Plantillas</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Plantillas</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (txtPermisos.contains("[16]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Agregar'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Codigo</th>");
            out.print("<th>Version</th>");
            out.print("<th>Fecha Registro</th>");
            out.print("<th>Estado</th>");
            out.print("<th style='text-align: center;'>Formato</th>");
            out.print("<th style='text-align: center;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_template = TemplateJpa.Consult_templates();
            if (lst_template != null || lst_template.size() != 0 || lst_template.isEmpty()) {
                for (int i = 0; i < lst_template.size(); i++) {
                    Object[] obj_templ = (Object[]) lst_template.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_templ[1] + "</td>");
                    out.print("<td>" + obj_templ[2] + "</td>");
                    out.print("<td>" + obj_templ[6] + "</td>");
                    est = Integer.parseInt(obj_templ[4].toString());
                    out.print("<td>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    out.print("<td align='center'><a href='Templates?opc=1&id_temp=" + obj_templ[0] + "&fto=codeEdit' class='btn btn-info' data-toggle='tooltip' data-placement='top' title='Gestion de formato'><i class='" + ((obj_templ[3] == null) ? "fas fa-question" : "fas fa-file-alt") + "'></i></a></td>");
                    out.print("<td align='center'>");
                    if (txtPermisos.contains("[17]")) {
                        out.print("<a href='Templates?opc=1&id_temp=" + obj_templ[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[18]")) {
                        out.print("<a href='Templates?opc=3&id_temp=" + obj_templ[0] + "&est=" + est + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
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
            Logger.getLogger(Tag_templates.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.

    }
}
