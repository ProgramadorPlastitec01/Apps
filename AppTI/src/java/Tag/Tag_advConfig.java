package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.SettingControllerJpa;
import java.util.List;

public class Tag_advConfig extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        List lst_setting = null;
        int idSett = 0;
        try {
            idSett = Integer.parseInt(pageContext.getRequest().getAttribute("idSett").toString());
        } catch (Exception e) {
            idSett = 0;
        }
        try {

            if (idSett > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDITER">
                lst_setting = SettingJpa.ConsultSettingId(idSett);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar parametro </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_setting != null) {
                    Object[] ObjSetting = (Object[]) lst_setting.get(0);
                    out.print("<form action='advConfig?opt=2&idSett=" + idSett + "' method='post'>");
                    out.print("<input type='text' class='form-control col-lg-5 mb-2' style='margin:auto;' name='txtCateogrie' id='' placeholder='Categoria' data-toggle='tooltip' data-placement='top' title='Categoria' value='"+ ObjSetting[1] +"'>");
                    out.print("<div class='d-flex justify-content-around'>");
                    out.print("<textarea class='form-control col-lg-5' name='txtValue' data-toggle='tooltip' data-placement='top' title='Valor'>"+ ObjSetting[2] +"</textarea>");
                    out.print("<textarea class='form-control col-lg-5' name='txtDescription' data-toggle='tooltip' data-placement='top' title='Descripción'>"+ ObjSetting[3] +"</textarea>");
                    out.print("</div>");
                    out.print("<div class='text-center mt-4'>");
                    out.print("<button class='btn btn-green'>Confirmar</button>");
                    out.print("</div>");
                    out.print("</form>");
                } else {
                    out.print("<h4>Error al consultar información del parametro</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Nuevo parametro </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='advConfig?opt=2' method='post'>");
            out.print("<input type='text' class='form-control col-lg-5 mb-2' style='margin:auto;' name='txtCateogrie' id='' placeholder='Categoria' data-toggle='tooltip' data-placement='top' title='Categoria' >");
            out.print("<div class='d-flex justify-content-around'>");
            out.print("<textarea class='form-control col-lg-5' name='txtValue' placeholder='Valor' data-toggle='tooltip' data-placement='top' title='Valor'></textarea>");
            out.print("<textarea class='form-control col-lg-5' name='txtDescription' placeholder='Descripción' data-toggle='tooltip' data-placement='top' title='Descripción'></textarea>");
            out.print("</div>");
            out.print("<div class='text-center mt-4'>");
            out.print("<button class='btn btn-green'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
//</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
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
                    + "<h4>Configuración Avanzada</h4>"
                    + "</div>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            lst_setting = SettingJpa.ConsultSetting();
            if (lst_setting != null) {
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Id</th>");
                out.print("<th>Categoria</th>");
                out.print("<th>Valor</th>");
                out.print("<th>Descripcion</th>");
                out.print("<th>Estado</th>");
                out.print("<th>OPC</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for (int i = 0; i < lst_setting.size(); i++) {
                    Object[] Objset = (Object[]) lst_setting.get(i);
                    out.print("<tr>");
                    out.print("<td>" + Objset[0] + "</td>");
                    out.print("<td>" + Objset[1] + "</td>");
                    out.print("<td>" + Objset[2] + "</td>");
                    out.print("<td>" + Objset[3] + "</td>");
                    int state = Integer.parseInt(Objset[4].toString());
                    out.print("<td><div class='badge badge-" + ((state == 1) ? "success'>Activo" : "danger'>Inactivo") + "</div></td>");
                    out.print("<td class='text-center'>");
                    out.print("<div class='d-flex justify-content-around'>");
                    if (state == 1) {
                        out.print("<a class='btn btn-success btn-sm' href='advConfig?opt=3&idSett=" + Objset[0] + "&state=0'><i class='fas fa-check'></i></a>");
                    } else {
                        out.print("<a class='btn btn-danger btn-sm' href='advConfig?opt=3&idSett=" + Objset[0] + "&state=1'><i class='fas fa-times'></i></a>");
                    }
                    out.print("<button class='btn btn-warning btn-sm' onclick='window.location.href=\"advConfig?opt=1&idSett=" + Objset[0] + "\"'><i class='fas fa-user-edit'></i></button>");
                    out.print("</div>");
                    out.print("</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
            } else {
                out.print("<h4>Ha ocurrido un problema al consultar la informacion de la configuración</h4>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_advConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
