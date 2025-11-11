package Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.ConfigurationControllerJpa;
import Controller.RoleControllerJpa;
import javax.servlet.http.HttpSession;

public class Tag_Setting extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ConfigurationControllerJpa SettingJpa = new ConfigurationControllerJpa();
        List lst_Setting = null, lst_SettingId = null, lst_SettingCategory = null;
        int IdSetting = 0, State = 0;
        String[] Arr_val = {};
        try {
            IdSetting = Integer.parseInt(pageContext.getRequest().getAttribute("IdSetting").toString());
        } catch (Exception e) {
            IdSetting = 0;
        }
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_role = null;
        HttpSession sesion = pageContext.getSession();
        int idRol = 0;
        String txtPermissions = "";
        try {
            idRol = Integer.parseInt(sesion.getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }
        try {
            if (IdSetting > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE SETTING">
                lst_SettingId = SettingJpa.ConsultSettingsByCategorieId(IdSetting);
                if (lst_SettingId != null) {
                    Object[] obj_SettingId = (Object[]) lst_SettingId.get(0);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_formSetting'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar Configuración</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<div class='cont_form_temp'>");
                    out.print("<form action='Setting?opt=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdSetting' value='" + IdSetting + "'>");
                    out.print("<div class='col-6 mb-2'>");
                    out.print("<input type='text' class='form-control' name='Txt_Category' value='" + obj_SettingId[1] + "' id='Category' placeholder='Categoria' required data-toggle='tooltip' data-placement='top' title='Categoria'>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-12 mb-2'>");
                    out.print("<textarea style='height:150px !important' class='form-control' value='" + obj_SettingId[2] + "' name='Txt_Value' id='Value' placeholder='Valor' required data-toggle='tooltip' data-placement='top' title='Valor'>" + obj_SettingId[2] + "</textarea>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-12 mb-2'>");
                    out.print("<textarea class='form-control' name='Txt_Description' id='Description' value='" + obj_SettingId[3] + "' placeholder='Descripción de la configuración' required data-toggle='tooltip' data-placement='top' title='Descripción'>" + obj_SettingId[3] + "</textarea>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
                    out.print("<button class='btn btn-blue btn-lg'>Modificar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER SETTING">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_formSetting'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Configuración</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_temp'>");
            out.print("<form action='Setting?opt=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-6 mb-2'>");
            out.print("<input type='text' class='form-control' name='Txt_Category' id='Category' placeholder='Categoria' required data-toggle='tooltip' data-placement='top' title='Categoria'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-12 mb-2'>");
            out.print("<textarea style='height:150px !important' class='form-control' name='Txt_Value' id='Value' placeholder='Valor' required data-toggle='tooltip' data-placement='top' title='Valor'></textarea>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-12 mb-2'>");
            out.print("<textarea class='form-control' name='Txt_Description' id='Description' placeholder='Descripción de la configuración' required data-toggle='tooltip' data-placement='top' title='Descripción'></textarea>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
            out.print("<button class='btn btn-blue btn-lg'>Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo Configuración</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de Configuración</h4>");
            if (txtPermissions.contains("[8]")) {
                out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-blue' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr style='text-align: center;'>");
            out.print("<th>Id</th>");
            out.print("<th>Categoria</th>");
            out.print("<th style='width:40%'>Valor</th>");
            out.print("<th>Descripcion</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_SettingCategory = SettingJpa.ConsultSettingsByCategorie("ControlViewSetting");
            Object[] obj_SettingCat = (Object[]) lst_SettingCategory.get(0);
            lst_Setting = SettingJpa.ConsultSettings();
            if (lst_Setting != null) {
                for (int i = 0; i < lst_Setting.size(); i++) {
                    Object[] obj_Setting = (Object[]) lst_Setting.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_Setting[0] + "</td>");
                    out.print("<td>" + obj_Setting[1] + "</td>");
                    String IdF = "[" + obj_Setting[0].toString() + "]";
                    if (obj_SettingCat[2].toString().contains(IdF)) {
                        //<editor-fold defaultstate="collapsed" desc="VALIDATION VIEW VALUE">
                        Arr_val = obj_SettingCat[2].toString().replace("][", "---").replace("[", "").replace("]", "").split("---");
                        for (int j = 0; j < Arr_val.length; j++) {
                            if (Integer.parseInt(Arr_val[j]) == Integer.parseInt(obj_Setting[0].toString())) {
                                out.print("<td style='text-align:center;'><button class='btn btn-blue mr-2' onclick='ViewContentMail(" + i + ")' data-toggle='tooltip' data-placement='top' title='Ver Contenido'><i class='fas fa-eye'></i></button></td>");
                                String Content = obj_Setting[2].toString().replace("Ã³", "ó").replace("Ã", "í");
                                out.print("<div class='sweet-local' tabindex='-1' id='Content" + i + "' style='opacity: 1.03; display:none;'>"
                                        + "<div class='cont_contentMail'>"
                                        + "<div style='display: flex; justify-content: space-between'>"
                                        + "<h2>Ver contenido</h2>"
                                        + "<button class='btn btn-outline-secondary' onclick='ViewContentMail(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>"
                                        + "</div>"
                                        + Content
                                        + "</div>"
                                        + "</div>");
                                j = Arr_val.length;
                            }
                        }
                        //</editor-fold>
                    } else {
                        out.print("<td>" + obj_Setting[2] + "</td>");
                    }
                    out.print("<td>" + obj_Setting[3] + "</td>");
                    State = Integer.parseInt(obj_Setting[4].toString());
                    out.print("<td style='text-align: center;'><div class='badge badge-" + ((State == 1) ? "success'>Activo" : "danger'>Inactivo") + " </div></td>");
                    out.print("<td style='display: flex;justify-content: center;'>");
                    if (txtPermissions.contains("[16]")) {
                        out.print("<button onclick=\"window.location.href='Setting?opt=1&IdSetting=" + obj_Setting[0] + "'\" class='btn btn-warning mr-2' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></button>");
                        out.print("<button onclick=\"window.location.href='Setting?opt=3&IdSetting=" + obj_Setting[0] + "&State=" + ((State == 1) ? "0" : "1") + "'\" class='btn btn-" + ((State == 1) ? "success" : "danger") + " mr-2' data-toggle='tooltip' data-placement='top' title='" + ((State == 1) ? "Activo" : "Inactivo") + "'><i class='fas fa-" + ((State == 1) ? "check" : "times") + "'></i></button>");
                    } else {
                        out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></button>");
                        out.print("<button class='btn btn-" + ((State == 1) ? "success" : "danger") + " style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-" + ((State == 1) ? "check" : "times") + "'></i></button>");
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
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_CIIU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
