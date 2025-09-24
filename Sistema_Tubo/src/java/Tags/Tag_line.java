package Tags;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controladores.LineaJpaController;
import java.util.List;

import Controladores.RolJpaController;

public class Tag_line extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        LineaJpaController JpaLinea = new LineaJpaController();
        RolJpaController RoleJpa = new RolJpaController();
        List lst_line = null;
        List lst_line_id = null;
        List lst_roll = null;
        JspWriter out = pageContext.getOut();
        int id_line = 0, state = 0, UserRol = 0;
        String txtPermisos = "";
        try {
            try {
                id_line = Integer.parseInt(pageContext.getRequest().getAttribute("id_line").toString());
            } catch (Exception e) {
                id_line = 0;
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

            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Línea</h1>");
            out.print("</div>");
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-12\">");
            out.print("<div class=\"card\">");
            out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
            out.print("<h4>Listado Línea</h4>");
            if (txtPermisos.contains("[7]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;opacity: 0.5;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            if (id_line != 0) {
                //<editor-fold defaultstate="collapsed" desc="LINE UPDATE">
                lst_line_id = JpaLinea.Consult_line_id(id_line);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_line'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Modificar Linea</h4>");
                if (lst_line_id != null) {
                    Object[] obj_line_update = (Object[]) lst_line_id.get(0);
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Line?opc=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='id_line' value='" + id_line + "'>");
                    out.print("<div class='' style='display: flex;'>");
                    out.print("<div class='col-lg-6 col-md-6'>");
                    out.print("<input type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' autocomplete='off' value='" + obj_line_update[1] + "' required='' data-toggle='tooltip' data-placement='top' title='Nombre'>");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='col-lg-6 col-md-6'>");
                    out.print("<input type='text' class='form-control' name='Txt_code' id='Txt_code' placeholder='Codigo' autocomplete='off' value='" + obj_line_update[2] + "' required='' data-toggle='tooltip' data-placement='top' title='Codigo' >");
                    out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center;'>");
                    out.print("<button class='btn btn-green btn-lg'>Modificar</button>");
                    out.print("</div>");
                    out.print("</form>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="LINE REGISTRER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_line'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Registrar Linea</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Line?opc=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='' style='display: flex;'>");
            out.print("<div class='col-lg-6 col-md-6'>");
            out.print("<input type='text' class='form-control' name='Txt_name' id='Txt_name' placeholder='Nombre' autocomplete='off' required='' data-toggle='tooltip' data-placement='top' title='Nombre'>");
            out.print("<div class='invalid-feedback invalid_data_rll'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='col-lg-6 col-md-6'>");
            out.print("<input type='text' class='form-control' name='Txt_code' id='Txt_code' placeholder='Codigo' autocomplete='off' required='' data-toggle='tooltip' data-placement='top' title='Codigo'>");
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
            out.print("<div class=\"card-body\">");
            out.print("<div class=\"table-responsive\">");
            out.print("<table class=\"table table-striped\" id=\"table-1\">");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th class=\"text-center\">Id</th>");
            out.print("<th>Nombre</th>");
            out.print("<th>Codigo</th>");
            out.print("<th>Estado</th>");
            out.print("<th class=\"text-center\">Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_line = JpaLinea.Consult_line();
            if (lst_line != null || lst_line.isEmpty()) {
                for (int i = 0; i < lst_line.size(); i++) {
                    Object[] obj_line = (Object[]) lst_line.get(i);
                    out.print("<tr>");
                    out.print("<td class=\"text-center\">" + obj_line[0] + "</td>");
                    out.print("<td>" + obj_line[1] + "</td>");
                    out.print("<td>" + obj_line[2] + "</td>");
                    state = Integer.parseInt(obj_line[3].toString());
                    out.print("<td>" + ((state == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    out.print("<td class=\"text-center\">");
                    if (txtPermisos.contains("[8]")) {
                        out.print("<a href='Line?opc=1&id_line=" + obj_line[0] + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    } else {
                        out.print("<a href='#' style='background: orange;opacity: 0.5;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permiso'><i class='fas fa-edit'></i></a> &nbsp;&nbsp;");
                    }
                    if (txtPermisos.contains("[9]")) {
                        out.print("<a href='Line?opc=3&id_line=" + obj_line[0] + "&state=" + state + "' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar estado'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    } else {
                        out.print("<a href='#' style='opacity: 0.5;' class='btn btn-" + ((state == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='No tiene permiso'><i class='" + ((state == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a>");
                    }
                    out.print("</td>");
                    out.print("</tr>");

                    out.print("</tr>");

                }
            } else {
                out.print("No existe información");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_line.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag(); //To change body of generated methods, choose Tools | Templates.
    }
}
