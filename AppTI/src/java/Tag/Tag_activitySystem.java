package Tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import Controller.ActivitySystemControllerJpa;

public class Tag_activitySystem extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession sesion = pageContext.getSession();
        JspWriter out = pageContext.getOut();
        ActivitySystemControllerJpa ActivityJpa = new ActivitySystemControllerJpa();
        Calendar cal = Calendar.getInstance();
        int CurrYear = cal.get(Calendar.YEAR);
        int CurrMonth = (cal.get(Calendar.MONTH));
        String[] meses = new DateFormatSymbols(new Locale("es", "ES")).getMonths();
        String nombreMes = meses[CurrMonth];
        int idUser = Integer.parseInt(sesion.getAttribute("idUsuario").toString());
        List lst_activitySys = null, lst_group = null, lst_filter = null;
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1 class='text-center'>Mis Actividades</h1>");
            out.print("</div>");

            out.print("<div class=\"section-body\">");

            out.print("<div class='d-flex justify-content-between'>");
            out.print("<h2 class=\"section-title marginTilte\"><span style='text-transform: capitalize;'>" + nombreMes + "</span> " + CurrYear + "</h2>");

            out.print("<div>");
            out.print("<select class='form-control select2'>");
            try {
                lst_filter = ActivityJpa.ConsultActivityFilter(idUser);
            } catch (Exception e) {
                lst_filter = null;
            }
            if (lst_filter != null) {
                for (int i = 0; i < lst_filter.size(); i++) {
                    Object[] ObjFilter = (Object[]) lst_filter.get(i);
                    int YearFilter = Integer.parseInt(ObjFilter[0].toString());
                    int MonthFilter = Integer.parseInt(ObjFilter[1].toString());
                    String MonthTrasform = ObjFilter[2].toString().toUpperCase();
                    if (YearFilter == CurrYear && MonthFilter == CurrMonth) {
                        out.print("<option selected style='text-transform: capitalize;'>" + ObjFilter[0] + " | " + MonthTrasform + " | " + ObjFilter[3] + "</option>");
                    } else {
                        out.print("<option style='text-transform: capitalize;'>" + ObjFilter[0] + " | " + MonthTrasform + " | " + ObjFilter[3] + "</option>");
                    }
                }
            }
            out.print("</select>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class=\"row\">");

            out.print("<div class=\"col-5 StlDiv\">");
            out.print("<div class=\"activities\">");
            //<editor-fold defaultstate="collapsed" desc="ACTIVITIES">

            lst_activitySys = ActivityJpa.ConsultActivitySystem(idUser, CurrYear, (CurrMonth + 1));
            if (lst_activitySys != null) {
                for (int i = 0; i < lst_activitySys.size(); i++) {
                    Object[] ObjActivity = (Object[]) lst_activitySys.get(i);
                    out.print("<div class=\"activity\">\n"
                            + "                    <div class=\"activity-icon " + ObjActivity[10] + " text-white shadow-primary\">\n"
                            + "                      " + ObjActivity[9] + "\n"
                            + "                    </div>\n"
                            + "                    <div class=\"activity-detail\">\n"
                            + "                      <div class=\"mb-2\">\n"
                            + "                        <span class=\"text-job text-primary\">" + ObjActivity[4] + "</span>\n"
                            + "                        <span class=\"bullet\"></span>\n"
                            + "                        <span class=\"text-job text-warning\">" + ObjActivity[11] + "</span>\n"
                            + "                      </div>\n"
                            + "                      <p>" + ObjActivity[5] + "</p>\n"
                            + "                    </div>\n"
                            + "                  </div>");
                }
            } else {
                out.print("<h6>Sin actividades del mes<i class=''></i></h6>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");

            out.print("<div class=\"col-7\">");
            lst_group = ActivityJpa.ConsultActivityGroupUser(idUser, CurrYear, (CurrMonth + 1));
            if (lst_group != null) {
                StringBuilder labels = new StringBuilder();
                StringBuilder values = new StringBuilder();
                StringBuilder classes = new StringBuilder();

                out.print("<div class=\"card card-statistic-2\">");
                out.print("  <div class=\"card-stats\" style='margin-bottom:0px'>");
                out.print("    <div class=\"card-stats-title row\"><h6>Actividades</h6></div>");
                out.print("    <div class=\"row card-stats-items\" style='height:auto'>");

                for (int i = 0; i < lst_group.size(); i++) {
                    Object[] ObjGroup = (Object[]) lst_group.get(i);

                    labels.append("'").append(ObjGroup[1].toString()).append("',");
                    values.append(ObjGroup[0].toString()).append(",");
                    classes.append("'").append(ObjGroup[2].toString()).append("',");

                    out.print("      <div class=\"col-6 col-md-4 col-lg-3 card-stats-item\">");
                    out.print("        <div class=\"card-stats-item-count\">" + ObjGroup[0] + "</div>");
                    out.print("        <div class=\"card-stats-item-label\">" + ObjGroup[1] + "</div>");
                    out.print("      </div>");
                }

                out.print("    </div>");
                out.print("  </div>");
                out.print("  <div class=\"card-wrap mt-3\" st>");
                out.print("    <canvas id=\"myChart1\" class='mb-2' width=\"400\" height=\"230\"></canvas>");
                out.print("    <script>");
                out.print("      function getColorFromClass(className) {");
                out.print("        return getComputedStyle(document.querySelector('.' + className)).backgroundColor;");
                out.print("      }");

                // Array de clases que vienen desde el servidor
                out.print("      var classNames = [" + classes.substring(0, classes.length() - 1) + "];");

                // Construimos los colores dinámicamente
                out.print("      var chartColors = []; ");
                out.print("      for (var i = 0; i < classNames.length; i++) {");
                out.print("        chartColors.push(getColorFromClass(classNames[i]));");
                out.print("      }");

                // Chart.js
                out.print("      var ctx = document.getElementById('myChart1').getContext('2d');");
                out.print("      new Chart(ctx, {");
                out.print("        type: 'doughnut',");
                out.print("        data: {");
                out.print("          labels: [" + labels.substring(0, labels.length() - 1) + "],");
                out.print("          datasets: [{");
                out.print("            label: 'Proceso',");
                out.print("            data: [" + values.substring(0, values.length() - 1) + "],");
                out.print("            backgroundColor: chartColors,");
                out.print("            borderColor: chartColors,");
                out.print("            borderWidth: 1.7");
                out.print("          }]"); // datasets
                out.print("        },");
                out.print("        options: { responsive: true, cutout: '60%', plugins: { legend: { position: 'bottom' } } }");
                out.print("      });");
                out.print("    </script>");
                out.print("  </div>");
                out.print("</div>");
            }

            out.print("</div>");

            out.print("</div>");

            out.print("</div>");

            out.print("</section>");
        } catch (IOException e) {
        }
        return super.doStartTag();
    }
}
