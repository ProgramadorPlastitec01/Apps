package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.BinnacleControllerJpa;
import Controller.RoleControllerJpa;
import Controller.SettingControllerJpa;
import Controller.TemplateControllerJpa;
import Controller.ActivitySystemControllerJpa;
import Controller.ShiftControllerJpa;

import java.util.List;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class Tag_binnacle extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        BinnacleControllerJpa binnacleJpa = new BinnacleControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        TemplateControllerJpa TemplategJpa = new TemplateControllerJpa();
        ActivitySystemControllerJpa SystemJpa = new ActivitySystemControllerJpa();
        ShiftControllerJpa ShiftJpa = new ShiftControllerJpa();
        List lst_role = null;
        List lst_binnacle = null;
        List lst_setting = null;
        List lst_template = null;
        List lst_system = null;
        List lst_shift = null;

        int id_user = 0;
        int idRol = 0, temp = 0, idbinn = 0, empyData = 0, tempx = 0;
        String txtPermissions = "";
        String NameUser = "";
        String ActualWeek = "";

        LocalDate today = LocalDate.now();
        String formattedDate = today.toString();
        try {
            idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }
        try {
            NameUser = sesion.getAttribute("Nombres").toString();
        } catch (Exception e) {
            NameUser = "";
        }
        try {
            id_user = Integer.parseInt(pageContext.getRequest().getAttribute("idUser").toString());
        } catch (Exception e) {
            id_user = 0;
        }

        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            tempx = Integer.parseInt(pageContext.getRequest().getAttribute("tempx").toString());
        } catch (Exception e) {
            tempx = 0;
        }
        try {
            idbinn = Integer.parseInt(pageContext.getRequest().getAttribute("idbinn").toString());
        } catch (Exception e) {
            idbinn = 0;
        }
        try {
            empyData = Integer.parseInt(pageContext.getRequest().getAttribute("empyData").toString());
        } catch (Exception e) {
            empyData = 0;
        }
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
//        int currentMonth = today.getMonthValue();
        LocalDate startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endDate = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        String formattedStart = startDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
        String formattedEnd = endDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
        if ((today.isEqual(startDate) || today.isAfter(startDate)) && (today.isEqual(endDate) || today.isBefore(endDate))) {
            ActualWeek = "Semana " + formattedStart + " / " + formattedEnd;
        }
//</editor-fold>
        try {
            if (idbinn > 0 && temp == 0) {
                //<editor-fold defaultstate="collapsed" desc="EDIT BINNACLE">
                lst_binnacle = binnacleJpa.ConsultBinnacleId(idbinn);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Editar bitácora</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                if (lst_binnacle != null) {
                    Object[] objBinn = (Object[]) lst_binnacle.get(0);
                    out.print("<form action='Binnacle?opt=2&idBinn=" + objBinn[0] + "' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");
                    out.print("<div class=''>");
                    out.print("<div class='col-lg-6' style='margin: auto;' data-toggle='tooltip' data-placemente='top' title='Seleccionar turno'>");
                    lst_setting = SettingJpa.ConsultSettingCategorie("Shift");
                    out.print("<select class='form-control' name='txtshift' id='shiftSelect2' style='margin-top: 12px;margin-bottom: 12px;' onchange='updateTimeFields2()'>");
                    out.print("<option  value='" + objBinn[4] + "' disabled>" + objBinn[4] + " </option>");
                    if (lst_setting != null) {
                        for (int i = 0; i < lst_setting.size(); i++) {
                            Object[] ObjSett = (Object[]) lst_setting.get(i);
                            String shift = ObjSett[2].toString().split("///")[0];
                            String[] parts = shift.split(": | - ");
                            String startTime = convertTo24HourFormat(parts[1].trim());
                            String endTime = convertTo24HourFormat(parts[2].trim());
                            out.print("<option value='" + shift + "' data-start='" + startTime + "' data-end='" + endTime + "'>" + shift + "</option>");
                        }
                    }

                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    String[] dateIn = objBinn[1].toString().split(" ");
                    out.print("<div class='d-flex'>");
                    out.print("<div class=' col-lg-6'>");
                    out.print("<label>Fecha inicio</label>");
                    out.print("<input type='date' class='form-control' style='margin: 0;' name='txtDateIni' id='' data-toggle='tooltip' data-placement='top' title='' value='" + dateIn[0] + "'>");
                    out.print("</div>");
                    out.print("<div class=' col-lg-6'>");
                    out.print("<label>Hora inicio</label>");
                    out.print("<input type='time' class='form-control' style='margin: 0;' name='txtHourIni' id='startTime2' data-toggle='tooltip' data-placement='top' title='' value='" + dateIn[1] + "'>");
                    out.print("</div>");
                    out.print("</div>");

                    String[] dateFin = objBinn[2].toString().split(" ");
                    out.print("<div class='d-flex mt-4'>");
                    out.print("<div class=' col-lg-6'>");
                    out.print("<label>Fecha final</label>");
                    out.print("<input type='date' class='form-control' style='margin: 0;' name='txtDateFin' id='' data-toggle='tooltip' data-placement='top' title='' value='" + dateFin[0] + "'>");
                    out.print("</div>");
                    out.print("<div class=' col-lg-6'>");
                    out.print("<label>Hora final</label>");
                    out.print("<input type='time' class='form-control' style='margin: 0;' name='txtHourfin' id='endTime2' data-toggle='tooltip' data-placement='top' title='' value='" + dateFin[1] + "' >");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='text-center mt-4'>");
                    out.print("<button class='btn btn-green' >Actualizar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("<script>"
                            + " document.addEventListener('DOMContentLoaded', function() {"
                            + "    function toggleClass() {"
                            + "        const body = document.body;"
                            + "        body.classList.add('modal-open');"
                            + "    }"
                            + "    toggleClass();"
                            + " });"
                            + "</script>");

                } else {
                    out.print("<h4 class='text-center'>Ha ocurrido un problema al realizar la consultar, favor intentar de nuevo, si el problema persiste favor comunicarse al area TI</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<script>"
                        + "function updateTimeFields2() {"
                        + "    var select = document.getElementById('shiftSelect2');"
                        + "    var selectedIndex = select.selectedIndex;"
                        + "    if (selectedIndex !== -1) {"
                        + "        var option = select.options[selectedIndex];"
                        + "        var startTime = option.getAttribute('data-start');"
                        + "        var endTime = option.getAttribute('data-end');"
                        + "        document.getElementById('startTime2').value = startTime;"
                        + "        document.getElementById('endTime2').value = endTime;"
                        + "    }"
                        + "}"
                        + "</script>");
                out.print("</div>");
                out.print("<script>"
                        + " document.addEventListener('DOMContentLoaded', function() {"
                        + "    function toggleClass() {"
                        + "        const body = document.body;"
                        + "        body.classList.add('modal-open');"
                        + "    }"
                        + "    toggleClass();"
                        + " });"
                        + "</script>");
//</editor-fold>
            } else if (idbinn > 0 && temp == 1) {
                //<editor-fold defaultstate="collapsed" desc="BINNACLE">
                String datos = "";
                int state = 0;
                lst_binnacle = binnacleJpa.ConsultBinnacleId(idbinn);
                Object[] objBin = (Object[]) lst_binnacle.get(0);
                try {
                    try {
                        datos = objBin[3].toString();
                    } catch (Exception e) {
                        datos = "";
                    }
                    state = Integer.parseInt(objBin[5].toString());
                } catch (Exception e) {
                    state = 99;
                }
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 64%; right: 9%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                if (state == 0) {
                    out.print("<h3>Contenido de la bitácora</h3>");
                } else if (state > 0) {
                    out.print("<h3>Bitácora</h3>");
                }
                out.print("<div class=''>");
                if (datos.equals("") && empyData == 0) {
                    out.print("<button class='btn btn-info mr-2' onclick='window.location.href=\"Template?opt=1\";cargarDatos()'>Gestionar plantillas</button>");
                }
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                if (datos.equals("") && empyData == 0) {
                    //<editor-fold defaultstate="collapsed" desc="SELECT TEMPLATE">
                    lst_template = TemplategJpa.ConsultTemplatexIdUser(id_user);
                    out.print("<div class=''>");
                    if (lst_template != null) {
                        out.print("<div class='row' style='justify-content: center;'>");
                        for (int i = 0; i < lst_template.size(); i++) {
                            Object[] ObjTem = (Object[]) lst_template.get(i);
                            int ste = Integer.parseInt(ObjTem[4].toString());
//                            if (ste == 1) {
                            out.print("<div class='col-lg-4 SquareTemplate mr-4 mt-4'>");
                            out.print("<div class='d-flex' style='align-items: center;'>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<h6 style='margin: 0px;'>" + ObjTem[2] + "</h6>");
                            out.print("<span class=''>" + ((ste == 1) ? "Activo" : "Inactivo") + " <span class='bullet text-" + ((ste == 1) ? "success" : "danger") + "'></span> </span>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='d-flex' style='justify-content: flex-end;'>");
                            if (ste == 1) {
                                out.print("<form action='Binnacle?opt=3' method='post' class='needs-validation' novalidate=''>");
                                out.print("<input type='hidden' class='form-control' name='idBinn' value='" + idbinn + "'>");
                                out.print("<input type='hidden' class='form-control' name='txtBinnacle' value='" + ObjTem[3] + "'>");
                                out.print("<button class='btn btn-green btn-sm mr-2' onclick='cargarDatos()'><i class='fas fa-check'></i> Usar</button>");
                                out.print("</form>");
                            } else {
                                out.print("<button type='button' disabled class='btn btn-green btn-sm mr-2'> Inactivo </button>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
//                            }
                        }
                        out.print("</div>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h5>No se ha encontrado plantillas asociadas al usuario!</h5>");
                        out.print("<h4>¿Desea registrar su plantilla o desea continuar en blanco?</h4>");
                        out.print("<div class='d-flex text-center' style='justify-content: space-evenly;'>");
                        out.print("<button class='btn btn-info' onclick='window.location.href=\"Template?opt=1\";cargarDatos()'>Registrar plantillas</button>");
                        out.print("<button class='btn btn-info' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + idbinn + "&temp=1&empyData=1\";cargarDatos()'>Seguir sin plantilla</button>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    //</editor-fold>
                } else {
                    out.print("<div class='cont_form_user'>");
                    //<editor-fold defaultstate="collapsed" desc="LOAD BINNACLE">
                    out.print("<form action='Binnacle?opt=3&idBinn=" + idbinn + "' method='post'>");
                    out.print("<div style='max-height: 540px;overflow: auto;'>");
                    out.print("<textarea id='editorCK' name='txtBinnacle'>");
                    out.print(datos);
                    out.print("</textarea>");
                    out.print("</div>");
                    out.print("<div class='text-center'>");
                    if (state == 0) {
                        out.print("<button class='btn btn-green'>Guardar <i class='fas fa-save'></i></button>");
                    }
                    out.print("</div>");
                    out.print("</form>");
                    //</editor-fold>
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<script>"
                        + " document.addEventListener('DOMContentLoaded', function() {"
                        + "    function toggleClass() {"
                        + "        const body = document.body;"
                        + "        body.classList.add('modal-open');"
                        + "    }"
                        + "    toggleClass();"
                        + " });"
                        + "</script>");
                //</editor-fold>
            } else if (idbinn > 0 && temp == 2) {
                //<editor-fold defaultstate="collapsed" desc="SEND BINNACLE">
                lst_template = TemplategJpa.ConsultTemplateId(id_user);
                String datos = "";
                String DteIni = "";
                String DteFin = "";
                String respo = "";
                int state = 0;
                try {
                    lst_binnacle = binnacleJpa.ConsultBinnacleId(idbinn);
                    Object[] objBin = (Object[]) lst_binnacle.get(0);
                    DteIni = objBin[1].toString();
                    DteFin = objBin[2].toString();
                    datos = objBin[3].toString();
                    respo = objBin[6].toString();
                    state = Integer.parseInt(objBin[5].toString());
                } catch (Exception e) {
                    datos = "";
                }
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 73%;'>");
                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-3' style='padding: 0px;'>");
                
                out.print("<div style='padding: 5px;box-shadow: 0px 0px 8px -3px black;border-radius: 6px;margin-bottom: 8%;'>");
                out.print("<span class=''><b>Nombre: </b><br>&nbsp;&nbsp;&nbsp;" + respo + "</span><br>");
                out.print("<span class=''><b>Fecha inicial: </b><br>&nbsp;&nbsp;&nbsp;" + DteIni + "</span><br>");
                out.print("<span class=''><b>Fecha Final: </b><br>&nbsp;&nbsp;&nbsp;" + DteFin + "</span><br>");
                out.print("</div>");
                
                //<editor-fold defaultstate="collapsed" desc="LOAD DAILY ACTIVITIES">
                out.print("<div class=''>");
                out.print("<h5>Actividades del día</h5>");
                out.print("</div>");
                out.print("<div class='' style='overflow-y: auto; padding: 6px;max-height:40%'>");
                lst_system = SystemJpa.ConsultActivitySystemByUser(id_user, DteIni);
                String idActy = "";
                if (lst_system != null) {
                    for (int i = 0; i < lst_system.size(); i++) {
                        Object[] ObjSystem = (Object[]) lst_system.get(i);
                        out.print("<div class='ActSym'><b>" + ObjSystem[3] + "</b><br><p>" + ObjSystem[4] + "</p></div>");
                        idActy += "[" + ObjSystem[0] + "]";
                    }
                } else {
                    out.print("<div class='card-body'><p><em>Que raro, no?<br> No se encuentra información.</em></p></div>");
                }
                out.print("</div>");
                //</editor-fold>

                out.print("</div>");
                out.print("<div class='col-lg-9'>");
                //<editor-fold defaultstate="collapsed" desc="LOAD BINNACLE">
                out.print("<div style='display: flex; justify-content: space-between'>");
                if (state == 0) {
                    out.print("<h3>Confirmar envio de bitácora</h3>");
                } else {
                    out.print("<h3>Bitácora</h3>");
                }
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<div style='max-height: 540px;overflow-y: auto;'>");
                out.print("<div name='txtBinnacle' >");
                if (datos.equals("")) {
                    if (lst_template != null) {
                        Object[] ObjTemop = (Object[]) lst_template.get(0);
                        String template = ObjTemop[2].toString();
                        out.print(template);
                    } else {
                        out.print("");
                    }
                } else {
                    out.print(datos);
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                if (state == 0) {
                    out.print("<form action='Binnacle?opt=4&idBinn=" + idbinn + "' method='post'>");
                    out.print("<input type='hidden' class='form-control' name='txtiIdAct' value='" + idActy + "'>");
                    out.print("<button class='btn btn-green' onclick='AlertMail()'>Enviar <i class=\"fas fa-share\"></i></button>");
                    out.print("</form>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("<script>"
                        + " document.addEventListener('DOMContentLoaded', function() {"
                        + "    function toggleClass() {"
                        + "        const body = document.body;"
                        + "        body.classList.add('modal-open');"
                        + "    }"
                        + "    toggleClass();"
                        + " });"
                        + "</script>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="REGISTER BINNACLE">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral' style='width: 44%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Registrar bitácora</h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");
            out.print("<form action='Binnacle?opt=2' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");

            out.print("<div class=''>");
            out.print("<div class='col-lg-6' style='margin: auto;' data-toggle='tooltip' data-placemente='top' title='Seleccionar turno'>");
            lst_setting = SettingJpa.ConsultSettingCategorie("Shift");
            out.print("<select class='form-control' name='txtshift' id='shiftSelect' required='' style='margin-top: 12px;margin-bottom: 12px;' onchange='updateTimeFields()'>");
            boolean valid = false;
            String shiftx = "";
            lst_shift = ShiftJpa.ConsultShift();
            if (lst_shift != null) {
                for (int i = 0; i < lst_shift.size(); i++) {
                    Object[] Obwk = (Object[]) lst_shift.get(i);
                    if (ActualWeek.equals(Obwk[1])) {
                        String[] shift = Obwk[2].toString().replace("][", "]///[").split("///");
                        for (int j = 0; j < shift.length; j++) {
                            String[] tempShift = shift[j].split("/");
                            shiftx = tempShift[0].toString().replace("]", "").replace("[", "");
                            String users = tempShift[1].toString();
                            if (users.contains("," + id_user + ",") || users.contains("," + id_user + "]") || users.contains("/" + id_user + ",") || users.contains("/" + id_user + "]")) {
                                String[] parts = shiftx.split(": | - ");
                                String startTime = convertTo24HourFormat(parts[1].trim());
                                String endTime = convertTo24HourFormat(parts[2].trim());
                                out.print("<option value='" + shiftx + "' data-start='" + startTime + "' data-end='" + endTime + "'>" + shiftx + "</option>");
                                valid = true;
                            }
                        }
                    }
                }
                if (!valid) {
                    out.print("<option selected disabled>Seleccione turno </option>");
                }
            } else {
                out.print("<option selected disabled>Seleccione turno </option>");
            }
            if (lst_setting != null) {
                for (int i = 0; i < lst_setting.size(); i++) {
                    Object[] ObjSett = (Object[]) lst_setting.get(i);
                    String shift = ObjSett[2].toString().split("///")[0];
                    String[] parts = shift.split(": | - ");
                    String startTime = convertTo24HourFormat(parts[1].trim());
                    String endTime = convertTo24HourFormat(parts[2].trim());
                    if (!shift.equals(shiftx)) {
                        out.print("<option value='" + shift + "' data-start='" + startTime + "' data-end='" + endTime + "'>" + shift + "</option>");
                    }
                }
            }
            out.print("</select>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='d-flex'>");
            out.print("<div class=' col-lg-6'>");
            out.print("<label>Fecha inicio</label>");
            out.print("<input type='date' class='form-control' style='margin: 0;' name='txtDateIni' id='' data-toggle='tooltip' data-placement='top' title='' value='" + formattedDate + "'>");
            out.print("</div>");
            out.print("<div class=' col-lg-6'>");
            out.print("<label>Hora inicio</label>");
            out.print("<input type='time' class='form-control' style='margin: 0;' name='txtHourIni' id='startTime' data-toggle='tooltip' data-placement='top' title='' required='' >");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='d-flex mt-4'>");
            out.print("<div class='col-lg-6'>");
            out.print("<label>Fecha final</label>");
            out.print("<input type='date' class='form-control' style='margin: 0;' name='txtDateFin' id='' data-toggle='tooltip' data-placement='top' title='' value='" + formattedDate + "'>");
            out.print("</div>");
            out.print("<div class=' col-lg-6'>");
            out.print("<label>Hora final</label>");
            out.print("<input type='time' class='form-control' style='margin: 0;' name='txtHourfin' id='endTime' data-toggle='tooltip' data-placement='top' title='' required='' >");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='text-center mt-4'>");
            out.print("<button class='btn btn-green' >Registrar</button>");
            out.print("</div>");

            out.print("</form>");
            out.print("</div>");
            out.print("</div>");
            out.print("<script>"
                    + "function updateTimeFields() {"
                    + "    var select = document.getElementById('shiftSelect');"
                    + "    var selectedIndex = select.selectedIndex;"
                    + "    if (selectedIndex !== -1) {"
                    + "        var option = select.options[selectedIndex];"
                    + "        var startTime = option.getAttribute('data-start');"
                    + "        var endTime = option.getAttribute('data-end');"
                    + "        document.getElementById('startTime').value = startTime;"
                    + "        document.getElementById('endTime').value = endTime;"
                    + "    }"
                    + "}"
                    + "</script>");
            if (valid) {
                out.print("<script>");
                out.print("updateTimeFields()");
                out.print("</script>");
            }
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            String RecolectId = "";
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            if (tempx == 1) {
                out.print("<h3>Bitacoras enviadas</h3>");
            } else {
                out.print("<h3>Bitacoras de " + NameUser + "</h3>");
            }
            out.print("<div class='d-flex'>");

            if (tempx == 1) {
                out.print("<button class='btn btn-info mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Mis bitacoras' onclick='window.location.href=\"Binnacle?opt=1&tempx=0\"'><i class=\"fas fa-id-card-alt\"></i></button>");
            } else {
                out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Consultar todas las bitacoras' onclick='window.location.href=\"Binnacle?opt=1&tempx=1\"'><i class=\"fas fa-folder-open\"></i></button>");
            }

            if (txtPermissions.contains("[27]")) {
                out.print("<form action='Binnacle?opt=6' method='post' id='SendIdRecole'>");
                out.print("<input type='hidden' class='form-control' name='txtIdRecolected' id='txtIdBins'>");
                out.print("<button type='button' class='btn btn-success mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Aprobar' onclick='SendIds()'><i class='fas fa-check-double'></i></button>");
                out.print("</form>");
            }
            if (txtPermissions.contains("[24]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Registrar'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            if (txtPermissions.contains("[27]")) {
                out.print("<th class='text-center'>");
                out.print("<div class='custom-checkbox custom-control'>");
                out.print("<input type='checkbox' data-checkboxes='mygroup' data-checkbox-role='dad' class='custom-control-input' id='checkbox-all' onclick='toggleAll()'>");
                out.print("<label for='checkbox-all' class='custom-control-label'>&nbsp;</label>");
                out.print("</div>");
                out.print("</th>");
            }
            if (tempx == 1) {
                out.print("<th>Nombre</th>");
            }
            out.print("<th>Fecha inicial</th>");
            out.print("<th>Fecha final</th>");
            out.print("<th>Turno</th>");
            out.print("<th class='text-center'>Estado</th>");
            out.print("<th class='text-center'>OPC</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");

            if (tempx == 0) {
                lst_binnacle = binnacleJpa.ConsultBinnacleIdUser(id_user);
            } else {
                lst_binnacle = binnacleJpa.ConsultBinnacleOtherUser(id_user);
            }
            if (lst_binnacle != null) {
                for (int i = 0; i < lst_binnacle.size(); i++) {
                    Object[] Objbin = (Object[]) lst_binnacle.get(i);
                    int state = Integer.parseInt(Objbin[5].toString());
                    out.print("<tr>");

                    if (txtPermissions.contains("[27]")) {
                        if (state == 1) {
                            RecolectId = RecolectId + "[" + Objbin[0] + "]";
                            out.print("<td class='text-center'>");
                            out.print("<div class='custom-checkbox custom-control'>");
                            out.print("<input type='checkbox' data-checkboxes='mygroup' class='custom-control-input' id='checkbox-" + Objbin[0] + "'>");
                            out.print("<label for='checkbox-" + Objbin[0] + "' class='custom-control-label' onclick='MoveData(" + Objbin[0] + ")'>&nbsp;</label>");
                            out.print("</div>");
                            out.print("</td>");
                        } else {
                            out.print("<td class='text-center'>-</td>");
                        }
                    }
                    if (tempx == 1) {
                        try {
                            out.print("<td>" + Objbin[9] + "</td>");
                        } catch (Exception e) {
                            out.print("<td>..</td>");
                        }
                    }
                    out.print("<td> " + Objbin[1] + " </td>");
                    out.print("<td> " + Objbin[2] + " </td>");
                    if (Objbin[3] == null) {
                        out.print("<td> Sin turno </td>");
                    } else {
                        out.print("<td> " + Objbin[3] + " </td>");
                    }
                    out.print("<td class='text-center'>");
                    if (state == 0) {
                        out.print("<span class='text-secondary' data-toggle='tooltip' data-placement='top' title='Creado'><i style='font-size: 20px;' class='fas fa-user-edit'></i></span>");
                    } else if (state == 1) {
                        out.print("<span class='text-dark' data-toggle='tooltip' data-placement='top' title='Enviado'><i style='font-size: 20px; opacity: 0.8;' class='fas fa-check'></i></span>");
                    } else if (state == 2) {
                        out.print("<span class='text-dark' data-toggle='tooltip' data-placement='top' title='Revisado'><i style='font-size: 20px;' class='fas fa-check-double'></i></span>");
                    }
                    out.print("</td>");
                    out.print("<td class='text-center'>");
                    if (tempx == 1) {
                        if (state == 0) {
                            out.print("<span class='text-warning'><b>Sin enviar</b></span>");
                        } else if (state > 0) {
                            out.print("<button class='btn btn-light mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Ver Bitacora' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + Objbin[0] + "&temp=2&tempx=1\";cargarDatos()'><i class='fas fa-eye'></i></button>");
                        }
                    } else {
                        if (state == 0) {
                            out.print("<button class='btn btn-yellow mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Bitácora' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + Objbin[0] + "&temp=1\";cargarDatos()'><i class='fas fa-eye'></i></button>");
                            if (txtPermissions.contains("[25]")) {
                                out.print("<button class='btn btn-warning mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Editar' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + Objbin[0] + "\";cargarDatos()'><i class='fas fa-pencil-alt'></i></button>");
                            } else {
                                out.print("<button class='btn btn-warning mr-2 btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-pencil-alt'></i></button>");
                            }
                            if (txtPermissions.contains("[26]")) {
                                out.print("<button class='btn btn-dark mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Enviar bitácora' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + Objbin[0] + "&temp=2\";cargarDatos()'><i class='fas fa-share'></i></button>");
                            } else {
                                out.print("<button class='btn btn-dark mr-2 btn-sm' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-share'></i></button>");
                            }
                        } else if (state == 1) {
                            if (txtPermissions.contains("[27]")) {
                                out.print("<button class='btn btn-yellow mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Aprobar' onclick='window.location.href=\"Binnacle?opt=5&idBinn=" + Objbin[0] + "&state=2\";cargarDatos()'><i class='fas fa-check'></i></button>");
                            }
                            out.print("<button class='btn btn-light mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Ver Bitacora' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + Objbin[0] + "&temp=2\";cargarDatos()'><i class='fas fa-eye'></i></button>");
                            out.print("<button class='btn btn-danger btn-sm' style='width: 30px;' data-toggle='tooltip' data-placement='top' title='Cancelar envio..' onclick='window.location.href=\"Binnacle?opt=5&idBinn=" + Objbin[0] + "&state=0\"'><i class='fas fa-times'></i></button>");
                        } else if (state == 2) {
                            out.print("<button class='btn btn-light mr-2 btn-sm' data-toggle='tooltip' data-placement='top' title='Ver Bitacora' onclick='window.location.href=\"Binnacle?opt=1&idBinn=" + Objbin[0] + "&temp=2\";cargarDatos()'><i class='fas fa-eye'></i></button>");
                        }
                    }
                    out.print("</td>");
                    out.print("</tr>");
                }
            } else {
                out.print("<tr><td>No se ha encontrado bitácoras</td></tr>");
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("<input type='hidden' class='form-control' id='idRecolect' value='" + RecolectId + "'>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");

//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_binnacle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

    private String convertTo24HourFormat(String time) {
        try {
            DateFormat date12Format = new SimpleDateFormat("hha");
            DateFormat date24Format = new SimpleDateFormat("HH:mm");
            return date24Format.format(date12Format.parse(time.toUpperCase()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00";
        }
    }
}
