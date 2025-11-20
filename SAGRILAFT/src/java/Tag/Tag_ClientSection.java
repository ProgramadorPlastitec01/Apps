package Tag;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.DocumentControllerJpa;
import Controller.CIIUControllerJpa;
import java.util.List;
import Controller.ConfigurationControllerJpa;
import Controller.TemplateControllerJpa;

import java.util.Calendar;
import java.util.ResourceBundle;

public class Tag_ClientSection extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        DocumentControllerJpa DocumentJpa = new DocumentControllerJpa();
        ConfigurationControllerJpa ConfigJpa = new ConfigurationControllerJpa();
        CIIUControllerJpa CIUUJpa = new CIIUControllerJpa();
        TemplateControllerJpa TemplateJpa = new TemplateControllerJpa();

        List lst_document = null;
        List lst_ciiu = null;
        List lst_config = null;
        List lst_template = null;
        int IdDOc = 0, est = 0, estTl = 0;
        String Format = "", TypeDocument = "";

        //<editor-fold defaultstate="collapsed" desc="LANGUAGE VARIABLES">
        String ButtonSave = "", ButtonAd = "", ButtonSaveDisabled = "", ButtonSaveDisDoc = "";
//</editor-fold>

        String[] TemplForm = {};
        try {
            IdDOc = Integer.parseInt(pageContext.getRequest().getAttribute("IdDoc").toString());
        } catch (Exception e) {
            IdDOc = 0;
        }

        try {
            IdDOc = Integer.parseInt(pageContext.getRequest().getAttribute("IdDoc").toString());
        } catch (Exception e) {
            IdDOc = 0;
        }

        Calendar cal = Calendar.getInstance();
        String anio = cal.get(Calendar.YEAR) + "";
        String mes = "";
        if ((cal.get(Calendar.MONTH) + 1) < 10) {
            mes = "0" + (cal.get(Calendar.MONTH) + 1);
        } else {
            mes = (cal.get(Calendar.MONTH) + 1) + "";
        }
        String dia = "";
        if ((cal.get(Calendar.DAY_OF_MONTH)) < 10) {
            dia = "0" + cal.get(Calendar.DAY_OF_MONTH);
        } else {
            dia = cal.get(Calendar.DAY_OF_MONTH) + "";
        }

        try {
            if (IdDOc > 0) {
                lst_document = DocumentJpa.ConsultDocumentsDetail(IdDOc);
                if (lst_document != null) {
                    Object[] ObjDoc = (Object[]) lst_document.get(0);
                    est = Integer.parseInt(ObjDoc[1].toString());
                    estTl = Integer.parseInt(ObjDoc[3].toString());
                    Format = ObjDoc[2].toString();
                    TypeDocument = ObjDoc[5].toString();
                    int Avance = Integer.parseInt(ObjDoc[4].toString());
                    int IdAgree = Integer.parseInt(ObjDoc[6].toString());
                    int BasicForm = 0;
                    if (TypeDocument.contains("Basic")) {
                        BasicForm = 1;
                    }
                    boolean bntFinal = false;

                    //<editor-fold defaultstate="collapsed" desc="Language Document">
                    ResourceBundle bundle = ResourceBundle.getBundle("Language.formulario_" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "");
                    ButtonSave = bundle.getString("form.ButtonSave");
                    ButtonSaveDisabled = bundle.getString("form.ButtonSaveDisabled");
                    ButtonSaveDisDoc = bundle.getString("form.ButtonSaveDisDoc");
                    ButtonAd = bundle.getString("form.ButtonSaveAd");
                    //</editor-fold>

                    String lang = ((TypeDocument.contains("Ingles")) ? "en" : "es");

                    try {
                        TemplForm = ObjDoc[2].toString().replace("]/[", "///").replace("[[", "[").replace("]]", "]").split("///");
                    } catch (Exception e) {
                    }
                    String TitleGen = bundle.getString("form.titleGeneral");
                    out.print("<section class='section'>");

                    out.print("<div class='section-header' style='padding-left: 6%; align-items: end; box-shadow: 0px 2px 3px 1px #00000038;z-index: 1;background: #4dc5e9e6;color: white;margin-bottom: 15px;'>");
                    out.print("<button class='btn btn-white' style='position: absolute; left: 26px;' onclick='mostrarConvencion(55)' data-toggle='tooltip' data-placement='right' title='Devolver modulo'><i class=\"fas fa-chevron-circle-left\"></i></button>");
                    out.print("<h5 style='color: black;'>" + TitleGen + "</h5>");
                    out.print("</div>");

                    //<editor-fold defaultstate="collapsed" desc="RETURN STATE">
                    out.print("<div class='ContFloatBack' id='Ventana55' style='display: none;'>");
                    out.print("<div style='height: 40px;position: relative;padding: 10px;background: #5ecbeb;color: black;border-radius: 5px;margin-bottom: 11px;'>");
                    out.print("<h6>Seleccione modulo: </h6>");
                    out.print("</div>");

                    if (TypeDocument.contains("Ingles")) {
                        lst_config = ConfigJpa.ConsultSettingsByCategorie("ListModulesEN");
                    } else {
                        lst_config = ConfigJpa.ConsultSettingsByCategorie("ListModulesES");
                    }
                    String[] DataModules = {};
                    if (lst_config != null) {
                        Object[] ObjList = (Object[]) lst_config.get(0);
                        DataModules = ObjList[2].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    }

                    out.print("<ul class=\"list-group list-group-flush\">");
                    if (Avance == 16) {
                        Avance = 15;
                    }
                    for (int i = 0; i < Avance; i++) {
                        if (BasicForm == 1 && (i == 3 || i == 8 || i == 10)) {

                        } else {
                            if (i == estTl) {
                                out.print("<li class='list-group-item list-group-item-primary' style='padding: .25rem 1.25rem; cursor: pointer;'><i class=\"fas fa-caret-right\"></i>&nbsp;&nbsp;" + DataModules[i].split("/")[1] + "</li>");
                            } else {
                                out.print("<li class='list-group-item list-group-item-white' style='padding: .25rem 1.25rem; cursor: pointer;' onclick='window.location.href=\"ClientSection?opt=17&IdDoc=" + IdDOc + "&Sttate=" + i + "\"'>" + DataModules[i].split("/")[1] + "</li>");
                            }
                        }
                    }
                    out.print("</ul>");
                    out.print("</div>");
//</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="PROGRESS BAR">
                    double DataCalc = (estTl / 14.0) * 100;
                    int Progress = (int) Math.ceil(DataCalc);
                    if (Progress > 100) {
                        Progress = 100;
                    }
                    out.print("<div class='section-body' style='margin-bottom: 15px;'>");
                    out.print("<div class='progress'>");
                    out.print("<div class='progress-bar progress-bar-striped progress-bar-animated' role='progressbar' style='width: " + Progress + "%' aria-valuenow='10' aria-valuemin='0' aria-valuemax='100'>" + Progress + "%</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    out.print("<div class='section-body'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-12'>");

                    //<editor-fold defaultstate="collapsed" desc="COMMENT">
                    lst_document = DocumentJpa.ConsultDocumentObservations(IdDOc, estTl);
                    if (lst_document != null) {
                        Object[] ObjNote = (Object[]) lst_document.get(0);
                        out.print("<div><button class='btn btn-warning' onclick='mostrarConvencion(99)' style='position: absolute; z-index: 1; top: 8px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Notas'><i class=\"fas fa-comment-dots\"></i></button><i class=\"fas fa-circle\" style='position: absolute;z-index: 1;right: 3px;top: 2px;color: #e51717;'></i><div>");
                        out.print("<div><button class='btn btn-success' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='position: absolute; z-index: 1; top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button><div>");
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana99' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='cont_reg' style='width: 40%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Notas: </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(99)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");

                        out.print("<p>" + ObjNote[3].toString() + "</p>");

                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        bntFinal = true;
                    } else {

                    }

                    //</editor-fold>
                    
                    out.print("<div class='card'>");
                    out.print("<script> ");
                    out.print("function validarRadios(formId, radioName, mssg) { ");
                    out.print("    var form = document.getElementById(formId); ");
                    out.print("    form.addEventListener('submit', function(event) { ");
                    out.print("        var radios = document.getElementsByName(radioName); ");
                    out.print("        var seleccionado = false; ");
                    out.print("        for (var i = 0; i < radios.length; i++) { ");
                    out.print("            if (radios[i].checked) { ");
                    out.print("                seleccionado = true; ");
                    out.print("                break; ");
                    out.print("            } ");
                    out.print("        } ");
                    out.print("        if (!seleccionado) { ");
                    out.print("            iziToast.warning({ ");
                    out.print("                title: 'Atención!', ");
                    out.print("                message: 'Falta seleccionar ' + mssg, ");
                    out.print("                position: 'bottomRight' ");
                    out.print("            }); ");
                    out.print("            event.preventDefault(); ");
                    out.print("        } ");
                    out.print("    }); ");
                    out.print("} ");
                    out.print("</script> ");

                    if (est <= 3) {
                        //<editor-fold defaultstate="collapsed" desc="FORMS CLIENT">
                        if (estTl == 0) {
                            //<editor-fold defaultstate="collapsed" desc="START STATE">

                            String title = bundle.getString("formOne.title");
                            String infoOne = bundle.getString("formOne.infoOne");
                            String SectionOne = bundle.getString("formOne.SectionOne");
                            String OptOne = bundle.getString("formOne.OptOne");
                            String OptTwo = bundle.getString("formOne.OptTwo");
                            String SectionTwo = bundle.getString("formOne.SectionTwo");
                            String SectionThree = bundle.getString("formOne.SectionThree");
                            String OptThree = bundle.getString("formOne.OptThree");
                            String OptFourth = bundle.getString("formOne.OptFourth");
                            String Optfifth = bundle.getString("formOne.Optfifth");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp;" + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-6 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='420'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con astericos (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> " + infoOne);
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=2&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionOne + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='radio' class='' name='TxtTypeProc' id='' placeholder='' value='Vinculacion' " + ((form[1].toString().equals("Vinculacion")) ? "checked" : "") + " style='pointer-events: none;'> " + OptOne + " <br>");
                            out.print("<input type='radio' class='' name='TxtTypeProc' id='' placeholder='' value='Actualizacion' " + ((form[1].toString().equals("Actualizacion")) ? "checked" : "") + " style='pointer-events: none;'> " + OptTwo + "");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='date' class='form-control' name='DateInit' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "' >");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='radio' class='' name='TxtTypeThird' id='' onclick='ToActiveShield(\"TxtTypeThird\", \"TxtOther\")' value='Cliente' " + ((form[3].toString().equals("Cliente")) ? "checked" : "") + " style='pointer-events: none;'> " + OptThree + "<br>");
                            out.print("<input type='radio' class='' name='TxtTypeThird' id='' onclick='ToActiveShield(\"TxtTypeThird\", \"TxtOther\")' value='Proveedor' " + ((form[3].toString().equals("Proveedor")) ? "checked" : "") + " style='pointer-events: none;'> " + OptFourth + "<br>");
                            if (form[3].toString().contains("Otro")) {
                                out.print("<input type='radio' class='' name='TxtTypeThird' id='TxtTypeThird' onclick='ToActiveShield(\"TxtTypeThird\", \"TxtOther\")' value='Otro' checked style='pointer-events: none;'> " + Optfifth + "");
                                out.print("<input type='text' class='form-control' name='TxtOther' id='TxtOther' placeholder='¿Cual?' value='" + form[3].toString().toString().split("/")[1] + "' required>");
                            } else {
                                out.print("<input type='radio' class='' name='TxtTypeThird' id='TxtTypeThird' onclick='ToActiveShield(\"TxtTypeThird\", \"TxtOther\")' value='Otro' style='pointer-events: none;' > " + Optfifth + "");
                                out.print("<input type='hidden' class='form-control' name='TxtOther' id='TxtOther' placeholder='¿Cual?' required>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 1) {
                            //<editor-fold defaultstate="collapsed" desc="GENERAL INFORMATION">
                            String title = bundle.getString("formA.title");
                            String SectionOne = bundle.getString("formA.SectionOne");
                            String SectionTwo = bundle.getString("formA.SectionTwo");
                            String SectionThree = bundle.getString("formA.SectionThree");
                            String SectionFour = bundle.getString("formA.SectionFour");
                            String SectionFive = bundle.getString("formA.SectionFive");
                            String SectionSix = bundle.getString("formA.SectionSix");
                            String SectionSeven = bundle.getString("formA.SectionSeven");
                            String SectionEight = bundle.getString("formA.SectionEight");
                            String SectionNine = bundle.getString("formA.SectionNine");
                            String SectionTen = bundle.getString("formA.SectionTen");
                            String SectionEleven = bundle.getString("formA.SectionEleven");
                            String SectionTwelve = bundle.getString("formA.SectionTwelve");
                            String SectionThirteen = bundle.getString("formA.SectionThirteen");
                            String SectionFourteen = bundle.getString("formA.SectionFourteen");
                            String SectionFifteen = bundle.getString("formA.SectionFifteen");
                            String OptOne = bundle.getString("formA.OptOne");
                            String OptTwo = bundle.getString("formA.OptTwo");
                            String OptThree = bundle.getString("formA.OptThree");
                            String OptFour = bundle.getString("formA.OptFour");
                            String OptFive = bundle.getString("formA.OptFive");
                            String OptSix = bundle.getString("formA.OptSix");
                            String OptSeven = bundle.getString("formA.OptSeven");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='450'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=3&IdDoc=" + IdDOc + "' method='post' id='formGeneral' class='needs-validation' novalidate=''>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtNameBusi' id='TxtNameBusi' value='" + ((form[1].toString().equals("N/A")) ? "" : form[1].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='NmbIndeti' id='NmbIndeti' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + "</h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='TxtDv' id='TxtDv' value='" + ((form[3].toString().equals("N/A")) ? "" : form[3].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title=''>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFour + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtCountry' id='TxtCountry' value='" + ((form[4].toString().equals("N/A")) ? "" : form[4].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFive + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtCity' id='TxtCity' value='" + ((form[5].toString().equals("N/A")) ? "" : form[5].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionSix + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtAddress' id='TxtAddress' value='" + ((form[6].toString().equals("N/A")) ? "" : form[6].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionSeven + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtPhones' id='TxtPhones' value='" + ((form[7].toString().equals("N/A")) ? "" : form[7].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionEight + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='email' class='form-control' name='TxtMail' id='TxtMail' value='" + ((form[8].toString().equals("N/A")) ? "" : form[8].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionNine + "</h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtWebPage' id='TxtWebPage' value='" + ((form[9].toString().equals("N/A")) ? "" : form[9].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTen + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtPostalCode' id='TxtPostalCode' value='" + ((form[10].toString().equals("N/A")) ? "" : form[10].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionEleven + "</h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<div class='col-lg-12' data-toggle='tooltip' data-placemente='top' title=''>");
                            out.print("<select class='form-control select2' name='CbxCiiu1' style='' required>");
                            if (form[11].toString().equals("N/A")) {
                                out.print("<option selected disabled value=''>Seleccionar CIUU 1</option>");
//                                out.print("<option value='1'>0</option>");
                            } else {
                                try {

                                    out.print("<option value='" + form[11].toString() + "'>" + form[11].toString().split("/")[1] + "</option>");
                                } catch (Exception e) {
                                    out.print("<option value='" + form[11].toString() + "'>" + form[11].toString() + "</option>");
                                }
                            }
                            lst_ciiu = CIUUJpa.ConsultCIIU();
                            if (lst_ciiu != null) {
                                for (int i = 0; i < lst_ciiu.size(); i++) {
                                    Object[] objCiiu = (Object[]) lst_ciiu.get(i);
                                    out.print("<option value='" + objCiiu[0] + "/" + objCiiu[1] + "'>" + objCiiu[1] + "</option>");
                                }
                            } else {
                                out.print("<option value=''>Error en consulta de datos </option>");
                            }
                            out.print("</select>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTwelve + " </h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<select class='form-control select2' name='CbxCiiu2'>");
                            if (form[12].toString().equals("N/A")) {
                                out.print("<option value='0'>0</option>");
                            } else {
                                try {
                                    out.print("<option value='" + form[12].toString() + "'>" + form[12].toString().split("/")[1] + "</option>");
                                } catch (Exception e) {
                                    out.print("<option value='" + form[12].toString() + "'>" + form[12].toString() + "</option>");
                                }
                            }
                            lst_ciiu = CIUUJpa.ConsultCIIU();
                            if (lst_ciiu != null) {
                                for (int i = 0; i < lst_ciiu.size(); i++) {
                                    Object[] objCiiu = (Object[]) lst_ciiu.get(i);
                                    out.print("<option value='" + objCiiu[0] + "/" + objCiiu[1] + "'>" + objCiiu[1] + "</option>");
                                }
                            } else {
                                out.print("<option value=''>Error en consulta de datos </option>");
                            }

                            out.print("</select>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThirteen + "</h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtNroComercial' id='TxtNroComercial' value='" + ((form[13].toString().equals("N/A")) ? "" : form[13].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title=''>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFourteen + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='d-flex mt-2'>");
                            out.print("<input type='radio' name='TypeCompany' id='' value='Publica' " + ((form[14].toString().equals("Publica")) ? "checked" : "") + ">&nbsp; " + OptOne + " &nbsp;&nbsp;");
                            out.print("<input type='radio' name='TypeCompany' id='' value='Privada' " + ((form[14].toString().equals("Privada")) ? "checked" : "") + ">&nbsp; " + OptTwo + " &nbsp;&nbsp;");
                            out.print("<input type='radio' name='TypeCompany' id='' value='Mixta' " + ((form[14].toString().equals("Mixta")) ? "checked" : "") + ">&nbsp; " + OptThree + " &nbsp;&nbsp;");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<span class='d-flex'><h6>" + SectionFifteen + " </h6>&nbsp;<i class='fas fa-question-circle' data-toggle='tooltip' data-placement='top' title='(Ley 1590 de 2000)'></i>&nbsp;<span class='text-danger'>*</span></span>");
                            out.print("</div>");
                            out.print("<div class='d-flex mt-2'>");
                            out.print("<input type='radio' class='' name='TxtClasiCompany' id='' value='Micro' " + ((form[15].toString().equals("Micro")) ? "checked" : "") + "> &nbsp; " + OptFour + " &nbsp;&nbsp;<br>");
                            out.print("<input type='radio' class='' name='TxtClasiCompany' id='' value='Pequenia' " + ((form[15].toString().equals("Pequenia")) ? "checked" : "") + "> &nbsp; " + OptFive + " &nbsp;&nbsp;<br>");
                            out.print("<input type='radio' class='' name='TxtClasiCompany' id='' value='Mediana' " + ((form[15].toString().equals("Mediana")) ? "checked" : "") + "> &nbsp; " + OptSix + " &nbsp;&nbsp;");
                            out.print("<input type='radio' class='' name='TxtClasiCompany' id='' value='Grande' " + ((form[15].toString().equals("Grande")) ? "checked" : "") + "> &nbsp; " + OptSeven + " &nbsp;&nbsp;");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<script>");
                            out.print("validarRadios('formGeneral', 'TypeCompany', 'Tipo de empresa');");
                            out.print("validarRadios('formGeneral', 'TxtClasiCompany', 'Clasificacion de empresa');");
                            out.print("</script>");

                            //</editor-fold>
                        } else if (estTl == 2) {
                            //<editor-fold defaultstate="collapsed" desc="CERTIFICATION / CALIFICATION">
                            String title = bundle.getString("formB.title");
                            String SectionOne = bundle.getString("formB.SectionOne");
                            String OptOne = bundle.getString("formB.OptOne");
                            String OptTwo = bundle.getString("formB.OptTwo");
                            String OptThree = bundle.getString("formB.OptThree");
                            String OptFour = bundle.getString("formB.OptFour");
                            String OptFive = bundle.getString("formB.OptFive");
                            String OptSix = bundle.getString("formB.OptSix");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-6 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='320'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=4&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='checkbox' class='mt-2' name='TxtOEA' id='TxtOEA' value='OEA' onclick='MoveData(this.value)'" + ((form[1].toString().contains("OEA")) ? "checked" : "") + "> " + OptOne + " <br>");
                            out.print("<input type='checkbox' class='mt-2' name='TxtCTPAT' id='TxtCTPAT' value='CTPAT' onclick='MoveData(this.value)'" + ((form[1].toString().contains("CTPAT")) ? "checked" : "") + "> " + OptTwo + "<br>");
                            out.print("<input type='checkbox' class='mt-2' name='TxtBASC' id='TxtBASC' value='BASC' onclick='MoveData(this.value)'" + ((form[1].toString().contains("BASC")) ? "checked" : "") + "> " + OptThree + "<br>");
                            out.print("<input type='checkbox' class='mt-2' name='TxtIso28000' id='TxtIso28000' value='ISO 28000' onclick='MoveData(this.value)'" + ((form[1].toString().contains("ISO 28000")) ? "checked" : "") + "> " + OptFour + "<br>");
                            out.print("<input type='checkbox' class='mt-2' name='TxtIso9001' id='TxtIso9001' value='ISO 9001' onclick='MoveData(this.value)'" + ((form[1].toString().contains("ISO 9001")) ? "checked" : "") + "> " + OptFive + "<br>");
                            String others = "";
                            if (form[1].toString().contains("Otro")) {
                                String[] Data = form[1].toString().split("--");
                                int conter = Data.length - 1;
                                others = Data[conter].toString();
                                out.print("<input type='checkbox' class='mt-2' name='TxtIdOther' id='TxtIdOther' placeholder='' onclick='ToActiveShield(\"TxtIdOther\", \"TxtOther\")' checked> " + OptSix + "<br>");
                                String secD = others.replace("|||", "==").split("==")[1];
                                out.print("<input type='text' class='form-control col-lg-8 mt-2' name='TxtOther' id='TxtOther' placeholder='¿Cuales?' data-toggle='tooltip' data-placement='top' title='' value='" + secD + "'>");

                            } else {
                                out.print("<input type='checkbox' class='mt-2' name='TxtIdOther' id='TxtIdOther' placeholder='' onclick='ToActiveShield(\"TxtIdOther\", \"TxtOther\")'> " + OptSix + "<br>");
                                out.print("<input type='hidden' class='form-control col-lg-8 mt-2' name='TxtOther' id='TxtOther' placeholder='¿Cuales?' data-toggle='tooltip' data-placement='top' title='' >");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            String Certi = form[1].toString();
                            if (Certi.contains("/")) {
                                Certi = Certi.replace("/", "][");
                                Certi = "[" + Certi + "]";
                            } else {
                                Certi = "[" + Certi + "]";
                            }
                            Certi = Certi.replace(others, "");
                            Certi = Certi.replace("[]", "");
                            out.print("<input type='hidden' class='form-control' name='TxtCertifications' id='TxtCertifications' value='" + Certi + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            if (BasicForm == 1) {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",3)'><i class=\"fas fa-share-square\"></i></button>");
                            } else {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            }
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 3) {
                            //<editor-fold defaultstate="collapsed" desc="TRIBUTARY INFORMATION">
                            String title = bundle.getString("formC.title");
                            String SectionOne = bundle.getString("formC.SectionOne");
                            String SectionTwo = bundle.getString("formC.SectionTwo");
                            String SectionThree = bundle.getString("formC.SectionThree");
                            String SectionFour = bundle.getString("formC.SectionFour");
                            String SectionFive = bundle.getString("formC.SectionFive");
                            String SectionSix = bundle.getString("formC.SectionSix");
                            String SectionSeven = bundle.getString("formC.SectionSeven");
                            String SectionEight = bundle.getString("formC.SectionEight");
                            String OptOne = bundle.getString("formC.OptOne");
                            String OptTwo = bundle.getString("formC.OptTwo");
                            String OptThree = bundle.getString("formC.OptThree");
                            String OptFour = bundle.getString("formC.OptFour");
                            String OptFive = bundle.getString("formC.OptFive");
                            String OptSix = bundle.getString("formC.OptSix");
                            String OptSeven = bundle.getString("formC.OptSeven");
                            String OptEight = bundle.getString("formC.OptEight");
                            String OptNine = bundle.getString("formC.OptNine");
                            String OptTen = bundle.getString("formC.OptTen");
                            String OptEleven = bundle.getString("formC.OptEleven");
                            String OptTwelve = bundle.getString("formC.OptTwelve");
                            String OptThirteen = bundle.getString("formC.OptThirteen");
                            String OptFourtheen = bundle.getString("formC.OptFourtheen");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").replace("/]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='450'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=5&IdDoc=" + IdDOc + "' method='post' id='formTributary' class='needs-validation' novalidate=''>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='radio' name='TxtIva' id='' value='Comun' " + ((form[1].toString().equals("Comun")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")'> &nbsp; " + OptOne + " &nbsp;&nbsp; ");
                            out.print("<input type='radio' name='TxtIva' id='' value='Simple' " + ((form[1].toString().equals("Simple")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")'> &nbsp; " + OptFourtheen + " &nbsp;&nbsp; ");
                            out.print("<input type='radio' name='TxtIva' id='' value='Simplificado' " + ((form[1].toString().equals("Simplificado")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")'> &nbsp; " + OptTwo + " &nbsp;&nbsp; ");
                            out.print("<br><input type='radio' name='TxtIva' id='' value='Contribuyente' " + ((form[1].toString().equals("Contribuyente")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")'> &nbsp; " + OptThree + " &nbsp;&nbsp;");
                            out.print("<input type='radio' name='TxtIva' id='' value='Gran_Contribuyente' " + ((form[1].toString().equals("Gran_Contribuyente")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")'> &nbsp; " + OptFour + " &nbsp;&nbsp; <br>");
                            if (form[1].toString().contains("Otro")) {
                                out.print("<input type='radio' name='TxtIva' id='TxtIva' onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")' value='Otro' checked> &nbsp; " + OptFive + " &nbsp;&nbsp; ");
                                out.print("<input type='text' class='form-control' name='TxtOther' id='TxtOther' placeholder='¿Cual?' value='" + form[1].toString().split("/")[1] + "'>");
                            } else {
                                out.print("<input type='radio' name='TxtIva' id='TxtIva' onclick='ToActiveShield(\"TxtIva\", \"TxtOther\")' value='Otro'> &nbsp; " + OptFive + " &nbsp;&nbsp; ");
                                out.print("<input type='hidden' class='form-control' name='TxtOther' id='TxtOther' placeholder='¿Cual?'>");
                            }
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='col-lg-5 d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='d-flex mt-2'>");
                            out.print("<div class=''>");

                            String[] DataReten = form[3].toString().split("/");
                            out.print("<input type='radio' name='TxtRetaining' id='' value='Si' onclick='ToActiveShield(\"TxtRetaining\", \"TxtOther2\", \"Si\");ActiveCont(\"Si\", \"ContRete\")' " + ((DataReten[0].toString().contains("Si")) ? "checked" : "") + "> &nbsp; " + OptSix + " &nbsp;&nbsp; ");
                            out.print("<input type='radio' name='TxtRetaining' id='' value='No' onclick='ToActiveShield(\"TxtRetaining\", \"TxtOther2\", \"No\");ActiveCont(\"No\", \"ContRete\")' " + ((DataReten[0].toString().contains("No")) ? "checked" : "") + "> &nbsp; " + OptSeven + " &nbsp;&nbsp;");
                            if (DataReten[0].toString().contains("Otro")) {
                                out.print("<input type='radio' name='TxtRetaining' id='TxtRetaining' value='Otro' onclick='ToActiveShield(\"TxtRetaining\", \"TxtOther2\");ActiveCont(\"No\", \"ContRete\")' checked> &nbsp; " + OptFive + " &nbsp;&nbsp; ");
                                out.print("<input type='text' class='form-control' name='TxtOther2' id='TxtOther2' placeholder='¿Cual?' value='" + DataReten[1] + "' required>");
                            } else {
                                out.print("<input type='radio' name='TxtRetaining' id='TxtRetaining' value='Otro' onclick='ToActiveShield(\"TxtRetaining\", \"TxtOther2\");ActiveCont(\"No\", \"ContRete\")'> &nbsp; " + OptFive + " &nbsp;&nbsp; ");
                                out.print("<input type='hidden' class='form-control' name='TxtOther2' id='TxtOther2' placeholder='¿Cual?' data-toggle='tooltip' data-placement='top' title=''>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            if (DataReten[0].toString().equals("Si")) {
                                out.print("<div class='col-lg-6' id='ContRete' style='display: block;'>");
                            } else {
                                out.print("<div class='col-lg-6' id='ContRete' style='display: none;'>");
                            }
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFour + " </h6>");
                            out.print("</div>");
                            out.print("<div class='d-flex mt-2'>");
                            out.print("<div class=''>");
                            if (DataReten[0].toString().equals("Si")) {
                                out.print("<input type='radio' name='TxtReteSource' id='TxtReteSource' value='Si' onclick='ToActiveShield(\"TxtReteSource\", \"TxtOther3\")' " + ((DataReten[1].toString().contains("Si")) ? "checked" : "") + "> &nbsp; " + OptSix + " &nbsp;&nbsp; ");
                                out.print("<input type='radio' name='TxtReteSource' id='' value='No' onclick='ToActiveShield(\"TxtReteSource\", \"TxtOther3\")' " + ((DataReten[1].toString().contains("No")) ? "checked" : "") + "> &nbsp; " + OptSeven + " &nbsp;&nbsp; <br>");
                                if (DataReten[1].toString().equals("Si")) {
                                    out.print("<input type='number' class='form-control' name='TxtOther3' id='TxtOther3' placeholder='% Retencion' Value='" + DataReten[2].toString() + "'>");
                                } else {
                                    out.print("<input type='hidden' class='form-control' name='TxtOther3' id='TxtOther3' placeholder='% Retencion' data-toggle='tooltip' data-placement='top' title=''>");
                                }
                            } else {
                                out.print("<input type='radio' name='TxtReteSource' id='TxtReteSource' value='Si' onclick='ToActiveShield(\"TxtReteSource\", \"TxtOther3\")'> &nbsp; " + OptSix + " &nbsp;&nbsp; ");
                                out.print("<input type='radio' name='TxtReteSource' id='' value='No' onclick='ToActiveShield(\"TxtReteSource\", \"TxtOther3\")' " + ((DataReten[0].toString().contains("No")) ? "checked" : "") + "> &nbsp; " + OptSeven + " &nbsp;&nbsp; <br>");
                                out.print("<input type='hidden' class='form-control' name='TxtOther3' id='TxtOther3' placeholder='% Retencion' data-toggle='tooltip' data-placement='top' title=''>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3' id='ContResolution' style='display: " + ((form[2].toString().equals("N/A") || form[2].toString().equals("NA") || DataReten[0].toString().equals("No")) ? "none" : "block") + "'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='TxtResolution' id='TxtResolution' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "'>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFive + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='radio' name='TxtDataRetain' id='' value='CompraBienes' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' " + ((form[4].toString().contains("CompraBienes")) ? "checked" : "") + "> &nbsp; " + OptEight + " &nbsp;&nbsp; <br>");
                            out.print("<input type='radio' name='TxtDataRetain' id='' value='CompraServicios' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' " + ((form[4].toString().contains("CompraServicios")) ? "checked" : "") + "> &nbsp; " + OptNine + " &nbsp;&nbsp; <br>");
                            out.print("<input type='radio' name='TxtDataRetain' id='' value='Consultoria' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' " + ((form[4].toString().contains("Consultoria")) ? "checked" : "") + "> &nbsp; " + OptTen + " &nbsp;&nbsp; <br>");
                            out.print("<input type='radio' name='TxtDataRetain' id='' value='SuminServicios' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' " + ((form[4].toString().contains("SuminServicios")) ? "checked" : "") + "> &nbsp; " + OptEleven + " &nbsp;&nbsp; <br>");
                            out.print("<input type='radio' name='TxtDataRetain' id='' value='SuminBienes' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' " + ((form[4].toString().contains("SuminBienes")) ? "checked" : "") + "> &nbsp; " + OptTwelve + " &nbsp;&nbsp; <br>");
                            out.print("<input type='radio' name='TxtDataRetain' id='' value='Obras' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' " + ((form[4].toString().contains("Obras")) ? "checked" : "") + "> &nbsp; " + OptThirteen + " &nbsp;&nbsp; <br>");
                            if (form[4].toString().contains("Otro")) {
                                out.print("<input type='radio' name='TxtDataRetain' id='TxtDataRetain' value='Otro' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")' checked> &nbsp; " + OptFive + " &nbsp;&nbsp; <br>");
                                out.print("<input type='text' class='form-control' name='TxtOther4' id='TxtOther4' placeholder='¿Cual?' value='" + form[4].toString().split("/")[1] + "' required>");
                            } else {
                                out.print("<input type='radio' name='TxtDataRetain' id='TxtDataRetain' value='Otro' onclick='ToActiveShield(\"TxtDataRetain\", \"TxtOther4\")'> &nbsp; " + OptFive + " &nbsp;&nbsp; <br>");
                                out.print("<input type='hidden' class='form-control' name='TxtOther4' id='TxtOther4' placeholder='¿Cual?' data-toggle='tooltip' data-placement='top' title='' required>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionSix + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtIca' id='' placeholder='' value='" + ((form[5].toString().equals("N/A")) ? "" : form[5].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionSeven + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtCityTri' id='' value='" + ((form[6].toString().equals("N/A")) ? "" : form[6].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionEight + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            if (form[7].contains("N/A")) {
                                out.print("<input type='radio' name='TxtUserZone' value='Si'> Si &nbsp;");
                                out.print("<input type='radio' name='TxtUserZone' checked value='No'> No");
                            } else {
                                out.print("<input type='radio' name='TxtUserZone' value='Si' " + ((form[7].toString().equals("Si")) ? "checked" : "") + "> Si &nbsp;");
                                out.print("<input type='radio' name='TxtUserZone' value='No' " + ((form[7].toString().equals("No")) ? "checked" : "") + " > No");

                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<script>");
                            out.print("validarRadios('formTributary', 'TxtIva', 'iva regimen');");
                            out.print("validarRadios('formTributary', 'TxtRetaining', 'autoretenedor');");
                            out.print("validarRadios('formTributary', 'TxtDataRetain', 'datos para la retención');");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (estTl == 4) {
                            //<editor-fold defaultstate="collapsed" desc="PAYMENT CONDITIONS">
                            String title = bundle.getString("formD.title");
                            String SectionOne = bundle.getString("formD.SectionOne");
                            String SectionTwo = bundle.getString("formD.SectionTwo");
                            String SectionThree = bundle.getString("formD.SectionThree");
                            String SectionFour = bundle.getString("formD.SectionFour");
                            String SectionFive = bundle.getString("formD.SectionFive");
                            String SectionSix = bundle.getString("formD.SectionSix");
                            String OptOne = bundle.getString("formD.OptOne");
                            String OptTwo = bundle.getString("formD.OptTwo");
                            String OptThree = bundle.getString("formD.OptThree");
                            String OptFour = bundle.getString("formD.OptFour");
                            String OptFive = bundle.getString("formD.OptFive");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-6 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='400'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=6&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + "</h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtValueAprov' id='' placeholder='' value='" + ((form[1].toString().equals("N/A")) ? "" : form[1].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='radio' name='TxtDays' value='30' id='' " + ((form[2].toString().equals("30")) ? "checked" : ((form[2].toString().equals("N/A")) ? "checked" : "")) + " onclick='ToActiveShield(\"TxtDays\", \"TxtOther\")'> &nbsp;" + OptOne + " &nbsp;&nbsp;");
                            out.print("<input type='radio' name='TxtDays' value='60' id='' " + ((form[2].toString().equals("60")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtDays\", \"TxtOther\")'> &nbsp;" + OptTwo + " &nbsp;&nbsp;");
                            out.print("<input type='radio' name='TxtDays' value='90' id='' " + ((form[2].toString().equals("90")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtDays\", \"TxtOther\")'> &nbsp;" + OptThree + " &nbsp;&nbsp;");
                            out.print("<input type='radio' name='TxtDays' value='120' id='' " + ((form[2].toString().equals("120")) ? "checked" : "") + " onclick='ToActiveShield(\"TxtDays\", \"TxtOther\")'> &nbsp; " + OptFour + " &nbsp;&nbsp;");
                            if (form[2].toString().contains("Otro")) {
                                out.print("<input type='radio' name='TxtDays' value='Otro' id='TxtDays' onclick='ToActiveShield(\"TxtDays\", \"TxtOther\")' checked> &nbsp; " + OptFive + " &nbsp;&nbsp;<br>");
                                try {
                                    out.print("<input type='text' class='form-control' name='TxtOther' id='TxtOther' value='" + form[2].toString().split("/")[1] + "' >");
                                } catch (Exception e) {
                                    out.print("<input type='text' class='form-control' name='TxtOther' id='TxtOther' value='" + form[2].toString().replace("/", "") + "' >");
                                }
                            } else {
                                out.print("<input type='radio' name='TxtDays' value='Otro' id='TxtDays' onclick='ToActiveShield(\"TxtDays\", \"TxtOther\")' > &nbsp; " + OptFive + " &nbsp;&nbsp;<br>");
                                out.print("<input type='hidden' class='form-control' name='TxtOther' id='TxtOther' placeholder='' data-toggle='tooltip' data-placement='top' title='' >");

                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtNames' id='' placeholder='' value='" + ((form[3].toString().equals("N/A")) ? "" : form[3].toString()) + "' required >");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFour + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtRole' id='' placeholder='' value='" + ((form[4].toString().equals("N/A")) ? "" : form[4].toString()) + "' required >");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFive + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='NmbCel' id='' placeholder='' value='" + ((form[5].toString().equals("N/A")) ? "" : form[5].toString()) + "' required >");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionSix + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='email' class='form-control' name='TxtMailFact' id='' placeholder='' value='" + ((form[6].toString().equals("N/A")) ? "" : form[6].toString()) + "' required >");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 5) {
                            //<editor-fold defaultstate="collapsed" desc="LEGAL REPRESENTATIVE">
                            String title = bundle.getString("formE.title");
                            String SectionOne = bundle.getString("formE.SectionOne");
                            String SectionTwo = bundle.getString("formE.SectionTwo");
                            String SectionThree = bundle.getString("formE.SectionThree");
                            String SectionFour = bundle.getString("formE.SectionFour");
                            String SectionFive = bundle.getString("formE.SectionFive");
                            String SectionSix = bundle.getString("formE.SectionSix");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-7 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='360'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=7&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtNames' id='' value='" + ((form[1].toString().equals("N/A")) ? "" : form[1].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTwo + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtLastName' id='' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 d-flex mt-2'>");
                            out.print("<div class='col-lg-6' style='margin-left: -30px;' data-toggle='tooltip' data-placemente='top' title=''>");
                            out.print("<select class='form-control' name='CbxTypeDoc' required>");
                            String[] DataType = {};
                            if (!form[3].toString().equals("N/A")) {
                                DataType = form[3].toString().split("/");
                                out.print("<option value='" + DataType[0].toString() + "'>" + DataType[0].toString() + " </option>");
                            } else {
                                out.print("<option selected disabled value=''>Tipo </option>");
                            }
                            lst_config = ConfigJpa.ConsultSettingsByCategorie("TypeDocumentUser");
                            if (lst_config != null) {
                                Object[] objType = (Object[]) lst_config.get(0);
                                String[] DataTypeDoc = objType[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                for (int i = 0; i < DataTypeDoc.length; i++) {
                                    out.print("<option value='" + DataTypeDoc[i] + "'>" + DataTypeDoc[i] + " </option>");
                                }
                            }
                            out.print("</select>");

                            out.print("</div>");
                            out.print("<input type='text' class='form-control col-lg-8' name='NmbNroDoc' id='' value='" + ((!form[3].toString().equals("N/A")) ? DataType[1].toString() : "") + "' required>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFour + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='col-lg-12 d-flex mt-2'>");
                            out.print("<div class='col-lg-7' style='margin-left: -30px;' data-toggle='tooltip' data-placemente='top' title=''>");
                            String[] DataDate = {};
                            if (!form[4].toString().equals("N/A")) {
                                DataDate = form[4].toString().split("/");
                                out.print("<input type='date' class='form-control' name='TxtDate' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='" + DataDate[0].toString() + "' required>");
                                out.print("</div>");
                                out.print("<input type='text' class='form-control col-lg-7' name='TxtPlace' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='" + DataDate[1].toString() + "' required>");
                            } else {
                                out.print("<input type='date' class='form-control' name='TxtDate' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                                out.print("</div>");
                                out.print("<input type='text' class='form-control col-lg-7' name='TxtPlace' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");

                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionFive + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='TxtPhones' id='' value='" + ((form[5].toString().equals("N/A")) ? "" : form[5].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionSix + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='email' class='form-control' name='TxtMail' id='' value='" + ((form[6].toString().equals("N/A")) ? "" : form[6].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 6) {
                            //<editor-fold defaultstate="collapsed" desc="SHAREHOLDING STRUCTURE">
                            String title = bundle.getString("formF.title");
                            String infoOne = bundle.getString("formF.infoOne");
                            String infoTwo = bundle.getString("formF.infoTwo");
                            String SectionOne = bundle.getString("formF.SectionOne");
                            String SectionTwo = bundle.getString("formF.SectionTwo");
                            String SectionThree = bundle.getString("formF.SectionThree");
                            String SectionFour = bundle.getString("formF.SectionFour");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            String validate = "";
                            int counter = 0;
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='height:auto;'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> &nbsp; " + infoOne + "");
                            out.print("</div>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> &nbsp; " + infoTwo + "");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=8&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            if (form[1].toString().equals("N/A")) {
                                //<editor-fold defaultstate="collapsed" desc="EMPTY FORM">
                                out.print("<div id='formulario'>");
                                out.print("<div class='d-flex'>");
                                out.print("<div class='col-lg-4'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='mt-2'>");
                                out.print("<input type='text' class='form-control' name='TxtName' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='Denominacion Social o Nombre completo' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-lg-4'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6>" + SectionTwo + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='d-flex'>");
                                out.print("<div class='col-lg-5' style='margin-left: -15px;' data-toggle='tooltip' data-placemente='top' title=''>");
                                out.print("<select class='form-control' name='CbxTypeDoc' required>");
                                out.print("<option value='Tipo'>Tipo </option>");
                                lst_config = ConfigJpa.ConsultSettingsByCategorie("TypeDocumentUser");
                                if (lst_config != null) {
                                    Object[] objType = (Object[]) lst_config.get(0);
                                    String[] DataTypeDoc = objType[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                    for (int i = 0; i < DataTypeDoc.length; i++) {
                                        out.print("<option value='" + DataTypeDoc[i] + "'>" + DataTypeDoc[i] + " </option>");
                                    }
                                }
                                out.print("</select>");
                                out.print("</div>");
                                out.print("<input type='text' class='form-control col-lg-9' name='NmbNroDoc' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-lg-2'>");
                                out.print("<div class='text-center mt-4'>");
                                out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='d-flex mt-2 justify-content-center'>");
                                out.print("<input type='radio' value='Si' name='is_pep'> &nbsp; Si &nbsp;&nbsp;");
                                out.print("<input type='radio' value='No' name='is_pep' checked> &nbsp; No &nbsp;&nbsp;");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-lg-2'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6>% " + SectionFour + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='mt-2'>");
                                out.print("<input type='number' class='form-control' name='TxtPart' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="ONE OR MORE">
                                validate = "";
                                counter = 0;
                                out.print("<div id='formulario'>");
                                for (int i = 1; i < form.length; i++) {
                                    String[] DataForm = form[i].split("/");
                                    if (DataForm[5].equals("0")) {
                                        validate = "";
                                    } else {
                                        validate = DataForm[5];
                                        counter = Integer.parseInt(DataForm[5].toString());
                                    }
                                    out.print("<div id='DataForm" + i + "' class='d-flex person'>");
                                    out.print("<div class='col-lg-4'>");
                                    out.print("<div class='mt-4'>");
                                    out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='mt-2'>");
                                    out.print("<input type='text' class='form-control' name='TxtName" + validate + "' id='' placeholder='Nombre completo' value='" + DataForm[0] + "' data-toggle='tooltip' data-placement='top' title='Denominacion Social o Nombre completo' required>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-lg-4'>");
                                    out.print("<div class='mt-4'>");
                                    out.print("<h6>" + SectionTwo + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='d-flex'>");
                                    out.print("<div class='col-lg-5' style='margin-left: -15px;' data-toggle='tooltip' data-placemente='top' title='' >");
                                    out.print("<select class='form-control' name='CbxTypeDoc" + validate + "' required>");
                                    out.print("<option value='" + DataForm[1] + "'>" + DataForm[1] + " </option>");
                                    out.print("<option value='Tipo'>Tipo </option>");
                                    lst_config = ConfigJpa.ConsultSettingsByCategorie("TypeDocumentUser");
                                    if (lst_config != null) {
                                        Object[] objType = (Object[]) lst_config.get(0);
                                        String[] DataTypeDoc = objType[2].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                                        for (int e = 0; e < DataTypeDoc.length; e++) {
                                            out.print("<option value='" + DataTypeDoc[e] + "'>" + DataTypeDoc[e] + " </option>");
                                        }
                                    }
                                    out.print("</select>");
                                    out.print("</div>");
                                    out.print("<input type='number' class='form-control col-lg-9' name='NmbNroDoc" + validate + "' id='' placeholder='Numero de documento' value='" + DataForm[2] + "' data-toggle='tooltip' data-placement='top' title='' required>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-lg-2'>");
                                    out.print("<div class='text-center mt-4'>");
                                    out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='d-flex mt-2 justify-content-center'>");
                                    out.print("<input type='radio' value='Si' name='is_pep" + validate + "' " + ((DataForm[3].contains("Si")) ? "checked" : "") + "> &nbsp; Si &nbsp;&nbsp;");
                                    out.print("<input type='radio' value='No' name='is_pep" + validate + "' " + ((DataForm[3].contains("No")) ? "checked" : "") + "> &nbsp; No &nbsp;&nbsp;");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-lg-2'>");
                                    out.print("<div class='mt-4'>");
                                    out.print("<h6>% " + SectionFour + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='mt-2'>");
                                    out.print("<input type='number' class='form-control' name='TxtPart" + validate + "' id='' placeholder='' data-toggle='tooltip' data-placement='top' title='' value='" + DataForm[4] + "' required>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    if (i != 1) {
                                        out.print("<div class='boton-delete'>");
                                        out.print("<button class='btn btn-danger' onclick='DeleteItem(" + i + ")'><i class='fas fa-trash'></i></button>");
                                        out.print("</div>");
                                    }

                                    out.print("</div>");
                                }
                                out.print("</div>");
                                //</editor-fold>
                            }
                            out.print("<div class='text-center mt-4' style='margin-bottom: 50px;'>");
                            out.print("<button type='button' onclick='agregarPersona(" + counter + ")' class='btn btn-info'><i class='fas fa-plus'></i></button>");
                            out.print("<input type='hidden' class='form-control' name='TxtCounterPerson' id='CounterPerson' value='" + ((validate.toString().equals("")) ? "" : validate) + "' >");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 7) {
                            //<editor-fold defaultstate="collapsed" desc="FINAL BENEFICIARIES">
                            String title = bundle.getString("formG.title");
                            String infoOne = bundle.getString("formG.infoOne");
                            String infoTwo = bundle.getString("formG.infoTwo");
                            String SectionOne = bundle.getString("formG.SectionOne");
                            String SectionTwo = bundle.getString("formG.SectionTwo");
                            String SectionThree = bundle.getString("formG.SectionThree");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            String validate = "";
                            int counter = 0;
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='height: auto;'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> &nbsp; " + infoOne);
                            out.print("</div>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> &nbsp; " + infoTwo);
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=9&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            if (form[1].toString().equals("N/A")) {
                                //<editor-fold defaultstate="collapsed" desc="EMPTY FORM">
                                out.print("<div id='formulario'>");
                                out.print("<div class='d-flex person'>");
                                out.print("<div class='col-lg-4'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6> " + SectionOne + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='mt-2'>");
                                out.print("<input type='text' class='form-control' name='TxtName' id='TxtName' placeholder='Nombre completo' data-toggle='tooltip' data-placement='top' title='Nombre completo' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-lg-4'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='d-flex'>");
                                out.print("<div class='col-lg-4' style='margin-left: -15px;' data-toggle='tooltip' data-placemente='top' title=''>");
                                out.print("<select class='form-control' name='CbxTypeDoc' required>");
                                out.print("<option value=''>Tipo </option>");
                                out.print("<option value='PP'>PP </option>");
                                out.print("<option value='CC'>CC </option>");
                                out.print("<option value='CE'>CE </option>");
                                out.print("<option value='NIT'>NIT </option>");
                                out.print("<option value='Otro'>Otro </option>");
                                out.print("</select>");
                                out.print("</div>");
                                out.print("<input type='text' class='form-control col-lg-9' name='NmbNroDoc' id='' placeholder='Numero documento' data-toggle='tooltip' data-placement='top' title='' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='col-lg-2'>");
                                out.print("<div class='text-center mt-4'>");
                                out.print("<h6> " + SectionThree + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='d-flex mt-2 justify-content-center'>");
                                out.print("<input type='radio' name='is_pep' value='Si'> &nbsp; Si &nbsp;&nbsp;");
                                out.print("<input type='radio' name='is_pep' value='No' checked> &nbsp; No &nbsp;&nbsp;");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                //</editor-fold>
                            } else {
                                //<editor-fold defaultstate="collapsed" desc="ONE OR MORE">
                                validate = "";
                                counter = 0;
                                out.print("<div id='formulario'>");
                                for (int i = 1; i < form.length; i++) {
                                    String[] DataForm = form[i].split("/");
                                    if (DataForm[4].equals("0")) {
                                        validate = "";
                                    } else {
                                        validate = DataForm[4];
                                        counter = Integer.parseInt(DataForm[4].toString());
                                    }
                                    out.print("<div id='DataForm" + i + "' class='d-flex person'>");
                                    out.print("<div class='col-lg-4'>");
                                    out.print("<div class='mt-4'>");
                                    out.print("<h6> " + SectionOne + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='mt-2'>");
                                    out.print("<input type='text' class='form-control' name='TxtName" + validate + "' id='TxtName' value='" + DataForm[0] + "' placeholder='Nombre completo' data-toggle='tooltip' data-placement='top' title='Nombre completo' required>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-lg-4'>");
                                    out.print("<div class='mt-4'>");
                                    out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='d-flex'>");
                                    out.print("<div class='col-lg-4' style='margin-left: -15px;' data-toggle='tooltip' data-placemente='top' title=''>");
                                    out.print("<select class='form-control' name='CbxTypeDoc" + validate + "' required>");
                                    out.print("<option value='" + DataForm[1] + "'>" + DataForm[1] + " </option>");
                                    out.print("<option value='PP'>PP </option>");
                                    out.print("<option value='CC'>CC </option>");
                                    out.print("<option value='CE'>CE </option>");
                                    out.print("<option value='NIT'>NIT </option>");
                                    out.print("</select>");
                                    out.print("</div>");
                                    out.print("<input type='number' class='form-control col-lg-9' name='NmbNroDoc" + validate + "' id='NmbNroDoc' value='" + DataForm[2] + "' placeholder='Numero documento' data-toggle='tooltip' data-placement='top' title='' required>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<div class='col-lg-2'>");
                                    out.print("<div class='text-center mt-4'>");
                                    out.print("<h6> " + SectionThree + " <span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<div class='d-flex mt-2 justify-content-center'>");
                                    out.print("<input type='radio' value='Si' name='is_pep" + validate + "' " + ((DataForm[3].contains("Si")) ? "checked" : "") + "> &nbsp; Si &nbsp;&nbsp;");
                                    out.print("<input type='radio' value='No' name='is_pep" + validate + "' " + ((DataForm[3].contains("No")) ? "checked" : "") + "> &nbsp; No &nbsp;&nbsp;");
                                    out.print("</div>");
                                    out.print("</div>");
                                    if (i != 1) {
                                        out.print("<div class='boton-delete'>");
                                        out.print("<button class='btn btn-danger' onclick='DeleteItem(" + i + ")'><i class='fas fa-trash'></i></button>");
                                        out.print("</div>");
                                    }
                                    out.print("</div>");
                                }
                                out.print("</div>");
                                //</editor-fold>
                            }
                            out.print("<div class='text-center mt-4' style='margin-bottom: 50px;'>");
                            out.print("<button type='button' onclick='agregarPersonaPlus(" + counter + ")' class='btn btn-info'><i class='fas fa-plus'></i></button>");
                            out.print("<input type='hidden' class='form-control' name='TxtCounterPerson' id='CounterPerson' value='" + ((validate.toString().equals("")) ? "" : validate) + "' >");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            if (BasicForm == 1) {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",3)'><i class=\"fas fa-share-square\"></i></button>");
                            } else {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            }
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 8) {
                            //<editor-fold defaultstate="collapsed" desc="FINANCIAL INFORMATION">
                            String title = bundle.getString("formH.title");
                            String SectionOne = bundle.getString("formH.SectionOne");
                            String SectionTwo = bundle.getString("formH.SectionTwo");
                            String SectionThree = bundle.getString("formH.SectionThree");
                            String SectionFour = bundle.getString("formH.SectionFour");
                            String SectionFive = bundle.getString("formH.SectionFive");
                            String SectionSix = bundle.getString("formH.SectionSix");
                            String SectionSeven = bundle.getString("formH.SectionSeven");
                            String SectionEight = bundle.getString("formH.SectionEight");
                            String SectionNine = bundle.getString("formH.SectionNine");
                            String SectionTen = bundle.getString("formH.SectionTen");
                            String SectionEleven = bundle.getString("formH.SectionEleven");
                            String SectionTwelve = bundle.getString("formH.SectionTwelve");
                            String SectionThirteen = bundle.getString("formH.SectionThirteen");
                            String SectionFourteen = bundle.getString("formH.SectionFourteen");
                            String OptOne = bundle.getString("formH.OptOne");
                            String OptTwo = bundle.getString("formH.OptTwo");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='450'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=10&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtEntity' id='TxtEntity' value='" + ((form[1].equals("N/A")) ? "" : form[1].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTwo + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='radio' name='TxtAccountType' value='Ahorros' " + ((form[2].toString().equals("Ahorros")) ? "checked" : "") + "> &nbsp " + OptOne + "&nbsp;&nbsp;");
                            out.print("<input type='radio' name='TxtAccountType' value='Corriente' " + ((form[2].toString().equals("Corriente")) ? "checked" : (form[2].toString().equals("N/A")) ? "checked" : "") + "> &nbsp;  " + OptTwo + "&nbsp;&nbsp;");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='TxtAccountNumb' id='TxtAccountNumb' placeholder='' value='" + ((form[3].equals("N/A")) ? "" : form[3].toString()) + "' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionFour + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtResourceOrigin' id='TxtResourceOrigin' placeholder='' value='" + ((form[4].equals("N/A")) ? "" : form[4].toString()) + "' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionFive + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtCoinType' id='TxtCoinType' value='" + ((form[5].equals("N/A")) ? "" : form[5].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionSix + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtAssets' id='TxtAssets' value='" + ((form[6].equals("N/A")) ? "" : form[6].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionSeven + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtPassives' id='TxtPassives' value='" + ((form[7].equals("N/A")) ? "" : form[7].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionEight + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtHeritage' id='TxtHeritage' value='" + ((form[8].equals("N/A")) ? "" : form[8].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionNine + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtIncome' id='TxtIncome' value='" + ((form[9].equals("N/A")) ? "" : form[9].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTen + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtExpenses' id='TxtExpenses' value='" + ((form[10].equals("N/A")) ? "" : form[10].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionEleven + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control moneyVal' name='TxtOtherIncome' id='TxtOtherIncome' value='" + ((form[11].equals("N/A")) ? "" : form[11].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex justify-content-centers'>");
                            out.print("<div class='col-lg-5'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionTwelve + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtConceptIncome' id='TxtConceptIncome' value='" + ((form[12].equals("N/A")) ? "" : form[12].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-3'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionThirteen + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtAnioReport' id='TxtAnioReport' value='" + ((form[13].equals("N/A")) ? "" : form[13].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6> " + SectionFourteen + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtUndReport' id='TxtUndReport' value='" + ((form[14].equals("N/A")) ? "" : form[14].toString()) + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 9) {
                            //<editor-fold defaultstate="collapsed" desc="POLITICALLY EXPOSED PERSON">
                            String title = bundle.getString("formI.title");
                            String infoOne = bundle.getString("formI.infoOne");
                            String infoTwo = bundle.getString("formI.infoTwo");
                            String infoThree = bundle.getString("formI.infoThree");
                            String SectionOne = bundle.getString("formI.SectionOne");
                            String SectionTwo = bundle.getString("formI.SectionTwo");
                            String SectionThree = bundle.getString("formI.SectionThree");
                            String SectionFour = bundle.getString("formI.SectionFour");
                            String SectionFive = bundle.getString("formI.SectionFive");
                            String SectionSix = bundle.getString("formI.SectionSix");
                            String OptOne = bundle.getString("formI.OptOne");
                            String OptTwo = bundle.getString("formI.OptTwo");
                            String OptThree = bundle.getString("formI.OptThree");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-9 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='height: auto; margin-bottom: 45px;'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=11&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> &nbsp; " + infoOne + " &nbsp; <br><input type='radio' name='IsPep' value='Si' onclick='ActiveCont(\"Si\", \"PepCont\")' " + ((form[1].toString().equals("Si")) ? "checked" : "") + "> &nbsp;" + OptOne + " &nbsp;&nbsp; <input type='radio' name='IsPep' value='No' onclick='ActiveCont(\"No\", \"PepCont\")' " + ((form[1].toString().equals("No")) ? "checked" : (form[1].toString().equals("N/A")) ? "checked" : "") + " >&nbsp;" + OptTwo + " &nbsp;&nbsp;<br><br>");
                            out.print("</div>");
                            if ((form[1].toString().equals("Si"))) {
                                out.print("<div id='PepCont' style='display: block;'>");
                            } else {
                                out.print("<div id='PepCont' style='display: none;'>");
                            }
                            out.print("<p>" + infoTwo + "</p>");
                            out.print("<table class='table table-bordered'>");
                            out.print("<thead>");
                            out.print("<tr>");
                            out.print("<th>" + infoThree + "</th>");
                            out.print("<th>" + OptOne + "</th>");
                            out.print("<th>" + OptTwo + "</th>");
                            out.print("<th>" + OptThree + "</th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody>");
                            out.print("<tr>");
                            out.print("<td>" + SectionOne + "</td>");
                            String[] Quest = {};
                            String Obs = "";
                            if (form[2].toString().equals("1")) {
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest1' value='Si'></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest1' value='No'></td>");
                                Obs = "";
                            } else {
                                Quest = form[2].toString().split("/");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest1' value='Si' " + ((Quest[0].toString().equals("Si")) ? "checked" : "") + " required></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest1' value='No' " + ((Quest[0].toString().equals("No")) ? "checked" : "") + " required></td>");
                                Obs = Quest[1].toString();
                                if (Obs.equals("NA")) {
                                    Obs = "";
                                }
                            }
                            out.print("<td><input type='test' class='form-control' name='Txt_Obs1' id='' data-toggle='tooltip data-placement='top' title='' value='" + Obs + "'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td>" + SectionTwo + "</td>");

                            if (form[3].toString().equals("2")) {
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest2' value='Si'></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest2' value='No'></td>");
                                Obs = "";
                            } else {
                                Quest = form[3].toString().split("/");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest2' value='Si' " + ((Quest[0].toString().equals("Si")) ? "checked" : "") + " required></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest2' value='No' " + ((Quest[0].toString().equals("No")) ? "checked" : "") + " required></td>");
                                Obs = Quest[1].toString();
                                if (Obs.equals("NA")) {
                                    Obs = "";
                                }
                            }

                            out.print("<td><input type='test' class='form-control' name='Txt_Obs2' id='' data-toggle='tooltip data-placement='top' title='' value='" + Obs + "'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td>" + SectionThree + "</td>");
                            if (form[4].toString().equals("3")) {
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest3' value='Si'></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest3' value='No'></td>");
                                Obs = "";
                            } else {
                                Quest = form[4].toString().split("/");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest3' value='Si' " + ((Quest[0].toString().equals("Si")) ? "checked" : "") + " required></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest3' value='No' " + ((Quest[0].toString().equals("No")) ? "checked" : "") + " required></td>");
                                Obs = Quest[1].toString();
                                if (Obs.equals("NA")) {
                                    Obs = "";
                                }
                            }
                            out.print("<td><input type='test' class='form-control' name='Txt_Obs3' id='' data-toggle='tooltip data-placement='top' title='' value='" + Obs + "'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td>" + SectionFour + "</td>");
                            if (form[5].toString().equals("4")) {
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest4' value='Si'></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest4' value='No'></td>");
                                Obs = "";
                            } else {
                                Quest = form[5].toString().split("/");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest4' value='Si' " + ((Quest[0].toString().equals("Si")) ? "checked" : "") + " required></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest4' value='No' " + ((Quest[0].toString().equals("No")) ? "checked" : "") + " required></td>");
                                Obs = Quest[1].toString();
                                if (Obs.equals("NA")) {
                                    Obs = "";
                                }
                            }
                            out.print("<td><input type='test' class='form-control' name='Txt_Obs4' id='' data-toggle='tooltip data-placement='top' title='' value='" + Obs + "'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td>" + SectionFive + "</td>");
                            if (form[6].toString().equals("5")) {
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest5' value='Si'></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest5' value='No'></td>");
                                Obs = "";
                            } else {
                                Quest = form[6].toString().split("/");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest5' value='Si' " + ((Quest[0].toString().equals("Si")) ? "checked" : "") + " required></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest5' value='No' " + ((Quest[0].toString().equals("No")) ? "checked" : "") + " required></td>");
                                Obs = Quest[1].toString();
                                if (Obs.equals("NA")) {
                                    Obs = "";
                                }
                            }
                            out.print("<td><input type='test' class='form-control' name='Txt_Obs5' id='' data-toggle='tooltip data-placement='top' title='' value='" + Obs + "'></td>");
                            out.print("</tr>");
                            out.print("<tr>");
                            out.print("<td>" + SectionSix + "</td>");
                            if (form[7].toString().equals("6")) {
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest6' value='Si'></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest6' value='No'></td>");
                                Obs = "";
                            } else {
                                Quest = form[7].toString().split("/");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest6' value='Si' " + ((Quest[0].toString().equals("Si")) ? "checked" : "") + " required></td>");
                                out.print("<td style='text-align: center;'><input type='radio' name='Txt_Quest6' value='No' " + ((Quest[0].toString().equals("No")) ? "checked" : "") + " required></td>");
                                Obs = Quest[1].toString();
                                if (Obs.equals("NA")) {
                                    Obs = "";
                                }
                            }
                            out.print("<td><input type='test' class='form-control' name='Txt_Obs6' id='' data-toggle='tooltip data-placement='top' title='' value='" + Obs + "'></td>");
                            out.print("</tr>");
                            out.print("</tbody>");
                            out.print("</table>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            if (BasicForm == 1) {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",3)'><i class=\"fas fa-share-square\"></i></button>");
                            } else {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            }
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 10) {
                            //<editor-fold defaultstate="collapsed" desc="INTERNATIONAL OPERATIONS">
                            String title = bundle.getString("formJ.title");
                            String SectionOne = bundle.getString("formJ.SectionOne");
                            String SectionTwo = bundle.getString("formJ.SectionTwo");
                            String SectionThree = bundle.getString("formJ.SectionThree");
                            String OptOne = bundle.getString("formJ.OptOne");
                            String OptTwo = bundle.getString("formJ.OptTwo");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-6 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='270'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=12&IdDoc=" + IdDOc + "' method='post' id='formOperational' class='needs-validation' novalidate=''>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='d-flex mt-2'>");
                            out.print("<input type='radio' class='' name='Txt_money1' id='' value='Si' " + ((form[1].toString().split("/")[0].equals("Si")) ? "checked" : "") + "> &nbsp;" + OptOne + "&nbsp;&nbsp;");
                            out.print("<input type='radio' class='' name='Txt_money1' id='' value='No' " + ((form[1].toString().split("/")[0].equals("No")) ? "checked" : "") + "> &nbsp;" + OptTwo + "&nbsp;&nbsp;");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + " </h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='Txt_detail1' id='' value='" + ((form[1].toString().split("/")[1]).equals("NA") ? "" : form[1].toString().split("/")[1]) + "' >");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTwo + "  <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='d-flex mt-2'>");
                            out.print("<input type='radio' class='' name='Txt_money2' id='' value='Si' " + ((form[2].toString().split("/")[0].equals("Si")) ? "checked" : "") + "> &nbsp;" + OptOne + "&nbsp;&nbsp;");
                            out.print("<input type='radio' class='' name='Txt_money2' id='' value='No' " + ((form[2].toString().split("/")[0].equals("No")) ? "checked" : "") + "> &nbsp;" + OptTwo + "&nbsp;&nbsp;");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<div class='col-lg-6'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + "  </h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='Txt_detail2' id='' value='" + ((form[2].toString().split("/")[1]).equals("NA") ? "" : form[2].toString().split("/")[1]) + "'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            out.print("<script>");
                            out.print("validarRadios('formOperational', 'Txt_money1', 'transacciones en moneda extranjera');");
                            out.print("validarRadios('formOperational', 'Txt_money2', 'productos financieros');");
                            out.print("</script>");
                            //</editor-fold>
                        } else if (estTl == 11) {
                            //<editor-fold defaultstate="collapsed" desc="SUPPLY CHAIN ​​SECURITY AGREEMENT">
                            String title = bundle.getString("formK.title");
//                            String SectionOne = bundle.getString("formK.SectionOne");
                            String SectionTwo = bundle.getString("formK.SectionTwo");
                            String SectionThree = bundle.getString("formK.SectionThree");
                            String SectionFour = bundle.getString("formK.SectionFour");
                            String SectionFive = bundle.getString("formK.SectionFive");
                            String SectionSix = bundle.getString("formK.SectionSix");
                            String SectionSeven = bundle.getString("formK.SectionSeven");
                            String SectionEight = bundle.getString("formK.SectionEight");
                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");

                            String nameClient = "";
                            try {
                                String[] infoclien = TemplForm[1].replace("][", "///").replace("[", "").replace("]", "").split("///");
                                nameClient = infoclien[1].toString();
                            } catch (Exception e) {
                                nameClient = "Agente de carga";
                            }

                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='auto'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("" + SectionFour + " (<span class='text-danger'>*</span>)");
                            out.print("</div>");
                            if (IdAgree == 0) {
                                out.print("<form action='ClientSection?opt=13&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            }
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-12'>");
                            out.print("<div class='card-body'>");

                            out.print("<ul class='nav nav-tabs' id='myTab2' role='tablist'>");
                            out.print("<li class='nav-item'>");
                            out.print("<a class='nav-link active' id='home-tab2' data-toggle='tab' href='#home2' role='tab' aria-controls='home' aria-selected='true'>" + SectionFive + "</a>");
                            out.print("</li>");
                            if (IdAgree > 0) {
                                out.print("<li class='nav-item'>");
                                out.print("<a class='nav-link' id='profile-tab2' data-toggle='tab' href='#profile2' role='tab' aria-controls='profile' aria-selected='false'>" + SectionSix + "</a>");
                                out.print("</li>");
                            }
                            out.print("</ul>");

                            out.print("<div class='tab-content tab-bordered' id='myTab3Content'>");
                            //<editor-fold defaultstate="collapsed" desc="SECURITY AGREEMENT">
                            out.print("<div class='tab-pane fade show active' id='home2' role='tabpanel' aria-labelledby='home-tab2'>");
                            out.print("<h6 class='text-center mb-4'>" + SectionTwo + "</h6>");
                            if (TypeDocument.contains("Ingles")) {
                                out.print("The Business Associate must comply with the minimum security requirements outlined here:<br>");
                                out.print("1. Carry out adequate and thorough selection, hiring, and familiarization of its business associates (Customers and suppliers).<br>");
                                out.print("2. Have documented processes for the selection, hiring, and familiarization of employees.<br>");
                                out.print("3. Have policies and methodologies for the prevention, control, and identification of the following risks, among others: money laundering, smuggling, drug trafficking, trafficking of substances for narcotic processing, terrorism, financing of terrorism, and arms trafficking.<br>");
                                out.print("4. Have implemented security measures and access controls in the facilities to prevent unauthorized access by individuals.<br>");
                                out.print("5. Have measures, controls, systems, and documented processes to ensure the confidentiality and custody of physical or magnetic information, especially information handled or custodied by Plastitec.<br>");
                                out.print("6. Have implemented tools and procedures for reporting any illicit or suspicious acts in its operations and those related to Plastitec's international supply chain.<br>");
                                out.print("7. When applicable, inspect containers and other cargo units, have security measures in the loading or unloading process of containers or cargo units.<br>");
                                out.print("8. When applicable, install high-security seals complying with ISO 17712 current standard, on sealable containers and cargo units, and send evidence.<br>");
                            } else {
                                out.print("El Asociado de Negocio debe dar cumplimiento a los requisitos mínimos de seguridad que aquí se suscriben:<br>");
                                out.print("1. Realizar una adecuada y completa selección, contratación y conocimiento de sus asociados de negocio (Clientes y proveedores).<br>");
                                out.print("2. Tener procesos documentados para la selección, contratación y conocimiento de empleados.<br>");
                                out.print("3. Tener Políticas y metodologías de prevención, control e identificación de los siguientes riesgos, entre otros: lavado de activos, contrabando, tráfico de estupefacientes, tráfico de sustancias para el procesamiento de narcóticos, terrorismo, financiación del terrorismo y tráfico de armas.<br>");
                                out.print("4. Tener implementadas medidas de seguridad y controles de acceso en las instalaciones que eviten y prevengan el acceso no autorizado de personas.<br>");
                                out.print("5. Contar con medidas, controles, sistemas y procesos documentados que aseguren la confidencialidad y custodia de la información física o magnética, especialmente de la información que maneje o custodie de Plastitec.<br>");
                                out.print("6. Tener implementadas herramientas y procedimientos para el reporte de cualquier acto ilícito o sospechoso en sus operaciones y las que tengan que ver con la cadena de suministro internacional de Plastitec.<br>");
                                out.print("7. Cuando aplique, realizar inspección de los contenedores y demás unidades de carga, contar con medidas de seguridad en el proceso de llenado o descargue de los contenedores o unidades de carga.<br>");
                                out.print("8. Cuando aplique, instalarsellos de alta seguridad que cumplan con la norma vigente ISO 17712, en los contenedores y unidades de carga precintables y enviar las evidencias.<br>");
                            }
                            out.print("<div class='ml-4'>");
                            out.print("<input type='checkbox' name='Txt_ReadDoc' value='1' id='Doc_security' " + ((form[1].toString().equals("1")) ? "checked" : "") + " required> &nbsp; " + SectionThree + "");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            if (IdAgree > 0) {
                                out.print("<div class='tab-pane fade' id='profile2' role='tabpanel' aria-labelledby='profile-tab2'>");
                                //<editor-fold defaultstate="collapsed" desc="ATTACHED DOCUMENT">
                                String BuildDoc = "";
                                int TypeSig = 0;
                                int IdSigna = 0;
                                boolean validSign = false;
                                String PathImg = "";

                                lst_document = DocumentJpa.sp_c_ConsultDocumentSignatureIdAgree(IdDOc);
                                Object[] objSign = {};
                                if (lst_document != null) {
                                    objSign = (Object[]) lst_document.get(0);
                                    IdSigna = Integer.parseInt(objSign[0].toString());
                                    TypeSig = Integer.parseInt(objSign[3].toString());
                                    validSign = true;
                                    PathImg = objSign[4].toString();
                                }

                                try {
                                    if (TypeDocument.contains("Ingles")) {
                                        if (IdAgree == 4) {
                                            IdAgree = 8;
                                        } else if (IdAgree == 5) {
                                            IdAgree = 9;
                                        } else if (IdAgree == 6) {
                                            IdAgree = 10;
                                        } else if (IdAgree == 7) {
                                            IdAgree = 11;
                                        }
                                        lst_template = TemplateJpa.ConsultTemplateId(IdAgree);
                                    } else {
                                        lst_template = TemplateJpa.ConsultTemplateId(IdAgree);
                                    }
                                    if (lst_template != null) {
                                        Object[] ObjTempl = (Object[]) lst_template.get(0);
                                        BuildDoc = ObjTempl[1].toString();
                                    }
                                } catch (Exception e) {
                                }
                                BuildDoc = BuildDoc.replace("XXDIAXX", dia).replace("XXMESXX", mes).replace("XXANIOXX", anio);
                                BuildDoc = BuildDoc.replace("XXXRAZONSOCIALXXX", nameClient);

                                out.print(BuildDoc);

                                out.print("<table class='table-bordered' style='text-align: center;width: 88%;margin: auto;'>");
                                out.print("<thead>");
                                out.print("<tr>");
                                out.print("<th>" + SectionSeven + "</th>");
                                out.print("<th>PLASTITEC</th>");
                                out.print("</tr>");
                                out.print("</thead>");
                                out.print("<tbody>");
                                out.print("<tr>");
                                out.print("<td>");

                                //<editor-fold defaultstate="collapsed" desc="BUTTONS">
                                out.print("<div class=''>");
                                out.print("<div class='col-12 col-sm-12 col-md-2 mb-3' style='display: flex; margin-top: 43px;'>");
                                out.print("<ul class='nav nav-pills flex-column' id='myTab4' role='tablist' style='display: contents;'>");
//                                out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='Dibujar' onclick='DataReplaceV2(1)'>");
//                                out.print("<a class='nav-link " + ((TypeSig == 1) ? "active" : (TypeSig == 0) ? "active" : "") + "' id='Draw-tab4' data-toggle='tab' href='#Draw4' role='tab' aria-controls='Draw' aria-selected='true'><i class=\"fas fa-signature\" style='font-size: 18px;'></i></a>");
//                                out.print("</li>");
//                                out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='Texto' onclick='DataReplaceV2(2)'>");
//                                out.print("<a class='nav-link " + ((TypeSig == 2) ? "active" : "") + "' id='Write-tab4' data-toggle='tab' href='#Write4' role='tab' aria-controls='Write' aria-selected='false'><i class=\"fas fa-keyboard\" style='font-size: 18px;'></i></a>");
//                                out.print("</li>");
                                out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='Imagen' onclick='DataReplaceV2(3)'>");
                                out.print("<a class='nav-link active' id='Img-tab4' data-toggle='tab' href='#Img4' role='tab' aria-controls='Img' aria-selected='false'><i class=\"fas fa-image\" style='font-size: 18px;'></i></a>");
                                out.print("</li>");
                                out.print("</ul>");
                                out.print("</div>");
                                //</editor-fold>

                                out.print("<div class='tab-content no-padding' id='myTab2Content'>");
                                out.print("<div class='tab-pane fade " + ((TypeSig == 1) ? "show active" : (TypeSig == 10) ? "show active" : "") + "' id='Draw4' role='tabpanel' aria-labelledby='Draw-tab4' style='border: 1px solid transparent;'>");
                                //<editor-fold defaultstate="collapsed" desc="SIGANTURE DRAWING">
                                out.print("<form action='ClientSection?opt=13&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate='' id='SignForm1'>");
                                out.print("<div class='canvas-container'>");
                                out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                                out.print("<canvas id='signature-canvas' width='400' height='200'></canvas>");
                                out.print("<div class=''>");
                                out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('signature-canvas')\"><i class='fas fa-sync-alt'></i></button>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='hidden' class='form-control' name='TxtSignatureDraw' id='coordenadas-hidden' value='" + ((validSign && TypeSig == 1) ? objSign[4].toString() : "") + "'>");
                                out.print("</div>");

                                if (validSign && TypeSig == 1) {
                                    out.print("<script>");
                                    out.print("function dibujarFirma() { "
                                            + "        const firmaGuardadaCanvas = document.getElementById('signature-canvas'); "
                                            + "        const firmaGuardadaContext = firmaGuardadaCanvas.getContext('2d'); "
                                            + "        const hiddenInput = document.getElementById('coordenadas-hidden'); "
                                            + "        const coordinatesJSON = hiddenInput.value;"
                                            + "        const coordinates = JSON.parse(coordinatesJSON); "
                                            + "        firmaGuardadaContext.clearRect(0, 0, firmaGuardadaCanvas.width, firmaGuardadaCanvas.height); "
                                            + "        firmaGuardadaContext.lineWidth = 2; "
                                            + "        firmaGuardadaContext.lineCap = 'round'; "
                                            + "        firmaGuardadaContext.beginPath(); "
                                            + "        firmaGuardadaContext.moveTo(coordinates[0].x, coordinates[0].y); "
                                            + "        for (let i = 1; i < coordinates.length; i++) { "
                                            + "            firmaGuardadaContext.lineTo(coordinates[i].x, coordinates[i].y); "
                                            + "        } "
                                            + "        firmaGuardadaContext.stroke(); "
                                            + "    } "
                                            + "    document.addEventListener('DOMContentLoaded', function() { "
                                            + "        dibujarFirma(); "
                                            + "    });");
                                    out.print("</script>");
                                }
                                out.print("<div class='mb-3'>");
                                out.print("<div class='col-lg-12'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6> " + SectionEight + " <span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='mt-2'>");
                                try {
                                    out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='" + form[2].toString() + "' required>");
                                } catch (Exception e) {
                                    out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='' required>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='1'>");
                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction1'>");
                                out.print("<input type='hidden' class='form-control' name='Txt_ReadDoc' value='1'>");
                                out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                out.print("</form>");
                                //</editor-fold>
                                out.print("</div>");

                                out.print("<div class='tab-pane fade " + ((TypeSig == 2) ? "show active" : "") + "' id='Write4' role='tabpanel' aria-labelledby='Write-tab4' style='border: 1px solid transparent;'>");
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE WRITING">
                                out.print("<form action='ClientSection?opt=13&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate='' id='SignForm2'>");
                                if (validSign && TypeSig == 2) {
                                    String[] DataSig = objSign[4].toString().split("/");
                                    out.print("<div class='signature-input d-flex'>");
                                    out.print("<input type='text' class='form-control col-lg-7' name='TxtSignatureWrite' id='name-input' value='" + DataSig[0] + "' placeholder='Escribe tu nombre...'>");
                                    out.print("<select class='form-control col-lg-4 ml-2' id='font-style-select' name='TxtSigLetter'>");
                                    out.print("<option  value='" + DataSig[1] + "' class='" + DataSig[1] + "'>" + DataSig[1] + "</option>");
                                } else {
                                    out.print("<div class='signature-input d-flex'>");
                                    out.print("<input type='text' class='form-control col-lg-7' name='TxtSignatureWrite' id='name-input' placeholder='Escribe tu nombre...'>");
                                    out.print("<select class='form-control col-lg-5 ml-2' id='font-style-select' name='TxtSigLetter'>");
                                    out.print("<option selected disabled value=''>Tipo de letra</option>");
                                }
                                out.print("<option value='GreatVibes' class='GreatVibes'>GreatVibes</option>");
                                out.print("<option value='Allura' class='Allura'>Allura</option>");
                                out.print("<option value='Coockie' class='Coockie'>Coockie</option>");
                                out.print("<option value='Whisper' class='Whisper'>Whisper</option>");
                                out.print("<option value='Tangerine' class='Tangerine'>Tangerine</option>");
                                out.print("</select>");
                                out.print("</div>");
                                out.print("<div class='canvas-container'>");
                                out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                                out.print("<canvas id='text-canvas' width='400' height='80'></canvas>");
                                out.print("<div class=''>");
                                out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('text-canvas')\"><i class='fas fa-sync-alt'></i></button>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                if (validSign && TypeSig == 2) {
                                    out.print("<script>");
                                    out.print("document.addEventListener('DOMContentLoaded', function() { "
                                            + "    if (nameInput.value) { "
                                            + "        updateText(); "
                                            + "    } "
                                            + "    }); "
                                            + "    function updateText() { "
                                            + "        const name = nameInput.value; "
                                            + "        contextText.clearRect(0, 0, textCanvas.width, textCanvas.height); "
                                            + "        contextText.font = `bold 60px ${fontStyleSelect.options[fontStyleSelect.selectedIndex].text}`; "
                                            + "        contextText.fillText(name, 10, 50); "
                                            + "    } "
                                            + "   ");
                                    out.print("</script>");
                                }
                                out.print("<div class='d-flex mb-3'>");
                                out.print("<div class='col-lg-12'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6>" + SectionEight + "<span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='mt-2'>");
                                try {
                                    out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='" + form[2].toString() + "' required>");
                                } catch (Exception e) {
                                    out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='' required>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='2'>");
                                out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                out.print("<input type='hidden' class='form-control' name='Txt_ReadDoc' value='1'>");
                                out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction2'>");
                                out.print("</form>");

                                //</editor-fold>
                                out.print("</div>");

                                out.print("<div class='tab-pane fade show active' id='Img4' role='tabpanel' aria-labelledby='Img-tab4' style='border: 1px solid transparent;'>");
                                //<editor-fold defaultstate="collapsed" desc="SIGNATURE IMAGEN">
                                out.print("<form action='SignatureAgree.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate='' id='SignForm3'>");
                                out.print("<input type='hidden' class='form-control' name='IdDOc' id='' value='" + IdDOc + "' >");
                                out.print("<div class='canvas-container'>");
                                out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                                out.print("<canvas id='image-canvas' width='400' height='200'></canvas>");
                                out.print("<div class=''>");
                                out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('image-canvas');sigChange()\"><i class='fas fa-sync-alt'></i></button>");
                                out.print("</div>");
                                out.print("</div>");

                                if (validSign) {
                                    out.print("<input type='hidden' class='form-control' id='image-path-input' value='Interfaz/Contenido/SagrilaftDocs/Signature/" + PathImg + "' >");
                                }
                                out.print("<div class='signature-input' id='sigChange' style='display: " + ((validSign) ? "none" : "block") + "'>");
                                out.print("<label for='file-input'><b>Subir imagen de firma:</b></label><br>");
                                out.print("<input type='file' name='TxtImageSigna' id='file-input' accept='image/png, image/jpeg' onchange='cargarImagen(event)'>");
//                                out.print("<input type='text' name='' id='idSignUpload' data-toggle='tooltip' value='"+ PathImg +"'>");
                                out.print("</div>");

                                out.print("</div>");

                                out.print("<div class='d-flex mb-3'>");
                                out.print("<div class='col-lg-12'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6>" + SectionEight + "<span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                out.print("<div class='mt-2'>");
                                try {
                                    out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocumentx' placeholder='Numero de documento' value='" + form[2] + "' required>");
                                } catch (Exception e) {
                                    out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocumentx' placeholder='Numero de documento' value='' required>");
                                }
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='3'>");
                                out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
//                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                out.print("<input type='hidden' class='form-control' name='Txt_ReadDoc' value='1'>");
                                out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction3'>");
                                out.print("</form>");
                                if (validSign && TypeSig == 3) {
                                    out.print("<script>");
                                    out.print("document.addEventListener('DOMContentLoaded', function() { "
                                            + "        const imagePathInput = document.getElementById('image-path-input'); "
                                            + "        const imageCanvas = document.getElementById('image-canvas'); "
                                            + "        const contextImage = imageCanvas.getContext('2d'); "
                                            + "        const imagePath = imagePathInput.value; "
                                            + " "
                                            + "        const image = new Image(); "
                                            + "        image.onload = function() { "
                                            + "            contextImage.clearRect(0, 0, imageCanvas.width, imageCanvas.height); "
                                            + "            contextImage.drawImage(image, 0, 0, imageCanvas.width, imageCanvas.height); "
                                            + "        }; "
                                            + "        image.src = imagePath; "
                                            + "    });");
                                    out.print("</script>");
                                }

                                //</editor-fold>
                                out.print("</div>");

                                out.print("</div>");
                                out.print("<b>Firma representante legal: </b><br>");
                                out.print("<b>CC: </b>");
                                out.print("</td>");
                                out.print("<td>");
                                out.print("<input type='hidden' class='form-control' name='' id='image-pathLfo' value='Interfaz/Contenido/Imagen/FirmaLFO.png' >");
                                out.print("<canvas id='lfo-canvas' width='350' height='180' style='border: 1px solid transparent;'></canvas>");
                                out.print("</td>");
                                out.print("<script>");
                                out.print("document.addEventListener('DOMContentLoaded', function() { "
                                        + "        const imagePathInput = document.getElementById('image-pathLfo'); "
                                        + "        const imageCanvas = document.getElementById('lfo-canvas'); "
                                        + "        const contextImage = imageCanvas.getContext('2d'); "
                                        + "        const imagePath = imagePathInput.value; "
                                        + " "
                                        + "        const image = new Image(); "
                                        + "        image.onload = function() { "
                                        + "            contextImage.clearRect(0, 0, imageCanvas.width, imageCanvas.height); "
                                        + "            contextImage.drawImage(image, 0, 0, imageCanvas.width, imageCanvas.height); "
                                        + "        }; "
                                        + "        image.src = imagePath; "
                                        + "    });");
                                out.print("</script>");

                                out.print("</tr>");
                                out.print("</tbody>");
                                out.print("</table>");
                                //</editor-fold>
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("</div>");

                                out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                                out.print("<input type='hidden' class='form-control' name='' id='IdTypeSigna' value='3'>");
//                                out.print("<input type='hidden' class='form-control' name='' id='IdTypeSigna' value='" + ((TypeSig == 0) ? "1" : TypeSig) + "'>");
                                //<editor-fold defaultstate="collapsed" desc="VALIDAICON DE BOTONES POR ENVIO DE IMAGENES">
                                if (validSign) {
                                    out.print("<button class='btn btn-blue mr-2' id='buttonSvve' style='display: none;' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidActionNew(\"TxtValidAction\",1);ExcuteForm(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\");'><i class='fas fa-save'></i></button>");
                                    out.print("<button class='btn btn-blue mr-2 disabled' id='buttonNsvve' style='display: block;' data-toggle='tooltip' data-placement='top' title='" + ButtonSaveDisabled + "' ><i class='fas fa-save'></i></button>");
                                } else {
                                    out.print("<button class='btn btn-blue mr-2' id='buttonSvve' style='display: block;' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='validEmpyData(\"NmbDocumentx\", \"No se ha ingresado el numero de documento.\", \"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\");'><i class='fas fa-save'></i></button>");
                                }
                                //</editor-fold>
                                if (validSign) {
                                    out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='window.location.href=\"ClientSection?opt=17&IdDoc=" + IdDOc + "&Sttate=12\"'><i class=\"fas fa-share-square\"></i></button>");
                                }
                                if (bntFinal) {
//                                    out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                                    out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                                }
                                out.print("</div>");
                            } else {
                                out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' >");
                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                                out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1);ReadDoc(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\");'><i class='fas fa-save'></i></button>");
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2);ReadDoc(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\");'><i class=\"fas fa-share-square\"></i></button>");
                                if (bntFinal) {
                                    out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                                }
                                out.print("</div>");
                                out.print("</form>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 12) {
                            //<editor-fold defaultstate="collapsed" desc="STATEMENTS">
                            String title = bundle.getString("formL.title");
                            String SectionOne = bundle.getString("formL.SectionOne");
                            String SectionTwo = bundle.getString("formL.SectionTwo");
                            String SectionThree = bundle.getString("formL.SectionThree");
                            String SectionFour = bundle.getString("formL.SectionFour");
                            String SectionFive = bundle.getString("formL.SectionFive");
                            String SectionSix = bundle.getString("formL.SectionSix");
                            String SectionEight = bundle.getString("formL.SectionEight");
                            String SectionNine = bundle.getString("formL.SectionNine");
                            String SectionTen = bundle.getString("formL.SectionTen");
                            String SectionEleven = bundle.getString("formL.SectionEleven");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp; " + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='height: auto;'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print(SectionOne + " (<span class='text-danger'>*</span>)");
                            out.print("</div>");
                            out.print("<form action='ClientSection?opt=14&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='card-body'>");
                            out.print("<ul class='nav nav-tabs' id='myTab' role='tablist'>");
                            out.print("<li class='nav-item'>");
                            out.print("<a class='nav-link active' id='home-tab' style='padding: 0.2rem 0.5rem;' data-toggle='tab' href='#home' role='tab' aria-controls='home' aria-selected='true'>" + SectionTwo + " &nbsp; <i class=\"fas fa-file-alt\"></i></a>");
                            out.print("</li>");
                            out.print("<li class='nav-item'>");
                            out.print("<a class='nav-link' id='profile-tab' style='padding: 0.2rem 0.5rem;' data-toggle='tab' href='#profile' role='tab' aria-controls='profile' aria-selected='false'>" + SectionThree + " &nbsp; <i class=\"fas fa-file-invoice\"></i></a>");
                            out.print("</li>");
                            out.print("<li class='nav-item'>");
                            out.print("<a class='nav-link' id='contact-tab' style='padding: 0.2rem 0.5rem;' data-toggle='tab' href='#contact' role='tab' aria-controls='contact' aria-selected='false'>" + SectionFour + " &nbsp; <i class=\"fas fa-copy\"></i></a>");
                            out.print("</li>");
                            out.print("</ul>");
                            out.print("<div class='tab-content' id='myTabContent'>");
                            out.print("<div class='tab-pane fade show active' id='home' role='tabpanel' aria-labelledby='home-tab'>");
                            out.print("<h6 class='text-center mt-2'>" + SectionFive + "</h6>");
                            if (TypeDocument.contains("Ingles")) {
                                out.print("In accordance with Statutory Law 1581 of 2012 on Data Protection and related regulations, with my signature, as the Data Subject, I authorize the incorporation of my personal data into a database under the responsibility of <b>PLASTITEC S.A.S</b>. The processing of this data will include collection, storage, use, circulation, and allocation for the purpose of administrative management, counterpart management, internal statistical management, economic and accounting management, billing management, collection and/or payment management, contact, and sending communications through registered means, conducting security studies on national or international binding, restrictive, and informative lists, maintaining updated and sufficient information about individuals who hold the position of administrators, preventing and controlling money laundering, financing of terrorism, and proliferation of weapons of mass destruction, transmission and transfer of data to business partners.\n"
                                        + "I declare that I have the authorization of the shareholders to register their data in this format and that their personal data will be incorporated into a database under the responsibility of <b>PLASTITEC S.A.S</b>. The processing of this data will include collection, storage, use, circulation, and allocation for the purpose of administrative management, data verification, maintaining updated and sufficient information about individuals who hold the position of administrators, preventing and controlling money laundering, financing of terrorism, and proliferation of weapons of mass destruction.\n"
                                        + "It is optional to provide information regarding Sensitive Data, understood as those that affect privacy or generate any type of discrimination, or information about minors. The data subject may exercise the rights of access, correction, or deletion of data and/or revocation of authorization or complaint for infringement of their data, by sending a written request to <b>PLASTITEC S.A.S</b>. at the email address proteccion.datos@plastitec-sa.com, indicating in the subject line the right they wish to exercise, or by ordinary mail sent to Carrera 56 # 5c- 72, Bogotá D.C. The data processing policy to which personal data are subject is published on the website www.plastitec-sa.com.\n"
                                        + "");
                            } else {
                                out.print("De acuerdo con la Ley Estatutaria 1581 de 2012 de Protección de Datos y normas concordantes, con mi firma autorizo como Titular de los datos personales, para que éstos sean incorporados en una base de datos responsabilidad de <b>PLASTITEC S.A.S</b>. cuyo tratamiento incluirá la recolección, almacenamiento, uso, circulación y destinación con la finalidad de realizar gestión administrativa, gestión de contrapartes, gestión de estadísticas internas, gestión económica y contable, gestión de facturación, gestión de cobros y/o pagos, contacto y envío de comunicaciones a través de los medios registrados, realizar estudios de seguridad en listas vinculantes, restrictivas e informativas nacionales o internacionales, tener información actualizada y suficiente acerca de las personas que tienen la calidad de administradores, prevenir y controlar el lavado de activos, financiación al terrorismo y la proliferación de armas de destrucción masiva, transmisión y transferencia de datos con aliados comerciales. Declaro que cuenta con la autorización de los accionistas para registrar sus datos en el presente formato y que sus datos personales sean incorporados en una base de datos responsabilidad de <b>PLASTITEC S.A.S</b>. cuyo tratamiento incluirá la recolección, almacenamiento, uso, circulación y destinación con la finalidad de realizar gestión administrativa, verificación de datos, tener información actualizada y suficiente acerca de las personas que tienen la calidad de administradores, prevenir y controlar el lavado de activos, financiación al terrorismo y la proliferación de armas de destrucción masiva. Es de carácter facultativo suministrar información que verse sobre Datos Sensibles, entendidos como aquellos que afectan la intimidad o generen algún tipo de discriminación, o sobre menores de edad. El titular podrá ejercer los derechos de acceso, corrección o supresión de datos y/o revocación de la autorización o reclamo por infracción sobre sus datos, con un escrito dirigido a <b>PLASTITEC S.A.S</b>. a la dirección de correo electrónico proteccion.datos@plastitec-sa.com, indicando en el asunto el derecho que desea ejercer, o mediante correo ordinario remitido a Carrera 56 # 5c- 72, Bogotá D.C. La política de tratamiento a la que se encuentran sujetos los datos personales esta publicada en la página web www.plastitec-sa.com.");
                            }

                            out.print("<div class=''>");
                            out.print("<input type='checkbox' value='1' onclick='MoveDataSelected(1, \"idReadDoc\")' " + ((form[1].toString().equals("1")) ? "checked" : "") + " required> &nbsp; " + SectionNine + " <span class='text-danger'>*</span>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='tab-pane fade' id='profile' role='tabpanel' aria-labelledby='profile-tab'>");
                            out.print("<h6 class='text-center mt-2'> " + SectionSix + " </h6>");
                            if (TypeDocument.contains("Ingles")) {
                                out.print("I hereby declare that the resources used or to be used in any commercial and/or contractual relationship with <b>PLASTITEC S.A.S</b> come from lawful activities; therefore, I affirm that they are not the result of activities penalized by Colombian law, such as crimes against economic patrimony, illicit enrichment, or money laundering, misuse of funds collected from the public, activities related to drug trafficking, fronting, crimes against the constitutional order, or any other crime or activity contrary to public order. Therefore, I declare under penalty of perjury that neither I nor the company I represent, nor its other legal representatives nor its shareholders, are currently included in any restrictive lists from OFAC, UN, or the EUROPEAN UNION. We have not been subject to any investigation by any authority as a result of asset forfeiture processes, we have not been convicted, and no judgment or ruling has been issued against us regarding the behaviors mentioned in this paragraph.");
                            } else {
                                out.print("Declaro que los recursos utilizados o a utilizarse en cualquier relación comercial y/o contractual con <b>PLASTITEC S.A.S</b>, provienen de actividades lícitas; por tal razón, manifiesto que aquellos no son resultado de actividades penalizadas por el ordenamiento colombiano, tales como delitos contra el patrimonio económico, enriquecimiento ilícito o lavado de activos, utilización indebida de fondos captados del público, actividades relacionadas con el tráfico de estupefacientes, testaferrato, delitos contra el orden constitucional o cualquier otro delito o actividad contraria al orden público. Por ende, declaro bajo la gravedad de juramento que ni yo ni la sociedad que represento, los demás representantes legales de la misma ni sus accionistas, actualmente nos encontramos incluidos en ninguna lista restrictiva OFAC, ONU o UNION EUROPEA, no hemos sido vinculados a investigación alguna ante cualquier autoridad como resultado de procesos de extinción de dominio, no hemos sido condenados, y no se ha emitido en nuestra contra sentencia o fallo en relación con las conductas mencionadas en este párrafo.");
                            }

                            out.print("<div class=''>");
                            out.print("<input type='checkbox' value='2' onclick='MoveDataSelected(2, \"idReadDoc\" )' " + ((form[1].toString().equals("1")) ? "checked" : "") + " required> &nbsp; " + SectionTen + " <span class='text-danger'>*</span>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='tab-pane fade' id='contact' role='tabpanel' aria-labelledby='contact-tab'>");
                            out.print("<h6 class='text-center mt-2'>" + SectionEight + "</h6>");
                            if (TypeDocument.contains("Ingles")) {
                                out.print("I declare that by signing this document, I am committed to Plastitec's Transparency and Business Ethics Program regarding the prevention of acts related to bribery, corruption, and other corrupt practices as stipulated in the Anti-Bribery and Anti-Corruption Policy available on the website www.plastitec-sa.com. Furthermore, in the event of any situation arising that contravenes the guidelines of the Program, I will withdraw from the business, as well as terminate the commercial and/or contractual relationship.");
                            } else {
                                out.print("Declaro que con la suscripción de este documento estoy comprometido con el Programa de Transparencia y Ética Empresarial de Plastitec, en materia de prevención de actos relacionados con prácticas de soborno, corrupción y otras prácticas corruptas según lo dispuesto en la Política Antisoborno y anticorrupción disponible en la página web www.plastitec-sa.com. Así mismo en caso de presentarse cualquier tipo de situación que contravenga los lineamientos del Programa, se desistirá del negocio, así como la terminación del vínculo comercial y/o contractual.");
                            }

                            out.print("<div class=''>");
                            out.print("<input type='checkbox' value='3' onclick='MoveDataSelected(3, \"idReadDoc\" )' " + ((form[1].toString().equals("1")) ? "checked" : "") + " required> &nbsp; " + SectionEleven + " <span class='text-danger'>*</span>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='Txt_ReadDoc' id='idReadDoc' value='" + ((form[1].toString().equals("1")) ? "[1][2][3]" : "") + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ReadDocComplet(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\");ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ReadDocComplet(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\");ValidAction(\"TxtValidAction\",2)'><i class='fas fa-share-square'></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 13) {
                            //<editor-fold defaultstate="collapsed" desc="DOCUMENTS TO ATTACH">
                            String title = bundle.getString("formO.title");
                            String SectionOne = bundle.getString("formO.SectionOne");
                            String SectionTwo = bundle.getString("formO.SectionTwo");
                            String SectionThree = bundle.getString("formO.SectionThree");
                            String SectionFour = bundle.getString("formO.SectionFour");
                            String SectionTitleErr = bundle.getString("formO.SectionTitleErr");
                            String SectionErr = bundle.getString("formO.SectionError");

                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp;" + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-10 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='height: auto;'>");
                            out.print("<div class='d-flex empty-state-icon justify-content-between'>");
                            out.print("<div class=''>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(999)' style='height: 30px;padding: 3px;width: 30px;'><i class=\"fas fa-question\"></i></button>");
                            out.print("</div>");
                            out.print("</div>");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            lst_document = DocumentJpa.ConsultDocumentFiles(IdDOc);
                            Object[] Objdoc = (Object[]) lst_document.get(0);
                            lst_config = ConfigJpa.ConsultSettingsByCategorie("Attach");
                            boolean validDocs = false;
                            if (!form[1].toString().equals("N/A")) {
                                validDocs = true;
                            }
                            //<editor-fold defaultstate="collapsed" desc="SHOW HINTS">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana999' style='opacity: 1.03; display: " + ((validDocs) ? "none" : "block") + ";'>");

                            out.print("<div class='cont_reg' style='width: 37%;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h3>Recomendaciones y aclaraciones</h3>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(999)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            out.print("<ul class='list-group list-group-flush'> "
                                    + "<li class='list-group-item'><i class='fas fa-exclamation'></i> &nbsp; El nombre del documento no debe tener caracteres especiales.</li>\n"
                                    + "<li class='list-group-item'><i class='fas fa-exclamation'></i> &nbsp; El nombre del documento no debe tener espacios, de ser necesario separar palabras con guion bajo ( _ ).</li>\n"
                                    + "<li class='list-group-item'><i class='fas fa-exclamation'></i> &nbsp; De preferencia usar tipos de archivos conocidos (.pdf, .docx, .txt).</li>\n"
                                    + "<li class='list-group-item'><i class='fas fa-exclamation'></i> &nbsp; Al guardarse los archivos todos van a quedar con la fecha y hora del momento de registro.</li>\n"
                                    + "</ul>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            if (validDocs == false) {
                                out.print("<form action='Attach.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
                                out.print("<input type='hidden' class='form-control' name='IdDoc' id='' value='" + IdDOc + "'>");
//                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            }

                            String FilesNames = "";
                            String[] DataFiles = {};
                            String idFiles = "";
                            int validator = 1;
                            if (lst_config != null) {
                                for (int i = 0; i < lst_config.size(); i++) {
                                    Object[] obj_config = (Object[]) lst_config.get(i);
                                    if (Objdoc[2].toString().contains("[" + obj_config[0].toString() + "]")) {
                                        if (validDocs) {
                                            String[] NameFilex = form[validator].toString().split("/");
                                            if (TypeDocument.contains("Ingles")) {
                                                FilesNames += "[" + obj_config[0] + "/" + obj_config[2].toString().split("/")[1] + "/" + NameFilex[1] + "]";
                                            } else {
                                                FilesNames += "[" + obj_config[0] + "/" + obj_config[2].toString().split("/")[0] + "/" + NameFilex[1] + "]";
                                            }
                                            validator++;
                                        } else {
                                            if (TypeDocument.contains("Ingles")) {
                                                FilesNames += "[" + obj_config[0] + "/" + obj_config[2].toString().split("/")[1] + "]";
                                            } else {
                                                FilesNames += "[" + obj_config[0] + "/" + obj_config[2].toString().split("/")[0] + "]";
                                            }
                                        }
                                        idFiles += "[" + obj_config[0] + "]";
                                    }
                                }
                            }
                            DataFiles = FilesNames.replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<input type='hidden' class='form-control' name='TxtIdFiles' value='" + idFiles + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value=''>");
                            if (validDocs) {
//                                out.print("<input type='text' class='form-control' name='TxtNew' id='IdFilex' value=''>");
                            }

                            int CountErr = 0;

                            for (int i = 0; i < DataFiles.length; i++) {
                                if (i % 2 == 0) {
                                    if (i != 0) {
                                        out.print("</div>");
                                    }
                                    if (i == DataFiles.length - 2 || i == DataFiles.length - 1) {
                                        out.print("<div class='row' style='margin-bottom: 60px;'>");
                                    } else {
                                        out.print("<div class='row'>");
                                    }
                                }

                                out.print("<div class='col-lg-6'>");
                                out.print("<div class='mt-4'>");
                                out.print("<h6 class='text-center'>" + DataFiles[i].toString().split("/")[1] + "<span class='text-danger'>*</span></h6>");
                                out.print("</div>");
                                if (validDocs) {
                                    //<editor-fold defaultstate="collapsed" desc="FILES LOAD">
                                    out.print("<div class='text-center mt-2 mb-2'>");
                                    String name_file = DataFiles[i].toString().split("/")[2];
                                    if (name_file.equals("Error")) {
                                        out.print("<b>" + SectionOne + ": </b> <span class='text-danger'><b>" + SectionTitleErr + "</b>&nbsp;</span><i class='fas fa-question-circle' data-toggle='tooltip' data-placement='top' title='" + SectionErr + "'></i>");
                                        CountErr++;
                                    } else {
                                        out.print("<b>" + SectionOne + ": </b> <span>" + name_file + "</span>");
                                    }
                                    out.print("</div>");
                                    out.print("<div class='d-flex justify-content-center'>");
                                    out.print("<button type='button' id='EditFile" + i + "' onclick='mostrarConvencion(" + i + ")' class='btn btn-warning mr-2' data-toggle='tooltip' data-placement='top' title='" + SectionFour + "'><i class='fas fa-exchange-alt'></i></button>");
                                    if (name_file.equals("Error")) {
                                        out.print("<button type='button' class='btn btn-success' disabled>" + SectionTwo + " <i class=\"fas fa-download\"></i></button>");
                                    } else {
                                        out.print("<button type='button' onclick='window.location.href=\"Download?File_name=" + DataFiles[i].toString().split("/")[2] + "\"' class='btn btn-success'>" + SectionTwo + " <i class=\"fas fa-download\"></i></button>");
                                    }
                                    out.print("</div>");
                                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana" + i + "' style='opacity: 1.03; display:none;'>");
                                    out.print("<div class='cont_reg' style='width: 40%;'>");
                                    out.print("<div style='text-align: end;'>");
                                    out.print("<button type='button' class='btn btn-outline-secondary' onclick='mostrarConvencion(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                                    out.print("</div>");
                                    out.print("<div class='mt-4'>");
                                    out.print("<h6 class='text-center'>" + DataFiles[i].toString().split("/")[1] + "<span class='text-danger'>*</span></h6>");
                                    out.print("</div>");
                                    out.print("<form action='Attach.jsp' method='post' id='FormNewFile" + i + "' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
                                    out.print("<input type='hidden' class='form-control' name='IdDoc' id='' value='" + IdDOc + "'>");
//                                    out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                    out.print("<input type='hidden' class='form-control' name='TxtIdFiles' value='" + idFiles + "'>");
                                    out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidAction' value='1'>");
                                    out.print("<input type='hidden' class='form-control' name='TxtNew' id='IdFileNew" + i + "' >");
                                    out.print("<input type='hidden' class='form-control' name='TxtBeforeFile' id='IdBeforeFile' value='" + DataFiles[i].toString().split("/")[2] + "'>");
                                    String validFile = DataFiles[i].toString().split("/")[0];
                                    out.print("<div class='d-flex mt-3' style='align-items: center;'>");
                                    out.print("<input type='file' class='form-control col-lg-8 TypeFile' name='File" + i + "' id='IdFile" + i + "' value='" + DataFiles[i].toString().split("/")[2] + "' onchange='MoveDataSelected(\"" + validFile + "/\" + this.value, \"IdFileNew" + i + "\")'>");
                                    out.print("<div id='DownloadFile" + i + "'></div>");
                                    out.print("</div>");
                                    out.print("<div class='text-center mt-3'>");
                                    out.print("<button type='button' class='btn btn-warning' onclick='ExecuteForm(\"FormNewFile" + i + "\")'>" + SectionThree + " <i class='fas fa-exchange-alt'></i></button>");
                                    out.print("</div>");
                                    out.print("</form>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("document.getElementById('IdFile" + i + "').addEventListener('change', function(){ "
                                            + "var input = this; "
                                            + "var NameFile = input.files[0].name; "
                                            + "var DownloadFile = document.getElementById('DownloadFile" + i + "'); "
                                            + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\" target='_blank'>" + SectionTwo + " <i class=\"fas fa-download\"></i></a>'; "
                                            + "});");
                                    out.print("</script>");
                                    //</editor-fold>
                                } else {
                                    //<editor-fold defaultstate="collapsed" desc="NEW FILES">
                                    out.print("<div class='d-flex justify-content-center' style='align-items: center;'>");
                                    out.print("<input type='file' class='form-control col-lg-8 TypeFile' name='File" + i + "' id='IdFile" + i + "' placeholder='' data-toggle='tooltip' data-placement='top' title='' required onchange='validarNombreArchivo(this, \"" + lang + "\")'>");
                                    out.print("<div id='DownloadFile" + i + "'></div>");
                                    out.print("</div>");
                                    out.print("<script>");
                                    out.print("document.getElementById('IdFile" + i + "').addEventListener('change', function(){ "
                                            + "var input = this; "
                                            + "var NameFile = input.files[0].name; "
                                            + "var DownloadFile = document.getElementById('DownloadFile" + i + "'); "
                                            + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\">" + SectionTwo + " <i class=\"fas fa-download\"></i></a>'; "
                                            + "});");
                                    out.print("</script>");
                                    //</editor-fold>
                                }
                                out.print("</div>");
                            }

                            out.print("<div class='d-flex align-items-center' style='position: absolute;bottom: 18px;width: 94%;justify-content: center;'>");
                            if (validDocs) {
                                out.print("<form action='ClientSection?opt=15' id='FormGeneral' method='post'>");
                                out.print("<input type='hidden' class='form-control' name='IdDoc' id='' value='" + IdDOc + "'>");
//                                out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                                out.print("<input type='hidden' class='form-control' name='TxtIdFiles' value='" + idFiles + "'>");
                                out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidActionx' value=''>");
                                out.print("<input type='hidden' class='form-control' name='Txt_FilesDoc' id='Txt_FilesDoc' value='-ChangeState-'>");
                                out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='Guardar' disabled><i class='fas fa-save'></i></button>");
                                if (CountErr == 0) {
                                    out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidActionx\",2);document.getElementById(\"FormGeneral\").submit();'><i class=\"fas fa-share-square\"></i></button>");
                                } else {
                                    out.print("<button class='btn btn-blue mr-2 disabled' data-toggle='tooltip' data-placement='top' title='" + ButtonSaveDisDoc + "' type='button'><i class=\"fas fa-share-square\"></i></button>");
                                }
                                if (bntFinal) {
                                    if (CountErr == 0) {
                                        out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                                    } else {
                                        out.print("<button class='btn btn-success disabled' type='button' title='Documentos con errores'><i class=\"fas fa-check-circle\"></i></button>");
                                    }
                                }
                                out.print("</form>");
                            } else {
                                out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidAction\",1)'><i class='fas fa-save'></i></button>");
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidAction\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            }
                            out.print("</div>");
                            if (validDocs == false) {
                                out.print("</form>");
                            }

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 14) {
                            //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                            String title = bundle.getString("formP.title");
                            String infoOne = bundle.getString("formP.infoOne");
                            String SectionOne = bundle.getString("formP.SectionOne");
                            String SectionTwo = bundle.getString("formP.SectionTwo");
                            String SectionThree = bundle.getString("formP.SectionThree");
                            String OptOne = bundle.getString("formP.OptOne");
                            String OptTwo = bundle.getString("formP.OptTwo");
                            String OptThree = bundle.getString("formP.OptThree");

                            String[] form = TemplForm[estTl].replace("][", "///").replace("[", "").replace("]", "").split("///");
                            int IdSigna = 0;
                            int TypeSig = 0;
                            boolean validSign = false;
                            String PathImg = "";

                            lst_document = DocumentJpa.ConsultDocumentSignatureId(IdDOc);
                            Object[] objSign = {};
                            if (lst_document != null) {
                                objSign = (Object[]) lst_document.get(0);
                                IdSigna = Integer.parseInt(objSign[0].toString());
                                TypeSig = Integer.parseInt(objSign[2].toString());
                                validSign = true;

                                lst_config = ConfigJpa.ConsultSettingsByCategorie("GlobalRouteAttach");
                                if (lst_config != null) {
                                    Object[] ObjSetting = (Object[]) lst_config.get(0);
                                    PathImg = ObjSetting[2].toString().replace("\\\\", "\\");
                                    PathImg = objSign[3].toString();
                                }

                            }
                            out.print("<div class='section-body' style='color: black'>");
                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp;" + title + "</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-6 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' style='height: auto;'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("Todos los campos con asterisco (<span class='text-danger'>*</span>) son obligatorios");
                            out.print("</div>");
                            out.print("<div class='empty-state-icon mt-2'>");
                            out.print("<i class='fas fa-caret-right'></i> &nbsp; " + infoOne);
                            out.print("</div>");

                            //<editor-fold defaultstate="collapsed" desc="SIGNATURE USER">                            
                            out.print("<div class='col-lg-12'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionOne + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");

                            //<editor-fold defaultstate="collapsed" desc="BUTTONS">
                            out.print("<div class=''>");
                            out.print("<div class='col-12 col-sm-12 col-md-2 mb-3' style='display: flex;'>");
                            out.print("<ul class='nav nav-pills flex-column' id='myTab4' role='tablist' style='display: contents;'>");
//                            out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='" + OptOne + "' onclick='DataReplace(1)'>");
//                            out.print("<a class='nav-link " + ((TypeSig == 1) ? "active" : (TypeSig == 0) ? "active" : "") + "' id='Draw-tab4' data-toggle='tab' href='#Draw4' role='tab' aria-controls='Draw' aria-selected='true'><i class=\"fas fa-signature\" style='font-size: 18px;'></i></a>");
//                            out.print("</li>");
//                            out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='" + OptTwo + "' onclick='DataReplace(2)'>");
//                            out.print("<a class='nav-link " + ((TypeSig == 2) ? "active" : "") + "' id='Write-tab4' data-toggle='tab' href='#Write4' role='tab' aria-controls='Write' aria-selected='false'><i class=\"fas fa-keyboard\" style='font-size: 18px;'></i></a>");
//                            out.print("</li>");
                            out.print("<li class='nav-item btn btn-sm' data-toggle='tooltip' data-placement='top' title='" + OptThree + "' onclick='DataReplace(3)'>");
                            out.print("<a class='nav-link active' id='Img-tab4' data-toggle='tab' href='#Img4' role='tab' aria-controls='Img' aria-selected='false'><i class=\"fas fa-image\" style='font-size: 18px;'></i></a>");
                            out.print("</li>");
                            out.print("</ul>");
                            out.print("</div>");
                            //</editor-fold>

                            out.print("<div class='col-12 col-sm-12 col-md-12'>");
                            out.print("<div class='tab-content no-padding' id='myTab2Content'>");
                            out.print("<div class='tab-pane fade " + ((TypeSig == 1) ? "show active" : (TypeSig == 10) ? "show active" : "") + "' id='Draw4' role='tabpanel' aria-labelledby='Draw-tab4'>");
                            //<editor-fold defaultstate="collapsed" desc="SIGANTURE DRAWING">
                            out.print("<form action='ClientSection?opt=16&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            out.print("<div class='canvas-container'>");
                            out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                            out.print("<canvas id='signature-canvas' width='400' height='200'></canvas>");
                            out.print("<div class=''>");
                            out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('signature-canvas')\"><i class='fas fa-sync-alt'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TxtSignatureDraw' id='coordenadas-hidden' value='" + ((validSign && TypeSig == 1) ? objSign[3].toString() : "") + "'>");
                            out.print("</div>");

                            if (validSign && TypeSig == 1) {
                                out.print("<script>");
                                out.print("function dibujarFirma() { "
                                        + "        const firmaGuardadaCanvas = document.getElementById('signature-canvas'); "
                                        + "        const firmaGuardadaContext = firmaGuardadaCanvas.getContext('2d'); "
                                        + "        const hiddenInput = document.getElementById('coordenadas-hidden'); "
                                        + "        const coordinatesJSON = hiddenInput.value;"
                                        + "        const coordinates = JSON.parse(coordinatesJSON); "
                                        + "        firmaGuardadaContext.clearRect(0, 0, firmaGuardadaCanvas.width, firmaGuardadaCanvas.height); "
                                        + "        firmaGuardadaContext.lineWidth = 2; "
                                        + "        firmaGuardadaContext.lineCap = 'round'; "
                                        + "        firmaGuardadaContext.beginPath(); "
                                        + "        firmaGuardadaContext.moveTo(coordinates[0].x, coordinates[0].y); "
                                        + "        for (let i = 1; i < coordinates.length; i++) { "
                                        + "            firmaGuardadaContext.lineTo(coordinates[i].x, coordinates[i].y); "
                                        + "        } "
                                        + "        firmaGuardadaContext.stroke(); "
                                        + "    } "
                                        + "    document.addEventListener('DOMContentLoaded', function() { "
                                        + "        dibujarFirma(); "
                                        + "    });");
                                out.print("</script>");
                            }
                            out.print("<div class='d-flex mb-3'>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTwo + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtName' id='TxtName' placeholder='Nombre completo' value='" + ((form[1].toString().equals("N/A")) ? "" : form[1].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='1'>");
                            out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidActionDraw'>");
                            out.print("<div class='d-flex align-items-center' style='bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidActionDraw\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidActionDraw\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");
                            //</editor-fold>
                            out.print("</div>");

                            out.print("<div class='tab-pane fade " + ((TypeSig == 2) ? "show active" : "") + "' id='Write4' role='tabpanel' aria-labelledby='Write-tab4'>");
                            //<editor-fold defaultstate="collapsed" desc="SIGNATURE WRITING">
                            out.print("<form action='ClientSection?opt=16&IdDoc=" + IdDOc + "' method='post' class='needs-validation' novalidate=''>");
                            if (validSign && TypeSig == 2) {
                                String[] DataSig = objSign[3].toString().split("/");
                                out.print("<div class='signature-input d-flex'>");
                                out.print("<input type='text' class='form-control col-lg-7' name='TxtSignatureWrite' id='name-input' value='" + DataSig[0] + "' placeholder='Escribe tu nombre...' required>");
                                out.print("<select class='form-control col-lg-5 ml-2' id='font-style-select' name='TxtSigLetter'>");
                                out.print("<option selected value='" + DataSig[1] + "' class='" + DataSig[1] + "'>" + DataSig[1] + "</option>");
                            } else {
                                out.print("<div class='signature-input d-flex'>");
                                out.print("<input type='text' class='form-control col-lg-7' name='TxtSignatureWrite' id='name-input' placeholder='Escribe tu nombre...' required>");
                                out.print("<select class='form-control col-lg-5 ml-2' id='font-style-select' name='TxtSigLetter'>");
                            }
                            out.print("<option value='GreatVibes' class='GreatVibes'>GreatVibes</option>");
                            out.print("<option value='Allura' class='Allura'>Allura</option>");
                            out.print("<option value='Coockie' class='Coockie'>Coockie</option>");
                            out.print("<option value='Whisper' class='Whisper'>Whisper</option>");
                            out.print("<option value='Tangerine' class='Tangerine'>Tangerine</option>");
                            out.print("</select>");
                            out.print("</div>");
                            out.print("<div class='canvas-container'>");
                            out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                            out.print("<canvas id='text-canvas' width='400' height='80'></canvas>");
                            out.print("<div class=''>");
                            out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('text-canvas')\"><i class='fas fa-sync-alt'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            if (validSign && TypeSig == 2) {
                                out.print("<script>");
                                out.print("document.addEventListener('DOMContentLoaded', function() { "
                                        + "    if (nameInput.value) { "
                                        + "        updateText(); "
                                        + "    } "
                                        + "    }); "
                                        + "    function updateText() { "
                                        + "        const name = nameInput.value; "
                                        + "        contextText.clearRect(0, 0, textCanvas.width, textCanvas.height); "
                                        + "        contextText.font = `bold 60px ${fontStyleSelect.options[fontStyleSelect.selectedIndex].text}`; "
                                        + "        contextText.fillText(name, 10, 50); "
                                        + "    } "
                                        + "   ");
                                out.print("</script>");
                            }
                            out.print("<div class='d-flex mb-3'>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTwo + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtName' id='TxtName' placeholder='Nombre completo' value='" + ((form[1].toString().equals("N/A")) ? "" : form[1].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='2'>");
                            out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidActionWrite'>");
                            out.print("<div class='d-flex align-items-center' style='bottom: 18px;width: 94%;justify-content: center;'>");
                            out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' data-placement='top' title='" + ButtonSave + "' onclick='ValidAction(\"TxtValidActionWrite\",1)'><i class='fas fa-save'></i></button>");
                            out.print("<button class='btn btn-blue' data-toggle='tooltip' data-placement='top' title='" + ButtonAd + "' onclick='ValidAction(\"TxtValidActionWrite\",2)'><i class=\"fas fa-share-square\"></i></button>");
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");

                            //</editor-fold>
                            out.print("</div>");

                            out.print("<div class='tab-pane fade show active' id='Img4' role='tabpanel' aria-labelledby='Img-tab4'>");
                            //<editor-fold defaultstate="collapsed" desc="SIGNATURE IMAGEN">
                            out.print("<form action='AttachSignature.jsp' method='post' enctype='multipart/form-data' class='needs-validation' novalidate=''>");
                            out.print("<div class='canvas-container'>");
                            out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                            out.print("<canvas id='image-canvas' width='400' height='200'></canvas>");
                            out.print("<div class=''>");
                            out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('image-canvas');" + ((validSign) ? "sigChangev2()" : "") + "\"><i class='fas fa-sync-alt'></i></button>");
                            out.print("</div>");
                            out.print("</div>");

                            if (validSign) {
                                out.print("<input type='hidden' class='form-control' id='image-path-input' value='Interfaz/Contenido/SagrilaftDocs/Signature/" + PathImg + "' >");
                            }

                            out.print("<div class='signature-input' id='sigChange' style='display: " + ((validSign) ? "none" : "block") + ";'>");
                            out.print("<label for='file-input'><b>Subir imagen de firma:</b></label><br>");
                            out.print("<input type='file' name='TxtImageSigna' id='file-input' accept='image/*' onchange='cargarImagen(event)' required>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class='d-flex mb-3'>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionTwo + "<span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='text' class='form-control' name='TxtName' id='TxtName' placeholder='Nombre completo' value='" + ((form[1].toString().equals("N/A")) ? "" : form[1].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<div class='mt-4'>");
                            out.print("<h6>" + SectionThree + " <span class='text-danger'>*</span></h6>");
                            out.print("</div>");
                            out.print("<div class='mt-2'>");
                            out.print("<input type='number' class='form-control' name='NmbDocx' id='NmbDocument' placeholder='Numero de documento' value='" + ((form[2].toString().equals("N/A")) ? "" : form[2].toString()) + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<input type='hidden' class='form-control' name='idDoc' id='IdDOc' value='" + IdDOc + "'>");
                            out.print("<input type='hidden' class='form-control' name='TypeSig' id='IdTypeSig' value='3'>");
                            out.print("<input type='hidden' class='form-control' name='NbmIdSigna' id='NbmIdSigna' value='" + IdSigna + "'>");
//                            out.print("<input type='hidden' class='form-control' name='TxtFormat' value='" + Format + "'>");
                            out.print("<input type='hidden' class='form-control' name='TxtValidAction' id='TxtValidActionImg'>");
                            out.print("<div class='d-flex align-items-center' style='bottom: 18px;width: 94%;justify-content: center;'>");

                            if (validSign) {
                                out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' id='buttonSvve' style='display: none;' data-placement='top' title='" + ButtonSave + "' onclick='validImg(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\", 1)'><i class='fas fa-save'></i></button>");
                                out.print("<button class='btn btn-blue mr-2 disabled' data-toggle='tooltip' id='buttonNsvve' style='display: block;' data-placement='top' title='" + ButtonSave + "' onclick=''><i class='fas fa-save'></i></button>");
                            } else {
                                out.print("<button class='btn btn-blue mr-2' data-toggle='tooltip' id='buttonSvve' style='display: block;' data-placement='top' title='" + ButtonSave + "' onclick='validImg(\"" + ((TypeDocument.contains("Ingles")) ? "en" : "es") + "\", 1)'><i class='fas fa-save'></i></button>");
                            }

                            if (validSign) {
                                out.print("<button class='btn btn-blue' data-toggle='tooltip' id='' data-placement='top' title='" + ButtonAd + "' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "\"'><i class=\"fas fa-share-square\"></i></button>");
                            }
                            if (bntFinal) {
                                out.print("<button class='btn btn-success' type='button' onclick='window.location.href=\"ClientSection?opt=18&IdDoc=" + IdDOc + "&Sttate=" + est + "\"' style='top: 50px; right: 8px;' data-toggle='tooltip' data-placement='left' title='Finalizar'><i class=\"fas fa-check-circle\"></i></button>");
                            }
                            out.print("</div>");
                            out.print("</form>");

                            //</editor-fold>
                            out.print("</div>");

                            if (validSign && TypeSig == 3) {
                                out.print("<script>");
                                out.print("document.addEventListener('DOMContentLoaded', function() { "
                                        + "        const imagePathInput = document.getElementById('image-path-input'); "
                                        + "        const imageCanvas = document.getElementById('image-canvas'); "
                                        + "        const contextImage = imageCanvas.getContext('2d'); "
                                        + "        const imagePath = imagePathInput.value; "
                                        + " "
                                        + "        const image = new Image(); "
                                        + "        image.onload = function() { "
                                        + "            contextImage.clearRect(0, 0, imageCanvas.width, imageCanvas.height); "
                                        + "            contextImage.drawImage(image, 0, 0, imageCanvas.width, imageCanvas.height); "
                                        + "        }; "
                                        + "        image.src = imagePath; "
                                        + "    });");
                                out.print("</script>");
                            }

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                        } else if (estTl == 15) {
                            //<editor-fold defaultstate="collapsed" desc="FINAL FORM">
                            String infoOne = bundle.getString("formQ.infoOne");
                            String infoTwo = bundle.getString("formQ.infoTwo");
                            out.print("<div class='section-body' style='color: black'>");
//                            out.print("<h2 class='' style='position: absolute;font-size: 20px; color: black; font-weight: 700; margin: 30px 0 25px 0;'><i class=\"fas fa-caret-right\"></i> &nbsp;Operaciones internacionales</h2>");
                            out.print("<div class='row' style='background: #e7e7e7; padding-top: 47px;'>");
                            out.print("<div class='col-12 col-md-6 col-sm-12' style='margin: auto; margin-top: 15px;'>");
                            out.print("<div class='card' style='border-radius: 5px;'>");
                            out.print("<div class='card-body'>");
                            out.print("<div class='' data-height='270'>");
                            out.print("<div class='empty-state-icon'>");
                            out.print("</div>");

                            out.print("<div class='text-center'>");
                            out.print("<h4>¡" + infoOne + "!</h4><br>");
                            out.print("<i class=\"fas fa-check-circle\" style='color: #3bcb33; font-size: 90px;'></i>");
                            out.print("</div>");

                            out.print("<div class='text-center mt-4'>");
                            out.print("<h6>" + infoTwo + "</h6>");
                            out.print("</div>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
//</editor-fold>
                        }
                        //</editor-fold>
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</section>");
                }
            } else {
            }
        } catch (Exception e) {
            Logger.getLogger(Tag_ClientSection.class.getName()).log(Level.SEVERE, null, e);
        }
        return super.doStartTag();
    }
}
