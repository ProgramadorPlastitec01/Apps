package Tags;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controladores.ParametrosJpaController;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controladores.RolJpaController;

public class Tag_parameter extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        ParametrosJpaController ParameterJpa = new ParametrosJpaController();
        RolJpaController RoleJpa = new RolJpaController();
        HttpSession sesion = pageContext.getSession();
        String UserName = pageContext.getSession().getAttribute("Nombres").toString();
        int id_param = Integer.parseInt(pageContext.getRequest().getAttribute("id_param").toString());
        List lst_parameter = null;
        List lst_roll = null;
        int est = 0, UserRol = 0;
        String txtPermisos = "";
        try {
            try {
                UserRol = Integer.parseInt(pageContext.getRequest().getAttribute("id_rol").toString());
                lst_roll = RoleJpa.Consult_role_id(UserRol);
                Object[] obj_permi = (Object[]) lst_roll.get(0);
                txtPermisos = obj_permi[2].toString();
            } catch (Exception e) {
                UserRol = 0;
                txtPermisos = "";
            }

            if (id_param > 0) {
                //<editor-fold defaultstate="collapsed" desc="PARAMETER EDIT">
                lst_parameter = ParameterJpa.ConsultParameters_id(id_param);
                Object[] obj_param = (Object[]) lst_parameter.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Parametro</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Parameter?opc=2&id_param=" + obj_param[0] + "' method='post' class='needs-validation' novalidate=''>");

                out.print("<div class='col-12' style='display: flex;'>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='Txt_category' id='Txt_category' placeholder='Categoria' required value='" + obj_param[1] + "' data-toggle='tooltip' data-placement='top' title='Categoria'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='Txt_value' id='Txt_value' placeholder='Valor' required value='" + obj_param[2] + "' data-toggle='tooltip' data-placement='top' title='Valor'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-12' style='display: flex; align-items: center;'>");
                out.print("<div class='col-8'>");
                out.print("<input type='text' class='form-control' name='Txt_descrip' id='Txt_descrip' placeholder='Descripcion' required value='" + obj_param[3] + "' data-toggle='tooltip' data-placement='top' title='Descripcion'>");
                out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("<div class='col-lg-4 col-md-6'>");
                out.print("<label class='custom-switch mt-2' style='margin: 12px;' onclick='SwitchValue()'>");
                est = Integer.parseInt(obj_param[4].toString());
                out.print("<span class='custom-switch-description'>Estado del parametro &nbsp;&nbsp;</span>");
                out.print("<input style='margin-left: 10px;' type='checkbox' class='custom-switch-input' value='" + est + "' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                out.print("<span class='custom-switch-indicator'></span>");
                out.print("</label>");
                out.print("<input type='hidden' name='Nmb_est' id='Nmb_est' value='" + est + "'>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Editar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="PARAMETER REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Parametro</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Parameter?opc=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-12' style='display: flex;'>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='Txt_category' id='Txt_category' placeholder='Categoria' required='' data-toggle='tooltip' data-placement='top' title='Categoria'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='Txt_value' id='Txt_value' placeholder='Valor' required data-toggle='tooltip' data-placement='top' title='Valor'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col-12' style='width: 97%;'>");
            out.print("<input type='text' class='form-control' name='Txt_descrip' id='Txt_descrip' placeholder='Descripcion' required='' data-toggle='tooltip' data-placement='top' title='Descripcion'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Parametros</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Parametros</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (txtPermisos.contains("[10]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Categoria</th>");
            out.print("<th style='max-width: 250px;'>Valor</th>");
            out.print("<th style='min-width: 200px;'>Descripcion</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center; min-width: 90px;'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_parameter = ParameterJpa.ConsultParameters();
            if (lst_parameter != null || lst_parameter.size() != 0) {
                for (int i = 0; i < lst_parameter.size(); i++) {
                    Object[] obj_param = (Object[]) lst_parameter.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_param[1] + "</td>");
                    out.print("<td>" + obj_param[2] + "</td>");
                    out.print("<td>" + obj_param[3] + "</td>");
                    est = Integer.parseInt(obj_param[4].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");

                    out.print("<td align='center'>");
                    if (txtPermisos.contains("[11]")) {
                        out.print("<a href='Parameter?opc=1&id_param=" + obj_param[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[12]")) {
                        out.print("<a href='Parameter?opc=3&id_param=" + obj_param[0] + "&est=" + est + "' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5;' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td colspan='7'>Sin datos</td>");
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
            Logger.getLogger(Tag_parameter.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
