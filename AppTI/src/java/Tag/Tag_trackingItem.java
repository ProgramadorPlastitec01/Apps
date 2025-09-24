package Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.ComputerControllerJpa;
import Controller.DeviceJpaController;
import Controller.ItemJpaController;
import Controller.MoveItemJpaController;

import SQL.ConnectionsBd;
import java.util.HashMap;

public class Tag_trackingItem extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        ComputerControllerJpa ComputeJpa = new ComputerControllerJpa();
        DeviceJpaController DeviceJpa = new DeviceJpaController();
        ItemJpaController ItemJpa = new ItemJpaController();
        ConnectionsBd SirhJpa = new ConnectionsBd();
        MoveItemJpaController MoveJpa = new MoveItemJpaController();

        List lst_result = null;
        List lst_computer = null;
        List lst_device = null;
        List lst_item = null;
        List lst_sirh = null;
        List lst_move = null;

        String idTemToSig = "";
        String NameSigna = "";
        int action = 0, idItem = 0, docx = 0, codx = 0;
        try {
            lst_result = (List<String>) pageContext.getRequest().getAttribute("ResultDataSearch");
        } catch (Exception e) {
            lst_result = null;
        }
        try {
            action = Integer.parseInt(pageContext.getRequest().getAttribute("action").toString());
        } catch (Exception e) {
            action = 0;
        }
        try {
            idItem = Integer.parseInt(pageContext.getRequest().getAttribute("idItem").toString());
        } catch (Exception e) {
            idItem = 0;
        }
        try {
            docx = Integer.parseInt(pageContext.getRequest().getAttribute("docx").toString());
        } catch (Exception e) {
            docx = 0;
        }
        try {
            codx = Integer.parseInt(pageContext.getRequest().getAttribute("codx").toString());
        } catch (Exception e) {
            codx = 0;
        }
        try {
            idTemToSig = pageContext.getRequest().getAttribute("idTemToSig").toString();
        } catch (Exception e) {
            idTemToSig = "";
        }
        try {
            try {

                if (action == 0) {
                    //<editor-fold defaultstate="collapsed" desc="MAIN MODULE - CONTERS">
                    out.print("<section class='section'>");
                    out.print("<div class='section-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='card'>");
                    out.print("<div class='card-header' style='justify-content: space-between;'>");
                    out.print("<span class=''></span>");
                    out.print("<h2>INVENTARIO</h2>");
                    out.print("<span class=''></span>");
//                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");

                    out.print("<div class='card'>");
                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-warning mr-2' onclick='window.location.href=\"Reference?opt=1\"'>(1) Gestion de referencias</button>");
                    out.print("<button class='btn btn-info mr-2' onclick='window.location.href=\"MoveItem?opt=1\"'>(2) Confirmar movimientos </button>");
                    out.print("<button class='btn btn-green' onclick='window.location.href=\"TrackingItem?opt=1&action=1\"'>(3) Consultar items</button>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div class='row'>");
                    lst_computer = ComputeJpa.consultCounterPdData();
                    //<editor-fold defaultstate="collapsed" desc="COUNTER PC">
                    if (lst_computer != null) {
                        Object[] ObjCounterPc = (Object[]) lst_computer.get(0);
                        out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">");
                        out.print("<div class=\"card card-statistic-2\" style='box-shadow: 0px 1px 9px -1px #999999; border-radius: 6px;'>");
                        out.print("<div class=\"card-stats\">");
                        out.print("<div class=\"card-stats-title\">Inventario de PC</div>");
                        out.print("<div class=\"card-stats-items\">");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">" + ObjCounterPc[2] + "</div>");
                        out.print("<div class=\"card-stats-item-label\">Bueno</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">" + ObjCounterPc[3] + "</div>");
                        out.print("<div class=\"card-stats-item-label\">Revisión</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">" + ObjCounterPc[4] + "</div>");
                        out.print("<div class=\"card-stats-item-label\">Baja</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-icon shadow-primary bg-primary\">");
                        out.print("<i class=\"fas fa-desktop\"></i>");
                        out.print("</div>");
                        out.print("<div class=\"card-wrap\">");
                        out.print("<div class=\"card-header\">");
                        out.print("<h4>Total PC</h4>");
                        out.print("</div>");
                        out.print("<div class=\"card-body\">");
                        out.print(ObjCounterPc[1]);
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">");
                        out.print("<div class=\"card card-statistic-2\">");
                        out.print("<div class=\"card-stats\">");
                        out.print("<div class=\"card-stats-items\">");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">-</div>");
                        out.print("<div class=\"card-stats-item-label\">Bueno</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">-</div>");
                        out.print("<div class=\"card-stats-item-label\">Revisión</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">-</div>");
                        out.print("<div class=\"card-stats-item-label\">Baja</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-icon shadow-primary bg-primary\">");
                        out.print("<i class=\"fas fa-archive\"></i>");
                        out.print("</div>");
                        out.print("<div class=\"card-wrap\">");
                        out.print("<div class=\"card-header\">");
                        out.print("<h4>Total PC</h4>");
                        out.print("</div>");
                        out.print("<div class=\"card-body\">");
                        out.print("-");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="COUTER TYPE PC">
                    out.print("<div class=''>");
                    if (lst_computer != null) {
                        Object[] ObjCounterPc = (Object[]) lst_computer.get(0);
                        out.print("<div class='dvGneral d-flex'>");
                        out.print("<div class='DvSraque'>");
                        out.print("<i class=\"fas fa-keyboard\" style='font-size: 20px;'></i>");
                        out.print("</div>");
                        out.print("<div class='DvData'>");
                        out.print("<span class='dvText' style='margin: 0;'>PC Torre</span><br>");
                        out.print("<span class='dvBold'>" + ObjCounterPc[5] + "</span>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='dvGneral d-flex'>");
                        out.print("<div class='DvSraque'>");
                        out.print("<i class=\"fas fa-laptop\" style='font-size: 20px;'></i>");
                        out.print("</div>");
                        out.print("<div class='DvData'>");
                        out.print("<span class='dvText' style='margin: 0;'>Portatil</span><br>");
                        out.print("<span class='dvBold'>" + ObjCounterPc[6] + "</span>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='dvGneral d-flex' style='align-items: baseline;'>");
                        out.print("<div class='DvSraque'>");
                        out.print("<i class=\"fas fa-search-plus\" style='font-size: 20px;'></i>");
                        out.print("</div>");
                        out.print("<div class='DvData'>");
                        out.print("<span class='dvBold'><a class='link' href='Computer?opt=1' >Ver más <i class=\"fas fa-chevron-right\"></i></a></span>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    //</editor-fold>

                    lst_device = DeviceJpa.ConsultGeneralCounter();
                    //<editor-fold defaultstate="collapsed" desc="COUNTER DEVICE">
                    if (lst_device != null) {
                        Object[] ObjDev = (Object[]) lst_device.get(0);
                        out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">");
                        out.print("<div class=\"card card-statistic-2\" style='box-shadow: 0px 1px 9px -1px #999999; border-radius: 6px;'>");
                        out.print("<div class=\"card-stats\">");
                        out.print("<div class=\"card-stats-title\">Inventario de Dispositivos</div>");
                        out.print("<div class=\"card-stats-items\">");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">" + ObjDev[2] + "</div>");
                        out.print("<div class=\"card-stats-item-label\">Bueno</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">" + ObjDev[3] + "</div>");
                        out.print("<div class=\"card-stats-item-label\">Revisión</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">" + ObjDev[4] + "</div>");
                        out.print("<div class=\"card-stats-item-label\">Baja</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-icon shadow-primary bg-primary\">");
                        out.print("<i class=\"fas fa-microchip\"></i>");
                        out.print("</div>");
                        out.print("<div class=\"card-wrap\">");
                        out.print("<div class=\"card-header\">");
                        out.print("<h4>Total Dispositivos</h4>");
                        out.print("</div>");
                        out.print("<div class=\"card-body\">");
                        out.print(ObjDev[1]);
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class=\"col-lg-4 col-md-4 col-sm-12\">");
                        out.print("<div class=\"card card-statistic-2\">");
                        out.print("<div class=\"card-stats\">");
                        out.print("<div class=\"card-stats-items\">");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">-</div>");
                        out.print("<div class=\"card-stats-item-label\">Bueno</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">-</div>");
                        out.print("<div class=\"card-stats-item-label\">Revisión</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-stats-item\">");
                        out.print("<div class=\"card-stats-item-count\">-</div>");
                        out.print("<div class=\"card-stats-item-label\">Baja</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class=\"card-icon shadow-primary bg-primary\">");
                        out.print("<i class=\"fas fa-archive\"></i>");
                        out.print("</div>");
                        out.print("<div class=\"card-wrap\">");
                        out.print("<div class=\"card-header\">");
                        out.print("<h4>Total PC</h4>");
                        out.print("</div>");
                        out.print("<div class=\"card-body\">");
                        out.print("-");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="COUTER TYPE DEVICE">
                    out.print("<div class=''>");
                    if (lst_device != null) {
                        Object[] ObjDevi = (Object[]) lst_device.get(0);
                        out.print("<div class='dvGneral d-flex'>");
                        out.print("<div class='DvSraque'>");
                        out.print("<i class=\"fas fa-tablet-alt\" style='font-size: 20px;'></i>");
                        out.print("</div>");
                        out.print("<div class='DvData'>");
                        out.print("<span class='dvText' style='margin: 0;'>Tablet</span><br>");
                        out.print("<span class='dvBold'>" + ObjDevi[5].toString().split("-")[1] + "</span>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='dvGneral d-flex'>");
                        out.print("<div class='DvSraque'>");
                        out.print("<i class=\"fas fa-print\" style='font-size: 20px;'></i>");
                        out.print("</div>");
                        out.print("<div class='DvData'>");
                        out.print("<span class='dvText' style='margin: 0;'>Impresora de papel</span><br>");
                        out.print("<span class='dvBold'>" + ObjDevi[7].toString().split("-")[1] + "</span>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='dvGneral d-flex' style='align-items: baseline;'>");
                        out.print("<div class='DvSraque'>");
                        out.print("<i class=\"fas fa-search-plus\" style='font-size: 20px;'></i>");
                        out.print("</div>");
                        out.print("<div class='DvData'>");
                        out.print("<span class='dvBold'><a class='link' href='Device?opt=1' >Ver más <i class=\"fas fa-chevron-right\"></i></a></span>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    //</editor-fold>

                    out.print("</div>");

                    out.print("<div class='d-flex' style='justify-content: space-around;'>");
                    int cnt_ref = 0, cnt_itm = 0;
                    lst_device = DeviceJpa.CounterReferenceItem();
                    try {
                        if (lst_device != null) {
                            Object[] ObjCount = (Object[]) lst_device.get(0);
                            cnt_ref = Integer.parseInt(ObjCount[1].toString());
                            cnt_itm = Integer.parseInt(ObjCount[2].toString());
                        } else {

                        }
                    } catch (Exception e) {
                    }

                    out.print("<div class='dvGneral d-flex col-lg-5' style='align-items: baseline;justify-content: space-evenly; cursor: pointer;'  onclick='window.location.href=\"Reference?opt=1\"'>");
                    out.print("<div class='d-flex' style='align-items: baseline;'>");
                    out.print("<div class='DvSraque'>");
                    out.print("<i class=\"fas fa-stream\" style='font-size: 20px;'></i>");
                    out.print("</div>");
                    out.print("<div class='DvData'>");
                    out.print("<span class='dvText' style='margin: 0;'>REFERENCIAS REGISTRADAS</span><br>");
                    out.print("</div>");
                    out.print("<span class='dvBold'>" + cnt_ref + "</span>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<span class=''><i class=\"fas fa-caret-right\"></i></span>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='dvGneral d-flex col-lg-5' style='align-items: baseline;justify-content: space-evenly; cursor: pointer;' onclick='window.location.href=\"TrackingItem?opt=1&action=1\"'>");
                    out.print("<div class='d-flex' style='align-items: baseline;'>");
                    out.print("<div class='DvSraque'>");
                    out.print("<i class=\"fas fa-sitemap\" style='font-size: 20px;'></i>");
                    out.print("</div>");
                    out.print("<div class='DvData'>");
                    out.print("<span class='dvText' style='margin: 0;'>ITEMS REGISTRADOS</span><br>");
                    out.print("</div>");
                    out.print("<span class='dvBold'>" + cnt_itm + "</span>");
                    out.print("</div>");
                    out.print("<div>");
                    out.print("<span class=''><i class=\"fas fa-caret-right\"></i></span>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");
                    
                    out.print("<div class='mt-4 text-center'>");
                    out.print("<h4>Estadisitica de datos (ultimos 6 meses)</h4>");
                    out.print("</div>");

                    out.print("<div class='row'>");
                    //<editor-fold defaultstate="collapsed" desc="GRAFICS">

                    //<editor-fold defaultstate="collapsed" desc="ALL MOV X MONTH">
                    lst_move = MoveJpa.ConsultMoveItemsMonth();
                    if (lst_move != null) {
                        String labels = "", data = "";
                        for (int i = 0; i < lst_move.size(); i++) {
                            Object[] ObjMv = (Object[]) lst_move.get(i);
                            labels += "\"" + ObjMv[0] + "\"";
                            data += ObjMv[1];
                            if (i < lst_move.size() - 1) {
                                labels += ",";
                                data += ",";
                            }
                        }
                        out.print("<div class=''>");
                        out.print("<div class=\"card\"> "
                                + "  <div class=\"card-header text-center\"> "
                                + "    <h4>Movimientos x mes</h4> "
                                + "  </div> "
                                + "<div class=\"card-body\"> "
                                + "<canvas id=\"myChart\"></canvas> "
                                + "</div> "
                                + "</div>");
                        out.print("</div>");

                        out.print("<script>");
                        out.print("var ctx = document.getElementById(\"myChart\").getContext('2d'); "
                                + "var myChart = new Chart(ctx, { "
                                + "    type: 'line', "
                                + "    data: { "
                                + "        labels: [" + labels.toUpperCase() + "], "
                                + "        datasets: [{ "
                                + "                label: 'Cantidad', "
                                + "                data: [" + data + "], "
                                + "                borderWidth: 2, "
                                + "                backgroundColor: '#33acbf8a', "
                                + "                borderColor: '#33acbf', "
                                + "                borderWidth: 2.5, "
                                + "                pointBackgroundColor: '#ffffff', "
                                + "                pointRadius: 4 "
                                + "            }] "
                                + "    }, "
                                + "    options: { "
                                + "        legend: { "
                                + "            display: false "
                                + "        }, "
                                + "        scales: { "
                                + "            yAxes: [{ "
                                + "                    gridLines: { "
                                + "                        drawBorder: false, "
                                + "                        color: '#f2f2f2', "
                                + "                    }, "
                                + "                    ticks: { "
                                + "                        beginAtZero: true, "
                                + "                        stepSize: 50 "
                                + "                    } "
                                + "                }], "
                                + "            xAxes: [{ "
                                + "                    ticks: { "
                                + "                        display: false "
                                + "                    }, "
                                + "                    gridLines: { "
                                + "                        display: false "
                                + "                    } "
                                + "                }] "
                                + "        }, "
                                + "    } "
                                + "});");
                        out.print("</script>");
                    } else {
                        out.print("<div class=''>");
                        out.print("<div class=\"card\"> "
                                + "   <div class=\"card-header text-center\"> "
                                + "        <h4>Movimientos x mes</h4> "
                                + "   </div> "
                                + "<div class=\"card-body\"> "
                                + "<span><b>No se han encontrado movimientos</b></span>"
                                + "</div> "
                                + "</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="ENT X MONTH">
                    lst_move = MoveJpa.ConsultMoveItemsMonthEnt();
                    if (lst_move != null) {
                        String labels = "", data = "";
                        for (int i = 0; i < lst_move.size(); i++) {
                            Object[] ObjMv = (Object[]) lst_move.get(i);
                            labels += "\"" + ObjMv[0] + "\"";
                            data += ObjMv[1];
                            if (i < lst_move.size() - 1) {
                                labels += ",";
                                data += ",";
                            }
                        }

                        out.print("<div class=''>");
                        out.print("<div class=\"card\"> "
                                + "                  <div class=\"card-header text-center\"> "
                                + "                    <h4>Entradas x mes</h4> "
                                + "                  </div> "
                                + "                  <div class=\"card-body\"> "
                                + "<canvas id=\"myChart99\"></canvas> "
                                + "</div> "
                                + "</div>");
                        out.print("</div>");

                        out.print("<script>");
                        out.print("var ctx = document.getElementById(\"myChart99\").getContext('2d'); "
                                + "var myChart = new Chart(ctx, { "
                                + "    type: 'line', "
                                + "    data: { "
                                + "        labels: [" + labels + "], "
                                + "        datasets: [{ "
                                + "                label: 'Cantidad', "
                                + "                data: [" + data + "], "
                                + "                borderWidth: 2, "
                                + "                backgroundColor: '#33bf98a6', "
                                + "                borderColor: '#33bf98', "
                                + "                borderWidth: 2.5, "
                                + "                pointBackgroundColor: '#ffffff', "
                                + "                pointRadius: 4 "
                                + "            }] "
                                + "    }, "
                                + "    options: { "
                                + "        legend: { "
                                + "            display: false "
                                + "        }, "
                                + "        scales: { "
                                + "            yAxes: [{ "
                                + "                    gridLines: { "
                                + "                        drawBorder: false, "
                                + "                        color: '#f2f2f2', "
                                + "                    }, "
                                + "                    ticks: { "
                                + "                        beginAtZero: true, "
                                + "                        stepSize: 25 "
                                + "                    } "
                                + "                }], "
                                + "            xAxes: [{ "
                                + "                    ticks: { "
                                + "                        display: false "
                                + "                    }, "
                                + "                    gridLines: { "
                                + "                        display: false "
                                + "                    } "
                                + "                }] "
                                + "        }, "
                                + "    } "
                                + "});");
                        out.print("</script>");

                    } else {
                        out.print("<div class=''>");
                        out.print("<div class=\"card\"> "
                                + "   <div class=\"card-header text-center\"> "
                                + "        <h4>Entradas x mes</h4> "
                                + "   </div> "
                                + "<div class=\"card-body\"> "
                                + "<span><b>No se han encontrado movimientos</b></span>"
                                + "</div> "
                                + "</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="SAL X MONTH">
                    lst_move = MoveJpa.ConsultMoveItemsMonthSal();
                    if (lst_move != null) {
                        String labels = "", data = "";
                        for (int i = 0; i < lst_move.size(); i++) {
                            Object[] ObjMv = (Object[]) lst_move.get(i);
                            labels += "\"" + ObjMv[0] + "\"";
                            data += ObjMv[1];
                            if (i < lst_move.size() - 1) {
                                labels += ",";
                                data += ",";
                            }
                        }
                        out.print("<div class=''>");
                        out.print("<div class=\"card\"> "
                                + "                  <div class=\"card-header text-center\"> "
                                + "                    <h4>Salidas x mes</h4> "
                                + "                  </div> "
                                + "                  <div class=\"card-body\"> "
                                + "<canvas id=\"myChart98\"></canvas> "
                                + "</div> "
                                + "</div>");
                        out.print("</div>");

                        out.print("<script>");
                        out.print("var ctx = document.getElementById(\"myChart98\").getContext('2d'); "
                                + "var myChart = new Chart(ctx, { "
                                + "    type: 'line', "
                                + "    data: { "
                                + "        labels: [" + labels + "], "
                                + "        datasets: [{ "
                                + "                label: 'Cantidad', "
                                + "                data: [" + data + "], "
                                + "                borderWidth: 2, "
                                + "                backgroundColor: '#cd545494', "
                                + "                borderColor: '#cd5454', "
                                + "                borderWidth: 2.5, "
                                + "                pointBackgroundColor: '#ffffff', "
                                + "                pointRadius: 4 "
                                + "            }] "
                                + "    }, "
                                + "    options: { "
                                + "        legend: { "
                                + "            display: false "
                                + "        }, "
                                + "        scales: { "
                                + "            yAxes: [{ "
                                + "                    gridLines: { "
                                + "                        drawBorder: false, "
                                + "                        color: '#f2f2f2', "
                                + "                    }, "
                                + "                    ticks: { "
                                + "                        beginAtZero: true, "
                                + "                        stepSize: 25 "
                                + "                    } "
                                + "                }], "
                                + "            xAxes: [{ "
                                + "                    ticks: { "
                                + "                        display: false "
                                + "                    }, "
                                + "                    gridLines: { "
                                + "                        display: false "
                                + "                    } "
                                + "                }] "
                                + "        }, "
                                + "    } "
                                + "});");
                        out.print("</script>");

                    } else {
                        out.print("<div class=''>");
                        out.print("<div class=\"card\"> "
                                + "   <div class=\"card-header text-center\"> "
                                + "        <h4>Salidas x mes</h4> "
                                + "   </div> "
                                + "<div class=\"card-body\"> "
                                + "<span><b>No se han encontrado movimientos</b></span>"
                                + "</div> "
                                + "</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    //</editor-fold>
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                    //</editor-fold>
                } else if (action == 1) {
                    //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                    if (idItem > 0) {
                        //<editor-fold defaultstate="collapsed" desc="EDIT ITEM">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block; z-index: 1500;'>");
                        out.print("<div class='contGeneral' style='width: 44%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Modificar Item </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        lst_item = ItemJpa.ConsultItemxId(idItem);
                        out.print("<form action='TrackingItem?opt=2&idItem=" + idItem + "' method='post' class='needs-validation' novalidate=''>");
                        if (lst_item != null) {
                            Object[] ObjDataitm = (Object[]) lst_item.get(0);
                            String[] dataMove = ObjDataitm[0].toString().split(" // ");
                            out.print("<input type='hidden' class='form-control' name='txtidMov' id='' value='" + dataMove[0] + "'>");
                            out.print("<span class=''>Referencia</span>");
                            out.print("<input type='text' class='form-control disabled' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ObjDataitm[1] + "'>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span class=''>Fecha</span>");
                            out.print("<input type='text' class='form-control disabled' id='' data-toggle='tooltip' data-placement='top' title='' value='" + dataMove[2] + "'>");
                            out.print("<span class=''>Modelo</span>");
                            out.print("<input type='text' class='form-control' name='txtModel' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ObjDataitm[7] + "'>");
                            out.print("<span class=''>Ubicacion</span>");
                            out.print("<input type='text' class='form-control' name='TxtLocation' id='' data-toggle='tooltip' data-placement='top' title='' value='" + dataMove[1] + "'>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span class=''>Item</span>");
                            out.print("<input type='number' class='form-control' name='nmbItem' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ObjDataitm[6] + "'>");
                            out.print("<span class=''>Serial</span>");
                            out.print("<input type='text' class='form-control' name='txtSerial' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ObjDataitm[8] + "'>");
                            out.print("<span class=''>Observaciones</span>");
                            out.print("<input type='text' class='form-control' name='txtObs' id='' data-toggle='tooltip' data-placement='top' title='' value='" + ObjDataitm[9] + "'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='text-center'>");
                            out.print("<button class='btn btn-green'>Modificar</button>");
                            out.print("</div>");

                        }
                        out.print("</form>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="LIST ITEMS">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:" + ((idItem > 0) ? "block" : "none") + ";'>");
                    out.print("<div class='contGeneral' style='width: 80%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Items </h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user' style='max-height: 600px;overflow-x: hidden;overflow-y: auto;'>");
                    out.print("<table class='table table-bordered table-sm' id='table-2' style='font-size: 12px;'>");
                    out.print("<thead>");
                    out.print("<tr class='text-dark text-center'>");
                    out.print("<th>Item</th>");
                    out.print("<th style='max-width: 200px;'>Referencia</th>");
                    out.print("<th>Asignacion</th>");
                    out.print("<th>Datos</th>");
                    out.print("<th>Observaciones</th>");
                    out.print("<th>Usuario Registro</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    lst_item = ItemJpa.ConsultAllItems();
                    if (lst_item != null) {
                        for (int i = 0; i < lst_item.size(); i++) {
                            Object[] objItem = (Object[]) lst_item.get(i);
                            out.print("<tr>");
                            out.print("<td class='text-center text-dark'><button class='btn btn-warning btn-sm' onclick='window.location.href=\"TrackingItem?opt=1&idItem=" + objItem[0] + "&action=1\"'>" + objItem[2] + "</button></td>");
                            out.print("<td>" + objItem[4] + "</td>");
                            out.print("<td><b>Asignado: </b><br>");
                            if (objItem[5] == null && objItem[6] == null) {
                                out.print(" No ");
                            } else if (objItem[5] != null && objItem[6] == null) {
                                out.print(" " + objItem[5] + " ");
                            } else if (objItem[6] != null && objItem[5] == null) {
                                out.print(" " + objItem[6] + " ");
                            }
                            out.print("</td>");
                            out.print("<td><span class=''><b>Modelo: </b>" + objItem[7] + "</span><br><span class=''><b>Serial: </b>" + objItem[8] + "</span></td>");
                            out.print("<td>" + objItem[9] + "</td>");
                            out.print("<td><span data-toggle='tooltip' data-placement='top' title='" + objItem[12] + "'>" + objItem[11] + "</span></td>");
//                            out.print("<td></td>");
                            out.print("</tr>");

                        }
                    }
                    out.print("</tbody>");
                    out.print("</table>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    if (!idTemToSig.equals("")) {
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                        if (docx == 0 && codx == 0) {
                            //<editor-fold defaultstate="collapsed" desc="FORM CONSULT DATA TO SIGNATURE">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='contGeneral' style='width: 44%; right: 21%;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h3>CONSULTAR FIRMA</h3>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<form action='TrackingItem?opt=1&action=1' method='post' class=''>");
                            out.print("<input type='hidden' name='idItmeToSig' id='' value='" + idTemToSig + "'>");
                            out.print("<div class='d-flex align-items-center'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<input type='number' class='form-control' name='docx' id='' placeholder='Documento' value='' required>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<input type='number' class='form-control' name='codx' id='' placeholder='Codigo' value='' required>");
                            out.print("</div>");
                            out.print("<div class='col-lg-2'>");
                            out.print("<button class='btn btn-green'><i class='fas fa-search'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="RESULT SIGANTURE">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                            out.print("<div class='contGeneral' style='width: 50%; right: 20%;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Firma</h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            lst_sirh = SirhJpa.Consultar_SIRH(docx, codx);

                            if (lst_sirh.size() > 0) {
                                //<editor-fold defaultstate="collapsed" desc="PERSONAL INFORMATION">
                                String[] ObjSr = lst_sirh.toString().replace("[", "").replace("]", "").split("///");
                                NameSigna = ObjSr[0].toString();
                                out.print("<div class='d-flex' style='justify-content: end;'>");
                                out.print("<div class='col-lg-2'>");
                                out.print("<span>Nombres:</span><br>");
                                out.print("<span>Documento:</span><br>");
                                out.print("<span>Area:</span><br>");
                                out.print("<span>Codigo:</span>");
                                out.print("</div>");
                                out.print("<div class='col-lg-8'>");
                                out.print("<b> " + ObjSr[0] + " </b><br>");
                                out.print("<b> " + ObjSr[1] + " </b><br>");
                                out.print("<b> " + ObjSr[3] + " </b><br>");
                                out.print("<b> " + ObjSr[2] + " </b>");
                                out.print("</div>");
                                out.print("</div>");
                                docx = Integer.parseInt(ObjSr[1].toString().trim());
                            } else {
//                            out.print("<div class='text-center'>");
//                            out.print("<h4>Se ha presentado un error al consultar la información del empleado.</h4>");
//                            out.print("<i class='fas fa-exclamation-triangle' style='font-size: 60px;'></i>");
//                            out.print("</div>");
                                //</editor-fold>
                            }

                            lst_sirh = SirhJpa.Consultar_firmasDoc(docx, codx);
                            String signt = "";
                            if (lst_sirh != null && lst_sirh.size() > 0) {
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                                String[] ObjSig = lst_sirh.toString().split("///");
                                signt = ObjSig[3];

                                out.print("<div class='text-center mt-4'>");
                                out.print("<h5><b class='text-dark'>Firma Encontrada &nbsp;</b> <i class='fas fa-check' style='color: #33bf98;font-size: 20px;'></i></h5>");

                                out.print("</div>");

                                out.print("<div class='d-flex' style='justify-content: center;margin-top: 20px;'>");
                                out.print("<div class='signature-pad' style='margin-bottom: 20px;'>");
                                out.print("<canvas id='signature-canvas' width='500' height='250' style='pointer-events: none;'></canvas>");
                                out.print("</div>");
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

                                out.print("<form action='TrackingItem?opt=3&action=1' method='post' onsubmit='cargarDatos()'>");
                                out.print("<input type='hidden' name='idItmeToSig' id='' value='" + idTemToSig + "'>");
                                out.print("<input type='hidden' name='idSig' id='' value='" + ObjSig[0].toString().replace("[", "") + "'>");
                                out.print("<input type='hidden' name='docx' id='' value='" + docx + "'>");
                                out.print("<input type='hidden' name='codx' value='" + codx + "'>");
                                out.print("<input type='hidden' name='NameSigna' value='" + NameSigna + "'>");
                                out.print("<div class='text-center'>");
                                out.print("<button class='btn btn-green'>Firmar</button>");
                                out.print("</div>");
                                out.print("</form>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="NEW SIGNATURE">
                                out.print("<div class='text-center mt-4'>");
                                out.print("<h4><b class='text-warning'>Firma no Encontrada &nbsp;</b> <i class='fas fa-exclamation-triangle' style='font-size: 20px;'></i></h4>");
                                out.print("</div>");

                                out.print("<div class='text-center'>");
                                out.print("<h3>Para registrar su firma hacer clic en el siguiente botón.</h3>");
                                out.print("<button class='btn btn-green' onclick='window.open(\"http://172.16.2.111:8084/SIRH/Firmas.jsp\", \"_blank\")'>Registrar Firma</button>");
                                out.print("</div>");
                                //</editor-fold>
                            }

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<script>"
                                    + " document.addEventListener('DOMContentLoaded', function() {"
                                    + "    function toggleClass() {"
                                    + "        const body = document.body;"
                                    + "        body.classList.add('modal-open');"
                                    + "    }"
                                    + "    toggleClass();"
                                    + " });"
                                    + "</script>");

//</editor-fold>
                        }
                        //</editor-fold>
                    }

                    //<editor-fold defaultstate="collapsed" desc="SEARCH FORM">
                    out.print("<section class='section'>");
                    out.print("<div class='section-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");
                    out.print("<div class='card' style='margin-bottom: 0;'>");
                    out.print("<div class='card-header' style='justify-content: space-between;'>");
                    out.print("<button style='border-radius: 4px;' class='btn btn-green' onclick='window.location.href=\"TrackingItem?opt=1&action=0\"'><i class='fas fa-arrow-left'></i></button>");
                    out.print("<h2>Seguimiento a Items</h2>");
                    out.print("<button style='border-radius: 4px;' class='btn btn-green' onclick='mostrarConvencion(2)'>Listado de items</button>");
                    out.print("</div>");
                    out.print("<div class='card-body'>");
                    out.print("<div class='table-responsive'>");

                    out.print("<div id='accordion'>");
                    out.print("<div class='accordion'>");
                    out.print("<div id='accFunc' class='accordion-header text-center " + ((lst_result != null) ? "collapsed" : "") + "' role='button' data-toggle='collapse' data-target='#panel-body-1' aria-expanded='" + ((lst_result == null) ? "true" : "false") + "'>");
                    out.print("<h4>Filtro de busqueda <i class='fas fa-search'></i></h4>");
                    out.print("</div>");
                    out.print("<div class='accordion-body collapse " + ((lst_result == null) ? "show" : "") + "' id='panel-body-1' data-parent='#accordion'>");
                    out.print("<div class='text-center mt-3'>");
                    out.print("<h4>Buscar movimientos realizados:</h4>");
                    out.print("</div>");
                    out.print("<div class=''>");
                    out.print("<form action='TrackingItem?opt=1&action=1' method='post' class='needs-validation' novalidate='' id='FormMoveSearch'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-lg-4 mt-3'>");
                    out.print("<input type='number' class='form-control' name='txt_numItem' id='id_numItem' data-toggle='tooltip' data-placement='top' title='Num. Item' placeholder='Num. Item' value=''>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 mt-3'>");
                    out.print("<input type='text' class='form-control' name='txt_Ref' id='id_ref' data-toggle='tooltip' data-placement='top' title='Referencia' placeholder='Referencia' value=''>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 mt-3'>");
                    out.print("<input type='date' class='form-control' name='txt_dateMove' id='id_dateMove' data-toggle='tooltip' data-placement='top' title='Fecha Movimiento' placeholder='Fecha Movimiento' value=''>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 mt-3'>");
                    out.print("<input type='number' class='form-control' name='txt_numMov' id='id_numMov' data-toggle='tooltip' data-placement='top' title='Num. Movimiento' placeholder='Num. Movimiento' value=''>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 mt-3'>");
                    out.print("<input type='text' class='form-control' name='txt_keyword' id='id_keyword' data-toggle='tooltip' data-placement='top' title='Palabra clave' placeholder='Palabra clave' value=''>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 mt-3'>");
                    out.print("<button class='btn btn-green' type='button' style='width: 100%;' onclick='validformSearch()'>Buscar.. <i class='fas fa-search'></i></button>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="LIST SERACHED">
                    if (lst_result != null) {
                        out.print("<div class='card'>");
                        out.print("<div class='card-body'>");
                        out.print("<form action='TrackingItem?opt=1&action=1' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");
                        out.print("<input type='hidden' name='idItmeToSig' id='idItmeToSig' value=''>");
                        out.print("<div class='text-right' style='margin: 10px;'>");
                        out.print("<button class='btn btn-green' id='btnUserSign' style='display: none;'>Firmar</button>");
                        out.print("</form>");
                        out.print("</div>");

                        out.print("<table class='table table-striped' id='table-1' style='font-size: 12px;'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th></th>");
                        out.print("<th style='max-width: 150px;'></th>");
                        out.print("<th style='min-width: 160px;'></th>");
                        out.print("<th></th>");
                        out.print("<th></th>");
                        out.print("<th></th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        for (int i = 0; i < lst_result.size(); i++) {
                            Object[] ObjRs = (Object[]) lst_result.get(i);
                            out.print("<tr>");
                            out.print("<td colspan=''>");
                            out.print("<div style='display: flex; justify-content: center;'>");
                            out.print("<span class='badge badge-" + ((ObjRs[6].toString().contains("ENT")) ? "success" : "info") + " text-dark'><b>ITEM: </b>" + ObjRs[3] + "</span>");
                            out.print("</div>");
                            out.print("<div style='text-align: center;margin-top: 10px;'>");
                            out.print("<span class='text-dark'><b>" + ObjRs[6] + ObjRs[7] + "</b></span>");
                            out.print("</div>");
                            out.print("</td>");

                            out.print("<td colspan=''>");
                            out.print("<div class=''>");
                            out.print("<span><b class='text-dark'>FECHA: </b>" + ObjRs[1] + "</span>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<span data-toggle='tooltip' data-placement='top' title='" + ObjRs[4] + "'><b class='text-dark'>REFERENCIA: </b>" + ObjRs[5] + "</span>");
                            out.print("</div>");
                            out.print("</td>");

                            out.print("<td colspan=''>");
                            out.print("<div class=''>");
                            out.print("<span><b class='text-dark'>UBICACION: </b>" + ObjRs[8] + "</span>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<span><b class='text-dark'>MODELO: </b>" + ObjRs[9] + "</span>");
                            out.print("</div>");
                            out.print("</td>");

                            out.print("<td colspan=''>");
                            out.print("<div class=''>");
                            out.print("<span><b class='text-dark'>SERIAL: </b>" + ObjRs[10] + "</span>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<span><b class='text-dark'>OBSERVACIONES: </b>" + ObjRs[11] + "</span>");
                            out.print("</div>");
                            out.print("</td>");

                            out.print("<td colspan=''>");
                            out.print("<div class=''>");
                            out.print("<span><b class='text-dark'>REGISTRO: </b>" + ObjRs[13] + "</span>");
                            out.print("</div>");

                            out.print("</td>");

                            out.print("<td>");
                            out.print("<div class='text-center'>");
                            if (ObjRs[14] == null) {
                                out.print("<button id='btnSelec" + i + "' class='btn btn-green btn-sm' onclick='SelectedSigna(" + i + ", " + ObjRs[0] + ")'><i class='fas fa-signature'></i></button>");
                            } else {
                                try {
                                    String[] sigStru = ObjRs[14].toString().split("/");
                                    out.print("<div class='text-center' data-toggle='tooltip' data-placement='top' title='" + sigStru[1] + " - " + sigStru[2] + "'>");
                                    out.print("<span><b>VERIFICADO </b></span><span class=''><i class='text-success fas fa-check'></i></span><br>");
                                    out.print("<span class='text-sm'>" + ObjRs[15] + "</span>");
                                    out.print("</div>");
                                } catch (Exception e) {
                                    out.print("<span class=''>Error al cargar firma</span>");
                                }
                            }
                            out.print("</div>");
                            out.print("</td>");

                            out.print("</tr>");

                        }
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("</div>");

                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</section>");
                    } else {
                        out.print("<div class='card'>");
                        out.print("<div class='card-body text-center'>");
                        out.print("<h3>No se han encontrado resultados de la busqueda! </h3>");
                        out.print("<img src='Interface/Imagen/vacio.png' alt=''>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    //</editor-fold>
                }
            } catch (Exception e) {
            }
        } catch (Exception ex) {
            Logger.getLogger(Tag_trackingItem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }

}
