package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import Controller.DashBoardJpaController;
import Controller.SettingControllerJpa;
import Controller.UserControllerJpa;
import Controller.PendingControllerJpa;
import Controller.ShiftControllerJpa;
import Controller.AppDetailControllerJpa;

import SQL.ConnectionsBd;

public class Tag_start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        DashBoardJpaController DashJpa = new DashBoardJpaController();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        PendingControllerJpa PedingJpa = new PendingControllerJpa();
        ShiftControllerJpa ShiftJpa = new ShiftControllerJpa();
        ConnectionsBd ConnectionBd = new ConnectionsBd();
        UserControllerJpa UserJpa = new UserControllerJpa();
        AppDetailControllerJpa AppDetailJpa = new AppDetailControllerJpa();

        int IdUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        String NameUser = sesion.getAttribute("Nombres").toString();
        String NameRol = sesion.getAttribute("NombreRol").toString();
        int CheckPending = Integer.parseInt(sesion.getAttribute("CheckPending").toString());
        Calendar cal = Calendar.getInstance();
        int CurrYear = cal.get(Calendar.YEAR);
        int CurrMonth = (cal.get(Calendar.MONTH));
//        nombreMes = nombreMes.substring(0, 1).toUpperCase() + nombreMes.substring(1).toLowerCase();
        List lst_items = null, lst_follow = null, lst_activity = null, lst_module = null, lst_tickets = null, lst_user = null, lst_pending = null,
                lst_shift = null, lst_appDetail = null;
        int CountP = 0;
        String Module = "";

        try {
            lst_user = UserJpa.ConsultUsersid(IdUser);
            if (lst_user != null) {
                Object[] ObjUser = (Object[]) lst_user.get(0);
                try {
                    Module = ObjUser[13].toString();
                } catch (Exception e) {
                    Module = "";
                }
            }
            out.print("<section class='section'>");

            out.print("<div class='section-header'>");
            out.print("<h1 class='text-center'>Inicio </h1>");
            out.print("</div>");

            if (CheckPending > 0) {
                //<editor-fold defaultstate="collapsed" desc="ALERT PENDING">
                lst_pending = PedingJpa.ConsultPendingAlert(NameRol, NameUser);
                if (lst_pending != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<div class='container-fluid' style='max-height:320px; overflow-y:auto;'>");

                    sb.append("<div class='card shadow-sm' style='border-radius:12px; overflow:hidden;'>");

                    sb.append("<div class='card-body p-0'>");
                    sb.append("<table class='table mb-0 table-sm text-center align-middle'>");
                    sb.append("<thead style='background-color:#f9fafb;'>");
                    sb.append("<tr>");
                    sb.append("<th>Rol</th>");
                    sb.append("<th><span class='text-danger'>Vencidos</span></th>");
                    sb.append("<th><span class='text-warning'>Por vencer hoy</span></th>");
                    sb.append("<th><span class='text-success'>Vigentes</span></th>");
                    sb.append("<th><span class='text-primary'>Total</span></th>");
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    for (int i = 0; i < lst_pending.size(); i++) {
                        Object[] ObjCount = (Object[]) lst_pending.get(i);
                        sb.append("<tr>");
                        sb.append("<td style='font-weight:600;'>").append(ObjCount[0]).append("</td>"); // Rol
                        sb.append("<td><span class='badge bg-danger fs-5 px-3 text-white'>").append(ObjCount[1]).append("</span></td>"); // Vencidos
                        sb.append("<td><span class='badge bg-warning fs-5 px-3 text-white'>").append(ObjCount[2]).append("</span></td>"); // Por vencer hoy
                        sb.append("<td><span class='badge bg-success fs-5 px-3 text-white'>").append(ObjCount[3]).append("</span></td>"); // Sin vencimiento
                        sb.append("<td><span class='badge bg-primary fs-5 px-3 text-white'>").append(ObjCount[4]).append("</span></td>"); // Total
                        sb.append("</tr>");
                    }
                    sb.append("</tbody>");

                    sb.append("</table>");
                    sb.append("</div>"); // cierre card-body
                    sb.append("</div>"); // cierre card
                    sb.append("</div>"); // cierre container-fluid

                    String htmlContent = sb.toString().replace("\"", "\\\"");

                    out.print("<div>");
                    out.print("<script>");
                    out.print("$(document).ready(function () {");
                    out.print("  var wrapper = document.createElement('div');");
                    out.print("  wrapper.innerHTML = \"" + htmlContent + "\";");
                    out.print("  swal({");
                    out.print("    title: 'Pendientes',");
                    out.print("    content: wrapper,");
                    out.print("    icon: 'warning',");
                    out.print("    buttons: false,");
                    out.print("    className: 'custom-swal-width'");  // clase personalizada
                    out.print("  }).then(function(){");
                    out.print("    $.post('Session?opt=6', function(resp){ console.log('CheckPending reiniciado a 0'); });");
                    out.print("  });");
                    out.print("});");
                    out.print("</script>");

                    out.print("</div>");
                }
                //</editor-fold>
            }

            out.print("<div class='container mt-4'>");

            out.print("<div class=\"row d-flex flex-wrap\">");
            //<editor-fold defaultstate="collapsed" desc="CONTADORES APPTI">
            lst_items = DashJpa.ConsultScheduleFollowItems(CurrYear, (CurrMonth + 1), NameRol, NameUser);
            if (lst_items != null) {
                for (int i = 0; i < lst_items.size(); i++) {
                    Object[] ObjItems = (Object[]) lst_items.get(i);
                    out.print("<div id='" + ObjItems[4] + "' class=\"col-lg-3 col-md-6 col-sm-6 col-12\" style='display:" + (Module.contains(ObjItems[4].toString()) ? "block" : "none") + "' >"
                            + "              <div class=\"card card-statistic-1\">"
                            + "                <div class=\"card-icon bg-" + ObjItems[3] + "\">"
                            + "                  <i class=\"" + ObjItems[2] + "\"></i>"
                            + "                </div>"
                            + "                <div class=\"card-wrap\">"
                            + "                  <div class=\"card-header\">"
                            + "                    <h4>" + ObjItems[0] + "</h4>"
                            + "                  </div>"
                            + "                  <div class=\"card-body\">"
                            + "                    " + (Integer.parseInt(ObjItems[1].toString()) == 0 ? "0" : ObjItems[1]) + ""
                            + "                  </div>"
                            + "                </div>"
                            + "              </div>"
                            + "            </div>");
                }
            }
            //</editor-fold>
            out.print("</div>");
            out.print("<div class='row d-flex flex-wrap g-4'>");
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
                out.print("<div class='col-lg-6'>");

                out.print("<div class=\"card\" id='I' style='display:" + (Module.contains("I") ? "block" : "none") + "'>"
                        + "                <div class=\"card-header\">"
                        + "                  <h4>Pendientes anuales</h4>"
                        + "                </div>");

                out.print("<div class=\"card-body\">");
                out.print("<canvas id=\"myChart1\" width=\"400\" height=\"auto\"></canvas>");
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
            lst_activity = DashJpa.ConsultActiviryRecent(CurrYear, (CurrMonth + 1));
            if (lst_activity != null) {
                out.print("<div class=\"col-lg-6\">");
                out.print("<div class=\"card\" id='J' style='display:" + (Module.contains("J") ? "block" : "none") + "'>");

                out.print("<div class=\"card-header\">");
                out.print("<h4>Actividades recientes</h4>");
                out.print("</div>");

                out.print("<div class=\"card-body scrollActivities\" style='font-size:11px'>");
                out.print("<div class=\" activities\" >");
                for (int i = 0; i < lst_activity.size(); i++) {
                    Object[] ObjActivity = (Object[]) lst_activity.get(i);
                    String name = ObjActivity[3].toString().replace(" ", "<br>") + "";
                    out.print("  <div class=\"activity\">");
                    lst_module = SettingJpa.ConsultSettingCategorie(ObjActivity[1].toString());
                    if (lst_module != null) {
                        Object[] ObjModule = (Object[]) lst_module.get(0);
                        out.print("    <div class=\"activity-icon " + ObjModule[3] + " text-white shadow-primary\">");
                        out.print(ObjModule[2]);
                        out.print("    </div>");
                    }
                    out.print("    <div class=\"activity-detail\" style='margin-bottom:9px !important;'>");

                    out.print("  <div class='d-flex justify-content-between mb-2'>");
                    out.print("    <span class='text-job text-primary'>" + ObjActivity[1] + "</span>");
                    out.print("    <span class='text-job text-warning'>" + ObjActivity[4] + "</span>");
                    out.print("  </div>");
                    out.print("        <span class=\"text-job text-primary\">" + ObjActivity[3] + "</span>");
                    out.print("      <div class='d-flex align-items-baseline'><span class=\"bullet \"></span><p>" + ObjActivity[2] + "</p></div>");
                    out.print("    </div>");

                    out.print("  </div>");

                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="PROGRAMACION DE TURNO">
            out.print("<div class=\"col-lg-12\">");
            out.print("<div class=\"card\" id='N' style='display:" + (Module.contains("N") ? "block" : "none") + "'>");
            out.print("<table class='table table-sm' id='table-1'>");
            out.print("<tr style='background: #33bf98;color: black; text-align: center;'>");
            out.print("<th>TURNO PROGRAMADO</th>");
            out.print("<th>PERSONAL PROGRAMADO</th>");
            out.print("</tr>");
            lst_shift = ShiftJpa.ConsultcurrentShift();
            if (lst_shift != null) {
                for (int i = 0; i < lst_shift.size(); i++) {
                    Object[] ObjShift = (Object[]) lst_shift.get(i);
                    String[] DataShift = ObjShift[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int j = 0; j < DataShift.length; j++) {
                        String tittle = DataShift[j].replace("am/", "am///").replace("pm/", "pm///").split("///")[0];
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
                }
            }
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="DOCUMENTOS EN PROCESO">

            out.print("<div class=\"col-lg-6\" style='box-shadow: 0px 1px 13px -1px #afafaf; padding: 15px; border-radius: 5px;'>");
            out.print("<div class=\"card\" id='O' style='display:" + (Module.contains("O") ? "block" : "none") + "'>");
            
            out.print("<div class='text-center'>");
            out.print("<h5>APLICACIONES EN PROCESO</h5>");
            out.print("</div>");
            
            lst_appDetail = AppDetailJpa.ConsultAppDocProcess();
            if (lst_appDetail != null) {
                out.print("<div id='accordion'>");
                for (int i = 0; i < lst_appDetail.size(); i++) {
                    Object[] ObjApp = (Object[]) lst_appDetail.get(i);
                    String[] stuc = ObjApp[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    out.print("<div class='accordion'>");
                    out.print("<div class='accordion-header' role='button' data-toggle='collapse' data-target='#panel-body-" + i + "'>");
                    out.print("<h4><b>" + ObjApp[2] + "</b> - " + stuc[0].toUpperCase() + " </h4>");
                    out.print("</div>");
                    out.print("<div class='accordion-body collapse' id='panel-body-" + i + "' data-parent='#accordion'>");
                    out.print("<table class='table table-sm' >");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Documento</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Acceder</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    int idHead = Integer.parseInt(ObjApp[0].toString());
                    int idApp =  Integer.parseInt(ObjApp[1].toString());
                    List lst_appDetailx = AppDetailJpa.ConsultAppProcess(idHead);
                    if (lst_appDetailx != null) {
                        for (int j = 0; j < lst_appDetailx.size(); j++) {
                            Object[] ObjDet = (Object[]) lst_appDetailx.get(j);
                            out.print("<tr>");
                            out.print("<td>" + ObjDet[2].toString() + "</td>");
                            out.print("<td>" + ObjDet[3].toString() + "</td>");
                            out.print("<td class='text-center'><button class='btn btn-green btn-sm' onclick='window.location.href=\"AppDetail?opt=1&idApp=" + idApp + "&mod=2&idHead=" + idHead + "\"'><i class='fas fa-share'></i></button></td>");
                            out.print("</tr>");
                        }
                    } else {
                        out.print("<tr>");
                        out.print("<td class='text-center' colspan='3'> Documento en proceso <br> <button class='btn btn-info btn-sm' onclick='window.location.href=\"AppDetail?opt=1&idApp=" + idApp + "&mod=2&idHead=" + idHead + "\"'><i class='fas fa-share'></i></button> </td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                    out.print("</div>");
                }
                out.print("</div>");
            }

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>

            out.print("</div>");

            out.print("</div>");

            out.print("</section>");

            //<editor-fold defaultstate="collapsed" desc="CONTADOR - HABILITAR MODAL">
            out.print("<div class='setting_toggle' id=\"customize-toggle\" onclick=\"toggleCustomizer()\">");
            out.print("<small class=\"text-uppercase text-primary fw-bold bg-primary-subtle py-2 pe-2 ps-1 rounded-end\">Contador <i class='fas fa-cog fa-spin'></i></small>");
            out.print("</div>");

            out.print("<div id='modPanel' class='mod-panel'>");

            out.print("  <div class='d-flex justify-content-between align-items-center px-3 pt-3'>");
            out.print("    <h5 class='mb-0'><i class='fas fa-chart-bar'></i> Contadores</h5>");
            out.print("    <button class='btn btn-link text-dark p-0' onclick=\"toggleCustomizer()\"><i class='fas fa-times'></i></button>");
            out.print("  </div>");

            out.print("  <div class='row p-3 scrollRowDiv'>");

            String[] ArgModule = {
                "Total Pendientes", "Bitacora", "Aplicativo en gestión", "Actas sin firmas",
                "Actividad mensuales", "PC en gestión", "Equipos en gestión", "Programaciones pendientes",
                "Pendientes vencidos", "Pendientes por vencer", "Pendientes vigentes",
                "Pendientes Anuales", "Actividad Reciente", "Programacion de turno", "App en proceso"
            };
            String[] ArgIcon = {
                "fa-bell", "fa-folder-open", "fa-lightbulb", "fa-file-alt",
                "fa-calendar", "fa-laptop", "fa-tablet", "fa-clipboard-check",
                "fa-exclamation", "fa-hourglass-half", "fa-check",
                "fa-list", "fa-comments", "fa-user-clock", "fas fa-rocket"
            };
            String[] DivOpenClose = {
                "A", "B", "C", "D",
                "E", "F", "G", "H",
                "K", "L", "M", "I",
                "J", "N", "O"
            };

            for (int i = 0; i < ArgModule.length; i++) {
                out.print("<div class='col-4 text-center mb-3'>");
                out.print("<div class='mod-icon-card " + (Module.contains(DivOpenClose[i]) ? "active" : "") + "' onclick=\"selectModule(this, '" + DivOpenClose[i] + "'); toggleSection('" + DivOpenClose[i] + "');reorganizeCards()\">");
                out.print("<i class='fas " + ArgIcon[i] + "'></i><br>");
                out.print("<small>" + ArgModule[i] + "</small>");
                out.print("</div>");
                out.print("</div>");
            }

            out.print("<form id='MyForm' action='Start?opt=2' method='POST'>");
            out.print("<input type=\"hidden\" id=\"modSelectedInput\" name='Module' value='" + Module + "'>");
            out.print("</form>");

            out.print("<button class='btn btn-green SaveButtom' data-toggle='tooltip' data-placement='top' onclick='SendForm()'><i class=\"fas fa-save\"></i>Guardar</button>");
            out.print("</div>"); // cierre row

            out.print("</div>"); // cierre panel
            //</editor-fold>

        } catch (IOException ex) {
            Logger.getLogger(Tag_start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_start.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
