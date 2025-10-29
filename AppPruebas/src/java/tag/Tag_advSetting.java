package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import controlador.settingControllerJpa;
import java.util.List;

public class Tag_advSetting extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        settingControllerJpa SettingJpa = new settingControllerJpa();
        List lst_setting = null;
        int id_adv = 0;
        try {
            try {
                id_adv = Integer.parseInt(pageContext.getRequest().getAttribute("id_adv").toString());
            } catch (Exception e) {
                id_adv = 0;
            }
            if (id_adv > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE ADV SETTING">

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 70%; margin-left: 23%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Actualizar ajuste</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_setting = SettingJpa.ConsultSettingId(id_adv);
                if (lst_setting != null) {
                    Object[] ObjSett = (Object[]) lst_setting.get(0);
                    out.print("<form action='AdvSetting?opt=2&id_adv=" + id_adv + "' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='card-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<input type='text' class='form-control' name='txtCategorie' id='' data-toggle='tooltip' placeholder='Categoria' data-placement='top' title='' value='" + ObjSett[1] + "' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<input type='text' class='form-control' name='txtValue' id='' data-toggle='tooltip' placeholder='Valor' data-placement='top' title='' value='" + ObjSett[2] + "' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<input type='text' class='form-control' name='txtDescrip' id='' data-toggle='tooltip' placeholder='Descripcion' data-placement='top' title='' value='" + ObjSett[3] + "' required>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-green'>Actualizar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<div class=''>");
                    out.print("<h4>Se ha encontrado error al consultar la información.</h4>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }

            //<editor-fold defaultstate="collapsed" desc="REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 55%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar configuración</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='AdvSetting?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class='card-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-lg-4'>");
            out.print("<input type='text' class='form-control' name='txtCategorie' id='' data-toggle='tooltip' placeholder='Categoria' data-placement='top' title='' value='' required>");
            out.print("</div>");
            out.print("<div class='col-lg-4'>");
            out.print("<input type='text' class='form-control' name='txtValue' id='' data-toggle='tooltip' placeholder='Valor' data-placement='top' title='' value='' required>");
            out.print("</div>");
            out.print("<div class='col-lg-4'>");
            out.print("<input type='text' class='form-control' name='txtDescrip' id='' data-toggle='tooltip' placeholder='Descripcion' data-placement='top' title='' value='' required>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="LIST SETTING">
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Setting?opt=1\"' data-toggle='tooltip' data-placement='top' title='Volver al menu'><i class='fas fa-arrow-left'></i></button>");
            out.print("<h2>Ajustes avanzados</h2>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            lst_setting = SettingJpa.ConsultSettings();
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>ID</th>");
            out.print("<th>Categoria</th>");
            out.print("<th>Valor</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            if (lst_setting != null) {
                for (int i = 0; i < lst_setting.size(); i++) {
                    Object[] ObjSet = (Object[]) lst_setting.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjSet[0] + "</td>");
                    out.print("<td>" + ObjSet[1] + "</td>");
                    out.print("<td>" + ObjSet[2] + "</td>");
                    out.print("<td>" + ObjSet[3] + "</td>");
                    int ste = Integer.parseInt(ObjSet[4].toString());
                    out.print("<td><div class='badge badge-" + ((ste == 1) ? "success'> Activo" : "danger'> Inactivo") + "</div></td>");
                    out.print("<td>");
                    out.print("<div class='d-flex'>");
                    out.print("<button onclick='window.location.href=\"AdvSetting?opt=3&id_adv=" + ObjSet[0] + "\"' class='mr-2 btn btn-" + ((ste == 1) ? "success'> <i class='fas fa-check'></i>" : "danger'> <i class='fas fa-times'></i>") + "</button>");
                    out.print("<button onclick='window.location.href=\"AdvSetting?opt=1&id_adv=" + ObjSet[0] + "\"' class='btn btn-warning'><i class='fas fa-edit'></i></button>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr>");
                out.print("<td></td>");
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
        } catch (Exception e) {
        }

        return super.doStartTag();
    }

}
