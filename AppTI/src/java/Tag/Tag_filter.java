package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.FilterJpaController;
import Controller.CustomerControllerJpa;
import java.util.List;
import java.util.regex.*;

public class Tag_filter extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        FilterJpaController FilterJpa = new FilterJpaController();
        CustomerControllerJpa CustomerJpa = new CustomerControllerJpa();
        List lst_binnacle = null, lst_pending = null, lst_ticket = null, lst_supportId = null;
        String Date = "", Module = "";
        String[] PersonActive = {}, PersonInactive = {}, Data = {};
        try {
            try {
                Date = pageContext.getRequest().getAttribute("Date").toString();
            } catch (Exception e) {
                Date = "";
            }
            try {
                Object dataObj = pageContext.getRequest().getAttribute("Data");
                if (dataObj instanceof String[]) {
                    Data = (String[]) dataObj;
                }
            } catch (Exception e) {
                Data = new String[0];
            }
            try {
                Object activeObj = pageContext.getRequest().getAttribute("PersonActive");
                if (activeObj instanceof String[]) {
                    PersonActive = (String[]) activeObj;
                }
            } catch (Exception e) {
                PersonActive = new String[0];
            }
            try {
                Object inactiveObj = pageContext.getRequest().getAttribute("PersonInactive");
                if (inactiveObj instanceof String[]) {
                    PersonInactive = (String[]) inactiveObj;
                }
            } catch (Exception e) {
                PersonInactive = new String[0];
            }
            try {
                Module = pageContext.getRequest().getAttribute("Module").toString();
            } catch (Exception e) {
                Module = "";
            }
            out.print("<section class='section'>");
            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='justify-content: space-between;'>");
            out.print("<div>"
                    + "<h4>Filtro de busqueda</h4>"
                    + "</div>");
            out.print("</div>");

            out.print("<div class='card-body'>");
            if (Module.contains("[1]")) {
                lst_binnacle = FilterJpa.ConsultFilterBinnacle(Date, Data, PersonActive, PersonInactive);
            } else {
                lst_binnacle = null;
            }
            if (Module.contains("[2]")) {
                lst_pending = FilterJpa.ConsultFilterPending(Date, Data, PersonActive, PersonInactive);
            } else {
                lst_pending = null;
            }
            if (Module.contains("[3]")) {
                lst_ticket = FilterJpa.ConsultFilterTicket(Date, Data, PersonActive, PersonInactive);
            } else {
                lst_ticket = null;

            }
            out.print("<ul class=\"nav nav-tabs justify-content-center\" id=\"myTab2\" role=\"tablist\">\n"
                    + "                      <li class=\"nav-item\">\n"
                    + "                        <a class=\"nav-link active\" id=\"binnacle\" data-toggle=\"tab\" href=\"#binnacle2\" role=\"tab\" aria-controls=\"binnacle\" aria-selected=\"true\"><div class='d-flex'><h5 class='mr-1'>Bitacora </h5> <input class=\"ConventionCant\" value='" + ((lst_binnacle != null) ? lst_binnacle.size() : "0") + "' readonly=\"\"></div></a>\n"
                    + "                      </li>\n"
                    + "                      <li class=\"nav-item\">\n"
                    + "                        <a class=\"nav-link\" id=\"pending\" data-toggle=\"tab\" href=\"#pending2\" role=\"tab\" aria-controls=\"pending\" aria-selected=\"false\"><div class='d-flex'><h5 class='mr-1'>Pendiente</h5><input class=\"ConventionCantPending\" value='" + ((lst_pending != null) ? lst_pending.size() : "0") + "' readonly=\"\"></div></a>\n"
                    + "                      </li>\n"
                    + "                      <li class=\"nav-item\">\n"
                    + "                        <a class=\"nav-link\" id=\"ticket\" data-toggle=\"tab\" href=\"#ticket2\" role=\"tab\" aria-controls=\"ticket\" aria-selected=\"false\"><div class='d-flex'><h5 class='mr-1'>Ticket</h5><input class=\"ConventionCantTicket\" value='" + ((lst_ticket != null) ? lst_ticket.size() : "0") + "' readonly=\"\"></div></a>\n"
                    + "                      </li>\n"
                    + "                    </ul>");

            out.print("<div class=\"tab-content tab-bordered\" id=\"myTab3Content\">");

            out.print("<div class=\"tab-pane fade show active\" id=\"binnacle2\" role=\"tabpanel\" aria-labelledby=\"binnacle\" style='border:none;'>");
            out.print("<div class='InputFilter'><input style='width:91%' type=\"text\" class='form-control' id=\"myInput\" placeholder=\"Buscar...\"></div>");
            //<editor-fold defaultstate="collapsed" desc="BINNACLE">
            if (lst_binnacle != null) {
                out.print("<div id='container' class=\"container\">");
                for (int i = 0; i < lst_binnacle.size(); i++) {
                    Object[] ObjBinnacle = (Object[]) lst_binnacle.get(i);
                    out.print("<div class=\"single-item\">");
                    if (ObjBinnacle[3] != null) {
                        out.print("<div class='d-flex justify-content-around DivHeadBinnacle '>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Usuario: </b><span class='b_the_black'>" + ((ObjBinnacle[8] == null) ? "" : ObjBinnacle[8]) + "</span></div>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Turno: </b><span class='b_the_black'>" + ((ObjBinnacle[4] == null) ? "" : ObjBinnacle[4]) + "</span></div>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Fecha Inicial: </b><span class='b_the_black'>" + ((ObjBinnacle[1] == null) ? "" : ObjBinnacle[1]) + "</span></div>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Fecha Final: </b><span class='b_the_black'>" + ((ObjBinnacle[2] == null) ? "" : ObjBinnacle[2]) + "</span></div>");
                        out.print("</div>");
                        out.print("<div class='DivContentBinnacle mb-2'>");
                        String FormatterBinnacle = "";
                        FormatterBinnacle = ObjBinnacle[3].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                        if (Data.length > 0) {
                            for (int j = 0; j < Data.length; j++) {
                                String palabra = Pattern.quote(Data[j]); // Escapar caracteres especiales
                                Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                                Matcher matcher = pattern.matcher(FormatterBinnacle);
                                FormatterBinnacle = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[j].toUpperCase() + "</b>");

                            }
                        }
                        FormatterBinnacle = FormatterBinnacle.replace("<img", "<img style='width:30%'");

                        out.print("<div class='ml-2 mr-2 mb-2 TextBinnacle'>" + FormatterBinnacle + "</div>");
                        out.print("</div>");

                    }
                    out.print("</div>");
                }
                out.print("</div>");
            } else {
                out.print("<h5 class='text-center'><i style='font-size:20px' class=\"far fa-surprise\"></i>&nbsp;SIN DATOS FILTRADOS O ENCONTRADOS</h5>");
            }
            //</editor-fold>
            out.print("</div>");

            out.print("<div class=\"tab-pane fade\" id=\"pending2\" role=\"tabpanel\" aria-labelledby=\"pending\" style='border:none;'>");
            out.print("<div class='InputFilter'><input style='width:91%' type=\"text\" class='form-control' id=\"myInput2\" placeholder=\"Buscar...\"></div>");
            //<editor-fold defaultstate="collapsed" desc="PENDING">
            if (lst_pending != null) {
                out.print("<div id='container2' class=\"container\">");
                for (int i = 0; i < lst_pending.size(); i++) {
                    Object[] ObjPending = (Object[]) lst_pending.get(i);
                    out.print("<div class=\"single-item2\">");
                    if (ObjPending[3] != null) {
                        out.print("<div class='d-flex justify-content-around DivHeadPending '>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Asunto: </b><span class='b_the_black'>" + ((ObjPending[1] == null) ? "" : ObjPending[1]) + "</span></div>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Para: </b><span class='b_the_black'>" + ((ObjPending[2] == null) ? "" : ObjPending[2]) + "</span></div>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>De: </b><span class='b_the_black'>" + ((ObjPending[7] == null) ? "" : ObjPending[7]) + "</span></div>");
                        out.print("<div class='TxtHeadBinnacle'><b class='b_the_black'>Proceso: </b><span class='b_the_black'>" + ((ObjPending[4] == null) ? "" : ObjPending[4] + "%") + "</span></div>");
                        out.print("</div>");
                        out.print("<div class='DivContentBinnacle mb-2'>");
                        //<editor-fold defaultstate="collapsed" desc="DESCRIPTION">
                        String FormatterPending = "";
                        FormatterPending = ObjPending[3].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                        if (Data.length > 0) {
                            for (int j = 0; j < Data.length; j++) {
                                String palabra = Pattern.quote(Data[j]); // Escapar caracteres especiales
                                Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                                Matcher matcher = pattern.matcher(FormatterPending);
                                FormatterPending = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[j].toUpperCase() + "</b>");
                            }
                        }

                        FormatterPending = FormatterPending.replace("<img", "<img style='width:30%'");
                        out.print("<div class='ml-2 mr-2 mb-2 TextPending DivPedingS'><h6>DESCRIPCIÓN</h6></hr>" + FormatterPending + "</div>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="SOLUTION">
                        String FormatterPendingSolution = "";
                        FormatterPendingSolution = ObjPending[5].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                        if (Data.length > 0) {
                            for (int j = 0; j < Data.length; j++) {
                                String palabra = Pattern.quote(Data[j]); // Escapar caracteres especiales
                                Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                                Matcher matcher = pattern.matcher(FormatterPendingSolution);
                                FormatterPendingSolution = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[j].toUpperCase() + "</b>");
                                FormatterPendingSolution = FormatterPendingSolution.replace(Data[j], "<b style='background: #f0ff05;'>" + Data[j] + "</b>");
                            }
                        }
                        FormatterPendingSolution = FormatterPendingSolution.replace("<img", "<img style='width:30%'");

                        if (FormatterPendingSolution.contains("[")) {
                            String[] ArrDetail = FormatterPendingSolution.split("///");
                            out.print("<div class='ml-2 mr-2 mb-2 TextPending'><h6 class='text-center'>SEGUIMIENTO <input class=\"ConventionCantPendingSeg\" value='" + ArrDetail.length + "' readonly=\"\"></h6></hr>");
                            for (int j = 0; j < ArrDetail.length; j++) {
                                String[] ArrDetailRlc = ArrDetail[j].replace("][", "///").replace("[", "").replace("]", "").split("///");
                                out.print("<div class='DivFolowUp'>");

                                out.print("<div class='d-flex justify-content-between'>");

                                out.print("<div class='d-flex '>");
                                out.print(ArrDetailRlc[2]);
                                out.print("</div>");

                                out.print("<div>");
                                out.print("<b>Progreso</b> (" + ArrDetailRlc[3] + "%)");
                                out.print("</div>");

                                out.print("</div>");

                                out.print("<div class='d-flex justify-content-between'>");
                                out.print("<div><b>Observación:</b></div>");
                                out.print("<div>" + ArrDetailRlc[0].replace("T", " ") + "</div>");
                                out.print("</div>");

                                out.print("<div>" + ArrDetailRlc[1].replace("<img", "<img style='width:30%'") + "</div>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                        } else {
                            out.print("<div class='m-2 TextBinnacle'><h6 class='text-center'>SEGUIMIENTO</h6></hr>" + FormatterPendingSolution + "</div>");
                        }
                        //</editor-fold>
                        out.print("</div>");
                    }
                    out.print("</div>");
                }
                out.print("</div>");
            } else {
                out.print("<h5 class='text-center'><i style='font-size:20px' class=\"far fa-surprise\"></i>&nbsp;SIN DATOS FILTRADOS O ENCONTRADOS</h5>");
            }
            //</editor-fold>
            out.print("</div>");

            out.print("<div class=\"tab-pane fade\" id=\"ticket2\" role=\"tabpanel\" aria-labelledby=\"ticket\" style='border:none;'>");
            out.print("<div class='InputFilter'><input style='width:91%' type=\"text\" class='form-control' id=\"myInput3\" placeholder=\"Buscar...\"></div>");
            //<editor-fold defaultstate="collapsed" desc="TICKET">
            if (lst_ticket != null) {
                out.print("<div id='container3' class=\"container\">");
                for (int i = 0; i < lst_ticket.size(); i++) {
                    Object[] ObjTicket = (Object[]) lst_ticket.get(i);
                    out.print("<div class=\"single-item3\">");
                    out.print("<div class='d-flex justify-content-around DivHeadTicket '>");
                    out.print("<div class='TxtHeadTicket'><b class='b_the_black'>#Ticket: </b><span class='b_the_black'>" + ((ObjTicket[0] == null) ? "" : ObjTicket[0]) + "</span></div>");
                    out.print("<div class='TxtHeadTicket' style='width:23%'><b class='b_the_black'>Fecha: </b><span class='b_the_black'>" + ((ObjTicket[1] == null) ? "" : ObjTicket[1]) + "</span></div>");
                    out.print("<div class='TxtHeadTicket'><b class='b_the_black'>Área: </b><span class='b_the_black'>" + ((ObjTicket[4] == null) ? "" : ObjTicket[4]) + "</span></div>");
                    out.print("<div class='TxtHeadTicket' style='width:37%'><b class='b_the_black'>Solicitante: </b><span class='b_the_black'>" + ((ObjTicket[6] == null) ? "" : ObjTicket[6]) + "</span></div>");
                    out.print("<div class='TxtHeadTicket'><b class='b_the_black'>Prioridad: </b><span class='b_the_black'>" + ((ObjTicket[2] == null) ? "" : ((ObjTicket[2] == "1") ? "ALTA" : (ObjTicket[2] == "2") ? "MEDIA" : "BAJA") + "") + "</span></div>");
                    out.print("</div>");
                    //<editor-fold defaultstate="collapsed" desc="MAJOR TICKET">
                    String FormatterTicket = "";
                    FormatterTicket = ObjTicket[7].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                    if (Data.length > 0) {
                        for (int j = 0; j < Data.length; j++) {
                            String palabra = Pattern.quote(Data[j]); // Escapar caracteres especiales
                            Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                            Matcher matcher = pattern.matcher(FormatterTicket);
                            FormatterTicket = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[j].toUpperCase() + "</b>");
                        }
                    }

                    FormatterTicket = FormatterTicket.replace("<img", "<img style='width:30%'");
                    out.print("<div class='m-2 TextTicket DivPedingS'>" + FormatterTicket + "</div>");

                    String FormatterTicketSolution = "";
                    FormatterTicketSolution = ObjTicket[14].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                    if (Data.length > 0) {
                        for (int j = 0; j < Data.length; j++) {
                            String palabra = Pattern.quote(Data[j]); // Escapar caracteres especiales
                            Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                            Matcher matcher = pattern.matcher(FormatterTicketSolution);
                            FormatterTicketSolution = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[j].toUpperCase() + "</b>");
                        }
                    }
                    FormatterTicketSolution = FormatterTicketSolution.replace("<img", "<img style='width:30%'");

                    out.print("<div class='DivFolowUp'>");

                    out.print("<div class='d-flex justify-content-between'>");

                    out.print("<div>");
                    out.print("<b>Funcionario T.I:</b> " + ObjTicket[13]);
                    out.print("</div>");

                    out.print("<div>");
                    out.print("<b>Fecha:</b> " + ObjTicket[15]);
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='d-flex justify-content-between'>");

                    out.print("<div><b>Descripción:</b> " + FormatterTicketSolution + "</div>");

                    out.print("<div>"
                            + "<div><b>Parada PC: </b>" + ((ObjTicket[16] == null) ? "" : ObjTicket[16]) + "</div>"
                            + "<div><b>Parada Producción: </b>" + ((ObjTicket[17] == null) ? "" : ObjTicket[17]) + "</div>"
                            + "</div>");

                    out.print("</div>");

                    out.print("</div>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="TICKET RE-OPEN">
                    lst_supportId = CustomerJpa.ConsultReopeningTicket(Integer.parseInt(ObjTicket[0].toString()));
                    if (lst_supportId != null) {
                        String SumTicket = "";
                        for (int j = 0; j < lst_supportId.size(); j++) {
                            Object[] ObjTicketOpen = (Object[]) lst_supportId.get(j);
                            SumTicket = (j + 1) + "";
                            if (ObjTicketOpen[3] != null) {
                                String FormatterTicketOpen = "";
                                FormatterTicketOpen = ObjTicketOpen[3].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                                if (Data.length > 0) {
                                    for (int y = 0; y < Data.length; y++) {
                                        String palabra = Pattern.quote(Data[y]); // Escapar caracteres especiales
                                        Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                                        Matcher matcher = pattern.matcher(FormatterTicketOpen);
                                        FormatterTicketOpen = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[y].toUpperCase() + "</b>");
                                    }
                                }

                                FormatterTicketOpen = FormatterTicketOpen.replace("<img", "<img style='width:30%'");
                                out.print("<div class='ml-2 mr-2 mb-2 TextTicket DivPedingS'><div><b class='b_black'>Re-apertura #</b>" + SumTicket + "</div>" + FormatterTicketOpen + "</div>");
                            }
                            if (ObjTicketOpen[4] != null) {
                                String FormatterTicketSolutionOpen = "";
                                FormatterTicketSolutionOpen = ObjTicketOpen[4].toString().trim().replaceAll("(?i)<p>(&nbsp;|\\s)*</p>", "");
                                if (Data.length > 0) {
                                    for (int k = 0; k < Data.length; k++) {
                                        String palabra = Pattern.quote(Data[k]); // Escapar caracteres especiales
                                        Pattern pattern = Pattern.compile("(?i)" + palabra); // Ignorar mayúsculas
                                        Matcher matcher = pattern.matcher(FormatterTicketSolutionOpen);
                                        FormatterTicketSolutionOpen = matcher.replaceAll("<b style='background: #f0ff05;'>" + Data[k].toUpperCase() + "</b>");
                                    }
                                }
                                FormatterTicketSolutionOpen = FormatterTicketSolutionOpen.replace("<img", "<img style='width:30%'");
//
                                out.print("<div class='DivFolowUp'>");

                                out.print("<div class='d-flex justify-content-between'>");

                                out.print("<div class='d-flex '>");
                                out.print("<b>Funcionario T.I:</b> " + ObjTicketOpen[7]);
                                out.print("</div>");

                                out.print("<div>");
                                out.print("<b>Fecha</b> " + ObjTicketOpen[2]);
                                out.print("</div>");

                                out.print("</div>");

                                out.print("<div class='d-flex justify-content-between'>");
                                out.print("<div><b>Solución:</b> " + FormatterTicketSolutionOpen + "</div>");
                                out.print("</div>");

                                out.print("</div>");
                            }
                        }
                    }
//                    //</editor-fold>
                    out.print("</div>");
                }
                out.print("</div>");
            } else {
                out.print("<h5 class='text-center'><i style='font-size:20px' class=\"far fa-surprise\"></i>&nbsp;SIN DATOS FILTRADOS O ENCONTRADOS</h5>");
            }
            //</editor-fold>
            out.print("</div>");

            out.print("</div>");

            out.print("</div>");

            out.print("</section>");
        } catch (IOException ex) {
            Logger.getLogger(Tag_filter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
