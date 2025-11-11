package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CIIUControllerJpa;
import Controller.ConfigurationControllerJpa;
import java.util.List;
import javax.servlet.http.HttpSession;

import Controller.RoleControllerJpa;

public class Tag_CIIU extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        CIIUControllerJpa CIIUJpa = new CIIUControllerJpa();
        ConfigurationControllerJpa ConfigurationJpa = new ConfigurationControllerJpa();
        List lst_CIIU = null, lst_CIIUId = null, lst_Configuration = null, lst_ConfigurationVal = null;
        int IdCIIU = 0, State = 0, RsklvlC = 0, RsklvlCF = 0;
        try {
            IdCIIU = Integer.parseInt(pageContext.getRequest().getAttribute("IdCIIU").toString());
        } catch (Exception e) {
            IdCIIU = 0;
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
            if (IdCIIU > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE CIIU">
                lst_CIIUId = CIIUJpa.ConsultCIIU(IdCIIU);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_form_permi'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Codigo CIIU</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                if (lst_CIIUId != null) {
                    Object[] obj_CIUUId = (Object[]) lst_CIIUId.get(0);
                    out.print("<div class='cont_form_temp'>");
                    out.print("<form action='CIIU?opt=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdCIIU' value='" + IdCIIU + "' >");
                    out.print("<div class=''>");
                    out.print("<div class='col-12' style='display: flex; justify-content:space-between;'>");
                    out.print("<div style='width: 48%;' id='select2' data-toggle='tooltip' data-placement='top' title='Código'>");
                    out.print("<input type='text' class='form-control' name='Code' id='Code' value='" + obj_CIUUId[1] + "' placeholder='Código' style='margin-bottom: 12px;' required data-toggle='tooltip' data-placement='top' title='Código'>");

                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div style='width: 48%;' data-toggle='tooltip' data-placement='top' title='Nivel de riesgo'>");
                    out.print("<select class='form-control' name='RiskLevel' id='RiskLevel' required>");
                    lst_ConfigurationVal = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIUUId[3].toString()));
                    Object[] obj_ConfigutionVal = (Object[]) lst_ConfigurationVal.get(0);
                    out.print("<option selected value='" + obj_ConfigutionVal[0].toString() + "'>" + obj_ConfigutionVal[3].toString().split("//")[0] + "</option>");
                    RsklvlC = Integer.parseInt(obj_ConfigutionVal[0].toString());
                    lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("RiskLevel");
                    for (int i = 0; i < lst_Configuration.size(); i++) {
                        Object[] obj_Configution = (Object[]) lst_Configuration.get(i);
                        RsklvlCF = Integer.parseInt(obj_Configution[0].toString());
                        if (RsklvlC != RsklvlCF) {
                            out.print("<option value='" + obj_Configution[0] + "'>" + obj_Configution[3].toString().split("//")[0] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-12' style='margin-bottom: 12px;'>");
                    out.print("<input type='text' class='form-control' name='Txt_Activity' id='Txt_Activity' value='" + obj_CIUUId[2] + "' placeholder='Actividad' required data-toggle='tooltip' data-placement='top' title='Actividad'>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");
                    out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
                    out.print("<button class='btn btn-blue btn-lg'>Modificar</button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER CIIU">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='cont_form_permi'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Codigo CIIU</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_temp'>");
            out.print("<form action='CIIU?opt=2' method='post' class='needs-validation' novalidate=''>");
            out.print("<div class=''>");
            out.print("<div class='col-12' style='display: flex; justify-content:space-between;'>");
            out.print("<div style='width: 48%;' id='select2' data-toggle='tooltip' data-placement='top' title='Código'>");
            out.print("<input type='number' class='form-control' name='Code' id='Code' placeholder='Código' style='margin-bottom: 12px;' required data-toggle='tooltip' data-placement='top' title='Código'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div style='width: 48%;' data-toggle='tooltip' data-placement='top' title='Nivel de riesgo'>");
            out.print("<select class='form-control' name='RiskLevel' id='RiskLevel' required>");
            out.print("<option selected disabled value=''>Seleccione Nivel de riesgo</option>");
            lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("RiskLevel");
            for (int i = 0; i < lst_Configuration.size(); i++) {
                Object[] obj_Configution = (Object[]) lst_Configuration.get(i);
                out.print("<option value='" + obj_Configution[0] + "'>" + obj_Configution[3].toString().split("//")[0] + "</option>");
            }
            out.print("</select>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una prioridad.</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col-12' style='margin-bottom: 12px;'>");
            out.print("<input type='text' class='form-control' name='Txt_Activity' id='Txt_Activity' placeholder='Actividad' required data-toggle='tooltip' data-placement='top' title='Actividad'>");
            out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
            out.print("</div>");
            out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
            out.print("<button class='btn btn-blue btn-lg'>Registrar</button>");
            out.print("</div>");
            out.print("</div>");
            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Modulo CIIU</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h4>Listado de CIIU</h4>");
            if (txtPermissions.contains("[1]")) {
                out.print("<button class='btn btn-blue' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registar'><i class='fas fa-plus' data-toggle='tooltip' data-placement='top' title='Registar'></i></button>");
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
            out.print("<th>Codigo</th>");
            out.print("<th>Actividad</th>");
            out.print("<th>Nivel de Riesgo</th>");
            out.print("<th>Estado</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            lst_CIIU = CIIUJpa.ConsultCIIU();
            if (lst_CIIU != null) {
                for (int i = 0; i < lst_CIIU.size(); i++) {
                    Object[] obj_CIIU = (Object[]) lst_CIIU.get(i);
                    out.print("<tr>");
                    out.print("<td>" + obj_CIIU[0] + "</td>");
                    out.print("<td>" + obj_CIIU[1] + "</td>");
                    out.print("<td>" + obj_CIIU[2] + "</td>");
                    lst_ConfigurationVal = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                    if (lst_ConfigurationVal != null) {
                        Object[] obj_ConfigutionId = (Object[]) lst_ConfigurationVal.get(0);
                        out.print("<td>" + obj_ConfigutionId[3].toString().split("//")[0] + "</td>");
                    }
                    try {
                        State = Integer.parseInt(obj_CIIU[4].toString());
                    } catch (Exception e) {
                        State = 0;
                    }
                    out.print("<td style='text-align: center;'><div class='badge badge-" + ((State == 1) ? "success'>Activo" : "danger'>Inactivo") + " </div></td>");
                    out.print("<td style='display: flex;justify-content: center;'>");
                    if (txtPermissions.contains("[9]")) {
                        out.print("<button onclick=\"window.location.href='CIIU?opt=1&IdCIIU=" + obj_CIIU[0] + "'\" class='btn btn-warning mr-2' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></button>");
                        out.print("<button onclick=\"window.location.href='CIIU?opt=3&IdCIIU=" + obj_CIIU[0] + "&State=" + ((State == 1) ? "0" : "1") + "'\" class='btn btn-" + ((State == 1) ? "success" : "danger") + " mr-2' data-toggle='tooltip' data-placement='top' title='" + ((State == 1) ? "Activo" : "Inactivo") + "'><i class='fas fa-" + ((State == 1) ? "check" : "times") + "'></i></button>");
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
