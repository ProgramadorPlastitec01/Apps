package Tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import Controller.RoleControllerJpa;
import Controller.PendingControllerJpa;
import Controller.UserControllerJpa;
import Controller.ReportControllerJpa;
import Controller.ComputerInformationControllerJpa;
import Controller.ComputerControllerJpa;
import Controller.AreaControllerJpa;

public class Tag_report extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        PendingControllerJpa PendingJpa = new PendingControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        UserControllerJpa UserJpa = new UserControllerJpa();
        AreaControllerJpa AreaJpa = new AreaControllerJpa();
        ReportControllerJpa ReportJpa = new ReportControllerJpa();
        ComputerInformationControllerJpa InformationJpa = new ComputerInformationControllerJpa();
        ComputerControllerJpa ComputerJpa = new ComputerControllerJpa();
        List lst_role = null, lst_user = null, lst_reportPending = null, lst_computer = null, lst_computerInfo = null, lst_area = null, lst_type = null;
        String Affair = "", Charge = "", Person = "", Description = "", Solution = "", DateR = "", Qrt = "",
                DateF = "";
        int IdPending = 0, Priority = 0, ProgressInitial = 0, ProgressFinal = 0, Count = 0, Filter = 0;
        try {
            if (pageContext.getRequest().getAttribute("Report").toString().equals("Pending")) {
                //<editor-fold defaultstate="collapsed" desc="VARIABLE">
                try {
                    Filter = Integer.parseInt(pageContext.getRequest().getAttribute("Filter").toString());
                } catch (NumberFormatException e) {
                    Filter = 0;
                }
                try {
                    IdPending = Integer.parseInt(pageContext.getRequest().getAttribute("IdPending").toString());
                } catch (NumberFormatException e) {
                    IdPending = 0;
                }
                try {
                    Affair = pageContext.getRequest().getAttribute("Txt_affair").toString();
                } catch (Exception e) {
                    Affair = "";
                }
                try {
                    Priority = Integer.parseInt(pageContext.getRequest().getAttribute("Txt_priority").toString());
                } catch (NumberFormatException e) {
                    Priority = 0;
                }
                try {
                    Charge = pageContext.getRequest().getAttribute("Txt_charge").toString();
                } catch (Exception e) {
                    Charge = "";
                }
                try {
                    Person = pageContext.getRequest().getAttribute("Txt_person").toString();
                } catch (Exception e) {
                    Person = "";
                }
                try {
                    DateR = pageContext.getRequest().getAttribute("DateRegister").toString();
                } catch (Exception e) {
                    DateR = "";
                }
                try {
                    DateF = pageContext.getRequest().getAttribute("DateSolution").toString();
                } catch (Exception e) {
                    DateF = "";
                }
                try {
                    ProgressInitial = Integer.parseInt(pageContext.getRequest().getAttribute("Txt_progressInitial").toString());
                } catch (NumberFormatException e) {
                }
                try {
                    ProgressFinal = Integer.parseInt(pageContext.getRequest().getAttribute("Txt_progressFinal").toString());
                } catch (NumberFormatException e) {
                    ProgressFinal = 0;
                }
                try {
                    Description = pageContext.getRequest().getAttribute("Txt_description").toString();
                } catch (Exception e) {
                    Description = "";
                }
                try {
                    Solution = pageContext.getRequest().getAttribute("Txt_solution").toString();
                } catch (Exception e) {
                    Solution = "";
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REPORT PENDING">
                out.print("<div class=\"card card-secondary\">");
                out.print("<div class=\"card-header\">\n"
                        + "                    <h4>Reporte Pendiente</h4>\n"
                        + "                    <div class=\"card-header-action\">\n"
                        + "                      <a data-collapse=\"#mycard-collapse\"  style='border-radius: 4px !important;' class=\"btn btn-icon btn-info\" href=\"#\"><i class=\"fas fa-" + ((Filter > 0) ? "plus" : "minus") + "\"></i></a>\n"
                        + "                    </div>\n"
                        + "                  </div>");
                out.print("<div class=\"card-body\">");
                //<editor-fold defaultstate="collapsed" desc="FILTER">
                out.print("<div class=\"collapse " + ((Filter > 0) ? "" : "show") + " \" id=\"mycard-collapse\">");
                out.print("<div id='DivPendingF' style='display:block; overflow:hidden; transition:max-height 0.5s ease-out;'>");
                out.print("<form action='Report?opt=1' method='post' name='FormReportPending' id='FormReportPending' class='needs-validation' novalidate=''>");
                out.print("<div class='d-flex  justify-content-around align-items-start w-100'>");
                out.print("<div class='form-group' style='width:20%'><b>ID Pendiente </b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-bell\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='number' class='form-control' name='IdPending' placeholder='ID'>"
                        + "</div>"
                        + "</div>");

                out.print("<div class='form-group' style='width:20%'><b>Asunto </b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fab fa-amilia\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Txt_affair' class='form-control' placeholder='Asunto'>"
                        + "</div>"
                        + "</div>");

                out.print("<div class=\"form-group\" style='width:20%'><b>Prioridad </b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-exclamation\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Txt_priority'>");
                out.print("<option selected disabled value=''>Seleccione Prioridad</option>");
                out.print("<option value='1'>ALTA</option>");
                out.print("<option value='2'>MEDIA</option>");
                out.print("<option value='3'>BAJA</option>");
                out.print("</select></div>");
                out.print("</div>");

                out.print("<div class=\"form-group\" style='width:20%'><b>Cargo </b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-user-friends\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Txt_charge' id='Select1'>");
                out.print("<option selected disabled value=''>Seleccione Cargo</option>");
                lst_role = RoleJpa.ConsultRole();
                if (lst_role != null) {
                    for (int i = 0; i < lst_role.size(); i++) {
                        Object[] obj_role = (Object[]) lst_role.get(i);
                        out.print("<option value='" + obj_role[1] + "'>" + obj_role[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-3'>");

                out.print("<div class=\"form-group\" style='width:20%'>"
                        + "<b>Funcionario </b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-user-alt\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Txt_person' >");
                out.print("<option selected disabled value=''>Seleccione Funcionario</option>");
                lst_user = UserJpa.ConsultPersonalActive();
                if (lst_user != null) {
                    for (int i = 0; i < lst_user.size(); i++) {
                        Object[] obj_user = (Object[]) lst_user.get(i);
                        out.print("<option value='" + obj_user[6] + "'>" + obj_user[6] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"form-group\" style='width:20%'>"
                        + "<b>Fecha Registro</b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-calendar\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='DateRegister' class='form-control daterange-cus' autocomplete='off'>"
                        + "</div>"
                        + "</div>");
                out.print("<div class=\"form-group\" style='width:20%'>"
                        + "<b>Fecha Solucion</b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-calendar\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='DateSolution' class='form-control daterange-cus' autocomplete='off'>"
                        + "</div>"
                        + "</div>");

                out.print("<div class='form-group'  style='width:20%'>"
                        + "<b>Progreso </b>"
                        + "<div class='d-flex justify-content-between'>"
                        + "<div style='width:45%'><div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-play\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='number' min='0' max='100' name='Txt_progressInitial' id='ProgressInitial' oninput=\"validarInput(this)\" class='form-control' placeholder=''>"
                        + "</div></div>"
                        + "<div style='width:45%'><div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-stop\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='number' min='0' max='100' name='Txt_progressFinal' id='ProgressFinal' onchange=\"validarInputTwo(this)\" class='form-control' placeholder='' disabled>"
                        + "</div></div></div>"
                        + "</div>");

                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-3'>");

                out.print("<div class=\"form-group\"  style='width:20%'>"
                        + "<b>Descripción </b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"far fa-comment-alt\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' class='form-control'name='Txt_description' placeholder='Descripción' autocomplete='off'>"
                        + "</div>"
                        + "</div>");

                out.print("<div class=\"form-group\"  style='width:20%'>"
                        + "<b>Solución </b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-comment-alt\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' class='form-control' placeholder='Solución' name='Txt_solution' autocomplete='off'>"
                        + "</div>"
                        + "</div>");

                out.print("</div>");
                out.print("<div class='mr-0' style='float: inline-end;'>");
                out.print("<button type='button' class='btn btn-warning mr-3' data-toggle='tooltip' data-placement='top' title='Limpiar' onclick='limpiarFormulario()'><i class='fas fa-eraser mr-2'></i>Limpiar</button>");
                out.print("<button type='button' class='btn btn-green' data-toggle='tooltip' data-placement='top' title='Registar' onclick=\"validarFormulario()\"><i class='fas fa-search mr-2'></i>Buscar</button>");
                out.print("</div>");
                out.print("<input type='hidden' name='Filter' value='1'>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='border-bottom: 1px solid rgba(0, 0, 0, .125);margin: 14px;'>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONTENT">
                out.print("</div>");
                if (Filter > 0) {
                    lst_reportPending = ReportJpa.ConsultReportPending(IdPending, Affair, Priority, Charge, Person, DateR, DateF, ProgressInitial, ProgressFinal, Description, Solution, Count);
                    if (lst_reportPending != null) {
                        out.print("<table class='table table-bordered' id='table-1'>");
                        out.print("<thead>");
                        out.print("<tr>");
                        out.print("<th>Id</th>");
                        out.print("<th>Prioridad</th>");
                        out.print("<th>Asunto</th>");
                        out.print("<th>Para </th>");
                        out.print("<th>Descripción</th>");
                        out.print("<th>Solución</th>");
                        out.print("<th class='text-center'><i style='font-size:17px' class=\"fab fa-font-awesome-flag\"></i></th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        for (int i = 0; i < lst_reportPending.size(); i++) {
                            Object[] obj_reportP = (Object[]) lst_reportPending.get(i);
                            out.print("<tr>");
                            out.print("<td>" + obj_reportP[0] + "</td>");
                            out.print("<td>" + ((obj_reportP[2].equals("ALTA")) ? "<span style='color:red'>ALTA</span>" : (obj_reportP[2].equals("MEDIA") ? "<span style='color:orange'>MEDIA</span>" : "<span>BAJA</span>")) + "</td>");
                            out.print("<td><span data-toggle='tooltip' data-placement='top' title='Registrado por: \n " + obj_reportP[10] + " '>" + ((obj_reportP[1] == null) ? "<b class='text-warning'>Sin asunto OLD </b>" : obj_reportP[1]) + "</span></td>");
                            out.print("<td>" + ((obj_reportP[3] == null) ? "<b class='text-warning'>Sin responsable OLD </b>" : obj_reportP[3]) + "</td>");

                            out.print("<td id='TxLower" + i + "' >");
                            if (Integer.parseInt(obj_reportP[14].toString()) >= 200) {
                                //<editor-fold defaultstate="collapsed" desc="LINK CHARACTERS >= 200">
                                out.print("<i class=\"fas fa-file-export\"></i>&nbsp;&nbsp;<a style='color:#3c00cb !important;cursor: pointer;' onclick='ViewDetailPendingDesc(" + i + ");'>"
                                        + obj_reportP[5].toString().replace("&nbsp;", "").trim() + "</a>");
                                //<editor-fold defaultstate="collapsed" desc="VIEW CONTENT DESC">
                                out.print("<div class='sweet-local' tabindex='-1' id='ViewDesc" + i + "' style='opacity: 1.03; display:none;'>");
                                out.print("<div class='contGeneral ScrollDivContent'>");

                                out.print("<div style='display: flex; justify-content: space-between'>");
                                out.print("<h4>Detalle Pendiente</h4>");
                                out.print("<button class='btn btn-outline-secondary' onclick='ViewDetailPendingDesc(" + i + ")' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                                out.print("</div>");

                                out.print(obj_reportP[4].toString()
                                        .replace("<div contenteditable=\"true\">", "<div contenteditable=\"false\">")
                                        .replace("<img ", "<img style='width:100%'"));

                                out.print("</div>");
                                out.print("</div>");

                                //</editor-fold>
                                //</editor-fold>
                            } else {
                                out.print(obj_reportP[5].toString()
                                        .replace("<p><img", "<p><img style='width:30%'")
                                        .replace("<p>&nbsp;</p>", ""));
                            }
                            out.print("</td>");
                            if (obj_reportP[9] != null) {
                                out.print("<td>");
                                if (obj_reportP[7].toString().contains("[")) {
                                    //<editor-fold defaultstate="collapsed" desc="VALID []">

                                    String[] ArgSlt = obj_reportP[7].toString().split("///");
                                    out.print("<div><button class='btn btn-green text-center' onclick='ViewDetailPending(" + i + ")'>Detalle <i class=\"fas fa-search\"></i></button></div>");
                                    out.print("<div class='sweet-local' tabindex='-1' id='View" + i + "' style='opacity: 1.03; display:none;'>");
                                    out.print("<div class='contGeneral ScrollDivContent'>");

                                    out.print("<div style='display: flex; justify-content: space-between'>");
                                    out.print("<h4>Detalle Pendiente</h4>");
                                    out.print("<button class='btn btn-outline-secondary' onclick='ViewDetailPending(" + i + ")' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                                    out.print("</div>");

                                    for (int j = 0; j < ArgSlt.length; j++) {
                                        String[] ArgSltD = ArgSlt[j].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                        //<editor-fold defaultstate="collapsed" desc="VIEW CONTENT PENDING []">

                                        out.print("<div class='FollowUp'>");
                                        out.print("<div class='d-flex justify-content-between'>");

                                        out.print("<div class='d-flex '>");
                                        out.print("<img src=\"Interface/Content/Assets/img/avatar/avatar-1.png\" alt=\"image\" class='mr-2' style='    width: 24px;height: 24px;'>");
                                        out.print(ArgSltD[2]);
                                        out.print("</div>");

                                        out.print("<div>");
                                        out.print("<b>Progreso</b> (" + ArgSltD[3] + "%)");
                                        out.print("</div>");

                                        out.print("</div>");

                                        out.print("<div class='d-flex justify-content-between'>");
                                        out.print("<div><b>Observación:</b></div>");
                                        out.print("<div>" + ArgSltD[0].replace("T", " ") + "</div>");
                                        out.print("</div>");

                                        out.print("<div>" + ArgSltD[1].replace("<img", "<img ") + "</div>");
                                        out.print("</div>");
                                        //</editor-fold>
                                    }
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="SOLUTION OLD">
                                    if (Integer.parseInt(obj_reportP[12].toString()) >= 200) {
                                        out.print("<i class=\"fas fa-file-export\"></i>&nbsp;&nbsp;<a style='color:#fb4600 !important;cursor: pointer;' onclick='ViewDetailPending(" + i + ");'>"
                                                + obj_reportP[13].toString()
                                                        .replace("<img ", "<img style='width:30%'")
                                                        .replace("<p>&nbsp;</p>", "").replace("<p>", "").replace("</p>", "")
                                                        .replace("<div>", "").replace("</div>", "")
                                                        .replace("<p>&nbsp;</p>", "")
                                                        .replace("<img ", "<div><img ") + "</div></a>");
                                    } else {
                                        out.print(obj_reportP[7].toString()
                                                .replace("<p><img", "<p><img style='width:70%'")
                                                .replace("<p>&nbsp;</p>", ""));
                                    }

                                    //<editor-fold defaultstate="collapsed" desc="VIEW CONTENT SOLUTION">
                                    out.print("<div class='sweet-local' tabindex='-1' id='View" + i + "' style='opacity: 1.03; display:none;'>");
                                    out.print("<div class='contGeneral ScrollDivContent'>");
                                    out.print("<div style='display: flex; justify-content: space-between'>");
                                    out.print("<h4>Detalle Pendiente</h4>");
                                    out.print("<button class='btn btn-outline-secondary' onclick='ViewDetailPending(" + i + ")' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                                    out.print("</div>");
                                    out.print(obj_reportP[7].toString().replace("<img ", "<img class=\"img-fluid\" style='width:100%'"));
                                    out.print("</div>");
                                    out.print("</div>");
                                    //</editor-fold>
                                    //</editor-fold>
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td  style='background:#efefef'><b class='text-warning'>Sin Solución</b></td>");
                            }
                            out.print("<td>");
                            //<editor-fold defaultstate="collapsed" desc="PROGRESS">
                            String uniqueId = "progressText" + i;
                            double progressPercentage = Double.parseDouble(obj_reportP[6].toString());
                            double strokeDashoffset = 125.66 - (progressPercentage / 100) * 125.66;
                            out.print("<div data-toggle='tooltip' data-placement='top' title='Progreso'>\n"
                                    + "        <svg>\n"
                                    + "            <circle class=\"bg\" cx=\"25\" cy=\"25\" r=\"20\"></circle>\n"
                                    + "            <circle class=\"meter\" cx=\"25\" cy=\"25\" r=\"20\" style=\"--progress: " + strokeDashoffset + ";\"></circle>\n"
                                    + "            <text x=\"25\" y=\"25\" class=\"progress-text\" id=\"" + uniqueId + "\">" + obj_reportP[6].toString() + "</text>\n"
                                    + "        </svg>\n"
                                    + "    </div>");

                            out.print("</div>");
                            //</editor-fold>
                            out.print("</td>");
                            out.print("</tr>");
                        }
                        out.print("</tbody>");
                        out.print("</table>");

                    } else {
                        out.print("<div class='d-flex justify-content-center align-baseline'><h3><i style='font-size:31px' class=\"far fa-surprise\"></i> No se encontraron registros</h3></div>");
                    }
                }
                //</editor-fold>
                //End CardBody
                out.print("</div>");
                //</editor-fold>
            } else if (pageContext.getRequest().getAttribute("Report").toString().equals("Information")) {
                //<editor-fold defaultstate="collapsed" desc="VARIABLE">
                try {
                    Filter = Integer.parseInt(pageContext.getRequest().getAttribute("Filter").toString());
                } catch (NumberFormatException e) {
                    Filter = 0;
                }
                try {
                    Qrt = pageContext.getRequest().getAttribute("Qrt").toString();
                } catch (Exception e) {
                    Qrt = "";
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="REPORT INFORMATION PC">
                out.print("<div class=\"card card-secondary\">");
                out.print("<div class=\"card-header\">\n"
                        + "                    <h4>Reporte Información PC</h4>\n"
                        + "                    <div class=\"card-header-action\">\n"
                        + "                      <a data-collapse=\"#mycard-collapse\"  style='border-radius: 4px !important;' class=\"btn btn-icon btn-info\" href=\"#\"><i class=\"fas fa-" + ((Filter > 0) ? "plus" : "minus") + "\"></i></a>\n"
                        + "                    </div>\n"
                        + "                  </div>");
                out.print("<div class=\"card-body\">");
                //<editor-fold defaultstate="collapsed" desc="FILTER">
                out.print("<div class=\"collapse " + ((Filter > 0) ? "" : "show") + " \" id=\"mycard-collapse\">");
                out.print("<div id='DivPendingF' style='display:block; overflow:hidden; transition:max-height 0.5s ease-out;'>");
                out.print("<form action='Report?opt=2' method='post' name='FormReportInformation' id='FormReportInformation' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='Filter' value='1'> ");
                out.print("<div class='d-flex  justify-content-around align-items-start w-100'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 1">
                out.print("<div class='' style='flex-wrap:nowrap' style='width:20%' ><b>PC </b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-desktop\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
//                out.print("<div><select class='form-control select2' name='IdPC' id=''>");
                out.print("<div><select class='form-control select2 widthSelect2' name='IdPC' id=''>");
                out.print("<option selected disabled value=''>Seleccione PC</option>");
                lst_computer = ComputerJpa.ViewComputer();
                if (lst_computer != null) {
                    for (int i = 0; i < lst_computer.size(); i++) {
                        Object[] obj_computer = (Object[]) lst_computer.get(i);
                        out.print("<option value='" + obj_computer[0] + "'>" + obj_computer[1] + "</option>");
                    }
                }
                out.print("</select></div>");
                out.print("</div>"
                        + "</div>");

                out.print("<div class='' style='width:20%'><b>Antivirus </b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-shield-alt\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Antivirus'>");
                out.print("<option selected value='' style=\"pointer-events: none; background: #e9ecef;\">Seleccione opcion</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>"
                        + "</div>");

                out.print("<div class=\"\" style='width:20%'><b>Correo Interno </b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                            <i class=\"fas fa-envelope\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Internal_Mail' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Correo Externo </b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"fas fa-envelope-open\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='External_Mail' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                //</editor-fold>
                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-1'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 2">

                out.print("<div class=\"\" style='width:20%'><b>Descripción</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"far fa-comment-alt\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Description' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Factura</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"fas fa-file-invoice\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Bill' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Fecha Factura</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"fas fa-calendar\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='date' name='Bill_Date' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Gmail</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-at\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Gmail' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");
//</editor-fold>
                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-1'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 3">
                out.print("<div class=\"\" style='width:20%'><b>Garantia</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-sign\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Warranty' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Fecha Garantia</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-calendar\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='date' name='Warranty_Date' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class='' style='width:20%'><b>Internet</b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"fab fa-google\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Internet' >");
                out.print("<option selected value='' style=\"pointer-events: none; background: #e9ecef;\">Seleccione opcion</option>");
                out.print("<option value='SI'>SI</option>");
                out.print("<option value='NO'>NO</option>");
                out.print("</select>");
                out.print("</div>"
                        + "</div>");

                out.print("<div class=\"\" style='width:20%'><b>Login Plastitec</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-portrait\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Login_Plastitec' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-1'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 4">
                out.print("<div class=\"\" style='width:20%'><b>Licencia</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-file-signature\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Licence' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Fecha Licencia</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-calendar\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='date' name='Licence_date' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class='' style='width:20%'><b>MAC</b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"fas fa-cog\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='MAC' class='form-control' autocomplete='off'>");
                out.print("</div>"
                        + "</div>");

                out.print("<div class=\"\" style='width:20%'><b>Proveedor</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-user-tie\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Supplier' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-1'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 5">
                out.print("<div class=\"\" style='width:20%'><b>Punto de Red</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-genderless\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Network_Point' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>RED</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-network-wired\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='RED' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class='' style='width:20%'><b>Skype</b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                           <i class=\"fab fa-skype\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Skype' class='form-control' autocomplete='off'>");
                out.print("</div>"
                        + "</div>");

                out.print("<div class=\"\" style='width:20%'><b>Tipo Estado</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-check-square\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Type_State'>");
                out.print("<option selected value='' style=\"pointer-events: none; background: #e9ecef;\">Seleccione Estado</option>");
                out.print("<option value='BUENO'>BUENO</option>");
                out.print("<option value='EN REVISION'>EN REVISION</option>");
                out.print("<option value='DE BAJA'>DE BAJA</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-1'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 6">
                out.print("<div class=\"\" style='width:20%'><b>IP</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-server\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='IP' class='form-control' autocomplete='off'>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>VLAN</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-project-diagram\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='VLAN' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");

                out.print("<div class='' style='width:20%'><b>VPN</b>"
                        + "<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-wifi\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='VPN' class='form-control' autocomplete='off'>");
                out.print("</div>"
                        + "</div>");

                out.print("<div class=\"\" style='width:20%'><b>Version Office</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                         <i class=\"fas fa-mail-bulk\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Office_Version' class='form-control' autocomplete='off'>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                out.print("</div>");

                out.print("<div class='d-flex justify-content-around align-items-start mt-1 mb-3'>");
                //<editor-fold defaultstate="collapsed" desc="ROW 7">
                out.print("<div class=\"\" style='width:20%'><b>Version WIN</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fab fa-windows\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='WIN_Version' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");
                out.print("<div class=\"\" style='width:20%'><b>Responsable</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-user\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>"
                        + "<input type='text' name='Responsible' class='form-control' autocomplete='off'>"
                        + "</div>");
                out.print("</div>");
                out.print("<div class=\"\" style='width:20%'><b>Area</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-users\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Area'>");
                out.print("<option selected value='' style=\"pointer-events: none; background: #e9ecef;\">Seleccione Area</option>");
                lst_area = AreaJpa.ConsultAreaActive();
                if (lst_area != null) {
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] ObjArea = (Object[]) lst_area.get(i);
                        out.print("<option value='" + ObjArea[2] + "'>" + ObjArea[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class=\"\" style='width:20%'><b>Tipo PC</b>");
                out.print("<div class=\"input-group\">"
                        + "<div class=\"input-group-prepend\">\n"
                        + "                          <div class=\"input-group-text\">\n"
                        + "                          <i class=\"fas fa-clipboard-list\"></i>\n"
                        + "                          </div>\n"
                        + "                        </div>");
                out.print("<select class='form-control' name='Type_PC'>");
                out.print("<option selected value='' style=\"pointer-events: none; background: #e9ecef;\">Seleccione Tipo</option>");
                lst_type = ComputerJpa.ViewComputerType();
                if (lst_type != null) {
                    for (int o = 0; o < lst_type.size(); o++) {
                        Object[] ObjType = (Object[]) lst_type.get(o);
                        out.print("<option value='" + ObjType[1] + "'>" + ObjType[1] + "</option>");
                    }
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='mr-0' style='float: inline-end;'>");
                out.print("<button type='button' class='btn btn-warning mr-3' data-toggle='tooltip' data-placement='top' title='Limpiar' onclick='ClearInforData()'><i class='fas fa-eraser mr-2'></i>Limpiar</button>");
                out.print("<button type='button' class='btn btn-green' onclick=\"validateFormInfo()\"><i class='fas fa-search mr-2'></i>Buscar</button>");
                out.print("</div>");
                out.print("<input type='hidden' name='Filter' value='1'>");
                out.print("</form>");
                out.print("</div>");
                out.print("<div style='border-bottom: 1px solid rgba(0, 0, 0, .125);margin: 14px;'>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONTENT">
                out.print("<div class=\"card-body\">");
                out.print("<table class='table table-bordered table-hover' id='table-4'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>PC</th>");
                out.print("<th>Responsable</th>");
                out.print("<th>IP</th>");
                out.print("<th>MAC</th>");
                out.print("<th>Area</th>");
                out.print("<th>Antivirus</th>");
                out.print("<th>Correo Interno</th>");
                out.print("<th>Correo Externo</th>");
                out.print("<th>Descripcion</th>");
                out.print("<th>Factura</th>");
                out.print("<th>Fecha Factura</th>");
                out.print("<th>Gmail</th>");
                out.print("<th>Garantia</th>");
                out.print("<th>Fecha Garantia</th>");
                out.print("<th>Internet</th>");
                out.print("<th>Login Plastitec</th>");
                out.print("<th>Licencia</th>");
                out.print("<th>Licencia Fin</th>");
                out.print("<th>Proveedor</th>");
                out.print("<th>Punto de red</th>");
                out.print("<th>Red</th>");
                out.print("<th>Skype</th>");
                out.print("<th>Tipo Estado</th>");
                out.print("<th>Proceso</th>");
                out.print("<th>Vlan</th>");
                out.print("<th>VPN</th>");
                out.print("<th>Versión Office</th>");
                out.print("<th>Versión WIN</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (!Qrt.equals("")) {
                    lst_computerInfo = InformationJpa.FilterComputer(Qrt);
                    if (lst_computerInfo != null) {
                        for (int i = 0; i < lst_computerInfo.size(); i++) {
                            Object[] ObjInfo = (Object[]) lst_computerInfo.get(i);
                            out.print("<tr>");
                            out.print("<td>" + ((ObjInfo[2] == null) ? "" : ObjInfo[2]) + "</td>");
                            out.print("<td>" + ((ObjInfo[4] == null) ? "" : ObjInfo[4]) + "</td>");
                            out.print("<td>" + ((ObjInfo[7] == null) ? "" : ObjInfo[7]) + "</td>");
                            out.print("<td>" + ((ObjInfo[8] == null) ? "" : ObjInfo[8]) + "</td>");
                            out.print("<td>" + ((ObjInfo[5] == null) ? "" : ObjInfo[5]) + "</td>");
                            out.print("<td>" + ((ObjInfo[13] == null) ? "" : ObjInfo[13]) + "</td>");
                            out.print("<td>" + ((ObjInfo[18] == null) ? "" : ObjInfo[18]) + "</td>");
                            out.print("<td>" + ((ObjInfo[19] == null) ? "" : ObjInfo[19]) + "</td>");
                            out.print("<td>" + ((ObjInfo[30] == null) ? "" : ObjInfo[30]) + "</td>");
                            out.print("<td>" + ((ObjInfo[20] == null) ? "" : ObjInfo[20]) + "</td>");
                            out.print("<td>" + ((ObjInfo[21] == null) ? "" : ObjInfo[21]) + "</td>");
                            out.print("<td>" + ((ObjInfo[17] == null) ? "" : ObjInfo[17]) + "</td>");
                            out.print("<td>" + ((ObjInfo[25] == null) ? "" : ObjInfo[25]) + "</td>");
                            out.print("<td>" + ((ObjInfo[26] == null) ? "" : ObjInfo[26]) + "</td>");
                            out.print("<td>" + ((ObjInfo[14] == null) ? "" : ObjInfo[14]) + "</td>");
                            out.print("<td>" + ((ObjInfo[6] == null) ? "" : ObjInfo[6]) + "</td>");
                            out.print("<td>" + ((ObjInfo[22] == null) ? "" : ObjInfo[22]) + "</td>");
                            out.print("<td>" + ((ObjInfo[23] == null) ? "" : ObjInfo[23]) + "</td>");
                            out.print("<td>" + ((ObjInfo[24] == null) ? "" : ObjInfo[24]) + "</td>");
                            out.print("<td>" + ((ObjInfo[29] == null) ? "" : ObjInfo[29]) + "</td>");
                            out.print("<td>" + ((ObjInfo[9] == null) ? "" : ObjInfo[9]) + "</td>");
                            out.print("<td>" + ((ObjInfo[16] == null) ? "" : ObjInfo[16]) + "</td>");
                            out.print("<td>" + ((ObjInfo[3] == null) ? "" : ObjInfo[3]) + "</td>");
                            out.print("<td>" + ((ObjInfo[28] == null) ? "" : ObjInfo[28]) + "</td>");
                            out.print("<td>" + ((ObjInfo[10] == null) ? "" : ObjInfo[10]) + "</td>");
                            out.print("<td>" + ((ObjInfo[15] == null) ? "" : ObjInfo[15]) + "</td>");
                            out.print("<td>" + ((ObjInfo[12] == null) ? "" : ObjInfo[12]) + "</td>");
                            out.print("<td>" + ((ObjInfo[11] == null) ? "" : ObjInfo[11]) + "</td>");
                            out.print("</tr>");
                        }
                    }
                }
                out.print("</tbody>");
                out.print("</table>");
                out.print("</div>");
                //</editor-fold>
                //</editor-fold>
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_report.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }

}
