package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import controlador.generalControllerJpa;
import java.util.List;

public class Tag_start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        String userSystem = pageContext.getSession().getAttribute("FullName").toString();
        generalControllerJpa GeneralJpa = new generalControllerJpa();
        List lst_general = null;
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<h2>Bienvenido, " + userSystem + "</h2>");
            out.print("</div>");
            out.print("<div class='card-body'>");
            String labelData = "";
            String valueData = "";
            lst_general = GeneralJpa.CounterCaseByUser();
            out.print("<div class='col-lg-12 d-flex'>");
            out.print("<div class='col-lg-6 mr-2 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
            //<editor-fold defaultstate="collapsed" desc="CHARY BY USERS">
            out.print("<h4 class='mb-2 mt-2 text-center'>Casos ejecutados por usuarios</h4>");
            out.print("<div class=''>");
            if (lst_general != null) {
                for (int i = 0; i < lst_general.size(); i++) {
                    Object[] ObjUsr = (Object[]) lst_general.get(i);
                    valueData += ObjUsr[0].toString() + ", ";
                    labelData += "'" + ObjUsr[1].toString() + "', ";
                }
                out.print("<canvas id=\"myChart1\" width=\"400\" height=\"230\"></canvas>");
                out.print("</div>");
                out.print("<script>");
                out.print("var ctx = document.getElementById(\"myChart1\").getContext('2d'); "
                        + "var myChart = new Chart(ctx, { "
                        + "  type: 'doughnut', "
                        + "  data: { "
                        + "    datasets: [{ "
                        + "      data: [ "
                        + "        " + valueData + " "
                        + "      ], "
                        + "      backgroundColor: [ "
                        + "        '#094e37', "
                        + "        '#10825c', "
                        + "        '#15a777', "
                        + "        '#13c88c', "
                        + "        '#13e59f', "
                        + "      ], "
                        + "      borderColor: [ "
                        + "        '#094e37b3', "
                        + "        '#10825cb3', "
                        + "        '#15a777b3', "
                        + "        '#13c88cb3', "
                        + "        '#13e59fb3', "
                        + "      ],"
                        + "      label: 'Dataset 1' "
                        + "    }], "
                        + "    labels: [ "
                        + "      " + labelData + " "
                        + "    ], "
                        + "  }, "
                        + "  options: { "
                        + "    responsive: true,"
                        + "    cutout: '60%', "
                        + "    legend: { "
                        + "      position: 'bottom', "
                        + "    }, "
                        + "  } "
                        + "});");
                out.print("</script>");
            } else {
                out.print("<div class='conGraf'>");
                out.print("No hay suficiente información para generar graficos.<br>");
                out.print("<i style='font-size: 45px;' class=\"fas fa-database\"></i><br>");
                out.print("</div>");
                out.print("</div>");
            }

            //</editor-fold>
            out.print("</div>");
            out.print("<div class='col-lg-6 mr-2 ' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
            //<editor-fold defaultstate="collapsed" desc="CHART BY APP">
            out.print("<h4 class='mb-2 mt-2 text-center'>Casos ejecutados por aplicacion</h4>");
            out.print("<div class=''>");
            valueData = "";
            labelData = "";
            lst_general = GeneralJpa.CounterCaseByApp();
            if (lst_general != null) {
                for (int i = 0; i < lst_general.size(); i++) {
                    Object[] ObjUsr = (Object[]) lst_general.get(i);
                    valueData += ObjUsr[0].toString() + ", ";
                    labelData += "'" + ObjUsr[1].toString() + "', ";
                }
                out.print("<canvas id=\"myChart2\" width=\"400\" height=\"230\"></canvas>");
                out.print("</div>");
                out.print("<script>");
                out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); "
                        + "var myChart = new Chart(ctx, { "
                        + "  type: 'pie', "
                        + "  data: { "
                        + "    datasets: [{ "
                        + "      data: [ "
                        + "        " + valueData + " "
                        + "      ], "
                        + "      backgroundColor: [ "
                        + "        '#1b435d', "
                        + "        '#255c80', "
                        + "        '#2f76a4', "
                        + "        '#3189c2', "
                        + "        '#3ba1e3', "
                        + "      ], "
                        + "      borderColor: [ "
                        + "        '#1b435d94', "
                        + "        '#255c8094', "
                        + "        '#2f76a494', "
                        + "        '#3189c294', "
                        + "        '#3ba1e394', "
                        + "      ],"
                        + "      label: 'Dataset 1' "
                        + "    }], "
                        + "    labels: [ "
                        + "      " + labelData + " "
                        + "    ], "
                        + "  }, "
                        + "  options: { "
                        + "    responsive: true,"
                        + "    cutout: '60%', "
                        + "    legend: { "
                        + "      position: 'bottom', "
                        + "    }, "
                        + "  } "
                        + "});");
                out.print("</script>");
            } else {
                out.print("<div class='conGraf'>");
                out.print("No hay suficiente información para generar graficos.<br>");
                out.print("<i style='font-size: 45px;' class=\"fas fa-database\"></i><br>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("<div class='col-lg-12 mt-2 d-flex'>");
            out.print("<div class='col-lg-6 mr-2 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
            //<editor-fold defaultstate="collapsed" desc="CHART BY MONTH">
            out.print("<h4 class='mt-2 mt-2 text-center'>Casos ejecutados por mes</h4>");
            out.print("<div class=''>");
            valueData = "";
            labelData = "";
            lst_general = GeneralJpa.CounterCaseByMonth();
            if (lst_general != null) {
                for (int i = 0; i < lst_general.size(); i++) {
                    Object[] ObjUsr = (Object[]) lst_general.get(i);
                    valueData += ObjUsr[0].toString() + ", ";
                    labelData += "'" + ObjUsr[1].toString().toUpperCase() + "', ";
                }
                out.print("<canvas id=\"myChart3\" width=\"400\" height=\"230\"></canvas>");
                out.print("</div>");
                out.print("<script>");
                out.print("var ctx = document.getElementById(\"myChart3\").getContext('2d'); ");
                out.print("var myChart = new Chart(ctx, { ");
                out.print("  type: 'bar', ");
                out.print("  data: { ");
                out.print("    labels: [" + labelData + "], ");
                out.print("    datasets: [{ ");
                out.print("      label: 'Tickets por soporte', ");
                out.print("      data: [" + valueData + "], ");
                out.print("      backgroundColor: [");

                String[] colors = {"'#641e16'", "'#78281f'", "'#7b241c'", "'#943126'", "'#922b21'", "'#b03a2e'", "'#a93226'", "'#cb4335'", "'#c0392b'", "'#e74c3c'", "'#cd6155'", "'#ec7063'"};
                for (int i = 0; i < lst_general.size(); i++) {
                    out.print(colors[i % colors.length]);
                    if (i < lst_general.size() - 1) {
                        out.print(", ");
                    }
                }

                out.print("], borderColor: [");
                for (int i = 0; i < lst_general.size(); i++) {
                    out.print(colors[i % colors.length]);
                    if (i < lst_general.size() - 1) {
                        out.print(", ");
                    }
                }

                out.print("], borderWidth: 1.7 }], ");
                out.print("}, options: { ");
                out.print("    legend: { display: true, position: 'bottom', labels: { fontColor: '#333', fontSize: 12 } }, ");
                out.print("    scales: { ");
                out.print("        yAxes: [{ gridLines: { drawBorder: false, color: '#f2f2f2' }, ticks: { beginAtZero: true, stepSize: 10 } }], ");
                out.print("        xAxes: [{ ticks: { display: false }, gridLines: { display: false } }] ");
                out.print("    } ");
                out.print("}});");
                out.print("</script>");
            } else {
                out.print("<div class='conGraf'>");
                out.print("No hay suficiente información para generar graficos.<br>");
                out.print("<i style='font-size: 45px;' class=\"fas fa-database\"></i><br>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            out.print("</div>");
            out.print("<div class='col-lg-6 mr-2 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
            //<editor-fold defaultstate="collapsed" desc="CHART BY TYPE">
            out.print("<h4 class='mt-2 mt-2 text-center'>Casos ejecutados por tipo</h4>");
            out.print("<div class=''>");
            valueData = "";
            labelData = "";
            lst_general = GeneralJpa.CounterCaseByMode();
            if (lst_general != null) {
                for (int i = 0; i < lst_general.size(); i++) {
                    Object[] ObjUsr = (Object[]) lst_general.get(i);
                    valueData += ObjUsr[0].toString() + ", ";
                    labelData += "'" + ObjUsr[1].toString().toUpperCase() + "', ";
                }

                labelData = labelData.substring(0, labelData.length() - 2);
                valueData = valueData.substring(0, valueData.length() - 2);

                out.print("<canvas id=\"myChart4\" width=\"400\" height=\"230\"></canvas>");
                out.print("</div>");
                out.print("<script>");
                out.print("var ctx = document.getElementById(\"myChart4\").getContext('2d'); ");
                out.print("var myChart = new Chart(ctx, { ");
                out.print("  type: 'bar', ");
                out.print("  data: { ");
                out.print("    labels: [" + labelData + "], ");
                out.print("    datasets: [{ ");
                out.print("      label: 'Tickets cerrados por personal', ");
                out.print("      data: [" + valueData + "], ");
                out.print("      backgroundColor: [");

                String[] colors = {"'#542b68'", "'#654176'", "'#6f2b90'", "'#844c9f'", "'#902fbf'", "'#b457e1'"};
                for (int i = 0; i < lst_general.size(); i++) {
                    out.print(colors[i % colors.length]);
                    if (i < lst_general.size() - 1) {
                        out.print(", ");
                    }
                }

                out.print("], borderColor: [");
                for (int i = 0; i < lst_general.size(); i++) {
                    out.print(colors[i % colors.length]);
                    if (i < lst_general.size() - 1) {
                        out.print(", ");
                    }
                }

                out.print("], borderWidth: 1.7 }], ");
                out.print("}, options: { ");
                out.print("    legend: { display: true, position: 'bottom', labels: { fontColor: '#333', fontSize: 12 } }, ");
                out.print("    scales: { ");
                out.print("        yAxes: [{ gridLines: { drawBorder: false, color: '#f2f2f2' }, ticks: { beginAtZero: true, stepSize: 10 } }], ");
                out.print("        xAxes: [{ ticks: { display: false }, gridLines: { display: false } }] ");
                out.print("    } ");
                out.print("}});");
                out.print("</script>");
            } else {
                out.print("<div class='conGraf'>");
                out.print("No hay suficiente información para generar graficos.<br>");
                out.print("<i style='font-size: 45px;' class=\"fas fa-database\"></i><br>");
                out.print("</div>");
                out.print("</div>");
            }
//</editor-fold>
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception e) {
        }

        return super.doStartTag();
    }
}
