package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.StartJpaController;
import java.util.List;

public class Start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String NameUser = session.getAttribute("Nombres").toString();
        StartJpaController StartJpa = new StartJpaController();
        JspWriter out = pageContext.getOut();
        List lst_amountC = null;
        List lst_ranking = null;
        List lst_ultimate = null;
        List lst_statistics = null;
        List lst_accountants = null;
        try {
            out.print("  <section class=\"section\">");

            out.print("    <div class=\"section-header\">");
            out.print("      <h1>Dashboard</h1>");
            out.print("    </div>");

            out.print("    <div class=\"row\">");
            //<editor-fold defaultstate="collapsed" desc="DIV CONTADORES CERTIFICADOS">
            lst_amountC = StartJpa.ConsultAmountCertificates();
            if (lst_amountC != null) {
                for (int i = 0; i < lst_amountC.size(); i++) {
                    Object[] ObjAmountC = (Object[]) lst_amountC.get(i);
                    out.print("      <div class=\"col-lg-3 col-md-6 col-sm-6 col-12 \"   >");
                    out.print("        <div class=\"card card-statistic-1 " + (ObjAmountC[5].equals("#") ? "" : "ClicButtom") + " \" onclick='window.location.href=\"" + ObjAmountC[5] + "\";cargarDatos()'>");
                    out.print("          <div class=\"card-icon bg-" + ObjAmountC[1] + "\">");
                    out.print("            <i class=\"" + ObjAmountC[2] + "\"></i>");
                    out.print("          </div>");
                    out.print("          <div class=\"card-wrap\">");
                    out.print("            <div class=\"card-header\"><h4>" + ObjAmountC[3] + "</h4></div>");
                    if (ObjAmountC[4] != null) {
                        int Amount = Integer.parseInt(ObjAmountC[4].toString());
                        out.print("            <div class=\"card-body\">" + ((Amount == 0) ? "<span class='text-small'>Sin datos</span>" : ObjAmountC[4]) + "</div>");
                    }
                    out.print("          </div>");
                    out.print("        </div>");
                    out.print("      </div>");
                }
            }
            //</editor-fold>
            out.print("</div>");

            out.print("<div class=\"row\">");
            //<editor-fold defaultstate="collapsed" desc="TABLA CON CLIENTES">
            lst_ranking = StartJpa.ConsultRankingCustomer();
            if (lst_ranking != null) {
                out.print("  <div class=\"col-md-8\">");
                out.print("    <div class=\"card\">");

                out.print("      <div class=\"card-header\">");
                out.print("         <h4>Clientes líderes en certificados gestionados (Top 5)</h4>");
                out.print("        <div class=\"card-header-action\">");
                out.print("          <a href='Client?opt=1&Client=' class=\"btn btn-danger\">Ver mas <i class=\"fas fa-chevron-right\"></i></a>");
                out.print("        </div>");
                out.print("      </div>");

                out.print("      <div class=\"card-body p-0\">");
                out.print("        <div class=\"table-responsive table-invoice\">");
                out.print("          <table class=\"table table-striped\">");

                out.print("            <tr>");
                out.print("              <th>Cliente</th>");
                out.print("              <th>Cantidad</th>");
                out.print("              <th>Ultimo generado</th>");
                out.print("              <th>Ultimo CC</th>");
                out.print("              <th>Action</th>");
                out.print("            </tr>");

                for (int i = 0; i < lst_ranking.size(); i++) {
                    Object[] ObjRanking = (Object[]) lst_ranking.get(i);
                    out.print("            <tr>");
                    out.print("              <td><b class='text-dark'>" + ObjRanking[0] + "</b></td>");
                    out.print("              <td class=\"font-weight-600\">" + ObjRanking[1] + "</td>");
                    out.print("              <td>" + ObjRanking[2] + "</td>");
                    out.print("              <td>" + ObjRanking[3] + "</td>");
                    out.print("              <td><a href='Client?opt=1&Client="+ObjRanking[0]+"' class=\"btn btn-primary\">Detalle</a></td>");
                    out.print("            </tr>");
                }

                out.print("          </table>");
                out.print("        </div>");
                out.print("      </div>");

                out.print("    </div>");
                out.print("  </div>");
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ULTIMOS CERTIFICADOS">
            lst_ultimate = StartJpa.ConsultUltimateCertificate();
            if (lst_ultimate != null) {
                out.print("<div class=\"col-lg-4\">");
                out.print("  <div class=\"card\">");

                out.print("    <div class=\"card-header\">");
                out.print("      <h4>Ultimos certificados</h4>");
                out.print("    </div>");

                out.print("    <div class=\"card-body\" id=\"top-5-scroll\">");
                out.print("      <ul class=\"list-unstyled list-unstyled-border\">");

                for (int i = 0; i < lst_ultimate.size(); i++) {
                    Object[] ObjUltimate = (Object[]) lst_ultimate.get(i);
                    out.print("        <li class=\"media\" onclick='window.location.href=\"" + ObjUltimate[3] + "\";cargarDatos()'>");
                    out.print("          <img class=\"mr-3 rounded\" width=\"55\" src=\"Interface/Imagen/product-document.png\" alt=\"product\">");
                    out.print("          <div class=\"media-body\">");
                    out.print("            <div class=\"media-title\">" + ObjUltimate[0] + "</div>");
                    out.print("            <div class=\"mt-1\">");
                    out.print("             <div class='text-small'>" + ObjUltimate[1] + "</div>");
                    out.print("             <div class='text-small text-capitalize fw-bold mb-1' >" + ObjUltimate[2] + "</div>");
                    out.print("            </div>");
                    out.print("          </div>");
                    out.print("        </li>");
                }

                out.print("      </ul>");
                out.print("    </div>");

                out.print("  </div>");
                out.print("  </div>");
            }
            //</editor-fold>
            out.print("</div>");

            out.print("<div class=\"row\">");
            //<editor-fold defaultstate="collapsed" desc="CONTADOR TRAZABILIDAD CERTIFICADOS">
            lst_accountants = StartJpa.ConsultAccountants();
            if (lst_accountants != null && !lst_accountants.isEmpty()) {
                Object[] ObjAccountants = (Object[]) lst_accountants.get(0);
                out.print("<div class=\"col-md-5\">");
                out.print("  <div class=\"card\">");

                out.print("    <div class=\"card-header\">");
                out.print("      <h4>Certificados</h4>");
                out.print("    </div>");

                out.print("    <div class=\"card-body\">");

                out.print("      <div class='d-flex justify-content-around' id=\"products-carousel\">");

                /* PRODUCTO 1 */
                out.print("        <div style='width:32%'>");
                out.print("          <div class=\"product-item pb-3\">");
                out.print("            <div class=\"product-image\">");
                out.print("              <img alt=\"image\" src=\"Interface/Imagen/Hoy.png\" class=\"img-fluid\">");
                out.print("            </div>");
                out.print("            <div class=\"product-details\">");
                out.print("              <div class=\"product-name\">Hoy</div>");
                out.print("              <div class=\"text-muted\">" + ObjAccountants[0] + "</div>");
                out.print("            </div>");
                out.print("          </div>");
                out.print("        </div>");

                /* PRODUCTO 2 */
                out.print("        <div style='width:32%'>");
                out.print("          <div class=\"product-item\">");
                out.print("            <div class=\"product-image\">");
                out.print("              <img alt=\"image\" src=\"Interface/Imagen/Esta_semana.png\" class=\"img-fluid\">");
                out.print("            </div>");
                out.print("            <div class=\"product-details\">");
                out.print("              <div class=\"product-name\">Esta semana</div>");
                out.print("              <div class=\"text-muted\">" + ObjAccountants[1] + "</div>");
                out.print("            </div>");
                out.print("          </div>");
                out.print("        </div>");

                out.print("        <div style='width:32%'>");
                out.print("          <div class=\"product-item\">");
                out.print("            <div class=\"product-image\">");
                out.print("              <img alt=\"image\" src=\"Interface/Imagen/Este_mes.png\" class=\"img-fluid\">");
                out.print("            </div>");
                out.print("            <div class=\"product-details\">");
                out.print("              <div class=\"product-name\">Este mes</div>");
                out.print("              <div class=\"text-muted\">" + ObjAccountants[2] + "</div>");
                out.print("            </div>");
                out.print("          </div>");
                out.print("        </div>");

                out.print("      </div>");
                out.print("      <div class='d-flex justify-content-around' id=\"products-carousel\">");

                /* PRODUCTO 3 */

 /* PRODUCTO 3 */
                out.print("        <div style='width:48%'>");
                out.print("          <div class=\"product-item\">");
                out.print("            <div class=\"product-image\">");
                out.print("              <img alt=\"image\" src=\"Interface/Imagen/Este_anio.png\" class=\"img-fluid\">");
                out.print("            </div>");
                out.print("            <div class=\"product-details\">");
                out.print("              <div class=\"product-name\">Ultimos 7 meses</div>");
                out.print("              <div class=\"text-muted\">" + ObjAccountants[3] + "</div>");
                out.print("            </div>");
                out.print("          </div>");
                out.print("        </div>");

                out.print("    </div>");
                out.print("    </div>");

                out.print("  </div>");
                out.print("</div>");
            }

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="GRAFICA">
            String labels = "", data = "";
            lst_statistics = StartJpa.ConsultStatistics();
            if (lst_statistics != null && !lst_statistics.isEmpty()) {
                out.print("<div class='col-lg-7 '>");
                out.print("  <div class='card'>");
                out.print("    <div class='card-header'>");
                out.print("     <h4>Certificados emitidos (últimos 7 meses)</h4>");
                out.print("    </div>");
                out.print("<div class=\"card-body\">");
                //<editor-fold defaultstate="collapsed" desc="ESTADISITICA GRAFICA">
                StringBuilder lbl = new StringBuilder();
                StringBuilder dat = new StringBuilder();
                for (int i = 0; i < lst_statistics.size(); i++) {
                    Object[] obj = (Object[]) lst_statistics.get(i);
                    String mes = obj[1].toString();
                    mes = mes.substring(0, 1).toUpperCase() + mes.substring(1).toLowerCase();
                    lbl.append("'").append(mes).append("'");
                    dat.append(obj[2]);
                    if (i < lst_statistics.size() - 1) {
                        lbl.append(",");
                        dat.append(",");
                    }
                }
                labels = lbl.toString();
                data = dat.toString();
                out.print("<canvas id=\"myChart\"></canvas>");

                out.print("<script>");
                out.print("var statistics_chart = document.getElementById('myChart').getContext('2d');");
                out.print("new Chart(statistics_chart, {");
                out.print("type: 'line',");
                out.print("data: {");
                out.print("labels: [" + labels + "],");
                out.print("datasets: [{");
                out.print("label: 'Certificados por mes',");
                out.print("data: [" + data + "],");
                out.print("borderWidth: 5,");
                out.print("borderColor: '#6777ef',");
                out.print("backgroundColor: 'transparent',");
                out.print("pointBackgroundColor: '#fff',");
                out.print("pointBorderColor: '#6777ef',");
                out.print("pointRadius: 4");
                out.print("}]");
                out.print("},");
                out.print("options: {");
                out.print("legend: { display: false },");
                out.print("scales: {");
                out.print("yAxes: [{");
                out.print("gridLines: { display: false, drawBorder: false },");
                out.print("ticks: { beginAtZero: true }");
                out.print("}],");
                out.print("xAxes: [{");
                out.print("gridLines: { color: '#fbfbfb', lineWidth: 2 }");
                out.print("}]");
                out.print("}");
                out.print("}");
                out.print("});");
                out.print("</script>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
            }
            //</editor-fold>
            out.print("</div>");

            out.print("</section>");

        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
