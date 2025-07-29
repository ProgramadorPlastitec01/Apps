package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.ActivityJpaController;
import Controller.ActivityDetailJpaController;
import Controller.ComputerControllerJpa;
import java.util.List;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Tag_activity extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        ActivityJpaController ActivityJpa = new ActivityJpaController();
        ComputerControllerJpa ComputerJpa = new ComputerControllerJpa();
        ActivityDetailJpaController ActivityDetail = new ActivityDetailJpaController();
        List lst_activity = null;
        List lst_computer = null;
        List lst_activityDet = null;
        JspWriter out = pageContext.getOut();
        int event = 0, idAct = 0, temp = 0;
        int year = LocalDate.now().getYear();
        try {
            event = Integer.parseInt(pageContext.getRequest().getAttribute("event").toString());
        } catch (Exception e) {
            event = 0;
        }
        try {
            idAct = Integer.parseInt(pageContext.getRequest().getAttribute("idAct").toString());
        } catch (Exception e) {
            idAct = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
        }
        try {
            if (event == 1) {
                //<editor-fold defaultstate="collapsed" desc="DATAIL">
                //<editor-fold defaultstate="collapsed" desc="VIEW REGISTER 005">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 69%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-dark btn btn-sm mr-2' id='btnImprimir'><i class='fas fa-print'></i></button>");
                out.print("</div>");
                out.print("<h2>R-TI-005</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                
                
                out.print("<div class='cont_form_user' id='dataDocument'>");

                lst_activity = ActivityJpa.ConsultActivityId(idAct);
                if (lst_activity != null) {
                    Object[] ObjAct = (Object[]) lst_activity.get(0);

                    out.print("<table class='table table-sm' id='' style='font-size: 12px; margin-bottom: 0;'>");
                    out.print("<tr class='text-center' style='background: #b3b2b2; color: white;'>");
                    out.print("<th colspan='3'>COPIA NO CONTROLADA</th>");
                    out.print("</tr>");
                    out.print("<tr class='text-center'>");
                    out.print("<td rowspan='2'><img src='Interface/Imagen/Logo.png' height='50px' alt='Logo'><br> <b>PLANTA PRODUCTOS <br> MEDICO FARMACEUTICOS</b></td>");
                    out.print("<td><b>REGISTRO</b></td>");
                    out.print("<td><b>CODIGO: R-TI-005</b></td>");
                    out.print("</tr>");
                    out.print("<tr class='text-center'>");
                    out.print("<td><b>SEGUIMIENTO ACTIVIDADES EQUIPOS</b></td>");
                    out.print("<td><b>VERSION: 1</b></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                    out.print("<td colspan='2'><b>ACTIVIDAD A REALIZAR: </b>" + ObjAct[1] + "</td>");
                    out.print("<td><b>SEMANA: </b>" + ObjAct[2] + "</td>");
                    out.print("</tr>");
                    out.print("</table>");

                }

                lst_activityDet = ActivityDetail.ConsultActivityDetailIdAct(idAct);

                if (lst_activityDet != null) {
                    out.print("<div class='table-container border' id='divFixedR5'>");

                    out.print("<table class='table table-sm' id=''>");
                    out.print("<thead class='sticky-top'>");
                    out.print("<tr class='text-center text-dark' style='font-size: 13px;'>");
                    out.print("<th># EQUIPO</th>");
                    out.print("<th>EJECUCION DE ACTIVIDAD</th>");
                    out.print("<th>FECHA EJECUCION</th>");
                    out.print("<th>RESPONSABLE EJECUCION</th>");
                    out.print("<th>VERIFICACION DE ACTIVIDAD</th>");
                    out.print("<th>FECHA DE VERIFICACION</th>");
                    out.print("<th>RESPONSABLE VERIFICACION</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_activityDet.size(); i++) {
                        Object[] vw = (Object[]) lst_activityDet.get(i);
                        String[] pcDv_name = vw[2].toString().replace("[", "").replace("]", "").split("/");
                        String namepcDv = pcDv_name[1].toString();
                        out.print("<tr style='font-size: 12px;'>");
                        out.print("<td><b>" + namepcDv + "</b></td>");
                        if (vw[7] != null) {
                            out.print("<td>" + vw[4] + "</td>");
                            out.print("<td>" + vw[3] + "</td>");
                            out.print("<td>" + vw[6] + "</td>");
                            out.print("<td>" + vw[8] + "</td>");
                            out.print("<td>" + vw[7] + "</td>");
                            out.print("<td>" + vw[10] + "</td>");
                        } else {
                            out.print("<td colspan='6' class='text-center text-warning'>Actividad sin completar <i class=\"fas fa-exclamation-circle\" ></i></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="EJECT DATA">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%; top: 60px;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Ejecutar Actividad </h3>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Activity?opt=5&action=1&idAct=" + idAct + "&event=1' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='' style='width: 93%;'>");
                out.print("<input type='date' class='form-control' name='txtDate' data-toggle='tooltip' data-placement='top' title='' required >");
                out.print("<textarea type='text' class='form-control' name='txtComent' style='margin: 12px;' id='' value='' placeholder='Explicar actividad ejecutada...' required ></textarea>");
                out.print("</div>");
                out.print("<input type='hidden' class='form-control' name='idEject' id='idAct'>");
                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-warning mt-2'>Ejecutar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="VERIFY DATA">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%; top: 60px;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Verificar Actividad </h3>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Activity?opt=5&action=2&idAct=" + idAct + "&event=1' method='post' class='needs-validation' novalidate=''>");
                out.print("<div class='' style='width: 93%;'>");
                out.print("<input type='date' class='form-control' name='txtDate' data-toggle='tooltip' data-placement='top' title='' required >");
                out.print("<textarea type='text' class='form-control' name='txtComent' style='margin: 12px;' id='' value='' placeholder='Comentario de la actividad ejecutada...' required ></textarea>");
                out.print("</div>");
                out.print("<input type='hidden' class='form-control' name='idVerf' id='idVer'>");
                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-info mt-2'>Verificar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="ACTIVITY DETAIL">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;align-items: flex-start;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Activity?opt=1&idAct=" + idAct + "&temp=1\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Seguimiento a la programacion de actividades</h2>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(4)'><i class='fas fa-eye'></i> R-TI-005</button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive divInfoCont'>");

                //<editor-fold defaultstate="collapsed" desc="HEADER">
                lst_activity = ActivityJpa.ConsultActivityId(idAct);
                if (lst_activity != null) {
                    Object[] ObjAct = (Object[]) lst_activity.get(0);

                    lst_activityDet = ActivityDetail.ConsultActivityDetailProcess(idAct);
                    if (lst_activityDet != null) {
                        Object[] Psc = (Object[]) lst_activityDet.get(0);
                        int Total = Integer.parseInt(Psc[0].toString());
                        int Eject = Integer.parseInt(Psc[1].toString());
                        int Verfy = Integer.parseInt(Psc[2].toString());
                        int Process = Integer.parseInt(Psc[3].toString());

                        String colorBg = ((Process > 0 && Process <= 10) ? "bg_ten" : (Process > 10 && Process <= 20) ? "bg_tweny"
                                : (Process > 20 && Process <= 30) ? "bg_thirty" : (Process > 30 && Process <= 40) ? "bg_fourty"
                                                : (Process > 40 && Process <= 50) ? "bg_fifty" : (Process > 50 && Process <= 60) ? "bg_sixty"
                                                                : (Process > 60 && Process <= 70) ? "bg_seventy" : (Process > 70 && Process <= 90) ? "bg_eighty"
                                                                                : (Process > 90 && Process <= 100) ? "bg_oneHundred" : "");
                        out.print("<div class='d-flex text-center' style='margin-bottom: 15px;'>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<button type='button' class='btn btn-dark'><span>Total actividades: <b>" + Total + "</b></span></button>");
                        out.print("</div>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<button type='button' class='btn btn-dark'><span>Ejecutadas: <b>" + Eject + "</b></span></button>");
                        out.print("</div>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<button type='button' class='btn btn-dark'><span>Verificadas: <b>" + Verfy + "</b></span></button>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='d-flex' style='justify-content: center; margin-bottom: 12px;'>");
                        out.print("<div class='col-lg-8'>");
                        out.print("<b>ACTIVIDAD A REALIZAR:</b> " + ObjAct[1] + "");
                        out.print("</div>");
                        out.print("<div class='col-lg-2'>");
                        out.print("<b>SEMANA:</b> " + ObjAct[2] + "");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='mb-4' style='width: 83%; margin: auto;'>");
                        out.print("<div class='progress'>");
                        out.print("<div class='progress-bar progress-bar-striped " + colorBg + "' role='progressbar' style='width: " + Process + "%' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'>" + Process + "%</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("</div>");

                    } else {
                    }

                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<div class='d-flex' style='justify-content: center;'>");
                out.print("<button type='button' class='btn btn-warning mr-2' id='bntEject' style='display: none;' onclick='mostrarConvencion(2)'>Ejecutar <i class='fas fa-exclamation-circle'></i></button>");
                out.print("<button type='button' class='btn btn-info mr-2' id='bntVerif' style='display: none;' onclick='mostrarConvencion(3)'>Verificar <i class='fas fa-check'></i></button>");
                out.print("</div>");

                lst_activityDet = ActivityDetail.ConsultActivityDetailIdAct(idAct);
                if (lst_activityDet != null) {
                    out.print("<table class='table table-bordered' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr class='text-center'>");
                    out.print("<th style='color: #4b4b4b;'>EQUIPO</th>");
                    out.print("<th style='color: #4b4b4b;'>ACTIVIDAD</th>");
                    out.print("<th style='color: #4b4b4b;'>RESPONSABLE</th>");
                    out.print("<th style='color: #f5f5f5;'>X</th>");
                    out.print("<th style='color: #4b4b4b;'>VERIFICACION</th>");
                    out.print("<th style='color: #4b4b4b;'>RESPONSABLE</th>");
                    out.print("<th style='color: #f5f5f5;'>X</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_activityDet.size(); i++) {
                        Object[] ObjDeta = (Object[]) lst_activityDet.get(i);
                        String[] pcDv_name = ObjDeta[2].toString().replace("[", "").replace("]", "").split("/");
                        String namepcDv = pcDv_name[1].toString();
                        out.print("<tr>");
                        out.print("<td>" + namepcDv + "</td>");
                        if (ObjDeta[3] == null) {
                            out.print("<td class='text-center text-warning'><b>Pendiente por ejecutar</b></td>");
                            out.print("<td class='text-center text-warning'><b>---</b></td>");
                            out.print("<td id='orangeToggle" + i + "' class='text-center'>");
                            out.print("<input id='checkOrange" + i + "' class='checkOrange' style='margin: auto;' type='checkbox' onclick='switchColorOrg( " + i + ", " + ObjDeta[0] + ")' data-toggle='tooltip' data-placement='top' title='Ejecutar Actividad'>");
                            out.print("</td>");
                        } else {
                            if (ObjDeta[7] == null) {
                                out.print("<td class='text-center'><span>" + ObjDeta[4] + "</span></td>");
                                out.print("<td class='text-center'>");
                                out.print("<div class=''>");
                                out.print("<span style='font-size: 12px;'>" + ObjDeta[6] + "<br> <b class='text-success'><i style='font-weight: bolder;'>" + ObjDeta[3] + "</i></b></span>");
                                out.print("</div>");
                                out.print("</td>");
                                out.print("<td id='orangeToggle" + i + "' class='text-center'>");
                                out.print("<input id='checkOrange" + i + "' class='checkOrange' style='margin: auto;' type='checkbox' onclick='switchColorOrg( " + i + ", " + ObjDeta[0] + ")' data-toggle='tooltip' data-placement='top' title='Modiifcar Actividad'>");
                                out.print("</td>");
                            } else {
                                out.print("<td class='text-center'><span>" + ObjDeta[4] + "</span></td>");
                                out.print("<td class='text-center'>");
                                out.print("<div class=''>");
                                out.print("<span style='font-size: 12px;'>" + ObjDeta[6] + "<br> <b class='text-success'><i style='font-weight: bolder;'>" + ObjDeta[3] + "</i></b></span>");
                                out.print("</div>");
                                out.print("</td>");
                                out.print("<td id='orangeToggle" + i + "' class='text-center'>");
                                out.print("<span class='text-success' data-toggle='tooltip' data-placement='top' title='Ya se verificó la actividad'><i style='font-size: 18px;' class=\"fas fa-check\"></i></span>");
                                out.print("</td>");
                            }
                        }
                        if (ObjDeta[3] == null) {
                            out.print("<td class='text-info text-center'><b>Pendiente por verificar</b></td>");
                            out.print("<td class='text-info text-center'><b>---</b></td>");
                            out.print("<td class='text-center text-info'>");
                            out.print("<span data-toggle='tooltip' data-placement='top' title='No se ha realizado la actividad'><i style='font-size: 18px;' class=\"fas fa-lock\"></i></span>");
                            out.print("</td>");
                        } else {
                            if (ObjDeta[7] == null) {
                                out.print("<td class='text-info text-center'><b>Pendiente por verificar</b></td>");
                                out.print("<td class='text-info text-center'><b>---</b></td>");
                                out.print("<td  id='blueToggle" + i + "' class='text-center '>");
                                out.print("<input id='checkBlue" + i + "' class='checkBlue' style='margin: auto;' type='checkbox' onclick='switchColorBle(" + i + ", " + ObjDeta[0] + ")' data-toggle='tooltip' data-placement='top' title='Verificar Actividad'>");
                                out.print("</td>");
                            } else {
                                out.print("<td class='text-center'>" + ObjDeta[8] + "</td>");
                                out.print("<td class='text-center'>");
                                out.print("<div class=''>");
                                out.print("<span style='font-size: 12px;'>" + ObjDeta[10] + "<br> <b class='text-success'><i style='font-weight: bolder;'>" + ObjDeta[7] + "</i></b></span>");
                                out.print("</div>");
                                out.print("</td>");
                                out.print("<td id='blueToggle" + i + "' class='text-center '>");
                                out.print("<input id='checkBlue" + i + "' class='checkBlue' style='margin: auto;' type='checkbox' onclick='switchColorBle(" + i + ", " + ObjDeta[0] + ")' data-toggle='tooltip' data-placement='top' title='Modificar Verificación'>");
                                out.print("</td>");
                            }
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");

                } else {
                    out.print("<div class=''>");
                    out.print("<h4>No se ha encontrado información de esta actividad.</h4>");
                    out.print("</div>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>

                //</editor-fold>
                //</editor-fold>
            } else if (event == 0) {
                //<editor-fold defaultstate="collapsed" desc="LIST ACTIVITY">
                LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
                WeekFields weekFields = WeekFields.of(Locale.getDefault());
                int totalWeeks = lastDayOfYear.get(weekFields.weekOfYear());
                int anioProd = year - 1990;

                if (idAct > 0 && temp == 0) {
                    //<editor-fold defaultstate="collapsed" desc="EDIT ACTIVITY">
                    lst_activity = ActivityJpa.ConsultActivityId(idAct);
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 63%; right: 10%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Modificar actividad</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='Activity?opt=2&idAct=" + idAct + "' method='post' class='needs-validation' novalidate=''>");
                    //<editor-fold defaultstate="collapsed" desc="ACTIVITY AND WEEK">
                    if (lst_activity != null) {
                        Object[] Act = (Object[]) lst_activity.get(0);
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-8'>");
                        out.print("<span class=''>Actividad</span>");
                        out.print("<input type='text' class='form-control' name='txtAct' id='' data-toggle='tooltip' data-placement='top' title='Escribir titulo de la actividad' value='" + Act[1] + "' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<span class=''>Semana</span>");
                        out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Seleccionar semana de programación'>");
                        out.print("<select class='form-control' name='cbxWeek' style='margin-top: 12px;' required>");
                        out.print("<option selected>" + Act[2] + "</option>");
                        for (int i = 1; i <= totalWeeks; i++) {
                            String weekAct = anioProd + ((i < 10) ? "0" : "") + i;
                            if (!weekAct.trim().equals(Act[2].toString().trim())) {
                                out.print("<option value='" + weekAct + "'>" + weekAct + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h6>Se ha presentado un error al consultar los datos de la actividad!, favor comunicarse con uno de los programadores!</h6>");
                        out.print("</div>");
                    }
                    //</editor-fold>

                    out.print("<div class='text-center'>");
                    out.print("<button class='btn btn-green'>Confirmar</button>");
                    out.print("</div>");

                    out.print("</form>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTER ACTIVITY">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 63%; right: 10%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Programar Actividades</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Activity?opt=4' method='post' class='needs-validation' novalidate='' id='formActi' autocomplete='off'>");
                //<editor-fold defaultstate="collapsed" desc="ACTIVITY AND WEEK">
                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-8'>");
                out.print("<span class=''>Actividad</span>");
                out.print("<input type='text' class='form-control' name='txtAct' id='' data-toggle='tooltip' data-placement='top' title='Escribir titulo de la actividad' value='' required>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class=''>Semana</span>");
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='Seleccionar semana de programación'>");
                out.print("<select class='form-control' name='cbxWeek' style='margin-top: 12px;' required>");
                out.print("<option selected disabled>Seleccionar semana</option>");
                for (int i = 1; i <= totalWeeks; i++) {
                    String weekAct = anioProd + ((i < 10) ? "0" : "") + i;
                    out.print("<option value='" + weekAct + "'>" + weekAct + "</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("<div class='d-flex' style='justify-content: center; margin-top: 3%;'>");
                out.print("<button type='button' id='btn1' class='btn btn-outline-dark mr-4' onclick='activeDiv(\"FirstDiv\", \"SecondDiv\", \"btn1\", \"btn2\", 1)' data-toggle='tooltip' data-placement='top' title='Selecionar PC o dipositivo especifico para la actividad'>Uno a Uno</button>");
                out.print("<button type='button' id='btn2' class='btn btn-outline-dark' onclick='activeDiv(\"SecondDiv\", \"FirstDiv\", \"btn2\", \"btn1\", 2)' data-toggle='tooltip' data-placement='top' title='Seleccionar PC o dipositivo por grupos para la actividad'>Grupo</button>");
                out.print("</div>");
                out.print("<div style='margin-top: 4%; margin-bottom: 4%; display: none;' id='FirstDiv'>");
                out.print("<div style='display: flex;'>");
                //<editor-fold defaultstate="collapsed" desc="ONE TO ONE">
                out.print("<div class='col-lg-6'>");
                out.print("<span class='mb-2'><b>Seleccionar computadores</b></span>");
                out.print("<div  style='margin-top: 10px;' data-toggle='tooltip' data-placement='top' title=''>");
                out.print("<select class='form-control select2' multiple='' name='cbxPc' id='cbxPc' style='margin-12px;'>");
                lst_computer = ComputerJpa.ConsulteComputerB_R();
                if (lst_computer != null) {
                    for (int i = 0; i < lst_computer.size(); i++) {
                        Object[] ObjComputer = (Object[]) lst_computer.get(i);
                        out.print("<option value='[" + ObjComputer[0] + "/" + ObjComputer[1] + "/" + ObjComputer[2] + "]'>" + ObjComputer[1] + "</option>");
                    }
                } else {
                    out.print("<option disabled value=''>Ha ocurrido un error</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-6'>");
                out.print("<span class='mb-2'><b>Seleccionar dispositivos</b></span>");
                out.print("<div style='margin-top: 10px;' data-toggle='tooltip' data-placement='top' title=''>");
                out.print("<select class='form-control select2' multiple='' name='cbxDv' id='cbxDv' style='margin-12px;'>");
                lst_computer = ComputerJpa.ConsulteDevicerB_R();
                if (lst_computer != null) {
                    for (int i = 0; i < lst_computer.size(); i++) {
                        Object[] ObjDevice = (Object[]) lst_computer.get(i);
                        out.print("<option value='[" + ObjDevice[0] + "/" + ObjDevice[3] + "/" + ObjDevice[4] + "]'>" + ObjDevice[3] + "</option>");
                    }
                } else {
                    out.print("<option disabled value=''>Ha ocurrido un error</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("<div style='margin-top: 4%; display: none;' id='SecondDiv'>");
                out.print("<div style='display: flex;'>");
                //<editor-fold defaultstate="collapsed" desc="MASSIVE">
                out.print("<div class='col-lg-3'>");
                out.print("<span class='mb-2'><b>Seleccionar computadores</b></span>");

                out.print("<div class='card-body'>");
                out.print("<button type='button' id='btnpc1' onclick=\"selectButtonpc(1)\" class='btn btn-outline-dark mb-2'><i class='fas fa-circle text-success '></i>&nbsp;Bueno  <i class='fas fa-circle text-warning '></i>&nbsp;Revision</button><br>");
                out.print("<button type='button' id='btnpc2' onclick=\"selectButtonpc(2)\" class='btn btn-outline-danger mb-2'><i class='fas fa-circle text-danger '></i>&nbsp;Critico</button>");
                out.print("<input type='hidden' class='form-control' name='cbxPc' id='selectedPc' value=''>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div class='col-lg-9'>");
                lst_computer = ComputerJpa.ConsulteDevicer_x_Act();
                out.print("<span class='mb-2'><b>Seleccionar dispositivos</b></span>");

                out.print("<div class='card-body'>");
                out.print("<div class='row'>");

                if (lst_computer != null) {
                    for (int i = 0; i < lst_computer.size(); i++) {
                        Object[] ObjDvi = (Object[]) lst_computer.get(i);
                        out.print("<div class='col-lg-4'>");
                        out.print("<button type='button' id='btnx" + i + "' class='btn btn-outline-info mb-2' onclick=\"selectButtondv(" + ObjDvi[0] + ", " + i + ")\">" + ObjDvi[1] + "</button>");
                        out.print("</div>");
                    }
                }
                out.print("<input type='hidden' class='form-control' name='cbxDv' id='selectedDv' value=''>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("<input type='hidden' class='form-control' name='typeSelect' id='typeSelec' value=''>");
                out.print("<div class='mt-4 text-center'>");
                out.print("<button type='button' class='btn btn-green' onclick='validForm()'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");
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
                out.print("<h2>Actividades programadas</h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr class='text-center'>");
                out.print("<th>Ver</th>");
                out.print("<th>Actividad</th>");
                out.print("<th>Semana</th>");
                out.print("<th>Fecha</th>");
                out.print("<th>Registro</th>");
                out.print("<th>Modificacion</th>");
                out.print("<th>Progreso</th>");
                out.print("<th>Editar</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");

                if (temp == 1) {
                    lst_activity = ActivityJpa.ConsultActivityRecent(idAct);
                } else {
                    lst_activity = ActivityJpa.ConsultActivity();
                }

                if (lst_activity != null) {
                    for (int i = 0; i < lst_activity.size(); i++) {
                        Object[] ObjAct = (Object[]) lst_activity.get(i);
                        if (temp == 1 && i == 0) {
                            out.print("<tr style='background:#33bf9826;'>");
                            out.print("<td  class='text-center'><button class='btn btn-green btn-sm' onclick='window.location.href=\"Activity?opt=1&event=1&idAct=" + ObjAct[0] + "\"' data-toggle='tooltip' data-placement='top' title='Visto recientemente'><i class='fas fa-clock'></i></button></td>");
                        } else {
                            out.print("<tr>");
                            out.print("<td  class='text-center'><button class='btn btn-green btn-sm' onclick='window.location.href=\"Activity?opt=1&event=1&idAct=" + ObjAct[0] + "\"' data-toggle='tooltip' data-placement='top' title='Ver detalle'><i class='fas fa-eye'></i></button></td>");
                        }
                        out.print("<td>" + ObjAct[1] + "</td>");
                        out.print("<td>" + ObjAct[2] + "</td>");
                        out.print("<td>" + ObjAct[3] + "</td>");
                        out.print("<td>" + ObjAct[5] + "</td>");

                        if (ObjAct[6] != null) {
                            out.print("<td class='text-center'><span data-toggle='tooltip' data-palcement='top' title='" + ObjAct[8] + "'>" + ObjAct[6] + "</td>");
                        } else {
                            out.print("<td class='text-center'><span><i>-Sin modificar-</i></td>");
                        }
                        int progss = Integer.parseInt(ObjAct[9].toString());

                        String colorBg = ((progss >= 0 && progss <= 10) ? "bg_ten" : (progss > 10 && progss <= 20) ? "bg_tweny"
                                : (progss > 20 && progss <= 30) ? "bg_thirty" : (progss > 30 && progss <= 40) ? "bg_fourty"
                                                : (progss > 40 && progss <= 50) ? "bg_fifty" : (progss > 50 && progss <= 60) ? "bg_sixty"
                                                                : (progss > 60 && progss <= 70) ? "bg_seventy" : (progss > 70 && progss <= 90) ? "bg_eighty"
                                                                                : (progss > 90 && progss <= 100) ? "bg_oneHundred" : "");
                        out.print("<td>");
                        out.print("<div class='progress'>");
                        out.print("<div class='progress-bar progress-bar-striped " + colorBg + "' role='progressbar' style='width: " + progss + "%; font-weight: bolder; " + ((progss < 10) ? "color: black; text-align: center;" : "") + "' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'>" + progss + "%</div>");
                        out.print("</div>");
                        out.print("</td>");

                        out.print("<td>");
                        out.print("<div class='d-flex text-center'>");
                        out.print("<button class='btn btn-warning btn-sm' onclick='window.location.href=\"Activity?opt=1&idAct=" + ObjAct[0] + "\"'><i class='fas fa-edit'></i></button>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='8' class='text-center'>No se han encontrado actividades, registradas. Puedes agregar la primera haciendo clic en el boton +</td>");
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
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_activity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
