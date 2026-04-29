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

import java.util.HashMap;
import java.util.Map;

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

            int seg_venc = 0, seg_xvenc = 0, seg_paus = 0, seg_rev = 0, client = 0, prove = 0, doc_seg = 0, doc_rev = 0, doc_aprov = 0;
            try {
                Lst_document = DocumentJpa.SagrilaftCounter();
                Object[] count = (Object[]) Lst_document.get(0);
                seg_venc = Integer.parseInt(count[0].toString());
                seg_xvenc = Integer.parseInt(count[1].toString());
                seg_paus = Integer.parseInt(count[2].toString());
                seg_rev = Integer.parseInt(count[3].toString());
                client = Integer.parseInt(count[4].toString());
                prove = Integer.parseInt(count[5].toString());
                doc_seg = Integer.parseInt(count[6].toString());
                doc_rev = Integer.parseInt(count[7].toString());
                doc_aprov = Integer.parseInt(count[8].toString());

            } catch (Exception e) {
            }

            //<editor-fold defaultstate="collapsed" desc="CONTADORES">
            if (Lst_document != null) {

                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-7'>");
                out.print("<h6>Documentos</h6>");
                out.print("</div>");
                out.print("<div class='col-lg-5'>");
                out.print("<h6>Segmentaciones</h6>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='d-flex'>");

                out.print("<div class='d-flex col-lg-7' style='justify-content: space-evenly;'>");
                out.print("<div class='card col-lg-3' style='box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px;'>");
                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-4' style='padding: 39px 3px;text-align: center;'>");
                out.print("<i class=\"fas fa-sync-alt\" style='color: #ffb42c; font-size: 27px;'></i>");
                out.print("</div>");
                out.print("<div class='col-lg-8' style='padding: 19px 3px;'>");
                out.print("<span style='font-size: 16px;'>En proceso</span>");
                out.print("<h2 class='text-dark'>" + doc_seg + "</h2>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='card col-lg-3' style='box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px;'>");
                out.print("<div class=' d-flex'>");
                out.print("<div class='col-lg-4' style='padding: 39px 3px;text-align: center;'>");
                out.print("<i class=\"fas fa-exchange-alt\" style='color: #ec5c27; font-size: 27px;'></i>");
                out.print("</div>");
                out.print("<div class='col-lg-8' style='padding: 19px 3px;'>");
                out.print("<span style='font-size: 16px;'>Pendiente revisar</span>");
                out.print("<h2 class='text-dark'>" + doc_rev + "</h2>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='card col-lg-3' style='box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px;'>");
                out.print("<div class=' d-flex'>");
                out.print("<div class='col-lg-4' style='padding: 39px 3px;text-align: center;'>");
                out.print("<i class=\"fas fa-tasks\" style='color: #3772af; font-size: 27px;'></i>");
                out.print("</div>");
                out.print("<div class='col-lg-8' style='padding: 19px 3px;'>");
                out.print("<span style='font-size: 16px;'>Pendiente aprobar</span>");
                out.print("<h2 class='text-dark'>" + doc_aprov + "</h2>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-5 d-flex'style='justify-content: space-evenly;'>");
                out.print("<div class='card col-lg-5'style='box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px;'>");
                out.print("<div class=' d-flex'>");
                out.print("<div class='col-lg-4' style='padding: 39px 3px;text-align: center;'>");
                out.print("<i class=\"fas fa-clipboard-check\" style='color: #49a93d; font-size: 27px;'></i>");
                out.print("</div>");
                out.print("<div class='col-lg-8' style='padding: 19px 3px;'>");
                out.print("<span style='font-size: 16px;'>Pendiente revisar</span>");
                out.print("<h2 class='text-dark'>" + seg_rev + "</h2>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='card col-lg-5'style='box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px;'>");
                out.print("<div class=' d-flex'>");
                out.print("<div class='col-lg-4' style='padding: 39px 3px;text-align: center;'>");
                out.print("<i class=\"fas fa-stopwatch\" style='color: #c8302f; font-size: 27px;'></i>");
                out.print("</div>");
                out.print("<div class='col-lg-8' style='padding: 19px 3px;'>");
                out.print("<span style='font-size: 16px;'>Vencidas</span>");
                out.print("<h2 class='text-dark'>" + seg_venc + "</h2>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("</div>");
            }

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="GRAFICOS">
            out.print("<div style='display:flex; justify-content:center; gap:30px; flex-wrap:wrap;'>");
            //<editor-fold defaultstate="collapsed" desc="SEGMENTATION STATE">
            out.print("<div style=''>");
            out.print("<div style='margin: 10px;margin-left: -20px;'>");
            out.print("<h6 class=''>Estado de Segmentación</h6>");
            out.print("</div>");
            out.print("<div style='width:340px; height:320px; box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px; padding: 5px;'>");
            out.print("<canvas id='graficoEstados'></canvas>");
            out.print("</div>");
            out.print("</div>");
            out.print("<script>");

            out.print("const ctx = document.getElementById('graficoEstados').getContext('2d');");

            out.print("new Chart(ctx, {");

            out.print(" type: 'pie',");

            out.print(" data: {");

            out.print("     labels: ['Vencido', 'Por vencer', 'Pausado', 'Por Revisar'],");
            out.print("     datasets: [{");
            out.print("         data: [" + seg_venc + ", " + seg_xvenc + ", " + seg_paus + ", " + seg_rev + "],");
            out.print("         backgroundColor: [");
            out.print("             'rgba(231, 76, 60, 0.7)',");   // rojo suave
            out.print("             'rgba(241, 196, 15, 0.5)',"); // amarillo suave
            out.print("             'rgba(52, 152, 219, 0.7)',"); // azul
            out.print("             'rgba(243, 156, 18, 0.75)',");  // naranja
            out.print("         ],");
            out.print("         borderColor: [");
            out.print("             '#e74c3c',");
            out.print("             '#f1c40f',");
            out.print("             '#3498db',");
            out.print("             '#ca6f1e'"); // naranja fuerte
            out.print("         ],");
            out.print("         borderWidth: 2,");
            out.print("         hoverOffset: 8");
            out.print("     }]");

            out.print(" },");

            out.print(" options: {");
            out.print("     responsive: true,");
            out.print("     maintainAspectRatio: false,");

            out.print("     plugins: {");

            out.print("         legend: {");
            out.print("             position: 'top'");
            out.print("         }");

            out.print("     }");

            out.print(" }");

            out.print("});");

            out.print("</script>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="SEGMENTATION MEMBERS">
            out.print("<div style=''>");
            out.print("<div style='margin: 10px;margin-left: -20px;'>");
            out.print("<h6 class=''>Documentos por contraparte</h6>");
            out.print("</div>");
            out.print("<div style='width:340px; height:320px; box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px; padding: 5px;'>");
            out.print("<canvas id='graficoTipoPersona'></canvas>");
            out.print("</div>");
            out.print("</div>");
            out.print("<script>");

            out.print("const ctx2 = document.getElementById('graficoTipoPersona').getContext('2d');");

            out.print("new Chart(ctx2, {");
            out.print(" type: 'doughnut',");

            out.print(" data: {");
            out.print("     labels: ['Cliente', 'Proveedor'],");
            out.print("     datasets: [{");
            out.print("         data: [" + client + ", " + prove + "],");

            out.print("         backgroundColor: [");
            out.print("             'rgba(46, 204, 113, 0.2)',");   // verde suave
            out.print("             'rgba(128, 118, 245, 0.7)',");  // morado proveedor
            out.print("         ],");
            out.print("         borderColor: [");
            out.print("             '#2ecc71',");
            out.print("             '#6053F5',");
            out.print("         ],");

            out.print("         borderWidth: 2");
            out.print("     }]");
            out.print(" },");

            out.print(" options: {");

            out.print("     responsive: true,");
            out.print(" maintainAspectRatio: false,");
            out.print("     cutout: '60%',"); // hace el hueco central más elegante

            out.print("     plugins: {");

            out.print("         legend: {");
            out.print("             position: 'bottom',");
            out.print("             labels: {");
            out.print("                 font: {");
            out.print("                     size: 14");
            out.print("                 }");
            out.print("             }");
            out.print("         },");

            out.print("         tooltip: {");
            out.print("             callbacks: {");
            out.print("                 label: function(context) {");
            out.print("                     return context.label + ': ' + context.raw;");
            out.print("                 }");
            out.print("             }");
            out.print("         }");

            out.print("     }");

            out.print(" }");

            out.print("});");

            out.print("</script>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="DOCUMENTS STATE">
            out.print("<div style=''>");
            out.print("<div style='margin: 10px;margin-left: -20px;'>");
            out.print("<h6 class=''>Documentos por contraparte</h6>");
            out.print("</div>");
            out.print("<div style='width:340px; height:320px; box-shadow: 1px 3px 8px 1px #9d9d9d;border-radius: 7px; padding: 5px;'>");
            out.print("<canvas id='graficoDocumentos'></canvas>");
            out.print("</div>");
            out.print("</div>");
            out.print("<script>");

            out.print("const ctxDoc = document.getElementById('graficoDocumentos').getContext('2d');");
            out.print("new Chart(ctxDoc, {");
            out.print(" type: 'pie',");
            out.print(" data: {");
            out.print("     labels: ['En proceso', 'Por revisar', 'Por aprobar'],");
            out.print("     datasets: [{");
            out.print("         data: [" + doc_seg + ", " + doc_rev + ", " + doc_aprov + "],");
            out.print("         backgroundColor: [");
            out.print("             'rgba(243, 156, 18, 0.7)',");   // verde aprobar
            out.print("             'rgba(231, 76, 60, 0.7)',");   // rojo suave
            out.print("             'rgba(52, 152, 219, 0.7)',"); // amarillo suave
            out.print("         ],");
            out.print("         borderColor: [");
            out.print("              '#ca6f1e',");
            out.print("             '#e74c3c',");
            out.print("             '#3498db',");
            out.print("         ],");
            out.print("     }]");
            out.print(" },");

            out.print(" options: {");
            out.print("     responsive: true,");
            out.print("     maintainAspectRatio: false,");
            out.print("     plugins: {");
            out.print("         legend: {");
            out.print("             position: 'top'");
            out.print("         }");
            out.print("     }");
            out.print(" }");
            out.print("});");

            out.print("</script>");
            //</editor-fold>
            out.print("</div>");
            out.print("<div class='mt-4'>");
            //<editor-fold defaultstate="collapsed" desc="LINES">

            out.print("<div style='margin: 10px;margin-left: -20px;'>");
            out.print("<h6 class=''>Historial de registros</h6>");
            out.print("</div>");
            out.print("<div style='width:100%; max-width:900px; height:320px; margin:auto;'>");
            out.print("<canvas id='graficoClientesProveedores'></canvas>");
            out.print("</div>");

            //<editor-fold defaultstate="collapsed" desc="MAPA DE MESES">
            Map<Integer, String> meses = new HashMap<>();
            meses.put(1, "Enero");
            meses.put(2, "Febrero");
            meses.put(3, "Marzo");
            meses.put(4, "Abril");
            meses.put(5, "Mayo");
            meses.put(6, "Junio");
            meses.put(7, "Julio");
            meses.put(8, "Agosto");
            meses.put(9, "Septiembre");
            meses.put(10, "Octubre");
            meses.put(11, "Noviembre");
            meses.put(12, "Diciembre");
            //</editor-fold>

            String[] months = {};
            String monthData = "";
            String docData = "";
            String segData = "";
            List lst_counter_x_mes = DocumentJpa.SagrilaftCounter_x_mes();
            if (lst_counter_x_mes != null) {
                //<editor-fold defaultstate="collapsed" desc="MONTHS">
                try {
                    Object[] ObjMonth = (Object[]) lst_counter_x_mes.get(0);
                    for (int i = 1; i <= 12; i++) {
                        int temMont = Integer.parseInt(ObjMonth[i].toString());
                        monthData += "'" + meses.get(temMont) + "'" + ",";
                    }
                } catch (Exception e) {
                }

                try {
                    Object[] ObjMonth = (Object[]) lst_counter_x_mes.get(1);
                    for (int i = 1; i <= 12; i++) {
                        docData += "'" + ObjMonth[i].toString() + "'" + ",";
                    }
                } catch (Exception e) {
                }
                try {
                    Object[] ObjMonth = (Object[]) lst_counter_x_mes.get(2);
                    for (int i = 1; i <= 12; i++) {
                        segData += "'" + ObjMonth[i].toString() + "'" + ",";
                    }
                } catch (Exception e) {
                }

                //</editor-fold>
            }
//            out.print(monthData);
            out.print("<script>");

            out.print("const ctxLinea2 = document.getElementById('graficoClientesProveedores').getContext('2d');");
            out.print("new Chart(ctxLinea2, {");
            out.print(" type: 'line',");
            out.print(" data: {");
            out.print("     labels: [" + monthData + "],");
            out.print("     datasets: [");
            out.print("     {");
            out.print("         label: 'Documentos',");
            out.print("         data: [" + docData + "],");
            out.print("         borderColor: '#2ecc71',");
            out.print("         backgroundColor: 'rgba(46, 204, 113, 0.2)',");
            out.print("         tension: 0.4,");
            out.print("         fill: true,");
            out.print("         pointRadius: 4");
            out.print("     },");
            out.print("     {");
            out.print("         label: 'Segmentaciones',");
            out.print("         data: [" + segData + "],");
            out.print("         borderColor: '#9b59b6',");
            out.print("         backgroundColor: 'rgba(155, 89, 182, 0.2)',");
            out.print("         tension: 0.4,");
            out.print("         fill: true,");
            out.print("         pointRadius: 4");
            out.print("     }");
            out.print("     ]");
            out.print(" },");
            out.print(" options: {");
            out.print("     responsive: true,");
            out.print("     maintainAspectRatio: false,");
            out.print("     plugins: {");
            out.print("         legend: {");
            out.print("             position: 'bottom'");
            out.print("         }");
            out.print("     },");
            out.print("     scales: {");
            out.print("         y: {");
            out.print("             beginAtZero: true");
            out.print("         }");
            out.print("     }");
            out.print(" }");
            out.print("});");
            out.print("</script>");
            //</editor-fold>
            out.print("</div>");

            //</editor-fold>
//            //<editor-fold defaultstate="collapsed" desc="DOUCMENTOS EN PROCESO">
//            out.print("<div class='section-body'>");
//            out.print("<div class='row'>");
//            out.print("<div class='col-12'>");
//            out.print("<div class='card'>");
//            out.print("<div class='card-body d-flex'>");
//            out.print("<div class='col lg-6 ContStart'>");
//            out.print("<div class='mb-4'>");
//            out.print("<h6 class=''>Documento en proceso</h6>");
//            out.print("</div>");
//            out.print("<div class=''>");
//            Lst_document = DocumentJpa.ConsultDocumentsList();
//            if (Lst_document != null) {
//                out.print("<table class='table table-hover'>");
//                out.print("<thead>");
//                out.print("<tr>");
//                out.print("<th>Empresa</th>");
//                out.print("<th>Estado</th>");
//                out.print("<th>Progreso</th>");
//                out.print("<th>Acceder</th>");
//                out.print("</tr>");
//                out.print("</thead>");
//                out.print("<tbody>");
//                for (int i = 0; i < Lst_document.size(); i++) {
//                    Object[] ObjDoc = (Object[]) Lst_document.get(i);
//                    out.print("<tr>");
//                    out.print("<td>" + ObjDoc[1] + "</td>");
////                    out.print("<td>" + ObjDoc[3] + "</td>");
//                    int state = Integer.parseInt(ObjDoc[5].toString());
//                    Lst_config = ConfigJpa.ConsultSettingsByCategorie("StatesIcons" + state + "");
//                    if (Lst_config != null) {
//                        Object[] ObjConf = (Object[]) Lst_config.get(0);
//                        String[] StateData = ObjConf[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                        out.print("<td class='text-center'><span data-toggle='tooltip' data-placement='top' title='" + StateData[1] + "'>" + StateData[0] + "</span></td>");
//                    } else {
//                        out.print("<td class='text-center'>Error</td>");
//                    }
//                    double cantModules = 0;
//                    try {
//                        if (ObjDoc[3].toString().contains("Due ")) {
//                            cantModules = ObjDoc[8].toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///").length - 4;
//                        } else {
//                            cantModules = ObjDoc[8].toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///").length - 1;
//                        }
//                    } catch (Exception e) {
//                        cantModules = 0;
//                    }
//                    int Temp = Integer.parseInt(ObjDoc[4].toString());
//                    double DataCalc = (Temp / cantModules) * 100;
//                    int Progress = (int) Math.ceil(DataCalc);
//                    if (Progress > 100) {
//                        Progress = 100;
//                    }
//                    out.print("<td class='text-center'><div class='progress'><div class='progress-bar progress-bar-striped progress-bar-animated' role='progressbar' style='width: " + Progress + "%; " + ((Progress < 5) ? "color: black;" : "") + "' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'>" + Progress + "%</div></div></td>");
//                    out.print("<td class='text-center'><button class='btn btn-outline-dark btn-sm' onclick='window.location.href=\"Document?opt=1&IdDoc=" + ObjDoc[0] + "&Event=Checking\";cargarDatos()'><i class=\"fas fa-sign-in-alt\"></i></button></td>");
//                    out.print("</tr>");
//                }
//                out.print("</tbody>");
//                out.print("</table>");
//            } else {
//                out.print("<h6>No se ha encontrado información </h6>");
//            }
//            out.print("</div>");
//            out.print("</div>");
//            //</editor-fold>
//            //<editor-fold defaultstate="collapsed" desc="PROXIMOS A VENCER">
//            out.print("<div class='col lg-6 ContStart'>");
//            out.print("<div class='mb-4'>");
//            out.print("<h6>Proximos a vencer</h6>");
//            out.print("</div>");
//            out.print("<div class=''>");
//            out.print("<table class='table table-bordered' id='table-1'>");
//            out.print("<thead>");
//            out.print("<tr>");
//            out.print("<th>Asociado</th>");
//            out.print("<th>Días</th>");
//            out.print("<th>Vigencia</th>");
//            out.print("<th>Acceder</th>");
//            out.print("</tr>");
//            out.print("</thead>");
//            out.print("<tbody>");
//            Lst_seg = SegmentationJpa.ConsultSegmentationList();
//            if (Lst_seg != null) {
//                for (int i = 0; i < Lst_seg.size(); i++) {
//                    Object[] ObjSeg = (Object[]) Lst_seg.get(i);
//                    out.print("<tr>");
//                    out.print("<td>" + ObjSeg[2] + "</td>");
//                    out.print("<td>" + ObjSeg[3] + "</td>");
//                    out.print("<td>");
//                    if (ObjSeg[3] != null) {
//                        int days = Integer.parseInt(ObjSeg[3].toString());
//                        if (days > 730) {
//                            out.print("<b style='color:red;'>Vencido</b>");
//                        } else if (days > 700) {
//                            out.print("<b style='color:orange;'>Proximo a vencer</b>");
//                        } else {
//                            out.print("<b style='color:green;'>Vigente</b>");
//                        }
//                    } else {
//                        out.print("<b>Sin datos registrados</b>");
//                    }
//                    String format = ObjSeg[4].toString();
//                    out.print("</td>");
//                    out.print("<td><button class='btn btn-outline-dark btn-sm' onclick='window.location.href=\"Segmentation?opt=1&IdSegmentation=" + ObjSeg[0] + "&Temp=2&Format=" + format + "\";cargarDatos()'><i class=\"fas fa-sign-in-alt\"></i></button></td>");
//                    out.print("</tr>");
//                }
//            }
//            out.print("</tbody>");
//            out.print("</table>");
//            out.print("</div>");
//            out.print("</div>");
//            //</editor-fold>
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
