package Tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import Controladores.AreaJpaController;

public class Tag_area extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        HttpSession sesion = pageContext.getSession();
        String UserName = "";
        String UserRol = "";
        UserName = pageContext.getSession().getAttribute("Nombre").toString();
        UserRol = pageContext.getSession().getAttribute("Rol").toString();
        boolean Auth = true;
        if (UserRol.equals("ADMINISTRADOR") || UserRol.equals("ASIS. METROLOGIA")) {
            Auth = false;
        }
        JspWriter out = pageContext.getOut();
        String filtro = "";
        AreaJpaController AreaJpa = new AreaJpaController();
        List lst_areas = null;
        int id_area = 0, est = 0;
        try {
            try {
                id_area = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
            } catch (Exception e) {
                id_area = 0;
            }
            if (id_area > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITAR AREA">
                lst_areas = AreaJpa.consultaAreaId(id_area);
                Object[] Obj_area = (Object[]) lst_areas.get(0);

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar Area</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Area?opc=3&idA=" + Obj_area[0] + "&txt_bus=" + filtro + "' method='post'>");

                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<input type='text' class='form-control' name='txt_nombreM' id='nombre-id' placeholder='Nombre' value='" + Obj_area[1] + "' required data-toggle='tooltip' data-placement='top' title='Nombre'>");
                out.print("<input type='text' class='form-control' name='txt_siglaM' id='sigla-id' placeholder='Sigla' value='" + Obj_area[2] + "' required data-toggle='tooltip' data-placement='top' title='Sigla'>");
                out.print("</div>");

                out.print("<div class='col-lg-12' style='display: flex;'>");
                out.print("<input type='text' class='form-control' name='txt_responsableM' id='responsable-id' placeholder='Responsable' value='" + Obj_area[3] + "' required data-toggle='tooltip' data-placement='top' title='Responsable'>");
                out.print("<div class='col-lg-6' style='display: flex;'>");
                out.print("<label class='custom-switch mt-2' style='margin: 12px;' onclick='SwitchValue()'>");
                est = Integer.parseInt(Obj_area[4].toString());
                out.print("<span class='custom-switch-description'>Estado &nbsp;&nbsp;</span>");
                out.print("<input style='margin-left: 10px;' type='checkbox' class='custom-switch-input' id='Nmb_estP' value='" + est + "' " + ((est == 1) ? "checked" : "") + " onclick='SwitchValue()'>");
                out.print("<span class='custom-switch-indicator' onclick='SwitchValue()'></span>");
                out.print("</label>");
                out.print("<input type='hidden' name='Nmb_est' id='Nmb_est' value='" + est + "'>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Confirmar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTRAR AREA">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Area</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Area?opc=2' method='post'>");

            out.print("<div class='col-lg-12' style='display: flex;'>");
            out.print("<input type='text' class='form-control' name='txt_nombre' id='nombre-id' placeholder='Nombre' required data-toggle='tooltip' data-placement='top' title='Nombre'>");
            out.print("<input type='text' class='form-control' name='txt_sigla' id='sigla-id' placeholder='Sigla' required data-toggle='tooltip' data-placement='top' title='Sigla'>");
            out.print("<input type='text' class='form-control' name='txt_responsable' id='responsable-id' placeholder='Responsable' value='N/A' required data-toggle='tooltip' data-placement='top' title='Responsable'>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="CONSULTA PRINCIPAL">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Areas</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de area</h4>");
//            out.print("<button class='btn btn-primary' id='toastr-2'>Launch</button>");
            if (Auth) {
                out.print("<button class='btn btn-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar' ><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered table-striped' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Area</th>");
            out.print("<th>Sigla</th>");
            out.print("<th>Responsable</th>");
            out.print("<th style='text-align: center;'>Estado</th>");
            out.print("<th style='text-align: center;min-width: 100px;'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_areas = AreaJpa.consultaAreas();
            if (lst_areas != null || lst_areas.size() != 0 || lst_areas.isEmpty()) {
                for (int i = 0; i < lst_areas.size(); i++) {
                    out.print("<tr>");
                    Object[] obj_area = (Object[]) lst_areas.get(i);
                    out.print("<td>" + obj_area[1] + "</td>");
                    out.print("<td>" + obj_area[2] + "</td>");
                    out.print("<td>" + obj_area[3] + "</td>");
                    est = Integer.parseInt(obj_area[4].toString());
                    out.print("<td align='center'>" + ((est == 1) ? "<div class='badge badge-success'>Activo</div>" : "<div class='badge badge-danger'>Inactivo</div>") + "</td>");
                    if (Auth) {
                        out.print("<td align='center'>"
                                + "<a href='#' id='btn_add' class='btn btn-secondary' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                                + "<a href='#' class='btn btn-secondary btn-icon' data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></a> </td>");
                    } else {
                        out.print("<td align='center'><a href='Area?opc=4&idA=" + obj_area[0] + "&est=" + ((est == 1) ? 0 : 1) + "&txt_bus=" + filtro + "' id='btn_add' class='btn btn-" + ((est == 1) ? "success" : "danger") + "' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((est == 1) ? "fas fa-check-circle" : "fas fa-times-circle") + "'></i></a> &nbsp;&nbsp;"
                                + "<a href='Area?opc=1&idA=" + obj_area[0] + "&txt_bus=" + filtro + "' style='background: orange;' class='btn btn-warning btn-icon' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></a> </td>");
                    }
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
        } catch (Exception ex) {
            Logger.getLogger(Tag_area.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
