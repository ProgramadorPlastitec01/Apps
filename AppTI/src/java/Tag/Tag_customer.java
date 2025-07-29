package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.CustomerControllerJpa;
import java.net.InetAddress;
import java.net.UnknownHostException;
import Controller.SettingControllerJpa;
import Controller.AppControllerJpa;
import Controller.SupportControllerJpa;
import Controller.UserControllerJpa;
import Controller.AreaControllerJpa;

import java.util.List;

public class Tag_customer extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        CustomerControllerJpa CustomerJpa = new CustomerControllerJpa();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        AppControllerJpa AppJpa = new AppControllerJpa();
        SupportControllerJpa SupportJpa = new SupportControllerJpa();
        UserControllerJpa UserJpa = new UserControllerJpa();
        AreaControllerJpa AreaJpa = new AreaControllerJpa();
        List lst_customer = null;
        List lst_setting = null;
        List lst_app = null;
        List lst_support = null;
        List lst_user = null;
        List lst_area = null;
        int DocCustom = 0, idTicket = 0, temp = 0;
        String IpCustomer = "";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            IpCustomer = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            DocCustom = Integer.parseInt(pageContext.getRequest().getAttribute("DocUser").toString());
        } catch (Exception e) {
            DocCustom = 0;
        }
        try {
            idTicket = Integer.parseInt(pageContext.getRequest().getAttribute("idTicket").toString());
        } catch (Exception e) {
            idTicket = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            lst_customer = CustomerJpa.ConsultUserReporter(DocCustom);
            if (lst_customer != null) {
                Object[] ObjCustom = (Object[]) lst_customer.get(0);
                int id_reporter = Integer.parseInt(ObjCustom[0].toString());
                String areaName = ObjCustom[2].toString();
                String nameReporter = ObjCustom[3].toString();
                String helper = "";
                //<editor-fold defaultstate="collapsed" desc="NEW TICKET">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 70%; right: 15%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nuevo Ticket</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<div class=''>");

                out.print("<div class='d-flex' style='justify-content: space-around;'>");
                out.print("<div class=''>");
                out.print("<span style='margin-left: -10px;'>Nombre</span>");
                out.print("<p class='lead' style='font-size: 1.05rem;'>" + nameReporter + "</p>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<span style='margin-left: -10px;'>Área</span>");
                out.print("<p class='lead' style='font-size: 1.05rem;'>" + areaName + "</p>");
                out.print("</div>");
                out.print("</div>");

                out.print("<form action='Customer?opt=2&idDoct=" + DocCustom + "' method='post' name='FromNew'>");
                out.print("<div class='d-flex'>");
                out.print("<input type='hidden' name='NmbIdArea' value='" + ObjCustom[1] + "'>");
                out.print("<input type='hidden' name='NmbIdUser' value='" + ObjCustom[0] + "'>");

                out.print("<div class='col-lg-3'>");
                lst_customer = CustomerJpa.ConsultComputerId(IpCustomer);
                if (lst_customer != null) {
                    Object[] ObjCus = (Object[]) lst_customer.get(0);
                    out.print("<input type='hidden' name='txtPcCustomer' value='" + ObjCus[0] + "' style='display: block;'>");
                } else {
                    out.print("<input type='hidden' name='txtPcCustomer' value='" + IpCustomer + "' style='display: block;'>");
                }
                out.print("<span>Numero / Ext. de contacto</span>");
                out.print("<input type='text' class='form-control' name='nmbExt' id='' placeholder='Ext.' data-toggle='tooltip' data-placement='top' title='Ext.' value=''>");

                out.print("<span class='mt-2 mb-2'>Prioridad</span>");
                out.print("<div class='selectgroup w-100' style='margin: 12px;'>");
                out.print("<label class='selectgroup-item'>");
                out.print("<input type='radio' name='txtPriority' value='1' class='selectgroup-input' checked>");
                out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Prioridad Baja'>Baja</span>");
                out.print("</label>");
                out.print("<label class='selectgroup-item'>");
                out.print("<input type='radio' name='txtPriority' value='2' class='selectgroup-input'>");
                out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Prioridad Media'>Media</span>");
                out.print("</label>");
                out.print("<label class='selectgroup-item'>");
                out.print("<input type='radio' name='txtPriority' value='3' class='selectgroup-input'>");
                out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Prioridad Alta'>Alta</span>");
                out.print("</label>");
                out.print("</div>");

                out.print("<span class='mt-2 mb-2'>Tipo de ticket</span>");
                out.print("<div class='selectgroup w-100' style='margin: 12px;'>");
                out.print("<label class='selectgroup-item'>");
                out.print("<input type='radio' name='txtTypeSpt' value='1' class='selectgroup-input' checked>");
                out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Mi equipo' onclick='switchSelect(1)'><i class=\"fas fa-desktop\"></i></span>");
                out.print("</label>");
                out.print("<label class='selectgroup-item'>");
                out.print("<input type='radio' name='txtTypeSpt' value='2' class='selectgroup-input'>");
                out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Aplicativo' onclick='switchSelect(2)'><i class=\"fas fa-laptop-code\"></i></span>");
                out.print("</label>");
                out.print("<label class='selectgroup-item'>");
                out.print("<input type='radio' name='txtTypeSpt' value='3' class='selectgroup-input'>");
                out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Otro dispositivo' onclick='switchSelect(3)'><i class=\"fab fa-windows\"></i></span>");
                out.print("</label>");
                out.print("</div>");

                out.print("<div class='' id='selectApp' style='display: none;'>");
                out.print("<p>Seleccione aplicativo</p>");
                out.print("<select class='form-control' name='selectApp'>");
                out.print("<option selected disabled value=''>Seleccione Aplicativo</option>");
                lst_app = AppJpa.ConsultActiveApp();
                if (lst_app != null) {
                    for (int i = 0; i < lst_app.size(); i++) {
                        Object[] ObjApp = (Object[]) lst_app.get(i);
                        out.print("<option value='" + ObjApp[0] + "'>" + ObjApp[1] + "</option>");
                    }
                } else {
                    out.print("<option selected disabled value=''>Ha ocurrido un problema en consulta de aplicativos</option>");
                }
                out.print("</select>");
                out.print("</div>");

                out.print("<div class='' id='selectPc' style='display: none;'>");
                out.print("<p>Seleccione equipo</p>");
                out.print("<select class='form-control' name='selectPc'>");
                out.print("<option selected disabled value=''>Seleccione Dispositivo</option>");
                lst_customer = CustomerJpa.ConsultTypeSupportActive();
                if (lst_customer != null) {
                    for (int i = 0; i < lst_customer.size(); i++) {
                        Object[] ObjOpt = (Object[]) lst_customer.get(i);
                        out.print("<option value='" + ObjOpt[0] + "'>" + ObjOpt[1] + "</option>");
                    }
                } else {
                    out.print("<option selected disabled value=''>Ha ocurrido un problema en consulta de otros equipos</option>");
                }
                out.print("</select>");
                out.print("</div>");

                out.print("<div class=''>");
                out.print("<button type='button' class='btn btn-green' style='margin-left: 35%; margin-top: 4px;' onclick='uploadFiles();dataPass();'>Registrar</button>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div class='col-lg-9'>");
                out.print("<p>Asunto</p>");
                out.print("<textarea id='editorNext'></textarea>");
                out.print("</div>");

                out.print("</div>");

                out.print("<input type='hidden' class='form-control' name='txtRequest' id='NewData'>");

                out.print("</div>");
                out.print("</form>");
                out.print("<div class='d-flex col-lg-12'>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="TICKET RE OPENING">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 32%;right: 34%;top: 18%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Reabrir el ticket</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Customer?opt=5&idDoct=" + DocCustom + "' method='post' class='needs-validation' novalidate=''>");
                out.print("<span class=''>Motivo de la reapertura del ticket</span></br>");

                out.print("<textarea class='form-control' name='txt_reason' placeholder='Ingresar motivo'></textarea>");
                out.print("<input type='text' class='form-control' name='idTicket' id='Reop' data-toggle='tooltip' data-placement='top' title='' value=''>");

                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Reabrir Caso</button>");
                out.print("</div>");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                if (idTicket > 0 && temp == 1) {
                    //<editor-fold defaultstate="collapsed" desc="SHOW TICKET">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 72%;right: 12%;'>");
                    lst_support = SupportJpa.ConsultSupportById(idTicket);
                    if (lst_support != null) {
                        Object[] ObjSp = (Object[]) lst_support.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Información del ticket</h5>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");

                        out.print("<div class='GlobalTk d-flex'>");
                        out.print("<div class='SideTk col-lg-3'>");

                        //<editor-fold defaultstate="collapsed" desc="LEFT SIDE">
                        out.print("<div class='TitleFrame' style='margin-bottom: 10px; font-size: 13px;'>");
                        out.print("<b>DETALLE DE TICKET</b>");
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Ticket:</b></span><br>");
                        out.print("<span class='ml-2'><b>" + ObjSp[0] + "</b></span><br>");
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Fecha:</b></span><br>");
                        out.print("<span class='ml-2'>" + ObjSp[1] + "</span><br>");
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Prioridad:</b></span><br>");
                        int prty = Integer.parseInt(ObjSp[3].toString());
                        out.print("<span class=''>" + ((prty == 1) ? "<b class='secondary'>BAJA</b>" : (prty == 2) ? "<b class='text-warning'>MEDIA</b>" : "<b class='text-danger'>ALTA</b>") + "</span>");
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Tipo:</b></span><br>");
                        int typeSupp = Integer.parseInt(ObjSp[4].toString());
                        out.print("<span class='ml-2'><b>" + ((typeSupp == 1) ? "MI PC" : (typeSupp == 2) ? " Aplicativo " : (typeSupp > 2) ? "Otro Dispositivo" : "Error") + "</b></span>");
                        out.print("</div>");

                        out.print("<div class='mb-3'>");

                        out.print("<span style='color: #33bf98;'><b>Detalle:</b></span><br>");

                        out.print("<span class='ml-2'>");
                        if (ObjSp[5] != null) {
                            int idDetail = Integer.parseInt(ObjSp[5].toString());
                            if (typeSupp == 1) {
                                out.print(" - ");
                            } else if (typeSupp == 2) {
                                lst_app = AppJpa.ConsultAppIdActive(idDetail);
                                if (lst_app != null) {
                                    Object[] ObjApp = (Object[]) lst_app.get(0);
                                    out.print("" + ObjApp[1] + "");
                                } else {
                                    out.print(" - ");
                                }
                            } else if (typeSupp >= 3) {
                                List lst_customerx = null;
                                if (idDetail > 10 && ObjSp[8] != null) {
                                    lst_customerx = CustomerJpa.ConsultDiviceIdReal(idDetail);
                                } else {
                                    lst_customerx = CustomerJpa.ConsultTypeSupportById(idDetail);
                                }
                                if (lst_customerx != null) {
                                    Object[] ObjSupp = (Object[]) lst_customerx.get(0);
                                    out.print("" + ObjSupp[1] + "");
                                } else {
                                    out.print(" - ");
                                }
                            }
                        } else {
                            out.print(" - ");
                        }

                        out.print("</span>");
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Estado:</b></span><br>");

//                        out.print("<span class='ml-2'>" + ObjSp[0] + " - pend</span>");
                        int count_ticket = 0;
                        try {
                            count_ticket = Integer.parseInt(ObjSp[15].toString());
                        } catch (Exception e) {
                            count_ticket = 0;
                        }
                        if (count_ticket > 0) {
                            out.print("<span class='ml-2 text-info'><b>RE - ABIERTO <i class=\"fas fa-reply\"></i></b></span>");
                        } else {
                            if (ObjSp[9] == null) {
                                out.print("<span class='ml-2 text-dark'><b>EN PROCESO <i class=\"fas fa-lock-open\"></i></b></span>");
                            } else {
                                if (ObjSp[12] == null) {
                                    out.print("<span class='ml-2 text-secondary'><b>SIN CALIFICAR <i class=\"fas fa-star\"></i></b></span>");
                                } else {
                                    out.print("<span class='ml-2 text-secondary'><b>FINALIZADO <i class=\"fas fa-lock\"></i></b></span>");
                                }
                            }
                        }
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Fecha solución:</b></span><br>");
                        if (ObjSp[7] != null) {
                            out.print("<span class='ml-2'>" + ObjSp[8] + "</span><br>");
                        } else {
                            out.print("<span class='ml-2 text-warning'>En proceso</span>");
                        }
                        out.print("</div>");

                        out.print("<div class='mb-3'>");
                        out.print("<span style='color: #33bf98;'><b>Responsable:</b></span><br>");

                        if (ObjSp[7] != null) {
                            int id_user = Integer.parseInt(ObjSp[7].toString());
                            lst_user = UserJpa.ConsultUsersid(id_user);
                            if (lst_user != null) {
                                Object[] ObjUser = (Object[]) lst_user.get(0);
                                helper = ObjUser[1] + " " + ObjUser[2];
                                out.print("<p class='' style='margin: 0;'><b>" + helper + "</b></p>");
                                out.print("<i>" + ObjUser[8] + "</i>");
                            }
                        } else {
                            out.print("<span class='ml-2 text-warning'>En proceso</span>");
                        }

                        out.print("</div>");

                        //</editor-fold>
                        out.print("</div>");

                        out.print("<div class='FrameTk col-lg-9'>");
                        
                        out.print("<div class='TitleFrame' style='margin-bottom: 10px; font-size: 13px;'>");
                        out.print("<b>SEGUIMIENTO TICKET</b>");
                        out.print("</div>");
                        
                        //<editor-fold defaultstate="collapsed" desc="RIGHT SIDE">
                        out.print("<div id='TKAff' class='AffairTk' style='max-height: 600px; overflow-y: auto;'>");

                        //<editor-fold defaultstate="collapsed" desc="MAIN CHAT">
                        out.print("<div class='bodyChatCS'>");
                        out.print("<div class='d-flex'>");
                        out.print("<div class='iconChatCS'>");
                        out.print("<i class='fas fa-user'></i>");
                        out.print("</div>");
                        out.print("<div class='titleChatCS'>");
                        out.print("<span style='font-weight: 100;'>" + nameReporter + "</span><br><span style='font-weight: bolder; font-size: 12px;'>" + ObjSp[1] + "</span>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='textChatCS'>");
                        out.print("<p>" + ObjSp[2] + "</p>");
                        out.print("</div>");

                        out.print("</div>");

                        if (ObjSp[9] != null) {
                            out.print("<div class='bodyChatTI'>");
                            out.print("<div class='d-flex' style='justify-content: end;'>");
                            out.print("<div class='titleChatTI'>");
                            out.print("<span style='font-weight: 100;'>" + helper + "</span><br><span style='font-weight: bolder; font-size: 12px;'>" + ObjSp[8] + "</span>");
                            out.print("</div>");
                            out.print("<div class='iconChatTI'>");
                            out.print("<span>T.I</span>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='textChatTI'>");
                            out.print("<p>" + ObjSp[9] + "</p>");
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            out.print("<div class='bodyChatTI'>");
                            out.print("<div class='d-flex' style='justify-content: end; align-items: baseline;'>");
                            out.print("<div class='titleChatTI' style='background: #d5d5d5;border-radius: 5px 0px 5px 5px;margin-right: 3px;color: black;padding: 5px;'>");
                            out.print("<span style='font-weight: 100;'>En espera de respuesta</span>");
                            out.print("</div>");
                            out.print("<div class='iconChatTI'>");
                            out.print("<span>T.I</span>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        }

//</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="RE OPEN CHAT">
                        lst_customer = CustomerJpa.ConsultReopeningTicket(idTicket);
                        if (lst_customer != null) {
                            for (int i = 0; i < lst_customer.size(); i++) {
                                Object[] ObjCustomx = (Object[]) lst_customer.get(i);

                                //<editor-fold defaultstate="collapsed" desc="CUSTOMER CHAT">
                                out.print("<div class='bodyChatCS'>");
                                out.print("<div class='d-flex'>");
                                out.print("<div class='iconChatCS'>");
                                out.print("<i class='fas fa-user'></i>");
                                out.print("</div>");
                                out.print("<div class='titleChatCS'>");
                                out.print("<span style='font-weight: 100;'>" + nameReporter + "</span><br><span style='font-weight: bolder; font-size: 12px;'>" + ObjCustomx[2] + "</span><div class=\"bullet\"></div><span class='text-warning'>Ticket reabierto</span>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='textChatCS'>");
                                out.print("<p>" + ObjCustomx[3] + "</p>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                                //<editor-fold defaultstate="collapsed" desc="TI CHAT">
                                if (ObjCustomx[4] != null) {
                                    out.print("<div class='bodyChatTI'>");
                                    out.print("<div class='d-flex' style='justify-content: end;'>");
                                    out.print("<div class='titleChatTI'>");
                                    out.print("<span style='font-weight: 100;'>" + helper + "</span><br><span style='font-weight: bolder; font-size: 12px;'>" + ObjCustomx[6] + "</span>");
                                    out.print("</div>");
                                    out.print("<div class='iconChatTI'>");
                                    out.print("<span>T.I</span>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='textChatTI'>");
                                    out.print("<p>" + ObjCustomx[4] + "</p>");
                                    out.print("</div>");
                                    out.print("</div>");
                                } else {
                                    out.print("<div class='bodyChatTI'>");
                                    out.print("<div class='d-flex' style='justify-content: end; align-items: baseline;'>");
                                    out.print("<div class='titleChatTI' style='background: #d5d5d5;border-radius: 5px 0px 5px 5px;margin-right: 3px;color: black;padding: 5px;'>");
                                    out.print("<span style='font-weight: 100;'>En espera de respuesta</span>");
                                    out.print("</div>");
                                    out.print("<div class='iconChatTI'>");
//                                    out.print("<i class='fas fa-user-friends'></i>");
                                    out.print("<span>T.I</span>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                                //</editor-fold>
                            }
                        }
                        //</editor-fold>
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");

                    } else {
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("Se ha presentado error al consultar la informacion del ticket.");
                    }

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
                } else if (idTicket > 0 && temp == 2) {
                    //<editor-fold defaultstate="collapsed" desc="EDIT DATA TICKET">
                    lst_support = SupportJpa.ConsultSupportById(idTicket);
                    if (lst_support != null) {
                        Object[] ObjtEdit = (Object[]) lst_support.get(0);
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 70%; right: 15%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Editar Ticket Nro. " + ObjtEdit[0] + "</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<div class=''>");

                        out.print("<div class='d-flex' style='justify-content: space-around;'>");
                        out.print("<div class=''>");
                        out.print("<span style='margin-left: -10px;'>Nombre</span>");
                        out.print("<p class='lead' style='font-size: 1.05rem;'>" + nameReporter + "</p>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<span style='margin-left: -10px;'>Área</span>");
                        out.print("<p class='lead' style='font-size: 1.05rem;'>" + areaName + "</p>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<form action='Customer?opt=2&idDoct=" + DocCustom + "&idTicket=" + ObjtEdit[0] + "' method='post'>");
                        out.print("<div class='d-flex'>");
                        out.print("<input type='hidden' name='NmbIdArea' value='" + ObjCustom[1] + "'>");
                        out.print("<input type='hidden' name='NmbIdUser' value='" + ObjCustom[0] + "'>");

                        out.print("<div class='col-lg-3'>");
                        lst_customer = CustomerJpa.ConsultComputerId(IpCustomer);
                        if (lst_customer != null) {
                            Object[] ObjCus = (Object[]) lst_customer.get(0);
                            out.print("<input type='hidden' name='txtPcCustomer' value='" + ObjCus[0] + "' style='display: block;'>");
                        } else {
                            out.print("<input type='hidden' name='txtPcCustomer' value='" + IpCustomer + "' style='display: block;'>");
                        }
//                        out.print("<span>Numero / Ext. de contacto</span>");
//                        out.print("<input type='text' class='form-control' name='nmbExt' id='' placeholder='Ext.' data-toggle='tooltip' data-placement='top' title='Ext.' value=''>");

                        out.print("<span class='mt-2 mb-2'>Prioridad</span>");
                        out.print("<div class='selectgroup w-100' style='margin: 12px;'>");
                        out.print("<label class='selectgroup-item'>");
                        int dataprio = Integer.parseInt(ObjtEdit[3].toString());
                        out.print("<input type='radio' name='txtPriority' value='1' class='selectgroup-input' " + ((dataprio == 1) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Prioridad Baja'>Baja</span>");
                        out.print("</label>");
                        out.print("<label class='selectgroup-item'>");
                        out.print("<input type='radio' name='txtPriority' value='2' class='selectgroup-input' " + ((dataprio == 2) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Prioridad Media'>Media</span>");
                        out.print("</label>");
                        out.print("<label class='selectgroup-item'>");
                        out.print("<input type='radio' name='txtPriority' value='3' class='selectgroup-input' " + ((dataprio == 3) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Prioridad Alta'>Alta</span>");
                        out.print("</label>");
                        out.print("</div>");

                        out.print("<span class='mt-2 mb-2'>Tipo de ticket</span>");
                        out.print("<div class='selectgroup w-100' style='margin: 12px;'>");
                        int datatype = Integer.parseInt(ObjtEdit[4].toString());
                        int sppDetail = Integer.parseInt(ObjtEdit[5].toString());
                        out.print("<label class='selectgroup-item'>");
                        out.print("<input type='radio' name='txtTypeSpt' value='1' class='selectgroup-input' " + ((datatype == 1) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Mi equipo' onclick='switchEdit(1)'><i class=\"fas fa-desktop\"></i></span>");
                        out.print("</label>");
                        out.print("<label class='selectgroup-item'>");
                        out.print("<input type='radio' name='txtTypeSpt' value='2' class='selectgroup-input' " + ((datatype == 2) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Aplicativo' onclick='switchEdit(2)'><i class=\"fas fa-laptop-code\"></i></span>");
                        out.print("</label>");
                        out.print("<label class='selectgroup-item'>");
                        out.print("<input type='radio' name='txtTypeSpt' value='3' class='selectgroup-input' " + ((datatype >= 3) ? "checked" : "") + ">");
                        out.print("<span class='selectgroup-button selectgroup-button-icon' data-toggle='tooltip' data-placement='top' title='Otro dispositivo' onclick='switchEdit(3)'><i class=\"fab fa-windows\"></i></span>");
                        out.print("</label>");
                        out.print("</div>");

                        out.print("<div class='' id='editApp' style='display: " + ((datatype == 2) ? "block" : "none") + ";'>");
                        out.print("<p>Seleccione aplicativo</p>");
                        out.print("<select class='form-control' name='selectApp'>");
                        if (datatype == 2) {
                            lst_app = AppJpa.ConsultAppIdActive(sppDetail);
                            if (lst_app != null) {
                                Object[] ObjApp = (Object[]) lst_app.get(0);
                                out.print("<option value='" + ObjApp[0] + "'>" + ObjApp[1] + "</option>");
                            } else {
                                out.print("<option selected disabled value=''>Seleccione Aplicativo</option>");
                            }
                        } else {
                            out.print("<option selected disabled value=''>Seleccione Aplicativo</option>");
                        }
                        lst_app = AppJpa.ConsultActiveApp();
                        if (lst_app != null) {
                            for (int i = 0; i < lst_app.size(); i++) {
                                Object[] ObjApp = (Object[]) lst_app.get(i);
                                out.print("<option value='" + ObjApp[0] + "'>" + ObjApp[1] + "</option>");
                            }
                        } else {
                            out.print("<option selected disabled value=''>Ha ocurrido un problema en consulta de aplicativos</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");

                        out.print("<div class='' id='editPc' style='display: " + ((datatype >= 3) ? "block" : "none") + ";'>");
                        out.print("<p>Seleccione equipo</p>");
                        out.print("<select class='form-control' name='selectPc'>");
                        if (datatype >= 3) {
                            lst_customer = CustomerJpa.ConsultDiviceId(sppDetail);
                            if (lst_customer != null) {
                                Object[] ObjApp = (Object[]) lst_customer.get(0);
                                out.print("<option value='" + ObjApp[0] + "'>" + ObjApp[1] + "</option>");
                            } else {
                                out.print("<option selected disabled value=''>Seleccione Dispositivo</option>");
                            }
                        } else {
                            out.print("<option selected disabled value=''>Seleccione Dispositivo</option>");
                        }
                        lst_customer = CustomerJpa.ConsultTypeSupportActive();
                        if (lst_customer != null) {
                            for (int i = 0; i < lst_customer.size(); i++) {
                                Object[] ObjOpt = (Object[]) lst_customer.get(i);
                                out.print("<option value='" + ObjOpt[0] + "'>" + ObjOpt[1] + "</option>");
                            }
                        } else {
                            out.print("<option selected disabled value=''>Ha ocurrido un problema en consulta de otros equipos</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-green' style='margin-left: 35%; margin-top: 4px;'>Modificar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='col-lg-9'>");
                        out.print("<p>Asunto</p>");
                        out.print("<textarea class='summernote' name='txtRequest'>" + ObjtEdit[2] + "</textarea>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("<div class='d-flex col-lg-12'>");
                        out.print("</div>");
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
                } else if (idTicket > 0 && temp == 3) {
                    //<editor-fold defaultstate="collapsed" desc="STARTING TICKET">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 44%; right: 28%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Calificar atención</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    lst_customer = CustomerJpa.ConsultRatingTicket(idTicket);
                    boolean validData = false;
                    String rating = "";
                    Object[] ObjRating = {};
                    if (lst_customer != null) {
                        try {
                            ObjRating = (Object[]) lst_customer.get(0);
                            rating = ObjRating[3].toString();
                            validData = true;
                        } catch (Exception e) {
                            validData = false;
                        }
                    }
                    if (validData == false) {
                        out.print("<form action='Customer?opt=3&idDoct=" + DocCustom + "&idTicket=" + idTicket + "' method='post' class=''>");
                    }
                    out.print("<div class='rating-container'>");
                    out.print("<div class='rating'>");
                    if (validData) {
                        out.print("<input type='radio' id='' name='rating' value='5' " + ((validData) ? (rating.equals("5") ? "checked" : "") : "") + ">");
                        out.print("<label for='star5' data-toggle='tooltip' data-placement='top' title='Excelente'  " + ((validData) ? "style='pointer-events: none;'" : "") + "><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='' name='rating' value='4' " + ((validData) ? (rating.equals("4") ? "checked" : "") : "") + ">");
                        out.print("<label for='star4' data-toggle='tooltip' data-placement='top' title='Bueno'  " + ((validData) ? "style='pointer-events: none;'" : "") + "><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='' name='rating' value='3' " + ((validData) ? (rating.equals("3") ? "checked" : "") : "") + ">");
                        out.print("<label for='star3' data-toggle='tooltip' data-placement='top' title='Regular'  " + ((validData) ? "style='pointer-events: none;'" : "") + "><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='' name='rating' value='2' " + ((validData) ? (rating.equals("2") ? "checked" : "") : "") + ">");
                        out.print("<label for='star2' data-toggle='tooltip' data-placement='top' title='Malo'  " + ((validData) ? "style='pointer-events: none;'" : "") + "><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='' name='rating' value='1' " + ((validData) ? (rating.equals("1") ? "checked" : "") : "") + ">");
                        out.print("<label for='star1' data-toggle='tooltip' data-placement='top' title='Muy malo' " + ((validData) ? "style='pointer-events: none;'" : "") + "><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                    } else {
                        out.print("<input type='radio' id='star5' name='rating' value='5'>");
                        out.print("<label for='star5' data-toggle='tooltip' data-placement='top' title='Excelente'><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='star4' name='rating' value='4'>");
                        out.print("<label for='star4' data-toggle='tooltip' data-placement='top' title='Bueno'><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='star3' name='rating' value='3'>");
                        out.print("<label for='star3' data-toggle='tooltip' data-placement='top' title='Regular'><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='star2' name='rating' value='2'>");
                        out.print("<label for='star2' data-toggle='tooltip' data-placement='top' title='Malo'><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                        out.print("<input type='radio' id='star1' name='rating' value='1'>");
                        out.print("<label for='star1' data-toggle='tooltip' data-placement='top' title='Muy malo'><i style='font-size: 25px;' class='fas fa-star'></i></label>");
                    }
                    out.print("</div>");
                    out.print("<div class='emoji' id='emoji'></div>");
                    out.print("</div>");
                    out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                    out.print("<div class=''>");
                    out.print("<span>¿Parada de PC?</span>");
                    out.print("<input type='text' class='form-control' placeholder='Minutos' name='NmbPc' id='' data-toggle='tooltip' data-placement='top' title='Minutos' value='" + ((validData) ? ObjRating[1] : "0") + "' " + ((validData) ? "readonly" : "") + ">");
                    out.print("</div>");
                    out.print("<div class=''>");
                    out.print("<span>¿Parada de producción?</span>");
                    out.print("<input type='text' class='form-control' placeholder='Minutos' name='NmbProd' id='' data-toggle='tooltip' data-placement='top' title='Minutos' value='" + ((validData) ? ObjRating[2] : "0") + "' " + ((validData) ? "readonly" : "") + ">");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div style='padding: 15px;'>");
                    if (validData) {
                        out.print("<span class='mb-2'>Tu opinion!</span>");
                        out.print("<div class='mt-2'>" + ObjRating[4].toString() + "</div>");
                    } else {
                        out.print("<span class='mb-2'>Danos tu opinion!</span>");
                        out.print("<textarea class='form-control mt-2' name='txtOpinion' plaholder=''>¡Buen trabajo!</textarea>");

                    }
                    out.print("</div>");
                    out.print("<div class='text-center mt-2'>");
                    if (validData) {
                    } else {
                        out.print("<button class='btn btn-yellow'>Calificar!</button>");
                    }
                    out.print("</div>");
                    if (validData == false) {
                        out.print("</form>");
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
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: center;justify-content: center;background: #33bf98;box-shadow: 0px 0px 8px 1px #a7a7a7;border-radius: 5px;color: black;border-bottom: none;'>");
                out.print("<h3 style='margin: 0;'>Bienvenid@ " + nameReporter + "</h3>");
                out.print("</div>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<div class='d-flex'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"http://172.16.2.117:8084/Aplicativos_Plastitec/\"' data-toggle='tooltip' data-placement='top' title='Salir'><i class='fas fa-arrow-left'></i></button>&nbsp;&nbsp;&nbsp;");
                out.print("<h4>Tickets</h4>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-light mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Customer?opt=1&idDoct=" + DocCustom + "\"' data-toggle='tooltip' data-placement='top' title='Refrescar'><i class=\"fas fa-undo-alt\"></i></button>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Nuevo Ticket'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                lst_customer = CustomerJpa.ConsultSupportReporter(id_reporter);
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr class='text-center'>");
                out.print("<th>No. Ticket</th>");
                out.print("<th>Tipo</th>");
                out.print("<th>Detalle</th>");
                out.print("<th>Proridad</th>");
                out.print("<th>Fecha</th>");
                out.print("<th>Estado</th>");
                out.print("<th>OPC</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody class='text-center'>");
                if (lst_customer != null) {
                    for (int i = 0; i < lst_customer.size(); i++) {
                        Object[] ObjCustomer = (Object[]) lst_customer.get(i);
                        out.print("<tr>");
                        out.print("<td><button class='btn btn-yellow' data-toggle='tooltip' data-placement='top' title='Ver' onclick='window.location.href=\"Customer?opt=1&idDoct=" + DocCustom + "&idTicket=" + ObjCustomer[0] + "&temp=1\"'><b class='text-dark'>" + ObjCustomer[0] + "</b></button></td>");
                        int typeSupport = 0;
                        try {
                            typeSupport = Integer.parseInt(ObjCustomer[5].toString());
                            out.print("<td>" + ((typeSupport == 1) ? "MI PC" : (typeSupport == 2) ? " Aplicativo " : (typeSupport > 2) ? "Otro Dispositivo" : "Error") + "</td>");
                        } catch (Exception e) {
                            out.print("<td>Error</td>");
                            typeSupport = 1;
                        }
                        if (ObjCustomer[6] != null) {
                            int idDetail = Integer.parseInt(ObjCustomer[6].toString());
                            if (typeSupport == 1) {
                                out.print("<td> - </td>");
                            } else if (typeSupport == 2) {
                                lst_app = AppJpa.ConsultAppIdActive(idDetail);
                                if (lst_app != null) {
                                    Object[] ObjApp = (Object[]) lst_app.get(0);
                                    out.print("<td>" + ObjApp[1] + "</td>");
                                } else {
                                    out.print("<td> - </td>");
                                }
                            } else if (typeSupport >= 3) {
                                List lst_customerx = null;
                                if (idDetail > 10 && ObjCustomer[8] != null) {
                                    lst_customerx = CustomerJpa.ConsultDiviceIdReal(idDetail);
                                } else {
                                    lst_customerx = CustomerJpa.ConsultTypeSupportById(idDetail);
                                }
                                if (lst_customerx != null) {
                                    Object[] ObjSupp = (Object[]) lst_customerx.get(0);
                                    out.print("<td>" + ObjSupp[1] + "</td>");
                                } else {
                                    out.print("<td> - </td>");
                                }
                            }
                        } else {
                            out.print("<td> - </td>");
                        }
                        int prio = Integer.parseInt(ObjCustomer[4].toString());
                        out.print("<td>" + ((prio == 1) ? "<b class='text-secondary'>BAJA</b>" : (prio == 2) ? "<b class='text-warning'>MEDIA</b>" : "<b class='text-danger'>ALTA</b>") + "</td>");

                        out.print("<td>" + ObjCustomer[1] + "</td>");

                        //<editor-fold defaultstate="collapsed" desc="STATE">
                        boolean state = false;

                        int count_ticket = 0;

                        try {
                            count_ticket = Integer.parseInt(ObjCustomer[10].toString());
                        } catch (Exception e) {
                            count_ticket = 0;
                        }

                        if (count_ticket > 0) {
                            out.print("<td><span class='text-info'><b>RE - ABIERTO <i class=\"fas fa-reply\"></i></b></span></td>");
                        } else {
                            if (ObjCustomer[8] == null) {
                                out.print("<td><span class='text-dark'><b>EN PROCESO <i class=\"fas fa-lock-open\"></i></b></span></td>");
                                state = true;
                            } else {
                                if (ObjCustomer[9] == null) {
                                    out.print("<td><span class='text-secondary'><b>SIN CALIFICAR <i class=\"fas fa-star\"></i></b></span></td>");
                                } else {
                                    out.print("<td><span class='text-secondary'><b>FINALIZADO <i class=\"fas fa-lock\"></i></b></span></td>");
                                }
                            }
                        }
                        //</editor-fold>

                        out.print("<td>");
                        out.print("<div class='d-flex justify-content-center'>");
                        if (state) {
                            out.print("<button class='btn btn-warning mr-2' data-toggle='tooltip' data-palcement='top' title='Editar' onclick='window.location.href=\"Customer?opt=1&idDoct=" + DocCustom + "&idTicket=" + ObjCustomer[0] + "&temp=2\"'><i class=\"fas fa-pencil-alt\"></i></button>");
                        } else {
                            if (count_ticket > 0) {
                                out.print("<button type='button' class='btn btn-info mr-2' data-toggle='tooltip' data-placement='top' title='Ticket reabierto' onclick='window.location.href=\"Customer?opt=1&idDoct=" + DocCustom + "&idTicket=" + ObjCustomer[0] + "&temp=1\"'><i class=\"fas fa-history\"></i></button>");
                            } else {
                                if (ObjCustomer[9] == null) {
                                    out.print("<button class='btn btn-yellow' data-toggle='tooltip' data-palcement='top' title='Calificar' onclick='window.location.href=\"Customer?opt=1&idDoct=" + DocCustom + "&idTicket=" + ObjCustomer[0] + "&temp=3\"'><i style='color: white;' class=\"fas fa-star\"></i></button>");
                                    out.print("<button type='button' style='height: 22px;font-size: 2px;width: 22px;padding: 3px; margin-top: 7px' class='btn btn-green btn-sm ml-2' data-toggle='tooltip' data-palcement='top' title='Reabrir ticket'  onclick='mostrarConvencion(5);passData(" + ObjCustomer[0] + ")'><i class=\"fas fa-sync-alt\"></i></button>");
                                } else {
                                    out.print("<button class='btn btn-yellow' data-toggle='tooltip' data-palcement='top' title='Calificado' onclick='window.location.href=\"Customer?opt=1&idDoct=" + DocCustom + "&idTicket=" + ObjCustomer[0] + "&temp=3\"'><i style='color: yellow;' class=\"fas fa-star\"></i></button>");
                                }
                            }
                        }
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='6'>No se han encontrado tickets registrados, selecciona la opcion <i class=\"fas fa-plus\"></i> para registrar tu primer Ticket.</td>");
                    out.print("</tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>

            } else {
                //<editor-fold defaultstate="collapsed" desc="NEW REPORTER">
                String nameUser = "";
                String AreaName = "";
                int dox = 0;
                int cdx = 0;
                try {
                    nameUser = pageContext.getRequest().getAttribute("Names").toString();
                } catch (Exception e) {
                    nameUser = "";
                }
                try {
                    AreaName = pageContext.getRequest().getAttribute("AreaName").toString();
                } catch (Exception e) {
                    AreaName = "";
                }
                try {
                    dox = Integer.parseInt(pageContext.getRequest().getAttribute("Documento").toString());
                } catch (Exception e) {
                    dox = 0;
                }
                try {
                    cdx = Integer.parseInt(pageContext.getRequest().getAttribute("CodeUser").toString());
                } catch (Exception e) {
                    cdx = 0;
                }
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='contGeneral' style='width: 50%; right: 25%;border: 2px solid #33bf98;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nuevo usuario </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-arrow-left'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<div class='d-flex' style='justify-content: space-around'>");
                out.print("<div class=''>");
                out.print("<span>Nombre: <br></span>");
                out.print("<h6>" + nameUser + "</h6>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<span>Nro. Documento: <br></span>");
                out.print("<h6>" + dox + "</h6>");
                out.print("</div>");
                out.print("<div class=''>");
                out.print("<span>Codigo: <br></span>");
                out.print("<h6>" + cdx + "</h6>");
                out.print("</div>");
                out.print("</div>");
                out.print("<form action='Customer?opt=4' method='post' class=''>");
                out.print("<input type='hidden' class='form-control' name='txtNames' value='" + nameUser + "'>");
                out.print("<input type='hidden' class='form-control' name='nmbDoc' value='" + dox + "'>");
                out.print("<input type='hidden' class='form-control' name='nmbCod' value='" + cdx + "'>");
                out.print("<div class='d-flex' style='justify-content: space-around;margin-top: 20px;'>");
                out.print("<div class='col-lg-5'>");
                out.print("<span>Seleccionar área:</span>");
                out.print("<select class='form-control select2' name='cbxArea' style='margin: 12px;'>");
                lst_area = AreaJpa.ConsultAreaActiveName(AreaName);
                if (lst_area != null) {
                    Object[] ObjArea = (Object[]) lst_area.get(0);
                    out.print("<option value='" + ObjArea[0] + "'>" + ObjArea[1] + "</option>");
                }
                lst_area = AreaJpa.ConsultAreaActive();
                if (lst_area != null) {
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] ObjArx = (Object[]) lst_area.get(i);
                        out.print("<option value='" + ObjArx[0] + "'>" + ObjArx[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("<div class='col-lg-5'>");
                out.print("<span>Correo: <br></span>");
                out.print("<input type='text' class='form-control' name='txtMail' placeholder='Correo electronico' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='text-center' style='margin-top: 20px;'>");
                out.print("<button class='btn btn-green'>Continuar <i class=\"fas fa-caret-right\"></i></button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
