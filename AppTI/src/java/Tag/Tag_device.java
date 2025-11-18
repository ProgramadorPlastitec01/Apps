package Tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import Controller.DeviceJpaController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Controller.ItemJpaController;
import Controller.AreaControllerJpa;
import Controller.DeviceHeaderJpaController;
import Controller.TypeSecuenceJpaController;
import Controller.DeviceDetailJpaController;
import Controller.FormatControllerJpa;
import Controller.SettingControllerJpa;
import SQL.ConnectionsBd;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tag_device extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
        DeviceJpaController DeviceJpa = new DeviceJpaController();
        ItemJpaController ItemJpa = new ItemJpaController();
        AreaControllerJpa AreaJpa = new AreaControllerJpa();
        DeviceHeaderJpaController DeviceHead = new DeviceHeaderJpaController();
        DeviceDetailJpaController DeviceDetailJpa = new DeviceDetailJpaController();
        TypeSecuenceJpaController SecuenceJpa = new TypeSecuenceJpaController();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        ConnectionsBd ConnectJpa = new ConnectionsBd();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();

//        LocalDate fecha = LocalDate.now();
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        String CurrentDate = fecha.format(formato);
        
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String CurrentDate = fecha.format(formato);
        int currentDay = fecha.getDayOfMonth();
        int currentMonth = fecha.getMonthValue();
        int currentYear = fecha.getYear();

        List lst_device = null;
        List lst_deviceHead = null;
        List lst_typeDevice = null;
        List lst_item = null;
        List lst_area = null;
        List lst_secuence = null;
        List lst_DeviceDetail = null;
        List lst_format = null;
        List lst_connect = null;
        List lst_setting = null;

        int action = 0, idTypeDv = 0, steDv = 0, idDevice = 0, state = 0;
        String usuario = "", area = "", cargouser = "", nameUser = "";
        String nameDevice = "", typeDvName = "";
        String[] structure = {};
        try {
            action = Integer.parseInt(pageContext.getRequest().getAttribute("act").toString());
        } catch (Exception e) {
            action = 0;
        }
        try {
            idTypeDv = Integer.parseInt(pageContext.getRequest().getAttribute("idTypeDv").toString());
        } catch (Exception e) {
            idTypeDv = 0;
        }
        try {
            steDv = Integer.parseInt(pageContext.getRequest().getAttribute("steDv").toString());
        } catch (Exception e) {
            steDv = 0;
        }

        try {
            idDevice = Integer.parseInt(pageContext.getRequest().getAttribute("idDevice").toString());
        } catch (Exception e) {
            idDevice = 0;
        }

        boolean isActive = false;
        boolean hvInfo = false;

        lst_device = DeviceJpa.ConsultDevicexId(idDevice);
        if (lst_device != null) {
            Object[] ObjDvGen = (Object[]) lst_device.get(0);
            nameDevice = ObjDvGen[3].toString();
            typeDvName = ObjDvGen[14].toString();
        } else {
            nameDevice = "-x-x NO ID DEVICE x-x-";
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="CONSULT ASIGMENT INFO">
        lst_DeviceDetail = DeviceDetailJpa.ConsultDeviceLastAsigment(idDevice);
        if (lst_DeviceDetail != null) {
            Object[] ObjDetail = (Object[]) lst_DeviceDetail.get(0);
            String[] strc = ObjDetail[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
            cargouser = strc[2].toString();
            area = strc[3].toString();;
            nameUser = strc[6].toString();
            usuario = strc[9].toString();
            hvInfo = true;
        } else {
            hvInfo = false;
        }
        //</editor-fold 

        //</editor-fold>
        try {
            if (action == 4) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE DOCUMENT">

                //<editor-fold defaultstate="collapsed" desc="VARAIBLES">
                int idDoc = 0, idDeviceHead = 0, docx = 0, codx = 0, stet = 0, idItem = 0;
                String nameDoc = "", code = "", format = "", SigMode = "", type = "";
                try {
                    idDeviceHead = Integer.parseInt(pageContext.getRequest().getAttribute("idDeviceHead").toString());
                } catch (Exception e) {
                    idDeviceHead = 0;
                }
                try {
                    idDoc = Integer.parseInt(pageContext.getRequest().getAttribute("idDoc").toString());
                } catch (Exception e) {
                    idDoc = 0;
                }
                try {
                    idItem = Integer.parseInt(pageContext.getRequest().getAttribute("idItem").toString());
                } catch (Exception e) {
                    idItem = 0;
                }
                try {
                    docx = Integer.parseInt(pageContext.getRequest().getAttribute("NmbDoc").toString());
                } catch (Exception e) {
                    docx = 0;
                }
                try {
                    codx = Integer.parseInt(pageContext.getRequest().getAttribute("NmbCod").toString());
                } catch (Exception e) {
                    codx = 0;
                }
                try {
                    type = pageContext.getRequest().getAttribute("type").toString();
                } catch (Exception e) {
                    type = "";
                }
                try {
                    SigMode = pageContext.getRequest().getAttribute("SigMode").toString();
                } catch (Exception e) {
                    SigMode = "";
                }
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
                lst_deviceHead = DeviceHead.ConsultDeviceHeaderIdHead(idDeviceHead);
                if (lst_deviceHead != null) {
                    Object[] ObDvHead = (Object[]) lst_deviceHead.get(0);
                }

                String[] typeSc = type.toString().split("/");
                int idDocx = Integer.parseInt(typeSc[0].toString());
                lst_format = FormatJpa.ConsultFormatId(idDocx);
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

                String post_script = "";
                lst_DeviceDetail = DeviceDetailJpa.ConsultDeviceDetailxDvxType(idDeviceHead, typeSc[0] + "/" + typeSc[1]);
                Object[] ObjDetail = {};
                int stetx = 0, idDeviceDetail = 0;
                String singExits = "";

                try {
                    ObjDetail = (Object[]) lst_DeviceDetail.get(0);
                    idDeviceDetail = Integer.parseInt(ObjDetail[0].toString());
                    stetx = Integer.parseInt(ObjDetail[6].toString());
                    try {
                        singExits = ObjDetail[5].toString();
                    } catch (Exception e) {
                        singExits = "";
                    }
                    if (code.contains("-004") || code.contains("-013") || code.contains("-029") || code.contains("-031") || code.contains("-032")) {
                        format = ObjDetail[4].toString();
                    }
                    try {
                        String[] usrs = singExits.toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        for (int i = 0; i < usrs.length; i++) {
                            //<editor-fold defaultstate="collapsed" desc="BUILD SIGNATURE BY DOC">
                            String[] usrx = usrs[i].split("/");
                            String temId = usrx[1].toString();
                            if (temId.equals("XX")) {
                                format = format.replace("XXX" + usrx[0] + "XXX", "<b class='text-warning'>Firma " + usrx[0] + "</b>");
                            } else {
                                int idSigx = Integer.parseInt(temId.toString());
                                List lst_signa = ConnectJpa.Consultar_firmas(idSigx);
                                if (lst_signa != null) {
                                    String[] ObjSi = lst_signa.toString().split("///");
                                    if (code.contains("-004") || code.contains("-013") || code.contains("-029") || code.contains("-031") || code.contains("-032")) {
//                                      
                                        format = format.replace("Firma " + usrx[0] + "", "<canvas id='signaCanvas" + i + "' width='120' height='60' style='border: 1px solid #fff;'></canvas>");
                                    } else {
                                        format = format.replace("XXX" + usrx[0] + "XXX", "<canvas id='signaCanvas" + i + "' width='200' height='100' style='border: 1px solid #fff;'></canvas>");
                                    }

                                    String json = ObjSi[3].toString();
                                    out.print("<input type='hidden' class='form-control' name='' id='coor" + i + "' value='" + json + "'>");
                                    post_script += "<script>"
                                            + " function dibujarCoordenadas" + i + "() { "
                                            + "            const canvas = document.getElementById('signaCanvas" + i + "'); "
                                            + "            const ctx = canvas.getContext('2d'); "
                                            + "            const coordenadas = JSON.parse(document.getElementById('coor" + i + "').value); "
                                            + "            const escalaX = canvas.width / 400; "
                                            + "            const escalaY = canvas.height / 200; "
                                            + "            ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                            + "            coordenadas.forEach(coord => { "
                                            + "                ctx.beginPath(); "
                                            + "                ctx.moveTo(coord.lx * escalaX, coord.ly * escalaY); "
                                            + "                ctx.lineTo(coord.mx * escalaX, coord.my * escalaY); "
                                            + "                ctx.strokeStyle = 'black'; "
                                            + "                ctx.lineWidth = 2; "
                                            + "                ctx.stroke(); "
                                            + "            }); "
                                            + "        } "
                                            + " "
                                            + " window.addEventListener('load', dibujarCoordenadas" + i + "); "
                                            + "</script>";
                                } else {
                                    format = format.replace("XXX" + usrx[1] + "XXX", "<b class='text-warning'>Firma " + usrx[1] + "</b>");
                                }
                            }
                            //</editor-fold>
                        }
                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                    stetx = 99;
                    idDeviceDetail = 0;
                }

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="FORM SIGNATURES">
                String[] Signatures = {};
                boolean useSign = false;
                try {
                    if (!ObjDetail[5].equals("")) {
                        Signatures = ObjDetail[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        useSign = true;
                    }
                } catch (Exception e) {
//                    Signatures = "";
                    useSign = false;
                }

                if (useSign) {
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana3' style='opacity: 1.03; display:none;'>");
                    out.print("<div class='contGeneral' style='width: 44%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");

                    out.print("<div class=''>");
                    out.print("<h2>Firmar</h2>");
                    out.print(" <h5>" + nameDoc + "</h5>");
                    out.print("</div>");

                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(3)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");

                    //<editor-fold defaultstate="collapsed" desc="LIST OF PERSONAL">
                    out.print("<table class='table table-bordered table-sm text-center' id='table-1'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    out.print("<th>Responsable</th>");
                    out.print("<th>Firmar</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < Signatures.length; i++) {
                        String[] SegDet = Signatures[i].toString().split("/");
                        out.print("<tr>");
                        out.print("<td>" + SegDet[0] + "</td>");
                        if (!SegDet[1].toString().equals("XX")) {
                            out.print("<td>Firmado</td>");
                        } else {
                            out.print("<td><button class='btn btn-green' onclick='sigMode(\"" + SegDet[0] + "\"); mostrarConvencion(4)'><i class='fas fa-signature'></i></button></td>");
                        }
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                    //</editor-fold>

                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana4' style='opacity: 1.03; display:" + ((docx > 0) ? "block" : "none") + ";'>");
                    out.print("<div class='contGeneral' style='width: 38%;'>");
                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h2>Firmar</h2>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(4)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");
                    out.print("<div class='cont_form_user'>");
                    try {
                        if (docx > 0) {
                            //<editor-fold defaultstate="collapsed" desc="LOAD SIGNATURE">
                            out.print("<form action='Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&type=" + type + "&idDeviceHead=" + idDeviceHead + "&idDevice=" + idDevice + "' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");
                            out.print("<div class='d-flex'>");
                            out.print("<div class='col-lg-5 mr-2'>");
                            out.print("<span class=''>Documento: </span>");
                            out.print("<input type='text' class='form-control' name='NmbDoc' id='' data-toggle='tooltip' data-placement='top' title='' value='" + docx + "'>");
                            out.print("</div>");

                            out.print("<div class='col-lg-5'>");
                            out.print("<span class=''>Codigo: </span>");
                            out.print("<input type='text' class='form-control' name='NmbCod' id='' data-toggle='tooltip' data-placement='top' title='' value='" + codx + "'>");
                            out.print("</div>");

                            out.print("<div style='margin: auto;'>");
                            out.print("<button class='btn btn-green'><i class='fas fa-search'></i></button>");
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</form>");

                            lst_connect = ConnectJpa.Consultar_firmasDoc(docx, codx);
                            String[] ObjCnn = lst_connect.toString().split("///");
//                        String sigma = ObjCnn[3].toString().trim();
                            if (lst_connect.size() == 0) {
                                lst_setting = SettingJpa.ConsultSettingCategorie("SirhSignature");
                                if (lst_setting != null) {
                                    Object[] ObjStt = (Object[]) lst_setting.get(0);
                                    out.print("<div class='text-center'>");
                                    out.print("<h4>No se ha encontrado la firma<br> Favor registrarla en el siguiente botton! </h4>");
                                    out.print("<div class='text-center mt-2'>");
                                    out.print("<button class='btn btn-green' onclick=\"window.open('" + ObjStt[2] + "', '_blank')\">Registrar firma <i class='fas fa-signature'></i></button>");
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                            } else if (ObjCnn[3].trim().equals("")) {
                                lst_setting = SettingJpa.ConsultSettingCategorie("SirhSignature");
                                if (lst_setting != null) {
                                    Object[] ObjStt = (Object[]) lst_setting.get(0);
                                    out.print("<div class='text-center'>");
                                    out.print("<h4>La firma se encuentra vacia! <br> Favor informar al área de TI que se debe corregir <br> la firma vacia posteriormente debe ingresar al <br> siguiente enlace para volverla a registrar! </h4>");
                                    out.print("<div class='text-center mt-2'>");
                                    out.print("<button class='btn btn-green' onclick=\"window.open('" + ObjStt[2] + "', '_blank')\">Registrar firma <i class='fas fa-signature'></i></button>");
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                            } else {
                                out.print("<form action='Device?opt=8&act=3&idTypeDv=" + idTypeDv + "&type=" + type + "&idDeviceHead=" + idDeviceHead + "&idDevice=" + idDevice + "' method='post' class='needs-validation' novalidate=''>");

                                out.print("<input type='hidden' class='form-control' name='idDeviceDetail' id='' value='" + ObjDetail[0] + "'>");
                                out.print("<input type='hidden' class='form-control' name='NmbDoc' id='' value='" + docx + "'>");
                                out.print("<input type='hidden' class='form-control' name='NmbCod' id='' value='" + codx + "'>");
                                out.print("<input type='hidden' class='form-control' name='idSignature' id='' value='" + ObjCnn[0].toString().replace("[", "") + "'>");
                                out.print("<input type='hidden' class='form-control' name='SigMode' id='idSigMode' value='" + SigMode + "'>");

                                out.print("<div class='canvas-container'>");
                                out.print("<div class='signature-pad mt-2 mb-4 d-flex' style='justify-content: center;'>");
                                out.print("<canvas id='miCanvas' width='400' height='200' style='border-bottom: 1px solid black;'></canvas>");
//                                out.print("<div class=''>");
//                                out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('signature-canvas')\"><i class='fas fa-sync-alt'></i></button>");
//                                out.print("</div>");
                                out.print("</div>");
                                out.print("<input type='hidden' class='form-control' name='TxtSignatureDraw' id='coordenadas-hidden' value='" + ObjCnn[3].toString() + "'>");
                                out.print("</div>");

                                out.print("<script>");
                                out.print(" function dibujarCoordenadas() { "
                                        + "            const canvas = document.getElementById('miCanvas'); "
                                        + "            const ctx = canvas.getContext('2d'); "
                                        + "            const coordenadas = JSON.parse(document.getElementById('coordenadas-hidden').value); "
                                        + "            ctx.clearRect(0, 0, canvas.width, canvas.height); "
                                        + "            coordenadas.forEach(coord => { "
                                        + "                ctx.beginPath(); "
                                        + "                ctx.moveTo(coord.lx, coord.ly); "
                                        + "                ctx.lineTo(coord.mx, coord.my); "
                                        + "                ctx.strokeStyle = 'black'; "
                                        + "                ctx.lineWidth = 2; "
                                        + "                ctx.stroke(); "
                                        + "            }); "
                                        + "        } "
                                        + " "
                                        + " window.onload = dibujarCoordenadas; ");
                                out.print("</script>");

                                out.print("<div class='text-center'>");
                                out.print("<button class='btn btn-green'>Firmar <i class='fas fa-signature'></i></button>");
                                out.print("</div>");

                            }

                            out.print("</form>");
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
                        } else {
                            //<editor-fold defaultstate="collapsed" desc="CONSULT SIGNATURE">
                            out.print("<form action='Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&type=" + type + "&idDeviceHead=" + idDeviceHead + "&idDevice=" + idDevice + "' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");
                            out.print("<span class=''>Firma seleccionada:</span> <input type='text' class='form-control inpMode' name='txtSigMode' id='idSigMode' value='" + SigMode + "'>");
                            out.print("<div class='text-center'>");
                            out.print("<div class='mr-2'>");
                            out.print("<span class=''>Documento: </span>");
                            out.print("<input type='text' class='form-control inputTextdt' name='NmbDoc' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");
                            out.print("</div>");

                            out.print("<div class=''>");
                            out.print("<span class=''>Codigo: </span>");
                            out.print("<input type='text' class='form-control inputTextdt' name='NmbCod' id='' data-toggle='tooltip' data-placement='top' title='' value=''>");
                            out.print("</div>");

                            out.print("<div style='margin: auto;'>");
                            out.print("<button class='btn btn-green'> Consultar <i class='fas fa-search '></i></button>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class=''>");
                            out.print("</div>");
                            out.print("</form>");
                            //</editor-fold>
                        }
                    } catch (Exception e) {
                    }

                    out.print("</div>");
                    out.print("</div>");
                    out.print("</div>");
                }
                //</editor-fold>

                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");

                //<editor-fold defaultstate="collapsed" desc="HEADER AND BUTTONS">
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=1&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDeviceHead=" + idDeviceHead + "&act=3\";cargarDatos()'><i class='fas fa-arrow-left'></i></button>");
                out.print("<div class='text-center'>");
                out.print("<h4>Documentacion " + nameDevice + "</h4><h1>" + typeSc[1] + "</h1><h4>" + nameDoc + "</h4>");
                out.print("</div>");

                out.print("<div class='d-flex'>");
                if (stetx != 99) {
                    if (singExits.contains("XX")) {
                        out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px;' onclick='mostrarConvencion(3)'><i class='fas fa-signature'></i></button>");
                    }
                    if (stetx == 0 && (code.equals("A") || code.contains("-019"))) {
                        out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=5&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDeviceHead=" + idDeviceHead + "&idDeviceDetail=" + ObjDetail[0] + "&type=" + type + "&act=3&xtemp=1\";cargarDatos()'><i class='fas fa-share'></i></button>");
                    } else if (stetx == 1) {
                        out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=5&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDeviceHead=" + idDeviceHead + "&idDeviceDetail=" + ObjDetail[0] + "&type=" + type + "&act=3&xtemp=1\";cargarDatos()'><i class='fas fa-share'></i></button>");
                    }
                } else {
                    out.print("<span></span>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="LOAD DOCUMENT">
                if (code.equals("A")) {
                    //<editor-fold defaultstate="collapsed" desc="ATTACH FILES">
                    if (lst_DeviceDetail == null) {
                        //<editor-fold defaultstate="collapsed" desc="NEW UPLOAD">
                        lst_setting = SettingJpa.ConsultSettingId(74);
                        if (lst_setting != null) {
                            Object[] ObjStt = (Object[]) lst_setting.get(0);
                            String[] docs = ObjStt[2].toString().split("///");
                            out.print("<form action='Attach.jsp' method='post' class='needs-validation' novalidate='' enctype='multipart/form-data' onsubmit='return cargarDatosForm(this)'>");

                            out.print("<input type='hidden' name='idDevice' value='" + idDevice + "'>");
                            out.print("<input type='hidden' name='idDeviceHead' value='" + idDeviceHead + "'>");
                            out.print("<input type='hidden' name='typeDoc' value='" + type + "'>");
                            out.print("<input type='hidden' class='form-control' name='txtNameCat' id='pruebas'>");
                            out.print("<input type='hidden' name='validDevice' value='1'>");

                            out.print("<div class='row'>");
                            for (int i = 0; i < docs.length; i++) {
                                String[] dtail = docs[i].split("/");
                                int counter = i + 1;
                                out.print("<div class='mt-4 col-lg-6'>");
                                out.print("<span class='mb-2'>" + counter + ". " + dtail[0] + "</span> <i class='fas fa-question-circle' data-toggle='tooltip' data-placement='top' title='" + dtail[1] + "'></i>");

                                out.print("<div class='d-flex' style='align-items: center;'>");
                                out.print("<input type='file' class='form-control intFile' data-categoria='" + dtail[0] + "' name='txtFile" + i + "' id='txtFile" + i + "' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                                out.print("<div id='DownloadFile" + i + "'></div>");
                                out.print("</div>");

                                out.print("</div>");
                                out.print("<script>");
                                out.print("document.getElementById('txtFile" + i + "').addEventListener('change', function(){ "
                                        + "var input = this; "
                                        + "var NameFile = input.files[0].name; "
                                        + "var DownloadFile = document.getElementById('DownloadFile" + i + "'); "
                                        + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\"><i class=\"fas fa-download\"></i></a>'; "
                                        + "});");
                                out.print("</script>");
                            }
                            out.print("</div>");

                            out.print("<div class='text-center mt-4'>");
                            out.print("<button class='btn btn-green'>Registrar</button>");
                            out.print("</div>");

                            out.print("<script>\n"
                                    + "document.addEventListener(\"DOMContentLoaded\", function () {\n"
                                    + "    document.querySelectorAll(\".intFile\").forEach(function(input) {\n"
                                    + "        input.addEventListener(\"change\", function() {\n"
                                    + "            if (this.files.length > 0) {\n"
                                    + "                const fileName = this.files[0].name;\n"
                                    + "                const categoria = this.getAttribute(\"data-categoria\");\n"
                                    + "                const combo = categoria + \"/\" + fileName;\n"
                                    + "\n"
                                    + "                const campoOculto = document.getElementById(\"pruebas\");\n"
                                    + "                // Si ya hay contenido, agregamos con separador. Si no, solo el nuevo.\n"
                                    + "                if (campoOculto.value.trim() !== \"\") {\n"
                                    + "                    campoOculto.value += \"[\" + combo + \"]\";\n"
                                    + "                } else {\n"
                                    + "                    campoOculto.value = \"[\" + combo + \"]\";\n"
                                    + "                }\n"
                                    + "            }\n"
                                    + "        });\n"
                                    + "    });\n"
                                    + "});\n"
                                    + "</script>");

                            out.print("</form>");
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="LIST FILES">
                        Object[] ObjD = (Object[]) lst_DeviceDetail.get(0);
                        String[] Cons_docs = ObjD[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        String allDocs = ObjD[4].toString();
                        out.print("<div class='row' style='justify-content: space-evenly;'>");
                        for (int i = 0; i < Cons_docs.length; i++) {
                            Object[] DataFiles = Cons_docs[i].split("/");
                            out.print("<div class='text-center mt-4 mb-4 col-lg-2'>");
                            out.print("<div class='SqDocs' onclick='window.location.href=\"Download?File_name=" + DataFiles[1] + "\"'>");
                            out.print("<div class='SqDetail'>");
                            out.print("<i class=\"fas fa-pen\" style='font-size: 15px;' onclick='mostrarConvencion(" + i + ");editar(event) '></i>");
                            out.print("</div>");
                            out.print("<i class=\"fas fa-cloud-download-alt\"></i>");
                            out.print("</div>");
                            out.print("<span class='mb-2'>" + DataFiles[0] + "</span> </i>");
                            out.print("</div>");

                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana" + i + "' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='contGeneral' style='width: 35%; top: 10%; right: 22%;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Modificar Archivo </h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(" + i + ")' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");

                            out.print("<form action='Attach.jsp' method='post' class='needs-validation' novalidate='' enctype='multipart/form-data' onsubmit='return cargarDatosForm(this)'>");
                            out.print("<input type='hidden' name='idDevice' value='" + idDevice + "'>");
                            out.print("<input type='hidden' name='idDeviceHead' value='" + idDeviceHead + "'>");
                            out.print("<input type='hidden' name='typeDoc' value='" + type + "'>");
                            out.print("<input type='hidden' class='form-control' name='txtNameCat' id='xpr" + DataFiles[0] + "'>");
                            out.print("<input type='hidden' class='form-control' name='fileDelete' value='" + DataFiles[1] + "'>");
                            out.print("<input type='hidden' name='allDocs' value='" + allDocs + "'>");
                            out.print("<input type='hidden' name='idDeviceDetail' value='" + ObjD[0] + "'>");
                            out.print("<input type='hidden' name='validDevice' value='1'>");

                            out.print("<div class='d-flex' style='align-items: center;'>");
                            out.print("<input type='file' class='form-control intxFile' data-categoria='" + DataFiles[0] + "' name='txtFile" + i + "' id='txtFilex" + i + "' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                            out.print("<div id='DownloadFile" + i + "'></div>");
                            out.print("</div>");

                            out.print("</div>");

                            out.print("<div class='text-center'>");
                            out.print("<button class='btn btn-green'>Modificar</button>");
                            out.print("</div>");

                            out.print("<script>");
                            out.print("document.getElementById('txtFilex" + i + "').addEventListener('change', function(){ "
                                    + "var input = this; "
                                    + "var NameFile = input.files[0].name; "
                                    + "var DownloadFile = document.getElementById('DownloadFile" + i + "'); "
                                    + "DownloadFile.innerHTML = '<a class=\"btn btn-info\" href=\"' + URL.createObjectURL(input.files[0]) + '\" download=\"' + NameFile + '\"><i class=\"fas fa-download\"></i></a>'; "
                                    + "});");
                            out.print("</script>");

                            out.print("</form>");

                            out.print("</div>");
                            out.print("</div>");
//                            out.print("</div>");

                            out.print("<script>\n"
                                    + "document.addEventListener(\"DOMContentLoaded\", function () {\n"
                                    + "  const inputs = document.querySelectorAll(\".intxFile\");\n"
                                    + "  inputs.forEach(function(input) {\n"
                                    + "    if (!input.dataset.listenerAttached) {\n"
                                    + "      input.dataset.listenerAttached = true;\n"
                                    + "      input.addEventListener(\"change\", function() {\n"
                                    + "        if (this.files.length > 0) {\n"
                                    + "          const fileName = this.files[0].name;\n"
                                    + "          const categoria = this.getAttribute(\"data-categoria\");\n"
                                    + "          const combo = categoria + \"/\" + fileName;\n"
                                    + "          const campoOculto = document.getElementById(\"xpr\"+ categoria );\n"
                                    + "\n"
                                    + "          if (campoOculto.value.trim() !== \"\") {\n"
                                    + "            campoOculto.value = \"[\" + combo + \"]\";\n"
                                    + "          } else {\n"
                                    + "            campoOculto.value = \"[\" + combo + \"]\";\n"
                                    + "          }\n"
                                    + "        }\n"
                                    + "      });\n"
                                    + "    }\n"
                                    + "  });\n"
                                    + "});\n"
                                    + "</script>");
                        }
                        out.print("</div>");
//</editor-fold>
                    }
                    //</editor-fold>
                } else if (code.contains("-019")) {
                    //<editor-fold defaultstate="collapsed" desc="ITEM ASIGN">
                    if (lst_DeviceDetail != null) {
                        //<editor-fold defaultstate="collapsed" desc="ITEM ASIGNED">
                        Object[] ObjCom = (Object[]) lst_DeviceDetail.get(0);
                        idItem = Integer.parseInt(ObjCom[4].toString());
                        out.print("<div class=''>");
                        lst_item = ItemJpa.ConsultItemLastMove(idItem);
                        if (lst_item != null) {
                            Object[] ObjItm = (Object[]) lst_item.get(0);
                            out.print("<div class='text-center mt-4'>");
                            out.print("<h4 class=''>DETALLE DEL ITEM</h4>");
                            out.print("</div>");

                            out.print("<div class='row mt-4'>");

                            out.print("<div class='col-lg-3 mb-4'>");
                            out.print("<span class=''><b>ITEM</b></span><br>");
                            out.print("<span class='ml-2'><b>" + ObjItm[1] + "</b></span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3'>");
                            out.print("<span class=''><b>REFERENCIA</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[2] + "</span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3'>");
                            out.print("<span class=''><b>PROVEEDOR</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[3] + "</span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3'>");
                            out.print("<span class=''><b>MARCA</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[4] + "</span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3 mb-4'>");
                            out.print("<span class=''><b>UBICACION</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[5] + "</span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3'>");
                            out.print("<span class=''><b>ULTIMO MOVIMIENTO</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[6] + "</span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3'>");
                            out.print("<span class=''><b>NUMERO MOVIMIENTO</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[7] + "</span>");
                            out.print("</div>");

                            out.print("<div class='col-lg-3'>");
                            out.print("<span class=''><b>FECHA MOVIMIENTO</b></span><br>");
                            out.print("<span class='ml-2'>" + ObjItm[8] + "</span>");
                            out.print("</div>");

                            out.print("</div>");

//                            out.print("<form action='Device?opt=6' method='post' id='formConfirmItem'>");
//                            out.print("<input type='text' name='IdComputer' value='" +  "'>");
//                            out.print("<input type='text' name='idpcHead' value='" +  "'>");
//                            out.print("<input type='text' name='type' value='" + type + "'>");
//                            out.print("<input type='text' name='cbxItem' value='" + idItem + "'>");
//                            out.print("</form>");
//                            out.print("<div class='text-center'>");
//                            out.print("<button class='btn btn-green' onclick='formConfirmItem.submit()'>Confirmar</button>");
//                            out.print("</div>");
                            out.print("</div>");

//                                SE PUEDE AGREGAR LA CONSULTA LA INFORMACION DEL EQUIPO, COMO ESTA REALCIONADO DIRECTAMENTE POR EL ID DEL ITME, 
//                                LOS DATOS SE PUEDEN AGREGAR EN LA CONSULTA ConsultItemLastMove CON UN INNER O LEFT JOIN COMO SEA :D
//                                LO AGREGARIA PERO ESTOY CORRIENDO CON LO QUE ES MAS PRIOPRITARIO Y ESTA DENSO
                        } else {
                            out.print("<div class='text-center'>");
                            out.print("<h4 class=''>Error al consultar la informacion detallada del item.</h4>");
                            out.print("</div>");
                        }
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="SEARCH ITEM TO ASIG">
                        lst_item = ItemJpa.ConsultItemAvailable();

                        out.print("<div class='text-center'>");
                        out.print("<form action='Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDoc=" + idDoc + "&idDeviceHead=" + idDeviceHead + "&type=" + type + "' method='post' class='needs-validation' novalidate='' id='formSearchItem' onsubmit='return cargarDatosForm(this)'>");
                        out.print("<h4 class='mb-4'>Items disponibles</h4>");
                        out.print("<div class='col-lg-6' style='margin: auto;' data-toggle='tooltip' data-placement='top' title=''>");
                        out.print("<select class='form-control' name='cbxItem' style='margin-12px;' onchange='formSearchItem.submit()'>");
                        out.print("<option selected disabled>Seleccionar </option>");
                        for (int i = 0; i < lst_item.size(); i++) {
                            Object[] ObjDet = (Object[]) lst_item.get(i);
                            out.print("<option value='" + ObjDet[0] + "'>Item: " + ObjDet[1] + "</option>");
                        }
                        out.print("</select>");
                        out.print("</div>");

                        out.print("</form>");
                        out.print("</div>");

                        if (idItem > 0) {
                            out.print("<div class=''>");
                            lst_item = ItemJpa.ConsultItemLastMove(idItem);
                            if (lst_item != null) {
                                Object[] ObjItm = (Object[]) lst_item.get(0);
                                out.print("<div class='text-center mt-4'>");
                                out.print("<h4 class=''>DETALLE DEL ITEM</h4>");
                                out.print("</div>");

                                out.print("<div class='row mt-4'>");

                                out.print("<div class='col-lg-3 mb-4'>");
                                out.print("<span class=''><b>ITEM</b></span><br>");
                                out.print("<span class='ml-2'><b>" + ObjItm[1] + "</b></span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3'>");
                                out.print("<span class=''><b>REFERENCIA</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[2] + "</span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3'>");
                                out.print("<span class=''><b>PROVEEDOR</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[3] + "</span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3'>");
                                out.print("<span class=''><b>MARCA</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[4] + "</span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3 mb-4'>");
                                out.print("<span class=''><b>UBICACION</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[5] + "</span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3'>");
                                out.print("<span class=''><b>ULTIMO MOVIMIENTO</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[6] + "</span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3'>");
                                out.print("<span class=''><b>NUMERO MOVIMIENTO</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[7] + "</span>");
                                out.print("</div>");

                                out.print("<div class='col-lg-3'>");
                                out.print("<span class=''><b>FECHA MOVIMIENTO</b></span><br>");
                                out.print("<span class='ml-2'>" + ObjItm[8] + "</span>");
                                out.print("</div>");

                                out.print("</div>");

                                out.print("<form action='Device?opt=6' method='post' id='formConfirmItem'>");
                                out.print("<input type='hidden' name='idDevice' value='" + idDevice + "'>");
                                out.print("<input type='hidden' name='idDeviceHead' value='" + idDeviceHead + "'>");
                                out.print("<input type='hidden' name='type' value='" + type + "'>");
                                out.print("<input type='hidden' name='idTypeDv' value='" + idTypeDv + "'>");
                                out.print("<input type='hidden' name='cbxItem' value='" + idItem + "'>");
                                out.print("</form>");

                                out.print("<div class='text-center'>");
                                out.print("<button class='btn btn-green' onclick='formConfirmItem.submit();cargarDatos()'>Confirmar</button>");
                                out.print("</div>");

                                out.print("</div>");

//                                SE PUEDE AGREGAR LA CONSULTA LA INFORMACION DEL EQUIPO, COMO ESTA REALCIONADO DIRECTAMENTE POR EL ID DEL ITME, 
//                                LOS DATOS SE PUEDEN AGREGAR EN LA CONSULTA ConsultItemLastMove CON UN INNER O LEFT JOIN COMO SEA :D
//                                LO AGREGARIA PERO ESTOY CORRIENDO CON LO QUE ES MAS PRIOPRITARIO Y ESTA DENSO
                            } else {
                                out.print("<div class='text-center'>");
                                out.print("<h4 class=''>Error al consultar la informacion detallada del item.</h4>");
                                out.print("</div>");
                            }
                        }
                        //</editor-fold>
                    }
                    //</editor-fold>
                } else if (code.contains("-003")) {
                    //<editor-fold defaultstate="collapsed" desc="ASSIGN PC">
                    lst_device = DeviceJpa.ConsultDevicexId(idDevice);
                    if (lst_DeviceDetail != null) {
                        Object[] ObInfo = (Object[]) lst_device.get(0);
                        //<editor-fold defaultstate="collapsed" desc="CONSULT DOCUMENT">
                        Object[] ObjFormat = (Object[]) lst_DeviceDetail.get(0);
                        String[] DtaFormat = ObjFormat[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                        //<editor-fold defaultstate="collapsed" desc="REPLACE DATA">
                        format = format.replace("XXXTIPOOXXX", typeDvName)
                                .replace("XXXAMRCAXXX", ObInfo[16].toString())
                                .replace("XXXMODELOXXX", ObInfo[15].toString())
                                .replace("XXXSERIALXXX", ObInfo[6].toString())
                                .replace("XXXITEMXXX", ObInfo[5].toString());
                        format = format.replace("XXXNROPCXXX", nameDevice);
                        format = format.replace("XXXCARGOXXX", "<b>" + DtaFormat[2].toString() + "</b>")
                                .replace("XXXAREAXXX", "<b>" + DtaFormat[3].toString() + "</b>")
                                .replace("XXXUBICACIONXXX", "<b>" + DtaFormat[4].toString() + "</b>")
                                .replace("XXXBOSSNAMEXXX", "<b>" + DtaFormat[5].toString() + "</b>")
                                .replace("XXXNAMEXXX", "<b>" + DtaFormat[6].toString() + "</b>")
                                .replace("XXXCEDULAXXX", "<b>" + DtaFormat[7].toString() + "</b>")
                                .replace("XXXLOCATIONXXX", "<b>" + DtaFormat[8].toString() + "</b>")
                                .replace("XXXUSERPCXXX", "<b>" + DtaFormat[9].toString() + "</b>")
                                .replace("XXXDIAXXX", "<b>" + DtaFormat[10].toString() + "</b>")
                                .replace("XXXMESXXX", "<b>" + DtaFormat[11].toString() + "</b>")
                                .replace("XXXANIOXXX", "<b>" + DtaFormat[12].toString() + "</b>")
                                .replace("XXXCOLUMM1XXX", "<b>" + DtaFormat[13].toString() + "</b>")
                                .replace("XXXCOLUMM2XXX", "<b>" + DtaFormat[14].toString() + "</b>")
                                .replace("XXXCOLUMM3XXX", "<b>" + DtaFormat[15].toString() + "</b>")
                                .replace("XXXCOLUMM4XXX", "<b>" + DtaFormat[16].toString() + "</b>")
                                .replace("XXXCOLUMM5XXX", "<b>" + DtaFormat[17].toString()) + "</b>";

                        format = format.replace("XXXElaboradorXXX", "<b class='text-warning'>Pendiente Firma</b>");
                        format = format.replace("XXXUsuarioXXX", "<b class='text-warning'>Pendiente Firma</b>");
                        format = format.replace("XXXJefe o DirectorXXX", "<b class='text-warning'><b class='text-warning'>Pendiente Firma</b></b>");

                        //</editor-fold>
                        //<editor-fold defaultstate="collapsed" desc="ITEMS PLUS">
                        String ItmsPlus = DtaFormat[0].toString();
                        if (!ItmsPlus.equals("NoN")) {
                            lst_item = ItemJpa.ConsultItemId(ItmsPlus);
                            String struc_itme = "";
                            if (lst_item != null) {
                                for (int i = 0; i < lst_item.size(); i++) {
                                    Object[] ObjItmeId = (Object[]) lst_item.get(i);
                                    struc_itme += "<tr>";
                                    struc_itme += "<td>" + ObjItmeId[1] + "</td><td>" + ObjItmeId[2] + "</td><td>" + ObjItmeId[3] + "</td><td>" + ObjItmeId[4] + "</td><td>" + ObjItmeId[5] + "</td>";
                                    struc_itme += "</tr>";
                                }
                                format = format.replace("<tr><td colspan=\"5\" class=\"text-center\" style=\"padding: 15px;\">XXXPLUS1XXX</td></tr>", struc_itme);
                            } else {
                                format = format.replace("XXXPLUS1XXX", "Error al consultar items");
                            }
                        } else {
                            format = format.replace("<tr><td colspan=\"5\" class=\"text-center\" style=\"padding: 15px;\">XXXPLUS1XXX</td></tr>", "");
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="SOFTWARE">
                        String softApp = DtaFormat[1].toString();
                        if (!softApp.equals("NoN")) {
                            try {
                                String str_soft = "<tr><td>" + softApp.replace("/", "</td><td>").replace("---", "</td></tr><tr><td>") + "</td></tr>";
                                format = format.replace("<tr><td colspan=\"5\" class=\"text-center\" style=\"padding: 15px;\">XXXPLUS2XXX</td></tr>", str_soft);
                            } catch (Exception e) {
                                format = format.replace("XXXPLUS2XXX", "Error al consultar software instalado");
                            }
                        } else {
                            format = format.replace("<tr><td colspan=\"5\" class=\"text-center\" style=\"padding: 15px;\">XXXPLUS2XXX</td></tr>", "");
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="ADITIONAL INFORMATION">
                        format = format.replace("name=\"textcal\" value=\"" + DtaFormat[18].toString() + "\"", "name=\"textcal\" value=\"" + DtaFormat[18].toString() + "\" checked disabled");
                        try {
                            format = format.replace("name=\"textFll\" value=\"" + DtaFormat[19].toString() + "\"", "name=\"textFll\" value=\"" + DtaFormat[19].toString() + "\" checked disabled");
                        } catch (Exception e) {
                        }

//                        format = format.replace("name=\"textcal\"", "name=\"textcal\" disabled");
//                        format = format.replace("name=\"textFll\"", "name=\"textFll\" disabled");
                        //</editor-fold>

                        out.print(format);
                        out.print(post_script);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="NEW REGISTER">
                        if (lst_device != null) {
                            Object[] ObInfo = (Object[]) lst_device.get(0);
                            out.print("<button class='btn btn-green' style='position: fixed; bottom: 12px; right: 71px;' onclick='validData003()'>Guardar <i class='fas fa-save'></i></button>");

                            //<editor-fold defaultstate="collapsed" desc="SEARCH ITEMS">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='contGeneral' style='width: 44%;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Agregar item </h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");
                            lst_item = ItemJpa.ConsultItemAdd();
                            if (lst_item != null) {

                                out.print("<table class='table table-bordered table-sm' id='table-1' style='cursor: pointer;'>");
                                out.print("<thead>");
                                out.print("<tr>");
                                out.print("<th>Item</th>");
                                out.print("<th>Referencia</th>");
                                out.print("<th>Marca</th>");
                                out.print("<th>Modelo</th>");
                                out.print("</tr>");
                                out.print("</thead>");
                                out.print("<tbody>");
                                for (int i = 0; i < lst_item.size(); i++) {
                                    Object[] ObjItm = (Object[]) lst_item.get(i);
                                    out.print("<tr data-v='" + ObjItm[0] + "'>");
                                    out.print("<td>" + ObjItm[5] + "</td>");
                                    out.print("<td>" + ObjItm[1] + "</td>");
                                    out.print("<td>" + ObjItm[2] + "</td>");
                                    out.print("<td>" + ObjItm[3] + "</td>");
                                    out.print("</tr>");
                                }
                                out.print("</tbody>");
                                out.print("</table>");

                            } else {
                                out.print("<div class=''>");
                                out.print("<h4>Al parecer no hay items disponibles para asignar.</h4>");
                                out.print("</div>");
                            }
                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");
                            //</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="SOFTWARE INSTALLED">
                            out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                            out.print("<div class='contGeneral' style='width: 44%;'>");
                            out.print("<div style='display: flex; justify-content: space-between'>");
                            out.print("<h2>Software </h2>");
                            out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                            out.print("</div>");
                            out.print("<div class='cont_form_user'>");

                            out.print("<table class='table table-bordered table-sm' id='tabla'>");
                            out.print("<thead>");
                            out.print("<tr>");
                            out.print("<th>Nombre</th>");
                            out.print("<th>Tipo</th>");
                            out.print("<th>Version</th>");
                            out.print("<th>OPC</th>");
                            out.print("</tr>");
                            out.print("</thead>");
                            out.print("<tbody id=\"tabla-body\">");
                            out.print("<tr>");
                            out.print("<td><input type='text' class='form-control' name='' id='idName'></td>");
                            out.print("<td><input type='text' class='form-control' name='' id='idType'></td>");
                            out.print("<td><input type='number' class='form-control' name='' id='idVersion'></td>");
                            out.print("<td><button class=\"btn btn-info\" onclick=\"agregarFila()\"><i class='fas fa-plus'></button></td>");
                            out.print("</tr>");
                            String Installed = "";
                            lst_setting = SettingJpa.ConsultSettingCategorie("InstalledSoftware003Dv");
                            if (lst_setting != null) {
                                for (int i = 0; i < lst_setting.size(); i++) {
                                    Object[] ObjSett = (Object[]) lst_setting.get(i);
                                    String[] dataSet = ObjSett[2].toString().split("/");
                                    out.print("<tr>");
                                    out.print("<td>" + dataSet[0] + "</td>");
                                    out.print("<td>" + dataSet[1] + "</td>");
                                    out.print("<td>" + dataSet[2] + "</td>");
                                    out.print("<td><button class='btn btn-danger' onclick='eliminarFila(this)'>Eliminar</button></td>");
                                    out.print("</tr>");
                                    Installed += "[" + ObjSett[2].toString() + "]";
                                }
                            }
                            out.print("</tbody>");
                            out.print("</table>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");   
                            //</editor-fold>
                            
                            String optArea = "";
                            lst_area = AreaJpa.ConsultAreaActive();
                            if (lst_area != null) {
                                for (int i = 0; i < lst_area.size(); i++) {
                                    Object[] ObjOpc = (Object[]) lst_area.get(i);
                                    optArea += "<option value='" + ObjOpc[0] + "/" + ObjOpc[1] + "'>" + ObjOpc[1] + "</option>";
                                }
                            }
                            String areaList = "<div class='col-lg-6'><select class='form-control select2' name='cbxArea' style='margin-12px;'>"
                                    + "<option selected disabled>Seleccionar </option> " + optArea + " </select></div>";
                            
                            //<editor-fold defaultstate="collapsed" desc="REPLACE DATA">
                            format = format.replace("XXXCARGOXXX", "<input type='text' class='form-control' name='txt_post' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXAREAXXX", areaList)
                                    .replace("XXXUBICACIONXXX", "<input type='text' class='form-control' name='txt_location' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXBOSSNAMEXXX", "<input type='text' class='form-control col-lg-2' name='txt_bossname' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXNAMEXXX", "<input type='text' class='form-control col-lg-2' name='txt_name' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXCEDULAXXX", "<input type='text' class='form-control col-lg-2' name='txt_indentity' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXLOCATIONXXX", "<input type='text' class='form-control col-lg-2' name='txt_place' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXUSERPCXXX", "<input type='text' class='form-control col-lg-2' name='txt_user' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    
                                    .replace("XXXDIAXXX", "<input type='number' class='form-control col-lg-2' name='txt_day' id='' value='" + currentDay + "' required>")
                                    .replace("XXXMESXXX", "<input type='number' class='form-control col-lg-2' name='txt_month' id='' value='" + currentMonth + "' required>")
                                    .replace("XXXANIOXXX", "<input type='number' class='form-control col-lg-2' name='txt_anio' id='' value='" + currentYear + "' required>")
                                    
                                    .replace("XXXCOLUMM1XXX", "<input type='text' class='form-control' name='txt_comm1' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM2XXX", "<input type='text' class='form-control' name='txt_comm2' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM3XXX", "<input type='text' class='form-control' name='txt_comm3' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM4XXX", "<input type='text' class='form-control' name='txt_comm4' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM5XXX", "<input type='text' class='form-control' name='txt_comm5' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >");
                            format = format.replace("XXXNROPCXXX", nameDevice);
                            format = format.replace("XXXTIPOOXXX", typeDvName)
                                    .replace("XXXAMRCAXXX", ObInfo[16].toString())
                                    .replace("XXXMODELOXXX", ObInfo[15].toString())
                                    .replace("XXXSERIALXXX", ObInfo[6].toString())
                                    .replace("XXXITEMXXX", ObInfo[5].toString());
                            format = format.replace("XXXPLUS1XXX", "<button type='buitton' class='btn btn-green btn-sm' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                            format = format.replace("XXXPLUS2XXX", "<button type='buitton' class='btn btn-green btn-sm' onclick='mostrarConvencion(2)'><i class='fas fa-plus'></i></button>");

                            format = format.replace("XXXElaboradorXXX", "<b class='text-warning'>Pendiente Firma</b>");
                            format = format.replace("XXXUsuarioXXX", "<b class='text-warning'>Pendiente Firma</b>");
                            format = format.replace("XXXJefe o DirectorXXX", "<b class='text-warning'>Pendiente Firma</b>");
//</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="FORM TO REGISTER">
                            out.print("<form action='Device?opt=7&idDevice=" + idDevice + "&idDeviceHead=" + idDeviceHead + "&type=" + type + "&idTypeDv=" + idTypeDv + "' method='post' class='needs-validation' novalidate='' id='formR03'>");
                            out.print(format);
                            out.print("<input type='hidden' class='form-control' name='txt_otherItem' id='infoField' >");
                            out.print("<input type='hidden' class='form-control' name='txt_soft' id='infoOculta' value='" + Installed + "' >");
                            out.print("</form>");

                            out.print("<script>\n"
                                    + "document.addEventListener('DOMContentLoaded', function () {\n"
                                    + "    const table = document.getElementById('table-1');\n"
                                    + "    const infoField = document.getElementById('infoField');\n"
                                    + "\n"
                                    + "    table.addEventListener('click', function (e) {\n"
                                    + "        const row = e.target.closest('tr');\n"
                                    + "        if (!row || !row.hasAttribute('data-v')) return;\n"
                                    + "\n"
                                    + "        const dataValue = row.getAttribute('data-v');\n"
                                    + "        let currentText = infoField.value;\n"
                                    + "\n"
                                    + "        if (row.classList.contains('selected-row')) {\n"
                                    + "            // Deseleccionar fila\n"
                                    + "            row.classList.remove('selected-row');\n"
                                    + "            currentText = currentText.replace(`[${dataValue}]`, '').trim();\n"
                                    + "        } else {\n"
                                    + "            // Seleccionar fila\n"
                                    + "            row.classList.add('selected-row');\n"
                                    + "            currentText += ` [${dataValue}]`;\n"
                                    + "        }\n"
                                    + "\n"
                                    + "        // Limpia espacios y múltiples corchetes\n"
                                    + "        infoField.value = currentText.replace(/\\s+/g, ' ').trim();\n"
                                    + "    });\n"
                                    + "});\n"
                                    + "</script>");
                            //</editor-fold>

                        } else {
                            out.print("<div style='text-align: center; margin-top: 5%;'>");
                            out.print("<h4>¡ATENCIÓN! <br><br> Ha ocurrido un error al consultar la información del pc seleccionado, "
                                    + "favor revisar si tiene su respectivo item asignado y ya tiene un movimiento en el aplicativo.</h4>");
                            out.print("<i style='font-size: 80px;' class=\"fas fa-exclamation-triangle\"></i>");
                            out.print("</div>");
                        }

                        //</editor-fold>
                    }
                    //</editor-fold>
                } else if (code.contains("-004") || code.contains("-013") || code.contains("-029") || code.contains("-031") || code.contains("-032")) {
                    //<editor-fold defaultstate="collapsed" desc="PREVENTIVE MAINTENANCE 004 // INSTALLED PROGRAMS 029">

                    format = format.replace("XXXDATEXXX", CurrentDate);
                    format = format.replace("XXXAREAXXX", area);
                    format = format.replace("XXXUSUARIOXXX", usuario);
                    format = format.replace("XXXEQUIPOXXX", nameDevice);
                    format = format.replace("XXXUSERXXX", usuario);
                    format = format.replace("XXXPOSITIONXXX", cargouser);
                    format = format.replace("XXXUSERNAMEXXX", nameUser);

                    if (stetx == 2) {
                        format = format.replace("id=\"idtabla\"", "id=\"idtabla\" class='inactive004'");
                    }
                    out.print(format);
                    out.print(post_script);

                    if (stetx == 1 || stetx == 99) {
                        out.print("<form action='Device?opt=9&idDevice=" + idDevice + "&idDeviceDetail=" + idDeviceDetail + "&idDeviceHead=" + idDeviceHead + "&idTypeDv=" + idTypeDv + "&type=" + type + "' method='post' id='Form04'>");
                        out.print("<input type='hidden' name='htmlTabla' id='htmlTabla' value=''>");
                        out.print("<input type='hidden' name='DocCode' id='' value='" + code.split("-")[2] + "'>");
                        out.print("<div class='text-center mt-4' style='position: fixed;right: 17px;bottom: 17px;'>");
                        out.print("<button type='button' class='btn btn-green' onclick='guardarHTMLTabla()'><i class=\"fas fa-save\"></i> Modificar</button>");
                        out.print("</div>");
                        out.print("</form>");
                    }
                    //</editor-fold>
                }
                //</editor-fold>

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>
            } else if (action == 3) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE DETAIL">
                int idDeviceHead = 0;
                try {
                    idDeviceHead = Integer.parseInt(pageContext.getRequest().getAttribute("idDeviceHead").toString());
                } catch (Exception e) {
                    idDeviceHead = 0;
                }

                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=1&act=2&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "\";cargarDatos()'><i class='fas fa-arrow-left'></i></button>");
                out.print("<div class='text-center'><h4>Documentacion</h4><h2>" + nameDevice + "</h2></div>");
                out.print("<span class=''></span>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                lst_deviceHead = DeviceHead.ConsultDeviceHeaderIdHead(idDeviceHead);
                if (lst_deviceHead != null) {
                    Object[] ObjDvHe = (Object[]) lst_deviceHead.get(0);
                    structure = ObjDvHe[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                    out.print("<div class='card'>");
                    out.print("<div class=\"row mt-4\" style='width: 100%; justify-content: center;'>");
                    out.print("<div class=\"col-12\">");
                    out.print("<div class=\"wizard-steps\" style='display: flex; flex-wrap: wrap; justify-content: center;'>");

                    state = Integer.parseInt(ObjDvHe[4].toString());
                    for (int i = 1; i < structure.length; i++) {
                        String[] idxnamexico = structure[i].toString().split("/");
                        int id = 0;
                        String name = idxnamexico[1].toString();
                        String ico = idxnamexico[2].toString();
                        if (!idxnamexico[0].toString().equals("A")) {
                            id = Integer.parseInt(idxnamexico[0].toString());
                        }

                        if (i == state) {
                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDoc=" + id + "&idDeviceHead=" + idDeviceHead + "&type=" + structure[i] + "&step=" + i + "\";cargarDatos()' style='background: #33bf98; color:#0b0025; cursor: pointer;' data-toggle='tooltip' data-placement='top' title='En proceso'>");
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
                            lst_DeviceDetail = DeviceDetailJpa.ConsultDeviceDetailxDvxType(idDeviceHead, name);
                            if (lst_DeviceDetail != null) {
                                Object[] ObSt = (Object[]) lst_DeviceDetail.get(0);
                                if (ObSt[5].toString().contains("XX")) {
                                    out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDoc=" + id + "&idDeviceHead=" + idDeviceHead + "&type=" + structure[i] + "&step=" + i + "\";cargarDatos()' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                    out.print("<div class=\"wizard-step-icon\">");
                                    out.print("<i class=\"" + ico + "\"></i>");
                                    out.print("</div>");
                                    out.print("<div class=\"wizard-step-label\" style='margin-bottom: 6px;'>");
                                    out.print(name);
                                    out.print("<div style='position: absolute;bottom: 5px;left: -5px;'>");
                                    out.print("<p style='margin: 0; width: 170px; background: #ffa426;border-radius: 3px;'><b><b class='text-black'>Pendiente Firma</b></b> &nbsp; <i class='fas fa-signature'></i></p>");
                                    out.print("</div>");
                                    out.print("</div>");
                                    out.print("</div>");
                                } else {
                                    int steDet = Integer.parseInt(ObSt[6].toString());
                                    if (steDet == 0) {
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDoc=" + id + "&idDeviceHead=" + idDeviceHead + "&type=" + structure[i] + "&step=" + i + "\";cargarDatos()' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
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
//                                    out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&act=3&idApp\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Device?opt=1&act=4&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDoc=" + id + "&idDeviceHead=" + idDeviceHead + "&type=" + structure[i] + "&step=" + i + "\";cargarDatos()' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
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

                }

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");

                //</editor-fold>
            } else if (action == 2) {
                //<editor-fold defaultstate="collapsed" desc="DEVICE LIST DETAIL">

                //<editor-fold defaultstate="collapsed" desc="LIST FILES">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 66%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Historial de " + nameDevice + " (REDEAC)</h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                lst_device = DeviceJpa.ConsulDataRedeacDevice(idDevice);
                if (lst_device != null) {
                    out.print("<div style='max-height: 545px;overflow-y: auto;overflow-x: hidden;'>");
                    out.print("<table class='tabletf table table-sm' id='table-3'>");
                    out.print("<thead>");
                    out.print("<tr class='text-center text-dark'>");
                    out.print("<th>CODIGO - DOCUMENTO</th>");
                    out.print("<th>FECHA</th>");
                    out.print("<th>USUARIO</th>");
                    out.print("<th>LINK</th>");
                    out.print("</tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (int i = 0; i < lst_device.size(); i++) {
                        Object[] comp = (Object[]) lst_device.get(i);
                        out.print("<tr>");
                        out.print("<td>" + comp[2] + "</td>");
                        String linkDoc = comp[3].toString().replace("UserFiles/File/", "http://172.16.2.117:8084/REDEAC/UserFiles/File/");
                        out.print("<td>" + comp[4].toString().split(" ")[0] + "</td>");
                        out.print("<td>" + comp[5] + "</td>");
                        out.print("<td>" + linkDoc + "</td>");
//                        out.print("<td>" + linkDoc + "</td>");
                        out.print("</tr>");
                    }
                    out.print("</tbody>");
                    out.print("</table>");
                    out.print("</div>");
                } else {
                    out.print("<div class='text-center'>");
                    out.print("<h5>No se ha encontrado información</h5>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="REGISTER EVENT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nuevo evento</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Device?opt=3' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");

                out.print("<input type='hidden' name='idTypeDv' value='" + idTypeDv + "'>");
                out.print("<input type='hidden' name='idDevice' value='" + idDevice + "'>");

                out.print("<div class='text-center' style='justify-content: center;'>");
                out.print("<span class='mb-2 mt-2'><b>Fecha</b></span>");
                out.print("<input type='date' style='margin: auto;' class='form-control col-lg-8 mb-2' name='txtDte' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");

                out.print("<div class='text-center mt-4'>");
                out.print("<span class=''><b>Seleccionar tipo de proceso</b></span>");
                out.print("<div class='mt-2'>");
                lst_secuence = SecuenceJpa.ConsultSecuenceByType("Device");
                out.print("<select class='form-control col-lg-8' name='CbxDvType' style='margin: auto;' required>");
                out.print("<option value='' disabled selected >Seleccionar un tipo</option>");
                if (lst_secuence != null) {
                    for (int i = 0; i < lst_secuence.size(); i++) {
                        Object[] ObjAp = (Object[]) lst_secuence.get(i);
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

                //<editor-fold defaultstate="collapsed" desc="LIST EVENTS">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "\";cargarDatos()'><i class='fas fa-arrow-left'></i></button>");
                out.print("<h2> " + nameDevice + " </h2>");
                out.print("<div class=''>");
                out.print("<button class='btn btn-yellow mr-2' style='border-radius: 4px;' onclick='mostrarConvencion(2)'><i class=\"fas fa-folder-open\"></i></button>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                out.print("<table class='table table-bordered' id='table-2'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Fecha</th>");
                out.print("<th>Tipo</th>");
                out.print("<th>Usuario Registro</th>");
                out.print("<th>Fecha Registro</th>");
                out.print("<th>Estado</th>");
                out.print("<th>Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");

                lst_deviceHead = DeviceHead.ConsultDeviceHeaderId(idDevice);
                if (lst_deviceHead != null) {
                    for (int i = 0; i < lst_deviceHead.size(); i++) {
                        Object[] Objdevice = (Object[]) lst_deviceHead.get(i);
                        out.print("<tr>");
                        int sta = Integer.parseInt(Objdevice[4].toString());
                        out.print("<td>" + Objdevice[2] + "</td>");
                        String struc = Objdevice[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0];
                        out.print("<td><b>" + struc + "</b></td>");
                        out.print("<td>" + Objdevice[7] + "</td>");
                        out.print("<td>" + Objdevice[8] + "</td>");
                        String[] states = Objdevice[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        String[] stat = {};
                        if (sta >= states.length) {
                            if (Objdevice[9] != null) {
                                String[] dataDetail = Objdevice[9].toString().split("--");
                                out.print("<td>Pendiente documento por firmar<br> &nbsp; <i class=\"fas fa-signature\"></i> <b class='text-warning'>" + dataDetail[0] + "</b></td>");
                            } else {
                                out.print("<td>Documento Finalizado</td>");
                            }

                        } else {
                            stat = states[sta].split("/");
                            out.print("<td>" + stat[1] + "</td>");
                        }
                        out.print("<td class='text-center'>");
                        out.print("<button class='btn btn-yellow' onclick='window.location.href=\"Device?opt=1&act=3&idTypeDv=" + idTypeDv + "&idDevice=" + idDevice + "&idDeviceHead=" + Objdevice[0] + "\";cargarDatos()'><i class='fas fa-folder-open'></i></button>");
                        out.print("</td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td class='text-center' colspan='6'><span style='font-size: 20px; font-weight: bold;'>No se han encontrado eventos, puedes registrar el primero haciendo clic sobre el boton <i class='fas fa-plus'></i></span></td>");
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
            } else if (action == 1) {
                //<editor-fold defaultstate="collapsed" desc="LIST DEVICE">
                String nameDv = "";
                lst_typeDevice = DeviceJpa.ConsultTypeDeviceId(idTypeDv);
                if (lst_typeDevice != null) {
                    Object[] Objtpe = (Object[]) lst_typeDevice.get(0);
                    nameDv = Objtpe[1].toString();
                } else {
                    nameDv = "";
                }

                //<editor-fold defaultstate="collapsed" desc="DEVICE REGISTER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 50%; right: 18%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h4>Registrar Dispositivos </h4>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");

                out.print("<form action='Device?opt=2' method='post' class='needs-validation' novalidate='' onsubmit='return cargarDatosForm(this)'>");
                out.print("<input type='hidden' name='idTypeDv' id='' value='" + idTypeDv + "'>");
                out.print("<div class='d-flex' style='justify-content: space-evenly;'>");
                out.print("<div class=''>");

                out.print("<span class=''>Item</span>");
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='' style='margin: 12px -12px 12px 12px;'>");
                out.print("<select id='slctDta2' class='form-control select2' name='cbx_Item' >");
                out.print("<option selected disabled>Seleccionar</option>");
                lst_item = ItemJpa.ConsultItemAvaibleDetail();
                if (lst_item != null) {
                    for (int i = 0; i < lst_item.size(); i++) {
                        Object[] Objitm = (Object[]) lst_item.get(i);
                        out.print("<option value='" + Objitm[1] + "'> " + Objitm[1].toString() + " - " + Objitm[2].toString() + "</option>");
                    }
                } else {
                    out.print("<option value='0'></option>");
                }
                out.print("</select>");
                out.print("</div>");

                out.print("<span class=''>Cargo</span>");
                out.print("<input type='text' class='form-control' name='txt_chargue' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");

                out.print("<span class=''>Nombre</span>");
                out.print("<input type='text' class='form-control' name='txt_name' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");

                out.print("<span class=''>Serial</span>");
                out.print("<input type='text' class='form-control' name='txt_serial' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");

                out.print("<div class=''>");

                out.print("<span class=''>Consecutivo</span>");
                lst_item = ItemJpa.ConsultLastItem();
                int consec = 0;
                if (lst_item != null) {
                    Object[] Objit = (Object[]) lst_item.get(0);
                    consec = Integer.parseInt(Objit[1].toString()) + 1;
                }
                out.print("<input type='number' class='form-control' name='nmb_consec' id='' data-toggle='tooltip' data-placement='top' title='' value='" + consec + "' required>");

                out.print("<span class=''>Responsable</span>");
                out.print("<input type='text' class='form-control' name='txt_respo' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");

                out.print("<span class=''>Area</span>");
                out.print("<div class='' data-toggle='tooltip' data-placement='top' title='' style='margin: 12px -12px 12px 12px;'>");
                out.print("<select id='slctDta' class='form-control select2' name='cbx_area'>");
                out.print("<option selected disabled>Seleccionar area</option>");
                lst_area = AreaJpa.ConsultAreaActive();
                if (lst_area != null) {
                    for (int i = 0; i < lst_area.size(); i++) {
                        Object[] ObjArea = (Object[]) lst_area.get(i);
                        out.print("<option value='" + ObjArea[0] + "' data-toggle='tooltip' data-placement='top' title='prueba'>" + ObjArea[1].toString() + "</option>");
                    }
                } else {
                    out.print("<option value='0'>Error</option>");

                }
                out.print("</select>");
                out.print("</div>");

                out.print("<span class=''>Ubicación</span>");
                out.print("<input type='text' class='form-control' name='txt_location' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");
                out.print("</div>");

                out.print("<div class='text-center'>");
                out.print("<button class='btn btn-green'>Registrar</button>");
                out.print("</div>");
                out.print("</form>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="DEVICE LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Device?opt=1\";cargarDatos()'><i class='fas fa-arrow-left'></i></button>");
                out.print("<div class='text-center'><h2>Listado de dispositivos</h2><h6>" + nameDv + "</h6></div>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                out.print("</div>");

                out.print("<div class='mt-4 w-100 d-flex' style='justify-content: space-between;'>");
                //<editor-fold defaultstate="collapsed" desc="FILTER AND BUTTONS">
                int est1 = 0, est2 = 0, est3 = 0;
                lst_device = DeviceJpa.ConsultDeviceCouterIdType(idTypeDv);
                if (lst_device != null) {
                    try {
                        Object[] ObDv = (Object[]) lst_device.get(0);
                        est1 = Integer.parseInt(ObDv[0].toString());
                        est2 = Integer.parseInt(ObDv[1].toString());
                        est3 = Integer.parseInt(ObDv[2].toString());
                    } catch (Exception e) {
                    }
                }

                out.print("<div class='col-lg-4'>");
                out.print("<input type='text' class='form-control' name='' id='myInput' placeholder='Buscar...' style='border: 1px solid #afafaf;' >");
                out.print("</div>");
                out.print("<div class='col-lg-4 d-flex justify-content-end'>");
                if (steDv > 0) {
                    out.print("<button class='btn btn-green mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "\";cargarDatos()'><i class=\"fas fa-times\"></i> </button>");
                }
                out.print("<button class='btn btn-success mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "&steDv=1\";cargarDatos()'><i class=\"fas fa-clipboard-check\"></i> Bueno (" + est1 + ")</button>");
                out.print("<button class='btn btn-warning mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "&steDv=2\";cargarDatos()'><i class=\"fas fa-folder-open\"></i> Revisión (" + est2 + ")</button>");
                out.print("<button class='btn btn-danger mr-2' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + idTypeDv + "&steDv=3\";cargarDatos()'><i class=\"fas fa-folder-minus\"></i> De baja (" + est3 + ")</button>");
                out.print("</div>");
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='card-body mt-3'>");
                //<editor-fold defaultstate="collapsed" desc="LSIT DATA">
                if (steDv > 0) {
                    lst_device = DeviceJpa.ConsultDevice_type_ste(idTypeDv, steDv);
                } else {
                    lst_device = DeviceJpa.ConsultDevice_type_All(idTypeDv);
                }
                out.print("<div class='row justify-content-around' id='container'>");
                if (lst_device != null) {
                    for (int i = 0; i < lst_device.size(); i++) {
                        //<editor-fold defaultstate="collapsed" desc="SQUARE LIST">
                        Object[] ObjDev = (Object[]) lst_device.get(i);
                        int stedv = Integer.parseInt(ObjDev[5].toString());
                        out.print("<div class='col-lg-3 mb-4 mr-2 dvList single-item' style='border-bottom: 1px solid #" + ((stedv == 1) ? "63ed7a" : (stedv == 2) ? "ffa426" : "fc544b") + ";'>");
                        out.print("<div class='conscDv'>");
                        out.print("<span>" + ObjDev[3] + "</span>");
                        out.print("</div>");

                        out.print("<div class=''>");
                        out.print("<h5>" + ObjDev[4] + "</h5>");
                        out.print("</div>");

                        out.print("<div id='dvDetFront" + i + "' class='textdv' style='display: block;'>");
                        out.print("<span>Area: " + ObjDev[11] + "</span><br>");
                        out.print("<span>Ubicacion: " + ObjDev[9] + "</span><br>");
                        out.print("<span>Cargo: " + ObjDev[7] + "</span><br>");
                        out.print("<span><span class='bullet text-" + ((stedv == 1) ? "success" : (stedv == 2) ? "warning" : "danger") + "'></span> " + ((stedv == 1) ? "Bueno" : (stedv == 2) ? "Revisión" : "De baja") + "</span><br>");
                        out.print("</div>");

                        out.print("<div id='dvDetBack" + i + "' class='textdv dvBack' style='display: none;'>");
                        out.print("<span>Serial: " + ObjDev[6] + "</span><br>");
                        out.print("<span>Usuario registro: " + ObjDev[12] + "</span><br>");
                        out.print("<span>Fecha registro: " + ObjDev[13] + "</span><br>");
                        out.print("</div>");

                        out.print("<div class='gnDiv'>");
                        out.print("<div class='d-flex dvListBtn'>");
                        out.print("<a href='#' onclick='showDetail(" + i + ")'><i id='arrow" + i + "' class='fas fa-chevron-down'></i> Ver detalle</a>");
                        out.print("<button class='btn btn-green btn-sm' onclick='window.location.href=\"Device?opt=1&act=2&idTypeDv=" + idTypeDv + "&idDevice=" + ObjDev[0] + "\";cargarDatos()'><i class=\"fas fa-link\"></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                        //</editor-fold>
                    }
                } else {
                    out.print("<div class='col-lg-5 mb-4 mr-2 dvList'>");
                    out.print("<h5>No se ha encontrado información relacionada!</h5>");
                    out.print("</div>");
                }
                //</editor-fold>
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>

                //</editor-fold>
            } else if (action == 0) {
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<span class=''></span>");
                out.print("<h2>DISPOSITIVOS</h2>");
                out.print("<span class=''></span>");
                out.print("</div>");
                out.print("<div class='card-body'>");

                lst_device = DeviceJpa.ConsultAllTypeDeviceCount();

                out.print("<div class='d-flex'>");
                out.print("<div class='col-lg-6 mr-2 contDataList'>");
                //<editor-fold defaultstate="collapsed" desc="LIST LEFT">
                if (lst_device != null) {
                    out.print("<div class='d-flex justify-content-between devfx'>");
                    out.print("<h5>Tipo de dispositivo</h5>");
                    out.print("<h6>Cantidad</h6>");
                    out.print("</div>");
                    for (int i = 0; i < lst_device.size(); i++) {
                        Object[] ObjDev = (Object[]) lst_device.get(i);
                        out.print("<div class='d-flex justify-content-between devDet' onclick='window.location.href=\"Device?opt=1&act=1&idTypeDv=" + ObjDev[0] + "\";cargarDatos()'>");
                        out.print("<span>" + ObjDev[1] + "</span>");
                        out.print("<span class=''>" + ObjDev[2] + "</span>");
                        out.print("</div>");
                    }
                }
                //</editor-fold>
                out.print("</div>");

                out.print("<div class='col-lg-6'>");
                //<editor-fold defaultstate="collapsed" desc="LIST RIGHT">

                //<editor-fold defaultstate="collapsed" desc="COUNTER">
                out.print("<div class='divCount'>");
                out.print("<div class=''>");
                out.print("<h5>Estado de dispositivos</h5>");
                out.print("</div>");
                lst_device = DeviceJpa.ConsultAllStateDeviceCount();
                out.print("<div class='row DivCounter'>");
                for (int i = 0; i < lst_device.size(); i++) {
                    Object[] ObjDevice = (Object[]) lst_device.get(i);
                    out.print("<div class='col-lg-3 mr-2 boxCount " + ((ObjDevice[0].toString().equals("Bueno")) ? "bgGreen" : (ObjDevice[0].toString().equals("Revision")) ? "bgOrange" : "bgRed") + "'>");
                    out.print("<h2>" + ObjDevice[2] + "</h2>");
                    out.print("<p>" + ObjDevice[0] + "</p>");
                    out.print("</div>");
                }
                out.print("</div>");
                out.print("</div>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="LIST DOC">
                out.print("<div class='mt-4 divList'>");

                out.print("<div class=''>");
                out.print("<h5>Documentos en proceso</h5>");
                out.print("</div>");

                out.print("<table class='table-sm w-100'>");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Dispositivo</th>");
                out.print("<th>Documento</th>");
//                out.print("<th>Estado</th>");
                out.print("<th>Ver</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_DeviceDetail = DeviceDetailJpa.ConsultDeviceDocumentsInProcess();
                if (lst_DeviceDetail != null) {
                    for (int i = 0; i < lst_DeviceDetail.size(); i++) {
                        Object[] ObjDev = (Object[]) lst_DeviceDetail.get(i);
                        out.print("<tr>");
                        out.print("<td>" + ObjDev[3] + " <br> <b class='text-dark' style='font-size: 12px;'>" + ObjDev[4] + "</b> </td>");
                        out.print("<td>" + ObjDev[5].toString().split("/")[1] + " <br> <b class='text-dark' style='font-size: 12px;'>" + ObjDev[6] + "</b></td>");
                        out.print("<td><button class='btn btn-info btn-sm' onclick='window.location.href=\"Device?opt=1&act=4&idTypeDv=" + ObjDev[7] + "&idDevice=" + ObjDev[2] + "&idDoc=" + ObjDev[5].toString().split("/")[0] + "&idDeviceHead=" + ObjDev[1] + "&type=" + ObjDev[5] + "&step=1\";cargarDatos()'><i class='fas fa-arrow-right'></i></button></td>");
                        out.print("</tr>");
                    }
                } else {
                    out.print("<tr>");
                    out.print("<td colspan='4'>Al dia! <br> No hay documentos en proceso</td>");
                    out.print("</tr>");
                }

                out.print("</tbody>");
                out.print("</table>");

                out.print("</div>");
                //</editor-fold>

                //</editor-fold>
                out.print("</div>");
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
            Logger.getLogger(Tag_device.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
