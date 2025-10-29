package tag;

import controlador.appControllerJpa;
import controlador.caseControllerJpa;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import controlador.caseLogControllerJpa;

public class Tag_support extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        caseLogControllerJpa caseLogJpa = new caseLogControllerJpa();
        int idRoleSystem = Integer.parseInt(pageContext.getSession().getAttribute("idRol").toString());
        caseControllerJpa caseJpa = new caseControllerJpa();
        appControllerJpa appJpa = new appControllerJpa();
        List lst_caseLog = null;
        List lst_case = null;
        List lst_app = null;
        List lst_result = null;
        int idSupport = 0, idApp = 0, idCaseApp = 0;
        String event = "", ConValue = "";
        try {
            try {
                event = pageContext.getRequest().getAttribute("event").toString();
            } catch (Exception e) {
                event = "";
            }
            if (event.equals("")) {
                //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4></h4>");
                out.print("<h2>Soporte para aplicativos</h2>");
                out.print("<h4></h4>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<div class='card-body'>");
                out.print("<div class='row' style='justify-content: space-around;'>");
                lst_app = appJpa.ConsultAppsActive();
                if (lst_app != null) {
                    for (int i = 0; i < lst_app.size(); i++) {
                        Object[] ObjApp = (Object[]) lst_app.get(i);
                        out.print("<div class='AppCont'>");
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-4' style='padding-top: 7%;padding-left: 0px;'>");
                        if (ObjApp[3] != null) {
                            out.print("<div class='gallery'>");
                            out.print("<div class=\"gallery-item\" style='width: 39px; height: 39px;' data-image='Interfaz/Contenido/dataFiles/" + ObjApp[3] + "' href='Interfaz/Contenido/dataFiles/" + ObjApp[3] + "' data-title=\"Image " + i + "\" style='background-image: url(Interfaz/Contenido/dataFiles/" + ObjApp[3] + ");'></div>");
                            out.print("</div>");
                        } else {
                            out.print("<p>Error</p>");
                        }
                        out.print("</div>");
                        out.print("<div class='col-lg-8' style='padding-top: 7%;'>");
                        out.print("<h6>" + ObjApp[1] + "</h6>");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-green btn-sm' onclick='window.location.href=\"Support?opt=1&event=Support&idApp=" + ObjApp[0] + "\"'>Soporte&nbsp;<i class=\"fas fa-wrench\"></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                } else {
                    out.print("<div class=''>");
                    out.print("Se ha producido un error al consultar la información.");
                    out.print("</div>");

                }
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else if (event.equals("Support")) {
                //<editor-fold defaultstate="collapsed" desc="MODULE SUPPORT BY APP">
                try {
                    idApp = Integer.parseInt(pageContext.getRequest().getAttribute("idApp").toString());
                } catch (Exception e) {
                    idApp = 0;
                }
                try {
                    ConValue = pageContext.getRequest().getAttribute("ConValue").toString();
                } catch (Exception e) {
                    ConValue = "";
                }
                try {
                    idSupport = Integer.parseInt(pageContext.getRequest().getAttribute("idSupport").toString());
                } catch (Exception e) {
                    idSupport = 0;
                }
                try {
                    idCaseApp = Integer.parseInt(pageContext.getRequest().getAttribute("idCaseApp").toString());
                } catch (Exception e) {
                    idCaseApp = 0;
                }
                try {
                    lst_result = (List) pageContext.getRequest().getAttribute("ListResult");
                } catch (Exception e) {
                    lst_result = null;
                }
                if (idSupport > 0) {
                    //<editor-fold defaultstate="collapsed" desc="DETAIL">

                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_reg' style='width: 65%; margin-left: 25%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Detalle del caso Nro. " + idSupport + "</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<div class='card-body'>");
                    out.print("<div class='row'>");

                    lst_caseLog = caseLogJpa.ConsultCaseLogByCase(idSupport);
                    if (lst_caseLog != null) {
                        Object[] ObjLog = (Object[]) lst_caseLog.get(0);
                        out.print("<div class='col-lg-4 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Aplicativo</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[0] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-4 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Nombre del caso</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[1] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-4 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Tipo de caso</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[2] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-4 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Ejecutor</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[3] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-4 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Usuario ejecutor</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[4] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-4 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Observaciones</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[5] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-6 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Fecha ejecucion</b></div>");
                        out.print("<div class='sqDetail'><span class='txtDetail'>" + ObjLog[7] + "</span></div>");
                        out.print("</div>");

                        out.print("<div class='col-lg-6 mb-2 mt-2'>");
                        out.print("<div class='titleDetail'><b class=''>Resultado</b></div>");
                        String resl = "";
                        try {
                            resl = ObjLog[8].toString();
                        } catch (Exception e) {
                            resl = "-";
                        }
                        out.print("<div class='sqDetail'><span class='txtDetail'><b class='text-" + ((resl.contains("Exitoso")) ? "success'>" + resl + " <i class=\"fas fa-check-circle\"></i>" : "danger'>" + resl + " <i class=\"fas fa-exclamation-circle\"></i>") + "</b></span></div>");
                        out.print("</div>");
                        if (idRoleSystem == 1) {
                            out.print("<div class='col-lg-8 mb-2 mt-2' style='margin: auto;'>");
                            out.print("<div class='titleDetail'><b class=''>Script ejecutado</b></div>");
                            out.print("<div class='sqDetail'><span class='txtDetail'>" + ((ObjLog[6] == null) ? "-" : ObjLog[6]) + "</span></div>");
                            out.print("</div>");
                        }
                    } else {
                        out.print("<div class='col-lg-12 text-center'>");
                        out.print("<h2><b class=''>Se ha presentado un error al consultar la información del caso</b></h2>");
                        out.print("<span class=''></span>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTER NEW CASE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:" + ((idCaseApp > 0) ? "block" : "none") + ";'>");
                out.print("<div class='cont_reg' style='width: 70%; margin-left: 24%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar caso</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                //<editor-fold defaultstate="collapsed" desc="FORM CONSULT CASE">
                out.print("<form action='Support?opt=1&event=Support&idApp=" + idApp + "' method='post' class='needs-validation' novalidate='' name='FormCase'>");
                out.print("<div class='d-flex' style='justify-content: center; align-items: center;'>");
                out.print("<div class=''>");
                out.print("<span class='mr-2'><b style='font-size: 15px;'>Seleccionar caso</b></span>");
                out.print("</div>");

                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title=''>");
                out.print("<select class='form-control' name='idCaseApp' style='margin: 12px;' onchange='FormCase.submit()'>");
                out.print("<option selected disabled value=''>Seleccionar caso</option>");
                lst_case = caseJpa.ConsultCaseAppId(idApp);
                if (lst_case != null) {
                    for (int i = 0; i < lst_case.size(); i++) {
                        Object[] ObjC = (Object[]) lst_case.get(i);
                        int idC = Integer.parseInt(ObjC[0].toString());
                        if (idCaseApp == idC) {
                            out.print("<option selected value='" + idC + "'>" + ObjC[3] + "</option>");
                        } else {
                            out.print("<option value='" + idC + "'>" + ObjC[3] + "</option>");
                        }
                    }
                } else {
                    out.print("<option disabled value=''>Error al consultar los datos</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");
                out.print("</form>");
                //</editor-fold>

                if (idCaseApp > 0) {
                    //<editor-fold defaultstate="collapsed" desc="VALIDATE DATA FROM CONSULT">
                    lst_case = caseJpa.ConsultCaseId(idCaseApp);
                    if (lst_case != null) {
                        Object[] OjCase = (Object[]) lst_case.get(0);
                        if (OjCase[12] != null) {
                            out.print("<form action='Support?opt=2&event=Support&idApp=" + idApp + "' method='post' class='needs-validation' novalidate='' id='formReload'>");
                            //<editor-fold defaultstate="collapsed" desc="FIELDS OF CONSULT">
                            out.print("<input type='hidden' class='form-control' name='idCaseApp' id='' data-toggle='tooltip' data-placement='top' title='' value='" + idCaseApp + "'>");
                            String[] fieldType = OjCase[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<input type='hidden' class='form-control' name='idSett' id='' value='" + OjCase[12] + "'>"); // ID DE LA CONFIGURACION
                            out.print("<div class='d-flex' style='align-items: center; margin: auto; justify-content: center;'>");
                            for (int i = 0; i < fieldType.length; i++) {
                                out.print("<div class='col-lg-6 d-flex' style='align-items: center;'>");
                                String[] field = fieldType[i].toString().split("/");
                                out.print("<input type='hidden' class='form-control' name='txtNamer' id='' value='" + field[0] + "'>"); // ESTA LINEA TIENE EL NOMBRE POR EL CUAL SE VA A CONSULTAR EL VALOR 
                                out.print("<input type='hidden' class='form-control' name='txtConsultData' id='' value='" + OjCase[4] + "'>"); // SCRIPT DE LA CONSULTA
                                out.print("<span class='mr-2' style='font-size: 15px;'><b>" + field[0] + "</b></span>");
                                out.print("<input type='" + field[1] + "' class='form-control' name='txt" + field[0] + "'>"); // ESTE ES EL VALOR
                                out.print("</div>");
                            }
                            out.print("<div style=''>");
                            out.print("<button class='btn btn-info' type='button' onclick='LoadData()'><i class='fas fa-search'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="LIST OF CONSULT AND FIELDS EJECTION">
                            if (lst_result != null && lst_result.size() > 0) {
//                            out.print("la lista tiene datos rey :D " + lst_result.size() + "");
                                out.print("<div class='mb-2 text-center'>");
                                out.print("<span><b class='text-warning'>Favor verificar muy bien la información antes de ejecutar los casos!</b></span><br>");
                                out.print("<span class='text-dark'>Debe seleccionar 1 o mas registros a los cuales se les aplicará el caso.</span>");
                                out.print("</div>");
                                String data = (String) lst_result.get(0);
                                String[] had = data.replace("]-[", "///").replace("]", "").replace("[", "").split("///");
                                out.print("<div class='table-responsive'>");

                                //<editor-fold defaultstate="collapsed" desc="BUILD DATA TABLE">
                                out.print("<table class='table table-bordered table-md' id='data-table'>");
                                out.print("<thead>");
                                out.print("<tr>");
                                for (int i = 0; i < had.length; i++) {
                                    out.print("<th>" + had[i] + "</th>");
                                }
                                out.print("</tr>");
                                out.print("</thead>");
                                out.print("<tbody>");
                                for (int i = 1; i < lst_result.size(); i++) {
                                    String Objs = (String) lst_result.get(i);
                                    String[] bdy = Objs.replace("]--[", "////").replace("]", "").replace("[", "").split("////");
                                    out.print("<tr data-id=" + bdy[0] + " style='cursor: pointer;'>");
                                    for (int j = 0; j < bdy.length; j++) {
                                        out.print("<td>" + bdy[j] + "</td>");
                                    }
                                    out.print("</tr>");
                                }
                                out.print("</tbody>");
                                out.print("</table>");
                                out.print("</div>");
                                //</editor-fold>

                                String fieldEjec = "";
                                try {
                                    fieldEjec = OjCase[8].toString();
                                } catch (Exception e) {
                                    fieldEjec = "";
                                }
                                //<editor-fold defaultstate="collapsed" desc="FIELDS EN CASE OF NEED REPLACE VALUES">
                                if (!fieldEjec.equals("")) {
                                    String[] Eject = fieldEjec.replace("][", "///").replace("]", "").replace("[", "").split("///");
                                    out.print("<div class='card-body'>");
                                    out.print("<div class='row' style='justify-content: center;'>");
                                    for (int i = 0; i < Eject.length; i++) {
                                        String[] detail = Eject[i].split("/");
                                        String fieldName = detail[0].toString().replace("_", " ");
                                        out.print("<div class='col-lg-4'>");
                                        out.print("<span class=''>" + fieldName + "</span>");
                                        out.print("<input type='" + detail[1] + "' class='form-control' name='fld" + detail[0] + "' data-toggle='tooltip' data-placement='top' title='" + fieldName + "' required>");
                                        out.print("</div>");
                                    }
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                                //</editor-fold>
                                out.print("<input type='hidden' class='form-control' name='ejectConsult' id='consultEJe' value='" + OjCase[7] + "'>");
                                out.print("<input type='hidden' class='form-control' name='fieldEJe' id='fieldEJe' value='" + fieldEjec + "'>");
                                out.print("<input type='hidden' class='form-control' name='idToChangue' id='selected-id' value='' required>");
                                out.print("<div class='text-center'>");
                                out.print("<button class='btn btn-info' type='button' onclick='sendData()'>Ejecutar</button>");
                                out.print("</div>");
                            } else {
                                out.print("<div class='mb-2 text-center'>");
                                out.print("<h6>No se han encontrado datos en el momento</h6>");
                                out.print("</div>");
                            }
                            //</editor-fold>
                            out.print("</form>");
                        } else {
                            out.print("<div class='text-center mt-4'>");
                            out.print("<h3><span class='text-warning'>Ups ha ocurrido un error, parece que el caso numero " + idCaseApp + " no esta correctamente parametrizado.</span></h3>");
                            out.print("<span class='text-dark'>Verifique que la aplicacion tiene la correcta conexión al servidor. </span>");
                            out.print("</div>");
                        }
                    }
                    //</editor-fold>
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="MODULE SUPPORT BY APP">
                String nameApp = "";
                lst_app = appJpa.ConsultAppId(idApp);
                if (lst_app != null) {
                    Object[] ObjAp = (Object[]) lst_app.get(0);
                    nameApp = ObjAp[1].toString();
                }
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"support.jsp\"' data-toggle='tooltip' data-placement='top' title='Volver'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Soporte para " + nameApp + "</h2>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Agregar'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                out.print("<table class='table table-bordered' id='table-1'>");
                out.print("<thead>");
                out.print("<tr class='text-center'>");
//                out.print("<th>ID</th>");
                out.print("<th>Fecha ejecucion</th>");
                out.print("<th>Ejecutor</th>");
                out.print("<th>Caso ejecutado</th>");
                out.print("<th>Tipo caso</th>");
                out.print("<th>Observaciones</th>");
                out.print("<th>Resultado</th>");
                out.print("<th>Detalle</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_caseLog = caseLogJpa.ConsultCaseLogByApp(idApp);
                if (lst_caseLog != null) {
                    for (int i = 0; i < lst_caseLog.size(); i++) {
                        Object[] ObjC = (Object[]) lst_caseLog.get(i);
                        out.print("<tr class='text-center'>");
//                        out.print("<td>" + ObjC[0] + "</td>");
                        out.print("<td>" + ObjC[2] + "</td>");
                        out.print("<td>" + ObjC[5] + "</td>");
                        out.print("<td>" + ObjC[3] + "</td>");
                        out.print("<td>" + ObjC[4] + "</td>");
                        out.print("<td>" + ObjC[6] + "</td>");
                        String result = ObjC[7].toString();
                        out.print("<td>");
                        out.print("<div class=''>");
                        if (result.contains("Exitoso")) {
                            out.print("<b class='text-success'>" + result + "</b>");
                        } else if (result.contains("Fallo")) {
                            out.print("<b class='text-danger'>" + result + "</b>");
                        }
                        out.print("</div>");
                        out.print("</td>");
                        out.print("<td>");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-info' onclick='window.location.href=\"Support?opt=1&event=Support&idApp=" + idApp + "&idSupport=" + ObjC[0] + "\"'><i class='fas fa-search'></i></button>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='6' class='text-center'><span>No se ha encontrado historial de casos para este aplicativo</span></td>");
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
            }
            //</editor-fold>
        } catch (Exception e) {
        }
        return super.doStartTag();
    }
}
