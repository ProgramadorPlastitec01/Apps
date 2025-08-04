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
            out.print("<h1 class='text-center'>Inicio </h1>");
            out.print("</div>");

            out.print("<div class='container mt-4'>");

            out.print("<div class='row g-4'>"); // g-4 agrega espacio entre columnas/fila
            
            //<editor-fold defaultstate="collapsed" desc="CONTADORES APPTI">
            lst_items = DashJpa.ConsultScheduleFollowItems(CurrYear, (CurrMonth + 1));
            if (lst_items != null) {
                for (int i = 0; i < lst_items.size(); i++) {
                    Object[] ObjItems = (Object[]) lst_items.get(i);
                    out.print("<div id='Card_" + ObjItems[4] + "' class=\"col-lg-3 col-md-6 col-sm-6 col-12\" style='display:none' >"
                            + "              <div class=\"card card-statistic-1\">"
                            + "                <div class=\"card-icon bg-" + ObjItems[3] + "\">"
                            + "                  <i class=\"" + ObjItems[2] + "\"></i>"
                            + "                </div>"
                            + "                <div class=\"card-wrap\">"
                            + "                  <div class=\"card-header\">"
                            + "                    <h4>" + ObjItems[0] + "</h4>"
                            + "                  </div>"
                            + "                  <div class=\"card-body\">"
                            + "                    " + (Integer.parseInt(ObjItems[1].toString()) == 0 ? "<i class='fas fa-laugh-beam mr-2' style='color:#399733;font-size:18px'></i><b>¡Al día!</b>" : ObjItems[1]) + ""
                            + "                  </div>"
                            + "                </div>"
                            + "              </div>"
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

                out.print("<div class=\"card\" id='Card_9' style='display:none'>"
                        + "                <div class=\"card-header\">"
                        + "                  <h4>Pendientes anuales</h4>"
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
                out.print("<div class=\"card\" id='Card_10' style='display:none'>");
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

            out.print("</div>");

            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="CONTADOR - HABILITAR MODAL">
            out.print("<div class='setting_toggle' id=\"customize-toggle\" onclick=\"toggleCustomizer()\">");
            out.print("<small class=\"text-uppercase text-primary fw-bold bg-primary-subtle py-2 pe-2 ps-1 rounded-end\">Contador <i class='fas fa-cog fa-spin'></i></small>");
            out.print("</div>");

            out.print("<div id='modPanel' class='mod-panel'>");

            out.print("  <div class='d-flex justify-content-between align-items-center px-3 pt-3'>");
            out.print("    <h5 class='mb-0'><i class='fas fa-chart-bar'></i> Contadores</h5>");
            out.print("    <button class='btn btn-link text-dark p-0' onclick=\"toggleCustomizer()\"><i class='fas fa-times'></i></button>");
            out.print("  </div>");

            out.print("  <div class='row p-3'>");

            String[] ArgModule = {
                "Pendientes", "Bitacora", "Aplicativo en gestión", "Actas sin firmas",
                "Actividad mensuales", "PC en gestión", "Equipos en gestión", "Programaciones pendientes",
                "Pendientes Anuales", "Actividad Reciente"
            };
            String[] ArgIcon = {
                "fa-bell", "fa-folder-open", "fa-lightbulb", "fa-file-alt",
                "fa-calendar", "fa-laptop", "fa-tablet", "fa-clipboard-check",
                "fa-list", "fa-comments"
            };
            String[] DivOpenClose = {
                "Card_1", "Card_2", "Card_3", "Card_4",
                "Card_5", "Card_6", "Card_7", "Card_8",
                "Card_9", "Card_10"
            };

            for (int i = 0; i < ArgModule.length; i++) {
                out.print("<div class='col-4 text-center mb-3'>");
                out.print("<div class='mod-icon-card' onclick=\"selectModule(this, '" + DivOpenClose[i] + "'); toggleSection('" + DivOpenClose[i] + "')\">");
                out.print("<i class='fas " + ArgIcon[i] + "'></i><br>");
                out.print("<small>" + ArgModule[i] + "</small>");
                out.print("</div>");
                out.print("</div>");
            }
            
            out.print("</div>"); // cierre row
            
            out.print("<form id='miFormulario' action='ControladorServlet' method='POST'>");
            out.print("<input type=\"hidden\" id=\"modSelectedInput\" name=\"modSelectedInput\" value=\"\">");
            out.print("</form>");
            
            out.print("<div class='text-center'>");
            out.print("<button class='btn btn-green' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top'><i class=\"fas fa-save\"></i>Guardar</button>");
            out.print("</div>");
            out.print("</div>"); // cierre panel
            //</editor-fold>
            
            out.print("</div>");

            out.print("</div>");

            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_start.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
