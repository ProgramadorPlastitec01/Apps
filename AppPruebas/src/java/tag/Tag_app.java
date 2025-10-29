package tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import controlador.settingControllerJpa;

import controlador.appControllerJpa;
import java.util.List;

public class Tag_app extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        appControllerJpa AppJpa = new appControllerJpa();
        settingControllerJpa SettingJpa = new settingControllerJpa();
        List lst_app = null;
        List lst_setting = null;
        int idApp = 0;
        try {
            try {
                idApp = Integer.parseInt(pageContext.getRequest().getAttribute("idApp").toString());
            } catch (Exception e) {
                idApp = 0;
            }
            if (idApp > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE APP">
                lst_app = AppJpa.ConsultAppId(idApp);
                Object[] ObjApp = (Object[]) lst_app.get(0);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_reg' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Actualizar aplicación</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<form action='attach.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' class='form-control' name='idApp' id='' data-toggle='tooltip' data-placement='top' title='' value='" + idApp + "'>");
                out.print("<div class='' style='display: flex;'>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='text' class='form-control' name='txtApp' id='' data-toggle='tooltip' data-placement='top' placeholder='Nombre de aplicativo' title='Nombre de aplicativo' value='" + ObjApp[1] + "' required>");
                out.print("</div>");
                out.print("<div class='col-lg-6'>");
                out.print("<input type='file' class='form-control' name='txtLogo' id='' data-toggle='tooltip' data-placement='top' title='Logo del aplicativo' value='' required>");
                out.print("</div>");
                out.print("</div>");

                if (ObjApp[2] != null) {
                    out.print("<input type='hidden' class='form-control' name='txtDelt' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ObjApp[2] + "' required>");
                }

                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title=''>");
                out.print("<select class='form-control' name='cbxSetting' style='margin-12px;'>");
                out.print("<option value='" + ObjApp[4] + "' selected>" + ObjApp[7] + "</option>");
                lst_setting = SettingJpa.ConsultSettingCategorieLike("Connect");
                if (lst_setting != null) {
                    for (int i = 0; i < lst_setting.size(); i++) {
                        Object[] Objset = (Object[]) lst_setting.get(i);
                        if (ObjApp[4] != Objset[0]) {
                            out.print("<option value='" + Objset[0] + "'>" + Objset[1] + "</option>");
                        }
                    }
                } else {
                    out.print("<option disabled>Error al consultar información</option>");
                }
                out.print("</select>");
                out.print("</div>");

                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER APP">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_reg' style='width: 44%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar aplicación</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='attach.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
            out.print("<input type='hidden' class='form-control' name='idApp' id='' value='0'>");
            out.print("<div class='' style='display: flex;'>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' name='txtApp' id='' data-toggle='tooltip' data-placement='top' placeholder='Nombre de aplicativo' title='Nombre de aplicativo' value='' required>");
            out.print("</div>");
            out.print("<div class='col-lg-6'>");
            out.print("<input type='file' class='form-control' name='txtLogo' id='' data-toggle='tooltip' data-placement='top' title='Logo del aplicativo' value='' required>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title=''>");
            out.print("<select class='form-control' name='cbxSetting' style='margin-12px;'>");
            out.print("<option selected disabled>Seleccionar configuración</option>");
            lst_setting = SettingJpa.ConsultSettingCategorieLike("Connect");
            if (lst_setting != null) {
                for (int i = 0; i < lst_setting.size(); i++) {
                    Object[] Objset = (Object[]) lst_setting.get(i);
                    out.print("<option value='" + Objset[0] + "'>" + Objset[1] + "</option>");
                }
            } else {
                out.print("<option disabled>Error al consultar información</option>");
            }
            out.print("</select>");
            out.print("</div>");

            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green'>Registrar</button>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Setting?opt=1\"' data-toggle='tooltip' data-placement='top' title='Volver al menu'><i class='fas fa-arrow-left'></i></button>");
            out.print("<h2>Aplicaciones</h2>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Id</th>");
            out.print("<th>App</th>");
            out.print("<th class='text-center'>Logo</th>");
            out.print("<th class='text-center'>Configuracion</th>");
            out.print("<th>Fecha registro</th>");
            out.print("<th>Estado</th>");
            out.print("<th>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_app = AppJpa.ConsultApps();
            if (lst_app != null) {
                for (int i = 0; i < lst_app.size(); i++) {
                    Object[] ObjApp = (Object[]) lst_app.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjApp[0] + "</td>");
                    out.print("<td>" + ObjApp[1] + "</td>");

                    if (ObjApp[2] == null) {
                        out.print("<td class='text-center'>- Sin logo -</td>");
                    } else {
                        String imag = ObjApp[2].toString();
                        out.print("<td class='text-center'>");
                        out.print("<div class='gallery' style='display: flex; justify-content: center;'>");
                        out.print("<div class=\"gallery-item\" data-image='Interfaz/Contenido/dataFiles/" + imag + "' href='Interfaz/Contenido/dataFiles/" + imag + "' data-title=\"Image " + i + "\" style='background-image: url(Interfaz/Contenido/dataFiles/" + imag + ");'></div>");
                        out.print("</div>");
                        out.print("</td>");
                    }

                    out.print("<td>" + ObjApp[7] + "</td>");
                    out.print("<td>" + ObjApp[5] + "</td>");
                    int ste = Integer.parseInt(ObjApp[3].toString());
                    out.print("<td><div class='badge badge-" + ((ste == 1) ? "success'>Activo" : "danger'>Inactivo") + "</div></td>");
                    out.print("<td>");
                    out.print("<div class='text-center' style='justify-content: space-around;'>");
                    out.print("<button class='btn btn-" + ((ste == 1) ? "success" : "danger") + " mr-2' onclick='window.location.href=\"App?opt=3&idApp=" + ObjApp[0] + "\"' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((ste == 1) ? "fas fa-check" : "fas fa-times") + "'></i></button>");
                    out.print("<button class='btn btn-warning' onclick='window.location.href=\"App?opt=1&idApp=" + ObjApp[0] + "\"'><i class='fas fa-edit'></i></button>");
                    out.print("</div>");
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
        } catch (IOException ex) {
            Logger.getLogger(Tag_app.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
