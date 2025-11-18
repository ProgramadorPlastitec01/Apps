package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.ConfigurationControllerJpa;
import Controller.SegmentationControllerJpa;
import Controller.EventControllerJpa;
import Controller.CIIUControllerJpa;
import java.util.List;
import java.text.NumberFormat;

public class Tag_Segmentation extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        SegmentationControllerJpa SegmentationJpa = new SegmentationControllerJpa();
        ConfigurationControllerJpa ConfigurationJpa = new ConfigurationControllerJpa();
        EventControllerJpa EventJpa = new EventControllerJpa();
        CIIUControllerJpa CIUUJpa = new CIIUControllerJpa();
        List lst_Segmentation = null, lst_Conf1 = null, lst_Conf2 = null, lst_Conf3 = null, lst_Conf4 = null, lst_Conf5 = null, lst_Conf6 = null, lst_config = null,
                lst_SegmentationId = null, lst_CIIU = null, lst_Conf7 = null, lst_Configuration = null, lst_Event = null, lst_visit = null;
        int IdSegmentation = 0, Qualification = 0, Experience = 0, Relationship = 0, Temp = 0, LevelCode1 = 0, LevelCode2 = 0,
                BaselIndex = 0, CorruptionIndex = 0, BirderyIndex = 0, State = 0, IdVisit = 0, vcv = 0;
        double CalcQ = 0.0, CalcEx = 0.0, CalcRel = 0.0, sumT = 0.0, LevelRisk = 0, CalcBasil = 0, CalcCorruption = 0, CalcBidery = 0;
        String Format = "", ListAttach = "", Query = "";
        String VlrFormatt = "";
        try {
            IdSegmentation = Integer.parseInt(pageContext.getRequest().getAttribute("IdSegmentation").toString());
        } catch (NumberFormatException e) {
            IdSegmentation = 0;
        }
        try {
            Temp = Integer.parseInt(pageContext.getRequest().getAttribute("Temp").toString());
        } catch (NumberFormatException e) {
            Temp = 0;
        }
        try {
            Format = pageContext.getRequest().getAttribute("Format").toString();
        } catch (Exception e) {
            Format = "";
        }
        try {
            IdVisit = Integer.parseInt(pageContext.getRequest().getAttribute("IdVisit").toString());
        } catch (NumberFormatException e) {
            IdVisit = 0;
        }
        try {
            if (Temp == 1) {
                //<editor-fold defaultstate="collapsed" desc="VISIT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_form_visit'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<div style='display:flex;width:90%;align-items:normal;'>"
                        + "<div class='mr-2' ><button class='btn btn-blue' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-plus'></i></button></div>"
                        + "<div ><h4>Visita</h4></div>"
                        + "</div>");
                out.print("<div><button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button></div>");
                out.print("</div>");
                if (IdVisit > 0) {
                    //<editor-fold defaultstate="collapsed" desc="VISIT UPDATE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='cont_visit_register'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h4>Modificar Visita</h4>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<div class='cont_form_temp'>");
                    lst_visit = EventJpa.ConsultEventId(IdVisit);
                    if (lst_visit != null) {
                        Object[] obj_visitId = (Object[]) lst_visit.get(0);
                        out.print("<form action='AttachVisit.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' class='form-control' name='IdSegmentation' id='' value='" + IdSegmentation + "'>");
                        out.print("<input type='hidden' class='form-control' name='Format' id='' value='" + Format + "'>");

                        out.print("<div class='col-11'>");
                        out.print("<input type='date' class='form-control' name='Txt_Date'  id='Date' placeholder='Fecha evento' value='" + obj_visitId[2] + "' required data-toggle='tooltip' data-placement='top' title='Fecha evento'>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                        out.print("</div>");

                        out.print("<div class='col-11 mb-2''>");
                        out.print("<input type='text' class='form-control' name='Txt_Affair'  id='Affair' placeholder='Asunto' value='" + obj_visitId[3] + "' required data-toggle='tooltip' data-placement='top' title='Asunto'>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                        out.print("</div>");

                        out.print("<div class='col-11 mb-2'>");
                        out.print("<textarea class='form-control' name='Txt_Description' id='Description' placeholder='Descripción'  required data-toggle='tooltip' data-placement='top' title='Descripción'>" + obj_visitId[4] + "</textarea>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                        out.print("</div>");
                        out.print("<input type='hidden' class='form-control' name='IdVisit' id='IdVisit' value='" + IdVisit + "'>");
                        String ArgFile = obj_visitId[5].toString().replace("[", "").replace("]", "");
                        out.print("<input type='hidden' class='form-control' name='OldAttach' id='OldAttach' value='" + ArgFile + "'>");
                        out.print("<div class='col-11 mb-2' style='text-align:center;'>");
                        out.print("<h6 style='color:#002237'>¿Desea remplazar el archivo?</h6>");
                        out.print("<div style='display:flex;text-align: center;justify-content: space-evenly;'>"
                                + "<div class='mr-2'>"
                                + "<button type='button' class='btn btn-outline-warning' onclick='ValidateAttachment(1)' id='BtnVal1' style='height: 30px;padding: 3px;width: 30px;'>SI</button></div>");
                        out.print("<div>"
                                + "<button type='button' class='btn btn-warning' onclick='ValidateAttachment(0)' id='BtnVal0' style='height: 30px;padding: 3px;width: 30px;'>NO</button></div>"
                                + "</div>");
                        out.print("</div>");

                        out.print("<div class='col-9 mb-2' style='display:none;margin-left:13%' id='Div1'>");
                        out.print("<input type='file' class='form-control col-lg-12 TypeFile' name='File' id='IdFile' placeholder='' value='' data-toggle='tooltip' data-placement='top' title=''>");

                        out.print("<script>");
                        out.print("document.getElementById('IdFile').addEventListener('change', function(){ "
                                + "var input = this; "
                                + "var NameFile = input.files[0].name; "
                                + "});");
                        out.print("</script>");
                        out.print("</div>");

                        out.print("<div class='col-11 mb-2' style='text-align:center;' id='Div0'>");
                        out.print("<button type='button' onclick='window.location.href=\"Download?File_name=" + ArgFile + "\"' class='btn btn-info'>Ver archivo adjuntado <i class=\"fas fa-download\"></i></button>");
                        out.print("</div>");

                        out.print("<div class='col-11 mb-2' style='width: 100%; text-align:center; margin-top: 12px;'>");
                        out.print("<button class='btn btn-blue btn-lg'>Modificar</button>");
                        out.print("</div>");

                        out.print("</form>");
                    } else {

                    }
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTER VISIT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:none;'>");
                out.print("<div class='cont_visit_register'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar Visita</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_temp'>");
                out.print("<form action='AttachVisit.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' class='form-control' name='IdSegmentation' id='' value='" + IdSegmentation + "'>");
                out.print("<input type='hidden' class='form-control' name='Format' id='' value='" + Format + "'>");
                out.print("<div class='col-11'>");
                out.print("<input type='date' class='form-control' name='Txt_Date'  id='Date' placeholder='Fecha evento' required data-toggle='tooltip' data-placement='top' title='Fecha evento'>");
                out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                out.print("</div>");

                out.print("<div class='col-11 mb-2''>");
                out.print("<input type='text' class='form-control' name='Txt_Affair'  id='Affair' placeholder='Asunto' required data-toggle='tooltip' data-placement='top' title='Asunto'>");
                out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                out.print("</div>");

                out.print("<div class='col-11 mb-2'>");
                out.print("<textarea class='form-control' name='Txt_Description' id='Description' placeholder='Descripción' required data-toggle='tooltip' data-placement='top' title='Descripción'></textarea>");
                out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                out.print("</div>");
                out.print("<input type='hidden' class='form-control' name='IdVisit' id='IdVisit' value='0'>");
                out.print("<input type='hidden' class='form-control' name='OldAttach' id='OldAttach' value='NA'>");
                out.print("<div class='col-11 mb-2'>");
                out.print("<input type='file' class='form-control col-lg-12 TypeFile' name='File' id='IdFile' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                out.print("<script>");
                out.print("document.getElementById('IdFile').addEventListener('change', function(){ "
                        + "var input = this; "
                        + "var NameFile = input.files[0].name; "
                        + "var DownloadFile = document.getElementById('DownloadFile'); "
                        + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\">Ver archivo <i class=\"fas fa-download\"></i></a>'; "
                        + "});");
                out.print("</script>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
                out.print("<button class='btn btn-blue btn-lg'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="CONSULT VISIT">
                lst_Event = EventJpa.ConsultEvent(IdSegmentation);
                out.print("<div class='card-body'>");
                out.print("<table class=\"table table-sm table-hover\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th scope=\"col\">Fecha Evento</th>");
                out.print("<th scope=\"col\">Asunto</th>");
                out.print("<th scope=\"col\">Descripción</th>");
                out.print("<th scope=\"col\">Adjunto</th>");
                out.print("<th scope=\"col\">Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                if (lst_Event != null) {
                    for (int i = 0; i < lst_Event.size(); i++) {
                        Object[] obj_Event = (Object[]) lst_Event.get(i);
                        out.print("<tr>");
                        out.print("<td>" + obj_Event[2] + "</td>");
                        out.print("<td>" + obj_Event[3] + "</td>");
                        out.print("<td>" + obj_Event[4] + "</td>");
                        out.print("<td>");
                        String ArgFile = obj_Event[5].toString().replace("[", "").replace("]", "");
                        out.print("<button type='button' onclick='window.location.href=\"Download?File_name=" + ArgFile + "\"' class='btn btn-info'>Ver archivo <i class=\"fas fa-download\"></i></button>");
                        out.print("</td>");
                        out.print("<td><button onclick=\"window.location.href='Segmentation?opt=1&IdSegmentation=" + IdSegmentation + "&Temp=1&Format=INTERNATIONAL&IdVisit=" + obj_Event[0] + "'\" class=\"btn btn-outline-primary btn-sm mr-2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Editar Visita\"><i class=\"fas fa-pencil-alt\"></i></button>"
                                + "</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr><td colspan='4' style='text-align:center;'>No existe visitas registradas</td></tr>");
                }
                out.print("</tbody>");
                out.print("</table>");
                //</editor-fold>
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (Temp == 2) {
                //<editor-fold defaultstate="collapsed" desc="VIEW DETAIL">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_form_detail'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<div style='width:95%;text-align:center;'><h4>Contenido detallado</h4></div>");
                out.print("<div style='width:5%;'><button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button></div>");
                out.print("</div>");
                lst_SegmentationId = SegmentationJpa.ConsultSegmentationId(IdSegmentation);
                if (lst_SegmentationId != null) {
                    Object[] obj_Segmentation = (Object[]) lst_SegmentationId.get(0);
                    out.print("<ul class=\"nav nav-tabs\" style='justify-content:center;' id=\"myTab2\" role=\"tablist\">\n"
                            + "                      <li class=\"nav-item\">\n"
                            + "                        <a class=\"nav-link active\" id=\"basic-tab2\" data-toggle=\"tab\" href=\"#basic\" role=\"tab\" aria-controls=\"BasicInformation\" aria-selected=\"true\">Información Basica</a>\n"
                            + "                      </li>\n"
                            + "                      <li class=\"nav-item\">\n"
                            + "                        <a class=\"nav-link\" id=\"risk-tab2\" data-toggle=\"tab\" href=\"#risk\" role=\"tab\" aria-controls=\"Análisis de riesgo\" aria-selected=\"false\">Análisis de riesgo</a>\n"
                            + "                      </li>\n"
                            + "                      <li class=\"nav-item\">\n"
                            + "                        <a class=\"nav-link\" id=\"oea-tab2\" data-toggle=\"tab\" href=\"#oea\" role=\"tab\" aria-controls=\"Controles OEA\" aria-selected=\"false\">Controles OEA</a>\n"
                            + "                      </li>\n"
                            + "                      <li class=\"nav-item\">\n"
                            + "                        <a class=\"nav-link\" id=\"sagrilaf-tab2\" data-toggle=\"tab\" href=\"#sagrilaf\" role=\"tab\" aria-controls=\"Controles SAGRILAFT y PTEE\" aria-selected=\"false\">Controles SAGRILAFT y PTEE</a>\n"
                            + "                      </li>\n"
                            + "                    </ul>");

                    out.print("<div class=\"tab-content tab-bordered\" id=\"myTab3Content\">");

                    out.print("<div class=\"tab-pane fade show active\" id=\"basic\" role=\"tabpanel\" aria-labelledby=\"basic-tab2\">");
                    //<editor-fold defaultstate="collapsed" desc="BASIC - INFORMATION">

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Código Plastitec</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[2] == null) ? "" : obj_Segmentation[2]) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Fecha Registro</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[3] == null) ? "" : obj_Segmentation[3]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Área responsable</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[4] == null) ? "" : obj_Segmentation[4]) + "</div>");
                    if (obj_Segmentation[32].equals("NATIONAL")) {
                        out.print("<div class='DivDetail b_color'><b>NIT / ID: </b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[6] == null) ? "" : obj_Segmentation[6]) + "</div>");
                    } else {
                        out.print("<div class='DivDetail b_color'><b>TAX / ID: </b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[6] == null) ? "" : obj_Segmentation[6]) + "</div>");
                    }
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Asociado de negocio</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[7] == null) ? "" : obj_Segmentation[7]) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Tipo</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[5] == null) ? "" : obj_Segmentation[5]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    if (obj_Segmentation[32].equals("NATIONAL")) {
                        out.print("<div class='DivDetail b_color'><b>Ciudad</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[12] == null) ? "" : obj_Segmentation[12]) + "</div>");
                    } else {
                        out.print("<div class='DivDetail b_color'><b>País</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[12] == null) ? "" : obj_Segmentation[12]) + "</div>");
                    }
                    out.print("<div class='DivDetail b_color'><b>PEP</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[21] == null) ? "" : obj_Segmentation[21]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Beneficiario Final</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[9] == null) ? "" : obj_Segmentation[9]) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Observacion</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[31] == null) ? "" : obj_Segmentation[31]) + "</div>");
                    out.print("</div>");

                    if (Format.equals("NATIONAL")) {
                        out.print("<div style='display:flex;justify-content:space-evenly'>");
                        out.print("<div class='DivDetail b_color'><b>Código CIIU principal</b></div><div class='DivDetailDescription'>");
                        if (obj_Segmentation[10] != null) {
                            lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_Segmentation[10].toString()));
                            if (lst_CIIU != null) {
                                Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                out.print(obj_CIIU[1]);
                                lst_CIIU = null;
                            }
                        } else {
                            out.print("Sin dato registrado");
                        }
                        out.print("</div>");
                        out.print("<div class='DivDetail b_color'><b>Actividad económica primaria</b></div><div class='DivDetailDescription'>");
                        if (obj_Segmentation[10] != null) {
                            lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_Segmentation[10].toString()));
                            if (lst_CIIU != null) {
                                Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                Object[] obj_Setting = (Object[]) lst_Conf7.get(0);
                                if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                    out.print("<b style='color:red;'>Riesgo Alto</b>");
                                } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                    out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                }
                                LevelCode1 = Integer.parseInt(obj_Setting[2].toString());
                                lst_CIIU = null;
                            } else {
                                out.print("Sin codigo CIIU asociado");
                            }
                        } else {
                            out.print("Sin dato registrado");
                        }
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div style='display:flex;justify-content:space-evenly'>");
                        out.print("<div class='DivDetail b_color'><b>Código CIIU secundario</b></div><div class='DivDetailDescription'>");
                        if (obj_Segmentation[11] != null) {
                            lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_Segmentation[11].toString()));
                            if (lst_CIIU != null) {
                                Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                out.print(obj_CIIU[1]);
                                lst_CIIU = null;
                            }
                            out.print("</div>");
                            out.print("<div class='DivDetail b_color'><b>Actividad económica secundario</b></div><div class='DivDetailDescription'>");
                            lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_Segmentation[11].toString()));
                            if (lst_CIIU != null) {
                                Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                Object[] obj_Setting = (Object[]) lst_Conf7.get(0);
                                if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                    out.print("<b style='color:red;'>Riesgo Alto</b>");
                                } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                    out.print("<b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print("<b style='color:gray;'>Riesgo Medio</b>");
                                }
                                LevelCode2 = Integer.parseInt(obj_Setting[2].toString());
                            } else {
                                out.print("Sin codigo CIIU asociado");
                            }
                        } else {
                            out.print("Sin dato registrado");
                        }
                        out.print("</div>");
                        out.print("</div>");
                    }

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Tipo de persona</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[8] == null) ? "" : obj_Segmentation[8]) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Representante Legal</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[13] == null) ? "" : obj_Segmentation[13]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Cargo de Desempeña</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[15] == null) ? "" : obj_Segmentation[15]) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Operación anuales</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[16] == null) ? "" : obj_Segmentation[16]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    NumberFormat format = NumberFormat.getInstance();
                    if (obj_Segmentation[17] != null) {
                        vcv = Integer.parseInt(obj_Segmentation[17].toString());
                        VlrFormatt = format.format(vcv);
                    } else {
                        vcv = 0;
                    }
                    out.print("<div class='DivDetail b_color'><b>Valor compras y ventas anuales</b></div><div class='DivDetailDescription'>" + ((vcv == 0) ? "" : VlrFormatt) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Antiguedad (AÑOS)</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[18] == null) ? "" : obj_Segmentation[18]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Tipo de servicio</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[20] == null) ? "" : obj_Segmentation[20]) + "</div>");
                    out.print("<div class='DivDetail b_color'><b>Cadena de suministro</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[22] == null) ? "" : obj_Segmentation[22]) + "</div>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class=\"tab-pane fade\" id=\"risk\" role=\"tabpanel\" aria-labelledby=\"risk-tab2\">");
                    //<editor-fold defaultstate="collapsed" desc="ANALITYS RISK LEVEL">
                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetailControl b_color'><b>Calificación OEA. CT - PAT. o avalada por aduana del país</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[23] == null) ? "" : obj_Segmentation[23]) + "</div>");
                    out.print("<div class='DivDetailControl b_color'><b>Experencia en el mercardo</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[24] == null) ? "" : obj_Segmentation[24]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetailControl b_color'><b>Tiempo de relación cormencial</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[25] == null) ? "" : obj_Segmentation[25]) + "</div>");
                    out.print("<div class='DivDetailControl b_color'><b>Información del carga</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[26] == null) ? "N/A" : obj_Segmentation[26]) + "</div>");
                    out.print("</div>");
                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetailControl b_color'><b>Puntaje total</b></div><div class='DivDetailDescription'>");
                    lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                    if (lst_Configuration != null) {
                        Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                        if (obj_Segmentation[23] != null && obj_Segmentation[24] != null && obj_Segmentation[25] != null) {
                            Qualification = Integer.parseInt(obj_Segmentation[23].toString());
                            Experience = Integer.parseInt(obj_Segmentation[24].toString());
                            Relationship = Integer.parseInt(obj_Segmentation[25].toString());
                            String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                            CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                            CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                            CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                            sumT = CalcQ + CalcEx + CalcRel;
                            out.print(sumT);
                        } else {
                            out.print("Sin datos");
                        }
                    } else {
                        out.print("Fallo en el calculo");
                    }
                    out.print("</div>");
                    out.print("<div class='DivDetailControl b_color'><b>Nivel de riesgo del negocio</b></div><div class='DivDetailDescription'>");
                    lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                    if (lst_Configuration != null) {
                        Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                        if (obj_Segmentation[23] != null && obj_Segmentation[24] != null && obj_Segmentation[25] != null) {
                            Qualification = Integer.parseInt(obj_Segmentation[23].toString());
                            Experience = Integer.parseInt(obj_Segmentation[24].toString());
                            Relationship = Integer.parseInt(obj_Segmentation[25].toString());
                            String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                            CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                            CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                            CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                            sumT = CalcQ + CalcEx + CalcRel;
                            if (sumT >= 6) {
                                out.print("<b style='color:red;'>Riesgo Alto</b>");
                            } else if (sumT <= 4) {
                                out.print("<b style='color:green;'>Riesgo Bajo</b>");
                            } else {
                                out.print("<b style='color:gray;'>Riesgo Medio</b>");
                            }
                            LevelRisk = sumT;
                        } else {
                            out.print("Sin datos");
                        }
                    } else {
                        out.print("Fallo en el calculo");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class=\"tab-pane fade\" id=\"oea\" role=\"tabpanel\" aria-labelledby=\"oea-tab2\">");
                    //<editor-fold defaultstate="collapsed" desc="CONTROL OEA">
                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Aplica acuerdo</b></div><div style='width:18;'>");
                    if (obj_Segmentation[23] != null) {
                        Qualification = Integer.parseInt(obj_Segmentation[23].toString());
                        if (Qualification == 0) {
                            out.print("<span style='text-decoration: underline;font-style: italic;'>No aplica acuerdo</span>");
                        } else {
                            out.print("<span style='text-decoration: underline;font-style: italic;'>Aplica Acuerdo de Seguridad</span>");
                        }
                    } else {
                        out.print("Sin datos");
                    }
                    out.print("</div>");
                    out.print("<div class='DivDetail b_color'><b>Aplica visita</b></div><div class='DivDetailDescription'>");
                    lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                    if (lst_Configuration != null) {
                        Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                        if (obj_Segmentation[23] != null && obj_Segmentation[24] != null && obj_Segmentation[25] != null) {
                            Qualification = Integer.parseInt(obj_Segmentation[23].toString());
                            Experience = Integer.parseInt(obj_Segmentation[24].toString());
                            Relationship = Integer.parseInt(obj_Segmentation[25].toString());
                            String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                            CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                            CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                            CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                            sumT = CalcQ + CalcEx + CalcRel;
                            if (sumT >= 6) {
                                out.print("<b style='color:red;'>VISITAR</b>");
                            } else {
                                out.print("<b style='color:gray;'>N/A</b>");
                            }
                        } else {
                            out.print("<b style='color:gray;'>SIN DATOS</b>");
                        }
                    } else {
                        out.print("Fallo en el calculo");
                    }
                    out.print("</div>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class=\"tab-pane fade\" id=\"sagrilaf\" role=\"tabpanel\" aria-labelledby=\"sagrilaf-tab2\">");
                    //<editor-fold defaultstate="collapsed" desc="CONTROL SAGRILAFT AND PTEE">
                    out.print("<div style='display:flex;justify-content:space-evenly'>");
                    out.print("<div class='DivDetail b_color'><b>Segmentación</b></div><div class='DivDetailDescription'>");
                    if (LevelCode1 >= 6 && LevelCode2 >= 6 && LevelRisk >= 6 && obj_Segmentation[21].equals("SI")) {
                        out.print("<b style='color:red'>Riesgo Alto</b>");
                    } else {
                        out.print("<b style='color:green;'>Riesgo Bajo</b>");
                    }
                    out.print("</div>");
                    out.print("<div class='DivDetail b_color'><b>Días</b></div><div class='DivDetailDescription'>" + ((obj_Segmentation[34] == null) ? "" : obj_Segmentation[34]) + "</div>");
                    out.print("</div>");

                    out.print("<div style='display:flex;justify-content:space-evenly'>");

                    out.print("<div class='DivDetail b_color'><b>Debida diligencia</b></div><div class='DivDetailDescription'>");
                    if (obj_Segmentation[21] != null || LevelRisk > 0) {
                        if (obj_Segmentation[21].equals("SI") || LevelRisk >= 6) {
                            out.print("<b style='color:#e15f00'>Intensificada</b>");
                        } else {
                            out.print("<b>Normal</b>");
                        }
                    }
                    out.print("</div>");

                    out.print("<div class='DivDetail b_color'><b>Estado vinculantes</b></div><div class='DivDetailDescription'>");
                    if (obj_Segmentation[34] != null) {
                        int days = Integer.parseInt(obj_Segmentation[34].toString());
                        if (days < 300) {
                            out.print("<b style='color:green;'>Vigente</b>");
                        } else if (days < 365) {
                            out.print("<b style='color:orange;'>Proximo a vencer</b>");
                        } else {
                            out.print("<b style='color:red;'>Vencido</b>");
                        }
                    } else {
                        out.print("<b style='color:gray;'>SIN DATOS</b>");
                    }
                    out.print("</div>");
                    out.print("</div>");

                    //</editor-fold>
                    out.print("</div>");

                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            } else if (Temp == 0 && IdSegmentation > 0) {
                //<editor-fold defaultstate="collapsed" desc="UPDATE SEGMENETATION">
                lst_SegmentationId = SegmentationJpa.ConsultSegmentationId(IdSegmentation);
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                out.print("<div class='cont_form_level' style='width: 65%; margin-left: 28%;'>");
                if (lst_SegmentationId != null) {
                    Object[] obj_SegId = (Object[]) lst_SegmentationId.get(0);
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h5>" + obj_SegId[7] + " - " + obj_SegId[6] + "</h5>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_temp'>");
                    out.print("<form action='Segmentation?opt=2' method='post' class='needs-validation' novalidate=''>");
                    out.print("<input type='hidden' name='IdSegmentation' value='" + IdSegmentation + "'>");
                    out.print("<input type='hidden' name='Format' value='" + obj_SegId[32] + "'>");
                    try {
                        out.print("<input type='hidden' name='idDoc' value='" + obj_SegId[1] + "'>");
                    } catch (Exception e) {
                        out.print("<input type='hidden' name='idDoc' value='0'>");
                    }

                    out.print("<div class='text-center'>");
                    out.print("<h4>Información General</h4>");
                    out.print("</div>");

                    out.print("<div class='d-flex justify-content-center mt-4'>");
                    out.print("<div class='col-lg-7'>");
                    out.print("<input type='text' class='form-control' name='txtName' id='txtName' placeholder='Nombre' value='" + ((obj_SegId[7] == null) ? "" : obj_SegId[7]) + "' data-toggle='tooltip' data-placement='top' title='Nombre cliente' required>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4 mb-2' data-toggle='tooltip' data-placement='top' title='Tipo de persona'>");
                    out.print("<select class='form-control' name='Txt_TypePerson' required>");
                    if (obj_SegId[8] == null) {
                        out.print("<option selected disabled value=''>Seleccione tipo de persona</option>");
                    } else {
                        out.print("<option value='" + obj_SegId[8] + "'>" + obj_SegId[8] + "</option>");
                    }
                    lst_config = ConfigurationJpa.ConsultSettingsByCategorieId(77);
                    if (lst_config != null) {
                        Object[] ObjType = (Object[]) lst_config.get(0);
                        String[] Opt = ObjType[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int i = 0; i < Opt.length; i++) {
                            if (obj_SegId[8] == null) {
                                out.print("<option value='" + Opt[i] + "'>" + Opt[i] + "</option>");
                            } else {
                                if (!obj_SegId[8].toString().equals(Opt[i])) {
                                    out.print("<option value='" + Opt[i] + "'>" + Opt[i] + "</option>");
                                }
                            }
                        }
                    } else {
                        out.print("<option selected disabled value=''>Se ha presentado error al consultar los tipos.</option>");
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='col-12 row' style='justify-content: center;'>");

                    out.print("<div class='col-lg-4' >");
                    out.print("<input type='text' class='form-control' name='Code' id='Code' placeholder='Code' value='" + ((obj_SegId[2] == null) ? "" : obj_SegId[2]) + "' data-toggle='tooltip' data-placement='top' title='Codigo Plastitec' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' data-toggle='tooltip'>");
                    out.print("<input type='text' class='form-control' name='Txt_Area' value='" + ((obj_SegId[4] == null) ? "" : obj_SegId[4]) + "' id='Area' placeholder='Area responsable' data-toggle='tooltip' data-placement='top' title='Área' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' data-toggle='tooltip'>");
                    out.print("<input type='date' class='form-control' name='Txt_Date' value='" + ((obj_SegId[19] == null) ? "" : obj_SegId[19]) + "' id='Date' placeholder='Fecha de último monitoreo' data-toggle='tooltip' data-placement='top' title='Fecha de último monitoreo' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un fecha!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4'>");
                    out.print("<input type='text' class='form-control' name='Txt_Pep' id='PEP' placeholder='PEP'  value='" + ((obj_SegId[21] == null) ? "" : obj_SegId[21]) + "' data-toggle='tooltip' data-placement='top' title='Se reporta persona expuesta políticamente (PEP)' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' data-toggle='tooltip'>");
                    out.print("<input type='text' class='form-control' name='Txt_ContacPerson' value='" + ((obj_SegId[14] == null) ? "" : obj_SegId[14]) + "' id='ContacPerson' placeholder='Nombre de la persona del contacto' data-toggle='tooltip' data-placement='top' title='Nombre de la persona del contacto' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4'>");
                    out.print("<input type='text' class='form-control' name='Txt_PerformsPost' id='PerformsPost' placeholder='Cargo que desempeña'  value='" + ((obj_SegId[15] == null) ? "" : obj_SegId[15]) + "' data-toggle='tooltip' data-placement='top' title='Cargo que desempeña' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' class='mr-2' data-toggle='tooltip'>");
                    out.print("<input type='number' class='form-control' name='AnnualFrequecy' value='" + ((obj_SegId[16] == null) ? "" : obj_SegId[16]) + "' id='AnnualFrequecy' placeholder='Frecuencia de operaciones anuales' data-toggle='tooltip' data-placement='top' title='Frecuencia de operaciones anuales' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' class='mr-2'>");
                    out.print("<input type='number' class='form-control' name='ValueSalesPurchases' id='ValueSalesPurchases' placeholder='Valor compras / Ventas anuales aprox (Millones)'  value='" + ((obj_SegId[17] == null) ? "" : obj_SegId[17]) + "' data-toggle='tooltip' data-placement='top' title='Valor compras / Ventas anuales aprox (Millones)' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' data-toggle='tooltip'>");
                    out.print("<input type='number' class='form-control' name='Antiquity' value='" + ((obj_SegId[18] == null) ? "" : obj_SegId[18]) + "' id='Antiquity' placeholder='Antiguedad (Años)' data-toggle='tooltip' data-placement='top' title='Antiguedad (Años)' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

//                    out.print("</div>");
//                    out.print("<div class='col-12' style='display: flex; justify-content:space-between;'>");
                    out.print("<div class='col-lg-4' class='mr-2'>");
                    out.print("<input type='text' class='form-control' name='Txt_BeneficiaryFinal' id='BeneficiaryFinal' placeholder='Beneficiario final'  value='" + ((obj_SegId[9] == null) ? "" : obj_SegId[9]) + "' data-toggle='tooltip' data-placement='top' title='Beneficiario final' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' class='mr-2'>");
                    out.print("<input type='text' class='form-control' name='Txt_TypeServiceOffered' id='TypeServiceOffered' placeholder='Tipo de servicio o producto comprado u ofrecido'  value='" + ((obj_SegId[20] == null) ? "" : obj_SegId[20]) + "' data-toggle='tooltip' data-placement='top' title='Tipo de servicio o producto comprado u ofrecido' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='col-lg-4' >");
                    out.print("<input type='text' class='form-control' name='Txt_SupplyChain' id='SupplyChain' placeholder='Asociado de negocio para cadena de suministro'  value='" + ((obj_SegId[22] == null) ? "" : obj_SegId[22]) + "' data-toggle='tooltip' data-placement='top' title='Asociado de negocio para cadena de suministro' required>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("</div>");

                    out.print("<div class='text-center'>");
                    out.print("<h4>Riesgos</h4>");
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="FUNCTION">
                    out.print("<div class='col-12 mb-2' style='display: flex; justify-content:space-between;'>");
                    out.print("<div style='width: 48%;display:flex;align-items:baseline;' >");
                    //<editor-fold defaultstate="collapsed" desc="QUALIFICATION">
                    out.print("<div style='width:82%;' data-toggle='tooltip' data-placement='top' title='Calificación como OEA,CT - PAT o Avalada por aduana del País.'>");
                    out.print("<select class='form-control' name='Qualification' id='Qualification' required>");
                    if (obj_SegId[23] == null) {
                        out.print("<option selected disabled value=''>Seleccione Calificación</option>");
                    } else {
                        out.print("<option selected  value='" + obj_SegId[23] + "'>" + obj_SegId[23] + "</option>");
                    }
                    lst_Conf1 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysisRiskQualification");
                    for (int i = 0; i < lst_Conf1.size(); i++) {
                        Object[] obj_Configution = (Object[]) lst_Conf1.get(i);
                        if (obj_SegId[23] != null) {
                            if (Integer.parseInt(obj_SegId[23].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        } else {
                            out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una calificación.</div>");
                    out.print("</div>");

                    out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                    out.print("<button type='button' onclick='ViewConvention(1)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div style='width: 48%;display:flex;align-items:baseline;' >");
                    //<editor-fold defaultstate="collapsed" desc="EXPERIENCE">
                    out.print("<div style='width:82%;' data-toggle='tooltip' data-placement='top' title='Experencia en el mercado'>");
                    out.print("<select class='form-control' name='Experience' id='Experience' required>");
                    if (obj_SegId[24] == null) {
                        out.print("<option selected disabled value=''>Seleccione Experiencia</option>");
                    } else {
                        out.print("<option selected  value='" + obj_SegId[24] + "'>" + obj_SegId[24] + "</option>");
                    }
                    lst_Conf2 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysisRiskExperience");
                    for (int o = 0; o < lst_Conf2.size(); o++) {
                        Object[] obj_Configution = (Object[]) lst_Conf2.get(o);
                        if (obj_SegId[24] != null) {
                            if (Integer.parseInt(obj_SegId[24].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        } else {
                            out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar una experencia del mercado.</div>");
                    out.print("</div>");
                    out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                    out.print("<button type='button' onclick='ViewConvention(2)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<div class='col-12 mb-2' style='display: flex; justify-content:space-between;'>");
                    out.print("<div style='width: 48%;display:flex;align-items:baseline;'>");
                    //<editor-fold defaultstate="collapsed" desc="RELATIONSHIP">
                    out.print("<div style='width:82%;'  data-toggle='tooltip' data-placement='top' title='Tiempo de relación comercial.'>");
                    out.print("<select class='form-control' name='Relationship' id='Relationship' required>");
                    if (obj_SegId[25] == null) {
                        out.print("<option selected disabled value=''>Seleccione tiempo de relación</option>");
                    } else {
                        out.print("<option selected value='" + obj_SegId[25] + "'>" + obj_SegId[25] + "</option>");
                    }
                    lst_Conf3 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysisRiskRelationship");
                    for (int p = 0; p < lst_Conf3.size(); p++) {
                        Object[] obj_Configution = (Object[]) lst_Conf3.get(p);
                        if (obj_SegId[25] != null) {
                            if (Integer.parseInt(obj_SegId[25].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        } else {
                            out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un tiempo de relación.</div>");
                    out.print("</div>");
                    out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                    out.print("<button type='button' onclick='ViewConvention(3)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("<div style='width: 48%;display:flex;align-items:baseline;' >");
                    //<editor-fold defaultstate="collapsed" desc="BURDEN">
                    out.print("<div style='width:82%;' data-toggle='tooltip' data-placement='top' title='Contacto con la carga o inofrmación de la carga'>");
                    out.print("<select class='form-control' name='Burden' id='Burden' required>");
                    if (obj_SegId[26] == null) {
                        out.print("<option selected disabled value=''>Seleccione Contacto de la carga</option>");
                    } else {
                        out.print("<option selected value='" + obj_SegId[26] + "'>" + obj_SegId[26] + "</option>");
                    }
                    lst_Conf4 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysisRiskBurden");
                    for (int a = 0; a < lst_Conf4.size(); a++) {
                        Object[] obj_Configution = (Object[]) lst_Conf4.get(a);
                        if (obj_SegId[26] != null) {
                            if (Integer.parseInt(obj_SegId[26].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        } else {
                            out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                        }
                    }
                    out.print("</select>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar un contacto de carga.</div>");
                    out.print("</div>");
                    out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                    out.print("<button type='button' onclick='ViewConvention(4)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    if (obj_SegId[32].equals("INTERNATIONAL")) {
                        out.print("<div class='col-12 mb-2' style='display: flex; justify-content:space-between;'>");
                        out.print("<div style='width: 48%;display:flex;align-items:baseline;' >");
                        //<editor-fold defaultstate="collapsed" desc="BASELINDEX">
                        out.print("<div style='width:82%;' data-toggle='tooltip' data-placement='top' title='Indice AML de basile (LAFT)'>");
                        out.print("<select class='form-control' name='BaselIndex' id='BaselIndex' required>");
                        if (obj_SegId[27] == null) {
                            out.print("<option selected disabled value=''>Seleccione Indice LAFT</option>");
                        } else {
                            out.print("<option selected value='" + obj_SegId[27] + "'>" + obj_SegId[27] + "</option>");
                        }
                        lst_Conf5 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysisBaselIndex");
                        for (int i = 0; i < lst_Conf5.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf5.get(i);
                            if (obj_SegId[27] != null) {
                                if (Integer.parseInt(obj_SegId[27].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                    out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                                }
                            } else {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar Indice LAFT.</div>");
                        out.print("</div>");
                        out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                        out.print("<button type='button' onclick='ViewConvention(5)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div style='width: 48%;display:flex;align-items:baseline;' >");
                        //<editor-fold defaultstate="collapsed" desc="CORRUPTION_INDEX">
                        out.print("<div style='width:82%;' data-toggle='tooltip' data-placement='top' title='Indice de percepcion de la corrupción'>");
                        out.print("<select class='form-control' name='CorruptionIndex' id='CorruptionIndex' required>");
                        if (obj_SegId[28] == null) {
                            out.print("<option selected disabled value=''>Seleccione Indice Corrupción</option>");
                        } else {
                            out.print("<option selected value='" + obj_SegId[28] + "'>" + obj_SegId[28] + "</option>");
                        }
                        lst_Conf6 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysisCorruptionIndex");
                        for (int i = 0; i < lst_Conf6.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf6.get(i);
                            if (obj_SegId[28] != null) {
                                if (Integer.parseInt(obj_SegId[28].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                    out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                                }
                            } else {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar Indice Corrupción.</div>");
                        out.print("</div>");
                        out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                        out.print("<button type='button' onclick='ViewConvention(6)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='col-12 mb-2'>");
                        out.print("<div style='display:flex;align-items:baseline;matgin:auto;justify-content:center;' >");
                        //<editor-fold defaultstate="collapsed" desc="BIBERY_INDEX">
                        out.print("<div style='width:41%;' data-toggle='tooltip' data-placement='top' title='Indice global de soborno'>");
                        out.print("<select class='form-control' name='BiberyIndex' id='BiberyIndex' required>");
                        if (obj_SegId[29] == null) {
                            out.print("<option selected disabled value=''>Seleccione Indice Soborno</option>");
                        } else {
                            out.print("<option selected value='" + obj_SegId[29] + "'>" + obj_SegId[29] + "</option>");
                        }
                        lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorie("AnalysiBiberyIndex");
                        for (int i = 0; i < lst_Conf7.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf7.get(i);
                            if (obj_SegId[29] != null) {
                                if (Integer.parseInt(obj_SegId[29].toString()) != Integer.parseInt(obj_Configution[2].toString())) {
                                    out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                                }
                            } else {
                                out.print("<option value='" + obj_Configution[2] + "'>" + obj_Configution[2] + "</option>");
                            }
                        }
                        out.print("</select>");
                        out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe seleccionar Indice Soborno.</div>");
                        out.print("</div>");
                        out.print("<div data-toggle='tooltip' data-placement='top' title='Convención'>");
                        out.print("<button type='button' onclick='ViewConvention(7)' class='btn btn-outline-info ml-2 btn-sm' ><i class='fas fa-question'></i></button>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CONVENTION">
                    out.print("<div class='sweet-local' tabindex='-1' id='View1' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="QUALIFICATION">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf1 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf1.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Controles de seguridad (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Calificación como OEA. CT - PAT. o avalada por aduana del país</p>");
                        for (int i = 0; i < lst_Conf1.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf1.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class='sweet-local' tabindex='-1' id='View2' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="EXPERIENCE">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf2 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf2.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Trayectoria del cliente / proveedor (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Experience en el mercado</p>");
                        for (int i = 0; i < lst_Conf2.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf2.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class='sweet-local' tabindex='-1' id='View3' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="RELATIONSHIP">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf3 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf3.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Trayectoria del cliente / proveedor (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Tiempo de relación comercial</p>");
                        for (int i = 0; i < lst_Conf3.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf3.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class='sweet-local' tabindex='-1' id='View4' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="BURDEN">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf4 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf4.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Contacto de carga (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Contacto con la carga o información de la carga</p>");
                        for (int i = 0; i < lst_Conf4.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf4.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class='sweet-local' tabindex='-1' id='View5' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="LAFT">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf5 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf5.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Indice AML de Basilea (LAFT) (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Contacto con la carga o información de la carga</p>");
                        for (int i = 0; i < lst_Conf5.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf5.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class='sweet-local' tabindex='-1' id='View6' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="CORRUPTION">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf6 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf6.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Indice de percepción de la corrupción (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Contacto con la carga o información de la carga</p>");
                        for (int i = 0; i < lst_Conf6.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf6.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    out.print("<div class='sweet-local' tabindex='-1' id='View7' style='opacity: 1.03; display:none;'>");
                    //<editor-fold defaultstate="collapsed" desc="BIBERY">
                    out.print("<div class='cont_viewConvention'>");
                    if (lst_Conf7 != null) {
                        Object[] obj_percentage = (Object[]) lst_Conf7.get(0);
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h5>Indice global de soborno (" + obj_percentage[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[1] + ")</h5>");
                        out.print("<button type='button' class='btn btn-outline-secondary' onclick='ViewConvention(7)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<p>Contacto con la carga o información de la carga</p>");
                        for (int i = 0; i < lst_Conf7.size(); i++) {
                            Object[] obj_Configution = (Object[]) lst_Conf7.get(i);
                            out.print("<div style='display:flex;align-items:baseline;text-align:center;'><div><i class='fas fa-star-of-life'></i></div>&nbsp;&nbsp;<div><p>" + obj_Configution[2] + " - " + obj_Configution[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</p></div></div>");
                        }
                    }
                    out.print("</div>");
                    //</editor-fold>
                    out.print("</div>");

                    //</editor-fold>
                    out.print("<div class='col-12 mb-2' >");
                    out.print("<textarea class='form-control' name='Txt_Observation' id='Txt_Observation' placeholder='Observación' required data-toggle='tooltip' data-placement='top' title='Observación'>" + ((obj_SegId[31] == null) ? "" : obj_SegId[31]) + "</textarea>");
                    out.print("<div class='invalid-feedback'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp;Debe ingresar un valor!</div>");
                    out.print("</div>");

                    out.print("<div class='' style='width: 100%; text-align:center; margin-top: 12px;'>");
                    out.print("<button class='btn btn-blue btn-lg'>Actualizar</button>");
                    out.print("</div>");
                    out.print("</form>");

                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
            }
            //<editor-fold defaultstate="collapsed" desc="SEARCH FILTER">
            out.print("<div class='sweet-local' tabindex='-1' id='Ventana8' style='opacity: 1.03; display:none;'>");
            out.print("<div class='contFilterSegmentation'>");
            out.print("<div style='display: flex; justify-content: space-between'>");
            out.print("<h4>Filtro de busqueda</h4>");
            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(8)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
            out.print("</div>");

            out.print("<form action='Segmentation?opt=5' method='post' class='needs-validation' novalidate=''>");
            out.print("<input type='hidden' name='Format' value='" + Format + "'>");
            out.print("<input type='hidden' name='Temp' value='" + 5 + "'>");
            out.print("<div style='display:flex; justify-content: space-between'>");

            out.print("<div class='DivFilter'>");
            out.print("<h5 class='mt-2'>Estado:</h5>");
            out.print("<div class='selectgroup m-2' >");
            out.print("<label class=\"selectgroup-item\" >");
            out.print("<input type=\"radio\" name=\"State\" value=\"2\" class=\"selectgroup-input\" checked>");
            out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Todas</span>");
            out.print("</label>");
            out.print("<label class=\"selectgroup-item\">");
            out.print("<input type=\"radio\" name=\"State\" value=\"1\" class=\"selectgroup-input\" >");
            out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Abiertas</span>");
            out.print("</label>");
            out.print("<label class=\"selectgroup-item\">");
            out.print("<input type=\"radio\" name=\"State\" value=\"0\" class=\"selectgroup-input\">");
            out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Cerradas</span>");
            out.print("</label>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='DivFilter'>");
            out.print("<h5 class='mt-2'>Vigencia:</h5>");
            out.print("<div class='selectgroup m-2' >");
            out.print("<label class=\"selectgroup-item\" >");
            out.print("<input type=\"radio\" name=\"Validity\" value=\"1\" class=\"selectgroup-input\" >");
            out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Vigente</span>");
            out.print("</label>");
            out.print("<label class=\"selectgroup-item\">");
            out.print("<input type=\"radio\" name=\"Validity\" value=\"2\" class=\"selectgroup-input\" >");
            out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Proxima a vencer</span>");
            out.print("</label>");
            out.print("<label class=\"selectgroup-item\">");
            out.print("<input type=\"radio\" name=\"Validity\" value=\"3\" class=\"selectgroup-input\">");
            out.print("<span class=\"selectgroup-button selectgroup-button-icon\">Vencido</span>");
            out.print("</label>");
            out.print("</div>");
            out.print("</div>");

            out.print("</div>");

            out.print("<div class='mt-2' style='display:flex; justify-content: space-between'>");

            out.print("<div class='DivFilter'>");
            out.print("<h5 class='mt-2 mb-2'>Texto:</h5>");
            out.print("<div style='width:93%;' class='ml-2'><input type='text' name='TextFilter' class='form-control' placeholder='Texto a filtrar'  data-toggle='tooltip' data-placement='top' title='Texto a filtrar'></div>");
            out.print("</div>");
            out.print("<div class='DivFilter'>");
            out.print("<h5 class='mt-2'>Fecha Monitoreo:</h5>");
            out.print("<div class='mb-2' style='display:flex;justify-content:space-around'>"
                    + "<div style='width:44%'>"
                    + "<input class='form-control' type=\"text\" name='InitialDate' placeholder='Fecha Inicio' id=\"InitialDate\" autocomplete='off' data-toggle='tooltip' data-placement='top' title='Fecha Inicio'></div>"
                    + "<div style='width:44%'> "
                    + "<input class='form-control' type=\"text\" name='EndDate' placeholder='Fecha Fin' id=\"EndDate\"  autocomplete='off' data-toggle='tooltip' data-placement='top' title='Fecha Fin'></div></div>");
            out.print("</div>");
            out.print("</div>");

            out.print("<div class='mt-2' style='width: 100%; text-align:center;'>");
            out.print("<button class='btn btn-blue btn-lg'>Consultar</button>");
            out.print("</div>");
            out.print("</form>");

            out.print("</div>");
            out.print("</div>");
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
            out.print("<section class='section'>");
            out.print("<div class='section-header'>");
            out.print("<h1>Módulo Segmentación</h1>");
            out.print("</div>");

            out.print("<div class='section-body'>");
            out.print("<div class='row'>");
            out.print("<div class='col-12'>");
            out.print("<div class='card'>");
            out.print("<div class='card-header' style='display:flex;justify-content:space-between'>");
            out.print("<div>");
            if (Format.equals("NATIONAL")) {
                out.print("<h4>Segmentación Nacional</h4>");
            } else {
                out.print("<h4>Segmentación Internacional</h4>");
            }
            out.print("</div>");
            if (Temp == 4 || Temp == 5) {
                out.print("<div style='display: flex;' >");
                out.print("<div class='mr-2'><button style='color:white;border-radius:5px;'  onclick=\"window.location.href='Segmentation?opt=1&IdSegmentation=0&Format=" + Format + "'\"  class=\"btn btn-danger btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Cancelar \"><i class=\"fas fa-times\"></i></button></div>");
                out.print("<div><button style='color:white;border-radius:5px;'  onclick='mostrarConvencion(8)'  class=\"btn btn-blue btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Filtrar \"><i class=\"fas fa-search\"></i></button></div>");
                out.print("</div>");
            } else {
                out.print("<div><button style='color:white;border-radius:5px;'  onclick='mostrarConvencion(8)'  class=\"btn btn-blue btn-sm\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Filtrar \"><i class=\"fas fa-search\"></i></button></div>");
            }
            out.print("</div>");
            out.print("<div class='card-body'>");
            out.print("<div class='table-responsive'>");
            out.print("<table class='table table-bordered table-hover' id='table-1'>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>DOC</th>");
            if (Format.equals("NATIONAL")) {
                out.print("<th>NIT</th>");
            } else {
                out.print("<th>TAX</th>");
            }
            out.print("<th>Cliente / Proveedor</th>");
            if (Format.equals("NATIONAL")) {
                out.print("<th>ACT. Primaria</th>");
                out.print("<th>ACT. Secundario</th>");
            }
            out.print("<th style='width:80px!important;' >Puntaje Total</th>");
            out.print("<th>Fecha Monitoreo</th>");
            out.print("<th>Dias</th>");
            out.print("<th>Vigencia</th>");
            out.print("<th>Opc</th>");
            out.print("</tr>");
            out.print("</thead>");
            out.print("<tbody>");
            switch (Temp) {
                case 4:
                    lst_Segmentation = SegmentationJpa.ConsultSegmentationId(IdSegmentation);
                    break;
                case 5:
                    try {
                        Query = pageContext.getRequest().getAttribute("Query").toString();
                    } catch (Exception e) {
                        Query = "";
                    }
                    lst_Segmentation = SegmentationJpa.ConsultQueryMysql(Query);
                    break;
                default:
                    lst_Segmentation = SegmentationJpa.ConsultSegmentation(Format);
                    break;
            }
            if (lst_Segmentation != null) {
                for (int i = 0; i < lst_Segmentation.size(); i++) {
                    Object[] obj_segmentation = (Object[]) lst_Segmentation.get(i);
                    out.print("<tr>");
                    //<editor-fold defaultstate="collapsed" desc="DOCUMENT">
                    out.print("<td style='text-align:center'>");
                    if (obj_segmentation[1] != null) {
                        out.print("<a style='color:white' onclick='window.location.href=\"Document?opt=1&IdDoc=" + obj_segmentation[1] + "&Event=Checking\"' class=\"btn btn-blue btn-sm mr-2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-original-title=\"Documento\"><i class=\"fas fa-file-alt\"></i></a>");
                    } else {
                        out.print("<b>S/N</b>");
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="NIT/ID">
                    out.print("<td>" + obj_segmentation[6] + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="CLIENT">
                    out.print("<td style='cursor:pointer;' data-toggle='tooltip' id='" + i + "' data-placement='top' title='" + ((obj_segmentation[5] == null) ? "" : obj_segmentation[5]) + "'>" + ((obj_segmentation[7] == null) ? "" : obj_segmentation[7]) + "</td>");
                    //</editor-fold>
                    if (Format.equals("NATIONAL")) {
                        //<editor-fold defaultstate="collapsed" desc="PRIMARY ECONOMIC ACTIVITY">
                        if (obj_segmentation[11] != null) {
                            lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_segmentation[10].toString()));
                            if (lst_CIIU != null) {
                                Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                Object[] obj_Setting = (Object[]) lst_Conf7.get(0);
                                out.print("<td style='text-align:center;' data-toggle='tooltip' data-placement='top' title=''>");
                                if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                    out.print(obj_CIIU[1].toString() + "<br/><b style='color:red;'>Riesgo Alto</b>");
                                } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                    out.print(obj_CIIU[1].toString() + "<br/><b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print(obj_CIIU[1].toString() + "<br/><b style='color:gray;'>Riesgo Medio</b>");
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td style='text-align:center;'>Sin codigo CIIU asociado</td>");
                            }
                        } else {
                            out.print("<td>Sin datos registrados</td>");
                        }
                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="SECONDARY ECONOMIC ACTIVITY">
                        if (obj_segmentation[12] != null) {
                            lst_CIIU = CIUUJpa.ConsultCIIU(Integer.parseInt(obj_segmentation[11].toString()));
                            if (lst_CIIU != null) {
                                Object[] obj_CIIU = (Object[]) lst_CIIU.get(0);
                                lst_Conf7 = ConfigurationJpa.ConsultSettingsByCategorieId(Integer.parseInt(obj_CIIU[3].toString()));
                                Object[] obj_Setting = (Object[]) lst_Conf7.get(0);
                                out.print("<td style='text-align:center;' data-toggle='tooltip' data-placement='top' title=''>");
                                if (Integer.parseInt(obj_Setting[2].toString()) >= 6) {
                                    out.print(obj_CIIU[1].toString() + "<br/><b style='color:red;'>Riesgo Alto</b>");
                                } else if (Integer.parseInt(obj_Setting[2].toString()) <= 4) {
                                    out.print(obj_CIIU[1].toString() + "<br/><b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print(obj_CIIU[1].toString() + "<br/><b style='color:gray;'>Riesgo Medio</b>");
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td style='text-align:center;'>Sin codigo CIIU asociado</td>");
                            }
                        } else {
                            out.print("<td style='text-align:center;'>Sin datos registrados</td>");
                        }
                        //</editor-fold>
                    }
                    //<editor-fold defaultstate="collapsed" desc="TOTAL SCRORE">
                    if (obj_segmentation[32].equals("NATIONAL")) {
                        //<editor-fold defaultstate="collapsed" desc="NATIONAL">
                        lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevel");
                        if (lst_Configuration != null) {
                            Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                            if (obj_segmentation[23] != null && obj_segmentation[24] != null && obj_segmentation[25] != null) {
                                Qualification = Integer.parseInt(obj_segmentation[23].toString());
                                Experience = Integer.parseInt(obj_segmentation[24].toString());
                                Relationship = Integer.parseInt(obj_segmentation[25].toString());
                                String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<td style='text-align:center;' >");
                                CalcQ = Qualification * Double.parseDouble((ArrConf[0]));
                                CalcEx = Experience * Double.parseDouble((ArrConf[1]));
                                CalcRel = Relationship * Double.parseDouble((ArrConf[2]));
                                sumT = CalcQ + CalcEx + CalcRel;
                                sumT = Math.round(sumT * 1000.0) / 1000.0;
                                if (sumT >= 6) {
                                    out.print(sumT + "<br/><b style='color:red;'>Riesgo Alto</b>");
                                } else if (sumT <= 4) {
                                    out.print(sumT + "<br/><b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print(sumT + "<br/><b style='color:gray;'>Riesgo Medio</b>");
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td style='text-align:center;'>Fallo en el calculo</td>");
                            }
                        } else {
                            out.print("<td style='text-align:center;'>Fallo en el calculo</td>");
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="INTERNATIONAL">
                        lst_Configuration = ConfigurationJpa.ConsultSettingsByCategorie("PercentageRiskLevelINT");
                        if (lst_Configuration != null) {
                            Object[] obj_Configuration = (Object[]) lst_Configuration.get(0);
                            if (obj_segmentation[27] != null && obj_segmentation[28] != null && obj_segmentation[29] != null) {
                                BaselIndex = Integer.parseInt(obj_segmentation[27].toString());
                                CorruptionIndex = Integer.parseInt(obj_segmentation[28].toString());
                                BirderyIndex = Integer.parseInt(obj_segmentation[29].toString());
                                String[] ArrConf = obj_Configuration[2].toString().replace("][", "//").replace("[", "").replace("]", "").split("//");
                                out.print("<td style='text-align:center;' >");
                                CalcBasil = BaselIndex * Double.parseDouble((ArrConf[0]));
                                CalcCorruption = CorruptionIndex * Double.parseDouble((ArrConf[1]));
                                CalcBidery = BirderyIndex * Double.parseDouble((ArrConf[2]));
                                sumT = CalcBasil + CalcCorruption + CalcBidery;
                                if (sumT >= 6) {
                                    out.print(sumT + "<br/><b style='color:red;'>Riesgo Alto</b>");
                                } else if (sumT <= 4) {
                                    out.print(sumT + "<br/><b style='color:green;'>Riesgo Bajo</b>");
                                } else {
                                    out.print(sumT + "<br/><b style='color:gray;'>Riesgo Medio</b>");
                                }
                                out.print("</td>");
                            } else {
                                out.print("<td style='text-align:center;'>Fallo en el calculo</td>");
                            }
                        } else {
                            out.print("<td style='text-align:center;'>Fallo en el calculo</td>");
                        }
                        //</editor-fold>
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DATE MONITORING">
                    out.print("<td>" + ((obj_segmentation[19] == null) ? "" : obj_segmentation[19]) + "</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="DAYS">
                    String[] validObs = {};
                    try {
                        validObs = obj_segmentation[31].toString().split("///");
                        if (validObs.length == 2) {
                            out.print("<td> - </td>");
                        } else {
                            out.print("<td>" + ((obj_segmentation[34] == null) ? "" : obj_segmentation[34]) + "</td>");
                        }
                    } catch (Exception e) {
                        out.print("<td> - </td>");
                    }
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="VALIDITY">
                    out.print("<td>");
                    if (validObs.length == 2) {
                        try {
                            String dateNewDoc = validObs[1].toString();
                            out.print("<div class='text-center'><b class='text-success'>Documento renovado</b> <br> <b class='text-dark'><i>" + dateNewDoc + "</i></b></div>");
                        } catch (Exception e) {
                            out.print("Error");
                        }
                    } else {
                        if (obj_segmentation[34] != null) {
                            int days = Integer.parseInt(obj_segmentation[34].toString());
                            if (days < 650) {
                                out.print("<b style='color:green;'>Vigente</b>");
                            } else if (days < 730) {
                                out.print("<b style='color:orange;'>Proximo a vencer</b>");
                            } else {
                                out.print("<b style='color:red;'>Vencido</b>");
                            }
                        } else {
                            out.print("<b>Sin datos registrados</b>");
                        }
                    }
                    out.print("</td>");
                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="OPC">
                    out.print("<td style='display:flex;justify-content: space-evenly;'>");
                    if (validObs.length == 2) {
                    } else {
                        if (obj_segmentation[34] != null) {
                            int days = Integer.parseInt(obj_segmentation[34].toString());
                            if (days > 365 || (days < 365 && days > 300)) {
                                String Info = "[" + obj_segmentation[0] + "][" + obj_segmentation[7] + "][" + Format + "][4]";
                                out.print("<div><button onclick=\"window.location.href='Document?opt=1&idSegx=" + obj_segmentation[0] + "&name=" + obj_segmentation[7] + "&format=" + Format + "&tempo=4&IdDocx=" + obj_segmentation[1] + "'\" class='btn btn-warning btn-sm mr-2' data-toggle='tooltip' data-placement='top' title='Generar documento'><i class=\"fas fa-file-medical\"></i></button></div>");
                            }
                        }
                    }
                    State = Integer.parseInt(obj_segmentation[33].toString());
                    out.print("<div><button onclick=\"window.location.href='Segmentation?opt=3&IdSegmentation=" + obj_segmentation[0] + "&State=" + ((State == 1) ? "0" : "1") + "&Format=" + Format + "'\" class='btn btn-outline-" + ((State == 1) ? "success" : "danger") + " btn-sm mr-2' data-toggle='tooltip' data-placement='top' title='" + ((State == 1) ? "Activo" : "Inactivo") + "'><i class='fas fa-" + ((State == 1) ? "check" : "times") + "'></i></button></div>");
                    out.print("<div class='dropdown d-inline'>\n");
                    out.print("<button class='btn btn-outline-primary btn-sm  dropdown-toggle' type='button' id='dropdownMenuButton2' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>\n");
                    out.print("<i class='fas fa-cog'></i>");
                    out.print("</a><div class='dropdown-menu menuDropdow'>");
                    out.print("<a class='dropdown-item has-icon' onclick=\"window.location.href='Segmentation?opt=1&IdSegmentation=" + obj_segmentation[0] + "&Format=" + Format + "'\" class='btn btn-outline-danger btn-sm mr-2' data-toggle='tooltip' data-placement='top' title='Actualizar'><i class='fas fa-user-edit'></i>Actualizar</a>");
                    out.print("<a class='dropdown-item has-icon' onclick=\"window.location.href='Segmentation?opt=1&IdSegmentation=" + obj_segmentation[0] + "&Temp=2&Format=" + Format + "'\" class='btn btn-outline-primary btn-sm mr-2' data-toggle='tooltip' data-placement='top' title='Ver detalle'><i class='fas fa-eye'></i>Detalle</a>");
                    //<editor-fold defaultstate="collapsed" desc="CONDITION RISK LEVE VISIT">
                    out.print("<a class='dropdown-item has-icon' onclick=\"window.location.href='Segmentation?opt=1&IdSegmentation=" + obj_segmentation[0] + "&Temp=1&Format=" + Format + "'\" class='btn btn-outline-info btn-sm mr-2' data-toggle='tooltip' data-placement='top' title='Aplica visita'><i class=\"fas fa-portrait\"></i>Visita</a>");
                    //</editor-fold>
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</td>");
                    //</editor-fold>
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
            out.print("</section>");
            //</editor-fold>
        } catch (Exception ex) {
            Logger.getLogger(Tag_Segmentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
