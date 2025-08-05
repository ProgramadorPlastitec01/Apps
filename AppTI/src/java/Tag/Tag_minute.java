package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.MinuteJpaController;
import Controller.UserControllerJpa;
import Controller.FormatControllerJpa;
import Controller.RoleControllerJpa;
import SQL.ConnectionsBd;
import com.google.gson.Gson;
import java.util.List;
import javax.servlet.http.HttpSession;

public class Tag_minute extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        HttpSession sesion = pageContext.getSession();
        MinuteJpaController MinuteJpa = new MinuteJpaController();
        ConnectionsBd SirhJpa = new ConnectionsBd();
        UserControllerJpa UserJpa = new UserControllerJpa();
        FormatControllerJpa formatJpa = new FormatControllerJpa();
        RoleControllerJpa RoleJpa = new RoleControllerJpa();
        List lst_minute = null;
        List lst_sirh = null;
        List lst_user = null;
        List lst_filter = null;
        List lst_format = null;
        int event = 0, idMinu = 0, idUserReg = 0, idState = 0, flt = 0, temp = 0;
        int docx = 0, codx = 0;
        String matter = "", staff = "", cont = "", date = "", content = "";
        int idRol = 0;
        String txtPermissions = "";
        List lst_role = null;
        try {
            idRol = Integer.parseInt(sesion.getAttribute("idRol").toString());
            lst_role = RoleJpa.ConsultRoleId(idRol);
            Object[] obj_permi = (Object[]) lst_role.get(0);
            txtPermissions = obj_permi[2].toString();
        } catch (Exception e) {
            idRol = 0;
            txtPermissions = "";
        }
        try {
            idMinu = Integer.parseInt(pageContext.getRequest().getAttribute("idMinu").toString());
        } catch (Exception e) {
            idMinu = 0;
        }
        try {
            flt = Integer.parseInt(pageContext.getRequest().getAttribute("flt").toString());
        } catch (Exception e) {
            flt = 0;
        }
        try {
            event = Integer.parseInt(pageContext.getRequest().getAttribute("event").toString());
        } catch (Exception e) {
            event = 0;
        }
        try {
            temp = Integer.parseInt(pageContext.getRequest().getAttribute("temp").toString());
        } catch (Exception e) {
            temp = 0;
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
            if (event == 1) {
                if (idMinu > 0) {
                    if (temp == 1) {
                        //<editor-fold defaultstate="collapsed" desc="LIST ASISTENTS">
                        //<editor-fold defaultstate="collapsed" desc="ASISTENTS">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 44%; right: 21%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Asistentes </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");

                        lst_minute = MinuteJpa.ConsultMinuteId(idMinu);
                        if (lst_minute != null) {
                            Object[] ObjPers = (Object[]) lst_minute.get(0);
                            String[] pers = ObjPers[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                                    out.print("<td class='text-center'><button class='btn btn-info' onclick='mostrarConvencion(6);ValidData(\"" + perste[2].trim() + "\", \"" + perste[3].trim() + "\");'><i class='fas fa-signature'></i></button></td>");
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
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana6' style='opacity: 1.03; display:none;'>");
                        out.print("<div class='contGeneral' style='width: 44%; right: 21%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Consultar firmas </h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(6)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                        out.print("</div>");
                        out.print("<div class='cont_form_user'>");
                        out.print("<form action='Minute?opt=1&idMinu=" + idMinu + "&event=1&temp=2' method='post' class=''>");
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
                    } else if (temp == 2) {
                        //<editor-fold defaultstate="collapsed" desc="SIGANTURE">
                        out.print("<div class='sweet-local' tabindex='-1' id='Ventana5' style='opacity: 1.03; display:block;'>");
                        out.print("<div class='contGeneral' style='width: 50%; right: 20%;'>");
                        out.print("<div style='display: flex; justify-content: space-between'>");
                        out.print("<h2>Firma</h2>");
                        out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(5)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
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

                            out.print("<form action='Minute?opt=3&idMinu=" + idMinu + "&event=1&temp=1' method='post' class=''>");
                            out.print("<input type='hidden' name='idSig' id='' value='" + ObjSig[0].toString().replace("[", "") + "'>");
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
                            out.print("<h5><b class='text-warning'>Firma no Encontrada &nbsp;</b> <i class='fas fa-exclamation-triangle' style='font-size: 20px;'></i></h5>");
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

                            out.print("<form action='Minute?opt=3&idMinu=" + idMinu + "&event=1&temp=1' method='post' class=''>");
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
                    }
                }
                //<editor-fold defaultstate="collapsed" desc="LOAD DOCUMENT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 75%;'>");
                out.print("<div class='mb-2' style='display: flex; justify-content: space-between'>");
                out.print("<button class='btn btn-green mr-4' id='btnImprimir' data-toggle='tooltip' data-placement='top' title='Imprimir'><i class=\"fas fa-print\"></i></button>");
                out.print("<h4>ACTA TECNOLOGIA DE INFORMACIÓN / R-TI-014 </h4>");
                out.print("<div class='d-flex' style='align-items: center;'>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' data-toggle='tooltip' data-placement='top' title='Cerrar' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                int validSing = 0;
                lst_minute = MinuteJpa.ConsultMinuteId(idMinu);
                if (lst_minute != null) {
                    Object[] ObjDoc = (Object[]) lst_minute.get(0);
                    int idDoc = Integer.parseInt(ObjDoc[4].toString().replace("][", "/-/-/").replace("]", "").replace("[", "").split("/-/-/")[0]);
                    String formater = "";
                    lst_format = formatJpa.ConsultFormatId(idDoc);
                    if (lst_format != null) {
                        Object[] Objfor = (Object[]) lst_format.get(0);
                        formater = Objfor[3].toString();
                    }
                    formater = formater.replace("XXXDATEXXX", ObjDoc[2].toString());
                    formater = formater.replace("XXXAFFAIRXXX", ObjDoc[1].toString());
                    String[] sdtr_data = ObjDoc[4].toString().replace("][", "/-/-/").replace("]", "").replace("[", "").split("/-/-/");
                    try {
                        formater = formater.replace("XXXCONTENTXXX", sdtr_data[1].toString());
                    } catch (Exception e) {
                        formater = formater.replace("XXXCONTENTXXX", "");
                    }
                    formater = formater.replace("XXXMADEBYXXX", ObjDoc[7].toString());

                    String[] people = ObjDoc[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                    int iterator2 = 0;
                    String signt = "";
                    int iterator = 1;

                    for (int i = 0; i < people.length; i++) {
                        String new_pers = "", advice = "";
                        int signa = 0;
                        String[] detail = people[i].split(" / ");
                        try {
                            signa = Integer.parseInt(detail[4].toString().trim());
                        } catch (Exception e) {
                            signa = 0;
                            validSing++;
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

                                advice = "<canvas id='signature-canvas-" + iterator2 + i + "' width='" + canvasWidth + "' height='" + canvasHeight + "' style='border:1px solid #fff;'></canvas>"
                                        + "<script>"
                                        + "function dibujarCoordenadas_" + iterator2 + i + "() { "
                                        + "   const canvas = document.getElementById('signature-canvas-" + iterator2 + i + "'); "
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
                                        + "dibujarCoordenadas_" + iterator2 + i + "();"
                                        + "</script>";
                            }

                        } else {
                            advice = "Sin firma";
                        }
                        new_pers = "<tr>"
                                + "<td colspan='4' class='text-center'>" + detail[0] + "</td>"
                                + "<td colspan='1' class='text-center'>" + detail[1] + "</td>"
                                + "<td colspan='2' class='text-center'>" + advice + "</td>"
                                + "</tr>";
                        if (i != people.length - 1) {
                            new_pers = new_pers + "<tr>XXXPERSONALASIGXXX</tr>";
                        }
                        formater = formater.replace("<tr>XXXPERSONALASIGXXX</tr>", new_pers);
                        iterator++;

                    }
                    formater = formater.replace("XXCOLXX", "" + iterator);

                    out.print("<div id='dataDocument' class='divDocumentContent'>");
                    out.print(formater);
                    out.print("</div>");

                } else {
                    out.print("<div class='text-center'>");
                    out.print("<h4>Se ha presentado un error al cargar la información.</h4>");
                    out.print("</div>");
                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DOCUMENT DETAIL">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");

                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Volver a actas' onclick='window.location.href=\"Minute?opt=1&idMinu=" + idMinu + "&temp=1\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2>Detalle del acta</h2>");
                out.print("<div class=''>");
                if (txtPermissions.contains("[67]")) {
                    if (validSing == 0) {
                        out.print("<button class='btn btn-info mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Acta firmada' onclick='window.location.href=\"Minute?opt=1&idMinu=" + idMinu + "&event=1&temp=1\"'><i class='fas fa-signature'></i></button>");
                    } else {
                        out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Faltan " + validSing + " firma(s)' onclick='window.location.href=\"Minute?opt=1&idMinu=" + idMinu + "&event=1&temp=1\"'><i class='fas fa-signature'></i></button>");
                    }
                }
                out.print("<button class='btn btn-green' style='border-radius: 4px;' data-toggle='tooltip' data-placement='top' title='Ver acta completa' onclick='mostrarConvencion(4)'><i class='fas fa-eye'></i></button>");
                out.print("</div>");

                out.print("</div>");
                out.print("<div class='card-body'>");

                if (lst_minute != null) {
                    Object[] ObjMin = (Object[]) lst_minute.get(0);
                    out.print("<div class='minuteDetail'>");
                    out.print("<div class='row'>");
                    out.print("<div class='col-lg-2'>");
                    out.print("<h6><b>Fecha:</b></h6>&nbsp;" + ObjMin[2] + "");
                    out.print("</div>");
                    out.print("<div class='col-lg-6'>");
                    out.print("<h6><b>Asunto:</b></h6>&nbsp;" + ObjMin[1] + "");
                    out.print("</div>");
                    out.print("<div class='col-lg-4 d-flex'>");
                    out.print("<h6><b>Usuario registro: &nbsp;</b></h6>");
                    out.print("<div class=''>");
                    out.print(ObjMin[7] + "<br>");
                    out.print("<i style='font-size: 10px;color: gray;'>" + ObjMin[8] + "</i>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<form action='Minute?opt=4&idMinu=" + ObjMin[0] + "&event=1' method='post' class='needs-validation' novalidate=''>");
                    out.print("<div class='mt-2'>");

                    int stae = Integer.parseInt(ObjMin[5].toString());
                    if (stae < 3) {
                        out.print("<div class='continueLine'>");
                        out.print("<h6>CONTENIDO DEL ACTA</h6>");
                        out.print("</div>");
                    } else {
                        out.print("<h6>CONTENIDO DEL ACTA</h6>");
                    }

                    int counter = 0;
                    String[] person = ObjMin[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    for (int i = 0; i < person.length; i++) {
                        String[] datil = person[i].split(" / ");
                        if (!datil[4].toString().equals("XX")) {
                            counter++;
                        }
                    }
                    String[] Str_data = ObjMin[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                    out.print("<input type='hidden' class='form-control' name='txtIdDoc' id='' title='' value='[" + Str_data[0] + "]'>");
                    out.print("<div id='textDataField' class='textDataField'>");
                    try {
                        if (counter > 0) {
                            out.print(Str_data[1]);
                        } else {
                            out.print("<textarea id='editorNext' class='form-control' name='txtCont'>" + Str_data[1] + "</textarea>");
                        }
                    } catch (Exception e) {
                        out.print("<textarea id='editorNext' class='form-control' name='txtCont'></textarea>");
                    }
                    out.print("</div>");
                    out.print("</div>");

                    out.print("<div style='position: fixed; right: 32px; bottom: 32px; z-index: 1;'>");
                    out.print("<button class='btn btn-green' " + ((counter > 0) ? "disabled data-toggle='tooltip' data-placement='top' title='Ya hay " + counter + " firma(s) no se puede modificar el contenido.'" : "") + " >Guardar &nbsp;<i class='fas fa-save'></i></button>");
                    out.print("</div>");

                    out.print("</form>");

                } else {
                    out.print("<h4>Ha ocurrido un problema al cargar la información del acta.</h4>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else if (event == 0) {
                //<editor-fold defaultstate="collapsed" desc="MAIN MODULE">
                if (idMinu > 0 && temp == 0) {
                    //<editor-fold defaultstate="collapsed" desc="UPDATE MINUTE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral' style='width: 70%; right: 70px;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Actualizar acta</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    lst_minute = MinuteJpa.ConsultMinuteId(idMinu);
                    if (lst_minute != null) {
                        Object[] ObjMin = (Object[]) lst_minute.get(0);
                        out.print("<form action='Minute?opt=2&idMinu=" + ObjMin[0] + "' method='post' id='formData'>");
                        out.print("<div id=\"accordion\">");
                        out.print("<div class='accordion'>");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-97\" aria-expanded=\"true\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                        out.print("<h4>Datos basicos</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse show\" id=\"panel-body-97\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="BASIC DATA">
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-4'>");
                        out.print("<span class=''>Fecha de registro</span>");
                        out.print("<input type='date' class='form-control' name='txtDate' id='idDateForm' value='" + ObjMin[2] + "' required>");
                        out.print("</div>");
                        out.print("<div class='col-lg-8'>");
                        out.print("<span class=''>Asunto</span>");
                        out.print("<input type='text' id='idAffair' class='form-control' placeholder='Asunto del acta' name='txtMatter' value='" + ObjMin[1] + "' required>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("<div class=\"accordion\">");
                        out.print("<div class=\"accordion-header\" role=\"button\" data-toggle=\"collapse\" data-target=\"#panel-body-96\" style='box-shadow: 0px 1px 4px 0px #a7a7a7;'>");
                        out.print("<h4>Asignar personal</h4>");
                        out.print("</div>");
                        out.print("<div class=\"accordion-body collapse\" id=\"panel-body-96\" data-parent=\"#accordion\">");
                        //<editor-fold defaultstate="collapsed" desc="PERSONAL">
                        out.print("<div class=''>");
                        lst_sirh = SirhJpa.Consultar_SIRHPersonal();
                        out.print("<div class='d-flex'>");
                        out.print("<div class='col-lg-6 mr-3 contSwtch' style='margin-left: -8px;' >");
                        //<editor-fold defaultstate="collapsed" desc="CONSULT PERSONAL">
                        String[] docPersonal = ObjMin[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
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
                        out.print("<input type=\"hidden\" id=\"assigned-person-ids\" name=\"txtStaff\" value='" + ObjMin[3] + "'>");
                        //</editor-fold>
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("<div class='mt-2 text-center'>");
                        out.print("<button class='btn btn-green'>Actualizar</button>");
                        out.print("</div>");
                        //</editor-fold>
                        out.print("</form>");
                    } else {
                        out.print("<div class='text-center'>");
                        out.print("<h4>Se ha presentado un error al cargar los datos del acta.</h4>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
//</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="REGISTER MINUTE">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 70%; right: 70px;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Registrar acta</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Minute?opt=2' method='post' id='formData'>");
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
                out.print("<input type='text' id='idAffair' class='form-control' placeholder='Asunto del acta' name='txtMatter' value='' required>");
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
                out.print("<input type=\"hidden\" id=\"assigned-person-ids\" name=\"txtStaff\">");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='mt-2 text-center'>");
                out.print("<button type='button' class='btn btn-green' onclick='validForm(\"idDateForm\",\"idAffair\",\"assigned-person-ids\")'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FILTER DATA CONTAINER">
                //<editor-fold defaultstate="collapsed" desc="FILTER DATA">
                try {
                    matter = pageContext.getRequest().getAttribute("f_matter").toString();
                } catch (Exception e) {
                    matter = "";
                }
                try {
                    staff = pageContext.getRequest().getAttribute("f_staff").toString();
                } catch (Exception e) {
                    staff = "";
                }
                try {
                    cont = pageContext.getRequest().getAttribute("f_content").toString();
                } catch (Exception e) {
                    cont = "";
                }
                try {
                    date = pageContext.getRequest().getAttribute("f_date").toString();
                } catch (Exception e) {
                    date = "";
                }
                try {
                    idUserReg = Integer.parseInt(pageContext.getRequest().getAttribute("f_idUserReg").toString());
                } catch (Exception e) {
                    idUserReg = 0;
                }
                try {
                    idState = Integer.parseInt(pageContext.getRequest().getAttribute("f_idState").toString());
                } catch (Exception e) {
                    idState = 0;
                }
                //</editor-fold>

                out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 60%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Filtro </h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<form action='Minute?opt=1&flt=1' method='post' class='needs-validation' novalidate=''>");

                out.print("<div class='card-body'>");
                out.print("<div class='row'>");

                out.print("<div class='col-lg-4'>");
                out.print("<span class='text-dark'><b>Asunto:</b></span>");
                out.print("<input type='text' class='form-control' name='txtMatter' id='' value='" + matter + "'>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class='text-dark'><b>Fecha: </b></span>");
                out.print("<input type='date' class='form-control' name='txtDate' id='' value='" + date + "'>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class='text-dark'><b>Personal:</b> </span>");
                out.print("<input type='text' class='form-control' name='txtStaff' id='' value='" + staff + "'>");

                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class='text-dark'><b>Contenido acta:</b> </span>");
                out.print("<input type='text' class='form-control' name='txtContent' id='' value='" + cont + "'>");
                out.print("</div>");
                out.print("<div class='col-lg-4'>");
                out.print("<span class='text-dark'><b>Usuario registro:</b> </span>");
                out.print("<div style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title=''>");
                out.print("<select class='form-control' name='cbxUser' style='margin-12px;'>");
                out.print("<option selected disabled>Seleccionar usuario</option>");
                lst_user = UserJpa.ConsultPersonalActive();
                if (lst_user != null) {
                    for (int i = 0; i < lst_user.size(); i++) {
                        Object[] ObjUser = (Object[]) lst_user.get(i);
                        out.print("<option value='" + ObjUser[0] + "'>" + ObjUser[2] + "</option>");
                    }
                } else {
                    out.print("<option disabled >Se ha presentado un error!</option>");
                }
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='col-lg-4'>");
                out.print("<span class='text-dark'><b>Estado:</b> </span>");
                out.print("<div style='margin-top: 12px;' data-toggle='tooltip' data-placement='top' title=''>");
                out.print("<select class='form-control' name='cbxState' style='margin-12px;'>");
                out.print("<option selected disabled value=''>Seleccionar estado</option>");
                out.print("<option value='1'>Creado</option>");
                out.print("<option value='2'>Pendiente firma</option>");
                out.print("<option value='3'>Finalizado</option>");
                out.print("</select>");
                out.print("</div>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");

                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Filtrar <i class='fas fa-search'></i></button>");
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
                out.print("<h4>Actas</h4>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-yellow mr-2' style='border-radius: 4px;' onclick='mostrarConvencion(3)' data-toggle='tooltip' data-placement='top' title='Filtro'><i class='fas fa-search'></i></button>");
                if (txtPermissions.contains("[65]")) {
                    out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)' data-toggle='tooltip' data-placement='top' title='Agregar una nueva acta'><i class='fas fa-plus'></i></button>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                try {
                    if (flt == 1) {
                        lst_minute = MinuteJpa.ConsultMinuteFilter(matter, date, staff, cont, idUserReg, idState);
                    } else {
                        lst_minute = MinuteJpa.ConsultMinute(idMinu);
                    }
                } catch (Exception e) {
                    lst_minute = MinuteJpa.ConsultMinute(idMinu);
                }

                if (lst_minute != null) {
                    out.print("<table class='table table-bordered' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr class='text-center'>");
                    out.print("<th>Id</th>");
                    out.print("<th>Asunto</th>");
                    out.print("<th>Fecha</th>");
                    out.print("<th>Estado</th>");
                    out.print("<th>Registro</th>");
                    out.print("<th>Modifico</th>");
                    out.print("<th>Opc</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    int counter = 0;
                    for (int i = 0; i < lst_minute.size(); i++) {
                        Object[] ObjMinu = (Object[]) lst_minute.get(i);
                        if (idMinu > 0 && i == 0) {
                            out.print("<tr style='background:#33bf9826;'>");
                            out.print("<td><i class='fas fa-clock' data-toggle='tooltip' data-placement='top' title='Abierto recientemente!'></i> &nbsp;" + ObjMinu[0] + "</td>");
                        } else {
                            out.print("<tr>");
                            out.print("<td>" + ObjMinu[0] + "</td>");
                        }
                        out.print("<td>" + ObjMinu[1] + "</td>");
                        out.print("<td>" + ObjMinu[2] + "</td>");
                        String persn = "";
                        String[] minu_pers = ObjMinu[3].toString().replace("][", "///").replace("]", "").replace("[", "").split("///");
                        for (int j = 0; j < minu_pers.length; j++) {
                            String[] data = minu_pers[j].toString().split(" / ");
                            String tempz = data[4].toString().trim();
                            if (tempz.equals("XX")) {
                                persn += "» " + data[0] + "- Sin firma ";
                            } else {
                                persn += "» " + data[0] + "- Firmado ";
                                counter++;
                            }
                        }
                        persn = "" + persn + "";

                        int ste = Integer.parseInt(ObjMinu[5].toString());

                        out.print("<td>" + ((ste == 1) ? "Creado" : (ste == 2) ? "Pendiente firma" : "Cerrado") + "</td>");

                        out.print("<td><span data-toggle='tooltip' data-placement='top' title='" + ObjMinu[8] + "'>" + ObjMinu[7] + "</span></td>");
                        if (ObjMinu[10] == null) {
                            out.print("<td><span data-toggle='tooltip' data-placement='top' title='No se ha modificado'><i>No se ha modificado</i></span></td>");
                        } else {
                            out.print("<td><span data-toggle='tooltip' data-placement='top' title='" + ObjMinu[11] + "' style=''>" + ObjMinu[10] + "</span></td>");
                        }
                        out.print("<td>");
                        out.print("<div class='d-flex' style='justify-content: center;'>");
                        out.print("<button class='btn btn-green mr-2' data-toggle='tooltip' data-placement='top' title='Ver acta' onclick='window.location.href=\"Minute?opt=1&idMinu=" + ObjMinu[0] + "&event=1\"'><i class='fas fa-eye'></i></button>");
                        if (counter > 0) {
                            out.print("<button class='btn btn-warning' data-toggle='tooltip' data-placement='top' title='No se puede editar, ya hay al menos una firma en el acta' disabled><i class='fas fa-edit'></i></button>");
                        } else {
                            if (txtPermissions.contains("[66]")) {
                                out.print("<button class='btn btn-warning'  onclick='window.location.href=\"Minute?opt=1&idMinu=" + ObjMinu[0] + "\"'><i class='fas fa-edit'></i></button>");
                            }
                        }

                        out.print("</div>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                } else {
                    out.print("<div class='text-center'>");
                    out.print("<h4>No se ha encontrado actas para mostrar!</h4>");
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
            }
        } catch (IOException ex) {
            Logger.getLogger(Tag_minute.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Tag_minute.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
