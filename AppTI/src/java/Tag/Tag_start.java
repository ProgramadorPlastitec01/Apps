package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controller.DashBoardJpaController;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import Controller.SettingControllerJpa;
import SQL.ConnectionsBd;

public class Tag_start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        DashBoardJpaController DashJpa = new DashBoardJpaController();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        ConnectionsBd ConnectionBd = new ConnectionsBd();
        int IdUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String NameUser = sesion.getAttribute("Nombres").toString();
        String NameRol = sesion.getAttribute("NombreRol").toString();
        Calendar cal = Calendar.getInstance();
        int CurrYear = cal.get(Calendar.YEAR);
        int CurrMonth = (cal.get(Calendar.MONTH));
        String[] meses = new DateFormatSymbols(new Locale("es", "ES")).getMonths();
        String nombreMes = meses[CurrMonth];
//        nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1).toLowerCase();
        List lst_items = null, lst_follow = null, lst_activity = null, lst_module = null, lst_tickets = null;
        int CountP = 0;

        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1 class='text-center'>DashBoard Pruebas</h1>");
            out.print("</div>");

            out.print("<div class='container mt-4'>");
            out.print("<div class='row g-4'>"); // g-4 agrega espacio entre columnas/fila
            //            //<editor-fold defaultstate="collapsed" desc="GRAFICAS PARA MODULOS">
            //            lst_pending = DashJpa.ConsultPendingDashBoard(NameUser, NameRol);
            //            if (lst_pending != null) {
            //                String labelData = "", valueData = "";
            //                out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">\n");
            //                //<editor-fold defaultstate="collapsed" desc="PENDIENTE">
            //                out.print("  <div class=\"card card-statistic-2\">\n");
            //                out.print("    <div class=\"card-stats\">\n");
            //                out.print("      <div class=\"card-stats-title\"><h6>Pendientes</h6> </div>\n");
            //                out.print("      <div class=\"card-stats-items\">\n");
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    Object[] ObjPending = (Object[]) lst_pending.get(i);
            //                    CountP += Integer.parseInt(ObjPending[0].toString());
            //                    labelData += "'" + ObjPending[1].toString() + "', ";
            //                    valueData += ObjPending[0].toString() + ", ";
            //                    out.print("        <div class=\"card-stats-item\">\n");
            //                    out.print("          <div class=\"card-stats-item-count\">" + ObjPending[0] + "</div>\n");
            //                    out.print("          <div class=\"card-stats-item-label\">" + ObjPending[1] + "</div>\n");
            //                    out.print("        </div>\n");
            //                }
            //
            //                out.print("      </div>\n");
            //                out.print("    </div>\n");
            //                out.print("    <div class=\"card-wrap mt-3\">\n");
            //                out.print("<canvas id=\"myChart1\" width=\"400\" height=\"230\"></canvas>");
            //                out.print("<script>");
            //                out.print("var ctx = document.getElementById(\"myChart1\").getContext('2d'); ");
            //                out.print("var myChart = new Chart(ctx, { ");
            //                out.print("  type: 'doughnut', ");
            //                out.print("  data: { ");
            //                out.print("    labels: [" + labelData + "], ");
            //                out.print("    datasets: [{ ");
            //                out.print("      label: 'Proceso', ");
            //                out.print("      data: [" + valueData + "], ");
            //                out.print("      backgroundColor: [");
            //
            //                String[] colors = {"'#ffcd56'", "'#36a2eb'", "'#ff6384'", "'#0943f1'"};
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderColor: [");
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderWidth: 1.7 }], ");
            //                out.print("}, options: { "
            //                        + "responsive: true,"
            //                        + "    cutout: '60%', "
            //                        + "    legend: { "
            //                        + "      position: 'bottom', "
            //                        + "    }, ");
            //                out.print("}});");
            //                out.print("</script>");
            //                out.print("    </div>\n");
            //                out.print("  </div>\n");
            //                //</editor-fold>
            //                out.print("</div>\n");
            //
            //            }
            //            lst_binnacle = DashJpa.ConsultBinnacleState(IdUser);
            //            if (lst_binnacle != null) {
            //                String labelData = "", valueData = "";
            //                out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">\n");
            //                //<editor-fold defaultstate="collapsed" desc="BITACORA">
            //                out.print("  <div class=\"card card-statistic-2\">\n");
            //                out.print("    <div class=\"card-stats\">\n");
            //                out.print("      <div class=\"card-stats-title\"><h6>Bitacora</h6> </div>\n");
            //                out.print("      <div class=\"card-stats-items\">\n");
            //                for (int i = 0; i < lst_binnacle.size(); i++) {
            //                    Object[] ObjBinnacle = (Object[]) lst_binnacle.get(i);
            //                    CountP += Integer.parseInt(ObjBinnacle[0].toString());
            //                    labelData += "'" + ObjBinnacle[1].toString() + "', ";
            //                    valueData += ObjBinnacle[0].toString() + ", ";
            //                    out.print("        <div class=\"card-stats-item\">\n");
            //                    out.print("          <div class=\"card-stats-item-count\">" + ObjBinnacle[0] + "</div>\n");
            //                    out.print("          <div class=\"card-stats-item-label\">" + ObjBinnacle[1] + "</div>\n");
            //                    out.print("        </div>\n");
            //                }
            //
            //                out.print("      </div>\n");
            //                out.print("    </div>\n");
            //                out.print("    <div class=\"card-wrap mt-3\">\n");
            //                out.print("<canvas id=\"myChart2\" width=\"400\" height=\"230\"></canvas>");
            //                out.print("<script>");
            //                out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); ");
            //                out.print("var myChart = new Chart(ctx, { ");
            //                out.print("  type: 'polarArea', ");
            //                out.print("  data: { ");
            //                out.print("    labels: [" + labelData + "], ");
            //                out.print("    datasets: [{ ");
            //                out.print("      label: 'Proceso', ");
            //                out.print("      data: [" + valueData + "], ");
            //                out.print("      backgroundColor: [");
            //
            //                String[] colors = {"'#ffcd56'", "'#36a2eb'", "'#ff6384'", "'#0943f1'"};
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderColor: [");
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderWidth: 1.7 }], ");
            //                out.print("}, options: { "
            //                        + "responsive: true,"
            //                        + "    cutout: '60%', "
            //                        + "    legend: { "
            //                        + "      position: 'bottom', "
            //                        + "    }, ");
            //                out.print("}});");
            //                out.print("</script>");
            //                out.print("    </div>\n");
            //                out.print("  </div>\n");
            //                //</editor-fold>
            //                out.print("</div>\n");
            //            }
            //            lst_app = DashJpa.ConsultAplicationState();
            //            if (lst_app != null) {
            //                String labelData = "", valueData = "";
            //                out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">\n");
            //                //<editor-fold defaultstate="collapsed" desc="APLICACION">
            //                out.print("  <div class=\"card card-statistic-2\">\n");
            //                out.print("    <div class=\"card-stats\">\n");
            //                out.print("      <div class=\"card-stats-title\"><h6>Aplicacion</h6> </div>\n");
            //                out.print("      <div class=\"card-stats-items\">\n");
            //                for (int i = 0; i < lst_app.size(); i++) {
            //                    Object[] ObjApp = (Object[]) lst_app.get(i);
            //                    CountP += Integer.parseInt(ObjApp[0].toString());
            //                    labelData += "'" + ObjApp[1].toString() + "', ";
            //                    valueData += ObjApp[0].toString() + ", ";
            //                    out.print("        <div class=\"card-stats-item\">\n");
            //                    out.print("          <div class=\"card-stats-item-count\">" + ObjApp[0] + "</div>\n");
            //                    out.print("          <div class=\"card-stats-item-label\">" + ObjApp[1] + "</div>\n");
            //                    out.print("        </div>\n");
            //                }
            //
            //                out.print("      </div>\n");
            //                out.print("    </div>\n");
            //                out.print("    <div class=\"card-wrap mt-3\">\n");
            //                out.print("<canvas id=\"myChart3\" width=\"400\" height=\"230\"></canvas>");
            //                out.print("<script>");
            //                out.print("var ctx = document.getElementById(\"myChart3\").getContext('2d'); ");
            //                out.print("var myChart = new Chart(ctx, { ");
            //                out.print("  type: 'pie', ");
            //                out.print("  data: { ");
            //                out.print("    labels: [" + labelData + "], ");
            //                out.print("    datasets: [{ ");
            //                out.print("      label: 'Proceso', ");
            //                out.print("      data: [" + valueData + "], ");
            //                out.print("      backgroundColor: [");
            //
            //                String[] colors = {"'#ffcd56'", "'#36a2eb'", "'#ff6384'", "'#0943f1'"};
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderColor: [");
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderWidth: 1.7 }], ");
            //                out.print("}, options: { "
            //                        + "responsive: true,"
            //                        + "    cutout: '60%', "
            //                        + "    legend: { "
            //                        + "      position: 'bottom', "
            //                        + "    }, ");
            //                out.print("}});");
            //                out.print("</script>");
            //                out.print("    </div>\n");
            //                out.print("  </div>\n");
            //                //</editor-fold>
            //                out.print("</div>");
            //            }
            //            lst_schedule = DashJpa.ConsultScheduleMonth(CurrYear, (CurrMonth + 1));
            //            if (lst_schedule != null) {
            //                out.print("<div class=\"col-lg-4\">");
            //                //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES MENSUALES - CRONOGRAMA">
            //                out.print("  <div class=\"card gradient-bottom\">");
            //                out.print("    <div class=\"card-header\">");
            //                out.print("      <h4>Actividad mensual</h4>");
            //                out.print("      <div class=\"card-header-action dropdown\">");
            //                out.print("        <button class=\"btn btn-info \">" + nombreMes + "</button>");
            //                out.print("      </div>");
            //                out.print("    </div>");
            //
            //                out.print("    <div class=\"card-body\" id=\"top-5-scroll\">");
            //                out.print("      <ul class=\"list-unstyled list-unstyled-border\">");
            //
            //                for (int i = 0; i < lst_schedule.size(); i++) {
            //                    Object[] ObjSchedule = (Object[]) lst_schedule.get(i);
            //                    int Type = Integer.parseInt(ObjSchedule[4].toString());
            //                    int State = Integer.parseInt(ObjSchedule[5].toString());
            //                    out.print("        <li class=\"media\">");
            //                    out.print("          <img class=\"mr-3 rounded\" width=\"55\" src='Interface/Imagen/Type" + Type + ".png' alt=\"product\">");
            //                    out.print("          <div class=\"media-body\">");
            //                    out.print("            <div class=\"media-title\">" + ObjSchedule[1] + "</div>");
            //                    out.print("            <div class=\"mt-1\">");
            //                    out.print("<div class=\"float-right\"><div class=\"font-weight-800 text-" + ((State == 0) ? "success" : "primary") + " text-small\">" + ((State == 0) ? "Cerrado" : "En Gestion") + "</div></div>");
            //                    out.print("            </div>");
            //                    out.print("          </div>");
            //                    out.print("        </li>");
            //                }
            //
            //                out.print("      </ul>");
            //                out.print("    </div>");
            //
            //                out.print("    <div class=\"card-footer pt-3 d-flex justify-content-center\">");
            //                out.print("      <div class=\"budget-price justify-content-center\">");
            //                out.print("        <div class=\"budget-price-label\">R-TI-026</div>");
            //                out.print("      </div>");
            //                out.print("    </div>");
            //                out.print("  </div>");
            //                //</editor-fold>
            //                out.print("</div>");
            //            } else {
            //            }
            //            lst_scheduleType1 = DashJpa.ConsultScheduleMonthType(CurrYear,2);
            //            if (lst_scheduleType1 != null) {
            //                String labelData = "", valueData = "";
            //                out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">\n");
            //                //<editor-fold defaultstate="collapsed" desc="TIPO ANUAL">
            //                out.print("  <div class=\"card card-statistic-2\">\n");
            //                out.print("    <div class=\"card-stats\">\n");
            //                out.print("      <div class=\"card-stats-title\"><h6>Actividad Anual</h6> </div>\n");
            //                out.print("      <div class=\"card-stats-items\">\n");
            //                for (int i = 0; i < lst_scheduleType1.size(); i++) {
            //                    Object[] ObjType = (Object[]) lst_scheduleType1.get(i);
            //                    CountP += Integer.parseInt(ObjType[2].toString());
            //                    labelData += "'" + ObjType[1].toString() + "', ";
            //                    valueData += ObjType[2].toString() + ", ";
            //                    out.print("        <div class=\"card-stats-item\">\n");
            //                    out.print("          <div class=\"card-stats-item-count\">" + ObjType[2] + "</div>\n");
            //                    out.print("          <div class=\"card-stats-item-label\">" + ObjType[1] + "</div>\n");
            //                    out.print("        </div>\n");
            //                }
            //
            //                out.print("      </div>\n");
            //                out.print("    </div>\n");
            //                out.print("    <div class=\"card-wrap mt-3\">\n");
            //                out.print("<canvas id=\"myChart4\" width=\"400\" height=\"230\"></canvas>");
            //                out.print("<script>");
            //                out.print("var ctx = document.getElementById(\"myChart4\").getContext('2d'); ");
            //                out.print("var myChart = new Chart(ctx, { ");
            //                out.print("  type: 'radar', ");
            //                out.print("  data: { ");
            //                out.print("    labels: [" + labelData + "], ");
            //                out.print("    datasets: [{ ");
            //                out.print("      label: 'Proceso', ");
            //                out.print("      data: [" + valueData + "], ");
            //                out.print("      backgroundColor: [");
            //
            //                String[] colors = {"'#ffcd56'", "'#36a2eb'", "'#ff6384'", "'#0943f1'"};
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderColor: [");
            //                for (int i = 0; i < lst_pending.size(); i++) {
            //                    out.print(colors[i % colors.length]);
            //                    if (i < lst_pending.size() - 1) {
            //                        out.print(", ");
            //                    }
            //                }
            //
            //                out.print("], borderWidth: 1.7 }], ");
            //                out.print("}, options: { "
            //                        + "responsive: true,"
            //                        + "    cutout: '60%', "
            //                        + "    legend: { "
            //                        + "      position: 'bottom', "
            //                        + "    }, ");
            //                out.print("}});");
            //                out.print("</script>");
            //                out.print("    </div>\n");
            //                out.print("  </div>\n");
            //                //</editor-fold>
            //                out.print("</div>");
            //            }
            //            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="CONTADORES APPTI">
            lst_items = DashJpa.ConsultScheduleFollowItems(CurrYear, (CurrMonth + 1));
            if (lst_items != null) {
                for (int i = 0; i < lst_items.size(); i++) {
                    Object[] ObjItems = (Object[]) lst_items.get(i);
                    out.print("<div class=\"col-lg-3 col-md-6 col-sm-6 col-12\">\n"
                            + "              <div class=\"card card-statistic-1\">\n"
                            + "                <div class=\"card-icon bg-" + ObjItems[3] + "\">\n"
                            + "                  <i class=\"" + ObjItems[2] + "\"></i>\n"
                            + "                </div>\n"
                            + "                <div class=\"card-wrap\">\n"
                            + "                  <div class=\"card-header\">\n"
                            + "                    <h4>" + ObjItems[0] + "</h4>\n"
                            + "                  </div>\n"
                            + "                  <div class=\"card-body\">\n"
                            + "                    " + (Integer.parseInt(ObjItems[1].toString()) == 0 ? "<i class='fas fa-laugh-beam mr-2' style='color:#399733;font-size:18px'></i><b>¡Al día!</b>" : ObjItems[1]) + "\n"
                            + "                  </div>\n"
                            + "                </div>\n"
                            + "              </div>\n"
                            + "            </div>");
                }
            }
            //</editor-fold>
            out.print("</div>"); // Fin container

            out.print("<div class=\"row\">");
            //<editor-fold defaultstate="collapsed" desc="PENDIENTE ANUALES">
            lst_follow = DashJpa.ConsultPendingHistory(CurrYear);
            if (lst_follow != null) {
                String labelData = "", valueData = "";
                for (int i = 0; i < lst_follow.size(); i++) {
                    Object[] ObjType = (Object[]) lst_follow.get(i);
                    CountP += Integer.parseInt(ObjType[2].toString());
                    labelData += "'" + ObjType[1].toString() + "', ";
                    valueData += ObjType[2].toString() + ", ";
                }
                out.print("<div class='col-md-7'>");

                out.print("<div class=\"card\">\n"
                        + "                <div class=\"card-header\">\n"
                        + "                  <h4>Pendientes anuales</h4>\n"
                        + "                </div>");

                out.print("<div class=\"card-body\">");
                out.print("<canvas id=\"myChart1\" width=\"400\" height=\"230\"></canvas>");
                out.print("<script>");
                out.print("var ctx = document.getElementById(\"myChart1\").getContext('2d'); ");
                out.print("var myChart = new Chart(ctx, { ");
                out.print("  type: 'line', ");
                out.print("  data: { ");
                out.print("    labels: [" + labelData + "], ");
                out.print("    datasets: [{ ");
                out.print("      label: 'Mes', ");
                out.print("      data: [" + valueData + "], ");
                out.print("      backgroundColor: ['#ffffff1f'");
                out.print("], borderColor: ['#33bf98'");
                out.print("], borderWidth: 1.7 }], ");
                out.print("}, options: { "
                        + "responsive: true,"
                        + "    cutout: '60%', "
                        + "    legend: { "
                        + "      position: 'bottom', "
                        + "    }, ");
                out.print("}});");
                out.print("</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ACTIVIDADES RECIENTES">
            lst_activity = DashJpa.ConsultActiviryRecent(CurrYear, (CurrMonth));
            if (lst_activity != null) {
                out.print("<div class=\"col-md-5\">");
                out.print("<div class=\"card\">");
                out.print("<div class=\"card-header\">");
                out.print("<h4>Actividades recientes</h4>");
                out.print("</div>");
                out.print("<div class=\"card-body\" style='font-size:11px'>");
                for (int i = 0; i < lst_activity.size(); i++) {
                    Object[] ObjActivity = (Object[]) lst_activity.get(i);
                    String name = ObjActivity[3].toString().replace(" ", "<br>") + "";
                    out.print("<div class=\"activities\">");
                    out.print("  <div class=\"activity\">");
                    lst_module = SettingJpa.ConsultSettingCategorie(ObjActivity[1].toString());
                    if (lst_module != null) {
                        Object[] ObjModule = (Object[]) lst_module.get(0);
                        out.print("    <div class=\"activity-icon " + ObjModule[3] + " text-white shadow-primary\">");
                        out.print(ObjModule[2]);
                    }
                    out.print("    </div>");
                    out.print("    <div class=\"activity-detail\" style='margin-bottom:9px !important;'>");
                    out.print("      <div class=\"mb-2\">");
                    out.print("        <span class=\"text-job text-primary\">" + ObjActivity[3] + "</span>");
                    out.print("      </div>");
                    out.print("      <p>" + ObjActivity[2] + "</p>");
                    out.print("    </div>");
                    out.print("  </div>");
                    out.print("</div>");

                }
            }
            //</editor-fold>
            out.print("</div>");

            out.print("<div class=\"row\">");
            //<editor-fold defaultstate="collapsed" desc="GLPI">
            lst_tickets = ConnectionBd.ConsultarGLPI();
            if (lst_tickets != null) {
                String[] tickets = lst_tickets.toString().split("///");
                String[] data = tickets[0].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                String valueData = data[0] + "," + data[1];
                String labelData = "Tickets Generados,Tickeks Solicitados";
                out.print("<div class='col-md-7'>");
                out.print("<div class=\"card\">\n"
                        + "                <div class=\"card-header\">\n"
                        + "                  <h4>Pendientes anuales</h4>\n"
                        + "                </div>");

                out.print("<div class=\"card-body\">");
                out.print("<canvas id=\"myChart2\" width=\"400\" height=\"230\"></canvas>");
                out.print("<script>");
                out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); ");
                out.print("var myChart = new Chart(ctx, { ");
                out.print("  type: 'line', ");
                out.print("  data: { ");
                out.print("    labels: [" + labelData + "], ");
                out.print("    datasets: [{ ");
                out.print("      label: 'Mes', ");
                out.print("      data: [" + valueData + "], ");
                out.print("      backgroundColor: ['#ffffff1f'");
                out.print("], borderColor: ['#33bf98'");
                out.print("], borderWidth: 1.7 }], ");
                out.print("}, options: { "
                        + "responsive: true,"
                        + "    cutout: '60%', "
                        + "    legend: { "
                        + "      position: 'bottom', "
                        + "    }, ");
                out.print("}});");
                out.print("</script>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_start.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
