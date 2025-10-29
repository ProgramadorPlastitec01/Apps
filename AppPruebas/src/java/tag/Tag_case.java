package tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import controlador.caseControllerJpa;
import controlador.appControllerJpa;
import controlador.settingControllerJpa;
import java.util.List;

public class Tag_case extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        caseControllerJpa caseJpa = new caseControllerJpa();
        appControllerJpa appJpa = new appControllerJpa();
        settingControllerJpa settingJpa = new settingControllerJpa();
        List lst_case = null;
        List lst_app = null;
        List lst_setting = null;
        int idCase = 0, idApp = 0;
        String event = "";
        try {

            try {
                event = pageContext.getRequest().getAttribute("event").toString();
            } catch (Exception e) {
                event = "";
            }
            if (event.equals("")) {
                //<editor-fold defaultstate="collapsed" desc="MODULE LIST APP CASE">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Setting?opt=1\"' data-toggle='tooltip' data-placement='top' title='Volver al menu'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Parametrización de casos</h2>");
                out.print("<h4></h4>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                out.print("<div class='card-body'>");
                out.print("<div class='row' style='justify-content: space-around;'>");

                lst_app = appJpa.ConsultAppsActiveV2();
                if (lst_app != null) {
                    for (int i = 0; i < lst_app.size(); i++) {
                        Object[] ObjApp = (Object[]) lst_app.get(i);
                        out.print("<div class='AppCont mr-2'>");
                        int ccs = Integer.parseInt(ObjApp[2].toString());
                        out.print("<div class='btnCounter'>");
                        if (ccs > 0) {
                            out.print("<button type='button' class='btn btn-warning btn-sm'><span class='badge badge-transparent'>" + ccs + "</span>&nbsp;</button>");
                        } else {
                            out.print("<button type='button' class='btn btn btn-sm' style='background: #afafaf;'><span class='badge badge-transparent'>" + ccs + "</span>&nbsp;</button>");
                        }
                        out.print("</div>");

                        out.print("<div class='text-center' style='margin-top: 20px; height: 45px;'>");
                        out.print("<h6>" + ObjApp[1] + "</h6>");
                        out.print("</div>");
                        out.print("<div class='text-center' style='margin-bottom: 20px'>");
                        out.print("<button class='btn btn-green' onclick='window.location.href=\"Case?opt=1&idApp=" + ObjApp[0] + "&event=Detail\"'><i class=\"fas fa-wrench\"></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                } else {
                    out.print("<div class=''>");
                    out.print("Se ha producido un error al consultar la información.");
                    out.print("</div>");

                }
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
//</editor-fold>
            } else if (event.equals("Detail")) {
                //<editor-fold defaultstate="collapsed" desc="MODULE CASE DATAIL OF APP">
                try {
                    idApp = Integer.parseInt(pageContext.getRequest().getAttribute("idApp").toString());
                } catch (Exception e) {
                    idApp = 0;
                }
                try {
                    idCase = Integer.parseInt(pageContext.getRequest().getAttribute("idCase").toString());
                } catch (Exception e) {
                    idCase = 0;
                }
                String nameApp = "";
                lst_app = appJpa.ConsultAppId(idApp);
                if (lst_app != null) {
                    Object[] ObjAp = (Object[]) lst_app.get(0);
                    nameApp = ObjAp[1].toString();
                }

                if (idCase > 0) {
                    //<editor-fold defaultstate="collapsed" desc="UPDATE CASE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='width: 70%; margin-left: 22%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Actualizar caso</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    lst_case = caseJpa.ConsultCaseId(idCase);
                    if (lst_case != null) {
                        Object[] ObjCase = (Object[]) lst_case.get(0);
                        out.print("<form action='Case?opt=2&idApp=" + idApp + "&event=" + event + "&idCase=" + ObjCase[0] + "' method='post' class='needs-validation' novalidate=''>");
                        out.print("<div class='d-flex' style='justify-content: space-around;'>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<input type='text' class='form-control' data-toggle='tooltip' data-placement='top' title='Nombre aplicacion' value='" + nameApp + "' disabled>");
                        out.print("</div>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<input type='text' class='form-control' name='txtNameCase' id='txtNameCase' data-toggle='tooltip' data-placement='top' title='Nombre del caso' placeholder='Nombre del caso' value='" + ObjCase[3] + "' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-4' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Modo'>");
                        int mde = Integer.parseInt(ObjCase[6].toString());
                        lst_setting = settingJpa.ConsultSettingCategorie("ModeCase");
                        out.print("<select class='form-control' name='cbxMode' style='margin-12px;' required>");
                        if (lst_setting != null) {
                            Object[] ObjStt = (Object[]) lst_setting.get(0);
                            String[] modes = ObjStt[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < modes.length; i++) {
                                String[] dta = modes[i].toString().split("/");
                                int idmde = Integer.parseInt(dta[0].toString());
                                if (idmde == mde) {
                                    out.print("<option value='" + idmde + "' selected>" + dta[1] + "</option>");
                                } else {
                                    out.print("<option value='" + idmde + "'>" + dta[1] + "</option>");
                                }
                            }
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='d-flex' >");

                        out.print("<div class='col-lg-6'>");
                        out.print("<div class='text-center'>");
                        out.print("<span class=''><b class='text-info'>SCRIPT PARA CONSULTAR</b></span>");
                        out.print("</div>");
                        out.print("<div class='editor-container mr-2 mt-2' style='height: 250px;'>");
                        out.print("<textarea id='editor3' name='txtScriptConsult' required>" + ObjCase[4] + "</textarea>");
                        out.print("</div>");
                        out.print("<div class='mt-3'>");
                        out.print("<div class='text-center'>");
                        out.print("<span class=''><b class='text-info'>CAMPOS PARA CONSULTAR</b></span>");
                        out.print("</div>");
                        out.print("<input type='text' class='form-control' name='txtFieldConsult' style='margin-left: 0px;' placeholder='Campos necesarios en el script...' value='" + ObjCase[5] + "' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<div class='text-center'>");
                        out.print("<span class=''><b class='text-warning'>SCRIPT PARA EJECUTAR</b></span>");
                        out.print("</div>");
                        out.print("<div class='editor-container mr-2 mt-2' style='height: 250px;'>");
                        out.print("<textarea id='editor4' name='txtScriptEject' required>" + ObjCase[7] + "</textarea>");
                        out.print("</div>");
                        out.print("<div class='mt-3'>");
                        out.print("<div class='text-center'>");
                        out.print("<span class=''><b class='text-warning'>CAMPOS PARA EJECUTAR</b></span>");
                        out.print("</div>");
                        out.print("<input type='text' class='form-control' name='txtFieldEject' style='margin-left: 0px;' placeholder='Campos necesarios en el script...' value='" + ObjCase[8] + "' >");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='text-center mt-2'>");
                        out.print("<button class='btn btn-green'>Actualizar</button>");
                        out.print("</div>");

                        out.print("</form>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h4>Se ha presentado un problema al cargar la información</h4>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTER CASE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_reg' style='width: 70%; margin-left: 22%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar caso</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Case?opt=2&idApp=" + idApp + "&event=" + event + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='d-flex' style='justify-content: space-around;'>");
                out.print("<div class='col-lg-4'>");
                out.print("<input type='text' class='form-control' data-toggle='tooltip' data-placement='top' title='Nombre aplicacion' value='" + nameApp + "' disabled>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<input type='text' class='form-control' name='txtNameCase' id='txtNameCase' data-toggle='tooltip' data-placement='top' title='Nombre del caso' placeholder='Nombre del caso' value='' autocomplete='off' required>");
                out.print("</div>");
//                out.print("<div class='col-lg-3'>");
//                out.print("<input type='text' class='form-control' name='txtConsultValue' id='txtConsultValue' data-toggle='tooltip' data-placement='top' title='Valor de consulta' placeholder='[Consulta][nombre/tipo]' value=''>");
//                out.print("</div>");
                out.print("<div class='col-lg-4' style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title='Modo'>");
                out.print("<select class='form-control' name='cbxMode' style='margin-12px;' required>");
                out.print("<option selected disabled value=''>Seleccionar tipo de caso</option>");
                lst_setting = settingJpa.ConsultSettingCategorie("ModeCase");
                if (lst_setting != null) {
                    Object[] ObjStt = (Object[]) lst_setting.get(0);
                    String[] modes = ObjStt[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < modes.length; i++) {
                        String[] dta = modes[i].toString().split("/");
                        out.print("<option value='" + dta[0] + "'>" + dta[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='d-flex' >");

                out.print("<div class='col-lg-6'>");
                out.print("<div class='text-center'>");
                out.print("<span class=''><b class='text-info'>SCRIPT PARA CONSULTAR</b></span>");
                out.print("</div>");
                out.print("<div class='editor-container mr-2 mt-2' style='height: 250px;'>");
                out.print("<textarea id='editor' name='txtScriptConsult' required>SQL CODE...</textarea>");
                out.print("</div>");
                out.print("<div class='mt-3'>");
                out.print("<div class='text-center'>");
                out.print("<span class=''><b class='text-info'>CAMPOS PARA CONSULTAR</b></span>");
                out.print("</div>");
                out.print("<input type='text' class='form-control' name='txtFieldConsult' style='margin-left: 0px;' placeholder='Campos necesarios en el script...' autocomplete='off' required>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-6'>");
                out.print("<div class='text-center'>");
                out.print("<span class=''><b class='text-warning'>SCRIPT PARA EJECUTAR</b></span>");
                out.print("</div>");
                out.print("<div class='editor-container mr-2 mt-2' style='height: 250px;'>");
                out.print("<textarea id='editor2' name='txtScriptEject' required>SQL CODE...</textarea>");
                out.print("</div>");
                out.print("<div class='mt-3'>");
                out.print("<div class='text-center'>");
                out.print("<span class=''><b class='text-warning'>CAMPOS PARA EJECUTAR</b></span>");
                out.print("</div>");
                out.print("<input type='text' class='form-control' name='txtFieldEject' style='margin-left: 0px;' placeholder='Campos necesarios en el script...' autocomplete='off'>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("<div class='text-center mt-2'>");
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
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Case?opt=1\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Casos de aplicativo " + nameApp + "</h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Nombre del caso</th>");
                out.print("<th>Modo</th>");
                out.print("<th>Script consulta</th>");
                out.print("<th>Campos consulta</th>");
                out.print("<th>Script ejecución</th>");
                out.print("<th>Campos ejecución</th>");
                out.print("<th>Estado</th>");
                out.print("<th>OPC</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_case = caseJpa.ConsultCaseAppId(idApp);
                if (lst_case != null) {
                    for (int i = 0; i < lst_case.size(); i++) {
                        Object[] ObjCase = (Object[]) lst_case.get(i);
                        out.print("<tr>");
                        out.print("<td>" + ObjCase[3] + "</td>");
                        int mod = Integer.parseInt(ObjCase[6].toString());
                        out.print("<td>" + ((mod == 1) ? "Actualizar" : "Eliminar") + "</td>");
                        out.print("<td>" + ObjCase[4] + "</td>");
                        out.print("<td>" + ObjCase[5] + "</td>");
                        out.print("<td>" + ObjCase[7] + "</td>");
                        out.print("<td>" + ObjCase[8] + "</td>");
                        int ste = Integer.parseInt(ObjCase[9].toString());
                        out.print("<td><div class='badge badge-" + ((ste == 1) ? "success" : "danger") + "'>" + ((ste == 1) ? "Activo" : "Inactivo") + "</td>");
                        out.print("<td>");
                        out.print("<div class='d-flex'>");
                        out.print("<button class='btn btn-" + ((ste == 1) ? "success" : "danger") + " mr-2 btn-sm' onclick='window.location.href=\"Case?opt=3&idApp=" + idApp + "&idCase=" + ObjCase[0] + "&event=Detail\"' data-toggle='tooltip' data-placement='top' title='Cambiar Estado'><i class='" + ((ste == 1) ? "fas fa-check" : "fas fa-times") + "'></i></button>");
                        out.print("<button class='btn btn-warning btn-sm' onclick='window.location.href=\"Case?opt=1&idApp=" + idApp + "&idCase=" + ObjCase[0] + "&event=Detail\"'><i class='fas fa-edit'></i></button>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr class='text-center'>");
                    out.print("<td colspan='7'>Aun no existen casos registrados para el aplicativo " + nameApp + "</td>");
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

                //</editor-fold>
            }

        } catch (IOException ex) {
            Logger.getLogger(Tag_case.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
