package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.SupportControllerJpa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.http.HttpSession;
import Controller.AreaControllerJpa;
import Controller.AppControllerJpa;
import Controller.CustomerControllerJpa;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Tag_support extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        SupportControllerJpa SupportJpa = new SupportControllerJpa();
        AreaControllerJpa AreaJpa = new AreaControllerJpa();
        AppControllerJpa AppJpa = new AppControllerJpa();
        CustomerControllerJpa CustomerJpa = new CustomerControllerJpa();
        List lst_support = null;
        List lst_area = null;
        List lst_app = null;
        List lst_customer = null;
        JspWriter out = pageContext.getOut();
        String section = "", dateIn = "", dateFin = "";
        int temp = 0;
        try {
            int idArea = 0;
            int idTicket = 0;
            try {
                idArea = Integer.parseInt(pageContext.getRequest().getAttribute("id_area").toString());
            } catch (Exception e) {
                idArea = 0;
            }
            try {
                idTicket = Integer.parseInt(pageContext.getRequest().getAttribute("idTicket").toString());
            } catch (Exception e) {
                idTicket = 0;
            }
            try {
                section = pageContext.getRequest().getAttribute("Section").toString();
            } catch (Exception e) {
                section = "Main";
            }

            if (section.equals("Main")) {
                //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                if (idArea > 0) {
                    //<editor-fold defaultstate="collapsed" desc="LOAD TICKETS - AREA">
                    if (idTicket > 0) {
                        LocalDateTime fechaHoraActual = LocalDateTime.now();
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String fechaFormateada = fechaHoraActual.format(formato);
                        //<editor-fold defaultstate="collapsed" desc="CLOSE TICKET">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 60%; right: 10%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<div class=''><button class='btn btn-yellow'><i class='fas fa-arrow-left'></i></button></div>");
                        out.print("<div class='d-flex'><h2>&nbsp;Cerrar Ticket</h2></div>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        lst_support = SupportJpa.ConsultTicketId(idTicket);
                        if (lst_support != null) {
                            Object[] ObjTick = (Object[]) lst_support.get(0);
                            int typeSpt = Integer.parseInt(ObjTick[9].toString());
                            out.print("<div class=''>");
                            out.print("<div class='d-flex' style='justify-content: space-evenly'>");
                            out.print("<div class=''>");
                            out.print("<span>No. Ticket: </span> <br> <p class='lead'>" + idTicket + "</p>");
                            out.print("</div>");
                            String detail = "";
                            if (typeSpt == 1) {
                                out.print("<div class=''>");
                                out.print("<span>Tipo de soporte: </span> <br> <p class='lead'> Equipo del usuario </p>");
                                out.print("</div>");
                                out.print("<div class=''>");
                                out.print("<span>Detalle: </span> <br> <p class='lead'>" + ObjTick[11] + "</p>");
                                out.print("</div>");
                            } else if (typeSpt == 2) {
                                out.print("<div class=''>");
                                out.print("<span>Tipo de soporte: </span> <br> <p class='lead'> Aplicativo </p>");
                                out.print("</div>");
                                out.print("<div class=''>");
                                int idApp = Integer.parseInt(ObjTick[10].toString());
                                lst_app = AppJpa.ConsultAppIdActive(idApp);
                                if (lst_app != null) {
                                    Object[] ObjApp = (Object[]) lst_app.get(0);
                                    out.print("<span>Detalle: </span> <br> <p class='lead'>" + ObjApp[1] + "</p>");
                                }
                                out.print("</div>");
                            } else if (typeSpt >= 3) {
                                out.print("<div class=''>");
                                out.print("<span>Tipo de soporte: </span> <br> <p class='lead'> Otro dispositivo </p>");
                                out.print("</div>");
                                out.print("<div class=''>");
                                int idType = Integer.parseInt(ObjTick[10].toString());
                                lst_customer = CustomerJpa.ConsultTypeSupportById(idType);
                                if (lst_customer != null) {
                                    Object[] ObjStp = (Object[]) lst_customer.get(0);
                                    detail = ObjStp[1].toString();
                                    out.print("<span>Detalle: </span> <br> <p class='lead'>" + ObjStp[1] + "</p>");
                                }
                                out.print("</div>");
                            }
                            out.print("<div class='text-center'>");
                            out.print("<span>Ver ticket: <br> </span>");
                            out.print("<button class='btn btn-yellow btn-sm' onclick='mostrarConvencion(6)'><i class='fas fa-search'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            int reop = Integer.parseInt(ObjTick[21].toString());
                            out.print("<form action='Support?opt=2&idArea=" + idArea + "&idTicket=" + idTicket + "" + ((reop > 0) ? "&subTicket=" + reop + "" : "") + "' method='post' class=''>");
                            out.print("<input type='hidden' class='form-control' name='typeTicket' id='' value='" + typeSpt + "'>");
                            out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                            if (typeSpt >= 3) {
                                out.print("<div class='mb-4 col-lg-6'>");
                                out.print("<span>Seleccionar dispositivo: </span> <br> ");
                                out.print("<select class='select2' name='Cbx_device'>");
                                out.print("<option selected disabled value=''>Dispositivos...</option>");
                                lst_customer = CustomerJpa.ConsultDiviceByTpe(detail);
                                if (lst_customer != null) {
                                    for (int i = 0; i < lst_customer.size(); i++) {
                                        Object[] ObjDevice = (Object[]) lst_customer.get(i);
                                        out.print("<option value='" + ObjDevice[0] + "'>" + ObjDevice[3] + "</option>");
                                    }
                                } else {
                                    lst_support = SupportJpa.ConsultDeviceActive();
                                    if (lst_support != null) {
                                        for (int i = 0; i < lst_support.size(); i++) {
                                            Object[] ObjDevce = (Object[]) lst_support.get(i);
                                            out.print("<option value='" + ObjDevce[0] + "'>" + ObjDevce[3] + "</option>");
                                        }
                                    }
                                }
                                out.print("</select>");
                                out.print("</div>");
                            }
                            out.print("<div class='mb-4'>");
                            out.print("<span>Fecha: </span> <br> ");
                            out.print("<input type='datetime-local' class='form-control' style='margin: 0;' name='txtDateFinish' id='' data-toggle='tooltip' data-placement='top' title='' value='" + fechaFormateada + "'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<textarea class='summernote' name='txtSolution' placeholder='' required></textarea>");
                            out.print("<div class='text-center'>");
                            out.print("<button class='btn btn-green'>Cerrar ticket</button>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                        } else {
                            out.print("<h4>No se ha encontrado información del ticket.</h4>");
                        }
//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="TICKET DETAIL">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='contGeneral' style='width: 57%; right: 11%; top: 82px;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Detalle del ticket </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        lst_support = SupportJpa.ConsultTicketId(idTicket);
                        if (lst_support != null) {
                            Object[] ObjSpt = (Object[]) lst_support.get(0);

                            out.print("<div class=''>");

                            out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                            out.print("<div class=''>");
                            out.print("<span class=''>Fecha ticket</span><br>");
                            out.print("<p class='lead'>" + ObjSpt[1] + "</p>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<span class=''>Usuario</span>");
                            out.print("<p class='lead'>" + ObjSpt[6] + "</p>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<span class=''>Prioridad</span>");
                            int prio = Integer.parseInt(ObjSpt[8].toString());
                            out.print("<p class='lead'><span " + ((prio == 1) ? "class='text-dark'> Baja" : ((prio == 2) ? "class='text-warning'> Media" : "class='text-danger'> Alta")) + "</span></p>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                            out.print("<div class=''>");
                            out.print("<span class=''>Tipo de ticket</span>");
                            int typeStp = Integer.parseInt(ObjSpt[9].toString());
                            out.print("<p class='lead'>" + ((typeStp == 1) ? "Pc del usuario" : (typeStp == 2) ? "Aplicativo" : (typeStp >= 3) ? "Otro Dispositivo" : "Error") + "</p>");
                            out.print("</div>");

                            out.print("<div class=''>");
                            out.print("<span class=''>Detalle</span>");
                            if (typeStp == 1) {
                                out.print("<p class='lead'> " + ObjSpt[11].toString() + " </p>");
                            } else if (typeStp == 2) {
                                out.print("<div class=''>");
                                out.print("<div class=''>");
                                int idApp = Integer.parseInt(ObjSpt[10].toString());
                                lst_app = AppJpa.ConsultAppIdActive(idApp);
                                if (lst_app != null) {
                                    Object[] ObjApp = (Object[]) lst_app.get(0);
                                    out.print("<p class='lead'>" + ObjApp[1] + "</p>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                            } else if (typeStp == 3) {
                                int idTypeX = Integer.parseInt(ObjSpt[10].toString());
                                lst_customer = CustomerJpa.ConsultTypeSupportById(idTypeX);
                                if (lst_customer != null) {
                                    Object[] ObjStp = (Object[]) lst_customer.get(0);
                                    out.print("<p class='lead'>" + ObjStp[1] + "</p>");
                                }
                            }
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class=''>");
                            out.print("<span class=''>Contenido del ticket</span>");
                            out.print("<div>");
                            out.print(ObjSpt[7]);

                            int idSppt = idTicket;
                            lst_customer = CustomerJpa.ConsultReopeningTicket(idSppt);
                            if (lst_customer != null) {
                                for (int j = 0; j < lst_customer.size(); j++) {
                                    Object[] Objs = (Object[]) lst_customer.get(j);
                                    out.print("<div class=''>");

                                    out.print("<div class='text-center text-dark' style='background: #ffa426;'>");
                                    out.print("<span class=''>Ticket re-abierto " + ((j == 0) ? "(Nuevo)" : "") + "</span>");
                                    out.print("</div>");

                                    out.print("<div class='d-flex'>");

                                    out.print("<div class='col-lg-3'>");
                                    out.print("<span class=''><b>Fecha:</b></span><br>");
                                    out.print("<span class=''>" + Objs[2] + "</span>");
                                    out.print("</div>");
                                    out.print("<div class='col-lg-9'>");
                                    out.print("<span class=''><b>Motivo:</b></span><br>");
                                    out.print("<span class=''>" + Objs[3] + "</span>");
                                    out.print("</div>");

                                    out.print("</div>");

                                    out.print("</div>");
                                }
                            }

                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='text-center'>");
                            out.print("<button class='btn btn-green' onclick='mostrarConvencion(6)'>Cerrar&nbsp;<i class='fas fa-times'></i></button>");
                            out.print("</div>");

                            out.print("</div>");

                        } else {
                            out.print("<div class=''>");
                            out.print("<h5>Se ha presentado un error al consultar el detalle del ticket</h5>");
                            out.print("</div>");
                        }
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
//</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="TICKETS BY AREA">
                        lst_support = SupportJpa.ConsultSupportByArea(idArea);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 80%;'>");
                        if (lst_support != null) {
                            Object[] dataGen = (Object[]) lst_support.get(0);
                            String area = dataGen[4].toString();
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h4></h4>");
                            out.print("<h4>Tickets de " + area + "</h4>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            //<editor-fold defaultstate="collapsed" desc="LIST LEFT">
                            out.print("<div class=\"tickets\">");
                            out.print("<div class=\"ticket-items ScrollDivTicket mt-2\" id=\"ticket-items\" >");
                            for (int i = 0; i < lst_support.size(); i++) {
                                Object[] ObjSupp = (Object[]) lst_support.get(i);
                                out.print("<div class='ticket-item " + ((i > 0) ? "" : "active") + " div-ticket' id='IdPendingDiv" + i + "' onclick='ViewContentPending(" + i + ")'>");
                                out.print("<div class=\"ticket-title\" >");
                                int prio = Integer.parseInt(ObjSupp[8].toString());
                                out.print("<div class='d-flex' style='justify-content: space-between;'><h4>Ticket: <b>" + ObjSupp[0] + "</b><br><br>" + ObjSupp[6] + "</h4>");
                                out.print("<span>");
                                int Rop = Integer.parseInt(ObjSupp[22].toString());
                                if (Rop > 0) {
                                    out.print("<span class='text-info'> <b>Reabierto</b> </span>");
                                } else {
                                    out.print("<span " + ((prio == 1) ? "class='text-dark'> <b>Baja</b>" : ((prio == 2) ? "class='text-warning'> <b>Media</b>" : "class='text-danger'> <b>Alta</b>")) + "</span>");
                                }
                                out.print("</span>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div class=\"ticket-desc\">");
                                out.print("<div>" + ObjSupp[4] + "</div>");
                                out.print("<div class=\"bullet\"></div>");
                                if (Rop > 0) {
                                    out.print("<div>Hace " + ObjSupp[23] + "</div>");
                                } else {
                                    out.print("<div>Hace " + ObjSupp[2] + "</div>");

                                }
                                out.print("</div>");

                                out.print("</div>");
                            }
                            out.print("</div>");
                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="CONTENT RIGHT">
                            for (int i = 0; i < lst_support.size(); i++) {
                                Object[] ObjSup = (Object[]) lst_support.get(i);
                                int prio = Integer.parseInt(ObjSup[8].toString());
                                int Rop = Integer.parseInt(ObjSup[22].toString());

                                out.print("<div class=\"ticket-content div-content ml-2 mt-2\" style='padding: 15px;box-shadow: 0px 0px 11px -3px #818181;border-radius: 7px; display:" + ((i > 0) ? "none" : "block") + "' id='DivPendingContent" + i + "'>");
                                out.print("<div class=\"ticket-header\">");
                                out.print("<div class=\"ticket-sender-picture img-shadow\">");
                                out.print("<img src=\"Interface/Content/Assets/img/avatar/avatar-1.png\" alt=\"image\">");
                                out.print("</div>");
                                out.print("<div style='display:flex;justify-content: space-between;width: 85%;'>");
                                out.print("<div>");
                                out.print("<div class=\"ticket-detail\">");
                                out.print("<div class=\"ticket-title\">");
                                out.print("<h4>" + ObjSup[6] + "</h4>");
                                out.print("</div>");
                                out.print("<div class=\"ticket-info\" style='align-items: center;'>");
                                out.print("<div class=\"font-weight-600\"><span>Prioridad <span " + ((prio == 1) ? "class='text-dark'> Baja" : ((prio == 2) ? "class='text-warning'> Media" : "class='text-danger'> Alta")) + "</span></span></div>");
                                out.print("<div class=\"bullet\"></div>");
                                out.print("<div class=\"text-primary font-weight-400\">" + ObjSup[1] + "</div>");
                                out.print("<div class=\"bullet\"></div>");
                                out.print("<div class=\"font-weight-600\"><span></span><span class='text-dark'>" + ObjSup[21] + "</span></div>");
                                if (ObjSup[9] != null) {
                                    String tpe = ObjSup[9].toString();
                                    if (tpe.equals("1")) {
                                        out.print("<div class=\"bullet\"></div>");
                                        out.print("<div class=\"font-weight-600\"><span>PC</div>");
                                    } else if (tpe.equals("2")) {
                                        out.print("<div class=\"bullet\"></div>");
                                        out.print("<div class=\"font-weight-600\">");
                                        if (ObjSup[10] != null) {
                                            int idDll = Integer.parseInt(ObjSup[10].toString());
                                            lst_app = AppJpa.ConsultAppIdActive(idDll);
                                            Object[] ObjAPp = (Object[]) lst_app.get(0);
                                            out.print("<span>" + ObjAPp[1] + "</span>");
                                        }
                                        out.print("</div>");

                                    } else if (tpe.equals("3")) {
                                        out.print("<div class=\"font-weight-600\">");
                                        try {
                                            out.print("<div class=\"bullet\"></div>");
                                            if (ObjSup[10] != null && !ObjSup[10].toString().equals("0")) {
                                                int idDll = Integer.parseInt(ObjSup[10].toString());
                                                if (idDll > 10 && ObjSup[15] != null) {
                                                    lst_customer = CustomerJpa.ConsultDiviceIdReal(idDll);
                                                } else {
                                                    lst_customer = CustomerJpa.ConsultDiviceId(idDll);
                                                }
                                                Object[] ObjDivice = (Object[]) lst_customer.get(0);
                                                out.print("<span>" + ObjDivice[1] + "</span>");
                                            } else {
                                                out.print("<span></span>");
                                            }
                                        } catch (Exception e) {
//                                            
                                        }
                                        out.print("</div>");
                                    } else {
                                        out.print("<div class=\"font-weight-600\"><span></span></div>");
                                    }
                                } else {
                                    out.print("<div class=\"font-weight-600\"><span></span></div>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class=\"ticket-description ScrollDivContent\" >");

                                out.print("<div class='text-center text-dark' style='background: #68d0b3;'>");
                                out.print("<span class=''><b>ASUNTO</b></span>");
                                out.print("</div>");

                                out.print("" + ObjSup[7].toString().replace("<img", "<img style='width:100%'") + "");

                                int idSppt = Integer.parseInt(ObjSup[0].toString());
                                lst_customer = CustomerJpa.ConsultReopeningTicket(idSppt);
                                if (lst_customer != null) {
                                    for (int j = 0; j < lst_customer.size(); j++) {
                                        Object[] Objs = (Object[]) lst_customer.get(j);
                                        out.print("<div class=''>");

                                        out.print("<div class='text-center text-dark' style='background: #ffa426;'>");
                                        out.print("<span class=''>Ticket re-abierto " + ((j == 0) ? "(Nuevo)" : "") + "</span>");
                                        out.print("</div>");

                                        out.print("<div class='d-flex'>");

                                        out.print("<div class='col-lg-3'>");
                                        out.print("<span class=''><b>Fecha:</b></span><br>");
                                        out.print("<span class=''>" + Objs[2] + "</span>");
                                        out.print("</div>");
                                        out.print("<div class='col-lg-9'>");
                                        out.print("<span class=''><b>Motivo:</b></span><br>");
                                        out.print("<span class=''>" + Objs[3] + "</span>");
                                        out.print("</div>");

                                        out.print("</div>");

                                        out.print("</div>");
                                    }
                                }

                                out.print("</div>");
                                out.print("<div class='DivButtonPending' style='left: 94%;'>");
//                            out.print("<div class='buttomsPending'><button class='btn btn-info' type='button' data-toggle='tooltip' data-placement='top' title='Editar'><i class='fas fa-edit'></i></button></div>");
                                if (Rop > 0) {
                                    out.print("<div class='buttomsPending'><button class='btn btn-info' type='button' data-toggle='tooltip' data-placement='top' title='Ejecutar' onclick='window.location.href=\"Support?opt=1&idTicket=" + ObjSup[0] + "&idArea=" + idArea + "\"'><i class='far fa-paper-plane' ></i></button></div>");
                                } else {
                                    out.print("<div class='buttomsPending'><button class='btn btn-success' type='button' data-toggle='tooltip' data-placement='top' title='Ejecutar' onclick='window.location.href=\"Support?opt=1&idTicket=" + ObjSup[0] + "&idArea=" + idArea + "\"'><i class='far fa-paper-plane' ></i></button></div>");
                                }

                                out.print("</div>");
                                out.print("</div>");
//</editor-fold>
                            }
                        } else {
                            out.print("<h3>Se ha presentado error al consultar los datos</h3");
                        }
                        //</editor-fold>
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
//                int counter = 0;
//                lst_support = SupportJpa.ConsultSupportOpenv2();
//                if (lst_support != null) {
//                    Object[] Obstp = (Object[]) lst_support.get(0);
//                    counter = Integer.parseInt(Obstp[1].toString());
//                }
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h3>Tickets abiertos</h3>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-green' style='border-radius: 0.2rem;' onclick='window.location.href=\"Support?opt=1&Section=History\"'>Historial &nbsp;<i class=\"fas fa-history\"></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                String[] RmColor = {"primary", "info", "secondary", "warning", "danger", "dark"};
                lst_support = SupportJpa.ConsultSupportOpen();
                int iter = 0;
                int total = 0;
                if (lst_support != null) {
                    out.print("<div class='row sortable-card'>");
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] ObjSpport = (Object[]) lst_support.get(i);
                        out.print("<div class='col-12 col-md-6 col-lg-3'> ");
                        out.print("<div class='card card-" + RmColor[iter] + "' style='border-radius: 7px; box-shadow: 2px 2px 8px 0px #b7b7b7;'> ");
                        out.print("<div class='card-header' style='border-bottom: 1px solid transparent; border-image: linear-gradient(to right, #fff, #8f8d8d, #fff) 1;justify-content: center; color: black; text-align: center;'> ");
                        out.print("<h4>" + ObjSpport[1] + "</h4>");
                        out.print("<span style='position: absolute;left: -5%;top: -7%;width: 30px;border-radius: 5px;background: #70dbbd;color: #000000;font-weight: bolder;box-shadow: 1px 0px 8px 0px #aba8a8;border: 1px solid #b7b7b7b5;'>" + ObjSpport[2] + "</span>");
                        out.print("</div> ");
                        out.print("<div class='card-body text-center' style='padding: 12px 0px 20px 0px;'> ");
                        out.print("<div class=''>");
                        out.print("</div>");
                        int tempx = Integer.parseInt(ObjSpport[2].toString());
                        total = total + tempx;
                        out.print("<div class='mt-2'>");
                        out.print("<button class='btn btn-green btn-sm' onclick='window.location.href=\"Support?opt=1&idArea=" + ObjSpport[0] + "\"'>Ver mas</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        if (iter == 5) {
                            iter = 0;
                        } else {
                            iter++;
                        }
                    }
                    out.print("</div>");
                } else {
                    out.print("<div class='row sortable-card justify-content-center'>");
                    out.print("<div class='col-12 col-md-6 col-lg-8'> ");
                    out.print("<div class='card card-primary'> ");
                    out.print("<div class='card-header justify-content-center'> ");
                    out.print("<h3>Estamos al día, no hay casos registrados!</h3>");
                    out.print("</div> ");
                    out.print("<div class='card-body text-center'> ");
                    out.print("<p><i style='font-size: 50px;' class=\"fas fa-check-circle\"></i></p> ");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
                //</editor-fold>
            } else if (section.equals("History")) {
                //<editor-fold defaultstate="collapsed" desc="HISTORY">
                //<editor-fold defaultstate="collapsed" desc="HISTORY DATA">
                LocalDate hoy = LocalDate.now();
                LocalDate haceUnMes = hoy.minus(1, ChronoUnit.MONTHS);
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String CurrentDay = hoy.format(formato);
                String LastMonth = haceUnMes.format(formato);
                String labelData = "";
                String valueData = "";
                try {
                    dateIn = pageContext.getRequest().getAttribute("dateIni").toString();
                    dateFin = pageContext.getRequest().getAttribute("dateFin").toString();
                } catch (Exception e) {
                    dateIn = LastMonth;
                    dateFin = CurrentDay;
                }
                try {
                    temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
                } catch (Exception e) {
                    temp = 0;
                }
                if (idArea > 0 && temp == 0) {
                    lst_support = SupportJpa.ConsultSupportByDateRange(dateIn, dateFin, idArea);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 80%;'>");
                    if (lst_support != null) {
                        Object[] dataGen = (Object[]) lst_support.get(0);
                        String area = dataGen[4].toString();
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h4></h4>");
                        out.print("<h4>Tickets de " + area + "</h4>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="LIST LEFT">
                        out.print("<div class=\"tickets\">");
                        out.print("<div class=\"ticket-items ScrollDivTicket mt-2\" id=\"ticket-items\" >");
                        for (int i = 0; i < lst_support.size(); i++) {
                            Object[] ObjSupp = (Object[]) lst_support.get(i);
                            out.print("<div class='ticket-item " + ((i > 0) ? "" : "active") + " div-ticket' id='IdPendingDiv" + i + "' onclick='ViewContentPending(" + i + ")'>");
                            out.print("<div class=\"ticket-title\" >");
                            int prio = Integer.parseInt(ObjSupp[8].toString());
                            out.print("<div class='d-flex' style='justify-content: space-between;'><h4>Ticket: <b>" + ObjSupp[0] + "</b><br><br>" + ObjSupp[6] + "</h4><span> <span " + ((prio == 1) ? "class='text-dark'> <b>Baja</b>" : ((prio == 2) ? "class='text-warning'> <b>Media</b>" : "class='text-danger'> <b>Alta</b>")) + "</span></span></div>");
                            out.print("</div>");

                            out.print("<div class=\"ticket-desc\">");
                            out.print("<div>" + ObjSupp[4] + "</div>");
                            out.print("<div class=\"bullet\"></div>");
                            out.print("<div>Hace " + ObjSupp[2] + "</div>");
                            out.print("</div>");

                            out.print("</div>");
                        }
                        out.print("</div>");
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="CONTENT RIGHT">
                        for (int i = 0; i < lst_support.size(); i++) {
                            Object[] ObjSup = (Object[]) lst_support.get(i);
                            out.print("<div class=\"ticket-content div-content ml-2 mt-2\" style='padding: 15px;box-shadow: 0px 0px 11px -3px #818181;border-radius: 7px;display:" + ((i > 0) ? "none" : "block") + "' id='DivPendingContent" + i + "'>");
                            out.print("<div class=\"ticket-header\">");
                            out.print("<div class=\"ticket-sender-picture img-shadow\">");
                            out.print("<img src=\"Interface/Content/Assets/img/avatar/avatar-1.png\" alt=\"image\">");
                            out.print("</div>");

                            out.print("<div style='display:flex;justify-content: space-between;width: 85%;'>");

                            out.print("<div>");
                            out.print("<div class=\"ticket-detail\">");
                            out.print("<div class=\"ticket-title\">");
                            out.print("<h4>" + ObjSup[6] + "</h4>");
                            out.print("</div>");
                            out.print("<div class=\"ticket-info\" style='align-items: center;'>");
                            int prio = Integer.parseInt(ObjSup[8].toString());
                            out.print("<div class=\"font-weight-600\"><span>Prioridad <span " + ((prio == 1) ? "class='text-dark'> Baja" : ((prio == 2) ? "class='text-warning'> Media" : "class='text-danger'> Alta")) + "</span></span></div>");
                            out.print("<div class=\"bullet\"></div>");
                            out.print("<div class=\"text-primary font-weight-400\">" + ObjSup[1] + "</div>");

                            out.print("<div class=\"bullet\"></div>");
                            out.print("<div class=\"font-weight-600\"><span></span><span class='text-dark'>" + ObjSup[20] + "</span></div>");
                            if (ObjSup[10] != null) {
                                String tpe = ObjSup[10].toString();
                                if (tpe.equals("1")) {
                                    out.print("<div class=\"bullet\"></div>");
                                    out.print("<div class=\"font-weight-600\"><span>PC</div>");
                                } else if (tpe.equals("2")) {
                                    out.print("<div class=\"bullet\"></div>");
                                    out.print("<div class=\"font-weight-600\">");
                                    if (ObjSup[9] != null) {
                                        int idDll = Integer.parseInt(ObjSup[9].toString());
                                        lst_app = AppJpa.ConsultAppIdActive(idDll);
                                        Object[] ObjAPp = (Object[]) lst_app.get(0);
                                        out.print("<span>" + ObjAPp[1] + "</span>");
                                    }
                                    out.print("</div>");

                                } else if (tpe.equals("3")) {
                                    out.print("<div class=\"font-weight-600\">");
                                    try {
                                        out.print("<div class=\"bullet\"></div>");
                                        if (ObjSup[9] != null && !ObjSup[9].toString().equals("0")) {
                                            int idDll = Integer.parseInt(ObjSup[9].toString());
                                            if (idDll > 10 && ObjSup[14] != null) {
                                                lst_customer = CustomerJpa.ConsultDiviceIdReal(idDll);
                                            } else {
                                                lst_customer = CustomerJpa.ConsultDiviceId(idDll);
                                            }
                                            Object[] ObjDivice = (Object[]) lst_customer.get(0);
                                            out.print("<span>" + ObjDivice[1] + "</span>");
                                        } else {
                                            out.print("<span></span>");
                                        }
                                    } catch (Exception e) {
//                                            
                                    }
                                    out.print("</div>");
                                } else {
                                    out.print("<div class=\"font-weight-600\"><span></span></div>");
                                }
                            } else {
                                out.print("<div class=\"font-weight-600\"><span></span></div>");
                            }

                            out.print("</div>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class=\"ticket-description ScrollDivContent\" style='height: auto;max-height: " + ((ObjSup[11] == null) ? "420px" : "222px") + ";'>");
                            out.print("" + ObjSup[7].toString().replace("<img", "<img style='width:100%'") + "");
                            out.print("</div>");
                            if (ObjSup[11] != null) {
                                out.print("<div style='padding: 3px;border-radius: 5px;box-shadow: 0px 0px 4px 1px #959191;'>");
                                out.print("<div class='text-center' style='background: #8ed1be; color: black;padding: 5px; border-radius: 3px;'>");
                                out.print("<h5 style='margin: 0px;'>Ticket cerrado &nbsp;<i class=\"fas fa-lock\"></i></h5>");
                                out.print("</div>");
                                out.print("<div class='d-flex' style='width: 98%; justify-content: space-between;'>");
                                out.print("<p style='margin: 0;'>Responsable: <b style='color:#33bf98;'>" + ObjSup[12] + "</b></p> <p style='margin: 0;'>Fecha solución: <b style='color:#33bf98;'>" + ObjSup[13] + "</b></p>");
                                out.print("</div>");

                                out.print("<div class='mt-2' style='max-height: 200px;overflow-y: auto;border: 1px solid #b7b7b785;padding: 5px;margin-bottom: 8px;border-radius: 4px;'>");
                                out.print("<b>Solución</b>");
                                out.print(ObjSup[14]);
                                out.print("</div>");
                                out.print("<div class='d-flex'>");
                                out.print("<div class='col-lg-4'>");
                                out.print("<b>Parada por equipo:</b> " + ((ObjSup[15] == null) ? "0" : ObjSup[15]) + "<br>");
                                out.print("<b>Parada por Porudcción:</b> " + ((ObjSup[16] == null) ? "0" : ObjSup[16]) + "");
                                out.print("</div>");
                                out.print("<div class='text-center col-lg-8'>");
                                out.print("<b>Comentario</b>");
                                if (ObjSup[18] == null) {
                                    out.print("<p> - Sin comentario -</p>");
                                } else {
                                    out.print("<p>" + ObjSup[18] + "</p>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                            } else {
                                out.print("<div style='padding: 2px;border-radius: 5px;box-shadow: 0px 0px 4px 1px #959191;position: absolute;bottom: 7%;width: 25%;right: 24%;background: white;'>");
                                out.print("<div class='text-center' style='background: #7bc2ff; color: black;padding: 5px; border-radius: 3px;'>");
                                out.print("<h6 style='margin: 0px;'>Ticket abierto &nbsp;<i class=\"fas fa-lock-open\"></i></h6>");
                                out.print("</div>");

                                out.print("</div>");
                            }
                            out.print("</div>");
//</editor-fold>
                        }
                    } else {
                        out.print("<h3>Se ha presentado error al consultar los datos</h3");
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
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ADVANCED SEARCH">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Buscar.. </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Support?opt=1&Section=History&temp=1' method='post' class=''>");
                out.print("<div class='col-lg-12 d-flex'>");
                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placment='top' title='Fecha Inicial'>");
                out.print("<input type='date' class='form-control' name='dateIni' value='" + dateIn + "' required>");
                out.print("</div>");
                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placment='top' title='Fecha Final'>");
                out.print("<input type='date' class='form-control' name='dateFin' value='" + dateFin + "' required>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-12'>");
                out.print("<select class='form-control select2' name='idArea'>");
                out.print("<option selected disabled>Seleccionar area..</option>");
                lst_area = AreaJpa.ConsultAreaActive();
                if (lst_area != null) {
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] ObjArea = (Object[]) lst_area.get(i);
                        out.print("<option value='" + ObjArea[0] + "'>" + ObjArea[1] + "</option>");
                    }
                }
                out.print("</select>");
//                out.print("<input type='text' class='form-control' name='' id='' placeholder='Buscar...' data-toggle='tooltip' data-placement='top' title='' value=''>");
                out.print("</div>");

                out.print("<div class='col-lg-12 mt-2 text-center'>");
                out.print("<button class='btn btn-green'>Buscar <i class='fas'></i></button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DATA REPRESENTATION">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 70%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2 class='text-center'>Resumen de datos historicos </h2>");
                out.print("<div class='d-flex'>");
                out.print("<button class='btn btn-green mr-2' id='btnData' onclick='SwitchView(\"dataGrafic\", \"dataTable\", \"btnGrafic\", \"btnData\")' style='height: 30px;padding: 3px; display: none;'>&nbsp;Graficos &nbsp;<i class=\"fas fa-chart-pie\"></i>&nbsp;</button>");
                out.print("<button class='btn btn-green mr-2' id='btnGrafic' onclick='SwitchView(\"dataTable\", \"dataGrafic\", \"btnData\", \"btnGrafic\")' style='height: 30px;padding: 3px; '>&nbsp;Tabla de datos &nbsp;<i class=\"fas fa-th-list\"></i>&nbsp;</button>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<div id='dataTable' style='display: none;'>");
                //<editor-fold defaultstate="collapsed" desc="TABLE DATA">
                out.print("<div class='d-flex col-lg-12'>");
                out.print("<div class='card col-lg-6' id='ConCount' style='max-height: 320px;overflow-y: auto;'>");
                //<editor-fold defaultstate="collapsed" desc="COUNTER AREA">
                lst_support = SupportJpa.CounterAreaSupport(dateIn, dateFin);
                out.print("<p class='lead mt-2 mb-2 text-center' style=''>Contador de tickets por área</p>");
                out.print("<table class='table table-sm' id='table-1' style='border: 1px solid black !important;'>");
                out.print("<thead style='background: #33bf98 !important; color: black;'>");
                out.print("<tr>");
                out.print("<th class='text-center' scope=\"col\">Area</th>");
                out.print("<th class='text-center' scope=\"col\">Tickets</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print("<tr>");
                        Object[] ObjOne = (Object[]) lst_support.get(i);
                        out.print("<td>" + ObjOne[0] + "</td>");
                        out.print("<td class='text-center'>" + ObjOne[1] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='2'><p>No se han encontrado datos!</p></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div class='card col-lg-6' id='ConCount' style='max-height: 320px;overflow-y: auto;'>");
                //<editor-fold defaultstate="collapsed" desc="COUNTER USER SUPPORT">
                lst_support = SupportJpa.CounterUserReporterSupport(dateIn, dateFin);
                out.print("<p class='lead mt-2 mb-2 text-center'>Contador de tickets por usuarios</p>");
                out.print("<table class='table table-sm' id='table-1' style='border: 1px solid black !important; margin-top: 6px;'>");
                out.print("<thead style='background: #33bf98 !important; color: black;'>");
                out.print("<tr>");
                out.print("<th class='text-center'>Nombres</th>");
                out.print("<th class='text-center'>Tickets</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print("<tr>");
                        Object[] ObjOne = (Object[]) lst_support.get(i);
                        out.print("<td>" + ObjOne[0] + "</td>");
                        out.print("<td class='text-center'>" + ObjOne[1] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='2'><p>No se han encontrado datos!</p></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='d-flex col-lg-12'>");
                out.print("<div class='card col-lg-6' id='ConCount' style='max-height: 320px;overflow-y: auto;'>");
                //<editor-fold defaultstate="collapsed" desc="COUNTER TYPE SUPPORT">
                lst_support = SupportJpa.CounterTypeSupport(dateIn, dateFin);
                out.print("<p class='lead mt-2 mb-2 text-center'>Contador de tickets por tipo de soporte</p>");
                out.print("<table class='table table-sm' id='table-1' style='border: 1px solid black !important;'>");
                out.print("<thead style='background: #33bf98 !important; color: black;'>");
                out.print("<tr>");
                out.print("<th class='text-center' scope=\"col\">Tipo de soporte</th>");
                out.print("<th class='text-center' scope=\"col\">Tickets</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print("<tr>");
                        Object[] ObjOne = (Object[]) lst_support.get(i);
                        out.print("<td>" + ObjOne[0] + "</td>");
                        out.print("<td class='text-center'>" + ObjOne[1] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='2'><p>No se han encontrado datos!</p></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
//</editor-fold>
                out.print("</div>");
                out.print("<div class='card col-lg-6' id='ConCount' style='max-height: 320px;overflow-y: auto;'>");
                //<editor-fold defaultstate="collapsed" desc="COUNTER BY USER SOLUTION">
                lst_support = SupportJpa.CounterUSerSolutionSupport(dateIn, dateFin);
                out.print("<p class='lead mt-2 mb-2 text-center'>Contador de tickets por personal</p>");
                out.print("<table class='table table-sm' id='table-1' style='border: 1px solid black !important;'>");
                out.print("<thead style='background: #33bf98 !important; color: black;'>");
                out.print("<tr>");
                out.print("<th class='text-center' scope=\"col\">Personal TI</th>");
                out.print("<th class='text-center' scope=\"col\">Tickets</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print("<tr>");
                        Object[] ObjOne = (Object[]) lst_support.get(i);
                        out.print("<td>" + ObjOne[0] + "</td>");
                        out.print("<td class='text-center'>" + ObjOne[1] + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='2'><p>No se han encontrado datos!</p></td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("<div id='dataGrafic' style='display: block;'>");
                //<editor-fold defaultstate="collapsed" desc="GRAFICS DATA">
                out.print("<div class='col-lg-12 d-flex'>");
                out.print("<div class='col-lg-6 mr-2 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                //<editor-fold defaultstate="collapsed" desc="TICKETS POR AREA">
                out.print("<h4 class='mb-2 mt-2 text-center'>Tickets por área</h4>");
                out.print("<div class=\"\">");
                lst_support = SupportJpa.CounterAreaSupport(dateIn, dateFin);
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] ObjArea = (Object[]) lst_support.get(i);
                        labelData += "'" + ObjArea[0].toString() + "', ";
                        valueData += ObjArea[1].toString() + ", ";
                    }
                    out.print("<canvas id=\"myChart3\" width=\"400\" height=\"230\"></canvas>");
                    out.print("</div>");
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart3\").getContext('2d'); "
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
                    out.print("No hay suficiente información para generar graficos.");
                    out.print("</div>");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("<div class='col-lg-6 ml-2 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                //<editor-fold defaultstate="collapsed" desc="TICKETS POR USUARIO">
                labelData = "";
                valueData = "";
                lst_support = SupportJpa.CounterUserReporterSupport(dateIn, dateFin);
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] ObjUserR = (Object[]) lst_support.get(i);
                        String temLabe = ObjUserR[0].toString();
                        labelData += "'" + (temLabe.length() > 20 ? temLabe.substring(0, 20) : temLabe) + "...', ";
                        valueData += ObjUserR[1].toString() + ", ";
                    }
//                    out.print(labelData);
//                    out.print(valueData);
                    out.print("<h4 class='mb-2 mt-2 text-center'>Tickes por usuario</h4>");
                    out.print("<canvas id=\"myChart4\" width=\"400\" height=\"230\"></canvas>");
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart4\").getContext('2d');\n"
                            + "var myChart = new Chart(ctx, { "
                            + "  type: 'pie', "
                            + "  data: { "
                            + "    datasets: [{ "
                            + "      data: [ " + valueData + " "
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
                            + "    labels: [ " + labelData + " "
                            + "    ], "
                            + "  }, "
                            + "  options: { "
                            + "    responsive: true, "
                            + "    legend: { "
                            + "      position: 'bottom', "
                            + "    }, "
                            + "  } "
                            + "});");
                    out.print("</script>");
                } else {
                    out.print("No se ha encontrado informaicon suficiente para generar el grafico.");
                }
//</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='col-lg-12 d-flex'>");
                out.print("<div class='col-lg-6 mr-2 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                //<editor-fold defaultstate="collapsed" desc="TICKETS FOR TYPE SUPPORT">
                out.print("<h4 class='mb-2 mt-4 text-center'>Tickets por soporte</h4>");
                labelData = "";
                valueData = "";
                lst_support = SupportJpa.CounterTypeSupport(dateIn, dateFin);
                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] ObjUserR = (Object[]) lst_support.get(i);
                        labelData += "'" + ObjUserR[0].toString() + "', ";
                        valueData += ObjUserR[1].toString() + ", ";
                    }
                    out.print("<canvas id=\"myChart2\" width=\"400\" height=\"230\"></canvas>");
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart2\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'bar', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labelData + "], ");
                    out.print("    datasets: [{ ");
                    out.print("      label: 'Tickets por soporte', ");
                    out.print("      data: [" + valueData + "], ");
                    out.print("      backgroundColor: [");

                    String[] colors = {"'#843232'", "'#a54343'", "'#c14848'", "'#dc4545'", "'#f05a5a'", "'#fe4545'"};
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < lst_support.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderColor: [");
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < lst_support.size() - 1) {
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
                    out.print("No se tiene la suficiente información para generar graficos");
                }
                //</editor-fold>
                out.print("</div>");
                out.print("<div class='col-lg-6 ml-2 mt-3 contGrafic' style='box-shadow: 0px 0px 6px 0px #6f6f6f;border-radius: 7px;'>");
                //<editor-fold defaultstate="collapsed" desc="TICKETS FOR PERSONAL TI">
                out.print("<h4 class='mb-2 mt-4 text-center'>Tickets por personal de TI</h4>");
                labelData = "";
                valueData = "";
                lst_support = SupportJpa.CounterUSerSolutionSupport(dateIn, dateFin);

                if (lst_support != null) {
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] ObjUserR = (Object[]) lst_support.get(i);
                        labelData += "'" + ObjUserR[0].toString() + "', ";
                        valueData += ObjUserR[1].toString() + ", ";
                    }

                    // Elimina la última coma y espacio
                    labelData = labelData.substring(0, labelData.length() - 2);
                    valueData = valueData.substring(0, valueData.length() - 2);

                    out.print("<canvas id=\"myChart1\" width=\"400\" height=\"230\"></canvas>");
                    out.print("<script>");
                    out.print("var ctx = document.getElementById(\"myChart1\").getContext('2d'); ");
                    out.print("var myChart = new Chart(ctx, { ");
                    out.print("  type: 'bar', ");
                    out.print("  data: { ");
                    out.print("    labels: [" + labelData + "], ");
                    out.print("    datasets: [{ ");
                    out.print("      label: 'Tickets cerrados por personal', ");
                    out.print("      data: [" + valueData + "], ");
                    out.print("      backgroundColor: [");

                    String[] colors = {"'#542b68'", "'#654176'", "'#6f2b90'", "'#844c9f'", "'#902fbf'", "'#b457e1'"};
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < lst_support.size() - 1) {
                            out.print(", ");
                        }
                    }

                    out.print("], borderColor: [");
                    for (int i = 0; i < lst_support.size(); i++) {
                        out.print(colors[i % colors.length]);
                        if (i < lst_support.size() - 1) {
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
                    out.print("No se tiene la suficiente información para generar gráficos");
                }

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="GENERAL INFORMATION">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 30%; margin-left: 42% !important;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2> Informacion General </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                lst_support = SupportJpa.ConsultInformationGeneralDate(dateIn, dateFin);
                if (lst_support != null) {
                    Object[] ObjData = (Object[]) lst_support.get(0);
                    out.print("<table class='table table-sm' id='table-1'>");

                    out.print("<tr style='background: #33bf98 !important; color: black; text-align: center;'>");
                    out.print("<th colspan='2' class='text-center'>Tickets Totales</th>");
                    out.print("</tr>");
                    out.print("<tr class='text-center'>");
                    out.print("<td colspan='2' class='text-center'>" + ObjData[0] + "</td>");
                    out.print("</tr>");

                    out.print("<tr style='background: #c3c3c3 !important; color: black; text-align: center;'>");
                    out.print("<th class='text-center'  style='width: 50%;'>Tickets Cerrados</th>");
                    out.print("<th class='text-center'>Tickets Abiertos</th>");
                    out.print("</tr>");
                    out.print("<tr style=''>");
                    out.print("<td class='text-center'>" + ObjData[1] + "</td>");
                    out.print("<td class='text-center'>" + ObjData[2] + "</td>");
                    out.print("</tr>");

                    out.print("<tr style='background: #c3c3c3 !important; color: black; text-align: center;'>");
                    out.print("<th class='text-center'>Paradas por PC</th>");
                    out.print("<th class='text-center'>Paradas por producción</th>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td class='text-center'>" + ObjData[3] + " min</td>");
                    out.print("<td class='text-center'>" + ObjData[4] + " min</td>");
                    out.print("</tr>");

                    out.print("</table>");
                } else {
                    out.print("No se ha encontrado información para mostrar.");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Support?opt=1\"' data-toggle='tooltip' data-placement='top' title='Atras'><i class='fas fa-arrow-left'></i></button>");
                out.print("</div>");

                out.print("<div class='d-flex' style='align-items: flex-start;'>");
                out.print("<button class='btn btn-green mr-2' style='border-radius: 0.2rem;' onclick='mostrarConvencion(4)' data-toggle='tooltip' data-placement='top' title='Información'><i class=\"fas fa-info-circle\"></i></button>");
                out.print("<button class='btn btn-green mr-2' style='border-radius: 0.2rem;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Resumen'><i class=\"fas fa-project-diagram\"></i></button>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(2)' data-toggle='tooltip' data-placement='top' title='Buscar'><i class='fas fa-search'></i></button>");
                out.print("</div>");

                out.print("</div>");
                out.print("<div class='card-body' style='padding-top: 5px;'>");
                out.print("<div class='text-center'>");
                out.print("<h3 class='mt-2'>Historial de Tickets</h3>");
                out.print("<p class='lead mr-4'>De <b>" + dateIn + "</b> a <b>" + dateFin + "</b></p>");
                out.print("</div>");
                String[] RmColor = {"primary", "info", "secondary", "warning", "danger", "dark"};
                if (idArea > 0 && temp == 1) {
                    lst_support = SupportJpa.ConsultSupportGeneralArea(dateIn, dateFin, idArea);
                } else {
                    lst_support = SupportJpa.ConsultSupportGeneral(dateIn, dateFin);
                }
                int iter = 0;
                int total = 0;
                if (lst_support != null) {
                    out.print("<div class='row sortable-card'>");
                    for (int i = 0; i < lst_support.size(); i++) {
                        Object[] ObjSpport = (Object[]) lst_support.get(i);
                        out.print("<div class='col-12 col-md-6 col-lg-3'>");
                        out.print("<div class='card card-" + RmColor[iter] + "' style='border-radius: 7px; box-shadow: 2px 2px 8px 0px #b7b7b7;'> ");
                        out.print("<div class='card-header' style='min-height: 48px;padding: 5px 20px;border-bottom: 1px solid transparent; border-image: linear-gradient(to right, #fff, #8f8d8d, #fff) 1; justify-content: center; color: black; text-align: center;min-height: 67px;'> ");
                        out.print("<h4>" + ObjSpport[1] + "</h4>");
                        out.print("<span style='position: absolute;left: -5%;top: -7%;width: 30px;border-radius: 5px;background: #70dbbd;color: #000000;font-weight: bolder;box-shadow: 1px 0px 8px 0px #aba8a8;border: 1px solid #b7b7b7b5;'>" + ObjSpport[2] + "</span>");
                        out.print("</div> ");
                        out.print("<div class='card-body text-center' style='padding: 7px 0px 20px 0px;'> ");
//                        out.print("<span style='margin: 0;'><b>" + ObjSpport[2] + "</b><br>Tickets Abiertos</span> ");
                        int tempx = Integer.parseInt(ObjSpport[2].toString());
                        total = total + tempx;
                        out.print("<div class='d-flex justify-content-around'>");
                        out.print("<span style='margin: 0;'> Computador <br> <b>" + ObjSpport[3] + " min</b></span>");
                        out.print("<span style='margin: 0;'> Produccion <br> <b>" + ObjSpport[4] + " min</b></span>");
                        out.print("</div>");
                        out.print("<button class='btn btn-green btn-sm mt-2' onclick='window.location.href=\"Support?opt=1&idArea=" + ObjSpport[0] + "&Section=History&dateIni=" + dateIn + "&dateFin=" + dateFin + "\"'>Ver mas</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        if (iter == 5) {
                            iter = 0;
                        } else {
                            iter++;
                        }
                    }
                    out.print("</div>");
                } else {
                    out.print("<div class='row sortable-card justify-content-center'>");
                    out.print("<div class='col-12 col-md-6 col-lg-8'> ");
                    out.print("<div class='card card-primary'> ");
                    out.print("<div class='card-header justify-content-center'> ");
                    out.print("<h3 style='text-align: center;margin-top: 40px;'>No se han reportado casos en el mes de proceso actual!</h3>");
                    out.print("</div> ");
                    out.print("<div class='card-body text-center'> ");
                    out.print("<p><i style='font-size: 50px;' class=\"fas fa-comment-dots\"></i></p> ");

                    out.print("<button class='btn btn-warning' onclick='window.location.href=\"Support?opt=1&Section=History\"'>Quitar Filtro</button>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
//                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_support.class.getName()).log(Level.SEVERE, null, ex);
        }

        return super.doStartTag();
    }
}
