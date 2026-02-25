package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.DocumentControllerJpa;
import Controller.ConfigurationControllerJpa;
import Controller.SegmentationControllerJpa;
import java.util.List;

public class Tag_Start extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        DocumentControllerJpa DocumentJpa = new DocumentControllerJpa();
        ConfigurationControllerJpa ConfigJpa = new ConfigurationControllerJpa();
        SegmentationControllerJpa SegmentationJpa = new SegmentationControllerJpa();
        List Lst_document = null;
        List Lst_config = null;
        List Lst_seg = null;
        String nameSession = "";
        try {
            nameSession = pageContext.getSession().getAttribute("Nombres").toString();
        } catch (Exception e) {
            nameSession = " - ";
        }

        JspWriter out = pageContext.getOut();
        try {
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Bienvenid@, " + nameSession + "</h1>");
            out.print("</div>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-body d-flex'>");
            out.print("<div class='col lg-6 ContStart'>");
            out.print("<div class='mb-4'>");
            out.print("<h6 class=''>Documento en proceso</h6>");
            out.print("</div>");
            out.print("<div class=''>");
            Lst_document = DocumentJpa.ConsultDocumentsList();
            if (Lst_document != null) {
                out.print("<table class='table table-hover'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Empresa</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Progreso</th>");
                out.print("<th>Acceder</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for (int i = 0; i < Lst_document.size(); i++) {
                    Object[] ObjDoc = (Object[]) Lst_document.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjDoc[1] + "</td>");
//                    out.print("<td>" + ObjDoc[3] + "</td>");
                    int state = Integer.parseInt(ObjDoc[5].toString());
                    Lst_config = ConfigJpa.ConsultSettingsByCategorie("StatesIcons" + state + "");
                    if (Lst_config != null) {
                        Object[] ObjConf = (Object[]) Lst_config.get(0);
                        String[] StateData = ObjConf[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        out.print("<td class='text-center'><span data-toggle='tooltip' data-placement='top' title='" + StateData[1] + "'>" + StateData[0] + "</span></td>");
                    } else {
                        out.print("<td class='text-center'>Error</td>");
                    }
                    double cantModules = 0;
                    try {
                        cantModules = ObjDoc[8].toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///").length;
                    } catch (Exception e) {
                        cantModules = 0;
                    }
                    int Temp = Integer.parseInt(ObjDoc[4].toString());
                    double DataCalc = (Temp / cantModules) * 100;
                    int Progress = (int) Math.ceil(DataCalc);
                    if (Progress > 100) {
                        Progress = 100;
                    }
                    out.print("<td class='text-center'><div class='progress'><div class='progress-bar progress-bar-striped progress-bar-animated' role='progressbar' style='width: " + Progress + "%; " + ((Progress < 5) ? "color: black;" : "") + "' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'>" + Progress + "%</div></div></td>");
                    out.print("<td class='text-center'><button class='btn btn-outline-dark btn-sm' onclick='window.location.href=\"Document?opt=1&IdDoc=" + ObjDoc[0] + "&Event=Checking\";cargarDatos()'><i class=\"fas fa-sign-in-alt\"></i></button></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
            } else {
                out.print("<h6>No se ha encontrado información </h6>");
            }
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='col lg-6 ContStart'>");
            out.print("<div class='mb-4'>");
            out.print("<h6>Proximos a vencer</h6>");
            out.print("</div>");
            out.print("<div class=''>");
            out.print("<table class='table table-bordered' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Asociado</th>");
            out.print("<th>Días</th>");
            out.print("<th>Vigencia</th>");
            out.print("<th>Acceder</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            Lst_seg = SegmentationJpa.ConsultSegmentationList();
            if (Lst_seg != null) {
                for (int i = 0; i < Lst_seg.size(); i++) {
                    Object[] ObjSeg = (Object[]) Lst_seg.get(i);
                    out.print("<tr>");
                    out.print("<td>" + ObjSeg[2] + "</td>");
                    out.print("<td>" + ObjSeg[3] + "</td>");
                    out.print("<td>");
                    if (ObjSeg[3] != null) {
                        int days = Integer.parseInt(ObjSeg[3].toString());
                        if (days < 300) {
                            out.print("<b style='color:green;'>Vigente</b>");
                        } else if (days < 365) {
                            out.print("<b style='color:orange;'>Proximo a vencer</b>");
                        } else {
                            out.print("<b style='color:red;'>Vencido</b>");
                        }
                    } else {
                        out.print("<b>Sin datos registrados</b>");
                    }
                    String format = ObjSeg[4].toString();
                    out.print("</td>");
                    out.print("<td><button class='btn btn-outline-dark btn-sm' onclick='window.location.href=\"Segmentation?opt=1&IdSegmentation=" + ObjSeg[0] + "&Temp=2&Format=" + format + "\";cargarDatos()'><i class=\"fas fa-sign-in-alt\"></i></button></td>");
                    out.print("</tr>");
                }
            }
            out.print("</tbody>");
            out.print("</table>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</div>");
            out.print("</section>");
        } catch (Exception ex) {
            Logger.getLogger(Tag_Start.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
