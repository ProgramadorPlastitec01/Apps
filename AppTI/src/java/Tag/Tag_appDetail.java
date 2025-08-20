package Tag;
//<editor-fold defaultstate="collapsed" desc="IMPORTS">

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import SQL.ConnectionsBd;

import Controller.AppControllerJpa;
import Controller.AppHeaderControllerJpa;
import Controller.AppDetailControllerJpa;
import Controller.FormatControllerJpa;
import Controller.SettingControllerJpa;
import Controller.UserControllerJpa;

import Method.SignatureToImg;
import com.google.gson.Gson;
//</editor-fold>

public class Tag_appDetail extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        AppControllerJpa AppJpa = new AppControllerJpa();
        AppHeaderControllerJpa AppheadJpa = new AppHeaderControllerJpa();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        AppDetailControllerJpa AppDetail = new AppDetailControllerJpa();
        ConnectionsBd SirhJpa = new ConnectionsBd();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        UserControllerJpa UserJpa = new UserControllerJpa();
        SignatureToImg ConverTo = new SignatureToImg();
        List lst_app = null;
        List lst_apph = null;
        List lst_format = null;
        List lst_sirh = null;
        List lst_appDetail = null;
        List lst_setting = null;
        List lst_user = null;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="VARIABLE">
        int module = 0, idApp = 0, state = 0, swpt = 0, idDet = 0, step = 0;
        String[] structure = {};
        try {
            module = Integer.parseInt(pageContext.getRequest().getAttribute("mod").toString());
        } catch (Exception e) {
            module = 0;
        }
        try {
            idApp = Integer.parseInt(pageContext.getRequest().getAttribute("idApp").toString());
        } catch (Exception e) {
            idApp = 0;
        }
        //</editor-fold>
        try {
            if (module == 3) {
                //<editor-fold defaultstate="collapsed" desc="APP DOCUMENT">
                //<editor-fold defaultstate="collapsed" desc="VARIABLE">

                int idDoc = 0, idHead = 0, docx = 0, codx = 0;
                String nameDoc = "", code = "", format = "", type = "";
                try {
                    idDoc = Integer.parseInt(pageContext.getRequest().getAttribute("idDoc").toString());
                } catch (Exception e) {
                    idDoc = 0;
                }
                try {
                    idHead = Integer.parseInt(pageContext.getRequest().getAttribute("idHead").toString());
                } catch (Exception e) {
                    idHead = 0;
                }
                try {
                    swpt = Integer.parseInt(pageContext.getRequest().getAttribute("swpt").toString());
                } catch (Exception e) {
                    swpt = 0;
                }
                try {
                    idDet = Integer.parseInt(pageContext.getRequest().getAttribute("idDet").toString());
                } catch (Exception e) {
                    idDet = 0;
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
                    type = pageContext.getRequest().getAttribute("type").toString();
                } catch (Exception e) {
                    type = "";
                }
                try {
                    step = Integer.parseInt(pageContext.getRequest().getAttribute("step").toString());
                } catch (Exception e) {
                    step = 0;
                }
//</editor-fold>
                lst_format = FormatJpa.ConsultFormatId(idDoc);
                if (lst_format != null) {
                    Object[] ObjDoc = (Object[]) lst_format.get(0);
                    code = ObjDoc[1].toString();
                    nameDoc = ObjDoc[2].toString();
                    try {
                        format = ObjDoc[3].toString();
                    } catch (Exception e) {
                        format = "";
                    }
                } else {
                    code = "";
                    nameDoc = "";
                    format = "";
                }
                String[] typeSc = type.toString().split("/");

                if (swpt == 2) {
                    //<editor-fold defaultstate="collapsed" desc="LIST ASISTENTS">
                    //<editor-fold defaultstate="collapsed" desc="ASISTENTS">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 44%; right: 21%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Asistentes </h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    lst_appDetail = AppDetail.ConsultAppDetailId(idDet);
                    if (lst_appDetail != null) {
                        Object[] ObjPers = (Object[]) lst_appDetail.get(0);
                        String[] pers = ObjPers[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        out.print("<div class=''>");
                        out.print("<table class='table table-bordered table-md' id='table-1'>");
                        out.print("<thead>");
                        out.print("<tr class='text-center'>");
                        out.print("<th>Nombre</th>");
                        out.print("<th>Cargo</th>");
                        out.print("<th>Firma</th>");
                        out.print("</tr>");
                        out.print("</thead>");
                        out.print("<tbody>");
                        for (int i = 0; i < pers.length; i++) {
                            String[] perste = pers[i].toString().split(" / ");
                            out.print("<tr class='text-center'>");
                            out.print("<td>" + perste[0] + "</td>");
                            String cargue = perste[1].toString().replace("EJECT", "").replace("APROB", "");
                            out.print("<td>" + cargue.trim() + "</td>");
                            if (perste[4].toString().trim().equals("XX")) {
                                out.print("<td class='text-center'><button class='btn btn-info' onclick='mostrarConvencion(4);ValidData(\"" + perste[2].trim() + "\", \"" + perste[3].trim() + "\");'><i class='fas fa-signature'></i></button></td>");
                            } else {
                                out.print("<td class='text-center'><b class='text-success'><i class='fas fa-check' style='font-size: 20px;'></i></b></td>");
                            }
                            out.print("</tr>");
                        }
                        out.print("</tbody>");
                        out.print("</table>");
                        out.print("</div>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h4>Ha ocurrido un problema al cargar los asistentes, favor revisar si tiene personal "
                                + "registrado en el documento, en caso de que se siga presentando el error comunicarse con el programador.</h4>");
                        out.print("<i class='fas fa-exclamation-triangle' style='font-size: 80px;'></i>");
                        out.print("</div>");
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

                    //<editor-fold defaultstate="collapsed" desc="FIND OUT SIGNATURE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='contGeneral' style='width: 44%; right: 21%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Consultar firmas </h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    out.print("<form action='AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&idDet=" + idDet + "&swpt=3&step=" + step + "' method='post' class=''>");
                    out.print("<input type='hidden' id='idDocx' value=''>");
                    out.print("<input type='hidden' id='idCodx' value=''>");
                    out.print("<div class='d-flex align-items-center'>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<span class='text-danger' id='DocSpan' style='display: none;'>El documento no coincide!</span>");
                    out.print("<input type='number' class='form-control' name='docx' id='FinDocx' placeholder='Documento' value='' onkeyup='FindDoc()' required>");
                    out.print("</div>");
                    out.print("<div class='col-lg-4'>");
                    out.print("<span class='text-danger' id='CodSpan' style='display: none;'>El codigo no coincide!</span>");
                    out.print("<input type='number' class='form-control' name='codx' id='FinCodx' placeholder='Codigo' value='' onkeyup='FindCod()' required>");
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
                    //</editor-fold>
                } else if (swpt == 3) {
                    //<editor-fold defaultstate="collapsed" desc="SIGNATURE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 50%; right: 20%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Firma</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    lst_sirh = SirhJpa.Consultar_SIRH(docx, codx);
                    if (lst_sirh != null) {
                        //<editor-fold defaultstate="collapsed" desc="PERSONAL INFORMATION">
                        String[] ObjSr = lst_sirh.toString().replace("[", "").replace("]", "").split("///");
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
                        out.print("<div class=''>");
                        out.print("<h4>Se ha presentado un error al consultar la información del empleado.</h4>");
                        out.print("<i class='fas fa-exclamation-triangle' style='font-size: 80px;'></i>");
                        out.print("</div>");
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

                        out.print("<form action='AppDetail?opt=7&idDet=" + idDet + "&idHead=" + idHead + "&idDoc=" + idDoc + "&idApp=" + idApp + "&type=" + type + "&step=" + step + "' method='post' class=''>");
                        out.print("<input type='hidden' name='idSignature' id='' value='" + ObjSig[0].toString().replace("[", "") + "'>");
                        out.print("<input type='hidden' name='docx' id='' value='" + docx + "'>");
                        out.print("<input type='hidden' name='codx' value='" + codx + "'>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green'>Firmar</button>");
                        out.print("</div>");
                        out.print("</form>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="NEW SIGNATURE">
                        out.print("<div class='text-center mt-4'>");
                        out.print("<h5><b class='text-warning'>Firma No Encontrada &nbsp;</b> <i class='fas fa-exclamation-triangle' style='font-size: 20px;'></i></h5>");
                        out.print("<h6>Debe dibujar la firma en el siguiente recuadro! </h6>");
                        out.print("</div>");

                        out.print("<div class='d-flex' style='justify-content: center;margin-top: 40px;'>");
                        out.print("<div class='signature-pad' style='margin-bottom: 20px;'>");
                        out.print("<canvas id='signature-canvas' width='500' height='250'></canvas>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<button class='btn btn-warning ml-4' onclick='clearCanvas()'><i class='fas fa-eraser'></i></button>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<form action='AppDetail?opt=7&idDet=" + idDet + "&idHead=" + idHead + "&idDoc=" + idDoc + "&idApp=" + idApp + "&type=" + type + "&step=" + step + "' method='post' class=''>");
                        out.print("<input type='hidden' name='docx' value='" + docx + "'>");
                        out.print("<input type='hidden' name='codx' value='" + codx + "'>");
                        out.print("<input type='hidden' class='form-control' name='txtSignature' id='coordenadas-hidden' value='" + signt + "'>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green'>Guardar y Firmar</button>");
                        out.print("</div>");
                        out.print("</form>");
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
                } else if (code.contains("-014")) {
                    if (swpt == 1) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTER 014">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 70%; right: 6%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Ingresar datos del acta </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='AppDetail?opt=2&idHead=" + idHead + "&idApp=" + idApp + "&idDoc=" + idDoc + "&type=" + type + "&step=" + step + "' method='post' id='formData'>");
                        out.print("<div id=\"accordion\">");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-99\" aria-expanded=\"true\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                        out.print("<h4>Datos basicos</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse show\" id=\"panel-body-99\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="BASIC DATA">
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<span class=''>Fecha de registro</span>");
                        out.print("<input type='date' class='form-control' name='txtDate' id='idDateForm' value='' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-8'>");
                        out.print("<span class=''>Asunto</span>");
                        out.print("<input type='text' id='idAffair' class='form-control' placeholder='Asunto del acta' name='txtAffair' id=''  value='' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class=\"accordion\">");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-98\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                        out.print("<h4>Asignar personal</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-98\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="PERSONAL">
                        out.print("<div class=''>");
                        lst_sirh = SirhJpa.Consultar_SIRHPersonal();
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6 mr-3 contSwtch' style='margin-left: -8px;' >");
                        //<editor-fold defaultstate="collapsed" desc="CONSULT PERSONAL">
                        if (lst_sirh != null) {
                            String[] personalData = lst_sirh.toString().split("///");
                            out.print("<span class='titlePers'>Personal</span>");
                            out.print("<input type='text' class='form-control col-lg-10' id='table-filter' placeholder='Buscar...' style='text-align: center; margin: auto;'>");
                            out.print("<div id='tabPersonal' class='mt-2' style='max-height: 280px;overflow-y: scroll;'>");
                            out.print("<ul id=\"available-persons\" class=\"list-group\" style='line-height: 18px;'>");
                            for (int i = 0; i < personalData.length - 1; i++) {
                                String[] data = personalData[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                try {
                                    out.print("<li class=\"list-group-item\" data-person-id='" + i + "' data-person-info='" + data[0].replace(",", "") + " / " + data[3] + " / " + data[1] + " / " + data[2] + " / XX' style='font-size: 13px; padding: 10px; cursor: alias; transition: all 0.4s ease-out;'>");
                                    out.print("" + data[2] + " / " + data[0].replace(",", "") + " / " + data[3] + "");
                                    out.print("</li>");
                                } catch (Exception e) {
                                }
                            }
                            out.print("</ul>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class='col-lg-6 contSwtch'>");
                        //<editor-fold defaultstate="collapsed" desc="PERSONAL SELECTED">
                        out.print("<span class='titlePers'>Seleccionado</span>");
                        out.print("<div id='tabSelected' class='mt-2' style='max-height: 280px;overflow-y: auto;'>");
                        out.print("<ul id='assigned-persons' class='list-group'>");

                        out.print("</ul>");
                        out.print("</div>");
                        out.print("<input type=\"hidden\" id=\"assigned-person-ids\" name=\"txtPersonal\">");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-97\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                        out.print("<h4>Contenido del acta</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-97\" data-parent=\"#accordion\" style='max-height: 300px; overflow-y: auto;'>");
                        //<editor-fold defaultstate="collapsed" desc="DOCUMENT CONTENT">
                        out.print("<textarea id='editorCK' name='txtContent' placeholder='Contenidon del acta'></textarea>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");

                        out.print("<div class='mt-2 text-center'>");
                        out.print("<button type='button' class='btn btn-green' onclick='validForm(\"idDateForm\",\"idAffair\",\"assigned-person-ids\",\"cke_editable\")'>Registrar</button>");
                        out.print("</div>");

                        out.print("</form>");

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
                    } else if (idDet > 0) {
                        //<editor-fold defaultstate="collapsed" desc="EDIT DOCUMENT">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 70%; right: 6%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Modificar datos del acta </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        lst_appDetail = AppDetail.ConsultAppDetailId(idDet);
                        if (lst_appDetail != null) {
                            Object[] ObjDtil = (Object[]) lst_appDetail.get(0);
                            String[] content = ObjDtil[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            String[] docPersonal = ObjDtil[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<form action='AppDetail?opt=2&idHead=" + idHead + "&idApp=" + idApp + "&idDoc=" + idDoc + "&type=" + type + "&idDet=" + idDet + "&step=" + step + "' method='post' id='formData'>");
                            out.print("<div id=\"accordion\">");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-99\" aria-expanded=\"true\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                            out.print("<h4>Datos basicos</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse show\" id=\"panel-body-99\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="BASIC DATA">
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-4'>");
                            out.print("<span class=''>Fecha de registro</span>");
                            out.print("<input type='date' class='form-control' name='txtDate' id='idDateForm' value='" + ObjDtil[2] + "' required>");
                            out.print("</div>");
                            out.print("<div class='col-lg-8'>");
                            out.print("<span class=''>Asunto</span>");
                            out.print("<input type='text' class='form-control' placeholder='Asunto del acta' name='txtAffair' id='idAffair' value='" + content[0] + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("<div class=\"accordion\">");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-98\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                            out.print("<h4>Asignar personal</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-body-98\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="PERSONAL">
                            out.print("<div class=''>");
                            lst_sirh = SirhJpa.Consultar_SIRHPersonal();
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6 mr-3 contSwtch' style='margin-left: -8px;' >");
                            //<editor-fold defaultstate="collapsed" desc="CONSULT PERSONAL">
                            String documents = "";
                            for (int i = 0; i < docPersonal.length; i++) {
                                String[] datPrls = docPersonal[i].toString().split(" / ");
                                documents += "[" + datPrls[2] + "]";
                            }
                            if (lst_sirh != null) {
                                String[] personalData = lst_sirh.toString().split("///");
                                out.print("<span class='titlePers'>Personal</span>");
                                out.print("<input type='text' class='form-control col-lg-10' id='table-filter' placeholder='Buscar...' style='text-align: center; margin: auto;'>");
                                out.print("<div id='tabPersonal' class='mt-2' style='max-height: 280px;overflow-y: scroll;'>");
                                out.print("<ul id=\"available-persons\" class=\"list-group\" style='line-height: 18px;'>");
                                for (int i = 0; i < personalData.length - 1; i++) {
                                    String[] data = personalData[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    try {
                                        if (!documents.contains("[" + data[1] + "]")) {
                                            out.print("<li class=\"list-group-item\" data-person-id='" + i + "' data-person-info='" + data[0].replace(",", "") + " / " + data[3] + " / " + data[1] + " / " + data[2] + " / XX' style='font-size: 13px; padding: 10px; cursor: alias; transition: all 0.4s ease-out;'>");
                                            out.print("" + data[2] + " / " + data[0].replace(",", "") + " / " + data[3] + "");
                                            out.print("</li>");
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                out.print("</ul>");
                                out.print("</div>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                            out.print("<div class='col-lg-6 contSwtch'>");
                            //<editor-fold defaultstate="collapsed" desc="PERSONAL SELECTED">
                            out.print("<span class='titlePers'>Seleccionado</span>");
                            out.print("<div id='tabSelected' class='mt-2' style='max-height: 280px;overflow-y: auto;'>");
                            out.print("<ul id='assigned-persons' class='list-group'>");
                            if (docPersonal.length > 0) {
                                for (int i = 0; i < docPersonal.length; i++) {
                                    String[] datUsr = docPersonal[i].toString().split(" / ");
                                    out.print("<li class=\"list-group-item\" data-person-id='user" + i + "' data-person-info='" + datUsr[0] + " / " + datUsr[1] + " / " + datUsr[2] + "  / " + datUsr[3] + " / " + datUsr[4] + " '>");
                                    out.print(datUsr[3] + " / " + datUsr[0] + " / " + datUsr[1]);
                                    out.print("</li>");
                                }
                            }
                            out.print("</ul>");
                            out.print("</div>");
                            out.print("<input type=\"hidden\" id=\"assigned-person-ids\" name=\"txtPersonal\" value='" + ObjDtil[5] + "'>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-97\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                            out.print("<h4>Contenido del acta</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-body-97\" data-parent=\"#accordion\" style='max-height: 300px; overflow-y: auto;'>");
                            //<editor-fold defaultstate="collapsed" desc="DOCUMENT CONTENT">
                            out.print("<textarea id='editorCK' name='txtContent' placeholder=''>" + content[1] + "</textarea>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='mt-2 text-center'>");
                            out.print("<button type='button' class='btn btn-green' onclick='validForm(\"idDateForm\",\"idAffair\",\"assigned-person-ids\",\"editorNext\")'>Actualizar</button>");
                            out.print("</div>");
                        }

                        out.print("</form>");

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
                } else if (code.contains("-033")) {
                    if (swpt == 1) {
                        //<editor-fold defaultstate="collapsed" desc="REGISTRO 033">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 67%; right: 113px;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Registrar datos</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='AppDetail?opt=6&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&step=" + step + "' method='post' class=''>");
                        out.print("<div id='accordion'>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-1\"  aria-expanded=\"true\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                        out.print("<h4>Datos Basicos</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse show\" id=\"panel-body-1\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="ACCORD ONE">
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span class=''>Areas usuarias</span>");
                        out.print("<input type='text' class='form-control' name='txtAreasUx' placeholder='Areas' id='' value='' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span class=''>Roles</span>");
                        out.print("<input type='text' class='form-control' name='txtRols' placeholder='Roles' id='' value='' required>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-2\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                        out.print("<h4>Requerimientos</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-2\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="ACCORD TWO">
                        out.print("<div id='ContRequi' style='max-height: 230px;overflow-y: auto;' >");
                        out.print("<div class='d-flex' style='width: 98%;'>");
                        out.print("<div class='col-lg-5' style='padding: 0 !important;'>");
                        out.print("<span>Requerimiento</span>");
                        out.print("<input type='text' class='form-control' style='margin-right: 0; margin-left: 0;' name='txtRequi_0' id=''  value='' required>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<span>Incremento</span>");
                        out.print("<div class='d-flex' style=''>");
                        out.print("<input type='text' class='form-control ml-2' name='txtMoveX_0' id='IncrX' style='width: 43px;margin-right: 0px;' placeholder='X' value='' oninput='updateVersionFinal()' required>");
                        out.print("<input type='text' class='form-control' name='txtMoveY_0' id='IncrY' style='width: 43px;margin-right: 0px;margin-left: 0px;' placeholder='Y' value='' oninput='updateVersionFinal()' required>");
                        out.print("<input type='text' class='form-control mr-2' name='txtMoveJ_0' id='IncrJ' style='width: 43px;margin-left: 0px;' placeholder='J' value='' oninput='updateVersionFinal()' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='mr-2'>");
                        out.print("<span>Area</span>");
                        out.print("<input type='text' class='form-control' style='margin-left: 0;padding: 3px;' name='txtArea_0' id='' value='' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-2 mr-2' style='padding: 0;'>");
                        out.print("<span>Fecha solicitud</span>");
                        out.print("<input type='date' class='form-control' style='margin-left: 0px;' name='txtDateSol_0' id='' value='' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-2' style='padding: 0;'>");
                        out.print("<span>Fecha ejecucion</span>");
                        out.print("<input type='date' class='form-control' style='margin-left: 0px;' name='txtDateEjec_0' id='' value='' v>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<input type='hidden' name='inCouter' id='InCouter' value='0'>");
                        out.print("<div class='d-flex' style='justify-content: space-between;'>");
                        out.print("<div class='d-flex' style='align-items: center;text-align: center;'>");
                        out.print("<span class=''>Versión Final</span>");
                        out.print("<input type='text' class='form-control' name='' id='VersFinal'  value=''>");
                        out.print("</div>");
                        out.print("<div style='margin-top: 25px;'>");
                        out.print("<button type='button' class='btn btn-info' onclick='addRequi()'><i class='fas fa-plus'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-3\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                        out.print("<h4>Programador</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-3\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="ACCORD THREE">
                        out.print("<div class=''>");
                        out.print("<form action='' method='post' class=''>");
                        out.print("<div class='d-flex' style='justify-content: center;'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Programador</span>");
                        out.print("<select class='form-control' name='CbxDev' style='margin-top: 12px;'>");
                        out.print("<option value='' selected disabled>Seleccionar programador</option>");
                        lst_user = UserJpa.ConsultDevelopersUser();
                        if (lst_user != null) {
                            for (int i = 0; i < lst_user.size(); i++) {
                                Object[] ObjUser = (Object[]) lst_user.get(i);
                                out.print("<option value='" + ObjUser[0] + " / PROGRAMADOR EJECT / " + ObjUser[1] + " / " + ObjUser[2] + " / XX'>" + ObjUser[2] + " - " + ObjUser[0] + "</option>");
                            }
                        } else {
                            out.print("<option value='' disabled>Error</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Fecha Capacitación</span>");
                        out.print("<input type='date' class='form-control' name='TxtDateCapac' id='' value='' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print("<span>Observaciones</span>");
                        out.print("<textarea class='form-control' name='TxtObservations' placeholder='Escribe algo...'></textarea>");
                        out.print("</div>");
                        out.print("</form>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-4\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                        out.print("<h4>Datos adicionales</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-4\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="ACCORD FOUR">
                        out.print("<div class=''>");
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Fecha y hora de lanzamiento</span>");
                        lst_appDetail = AppDetail.ConsultDocumentType(idHead, "Lanzamiento");
                        if (lst_appDetail != null) {
                            Object[] ObjLan = (Object[]) lst_appDetail.get(0);
                            out.print("<input type='datetime-local' class='form-control' name='TxtDateTime' id=''  value='" + ObjLan[2] + "'>");
                        } else {
                            out.print("<input type='text' class='form-control' disabled value='Sin lanzamiento'>");
                        }
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Versión Final</span>");
                        out.print("<input type='text' class='form-control' name='txtFinalVers' id='VerFin'  value='Va. '>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Nuevos roles</span>");
                        out.print("<input type='text' class='form-control' name='TxtNewRole' id=''  value='N/A'>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Nuevas áreas</span>");
                        out.print("<input type='text' class='form-control' name='TxtNewArea' id=''  value='N/A'>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Cobian BD</span>");
                        out.print("<input type='text' class='form-control' name='TxtCobianBD' id=''  value='N/A'>");
                        out.print("</div>");
                        out.print("<div class='col-lg-6'>");
                        out.print("<span>Cobian Archivos</span>");
                        out.print("<input type='text' class='form-control' name='TxtCobianFile' id=''  value='N/A'>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-5\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                        out.print("<h4>Solicitante(s)</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-5\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="ACCORD FIVE">
                        out.print("<div class=''>");
                        lst_sirh = SirhJpa.Consultar_SIRHPersonal();
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6 mr-3 contSwtch' style='margin-left: -8px;' >");
                        //<editor-fold defaultstate="collapsed" desc="CONSULT PERSONAL">
                        if (lst_sirh != null) {
                            String[] personalData = lst_sirh.toString().split("///");
                            out.print("<span class='titlePers'>Personal</span>");
                            out.print("<input type='text' class='form-control col-lg-10' id='table-filter' placeholder='Buscar...' style='text-align: center; margin: auto;'>");
                            out.print("<div id='tabPersonal' class='mt-2' style='max-height: 170px;overflow-y: scroll;'>");
                            out.print("<ul id=\"available-persons\" class=\"list-group\" style='line-height: 18px;'>");
                            for (int i = 0; i < personalData.length - 1; i++) {
                                String[] data = personalData[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                try {
                                    out.print("<li class=\"list-group-item\" data-person-id='" + i + "' data-person-info='" + data[0].replace(",", "") + " / " + data[3] + " / " + data[1] + " / " + data[2] + " / XX' style='font-size: 13px; padding: 10px; cursor: alias; transition: all 0.4s ease-out;'>");
                                    out.print("" + data[2] + " / " + data[0].replace(",", "") + " / " + data[3] + "");
                                    out.print("</li>");
                                } catch (Exception e) {
                                }
                            }
                            out.print("</ul>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class='col-lg-6 contSwtch'>");
                        //<editor-fold defaultstate="collapsed" desc="PERSONAL SELECTED">
                        out.print("<span class='titlePers'>Seleccionado</span>");
                        out.print("<div id='tabSelected' class='mt-2' style='max-height: 170px;overflow-y: auto;'>");
                        out.print("<ul id='assigned-persons' class='list-group'>");

                        out.print("</ul>");
                        out.print("</div>");
                        out.print("<input type=\"hidden\" id=\"assigned-person-ids\" name=\"txtPersonal\">");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green'>Registrar</button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</form>");
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
                    } else if (idDet > 0) {
                        //<editor-fold defaultstate="collapsed" desc="EDIT REGISTER 033">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 67%; right: 113px;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Editar datos</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        lst_appDetail = AppDetail.ConsultAppDetailId(idDet);
                        if (lst_appDetail != null) {
                            Object[] ObjEditD = (Object[]) lst_appDetail.get(0);
                            String[] content = ObjEditD[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            out.print("<form action='AppDetail?opt=6&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&idDet=" + idDet + "&step=" + step + "' method='post' class=''>");
                            out.print("<div id='accordion'>");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-1\"  aria-expanded=\"true\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                            out.print("<h4>Datos Basicos</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse show\" id=\"panel-body-1\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="ACCORD ONE">
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span class=''>Areas usuarias</span>");
                            out.print("<input type='text' class='form-control' name='txtAreasUx' placeholder='Areas' id='' value='" + content[0] + "' required>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span class=''>Roles</span>");
                            out.print("<input type='text' class='form-control' name='txtRols' placeholder='Roles' id='' value='" + content[1] + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-2\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                            out.print("<h4>Requerimientos</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-body-2\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="ACCORD TWO">
                            out.print("<div id='ContRequi' style='max-height: 230px;overflow-y: auto;' >");
                            String[] requeriment = content[2].split("---");
                            int cout = 0;
                            for (int i = 0; i < requeriment.length; i++) {
                                String[] dataReq = requeriment[i].split("/");
                                out.print("<div class='d-flex' style='width: 98%;'>");
                                out.print("<div class='col-lg-5' style='padding: 0 !important;'>");
                                out.print("<span>Requerimiento</span>");
                                out.print("<input type='text' class='form-control' style='margin-right: 0; margin-left: 0;' name='txtRequi_" + i + "' id=''  value='" + dataReq[0] + "' required>");
                                out.print("</div>");
                                out.print("<div class=''>");
                                out.print("<span>Incremento</span>");
                                out.print("<div class='d-flex' style=''>");
                                out.print("<input type='text' class='form-control ml-2' name='txtMoveX_" + i + "' id='IncrX' style='width: 43px;margin-right: 0px;' placeholder='X' value='" + dataReq[1] + "' oninput='updateVersionFinal()' required>");
                                out.print("<input type='text' class='form-control' name='txtMoveY_" + i + "' id='IncrY' style='width: 43px;margin-right: 0px;margin-left: 0px;' placeholder='Y' value='" + dataReq[2] + "' oninput='updateVersionFinal()' required>");
                                out.print("<input type='text' class='form-control mr-2' name='txtMoveJ_" + i + "' id='IncrJ' style='width: 43px;margin-left: 0px;' placeholder='J' value='" + dataReq[3] + "' oninput='updateVersionFinal()' required>");
                                out.print("</div>");
                                out.print("</div>");
                                out.print("<div class='mr-2'>");
                                out.print("<span>Area</span>");
                                out.print("<input type='text' class='form-control' style='margin-left: 0;padding: 3px;' name='txtArea_" + i + "' id='' value='" + dataReq[4] + "' required>");
                                out.print("</div>");
                                out.print("<div class='col-lg-2 mr-2' style='padding: 0;'>");
                                out.print("<span>Fecha solicitud</span>");
                                out.print("<input type='date' class='form-control' style='margin-left: 0px;' name='txtDateSol_" + i + "' id='' value='" + dataReq[5] + "' required>");
                                out.print("</div>");
                                out.print("<div class='col-lg-2' style='padding: 0;'>");
                                out.print("<span>Fecha ejecucion</span>");
                                out.print("<input type='date' class='form-control' style='margin-left: 0px;' name='txtDateEjec_" + i + "' id='' value='" + dataReq[6] + "' v>");
                                out.print("</div>");

                                if (i > 0) {
                                    out.print("<div>");
                                    out.print("<button class='btn btn-danger btn-sm' onclick='removeRequ(this)' style='margin-top: 40px;margin-left: 6px;'><i class='fas fa-trash'></i></button>");
                                    out.print("</div>");
                                }

                                out.print("</div>");
                                cout = i;
                            }
                            out.print("</div>");
                            out.print("<div class='d-flex' style='justify-content: space-between;'>");
                            out.print("<div class='d-flex' style='align-items: center;text-align: center;'>");
                            out.print("<span class=''>Versión Final</span>");
                            out.print("<input type='text' class='form-control' name='' id='VersFinal'  value=''>");
                            out.print("</div>");
                            out.print("<div style='margin-top: 25px;'>");
                            out.print("<button type='button' class='btn btn-info' onclick='addRequi()'><i class='fas fa-plus'></i></button>");
                            out.print("</div>");
                            out.print("<input type='text' name='inCouter' id='InCouter' value='" + cout + "'>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-3\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                            out.print("<h4>Programador</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-body-3\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="ACCORD THREE">
                            out.print("<div class=''>");
                            out.print("<form action='' method='post' class=''>");
                            out.print("<div class='d-flex' style='justify-content: center;'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Programador</span>");
                            out.print("<select class='form-control' name='CbxDev' style='margin-top: 12px;'>");
                            out.print("<option value='" + content[3] + "'>" + content[3].split(" / ")[0] + " - " + content[3].split(" / ")[1] + "</option>");
                            lst_user = UserJpa.ConsultDevelopersUser();
                            if (lst_user != null) {
                                for (int i = 0; i < lst_user.size(); i++) {
                                    Object[] ObjUser = (Object[]) lst_user.get(i);
                                    String datos = ObjUser[2].toString();
                                    if (!datos.equals(content[3].split(" / ")[2])) {
                                        out.print("<option value='" + ObjUser[0] + " / PROGRAMADOR / " + ObjUser[1] + " / " + ObjUser[2] + " / XX'>" + ObjUser[2] + " - " + ObjUser[0] + "</option>");
                                    }
                                }
                            } else {
                                out.print("<option value='' disabled>Error</option>");
                            }
                            out.print("</select>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Fecha Capacitación</span>");
                            out.print("<input type='date' class='form-control' name='TxtDateCapac' id='' value='" + content[4] + "' required>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class=''>");
                            out.print("<span>Observaciones</span>");
                            out.print("<textarea class='form-control' name='TxtObservations' placeholder='Escribe algo...'>" + content[5] + "</textarea>");
                            out.print("</div>");
                            out.print("</form>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-4\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                            out.print("<h4>Datos adicionales</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-body-4\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="ACCORD FOUR">
                            out.print("<div class=''>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Fecha y hora de lanzamiento</span>");
                            lst_appDetail = AppDetail.ConsultDocumentType(idHead, "Lanzamiento");
                            if (lst_appDetail != null) {
                                Object[] ObjLan = (Object[]) lst_appDetail.get(0);
                                out.print("<input type='datetime-local' class='form-control' name='TxtDateTime' id='' value='" + ObjLan[2] + "'>");
                            } else {
                                out.print("<input type='text' class='form-control' disabled value='Sin lanzamiento'>");
                            }
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Versión Final</span>");
                            out.print("<input type='text' class='form-control' name='txtFinalVers' id='VerFin'  value='" + content[6] + "'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Nuevos roles</span>");
                            out.print("<input type='text' class='form-control' name='TxtNewRole' id=''  value='" + content[7] + "'>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Nuevas áreas</span>");
                            out.print("<input type='text' class='form-control' name='TxtNewArea' id=''  value='" + content[8] + "'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Cobian BD</span>");
                            out.print("<input type='text' class='form-control' name='TxtCobianBD' id=''  value='" + content[9] + "'>");
                            out.print("</div>");
                            out.print("<div class='col-lg-6'>");
                            out.print("<span>Cobian Archivos</span>");
                            out.print("<input type='text' class='form-control' name='TxtCobianFile' id=''  value='" + content[10] + "'>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-5\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'");
                            out.print("<h4>Solicitante(s)</h4>");
                            out.print("</div>");
                            out.print("<div class=\"accordion-body collapse\" id=\"panel-body-5\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="ACCORD FIVE">
                            out.print("<div class=''>");
                            lst_sirh = SirhJpa.Consultar_SIRHPersonal();
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-6 mr-3 contSwtch' style='margin-left: -8px;' >");
                            //<editor-fold defaultstate="collapsed" desc="CONSULT PERSONAL">
                            String documents = "";
                            String[] person = ObjEditD[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int i = 0; i < person.length; i++) {
                                String[] datprn = person[i].toString().split(" / ");
                                documents += "[" + datprn[2] + "]";
                            }
                            if (lst_sirh != null) {
                                String[] personalData = lst_sirh.toString().split("///");
                                out.print("<span class='titlePers'>Personal</span>");
                                out.print("<input type='text' class='form-control col-lg-10' id='table-filter' placeholder='Buscar...' style='text-align: center; margin: auto;'>");
                                out.print("<div id='tabPersonal' class='mt-2' style='max-height: 170px;overflow-y: scroll;'>");
                                out.print("<ul id=\"available-persons\" class=\"list-group\" style='line-height: 18px;'>");
                                for (int i = 0; i < personalData.length - 1; i++) {
                                    String[] data = personalData[i].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                                    try {
                                        if (!documents.contains("[" + data[1] + "]")) {
                                            out.print("<li class=\"list-group-item\" data-person-id='" + i + "' data-person-info='" + data[0].replace(",", "") + " / " + data[3] + " / " + data[1] + " / " + data[2] + " / XX' style='font-size: 13px; padding: 10px; cursor: alias; transition: all 0.4s ease-out;'>");
                                            out.print("" + data[2] + " / " + data[0].replace(",", "") + " / " + data[3] + "");
                                            out.print("</li>");
                                        }
                                    } catch (Exception e) {
                                    }
                                }
                                out.print("</ul>");
                                out.print("</div>");
                            }
                            //</editor-fold>
                            out.print("</div>");
                            out.print("<div class='col-lg-6 contSwtch'>");
                            //<editor-fold defaultstate="collapsed" desc="PERSONAL SELECTED">
                            out.print("<span class='titlePers'>Seleccionado</span>");
                            out.print("<div id='tabSelected' class='mt-2' style='max-height: 170px;overflow-y: auto;'>");
                            out.print("<ul id='assigned-persons' class='list-group'>");
                            if (person.length > 0) {
                                for (int i = 0; i < person.length; i++) {
                                    String[] datUsr = person[i].toString().split(" / ");
                                    out.print("<li class=\"list-group-item\" data-person-id='user" + i + "' data-person-info='" + datUsr[0] + " / " + datUsr[1] + " / " + datUsr[2] + "  / " + datUsr[3] + " / " + datUsr[4] + " '>");
                                    out.print(datUsr[3] + " / " + datUsr[0] + " / " + datUsr[1]);
                                    out.print("</li>");
                                }
                            }
                            out.print("</ul>");
                            out.print("</div>");
                            out.print("<input type=\"hidden\" id=\"assigned-person-ids\" name=\"txtPersonal\" value='" + ObjEditD[5] + "'>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>
                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='text-center'>");
                            out.print("<button class='btn btn-green'>Actualizar</button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</form>");
                        } else {
                            out.print("<div class='text-center mt-2'>");
                            out.print("<h5>Se ha presentado un error, favor comunicarse con los programadores.</h5>");
                            out.print("<i class=\"fas fa-exclamation-triangle\" style='font-size: 80px;'></i>");
                            out.print("</div>");
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
                }

                //<editor-fold defaultstate="collapsed" desc="LOAD DOCUMENT">
                //<editor-fold defaultstate="collapsed" desc="HEADER">
                lst_appDetail = AppDetail.ConsultDocumentType(idHead, typeSc[0] + "/" + typeSc[1]);
                Object[] ObjDetail = {};
                int stetx = 0, idDetail = 0;
                String singExits = "";
                try {
                    ObjDetail = (Object[]) lst_appDetail.get(0);
                    idDetail = Integer.parseInt(ObjDetail[0].toString());
                    stetx = Integer.parseInt(ObjDetail[6].toString());
                    try {
                        singExits = ObjDetail[5].toString();
                    } catch (Exception e) {
                        singExits = "";
                    }
                } catch (Exception e) {
                    stetx = 99;
                    idDetail = 0;
                }

                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: center;'>");
                out.print("<div style='position: absolute;left: 26px;top: 32px;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"AppDetail?opt=1&mod=2&idApp=" + idApp + "&idHead=" + idHead + "\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<h4>Documentacion</h4><h1>" + typeSc[1] + "</h1><h4>" + nameDoc + "</h4>");
                out.print("</div>");
                out.print("<div style='position: absolute; right: 26px;top: 32px;'>");

                if (code.contains("-014") && type.contains("Levantamiento")) {
                    out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Agregar acta' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&swpt=1&idHead=" + idHead + "&type=" + type + "\"'><i class='fas fa-plus'></i></button>");
                }
                if (stetx == 1) {
                    out.print("<button class='btn btn-yellow' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Continuar' onclick='window.location.href=\"AppDetail?opt=4&idApp=" + idApp + "&idDetail=" + idDetail + "&mod=2&swpt=1&idHead=" + idHead + "&step=" + step + "&xtemp=1\"'><i class=\"fas fa-share\"></i></button>");
                }

                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="LA IDEA NAVEGADOR">
                // LA IDEA ERA CREAR UN VAGADOR PARA VER EL PROXIMO DOCUMENTO Y EL ANTERIOR, PERO NO ME GUSTA COMO ESTA QUEDANDO, PA MAS TARDE SI ME ILUMINO POR QUE TENGO MERA HAMBRE Y NO PIENSO BIEN GRREA
//                out.print("<div class='secNavigator'>");
//                lst_apph = AppheadJpa.ConsultHeadbyIdApp(idApp);
//                if (lst_apph != null) {
//                    Object[] ObjApp = (Object[]) lst_apph.get(0);
//                    structure = ObjApp[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                    state = Integer.parseInt(ObjApp[4].toString());
//                    String[] data = {};
//
//                    if (state != 1) {
//                        data = structure[state - 1].toString().split("/");
//                        out.print("<div class='NavActive'>");
//                        out.print("<p style='margin: 0;'>" + data[1] + "</p>");
//                        out.print("</div>");
//                    }
//
//                    data = structure[state].toString().split("/");
//                    out.print("<div class='NavActive'>");
//                    out.print("<p style='margin: 0;'>" + data[1] + "</p>");
//                    out.print("</div>");
//
//                    data = structure[state + 1].toString().split("/");
//                    out.print("<div class='NavSecod'>");
//                    out.print("<p style='margin: 0;'>" + data[1] + "</p>");
//                    out.print("</div>");
//
//                } else {
//
//                }
//                out.print("</div>");
//</editor-fold>
                out.print("<div class='card-body'>");
                if (code.contains("-014")) {
                    //<editor-fold defaultstate="collapsed" desc="R-TI-014">
                    if (lst_appDetail != null) {
                        //<editor-fold defaultstate="collapsed" desc=" LIST R.TI.014">
                        out.print("<div id='accordion'>");
                        int iterator2 = 0;
                        for (int i = 0; i < lst_appDetail.size(); i++) {
                            //<editor-fold defaultstate="collapsed" desc="BUILD DOCUMENT">
                            Object[] Objdet = (Object[]) lst_appDetail.get(i);
                            String[] conect = Objdet[4].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                            String[] typeDoc = Objdet[3].toString().split("/");
                            String affair = conect[0].toString();
                            lst_format = FormatJpa.ConsultFormatId(idDoc);
                            int sigCount = 0;
                            int sigCatn = 0;
                            String signt = "";
                            String signttotal = "";
                            if (lst_format != null) {
                                Object[] ObjDoc = (Object[]) lst_format.get(0);
                                format = ObjDoc[3].toString();
                                format = format.replace("XXXDATEXXX", Objdet[2].toString());
                                format = format.replace("XXXAFFAIRXXX", affair);
                                format = format.replace("XXXCONTENTXXX", conect[1].toString());
                                int iterator = 1;

                                String[] people = Objdet[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                                for (int j = 0; j < people.length; j++) {
                                    String newPers = "", advice = "";
                                    int signa = 0;
                                    String[] detail = people[j].split(" / ");
                                    try {
                                        signa = Integer.parseInt(detail[4].toString().trim());
                                    } catch (Exception e) {
                                        signa = 0;
                                    }
                                    if (signa > 0) {
                                        lst_sirh = SirhJpa.Consultar_firmas(signa);
                                        if (lst_sirh.size() > 0) {
                                            String[] ObjSig = lst_sirh.toString().split("///");
                                            Gson gson = new Gson();
                                            signt = gson.toJson(ObjSig[3]);

                                            int canvasWidth = 130; // ancho del nuevo canvas
                                            int canvasHeight = 45;  // alto del nuevo canvas

                                            int originalWidth = 500;  // ancho original del canvas
                                            int originalHeight = 250; // alto original del canvas

                                            double scaleX = (double) canvasWidth / originalWidth;  // Factor de escala horizontal
                                            double scaleY = (double) canvasHeight / originalHeight; // Factor de escala vertical

                                            double offsetX = 0;
                                            double offsetY = 0;

                                            advice = "<canvas id='signature-canvas-" + iterator2 + j + "' width='" + canvasWidth + "' height='" + canvasHeight + "' style='border:1px solid #fff;'></canvas>"
                                                    + "<script>"
                                                    + "function dibujarCoordenadas_" + iterator2 + j + "() { "
                                                    + "   const canvas = document.getElementById('signature-canvas-" + iterator2 + j + "'); "
                                                    + "   const ctx = canvas.getContext('2d'); "
                                                    + "   const coordenadas = JSON.parse(" + signt + "); "
                                                    + "   ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                                    + "   coordenadas.forEach(coord => { "
                                                    + "       const scaledLX = (coord.lx * " + scaleX + ") + " + offsetX + ";"
                                                    + "       const scaledLY = (coord.ly * " + scaleY + ") + " + offsetY + ";"
                                                    + "       const scaledMX = (coord.mx * " + scaleX + ") + " + offsetX + ";"
                                                    + "       const scaledMY = (coord.my * " + scaleY + ") + " + offsetY + ";"
                                                    + "       ctx.beginPath(); "
                                                    + "       ctx.moveTo(scaledLX, scaledLY); "
                                                    + "       ctx.lineTo(scaledMX, scaledMY); "
                                                    + "       ctx.strokeStyle = 'black'; "
                                                    + "       ctx.lineWidth = 0.6; "
                                                    + "       ctx.stroke(); "
                                                    + "   }); "
                                                    + "} "
                                                    + "dibujarCoordenadas_" + iterator2 + j + "();"
                                                    + "</script>";
                                        }

                                    } else {
                                        advice = "Sin firma";
                                        sigCount++;
                                    }
                                    newPers = "<tr>"
                                            + "<td colspan='4' class='text-center'>" + detail[0] + "</td>"
                                            + "<td colspan='1' class='text-center'>" + detail[1] + "</td>"
                                            + "<td colspan='2' class='text-center'>" + advice + "</td>"
                                            + "</tr>";
                                    if (j != people.length - 1) {
                                        newPers = newPers + "<tr>XXXPERSONALASIGXXX</tr>";
                                    }
                                    format = format.replace("<tr>XXXPERSONALASIGXXX</tr>", newPers);
                                    iterator++;
                                }
                                format = format.replace("XXCOLXX", "" + iterator + "");
                                format = format.replace("XXXMADEBYXXX", "" + Objdet[9].toString() + "");

                            }

                            //</editor-fold>
                            out.print("<div class='accordion'>");
                            out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-" + i + "\"  " + ((i == 0) ? "aria-expanded=\"true\"" : "") + " style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                            out.print("<div class='d-flex' style='justify-content: space-between;'><h4 class=''>" + Objdet[2].toString() + " - " + affair + "</h4>");
                            int countSig = 0;
                            String[] dataUser = Objdet[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                            for (int j = 0; j < dataUser.length; j++) {
                                String[] dataSign = dataUser[j].toString().split(" / ");
                                if (dataSign[4].toString().equals("XX")) {
                                    countSig++;
                                }
                            }
                            if (countSig > 0) {
                                out.print("<h4 class='text-warning'>" + countSig + " " + ((countSig == 1) ? "firma" : "firmas") + " pendientes <i class=\"fas fa-exclamation-circle\"></i> </h4>");
                            } else {
                                out.print("<h4 class='text-info'>Documento Firmado</h4>");
                            }

                            out.print("</div>");
                            out.print("</div>");
                            out.print("<div class='accordion-body collapse " + ((i == 0) ? "show" : "") + "' id=\"panel-body-" + i + "\" data-parent=\"#accordion\">");
                            //<editor-fold defaultstate="collapsed" desc="HEAD AND BUTTONS">                        
                            out.print("<div class='d-flex' style='justify-content: space-between; align-items: baseline;'>");
                            out.print("<div class=''>");
                            out.print("<h6>Contenido del documento</h6>");
                            out.print("</div>");
                            out.print("<div class='d-flex mb-2'>");

                            if (stetx != 99) {
                                if (singExits.contains("XX")) {
                                    out.print("<button class='btn btn-info mr-2' data-toggle='tooltip' data-placement='top' title='Firmar' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&idDet=" + Objdet[0] + "&swpt=2&step=" + step + "\"'><i class=\"fas fa-signature\"></i></button>");
                                }
                            }

                            if (sigCount == 0) {
                                out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px; opacity: 0.6;' disabled data-toggle='tooltip' data-placement='top' title='El documento ya tiene al menos una firma'><i class='fas fa-edit'></i></button>");
                            } else {
                                out.print("<button class='btn btn-warning mr-2' data-toggle='tooltip' data-placement='top' title='Editar' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&idDet=" + Objdet[0] + "&step=" + step + "\"'><i class=\"fas fa-edit\"></i></button>");
                            }
                            out.print("</div>");
                            //</editor-fold>                        
                            out.print("</div>");
                            out.print(format);
                            out.print("</div>");

                            out.print("</div>");
                            iterator2++;
                        }
                        out.print("</div>");
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc=" NEW  R.TI.014">
                        out.print("<div id='accordion'>");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-96\" aria-expanded=\"true\">");
                        out.print("<h4>Nueva acta</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse show\" id=\"panel-body-96\" data-parent=\"#accordion\">");
                        out.print("<div class='d-flex' style='justify-content: space-between; align-items: baseline;'>");
                        out.print("<div class=''>");
                        out.print("<h6>Contenido del documento</h6>");
                        out.print("</div>");
                        out.print("<div class='mb-2'>");
                        out.print("<button class='btn btn-green mr-2' data-toggle='tooltip' data-placement='top' title='Datos de la acta' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&swpt=1&idHead=" + idHead + "&type=" + type + "&step=" + step + "\"'><i class=\"fas fa-file-alt\"></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        format = format.replace("XXXPERSONALASIGXXX", "").replace("XXXDATEXXX", "").replace("XXXAFFAIRXXX", "").replace("XXXCONTENTXXX", "").replace("XXXMADEBYXXX", "");
                        out.print(format);
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                    //</editor-fold>
                } else if (code.contains("A")) {
                    //<editor-fold defaultstate="collapsed" desc="ANNEXES">
                    if (lst_appDetail != null) {
                        Object[] ObjDe = (Object[]) lst_appDetail.get(0);
                        out.print("<form action='AppDetail?opt=5&idDet=" + ObjDe[0] + "&idHead=" + idHead + "&idApp=" + idApp + "&idDoc=" + idDoc + "&type=" + type + "&step=" + step + "' method='post' class=''>");
                        out.print("<textarea id='editorCK' name='txtProtoData' class='form-control'>" + ObjDe[4] + "</textarea>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green mt-2'>Actualizar</button>");
                        out.print("</div>");
                        out.print("</form>");
                    } else {
                        out.print("<form action='AppDetail?opt=5&idHead=" + idHead + "&idApp=" + idApp + "&idDoc=" + idDoc + "&type=" + type + "&step=" + step + "' method='post' class=''>");
                        out.print("<textarea id='editorCK' name='txtProtoData' class='form-control' required></textarea>");
                        out.print("<div class='text-center'>");
                        out.print("<button class='btn btn-green mt-2'>Registrar</button>");
                        out.print("</div>");
                        out.print("</form>");
                    }
                    //</editor-fold>
                } else if (code.contains("-033")) {
                    //<editor-fold defaultstate="collapsed" desc="R-TI-033">
                    lst_apph = AppheadJpa.ConsultHeadbyIdApp(idApp);
                    if (lst_apph != null) {
                        Object[] Objapph = (Object[]) lst_apph.get(0);
                        try {
                            String[] typeVer = Objapph[5].toString().replace(" ", "///").split("///");
                            String[] vers = typeVer[1].toString().replace(".", "///").split("///");
                            format = format.replace("XXXNAMEAPPXXX", Objapph[0].toString()).replace("XXXTIPEVERXXX", typeVer[0]).replace("XXXPOSEXXXX", vers[0]).replace("XXXPOSEYXXX", vers[1]).replace("XXXPOSEJXXX", vers[2]);
                        } catch (Exception e) {
                            format = format.replace("XXXNAMEAPPXXX", Objapph[0].toString()).replace("XXXTIPEVERXXX", "-").replace("XXXPOSEXXXX", "0").replace("XXXPOSEYXXX", "0").replace("XXXPOSEJXXX", "0");
                        }
                    } else {
                        format = format.replace("XXXNAMEAPPXXX", type).replace("XXXTIPEVERXXX", "").replace("XXXPOSEXXXX", "").replace("XXXPOSEYXXX", "").replace("XXXPOSEJXXX", "");
                    }
                    if (lst_appDetail != null) {
                        Object[] ObjAp = (Object[]) lst_appDetail.get(0);
                        String[] conten = ObjAp[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        //<editor-fold defaultstate="collapsed" desc="SIGNATURES">
                        int canvasWidth = 200; // ancho del nuevo canvas
                        int canvasHeight = 70;  // alto del nuevo canvas

                        int originalWidth = 500;  // ancho original del canvas
                        int originalHeight = 250; // alto original del canvas

                        double scaleX = (double) canvasWidth / originalWidth;  // Factor de escala horizontal
                        double scaleY = (double) canvasHeight / originalHeight; // Factor de escala vertical

                        double offsetX = 0;
                        double offsetY = 0;
                        String Signatur = "";
                        String sigSol = "", sigAprb = "", sigEject = "", sigResp = "", sigAprb_bm = "", sigEject_bm = "";
                        String[] personal = ObjAp[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        boolean thrSing = true;
                        for (int i = 0; i < personal.length; i++) {
                            String[] data = personal[i].toString().split(" / ");
                            String tempCarg = data[1].toString().trim();
                            String sig_user = data[4].toString().trim();
                            if (!sig_user.equals("XX")) {
                                int idSigz = Integer.parseInt(sig_user.toString());
                                lst_sirh = SirhJpa.Consultar_firmas(idSigz);
                                if (lst_sirh != null) {
                                    String[] DataSig = lst_sirh.toString().split("///");
                                    Gson gson = new Gson();
                                    String signt = gson.toJson(DataSig[3]);
                                    Signatur = "<div style='max-width: 200px;'><canvas id='canva" + idSigz + "' width='" + canvasWidth + "' height='" + canvasHeight + "' style='border:1px solid #fff;'></canvas>"
                                            + "<script>"
                                            + "function drawSign" + idSigz + "() { "
                                            + "   const canvas = document.getElementById('canva" + idSigz + "'); "
                                            + "   const ctx = canvas.getContext('2d'); "
                                            + "   const coordenadas = JSON.parse(" + signt + "); "
                                            + "   ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                            + "   coordenadas.forEach(coord => { "
                                            + "       const scaledLX = (coord.lx * " + scaleX + ") + " + offsetX + ";"
                                            + "       const scaledLY = (coord.ly * " + scaleY + ") + " + offsetY + ";"
                                            + "       const scaledMX = (coord.mx * " + scaleX + ") + " + offsetX + ";"
                                            + "       const scaledMY = (coord.my * " + scaleY + ") + " + offsetY + ";"
                                            + "       ctx.beginPath(); "
                                            + "       ctx.moveTo(scaledLX, scaledLY); "
                                            + "       ctx.lineTo(scaledMX, scaledMY); "
                                            + "       ctx.strokeStyle = 'black'; "
                                            + "       ctx.lineWidth = 0.6; "
                                            + "       ctx.stroke(); "
                                            + "   }); "
                                            + "} "
                                            + " drawSign" + idSigz + "();"
                                            + "</script></div>";

                                    if (tempCarg.contains("APROB")) {
                                        sigAprb = Signatur;
                                        sigAprb_bm = Signatur.toString().replace("drawSign" + idSigz + "", "drawSign" + idSigz + "x").replace("canva" + idSigz + "", "canva" + idSigz + "x");
                                    } else if (tempCarg.contains("EJECT")) {
                                        sigEject = Signatur;
                                        sigEject_bm = Signatur.toString().replace("drawSign" + idSigz + "", "drawSign" + idSigz + "x").replace("canva" + idSigz + "", "canva" + idSigz + "x");
                                    } else if (tempCarg.contains("RESPONSABLE")) {
                                        sigResp = Signatur;
                                    } else {
                                        sigSol += Signatur;
                                    }
                                }
                                thrSing = false;
                            } else {
                                if (tempCarg.contains("APROB")) {
                                    sigAprb = "- Sin Firma -";
                                    sigAprb_bm = "- Sin Firma -";
                                } else if (tempCarg.contains("EJECT")) {
                                    sigEject = "- Sin Firma -";
                                    sigEject_bm = "- Sin Firma -";
                                } else if (tempCarg.contains("RESPONSABLE")) {
                                    sigResp = "- Sin Firma -";
                                } else {
                                    sigSol = "- Sin Firma -";
                                }
                            }
                        }
                        //</editor-fold>
                        out.print("<div class='mb-2 d-flex' style='justify-content: end;'>");

                        if (stetx != 99) {
                            if (singExits.contains("XX")) {
                                out.print("<button class='btn btn-info mr-2' data-toggle='tooltip' data-placement='top' title='Firmar' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&idDet=" + ObjAp[0] + "&swpt=2&step=" + step + "\"'><i class=\"fas fa-signature\"></i></button>");
                            }
                        }
                        if (stetx == 1) {
                            out.print("<button class='btn btn-warning' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&idHead=" + idHead + "&type=" + type + "&idDet=" + ObjAp[0] + "&step=" + step + "\"'><i class='fas fa-edit'></i></button>");
                        }
                        out.print("</div>");
                        //<editor-fold defaultstate="collapsed" desc="BUILD DOCUMENT">
                        format = format.replace("XXXAREASUXXX", conten[0]);
                        format = format.replace("XXXROLESXXX", conten[1]);
                        try {
                            String[] requirem = conten[2].split("---");
                            for (int i = 0; i < requirem.length; i++) {
                                String[] detail = requirem[i].toString().split("/");
                                String allRequ = "<tr> "
                                        + "   <td class='text-center'>" + (i + 1) + "</td> "
                                        + "   <td class='text-center' colspan='3'>" + detail[0] + "</td> "
                                        + "   <td class='text-center'>" + detail[1] + "</td> "
                                        + "   <td class='text-center'>" + detail[2] + "</td> "
                                        + "   <td class='text-center'>" + detail[3] + "</td> "
                                        + "   <td class='text-center'>" + detail[4] + "</td> "
                                        + "   <td class='text-center'>" + detail[5] + "</td> "
                                        + "   <td class='text-center'>" + detail[6] + "</td> "
                                        + "</tr>";
                                if (i != requirem.length - 1) {
                                    allRequ = allRequ + "<tr>XXXREQURIMENTS</tr>";
                                }
                                format = format.replace("<tr>XXXREQURIMENTS</tr>", allRequ);
                            }
                        } catch (Exception e) {
                            format = format.replace("<tr>XXXREQURIMENTS</tr>", "<tr> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center' colspan='3'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "   <td class='text-center'>&nbsp;&nbsp;&nbsp;</td> "
                                    + "</tr>");
                        }
                        format = format.replace("XXXDEVELOPERXXX", conten[3].split(" / ")[0]);
                        String[] vers = conten[6].toString().replace(" ", "///").split("///");
                        String[] nros = vers[1].replace(".", "///").split("///");
                        format = format.replace("XXXFINXXXX", nros[0]).replace("XXXFINYXXX", nros[1]).replace("XXXFINJXXX", nros[2]).replace("XXXTYPOXXX", vers[0]);

                        format = format.replace("XXXSOLICXXX", sigSol).replace("XXXAPROBXXX", sigAprb)
                                .replace("XXXEJECTXXX", sigEject).replace("XXXRESPONSEXXX", sigResp)
                                .replace("XXXDEVELOPER_BMXXX", sigEject_bm).replace("XXXAPROB_BMXXX", sigAprb_bm);

                        lst_appDetail = AppDetail.ConsultDocumentTypeRpt(idHead, "Presentación prototipo");
                        if (lst_appDetail != null) {
                            Object[] ObjDetailx = (Object[]) lst_appDetail.get(0);
                            format = format.replace("XXXPRESENTATIONXXX", ObjDetailx[2].toString());
                        } else {
                            format = format.replace("XXXPRESENTATIONXXX", "-");
                        }
                        lst_appDetail = AppDetail.ConsultDocumentTypeRpt(idHead, "Verificación");
                        if (lst_appDetail != null) {
                            Object[] ObjDetailx = (Object[]) lst_appDetail.get(0);
                            format = format.replace("XXXVERIFICADOXXX", ObjDetailx[2].toString());
                        } else {
                            format = format.replace("XXXVERIFICADOXXX", "-");
                        }
                        lst_appDetail = AppDetail.ConsultDocumentTypeRpt(idHead, "XXXXX");
                        if (lst_appDetail != null) {
                            Object[] ObjDetailx = (Object[]) lst_appDetail.get(0);
                            format = format.replace("XXXIQOXXX", ObjDetailx[2].toString());
                        } else {
                            format = format.replace("XXXIQOXXX", "-");
                        }
                        lst_appDetail = AppDetail.ConsultDocumentTypeRpt(idHead, "Validacion");
                        if (lst_appDetail != null) {
                            Object[] ObjDetailx = (Object[]) lst_appDetail.get(0);
                            format = format.replace("XXXVALIDATIONOXXX", ObjDetailx[2].toString());
                        } else {
                            format = format.replace("XXXVALIDATIONOXXX", "-");
                        }
                        format = format.replace("XXXCAPACITAOXXX", conten[4].toString());
                        format = format.replace("XXXOBSXXX", conten[5].toString());
                        lst_appDetail = AppDetail.ConsultDocumentTypeRpt(idHead, "Lanzamiento");
                        if (lst_appDetail != null) {
                            Object[] ObjDetailx = (Object[]) lst_appDetail.get(0);
                            format = format.replace("XXXDATELANZAXXX", ObjDetailx[2].toString());
                        } else {
                            format = format.replace("XXXDATELANZAXXX", "-");
                        }
                        format = format.replace("XXXFINALVERSIONXXX", conten[6].toString());
                        format = format.replace("XXXNEWROLSXXX", conten[7].toString());
                        format = format.replace("XXXNEWAREASXXX", conten[8].toString());
                        format = format.replace("XXXCOBIANXXX", conten[9].toString());
                        format = format.replace("XXXFILESXXX", conten[9].toString());
                        //</editor-fold>
                        out.print("<div class='tbFormat'>");
                        out.print(format);
                        out.print("</div>");
                    } else {
                        format = format.replace("XXXNAMEAPPXXX", "").replace("XXXAREASUXXX", "")
                                .replace("XXXROLESXXX", "").replace("XXXTIPEVERXXX", "").replace("XXXPOSEXXXX", "")
                                .replace("XXXPOSEYXXX", "").replace("XXXPOSEJXXX", "").replace("XXXREQURIMENTS", "")
                                .replace("XXXDEVELOPERXXX", "").replace("XXXFINXXXX", "").replace("XXXFINYXXX", "")
                                .replace("XXXFINJXXX", "").replace("XXXTYPOXXX", "").replace("XXXSOLICXXX", "")
                                .replace("XXXAPROBXXX", "").replace("XXXEJECTXXX", "").replace("XXXRESPONSEXXX", "").replace("XXXPRESENTATIONXXX", "")
                                .replace("XXXVERIFICADOXXX", "").replace("XXXIQOXXX", "").replace("XXXVALIDATIONOXXX", "").replace("XXXCAPACITAOXXX", "")
                                .replace("XXXOBSXXX", "").replace("XXXDATELANZAXXX", "").replace("XXXFINALVERSIONXXX", "").replace("XXXNEWROLSXXX", "")
                                .replace("XXXNEWAREASXXX", "").replace("XXXCOBIANXXX", "").replace("XXXFILESXXX", "").replace("XXXAPROBXXX", "")
                                .replace("XXXDEVELOPER_BMXXX", "").replace("XXXAPROB_BMXXX", "");
                        out.print("<div class='mb-2 d-flex' style='justify-content: end;'>");
                        out.print("<button class='btn btn-green' onclick='window.location.href=\"AppDetail?opt=1&mod=3&idDoc=" + idDoc + "&idApp=" + idApp + "&swpt=1&idHead=" + idHead + "&type=" + type + "\"'><i class='fas fa-file-alt'></i></button>");
                        out.print("</div>");
                        out.print("<div class=''>");
                        out.print(format);
                        out.print("</div>");
                    }
                    //</editor-fold>
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
                //</editor-fold>
            } else if (module == 2) {
                //<editor-fold defaultstate="collapsed" desc="APP DETAIL">
                int idHead = 0;
                try {
                    idHead = Integer.parseInt(pageContext.getRequest().getAttribute("idHead").toString());
                } catch (Exception e) {
                    idHead = 0;
                }
                String nameApp = "";
                lst_app = AppJpa.ConsultAppIdActive(idApp);
                if (lst_app != null) {
                    Object[] ObjApp = (Object[]) lst_app.get(0);
                    nameApp = ObjApp[1].toString();
                } else {
                    nameApp = "";
                }
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: center;'>");
                out.print("<div style='position: absolute;left: 26px;top: 32px;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"AppDetail?opt=1&mod=1&idApp=" + idApp + "\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<h4>Documentacion aplicativo</h4> <h1>" + nameApp + "</h1>");
                out.print("</div>");
                out.print("<p></p>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                lst_apph = AppheadJpa.ConsultHeadbyIdApp(idApp);
                if (lst_apph != null) {
                    Object[] ObjApp = (Object[]) lst_apph.get(0);
                    state = Integer.parseInt(ObjApp[4].toString());
                    structure = ObjApp[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                    out.print("<div class='card'>");
                    out.print("<div class=\"row mt-4\" style='width: 100%; justify-content: center;'>");
                    out.print("<div class=\"col-12\">");
                    out.print("<div class=\"wizard-steps\" style='display: flex; flex-wrap: wrap; justify-content: center;'>");

                    for (int i = 1; i < structure.length; i++) {
                        String[] idxNamexIco = structure[i].toString().split("/");
                        int id = 0;
                        String name = idxNamexIco[1].toString();
                        String ico = idxNamexIco[2].toString();
                        if (!idxNamexIco[0].toString().equals("A")) {
                            id = Integer.parseInt(idxNamexIco[0].toString());
                        }
                        if (i == state) {
                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp=" + idApp + "&idDoc=" + id + "&idHead=" + idHead + "&type=" + structure[i] + "&step=" + i + "\"' style='background: #33bf98; color:#0b0025; cursor: pointer;' data-toggle='tooltip' data-placement='top' title='En proceso'>");
                            out.print("<div class=\"wizard-step-icon\">");
                            out.print("<i class=\"" + ico + "\"></i>");
                            out.print("</div>");
                            out.print("<div class=\"wizard-step-label\">");
                            out.print(name);
                            out.print("<div style='position: absolute;bottom: 2px;left: 47%;'>");
                            out.print("<p style='margin: 0;'><i class=\"fas fa-spinner fa-spin\"></i></p>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        } else if (i > state) {
                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" style='opacity: 0.5;background: #0b002599; cursor: no-drop;' data-toggle='tooltip' data-placement='top' title='Aún no disponible'>");
                            out.print("<div class=\"wizard-step-icon\">");
                            out.print("<i class=\"" + ico + "\"></i>");
                            out.print("</div>");
                            out.print("<div class=\"wizard-step-label\">");
                            out.print(name);
                            out.print("<div style='position: absolute;bottom: 2px;left: 24%;'>");
                            out.print("<p style='margin: 0;'>-&nbsp;Pendiente&nbsp;-</p>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                        } else {
                            lst_appDetail = AppDetail.ConsultDocumentTypeRpt(idHead, name);
                            if (lst_appDetail != null) {
                                Object[] ObSte = (Object[]) lst_appDetail.get(0);
                                if (ObSte[5] != null) {
                                    if (ObSte[5].toString().contains("XX")) {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp=" + idApp + "&idDoc=" + id + "&idHead=" + idHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class=\"" + ico + "\"></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print(name);
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #ffa426;border-radius: 3px;'><b>Pendiente Firma</b> &nbsp; <i class='fas fa-signature'></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    }else{
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp=" + idApp + "&idDoc=" + id + "&idHead=" + idHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class=\"" + ico + "\"></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print(name);
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #33bf98;border-radius: 3px;'><b>Realizado</b> &nbsp; <i class=\"fas fa-check\"></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    }
                                } else {
                                    int steDet = Integer.parseInt(ObSte[6].toString());
                                    if (steDet == 0) {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp=" + idApp + "&idDoc=" + id + "&idHead=" + idHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class=\"" + ico + "\"></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print(name);
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #ffa426;border-radius: 3px;'><b><b class='text-warning'>Pendiente Firma</b></b> &nbsp; <i class='fas fa-signature'></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    } else if (steDet == 2) {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp=" + idApp + "&idDoc=" + id + "&idHead=" + idHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class=\"" + ico + "\"></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print(name);
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #33bf98;border-radius: 3px;'><b>Realizado</b> &nbsp; <i class=\"fas fa-check\"></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    } else {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step-icon\">");
                                        out.print("<i class='<i class=\"fas fa-exclamation-triangle\"></i>'></i>");
                                        out.print("</div>");
                                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                        out.print("Error");
                                        out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                        out.print("<p style='margin: 0; width: 170px; background: #33bf98;border-radius: 3px;'><b>Error</b> &nbsp; <i class=\"fas fa-check\"></i></p>");
                                        out.print("</div>");
                                        out.print("</div>");
                                        out.print("</div>");
                                    }
                                }

                            }
                        }
                    }

//                    if (state == structure.length) {
//                        out.print("<div class='fieldDiv'>");
//                        out.print("<div class=\"circleDiv\" onclick='window.location.href=\"AppDetail?opt=8&mod=3&idApp=" + idApp + "&idHead=" + idHead + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
//                        out.print("<div class=\"wizard-step-icon\">");
//                        out.print("</div>");
//                        out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'><br>");
//                        out.print("<i style='font-size: 27px;' class=\"fas fa-flag-checkered\"></i><br>");
//                        out.print("<span style='font-size: 12px;'>FINALIZAR</span>");
//                        out.print("</div>");
//                        out.print("</div>");
//                        out.print("</div>");
//                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                } else {
                    out.print("<div class=''>");
                    out.print("<h4>Se ha presentado un error al cargar la información de los documentos.</h4>");
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
            } else if (module == 1) {
                //<editor-fold defaultstate="collapsed" desc="APP VERSIONS">
                String nameApp = "";
                lst_app = AppJpa.ConsultAppIdActive(idApp);
                if (lst_app != null) {
                    Object[] ObjApp = (Object[]) lst_app.get(0);
                    nameApp = ObjApp[1].toString();
                } else {
                    nameApp = "";
                }

                //<editor-fold defaultstate="collapsed" desc="NEW VERSION">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar versión</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='AppDetail?opt=3&idApp=" + idApp + "&mod=1' method='post' class=''>");

                out.print("<div class='text-center'>");
                out.print("<span class=''>Seleccionar tipo de aplicativo</span>");
                out.print("<div class='mt-4'>");
                lst_setting = SettingJpa.ConsultSettingCategorie("AppType");
                out.print("<select class='form-control col-lg-8' name='CbxApp' style='margin: auto;' required>");
                out.print("<option value='' disabled selected >Seleccionar un tipo</option>");
                if (lst_setting != null) {
                    for (int i = 0; i < lst_setting.size(); i++) {
                        Object[] ObjAp = (Object[]) lst_setting.get(i);
                        String struc = ObjAp[2].toString();
                        out.print("<option value='" + struc + "'>" + struc.replace("][", "///").replace("[", "").replace("]", "").split("///")[0] + "</option>");
                    }

                } else {
                    out.print("<option value='' disabled>Ha ocurrido un error.</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='text-center mt-4'>");
                out.print("<button class='btn btn-green'>Registrar</button>");
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

                out.print("<div class='d-flex'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"AppDetail?opt=1\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("</div>");
                out.print("<div class='text-center'>");
                out.print("<h4>Aplicativo</h4> <h1>" + nameApp + "</h1>");
                out.print("</div>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                lst_apph = AppheadJpa.ConsultHeadbyIdApp(idApp);
                if (lst_apph != null) {
                    out.print("<table class='table table-bordered' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Tipo</th>");
                    out.print("<th>Version</th>");
                    out.print("<th>Usuario registro</th>");
                    out.print("<th>Fecha registro</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th class='text-center'>OPC</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_apph.size(); i++) {
                        Object[] ObjHead = (Object[]) lst_apph.get(i);
                        out.print("<tr>");
                        int sta = Integer.parseInt(ObjHead[4].toString());
                        String struc = ObjHead[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0];
                        out.print("<td>" + struc + "</td>");
                        out.print("<td class='text-center'>" + ((ObjHead[5] == null) ? "Sin versión" : ObjHead[5]) + "</td>");
                        out.print("<td>" + ObjHead[8] + "</td>");
                        out.print("<td>" + ObjHead[9] + "</td>");
                        String[] procs = ObjHead[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        String[] process = {};
                        if (sta >= procs.length) {
                            out.print("<td>Documento Finalizado</td>");
                        } else {
                            process = procs[sta].split("/");
                            out.print("<td>" + process[1] + "</td>");
                        }
                        out.print("<td class=''>");
                        out.print("<div class='d-flex' style='justify-content: center;'>");
                        out.print("<button class='btn btn-yellow' onclick='window.location.href=\"AppDetail?opt=1&idApp=" + idApp + "&mod=2&idHead=" + ObjHead[1] + "\"'><i class='fas fa-folder-open'></i></button>");
                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                } else {
                    out.print("<div class='text-center'>");
                    out.print("<h6>No se han encontrado versiones de el aplicativo " + nameApp + ", para registrar la primera debe dar clic en el boton <i class='fas fa-plus'></i></h6>");
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
            } else if (module == 0) {
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST APPS">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<h4>Aplicativos PT</h4>");
//                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                lst_app = AppJpa.ConsultActiveAppV2();
                if (lst_app != null) {
                    out.print("<div class='row' style='width: 100%;'> ");
                    for (int i = 0; i < lst_app.size(); i++) {
                        Object[] ObjApp = (Object[]) lst_app.get(i);
                        out.print("<div class='col-12 col-md-6 col-lg-4'>");
                        out.print("<div class='card card-primary'>");
                        out.print("<div class='card-header'>");
                        out.print("<h4>" + ObjApp[1] + "</h4>");
                        out.print("</div>");
                        out.print("<div class='card-body' style='padding-bottom: 10px; padding-top: 10px;'>");
                        String owner = ObjApp[2].toString().replace("[", "").replace("]", "").split(" / ")[0];
                        out.print("<p class='textSquare'>Propietario <code>." + owner + "</code></p>");
                        out.print("<p class='textSquare'>Aplica Protocolo <code>." + ObjApp[3] + "</code></p>");
                        out.print("<p class='textSquare'>Version <code>." + ((ObjApp[4] == null) ? "Sin versión" : ObjApp[4]) + "</code></p>");
                        int ste = 0;
                        if (ObjApp[5] != null) {
                            ste = Integer.parseInt(ObjApp[5].toString());
                        } else {
                            ste = 0;
                        }
                        out.print("<p class='textSquare'>Estado <code>." + ((ObjApp[4] == null) ? "Sin estado" : ((ste < 9) ? "En proceso" : "Activo")) + "</code></p>");
                        out.print("<button class='btn btn-green btn-sm' style='position: absolute;right: 0;bottom: 0px;' onclick='window.location.href=\"AppDetail?opt=1&idApp=" + ObjApp[0] + "&mod=1\"'><i class='fas fa-arrow-right'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                } else {
                    out.print("<div class=''>");
                    out.print("<div class='text-center'>");
                    out.print("<h5>Se ha presentado un error al cargar la información de las aplicaciones</h5><br>");
                    out.print("<h6>Favor comunicarse con el administrador</h6>");
                    out.print("<i style='font-size: 40px;' class=\"fas fa-exclamation-circle\"></i><br>");
                    out.print("</div>");
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
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_appDetail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_appDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
