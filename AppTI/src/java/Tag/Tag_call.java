package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.SupportControllerJpa;
import Controller.FormatControllerJpa;
import SQL.ConnectionsBd;
import com.google.gson.Gson;
import java.util.List;

public class Tag_call extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        SupportControllerJpa SupportJpa = new SupportControllerJpa();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        ConnectionsBd SirhJpa = new ConnectionsBd();
        List lst_support = null, lst_format = null, lst_detail = null, lst_sirh = null, lst_year = null;
        int Yar = 0, iterator2 = 0, Mth = 0;
        String Module = "", signt = "", advice = "";
        try {
            try {
                Yar = Integer.parseInt(pageContext.getRequest().getAttribute("Yar").toString());
            } catch (Exception e) {
                Yar = 0;
            }
            try {
                Mth = Integer.parseInt(pageContext.getRequest().getAttribute("Mth").toString());
            } catch (Exception e) {
                Mth = 0;
            }
            try {
                Module = pageContext.getRequest().getAttribute("Module").toString();
            } catch (Exception e) {
                Module = "";
            }
            out.print("<section class='section'>");
            out.print("<div class=\"row\">");
            out.print("<div class=\"col-12\">");
            out.print("<div class=\"card\">");
            out.print("<div class=\"card-header\" style='justify-content: space-between;'>");

            out.print("<div class='d-flex'>");
            if (Module.equals("Detail")) {
                out.print("<div class='mr-2'><button class='btn btn-green' style='border-radius: 3px;' onclick=\"location.href='Call?opt=1&Module=General&Yar=" + Yar + "'\"><i class=\"far fa-hand-point-left\"></i></button></div>");
            }
            out.print("<div><h4>Módulo R-TI-001</h4></div>");
            out.print("</div>");

            if (Module.equals("Detail")) {
                out.print("<div><button class='btn btn-green'  onclick='exportToExcel()' style='border-radius: 3px;'><i class=\"fas fa-file-excel\"></i></button></div>");
            }
            if (Module.equals("General")) {
                //<editor-fold defaultstate="collapsed" desc="FILTER YEAR">
                out.print("<div>");
                out.print("<select class='select2 form-control' style='border-radius:3px;' onchange='redirigir(this)' >");
                out.print("<option value='" + Yar + "'>" + Yar + "</option>");
                lst_year = SupportJpa.ConsultIndicatorYear();
                if (lst_year != null) {
                    for (int i = 0; i < lst_year.size(); i++) {
                        Object[] obj_year = (Object[]) lst_year.get(i);
                        int YearFt = Integer.parseInt(obj_year[1].toString());
                        if (Yar != YearFt) {
                            out.print("<option value='" + obj_year[1] + "'>" + obj_year[1] + "</option>");
                        }
                    }
                }
                out.print("</select>");
                out.print("</div>");
                //</editor-fold>
            }
            out.print("</div>");

            out.print("<div class='card-body'>");
            if (Module.equals("General")) {
                //<editor-fold defaultstate="collapsed" desc="GENERAL">
                lst_support = SupportJpa.ConsultIndicatorSupport(Yar);
                if (lst_support != null) {
                    out.print("<div class=\"container\">");
                    out.print("<div class=\"row\">\n");
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] obj_support = (Object[]) lst_support.get(i);
                        out.print("           <div class=\"col-lg-3 col-md-4 col-sm-12\">\n"
                                + "              <div class=\"card card-statistic-2 boxShadowDiv AnimationDiv\">\n"
                                + "                <div class=\"card-stats\">\n"
                                + "                  <div class=\"card-stats-title\" style='color:black' ><span "+((obj_support[5] == null)?"":"data-toggle='tooltip' data-placement='top' title='Área con mas casos'")+" ><i class='fas fa-arrow-up'></i> Casos" + ((obj_support[5] == null) ? "" : " -  <code> " + obj_support[5] + "</code>") + "</span>\n"
                                + "                  </div>\n"
                                + "                  <div class=\"card-stats-items\">\n"
                                + "                    <div class=\"card-stats-item CalDiv\">\n"
                                + "                      <div class=\"card-stats-item-count LDiv\">" + obj_support[1] + "</div>\n"
                                + "                      <div class=\"card-stats-item-label\"><i class=\"fas fa-phone SizeFont\"></i></div>\n"
                                + "                    </div>\n"
                                + "                    <div class=\"card-stats-item CalDiv\">\n"
                                + "                      <div class=\"card-stats-item-count LDiv\">" + obj_support[2] + "</div>\n"
                                + "                      <div class=\"card-stats-item-label\"><i class=\"fas fa-phone-slash SizeFont\"></i></div>\n"
                                + "                    </div>\n"
                                + "                    <div class=\"card-stats-item CalDiv\">\n"
                                + "                      <div class=\"card-stats-item-count LDiv\">" + obj_support[3] + "</div>\n"
                                + "                      <div class=\"card-stats-item-label\"><i class=\"fas fa-clock SizeFont\"></i></div>\n"
                                + "                    </div>\n"
                                + "                    <div class=\"card-stats-item CalDiv\">\n"
                                + "                      <div class=\"card-stats-item-count LDiv\">" + obj_support[4] + "</div>\n"
                                + "                      <div class=\"card-stats-item-label\"><i class=\"far fa-clock SizeFont\"></i></div>\n"
                                + "                    </div>\n"
                                + "                  </div>\n"
                                + "                </div>\n"
                                + "                <div class=\"card-wrap mb-3\">\n"
                                + "                  <div class=\" d-flex justify-content-between borderTopH5\">\n"
                                + "                    <div class='ml-2'><h5 style='color:black'>" + obj_support[0] + "</h5></div>\n"
                                + "<div><button class='btn btn-green btn-sm mr-2'><i class='fas fa-arrow-right' onclick=\"location.href='Call?opt=1&Module=Detail&Yar=" + Yar + "&Mth=" + (i + 1) + "'\"></i></button></div>"
                                + "                  </div>\n"
                                + "                </div>\n"
                                + "              </div>\n"
                                + "            </div>\n");

                    }
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>
            } else if (Module.equals("Detail")) {
                //<editor-fold defaultstate="collapsed" desc="DETAIL">
                lst_format = FormatJpa.ConsultFormatName("R-TI-001");
                out.print("<div id='TableStyleDiv'>");
                if (lst_format != null) {
                    Object[] obj_format = (Object[]) lst_format.get(0);
                    out.print(obj_format[3]);
                    lst_detail = SupportJpa.ConsultR1Month(Yar, Mth);
                    if (lst_detail != null) {
                        for (int i = 0; i < lst_detail.size(); i++) {
                            Object[] obj_detail = (Object[]) lst_detail.get(i);
                            out.print("<tr class='fontSmaller'>");
                            out.print("<td>" + obj_detail[0] + "</td>");
                            out.print("<td><div>" + obj_detail[2] + "</div><div class='float-right'><code>" + obj_detail[4] + "</code></div></td>");
                            if (obj_detail[5].toString().contains("<br>")) {
                                String[] DataVal = obj_detail[5].toString().split("<br>");
                                if (DataVal.length > 1) {
                                    out.print("<td class='OutPaddingP'>" + DataVal[1].replace("<p", "<p style='line-height: 14px !important;'").replace("&nbsp;", "") + "<br><span class='DateR001'>" + obj_detail[6] + "</span></td>");
                                } else {
                                    out.print("<td class='OutPaddingP'></td>");
                                }
                            } else {
                                out.print("<td class='OutPaddingP'>" + obj_detail[5].toString().replace("<p", "<p style='line-height: 14px !important;'").replace("&nbsp;", "") + "<br><span class='DateR001'>" + obj_detail[6] + "</span></td>");
                            }
                            if (obj_detail[7] != null) {
                                out.print("<td>" + obj_detail[8] + "</td>");
                                out.print("<td>" + obj_detail[9].toString().replace("<p", "<p style='line-height: 14px !important;'").replace("&nbsp;", "") + "<span class='DateR001'>" + obj_detail[10] + "</span></td>");
                                out.print("<td>" + ((obj_detail[11] == null) ? "0" : obj_detail[11]) + "</td>");
                                out.print("<td>" + ((obj_detail[12] == null) ? "0" : obj_detail[12]) + "</td>");
                                out.print("<td>" + ((obj_detail[14] == null) ? "" : obj_detail[14]) + "</td>");
                                out.print("<td>" + ((obj_detail[15] == null) ? "" : obj_detail[15]) + "</td>");
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                                String[] ArgData = obj_detail[17].toString().split("//");
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
                                    out.print(advice);
                                    out.print("</td>");
                                }
//</editor-fold>
                            } else {
                                out.print("<td colspan='7' class='text-center bg-light' ><b>SIN SOLUCIÓN</b></td>");
                            }
                            out.print("</tr>");

                        }
                    }
                }
                out.print("</div>");
                //</editor-fold>
            }
            out.print("</div>");

            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_user.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_call.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
