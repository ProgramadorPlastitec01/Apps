package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.AppControllerJpa;
import Controller.ScheduleControllerJpa;
import Controller.FormatControllerJpa;
import Controller.RoleControllerJpa;
import SQL.ConnectionsBd;
import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_schedule extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        String IdRol = sesion.getAttribute("idRol").toString();
        AppControllerJpa AppJpa = new AppControllerJpa();
        ScheduleControllerJpa ScheduleJpa = new ScheduleControllerJpa();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        ConnectionsBd SirhJpa = new ConnectionsBd();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_app = null, lst_schedule = null, lst_scheduleId = null, lst_format = null, lst_sirh = null, lst_role = null, lst_year = null;
        String module = "", signt = "", advice = "", Permissions = "", Year = "", activityFilter = "";
        int IdSchedule = 0, temp = 0, type = 0, iterator2 = 0, iterator3 = 0, idRol = 0;
        try {
            //<editor-fold defaultstate="collapsed" desc="DECLARATION">
            try {
                IdSchedule = Integer.parseInt(pageContext.getRequest().getAttribute("IdSchedule").toString());
            } catch (Exception e) {
                IdSchedule = 0;
            }
            try {
                temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
            } catch (Exception e) {
                temp = 0;
            }
            try {
                module = pageContext.getRequest().getAttribute("module").toString();
            } catch (Exception e) {
                module = "";
            }
            try {
                type = Integer.parseInt(pageContext.getRequest().getAttribute("type").toString());
            } catch (Exception e) {
                type = 0;
            }
            try {
                Year = pageContext.getRequest().getAttribute("Year").toString();
            } catch (Exception e) {
                Year = "";
            }
            try {
                activityFilter = pageContext.getRequest().getAttribute("activityFilter").toString();
            } catch (Exception e) {
                activityFilter = "";
            }
            try {
                idRol = Integer.parseInt(IdRol);
                lst_role = RoleJpa.ConsultRoleId(idRol);
                Object[] obj_permi = (Object[]) lst_role.get(0);
                Permissions = obj_permi[2].toString();
            } catch (NumberFormatException e) {
                idRol = 0;
                Permissions = "";
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MANAGEMENT">
            if (IdSchedule > 0) {
                //<editor-fold defaultstate="collapsed" desc="FORM UPDATE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar Actividad </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_scheduleId = ScheduleJpa.ConsultScheduleTypeId(IdSchedule);

                if (lst_scheduleId != null) {
                    Object[] obj_schedule = (Object[]) lst_scheduleId.get(0);
                    out.print("<form action='Schedule?opt=2' method='post' class='needs-validation' novalidate=''>");

                    out.print("<div class='col-11 text-center'>");
                    out.print("<div class=\"selectgroup selectgroup-pills\">\n");

                    if (type == 1) {
                        out.print("<label class=\"selectgroup-item\">\n");
                        out.print("<input type=\"radio\" name=\"type\" value=\"1\" class=\"selectgroup-input\"  " + ((type == 1) ? "checked" : "") + " >\n");
                        out.print("<span class=\"selectgroup-button selectgroup-button-icon\" style='border-radius:4px !important;'><i class=\"fas fa-server\"></i> Hardware</span>\n");
                        out.print("</label>\n");
                    } else {
                        out.print("<label class=\"selectgroup-item\">\n");
                        out.print("<input type=\"radio\" name=\"type\" value=\"2\" class=\"selectgroup-input\" " + ((type == 2) ? "checked" : "") + ">\n");
                        out.print("<span class=\"selectgroup-button selectgroup-button-icon\" style='border-radius:4px !important; '><i class=\"fas fa-database\"></i> Software</span>\n");
                        out.print("</label>\n");
                    }

                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='d-flex'>");

                    out.print("<div style='width:50%'>");
                    //<editor-fold defaultstate="collapsed" desc="CONTENT ACTIVITY TEC-DEV">
                    if (type == 1) {
                        out.print("<div class='col-11' style='display:" + ((type == 1) ? "block" : "none") + "' >");
                        out.print("<input class=\"form-control\" name='txt_activityTec' placeholder=\"Nombre de la actividad\" value='" + obj_schedule[4] + "' autocomplete='off' " + ((type == 1) ? "required" : "") + ">");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='col-11' style='display:" + ((type == 2) ? "display:block" : "none") + "' >");
                        out.print("<input class='form-control' list='datalistOptions' name='txt_activityProg' placeholder='Seleccione o escriba actividad' value='" + obj_schedule[4] + "' autocomplete='off' oninput='AsigmentType();' " + ((type == 2) ? "required" : "") + ">");
                        out.print("<datalist id='datalistOptions'>");
                        lst_app = AppJpa.ConsultActiveApp();
                        if (lst_app != null) {
                            for (int i = 0; i < lst_app.size(); i++) {
                                Object[] ObjApp = (Object[]) lst_app.get(i);
                                out.print("<option value='" + ObjApp[0] + "-" + ObjApp[1] + "'></option>");
                            }
                        }

                        out.print("</datalist>");
                        out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div id='' style='display:flex;width:50%'>");
                    //<editor-fold defaultstate="collapsed" desc="COLOR">
                    out.print("<div class=\"input-group colorpickerinput\" style='width:80%'>\n"
                            + "<input type=\"text\" id=\"colorInput2\" class=\"form-control colorpickerinput colorpicker-element\" autocomplete='off' name='color' value='" + ((obj_schedule[19] == null) ? "" : obj_schedule[19]) + "' style='margin:0px; margin-left:12px !important;  margin-top:12px !important'>\n"
                            + "</div>");
                    out.print("<div class=\"input-group-text\" id=\"iconContainer\" style='margin-top:12px;width:12%'>\n"
                            + "    <i class='fas fa-fill-drip' id='myIconU'  style='color:" + ((obj_schedule[19] == null) ? "" : obj_schedule[19]) + "; cursor:pointer'></i>\n"
                            + "</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    String monthDB = "";
                    String monthAct = "";
                    String[] FirstSemester = {"1/ENERO", "2/FEBRERO", "3/MARZO", "4/ABRIL", "5/MAYO", "6/JUNIO", "7/JULIO", "8/AGOSTO", "9/SEPTIEMBRE", "10/OCTUBRE", "11/NOVIEMBRE", "12/DICIEMBRE"};
                    out.print("<div class='card-body'>");
                    out.print("<div class='d-flex justify-content-around row col-lg-12'>");
                    for (int i = 0; i < FirstSemester.length; i++) {
                        out.print("<div class='col-lg-2'>");
                        out.print("<b>" + FirstSemester[i].split("/")[1] + "</b>");
                        monthDB = obj_schedule[5].toString().split("-")[1];
                        monthAct = FirstSemester[i].split("/")[0];
                        if (monthDB.equals(monthAct)) {
                            out.print("<input type='checkbox' onclick='Masive(this.value);' value='" + (i + 1) + "' class='form-control' style='width:30%' checked>");
                        } else {
                            out.print("<input type='checkbox' onclick='Masive(this.value);' value='" + (i + 1) + "' class='form-control' style='width:30%'>");
                        }
                        out.print("</div>");

                    }
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='d-flex justify-content-around'>");

                    out.print("<div class='text-center mt-2'>");
                    out.print("<button  class='btn btn-green'>Modificar</button>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<input type='hidden' name='app' id='TypeApp' value='0'>");
                    out.print("<input type='hidden' name='month' id='ChMonth' value='[" + monthDB + "]'>");
                    out.print("<input type='hidden' name='IdSchedule' value='" + IdSchedule + "'>");
                    out.print("<input type='hidden' name='SC' value='" + 0 + "'>");
                    out.print("<input type='hidden' name='module' value='" + module + "'>");
                    out.print("<input type='hidden' name='Year' value='" + Year + "'>");
                    out.print("</form>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="FORM REGISTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + ((temp == 1) ? "none" : (temp == 2) ? "block" : "none") + ";'>");
            out.print("<div class='contGeneral'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar Actividad </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");

            out.print("<form action='Schedule?opt=2' method='post' class='needs-validation' novalidate=''>");

            out.print("<div class='col-11 text-center'>");
            out.print("<div class=\"selectgroup selectgroup-pills\">\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" name=\"type\" onclick='ChangeType(1)' value=\"1\" class=\"selectgroup-input\"  " + ((type == 1) ? "checked" : "") + " >\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon\" style='border-radius:4px !important;'><i class=\"fas fa-server\"></i> Hardware</span>\n"
                    + "                        </label>\n"
                    + "                        <label class=\"selectgroup-item\">\n"
                    + "                          <input type=\"radio\" name=\"type\" onclick='ChangeType(0)' value=\"2\" class=\"selectgroup-input\" " + ((type == 2) ? "checked" : "") + ">\n"
                    + "                          <span class=\"selectgroup-button selectgroup-button-icon\" style='border-radius:4px !important; '><i class=\"fas fa-database\"></i> Software</span>\n"
                    + "                        </label>\n"
                    + "                      </div>");
            out.print("</div>");

            out.print("<div class='d-flex'>");

            out.print("<div style='width:50%'>");
            //<editor-fold defaultstate="collapsed" desc="CONTENT ACTIVITY TEC-DEV">
            out.print("<div class='col-11' id='Technique' style='display:" + ((type == 1) ? "block" : "none") + "' >");
            out.print("<input class=\"form-control\" id='Input1' name='txt_activityTec' placeholder=\"Nombre de la actividad\"  autocomplete='off' " + ((type == 1) ? "required" : "") + ">");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");

            out.print("<div class='col-11' id='Developer' style='display:" + ((type == 2) ? "display:block" : "none") + "' >");
            out.print("<input class='form-control' list='datalistOptions' id='Input2' name='txt_activityProg' placeholder='Seleccione o escriba actividad' value='' autocomplete='off' oninput='AsigmentType();' " + ((type == 2) ? "required" : "") + ">");
            out.print("<datalist id='datalistOptions'>");

            lst_app = AppJpa.ConsultActiveApp();
            if (lst_app != null) {
                for (int i = 0; i < lst_app.size(); i++) {
                    Object[] ObjApp = (Object[]) lst_app.get(i);
                    out.print("<option value='" + ObjApp[0] + "-" + ObjApp[1] + "'></option>");
                }
            }

            out.print("</datalist>");
            out.print("<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");

            out.print("<div id='' style='display:flex;width:50%'>");
            //<editor-fold defaultstate="collapsed" desc="COLOR">
            out.print("<div class=\"input-group colorpickerinput\" style='width:80%'>\n"
                    + "<input type=\"text\" id=\"colorInput\" class=\"form-control colorpickerinput colorpicker-element\" autocomplete='off' name='color' style='margin:0px; margin-left:12px !important;  margin-top:12px !important'>\n"
                    + "</div>");
            out.print("<div class=\"input-group-text\" id=\"iconContainer\" style='margin-top:12px;width:12%'>\n"
                    + "    <i class='fas fa-fill-drip' id='myIcon' style='cursor:pointer'></i>\n"
                    + "</div>");
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");

            String[] FirstSemester = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
            out.print("<div class='card-body'>");
            out.print("<div class='d-flex justify-content-around row col-lg-12'>");
            for (int i = 0; i < FirstSemester.length; i++) {
                out.print("<div class='col-lg-2'>");
                out.print("<b>" + FirstSemester[i] + "</b>");
                out.print("<input type='checkbox' onclick='Masive(this.value);' value='" + (i + 1) + "' class='form-control' style='width:30%' >");
                out.print("</div>");

            }
            out.print("</div>");
            out.print("</div>");
//
//
            out.print("<div class='d-flex justify-content-around'>");

            out.print("<div class='text-center mt-2'>");
            out.print("<button onclick='SaveContinue(\"2\")' class='btn btn-info'>Registrar y Continuar</button>");
            out.print("</div>");

            out.print("<div class='text-center mt-2'>");
            out.print("<button  onclick='SaveContinue(\"1\")' class='btn btn-green'>Registrar y Cerrar</button>");
            out.print("</div>");

            out.print("</div>");

            out.print("<input type='hidden' name='app' id='TypeApp' value='0'>");
            out.print("<input type='hidden' name='month' id='ChMonth' value=''>");
            out.print("<input type='hidden' name='IdSchedule' value='0'>");
            out.print("<input type='hidden' name='SC' id='SCN' value=''>");
            out.print("<input type='hidden' name='module' value='" + module + "'>");
            out.print("<input type='hidden' name='Year' value='" + Year + "'>");
            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MODAL SIGNATURE MASIVE">
            out.print("<div class='sweet-local' tabindex='-1' id='View1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h3>Firmar Actividad </h3>");
            out.print("<button class='btn btn-outline-secondary' onclick='EnableDivView(1);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Schedule?opt=4' method='post' class='needs-validation' novalidate=''>");

            out.print("<input type='hidden' name='module' value='" + module + "'>");
            out.print("<input type='hidden' name='type' value='" + type + "'>");

            out.print("<input type='hidden' id='IdMasive' name='IdMasive' value=''>");
            out.print("<input type='hidden' name='validation' value='2'>");

            out.print("<div class='d-flex align-items-center'>");

            out.print("<div class='col-lg-6'>");
            out.print("<div id='ContAct' class='DivSignatureAct' data-toggle='tooltip' data-placement='top' title='Actividades' disabled></div>");
            out.print("</div>");

            out.print("<div class='col-lg-6'>");
            out.print("<input type='date' class='form-control' id='' name='date' placeholder='Fecha Actividad' value='' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Fecha\" autocomplete='off' required>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='text-center mt-2'>");
            out.print("<button onclick='' class='btn btn-info'>Firmar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MODAL SIGNATURE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h3>Firmar Actividad </h3>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3);SignatureR026(0,0);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Schedule?opt=3' method='post' class='needs-validation' novalidate=''>");
            out.print("<input type='hidden' id='IdSg' name='IdSchedule' value=''>");
            out.print("<input type='hidden' id='Validation' name='validation' value=''>");
            out.print("<input type='hidden' id='' name='module' value='" + module + "'>");
            out.print("<input type='hidden' id='' name='type' value='" + type + "'>");
            out.print("<div class='d-flex'>");

            out.print("<div class='col-lg-6'>");
            out.print("<input type='text' class='form-control' id='Act' name='' value='' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Actividad\" autocomplete='off' disabled>");
            out.print("</div>");

            out.print("<div class='col-lg-6'>");
            out.print("<input type='date' class='form-control' id='' name='date' placeholder='Fecha Actividad' value='' data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Fecha\" autocomplete='off' required>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='text-center mt-2'>");
            out.print("<button class='btn btn-info'>Firmar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="FILTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral'>");

            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h3>Filtro</h3>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5);' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<div class='cont_form_user'>");
            out.print("<form action='Schedule?opt=1' method='post' class='needs-validation' novalidate=''>");

            out.print("<input type='hidden' name='module' value='" + module + "'>");
            out.print("<input type='hidden' name='type' value='" + type + "'>");

            out.print("<div class='d-flex'>");

            out.print("<div class='col-lg-6' id=''>");
            out.print("<select class='form-control' name='Year' style='margin:12px'>");
            out.print("<option value='" + Year + "'>" + Year + "</option>");
            lst_year = ScheduleJpa.ConsultScheduleTypeYear();
            if (lst_year != null) {
                for (int i = 0; i < lst_year.size(); i++) {
                    Object[] obj_year = (Object[]) lst_year.get(i);
                    if (!Year.equals(obj_year[1])) {
                        out.print("<option value='" + obj_year[1] + "'>" + obj_year[1] + "</option>");
                    }
                }
            }
            out.print("</select>");
            out.print("</div>");

            out.print("<div class='col-lg-6' id=''>");
            out.print("<input class=\"form-control\" id='' name='activityFilter' placeholder=\"Nombre de la actividad\"  autocomplete='off'>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='text-center mt-2'>");
            out.print("<button class='btn btn-info'>Consultar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //</editor-fold>
            out.print("<section class='section'>");
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-12\">");
            out.print("<div class=\"card\">");
            out.print("<div class=\"card-header\" style='justify-content: space-between;'>");
            out.print("<div><h4>Módulo R-TI-026</h4></div>");
            //<editor-fold defaultstate="collapsed" desc="OPTION">
            out.print("<div class='d-flex'>");
            out.print("<div><button class=\"btn btn-green mr-4\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" onclick=\"location.href='Schedule?opt=1&type=" + ((type == 1) ? 2 : 1) + "&module=" + module + "&Year=" + Year + "'\" data-original-title=\"Tipo\"><i class=\"fas fa-" + ((type == 1) ? "server" : "database") + "\"></i></button></div>");
            if (!activityFilter.equals("")) {
                out.print("<div><button class=\"btn btn-danger mr-4\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" onclick=\"location.href='Schedule?opt=1&type=" + type + "&module=" + module + "&Year=" + Year + "'\" data-original-title=\"Filtrar\"><i class=\"fas fa-times\"></i></button></div>");
            }
            out.print("<div><button class=\"btn btn-green mr-4\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" onclick=\"mostrarConvencion(5)\" data-original-title=\"Filtrar\"><i class=\"fas fa-search\"></i></button></div>");
            if (module.equals("Report")) {
                out.print("<div><button class=\"btn btn-info mr-4\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\"  onclick=\"location.href='Schedule?opt=1&type=" + type + "&module=Schedule&Year=" + Year + "'\" data-original-title=\"Calendario\"><i class=\"fas fa-calendar\"></i></button></div>");
                out.print("<div><button class=\"btn btn-green mr-4\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\"  onclick='EnableDivView(1)' data-original-title=\"Firma Masiva\"><i class=\"fas fa-signature\"></i></button></div>");
            } else {
                out.print("<div><button class=\"btn btn-info mr-4\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\"  onclick=\"location.href='Schedule?opt=1&type=" + type + "&module=Report&Year=" + Year + "'\" data-original-title=\"Reporte\"><i class=\"fas fa-file-contract\"></i></button></div>");
            }
            out.print("<div><button class=\"btn btn-green\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" onclick=\"mostrarConvencion(1)\" data-original-title=\"Registar\"><i class=\"fas fa-plus\"></i></button></div>");
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");
            if (module.equals("Report")) {
                //<editor-fold defaultstate="collapsed" desc="REPORT">
                out.print("<div class=\"card-body\">");
                lst_format = FormatJpa.ConsultFormatName("R-TI-026");
                if (lst_format != null) {
                    Object[] obj_format = (Object[]) lst_format.get(0);
                    //<editor-fold defaultstate="collapsed" desc="HEAD">
                    String Format = obj_format[3].toString();
                    Format = Format.replace("XTPOX", ((type == 1) ? "HARDWARE" : "SOFTWARE"));
                    Format = Format.replace("XANIOX", ((Integer.parseInt(Year.toString()) > 0) ? Year : "SOFTWARE"));
                    out.print(Format);
                    out.print("<tr class='tr026' >");
                    out.print("<td class='p-2 sticky2'><input type='checkbox' id='masterCheckbox' onclick='toggleAllCheckboxes(this)'></td>");
                    out.print("<td class='p-2 sticky2'>ACTIVIDAD</td>");
                    out.print("<td class='p-2 sticky2'>VERIFICADO</td>");
                    out.print("<td class='p-2 sticky2'>REVISADO</td>");
                    String[] MonthAbbreviation = {"ENE", "FEB", "MAR", "MAY", "ABR", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
                    for (int i = 0; i < MonthAbbreviation.length; i++) {
                        out.print("<td class='p-2 sticky2'>" + MonthAbbreviation[i] + "</td>");
                    }
                    out.print("</tr>");
                    //</editor-fold>
                    if (!activityFilter.equals("")) {
                        lst_schedule = ScheduleJpa.ConsultScheduleTypeFilter(Year, activityFilter);
                    } else {
                        lst_schedule = ScheduleJpa.ConsultScheduleType(Year, type);
                    }
                    if (lst_schedule != null) {
                        for (int i = 0; i < lst_schedule.size(); i++) {
                            Object[] obj_schedule = (Object[]) lst_schedule.get(i);
                            out.print("<input type='hidden' id='IdSc" + i + "' value='" + obj_schedule[0] + "'>");
                            out.print("<input type='hidden' id='Activity" + i + "' value='" + (obj_schedule[4].equals("VERIFICACION DE CONTROL") ? (obj_schedule[4] + " " + obj_schedule[3]) : obj_schedule[4]) + "'>");
                            out.print("<tr style='font-size:13px'>");
                            if (obj_schedule[6] != null && obj_schedule[8] != null) {
                                out.print("<td class='p-2'><input type='checkbox' class='form-control' disabled></td>");
                                out.print("<td class='p-2'><span>" + (obj_schedule[4].equals("VERIFICACION DE CONTROL") ? (obj_schedule[4] + " " + obj_schedule[3]) : obj_schedule[4]) + "</span></td>");
                            } else {
                                out.print("<td class='p-2'><input type='checkbox' class='form-control tst' data-id='" + obj_schedule[0] + "' data-act='" + obj_schedule[4] + "' onclick='MasiveActivity(" + obj_schedule[0] + ");ActivityTextarea(\"" + obj_schedule[4] + "\")'></td>");
                                out.print("<td class='p-2'><span onclick=\"location.href='Schedule?opt=1&IdSchedule=" + obj_schedule[0] + "&temp=" + temp + "&type=" + type + "&module=" + module + "'\">" + (obj_schedule[4].equals("VERIFICACION DE CONTROL") ? (obj_schedule[4] + " " + obj_schedule[3]) : obj_schedule[4]) + "</span></td>");
                            }

                            if (obj_schedule[6] != null && obj_schedule[10] != null) {
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE EXECUTE">
                                String[] ArgData = obj_schedule[20].toString().split("/");
                                int doc = Integer.parseInt(ArgData[0]);
                                int cod = Integer.parseInt(ArgData[1]);
                                lst_sirh = SirhJpa.Consultar_firmasDoc(doc, cod);

                                if (lst_sirh != null) {
                                    String[] ObjSig = lst_sirh.toString().split("///");

                                    Gson gson = new Gson();
                                    signt = gson.toJson(ObjSig[3]);

                                    int canvasWidth = 130; // ancho del nuevo canvas
                                    int canvasHeight = 45;  // alto del nuevo canvas

                                    int originalWidth = 500;  // ancho original del canvas
                                    int originalHeight = 250; // alto original del canvas

                                    double scaleX = (double) canvasWidth / originalWidth;  // Factor de escala horizontal
                                    double scaleY = (double) canvasHeight / originalHeight; // Factor de escala vertical

                                    double offsetX = 0;
                                    double offsetY = 0;

                                    advice = "<canvas id='signature-canvas-" + iterator2 + i + "' width='" + canvasWidth + "' height='" + canvasHeight + "' style='border:1px solid #fff;'></canvas>"
                                            + "<script>"
                                            + "function dibujarCoordenadas_" + iterator2 + i + "() { "
                                            + "   const canvas = document.getElementById('signature-canvas-" + iterator2 + i + "'); "
                                            + "   const ctx = canvas.getContext('2d'); "
                                            + "   const coordenadas = JSON.parse(" + signt + "); "
                                            + "   ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                            + "   coordenadas.forEach(coord => { "
                                            + "       const scaledLX = (coord.lx * " + scaleX + ") + " + offsetX + ";"
                                            + "       const scaledLY = (coord.ly * " + scaleY + ") + " + offsetY + ";"
                                            + "       const scaledMX = (coord.mx * " + scaleX + ") + " + offsetX + ";"
                                            + "       const scaledMY = (coord.my * " + scaleY + ") + " + offsetY + ";"
                                            + "       ctx.beginPath(); "
                                            + "       ctx.moveTo(scaledLX, scaledLY); "
                                            + "       ctx.lineTo(scaledMX, scaledMY); "
                                            + "       ctx.strokeStyle = 'black'; "
                                            + "       ctx.lineWidth = 0.6; "
                                            + "       ctx.stroke(); "
                                            + "   }); "
                                            + "} "
                                            + "dibujarCoordenadas_" + iterator2 + i + "();"
                                            + "</script>";
                                    out.print("<script>");
                                    out.print("function dibujarCoordenadas() { "
                                            + "            const canvas = document.getElementById('signature-canvas'); "
                                            + "            const ctx = canvas.getContext('2d'); "
                                            + "            const coordenadas = JSON.parse(document.getElementById('coordenadas-hidden').value); "
                                            + "             "
                                            + "            ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                            + "             "
                                            + "            coordenadas.forEach(coord => { "
                                            + "                ctx.beginPath(); "
                                            + "                ctx.moveTo(coord.lx, coord.ly); "
                                            + "                ctx.lineTo(coord.mx, coord.my);  "
                                            + "                ctx.strokeStyle = 'black';  "
                                            + "                ctx.lineWidth = 2;  "
                                            + "                ctx.stroke(); "
                                            + "            }); "
                                            + "        } "
                                            + " "
                                            + "        window.onload = dibujarCoordenadas;");
                                    out.print("</script>");
                                    out.print("</div>");
                                    out.print("<input type='hidden' class='form-control' name='' id='coordenadas-hidden' value='" + signt + "'>");
                                    out.print("<td class='text-center'>");
                                    out.print(obj_schedule[10]);
                                    out.print(advice);
                                    out.print("</td>");
                                }
//                                out.print("<td class='text-center'>" + obj_schedule[7] + "<br>" + obj_schedule[10] + "</td>");
//</editor-fold>
                            } else {
                                out.print("<td class='text-center'>"
                                        + "<button class=\"btn btn-green btn-sm\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" onclick='mostrarConvencion(3);SignatureR026(" + i + ",1)' data-original-title=\"Firmar\" ><i class=\"fas fa-signature\"></i></button>"
                                        + "</td>");
                            }
                            if (obj_schedule[8] != null && obj_schedule[11] != null) {
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE CHECKED">
                                String[] ArgData = obj_schedule[21].toString().split("/");
                                int doc = Integer.parseInt(ArgData[0]);
                                int cod = Integer.parseInt(ArgData[1]);
                                lst_sirh = SirhJpa.Consultar_firmasDoc(doc, cod);

                                if (lst_sirh != null) {
                                    String[] ObjSig = lst_sirh.toString().split("///");

                                    Gson gson = new Gson();
                                    signt = gson.toJson(ObjSig[3]);

                                    int canvasWidth = 130; // ancho del nuevo canvas
                                    int canvasHeight = 45;  // alto del nuevo canvas

                                    int originalWidth = 500;  // ancho original del canvas
                                    int originalHeight = 250; // alto original del canvas

                                    double scaleX = (double) canvasWidth / originalWidth;  // Factor de escala horizontal
                                    double scaleY = (double) canvasHeight / originalHeight; // Factor de escala vertical

                                    double offsetX = 0;
                                    double offsetY = 0;

                                    advice = "<canvas id='signature-canvas1-" + iterator3 + i + "' width='" + canvasWidth + "' height='" + canvasHeight + "' style='border:1px solid #fff;'></canvas>"
                                            + "<script>"
                                            + "function dibujarCoordenadas_" + iterator3 + i + "() { "
                                            + "   const canvas = document.getElementById('signature-canvas1-" + iterator3 + i + "'); "
                                            + "   const ctx = canvas.getContext('2d'); "
                                            + "   const coordenadas = JSON.parse(" + signt + "); "
                                            + "   ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                            + "   coordenadas.forEach(coord => { "
                                            + "       const scaledLX = (coord.lx * " + scaleX + ") + " + offsetX + ";"
                                            + "       const scaledLY = (coord.ly * " + scaleY + ") + " + offsetY + ";"
                                            + "       const scaledMX = (coord.mx * " + scaleX + ") + " + offsetX + ";"
                                            + "       const scaledMY = (coord.my * " + scaleY + ") + " + offsetY + ";"
                                            + "       ctx.beginPath(); "
                                            + "       ctx.moveTo(scaledLX, scaledLY); "
                                            + "       ctx.lineTo(scaledMX, scaledMY); "
                                            + "       ctx.strokeStyle = 'black'; "
                                            + "       ctx.lineWidth = 0.6; "
                                            + "       ctx.stroke(); "
                                            + "   }); "
                                            + "} "
                                            + "dibujarCoordenadas_" + iterator3 + i + "();"
                                            + "</script>";
                                    out.print("<script>");
                                    out.print("function dibujarCoordenadas() { "
                                            + "            const canvas = document.getElementById('signature-canvas1'); "
                                            + "            const ctx = canvas.getContext('2d'); "
                                            + "            const coordenadas = JSON.parse(document.getElementById('coordenadas-hidden').value); "
                                            + "             "
                                            + "            ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                            + "             "
                                            + "            coordenadas.forEach(coord => { "
                                            + "                ctx.beginPath(); "
                                            + "                ctx.moveTo(coord.lx, coord.ly); "
                                            + "                ctx.lineTo(coord.mx, coord.my);  "
                                            + "                ctx.strokeStyle = 'black';  "
                                            + "                ctx.lineWidth = 2;  "
                                            + "                ctx.stroke(); "
                                            + "            }); "
                                            + "        } "
                                            + " "
                                            + "        window.onload = dibujarCoordenadas;");
                                    out.print("</script>");
                                    out.print("</div>");
                                    out.print("<input type='hidden' class='form-control' name='' id='coordenadas-hidden' value='" + signt + "'>");
                                    out.print("<td class='text-center'>");
                                    out.print(obj_schedule[11]);
                                    out.print(advice);
                                    out.print("</td>");
                                }
                                //</editor-fold>
                            } else {
                                if (obj_schedule[6] != null && obj_schedule[10] != null) {
                                    out.print("<td class='text-center'>"
                                            + "<button class=\"btn btn-green btn-sm\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" onclick=\"mostrarConvencion(3);SignatureR026(" + i + ",2)\" data-original-title=\"Firmar\"><i class=\"fas fa-signature\"></i></button>"
                                            + "</td>");
                                } else {
                                    out.print("<td class='text-center'>"
                                            + "<button class=\"btn btn-green btn-sm\" style=\"border-radius: 4px;\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\"  data-original-title=\"Pendiente Verificar\" disabled><i class=\"fas fa-signature\"></i></button>"
                                            + "</td>");
                                }
                            }
                            for (int j = 0; j < 12; j++) {
                                String[] monthChecked = obj_schedule[5].toString().split("-");
                                out.print("<td align='center'>");
                                if (monthChecked.length == 2) {
                                    if ((j + 1) == Integer.parseInt(monthChecked[1])) {
                                        //VALIDATION SIGNATURE EXECUTE
                                        if (obj_schedule[7] != null) {
                                            out.print("<b class='title'><i style='font-size:16px;' class='fas fa-check mt-2'></i></b>");
                                        } else {
                                            out.print("<b class='title'><i style='font-size:16px;' class='fas fa-times mt-2'></i></b>");
                                        }
                                    }
                                }else{
                                    out.print("<b>Fallo en estructura</b>");
                                }
                                out.print("</td>");
                            }
                            out.print("</tr>");
                        }

                    }
                    out.print("</table>");
                }

                out.print("</div>");

                //</editor-fold>
            } else if (module.equals("Schedule")) {
                //<editor-fold defaultstate="collapsed" desc="SCHEDULE">
                out.print("<div class=\"card-body\">");

                lst_schedule = ScheduleJpa.ConsultScheduleType(Year, type);
                if (lst_schedule != null) {
                    out.print("<script>");
                    out.print("document.addEventListener('DOMContentLoaded', function() {");
                    out.print("var calendarEl = document.getElementById('calendar');");

                    out.print("var currentYear = new Date().getFullYear();");

                    out.print("var calendar = new FullCalendar.Calendar(calendarEl, {");
                    out.print("headerToolbar: {");
                    out.print("left: 'prev,next,today',");
                    out.print("center: 'title',");
                    out.print("right: 'multiMonthYear,dayGridMonth'");
                    out.print("},");
                    out.print("locales: 'es',");
                    out.print("initialView: 'dayGridMonth',");
                    out.print("editable: true,");
                    out.print("selectable: false,");
                    out.print("dayMaxEvents: true,");
                    out.print("events: [");

                    for (int i = 0; i < lst_schedule.size(); i++) {
                        Object[] obj_calendar = (Object[]) lst_schedule.get(i);
                        out.print("{");
                        out.print("title: '" + (obj_calendar[4].equals("VERIFICACION DE CONTROL") ? (obj_calendar[4] + " " + obj_calendar[3]) : obj_calendar[4]) + "',");
                        out.print("start: '" + obj_calendar[17] + "',");
                        out.print("end: '" + obj_calendar[18] + "',");
                        out.print("backgroundColor: '" + ((obj_calendar[19] == null) ? "#0b0025" : obj_calendar[19]) + "',");
                        out.print("url: 'Schedule?opt=1&IdSchedule=" + obj_calendar[0] + "&temp=" + temp + "&type=" + type + "&module=" + module + "&Year=" + Year + "'");
                        if (i == lst_schedule.size() - 1) {
                            out.print("}");
                        } else {
                            out.print("},");
                        }
                    }

                    out.print("],");

                    out.print("validRange: {");
                    out.print("start: '" + Integer.parseInt(Year) + "' + '-01-01',");  // Inicio del año actual
                    out.print("end: '" + Integer.parseInt(Year) + "' + '-12-31'");     // Fin del año actual
                    out.print("},");

                    out.print("datesSet: function() {");
                    out.print("if (calendar.view.type === 'multiMonthYear') {");
                    out.print("calendar.setOption('headerToolbar', {");
                    out.print("left: '',"); // Ocultar 'prev', 'next', 'today' en multiMonthYear
                    out.print("center: 'title',");
                    out.print("right: 'multiMonthYear,dayGridMonth'");
                    out.print("});");
                    out.print("} else if (calendar.view.type === 'dayGridMonth') {");
                    out.print("calendar.setOption('headerToolbar', {");
                    out.print("left: 'prev,next,today',"); // Mostrar 'prev', 'next', 'today' en dayGridMonth
                    out.print("center: 'title',");
                    out.print("right: 'multiMonthYear,dayGridMonth'");
                    out.print("});");
                    out.print("}");
                    out.print("},"); // Fin de datesSet

                    out.print("});");
                    out.print("calendar.render();");
                    out.print("});");
                    out.print("</script>");

                } else {
                    out.print("<h4 class='text-center'>Sin actividades registradas <i class=\"far fa-sad-cry\" style='font-size:27px;'></i></h4>");
                }
                out.print("<div id='calendar' style='m-5'></div>");
                out.print("</div>");

                //</editor-fold>
            }
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

        } catch (IOException ex) {
            Logger.getLogger(Tag_schedule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_schedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
