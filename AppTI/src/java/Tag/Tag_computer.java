package Tag;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import Controller.ComputerControllerJpa;
import Controller.SettingControllerJpa;
import Controller.ComputerHeaderControllerJpa;
import Controller.ComputerDetailJpaController;
import Controller.FormatControllerJpa;
import Controller.ItemJpaController;
import SQL.ConnectionsBd;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tag_computer extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        ComputerControllerJpa ComputerJpa = new ComputerControllerJpa();
        SettingControllerJpa SettingJpa = new SettingControllerJpa();
        ComputerDetailJpaController ComDetail = new ComputerDetailJpaController();
        ComputerHeaderControllerJpa ComputerHeaderJpa = new ComputerHeaderControllerJpa();
        FormatControllerJpa FormatJpa = new FormatControllerJpa();
        ItemJpaController ItemJpa = new ItemJpaController();
        ConnectionsBd ConnectJpa = new ConnectionsBd();
        LocalDate fecha = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String CurrentDate = fecha.format(formato);

        List lst_computer = null, lst_computerId = null, lst_computerHeader = null, lst_setting = null, lst_compDetail = null;
        List lst_format = null;
        List lst_item = null;
        List lst_connect = null;
        int module = 0, IdComputer = 0, StateCmp = 0, state = 0;
        String usuario = "", area = "";
        String[] structure = {};

        boolean isActive = false;
        boolean hvInfo = false;

        try {
            try {
                module = Integer.parseInt(pageContext.getRequest().getAttribute("mod").toString());
            } catch (NumberFormatException e) {
                module = 0;
            }
            try {
                IdComputer = Integer.parseInt(pageContext.getRequest().getAttribute("IdComputer").toString());
            } catch (NumberFormatException e) {
                IdComputer = 0;
            }

            String NroPC = "";

            lst_computer = ComputerJpa.ConsulteComputerIdPC(IdComputer);
            if (lst_computer != null) {
                Object[] ObjCompx = (Object[]) lst_computer.get(0);
                NroPC = ObjCompx[1].toString();
            } else {
                NroPC = "-x-x NO ID PC x-x-";
            }

            //<editor-fold defaultstate="collapsed" desc="CONSULT ASIGNMENT INFO">
            lst_compDetail = ComDetail.ConsultLastAsignmentbyPc(IdComputer);
            if (lst_compDetail != null) {
                Object[] ObjDetail = (Object[]) lst_compDetail.get(0);
                String[] strc = ObjDetail[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                area = strc[3].toString();;
                usuario = strc[9].toString();
                hvInfo = true;
            } else {
                hvInfo = false;
            }
//</editor-fold>

            if (module == 3) {
                //<editor-fold defaultstate="collapsed" desc="PC DOCUMENT">

                //<editor-fold defaultstate="collapsed" desc="VARIABLE">
                int idDoc = 0, idPcHead = 0, docx = 0, codx = 0, stet = 0, idItem = 0;
                String nameDoc = "", code = "", format = "", type = "", SigMode = "";

                try {
                    idPcHead = Integer.parseInt(pageContext.getRequest().getAttribute("idPcHead").toString());
                } catch (Exception e) {
                    idPcHead = 0;
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
                lst_computerHeader = ComputerHeaderJpa.ConsulteComputerHeader(idPcHead);
                if (lst_computerHeader != null) {
                    Object[] ObjComp = (Object[]) lst_computerHeader.get(0);
                    stet = Integer.parseInt(ObjComp[4].toString());
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
                lst_compDetail = ComDetail.ConsultComputerDetailxPCxType(idPcHead, typeSc[0] + "/" + typeSc[1]); // CONSULTA PRINCIPAL DEL DETALLE
                Object[] ObjDetail = {};
                int stetx = 0, idDetail = 0;
                String contentHtml = "";
                String singExits = "";
                try {
                    ObjDetail = (Object[]) lst_compDetail.get(0);
                    idDetail = Integer.parseInt(ObjDetail[0].toString());
                    stetx = Integer.parseInt(ObjDetail[6].toString());
                    try {
                        singExits = ObjDetail[5].toString();
                    } catch (Exception e) {
                        singExits = "";
                    }

                    if (code.contains("-004") || code.contains("-029") || code.contains("-031") || code.contains("-032")) {
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
                                    if (code.contains("-004") || code.contains("-029") || code.contains("-031") || code.contains("-032")) {
//                                        format = contentHtml;
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
                        format = format.replace("XXXElaboradorXXX", "<b class='text-warning'>Pendiente Firma</b>");
                        format = format.replace("XXXUsuarioXXX", "<b class='text-warning'>Pendiente Firma</b>");
                        format = format.replace("XXXJefe o DirectorXXX", "<b class='text-warning'><b class='text-warning'>Pendiente Firma</b></b>");
                    }

                } catch (Exception e) {
                    stetx = 99;
                    idDetail = 0;
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
                            out.print("<form action='Computer?opt=1&mod=3&type=" + type + "&idpcHead=" + idPcHead + "&IdComputer=" + IdComputer + "' method='post' class='needs-validation' novalidate=''>");
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
                                    out.print("<button class='btn btn-green' onclick='window.location.href=\"" + ObjStt[2] + "\"'>Registrar firma <i class='fas fa-signature'></i></button>");
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
                                    out.print("<button class='btn btn-green' onclick='window.location.href=\"" + ObjStt[2] + "\"'>Registrar firma <i class='fas fa-signature'></i></button>");
                                    out.print("</div>");
                                    out.print("</div>");
                                }
                            } else {
                                out.print("<form action='Computer?opt=8&mod=3&type=" + type + "&idpcHead=" + idPcHead + "&IdComputer=" + IdComputer + "' method='post' class='needs-validation' novalidate=''>");

                                out.print("<input type='hidden' class='form-control' name='idDetail' id='' value='" + ObjDetail[0] + "'>");
                                out.print("<input type='hidden' class='form-control' name='NmbDoc' id='' value='" + docx + "'>");
                                out.print("<input type='hidden' class='form-control' name='NmbCod' id='' value='" + codx + "'>");
                                out.print("<input type='hidden' class='form-control' name='idSignature' id='' value='" + ObjCnn[0].toString().replace("[", "") + "'>");
                                out.print("<input type='hidden' class='form-control' name='SigMode' id='idSigMode' value='" + SigMode + "'>");

                                out.print("<div class='canvas-container'>");
                                out.print("<div class='signature-pad mt-2 d-flex' style='justify-content: center;'>");
                                out.print("<canvas id='miCanvas' width='400' height='200'></canvas>");
                                out.print("<div class=''>");
                                out.print("<button type='button' class='btn btn-info ml-2' onclick=\"limpiarCanvas('signature-canvas')\"><i class='fas fa-sync-alt'></i></button>");
                                out.print("</div>");
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
                            out.print("<form action='Computer?opt=1&mod=3&type=" + type + "&idpcHead=" + idPcHead + "&IdComputer=" + IdComputer + "' method='post' class='needs-validation' novalidate=''>");
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
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Computer?opt=1&IdComputer=" + IdComputer + "&idpcHead=" + idPcHead + "&mod=2\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<div class='text-center'>");
                out.print("<h4>Documentacion " + NroPC + "</h4><h1>" + typeSc[1] + "</h1><h4>" + nameDoc + "</h4>");
                out.print("</div>");

                out.print("<div class='d-flex'>");
                if (stetx != 99) {
                    if (singExits.contains("XX")) {
                        out.print("<button class='btn btn-warning mr-2' style='border-radius: 4px;' onclick='mostrarConvencion(3)'><i class='fas fa-signature'></i></button>");
                    }
                    if (stetx == 0 && (code.equals("A") || code.contains("-019"))) {
                        out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Computer?opt=5&IdComputer=" + IdComputer + "&idpcHead=" + idPcHead + "&idDetail=" + ObjDetail[0] + "&type=" + type + "&mod=2&xtemp=1\"'><i class='fas fa-share'></i></button>");
                    } else if (stetx == 1) {
                        out.print("<button class='btn btn-green mr-2' style='border-radius: 4px;' onclick='window.location.href=\"Computer?opt=5&IdComputer=" + IdComputer + "&idpcHead=" + idPcHead + "&idDetail=" + ObjDetail[0] + "&type=" + type + "&mod=2&xtemp=1\"'><i class='fas fa-share'></i></button>");
                    }
                } else {
                    out.print("<span> </span>");
                }
                out.print("</div>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="LOAD DOCUMENT">
                if (code.equals("A")) {
                    //<editor-fold defaultstate="collapsed" desc="ATTACH FILES">
                    if (lst_compDetail == null) {
                        //<editor-fold defaultstate="collapsed" desc="NEW UPLOAD">
                        lst_setting = SettingJpa.ConsultSettingId(74);
                        if (lst_setting != null) {
                            Object[] ObjStt = (Object[]) lst_setting.get(0);
                            String[] docs = ObjStt[2].toString().split("///");
                            out.print("<form action='Attach.jsp' method='post' class='needs-validation' novalidate='' enctype='multipart/form-data'>");

                            out.print("<input type='hidden' name='IdComputer' value='" + IdComputer + "'>");
                            out.print("<input type='hidden' name='idpcHead' value='" + idPcHead + "'>");
                            out.print("<input type='hidden' name='typeDoc' value='" + type + "'>");
                            out.print("<input type='hidden' class='form-control' name='txtNameCat' id='pruebas'>");

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
                        Object[] ObjD = (Object[]) lst_compDetail.get(0);
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

                            out.print("<form action='Attach.jsp' method='post' class='needs-validation' novalidate='' enctype='multipart/form-data'>");
                            out.print("<input type='hidden' name='IdComputer' value='" + IdComputer + "'>");
                            out.print("<input type='hidden' name='idPcHead' value='" + idPcHead + "'>");
                            out.print("<input type='hidden' name='typeDoc' value='" + type + "'>");
                            out.print("<input type='hidden' class='form-control' name='txtNameCat' id='xpr" + DataFiles[0] + "'>");
                            out.print("<input type='hidden' class='form-control' name='fileDelete' value='" + DataFiles[1] + "'>");
                            out.print("<input type='hidden' name='allDocs' value='" + allDocs + "'>");
                            out.print("<input type='hidden' name='idDetail' value='" + ObjD[0] + "'>");

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
                    if (lst_compDetail != null) {
                        //<editor-fold defaultstate="collapsed" desc="ITEM ASIGNED">
                        Object[] ObjCom = (Object[]) lst_compDetail.get(0);
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

//                            out.print("<form action='Computer?opt=6' method='post' id='formConfirmItem'>");
//                            out.print("<input type='text' name='IdComputer' value='" + IdComputer + "'>");
//                            out.print("<input type='text' name='idpcHead' value='" + idPcHead + "'>");
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
                        out.print("<form action='Computer?opt=1&mod=3&IdComputer=" + IdComputer + "&idDoc=" + idDoc + "&idpcHead=" + idPcHead + "&type=" + type + "' method='post' class='needs-validation' novalidate='' id='formSearchItem'>");
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

                                out.print("<form action='Computer?opt=6' method='post' id='formConfirmItem'>");
                                out.print("<input type='hidden' name='IdComputer' value='" + IdComputer + "'>");
                                out.print("<input type='hidden' name='idpcHead' value='" + idPcHead + "'>");
                                out.print("<input type='hidden' name='type' value='" + type + "'>");
                                out.print("<input type='hidden' name='cbxItem' value='" + idItem + "'>");
                                out.print("</form>");

                                out.print("<div class='text-center'>");
                                out.print("<button class='btn btn-green' onclick='formConfirmItem.submit()'>Confirmar</button>");
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
                    lst_computer = ComputerJpa.ConsultInfoComputerId(IdComputer);
                    Object[] ObInfo = (Object[]) lst_computer.get(0);
                    if (lst_compDetail != null) {
                        //<editor-fold defaultstate="collapsed" desc="CONSULT DOCUMENT">
                        Object[] ObjFormat = (Object[]) lst_compDetail.get(0);
                        String[] DtaFormat = ObjFormat[4].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                        //<editor-fold defaultstate="collapsed" desc="REPLACE DATA">
                        format = format.replace("XXXTIPOOXXX", ObInfo[2].toString())
                                .replace("XXXAMRCAXXX", ObInfo[3].toString())
                                .replace("XXXMODELOXXX", ObInfo[4].toString())
                                .replace("XXXSERIALXXX", ObInfo[5].toString())
                                .replace("XXXITEMXXX", ObInfo[6].toString());
                        format = format.replace("XXXNROPCXXX", NroPC);
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

//                        en este momento se viene el remplazo de las firmas realizadas y validacion del modulo en general
//                        try {
//                            String[] usrs = ObjFormat[5].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
//                            for (int i = 0; i < usrs.length; i++) {
//                                //<editor-fold defaultstate="collapsed" desc="BUILD SINATURES BY DOC">
//                                String[] usrx = usrs[i].split("/");
//                                String temId = usrx[1].toString();
//                                if (temId.equals("XX")) {
//                                    format = format.replace("XXX" + usrx[0] + "XXX", "<b class='text-warning'>Pendiente Firma</b>");
//                                } else {
//                                    int idSigx = Integer.parseInt(temId.toString());
//                                    List lst_signa = ConnectJpa.Consultar_firmas(idSigx);
//                                    if (lst_signa != null) {
//                                        String[] ObjSi = lst_signa.toString().split("///");
//                                        format = format.replace("XXX" + usrx[0] + "XXX", "<canvas id='signaCanvas" + i + "' width='200' height='100' style='border: 1px solid #fff;'></canvas>");
//                                        String json = ObjSi[3].toString();
//                                        out.print("<input type='hidden' class='form-control' name='' id='coor" + i + "' value='" + json + "'>");
//                                        post_script += "<script>"
//                                                + " function dibujarCoordenadas" + i + "() { "
//                                                + "            const canvas = document.getElementById('signaCanvas" + i + "'); "
//                                                + "            const ctx = canvas.getContext('2d'); "
//                                                + "            const coordenadas = JSON.parse(document.getElementById('coor" + i + "').value); "
//                                                + "            const escalaX = canvas.width / 400; "
//                                                + "            const escalaY = canvas.height / 200; "
//                                                + "            ctx.clearRect(0, 0, canvas.width, canvas.height); "
//                                                + "            coordenadas.forEach(coord => { "
//                                                + "                ctx.beginPath(); "
//                                                + "                ctx.moveTo(coord.lx * escalaX, coord.ly * escalaY); "
//                                                + "                ctx.lineTo(coord.mx * escalaX, coord.my * escalaY); "
//                                                + "                ctx.strokeStyle = 'black'; "
//                                                + "                ctx.lineWidth = 2; "
//                                                + "                ctx.stroke(); "
//                                                + "            }); "
//                                                + "        } "
//                                                + " "
//                                                + " window.addEventListener('load', dibujarCoordenadas" + i + "); "
//                                                + "</script>";
//                                    } else {
//                                        format = format.replace("XXX" + usrx[1] + "XXX", "<b class='text-warning'>Pendiente Firma</b>");
//                                    }
//                                }
//                                //</editor-fold>
//                            }
//
//                        } catch (Exception e) {
//                            format = format.replace("XXXElaboradorXXX", "<b class='text-warning'>Pendiente Firma</b>");
//                            format = format.replace("XXXUsuarioXXX", "<b class='text-warning'>Pendiente Firma</b>");
//                            format = format.replace("XXXJefe o DirectorXXX", "<b class='text-warning'><b class='text-warning'>Pendiente Firma</b></b>");
//                        }
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
                        format = format.replace("name=\"textcal\" value=\"" + DtaFormat[18].toString() + "\"", "name=\"textcal\" value=\"" + DtaFormat[18].toString() + "\" checked");
                        format = format.replace("name=\"textFll\" value=\"" + DtaFormat[19].toString() + "\"", "name=\"textFll\" value=\"" + DtaFormat[19].toString() + "\" checked");

                        format = format.replace("name=\"textcal\"", "name=\"textcal\" disabled");
                        format = format.replace("name=\"textFll\"", "name=\"textFll\" disabled");
                        //</editor-fold>

                        out.print(format);
                        out.print(post_script);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="NEW REGISTER">
                        if (lst_computer != null) {
                            out.print("<button class='btn btn-green' style='position: fixed; bottom: 12px; right: 71px;' onclick='formR03.submit()'>Guardar <i class='fas fa-save'></i></button>");

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
//                                out.print("<div class='col-lg-6' data-toggle='tooltip' data-placement='top' title=''>");
//                                out.print("<select class='form-control select2' name='cbx' style='margin-12px;'>");
//                                out.print("<option selected disabled>Seleccionar </option>");
//                                for (int i = 0; i < lst_item.size(); i++) {
//                                    Object[] ObjItm = (Object[]) lst_item.get(i);
//                                    out.print("<option value='" + ObjItm[1] + "/" + ObjItm[2] + "/" + ObjItm[3] + "/" + ObjItm[4] + "/" + ObjItm[5] + "'>" + ObjItm[5] + " / " + ObjItm[1] + "</option>");
//                                }
//                                out.print("</select>");
//                                out.print("</div>");
//                                

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
                            out.print("</tbody>");
                            out.print("</table>");

                            out.print("</div>");
                            out.print("</div>");
                            out.print("</div>");

                            //</editor-fold>
                            //<editor-fold defaultstate="collapsed" desc="REPLACE DATA">
                            format = format.replace("XXXCARGOXXX", "<input type='text' class='form-control' name='txt_post' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXAREAXXX", "<input type='text' class='form-control' name='txt_area' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXUBICACIONXXX", "<input type='text' class='form-control' name='txt_location' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXBOSSNAMEXXX", "<input type='text' class='form-control col-lg-2' name='txt_bossname' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXNAMEXXX", "<input type='text' class='form-control col-lg-2' name='txt_name' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXCEDULAXXX", "<input type='text' class='form-control col-lg-2' name='txt_indentity' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXLOCATIONXXX", "<input type='text' class='form-control col-lg-2' name='txt_place' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXUSERPCXXX", "<input type='text' class='form-control col-lg-2' name='txt_user' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXDIAXXX", "<input type='number' class='form-control col-lg-2' name='txt_day' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXMESXXX", "<input type='number' class='form-control col-lg-2' name='txt_month' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXANIOXXX", "<input type='number' class='form-control col-lg-2' name='txt_anio' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>")
                                    .replace("XXXCOLUMM1XXX", "<input type='text' class='form-control' name='txt_comm1' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM2XXX", "<input type='text' class='form-control' name='txt_comm2' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM3XXX", "<input type='text' class='form-control' name='txt_comm3' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM4XXX", "<input type='text' class='form-control' name='txt_comm4' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >")
                                    .replace("XXXCOLUMM5XXX", "<input type='text' class='form-control' name='txt_comm5' id='' data-toggle='tooltip' data-placement='top' title=''  value=''  required >");
                            format = format.replace("XXXNROPCXXX", NroPC);
                            format = format.replace("XXXTIPOOXXX", ObInfo[2].toString())
                                    .replace("XXXAMRCAXXX", ObInfo[3].toString())
                                    .replace("XXXMODELOXXX", ObInfo[4].toString())
                                    .replace("XXXSERIALXXX", ObInfo[5].toString())
                                    .replace("XXXITEMXXX", ObInfo[6].toString());
                            format = format.replace("XXXPLUS1XXX", "<button type='buitton' class='btn btn-green btn-sm' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button>");
                            format = format.replace("XXXPLUS2XXX", "<button type='buitton' class='btn btn-green btn-sm' onclick='mostrarConvencion(2)'><i class='fas fa-plus'></i></button>");

                            format = format.replace("XXXElaboradorXXX", "<b class='text-warning'>Pendiente Firma</b>");
                            format = format.replace("XXXUsuarioXXX", "<b class='text-warning'>Pendiente Firma</b>");
                            format = format.replace("XXXJefe o DirectorXXX", "<b class='text-warning'>Pendiente Firma</b>");
//</editor-fold>

                            //<editor-fold defaultstate="collapsed" desc="FORM TO REGISTER">
                            out.print("<form action='Computer?opt=7&IdComputer=" + IdComputer + "&idpcHead=" + idPcHead + "&type=" + type + "' method='post' class='needs-validation' novalidate='' id='formR03'>");
                            out.print(format);
                            out.print("<input type='hidden' class='form-control' name='txt_soft' id='infoOculta' >");
                            out.print("<input type='hidden' class='form-control' name='txt_otherItem' id='infoField' >");
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
                } else if (code.contains("-004") || code.contains("-029") || code.contains("-031") || code.contains("-032")) {
                    //<editor-fold defaultstate="collapsed" desc="PREVENTIVE MAINTENANCE 004 // INSTALLED PROGRAMS 029">
                    if (contentHtml.equals("")) {
                        format = format.replace("XXXRealizadoXXX", "<b class='text-warning'>Firma Realizado</b>");
                        format = format.replace("XXXUsuarioXXX", "<b class='text-warning'>Firma Usuario</b>");

                        format = format.replace("XXXTurno1XXX", "<b class='text-warning'>Firma Turno1</b>");
                        format = format.replace("XXXTurno2XXX", "<b class='text-warning'>Firma Turno2</b>");
                        format = format.replace("XXXTurno3XXX", "<b class='text-warning'>Firma Turno3</b>");
                        format = format.replace("XXXXJefeXXX", "<b class='text-warning'>Firma Jefe</b>");
                    } else {
//                        format = contentHtml;
                    }
                    format = format.replace("XXXDATEXXX", CurrentDate);
                    format = format.replace("XXXAREAXXX", area);
                    format = format.replace("XXXUSUARIOXXX", usuario);
                    format = format.replace("XXXEQUIPOXXX", NroPC);
                    if (stetx == 2) {
                        format = format.replace("id=\"idtabla\"", "id=\"idtabla\" class='inactive004'");
                    }
                    out.print(format);
                    out.print(post_script);

                    if (stetx == 1) {
                        out.print("<form action='Computer?opt=9&IdComputer=" + IdComputer + "&idDetail=" + idDetail + "&idpcHead=" + idPcHead + "&type=" + type + "' method='post' id='Form04'>");
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
            } else if (module == 2) {
                //<editor-fold defaultstate="collapsed" desc="PC DETAIL">
                int idPcHead = 0;
                try {
                    idPcHead = Integer.parseInt(pageContext.getRequest().getAttribute("idPcHead").toString());
                } catch (Exception e) {
                    idPcHead = 0;
                }

                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between;'>");
                out.print("<button class='btn btn-green' style='border-radius: 4px;' onclick='window.location.href=\"Computer?opt=1&IdComputer=" + IdComputer + "&mod=1\"'><i class='fas fa-arrow-left'></i></button>");
                out.print("<div class='text-center'>");
                out.print("<h4>Documentacion</h4><h2>" + NroPC + "</h2>");
                out.print("</div>");
                out.print("<span class=''></span>");
                out.print("</div>");
                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");

                lst_compDetail = ComputerHeaderJpa.ConsulteComputerHeader(idPcHead);
                if (lst_compDetail != null) {
                    Object[] ObjPc = (Object[]) lst_compDetail.get(0);
                    structure = ObjPc[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");

                    out.print("<div class='card'>");
                    out.print("<div class=\"row mt-4\" style='width: 100%; justify-content: center;'>");
                    out.print("<div class=\"col-12\">");
                    out.print("<div class=\"wizard-steps\" style='display: flex; flex-wrap: wrap; justify-content: center;'>");

                    state = Integer.parseInt(ObjPc[4].toString());
                    for (int i = 1; i < structure.length; i++) {
                        String[] idxnamexico = structure[i].toString().split("/");
                        int id = 0;
                        String name = idxnamexico[1].toString();
                        String ico = idxnamexico[2].toString();
                        if (!idxnamexico[0].toString().equals("A")) {
                            id = Integer.parseInt(idxnamexico[0].toString());
                        }

                        if (i == state) {
                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + IdComputer + "&idDoc=" + id + "&idpcHead=" + idPcHead + "&type=" + structure[i] + "&step=" + i + "\"' style='background: #33bf98; color:#0b0025; cursor: pointer;' data-toggle='tooltip' data-placement='top' title='En proceso'>");
//                            out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp=\"' style='background: #33bf98; color:#0b0025; cursor: pointer;' data-toggle='tooltip' data-placement='top' title='En proceso'>");
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
                            lst_compDetail = ComDetail.ConsultComputerDetailxPCxType(idPcHead, name);
                            if (lst_compDetail != null) {
                                Object[] ObSt = (Object[]) lst_compDetail.get(0);
                                if (ObSt[5].toString().contains("XX")) {
                                    out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + IdComputer + "&idDoc=" + id + "&idpcHead=" + idPcHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
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
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + IdComputer + "&idDoc=" + id + "&idpcHead=" + idPcHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
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
//                                    out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"AppDetail?opt=1&mod=3&idApp\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
                                        out.print("<div class=\"wizard-step wizard-step-active addStepCls\" onclick='window.location.href=\"Computer?opt=1&mod=3&IdComputer=" + IdComputer + "&idDoc=" + id + "&idpcHead=" + idPcHead + "&type=" + structure[i] + "&step=" + i + "\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
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
//                        out.print("<div class=\"circleDiv\" onclick='window.location.href=\"AppDetail?opt=8&mod=3&idApp\"' style=' cursor: pointer;'  data-toggle='tooltip' data-placement='top' title='Realizado'>");
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
                //<editor-fold defaultstate="collapsed" desc="PC - LIST EVENTS">

                //<editor-fold defaultstate="collapsed" desc="REGISTER EVENT">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral' style='width: 44%;right: 22%;'>");
                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h2>Nuevo proceso</h2>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");
                out.print("<div class='cont_form_user'>");
                out.print("<div class=''>");
                out.print("<form action='Computer?opt=3&IdComputer=" + IdComputer + "&mod=1' method='post' class=''>");
                out.print("<div class='text-center' style='justify-content: center;'>");
                out.print("<span class='mb-2 mt-2'><b>Fecha</b></span>");
                out.print("<input type='date' style='margin: auto;' class='form-control col-lg-8 mb-2' name='txtDte' id='' data-toggle='tooltip' data-placement='top' title='' value='' required>");
                out.print("</div>");
                out.print("<div class='text-center mt-4'>");
                out.print("<span class=''><b>Seleccionar tipo de proceso</b></span>");
                out.print("<div class='mt-2'>");
                lst_setting = SettingJpa.ConsultSettingCategorie("PcType");
                out.print("<select class='form-control col-lg-8' name='CbxPcType' style='margin: auto;' required>");
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
                out.print("</div>");
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="LIST EVENTS">
//                lst_computerId = ComputerJpa.ConsulteComputerIdPC(IdComputer);
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");
                out.print("<div class='card-header' style='justify-content: space-between; align-items:flex-start'>");
                out.print("<p></p>");
//                if (lst_computerId != null) {
//                    Object[] ObjComputer = (Object[]) lst_computerId.get(0);
                out.print("<h2>" + NroPC + "</h2>");
//                } else {
//                    out.print("<h2>Fallo en consulta</h2>");
//                }
                out.print("<div><button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button></div>");
                out.print("</div>");

                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                out.print("<table class=\"table table-bordered\" id=\"table-1\">");
                out.print("<thead>");
                out.print("<tr>");
                out.print("<th>Fecha</th>");
                out.print("<th>Tipo</th>");
                out.print("<th>Usuario Registro</th>");
                out.print("<th>Fecha Registro</th>");
                out.print("<th>Estado</th>");
                out.print("<th class=\"text-center\">Opc</th>");
                out.print("</tr>");
                out.print("</thead>");
                out.print("<tbody>");
                lst_computerHeader = ComputerHeaderJpa.ConsulteComputerHeaderIdCmp(IdComputer);
                if (lst_computerHeader != null) {
                    for (int i = 0; i < lst_computerHeader.size(); i++) {
                        Object[] ObjHeader = (Object[]) lst_computerHeader.get(i);
                        out.print("<tr>");
                        int sta = Integer.parseInt(ObjHeader[4].toString());
                        out.print("<td>" + ObjHeader[2] + "</td>");
                        String struc = ObjHeader[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///")[0];
                        out.print("<td><b>" + struc + "</b></td>");
                        out.print("<td>" + ObjHeader[7] + "</td>");
                        out.print("<td>" + ObjHeader[8] + "</td>");
                        String[] states = ObjHeader[3].toString().replace("][", "///").replace("[", "").replace("]", "").split("///");
                        String[] stat = {};
                        if (sta >= states.length) {
                            if (ObjHeader[9] != null) {
                                String[] dataDetail = ObjHeader[9].toString().split("--");
                                out.print("<td>Pendiente documento por firmar<br> &nbsp; <i class=\"fas fa-signature\"></i> <b class='text-warning'>" + dataDetail[0] + "</b></td>");
                            } else {
                                out.print("<td>Documento Finalizado</td>");
                            }

                        } else {
                            stat = states[sta].split("/");
                            out.print("<td>" + stat[1] + "</td>");
                        }
                        out.print("<td class='text-center'>");
                        out.print("<button class='btn btn-yellow' onclick='window.location.href=\"Computer?opt=1&IdComputer=" + IdComputer + "&idpcHead=" + ObjHeader[0] + "&mod=2\"'><i class='fas fa-folder-open'></i></button>");
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
                out.print("</div>");
                out.print("</section>");
                //</editor-fold>

                //</editor-fold>
            } else if (module == 0) {
                //<editor-fold defaultstate="collapsed" desc="MAIN LIST">
                try {
                    StateCmp = Integer.parseInt(pageContext.getRequest().getAttribute("StateCmp").toString());
                } catch (NumberFormatException e) {
                    StateCmp = 99;
                }
                if (IdComputer > 0) {
                    //<editor-fold defaultstate="collapsed" desc="COMPUTER UPDATE">
                    out.print("<div class='sweet-local' tabindex='-1' id='Ventana2' style='opacity: 1.03; display:block;'>");
                    out.print("<div class='contGeneral'>");

                    out.print("<div style='display: flex; justify-content: space-between'>");
                    out.print("<h3>Modificar PC</h3>");
                    out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(2)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                    out.print("</div>");

                    out.print("<div class='cont_form_user'>");
                    lst_computerId = ComputerJpa.ConsulteComputerIdPC(IdComputer);
                    if (lst_computerId != null) {
                        Object[] ObjComputerId = (Object[]) lst_computerId.get(0);
                        out.print("<form action='Computer?opt=2' method='post' class='needs-validation' novalidate=''>");
                        out.print("<input type='hidden' name='IdComputer' value='" + IdComputer + "'>");
                        out.print("<input type='hidden' name='StateCmp' value='" + StateCmp + "'>");
                        out.print("<div  class='d-flex justify-content-between w-100'>");
                        out.print("<div class='col-6'>"
                                + "<input type='text' class='form-control' name='txtPC' value='" + ObjComputerId[1] + "' placeholder='# Equipo' data-toggle='tooltip' data-placement='top' title='# Equipo' required>"
                                + "<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div></div>");
                        out.print("<div class='col-6'>"
                                + "<input type='email' class='form-control' name='txtMail' value='" + ObjComputerId[12] + "' placeholder='Correo' data-toggle='tooltip' data-placement='top' title='Correo' required>"
                                + "<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div></div>");
                        out.print("</div>");

                        out.print("<div class='col-12' style='margin:12px;' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                        out.print("<textarea class=\"form-control\" id='editorNextReg' placeholder=\"Descripción ...\" name='txtDescription' required>" + ObjComputerId[9] + "</textarea>"
                                + "<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                        out.print("</div>");

                        out.print("<div class='' style='width: 100%; text-align:center;'>");
                        out.print("<button class='btn btn-green btn-lg'>Modificar</button>");
                        out.print("</div>");

                        out.print("</form>");
                    }
                    out.print("</div>");

                    out.print("</div>");
                    out.print("</div>");

                    //</editor-fold>
                }
                //<editor-fold defaultstate="collapsed" desc="COMPUTER REGISTER">
                out.print("<div class='sweet-local' tabindex='-1' id='Ventana1' style='opacity: 1.03; display:none;'>");
                out.print("<div class='contGeneral'>");

                out.print("<div style='display: flex; justify-content: space-between'>");
                out.print("<h3>Registar PC</h3>");
                out.print("<button class='btn btn-outline-secondary' onclick='mostrarConvencion(1)' style='height: 30px;padding: 3px;width: 30px;'><i class='fas fa-times'></i></button>");
                out.print("</div>");

                out.print("<div class='cont_form_user'>");
                out.print("<form action='Computer?opt=2' method='post' class='needs-validation' novalidate=''>");
                out.print("<input type='hidden' name='StateCmp' value='" + StateCmp + "'>");
                out.print("<div  class='d-flex justify-content-between w-100'>");
                out.print("<div class='col-6'>"
                        + "<input type='text' class='form-control' name='txtPC' placeholder='# Equipo' data-toggle='tooltip' data-placement='top' title='# Equipo' required>"
                        + "<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div></div>");
                out.print("<div class='col-6'>"
                        + "<input type='email' class='form-control' name='txtMail' placeholder='Correo' data-toggle='tooltip' data-placement='top' title='Correo' required>"
                        + "<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div></div>");
                out.print("</div>");

                out.print("<div class='col-12' style='margin:12px;' data-toggle='tooltip' data-placement='top' title='Descripción'>");
                out.print("<textarea class=\"form-control\" id='editorNextReg' placeholder=\"Descripción ...\" name='txtDescription' required></textarea>"
                        + "<div class='invalid-feedback invalid_data'><i class='fas fa-exclamation-circle'></i>&nbsp;&nbsp; Debe ingresar un valor!</div>");
                out.print("</div>");

                out.print("<div class='' style='width: 100%; text-align:center;'>");
                out.print("<button class='btn btn-green btn-lg'>Registrar</button>");
                out.print("</div>");

                out.print("</form>");
                out.print("</div>");

                out.print("</div>");
                out.print("</div>");

                //</editor-fold>
                out.print("<section class='section'>");
                out.print("<div class='section-body'>");
                out.print("<div class='row'>");
                out.print("<div class='col-12'>");
                out.print("<div class='card'>");

                out.print("<div class='card-header' style='justify-content: space-between; align-items:flex-start'>");
                out.print("<p></p>");
                out.print("<h2>PC</h2>");
                out.print("<div><button class='btn btn-green' style='border-radius: 4px;' onclick='mostrarConvencion(1)'><i class='fas fa-plus'></i></button></div>");
                out.print("</div>");

                out.print("<div class='card-body'>");
                out.print("<div class='table-responsive'>");
                if (StateCmp != 99) {
                    lst_computer = ComputerJpa.ConsulteComputerState(StateCmp);
                } else {
                    lst_computer = ComputerJpa.ConsulteComputer();
                }
                if (lst_computer != null) {
                    out.print("<div class='d-flex justify-content-between mb-2 align-items-baseline'>");
                    out.print("<div class=\"form-group text-center mb-0\">\n"
                            + "                      <div class=\"selectgroup selectgroup-pills\">\n"
                            + "                        <label class=\"selectgroup-item\">\n"
                            + "                          <input type=\"radio\" onclick=\"javascript:location.href='Computer?opt=1&StateCmp=1'\" name=\"icon-input\" value=\"1\" class=\"selectgroup-input\" " + ((StateCmp == 1) ? "checked" : "") + ">\n"
                            + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-success\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='Bueno'><i style='font-size:17px' class=\"fas fa-thumbs-up\"></i></span>\n"
                            + "                        </label>\n"
                            + "                        <label class=\"selectgroup-item\">\n"
                            + "                          <input type=\"radio\" onclick=\"javascript:location.href='Computer?opt=1&StateCmp=2'\" name=\"icon-input\" value=\"2\" class=\"selectgroup-input\" " + ((StateCmp == 2) ? "checked" : "") + ">\n"
                            + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-warning\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='En Proceso'><i style='font-size:17px' class=\"fas fa-fist-raised\"></i></span>\n"
                            + "                        </label>\n"
                            + "                        <label class=\"selectgroup-item\">\n"
                            + "                          <input type=\"radio\" onclick=\"javascript:location.href='Computer?opt=1&StateCmp=0'\" name=\"icon-input\" value=\"3\" class=\"selectgroup-input\" " + ((StateCmp == 0) ? "checked" : "") + ">\n"
                            + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-danger\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='De Baja'><i style='font-size:17px' class=\"fas fa-thumbs-down\"></i></span>\n"
                            + "                        </label>\n");
                    if (StateCmp < 99) {
                        out.print("                        <label class=\"selectgroup-item\">\n"
                                + "                          <input type=\"radio\" onclick=\"javascript:location.href='Computer?opt=1&StateCmp=99'\" name=\"icon-input\" value=\"3\" class=\"selectgroup-input\" >\n"
                                + "                          <span class=\"selectgroup-button selectgroup-button-icon btn-outline-info\" style='border-radius:6px !important;' data-toggle='tooltip' data-placement='top' title='Quitar Filtro'><i style='font-size:17px' class=\"fas fa-times\"></i></span>\n"
                                + "                        </label>\n");
                    }
                    out.print("</div>\n"
                            + "                    </div>");
                    out.print("<div><input class='form-control' type=\"text\" id=\"myInput\" placeholder=\"Buscar\" onkeyup=\"filterCards()\" ></div>");
                    out.print("</div>");
                    out.print("<div class='row' style='width: 100%;' id='cardContainer'> ");
                    for (int i = 0; i < lst_computer.size(); i++) {
                        Object[] ObjComputer = (Object[]) lst_computer.get(i);
                        int State = Integer.parseInt(ObjComputer[8].toString());
                        out.print("<div class='col-12 col-md-6 col-lg-4 card-container'>");
                        if (ObjComputer[2] == null) {
                            out.print("<div class='card card-secondary'>");
                        } else {
                            out.print("<div class='card card-" + ((State == 0) ? "danger" : (State == 1) ? "success" : "warning") + "'>");
                        }
                        out.print("<div class='card-header'>");
                        out.print("<a href='Computer?opt=1&IdComputer=" + ObjComputer[0] + "'><h4>" + ObjComputer[1] + "</h4></a>");
                        out.print("</div>");
                        out.print("<div class='card-body' style='padding-bottom: 10px; padding-top: 10px;'>");
                        out.print("<p class='textSquare'>Tipo <code>" + ((ObjComputer[3] == null || ObjComputer[3].toString().isEmpty()) ? "<b>SIN PROCESO</b>" : ObjComputer[3]) + "</code></p>");
                        out.print("<p class='textSquare'>Área <code>" + ((ObjComputer[6] == null) ? "<b>SIN DEFINIR</b>" : ObjComputer[6]) + "</code></p>");
                        out.print("<p class='textSquare'>Cargo <code>" + ((ObjComputer[7] == null) ? "<b>SIN DEFINIR</b>" : ObjComputer[7]) + "</code></p>");
                        out.print("<p class='textSquare'>Responsable <code>" + ((ObjComputer[2] == null) ? "<b>SIN DEFINIR</b>" : ObjComputer[2]) + "</code></p>");
                        out.print("<p class='textSquare'>Correo <code>" + ObjComputer[12] + "</code></p>");
                        out.print("<button class='btn btn-green btn-sm' style='position: absolute;right: 0;bottom: 0px;' onclick='window.location.href=\"Computer?opt=1&IdComputer=" + ObjComputer[0] + "&mod=1\"'><i class='fas fa-arrow-right'></i></button>");
                        out.print("</div>");
                        out.print("</div>");
                        out.print("</div>");
                    }
                    out.print("</div>");
                } else {
                    out.print("<div class=''>");
                    out.print("<div class='text-center'>");
                    out.print("<h5>Se ha presentado un error al cargar la información de los equipos</h5><br>");
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
            Logger.getLogger(Tag_computer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return super.doStartTag();
    }
}
