package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.CorreoJpaController;
import java.util.List;
import Controladores.RolJpaController;

public class Tag_mail extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        CorreoJpaController MailJpa = new CorreoJpaController();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_mail = null;
        List lst_roll = null;
        int est = 0, id_mail = 0, UserRol = 0;
        String txtPermisos = "";
        try {
            try {
                id_mail = Integer.parseInt(pageContext.getRequest().getAttribute("id_mail").toString());
            } catch (Exception e) {
                id_mail = 0;
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

            if (id_mail > 0) {
                lst_mail = MailJpa.ConsultMail_id(id_mail);
                Object[] Obj_mail = (Object[]) lst_mail.get(0);
                //<editor-fold defaultstate="collapsed" desc="EDIT MAIL">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Correo</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Mail?opc=2&id_mail=" + Obj_mail[0] + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='' style=''>");
                out.print("<div class='col-12' style='display: flex;'>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='Txt_funct' id='Txt_funct' placeholder='Funcion' required='' title='Funcion del correo' value='" + Obj_mail[1] + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='Txt_emisor' id='Txt_emisor' placeholder='Emisor' required title='Origen de los correos' value='" + Obj_mail[2] + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-12' style='display: flex;'>");
                out.print("<div class='col-lg-4'>");
                out.print("<input type='text' class='form-control' name='Txt_password' id='Txt_password' placeholder='Contraseña' required  title='Contraseña del correo emisor' value='" + Obj_mail[3] + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<input type='text' class='form-control' name='Txt_host' id='Txt_host' placeholder='Host' required='' title='Servicio de transporte de correos' value='" + Obj_mail[4] + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<input type='number' min='1' class='form-control' name='Nmb_port' id='Nmb_port' placeholder='Puerto' required='' title='Puerto para envio de correos' value='" + Obj_mail[5] + "'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-12' style='display: flex;'>");
                out.print("<div class='col-8'>");
                out.print("<textarea class='form-control' name='Txt_recept' id='Txt_recept' placeholder='Receptor' required='' style='margin: 12px;' title='Correos destino'>" + Obj_mail[6] + "</textarea>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-4'>");
                out.print("<label class='custom-switch mt-2' style='margin: 12px;' onclick='SwitchValue()'>");
                est = Integer.parseInt(Obj_mail[7].toString());
                out.print("<span class='custom-switch-description' style='margin-right: 10px;'>Estado de<br> correo &nbsp;&nbsp;</span>");
                out.print("<input style='margin-left: 10px;' type='checkbox' class='custom-switch-input' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                out.print("<span class='custom-switch-indicator'></span>");
                out.print("</label>");
                out.print("</div>");

                out.print("<input type='hidden' name='Nmb_est' id='Nmb_est' value='" + est + "'>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-primary btn-lg'>Editar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="MAIL REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Correo</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form class='needs-validation' novalidate='' action='Mail?opc=2' method='post'>");

            out.print("<div class='' style=''>");

            out.print("<div class='col-12' style='display: flex;'>");

            out.print("<div class='col-6'>");
            out.print("<input type='text' class='form-control' name='Txt_funct' id='Txt_funct' placeholder='Funcion' required='' title='Funcion del correo' required=''>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-6'>");
            out.print("<input type='text' class='form-control' name='Txt_emisor' id='Txt_emisor' placeholder='Emisor' required title='Origen de los correos' required=''>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<div class='col-lg-4'>");
            out.print("<input type='text' class='form-control' name='Txt_password' id='Txt_password' placeholder='Contraseña' required  title='Contraseña del correo emisor'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-4'>");
            out.print("<input type='text' class='form-control' name='Txt_host' id='Txt_host' placeholder='Host' required='' title='Servicio de transporte de correos'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-4'>");
            out.print("<input type='number' min='1' class='form-control' name='Nmb_port' id='Nmb_port' placeholder='Puerto' required='' title='Puerto para envio de correos'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div>");
            out.print("<textarea class='form-control' name='Txt_recept' id='Txt_recept' placeholder='Receptor' required='' style='margin: 12px; width: 97%;' title='Correos destino'></textarea>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-primary btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo de correo</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado Correo</h4>");
            if (txtPermisos.contains("[4]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar' ><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity:0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos' ><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Funcion</th>");
            out.print("<th>Emisor</th>");
            out.print("<th>Contraseña</th>");
            out.print("<th>Host</th>");
            out.print("<th>Puerto</th>");
            out.print("<th style='max-width: 260px;'>Receptor</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center; min-width: 100px;'>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_mail = MailJpa.ConsultMail();
            if (lst_mail != null || lst_mail.size() > 0) {
                for (int i = 0; i < lst_mail.size(); i++) {
                    Object[] obj_mail = (Object[]) lst_mail.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_mail[1] + "</td>");
                    out.print("<td>" + obj_mail[2] + "</td>");
                    out.print("<td>" + obj_mail[3] + "</td>");
                    out.print("<td>" + obj_mail[4] + "</td>");
                    out.print("<td>" + obj_mail[5] + "</td>");
                    out.print("<td>" + obj_mail[6] + "</td>");
                    est = Integer.parseInt(obj_mail[7].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");

                    out.print("<td align='center'>");
                    if (txtPermisos.contains("[5]")) {
                        out.print("<a href='Mail?opc=1&id_mail=" + obj_mail[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar' ><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[6]")) {
                        out.print("<a href='Mail?opc=3&id_mail=" + obj_mail[0] + "&Nmb_est=" + est + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado' ><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5;' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos' ><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_mail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
