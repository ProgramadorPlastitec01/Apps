package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.FormatControllerJpa;
import Controller.ComputerControllerJpa;
import Controller.SupportControllerJpa;
import Controller.SettingControllerJpa;
import Controller.ComputerDetailJpaController;
import java.text.DecimalFormat;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import Method.ContaDayLab;

public class Tag_indicator extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        ComputerControllerJpa ComputerJpa = new ComputerControllerJpa();
        SupportControllerJpa SupportJpa = new SupportControllerJpa();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        ComputerDetailJpaController ComputerDetailJpa = new ComputerDetailJpaController();
        List lst_format = null, lst_computer = null, lst_support = null, lst_yearSupport = null, lst_chart = null, lst_conventions = null, lst_festive_day = null,
                lst_discount_day = null, lst_executerType = null, lst_score = null, lst_calification = null, lst_typeS = null, lst_countTicket = null, lst_assignment = null,
                lst_history = null;
        int YearInt = 0, MonthInt = 0, Type = 0, AdminCant = 0, ProcessCant = 0, ServCant = 0, Count = 0;
        double PE = 0, PDP = 0, PC = 0, PP = 0, MinuteJob = 0;
        String MonthName = "", Admin = "", Process = "", Serv = "", labels = "", TS = "", TC = "";

        try {
            try {
                YearInt = Integer.parseInt(pageContext.getRequest().getAttribute("YearInt").toString());
            } catch (NumberFormatException e) {
                YearInt = LocalDate.now().getYear();
            }
            try {
                MonthInt = Integer.parseInt(pageContext.getRequest().getAttribute("MonthInt").toString());
            } catch (NumberFormatException e) {
                MonthInt = LocalDate.now().getMonthValue();
            }
            try {
                MonthName = pageContext.getRequest().getAttribute("MonthName").toString();
            } catch (Exception e) {
                MonthName = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            }
            try {
                Type = Integer.parseInt(pageContext.getRequest().getAttribute("Type").toString());
            } catch (NumberFormatException e) {
                Type = 0;
            }

            switch (Type) {
                case 0:
                    //<editor-fold defaultstate="collapsed" desc="INDICATOR DETAIL">
                    //<editor-fold defaultstate="collapsed" desc="LIST CONTETNT">
                    lst_format = FormatJpa.ConsultFormatName("Indicador");
                    lst_support = SupportJpa.ConsultIndicatorMonth(YearInt, MonthInt);
                    lst_chart = SupportJpa.ConsultIndicatorChart(YearInt, MonthInt);
                    lst_countTicket = SupportJpa.ConsultIndicatorCountTicketMonth(YearInt, MonthInt);
                    //</editor-fold>
                    if (lst_countTicket != null) {
                        Object[] ObjCountT = (Object[]) lst_countTicket.get(0);
                        if (Integer.parseInt(ObjCountT[1].toString()) > 0) {
                            out.print("<input type='hidden' id='CantTicket' value=' Durante el mes, se cuenta con un total de " + ObjCountT[1] + " casos, donde los más representativos son los de tipo '>");
                        }
                    }
                    if (lst_chart != null) {
                        Object[] ObjChart = (Object[]) lst_chart.get(0);
                        if (ObjChart[0] != null) {
                            PC = Integer.parseInt(ObjChart[0].toString());
                        } else {
                            PC = 0;
                        }
                        if (ObjChart[1] != null) {
                            PP = Integer.parseInt(ObjChart[1].toString());
                        } else {
                            PP = 0;
                        }
                    }
                    if (lst_format != null) {
                        Object[] ObjFormat = (Object[]) lst_format.get(0);

                        //<editor-fold defaultstate="collapsed" desc="MAPS">
                        String[] ArrDayFest = {};
                        lst_festive_day = SettingJpa.ConsultSettingCategorie(YearInt + "");
                        HashMap<String, String> mapDay = new HashMap<>();
                        HashMap<String, String> mapDayEvent = new HashMap<>();
                        if (lst_festive_day != null) {
                            Object[] ObjDayFestive = (Object[]) lst_festive_day.get(0);
                            ArrDayFest = ObjDayFestive[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                            for (String DayFest : ArrDayFest) {
                                String[] DesDayFest = DayFest.split("/");
                                if (DesDayFest.length == 3) {
                                    mapDay.put(DesDayFest[0], DesDayFest[1]);
                                    mapDayEvent.put(DesDayFest[0], DesDayFest[2]);
                                }
                            }
                        }
                        String[] ArrDisDay = {};
                        String Method = "DiscountDayFest" + YearInt;
                        HashMap<String, String> mapDisDay = new HashMap<>();
                        lst_discount_day = SettingJpa.ConsultSettingCategorie(Method);
                        if (lst_discount_day != null) {
                            Object[] ObjDiscountDay = (Object[]) lst_discount_day.get(0);
                            ArrDisDay = ObjDiscountDay[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                            for (String DisDay : ArrDisDay) {
                                String[] DesDisDay = DisDay.split("/");
                                if (DesDisDay.length == 2) {
                                    mapDisDay.put(DesDisDay[0], DesDisDay[1]);
                                }
                            }
                        }

                        String[] ArrAgm = {};
                        lst_assignment = ComputerJpa.ConsultIndicatorAssignment(YearInt, MonthInt);
                        Object[] ObjAssignment = (Object[]) lst_assignment.get(0);
                        HashMap<String, String> mapAsg = new HashMap<>();
                        HashMap<String, String> mapAsgDate = new HashMap<>();
                        HashMap<String, String> mapAsgType = new HashMap<>();
                        if (lst_assignment != null && ObjAssignment[1] != null) {
                            ArrAgm = ObjAssignment[1].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                            for (String ArrAsgFinal : ArrAgm) {
                                String[] AgmCmp = ArrAsgFinal.split("/");
                                if (AgmCmp.length == 4) {
                                    mapAsg.put(AgmCmp[0], AgmCmp[0]);
                                    mapAsgDate.put(AgmCmp[0], AgmCmp[1]);
                                    mapAsgType.put(AgmCmp[0], AgmCmp[3]);
                                }
                            }
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="EVENT DAY">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='contEventDay'>");

                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4>Eventos del mes</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");

                        out.print("<div class='row w-100 ml-2'>");
                        String Day = mapDay.get(MonthName.toLowerCase());
                        String TextDay = mapDayEvent.get(MonthName.toLowerCase());

                        String[] ArgDay = Day.split("-");
                        String[] ArgTextDay = TextDay.split("-");
                        for (int i = 0; i < ArgDay.length; i++) {
                            if (!ArgDay[i].equals("0") && !ArgTextDay[i].equals("NA")) {
                                out.print("<div class='DivDay'>");
                                out.print("<div class='DivHeadDay'><b>" + ArgDay[i] + "</b></div>");
                                out.print("<div><p class='m-2'>" + ArgTextDay[i] + "</p></div>");
                                out.print("</div>");
                            } else {
                                out.print("<h6 class='text-warning'>Sin eventos o festivades en el mes</h6>");
                            }
                        }

                        out.print("</div>");

                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>

                        out.print("<section class='section'>");
                        out.print("<div class='card'>");

                        //<editor-fold defaultstate="collapsed" desc="INPUT HIDDEN">
                        out.print("<input type='hidden' id='TotalH'>");
                        out.print("<input type='hidden' id='TotalM'>");
                        out.print("<input type='hidden' id='PC_data' value='" + PC + "'>");
                        out.print("<input type='hidden' id='PP_data' value='" + PP + "'>");
                        //</editor-fold>

                        out.print("<div class='card-header' style='justify-content: space-between; align-items:flex-start'>");
                        //<editor-fold defaultstate="collapsed" desc="TILTE AND FESTIVES / YEAR CALENDAR">
                        out.print("<p></p>");
                        out.print("<h2>Indicador <span style='text-transform: uppercase;'>" + MonthName + "</span></h2>");
                        out.print("<div class='d-flex'>");
                        out.print("<div class='mr-4'><button onclick='mostrarConvencion(1)' class='btn btn-success' data-toggle='tooltip' data-placement='top' title='Festivos' style='border-radius: 4px;' ><i class=\"fas fa-star\"></i></button></div>");
                        out.print("<div ><a href='Indicator?opt=1&Type=1'><button class='btn btn-green' data-toggle='tooltip' data-placement='top' title='Consulta Anual' style='border-radius: 4px;' ><i class='far fa-calendar-alt'></i></button></a></div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div class='d-flex justify-content-around m-3'>");
                        //<editor-fold defaultstate="collapsed" desc="BUTTONS MODULE">
                        out.print("<div><button onclick='ViewIndicatorSection(1)' id='Btn1' class='btn btn-info' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Calculo Horas x Equipo' onclick='mostrarConvencion(4)' ><i class='fas fa-desktop'></i></button></div>");
                        out.print("<div><button onclick='ViewIndicatorSection(2)' id='Btn2' class='btn btn-outline-warning' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Parada x Área' onclick='mostrarConvencion(4);' ><i class='fas fa-stop'></i></button></div>");
                        out.print("<div><button onclick='ViewIndicatorSection(3)' id='Btn3' class='btn btn-outline-danger' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Indicador Vs Parada' onclick='mostrarConvencion(4)' ><i class='fas fa-minus'></i></button></div>");
                        out.print("<div><button onclick='ViewIndicatorSection(4)' id='Btn4' class='btn btn-outline-secondary' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Reporte Tickets' onclick='mostrarConvencion(4)' ><i class=\"fas fa-headset\"></i></button></div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div id='View1' style='display:block'>");
                        //<editor-fold defaultstate="collapsed" desc="INDICADOR INFORME">
                        DecimalFormat ct = new DecimalFormat("#");

                        out.print("<div class='ml-5 mr-5 mb-5'>");
                        out.print(ObjFormat[3].toString()
                                .replace("XXXMESXXX", "<span style='text-transform: uppercase;'>" + MonthName + "</span>")
                                .replace("XXTTMXX", "<span id='MinuteJob'></span>")
                                .replace("XXPEMXX", "" + ct.format(PC) + "")
                                .replace("XXPRXX", "" + ct.format(PP) + "")
                                .replace("XXPEXX", "<span id='PE'></span>")
                                .replace("XXPPXX", "<span id='PDP'></span>")
                                .replace("XXANALISISXX", "<span id='Analysis'></span>"));
                        out.print("</div>");
                        out.print("<div class='ButtonIndicator'><button class='btn btn-warning' type='button' data-toggle='tooltip' data-placement='top' onclick='SendDataCount();' title='Ejecutar'><i class='fas fa-play' ></i></button></div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div id='View2' style='display:none'>");
                        //<editor-fold defaultstate="collapsed" desc="CALCULO HORAS X EQUIPO">
                        out.print("<div class='d-flex justify-content-around' style='width:100%'>");
                        out.print("<div><h5>Total Horas: <b class='b_text' id='GetData1'></b></h5></div>");
                        out.print("<div><h5>Minutos totales: <b class='b_text' id='GetData2'></b></h5></div>");
                        out.print("</div>");
                        
                         out.print("<div class='ButtonIndicator'><button class='btn btn-warning' type='button' data-toggle='tooltip' data-placement='top' onclick='SendDataCount();' title='Ejecutar'><i class='fas fa-play' ></i></button></div>");

                        lst_conventions = SettingJpa.ConsultSettingCategorie("ConventionsIndicator");
                        //<editor-fold defaultstate="collapsed" desc="CONVECTION DEFAULT INDICATOR">
                        if (lst_conventions != null) {
                            Object[] ObjConventions = (Object[]) lst_conventions.get(0);
                            String[] ArgConv = ObjConventions[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                            Admin = ArgConv[0].split("/")[0];
                            AdminCant = Integer.parseInt(ArgConv[0].split("/")[1]);
                            Process = ArgConv[1].split("/")[0];
                            ProcessCant = Integer.parseInt(ArgConv[1].split("/")[1]);
                            Serv = ArgConv[2].split("/")[0];
                            ServCant = Integer.parseInt(ArgConv[2].split("/")[1]);
                        }
                        //</editor-fold>

                        out.print("<div class='table-responsive' style='width: 90%;margin-left: 5%;' >");
                        out.print("<table class=\"table\" id=\"table-1\">");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th class=\"text-center\">PC</th>");
                        out.print("<th class=\"text-center\">Área</th>");
                        out.print("<th class=\"text-center\">Cargo</th>");
                        out.print("<th class=\"text-center\">Nombre Usuario</th>");
                        out.print("<th class=\"text-center\">Horas</th>");
                        out.print("<th class=\"text-center\">Tipo</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody style='font-size:12px;'>");
                        lst_computer = ComputerJpa.ConsultComputerIndicator();
                        if (lst_computer != null) {
                            for (int i = 0; i < lst_computer.size(); i++) {
                                Object[] ObjComputer = (Object[]) lst_computer.get(i);
                                out.print("<tr>");
                                out.print("<td><b>" + ObjComputer[0] + "</b></td>");
                                out.print("<td>" + ((ObjComputer[1] == null) ? "" : ObjComputer[1].toString().trim()) + "</td>");
                                out.print("<td>" + ((ObjComputer[2] == null) ? "" : ObjComputer[2]) + "</td>");
                                out.print("<td>" + ((ObjComputer[3] == null) ? "" : ObjComputer[3]) + "</td>");
                                int CalF = 0, Cal1 = 0, diasLaborables = 0, horasPorDia = 0, cantDHours = 0, diasLaborablesSec = 0, CalFSecond = 0, horasPorDiaSecond = 0;
                                String IdCp = mapAsg.get(ObjComputer[5].toString());
                                if (IdCp != null && IdCp.equals(ObjComputer[5].toString())) {
                                    //<editor-fold defaultstate="collapsed" desc="VALIDACION DE ID DE INDICADOR PC VS ID DE LA ASIGNACIÓN">
                                    String DateAgm = mapAsgDate.get(ObjComputer[5].toString());
                                    String AsgType = mapAsgType.get(ObjComputer[5].toString());
                                    String val = mapDay.get(MonthName.toLowerCase());

                                    if (DateAgm != null && !DateAgm.isEmpty()) {
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        LocalDate fechaAsignacion = LocalDate.parse(DateAgm, formatter);
                                        if (!AsgType.equals("SOPORTE") && !AsgType.equals("DE BAJA")) {
                                            horasPorDia = Integer.parseInt(mapDisDay.get(AsgType));
                                        } else {
                                            horasPorDia = 0;
                                        }
                                        Set<Integer> diasFestivosMes = new HashSet<>();
                                        if (val != null && !val.equals("0")) {
                                            for (String day : val.split("-")) {
                                                if (!day.trim().equals("0")) {
                                                    diasFestivosMes.add(Integer.parseInt(day.trim()));
                                                }
                                            }
                                        }
                                        switch (AsgType) {
                                            case "PROCESO":
                                                //<editor-fold defaultstate="collapsed" desc="VALIDATION PROCESS">
                                                diasLaborables = ContaDayLab.contarDiasLaborablesProceso(fechaAsignacion, diasFestivosMes);
                                                CalF = diasLaborables * horasPorDia;
                                                lst_history = ComputerDetailJpa.ConsultIndicatorHistoryCmp(ObjComputer[5].toString(), DateAgm);
                                                if (lst_history != null) {
                                                    Object[] ObjHis = (Object[]) lst_history.get(0);
                                                    if (ObjHis[0] != null) {
                                                        //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE ASIGNACION DURANTE EL MES">
                                                        String[] ArgHis = ObjHis[0].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                                        LocalDate fechaAnteriorAsignacion = null;
                                                        for (int j = 0; j < ArgHis.length; j++) {
                                                            String DateSecAsg = ArgHis[j].split("/")[1];
                                                            LocalDate fechaActualAsignacion = LocalDate.parse(DateSecAsg, formatter);
                                                            String TypePss = ArgHis[j].split("/")[3];
                                                            if (!TypePss.equals("SOPORTE") && !TypePss.equals("DE BAJA")) {
                                                                horasPorDiaSecond = Integer.parseInt(mapDisDay.get(TypePss));
                                                            } else {
                                                                horasPorDiaSecond = 0;
                                                            }
                                                            if (j == 0) {
                                                                // En la primera iteración, se cuenta desde la primera asignación hasta la fecha actual
                                                                switch (TypePss) {
                                                                    case "ADMINISTRATIVO":
                                                                        diasLaborablesSec = ContaDayLab.contarDíaLaboralesAdministrativoRango(fechaActualAsignacion, fechaAsignacion, diasFestivosMes);
                                                                          break;
                                                                    case "PROCESO":
                                                                        diasLaborablesSec = ContaDayLab.contarDíaLaboralesProcesoRango(fechaActualAsignacion, fechaAsignacion, diasFestivosMes);
                                                                        break;
                                                                    default:
                                                                        diasLaborablesSec = 0;
                                                                        break;
                                                                }
                                                            } else {
                                                                if (null == TypePss) {
                                                                    diasLaborablesSec = 0;
                                                                } else {
                                                                    // A partir de la segunda, se cuenta entre la fecha anterior y la actual
                                                                    switch (TypePss) {
                                                                        case "ADMINISTRATIVO":
                                                                            diasLaborablesSec = ContaDayLab.contarDíaLaboralesAdministrativoRango(fechaActualAsignacion, fechaAnteriorAsignacion, diasFestivosMes);
                                                                            break;
                                                                        case "PROCESO":
                                                                            diasLaborablesSec = ContaDayLab.contarDíaLaboralesProcesoRango(fechaActualAsignacion, fechaAnteriorAsignacion, diasFestivosMes);
                                                                            break;
                                                                        default:
                                                                            diasLaborablesSec = 0;
                                                                            break;
                                                                    }
                                                                }
                                                            }
                                                            CalFSecond += diasLaborablesSec * horasPorDiaSecond;
                                                            // Guardar la fecha actual como la anterior para la próxima iteración
                                                            fechaAnteriorAsignacion = fechaActualAsignacion;
                                                        }
                                                        CalF = CalF + CalFSecond;
                                                        //</editor-fold>
                                                    } else {
                                                        //<editor-fold defaultstate="collapsed" desc="CONSULTA RESTANTE SIN HISTORIAL DE ASIGNACIONES">
                                                        String TypePss = ObjHis[1].toString();
                                                        if (!TypePss.equals("SOPORTE") && !TypePss.equals("DE BAJA")) {
                                                            horasPorDiaSecond = Integer.parseInt(mapDisDay.get(TypePss));
                                                        } else {
                                                            horasPorDiaSecond = 0;
                                                        }
                                                        switch (TypePss) {
                                                            case "ADMINISTRATIVO":
                                                                diasLaborablesSec = ContaDayLab.contarDiasLaborablesAdministrativoSinHistorial(fechaAsignacion, diasFestivosMes);
                                                                break;
                                                            case "PROCESO":
                                                                diasLaborablesSec = ContaDayLab.contarDiasLaborablesProcesoSinHistorial(fechaAsignacion, diasFestivosMes);
                                                                break;
                                                            default:
                                                                diasLaborablesSec = 0;
                                                                break;
                                                        }
                                                        CalFSecond += diasLaborablesSec * horasPorDiaSecond;
                                                        CalF = CalF + CalFSecond;
                                                        //</editor-fold>
                                                    }
                                                }
                                                //</editor-fold>
                                                break;
                                            case "ADMINISTRATIVO":
                                                //<editor-fold defaultstate="collapsed" desc="ADMIN">
                                                diasLaborables = ContaDayLab.contarDiasLaborablesAdministrativo(fechaAsignacion, diasFestivosMes);
                                                CalF = diasLaborables * horasPorDia;
                                                lst_history = ComputerDetailJpa.ConsultIndicatorHistoryCmp(ObjComputer[5].toString(), DateAgm);
                                                if (lst_history != null) {
                                                    Object[] ObjHis = (Object[]) lst_history.get(0);
                                                    if (ObjHis[0] != null) {
                                                        //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE ASIGNACION DURANTE EL MES">
                                                        String[] ArgHis = ObjHis[0].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                                        LocalDate fechaAnteriorAsignacion = null;
                                                        for (int j = 0; j < ArgHis.length; j++) {
                                                            String DateSecAsg = ArgHis[j].split("/")[1];
                                                            LocalDate fechaActualAsignacion = LocalDate.parse(DateSecAsg, formatter);
                                                            String TypePss = ArgHis[j].split("/")[3];
                                                            if (!TypePss.equals("SOPORTE") && !TypePss.equals("DE BAJA")) {
                                                                horasPorDiaSecond = Integer.parseInt(mapDisDay.get(TypePss));
                                                            } else {
                                                                horasPorDiaSecond = 0;
                                                            }
                                                            if (j == 0) {
                                                                // En la primera iteración, se cuenta desde la primera asignación hasta la fecha actual
                                                                switch (TypePss) {
                                                                    case "ADMINISTRATIVO":
                                                                        diasLaborablesSec = ContaDayLab.contarDíaLaboralesAdministrativoRango(fechaActualAsignacion, fechaAsignacion, diasFestivosMes);
                                                                          break;
                                                                    case "PROCESO":
                                                                        diasLaborablesSec = ContaDayLab.contarDíaLaboralesProcesoRango(fechaActualAsignacion, fechaAsignacion, diasFestivosMes);
                                                                        break;
                                                                    default:
                                                                        diasLaborablesSec = 0;
                                                                        break;
                                                                }
                                                            } else {
                                                                if (null == TypePss) {
                                                                    diasLaborablesSec = 0;
                                                                } else {
                                                                    // A partir de la segunda, se cuenta entre la fecha anterior y la actual
                                                                    switch (TypePss) {
                                                                        case "ADMINISTRATIVO":
                                                                            diasLaborablesSec = ContaDayLab.contarDíaLaboralesAdministrativoRango(fechaActualAsignacion, fechaAnteriorAsignacion, diasFestivosMes);
                                                                            break;
                                                                        case "PROCESO":
                                                                            diasLaborablesSec = ContaDayLab.contarDíaLaboralesProcesoRango(fechaActualAsignacion, fechaAnteriorAsignacion, diasFestivosMes);
                                                                            break;
                                                                        default:
                                                                            diasLaborablesSec = 0;
                                                                            break;
                                                                    }
                                                                }
                                                            }
                                                            CalFSecond += diasLaborablesSec * horasPorDiaSecond;
                                                            // Guardar la fecha actual como la anterior para la próxima iteración
                                                            fechaAnteriorAsignacion = fechaActualAsignacion;
                                                        }
                                                        CalF = CalF + CalFSecond;
                                                        //</editor-fold>
                                                    } else {
                                                        //<editor-fold defaultstate="collapsed" desc="CONSULTA RESTANTE SIN HISTORIAL DE ASIGNACIONES">
                                                        String TypePss = ObjHis[1].toString();
                                                        if (!TypePss.equals("SOPORTE") && !TypePss.equals("DE BAJA")) {
                                                            horasPorDiaSecond = Integer.parseInt(mapDisDay.get(TypePss));
                                                        } else {
                                                            horasPorDiaSecond = 0;
                                                        }
                                                        switch (TypePss) {
                                                            case "ADMINISTRATIVO":
                                                                diasLaborablesSec = ContaDayLab.contarDiasLaborablesAdministrativoSinHistorial(fechaAsignacion, diasFestivosMes);
                                                                break;
                                                            case "PROCESO":
                                                                diasLaborablesSec = ContaDayLab.contarDiasLaborablesProcesoSinHistorial(fechaAsignacion, diasFestivosMes);
                                                                break;
                                                            default:
                                                                diasLaborablesSec = 0;
                                                                break;
                                                        }
                                                        CalFSecond += diasLaborablesSec * horasPorDiaSecond;
                                                        CalF = CalF + CalFSecond;
                                                        //</editor-fold>
                                                    }
                                                }
                                                //</editor-fold>
                                                break;
                                            case "SERVIDOR":
                                                //<editor-fold defaultstate="collapsed" desc="SERVICE">
                                                
                                                diasLaborables = ContaDayLab.contarDiasLaborablesServidor(fechaAsignacion, diasFestivosMes);
                                                CalF = diasLaborables * horasPorDia;
                                                lst_history = ComputerDetailJpa.ConsultIndicatorHistoryCmp(ObjComputer[5].toString(), DateAgm);
                                                if (lst_history != null) {
                                                    Object[] ObjHis = (Object[]) lst_history.get(0);
                                                    if (ObjHis[0] != null) {
                                                        //<editor-fold defaultstate="collapsed" desc="HISTORIAL DE ASIGNACION DURANTE EL MES">
                                                        String[] ArgHis = ObjHis[0].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                                        LocalDate fechaAnteriorAsignacion = null;
                                                        for (int j = 0; j < ArgHis.length; j++) {
                                                            String DateSecAsg = ArgHis[j].split("/")[1];
                                                            LocalDate fechaActualAsignacion = LocalDate.parse(DateSecAsg, formatter);
                                                            String TypePss = ArgHis[j].split("/")[3];
                                                            if (!TypePss.equals("SOPORTE") && !TypePss.equals("DE BAJA")) {
                                                                horasPorDiaSecond = Integer.parseInt(mapDisDay.get(TypePss));
                                                            } else {
                                                                horasPorDiaSecond = 0;
                                                            }
                                                            if (j == 0) {
                                                                // En la primera iteración, se cuenta desde la primera asignación hasta la fecha actual
                                                                switch (TypePss) {
                                                                    case "ADMINISTRATIVO":
                                                                        diasLaborablesSec = ContaDayLab.contarDíaLaboralesAdministrativoRango(fechaActualAsignacion, fechaAsignacion, diasFestivosMes);
                                                                          break;
                                                                    case "PROCESO":
                                                                        diasLaborablesSec = ContaDayLab.contarDíaLaboralesProcesoRango(fechaActualAsignacion, fechaAsignacion, diasFestivosMes);
                                                                        break;
                                                                    default:
                                                                        diasLaborablesSec = 0;
                                                                        break;
                                                                }
                                                            } else {
                                                                if (null == TypePss) {
                                                                    diasLaborablesSec = 0;
                                                                } else {
                                                                    // A partir de la segunda, se cuenta entre la fecha anterior y la actual
                                                                    switch (TypePss) {
                                                                        case "ADMINISTRATIVO":
                                                                            diasLaborablesSec = ContaDayLab.contarDíaLaboralesAdministrativoRango(fechaActualAsignacion, fechaAnteriorAsignacion, diasFestivosMes);
                                                                            break;
                                                                        case "PROCESO":
                                                                            diasLaborablesSec = ContaDayLab.contarDíaLaboralesProcesoRango(fechaActualAsignacion, fechaAnteriorAsignacion, diasFestivosMes);
                                                                            break;
                                                                        default:
                                                                            diasLaborablesSec = 0;
                                                                            break;
                                                                    }
                                                                }
                                                            }
                                                            CalFSecond += diasLaborablesSec * horasPorDiaSecond;
                                                            // Guardar la fecha actual como la anterior para la próxima iteración
                                                            fechaAnteriorAsignacion = fechaActualAsignacion;
                                                        }
                                                        CalF = CalF + CalFSecond;
                                                        //</editor-fold>
                                                    } else {
                                                        //<editor-fold defaultstate="collapsed" desc="CONSULTA RESTANTE SIN HISTORIAL DE ASIGNACIONES">
                                                        String TypePss = ObjHis[1].toString();
                                                        if (!TypePss.equals("SOPORTE") && !TypePss.equals("DE BAJA")) {
                                                            horasPorDiaSecond = Integer.parseInt(mapDisDay.get(TypePss));
                                                        } else {
                                                            horasPorDiaSecond = 0;
                                                        }
                                                        switch (TypePss) {
                                                            case "ADMINISTRATIVO":
                                                                diasLaborablesSec = ContaDayLab.contarDiasLaborablesAdministrativoSinHistorial(fechaAsignacion, diasFestivosMes);
                                                                break;
                                                            case "PROCESO":
                                                                diasLaborablesSec = ContaDayLab.contarDiasLaborablesProcesoSinHistorial(fechaAsignacion, diasFestivosMes);
                                                                break;
                                                            default:
                                                                diasLaborablesSec = 0;
                                                                break;
                                                        }
                                                        CalFSecond += diasLaborablesSec * horasPorDiaSecond;
                                                        CalF = CalF + CalFSecond;
                                                        //</editor-fold>
                                                    }
                                                }
                                                //</editor-fold>
                                                break;
                                            default:
                                                //<editor-fold defaultstate="collapsed" desc="TIPO NO APLICABLES">
                                                if (lst_festive_day != null) {
                                                    int dayDFest = 0;
                                                    if (val != null && !val.equals("0")) {
                                                        String[] splitDay = val.split("-");
                                                        for (String day : splitDay) {
                                                            if (!day.trim().equals("0")) {
                                                                dayDFest++;
                                                            }
                                                        }
                                                    }
                                                    cantDHours = Integer.parseInt(mapDisDay.get(ObjComputer[4].toString()));
                                                    Cal1 = cantDHours * dayDFest;
                                                    if (AsgType.equals("SOPORTE") || AsgType.equals("DE BAJA")) {

                                                    }
                                                    CalF = ProcessCant - Cal1;
                                                } else {
                                                    CalF = ProcessCant;
                                                }
                                                Count += CalF;
                                                //</editor-fold>
                                                break;
                                        }
                                    } else {
                                        //<editor-fold defaultstate="collapsed" desc="TIPO NO APLICABLES">
                                        if (lst_festive_day != null) {
                                            int dayDFest = 0;
                                            if (val != null && !val.equals("0")) {
                                                String[] splitDay = val.split("-");
                                                for (String day : splitDay) {
                                                    if (!day.trim().equals("0")) {
                                                        dayDFest++;
                                                    }
                                                }
                                            }
                                            cantDHours = Integer.parseInt(mapDisDay.get(ObjComputer[4].toString()));
                                            Cal1 = cantDHours * dayDFest;
                                            CalF = ProcessCant - Cal1;
                                        } else {
                                            CalF = ProcessCant;
                                        }

                                        Count += CalF;
                                        //</editor-fold>
                                    }

                                    Count += CalF;
                                    out.print("<td class='text-center font-weight-bold'>" + CalF + "</td>");
                                    out.print("<td>" + AsgType + "</td>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="SIN ASIGNACION">
                                    // No tiene asignación
                                    String TypeSA = ObjComputer[4].toString();
                                    if (lst_festive_day != null) {
                                        String val = mapDay.get(MonthName.toLowerCase());
                                        int dayDFest = 0;
                                        if (val != null && !val.equals("0")) {
                                            String[] splitDay = val.split("-");
                                            for (String day : splitDay) {
                                                if (!day.trim().equals("0")) {
                                                    dayDFest++;
                                                }
                                            }
                                        }
                                        switch (TypeSA) {
                                            case "ADMINISTRATIVO":
                                                cantDHours = Integer.parseInt(mapDisDay.get(TypeSA));
                                                Cal1 = cantDHours * dayDFest;
                                                CalF = AdminCant - Cal1;
                                                break;
                                            case "PROCESO":
                                                cantDHours = Integer.parseInt(mapDisDay.get(TypeSA));
                                                Cal1 = cantDHours * dayDFest;
                                                CalF = ProcessCant - Cal1;
                                                break;
                                            case "SERVIDOR":
                                                CalF = ServCant - Cal1;
                                                break;
                                            default:
                                                CalF = 0;

                                        }
                                    } else {
                                        switch (TypeSA) {
                                            case "ADMINISTRATIVO":
                                                CalF = AdminCant - Cal1;
                                                break;
                                            case "PROCESO":
                                                CalF = ProcessCant - Cal1;
                                                break;
                                            case "SERVIDOR":
                                                CalF = ServCant - Cal1;
                                                break;
                                            default:
                                                CalF = 0;
                                        }
                                    }
                                    Count += CalF;
                                    out.print("<td class='text-center font-weight-bold'>" + CalF + "</td>");
                                    out.print("<td>" + ((ObjComputer[4] == null) ? "" : ObjComputer[4]) + "</td>");
                                    //</editor-fold>
                                }
                                out.print("</tr>");
                            }
                        }
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("<input type='hidden' id='Data1' value='" + Count + "'>");
                        MinuteJob += Count;
                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div id='View3' style='display:none'>");
                        //<editor-fold defaultstate="collapsed" desc="PARADAS X AREA">
                        out.print("<div>");
                        out.print("<div class='table-responsive' style='width: 90%;margin-left: 5%;' >");
                        out.print("<table class=\"table table-hover\" id=\"table-3\">");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th>Área</th>");
                        out.print("<th>Parada Equipo</th>");
                        out.print("<th>Parada Producción</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        if (lst_support != null) {
                            for (int i = 0; i < lst_support.size(); i++) {
                                Object[] ObjIndicator = (Object[]) lst_support.get(i);
                                out.print("<tr>");
                                out.print("<td>" + ObjIndicator[0] + "</td>");
                                out.print("<td> " + ((Integer.parseInt(ObjIndicator[1].toString()) == 0) ? "0" : "<b>" + ObjIndicator[1].toString() + "</b>") + "</td>");
                                out.print("<td> " + ((Integer.parseInt(ObjIndicator[2].toString()) == 0) ? "0" : "<b>" + ObjIndicator[2].toString() + "</b>") + "</td>");
                                out.print("</tr>");
                            }
                        }
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='m-5'>");

                        out.print("<canvas id=\"myChart2\" height=\"180\"></canvas>");

                        out.print("<script>");
                        out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d');\n"
                                + "var myChart = new Chart(ctx, {\n"
                                + "  type: 'bar',\n"
                                + "  data: {\n"
                                + "    labels: [\"Parada PC\", \"Parada Produccion\"],\n"
                                + "    datasets: [{\n"
                                + "      label: 'Minutos',\n"
                                + "      data: [" + PC + ", " + PP + "],\n"
                                + "      borderWidth: 2,\n"
                                + "      backgroundColor: ['#fa8686', '#6575e9'],\n"
                                + "      borderColor: ['#fa8686', '#6575e9'],\n"
                                + "      borderWidth: 2.5,\n"
                                + "      pointBackgroundColor: '#ffffff',\n"
                                + "      pointRadius: 4\n"
                                + "    }]\n"
                                + "  },\n"
                                + "  options: {\n"
                                + "    legend: {\n"
                                + "      display: false\n"
                                + "    },\n"
                                + "    scales: {\n"
                                + "      yAxes: [{\n"
                                + "        gridLines: {\n"
                                + "          drawBorder: false,\n"
                                + "          color: '#f2f2f2',\n"
                                + "        },\n"
                                + "        ticks: {\n"
                                + "          beginAtZero: true,\n"
                                + "          stepSize: 0\n"
                                + "        }\n"
                                + "      }],\n"
                                + "      xAxes: [{\n"
                                + "        ticks: {\n"
                                + "          display: false\n"
                                + "        },\n"
                                + "        gridLines: {\n"
                                + "          display: false\n"
                                + "        }\n"
                                + "      }]\n"
                                + "    },\n"
                                + "  }\n"
                                + "});");
                        out.print("</script>");
                        out.print("</div>");

                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div id='View4' style='display:none'>");
                        //<editor-fold defaultstate="collapsed" desc="REPORTE TICKET">
                        out.print("<div class='m-5'>");

                        out.print("<div class=\"section-title mt-0\">Ejecutor por tipo</div>");
                        lst_executerType = SupportJpa.ConsultIndicatorExecuterxType(YearInt, MonthInt);
                        if (lst_executerType != null) {
                            //<editor-fold defaultstate="collapsed" desc="EJECUTOR TIPO">
                            out.print("<table class='table table-hover table-bordered table-striped table-sm'>");
                            for (int j = 0; j < lst_executerType.size(); j++) {
                                Object[] Executer = (Object[]) lst_executerType.get(j);
                                if (j > 0) {
                                    out.print("<tr>");
                                }
                                for (int i = 0; i < Executer.length; i++) {
                                    if (j == 0) {
                                        out.print("<th scope=\"col\">" + Executer[i] + "</th>");
                                    } else {
                                        out.print("<td>" + Executer[i] + "</td>");
                                    }
                                }
                                if (j > 0) {
                                    out.print("</tr>");
                                }
                            }
                            out.print("</table>");
                            //</editor-fold>
                        } else {
                            out.print("<h5><i class=\"far fa-surprise Emote\"></i>No existen datos registrados</h5>");
                        }

                        out.print("<div class=\"section-title mt-0\">Calificación</div>");
                        lst_score = SupportJpa.ConsultIndicatorExecuterxScore(YearInt, MonthInt);
                        if (lst_score != null) {
                            //<editor-fold defaultstate="collapsed" desc="CALIFICACION">
                            out.print("<table class='table table-hover table-bordered table-striped table-sm '>");
                            for (int h = 0; h < lst_score.size(); h++) {
                                Object[] Score = (Object[]) lst_score.get(h);
                                out.print("<tr>");
                                for (int i = 0; i < Score.length; i++) {
                                    if (h == 0) {
                                        if (i > 0 && !Score[i].equals("Total general")) {
                                            out.print("<th>");
                                            for (int j = 0; j < Integer.parseInt(Score[i].toString()); j++) {
                                                out.print("<i class=\"fas fa-star\" style='color:#ffd403'></i>");
                                            }
                                            out.print("</th>");

                                            labels += "'" + Score[i] + "', ";
                                        } else {
                                            out.print("<th>" + Score[i] + "</th>");

                                        }
                                    } else {
                                        out.print("<td>" + Score[i] + "</td>");
                                    }
                                }
                                out.print("</tr>");
                            }
                            out.print("</table>");

                            //</editor-fold>
                        } else {
                            out.print("<h5><i class=\"far fa-surprise Emote\"></i>No existen datos registrados</h5>");
                        }

                        out.print("<div class=\"section-title mt-0\">Calificación Resultados</div>");
                        lst_calification = SupportJpa.ConsultIndicatorCalificationxEjecuter(YearInt, MonthInt);
                        if (lst_calification != null) {
                            Object[] Validation = (Object[]) lst_calification.get(0);
                            if (Validation[3] != null) {
                                //<editor-fold defaultstate="collapsed" desc="CALIFICACIONES TOTALES">
                                out.print("<table class='table table-hover table-bordered table-striped table-sm '>");
                                out.print("<tr>");
                                out.print("<th>Calificaciones Inferiores a 3</th>");
                                out.print("<th>Total</th>");
                                out.print("<th>Calificaciones Superior a 4</th>");
                                out.print("<th>Total</th>");
                                out.print("<th>Total Casos</th>");
                                out.print("</tr>");
                                for (int h = 0; h < lst_calification.size(); h++) {
                                    Object[] Calification = (Object[]) lst_calification.get(h);
                                    out.print("<tr>");
                                    out.print("<td>" + Calification[0] + "</td>");
                                    out.print("<td>" + Calification[3] + "%</td>");
                                    out.print("<td>" + Calification[1] + "</td>");
                                    out.print("<td>" + Calification[4] + "%</td>");
                                    out.print("<td>" + Calification[2] + "</td>");
                                    out.print("</tr>");

                                    TC = "Del total de casos calificados el " + Calification[3] + "%  a calificaciones menores o iguales a 3 por no dar una solución efectiva al caso y el " + Calification[4] + "% corresponde a calificaciones superiores a 4 y 5, donde 1 es un mal servicio y 5 un excelente servicio.";
                                }
                                out.print("</table>");
                                //</editor-fold>
                            } else {
                                out.print("<h5><i class=\"far fa-surprise Emote\"></i>No existen datos registrados</h5>");
                            }
                            out.print("<input type='hidden' id='ResultCl' value='" + TC + "'>");

                        }

                        out.print("<div class=\"section-title mt-0\">Tipo Servicio</div>");
                        lst_typeS = SupportJpa.ConsultIndicatorTypeService(YearInt, MonthInt);
                        if (lst_typeS != null) {
                            //<editor-fold defaultstate="collapsed" desc="CALIFICACIONES TIPO">

                            out.print("<table class='table table-hover table-bordered table-striped table-sm '>");
                            out.print("<tr>");
                            out.print("<th>TIPO</th>");
                            out.print("<th>CANTIDAD</th>");
                            out.print("</tr>");
                            for (int t = 0; t < lst_typeS.size(); t++) {
                                Object[] TypeS = (Object[]) lst_typeS.get(t);
                                out.print("<tr>");
                                out.print("<td>" + TypeS[0] + "</td>");
                                out.print("<td>" + TypeS[1] + "</td>");
                                out.print("</tr>");
                                if (t <= 2) {
                                    TS += TypeS[0].toString() + "=" + TypeS[1].toString() + ((t == 2 || t == (lst_typeS.size() - 1)) ? ", " : ",");
                                } else if (t == lst_typeS.size() - 1) {
                                    TS += TypeS[0].toString() + ".";
                                } else {
                                    TS += "adicional se tiene " + TypeS[0].toString() + ", ";
                                }
                            }
                            out.print("</table>");
                            out.print("<input type='hidden' id='TypeServ' value=' " + TS + "'>");
                            //</editor-fold>
                        } else {
                            out.print("<h5><i class=\"far fa-surprise Emote\"></i>No existen datos registrados</h5>");
                        }

                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");

                        out.print("</div>");
                        out.print("</section>");
                    }
                    //</editor-fold>
                    break;

                default:
                    //<editor-fold defaultstate="collapsed" desc="INDICATOR GENERAL">
                    out.print("<section class='section'>");
                    out.print("<div class='card'>");
                    out.print("<div class='card-header' style='justify-content: space-between; align-items:flex-start'>");
                    out.print("<p></p>");
                    out.print("<h2>Indicador Anual</h2>");
                    out.print("<div class='mr-2'>");
                    out.print("<form>");
                    out.print("<select class='form-control' style='border-radius:4px;'>");
                    lst_yearSupport = SupportJpa.ConsultIndicatorYear();
                    if (lst_yearSupport != null) {
                        for (int i = 0; i < lst_yearSupport.size(); i++) {
                            Object[] ObjYear = (Object[]) lst_yearSupport.get(i);
                            out.print("<option value='" + ObjYear[1] + "'>" + ObjYear[1] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<p></p>");
                    out.print("<div class='row' style='justify-content: space-around;'>");
                    String[] nmes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                    String[] mes = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
                    String[] color = {"#ff0000", "#ff4000", "#ffbf00", "#ffff00", "#bfff00", "#40ff00", "#00ff80", "#00ffff", "#0080ff", "#0040ff", "#8000ff", "#ff0000"};
                    for (int i = 0; i < mes.length; i++) {
                        if (i % 4 == 0 && i != 0) {
                            out.print("</div>");
                            out.print("<div class='row' style='justify-content: space-around;'>");
                        }
                        out.print("<div class='DivShadow' onmouseover=\"this.style.transform='scale(1.1)';\" onmouseout=\"this.style.transform='scale(1)';\">");
                        out.print("<div class='col-lg-12' style='padding-left: 0px;'>\n");
                        out.print("<div style='border-top: 2px solid " + color[i] + "; !important'>");
                        out.print("<div style='min-height:0; margin-top:21px'>");
                        out.print("<h4 class='text-center w-100 TilteFrist' >" + mes[i] + "</h4>");
                        out.print("</div>");
                        out.print("<div class=\"card-body\">");
                        out.print("<a href='Indicator?opt=1&Type=0&MonthInt=" + nmes[i] + "&MonthName=" + mes[i] + "'><button class='btn btn-green text-center w-100' data-toggle='tooltip' data-placement='top' title='Ingresar' style='border-radius: 4px;' >Ingresar</button></a>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                    //</editor-fold>
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(Tag_indicator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
