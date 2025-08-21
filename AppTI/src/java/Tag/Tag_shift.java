package Tag;

import Controller.RoleControllerJpa;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import java.time.format.DateTimeFormatter;
import java.util.List;

import Controller.UserControllerJpa;
import Controller.ShiftControllerJpa;
import Controller.SettingControllerJpa;
import java.util.ArrayList;

public class Tag_shift extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        UserControllerJpa UserJpa = new UserControllerJpa();
        ShiftControllerJpa ShiftJap = new ShiftControllerJpa();
        SettingControllerJpa SettingJps = new SettingControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_role = null;

        List lst_user = null;
        List lst_shift = null;
        List lst_shift2 = null;
        List lst_setting = null;
//        LocalDate today = LocalDate.now();
        String weekNext = "";
        String weekAct = "";
        int idShift = 0;
        String ActualWeek = "";
        int idRol = 0;
        String txtPermissions = "";
        try {
            idRol = Integer.parseInt(pageContext.getRequest().getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }

        //<editor-fold defaultstate="collapsed" desc="WEEK ACTUAL">
        LocalDate today = LocalDate.now();
        int currentMonth = today.getMonthValue();
        int nextMonth = today.plusMonths(1).getMonthValue();
        int DayNow = today.getDayOfMonth();
        List<String> currentMonthWeeks = new ArrayList<>();
        LocalDate startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        String lastWeek = "";
        while (startDate.getMonthValue() <= currentMonth) {
            LocalDate endDate = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
            String formattedStart = startDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
            String formattedEnd = endDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
            String weekLabel;
            if ((today.isEqual(startDate) || today.isAfter(startDate)) && (today.isEqual(endDate) || today.isBefore(endDate))) {
                weekLabel = "Semana " + formattedStart + " / " + formattedEnd;
                ActualWeek = weekLabel;
            } else {
                weekLabel = "Semana " + formattedStart + " / " + formattedEnd;
            }
            weekAct += weekLabel + "///";
            currentMonthWeeks.add(weekLabel);
            startDate = endDate.plusDays(1);
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="WEEK NEXT">
        List<String> nextMonthWeeks = new ArrayList<>();
        startDate = today.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        startDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        while (startDate.getMonthValue() <= nextMonth) {
            LocalDate endDate = startDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
            String formattedStart = startDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
            String formattedEnd = endDate.format(DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()));
            String weekLabel = "Semana " + formattedStart + " / " + formattedEnd;
            weekNext += weekLabel + "///";
            if (!lastWeek.equals(weekLabel)) {
                nextMonthWeeks.add(weekLabel);
            }
            startDate = endDate.plusDays(1);
        }
        //</editor-fold>
        try {
            idShift = Integer.parseInt(pageContext.getRequest().getAttribute("idShift").toString());
        } catch (Exception e) {
            idShift = 0;
        }
        try {
            if (idShift > 0) {
                //<editor-fold defaultstate="collapsed" desc="EDIT PROGRAMATION">
                lst_shift2 = ShiftJap.ConsultShiftId(idShift);
                Object[] ObjSft = {};
                String[] Shiftids = {};
                String ShiftAll = "";
                String UserIn = "", shiftOne = "", shiftTwo = "", shiftThree = "", shiftFour = "", shiftFive = "",
                        shiftSix = "", shiftSeven = "", shiftEight = "", shiftNine = "", shiftTen = "";
                if (lst_shift2 != null) {
                    ObjSft = (Object[]) lst_shift2.get(0);
                    Shiftids = ObjSft[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < Shiftids.length; i++) {
                        String temp = Shiftids[i].toString().split("/")[0];
                        ShiftAll += "[" + temp + "]";
                        UserIn += "[" + temp + "]-[" + Shiftids[i].toString().replace(",", "][").split("/")[1] + "]///";
                    }
                }
                lst_shift = ShiftJap.ConsultShiftValidation();
                String valid = "";
                if (lst_shift != null) {
                    for (int i = 0; i < lst_shift.size(); i++) {
                        Object[] ObjShift = (Object[]) lst_shift.get(i);
                        valid += "[" + ObjShift[1] + "]";
                    }
                }
                String[] datShift = ObjSft[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                for (int i = 0; i < datShift.length; i++) {
                    if (datShift[i].contains("Turno 1: 6am - 2pm")) {
                        shiftOne = datShift[i];
                    } else if (datShift[i].contains("Turno 2: 2pm - 10pm")) {
                        shiftTwo = datShift[i];
                    } else if (datShift[i].contains("Turno 3: 10pm - 6am")) {
                        shiftThree = datShift[i];
                    } else if (datShift[i].contains("Turno Oficina: 6am - 4pm")) {
                        shiftFour = datShift[i];
                    } else if (datShift[i].contains("Turno Oficina: 7am - 5pm")) {
                        shiftFive = datShift[i];
                    } else if (datShift[i].contains("Turno Oficina: 8am - 6pm")) {
                        shiftSix = datShift[i];
                    } else if (datShift[i].contains("Turno 1/12: 6am - 6pm")) {
                        shiftSeven = datShift[i];
                    } else if (datShift[i].contains("Turno 2/12: 6pm - 6am")) {
                        shiftEight = datShift[i];
                    } else if (datShift[i].contains("Turno Lab: 7am - 5pm")) {
                        shiftNine = datShift[i];
                    } else if (datShift[i].contains("Turno Apoyo: 7am - 5pm")) {
                        shiftTen = datShift[i];
                    }
                }
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 80%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Modificar programación de turnos</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<div class='d-flex'>");

                out.print("<div class='col-lg-4' style='box-shadow: 0px 1px 9px -1px #979797;border-radius: 5px;margin-top: 2%;padding-bottom: 10px;margin-bottom: 10px;'>");

                out.print("<div class='mt-3'>");
                //<editor-fold defaultstate="collapsed" desc="SELECT WEEK">
                out.print("<h4>Seleccionar semana</h4>");
                out.print("<select class='form-control col-lg-6' name='weekSelectx' style='margin-top: 12px; margin-bottom: 12px; margin: auto;'  onchange=\"moveWeekAct2(this.value)\">");
                out.print("<option value='" + ObjSft[1] + "'>Semana " + ObjSft[1] + "</option>");
                out.print("<optgroup label='Este mes'>");
                for (String week : currentMonthWeeks) {
                    if (valid.contains("[" + week + "]")) {
                        out.print("<option class='text-secondary' disabled>" + week + "</option>");
                    } else {
                        out.print("<option value='" + week + "[1]'>" + week + "</option>");
                    }
                }
                out.print("</optgroup>");
                out.print("<optgroup label='Próximo mes'>");
                for (String week : nextMonthWeeks) {
                    if (valid.contains("[" + week + "]")) {
                        out.print("<option  class='text-secondary' disabled>" + week + "</option>");
                    } else {
                        out.print("<option value='" + week + "[2]'>" + week + "</option>");
                    }
                }
                out.print("</optgroup>");
                out.print("</select>");
                //</editor-fold>
                out.print("</div>");

                //<editor-fold defaultstate="collapsed" desc="TUNROS SELECCIONABLES">
                out.print("<h5 class='text-center mt-3'>Turno</h5>");
                lst_setting = SettingJps.ConsultSettingCategorie("Shift");
                String ShiftColors = "";
                if (lst_setting != null) {
                    for (int i = 0; i < lst_setting.size(); i++) {
                        Object[] ObjSett = (Object[]) lst_setting.get(i);
                        if (i % 2 == 0) {
                            if (i != 0) {
                                out.print("</div>");
                            }
                            out.print("<div class='d-flex' style='justify-content: space-evenly;margin-bottom: 10px;'>");
                        }
                        String shift = ObjSett[2].toString().split("///")[0];
                        String color = ObjSett[2].toString().split("///")[1];
                        if (ShiftAll.contains("[" + shift + "]")) {
                            ShiftColors += "[" + ObjSett[2].toString() + "]";
                            out.print("<div class='mediax turno " + color + "' id='mediax" + i + "' onclick=\"swapColors2(" + i + ", '" + color + "', '" + shift + "')\" style='padding-bottom: 4px;margin-bottom: 4px; width: 114px !important;' data-turno-id='" + ObjSett[2] + "'>");
                        } else {
                            out.print("<div class='mediax turno' id='mediax" + i + "' onclick=\"swapColors2(" + i + ", '" + color + "', '" + shift + "')\" style='padding-bottom: 4px;margin-bottom: 4px; width: 114px !important;' data-turno-id='" + ObjSett[2] + "'>");
                        }

                        String[] turn = shift.split(":");
                        out.print("<div class='media-body'>");
                        out.print("<div class='media-title'>" + turn[0] + "</div>");
                        out.print("<div class='text-job text-muted'>" + turn[1] + "</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                } else {
                    out.print("<h4>Se ha presentado un problema al consultar los datos.</h4>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("<div class='col-lg-8'>");
                //<editor-fold defaultstate="collapsed" desc="PERSONAL ACTIVO">
                out.print("<h4 class='text-center'>Personal Activo</h4>");
                lst_user = UserJpa.ConsultPersonalActive();
                if (lst_user != null || lst_user.size() > 0) {
                    for (int i = 0; i < lst_user.size(); i++) {
                        Object[] ObjUser = (Object[]) lst_user.get(i);
                        if (i % 3 == 0) {
                            if (i != 0) {
                                out.print("</div>");
                            }
                            out.print("<div class='d-flex' style='justify-content: space-evenly;margin-bottom: 10px;'>");
                        }
                        UserIn = UserIn;
                        ShiftColors = ShiftColors;
                        String[] userShift = UserIn.split("///");
                        String[] Shifcolor = ShiftColors.replace("][", "--").replace("[", "").replace("]", "").split("--");
                        String colorClas = "";
                        for (int j = 0; j < userShift.length; j++) {
                            String usr = userShift[j];
                            String[] validUser = usr.replace("]-[", "///").split("///");
                            String idUser = "[" + validUser[1];
                            if (idUser.contains("[" + ObjUser[0] + "]")) {
                                for (int k = 0; k < Shifcolor.length; k++) {
                                    String[] temp = Shifcolor[k].split("///");
                                    if (validUser[0].contains(temp[0])) {
                                        colorClas = temp[1];
                                        break;
                                    } else {
                                        colorClas = "";
                                    }
                                }
                            }
                        }
                        if (!colorClas.equals("")) {
                            out.print("<div class='mediax usuario " + colorClas + " disabled' id='mediaUsx" + i + "' onclick=\"RotateColors2(" + i + ",'" + ObjUser[0] + "')\" style='padding-bottom: 4px;margin-bottom: 4px; width: 185px !important;' data-user-id='" + ObjUser[0] + "'>");
                        } else {
                            out.print("<div class='mediax usuario' id='mediaUsx" + i + "' onclick=\"RotateColors2(" + i + ",'" + ObjUser[0] + "')\" style='padding-bottom: 4px;margin-bottom: 4px; width: 185px !important;' data-user-id='" + ObjUser[0] + "'>");
                        }
                        out.print("<img alt='image' class='mr-3 rounded-circle' style='width:55px; height: 68px; box-shadow: 0px 0px 4px 1px #9d9d9d;margin-bottom: 5px;' src='Interface/Photos/" + ObjUser[1] + ".jpg'>");
                        out.print("<div class='media-body'>");
                        out.print("<div class='media-title'>" + ObjUser[2] + " " + ObjUser[3] + "</div>");
                        out.print("<div class='text-job text-muted'>" + ObjUser[5] + "</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                } else {
                    out.print("<h4>No se han encontrado usuarios activos!</h4>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
//                out.print("</div>");

                out.print("<form action='Shift?opt=3&idShift=" + ObjSft[0] + "' method='post' id='formShiftx'>");
                //<editor-fold defaultstate="collapsed" desc="FORM EJECT">
                out.print("<input type='hidden' class='form-control' id='ClassDatax' name='tempClass' placeholder='class'>");
                out.print("<input type='hidden' class='form-control' id='tempTurnox' name=''  placeholder='turno' >");
                out.print("<input type='hidden' class='form-control' name='weekSelect' id='weekSelectx' value='" + ObjSft[1] + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId1' name='hiddenTurnoId1' placeholder='turno 1' value='" + shiftOne + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId2' name='hiddenTurnoId2' placeholder='turno 2' value='" + shiftTwo + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId3' name='hiddenTurnoId3' placeholder='turno 3' value='" + shiftThree + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId4' name='hiddenTurnoId4' placeholder='turno 4' value='" + shiftFour + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId5' name='hiddenTurnoId5' placeholder='turno 5' value='" + shiftFive + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId6' name='hiddenTurnoId6' placeholder='turno 6' value='" + shiftSix + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId7' name='hiddenTurnoId7' placeholder='turno 7' value='" + shiftSeven + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId8' name='hiddenTurnoId8' placeholder='turno 8' value='" + shiftEight + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId9' name='hiddenTurnoId9' placeholder='turno 9' value='" + shiftNine + "'>");
                out.print("<input type='hidden' class='form-control' id='hiddenTurnoxId10' name='hiddenTurnoId10' placeholder='turno 10' value='" + shiftTen + "'>");
                out.print("<input type='hidden' class='form-control' name='ValWeek' id='ValWeekx' placeholder='validacion'>");
                out.print("<input type='hidden' class='form-control' name='WeekAct' id='' value='" + weekAct + "' placeholder='Semanas iniciales'>");
                out.print("<input type='hidden' class='form-control' name='WeekNext' id='' value='" + weekNext + "' placeholder='Semanas finales'>");
                out.print("<div class='text-center'>");
                out.print("<button type='button' class='btn btn-green' onclick='ejectForm2()'>Actualizar</button>");
                out.print("</div>");
                //</editor-fold>
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="NEW PROGRAMATION">
            lst_shift = ShiftJap.ConsultShiftValidation();
            String valid = "";
            if (lst_shift != null) {
                for (int i = 0; i < lst_shift.size(); i++) {
                    Object[] ObjShift = (Object[]) lst_shift.get(i);
                    valid += "[" + ObjShift[1] + "]";
                }
            }
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contGeneral' style='width: 80%;'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h2>Nueva programación de turnos </h2>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");
            out.print("<div class='cont_form_user'>");

            out.print("<div class='d-flex'>");
            out.print("<div class='col-lg-4' style='box-shadow: 0px 1px 9px -1px #979797;border-radius: 5px;margin-top: 2%;padding-bottom: 10px;margin-bottom: 10px;'>");

            out.print("<div class='mt-3'>");
            //<editor-fold defaultstate="collapsed" desc="SELECT SEMANA">
            out.print("<div class='col-lg-12 text-center' data-toggle='tooltip' data-placemente='top' title=''>");
            out.print("<h5>Seleccionar semana</h5>");
            out.print("<select class='form-control col-lg-12' name='weekSelect' style='margin-top: 12px; margin-bottom: 12px; margin: auto;'  onchange=\"moveWeekAct(this.value)\">");
            out.print("<option selected disabled>Seleccione semana</option>");
            out.print("<optgroup label='Este mes'>");
            for (String week : currentMonthWeeks) {
                if (valid.contains("[" + week + "]")) {
                    out.print("<option class='text-secondary' disabled>" + week + "</option>");
                } else {
                    out.print("<option value='" + week + "[1]'>" + week + "</option>");
                }
            }
            out.print("</optgroup>");
            out.print("<optgroup label='Próximo mes'>");
            for (String week : nextMonthWeeks) {
                if (valid.contains("[" + week + "]")) {
                    out.print("<option  class='text-secondary' disabled>" + week + "</option>");
                } else {
                    out.print("<option value='" + week + "[2]'>" + week + "</option>");
                }
            }
            out.print("</optgroup>");
            out.print("</select>");
            out.print("</div>");
            //</editor-fold>
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="TURNOS SELECCIONABLES">
            out.print("<h5 class='text-center mt-3'>Turno</h5>");
            lst_setting = SettingJps.ConsultSettingCategorie("Shift");
            if (lst_setting != null) {
                for (int i = 0; i < lst_setting.size(); i++) {
                    Object[] ObjSett = (Object[]) lst_setting.get(i);
                    if (i % 2 == 0) {
                        if (i != 0) {
                            out.print("</div>");
                        }
                        out.print("<div class='d-flex' style='justify-content: space-evenly;margin-bottom: 10px;'>");
                    }
                    String shift = ObjSett[2].toString().split("///")[0];
                    String color = ObjSett[2].toString().split("///")[1];
                    out.print("<div class='media turno' id='media" + i + "' onclick=\"swapColors(" + i + ", '" + color + "', '" + shift + "')\" style='padding-bottom: 4px;margin-bottom: 4px; width: 114px !important;' data-turno-id='" + ObjSett[2] + "'>");
                    String[] turn = shift.split(":");
                    out.print("<div class='media-body'>");
                    out.print("<div class='media-title'>" + turn[0] + "</div>");
                    out.print("<div class='text-job text-muted'>" + turn[1] + "</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</div>");
            } else {
                out.print("<h4>Se ha presentado un problema al consultar los datos.</h4>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("<div class='col-lg-8'>");
            //<editor-fold defaultstate="collapsed" desc="PERSONAL ACTIVO">
            out.print("<h4 class='text-center'>Personal Activo</h4>");
            lst_user = UserJpa.ConsultPersonalActive();
            if (lst_user != null || lst_user.size() > 0) {
                for (int i = 0; i < lst_user.size(); i++) {
                    if (i % 3 == 0) {
                        if (i != 0) {
                            out.print("</div>");
                        }
                        out.print("<div class='d-flex' style='justify-content: space-evenly;margin-bottom: 10px;'>");
                    }
                    Object[] ObjUser = (Object[]) lst_user.get(i);
                    out.print("<div class='media usuario' id='mediaUs" + i + "' onclick=\"RotateColors(" + i + ",'" + ObjUser[0] + "')\" style='padding-bottom: 4px;margin-bottom: 4px; width: 185px !important;' data-user-id='" + ObjUser[0] + "'>");
                    out.print("<img alt='image' class='mr-3 rounded-circle' style='width:55px; height: 68px; box-shadow: 0px 0px 4px 1px #9d9d9d;margin-bottom: 5px;' src='Interface/Photos/" + ObjUser[1] + ".jpg'>");
                    out.print("<div class='media-body'>");
                    out.print("<div class='media-title'>" + ObjUser[2] + " " + ObjUser[3] + "</div>");
                    out.print("<div class='text-job text-muted'>" + ObjUser[5] + "</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</div>");
            } else {
                out.print("<h4>no se han encontrado usuarios activos!</h4>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");

            out.print("<form action='Shift?opt=2' method='post' id='formShift'>");
            //<editor-fold defaultstate="collapsed" desc="FORM">
            out.print("<input type='hidden' class='form-control' id='ClassData' name='tempClass' placeholder='class'>");
            out.print("<input type='hidden' class='form-control' id='tempTurno' name=''  placeholder='turno' >");
            out.print("<input type='hidden' class='form-control' name='weekSelect' id='weekSelect'>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId1' name='hiddenTurnoId1' placeholder='turno 1' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId2' name='hiddenTurnoId2' placeholder='turno 2' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId3' name='hiddenTurnoId3' placeholder='turno 3' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId4' name='hiddenTurnoId4' placeholder='turno 4' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId5' name='hiddenTurnoId5' placeholder='turno 5' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId6' name='hiddenTurnoId6' placeholder='turno 6' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId7' name='hiddenTurnoId7' placeholder='turno 7' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId8' name='hiddenTurnoId8' placeholder='turno 8' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId9' name='hiddenTurnoId9' placeholder='turno 9' style=''>");
            out.print("<input type='hidden' class='form-control' id='hiddenTurnoId10' name='hiddenTurnoId10' placeholder='turno 10' style=''>");
            out.print("<input type='checkbox' class='' name='CbxValidPog' id='CbxValidPog' onclick='weekClean()'>Realizar programación del mes");
            out.print("<input type='hidden' class='form-control' name='WeekAct' id='' value='" + weekAct + "' placeholder='Semanas iniciales'>");
            out.print("<input type='hidden' class='form-control' name='WeekNext' id='' value='" + weekNext + "' placeholder='Semanas finales'>");
            out.print("<div class='text-center'>");
            out.print("<button type='button' class='btn btn-green' onclick='ejectForm()'>Programar</button>");
            out.print("</div>");
            //</editor-fold>
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
            out.print("<div class='mr-2'>"
                    + "<button class='btn btn-green btn-sm' style='border-radius: 4px; padding: 2px 9px;'  onclick=\"javascript:location.href='Setting.jsp'\" ><i class=\"fas fa-arrow-left\"></i></button>"
                    + "</div>"
                    + "<h2>Programación de turnos</h2>");
            if (txtPermissions.contains("[18]")) {
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
            } else {
                out.print("<button class='btn btn-green' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-plus'></i></button>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            lst_shift = ShiftJap.ConsultShift();
            if (lst_shift != null) {
                out.print("<div class=''>");
                out.print("<h4>Semanas programas</h4>");
                out.print("</div>");
                out.print("<div id='accordion'>");
                out.print("<div class='accordion'>");
                for (int i = 0; i < lst_shift.size(); i++) {
                    Object[] ObjShift = (Object[]) lst_shift.get(i);
                    boolean ValWeek = false;
                    if (ActualWeek.equals(ObjShift[1].toString())) {
                        ValWeek = true;
                    }

                    out.print("<div class='accordion-header' role='button' data-toggle='collapse' data-target='#panel-body-" + i + "' " + ((ValWeek) ? "aria-expanded=\"true\"" : "") + " >");
                    out.print("<h4>" + ObjShift[1] + " &nbsp; " + ((ValWeek) ? "<span class='text-success'><i class=\"fas fa-circle\" style='font-size: 10px;'></i></span>" : "") + "</h4> ");
                    out.print("</div>");
                    String[] DataShift = ObjShift[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    out.print("<div class='accordion-body collapse " + ((ValWeek) ? "show" : "") + "' id='panel-body-" + i + "' data-parent='#accordion'>");
                    out.print("<div style='text-align: end;margin-bottom: 10px;'>");
                    if (txtPermissions.contains("[19]")) {
                        out.print("<button class='btn btn-warning' onclick='window.location.href=\"Shift?opt=1&idShift=" + ObjShift[0] + "\"'><i class='fas fa-edit'></i></button>");
                    } else {
                        out.print("<button class='btn btn-warning' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='No tiene permisos'><i class='fas fa-edit'></i></button>");
                    }
                    out.print("</div>");

                    out.print("<div class=''>");
                    out.print("<table class='table table-sm' id='table-1'>");
                    out.print("<tr style='background: #33bf98;color: black; text-align: center;'>");
                    out.print("<th>TURNO PROGRAMADO</th>");
                    out.print("<th>PERSONAL PROGRAMADO</th>");
                    out.print("</tr>");

                    for (int j = 0; j < DataShift.length; j++) {
                        String tittle = DataShift[j].toString().replace("am/", "am///").replace("pm/", "pm///").split("///")[0];
                        out.print("<tr>");
                        out.print("<th class='text-center'><b>" + tittle.replace("Turno", "").split(":")[0] + "</b>&nbsp;&nbsp; <i class=\"fas fa-arrow-right\"></i> &nbsp; <span style='font-weight: lighter;'>" + tittle.split(":")[1] + "</span></th>");

                        String userId = DataShift[j].replace("am/", "am///").replace("pm/", "pm///").split("///")[1];
                        lst_user = UserJpa.ConsultUsersMultiple(userId);
                        out.print("<td>");
                        if (lst_user != null) {
                            for (int k = 0; k < lst_user.size(); k++) {
                                Object[] ObjUser = (Object[]) lst_user.get(k);
                                out.print("<span class='bullet'></span><span>" + ObjUser[1] + " " + ObjUser[2] + "</span>&nbsp;");
                            }
                        } else {
                            out.print("<h4>Error al consultar usuarios</h4>");
                        }
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</table>");
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="OLD">
//                    out.print("<div class='d-flex justify-content-around'>");
//                    for (int j = 0; j < DataShift.length; j++) {
//                        out.print("<div class='contShift' style='width:" + ((DataShift.length <= 4) ? "20%;" : (DataShift.length == 5) ? "19%;" : (DataShift.length == 6) ? "16%;" : "14%;") + "'>");
//                        
//                        out.print("<div class='contShiftHead'>");
//                        String tittle = DataShift[j].toString().replace("am/", "am///").replace("pm/", "pm///").split("///")[0];
//                        out.print("<h4><b style='color: black;'>" + tittle.split(":")[0] + "</b></h4>");
//                        out.print("<p class='text-dark'>" + tittle.split(":")[1] + "</p>");
//                        out.print("</div>");
//
//                        out.print("<div class=''>");
//                        String userId = DataShift[j].replace("am/", "am///").replace("pm/", "pm///").split("///")[1];
//                        lst_user = UserJpa.ConsultUsersMultiple(userId);
//                        if (lst_user != null) {
//                            out.print("<ul class='list-group list-group-flush text-center'>");
//                            for (int k = 0; k < lst_user.size(); k++) {
//                                Object[] ObjUser = (Object[]) lst_user.get(k);
//                                out.print("<li class='list-group-item' style='padding: 0.1rem 0.25rem;'>" + ObjUser[1] + " " + ObjUser[2] + " </li>");
//                            }
//                            out.print("</ul>");
//                        } else {
//                            out.print("<h4>Error al consultar usuarios</h4>");
//                        }
//                        out.print("</div>");
//                        
//                        out.print("</div>");
//                    }
//                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");

            } else {
                out.print("<div class=''>");
                out.print("<h4 class='text-center'>No se han encontrado programacion de turnos!<br>");
                out.print("<span class='text-center'><i class=\"fas fa-exclamation-triangle\" style='font-size: 80px;margin-top: 25px;'></i></span></h4>");
                out.print("</div>");
            }

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
//</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Tag_shift.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
